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
				imgheight: 200
			}
		},
		onShow(){
		},
		onReady() {
			// #ifdef H5
			this.getWxJs()
			// #endif
			this.$refs.loading.open()
			// setTimeout((val, index, arr) => {
			// 	uni.navigateTo({
			// 		url: '/pages/login/login',
			// 	});
			// }, 3000)
		},
		methods: {
			toLogin() {
				uni.navigateTo({
					url: '/pages/login/login',
				});
			},
			getWxJs() {
				var v = this
				console.log("============================getWxJs")
				// 微信js初始化 
				var script = document.createElement("script")
				script.type = "text/javascript"
				script.src = "../../libs/jweixin-1.2.0.js"
				document.body.appendChild(script)

				script.onload = function() { // 微信js初始化 回调函数
					console.log('*****wx', wx)
// 					// 微信js初始化成功后引用 微信功能方法
// 					//获取Code 直
					let authCode = v.wxApi.getQueryString("code");
					console.log('获取code', authCode)
					alert('获取code=      '+authCode);
					if (authCode) {

						// 获取Openid
						v.wxApi.vueParent = this;
						v.wxApi.getOpenid(authCode, (res) => {
							console.log('openid-------', res)
							// alert("wx3+"+res);
							localStorage.setItem("openid", res); //存储openid
							v.wxApi.initConfig(); //执行 微信 config
						});
					} else {
						alert('1')
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
