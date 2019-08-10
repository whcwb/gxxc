import Vue from 'vue'
import App from './App'

import store from './store'
Vue.prototype.$store = store

import http from './ajax/index.js'
Vue.prototype.$http = http

import apis from './ajax/apis.js'
Vue.prototype.apis = apis;

import Met from './libs/CommonMethod.js'
Vue.prototype.Met = Met;

import VueClipboard from 'vue-clipboard2'

Vue.use(VueClipboard);
import wxApi from  './libs/wechatUtil'
Vue.prototype.wxApi = wxApi;


Vue.config.productionTip = false


App.mpType = 'app'



const app = new Vue({
    store,
    ...App
})
app.$mount()
