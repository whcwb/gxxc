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
      }
  ]
})

router.beforeEach(function(to, from, next){
			next()
})

export default router