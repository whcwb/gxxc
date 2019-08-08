import apis from './apis.js'
// import CryptoJS from 'crypto-js'
// import store from '../store/index.js'
// let clientId = "app"
// let secret = '12341234'
let ajax = {}

// function encryptByDES(message, key){
//     const keyHex = CryptoJS.enc.Utf8.parse(key);
//     const encrypted = CryptoJS.DES.encrypt(message, keyHex, {
//         mode: CryptoJS.mode.ECB,
//         padding: CryptoJS.pad.Pkcs7
//     });
//     return encrypted.toString();
// }

//取token
var token='';
var userId=''
function getToken(){
	uni.getStorage({
	    key: 'token',
	    success: function (res) {
	        token=res.data.token
			userId=res.data.userId
	    }
	});
}

ajax.post = (httpUrl,data) => {
	getToken();
	return new Promise(function(resolve, reject) {
		uni.request({
			url: apis.url+httpUrl, 
			data :data,
			method:'POST',
			header: {
				'Content-Type': 'application/x-www-form-urlencoded',
				'token':token,
				'userid':userId
			},
			success: (res) => {
				if(res.statusCode == 200){
					resolve(res.data)
				}else{
					reject(res)
				}
			},
			fail:(err)=>{reject(err)}
		});
	});
};
ajax.get = (url,data) => {
	return new Promise(function(resolve, reject) {
		uni.request({
			url: apis.url+url, 
			data : data,
			method:'GET',
			header: {
				'Content-Type': 'application/x-www-form-urlencoded',
				'token':token,
				'userid':userId
			},
			success: (res) => {
				if(res.statusCode == 200){
					resolve(res.data)
				}else{
					reject(res)
				}
			},
			fail:(err)=>{reject(err)}
		});
	});
}
export default ajax
