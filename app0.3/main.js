import app from './app.ui'
import '#/static/css/box.less'

let options = {
  app: app,
  beforeEach(to,from,next) {
    console.log('去',to)
    console.log('来',from)
    next()
  }
}
// const Ajax = ui.getApp().Ajax

ui.extend({
    $http(method,url,data,callback){
      ui.request({
        url: ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+url, //仅为示例，并非真实的接口地址
        data: data,
        header: ui.getApp().Ajax.header,
        method:method,
        success: function (res) {
          console.log(res)
          callback && callback(res);
        },
        fail:function(err){
          console.log(err)
          callback && callback(err);
        },
        complete:function(mess){
          console.log(mess)
          callback && callback(mess);
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