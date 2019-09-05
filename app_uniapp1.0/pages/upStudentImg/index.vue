<template>
	<view style="font-family:PingFangSC-Regular;width: 100%;background-color: rgba(245,246,249,1);">
		<view>
			<view class="IDPhoto">
				<view class="text">温馨提示：请上传原始比例的学生证，请勿裁剪涂改，保证证件信息清晰显示</view>
				<view style="display: flex;justify-content: space-around;align-items: center;" @click="upImg">
					<view class="upFlieBox" style="">
						<span v-if="!upfile">
							上传
						</span>
						<span v-else>
							完成
						</span>
					</view>
					<!-- <img v-if="upImgControl" :src="imgList.zm" @click="getImg(0,10)" style="width: 300upx;height: 180upx;"> -->
					<!-- <robby-image-upload 
					@adds="add" 
					v-model="imageData" 
					fileKeyName="file" server-url="/wj/uploadWj" 
					:form-data="formData" limit="1"></robby-image-upload> -->
				</view>
			</view>
		</view>
		<view v-if="upfile" class="btn" @tap="toPay">
			去缴费
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
				upfile:false,
				zm: '',
				upImgControl:false,
				imageData:'',
				appImg:'',
				formData:{
					userid:'',
					token:''
				},
			}
		},
		onShow() {

			var v=this
				uni.getStorage({
				    key: 'token',
				    success: function (res) {
				        v.formData.token=res.data.token
						v.formData.userid=res.data.userId
				    }
				});
			
			// #ifdef H5
			this.upImgControl=true
			// #endif
			// #ifdef APP-PLUS
			this.upImgControl=false
			// #endif
		},
		methods: {
			add(e){
				uni.hideLoading();
                this.appImg=e.result.filePath
            },
			upImg(){
				uni.chooseImage({
				    success: (chooseImageRes) => {
				        const tempFilePaths = chooseImageRes.tempFilePaths;
				        uni.uploadFile({
				            url: this.apis.url+'/app/wj/uploadWj', //仅为示例，非真实的接口地址
				            filePath: tempFilePaths[0],
				            name: 'file',
							header:{
								token:this.formData.token
							},
				            formData:{
								userId:this.formData.userid
							},
				            success: (uploadFileRes) => {
				                console.log(uploadFileRes.data);
								this.upfile = true
				            }
				        });
				    }
				});
			},
			toPay() {
				uni.navigateTo({
					url: '/pages/center/learnCarFile/learnCarFile'
				})
			}
		}
	}
</script>

<style>
	.upFlieBox{
		width: 300rpx;
		height: 300rpx;
		margin: auto;
		background: #FFFFFF;
		border: 6rpx #ededed dashed;
		text-align: center;
		line-height: 300rpx;
		font-size: 52rpx;
		font-weight: 600;
		color: #717171;
	}
	
	.personMess {
		margin: 16upx 0 16upx 44upx;
		font-size: 36upx;
		font-weight: 400;
		color: rgba(51, 51, 51, 1);
	}

	.inputMess {
		width: 100%;
		height: 200upx;
		background: rgba(255, 255, 255, 1);
		margin-bottom: 65rpx;
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


	/deep/ .input-placeholder {
		font-size: 32upx;
		font-weight: 400;
		color: rgba(200, 200, 200, 1);
	}

	/deep/ .uni-input {
		padding: 0
	}
</style>
