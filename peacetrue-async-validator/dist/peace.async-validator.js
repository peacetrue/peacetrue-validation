(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("axios"), require("peacetrue-js/src/core"));
	else if(typeof define === 'function' && define.amd)
		define(["axios", "peacetrue-js/src/core"], factory);
	else if(typeof exports === 'object')
		exports["AsyncValidator"] = factory(require("axios"), require("peacetrue-js/src/core"));
	else
		root["Peace"] = root["Peace"] || {}, root["Peace"]["AsyncValidator"] = factory(root["axios"], root["Peace"]["Core"]);
})(this, function(__WEBPACK_EXTERNAL_MODULE_axios__, __WEBPACK_EXTERNAL_MODULE_peacetrue_js_src_core__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./index.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./index.js":
/*!******************!*\
  !*** ./index.js ***!
  \******************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let Rules = __webpack_require__(/*! ./src/rules */ "./src/rules.js");
Rules.register(__webpack_require__(/*! ./src/requires */ "./src/requires.js"));
Rules.register(__webpack_require__(/*! ./src/required_if */ "./src/required_if.js"));
Rules.register(__webpack_require__(/*! ./src/unique */ "./src/unique.js"));

let Generator = __webpack_require__(/*! ./src/message_generator */ "./src/message_generator.js");

function setField(name, desc) {
    Rules.setField(name, desc);
    Generator.setField(name, desc);
}

function setFields(name, desc) {
    Rules.setFields(name, desc);
    Generator.setFields(name, desc);
}

module.exports = {Rules, Generator, setField, setFields};

/***/ }),

/***/ "./src/message_generator.js":
/*!**********************************!*\
  !*** ./src/message_generator.js ***!
  \**********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let messages = __webpack_require__(/*! ./messages_zh_CN */ "./src/messages_zh_CN.js");

/*
* name: {type:'string',required:true,min:2,max:6}
* => name: {required:true,message:'%s 是必须的'}
* => name: {required:true,message:'姓名 是必须的'}
*
* name: {type:'string'}
* => name: {type:'string',message:'%s 不是一个 %s'}
* => name: {type:'string',message:'名称 不是一个 字符串'}
*/

module.exports = {
    fields: {},
    setField(name, desc) {
        this.fields[name] = desc;
        return this;
    },
    setFields(fields) {
        Object.assign(this.fields, fields);
        return this;
    },
    generate(descriptor, fields) {
        fields = Object.assign({}, this.fields, fields);
        Object.keys(descriptor).forEach(field => {
            let rules = descriptor[field];
            if (!Array.isArray(rules)) rules = [rules];
            let typeRuleValue;
            rules.forEach((rule, i) => {
                let ruleName = Object.keys(rule)[0];
                let ruleValue = rule[ruleName];
                if (i === 0) typeRuleValue = ruleName !== 'type' ? 'string' : ruleValue;
                //已指定消息或者自定义的规则 不处理
                if (rule.message || rule.validator || rule.asyncValidator) return;
                console.info(rule, typeRuleValue, ruleName, ruleValue);
                let message;
                switch (ruleName) {
                    case 'type':
                        message = messages.messages.types[ruleValue];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', messages.types[ruleValue]);
                        rule.message = message;
                        break;
                    case 'default':
                    case 'required':
                    case 'enum':
                    case 'whitespace':
                        message = messages.messages[ruleName];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', ruleValue);
                        rule.message = message;
                        break;
                    default:
                        message = messages.messages[typeRuleValue][ruleName];
                        message = message.replace('%s', fields[field]);
                        message = message.replace('%s', ruleValue);
                        rule.message = message;
                        break;
                }
            });
        });
        return descriptor;
    }
};

/***/ }),

/***/ "./src/messages_zh_CN.js":
/*!*******************************!*\
  !*** ./src/messages_zh_CN.js ***!
  \*******************************/
/*! no static exports found */
/***/ (function(module, exports) {

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


/***/ }),

/***/ "./src/required_if.js":
/*!****************************!*\
  !*** ./src/required_if.js ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let Core = __webpack_require__(/*! peacetrue-js/src/core */ "peacetrue-js/src/core");

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


/***/ }),

/***/ "./src/requires.js":
/*!*************************!*\
  !*** ./src/requires.js ***!
  \*************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let Core = __webpack_require__(/*! peacetrue-js/src/core */ "peacetrue-js/src/core");

module.exports = {
    name: 'requires',
    /** {fields:Array} */
    rule: function (rule, value, callback, source, options) {
        if (Core.isEmpty(value)) return true;
        return rule.requires.fields.filter(name => !Core.isEmpty(value[name])).length > 0;
    },
    message: {'*': 'one of {fields} is required', 'zh_CN': '{fields}中至少一个有值'}
};


/***/ }),

/***/ "./src/rules.js":
/*!**********************!*\
  !*** ./src/rules.js ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let Core = __webpack_require__(/*! peacetrue-js/src/core */ "peacetrue-js/src/core");

function join(name) {
    return {
        validator(rule, value, callback, source, options) {
            let result = Rules.rules[name].apply(this, arguments);
            if (!(result instanceof Promise)) result = Promise.resolve(result);
            result.then(isValid => {
                if (isValid === 'ignored') return;//请求出错时，无法判断是成功还是失败，忽略此次验证
                if (isValid === true) return callback();
                //规则选项
                let ruleOptions = rule[name];
                //消息国际化
                let message = ruleOptions.message || options.messages[name] || Rules.messages[name][Rules.lang] || Rules.messages[name]['*'];
                //当前字段名本地化
                let field = (options.fields || {})[rule.field] || Rules.fields[rule.field] || rule.field;
                //选项字段名本地化
                ruleOptions = Object.assign({}, ruleOptions);
                Object.keys(ruleOptions).forEach(key => {
                    let values = ruleOptions[key];
                    if (Array.isArray(values)) {
                        values.forEach((value, i) => {
                            (value in Rules.fields) && (values[i] = Rules.fields[value]);
                        });
                    } else {
                        (value in Rules.fields) && (ruleOptions[key] = Rules.fields[value]);
                    }
                });
                let messageOptions = Object.assign({_field: field, _value: value}, ruleOptions);
                callback(new Error(Core.format(message, messageOptions)));
            });
        },
    };
}

const Rules = {
    rules: {},
    messages: {},
    fields: {},
    lang: 'zh_CN',
    validators: {},
};

module.exports = {
    /**{name:String, rule:Function, message:String|Object}*/
    register(options) {
        Rules.rules[options.name] = options.rule;
        this.setMessage(options.name, options.message);
        Rules.validators[options.name] = join(options.name);
        return this;
    },
    use(name, options) {
        return Object.assign({[name]: options}, Rules.validators[name]);
    },
    setLang(lang = 'en') {
        Rules.lang = lang;
        return this;
    },
    setMessage(name, message, lang = '*') {
        if (typeof message === 'string') {
            Rules.messages[name][lang] = message;
        } else {
            Rules.messages[name] = message;
        }
        return this;
    },
    setMessages(messages, lang = "*") {
        Object.keys(messages).forEach(key => {
            Rules.messages[key][lang] = messages[key];
        });
        return this;
    },
    setField(name, desc) {
        Rules.fields[name] = desc;
        return this;
    },
    setFields(names) {
        Object.assign(Rules.fields, names);
        return this;
    },
};

/***/ }),

/***/ "./src/unique.js":
/*!***********************!*\
  !*** ./src/unique.js ***!
  \***********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

let Core = __webpack_require__(/*! peacetrue-js/src/core */ "peacetrue-js/src/core");
let Axios = __webpack_require__(/*! axios */ "axios");

module.exports = {
    name: 'unique',
    /** {url:String,params:Function,original:*} */
    rule: function (rule, value, callback, source, options) {
        if (Core.isEmpty(value)) return true;

        let ruleOptions = rule.unique;
        if (value === ruleOptions.original) return true;

        !ruleOptions.params && (ruleOptions.params = (options) => ({[options.field]: options.value}));
        let params = ruleOptions.params({field: rule.field, value: value});

        // ruleOptions.params
        // && Object.keys(ruleOptions.params).forEach(key => {
        //     let value = rule.unique.params[key];
        //     params[key] = value instanceof Function ? value() : value;
        // });
        //
        // let original = ruleOptions.original;
        // if (original) {
        //     if (Core.getType(original) !== 'object') original = {[rule.field]: original};
        //     if (Object.keys(params).filter(key => params[key] !== original[key]).length === 0) return true;
        // }

        return Axios
            .get(ruleOptions.url, {params: params})
            .then(t => Promise.resolve(ruleOptions.successHandler ? ruleOptions.successHandler(t) : t),
                t => {
                    ruleOptions.errorHandler && ruleOptions.errorHandler(t);
                    return Promise.resolve('ignored');
                });
    },
    message: {'*': '{_field} must be unique', 'zh_CN': '{_field} 必须是唯一的'}
};


/***/ }),

/***/ "axios":
/*!************************!*\
  !*** external "axios" ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = __WEBPACK_EXTERNAL_MODULE_axios__;

/***/ }),

/***/ "peacetrue-js/src/core":
/*!***********************************************************************************************************************************************!*\
  !*** external {"root":["Peace","Core"],"commonjs":"peacetrue-js/src/core","commonjs2":"peacetrue-js/src/core","amd":"peacetrue-js/src/core"} ***!
  \***********************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = __WEBPACK_EXTERNAL_MODULE_peacetrue_js_src_core__;

/***/ })

/******/ });
});
//# sourceMappingURL=peace.async-validator.js.map