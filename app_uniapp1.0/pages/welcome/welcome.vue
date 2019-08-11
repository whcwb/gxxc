<template>
	<view style="width: 100%;">
		<image src="/static/img/welcome.png" style="width: 100vw;height: 100vh;"></image>
		<w-loading text="加载中.." mask="false" click="true" ref="loading"></w-loading>
	</view>
</template>

<script>
	import wLoading from "@/components/w-loading/w-loading.vue";
	export default {
		components: {
			wLoading
		},
		data() {
			return {
				imgheight: 200,
			}
		},
		onShow(){
			
		},
		onReady() {
			// this.toLogin()
			this.$refs.loading.open()
			this.isYqm()
			
		},
		methods: {
			isYqm(){
				var url = location.search; //获取url中"?"符后的字串
				alert(url)
				 if (url.indexOf("?") != -1) {    //判断是否有参数
					var str = url.substr(1); //从第一个字符开始 因为第0个是?号 获取所有除问号的所有符串
					var strs = str.split("&");//用等号进行分隔 （因为知道只有一个参数 所以直接用等号进分隔 如果有多个参数 要用&号分隔 再用等号进行分隔）
					var yam = strs[1].split('=');
					var yqm = yam[1];
					alert(yqm,'yqm')
					if(yqm!=''&&yqm!=null&&yqm!=undefined&&yam[0]=='id'){
						alert(yqm,'jin ru if yqm')
						uni.setStorage({
							key: 'yqm',
							data: yqm,
							success: function () {
								alert(yqm,'cun yi ge yqm')
								uni.navigateTo({
									url: '../reg/reg'
							  })
							}
						 });
					}else{
						// #ifdef H5
						 this.getWxJs()
						// #endif
						// #ifdef APP-PLUS
						// app执行
						this.toLogin()
						// #endif
						// this.toLogin()
					}
			    }
			},
			toLogin() {
				var  token = uni.getStorageSync('token');
				setTimeout(()=>{
				if(token){
				uni.switchTab({
				url: '/pages/main/p_index/main'
				});
				}else{
				uni.navigateTo({
				url: '../login/login'
				});
				}
				},3000)
				
			},
			getWxJs() {
				var v = this
				// 微信js初始化 
				var script = document.createElement("script")
				script.type = "text/javascript"
				script.src = "http://res.wx.qq.com/open/js/jweixin-1.4.0.js"
				document.body.appendChild(script)

				script.onload = function() { // 微信js初始化 回调函数
					// console.log('*****wx', wx)
// 					// 微信js初始化成功后引用 微信功能方法
// 					//获取Code 直
					let authCode = v.wxApi.getQueryString("code");
					// console.log('获取code', authCode)
					if (authCode) {
						// 获取Openid
						v.wxApi.vueParent = this;
						v.wxApi.getOpenid(authCode, (res) => {
							// console.log('openid-------', res)
							localStorage.setItem("openid", res); //存储openid
							v.wxApi.initConfig(); //执行 微信 config
							v.toLogin()
						});
					} else {
						v.wxApi.getCode()
						return
					}
				}
			}
		}
	}
</script>

<style lang="less">
	.welcome {
		width: 100%;
		height: 100vh;
		background-color: #007AFF;

		.status_bar {
			//app 内嵌样式
			height: var(--status-bar-height);
			width: 100%;
		}

		.bodyBox {
			img {
				width: 100%;
				height: 100%;
			}
		}
	}
</style>
