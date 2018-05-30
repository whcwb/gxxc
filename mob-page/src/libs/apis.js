import axios from 'axios';
import qs from 'qs'
import { Toast } from 'mand-mobile'

const ajaxUrl ='http://127.0.0.1';
let API = {
    NETWORK_ERR: "网络请求异常，请重试！",
    LOGIN: '/login',
    LOGOUT: '/logout',
    UPLOAD: '/upload'
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
      let accessTokenStr = localStorage.getItem("accessToken");
      if (accessTokenStr != null && accessTokenStr != ''){
        let jsonObject = JSON.parse(accessTokenStr);
        config.headers.common['user_id'] = jsonObject.userId;
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

  return response;
}, error=> {
  Toast.hide();
  setTimeout(() => {
    Toast.failed(API.NETWORK_ERR)
  }, 100);
  return Promise.reject(error);
});

export default API;
