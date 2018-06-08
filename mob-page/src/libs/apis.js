import axios from 'axios';
import qs from 'qs'
import { Toast } from 'mand-mobile'
import router from '@/router'

// const ajaxUrl ='http://127.0.0.1';
// const ajaxUrl = 'http://47.98.39.45:9086/';//服务器
const ajaxUrl ='http://192.168.31.228:9086';//羊祥
let API = {
    NETWORK_ERR: "网络请求异常，请重试！"
    // LOGIN: '/login',
    // LOGOUT: '/logout',
    // UPLOAD: '/upload'
};

API.title = function (title) {
  title = title || 'APP平台';
  window.document.title = title;
};

API.ajax = axios.create({
  baseURL: ajaxUrl,
  timeout: 30000,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
});

API.ajax.interceptors.request.use(config=> {
    //网络请求加载动画
    Toast.loading('加载中...');
    var headers = config.headers;
    var contentType = headers['Content-Type'];
    if (contentType == "application/x-www-form-urlencoded"){
      config.data = qs.stringify(config.data);
      try{
        //如果是数组对象，将转换出来的数组字符串中的[]关键字替换，这样方便后台接收数据
        config.data = config.data.replace(/(%5B\d%5D)/g,"");
      }catch(e){

      }
    }

    try{
      let accessTokenStr = localStorage.getItem("token");
      if (accessTokenStr != null && accessTokenStr != ''){
        let jsonObject = JSON.parse(accessTokenStr);
        config.headers.common['userId'] = jsonObject.userId;
        config.headers.common['token'] = jsonObject.token;
      }
    }catch(e){
    }

    return config;
}, error=> {
    Toast.hide();
    setTimeout(() => {
      Toast.failed(API.NETWORK_ERR)
    }, 100);
    return Promise.reject(error);
});

API.ajax.interceptors.response.use(response=> {
  //网络请求加载动画
  Toast.hide();
  if(response.data.code==403){
    Toast.info('权限丢失，请重新登录')
    router.push({name:'Login'})
    return
  }

  return response.data;
}, error=> {
  Toast.hide();
  setTimeout(() => {
    Toast.failed(API.NETWORK_ERR)
  }, 100);
  return Promise.reject(error);
});

export default API;
