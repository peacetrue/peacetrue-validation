let messages = require('./messages_zh_CN');

/*
* name: {type:'string',required:true,min:2,max:6}
* => name: {required:true,message:'%s 是必须的'}
* => name: {required:true,message:'姓名 是必须的'}
*
* name: {type:'string'}
* => name: {type:'string',message:'%s 不是一个 %s'}
* => name: {type:'string',message:'名称 不是一个 字符串'}
*/

module.exports = {
    fields: {},
    setField(name, desc) {
        this.fields[name] = desc;
        return this;
    },
    setFields(fields) {
        Object.assign(this.fields, fields);
        return this;
    },
    generate(descriptor, fields) {
        fields = Object.assign({}, this.fields, fields);
        Object.keys(descriptor).forEach(field => {
            let rules = descriptor[field];
            if (!Array.isArray(rules)) rules = [rules];
            let typeRuleValue;
            rules.forEach((rule, i) => {
                let ruleName = Object.keys(rule)[0];
                let ruleValue = rule[ruleName];
                if (i === 0) typeRuleValue = ruleName !== 'type' ? 'string' : ruleValue;
                //已指定消息或者自定义的规则 不处理
                if (rule.message || rule.validator || rule.asyncValidator) return;
                console.info(rule, typeRuleValue, ruleName, ruleValue);
                let message;
                switch (ruleName) {
                    case 'type':
                        message = messages.messages.types[ruleValue];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', messages.types[ruleValue]);
                        rule.message = message;
                        break;
                    case 'default':
                    case 'required':
                    case 'enum':
                    case 'whitespace':
                        message = messages.messages[ruleName];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', ruleValue);
                        rule.message = message;
                        break;
                    default:
                        message = messages.messages[typeRuleValue][ruleName];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', ruleValue);
                        rule.message = message;
                        break;
                }
            });
        });
        return descriptor;
    }
};