import Vue from 'vue'
import Router from 'vue-router'
import routers from './routers'
import Util from '../libs/apis';

Vue.use(Router)

const router = new Router({
  routes: routers
})
router.beforeEach((to, from, next) => {
  Util.title(to.meta.title);
  next()
})
export default router
