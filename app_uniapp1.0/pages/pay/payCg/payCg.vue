<template>
	<view style="font-family:PingFangSC-Regular;width: 100%;background-color: rgba(245,246,249,1);">
		<view class="done">
			<view style="margin-top:204upx;">
				<img src="/static/img/my/success.png" style="width: 240upx;height: 240upx;bottom: 78upx;">
			</view>
			<view style="margin-top: 78upx;font-size:40upx;font-weight:400;color:#3778FF;">支付成功</view>
		</view>
		<view class="btn" @tap="upload">
			去首页
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			upload(){
				uni.switchTab({
					url:"../../main/p_index/main"
				})
			}
		}
	}
</script>

<style>
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
		height: 950upx;
		background: rgba(255, 255, 255, 1);
		margin-bottom: 84upx;
		text-align: center;
		padding-top: 1upx;
	}
</style>
