// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import * as FastClick from "fastclick"
import App from './App'
import router from './router'
import store from './store';
import './styles/global.css'
import './styles/box.less'
import 'iview/dist/styles/iview.css'//iview 框架 样式

FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router:router,
  store: store,
  components: { App },
  template: '<App/>'
})
//*****------Cookies------*****
//import Cookies from 'js-cookie';---引入
//cookies 存储-------Cookies.set('name',val)
//cookies 获取-------Cookies.get('name')
