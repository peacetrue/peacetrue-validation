let Validator = require('async-validator');
// let {Rules, Generator} = require('../dist/peace.async-validator');
let {Rules, Generator, setField, setFields} = require('../index');
setFields({name: '姓名', sex: '性别'});
//requires
(function () {
    let rule = Rules.use('requires', {fields: ['name', 'sex']});
    let descriptor = {value: [{type: 'object'}, rule],};
    let validator = new Validator(Generator.generate(descriptor));
    validator.validate({
        value: {name: null, sex: null}
    }, (errors, field) => {
        console.info('error:', errors, field)
    });

})();

