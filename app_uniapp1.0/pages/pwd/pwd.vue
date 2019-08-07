<template>
    <view style="width: 100%;background:rgba(255,255,255,1);">
        
            <!-- <view class="input-row">
                <text class="title">邮箱：</text>
                <m-input type="text" focus clearable v-model="email" placeholder="请输入邮箱"></m-input>
            </view> -->
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
    import service from '../../service.js';
    import mInput from '../../components/m-input.vue';

    export default {
        components: {
            mInput
        },
        data() {
            return {
               inputList: [ //验证、提交可let临时数组or对象,若有新属性，可添加
               	{
               		placeholder: '请输入手机号',
               		val: ''
               	},
               	{
               		placeholder: '请输入验证码',
               		val: ''
               	},
               	{
               		placeholder: '请输入新密码',
               		val: ''
               	},
               ]
            }
        },
        methods: {
            findPassword() {
                /**
                 * 仅做示例
                 */
                if (this.email.length < 3 || !~this.email.indexOf('@')) {
                    uni.showToast({
                        icon: 'none',
                        title: '邮箱地址不合法',
                    });
                    return;
                }
                uni.showToast({
                    icon: 'none',
                    title: '已发送重置邮件至注册邮箱，请注意查收。',
                    duration: 3000
                });
            }
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
