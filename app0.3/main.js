import app from './app.ui'
import '#/static/css/box.less'
let options = {
  app: app,
  beforeEach(to,from,next) {
    let um = localStorage.getItem('usermess')
    try{
      if(!um){
        ui.getApp().userMess = JSON.parse(localStorage.getItem('usermess'))
      }
    }catch(e){ }
    
    if(to.path=='/pages/login' || to.path=='/pages/reg' || to.path=='/pages/retrieveworld' ){
      next()
      return
    }else if(!um){
      ui.showToast({ title: '用户信息丢失，请重新登录！' })
      next({
        path:'/pages/login'
      })
    }else{
      next()
    }
    console.log('去',to)
    console.log('来',from)
  }
}

ui.extend({
    getUser(){
      return JSON.parse(localStorage.getItem('usermess'))
    },
    getUserMess(callback){
      this.$http('POST',ui.getApp().apis.USERMESS,{},(res)=>{
        if(res.code==200 && res.result){
            console.log('用户信息',res)
            if(res.result.yhTx == ''){
              res.result.yhTx ='static/img/login/logo.png'
            }
          }
          localStorage.setItem('usermess',JSON.stringify(res.result))
          ui.getApp().userMess = res.result
          callback && callback(res.result)
        }
      )
    },

    $http(method,url,data,callback){
      let accessTokenStr = localStorage.getItem("token");
      if(accessTokenStr != null && accessTokenStr != ''&& accessTokenStr!=undefined){
        let tokMess = JSON.parse(accessTokenStr)
        ui.getApp().Ajax.header.token = tokMess.token
        ui.getApp().Ajax.header.userId = tokMess.userId
      }

      ui.request({
        // ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+
        url: url, //仅为示例，并非真实的接口地址
        data: data,
        header: ui.getApp().Ajax.header,
        method:method,
        success: function (res) {
          console.log('请求成功')
          if(res.data.code ==403){
            ui.showToast({ title: res.data.message})
            ui.redirectTo({
              url: '/pages/login'
            })
          }else{
            callback && callback(res.data);
          }
        },
        fail:function(err){
          console.log('请求失败')
          callback && callback(err.data);
        },
        complete:function(mess){
          console.log('请求结果')
          // callback && callback(mess.data);
        }
      })
    },




  sayHello(){
    console.log('全局方法参数',ui.getApp().Ajax)
  },
  goBack(){
    ui.navigateBack()
  },
  pageHeight(val){
    return  ui.DEFAULT_CONTENT_HEIGHT + val
            // default content height
  },
  getdateStr(Y){
    var NowDate = new Date
    let Year = NowDate.getFullYear()+Y
    let Month = NowDate.getMonth()+1
    let Day = NowDate.getDate()
    let Hours = NowDate.getHours()
    let Minutes = NowDate.getMinutes()
    let Seconds = NowDate.getSeconds()
    // +Seconds+'秒'
    let time = Year+'年'+Month+'月'+Day+'日'+Hours+'时'+Minutes+'分'
    return time
  },
})

ui.start(options)