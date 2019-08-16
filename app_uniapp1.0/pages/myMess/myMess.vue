<template>
	<view class="box_col" style="width: 100%;">
		
		<view style="">
			<cmd-cell-item title="我的头像" slot-right @click='getTx' arrow>
				<img :src="usermess.yhTx" style="width: 60upx;height: 60upx;border-radius: 50%;">
			</cmd-cell-item>
			<cmd-cell-item title="我的昵称" slot-right @click='promptVisible=true' arrow>
				{{usermess.yhBm}}
			</cmd-cell-item>
			<cmd-cell-item title="修改密码" slot-right @click='toxgpwd' arrow></cmd-cell-item>
		</view>
		<view style="margin: 60rpx;">
			<view class="btn" @tap="tologin">
				退出登录
			</view>
		</view>
		<view class="box_col_100">
			
		</view>
		
		<view class="copyright">
			<view>
				Copyright@2018-2019
			</view>
			<view>
				吉驾无忧1.0
			</view>
			<view>
				武汉天弘腾创科技有限公司
			</view>
		</view>
		
		<prompt :visible.sync="promptVisible"  title="修改昵称" placeholder="请输入新的昵称"  @confirm="updateName" mainColor="#e74a39">
		</prompt>
	</view>
	
</template>

<script>
	import Prompt from '@/components/zz-prompt/index.vue'
	import cmdCellItem from '@/components/cmd-cell-item/cmd-cell-item.vue'
	export default {
		components: {cmdCellItem,Prompt},
		data() {
			return {
				usermess:{},//个人信息
				promptVisible:false
			}
		},
		methods: {
			getusermess(){
				this.$http.post(this.apis.USERMESS).then(res => {
					if (res.code == 200) {
						this.usermess = res.result
					} else {
						uni.showToast({
							title: res.message,
							duration: 2000,
							icon:'none'
						});
					}
				}).catch(err => {})
			},
			updateName(val){
				var v=this
				if(val!=''){
                  this.$http.post(this.apis.CHUSERMESS,{yhBm:val}).then(res=>{
                      if(res.code==200){
                        uni.showToast({
                          title: '昵称修改成功'
                        })
                        v.usermess.yhBm = val
                        v.getusermess()
						v.promptVisible=false					//关闭对话框
                      }else{
                        uni.showToast({
                          title: '昵称修改失败',
						  icon:'none'
                        })
                      }
                  })
              }else{

              }
			},
			tologin(){
				uni.reLaunch({
					url:'../login/login'
				})
			},
			upImgAPP(){
				var v=this
				uni.chooseImage({
				    success: (chooseImageRes) => {
				        const tempFilePaths = chooseImageRes.tempFilePaths;
				        uni.uploadFile({
				            url: v.apis.upImgUrl, //仅为示例，非真实的接口地址
				            filePath: tempFilePaths[0],
				            name: 'file',				            
				            success: (uploadFileRes) => {
								v.updateTXAPP(JSON.parse(uploadFileRes.data).message)
				            }
				        });
				    }
				});
			},
			updateTXAPP(path){
				this.$http.post(this.apis.CHUSERMESS,{yhTx:path}).then(res=>{
				this.getusermess((res)=>{
					this.usermess = res
					})
				})
			},
			getTx(){				
				// #ifdef APP-PLUS
				this.upImgAPP()
				return
				// #endif
				var v = this
				this.wxApi.chooseImage((imgID)=>{
				this.wxApi.uploadImage(imgID[0],(httpID)=>{
				v.UPIMG(httpID.serverId)
					})		
				})
			},
			UPIMG(id){
				var v = this
				this.$http.post(this.apis.WXIMGUP,{code:id,fileType:'-'}).then(res=>{
				this.getusermess((res)=>{
					this.usermess = res
					})
				})
			},
			toxgpwd(){
				uni.navigateTo({
					url:'../xgpwd/xgpwd'
				})
			}
		},
		onShow(){
			this.getusermess()
		}
	}
</script>

<style>
 .txItem{
    background-color: #fff;
    padding: 25rpx 25rpx;
    margin-bottom:5rpx;  
    font-size: 30rpx;
  }
   .copyright{
    text-align: center;
    padding-bottom: 120rpx;
  }
  
  .btn{
	  	width: 592upx;
	  	height: 104upx;
	  	background:linear-gradient(90deg,rgba(254,128,101,1) 0%,rgba(238,70,68,1) 100%);
	  	box-shadow: 0 8upx 16upx 0 rgba(254,128,101, 0.5);
	  	border-radius: 52upx;
	  	display: flex;
	  	justify-content: center;
	  	align-items: center;
	  	margin: 0 auto;
	  	font-size: 40upx;
	  	font-weight: 400;
	  	color: rgba(255, 255, 255, 1);
  }
</style>
