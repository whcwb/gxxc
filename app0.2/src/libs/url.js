// let ajaxUrl = "http://xclm.xxpt123.com";
// let ajaxUrl = "http://127.0.0.1"
  let ajaxUrl = "http://192.168.31.228"
module.exports = {
  url:ajaxUrl,
  wechat:{
      getCode:'/wechat/getCode',
      getOpenid:'/wechat/getOpenid',
      getAccessToken:'/wechat/getAccessToken',
      getJsApiSign:'/wechat/getJsApiSign',
  },
  ajaxUrl: ajaxUrl + ':',


  getImgUrl:ajaxUrl+':8001/',//获取
  // upImgUrl:ajaxUrl+':8080/biz/upload',//上传
  upImgUrl:ajaxUrl+':9086/upload',//上传



    // 证件识别
  ZJSB:'/app/zjsb',


  LOGIN:'/app/login',//登录接口
  LOGOUT:'/app/logout',//退出用戶
  USERMESS:'/app/ptyh/get',//用户信息
  USERIMGMESS:'/app/wj/getCondition',//获取用户图片

  USERZH:'/app/zh/get',//账户余额
  CODEYZ:'/app/yzyym',//邀请码验证
  PHINECODE:'/app/sendSMSzc',//获取短信验证码
  YZDX:'/app/validateSms',//短信验证
  USERSAVE:'/app/ptyh/save',//用户注册
  SWIPER:'/app/hd/pager ',//轮播图
  TX:'/app/tx/save',//资金提现
  TEAM:'/app/user/pager',//我的团队
  CHUSERMESS:'/app/ptyh/update',//昵称 头像修改
  UPWORLD:'app/ptyh/mdfPwd',//密码修改
  CPTYPE:'/app/cp/getcplx',//产品类别
  CPPAY:'/app/order/save',//产品支付
  ADDBANK:'/app/yhk/save',//新增銀行卡
  BANKLIST:'/app/yhk/getCondition',//银行卡列表
  ZDLIST:'/app/yjmx/pager',//账单列表
  ZDLISTTYPE:'/app/zdxm',//账单列表状态

  //我的学员
  SUDENTLIST:'/app/cjd/getcjblist',//学院列表
  XYMESS:'/app/cjd/getxy',//学员详情
  XYCJD:'/app/cjd/save',//学院成绩单上传

  //认证
  IDRZ:'/app/ptyh/updatesm',//身份认证 实名认证
  JLRZ:'app/ptyh/updatelx',//教练员认证

  zdxmPager:'app/zdxm/getzdxm',

  getStudentCoach:'/app/ptyh/getStudentCoach', // 获取学员教练信息

  getHandleStatus:'app/kssl/getHandleStatus', // 获取受理状态信息

  getExamInfo:'app/ksyk/getUserExamInfo', // 考试信息
  getPayInfo:'app/ksjf/getPayInfo' // 缴费信息
}
