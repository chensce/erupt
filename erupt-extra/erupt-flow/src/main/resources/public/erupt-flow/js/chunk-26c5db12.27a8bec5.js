(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-26c5db12"],{"8f73":function(e,t,a){"use strict";t["a"]={props:{mode:{type:String,default:"DESIGN"},formDisable:{type:Boolean,default:!1},required:{type:Boolean,default:!1}},data(){return{}},watch:{_value(e,t){this.$emit("change",e)}},computed:{_value:{get(){return this.value},set(e){this.$emit("input",e)}}},methods:{_opValue(e){return"object"===typeof e?e.value:e},_opLabel(e){return"object"===typeof e?e.label:e}}}},b28d:function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e._self._c;return t("div",["DESIGN"===e.mode?t("div",[t("el-input",{attrs:{size:"medium",disabled:"",placeholder:e.placeholder}})],1):t("div",[t("el-input",{attrs:{size:"medium",disabled:e.formDisable,clearable:"",placeholder:e.placeholder},model:{value:e._value,callback:function(t){e._value=t},expression:"_value"}})],1)])},u=[],n=a("8f73"),o={mixins:[n["a"]],name:"TextInput",components:{},props:{value:{type:String,default:null},placeholder:{type:String,default:"请输入内容"}},data(){return{}},methods:{}},r=o,i=a("2877"),d=Object(i["a"])(r,l,u,!1,null,"5be6e08e",null);t["default"]=d.exports}}]);
//# sourceMappingURL=chunk-26c5db12.27a8bec5.js.map