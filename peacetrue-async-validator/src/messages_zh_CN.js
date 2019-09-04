module.exports = {
    messages: {
        'default': '字段 %s 验证错误',
        required: '%s 是必须的',
        'enum': '%s 必须是 %s 中之一',
        whitespace: '%s 不能为空',
        date: {
            format: '%s 日期 %s 对于格式 %s 是无效的',
            parse: '%s 日期不能够被解析, %s 是无效的 ',
            invalid: '%s 日期 %s 是无效的'
        },
        types: {
            string: '%s 不是一个 %s',
            method: '%s 不是一个 %s (function)',
            array: '%s 不是一个 %s',
            object: '%s 不是一个 %s',
            number: '%s 不是一个 %s',
            date: '%s 不是一个 %s',
            boolean: '%s 不是一个 %s',
            integer: '%s 不是一个 %s',
            float: '%s 不是一个 %s',
            regexp: '%s 不是一个有效的 %s',
            email: '%s 不是一个有效的 %s',
            url: '%s 不是一个有效的 %s',
            hex: '%s 不是一个有效的 %s'
        },
        string: {
            len: '%s 必须精确地是 %s 个字符',
            min: '%s 必须至少是 %s 个字符',
            max: '%s 不能够长于 %s 个字符',
            range: '%s 必须在 %s 和 %s 个字符之间'
        },
        number: {
            len: '%s 必须等于 %s',
            min: '%s 不能小于 %s',
            max: '%s 不能大于 %s',
            range: '%s 必须在 %s 和 %s 之间'
        },
        array: {
            len: '%s 长度必须精确地是 %s ',
            min: '%s 长度不能小于 %s ',
            max: '%s 长度不能大于 %s',
            range: '%s 长度必须在 %s 和 %s 之间'
        },
        pattern: {
            mismatch: '%s 值 %s 不能匹配规则 %s'
        },
        clone: function clone() {
            let cloned = JSON.parse(JSON.stringify(this));
            cloned.clone = this.clone;
            return cloned;
        }
    },
    types: {
        string: '字符串',
        method: '方法',
        array: '数组',
        object: '对象',
        number: '数字',
        date: '日期',
        boolean: '布尔值',
        integer: '整数',
        float: '小数',
        regexp: '正则表达式',
        email: '邮件地址',
        url: '网址',
        hex: '16进制格式'
    }
};
