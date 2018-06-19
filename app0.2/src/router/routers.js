module.exports = [
  {
    path: '/login',
    name: 'login',
    component:()=>import('@/views/login'),
    meta:{
      title:'登录'
    }
  },
  {
    path: '/',
    name: 'Home',
    component:()=>import('@/views/home'),
    meta:{
      title:'学车联盟'
    }
  }
]
