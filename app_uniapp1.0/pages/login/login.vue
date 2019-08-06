<template>
		<view style="width: 100%;padding-top: 1upx;background: rgba(255,255,255,1);">
			<view style="margin: 102upx 0 140upx;text-align: center;">
				<img src="/static/img/head.png" style="width:136upx;height: 136upx;">
			</view>
			<view class="inputMess">
				<input class="uni-input input"  v-for="item in inputList" :placeholder="item.placeholder" v-model="item.val"/>
			</view>
			<view style="margin-bottom: 136upx;text-align: right;width: 678upx;font-size:32upx;color:rgba(37,128,222,1);">忘记密码？</view>
			<view class="btn" @tap="toIndex">
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
				inputList:[						//验证、提交可let临时数组or对象,若有新属性，可添加
					{
						placeholder:'请输入手机号',
						val:''
					},
					{
						placeholder:'请输入密码',
						val:''
					}
				]
			}
		},
		methods: {
			toReg(){
				uni.navigateTo({
					url: '/pages/reg/reg'
				})
			},
			toIndex(){
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
	
	.createAccount{
		width:160upx;
		height:56upx;
		font-size:40upx;
		font-weight:400;
		color:rgba(51,51,51,1);
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
