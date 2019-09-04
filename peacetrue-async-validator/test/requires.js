let Validator = require('async-validator');
let Rules = require("../src/requires");
Rules.setLang('zh_CN');
let descriptor = {
    value: Rules.use('requires', {fields: ['name', 'sex'], message: '名称和性别中至少一个有值'}),
};

let validator = new Validator(descriptor);
validator.validate({
    value: {name: null, sex: null}
}, (errors, field) => {
    console.info('error:', errors, field)
});

