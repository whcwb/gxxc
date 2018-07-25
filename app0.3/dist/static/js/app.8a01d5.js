webpackJsonp([25],{

/***/ 117:
/***/ (function(module, exports) {

let ajaxUrl = "http://www.520xclm.com";
// let ajaxUrl = "http://127.0.0.1"
//   let ajaxUrl = "http://192.168.31.228"
// let ajaxUrl = "http://192.168.31.92"//宋

module.exports = {
  url:ajaxUrl,
  // port:'8080',
  port:'8080/biz',
  wechat:{
      getCode:'/wechat/message/getCode',
      getOpenid:'/wechat/message/getOpenid',
      getAccessToken:'/wechat/message/getAccessToken',
      getJsApiSign:'/wechat/message/getJsApiSign',
  },
  ajaxUrl: ajaxUrl + ':',


  getImgUrl:ajaxUrl+':8001/',//获取
  upImgUrl:ajaxUrl+':8080/biz/upload',//上传
  // upImgUrl:ajaxUrl+':9086/upload',//上传



    // 证件识别
  ZJSB:'/app/zjsb',


  LOGIN:'/app/login',//登录接口
  LOGOUT:'/app/logout',//退出用戶
  USERMESS:'/app/ptyh/get',//用户信息
  USERIMGMESS:'/app/wj/getCondition',//获取用户图片

  USERZH:'/app/zh/get',//账户余额
  CODEYZ:'/app/yzyym',//邀请码验证
  PHINECODE:'/app/sendSMSzc',//获取短信验证码
  YZDX:'/app/validateSms',//短信验证
  USERSAVE:'/app/ptyh/save',//用户注册
  SWIPER:'/app/hd/pager ',//轮播图
  TX:'/app/tx/save',//资金提现

  TEAM:'/app/user/pager',//我的团队

  TEAMMESS:'/app/user/myteam',//团队学员信息

  CHUSERMESS:'/app/ptyh/update',//昵称 头像修改
  UPWORLD:'/app/ptyh/mdfPwd',//密码修改
  CPTYPE:'/app/cp/getcplx',//产品类别
  CPLIST:'/app/cp/getcplist',//产品列表



  CPPAY:'/app/order/save',//产品支付
  ADDBANK:'/app/yhk/save',//新增銀行卡
  BANKLIST:'/app/yhk/getCondition',//银行卡列表
  ZDLIST:'/app/yjmx/pager',//账单列表
  ZDLISTTYPE:'/app/zdxm',//账单列表状态

  //我的学员
  SUDENTLIST:'/app/cjd/getcjblist',//学院列表
  XYMESS:'/app/cjd/getxy',//学员详情
  XYCJD:'/app/cjd/save',//学院成绩单上传

  //认证
  IDRZ:'/app/ptyh/updatesm',//身份认证 实名认证
  JLRZ:'app/ptyh/updatelx',//教练员认证

  zdxmPager:'app/zdxm/getzdxm',

  getStudentCoach:'/app/ptyh/getStudentCoach', // 获取学员教练信息


  getZYmess:'/app/ptyh/getJls',//获取专员信息


  getHandleStatus:'/app/kssl/getHandleStatus', // 获取受理状态信息

  getExamInfo:'/app/ksyk/getUserExamInfo', // 考试信息
  getPayInfo:'/app/ksjf/getPayInfo', // 缴费信息

  PF_TEACHER:'/app/pf/save',//学员为教练员评分
  GET_PF:'/app/pf/getjlpf',//获取评分信息


  //忘记密码  找回密码
  GET_FORGET_code:'/app/sendSMScz',//获取短信验证码
  GET_FORGET_RESETPWD:'/app/resetpwd',//重置密码

  XLC:'/app/cd/xlcpager',//训练场列表

  JSZZP:'/app/ptyh/updatesfyjz',//驾驶证


  WXIMGUP:'/app/getWxFile',//微信 图片

  YZYHK:'/app/yhk/yhkyz',//验证银行卡
  BANKCARDDELE:'/app/yhk/remove/',//删除银银行卡

  SIGN:'/app/ptyh/getqm',//签名

  BACKMONEY:'/app/tk/save',//退款
}


/***/ }),

/***/ 120:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";

// CONCATENATED MODULE: ../babel-loader/lib!../vue-loader/lib/selector.js?type=script&index=0!../touchui/dist/core/elements/pull/material-pull-hook.vue
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var CLASS_MAP = {
  'pull-down': 'pull-down-content',
  'pull-up': 'pull-up-content'

  /**
   * material-pull-hook组件只负责处理svg动画，该元素的transform则由Pull类处理
   */
};/* harmony default export */ var material_pull_hook = ({
  props: {
    state: {
      type: Number
    },
    translate: {
      type: Number
    },
    type: String
  },
  computed: {
    classes: function classes() {
      return CLASS_MAP[this.type];
    }
  },
  data: function data() {
    return {
      turn: null,
      svg: null,
      background: null,
      primary: null,
      secondary: null,
      selfTranslate: 0
    };
  },
  mounted: function mounted() {
    this.turn = this.$el.querySelector('.material-progress-circular');
    this.svg = this.$el.querySelector('.progress-circular');
    this.background = this.$el.querySelector('.progress-circular__background');
    this.primary = this.$el.querySelector('.progress-circular__primary');
    this.secondary = this.$el.querySelector('.progress-circular__secondary');
  },

  watch: {
    state: function state() {
      this.handleProgress();
    },
    translate: function translate(val) {
      this.handleProgress();
    }
  },
  methods: {
    handleProgress: function handleProgress() {
      if (this.state === 1 || this.state === 2) {
        this.$el.style.webkitTransition = 'none';
        var rate = Math.abs(this.translate) / 84;
        var dash = Math.floor(rate * 252);
        this.turn.style.transform = 'rotate(' + rate + 'turn)';

        if (dash >= 252) {
          dash = 252;
        }

        this.primary.style.strokeDasharray = dash + '%, 251.32%';
        this.primary.style.webkitTransition = 'none';
      } else if (this.state === 3 || this.state === 4) {
        this.turn.style.tranform = 'rotate(1turn)';
      } else {
        this.primary.style.strokeDasharray = '0%, 251.32%';
      }
    }
  }
});
// CONCATENATED MODULE: ../babel-loader/lib!../touchui-pre-loader/src/script-loader.js!../vue-loader/lib/selector.js?type=script&index=0!../touchui/dist/core/elements/pull/material-pull-hook.vue
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var material_pull_hook_CLASS_MAP = {
  'pull-down': 'pull-down-content',
  'pull-up': 'pull-up-content'

  /**
   * material-pull-hook组件只负责处理svg动画，该元素的transform则由Pull类处理
   */
};/* harmony default export */ var pull_material_pull_hook = ({
  props: {
    state: {
      type: Number
    },
    translate: {
      type: Number
    },
    type: String
  },
  computed: {
    classes: function classes() {
      return material_pull_hook_CLASS_MAP[this.type];
    }
  },
  data: function data() {
    return {
      turn: null,
      svg: null,
      background: null,
      primary: null,
      secondary: null,
      selfTranslate: 0
    };
  },
  mounted: function mounted() {
    this.turn = this.$el.querySelector('.material-progress-circular');
    this.svg = this.$el.querySelector('.progress-circular');
    this.background = this.$el.querySelector('.progress-circular__background');
    this.primary = this.$el.querySelector('.progress-circular__primary');
    this.secondary = this.$el.querySelector('.progress-circular__secondary');
  },

  watch: {
    state: function state() {
      this.handleProgress();
    },
    translate: function translate(val) {
      this.handleProgress();
    }
  },
  methods: {
    handleProgress: function handleProgress() {
      if (this.state === 1 || this.state === 2) {
        this.$el.style.webkitTransition = 'none';
        var rate = Math.abs(this.translate) / 84;
        var dash = Math.floor(rate * 252);
        this.turn.style.transform = 'rotate(' + rate + 'turn)';

        if (dash >= 252) {
          dash = 252;
        }

        this.primary.style.strokeDasharray = dash + '%, 251.32%';
        this.primary.style.webkitTransition = 'none';
      } else if (this.state === 3 || this.state === 4) {
        this.turn.style.tranform = 'rotate(1turn)';
      } else {
        this.primary.style.strokeDasharray = '0%, 251.32%';
      }
    }
  }
});
// CONCATENATED MODULE: ../vue-loader/lib/template-compiler?{"id":"data-v-48e115cf","hasScoped":false,"transformToRequire":{"video":"src","source":"src","img":"src","image":"xlink:href"},"buble":{"transforms":{}}}!../touchui-pre-loader/src/before-template-compiler-loader.js!../touchui-pre-loader/src/template-loader.js!../vue-loader/lib/selector.js?type=template&index=0!../touchui/dist/core/elements/pull/material-pull-hook.vue
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;return _c('div',{staticClass:"material-pull-hook",class:_vm.classes},[_c('div',{staticClass:"pull-hook-progress"},[_c('div',{staticClass:"material-progress-circular"},[_c('svg',{staticClass:"progress-circular",class:{ 'progress-circular--indeterminate': _vm.state >= 3}},[_c('circle',{staticClass:"progress-circular__background",class:{ 'progress-circular--indeterminate__background': _vm.state >= 3}}),_vm._v(" "),_c('circle',{staticClass:"progress-circular__secondary",class:{ 'progress-circular--indeterminate__secondary': _vm.state >= 3},staticStyle:{"display":"none"}}),_vm._v(" "),_c('circle',{staticClass:"progress-circular__primary",class:{ 'progress-circular--indeterminate__primary': _vm.state >= 3}})])])])])}
var staticRenderFns = []
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ var elements_pull_material_pull_hook = (esExports);
// CONCATENATED MODULE: ../touchui/dist/core/elements/pull/material-pull-hook.vue
var normalizeComponent = __webpack_require__(82)
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = null
/* scopeId */
var __vue_scopeId__ = null
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  pull_material_pull_hook,
  elements_pull_material_pull_hook,
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)

/* harmony default export */ var core_elements_pull_material_pull_hook = (Component.exports);

// CONCATENATED MODULE: ../babel-loader/lib!../vue-loader/lib/selector.js?type=script&index=0!../touchui/dist/core/elements/pull/pull-hook.vue
//
//
//
//
//
//
//
//
//
//
//
//


var PullText = {
  'pull-down': {
    1: 'ui.pulldownrefresh.pull',
    2: 'ui.pulldownrefresh.release',
    3: 'ui.pulldownrefresh.refreshing',
    4: 'ui.pulldownrefresh.success',
    5: 'ui.pulldownrefresh.cancel'
  },
  'pull-up': {
    0: 'ui.pullupload.pull_load_more',
    1: 'ui.pullupload.pull',
    2: 'ui.pullupload.release',
    3: 'ui.pullupload.refreshing',
    4: 'ui.pullupload.success',
    5: 'ui.pullupload.cancel'
  }
};

var PullIcon = {
  'pull-down': {
    1: 'arrow down',
    2: 'arrow up',
    3: 'loading',
    4: 'none'
  },
  'pull-up': {
    1: 'arrow up',
    2: 'arrow down',
    3: 'loading',
    4: 'none'
  }
};



/* harmony default export */ var pull_hook = ({
  props: {
    type: {
      type: String,
      default: 'pull-down'
    },
    state: {
      type: Number,
      default: 0
    },
    translate: {
      type: Number,
      default: 0
    },
    pullStyle: {
      type: String,
      default: 'ios'
    }
  },
  components: {
    MaterialPullHook: core_elements_pull_material_pull_hook
  },
  computed: {
    text: function text() {
      return this.$t(PullText[this.type][this.state]);
    },
    contentClasses: function contentClasses() {
      return [this.type + '-content'];
    },
    iconClasses: function iconClasses() {
      return [this.type + '-icon', PullIcon[this.type][this.state]];
    }
  }
});
// CONCATENATED MODULE: ../babel-loader/lib!../touchui-pre-loader/src/script-loader.js!../vue-loader/lib/selector.js?type=script&index=0!../touchui/dist/core/elements/pull/pull-hook.vue
//
//
//
//
//
//
//
//
//
//
//
//


var pull_hook_PullText = {
  'pull-down': {
    1: 'ui.pulldownrefresh.pull',
    2: 'ui.pulldownrefresh.release',
    3: 'ui.pulldownrefresh.refreshing',
    4: 'ui.pulldownrefresh.success',
    5: 'ui.pulldownrefresh.cancel'
  },
  'pull-up': {
    0: 'ui.pullupload.pull_load_more',
    1: 'ui.pullupload.pull',
    2: 'ui.pullupload.release',
    3: 'ui.pullupload.refreshing',
    4: 'ui.pullupload.success',
    5: 'ui.pullupload.cancel'
  }
};

var pull_hook_PullIcon = {
  'pull-down': {
    1: 'arrow down',
    2: 'arrow up',
    3: 'loading',
    4: 'none'
  },
  'pull-up': {
    1: 'arrow up',
    2: 'arrow down',
    3: 'loading',
    4: 'none'
  }
};



/* harmony default export */ var pull_pull_hook = ({
  props: {
    type: {
      type: String,
      default: 'pull-down'
    },
    state: {
      type: Number,
      default: 0
    },
    translate: {
      type: Number,
      default: 0
    },
    pullStyle: {
      type: String,
      default: 'ios'
    }
  },
  components: {
    MaterialPullHook: core_elements_pull_material_pull_hook
  },
  computed: {
    text: function text() {
      return this.$t(pull_hook_PullText[this.type][this.state]);
    },
    contentClasses: function contentClasses() {
      return [this.type + '-content'];
    },
    iconClasses: function iconClasses() {
      return [this.type + '-icon', pull_hook_PullIcon[this.type][this.state]];
    }
  }
});
// CONCATENATED MODULE: ../vue-loader/lib/template-compiler?{"id":"data-v-bc49aa0e","hasScoped":false,"transformToRequire":{"video":"src","source":"src","img":"src","image":"xlink:href"},"buble":{"transforms":{}}}!../touchui-pre-loader/src/before-template-compiler-loader.js!../touchui-pre-loader/src/template-loader.js!../vue-loader/lib/selector.js?type=template&index=0!../touchui/dist/core/elements/pull/pull-hook.vue
var pull_hook_render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;return (_vm.pullStyle === 'ios')?_c('div',{class:_vm.contentClasses},[_c('div',{class:_vm.iconClasses}),_vm._v(" "),_c('span',[_vm._v(_vm._s(_vm.text))])]):_c('material-pull-hook',{attrs:{"state":_vm.state,"translate":_vm.translate,"type":_vm.type}})}
var pull_hook_staticRenderFns = []
var pull_hook_esExports = { render: pull_hook_render, staticRenderFns: pull_hook_staticRenderFns }
/* harmony default export */ var elements_pull_pull_hook = (pull_hook_esExports);
// CONCATENATED MODULE: ../touchui/dist/core/elements/pull/pull-hook.vue
var pull_hook_normalizeComponent = __webpack_require__(82)
/* script */


/* template */

/* template functional */
var pull_hook___vue_template_functional__ = false
/* styles */
var pull_hook___vue_styles__ = null
/* scopeId */
var pull_hook___vue_scopeId__ = null
/* moduleIdentifier (server only) */
var pull_hook___vue_module_identifier__ = null
var pull_hook_Component = pull_hook_normalizeComponent(
  pull_pull_hook,
  elements_pull_pull_hook,
  pull_hook___vue_template_functional__,
  pull_hook___vue_styles__,
  pull_hook___vue_scopeId__,
  pull_hook___vue_module_identifier__
)

/* harmony default export */ var core_elements_pull_pull_hook = __webpack_exports__["a"] = (pull_hook_Component.exports);


/***/ }),

/***/ 128:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__ = __webpack_require__(130);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_ui__ = __webpack_require__(191);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__app_ui__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__static_css_box_less__ = __webpack_require__(201);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__static_css_box_less___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__static_css_box_less__);


__WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].networkTimeout = NaN;
__WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].networkBaseUrl = 'http://www.520xclm.com:8080/biz';


var options = {
  app: __WEBPACK_IMPORTED_MODULE_2__app_ui___default.a,
  beforeEach: function beforeEach(to, from, next) {
    var um = localStorage.getItem('usermess');
    try {
      if (!um) {
        __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().userMess = JSON.parse(localStorage.getItem('usermess'));
      }
    } catch (e) {}

    if (to.path == '/pages/login' || to.path == '/pages/reg' || to.path == '/pages/retrieveworld') {
      next();
      return;
    } else if (!um) {
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].showToast({ title: '用户信息丢失，请重新登录！' });
      next({
        path: '/pages/login'
      });
    } else {
      next();
    }
    console.log('去', to);
    console.log('来', from);
  }
};

__WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].extend({
  // 微信js方法
  getWxJs: function getWxJs() {
    // 微信js初始化 
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "./static/utils/jweixin-1.2.0.js";
    document.body.appendChild(script);

    script.onload = function () {
      // 微信js初始化 回调函数
      console.log('*****wx', wx);

      // 微信js初始化成功后引用 微信功能方法
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().wxUtil = __webpack_require__(202).default;

      //获取Code 直
      var authCode = __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().wxUtil.getQueryString("code");
      console.log('获取code', authCode);

      if (authCode) {
        localStorage.setItem("projectType", __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(true));

        // 获取Openid
        __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().wxUtil.vueParent = this;
        __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().wxUtil.getOpenid(authCode, function (res) {
          console.log('openid-------', res);
          localStorage.setItem("openid", res); //存储openid
          __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().wxUtil.initConfig(); //执行 微信 config
        });
      }
    };
  },
  projectType: function projectType() {
    return localStorage.getItem('projectType');
  },
  getUser: function getUser() {
    return JSON.parse(localStorage.getItem('usermess'));
  },
  getUserMess: function getUserMess(callback) {
    this.$http('POST', __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().apis.USERMESS, {}, function (res) {
      if (res.code == 200 && res.result) {
        console.log('用户信息', res);
        if (res.result.yhTx == '') {
          res.result.yhTx = 'static/img/login/logo.png';
        }
      }
      localStorage.setItem('usermess', __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(res.result));
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().userMess = res.result;
      callback && callback(res.result);
    });
  },
  $http: function $http(method, url, data, callback) {
    var accessTokenStr = localStorage.getItem("token");
    if (accessTokenStr != null && accessTokenStr != '' && accessTokenStr != undefined) {
      var tokMess = JSON.parse(accessTokenStr);
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().Ajax.header.token = tokMess.token;
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().Ajax.header.userId = tokMess.userId;
    }
    var openid = localStorage.getItem("openid");
    if (openid) {
      __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().Ajax.header.openid = openid;
    }

    __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].request({
      // ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+
      url: url, //仅为示例，并非真实的接口地址
      data: data,
      header: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().Ajax.header,
      method: method,
      success: function success(res) {
        console.log('请求成功');
        if (res.data.code == 403) {
          __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].showToast({ title: res.data.message });
          __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].redirectTo({
            url: '/pages/login'
          });
        } else {
          callback && callback(res.data);
        }
      },
      fail: function fail(err) {
        console.log('请求失败');
        callback && callback(err.data);
      },
      complete: function complete(mess) {
        console.log('请求结果');
        // callback && callback(mess.data);
      }
    });
  },
  sayHello: function sayHello() {
    console.log('全局方法参数', __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].getApp().Ajax);
  },
  goBack: function goBack() {
    __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].navigateBack();
  },
  pageHeight: function pageHeight(val) {
    return __WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].DEFAULT_CONTENT_HEIGHT + val;
    // default content height
  },
  getdateStr: function getdateStr(Y) {
    var NowDate = new Date();
    var Year = NowDate.getFullYear() + Y;
    var Month = NowDate.getMonth() + 1;
    var Day = NowDate.getDate();
    var Hours = NowDate.getHours();
    var Minutes = NowDate.getMinutes();
    var Seconds = NowDate.getSeconds();
    // +Seconds+'秒'
    var time = Year + '年' + Month + '月' + Day + '日' + Hours + '时' + Minutes + '分';
    return time;
  }
});

var config = {};
config.routes = [{
  path: '/pages/index',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTitleText: "首页",
      navigationBarTextStyle: "white",
      title: '首页',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-86f785', isTabBar: true },
  component: __webpack_require__(50)
}, {
  path: '/pages/studyCar',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      title: '学车',
      backgroundColor: '#fff',
      delay: false //延迟加载

    }, pageClass: 'page-acca80', isTabBar: true },
  component: __webpack_require__(50)
}, {
  path: '/pages/myTeam',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTitleText: "团队",
      navigationBarTextStyle: "white",
      title: '团队',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-522152', isTabBar: true },
  component: __webpack_require__(50)
}, {
  path: '/pages/user',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#3399FF",
      navigationBarTitleText: "我的",
      navigationBarTextStyle: "white",
      title: '我的',
      backgroundColor: '#3399FF',
      delay: false //延迟加载

    }, pageClass: 'page-3fa662', isTabBar: true },
  component: __webpack_require__(50)
}, {
  path: '*',
  component: function component(resolve) {
    __webpack_require__.e/* require */(8).then(function() { var __WEBPACK_AMD_REQUIRE_ARRAY__ = [__webpack_require__(245)]; ((resolve).apply(null, __WEBPACK_AMD_REQUIRE_ARRAY__));}.bind(this)).catch(__webpack_require__.oe);
  },
  meta: {
    pageConfig: {
      "navigationBarTitleText": "404",
      "delay": false,
      "disableScroll": true
    }
  }
}, {
  path: '/touch-ui-start-page',
  component: __webpack_require__(239).default
}, {
  path: '/',
  redirect: '/pages/index'
}, {
  path: '/pages/login',
  meta: { pageConfig: {
      // "scrollType": "div",
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "登录",
      title: '登录',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-129236', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(7).then(__webpack_require__.bind(null, 246));
  }
}, {
  path: '/pages/detail',
  meta: { pageConfig: {
      navigationBarTitleText: '详情'
    }, pageClass: 'page-a05903', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(16).then(__webpack_require__.bind(null, 247));
  }
}, {
  path: '/pages/reg',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "注册",
      title: '注册',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-dfd55b', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(5).then(__webpack_require__.bind(null, 248));
  }
}, {
  path: '/pages/center/myTeam',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "我的团队",
      title: '我的团队',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-bbc776', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(0).then(__webpack_require__.bind(null, 249));
  }
}, {
  path: '/pages/center/myTeam2',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "我的团队",
      title: '我的团队',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-ec363e', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(6).then(__webpack_require__.bind(null, 250));
  }
}, {
  path: '/pages/center/bill',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "我的账单",
      title: '我的账单',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-8a361c', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(2).then(__webpack_require__.bind(null, 251));
  }
}, {
  path: '/pages/center/bill2',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "我的账单",
      title: '我的账单',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-41b18e', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(20).then(__webpack_require__.bind(null, 252));
  }
}, {
  path: '/pages/home/xlcMess',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "训练场详情",
      title: '训练场详情',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-506b3c', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(15).then(__webpack_require__.bind(null, 253));
  }
}, {
  path: '/pages/center/info',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "个人信息",
      title: '个人信息',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-a95d88', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(19).then(__webpack_require__.bind(null, 254));
  }
}, {
  path: '/pages/center/reallyName',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "实名认证",
      title: '实名认证',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-5987a8', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(18).then(__webpack_require__.bind(null, 255));
  }
}, {
  path: '/pages/tx/index',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "提现",
      title: '提现',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-6098e4', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(11).then(__webpack_require__.bind(null, 256));
  }
}, {
  path: '/pages/tx/pages/bankCardlist',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "选择银行卡",
      title: '选择银行卡',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-6b1b39', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(9).then(__webpack_require__.bind(null, 257));
  }
}, {
  path: '/pages/tx/pages/addBankCard',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "添加银行卡",
      title: '添加银行卡',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-a31946', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(10).then(__webpack_require__.bind(null, 258));
  }
}, {
  path: '/pages/pay/index',
  meta: { pageConfig: {
      "navigationStyle": 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "缴费",
      title: '缴费',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-8ab51c', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(13).then(__webpack_require__.bind(null, 259));
  }
}, {
  path: '/pages/product/index',
  meta: { pageConfig: {
      "navigationStyle": 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "套餐",
      title: '套餐',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-d0e728', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(12).then(__webpack_require__.bind(null, 260));
  }
}, {
  path: '/pages/Agreement/learnCarFile',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#fff",
      navigationStyle: 'custom',
      navigationBarTextStyle: "white",
      navigationBarTitleText: "学车协议",
      title: '学车协议',
      backgroundColor: '#fff'
    }, pageClass: 'page-80090a', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(21).then(__webpack_require__.bind(null, 261));
  }
}, {
  path: '/pages/Agreement/MemberFile',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#fff",
      navigationStyle: 'custom',
      navigationBarTextStyle: "white",
      navigationBarTitleText: "学车协议",
      title: '学车协议',
      backgroundColor: '#fff'
    }, pageClass: 'page-ea1a62', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(22).then(__webpack_require__.bind(null, 262));
  }
}, {
  path: '/pages/common/signName',
  meta: { pageConfig: {
      backgroundColor: '#ffffff'
    }, pageClass: 'page-ce827c', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(3).then(__webpack_require__.bind(null, 263));
  }
}, {
  path: '/pages/common/qrCode',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "邀请码",
      title: '邀请码',
      backgroundColor: '#232323',
      delay: false //延迟加载

    }, pageClass: 'page-3684a4', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(17).then(__webpack_require__.bind(null, 264));
  }
}, {
  path: '/pages/score/index',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "专员详情",
      title: '专员详情',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-0cb1eb', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(1).then(__webpack_require__.bind(null, 265));
  }
}, {
  path: '/pages/retrieveworld',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "密码找回",
      title: '密码找回',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-71d02d', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(4).then(__webpack_require__.bind(null, 266));
  }
}, {
  path: '/pages/common/star',
  meta: { pageConfig: {
      navigationBarTitleText: ""
    }, pageClass: 'page-2e4115', isTabBar: false },
  component: function component() {
    return new Promise(function(resolve) { resolve(); }).then(__webpack_require__.bind(null, 86));
  }
}, {
  path: '/pages/myTeam/details',
  meta: { pageConfig: {
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "学员进度",
      title: '学员进度',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-ea7486', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(14).then(__webpack_require__.bind(null, 267));
  }
}];
config.window = { "scrollType": "div", "navigationBarBackgroundColor": "#ffffff", "navigationBarTextStyle": "black", "navigationBarBorderColor": "rgba(231, 231, 231, 0.6)" };
config.theme = { "theme-color": "#3399ff" };
config.template = undefined;
config.tabBar = {
  groupId: '2289add',
  paths: ['/pages/index', '/pages/studyCar', '/pages/myTeam', '/pages/user'],
  position: 'undefined',
  color: '#7a7e83',
  selectedColor: '#39f',
  borderColor: 'rgba(231, 231, 231, 0.6)',
  backgroundColor: '#fff',
  list: [{
    text: '首页',
    pagePath: '/pages/index',
    pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTitleText: "首页",
      navigationBarTextStyle: "white",
      title: '首页',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    },
    iconName: 'shouye1'

  }, {
    text: '学车',
    pagePath: '/pages/studyCar',
    pageConfig: {
      navigationStyle: 'custom',
      title: '学车',
      backgroundColor: '#fff',
      delay: false //延迟加载

    },
    iconName: 'bus'

  }, {
    text: '团队',
    pagePath: '/pages/myTeam',
    pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTitleText: "团队",
      navigationBarTextStyle: "white",
      title: '团队',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    },
    iconName: 'tuandui'

  }, {
    text: '我的',
    pagePath: '/pages/user',
    pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#3399FF",
      navigationBarTitleText: "我的",
      navigationBarTextStyle: "white",
      title: '我的',
      backgroundColor: '#3399FF',
      delay: false //延迟加载

    },
    iconName: 'user'

  }]

};
config.pageTabBars = [];

__WEBPACK_IMPORTED_MODULE_1_touchui_dist_ui__["a" /* default */].start(options, config);

/***/ }),

/***/ 184:
/***/ (function(module, exports) {

module.exports = {"zh-cn":{"ui.alert.button_text":"确定","ui.confirm.confirm_text":"确定","ui.confirm.cancel_text":"取消","ui.prompt.confirm_text":"确定","ui.prompt.cancel_text":"取消","ui.picker.confirm_text":"确定","ui.picker.cancel_text":"取消","ui.actionsheet.cancel_text":"取消","ui.loading.text":"加载中...","ui.cascader.loading_text":"加载中","ui.cascader.please_select":"请选择","ui.keyboard.space_text":"空格","ui.keyboard.complete_text":"完成","ui.countdown.done_text":"已结束","ui.requeststatus.loading":"加载中...","ui.requeststatus.nomore":"没有更多了...","ui.requeststatus.offline":"网络异常，点击刷新","ui.requeststatus.timeout":"请求超时，点击刷新","ui.requeststatus.error":"请求失败，点击刷新","ui.requeststatus.empty":"没有数据，点击刷新","ui.pulldownrefresh.pull":"下拉刷新","ui.pulldownrefresh.release":"释放刷新","ui.pulldownrefresh.refreshing":"正在刷新...","ui.pulldownrefresh.success":"刷新成功","ui.pulldownrefresh.cancel":"取消下拉","ui.pullupload.pull_load_more":"上拉加载更多","ui.pullupload.pull":"上拉加载","ui.pullupload.release":"释放加载","ui.pullupload.refreshing":"正在加载...","ui.pullupload.success":"加载成功","ui.pullupload.cancel":"取消加载","ui.smscode.getcode":"获取短信验证码","ui.smscode.run":"{%s}秒后重新获取","ui.smscode.reset":"重新获取验证码","ui.smscode.placeholder":"请输入验证码","ui.table.request_failed":"请求失败","ui.table.refresh":"刷新","ui.table.timeout":"网络超时","ui.table.offline":"网络无法连接，请检查您的网络","ui.table.empty":"结果为空","ui.table.need_login":"请先登录","ui.table.loading":"正在加载...","ui.cascader.select":"请选择","ui.calendar.head":"日 一 二 三 四 五 六","ui.calendar.yearsplit":"年","ui.calendar.monthsplit":"月","ui.countdown.over":"已结束"},"en":{"ui.alert.button_text":"OK","ui.confirm.confirm_text":"Confirm","ui.confirm.cancel_text":"Cancel","ui.prompt.confirm_text":"Confirm","ui.prompt.cancel_text":"Cancel","ui.picker.confirm_text":"Confirm","ui.picker.cancel_text":"Cancel","ui.actionsheet.cancel_text":"Cancel","ui.loading.text":"Loading...","ui.cascader.loading_text":"Loading","ui.cascader.please_select":"Please select","ui.keyboard.space_text":"Space","ui.keyboard.complete_text":"Complete","ui.countdown.done_text":"Done","ui.requeststatus.loading":"Loading...","ui.requeststatus.nomore":"No more data...","ui.requeststatus.offline":"Network error, tap to refresh","ui.requeststatus.timeout":"Network error, tap to refresh","ui.requeststatus.error":"Request failed, tap to refresh","ui.requeststatus.empty":"No data, tap to refresh","ui.pulldownrefresh.pull":"Pull refresh","ui.pulldownrefresh.release":"Release to refresh","ui.pulldownrefresh.refreshing":"Refreshing...","ui.pulldownrefresh.success":"Refresh success","ui.pulldownrefresh.cancel":"Cancel pull","ui.pullupload.pull_load_more":"Pull to load more","ui.pullupload.pull":"Pull load","ui.pullupload.release":"Release to load","ui.pullupload.refreshing":"Loading...","ui.pullupload.success":"Load success","ui.pullupload.cancel":"Cancel load","ui.smscode.getcode":"Get code","ui.smscode.run":"{%s} seconds","ui.smscode.reset":"Reset","ui.smscode.placeholder":"Please input code","ui.table.request_failed":"Request failed","ui.table.refresh":"Refresh","ui.table.timeout":"Timeout","ui.table.offline":"Can't connect to network","ui.table.empty":"No results","ui.table.need_login":"Please login first","ui.table.loading":"Loading...","ui.cascader.select":"Please Select","ui.calendar.head":"Sun Mon Tue Wen Thu Fri Sat","ui.calendar.yearsplit":"/","ui.calendar.monthsplit":"","ui.countdown.over":"Time's up"}}

/***/ }),

/***/ 191:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(192)
  __webpack_require__(194)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(195),
  /* template */
  __webpack_require__(200),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 192:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 194:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 195:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__ = __webpack_require__(25);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(17);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tags__ = __webpack_require__(196);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_input__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_form__ = __webpack_require__(125);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_mask__ = __webpack_require__(30);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_tab_bar__ = __webpack_require__(197);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_col__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_row__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_nav_bar__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_text__ = __webpack_require__(40);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_slide_menu__ = __webpack_require__(198);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_view__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__static_ajax_url__ = __webpack_require__(117);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__static_ajax_url___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_14__static_ajax_url__);
















// import wechatUtil from'./static/ajax/wechatUtil.js'
/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiView: __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_view__["a" /* default */],
    UiSlideMenu: __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_slide_menu__["a" /* default */],
    UiText: __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_text__["a" /* default */],
    UiNavBar: __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_nav_bar__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_col__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_icon__["a" /* default */],
    UiTabBar: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_tab_bar__["a" /* default */],
    UiMask: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_mask__["a" /* default */],
    UiForm: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_form__["a" /* default */],
    UiInput: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_input__["a" /* default */],
    UiTags: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tags__["a" /* default */]
  },

  config: {
    "pages": ["pages/index", "pages/login", "pages/detail", "pages/reg", "pages/studyCar", "pages/myTeam", "pages/center/myTeam", "pages/center/myTeam2", "pages/center/bill", "pages/center/bill2", "pages/home/xlcMess", "pages/center/info", "pages/center/reallyName", "pages/tx/index", "pages/tx/pages/bankCardlist", "pages/tx/pages/addBankCard", "pages/pay/index", "pages/product/index", "pages/Agreement/learnCarFile", "pages/Agreement/MemberFile", "pages/common/signName", "pages/common/qrCode", "pages/score/index", "pages/retrieveworld", "pages/common/star", "pages/myTeam/details"],
    "theme": {
      "theme-color": "#3399ff"
    },
    "window": {
      "scrollType": "div",
      "navigationBarBackgroundColor": "#ffffff",
      "navigationBarTextStyle": "black",
      "navigationBarBorderColor": "rgba(231, 231, 231, 0.6)"
    },
    "tabBar": {
      "color": "#7a7e83",
      "selectedColor": "#39f",
      "borderColor": "rgba(231, 231, 231, 0.6)",
      "backgroundColor": "#fff",
      "list": [{
        "pagePath": "pages/index",
        "iconName": "shouye1",
        "text": "首页"
      }, {
        "pagePath": "pages/studyCar",
        "iconName": "bus",
        "text": "学车"
      }, {
        "pagePath": "pages/myTeam",
        "iconName": "tuandui",
        "text": "团队"
      }, {
        "pagePath": "pages/user",
        "iconName": "user",
        "text": "我的"
      }]
    },
    "networkBaseUrl": 'http://www.520xclm.com:8080/biz',
    "networkTimeout": "3000"
  },
  data: function data() {
    return {
      //全局变量
      // ajax数据请求参数配置
      apis: __WEBPACK_IMPORTED_MODULE_14__static_ajax_url___default.a,
      Ajax: {
        url: __WEBPACK_IMPORTED_MODULE_14__static_ajax_url___default.a.url,
        port: __WEBPACK_IMPORTED_MODULE_14__static_ajax_url___default.a.port,
        header: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'token': '',
          'userId': '',
          'openid': ''
        }
      },

      wxUtil: null, //微信 功能方法

      yqm: '', //新用户注册邀请码
      userMess: '', //用户信息

      payMess: null, //支付信息
      signUrl: '', //签名地址
      backsignUrl: '', //签名页面返回
      xlcMess: {
        mess: 'mess'
      }, //训练场详情

      QRpage: '/pages/index', //我的邀请码返回页面地址

      bankIndex: 3, //银行卡列表索引
      banklist: [], //银行卡列表

      zyMess: {}, //专员信息

      myTeamMess: {}, //团队信息传值-查看学院进度


      globalData: {
        searchShow: false
      },
      searchValue: '',
      WIN_WIDTH: ui.WIN_WIDTH,
      tabIndex: 0,
      tabBarList: [],
      navStyle: {},
      tabBar: {},
      tabBarHeight: 56,
      isApp: ui.IS_APP,
      headerHeight: ui.DEFAULT_HEADER_HEIGHT,
      paths: [],
      barHeight: ui.STATUS_BAR_HEIGHT,
      showSlideMenu: false
    };
  },

  computed: {
    // 控制通用导航栏是否显示
    navigationBarVisible: function navigationBarVisible() {
      return this.winConfig.navigationStyle !== 'custom';
    },

    // 控制通用导航栏是否显示返回按钮（首页不显示，其他页显示）
    showBack: function showBack() {
      var homePath = this.paths[0];
      if (this.tabBar && this.tabBar.paths) {
        return this.tabBar.paths.indexOf(homePath) < 0;
      } else {
        return homePath !== this.$route.path;
      }
    },

    // 通用导航栏应用app.json和page.json的设置
    winConfig: function winConfig() {
      var window = this.$router.options.window;
      var config = __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, window, this.pageConfig);
      var color = config.navigationBarTextStyle || 'white';

      var scrollType = document.body.getAttribute('scroll-type');

      this.navStyle = {
        backgroundColor: config.navigationBarBackgroundColor,
        color: color,
        borderBottom: '1px solid ' + config.navigationBarBorderColor,
        backgroundImage: config.navigationBarBackgroundGradient,
        position: scrollType === 'body' ? 'fixed' : 'absolute'
      };

      if (this.navStyle.backgroundColor) {
        this.navStyle.backgroundImage = 'none';
      }

      if (config.navigationBarBorderColor) {
        this.navStyle.borderBottom = '1px solid ' + config.navigationBarBorderColor;
      } else {
        this.navStyle.borderBottom = '0';
      }
      return config;
    },

    // 控制底部标签栏是否显示
    isTabBar: function isTabBar() {
      return this.tabBarList && this.tabBarList.length > 0;
    },

    // 设置底部标签栏样式
    tabBarStyle: function tabBarStyle() {
      var style = {};
      if (this.navigationBarVisible && this.tabBar.position === 'top') {
        style.top = this.headerHeight + 'px';
      }
      var scrollType = document.body.getAttribute('scroll-type');
      style.position = scrollType === 'body' ? 'fixed' : 'absolute';
      return style;
    },
    bottom: function bottom() {
      return this.isTabBar ? this.tabBarHeight + 'px' : 0;
    },

    // 底部标签栏应用app.json和page.json的设置
    pageConfig: function pageConfig() {
      if (this.isTabBar) {
        return this.tabBarList[this.tabIndex].pageConfig;
      } else {
        return this.$route.meta.pageConfig || window.__$uiPageConfig;
      }
    }
  },
  watch: {
    '$route.path': function $routePath(path) {
      this.handleIosGesture();
      this.initTab();
      this.initBodyStyle();
    },
    wxUtil: function wxUtil(n, o) {
      //微信方法监听
      console.log('数据监听*-*-*-*-*-*-*-');
      if (n == null) {
        this.getWxJs();
      }
    }
  },
  // 初始化
  created: function created() {
    var _this = this;

    //微信 ? 微信 ：App
    localStorage.setItem("projectType", false);

    //微信js 初始化
    this.getWxJs();
    //微信js初始化END


    this.initTab();
    this.initPaths();
    this.$nextTick(function () {
      _this.handleIosGesture();
    });
  },

  methods: {
    handleNavIconTap: function handleNavIconTap() {
      this.showSlideMenu = !this.showSlideMenu;
    },

    // 初始化底部标签栏
    initTab: function initTab() {
      this.tabBar = this.getTabBar();
      this.tabBarList = this.tabBar.list;
      if (this.tabBar.paths) {
        this.tabIndex = this.tabBar.paths.indexOf(this.$route.path);
      }
    },

    // 初始化路由
    initPaths: function initPaths() {
      var routes = this.$router.options.routes;
      this.paths = routes.map(function (item) {
        return item.path;
      });
    },
    initBodyStyle: function initBodyStyle() {
      if (this.winConfig && this.winConfig.backgroundColor) {
        document.body.style.backgroundColor = this.winConfig.backgroundColor;
      }
    },

    // iOS是否禁用侧滑退出
    handleIosGesture: function handleIosGesture() {
      if (ui.IS_IOS) {
        ui.setPagePopGesture && ui.setPagePopGesture({
          openPagePopGesture: !this.pageConfig.disableIosGesture
        });
      }
    },

    // tabBar示例用到，底部标签栏切换前事件处理
    handleTabBeforeChange: function handleTabBeforeChange(_ref) {
      var id = _ref.id,
          index = _ref.index,
          next = _ref.next;

      next();
    },


    // tabBar示例用到，底部标签栏切换后事件处理
    handleTabChange: function handleTabChange(_ref2) {
      var id = _ref2.id,
          index = _ref2.index;
    },

    // 回退处理
    handleBack: function handleBack() {
      ui.navigateBack();
    },
    getTabBar: function getTabBar() {
      var tabBars = [this.$router.options.tabBar].concat(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default()(this.$router.options.pageTabBars));
      for (var i = 0; i < tabBars.length; i++) {
        if (tabBars[i].paths && tabBars[i].paths.indexOf(this.$route.path) > -1) {
          return tabBars[i];
        }
      }
      return {};
    },

    // ---------------------------
    // 退出搜索
    hideFullScreenSearch: function hideFullScreenSearch() {
      this.globalData.searchShow = false;
      ui.setStatusBarStyle({ style: 'light' });
    },

    // 清空历史记录
    deleteHistory: function deleteHistory() {
      // this.historyContent = []
    },

    // 搜索退出还原状态栏颜色
    searchHideHandle: function searchHideHandle() {
      // ui.setStatusBarStyle({ style: 'light' })
    },

    // 进入搜索设置状态栏颜色为黑色
    searchShowHandle: function searchShowHandle() {
      // ui.setStatusBarStyle({ style: 'dark' })
    },

    // 提交搜索
    searchSubmit: function searchSubmit(e) {
      //   let value = e.search
      //   if (value !== '') {
      //   this.historyContent.unshift(value)
      //   this.hideFullScreenSearch()
      //   ui.showToast({ title: value })
      // }    
      //     this.searchValue = ''
    },

    // 点击热门搜索
    hotTap: function hotTap(opt) {
      // this.hideFullScreenSearch()
      // ui.showToast({
      //     title: this.hotList[opt].text
      // })
    },

    // 点击搜索历史
    historyTap: function historyTap(index) {
      // this.hideFullScreenSearch()
      // ui.showToast({
      //     title: this.historyContent[index]
      // })
    }
  },
  mounted: function mounted() {
    ui.closeSplashscreen();
  }
});

/***/ }),

/***/ 200:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "ui-view",
    attrs: {
      "id": "ui-app"
    }
  }, [_c('ui-slide-menu', {
    attrs: {
      "width": "280",
      "threshold": "30",
      "touch": _vm.pageConfig.touchSlideMenu
    },
    model: {
      value: (_vm.showSlideMenu),
      callback: function($$v) {
        _vm.showSlideMenu = $$v
      },
      expression: "showSlideMenu"
    }
  }, [_c('div', {
    staticClass: "menu ui-view",
    slot: "menu"
  }, [_c('ui-text', {
    staticStyle: {
      "color": "#fff",
      "margin": "10px"
    },
    attrs: {
      "block": ""
    }
  }, [_vm._v("侧滑菜单")])], 1), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    slot: "panel"
  }, [(_vm.pageConfig) ? _c('ui-nav-bar', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (_vm.navigationBarVisible),
      expression: "navigationBarVisible"
    }],
    staticClass: "page-29b3bf",
    style: (_vm.navStyle)
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 80px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content"
  }, [_c('span', {
    staticClass: "back",
    on: {
      "tap": _vm.handleBack
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "left1",
      "font-size": "24"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "nav-bar-center ui-col ui-col-align-center align-center",
    style: ({
      'flex': ("0 0 " + (_vm.WIN_WIDTH-160) + "px")
    }),
    attrs: {
      "content-direction": "row"
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center flex-row"
  }, [_c('div', {
    staticClass: "nav-bar-center-loading ui-view",
    staticStyle: {
      "display": "none"
    }
  }), _vm._v(" "), _c('span', {
    staticClass: "nav-bar-center-text"
  }, [_vm._v(_vm._s(_vm.$t(_vm.winConfig.navigationBarTitleText)))])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 80px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content"
  })])])]) : _vm._e(), _vm._v(" "), _c('keep-alive', [(!_vm.isApp && _vm.pageConfig.cache) ? _c('router-view', {
    staticClass: "router-view"
  }) : _vm._e()], 1), _vm._v(" "), (_vm.isApp || !_vm.pageConfig.cache) ? _c('router-view', {
    staticClass: "router-view"
  }) : _vm._e(), _vm._v(" "), (_vm.isTabBar) ? _c('ui-tab-bar', {
    style: (_vm.tabBarStyle),
    attrs: {
      "id": _vm.tabBar.id,
      "group-id": _vm.tabBar.groupId,
      "icon-size": _vm.tabBar.iconSize,
      "font-size": _vm.tabBar.fontSize,
      "position": _vm.tabBar.position,
      "list": _vm.tabBarList,
      "paths": _vm.tabBar.paths,
      "background-color": _vm.tabBar.backgroundColor,
      "border-color": _vm.tabBar.borderColor,
      "color": _vm.tabBar.color,
      "selected-color": _vm.tabBar.selectedColor
    },
    on: {
      "beforechange": _vm.handleTabBeforeChange,
      "change": _vm.handleTabChange
    },
    model: {
      value: (_vm.tabIndex),
      callback: function($$v) {
        _vm.tabIndex = $$v
      },
      expression: "tabIndex"
    }
  }) : _vm._e()], 1)]), _vm._v(" "), _c('ui-mask', {
    staticClass: "full-screen-box page-29b3bf",
    attrs: {
      "top": "0"
    },
    on: {
      "hide": _vm.searchHideHandle,
      "show": _vm.searchShowHandle
    },
    model: {
      value: (_vm.globalData.searchShow),
      callback: function($$v) {
        _vm.globalData.searchShow = $$v
      },
      expression: "globalData.searchShow"
    }
  }, [_c('div', {
    staticClass: "bar ui-view",
    style: ({
      height: _vm.barHeight + 'px'
    })
  }), _vm._v(" "), _c('div', {
    staticClass: "search_bg ui-row",
    style: ({
      'height': '46px'
    }),
    attrs: {
      "height": "46"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'padding-left': '15px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  }, [_c('div', {
    staticClass: "search ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({
      'height': '30px'
    }),
    attrs: {
      "height": "30"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'flex': '0 0 30px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "30px"
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "magnifier",
      "size": "16",
      "color": "#B5B5B5"
    }
  })], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col valign-middle",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle",
    staticStyle: {
      "height": "30px"
    }
  }, [_c('ui-form', {
    attrs: {
      "action": "/search"
    },
    on: {
      "submit": _vm.searchSubmit
    }
  }, [_c('ui-input', {
    attrs: {
      "type": "search",
      "name": "search",
      "placeholder": "搜索",
      "focus": _vm.globalData.searchShow,
      "show-clear": false
    },
    model: {
      value: (_vm.searchValue),
      callback: function($$v) {
        _vm.searchValue = $$v
      },
      expression: "searchValue"
    }
  })], 1)], 1)])])])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'flex': '0 0 50px'
    }),
    on: {
      "tap": _vm.hideFullScreenSearch
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  }, [_c('div', {
    staticClass: "cancel ui-view"
  }, [_vm._v("取消")])])])])])], 1)
},staticRenderFns: []}

/***/ }),

/***/ 201:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 202:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jquery__ = __webpack_require__(203);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jquery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jquery__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__url__ = __webpack_require__(117);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__url___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__url__);



// import wx from '#/static/ajax/jweixin-1.2.0.js'
// var wx = require('#/static/ajax/jweixin-1.2.0.js')
// import router from '@/router'
let wechatUtil = {}

wechatUtil.now = new Date();
wechatUtil.timestamp = parseInt(wechatUtil.now.getTime()/1000);
wechatUtil.appId = 'wxb01394ea85904296';
wechatUtil.token = '';
wechatUtil.sign = '';
wechatUtil.code = '';
wechatUtil.openid = '';
wechatUtil.nonceStr = randomString(16);
wechatUtil.baseUrl = __WEBPACK_IMPORTED_MODULE_1__url___default.a.url+':8080/biz/';
wechatUtil.authLoginUrl = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+wechatUtil.appId+'&redirect_uri='+__WEBPACK_IMPORTED_MODULE_1__url___default.a.url+'/wx&response_type=code&scope=snsapi_userinfo&state=debug&connect_redirect=1#wechat_redirect';

wechatUtil.afterReady = null;
//存储Vue对象，用来在微信方法中，可以调用vue内容
wechatUtil.vueParent = null;
wechatUtil.getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

wechatUtil.getCode = ()=>{
    window.location.href = wechatUtil.authLoginUrl;
}
wechatUtil.getOpenid = (code,callback)=>{
    __WEBPACK_IMPORTED_MODULE_0_jquery___default.a.ajax({
        // url:wechatUtil.baseUrl+urls.wechat.getOpenid+"?code="+code,
        url:__WEBPACK_IMPORTED_MODULE_1__url___default.a.url+':8080/biz'+__WEBPACK_IMPORTED_MODULE_1__url___default.a.wechat.getOpenid+"?code="+code,
        type:'get',
        success:function(res){
            console.log(res);
            if (res.code == 200){
                wechatUtil.openid = res.message;
                callback && callback(res.message)
            }else {
              alert('ID获取失败！！！')
            }
        }
    })
}

wechatUtil.initConfig = ()=>{
    let curl = location.href.split('#')[0];
  // let url = wechatUtil.baseUrl+urls.wechat.getJsApiSign+"?&timestamp="+wechatUtil.timestamp+"&url="+encodeURIComponent(curl)+'&nonceStr='+wechatUtil.nonceStr;
  let url = __WEBPACK_IMPORTED_MODULE_1__url___default.a.url+':8080/biz'+__WEBPACK_IMPORTED_MODULE_1__url___default.a.wechat.getJsApiSign+"?&timestamp="+wechatUtil.timestamp+"&url="+encodeURIComponent(curl)+'&nonceStr='+wechatUtil.nonceStr;
  __WEBPACK_IMPORTED_MODULE_0_jquery___default.a.ajax({
    url:url,
    type:'get',
    success:function(res){
      if (res.code == 200){
        wechatUtil.sign = res.message;
        wechatUtil.config();
      }
    }
  })
}


wechatUtil.config = function(){
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: wechatUtil.appId, // 必填，公众号的唯一标识
        timestamp: wechatUtil.timestamp, // 必填，生成签名的时间戳
        nonceStr: wechatUtil.nonceStr, // 必填，生成签名的随机串
        signature: wechatUtil.sign,// 必填，签名
        jsApiList: ['scanQRCode','chooseImage','uploadImage','previewImage','chooseWXPay'] // 必填，需要使用的JS接口列表
    });
    wechatUtil.refreshNonceStr();
}
wechatUtil.refreshNonceStr = ()=>{
    wechatUtil.nonceStr = randomString(16);
}

wechatUtil.pay = function(prepay,callback){
    wx.chooseWXPay({
        appId:prepay.appId,
        timestamp: prepay.timeStamp,// 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
        nonceStr: prepay.nonceStr, // 支付签名随机串，不长于 32 位
        package: prepay.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
        signType: prepay.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
        paySign: prepay.paySign, // 支付签名
        success: function (res) {
          callback && callback(res)
// 支付成功后的回调函数
        }
    });
}
wechatUtil.checkJsApi = ()=>{
  wx.checkJsApi({
    jsApiList: ['scanQRCode','chooseImage','uploadImage','previewImage','chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    success: function(res) {
      console.log(res);
      // 以键值对的形式返回，可用的api值true，不可用为false
      // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    }
  });
}
wx.ready(function(){
    if (typeof wechatUtil.afterReady == 'function'){
        wechatUtil.afterReady();
        return;
    }
    //微信jsapi使用，不允许页面切换，所以这里使用router来做页面跳转，不再使用location强制跳页面
    if (wechatUtil.vueParent){
    //   wechatUtil.vueParent.$router.push({name:'indexName'});
    console.log('页面跳转')
      return;
    }
    wechatUtil.checkJsApi();
    //window.location.href = "/wx/";
    // chooseImage();
    // wechatUtil.qrScan();
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
});

wx.error(function(res){
    console.log('error',res);
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});
//--------------------------------------------------------------------------
//以上功能方法 是调用微信开发功能的前期准备*******调用wechatUtil.getAccessToken()
//---------------------------------------------------------------------------
wechatUtil.qrScan = (callback)=>{//打开微信扫码功能
    wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function (res) {
            // alert(res)
            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
            // alert(result);
            callback && callback(result);
        },
        fail : function(res) {
            console.log(res)
            // alert(JSON.stringify(res));
        }
    });
}
wechatUtil.chooseImage = (callback)=>{//拍照或从手机相册中选图接口
    console.log('chooseImage');
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            console.log(res);
            callback && callback(localIds);
        }
    });
}
wechatUtil.previewImage = ()=>{//预览图片接口
  wx.previewImage({
    current: '', // 当前显示图片的http链接
    urls: [] // 需要预览的图片http链接列表
  })
},
wechatUtil.uploadImage = (id , callback)=>{//上传图片接口
    wx.uploadImage({
        localId: id, // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
          console.log(res);
          var serverId = res.serverId; // 返回图片的服务器端ID
          callback && callback(res)
        }
    });
}
function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = $chars.length;
    var pwd = '';
    for (let i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

/* harmony default export */ __webpack_exports__["default"] = (wechatUtil);

/***/ }),

/***/ 204:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 205:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 206:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__ = __webpack_require__(25);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_array_from__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_array_from___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_array_from__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__pages_index_ui__ = __webpack_require__(207);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__pages_index_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__pages_index_ui__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__pages_studyCar_ui__ = __webpack_require__(215);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__pages_studyCar_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__pages_studyCar_ui__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_myTeam_ui__ = __webpack_require__(227);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_myTeam_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4__pages_myTeam_ui__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__pages_user_ui__ = __webpack_require__(232);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__pages_user_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5__pages_user_ui__);


//
//
//
//
//
//
//
//
//
//
//






/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    Page0: __WEBPACK_IMPORTED_MODULE_2__pages_index_ui___default.a,
    Page1: __WEBPACK_IMPORTED_MODULE_3__pages_studyCar_ui___default.a,
    Page2: __WEBPACK_IMPORTED_MODULE_4__pages_myTeam_ui___default.a,
    Page3: __WEBPACK_IMPORTED_MODULE_5__pages_user_ui___default.a
  },
  data: function data() {
    return {
      tabIndex: -1,
      cachedPages: []
    };
  },

  watch: {
    tabIndex: function tabIndex(index, oldIndex) {
      this.setPageVisibility();
      this.handlePageEvent(index, oldIndex);
    }
  },
  created: function created() {
    var _this = this;

    this.initTabIndex();
    this.$EventService.on('ui-tab-change-2289add', function (index) {
      _this.tabIndex = index;
    });
  },

  methods: {
    setPageVisibility: function setPageVisibility() {
      var _this2 = this;

      this.$children.forEach(function (child, index) {
        var classList = child.$el.classList;
        if (classList) {
          var pageClass = _this2.findPageClass(classList);
          if (pageClass) {
            _this2.$nextTick(function () {
              var els = __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_array_from___default()(document.querySelectorAll(pageClass));
              els.forEach(function (el) {
                if (!(el.__vue__ && el.__vue__.isDialog)) {
                  el.style.visibility = index === _this2.tabIndex ? 'visible' : 'hidden';
                }
              });
            });
          }
        }
      });
    },
    handlePageEvent: function handlePageEvent(index, oldIndex) {
      var _this3 = this;

      var child = this.$children[index];
      if (child.$children) {
        var page = ui.findTabPageInstance(child);

        var handle = function handle() {
          if (page) {
            var pageLoad = page.pageLoad;
            page.showCustomNavBar = true;
            page.isTabBarActive = true;

            page.triggerTabLoad();

            _this3.$nextTick(function () {

              var oldTabPage = _this3.$children[oldIndex];
              if (oldTabPage) {
                var oldPage = ui.findTabPageInstance(oldTabPage);
                oldPage.showCustomNavBar = false;
                oldPage.isTabBarActive = false;
                // let pageClass = this.findPageClass(oldTabPage.$el.classList)
                // document.querySelector(pageClass).__vue__.showCustomNavBar = false
              }
            });
          }
        };

        if (page) {
          handle();
        } else {
          window.setTimeout(function () {
            page = child.$children[0];
            handle();
          }, 200);
        }
      } else {
        throw new Error('请将ui-page作为页面的根元素');
      }
    },
    findPageClass: function findPageClass(classList) {
      for (var i = 0; i < classList.length; i++) {
        if (classList[i].match(/page-\w{6}/)) {
          return '.' + classList[i];
        }
      }
    },
    initTabIndex: function initTabIndex() {
      var tabBars = [this.$router.options.tabBar].concat(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default()(this.$router.options.pageTabBars));
      var tabBar = void 0;
      for (var i = 0; i < tabBars.length; i++) {
        if (tabBars[i].paths.indexOf(this.$route.path) > -1) {
          tabBar = tabBars[i];
        }
      }

      if (tabBar && tabBar.paths) {
        this.tabIndex = tabBar.paths.indexOf(this.$route.path);
      }
    }
  }
});

/***/ }),

/***/ 207:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(208)
  __webpack_require__(209)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(210),
  /* template */
  __webpack_require__(214),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 208:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 209:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 210:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_check_list__ = __webpack_require__(118);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_popup__ = __webpack_require__(58);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_divider__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_scroll_view__ = __webpack_require__(55);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_tab__ = __webpack_require__(121);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_tabs__ = __webpack_require__(122);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_sticky__ = __webpack_require__(212);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_image__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_ex_swiper_item__ = __webpack_require__(83);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_ex_swiper__ = __webpack_require__(84);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_view__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_col__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_row__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_touchui_dist_components_nav_bar__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_touchui_dist_components_page__ = __webpack_require__(39);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

















/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiPage: __WEBPACK_IMPORTED_MODULE_15_touchui_dist_components_page__["a" /* default */],
    UiNavBar: __WEBPACK_IMPORTED_MODULE_14_touchui_dist_components_nav_bar__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_col__["a" /* default */],
    UiView: __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_view__["a" /* default */],
    UiExSwiper: __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_ex_swiper__["a" /* default */],
    UiExSwiperItem: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_ex_swiper_item__["a" /* default */],
    UiImage: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_image__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_icon__["a" /* default */],
    UiSticky: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_sticky__["a" /* default */],
    UiTabs: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_tabs__["a" /* default */],
    UiTab: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_tab__["a" /* default */],
    UiScrollView: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_scroll_view__["a" /* default */],
    UiDivider: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_divider__["a" /* default */],
    UiPopup: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_popup__["a" /* default */],
    UiCheckList: __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_check_list__["a" /* default */]
  },

  config: {
    navigationStyle: 'custom',
    navigationBarBackgroundColor: "#26a2ff",
    navigationBarTitleText: "首页",
    navigationBarTextStyle: "white",
    title: '首页',
    backgroundColor: '#f2f2f2',
    delay: false //延迟加载
  },
  data: function data() {
    return {
      tabsIndex: 0,

      popupshow: false,
      checklist: ['全部'],
      foodList: ['武汉市', '江岸区', '江汉区', '硚口区', '汉阳区', '武昌区', '青山区', '洪山区', '东西湖区', '汉南区', '蔡甸区', '江夏区', '黄陂区', '新洲区'],

      pullUp: {
        distance: 50,
        onBegin: function onBegin() {},
        onActive: this.handleLoadMore,
        onAfter: this.handlePullUpAfter
      },

      height: ui.DEFAULT_CONTENT_HEIGHT,
      items: [{}],
      autoplay: {
        delay: 2.5 * 1000
      },

      itemlist: [],
      count: 0,
      loadMore: true,
      offsetTop: ui.DEFAULT_HEADER_HEIGHT,
      customStyle: {
        'background-color': '#fff'
      }
    };
  },

  methods: {
    handleLoadMore: function handleLoadMore() {
      var _this = this;

      ui.showToast({
        title: '上拉'
      });
      this.count++;
      if (this.count > 10) {
        this.loadMore = false;
        return false;
      } else {
        window.setTimeout(function () {
          for (var i = _this.bottom + 1; i <= _this.bottom + 10; i++) {
            _this.items.push('\u5217\u8868\u9879\u76EE' + i);
          }
          _this.bottom += 10;
        }, 1500);
      }
    },
    handlePullUpAfter: function handlePullUpAfter(flag) {
      ui.showToast({
        title: flag
      });
      if (flag) {
        ui.showToast({
          title: '处理上拉刷新'
        });
      }
    },
    handleChange: function handleChange(index, key) {},
    change: function change(val) {
      //check-list 单选 数据返回
      console.log(val);
      console.log(this.checklist);
      this.popupshow = false;
    },
    showPopup: function showPopup() {
      this.tabsIndex = 1;
      this.popupshow = true;
    },
    goMyTeam: function goMyTeam() {
      ui.navigateTo({
        url: 'center/myTeam2'
      });
    },
    goBill: function goBill() {
      ui.navigateTo({
        url: 'center/bill2'
      });
    },
    qrcode: function qrcode() {
      ui.getApp().QRpage = '/pages/index';
      ui.navigateTo({
        url: '/pages/common/qrCode'
      });
    },
    goXlc: function goXlc(item) {
      ui.getApp().xlcMess = item;
      ui.navigateTo({
        url: 'home/xlcMess'
      });
    },

    // getLocationMess(){//定位 获取 位置信息
    //   ui.getLocation({
    //     type: 'wgs84',
    //     success: function (res) {
    //       console.log('获取 位置信息',res);

    //     }
    //   })
    // },
    getSwiperImg: function getSwiperImg() {
      var _this2 = this;

      //获取轮播图片
      this.$http('POST', ui.getApp().apis.SWIPER, { hdSxs: 0 }, function (res) {
        if (res.code == 200) {
          _this2.items = res.page.list;
        }
      });
    },
    XLC: function XLC() {
      var _this3 = this;

      var v = this;
      this.$http('POST', ui.getApp().apis.XLC, {}, function (res) {
        if (res.code == 200) {
          _this3.itemlist = res.page.list;
        }
      });

      // this.$http.post(this.apis.XLC).then((res)=>{
      //   if(res.code==200){
      //     v.xlcList = res.page.list
      //   }
      //   console.log(res)
      // }).catch((err)=>{

      // })
    }
  },
  created: function created() {
    this.getSwiperImg();
    this.XLC();
  },
  mounted: function mounted() {
    // for (let i = 1; i <= 10; i++) {
    //   this.itemlist.push(i + ' - 内容文本')
    // }
  }
});

/***/ }),

/***/ 213:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 214:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('ui-page', {
    staticClass: "page-86f785"
  }, [_c('ui-nav-bar', {
    staticClass: "page-86f785",
    attrs: {
      "custom-style": {
        'color': '#fff',
        'background-color': '#26a2ff'
      }
    },
    slot: "nav-bar"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({
      'height': '46px'
    }),
    attrs: {
      "height": "46"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'flex': '0 0 60px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  }, [_c('div', {
    staticClass: "nav_title ui-view"
  }, [_vm._v("首页")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'flex': '0 0 60px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  })])])]), _vm._v(" "), _c('div', {
    staticClass: "grid_box backF2 ui-view"
  }, [_c('div', {
    staticClass: "demo demo3 ui-view"
  }, [_c('ui-ex-swiper', {
    attrs: {
      "id": "swiperdotsstyle",
      "autoplay": false,
      "pagination": "",
      "centered-slides": "",
      "loop": "",
      "space-between": "5",
      "slides-per-view": "auto"
    }
  }, _vm._l((_vm.items), function(item, index) {
    return _c('ui-ex-swiper-item', {
      key: index,
      attrs: {
        "ui:for-item": "item",
        "ui:for-index": "index"
      }
    }, [_c('ui-image', {
      attrs: {
        "width": "100%",
        "height": "100%",
        "src": item.hdTpdz,
        "mode": "simple"
      }
    })], 1)
  }))], 1), _vm._v(" "), _c('div', {
    staticClass: "module2 ui-view"
  }, [_c('div', {
    staticClass: "price ui-view"
  }, [_vm._v("\n            听说邀请好友还有现金，速速围观\n          ")]), _vm._v(" "), _c('div', {
    staticClass: "funcFloat box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 ui-view",
    on: {
      "tap": _vm.goBill
    }
  }, [_c('div', {
    staticClass: "itemVal ui-view"
  }, [_vm._v("\n                    0元\n                ")]), _vm._v(" "), _c('div', {
    staticClass: "itemTxt ui-view"
  }, [_vm._v("\n                    累计奖励\n                ")])]), _vm._v(" "), _c('div', {
    staticClass: "box_row_100 ui-view",
    staticStyle: {
      "text-align": "center"
    },
    on: {
      "tap": _vm.goMyTeam
    }
  }, [_c('div', {
    staticClass: "itemVal ui-view"
  }, [_vm._v("\n                    0人\n                ")]), _vm._v(" "), _c('div', {
    staticClass: "itemTxt ui-view"
  }, [_vm._v("\n                    已邀请\n                ")])]), _vm._v(" "), _c('div', {
    staticClass: "box_row_100 ui-view",
    staticStyle: {
      "text-align": "right"
    },
    on: {
      "tap": _vm.qrcode
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "erweima",
      "size": "28",
      "color": "#fff"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "sticky-in-relative-container ui-view"
  }, [_c('ui-sticky', {
    staticClass: "page-86f785",
    attrs: {
      "stick-on-tap": "",
      "custom-style": _vm.customStyle
    }
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_c('ui-tabs', {
    attrs: {
      "index": _vm.tabsIndex
    },
    on: {
      "change": function($event) {
        _vm.handleChange($event, 'current1')
      }
    }
  }, [_c('ui-tab', {
    nativeOn: {
      "tap": function($event) {
        _vm.tabsIndex = 0
      }
    }
  }, [_vm._v("推荐")]), _vm._v(" "), _c('ui-tab', {
    nativeOn: {
      "tap": function($event) {
        _vm.showPopup($event)
      }
    }
  }, [_vm._v(_vm._s(_vm.checklist[0]))])], 1)], 1), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    slot: "sticky"
  }, [_c('ui-tabs', {
    attrs: {
      "index": _vm.tabsIndex
    },
    on: {
      "change": function($event) {
        _vm.handleChange($event, 'current1')
      }
    }
  }, [_c('ui-tab', [_vm._v("推荐")]), _vm._v(" "), _c('ui-tab', {
    nativeOn: {
      "tap": function($event) {
        _vm.showPopup($event)
      }
    }
  }, [_vm._v(_vm._s(_vm.checklist[0]))])], 1)], 1)])], 1), _vm._v(" "), _c('ui-scroll-view', {
    attrs: {
      "height": _vm.pageHeight(0),
      "scroll-y": "scroll-y",
      "pull-up": _vm.pullUp
    }
  }, [_c('div', {
    staticClass: "content_list ui-view"
  }, _vm._l((_vm.itemlist), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "xlcList box_row ui-view",
      on: {
        "tap": function($event) {
          _vm.goXlc(item)
        }
      }
    }, [_c('div', {
      staticClass: "xlcItemImg ui-view"
    }, [_c('ui-image', {
      attrs: {
        "src": "static/img/home/car0000.png",
        "width": "100"
      }
    })], 1), _vm._v(" "), _c('div', {
      staticClass: "box_row_100 xlcItemMess ui-view"
    }, [_c('div', {
      staticClass: "ui-row",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col-content"
    }, [_vm._v("\n                            " + _vm._s(item.schoolName) + "\n                          ")])]), _vm._v(" "), _c('div', {
      staticClass: "ui-col ui-col-align-right align-right",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col-content align-right"
    }, [_vm._v("￥2600.00")])])]), _vm._v(" "), _c('div', {
      staticClass: "ui-row",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col ui-col-1",
      style: ({
        'flex': '0 0 8.333333333333332%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content"
    }, [_c('ui-icon', {
      attrs: {
        "type": "dingwei",
        "color": "#3399ff"
      }
    })], 1)]), _vm._v(" "), _c('div', {
      staticClass: "ui-col",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col-content"
    }, [_vm._v(_vm._s(item.address))])])]), _vm._v(" "), _c('div', {
      staticClass: "ui-row",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col ui-col-1",
      style: ({
        'flex': '0 0 8.333333333333332%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content"
    }, [_c('ui-icon', {
      attrs: {
        "type": "bus",
        "color": "#3399ff"
      }
    })], 1)]), _vm._v(" "), _c('div', {
      staticClass: "ui-col",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col-content"
    }, [_vm._v("566、567、568")])])]), _vm._v(" "), _c('div', {
      staticClass: "ui-row",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col ui-col-align-right align-right",
      style: ({})
    }, [_c('div', {
      staticClass: "ui-col-content align-right"
    }, [_vm._v(" >3000米")])])])])])
  })), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "width": "200px",
      "margin": "0 auto"
    }
  }, [_c('ui-divider', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (!_vm.loadMore),
      expression: "!loadMore"
    }]
  }, [_vm._v("没有新数据了")])], 1)])], 1), _vm._v(" "), _c('ui-popup', {
    staticClass: "page-86f785",
    attrs: {
      "position": "top"
    },
    model: {
      value: (_vm.popupshow),
      callback: function($$v) {
        _vm.popupshow = $$v
      },
      expression: "popupshow"
    }
  }, [_c('div', {
    staticClass: "top_pop ui-view"
  }, [_c('div', {
    staticClass: "checkTit ui-view"
  }, [_c('ui-tabs', {
    attrs: {
      "index": _vm.tabsIndex
    },
    on: {
      "change": function($event) {
        _vm.handleChange($event, 'current1')
      }
    }
  }, [_c('ui-tab', {
    nativeOn: {
      "tap": function($event) {
        _vm.tabsIndex = 0, _vm.popupshow = false
      }
    }
  }, [_vm._v("推荐")]), _vm._v(" "), _c('ui-tab', {
    nativeOn: {
      "tap": function($event) {
        _vm.showPopup($event)
      }
    }
  }, [_vm._v(_vm._s(_vm.checklist[0]))])], 1)], 1), _vm._v(" "), _c('ui-scroll-view', {
    attrs: {
      "scroll-y": "scroll-y"
    }
  }, [_c('ui-check-list', {
    attrs: {
      "max": "1",
      "label-position": "left",
      "options": _vm.foodList,
      "type": "plain",
      "color": "#3AC3B0"
    },
    on: {
      "change": _vm.change
    },
    model: {
      value: (_vm.checklist),
      callback: function($$v) {
        _vm.checklist = $$v
      },
      expression: "checklist"
    }
  })], 1)], 1)])], 1)
},staticRenderFns: []}

/***/ }),

/***/ 215:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(216)
  __webpack_require__(217)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(218),
  /* template */
  __webpack_require__(226),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 216:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 217:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 218:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_graphics_circular__ = __webpack_require__(219);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_badge__ = __webpack_require__(116);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_timeline_item__ = __webpack_require__(220);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_timeline__ = __webpack_require__(221);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_text__ = __webpack_require__(40);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_image__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_ex_swiper_item__ = __webpack_require__(83);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_ex_swiper__ = __webpack_require__(84);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_divider__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_col__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_row__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_view__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_page__ = __webpack_require__(39);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__static_ajax_mixin__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__common_star__ = __webpack_require__(86);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__common_star___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_15__common_star__);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

















/* harmony default export */ __webpack_exports__["default"] = ({
  config: {
    navigationStyle: 'custom',
    title: '学车',
    backgroundColor: '#fff',
    delay: false //延迟加载
  },
  components: {
    mystar: __WEBPACK_IMPORTED_MODULE_15__common_star___default.a,
    UiPage: __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_page__["a" /* default */],
    UiView: __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_view__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_col__["a" /* default */],
    UiDivider: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_divider__["a" /* default */],
    UiExSwiper: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_ex_swiper__["a" /* default */],
    UiExSwiperItem: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_ex_swiper_item__["a" /* default */],
    UiImage: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_image__["a" /* default */],
    UiText: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_text__["a" /* default */],
    UiTimeline: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_timeline__["a" /* default */],
    UiTimelineItem: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_timeline_item__["a" /* default */],
    UiBadge: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_badge__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_icon__["a" /* default */],
    UiCircular: __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_graphics_circular__["a" /* default */]
  },
  mixins: [__WEBPACK_IMPORTED_MODULE_14__static_ajax_mixin__["a" /* default */]],
  data: function data() {
    return {
      list: [{ pos: '医院体检', content: '待完成' }, { pos: '入网面签', content: '待完成' }, { pos: '档案采集', content: '待完成' }, { pos: '受理成功', content: '待完成' }],
      left: (window.innerWidth - 280) / 2,
      winWidth: window.innerWidth,
      color: '',
      thisIndex: 0,
      handleStatus: [],

      usermess: {},
      timeline: 0, //swiper索引
      timelineNext: 0, //swiper 模块索引
      items: [{
        src: 'static/img/bank/card1.png',
        name: '受理进度',
        mess: '受理成功可考试'
      }, {
        src: 'static/img/bank/card1.png',
        name: '科目一',
        mess: '基础理论知识考试'
      }, {
        src: 'static/img/bank/card1.png',
        name: '科目二',
        mess: '场地驾驶技能考试'
      }, {
        src: 'static/img/bank/card1.png',
        name: '科目三',
        mess: '道路驾驶技能考试'
      }, {
        src: 'static/img/bank/card1.png',
        name: '科目四',
        mess: '安全文明驾驶常识'
      }],
      jlPf: 0,
      zyMess: { //专员信息
        yhXm: ""
      },
      zyMwssList: [//专员信息列表
      {
        yhXm: ""
      }],
      showStar: false,
      payInfo: {}, //缴费信息
      examInfo: [] //考试信息
    };
  },
  created: function created() {
    this.getUserMess();

    this.usermess = this.getUser();
    this.timeline = parseInt(this.getUser().yhDqzt);
    console.log('----', this.timeline);

    this.getZYmess(); //获取专员信息
    this.swiperChange(this.timeline); //初始化选项卡

    this.getHandleStatus(); // 获取受理状态信息
    this.getPayInfo(); // 缴费信息
    this.getExamInfo(); //考试信息
  },
  mounted: function mounted() {},

  methods: {
    gouTxt: function gouTxt(url) {
      window.location.href = url;
    },
    getZYmess: function getZYmess() {
      //获取专员信息
      var v = this;
      this.$http("post", ui.getApp().apis.getZYmess, {}, function (res) {
        console.log('钻元信息', res);
        if (res.code == 200 && res.result) {
          res.result.forEach(function (item, index) {
            if (item.jlPf) {
              item.jlPf = parseInt(item.jlPf);
            }
          });
          v.zyMwssList = res.result;
          v.zyMess = v.zyMwssList[v.timeline];
          v.showStar = true;
        }
      });
    },
    getHandleStatus: function getHandleStatus() {
      var _this = this;

      // 获取受理状态信息
      this.$http("POST", ui.getApp().apis.getHandleStatus, { yhId: this.usermess.id }, function (res) {
        console.log('获取受理状态信息-', res);
        if (res.code == 200 && res.result) {
          _this.handleStatus = res.result;
          _this.thisIndex = parseInt(res.message);
        }
      });
    },
    getPayInfo: function getPayInfo() {
      var _this2 = this;

      // 缴费信息
      this.$http("GET", ui.getApp().apis.getPayInfo, { yhId: this.usermess.id }, function (res) {
        if (res.code == 200 && res.result) {
          console.log('缴费信息', res);

          _this2.payInfo = res.result;
        }
      });
    },
    getExamInfo: function getExamInfo() {
      var _this3 = this;

      // 考试信息
      var v = this;
      this.$http('GET', ui.getApp().apis.getExamInfo, { yhId: this.usermess.id }, function (res) {
        console.log('考试信息', res);

        if (res.code == 200 && res.result) {
          _this3.examInfo = res.result;
        }
      });
    },
    goscore: function goscore() {
      //去专员评分界面
      if (this.zyMwssList[this.timelineNext] == "") {
        ui.showToast({ title: '当前阶段没有为您分配教练员', duration: 2.5 * 1000 });
      } else {
        ui.getApp().zyMess = this.zyMwssList[this.timelineNext];
        ui.getApp().zyMess.type = this.timelineNext;
        ui.navigateTo({
          url: '/pages/score/index'
        });
      }
    },
    swiperChange: function swiperChange(val) {
      //板块切换
      console.log('板块切换索引', val);
      this.timelineNext = val;
      this.zyMess = this.zyMwssList[val]; //板块切换 专员信息 随之切换
      this.items.forEach(function (item, index) {
        if (index == val) {
          item.src = 'static/img/bank/card1.png';
        } else {
          item.src = 'static/img/bank/card2.png';
        }
      });
    },
    showMap: function showMap(exam) {
      //考场路线导航

      var script = document.createElement("script");
      script.type = "text/javascript";
      var callbackName = '_callback' + Date.now();
      window[callbackName] = function () {
        // 百度地图代码
        if (!exam.examPlaceLat || !exam.examPlaceLng) {
          Toast('未设置经纬度');
          return;
        }
        var map = new BMap.Map("allmap");
        var geolocation = new BMap.Geolocation();
        var lat = exam.examPlaceLat;
        var lng = exam.examPlaceLng;
        geolocation.getCurrentPosition(function (r) {
          if (this.getStatus() === BMAP_STATUS_SUCCESS) {
            location.href = "http://api.map.baidu.com/direction?origin=" + r.point.lat + "," + r.point.lng + "&destination=" + lat + "," + lng + "&mode=driving&region=武汉&output=html";
          } else {
            alert('failed' + this.getStatus());
          }
        }, { enableHighAccuracy: true });
      };
      script.src = "http://api.map.baidu.com/api?v=2.0&ak=evDHwrRoILvlkrvaZEFiGp30&callback=" + callbackName;
      document.body.appendChild(script);
    }
  }
});

/***/ }),

/***/ 222:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 223:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 224:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_view__ = __webpack_require__(21);
//
//
//
//
//
//
//
//
//
//
//
//



/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiView: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_view__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_icon__["a" /* default */]
  },

  config: {
    navigationBarTitleText: ""
  },
  data: function data() {
    return {
      starList: [{
        color: '#c0bfbf'
      }, {
        color: '#c0bfbf'
      }, {
        color: '#c0bfbf'
      }, {
        color: '#c0bfbf'
      }, {
        color: '#c0bfbf'
      }]
    };
  },

  props: {
    num: {
      type: Number,
      default: 0
    }
  },
  created: function created() {
    var _this = this;

    console.log(this.num);
    this.starList.forEach(function (item, index) {
      if (index < _this.num) {
        item.color = "#ffc217";
      }
    });
  },

  methods: {},
  mounted: function mounted() {
    var _this2 = this;

    console.log(this.num);
    this.starList.forEach(function (item, index) {
      if (index < _this2.num) {
        item.color = "#ffc217";
      }
    });
  }
});

/***/ }),

/***/ 225:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "box_row ui-view page-2e4115"
  }, [_vm._l((_vm.starList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "ui-view"
    }, [_c('ui-icon', {
      attrs: {
        "type": "xingxing2",
        "size": "28",
        "color": item.color
      }
    })], 1)
  }), _vm._v(" "), _c('div', {
    staticClass: "fzsty ui-view"
  }, [_vm._v("\n    " + _vm._s(_vm.num) + "分\n  ")])], 2)
},staticRenderFns: []}

/***/ }),

/***/ 226:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('ui-page', {
    staticClass: "page-acca80",
    staticStyle: {
      "overflow-y": "auto"
    },
    attrs: {
      "top": "0"
    }
  }, [_c('div', {
    staticClass: "win ui-view"
  }, [_c('div', {
    staticClass: "_top ui-view",
    staticStyle: {
      "background-image": "url('static/img/png/title-bg.png')"
    }
  }, [_c('div', {
    staticClass: "nimg ui-view",
    on: {
      "tap": _vm.goscore
    }
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "titSty ui-view"
  })]), _vm._v(" "), _c('div', {
    staticClass: "_font alcen ui-view"
  }, [_vm._v("\n                    " + _vm._s(_vm._f("yhXmZY")(_vm.zyMess.yhXm)) + "\n                  ")]), _vm._v(" "), _c('div', {
    staticClass: "_fontQy alcen ui-view"
  }, [_vm._v("\n                    " + _vm._s(_vm._f("jlQu")(_vm.zyMess.jlQu)) + "\n                  ")]), _vm._v(" "), _c('div', {
    staticClass: "alcen ui-view"
  }), _vm._v(" "), _c('div', {
    staticClass: "alcen ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({
      'height': '35px'
    }),
    attrs: {
      "height": "35"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    staticStyle: {},
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content align-center",
    staticStyle: {
      "height": "35px"
    }
  }, [(_vm.showStar) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('mystar', {
    attrs: {
      "num": _vm.zyMess.jlPf
    }
  })], 1) : _vm._e()])])])])])]), _vm._v(" "), _c('div', {
    staticClass: "content ui-view"
  }, [_c('div', {
    staticClass: "demo2 ui-view"
  }, [_c('ui-divider', {
    attrs: {
      "color": "#ff9900"
    }
  }, [_c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "color": "#ff9900"
    }
  }, [_vm._v("\n                    *请注意您的学车进度！\n                  ")])]), _vm._v(" "), _c('ui-ex-swiper', {
    attrs: {
      "pagination": "",
      "centered-slides": "",
      "space-between": "10",
      "loop": false,
      "slides-per-view": "auto",
      "index": _vm.timeline
    },
    on: {
      "change": _vm.swiperChange
    }
  }, _vm._l((_vm.items), function(item, index) {
    return _c('ui-ex-swiper-item', {
      key: index,
      style: ({
        'background-image': 'url(' + item.src + ')'
      }),
      attrs: {
        "ui:for-item": "item",
        "ui:for-index": "index"
      }
    }, [_c('div', {
      staticClass: "swiperbox ui-view"
    }, [_c('div', {
      staticClass: "name ui-view"
    }, [_vm._v(_vm._s(item.name))]), _vm._v(" "), _c('div', {
      staticClass: "mess ui-view"
    }, [_vm._v(_vm._s(item.mess))])])])
  }))], 1), _vm._v(" "), _c('div', {
    staticClass: "boxSty ui-view"
  }, [(_vm.timelineNext == 0) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/info_1_2/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/bm.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("报名条件")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/info_1_4/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/fy.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("学车费用")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/info_1_6/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/tj.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("报名条件")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/info_1_8/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/xclc.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("学车流程")])], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "padding": "8px"
    }
  }, [_c('div', {
    staticClass: "slboxSty ui-view"
  }, [_c('div', {
    staticClass: "slTitSty ui-view"
  }, [_vm._v("\n                              受理进度\n                          ")]), _vm._v(" "), _c('ui-timeline', {
    staticStyle: {
      "text-align": "center"
    },
    attrs: {
      "mode": "row"
    }
  }, _vm._l((_vm.list), function(item, index) {
    return _c('ui-timeline-item', {
      key: index,
      staticClass: "timeline",
      attrs: {
        "point-color": index <= _vm.thisIndex ? '#3399ff' : '#8a8a8a',
        "line-color": index < _vm.thisIndex ? '#3399ff' : '',
        "shadow-class": index === _vm.thisIndex ? 'shadow-class' : '',
        "show-line": index === _vm.list.length - 1 ? false : true
      }
    }, [_c('div', {
      staticClass: "continer ui-view"
    }, [_c('div', {
      staticClass: "pos ui-view"
    }, [_vm._v("\n                                      " + _vm._s(item.pos) + "\n                                    ")]), _vm._v(" "), _c('div', {
      staticClass: "gary ui-view"
    }, [(_vm.handleStatus[JSON.stringify(index + 1)]) ? _c('div', {
      staticClass: "ui-view"
    }, [_vm._v("\n                                        " + _vm._s(_vm.handleStatus[index + 1].name)), _c('br'), _vm._v("\n                                        " + _vm._s(_vm.handleStatus[index + 1].slSj) + "\n                                      ")]) : _c('div', {
      staticClass: "ui-view"
    }, [_vm._v("\n                                        待完成\n                                      ")])])])])
  }))], 1)])]) : _vm._e(), _vm._v(" "), (_vm.timelineNext == 1) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-center align-center",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/sxlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/sxlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("顺序练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-center align-center",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/zjlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/zjlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("章节练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-center align-center",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/zxlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/zxlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("专项练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '4px',
      'padding-right': '4px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content"
  })])]), _vm._v(" "), _c('div', {
    staticClass: "box2Sty ui-row ui-row-border-bottom",
    style: ({
      'height': '50px'
    }),
    attrs: {
      "height": "50"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-10 valign-middle",
    style: ({
      'flex': '0 0 83.33333333333334%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [_vm._v("\n                            当前科目考试费\n                        ")])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [(_vm.payInfo['1'] == '已缴') ? _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#67c23a'
    }),
    attrs: {
      "text": "已缴费"
    }
  }) : _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#f00'
    }),
    attrs: {
      "text": "未缴费"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "padding": "8px"
    }
  }, [_c('div', {
    staticClass: "kcBoxSty ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTit box_row ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTitTit box_row_100 ui-view"
  }, [(_vm.examInfo['1']) ? _c('ui-text', [_vm._v(_vm._s(_vm.examInfo['1'].schoolName))]) : _c('ui-text', [_vm._v("未预约")])], 1), _vm._v(" "), (_vm.examInfo['1']) ? _c('div', {
    staticClass: "ui-view",
    on: {
      "tap": function($event) {
        _vm.showMap(_vm.examInfo['1'])
      }
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "location",
      "color": "#67c23a",
      "size": "26"
    }
  })], 1) : _vm._e()]), _vm._v(" "), (_vm.examInfo['1'].cj1 !== '') ? _c('div', {
    staticClass: "kcBoxBody box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 kcBoxBodyTiT ui-view"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                          第一次考试 \n                                      ")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                          " + _vm._s(_vm.examInfo['1'].ykSj) + "\n                                      ")])]), _vm._v(" "), _c('div', {
    staticClass: "fs ui-view"
  }, [_c('ui-circular', {
    attrs: {
      "radius": "35",
      "color": _vm.examInfo['1'].cj1 >= 90 ? '#1ec695' : '#f00',
      "value": "70",
      "line-width": "4",
      "line-cap": "round"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "fs_chil_box ui-view",
    style: ({
      color: _vm.examInfo['1'].cj1 >= 90 ? '#1ec695' : '#f00'
    })
  }, [_c('div', {
    staticClass: "fs_va ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['1'].cj1) + "分\n                                          ")]), _vm._v(" "), _c('div', {
    staticClass: "fs_typ ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['1'].cj1 >= 90 ? '合格' : '不合格') + "\n                                          ")])])], 1)]) : _vm._e(), _vm._v(" "), (_vm.examInfo['1'] && _vm.examInfo['1'].cj1 < 90 && _vm.examInfo['1'].cj2 !== '') ? _c('div', {
    staticClass: "kcBoxBody box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 kcBoxBodyTiT ui-view"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                          第二次考试 \n                                      ")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                          " + _vm._s(_vm.examInfo['1'].ykSj) + "\n                                      ")])]), _vm._v(" "), _c('div', {
    staticClass: "fs ui-view"
  }, [_c('ui-circular', {
    attrs: {
      "radius": "35",
      "color": _vm.examInfo['1'].cj1 >= 90 ? '#1ec695' : '#f00',
      "value": "70",
      "line-width": "4",
      "line-cap": "round"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "fs_chil_box ui-view",
    style: ({
      color: _vm.examInfo['1'].cj1 >= 90 ? '#1ec695' : '#f00'
    })
  }, [_c('div', {
    staticClass: "fs_va ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['1'].cj1) + "分\n                                          ")]), _vm._v(" "), _c('div', {
    staticClass: "fs_typ ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['1'].cj1 >= 90 ? '合格' : '不合格') + "\n                                          ")])])], 1)]) : _vm._e()])])]) : _vm._e(), _vm._v(" "), (_vm.timelineNext == 2) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_212')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/hgbz.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("合格标准")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_603/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/cftc.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("侧方停车")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_604/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/qxxs.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("曲线行驶")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_605/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "../static/img/jpg/zjzw.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("直角转弯")])], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({}),
    attrs: {
      "span": "6"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_602/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "../static/img/jpg/spqb.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("上坡起步")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km2_82_606/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "../static/img/jpg/dcrk.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("倒车入库")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content"
  })]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content"
  })])])]), _vm._v(" "), _c('div', {
    staticClass: "box2Sty ui-row ui-row-border-bottom",
    style: ({
      'height': '50px'
    }),
    attrs: {
      "height": "50"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-10 valign-middle",
    style: ({
      'flex': '0 0 83.33333333333334%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [_vm._v("\n                              当前科目考试费\n                          ")])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [(_vm.payInfo['2'] == '已缴') ? _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#67c23a'
    }),
    attrs: {
      "text": "已缴费"
    }
  }) : _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#f00'
    }),
    attrs: {
      "text": "未缴费"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "padding": "8px"
    }
  }, [_c('div', {
    staticClass: "kcBoxSty ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTit box_row ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTitTit box_row_100 ui-view"
  }, [(_vm.examInfo['2']) ? _c('ui-text', [_vm._v(_vm._s(_vm.examInfo['1'].schoolName))]) : _c('ui-text', [_vm._v("未预约")])], 1), _vm._v(" "), (_vm.examInfo['2']) ? _c('div', {
    staticClass: "ui-view",
    on: {
      "tap": function($event) {
        _vm.showMap(_vm.examInfo['2'])
      }
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "location",
      "color": "#67c23a",
      "size": "26"
    }
  })], 1) : _vm._e()]), _vm._v(" "), (_vm.examInfo['2']) ? _c('div', {
    staticClass: "kcBoxBody box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 kcBoxBodyTiT ui-view"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            第一次考试 \n                                        ")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['2'].ykSj) + "\n                                        ")])]), _vm._v(" "), _c('div', {
    staticClass: "fs ui-view"
  }, [_c('ui-circular', {
    attrs: {
      "radius": "35",
      "color": _vm.examInfo['2'].cj1 >= 90 ? '#1ec695' : '#f00',
      "value": "70",
      "line-width": "4",
      "line-cap": "round"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "fs_chil_box ui-view",
    style: ({
      color: _vm.examInfo['2'].cj1 >= 90 ? '#1ec695' : '#f00'
    })
  }, [_c('div', {
    staticClass: "fs_va ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['2'].cj1) + "分\n                                            ")]), _vm._v(" "), _c('div', {
    staticClass: "fs_typ ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['2'].cj1 >= 90 ? '合格' : '不合格') + "\n                                            ")])])], 1)]) : _vm._e()])])]) : _vm._e(), _vm._v(" "), (_vm.timelineNext == 3) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '5px',
      'padding-right': '5px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km3_607_608/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/hgbz.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("评判标准")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km3/yyzl/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/yyzl.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("语言指令")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km3/dgcz/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/dg.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("灯光操作")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/km3_607_627/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/kskj.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("考试口诀")])], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "box2Sty ui-row ui-row-border-bottom",
    style: ({
      'height': '50px'
    }),
    attrs: {
      "height": "50"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-10 valign-middle",
    style: ({
      'flex': '0 0 83.33333333333334%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [_vm._v("\n                              当前科目考试费\n                          ")])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [(_vm.payInfo['3'] == '已缴') ? _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#67c23a'
    }),
    attrs: {
      "text": "已缴费"
    }
  }) : _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#f00'
    }),
    attrs: {
      "text": "未缴费"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "padding": "8px"
    }
  }, [_c('div', {
    staticClass: "kcBoxSty ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTit box_row ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTitTit box_row_100 ui-view"
  }, [(_vm.examInfo['3']) ? _c('ui-text', [_vm._v(_vm._s(_vm.examInfo['3'].schoolName))]) : _c('ui-text', [_vm._v("未预约")])], 1), _vm._v(" "), (_vm.examInfo['3']) ? _c('div', {
    staticClass: "ui-view",
    on: {
      "tap": function($event) {
        _vm.showMap(_vm.examInfo['3'])
      }
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "location",
      "color": "#67c23a",
      "size": "26"
    }
  })], 1) : _vm._e()]), _vm._v(" "), (_vm.examInfo['3']) ? _c('div', {
    staticClass: "kcBoxBody box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 kcBoxBodyTiT ui-view"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            第一次考试 \n                                        ")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['3'].ykSj) + "\n                                        ")])]), _vm._v(" "), _c('div', {
    staticClass: "fs ui-view"
  }, [_c('ui-circular', {
    attrs: {
      "radius": "35",
      "color": _vm.examInfo['3'].cj1 >= 90 ? '#1ec695' : '#f00',
      "value": "70",
      "line-width": "4",
      "line-cap": "round"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "fs_chil_box ui-view",
    style: ({
      color: _vm.examInfo['4'].cj1 >= 90 ? '#1ec695' : '#f00'
    })
  }, [_c('div', {
    staticClass: "fs_va ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['3'].cj1) + "分\n                                            ")]), _vm._v(" "), _c('div', {
    staticClass: "fs_typ ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['3'].cj1 >= 90 ? '合格' : '不合格') + "\n                                            ")])])], 1)]) : _vm._e()])])]) : _vm._e(), _vm._v(" "), (_vm.timelineNext == 4) ? _c('div', {
    staticClass: "ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/sxlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/sxlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("顺序练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/zjlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/zjlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("章节练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center",
    style: ({
      'padding-left': '4px',
      'padding-right': '4px'
    }),
    on: {
      "tap": function($event) {
        _vm.gouTxt('http://m.jxedt.com/mnks/ckm1/zxlx/')
      }
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center"
  }, [_c('ui-image', {
    attrs: {
      "mode": "aspectFit",
      "src": "static/img/jpg/zxlx.jpg"
    }
  }), _vm._v(" "), _c('ui-text', [_vm._v("专项练习")])], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content"
  })])]), _vm._v(" "), _c('div', {
    staticClass: "box2Sty ui-row ui-row-border-bottom",
    style: ({
      'height': '50px'
    }),
    attrs: {
      "height": "50"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-10 valign-middle",
    style: ({
      'flex': '0 0 83.33333333333334%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [_vm._v("\n                              当前科目考试费\n                          ")])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle",
    staticStyle: {
      "height": "50px"
    }
  }, [(_vm.payInfo['4'] == '已缴') ? _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#67c23a'
    }),
    attrs: {
      "text": "已缴费"
    }
  }) : _c('ui-badge', {
    staticClass: "vip",
    style: ({
      backgroundColor: '#f00'
    }),
    attrs: {
      "text": "未缴费"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "padding": "8px"
    }
  }, [_c('div', {
    staticClass: "kcBoxSty ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTit box_row ui-view"
  }, [_c('div', {
    staticClass: "kcBoxTitTit box_row_100 ui-view"
  }, [(_vm.examInfo['4']) ? _c('ui-text', [_vm._v(_vm._s(_vm.examInfo['4'].schoolName))]) : _c('ui-text', [_vm._v("未预约")])], 1), _vm._v(" "), (_vm.examInfo['4']) ? _c('div', {
    staticClass: "ui-view",
    on: {
      "tap": function($event) {
        _vm.showMap(_vm.examInfo['4'])
      }
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "location",
      "color": "#67c23a",
      "size": "26"
    }
  })], 1) : _vm._e()]), _vm._v(" "), (_vm.examInfo['4']) ? _c('div', {
    staticClass: "kcBoxBody box_row ui-view"
  }, [_c('div', {
    staticClass: "box_row_100 kcBoxBodyTiT ui-view"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            第一次考试 \n                                        ")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("\n                                            " + _vm._s(_vm.examInfo['4'].ykSj) + "\n                                        ")])]), _vm._v(" "), _c('div', {
    staticClass: "fs ui-view"
  }, [_c('ui-circular', {
    attrs: {
      "radius": "35",
      "color": _vm.examInfo['4'].cj1 >= 90 ? '#1ec695' : '#f00',
      "value": "70",
      "line-width": "4",
      "line-cap": "round"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "fs_chil_box ui-view",
    style: ({
      color: _vm.examInfo['4'].cj1 >= 90 ? '#1ec695' : '#f00'
    })
  }, [_c('div', {
    staticClass: "fs_va ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['4'].cj1) + "分\n                                            ")]), _vm._v(" "), _c('div', {
    staticClass: "fs_typ ui-view"
  }, [_vm._v("\n                                              " + _vm._s(_vm.examInfo['4'].cj1 >= 90 ? '合格' : '不合格') + "\n                                            ")])])], 1)]) : _vm._e()])])]) : _vm._e()])])])])
},staticRenderFns: []}

/***/ }),

/***/ 227:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(228)
  __webpack_require__(229)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(230),
  /* template */
  __webpack_require__(231),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 228:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 229:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 230:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__ = __webpack_require__(85);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_check_list__ = __webpack_require__(118);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tab__ = __webpack_require__(121);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_tabs__ = __webpack_require__(122);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_popup__ = __webpack_require__(58);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_divider__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_text__ = __webpack_require__(40);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_image__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_scroll_view__ = __webpack_require__(55);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_input__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_view__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_col__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_row__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_touchui_dist_components_nav_bar__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_touchui_dist_components_page__ = __webpack_require__(39);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__static_ajax_mixin__ = __webpack_require__(51);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

















/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiPage: __WEBPACK_IMPORTED_MODULE_15_touchui_dist_components_page__["a" /* default */],
    UiNavBar: __WEBPACK_IMPORTED_MODULE_14_touchui_dist_components_nav_bar__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_13_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_12_touchui_dist_components_col__["a" /* default */],
    UiView: __WEBPACK_IMPORTED_MODULE_11_touchui_dist_components_view__["a" /* default */],
    UiInput: __WEBPACK_IMPORTED_MODULE_10_touchui_dist_components_input__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_icon__["a" /* default */],
    UiScrollView: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_scroll_view__["a" /* default */],
    UiImage: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_image__["a" /* default */],
    UiText: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_text__["a" /* default */],
    UiDivider: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_divider__["a" /* default */],
    UiPopup: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_popup__["a" /* default */],
    UiTabs: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_tabs__["a" /* default */],
    UiTab: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tab__["a" /* default */],
    UiCheckList: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_check_list__["a" /* default */]
  },

  config: {
    navigationStyle: 'custom',
    navigationBarBackgroundColor: "#26a2ff",
    navigationBarTitleText: "团队",
    navigationBarTextStyle: "white",
    title: '团队',
    backgroundColor: '#f2f2f2',
    delay: false //延迟加载
  },
  computed: {
    searchValue: function searchValue() {
      return ui.getApp().searchValue;
    }
  },
  watch: {
    searchValue: function searchValue(n, o) {
      this.searchVal = n;
      console.log(n);
      console.log(o);
      this.items = [];
      this.getPageList([n, '', '', '', 1]);
    }
  },
  mixins: [__WEBPACK_IMPORTED_MODULE_16__static_ajax_mixin__["a" /* default */]],
  data: function data() {
    var _ref;

    return _ref = {
      loadMore: true,

      popupshow: false,

      selectVal: ['0'],
      selectList: [{
        value: '全部',
        typ: 'all',
        key: '0'
      }, {
        value: '一级用户',
        typ: 'grade',
        tpval: 1,
        key: '1'
      }, {
        value: '二级用户',
        typ: 'grade',
        tpval: 2,
        key: '2'
      }, {
        value: '学员',
        typ: 'yhlx',
        tpval: 1,
        key: '3'
      }, {
        value: '会员',
        typ: 'yhlx',
        tpval: 3,
        key: '4'
      }, {
        value: '已交费',
        typ: 'sfjf',
        tpval: 1,
        key: '5'
      }, {
        value: '未交费',
        typ: 'sfjf',
        tpval: 0,
        key: '6'
      }],
      // Mask遮罩层END
      searchVal: '',
      items: [],
      pullDown: {
        distance: 50,
        onBegin: function onBegin() {},
        onActive: this.handleActive,
        onAfter: this.handlePullDownAfter
      },
      count: 0
    }, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_ref, 'loadMore', true), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_ref, 'pageNum', 1), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_ref, 'pages', 0), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_ref, 'listTyp', 0), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_ref, 'Objmess', {}), _ref;
  },

  filters: {
    typVal: function typVal(val) {
      switch (val) {
        case '1':
          return '一级用户';
          break;
        case '2':
          return '二级用户';
          break;
        case '3':
          return '学员';
          break;
        case '4':
          return '会员';
          break;
        case '5':
          return '已交费';
          break;
        case '6':
          return '未交费';
          break;
        case '0':
          return '全部';
          break;
        default:
          return '用户类型';
          break;
      }
    }
  },
  created: function created() {
    this.getPageList(['', '', '', '', 1]);
  },

  methods: {
    getPageList: function getPageList(Arr) {
      var v = this;
      this.$http('POST', ui.getApp().apis.TEAMMESS, {
        yhxm: Arr[0], grade: Arr[1], yhlx: Arr[2], sfjf: Arr[3], pageNum: Arr[4], pageSize: 10
      }, function (res) {
        if (res.code == 200) {
          v.pages = res.page.pages;
          v.pageNum = res.page.pageNum;
          if (res.page.list) {
            res.page.list.forEach(function (item, index) {
              v.items.push(item);
            });
          }
        }
      });
    },
    change: function change(val) {
      //状态选择
      // parseInt
      this.Objmess = this.selectList[parseInt(val[0])];
      this.items = [];
      this.getPageList(this.csMess(this.Objmess));
      this.popupshow = false;
    },
    csMess: function csMess(Obj) {
      //参数验证
      if (Obj.typ === 'all') {
        return ['', '', '', '', 1];
      } else if (Obj.typ === 'grade') {
        return ['', Obj.tpval, '', '', 1];
      } else if (Obj.typ === 'yhlx') {
        return ['', '', Obj.tpval, '', 1];
      } else if (Obj.typ === 'sfjf') {
        return ['', '', '', Obj.tpval, 1];
      }
    },
    handleInput: function handleInput() {
      //search收索
      ui.getApp().globalData.searchShow = true;
    },
    handleLoadMore: function handleLoadMore() {
      //到底加载
      console.log(this.pageNum);
      console.log(this.pages);
      ui.getApp().searchValue = '';
      if (this.pageNum < this.pages) {
        this.getPageList(this.csMess(this.Objmess));
      } else {
        this.loadMore = false;
        return false;
      }
    },
    handleActive: function handleActive() {//下拉刷新时的方法
    },

    // 下拉结束后，执行的函数。因为只要下拉即时没有到达刷新的高度也会触发这个方法
    // flag 为1表示执行了handleActive。为undefined代表handleActive没有执行。
    handlePullDownAfter: function handlePullDownAfter(flag) {
      this.pageNum = 1;
      this.items = [];
      this.getPageList(this.csMess(this.Objmess));
      ui.showToast({
        title: '处理下拉刷新'
      });
    },
    goStudentDetails: function goStudentDetails(item) {
      if (item.userDetail.yhLx != "1") {
        return;
      }
      this.$router.push({ name: 'process',
        params: {
          id: item.userDetail.id,
          number: parseInt(item.yhDqzt)
        }
      });
      console.log('学员信息', item);
    }
  },
  mounted: function mounted() {
    // for (let i = 1; i <= this.bottom; i++) {
    //   this.items.push(`列表项目${i}`)
    // }
  }
});

/***/ }),

/***/ 231:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('ui-page', {
    staticClass: "page-522152",
    staticStyle: {
      "overflow-y": "hidden",
      "position": "relative"
    }
  }, [_c('ui-nav-bar', {
    staticClass: "page-522152",
    attrs: {
      "custom-style": {
        'background': 'linear-gradient(to right, #4075FF 10% ,#29B6FD 80%, #32A8FE 100%)'
      }
    },
    slot: "nav-bar"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({
      'height': '46px'
    }),
    attrs: {
      "height": "46"
    }
  }, [_c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content",
    staticStyle: {
      "height": "46px"
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  }, [_c('div', {
    staticClass: "findInput ui-view",
    staticStyle: {
      "width": "100%"
    },
    on: {
      "tap": _vm.handleInput
    }
  }, [_c('ui-input', {
    attrs: {
      "placeholder": "收索"
    },
    on: {
      "input": function($event) {}
    },
    model: {
      value: (_vm.searchVal),
      callback: function($$v) {
        _vm.searchVal = $$v
      },
      expression: "searchVal"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content",
    staticStyle: {
      "height": "46px"
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-center align-center valign-middle",
    style: ({
      'flex': '0 0 70px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-center valign-middle",
    staticStyle: {
      "height": "46px"
    }
  }, [_c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "color": "#fff",
      "font-weight": "700"
    },
    on: {
      "tap": function($event) {
        _vm.popupshow = true
      }
    }
  }, [_vm._v("\n              " + _vm._s(_vm._f("typVal")(_vm.selectVal[0])) + "\n            ")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content",
    staticStyle: {
      "height": "46px"
    }
  })])])]), _vm._v(" "), (_vm.items.length == 0) ? _c('div', {
    staticClass: "listErr ui-view",
    on: {
      "tap": function($event) {
        _vm.selectVal = ['0']
      }
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "tuandui",
      "color": "#9e9e9e",
      "size": "50"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "mess ui-view"
  }, [_vm._v("\n          您的团队没有成员呦\n      ")])], 1) : _vm._e(), _vm._v(" "), _c('ui-scroll-view', {
    attrs: {
      "scroll-y": "",
      "height": _vm.pageHeight(-46),
      "pull-down": _vm.pullDown,
      "enable-reach-bottom-load": _vm.loadMore,
      "on-load-more": _vm.handleLoadMore
    }
  }, [_c('div', {
    staticClass: "content_list ui-view"
  }, _vm._l((_vm.items), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "ui-view",
      staticStyle: {
        "background": "#ffffff"
      },
      on: {
        "tap": function($event) {
          _vm.goStudentDetails(item)
        }
      }
    }, [_c('div', {
      staticClass: "ui-row",
      style: ({
        'margin-top': '10px',
        'height': '60px'
      }),
      attrs: {
        "height": "60"
      }
    }, [_c('div', {
      staticClass: "ui-col ui-col-3 ui-col-align-center align-center valign-middle",
      style: ({
        'flex': '0 0 25%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content align-center valign-middle",
      staticStyle: {
        "height": "60px"
      }
    }, [_c('ui-image', {
      attrs: {
        "src": _vm._f("yhTx")(item.userDetail.yhTx),
        "width": "50",
        "height": "50"
      }
    })], 1)]), _vm._v(" "), _c('div', {
      staticClass: "ui-col ui-col-9",
      style: ({
        'flex': '0 0 75%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content",
      staticStyle: {
        "height": "60px"
      }
    }, [_c('div', {
      staticClass: "ui-row",
      style: ({
        'margin-top': '10px',
        'height': '50px'
      }),
      attrs: {
        "height": "50"
      }
    }, [_c('div', {
      staticClass: "ui-col ui-col-6",
      style: ({
        'flex': '0 0 50%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content",
      staticStyle: {
        "height": "50px"
      }
    }, [_vm._v("\n                     " + _vm._s(_vm._f("yhXm")(item.yhXm)) + "\n                  ")])]), _vm._v(" "), _c('div', {
      staticClass: "ui-col ui-col-5 ui-col-align-right align-right",
      style: ({
        'flex': '0 0 41.66666666666667%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content align-right",
      staticStyle: {
        "height": "50px"
      }
    }, [_c('a', {
      attrs: {
        "href": 'tel:' + item.yhSjhm
      }
    }, [_vm._v(_vm._s(item.yhSjhm))])])])]), _vm._v(" "), _c('div', {
      staticClass: "ui-row",
      style: ({
        'height': '30px'
      }),
      attrs: {
        "height": "30"
      }
    }, [_c('div', {
      staticClass: "ui-col ui-col-4",
      style: ({
        'flex': '0 0 33.33333333333333%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content",
      staticStyle: {
        "height": "30px"
      }
    }, [_c('div', {
      staticClass: "ui-view",
      style: ({
        color: item.userDetail.ddSfjx == '1' ? '#00b65f' : '#ff8800'
      })
    }, [_vm._v("\n                      " + _vm._s(_vm._f("ddSfjx")(item.userDetail.ddSfjx)) + "\n                    ")])])]), _vm._v(" "), _c('div', {
      staticClass: "ui-col ui-col-4 ui-col-align-left align-left",
      style: ({
        'flex': '0 0 33.33333333333333%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content align-left",
      staticStyle: {
        "height": "30px"
      }
    }, [_c('div', {
      staticClass: "ui-view",
      style: ({
        color: item.userDetail.yhLx == '3' ? '#ff000a' : '#2d8cf0'
      })
    }, [_c('ui-text', {
      directives: [{
        name: "show",
        rawName: "v-show",
        value: (item.userDetail.ddSfjx == '1'),
        expression: "item.userDetail.ddSfjx=='1'"
      }]
    }, [_vm._v("\n                            " + _vm._s(_vm._f("yhLx")(item.userDetail.yhLx)) + "\n                          ")])], 1)])]), _vm._v(" "), _c('div', {
      staticClass: "ui-col ui-col-3 ui-col-align-right align-right",
      style: ({
        'flex': '0 0 25%'
      })
    }, [_c('div', {
      staticClass: "ui-col-content align-right",
      staticStyle: {
        "height": "30px"
      }
    }, [_vm._v("\n                      " + _vm._s(_vm._f("userGrade")(item.userGrade)) + "\n                  ")])])])])])]), _vm._v(" "), (item.userDetail.yhLx == '1' && item.userDetail.ddSfjx == '1') ? _c('div', {
      staticClass: "ui-view",
      staticStyle: {
        "font-size": "0.14rem",
        "padding": "0.02rem 0.15rem",
        "border-top": "slategray 1px solid"
      }
    }, [_vm._v("\n          学员当前进度 >>> " + _vm._s(_vm._f("yhslZt")(item.yhslZt)) + "\n        ")]) : _vm._e()])
  })), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    staticStyle: {
      "width": "200px",
      "margin": "0 auto"
    }
  }, [_c('ui-divider', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (!_vm.loadMore),
      expression: "!loadMore"
    }]
  }, [_vm._v("没有新数据了")])], 1)]), _vm._v(" "), _c('ui-popup', {
    staticClass: "page-522152",
    attrs: {
      "position": "top"
    },
    model: {
      value: (_vm.popupshow),
      callback: function($$v) {
        _vm.popupshow = $$v
      },
      expression: "popupshow"
    }
  }, [_c('div', {
    staticClass: "top_pop ui-view"
  }, [_c('div', {
    staticClass: "checkTit ui-view"
  }, [_c('ui-tabs', {
    attrs: {
      "index": 0
    }
  }, [_c('ui-tab', [_vm._v("区域选着")])], 1)], 1), _vm._v(" "), _c('ui-scroll-view', {
    attrs: {
      "scroll-y": "scroll-y"
    }
  }, [_c('ui-check-list', {
    attrs: {
      "max": "1",
      "label-position": "left",
      "options": _vm.selectList,
      "type": "plain",
      "color": "#3AC3B0"
    },
    on: {
      "change": _vm.change
    },
    model: {
      value: (_vm.selectVal),
      callback: function($$v) {
        _vm.selectVal = $$v
      },
      expression: "selectVal"
    }
  })], 1)], 1)])], 1)
},staticRenderFns: []}

/***/ }),

/***/ 232:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(233)
  __webpack_require__(234)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(235),
  /* template */
  __webpack_require__(237),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 233:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 234:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 235:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_mask__ = __webpack_require__(30);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_button__ = __webpack_require__(123);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_icon__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_text__ = __webpack_require__(40);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_image__ = __webpack_require__(28);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_view__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_col__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_row__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_nav_bar__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_page__ = __webpack_require__(39);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__static_ajax_mixin__ = __webpack_require__(51);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//












/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiPage: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_page__["a" /* default */],
    UiNavBar: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_nav_bar__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_col__["a" /* default */],
    UiView: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_view__["a" /* default */],
    UiImage: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_image__["a" /* default */],
    UiText: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_text__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_icon__["a" /* default */],
    UiButton: __WEBPACK_IMPORTED_MODULE_1_touchui_dist_components_button__["a" /* default */],
    UiMask: __WEBPACK_IMPORTED_MODULE_0_touchui_dist_components_mask__["a" /* default */]
  },

  config: {
    navigationStyle: 'custom',
    navigationBarBackgroundColor: "#3399FF",
    navigationBarTitleText: "我的",
    navigationBarTextStyle: "white",
    title: '我的',
    backgroundColor: '#3399FF',
    delay: false //延迟加载
  },
  mixins: [__WEBPACK_IMPORTED_MODULE_10__static_ajax_mixin__["a" /* default */]],
  data: function data() {
    return {
      showMask: false,
      portraitUrl: __webpack_require__(236),
      pullDown: {
        distance: 100,
        onActive: this.handleLoadMore
      },
      usermess: '',
      zhYE: {
        yhZhye: 0
      }
    };
  },

  watch: {},
  methods: {
    handleLoadMore: function handleLoadMore() {
      this.getUserMess();
      this.getYE(function (res) {
        ui.showToast({ title: '用户信息更新成功', icon: 'success' });
      });
    },
    sys: function sys() {
      ui.scanCode({
        backgroundColor: '#39f',
        color: '#4caf5',
        onlyFromCamera: true,
        success: function success(res) {
          console.log(res);
          ui.showAlert({
            content: res,
            buttonText: '扫一扫',
            success: function success() {
              ui.showToast({
                title: '触发了解更多'
              });
            }
          });
        }
      });
    },
    paymoney: function paymoney() {
      ui.request({
        url: 'http://192.168.31.92:8080/pub/appPay', //仅为示例，并非真实的接口地址
        data: {},
        method: 'POST',
        header: {
          'content-type': 'application/json'
          // 'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function success(res) {
          console.log('数据请求', res.data);
        }
      });
    },
    WxPay: function WxPay() {
      ui.requestPayment({
        target: 'wxpay',
        statement: {
          "appId": 'wxb01394ea85904296', //公众号名称，由商户传入
          "timeStamp": v.payMess.timeStamp, //时间戳，自1970年以来的秒数
          "nonceStr": v.payMess.nonceStr, //随机串
          "package": v.payMess.package, //
          "signType": v.payMess.signType, //微信签名方式：
          "paySign": v.payMess.paySign //微信签名
        },
        success: function success() {
          console.log('支付成功');
        },
        fail: function fail() {
          console.log('支付失败');
        }
      });
    },
    goMyMess: function goMyMess() {
      ui.navigateTo({
        url: 'center/info'
      });
    },
    qrcode: function qrcode() {
      ui.getApp().QRpage = '/pages/user';
      ui.navigateTo({
        url: '/pages/common/qrCode'
      });
    },
    goMyTeam: function goMyTeam() {
      ui.navigateTo({
        url: 'center/myTeam2'
      });
    },
    goBill: function goBill() {
      ui.navigateTo({
        url: 'center/bill2'
      });
    },
    goReallyname: function goReallyname() {
      ui.navigateTo({
        url: 'center/reallyName'
      });
    },
    goProductList: function goProductList() {
      ui.navigateTo({
        url: '/pages/product/index'
      });
    },
    tx: function tx() {
      ui.navigateTo({
        url: 'tx/index'
      });
    },
    getYE: function getYE(callback) {
      //获取账户余额
      var v = this;
      this.$http('POST', ui.getApp().apis.USERZH, {}, function (res) {
        if (res.code == 200) {
          v.zhYE.yhZhye = res.result.yhZhye;
          callback && callback(true);
        } else {
          ui.showToast({ title: res.message });
        }
      });
    }
  },
  mounted: function mounted() {},
  created: function created() {
    var _this = this;

    this.getYE();
    this.getUserMess(function (res) {
      _this.usermess = res;
    });
  }
});

/***/ }),

/***/ 236:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "static/img/portrait_bg.d8ab821.png";

/***/ }),

/***/ 237:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('ui-page', {
    staticClass: "page-3fa662",
    staticStyle: {
      "overflow-y": "hidden"
    },
    attrs: {
      "pull-down": _vm.pullDown
    }
  }, [_c('div', {
    staticClass: "box_col ui-view",
    style: ({
      height: _vm.pageHeight(0) + 'px',
      'background-color': '#f2f2f2'
    })
  }, [_c('div', {
    staticClass: "userTop ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-2 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 16.666666666666664%'
    }),
    on: {
      "tap": _vm.goMyMess
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "pageHello ui-view"
  }, [_c('ui-image', {
    staticStyle: {
      "border-radius": "30px"
    },
    attrs: {
      "src": _vm.usermess.yhTx,
      "width": "60",
      "height": "60"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-7 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 58.333333333333336%',
      'padding-left': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "pageHello ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({
      'height': '26px'
    }),
    attrs: {
      "height": "26"
    }
  }, [_c('div', {
    staticClass: "ui-col ui-col-4",
    style: ({
      'flex': '0 0 33.33333333333333%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content",
    staticStyle: {
      "height": "26px"
    }
  }, [_vm._v("\n                        " + _vm._s(_vm._f("yhXm")(_vm.usermess.yhXm)) + "\n                    ")])]), _vm._v(" "), _c('div', {
    staticClass: "userTyp ui-col ui-col-8 valign-bottom",
    style: ({
      'flex': '0 0 66.66666666666666%'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-bottom",
    staticStyle: {
      "height": "26px"
    }
  }, [_vm._v("\n                          " + _vm._s(_vm._f("yhLx")(_vm.usermess.yhLx)) + "  \n                        ")])])])]), _vm._v(" "), _c('div', {
    staticClass: "pageHello ui-view"
  }, [_vm._v(_vm._s(_vm.usermess.yhZh))])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-right align-right valign-middle",
    style: ({
      'flex': '0 0 25%',
      'padding-right': '20px'
    }),
    on: {
      "tap": _vm.qrcode
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "erweima",
      "size": "36",
      "color": "#fff"
    }
  })], 1)])])]), _vm._v(" "), _c('div', {
    staticClass: "userItem marB25 ui-view"
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v(_vm._s(_vm._f("yhZhye")(_vm.zhYE.yhZhye / 100)) + "元")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("账户余额")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_vm._v(_vm._s(_vm._f("yhZhye")(_vm.zhYE.yhZhye / 100)) + "元")]), _vm._v(" "), _c('div', {
    staticClass: "ui-view"
  }, [_vm._v("邀请奖励")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '40px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_c('ui-button', {
    attrs: {
      "type": "primary",
      "size": "default"
    },
    nativeOn: {
      "tap": function($event) {
        _vm.goProductList($event)
      }
    }
  }, [_vm._v("缴费")])], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-3 ui-col-align-left align-left valign-middle",
    style: ({
      'flex': '0 0 25%',
      'padding-left': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ui-view"
  }, [_c('ui-button', {
    attrs: {
      "type": "primary",
      "size": "default"
    },
    nativeOn: {
      "tap": function($event) {
        _vm.tx($event)
      }
    }
  }, [_vm._v("提现")])], 1)])])])]), _vm._v(" "), _c('div', {
    staticClass: "userItem marB5 ui-view",
    on: {
      "tap": _vm.goBill
    }
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col valign-middle",
    style: ({
      'flex': '0 0 20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "wenjian1",
      "size": "15",
      "color": "#3399FF"
    }
  })], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-6 ui-col-align-left align-left valign-middle",
    staticStyle: {
      "padding": "5px 0"
    },
    style: ({
      'flex': '0 0 50%',
      'padding-left': '10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ftWt ui-view"
  }, [_vm._v("\n                        我的账单\n                  ")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "right",
      "size": "20",
      "color": "#3399FF"
    }
  })], 1)])])]), _vm._v(" "), _c('div', {
    staticClass: "userItem marB5 ui-view",
    on: {
      "tap": _vm.goMyTeam
    }
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col valign-middle",
    style: ({
      'flex': '0 0 20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "tuandui",
      "size": "18",
      "color": "#3399FF"
    }
  })], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-6 ui-col-align-left align-left valign-middle",
    staticStyle: {
      "padding": "5px 0"
    },
    style: ({
      'flex': '0 0 50%',
      'padding-left': '10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ftWt ui-view"
  }, [_vm._v("\n                    我的团队\n                  ")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "right",
      "size": "20",
      "color": "#3399FF"
    }
  })], 1)])])]), _vm._v(" "), _c('div', {
    staticClass: "userItem marB5 ui-view",
    on: {
      "tap": _vm.goReallyname
    }
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col valign-middle",
    style: ({
      'flex': '0 0 20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "anquan",
      "size": "18",
      "color": "#3399FF"
    }
  })], 1)]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-6 ui-col-align-left align-left valign-middle",
    staticStyle: {
      "padding": "5px 0"
    },
    style: ({
      'flex': '0 0 50%',
      'padding-left': '10px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-left valign-middle"
  }, [_c('div', {
    staticClass: "ftWt ui-view"
  }, [_vm._v("\n                    实名认证\n                ")])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle"
  }, [_vm._v("\n              " + _vm._s(_vm._f("yhZt")(_vm.usermess.yhZt)) + "\n            ")])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col ui-col-align-right align-right valign-middle",
    style: ({
      'padding-right': '10px',
      'flex': '0 0 20px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content align-right valign-middle"
  }, [_c('ui-icon', {
    attrs: {
      "type": "right",
      "size": "20",
      "color": "#3399FF"
    }
  })], 1)])])]), _vm._v(" "), _c('ui-button', {
    attrs: {
      "type": "primary",
      "full-width": false
    },
    nativeOn: {
      "tap": function($event) {
        _vm.paymoney($event)
      }
    }
  }, [_vm._v("支付")]), _vm._v(" "), _c('ui-button', {
    attrs: {
      "type": "primary",
      "full-width": false
    },
    nativeOn: {
      "tap": function($event) {
        _vm.sys($event)
      }
    }
  }, [_vm._v("扫一扫 ")]), _vm._v(" "), (_vm.showMask) ? _c('ui-mask', {
    staticClass: "page-3fa662",
    attrs: {
      "effect": "scale-in",
      "hide-on-tap": true,
      "hide-on-touch": true
    },
    nativeOn: {
      "tap": function($event) {
        _vm.showMask = false
      }
    },
    model: {
      value: (_vm.showMask),
      callback: function($$v) {
        _vm.showMask = $$v
      },
      expression: "showMask"
    }
  }, [_c('ui-image', {
    staticStyle: {
      "position": "absolute",
      "top": "45%",
      "left": "50%",
      "transform": "translate(-50%,-50%)"
    },
    attrs: {
      "src": "http://www.520xclm.com:8001//QRCode/20180625/460765622230319888.png",
      "width": "300"
    }
  })], 1) : _vm._e()], 1)])
},staticRenderFns: []}

/***/ }),

/***/ 238:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "ui-tab-page-container page-b92ac4"
  }, [_c('Page0', {
    staticClass: "ui-tab-page",
    class: {
      visible: _vm.tabIndex === 0
    }
  }), _vm._v(" "), _c('Page1', {
    staticClass: "ui-tab-page",
    class: {
      visible: _vm.tabIndex === 1
    }
  }), _vm._v(" "), _c('Page2', {
    staticClass: "ui-tab-page",
    class: {
      visible: _vm.tabIndex === 2
    }
  }), _vm._v(" "), _c('Page3', {
    staticClass: "ui-tab-page",
    class: {
      visible: _vm.tabIndex === 3
    }
  })], 1)
},staticRenderFns: []}

/***/ }),

/***/ 242:
/***/ (function(module, exports) {

module.exports = "data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PScwIDAgMTIwIDEyMCcgeG1sbnM9J2h0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnJyB4bWxuczp4bGluaz0naHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayc+PGRlZnM+PGxpbmUgaWQ9J2wnIHgxPSc2MCcgeDI9JzYwJyB5MT0nNycgeTI9JzI3JyBzdHJva2U9JyNkOGQ4ZDgnIHN0cm9rZS13aWR0aD0nMTEnIHN0cm9rZS1saW5lY2FwPSdyb3VuZCcvPjwvZGVmcz48Zz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDMwIDYwLDYwKScvPjx1c2UgeGxpbms6aHJlZj0nI2wnIG9wYWNpdHk9Jy4yNycgdHJhbnNmb3JtPSdyb3RhdGUoNjAgNjAsNjApJy8+PHVzZSB4bGluazpocmVmPScjbCcgb3BhY2l0eT0nLjI3JyB0cmFuc2Zvcm09J3JvdGF0ZSg5MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDEyMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDE1MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMzcnIHRyYW5zZm9ybT0ncm90YXRlKDE4MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNDYnIHRyYW5zZm9ybT0ncm90YXRlKDIxMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNTYnIHRyYW5zZm9ybT0ncm90YXRlKDI0MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNjYnIHRyYW5zZm9ybT0ncm90YXRlKDI3MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNzUnIHRyYW5zZm9ybT0ncm90YXRlKDMwMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuODUnIHRyYW5zZm9ybT0ncm90YXRlKDMzMCA2MCw2MCknLz48L2c+PC9zdmc+DQo="

/***/ }),

/***/ 243:
/***/ (function(module, exports) {

module.exports = "data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PScwIDAgMTIwIDEyMCcgeG1sbnM9J2h0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnJyB4bWxuczp4bGluaz0naHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayc+PGRlZnM+PGxpbmUgaWQ9J2wnIHgxPSc2MCcgeDI9JzYwJyB5MT0nNycgeTI9JzI3JyBzdHJva2U9JyM2YzZjNmMnIHN0cm9rZS13aWR0aD0nMTEnIHN0cm9rZS1saW5lY2FwPSdyb3VuZCcvPjwvZGVmcz48Zz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDMwIDYwLDYwKScvPjx1c2UgeGxpbms6aHJlZj0nI2wnIG9wYWNpdHk9Jy4yNycgdHJhbnNmb3JtPSdyb3RhdGUoNjAgNjAsNjApJy8+PHVzZSB4bGluazpocmVmPScjbCcgb3BhY2l0eT0nLjI3JyB0cmFuc2Zvcm09J3JvdGF0ZSg5MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDEyMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMjcnIHRyYW5zZm9ybT0ncm90YXRlKDE1MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuMzcnIHRyYW5zZm9ybT0ncm90YXRlKDE4MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNDYnIHRyYW5zZm9ybT0ncm90YXRlKDIxMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNTYnIHRyYW5zZm9ybT0ncm90YXRlKDI0MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNjYnIHRyYW5zZm9ybT0ncm90YXRlKDI3MCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuNzUnIHRyYW5zZm9ybT0ncm90YXRlKDMwMCA2MCw2MCknLz48dXNlIHhsaW5rOmhyZWY9JyNsJyBvcGFjaXR5PScuODUnIHRyYW5zZm9ybT0ncm90YXRlKDMzMCA2MCw2MCknLz48L2c+PC9zdmc+DQo="

/***/ }),

/***/ 50:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(204)
  __webpack_require__(205)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(206),
  /* template */
  __webpack_require__(238),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 51:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony default export */ __webpack_exports__["a"] = ({
  filters: {
    yhLx: (val) => {//用戶类型
      switch (val) {
        case '1':
          return '学员';
          break;
        case '2':
          return '专员';
          break;
        case '3':
          return '会员';
          break;
        default:
          return '用户类型'
          break;
      }
    },

    yhXm: (val) => {//用户姓名
      if (val) {
        return val
      }
      return '未实名'
    },

    yhTx: (val) => {
      if (val) {
        return val
      }
      return 'static/img/login/LOGO.png'
    },
    yhSfyjz(val){
      switch (val) {
        case '0':
          return '无驾驶证';
          break;
        case '1':
          return '有驾驶证';
          break;
        default:
          return "无驾驶证";
          break;
      }
    },
    userGrade: (val) => {
      switch (val) {
        case '1':
          return '一级用户'
          break;
        case '2':
          return '二级用户'
      }
    },

    yhZhye(val) {//用户账户余额
      if (val == '') {
        return 0
      }
      return val
    },

    ddSfjx: (val) => {
      switch (val) {
        case '0':
          return '未交费'
          break;
        case '1':
          return '已交费'
      }
    },

    yhZt: function (val) {//用户状态
      switch (val) {
        case '0':
          return '审核中';
          break;
        case '1':
          return '已认证';
          break;
        case '2':
          return '审核驳回';
          break;
        case '-1':
          return '未认证';
          break;
        default:
          return val
          break;
      }
    },
    yhslZt(val) {
      switch (val) {
        case "00":
          return '档案未受理'
          break;
        case "01":
          return '档案受理成功'
          break;
        case "02":
          return '档案受理中'
          break;
        case "10":
          return '科目一已约考'
          break;
        case "11":
          return '科目一已通过'
          break;
        case  "12":
          return '科目一未通过'
          break;
        case  "20":
          return '科目二已约考'
          break;
        case  "21":
          return '科目二已通过'
          break;
        case  "22":
          return '科目二未通过'
          break;
        case  "30":
          return '科目三已约考'
          break;
        case  "31":
          return '科目三已通过'
          break;
        case  "32":
          return '科目三未通过'
          break;
        case  "40":
          return '科目四已约考'
          break;
        case  "41":
          return '科目四已通过'
          break;
        case  "42":
          return '科目四未通过'
          break;
        default:
          return '完结'
      }
    },

    userInviteCount(val) {
      if (val) {
        return val
      }
      return 0
    },

    jlQu:(val)=>{
      switch (val) {
        case "430014":
          return '江岸区'
          break;
        case "4300001":
          return '江汉区'
          break;
        case "4300002":
          return '硚口区'
          break;
        case "430050":
          return '汉阳区'
          break;
        case "4300003":
          return '武昌区'
          break;
        case "430080":
          return '武昌区'
          break;
        case "430080":
          return '青山区'
          break;
        case "430070":
          return '洪山区'
          break;
        case "430040":
          return '东西湖区'
          break;
        case "430090":
          return '汉南区'
          break;
        case "430100":
          return '蔡甸区'
          break;
        case "430200":
          return '蔡甸区'
          break;
        case "432200":
          return '黄陂区'
          break;
        case "431400":
          return '新洲区'
          break;
        default:
          return '***'
          break;
      }
    },
    getType:(val)=>{
      switch (val){
        case 0:
          return '受理专员'
        break;
        case 1:
          return '科目一专员'
          break;
        case 2:
          return '科目二专员'
        break;
        case 3:
          return '科目三专员'
        break;
        case 4:
          return '科目四专员'
        break;
      }
    },
    yhXmZY:(val)=>{
      if (val){
        return val
      }
      return '暂未分配专员'
    },
    jlPf:(val)=>{
      if (val){
        return parseInt(val)
      }
      return 0
    },
    zjZt:(val)=>{
      switch (val){
        case '0':
          return '提现冻结'
          break;
        case '1':
          return '处理成功'
          break;
        case '2':
          return '提现失败'
          break;
      }
    },
    txShZt:(val)=>{
      switch (val){
        case '0':
          return '待审核'
          break;
        case '1':
          return '待打款'
          break;
        case '2':
          return '审核失败'
          break;
      }
    },
    mxlx:(val)=>{
      switch (val){
        case '1':
          return '充值'
        break;
        case '2':
          return '分佣'
        break;
        case '3':
            return '消费'
        break;
        case '4':
          return '提现'
        break;
      }
    },
    zjFs:(val)=>{
      switch (val){
        case '1':
          return ''
          break;
        case '-1':
          return '-'
          break;
      }
    }

  },
  created() {
  },
  mounted() {
  }

});


/***/ }),

/***/ 86:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(222)
  __webpack_require__(223)
}
var Component = __webpack_require__(16)(
  /* script */
  __webpack_require__(224),
  /* template */
  __webpack_require__(225),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ })

},[128]);