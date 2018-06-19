module.exports = [
  {
    path: '/',
    name: 'login',
    component:()=>import('@/views/login'),
    meta:{
      title:'登录'
    }
  },
  {
    path: '/Home',
    name: 'Home',
    component:()=>import('@/views/home'),
    meta:{
      title:'学车联盟'
    }
  }
]
