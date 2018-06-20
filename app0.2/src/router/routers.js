module.exports = [
  {
    path: '/',
    name: 'Login',
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
  },
  {
    path: '/myCenter-qrcode',
    name: 'myCenterQrcode',
    meta:{
      title:'我的二维码'
    },
    component:()=>import('@/views/main/center/page/qrcode.vue'),
  },
  {
    path: '/myCenter-info',
    name: 'myCenterInfo',
    meta:{
      title:'个人信息'
    },
    component:()=>import('@/views/main/center/page/info.vue'),
  },
]
