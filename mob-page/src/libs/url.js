module.exports = {
  wechat:{
      getCode:'/wechat/getCode',
      getOpenid:'/wechat/getOpenid',
      getAccessToken:'/wechat/getAccessToken',
      getJsApiSign:'/wechat/getJsApiSign',
  },
  LOGIN:'/app/login',//登录接口
  USERMESS:'/app/ptyh/get',//用户信息
  USERZH:'/app/zh/get',//账户余额
  CODEYZ:'/app/yzyym',//邀请码验证
  PHINECODE:'/app/sendSMSzc',//获取短信验证码
  YZDX:'/app/validateSms',//短信验证
  USERSAVE:'/app/ptyh/save',//用户注册
  SWIPER:'/app/hd/pager ',//轮播图
  TX:'/app/tx/save',//资金提现
  TEAM:'/app/user/pager',//我的团队
}
