let Core = require('peacetrue-js/dist/peace.core');
let Axios = require('axios');

module.exports = {
    name: 'unique',
    /** {url:String,params:Function,original:*} */
    rule: function (rule, value, callback, source, options) {
        if (Core.isEmpty(value)) return true;

        let ruleOptions = rule.unique;
        if (value === ruleOptions.original) return true;

        !ruleOptions.params && (ruleOptions.params = (options) => ({[options.field]: options.value}));
        let params = ruleOptions.params({field: rule.field, value: value});

        // ruleOptions.params
        // && Object.keys(ruleOptions.params).forEach(key => {
        //     let value = rule.unique.params[key];
        //     params[key] = value instanceof Function ? value() : value;
        // });
        //
        // let original = ruleOptions.original;
        // if (original) {
        //     if (Core.getType(original) !== 'object') original = {[rule.field]: original};
        //     if (Object.keys(params).filter(key => params[key] !== original[key]).length === 0) return true;
        // }

        return Axios
            .get(ruleOptions.url, {params: params})
            .then(t => Promise.resolve(ruleOptions.successHandler ? ruleOptions.successHandler(t) : t),
                t => {
                    ruleOptions.errorHandler && ruleOptions.errorHandler(t);
                    return Promise.resolve('ignored');
                });
    },
    message: {'*': '{_field} must be unique', 'zh_CN': '{_field} 必须是唯一的'}
};
