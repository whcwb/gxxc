<template>
	<view style="width: 100%;padding-top: 1upx;background: rgba(255,255,255,1);">
		<view style="margin: 102upx 0 140upx;text-align: center;">
			<img src="/static/img/head.png" style="width:170upx;height: 170upx;">
		</view>
		<view class="inputMess">
			<input class="uni-input input" placeholder="请输入手机号" v-model="form.username" />
			<input class="uni-input input" password placeholder="请输入密码" v-model="form.password" />
		</view>
		<view style="margin-bottom: 136upx;text-align: right;width: 678upx;font-size:28rpx;color:rgba(37,128,222,1);" @click="topwd">忘记密码？</view>
		<view class="btn" @tap="login">
			登录
		</view>
		<view class="createAccount" @tap="toReg">
			创建账号
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					username: '18672368676',
					password: '123456'
				}
			}
		},
		onShow() {
			console.log(uni.getStorage({
				key: 'token'
			}));
		},
		onReady() {
			try {
				uni.clearStorageSync();
			} catch (e) {
				// error
			}
		},
		methods: {
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
						v.toIndex()
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none'
						})
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
	.inputMess {
		width: 100%;
		background: rgba(255, 255, 255, 1);
		margin-bottom: 28upx;
	}

	.input {
		height: 122upx;
		width: 678upx;
		border-bottom: 2upx solid #DFE7EE;
		margin: 0 auto;
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
		width: 160upx;
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
		padding: 0
	}
</style>
