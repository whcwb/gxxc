import Vue from 'vue'
import App from './App'
Vue.config.productionTip = false
App.mpType = 'app'

let options = {
  app: app,
  beforeEach(to,from,next) {
    let um = localStorage.getItem('usermess')
    try{
      if(!um){
        
      }
    }catch(e){ }
    
    if(to.path=='/pages/login' || to.path=='/pages/reg' || to.path=='/pages/retrieveworld' || to.path=='/pages/Welcome' ){
      next()
      return
    }else if(!um){
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

const app = new Vue({
    ...App
})
app.$mount()
