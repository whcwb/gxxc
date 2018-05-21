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
      }
  ]
})

router.beforeEach(function(to, from, next){
			next()
})

export default router
