// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import * as FastClick from "fastclick"
import App from './App'
import router from './router'
import store from './store';
import './styles/box.less'

import Mint from 'mint-ui';
Vue.use(Mint);
import 'mint-ui/lib/style.css'

import ElementUI from 'element-ui';
Vue.use(ElementUI);
import 'element-ui/lib/theme-chalk/index.css';

import wechatUtil from './libs/wechatUtil'
import Apis from './libs/apis';
import url from './libs/url';

FastClick.attach(document.body)

Vue.config.productionTip = false
Vue.prototype.wechatUtil = wechatUtil;
Vue.prototype.apis = url;
Vue.prototype.$http = Apis.ajax;
import MyFunction from './util/auto'
Vue.prototype.util = MyFunction

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router:router,
  store: store,
  components: { App },
  template: '<App/>',
})
