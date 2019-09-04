let Message = require('../src/message_generator');

let descriptor = {name: [{type: 'string'}, {required: true}, {min: 3, message: '至少 3个字符'}, {max: 8}]};
Message.generate(descriptor, {name: '姓名'});
console.info(descriptor)
