let Core = require('peacetrue-js/src/core');

function join(name) {
    return {
        validator(rule, value, callback, source, options) {
            let result = Rules.rules[name].apply(this, arguments);
            if (!(result instanceof Promise)) result = Promise.resolve(result);
            result.then(isValid => {
                if (isValid === 'ignored') return;//请求出错时，无法判断是成功还是失败，忽略此次验证
                if (isValid === true) return callback();
                //规则选项
                let ruleOptions = rule[name];
                //消息国际化
                let message = ruleOptions.message || options.messages[name] || Rules.messages[name][Rules.lang] || Rules.messages[name]['*'];
                //当前字段名本地化
                let field = (options.fields || {})[rule.field] || Rules.fields[rule.field] || rule.field;
                //选项字段名本地化
                ruleOptions = Object.assign({}, ruleOptions);
                Object.keys(ruleOptions).forEach(key => {
                    let values = ruleOptions[key];
                    if (Array.isArray(values)) {
                        values.forEach((value, i) => {
                            (value in Rules.fields) && (values[i] = Rules.fields[value]);
                        });
                    } else {
                        (value in Rules.fields) && (ruleOptions[key] = Rules.fields[value]);
                    }
                });
                let messageOptions = Object.assign({_field: field, _value: value}, ruleOptions);
                callback(new Error(Core.format(message, messageOptions)));
            });
        },
    };
}

const Rules = {
    rules: {},
    messages: {},
    fields: {},
    lang: 'zh_CN',
    validators: {},
};

module.exports = {
    /**{name:String, rule:Function, message:String|Object}*/
    register(options) {
        Rules.rules[options.name] = options.rule;
        this.setMessage(options.name, options.message);
        Rules.validators[options.name] = join(options.name);
        return this;
    },
    use(name, options) {
        return Object.assign({[name]: options}, Rules.validators[name]);
    },
    setLang(lang = 'en') {
        Rules.lang = lang;
        return this;
    },
    setMessage(name, message, lang = '*') {
        if (typeof message === 'string') {
            Rules.messages[name][lang] = message;
        } else {
            Rules.messages[name] = message;
        }
        return this;
    },
    setMessages(messages, lang = "*") {
        Object.keys(messages).forEach(key => {
            Rules.messages[key][lang] = messages[key];
        });
        return this;
    },
    setField(name, desc) {
        Rules.fields[name] = desc;
        return this;
    },
    setFields(names) {
        Object.assign(Rules.fields, names);
        return this;
    },
};