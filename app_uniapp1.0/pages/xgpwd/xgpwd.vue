<template>
	 <view style="width: 100%;background:rgba(255,255,255,1);">
			<view class="inputMess" v-for="item in inputList">
				<input class="uni-input input" :placeholder="item.placeholder" v-model="item.val" />
				<view v-if="item.placeholder==='请输入验证码'" class="inputCodeTip">请获取验证码</view>
			</view>
			<view class="btn">
				立即修改
			</view>
	   
	</view>
</template>

<script>
	 import mInput from '../../components/m-input.vue';
	export default {
		 components: {
		    mInput
		},
		data() {
			return {
				inputList: [ //验证、提交可let临时数组or对象,若有新属性，可添加
					{
						placeholder: '请输入旧密码',
						val: ''
					},
					{
						placeholder: '请输入新密码',
						val: ''
					},
					{
						placeholder: '请再输入新密码',
						val: ''
					},
				]
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.inputMess {
		position: relative;
		width: 100%;
		background: rgba(255, 255, 255, 1);
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
		margin: 58upx auto 40upx;
		font-size: 40upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}

	.inputCodeTip {
		position: absolute;
		right: 48upx;
		top: 50%;
		transform: translateY(-50%);
		font-size: 32upx;
		font-weight: 400;
		color: rgba(37, 128, 222, 1);
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
