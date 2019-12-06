let Core = require('peacetrue-js/dist/peace.core');

module.exports = {
    name: 'requiredIf',
    /** {source:String,target:String} */
    rule: function (rule, value, callback, source, options) {
        if (Core.isEmpty(value)) return true;
        let ruleOptions = rule.requiredIf;
        return Core.isEmpty(value[ruleOptions.source])
            ? true
            : !Core.isEmpty(value[ruleOptions.target]);
    },
    message: {'*': '{target} must have a value when {source} has a value', 'zh_CN': '{source}有值时，{target}必须有值'}
};
