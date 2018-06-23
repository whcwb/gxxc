import axios from 'axios';
import qs from 'qs'
import { Indicator , Toast} from 'mint-ui';
import router from '@/router'
import url from './url'
import wxutil from  './wechatUtil'

// const dk = '9086'
const dk = '8080/biz'
const ajaxUrl =url.ajaxUrl + dk;//羊祥
let API = {
  NETWORK_ERR: "网络请求异常，请重试！",
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
  timeout: 1000*10,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
  }
  // 'openid':'oRPNG0pmiE91Qt9qjas37mMpnz0I',
});

API.ajax.interceptors.request.use(config=> {
  //网络请求加载动画
  Indicator.open({
    text: '数据加载中……',
    spinnerType: 'fading-circle'
  });

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

    config.headers.common['openid'] = localStorage.getItem('openid')
  }catch(e){
  }

  return config;
}, error=> {
  Indicator.close();
  setTimeout(() => {
    Toast('数据请求超时')
    router.push({path:'/'})
  }, 100);
  return Promise.reject(error);
});

API.ajax.interceptors.response.use(response=> {
  //网络请求加载动画
  Indicator.close();

  if(response.data.code===403){
    Toast('权限丢失，请重新登录')
    localStorage.clear();
    setTimeout(function () {
      window.location.href = "/wx/";
      // router.push({name:'Login'})
    },100)
  }

  return response.data;
}, error=> {
  Indicator.close();
  setTimeout(() => {
    Toast(API.NETWORK_ERR)
  }, 100);
  return Promise.reject(error);
});

export default API;
