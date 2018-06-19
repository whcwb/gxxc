import Apis from '@/libs/apis';
import apis from '@/libs/url';
import store from '@/store'
let myFunction = {}

myFunction.auto=function (window, document , num) {
  function resize(){
    var ww = window.innerWidth;
    if (ww > window.screen.width) {
      window.requestAnimationFrame(resize);
    }
    else{
      if (ww > 720) {
        ww = 720
      }
      // set 1rem based on iPhone6 750px width, 750/7.5=100
      // document.documentElement.style.fontSize = ww / 7.5 + 'px';
      document.documentElement.style.fontSize = ww / num + 'px';

      document.body.style.opacity = 1;
    }
  }
  if (document.readyState !== 'loading') {
    resize();
  }
  else {
    document.addEventListener('DOMContentLoaded', resize);
  }
  window.addEventListener('resize', resize);

}

myFunction.GetUserMess = function(v,callback){//获取个人信息
  Apis.ajax.post(apis.USERMESS).then((res)=>{
    if(res.code==200){
      if(res.result.yhTx == ''){
        res.result.yhTx ='/static/login/LOGO.png'
      }
      localStorage.setItem('userMess',JSON.stringify(res.result))
    }
    callback && callback(res.result)
    store.commit('M_userMess', res.result)

  }).catch((err)=>{
    console.log('出错了！！！')
  })
}




export default myFunction
