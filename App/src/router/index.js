import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const router = new Router({
  routes: [
      {
        path: '/',
        name: 'home',
        component: ()=>import('@/views/home')
      },{
        path:'/login',
        name:'login',
        component:()=>import('@/views/login')
      },{
        path:'/register',
        name:'register',
        component:()=>import('@/views/register')
      },{//个人
        path:'/myCenter', name:'myCenter',
        component:()=>import('@/views/myCenter')
      },{//个人详细信息
        path:'/myMess', name:'myMess',
        component:()=>import('@/views/myMess')
      },{//缴费支付
        path:'/pay',name:'pay',
        component:()=>import('@/views/pay')
      },{//信息认证
        path:'/rz',name:'rz',
        component:()=>import('@/views/RZ')
      },{//佣金
        path:'/yj',name:'yj',
        component:()=>import('@/views/yj')
      },{//佣金提现
        path:'/yjtx',name:'yjtx',
        component:()=>import('@/views/YJ_TX')
      },{//佣金提现明细
        path:'/yjtxmx',name:'yjtxmx',
        component:()=>import('@/views/YJ_TXMX')
      },{//我的团队
        path:'/myTeam',name:'myteam',
        component:()=>import('@/views/myTeam')
      },{//订单列表
        path:'/orderlist',name:'orderlist',
        component:()=>import('@/views/orderlist')
      }

  ]
})

router.beforeEach(function(to, from, next){
			next()
})

export default router
