<template>
	<view>
		<view class="bg">
			<img src="/static/index/1.png" style="width: 132upx;height: 132upx;margin: 36upx 0 0 64upx;">	
			<view style="font-size:28upx;font-family:PingFangSC-Regular;font-weight:400;color:rgba(255,255,255,1);float: right;margin-top: 66upx;margin-right: 344upx;">
				<view style="margin-bottom: 10upx;">李文超</view>
				<view>15496854152</view>
			</view>			
		</view>
		<view class="btn">
			<view class="item" v-for="item in btnList">
				<view style="display: flex;	align-content: center;">
					<img :src="item.src" style="margin-right:12upx;width: 48upx;height: 48upx;">
					<text>{{item.text}}</text>
				</view>
				<text style="color: #DFE7EE;">></text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				btnList:[
					{
						src:'/static/my/my/3.png',
						text:'实名认证'
					},
					{
						src:'/static/my/my/1.png',
						text:'邀请奖励',
					},
					{
						src:'/static/my/my/2.png',
						text:'我的账单',
					},
					{
						src:'/static/my/my/4.png',
						text:'我的团队'
					}
				]
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.bg{
	width:750upx;
	height:338upx;
	background:linear-gradient(132deg,rgba(59,147,253,1) 0%,rgba(60,128,253,1) 41%,rgba(55,84,252,1) 100%);
}

.btn{
	width:678upx;
	height:580upx;
	background:rgba(255,255,255,1);
	box-shadow:4upx 4upx 16upx 0upx rgba(179,190,233,0.5);
	border-radius:16upx;
	margin: -106upx auto 0;
}

.item{
	width: 628upx;
	height: 142upx;
	border-bottom: 2upx solid #DFE7EE;
	margin: 0 auto;
	display: flex;
	align-items: center;
	justify-content: space-between;
}
</style>
