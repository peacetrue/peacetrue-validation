let Rules = require('./src/rules');
Rules.register(require('./src/requires'));
Rules.register(require('./src/required_if'));
Rules.register(require('./src/unique'));

let Generator = require('./src/message_generator');

function setField(name, desc) {
    Rules.setField(name, desc);
    Generator.setField(name, desc);
}

function setFields(name, desc) {
    Rules.setFields(name, desc);
    Generator.setFields(name, desc);
}

module.exports = {Rules, Generator, setField, setFields};