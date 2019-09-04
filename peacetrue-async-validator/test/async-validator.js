let Validator = require('async-validator');
let messages = require('../src/messages_zh_CN');

// (function () {
//     //test message
//     let descriptor = {name: {required: true}};
//     let object = {name: undefined};
//     let options = {
//         // messages: messages()
//     };
//     let validator = new Validator(descriptor);
//     validator.messages(messages());
//     validator.validate(object, options, (errors, field) => {
//         console.info(field);
//     });
// })();

// (function () {
//     //test message
//     let descriptor = {name: [{type: 'string'}, {required: true}, {min: 3, message: '至少 3个字符'}, {max: 8}]};
//     let object = {name: 1};
//     let options = {
//         // messages: messages()
//     };
//     let validator = new Validator(descriptor);
//     validator.messages(messages());
//     validator.validate(object, options, (errors, field) => {
//         console.info(field);
//     });
// })();

(function () {
    //test message
    let descriptor = {name: [{type: 'string', required: true, range: {min: 3, max: 9}, /*message: '至少 3个字符'*/}]};
    let object = {name: '1'};
    let validator = new Validator(descriptor);
    validator.messages(messages.messages);
    validator.validate(object, (errors, field) => {
        console.info(field);
    });
})();
