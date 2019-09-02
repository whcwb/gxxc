<template>
	<view class="backgroundDiv" style="width: 100%;padding-top: 1upx;background: rgba(255,255,255,1)" :style="backgroundDiv">
		<view style="margin: 102upx 0 140upx;text-align: center;">
			<img src="/static/img/head.png" style="width:192upx;height: 192upx;">
		</view>
		<!-- <view>infoRes:{{infoRes}}</view> 
		<view>loginRes:{{loginRes}}</view> -->
		<view class="inputMess">
			<input class="uni-input input" style="font-size: 16px;height: 40px;margin: 10px 30rpx;" placeholder="请输入手机号" v-model="form.username" />
			<input class="uni-input input" style="font-size: 16px;height: 40px;margin: 10px 30rpx;" password placeholder="请输入密码" v-model="form.password" />
		</view>
		<view style="margin-bottom: 136upx;text-align: right;width: 678upx;font-size:28rpx;color:rgba(37,128,222,1);" @click="topwd">忘记密码？</view>
		<view class="copybtn" @tap="login">
			登录
		</view>
		<!-- <view class="btn" @tap="wxlogin">
			微信登录
		</view>
		<view class="btn" @tap="logout">
			退出微信登录
		</view> -->
		<view class="sharebtn" @tap="toReg">
			创建账号
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				backgroundDiv: {
					backgroundImage:'url(' + require('./file/loginbg.png') + ')',
					backgroundRepeat:'no-repeat',
					backgroundSize:'100% 100%'
				},
				form: {
					username: '18672368676',
					password: '123456'
				},
				infoRes: '',
				loginRes: '',
				isApp:false
			}
		},
		onShow() {
			// #ifdef APP-PLUS
				this.isApp = true
			// #endif
		},
		onReady() {
			try {
				uni.removeStorageSync('token');
				uni.removeStorageSync('phone');
				uni.removeStorageSync('usermess');
			} catch (e) {
				// error
			}
		},
		methods: {
			wxlogin() {
				var self = this
				uni.logout({
					provider: 'weixin',
					success: function(loginRes) {
						console.log(loginRes);
						self.loginRes = JSON.stringify(loginRes)
					}
				});
			},
			login() {
				var v = this
				this.$http.post(this.apis.LOGIN, v.form).then(res => {
					if (res.code == 200) {
						uni.setStorage({
							key: 'token',
							data: res.result.accessToken
						});
						uni.setStorage({
							key: 'phone',
							data: v.form.username
						});
						uni.setStorage({
							key: 'openid',
							data: res.result.openid
						});
						v.toIndex()
					} else {
						uni.showToast({
							title: res.message,
							duration: 2000,
							icon: 'none'
						});
					}
				})
			},
			topwd() {
				uni.navigateTo({
					url: '/pages/pwd/pwd'
				})
			},
			toReg() {
				uni.navigateTo({
					url: '/pages/reg/reg'
				})
			},
			toIndex() {
				console.log('index');
				uni.switchTab({
					url: '/pages/main/p_index/main'
				})
			},
		}
	}
</script>

<style>
	.loginbg{
		background-image: url('./file/loginbg.png');
	}
	.copybtn {
		width:70%;
		height: 80upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
		box-shadow: 0 8upx 16upx 0 rgba(69, 124, 232, 0.5);
		border-radius: 52upx;
		margin: 40upx auto 28upx;
		font-size: 32upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
		line-height: 80upx;
		text-align: center;
	}
	
	.sharebtn {
		width: 70%;
		height: 80upx;
		box-shadow: 0 8upx 16upx 0 rgba(69, 124, 232, 0.5);
		border-radius: 52upx;
		border: 2upx solid rgba(151, 151, 151, 1);
		margin: 0 auto;
		font-size: 32upx;
		font-weight: 400;
		line-height: 80upx;
		text-align: center;
	}
	.inputMess {
		width: 100%;
		background: rgba(255, 255, 255, 0);
		margin-bottom: 28upx;
	}

	.input {
		height: 122upx;
		width: 678upx;
		border-bottom: 2upx solid #DFE7EE;
		margin: 0 auto;
		background: rgba(245,246,249,1);
	}

	.btn {
		width: 592upx;
		height: 104upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
		box-shadow: 0 8upx 16upx 0 rgba(69, 124, 232, 0.5);
		border-radius: 52upx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 0 auto 40upx;
		font-size: 40upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}

	.createAccount {
		width: 280upx;
		height: 56upx;
		font-size: 16px;
		text-align: center;
		font-weight: 400;
		color: rgba(51, 51, 51, 1);
		margin: 0 auto;
	}

	/deep/ .input-placeholder {
		font-size: 32upx;
		font-weight: 400;
		color: rgba(200, 200, 200, 1);
	}

	/deep/ .uni-input {
		padding: 0;
	}
</style>
