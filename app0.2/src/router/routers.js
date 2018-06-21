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
    path: '/reg',
    name: 'reg',
    component:()=>import('@/views/reg'),
    meta:{
      title:'注册'
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
  {
    path: '/bill',
    name: 'bill',
    meta:{
      title:'账单'
    },
    component:()=>import('@/views/main/bill')
  },
  {
    path:'/myTeam',name:'myteam',
    meta:{title:'我的团队'},
    component:()=>import('@/views/main/myTeam')
  },
  {
    path:'/smrz',name:'smrz',
    meta:{title:'实名认证'},
    component:()=>import('@/views/main/smrz')
  },{
    path:'/myStudent',name:'myStudent',
    meta:{title:'我的学员'},
    component:()=>import('@/views/main/student')
  },{
    path:'/stuMess',name:'stuMess',
    meta:{title:'学员信息'},
    component:()=>import('@/views/main/stuMess')
  },{
    path:'/coach',name:'coach',
    meta:{title:'教练信息'},
    component:()=>import('@/views/main/mess/coach')
  },{
    path: '/pay/payIndex',
    name: 'pay',
    meta:{
      title:'缴费'
    },
    component:()=>import('@/views/main/pay')
  },{
    path: '/tx',
    name: 'tx',
    meta:{
      title:'提现'
    },
    component:()=>import('@/views/main/TX')
  }
]
