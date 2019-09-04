let Validator = require('async-validator');
let Rules = require("../src/required_if");
Rules.setLang('zh_CN');
Rules.setFields({name: '姓名', sex: '性别'});
let descriptor = {
    value: Rules.use('requiredIf', {source: 'name', target: 'sex'}),
};

let validator = new Validator(descriptor);
validator.validate({
    value: {name: '小玉', sex: null}
}, (errors, field) => {
    console.info('error:', errors, field)
});

