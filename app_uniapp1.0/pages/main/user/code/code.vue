<template>
	<view style="font-family:PingFangSC-Regular;width: 100%;">
		<view class="bg">
			<view class="personBG">
				<img :src="user.yhTx" style="border-radius: 50%;width: 96upx;height: 96upx;">
				<view style="margin: 15upx 0 5upx;font-size:36upx;">{{user.yhXm}}</view>
				<text style="font-size:36upx;">{{user.yhZh}}</text>
			</view>
			<view style="height: 75%;">
				<img :src="user.yhZsyqmImg" class="codeImg" id='imgs' :style="{height:height}">
				<text style="display: block;font-size:36upx;font-weight:400;color:rgba(51,51,51,1);">邀请码：{{user.yhZsyqm}}</text>
				<view class="copybtn" v-clipboard:copy="user.yhZsyqm" v-clipboard:success="(type) => onCopyResult('success')"
				 v-clipboard:error="(type) => onCopyResult('error')">
					点此复制邀请码
				</view>
				<view class="sharebtn">
					点击右上角分享给朋友
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
				height:''
			}
		},
		watch:{

		},
		methods: {
			onCopyResult(type) {
				if (type === 'success') {
					uni.showToast({
						title: '复制成功',
						duration: 2000
					});
				} else {
					uni.showToast({
						title: '复制失败,请手动长按复制',
						duration: 2000,
						icon:'none'
					});
				}
			}
		},
		onLoad() {			
			this.$http.post(this.apis.USERMESS).then(res => {
				if (res.code == 200) {
					this.user = res.result
				} else {

				}
			}).catch(err => {})
		},
		mounted() {
			//动态赋值二维码图片高度
			var v=this
			const query = uni.createSelectorQuery().in(v);
			query.select('#imgs').boundingClientRect();
			query.exec(res => {
					v.height=res[0].width+'px'
			});
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
</style>
