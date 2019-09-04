let Validator = require('async-validator');
let Rules = require("../src/unique");
Rules.setLang('zh_CN');
Rules.setFields({code: '编码'});
let descriptor = {
    code: Rules.use('unique', {
        url: 'http://localhost:8082/common/demos/unique',
        successHandler: response => response.data.data,
        errorHandler: response => console.error(response),
    }),
};

let validator = new Validator(descriptor);
validator.validate({
    code: '1'
}, (errors, field) => {
    console.info('error:', errors, field)
});

