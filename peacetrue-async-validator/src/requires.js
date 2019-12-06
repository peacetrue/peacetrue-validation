let Core = require('peacetrue-js/dist/peace.core');

module.exports = {
    name: 'requires',
    /** {fields:Array} */
    rule: function (rule, value, callback, source, options) {
        if (Core.isEmpty(value)) return true;
        return rule.requires.fields.filter(name => !Core.isEmpty(value[name])).length > 0;
    },
    message: {'*': 'one of {fields} is required', 'zh_CN': '{fields}中至少一个有值'}
};
