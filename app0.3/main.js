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

ui.extend({
  sayHello(){
    console.log('全局方法')
  },
  pageHeight(val){
    return  ui.DEFAULT_CONTENT_HEIGHT + val
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