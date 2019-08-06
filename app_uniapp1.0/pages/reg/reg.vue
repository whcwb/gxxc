<template>
    <!-- <view class="content">
        <view class="input-group">
            <view class="input-row border">
                <text class="title">账号：</text>
                <m-input type="text" focus clearable v-model="account" placeholder="请输入账号"></m-input>
            </view>
            <view class="input-row border">
                <text class="title">密码：</text>
                <m-input type="password" displayable v-model="password" placeholder="请输入密码"></m-input>
            </view>
            <view class="input-row">
                <text class="title">邮箱：</text>
                <m-input type="text" clearable v-model="email" placeholder="请输入邮箱"></m-input>
            </view>
        </view>
        <view class="btn-row">
            <button type="primary" class="primary" @tap="register">注册</button>
        </view>
    </view> -->
	<view style="width: 100%;background:rgba(255,255,255,1);">
	<img src="/static/img/banner.png" style="height: 336upx;width: 750upx;">
		<view class="inputMess">
			<input class="uni-input input"  v-for="item in inputList" :placeholder="item.placeholder" v-model="item.val"/>
		</view>
		<view class="btn">
			立即注册
		</view>
		<view style="margin-bottom: 114upx;display: flex;justify-content: center;align-items: center;">
			<checkbox value="cb" />我已阅读并同意
			<text style="font-size:28upx;font-weight:400;color:rgba(51,128,168,1);">
				《注册协议》
			</text>
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
                account: '',
                password: '',
                email: '',
				
				
				inputList:[						//验证、提交可let临时数组or对象,若有新属性，可添加
					{
						placeholder:'请输入手机号',
						val:''
					},
					{
						placeholder:'请输入验证码',
						val:''
					},
					{
						placeholder:'请输入密码',
						val:''
					},
					{
						placeholder:'请输入真实姓名',
						val:''
					},
					{
						placeholder:'请输入身份证号',
						val:''
					},
					{
						placeholder:'请输入邀请码',
						val:''
					}
				]
            }
        },
        methods: {
            register() {
                /**
                 * 客户端对账号信息进行一些必要的校验。
                 * 实际开发中，根据业务需要进行处理，这里仅做示例。
                 */
                if (this.account.length < 5) {
                    uni.showToast({
                        icon: 'none',
                        title: '账号最短为 5 个字符'
                    });
                    return;
                }
                if (this.password.length < 6) {
                    uni.showToast({
                        icon: 'none',
                        title: '密码最短为 6 个字符'
                    });
                    return;
                }
                if (this.email.length < 3 || !~this.email.indexOf('@')) {
                    uni.showToast({
                        icon: 'none',
                        title: '邮箱地址不合法'
                    });
                    return;
                }

                const data = {
                    account: this.account,
                    password: this.password,
                    email: this.email
                }
                service.addUser(data);
                uni.showToast({
                    title: '注册成功'
                });
                uni.navigateBack({
                    delta: 1
                });
            }
        }
    }
</script>

<style>
	.inputMess {
		width: 100%;
		background: rgba(255, 255, 255, 1);
		margin-bottom: 58upx;
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
	
	/deep/ .input-placeholder {
		font-size: 32upx;
		font-weight: 400;
		color: rgba(200, 200, 200, 1);
	}
	
	/deep/ .uni-input {
		padding: 0
	}
</style>
