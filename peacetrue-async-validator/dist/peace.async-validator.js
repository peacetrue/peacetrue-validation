!function(e,s){"object"==typeof exports&&"object"==typeof module?module.exports=s(require("peacetrue-js/dist/peace.core"),require("axios")):"function"==typeof define&&define.amd?define(["peacetrue-js/dist/peace.core","axios"],s):"object"==typeof exports?exports.AsyncValidator=s(require("peacetrue-js/dist/peace.core"),require("axios")):(e.Peace=e.Peace||{},e.Peace.AsyncValidator=s(e.Peace.Core,e.axios))}(this,function(e,s){return function(e){var s={};function r(t){if(s[t])return s[t].exports;var i=s[t]={i:t,l:!1,exports:{}};return e[t].call(i.exports,i,i.exports,r),i.l=!0,i.exports}return r.m=e,r.c=s,r.d=function(e,s,t){r.o(e,s)||Object.defineProperty(e,s,{enumerable:!0,get:t})},r.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},r.t=function(e,s){if(1&s&&(e=r(e)),8&s)return e;if(4&s&&"object"==typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(r.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&s&&"string"!=typeof e)for(var i in e)r.d(t,i,function(s){return e[s]}.bind(null,i));return t},r.n=function(e){var s=e&&e.__esModule?function(){return e.default}:function(){return e};return r.d(s,"a",s),s},r.o=function(e,s){return Object.prototype.hasOwnProperty.call(e,s)},r.p="",r(r.s=1)}([function(s,r){s.exports=e},function(e,s,r){let t=r(2);t.register(r(3)),t.register(r(4)),t.register(r(5));let i=r(7);e.exports={Rules:t,Generator:i,setField:function(e,s){t.setField(e,s),i.setField(e,s)},setFields:function(e,s){t.setFields(e,s),i.setFields(e,s)}}},function(e,s,r){let t=r(0);const i={rules:{},messages:{},fields:{},lang:"zh_CN",validators:{}};e.exports={register(e){var s;return i.rules[e.name]=e.rule,this.setMessage(e.name,e.message),i.validators[e.name]=(s=e.name,{validator(e,r,n,a,o){let l=i.rules[s].apply(this,arguments);l instanceof Promise||(l=Promise.resolve(l)),l.then(a=>{if("ignored"===a)return;if(!0===a)return n();let l=e[s],u=l.message||o.messages[s]||i.messages[s][i.lang]||i.messages[s]["*"],c=(o.fields||{})[e.field]||i.fields[e.field]||e.field;l=Object.assign({},l),Object.keys(l).forEach(e=>{let s=l[e];Array.isArray(s)?s.forEach((e,r)=>{e in i.fields&&(s[r]=i.fields[e])}):r in i.fields&&(l[e]=i.fields[r])});let f=Object.assign({_field:c,_value:r},l);n(new Error(t.format(u,f)))})}}),this},use:(e,s)=>Object.assign({[e]:s},i.validators[e]),setLang(e="en"){return i.lang=e,this},setMessage(e,s,r="*"){return"string"==typeof s?i.messages[e][r]=s:i.messages[e]=s,this},setMessages(e,s="*"){return Object.keys(e).forEach(r=>{i.messages[r][s]=e[r]}),this},setField(e,s){return i.fields[e]=s,this},setFields(e){return Object.assign(i.fields,e),this}}},function(e,s,r){let t=r(0);e.exports={name:"requires",rule:function(e,s,r,i,n){return!!t.isEmpty(s)||e.requires.fields.filter(e=>!t.isEmpty(s[e])).length>0},message:{"*":"one of {fields} is required",zh_CN:"{fields}中至少一个有值"}}},function(e,s,r){let t=r(0);e.exports={name:"requiredIf",rule:function(e,s,r,i,n){if(t.isEmpty(s))return!0;let a=e.requiredIf;return!!t.isEmpty(s[a.source])||!t.isEmpty(s[a.target])},message:{"*":"{target} must have a value when {source} has a value",zh_CN:"{source}有值时，{target}必须有值"}}},function(e,s,r){let t=r(0),i=r(6);e.exports={name:"unique",rule:function(e,s,r,n,a){if(t.isEmpty(s))return!0;let o=e.unique;if(s===o.original)return!0;!o.params&&(o.params=e=>({[e.field]:e.value}));let l=o.params({field:e.field,value:s});return i.get(o.url,{params:l}).then(e=>Promise.resolve(o.successHandler?o.successHandler(e):e),e=>(o.errorHandler&&o.errorHandler(e),Promise.resolve("ignored")))},message:{"*":"{_field} must be unique",zh_CN:"{_field} 必须是唯一的"}}},function(e,r){e.exports=s},function(e,s,r){let t=r(8);e.exports={fields:{},setField(e,s){return this.fields[e]=s,this},setFields(e){return Object.assign(this.fields,e),this},generate(e,s){return s=Object.assign({},this.fields,s),Object.keys(e).forEach(r=>{let i,n=e[r];Array.isArray(n)||(n=[n]),n.forEach((e,n)=>{let a,o=Object.keys(e)[0],l=e[o];if(0===n&&(i="type"!==o?"string":l),!(e.message||e.validator||e.asyncValidator))switch(console.info(e,i,o,l),o){case"type":a=(a=(a=t.messages.types[l]).replace("%s",s[r])).replace("%s",t.types[l]),e.message=a;break;case"default":case"required":case"enum":case"whitespace":a=(a=(a=t.messages[o]).replace("%s",s[r])).replace("%s",l),e.message=a;break;default:a=(a=(a=t.messages[i][o]).replace("%s",s[r])).replace("%s",l),e.message=a}})}),e}}},function(e,s){e.exports={messages:{default:"字段 %s 验证错误",required:"%s 是必须的",enum:"%s 必须是 %s 中之一",whitespace:"%s 不能为空",date:{format:"%s 日期 %s 对于格式 %s 是无效的",parse:"%s 日期不能够被解析, %s 是无效的 ",invalid:"%s 日期 %s 是无效的"},types:{string:"%s 不是一个 %s",method:"%s 不是一个 %s (function)",array:"%s 不是一个 %s",object:"%s 不是一个 %s",number:"%s 不是一个 %s",date:"%s 不是一个 %s",boolean:"%s 不是一个 %s",integer:"%s 不是一个 %s",float:"%s 不是一个 %s",regexp:"%s 不是一个有效的 %s",email:"%s 不是一个有效的 %s",url:"%s 不是一个有效的 %s",hex:"%s 不是一个有效的 %s"},string:{len:"%s 必须精确地是 %s 个字符",min:"%s 必须至少是 %s 个字符",max:"%s 不能够长于 %s 个字符",range:"%s 必须在 %s 和 %s 个字符之间"},number:{len:"%s 必须等于 %s",min:"%s 不能小于 %s",max:"%s 不能大于 %s",range:"%s 必须在 %s 和 %s 之间"},array:{len:"%s 长度必须精确地是 %s ",min:"%s 长度不能小于 %s ",max:"%s 长度不能大于 %s",range:"%s 长度必须在 %s 和 %s 之间"},pattern:{mismatch:"%s 值 %s 不能匹配规则 %s"},clone:function(){let e=JSON.parse(JSON.stringify(this));return e.clone=this.clone,e}},types:{string:"字符串",method:"方法",array:"数组",object:"对象",number:"数字",date:"日期",boolean:"布尔值",integer:"整数",float:"小数",regexp:"正则表达式",email:"邮件地址",url:"网址",hex:"16进制格式"}}}])});
//# sourceMappingURL=peace.async-validator.js.map