import Vue from 'vue'
import Router from 'vue-router'
import routers from './routers'
import Util from '../libs/apis';
import {Toast } from 'mint-ui';
Vue.use(Router)

const router = new Router({
  routes: routers
})
router.beforeEach((to, from, next) => {
  console.log('去',to)
  // console.log('来',from)
  Util.title(to.meta.title);
  // debugger
  let openid = localStorage.getItem("openid");
  // let openid = "123123";
  let ISLOGIN = sessionStorage.getItem("ISLOGIN");

  let userToken = localStorage.getItem("userMess");
  /*if(openid!=null && userToken){
    ISLOGIN=true;
  }*/

  //   console.log(openicd);
  //
  // 如果没有openid，则需要获取
  if ((ISLOGIN == null || openid == null) &&  to.path != '/index') {
    next({
      name: 'index'
    });
    return;
  }else if ((ISLOGIN == null || openid == null) && to.path == '/index') {
    next();
  }else if (ISLOGIN != null && openid != null){
    if(to.name=='Login'||to.name=='reg'||to.name=='forget'||to.name =='index'){
      next()
    }else if(to.name!='Login' && localStorage.getItem('userMess')){
      next()
    }else if(to.name=='indexName' && !localStorage.getItem('userMess')){
      next({
        name: 'Login'
      });
      return;
    }else{
      Toast('用户信息丢失，请重新登录！')
      next({
        name: 'Login'
      });
    }
  }
})



export default router
