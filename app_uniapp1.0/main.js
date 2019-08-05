import Vue from 'vue'
import App from './App'

import store from './store'
Vue.prototype.$store = store

import http from './ajax/index.js'
Vue.prototype.$http = http

import apis from './ajax/apis.js'
Vue.prototype.apis = apis;

import wxApi from  './libs/wechatUtil'
Vue.prototype.wxApi = wxApi;

// import iView from 'iview';
// import 'iview/dist/styles/iview.css';
// import 'common/iview.css'
// Vue.use(iView);

// import { Button } from 'iview';
// Vue.component('Button', Button);


Vue.config.productionTip = false


App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()
