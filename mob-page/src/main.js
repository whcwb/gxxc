// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import * as FastClick from "fastclick"
import App from './App'
import router from './router'
import store from './store';
import './styles/box.less'
import Apis from './libs/apis';
import 'iview/dist/styles/iview.css';
import boxHead from '@/views/components/boxHead'


// router.beforeEach((to, from, next) => {
//   console.log('***************',to)
//   if(to.name == 'pay'){
//     import responsiveto from '@/js/responsiveto.js'
//   }else {
//     import responsive from '@/js/responsive.js'
//   }
//   next();
// })


Vue.component(boxHead.name, boxHead)
FastClick.attach(document.body)

Vue.config.productionTip = false
Vue.prototype.apis = Apis;
Vue.prototype.$http = Apis.ajax;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router:router,
  store: store,
  components: { App },
  template: '<App/>'
})
