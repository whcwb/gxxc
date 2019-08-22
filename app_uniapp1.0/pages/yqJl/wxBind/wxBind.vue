<template>
	<view style="font-family:PingFangSC-Regular;width: 100%;">
		<view>
			<view>
				<text class="tit">您的绑定码为:</text>
				<text style="display: block;font-size:48upx;font-weight:700;color:rgba(51,51,51,1);text-align: center;">{{user.yhZsyqm}}</text>
				<view v-if="!APP" class="copybtn" v-clipboard:copy="user.yhZsyqm" v-clipboard:success="(type) => onCopyResult('success')"
				 v-clipboard:error="(type) => onCopyResult('error')">
					点此复制绑定码
				</view>
				<view class="copybtn" v-else @click="appCopyClick">
					点此复制绑定码
				</view>
			</view>
			<view class="tit">
				<view class="boby" >
					<b class='b'>1</b> <text>复制绑定码并打开微信,点击右上角搜索</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss.png">
				</view>
				<view class="boby" >
					<b class='b'>2</b><text>搜索公众号"吉驾无忧"</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss2.png">
				</view>
				<view class="boby" >
					<b class='b'>3</b><text>点击关注"吉驾无忧"公众号</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss3.png">
				</view>
				<view class="boby" >
					<b class='b'>4</b><text>点击进入平台->微信绑定</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss4.png">
				</view>
				<view class="boby" >
					<b class='b'>5</b><text>点击您收到的绑定地址消息</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss5.png">
				</view>
				<view class="boby" >
					<b class='b'>6</b><text>按提示输入绑定码</text>
				</view>
				<view class="img">
					<img style="height: 150px;width: 300px;" src="../file/img/ss6.jpg">
				</view>
				<view class="boby" >
					<b class='b'>7</b><text>绑定完成</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				height:'',
				APP:false
			}
		},
		watch:{

		},
		methods: {
			appCopyClick(){
				uni.setClipboardData({
				    data:this.user.yhZsyqm,
				    success: function () {
				        uni.showToast({
				        	title: '复制成功',
				        	duration: 2000,
				        	icon:'none'
				        });
				    },
					fail:function(){
						uni.showToast({
							title: '复制失败',
							duration: 2000,
							icon:'none'
						});
					}
				});
			},
			onCopyResult(type) {
				// #ifdef APP-PLUS
				return
				// #endif
				
				if (type === 'success') {
					uni.showToast({
						title: '复制成功',
						duration: 2000
					});
				} else {
					uni.showToast({
						title: '复制失败',
						duration: 2000,
						icon:'none'
					});
				}
			},
		},
		onLoad() {
			// #ifdef APP-PLUS
			this.APP=true
			// #endif
			// #ifdef H5
			this.APP=false
			// #endif
			
			
			this.$http.post(this.apis.USERMESS).then(res => {
				if (res.code == 200) {
					this.user = res.result
				} else {

				}
			}).catch(err => {})
		},
		mounted() {
			// //动态赋值二维码图片高度
			// var v=this
			// const query = uni.createSelectorQuery().in(v);
			// query.select('#imgs').boundingClientRect();
			// query.exec(res => {
			// 		v.height=res[0].width+'px'
			// });
			// 
			// 
		}
	}
</script>

<style>
	.bg {
		width: 678upx;
		height: 85%;
		/* min-height: 886rpx; */
		background: rgba(255, 255, 255, 1);
		box-shadow: 4upx 6upx 16upx 0upx rgba(174, 184, 224, 0.5);
		border-radius: 16upx;
		margin: 30upx auto;
		text-align: center;
	}
	
	.codeImg{
		width: 50%
	}

	.personBG {
		width: 678upx;
		height: 25%;
		background: linear-gradient(180deg, rgba(59, 147, 253, 1) 0%, rgba(55, 84, 252, 1) 100%);
		border-radius: 16upx 16upx 0upx 0upx;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}
    .img{
		height: 150px;
		width: 100%;
		text-align:center;
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
	}
	.tit{
		padding: 0 46rpx;
		padding-top: 36rpx;
		font-size:32upx;
		font-family:PingFangSC-Semibold;
		font-weight:600;
		color:rgba(51,51,51,1);
		line-height:28px;
	}
	.boby{
		margin-top: 10rpx;
		font-size:28upx;
		font-family:PingFangSC-Regular;
		font-weight:400;
		color:rgba(51,51,51,1);
		line-height:50rpx;
	}
	.b{
		margin-right: 10rpx;
	    border-radius: 50%;
	    background-color: #5090F1;
	    color: #fff;
	    display:inline-block;
	    text-align: center;
	    line-height: 20px;
	    height: 20px;
	    width: 20px;
	}
</style>
