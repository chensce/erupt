(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0c54407a"],{4590:function(e,t,n){"use strict";var o=n("56aa"),r=n.n(o);r.a},"56aa":function(e,t,n){},"8f73":function(e,t,n){"use strict";n("a4d3"),n("e01a"),n("d28b"),n("d3b7"),n("3ca3"),n("ddb0");function o(e){return o="function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"===typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},o(e)}t["a"]={props:{mode:{type:String,default:"DESIGN"},editable:{type:Boolean,default:!0},required:{type:Boolean,default:!1}},data:function(){return{}},watch:{_value:function(e,t){this.$emit("change",e)}},computed:{_value:{get:function(){return this.value},set:function(e){this.$emit("input",e)}}},methods:{_opValue:function(e){return"object"===o(e)?e.value:e},_opLabel:function(e){return"object"===o(e)?e.label:e}}}},"918a":function(e,t,n){"use strict";n.r(t);var o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",["DESIGN"===e.mode?n("div",[n("draggable",{staticClass:"l-drag-from",attrs:{list:e._items,group:"form",options:{animation:300,chosenClass:"choose",sort:!0}},on:{start:function(t){e.drag=!0,e.selectFormItem=null},end:function(t){e.drag=!1}}},e._l(e._items,(function(t,o){return n("div",{key:o,staticClass:"l-form-item",style:e.getSelectedClass(t),on:{click:function(n){return n.stopPropagation(),e.selectItem(t)}}},[n("div",{staticClass:"l-form-header"},[n("p",[t.props.required?n("span",[e._v("*")]):e._e(),e._v(e._s(t.title))]),n("div",{staticClass:"l-option"},[n("i",{staticClass:"el-icon-close",on:{click:function(t){return e.delItem(o)}}})]),n("form-design-render",{attrs:{config:t}})],1)])})),0),n("div",{staticStyle:{color:"#c0bebe","text-align":"center",width:"90%",padding:"5px"}},[e._v("☝ 拖拽控件到布局容器内部")])],1):n("div",e._l(e.__items,(function(t,o){return n("el-row",{key:o+"_rows",attrs:{gutter:20}},e._l(t,(function(o,r){return n("el-col",{key:r+"_row",attrs:{span:24/t.length}},["SpanLayout"!==o.name&&"Description"!==o.name?n("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"H"!=o.props.perm,expression:"item.props.perm!='H'"}],key:o.name+r,attrs:{prop:o.id,label:"SpanLayout"!==o.name?o.title+"["+o.props.perm+"]":""}},[n("form-design-render",{attrs:{mode:e.mode,config:o},model:{value:e._value[o.id],callback:function(t){e.$set(e._value,o.id,t)},expression:"_value[item.id]"}})],1):n("form-design-render",{attrs:{mode:e.mode,config:o},model:{value:e._value,callback:function(t){e._value=t},expression:"_value"}})],1)})),1)})),1)])},r=[],i=(n("4160"),n("a434"),n("b0c0"),n("159b"),n("96cf"),n("1da1")),s=n("310e"),a=n.n(s),c=n("d16b"),u=n("8f73"),l={mixins:[u["a"]],name:"SpanLayout",components:{draggable:a.a,FormDesignRender:c["a"]},props:{value:{default:null},items:{type:Array,default:function(){return[]}}},computed:{_items:{get:function(){return this.items},set:function(e){this.items=e}},__items:function(){for(var e=[],t=0;t<this.items.length;t++)t>0&&t%2>0&&e.push([this.items[t-1],this.items[t]]);return 2*e.length<this.items.length&&e.push([this.items[this.items.length-1]]),e},selectFormItem:{get:function(){return this.$store.state.selectFormItem},set:function(e){this.$store.state.selectFormItem=e}},nodeMap:function(){return this.$store.state.nodeMap}},data:function(){return{select:null,drag:!1,formConfig:{data:{},rules:{}},form:{formId:"",formName:"",logo:{},formItems:[],process:{},remark:""}}},methods:{selectItem:function(e){this.selectFormItem=e},getSelectedClass:function(e){return this.selectFormItem&&this.selectFormItem.id===e.id?"border-left: 4px solid #f56c6c":""},delItem:function(e){var t=this;this.$confirm("删除组件将会连带删除包含该组件的条件以及相关设置，是否继续?","提示",{confirmButtonText:"确 定",cancelButtonText:"取 消",type:"warning"}).then((function(){"SpanLayout"===t._items[e].name?(t._items[e].props.items.forEach((function(e){t.removeFormItemAbout(e)})),t._items[e].props.items.length=0):t.removeFormItemAbout(t._items[e]),t._items.splice(e,1)}))},removeFormItemAbout:function(e){var t=this;return Object(i["a"])(regeneratorRuntime.mark((function n(){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:t.nodeMap.forEach((function(t){"CONDITION"===t.type&&t.props.groups.forEach((function(t){var n=t.cids.remove(e.id);n>-1&&t.conditions.splice(n,1)})),"ROOT"!==t.type&&"APPROVAL"!==t.type&&"CC"!==t.type||t.props.formPerms.removeByKey("id",e.id)}));case 1:case"end":return n.stop()}}),n)})))()}}},m=l,f=(n("4590"),n("2877")),p=Object(f["a"])(m,o,r,!1,null,"4bf070cd",null);t["default"]=p.exports},d28b:function(e,t,n){var o=n("746f");o("iterator")}}]);
//# sourceMappingURL=chunk-0c54407a.4b6db23f.js.map