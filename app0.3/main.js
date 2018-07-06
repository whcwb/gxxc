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
  sayHello:function(){
    console.log('全局方法')
  }
})

ui.start(options)