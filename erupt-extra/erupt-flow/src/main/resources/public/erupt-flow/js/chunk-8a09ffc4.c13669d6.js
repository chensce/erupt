(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8a09ffc4"],{"01f7":function(t,e,n){"use strict";var i=n("5617"),a=n.n(i);a.a},"23ca":function(t,e,n){},"2b36":function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-form",{ref:"form",staticClass:"process-form",attrs:{"label-position":"top",rules:t.rules,model:t._value}},t._l(t.forms,(function(e,i){return n("el-form-item",{directives:[{name:"show",rawName:"v-show",value:"H"!=e.props.perm,expression:"item.props.perm!='H'"}],key:e.name+i,staticStyle:{"margin-bottom":"8px"},attrs:{prop:e.id,label:"SpanLayout"!==e.name?e.title+"["+e.props.perm+"]":""}},["SpanLayout"!==e.name&&"Description"!==e.name?n("form-design-render",{ref:"sub-item_"+e.id,refInFor:!0,attrs:{mode:t.mode,config:e},on:{change:t.change},model:{value:t._value[e.id],callback:function(n){t.$set(t._value,e.id,n)},expression:"_value[item.id]"}}):n("form-design-render",{ref:"`span-layou_${item.id}`",refInFor:!0,attrs:{mode:t.mode,config:e},model:{value:t._value,callback:function(e){t._value=e},expression:"_value"}})],1)})),1)},a=[],r=(n("4160"),n("b0c0"),n("159b"),n("d16b")),o={name:"FormRender",components:{FormDesignRender:r["a"]},props:{forms:{type:Array,default:function(){return[]}},value:{type:Object,default:function(){return{}}},mode:{type:String,default:"PC"}},data:function(){return{rules:{}}},mounted:function(){this.loadFormConfig(this.forms)},computed:{_value:{get:function(){return this.value},set:function(t){this.$emit("input",t)}}},watch:{},methods:{validate:function(t){var e=this,n=!0;this.$refs.form.validate((function(i){if(n=i,i)for(var a=0;a<e.forms.length;a++)if("TableList"===e.forms[a].name){var r=e.$refs["sub-item_".concat(e.forms[a].id)];if(r&&Array.isArray(r)&&r.length>0&&(r[0].validate((function(t){n=t})),!n))break}t(n)}))},loadFormConfig:function(t){var e=this;t.forEach((function(t){"SpanLayout"===t.name?e.loadFormConfig(t.props.items):(e.$set(e._value,t.id,e.value[t.id]),t.props.required&&e.$set(e.rules,t.id,[{type:"Array"===t.valueType?"array":void 0,required:!0,message:"请填写".concat(t.title),trigger:"blur"}]))}))},change:function(t,e){this.$emit("change",t,e)}}},s=o,u=(n("01f7"),n("2877")),c=Object(u["a"])(s,i,a,!1,null,"e6e9553e",null);e["a"]=c.exports},4773:function(t,e,n){"use strict";var i=n("d7bf"),a=n.n(i);a.a},5617:function(t,e,n){},"5e34":function(t,e,n){"use strict";var i=n("23ca"),a=n.n(i);a.a},"644f":function(t,e,n){"use strict";n.d(e,"g",(function(){return a})),n.d(e,"e",(function(){return r})),n.d(e,"a",(function(){return o})),n.d(e,"f",(function(){return s})),n.d(e,"i",(function(){return u})),n.d(e,"h",(function(){return c})),n.d(e,"d",(function(){return l})),n.d(e,"b",(function(){return d})),n.d(e,"c",(function(){return f}));var i=n("0c6d");function a(t,e){return Object(i["a"])({url:"../erupt-api/erupt-flow/process/start/form/"+t,method:"post",data:e})}function r(t){return Object(i["a"])({url:"../erupt-api/erupt-flow/task/mine",method:"get",params:t})}function o(t,e,n){return Object(i["a"])({url:"../erupt-api/erupt-flow/task/complete/"+t,method:"post",data:{remarks:e,data:n}})}function s(t,e,n){return Object(i["a"])({url:"../erupt-api/erupt-flow/task/refuse/"+t,method:"post",data:{remarks:e,data:n}})}function u(t,e){return Object(i["a"])({url:"../erupt-api/erupt-flow/process/timeline/preview/"+t,method:"post",data:e})}function c(t){return Object(i["a"])({url:"../erupt-api/erupt-flow/process/timeline/"+t,method:"post"})}function l(t){return Object(i["a"])({url:"../erupt-api/erupt-flow/task/detail/"+t,method:"get"})}function d(t){return Object(i["a"])({url:"../erupt-api/erupt-flow/inst/detail/"+t,method:"get"})}function f(t){return Object(i["a"])({url:"../erupt-api/erupt-flow/inst/mine/about",method:"get",params:t})}},9451:function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-timeline",{staticStyle:{"margin-top":"10px"},attrs:{reverse:!1}},[t.activities.length<=0?n("div",{staticStyle:{"padding-left":"10px",color:"#909399"}},[n("p",[t._v("填写表单以预览时间线")]),n("el-skeleton",{staticStyle:{width:"480px"},attrs:{rows:6,animated:""}})],1):t._e(),t._l(t.activities,(function(e,i){return n("el-timeline-item",{key:e.activityKey,attrs:{type:t.timeLineType(e),size:"large",timestamp:e.createDate,placement:"top"}},[e.tasks?n("el-card",{attrs:{shadow:"never"}},[n("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[n("span",[t._v(t._s(e.activityName))]),n("span",{staticStyle:{font:"12px Extra Small",color:"#909399","margin-left":"10px"}},[t._v(t._s(e.description||""))])]),n("div",t._l(e.tasks,(function(e){return n("div",{staticStyle:{display:"inline-block","margin-left":"10px"}},[n("div",{staticStyle:{display:"inline-block"}},[n("el-avatar",{staticStyle:{background:"#409EFF"}},[t._v(t._s(e.finishUser||e.taskOwner||e.assignee))])],1),n("div",{staticStyle:{display:"inline-block","min-height":"60px","vertical-align":"middle","margin-left":"10px"}},[n("div",[t._v(t._s(e.finishUserName||e.taskOwner||e.assignee||"候选人"))]),e.finishDate?n("div",{staticStyle:{color:"#67C23A","font-size":"14px","line-height":"20px"}},[t._v(t._s(e.finishDate))]):n("div",{staticStyle:{color:"#E6A23C","font-size":"14px","line-height":"20px"}},[t._v(t._s("审批中"))])])])})),0)]):t._e(),e.tasks?t._e():n("el-card",{attrs:{shadow:"never"}},[n("span",[t._v(t._s(e.activityName))]),n("span",{staticStyle:{font:"12px Extra Small",color:"#909399","margin-left":"10px"}},[t._v(t._s(e.description||""))])])],1)}))],2)},a=[],r=n("644f"),o={name:"TimeLine",components:{},props:{current:{default:"root"}},data:function(){return{loading:!1,activities:[]}},mounted:function(){},computed:{},methods:{getActivities:function(){return this.activities},timestamp:function(t){return t.activityKey===this.current?t.createDate:""},timeLineType:function(t){return t.activityKey===this.current?"warning":t.finishDate?"success":"primary"},fresh:function(t,e){var n=this;this.loading=!0,this.activities=[],Object(r["i"])(t,e).then((function(t){n.loading=!1,n.activities=t.data}))},freshForInst:function(t){var e=this;this.loading=!0,this.activities=[],Object(r["h"])(t).then((function(t){e.loading=!1,e.activities=t.data}))}}},s=o,u=(n("4773"),n("2877")),c=Object(u["a"])(s,i,a,!1,null,"c9df9cd4",null);e["a"]=c.exports},b78d:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticStyle:{padding:"10px 20px"}},[t.loading?t._e():n("div",[n("p",{staticStyle:{font:"14px Base",color:"#909399"}},[t._v(t._s(t.taskDetail.instCreatorName+" 发布于 "+t.taskDetail.instCreateDate))]),n("form-render",{ref:"form",staticClass:"process-form",attrs:{mode:"PC",forms:t.taskDetail.formItems},on:{input:t.valChange},model:{value:t.taskDetail.formData,callback:function(e){t.$set(t.taskDetail,"formData",e)},expression:"taskDetail.formData"}})],1),n("div",{staticStyle:{"padding-bottom":"10px"},on:{click:function(e){t.showTimeLine=!t.showTimeLine}}},[n("el-button",{staticStyle:{color:"#909399"},attrs:{type:"text",size:"medium"}},[t._v(" 审批流程 "),n("i",{class:{"el-icon-arrow-down":!t.showTimeLine,"el-icon-arrow-up":t.showTimeLine}})])],1),n("div",{directives:[{name:"show",rawName:"v-show",value:t.showTimeLine,expression:"showTimeLine"}]},[n("timeLine",{ref:"timeLine",attrs:{current:t.taskDetail.activityKey}})],1)])},a=[],r=(n("4160"),n("d3b7"),n("159b"),n("2b36")),o=n("d16b"),s=n("644f"),u=n("9451"),c={name:"InitiateProcess",components:{FormDesignRender:o["a"],FormRender:r["a"],TimeLine:u["a"]},props:{instId:{type:String,required:!1},taskId:{type:String,required:!1},mode:{type:String,default:"view"}},data:function(){return{myInstId:null,loading:!1,taskDetail:{formItems:[],formData:{}},showTimeLine:!1,count:0}},mounted:function(){this.loading=!0,this.myInstId=this.instId,this.taskId?this.loadByTaskId(this.taskId):this.myInstId&&this.loadByInstId(this.myInstId)},computed:{},methods:{loadByTaskId:function(t){var e=this;this.loading=!0,Object(s["d"])(t).then((function(t){e.loading=!1;var n,i=t.data||{};if(n=e.findNode(i.activityKey,i.nodeConfig)){var a={};n.props.formPerms.forEach((function(t){a[t.id]=t.perm})),e.setPerms(i.formItems,a)}e.taskDetail=i,e.myInstId=e.taskDetail.processInstId})).then((function(){e.$refs.timeLine.freshForInst(e.myInstId)})).finally((function(){return e.loading=!1}))},findNode:function(t,e){if(t==e.id)return e;var n;if(e.branchs&&e.branchs.length>0){for(var i in e.branchs)if(n=this.findNode(t,e.branchs[i]))return n}else if(e.children&&(n=this.findNode(t,e.children)))return n;return null},setPerms:function(t,e){var n=this;t.forEach((function(t){t.props.perm=e[t.id]||"R","E"==t.props.perm?t.props.editable=!0:t.props.editable=!1,t.props.items&&n.setPerms(t.props.items,e)}))},loadByInstId:function(t){var e=this;this.loading=!0,Object(s["b"])(t).then((function(t){e.loading=!1;var n=t.data||{};e.setPerms(n.formItems,{}),e.taskDetail=n})).then((function(){e.$refs.timeLine.freshForInst(e.myInstId)})).finally((function(){return e.loading=!1}))},validate:function(t){this.$refs.form.validate(t)},getFormData:function(){return this.taskDetail.formData},valChange:function(t){console.log(t)}}},l=c,d=(n("5e34"),n("2877")),f=Object(d["a"])(l,i,a,!1,null,"54e902f4",null);e["default"]=f.exports},d16b:function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n(t.config.name,t._b({ref:"form",tag:"component",attrs:{mode:t.mode},on:{change:t.change},model:{value:t._value,callback:function(e){t._value=e},expression:"_value"}},"component",t.config.props,!1))},a=[],r=(n("d3b7"),function(){return n.e("chunk-afe908e4").then(n.bind(null,"b28d"))}),o=function(){return n.e("chunk-e0ccc8b4").then(n.bind(null,"cf45"))},s=function(){return n.e("chunk-8c1fc5b0").then(n.bind(null,"5cb6"))},u=function(){return n.e("chunk-ba34bacc").then(n.bind(null,"d158"))},c=function(){return n.e("chunk-6b705aef").then(n.bind(null,"0d29"))},l=function(){return n.e("chunk-6bc1e906").then(n.bind(null,"412b"))},d=function(){return n.e("chunk-6a2da2a0").then(n.bind(null,"f89a"))},f=function(){return n.e("chunk-0e5083ab").then(n.bind(null,"4f98"))},m=function(){return n.e("chunk-d6bb8d6c").then(n.bind(null,"77aa"))},p=function(){return n.e("chunk-6381b3f0").then(n.bind(null,"db9e"))},h=function(){return n.e("chunk-4fc2b743").then(n.bind(null,"023d"))},v=function(){return n.e("chunk-2d0f04df").then(n.bind(null,"9c98"))},b=function(){return n.e("chunk-2d0e4c53").then(n.bind(null,"9248"))},g=function(){return n.e("chunk-db9a1e2e").then(n.bind(null,"f13b"))},y=function(){return n.e("chunk-67c6dcf5").then(n.bind(null,"86c3"))},k=function(){return n.e("chunk-29336a56").then(n.bind(null,"6ea6"))},_=function(){return n.e("chunk-2d0e9937").then(n.bind(null,"8db7"))},I=function(){return Promise.all([n.e("chunk-b27dd9ce"),n.e("chunk-b3a1d860"),n.e("chunk-0c54407a")]).then(n.bind(null,"918a"))},w=function(){return Promise.all([n.e("chunk-b27dd9ce"),n.e("chunk-3bcd2b64")]).then(n.bind(null,"7ca0"))},x={TextInput:r,NumberInput:o,AmountInput:s,TextareaInput:u,SelectInput:c,MultipleSelect:l,DateTime:d,DateTimeRange:f,UserPicker:y,DeptPicker:g,RolePicker:k,Description:m,FileUpload:h,ImageUpload:p,MoneyInput:b,Location:v,SignPanel:_,SpanLayout:I,TableList:w},D={name:"FormRender",components:x,props:{mode:{type:String,default:"DESIGN"},value:{default:void 0},config:{type:Object,default:function(){return{}}}},computed:{_value:{get:function(){return this.value},set:function(t){this.$emit("input",t)}}},data:function(){return{}},methods:{validate:function(t){this.$refs.form.validate(t)},change:function(t){this.$emit("change",this.config.id,t)}}},S=D,L=n("2877"),O=Object(L["a"])(S,i,a,!1,null,"16180c58",null);e["a"]=O.exports},d7bf:function(t,e,n){}}]);
//# sourceMappingURL=chunk-8a09ffc4.c13669d6.js.map