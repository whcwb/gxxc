import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/home'
import Util from '../libs/apis';
import {Toast } from 'mint-ui';
import wx from '../libs/wechatUtil'
Vue.use(Router)
// 路由配置
const router = new Router({
  routes: [
    {
      path: '/index',
      name: 'index',
      meta:{
        title:'学车联盟'
      },
      component: resolve => { require(['@/views/index.vue'], resolve); }
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta:{
        title:'学车联盟'
      },
      children:[
      ]
    },
    {
      path: '/',
      name: 'Login',
      meta:{
        title:'登录'
      },
      component: resolve => { require(['@/views/login.vue'], resolve); }
    },
    {
      path: '/reg',
      name: 'Reg',
      meta:{
        title:'注册'
      },
      component: resolve => { require(['@/views/reg.vue'], resolve); }
    },
    {
      path: '/myCenter-info',
      name: 'myCenterInfo',
      meta:{
        title:'个人信息'
      },
      component: resolve => { require(['@/views/myCenter/info.vue'], resolve); }
    },
    {
      path: '/myCenter-qrcode',
      name: 'myCenterQrcode',
      meta:{
        title:'我的二维码'
      },
      component: resolve => { require(['@/views/myCenter/qrcode.vue'], resolve); }
    },
    {
      path: '/myCenter-sfrz',
      name: 'myCenterSfrz',
      meta:{
        title:'实名认证'
      },
      component: resolve => { require(['@/views/myCenter/renzhen/sfrz.vue'], resolve); }
    },
    {
      path: '/myCenter-jlyrz',
      name: 'myCenterJlyrz',
      meta:{
        title:'教练员认证'
      },
      component: resolve => { require(['@/views/myCenter/renzhen/jlyrz.vue'], resolve); }
    },
    {
      path: '/jxlist',
      name: 'jxlist',
      meta:{
        title:'训练场'
      },
      component:()=>import('@/views/jxlist')
    },
    {
      path: '/jxMess',
      name: 'jxMess',
      meta:{
        title:'训练场详情'
      },
      component:()=>import('@/views/jxMess')
    },
    {
      path: '/kcfb',
      name: 'kcfb',
      meta:{
        title:'考场分布'
      },
      component:()=>import('@/views/kcfb')
    },
    {
      path: '/mycenter',
      name: 'mycenter',
      meta:{
        title:'个人中心'
      },
      component:()=>import('@/views/myCenter')
    },
    {
      path: '/pay/payIndex',
      name: 'pay',
      meta:{
        title:'缴费'
      },
      component:()=>import('@/views/pay')
    },
    {
      path: '/bill',
      name: 'bill',
      component:()=>import('@/views/bill')
    },{
      path: '/tx',
      name: 'tx',
      meta:{
        title:'提现'
      },
      component:()=>import('@/views/TX')
    },{
      path:'/myTeam',name:'myteam',
      meta:{title:'我的团队'},
      component:()=>import('@/views/myTeam')
    },{
      path:'/myStudent',name:'myStudent',
      meta:{title:'我的学员'},
      component:()=>import('@/views/student')
    },{
      path:'/stuMess',name:'stuMess',
      meta:{title:'学员信息'},
      component:()=>import('@/views/stuMess')
    }
  ]
});


router.beforeEach((to, from, next) => {
    Util.title(to.meta.title);
    let openid = localStorage.getItem("openid");
    // console.log(openid);
  // 如果没有openid，则需要获取
    if (openid == null && to.path != '/index') {
      next({
        name: 'index'
      });
      return;
    }else if (openid == null && to.path == '/index') {
      next();
    }else if (openid != null){
      if(to.name=='Login'||to.name=='Reg'){
        next()
      }else if(to.name!='Login' && localStorage.getItem('userMess')){
        next()
      }else{
        Toast('用户信息丢失，请重新登录！')
        next({
          name: 'Login'
        });
      }

      if(to.name == 'pay'){
        auto(window, document,11.5)
      }else {
        auto(window, document,7.5)
      }

      next();
    }
})

router.afterEach((to) => {
  //Util.openNewPage(router.app, to.name, to.params, to.query);
  window.scrollTo(0, 0);
});
export default router;
function auto (window, document , num) {

  function resize(){
    var ww = window.innerWidth;
    if (ww > window.screen.width) {
      window.requestAnimationFrame(resize);
    }
    else{
      if (ww > 720) {
        ww = 720
      }
      // set 1rem based on iPhone6 750px width, 750/7.5=100
      // document.documentElement.style.fontSize = ww / 7.5 + 'px';
      document.documentElement.style.fontSize = ww / num + 'px';

      document.body.style.opacity = 1;
    }
  }
  if (document.readyState !== 'loading') {
    resize();
  }
  else {
    document.addEventListener('DOMContentLoaded', resize);
  }
  window.addEventListener('resize', resize);

}
