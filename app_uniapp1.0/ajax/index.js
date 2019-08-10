import apis from './apis.js'
let ajax = {}


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
					if(res.data.code == 403 || res.data.code == 500){
						uni.showToast({
							icon:"none",
							title:"请重新登录",
							duration:1555,
							complete:function(){
								setTimeout(()=>{
									uni.navigateTo({
										url: '/pages/login/login',
									});
								},1600)
							}
						})
					}else{
						resolve(res.data)
					}
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
					if(res.data.code == 403 || res.data.code == 500){
						uni.showToast({
							icon:"none",
							title:"请重新登录",
							duration:1555,
							complete:function(){
								setTimeout(()=>{
									uni.navigateTo({
										url: '/pages/login/login',
									});
								},1600)
							}
						})
					}else{
						resolve(res.data)
					}
				}else{
					reject(res)
				}
			},
			fail:(err)=>{reject(err)}
		});
	});
}
export default ajax
