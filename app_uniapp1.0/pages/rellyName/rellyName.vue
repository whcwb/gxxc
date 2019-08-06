<template>
	<view style="font-family:PingFangSC-Regular;width: 100%;background-color: rgba(245,246,249,1);">
		<view v-if="!isDone">
			<view class="personMess">身份信息</view>
			<view class="inputMess">
				<input class="uni-input input" focus placeholder="请输入您的真实姓名" />
				<input class="uni-input input" style="border-bottom: none;" focus placeholder="请输入您的身份证号码" />
			</view>
			<view class="personMess">身份证正反面照</view>
			<view class="IDPhoto">
				<view class="text">温馨提示：请上传原始比例的身份证正反面，请勿裁剪涂改，保证身份证信息清晰显示</view>
				<robby-image-upload v-model="imageData" @delete="deleteImage" @add="addImage" :enable-drag="enableDrag" :enable-del="enableDel"
				 :enable-add="enableAdd" :limit="limitNumber"></robby-image-upload>
				<view style="margin-bottom: 14upx;font-size:24upx;font-weight:400;color:rgba(153,153,153,1);">示例</view>
				<img src="/static/img/my/exp.png" style="width: 132upx;height: 84upx;">
			</view>
		</view>

		<view v-else class="done">
			<view style="margin-top:234upx;">
			<img src="/static/img/my/success.png" style="width: 240upx;height: 240upx;bottom: 78upx;">
			</view>
			<view style="margin-top: 78upx;font-size:40upx;font-weight:400;color:rgba(51,51,51,1);">审核通过</view>
		</view>

		<view class="btn" @tap="toPay">
			{{isDone?'去缴费':'申请认证'}}
		</view>
	</view>
</template>

<script>
	import robbyImageUpload from '@/components/robby-image-upload/robby-image-upload.vue'
	export default {
		components: {
			robbyImageUpload
		},
		data() {
			return {
				isDone: true //认证控制
			}
		},
		methods: {
			toPay(){
				if(this.isDone){
					uni.navigateTo({
					    url: '/pages/goMoney/goMoney',
					});
				}else return
			}
		}
	}
</script>

<style>
	.personMess {
		margin: 16upx 0 16upx 44upx;
		font-size: 36upx;
		font-weight: 400;
		color: rgba(51, 51, 51, 1);
	}

	.inputMess {
		width: 100%;
		height: 276upx;
		background: rgba(255, 255, 255, 1);
	}

	.input {
		height: 136upx;
		width: 678upx;
		border-bottom: 2upx solid #DFE7EE;
		margin: 0 auto;
	}

	.IDPhoto {
		width: 100%;
		height: 572upx;
		background: rgba(255, 255, 255, 1);
		padding-top: 1upx;
		text-align: center;
		margin-bottom: 84upx;
	}

	.text {
		width: 632upx;
		font-size: 28upx;
		font-weight: 400;
		color: rgba(153, 153, 153, 1);
		margin: 16upx auto 54upx;
		text-align: left;
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
		margin: 0 auto;
		font-size: 40upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}

	.done {
		width: 100%;
		height: 1008upx;
		background: rgba(255, 255, 255, 1);
		margin-bottom: 84upx;
		text-align: center;
		padding-top: 1upx;
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
