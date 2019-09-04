<template>
	<view>
		<view class="bg">
			<view style="display: flex;align-content: center;margin: 48upx 0 0 64upx;">
				<img :src="imgUrl+zyMess.jlImg" style="border-radius: 50%;width: 132upx;height: 132upx;" @tap='tomymess()'>
				<view class="personMessage">
					<view style="margin-top: 30upx;font-size:20px;
						margin-left: 60rpx;
						font-family:PingFangSC-Regular;
						font-weight:400;
						color:rgba(255,255,255,1);
						line-height:28px;" >{{zyMess.yhXm}}</view>
				</view>
			</view>
		</view>
		<view class="btn">
			<view class="item" :style="{borderBottom:index===5?'none':'2upx solid #DFE7EE'}" 
			v-for="(item,index) in btnList" :key=index>
				<view style="display: flex;align-items: center;">
					<text>{{item.title}}</text>
				</view>
				{{zyMess[item.val]}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name:'JLmess',
		computed:{
			zyMess(){
				return this.$store.state.zyMess
			}
		},
		data(){
			return{
				imgUrl:'http://www.520xclm.com:8001/',
				btnList: [
					{
						title: '手机号码',
						val: 'yhSjhm'
					},
					{
						title: '教练地址',
						val: 'jlZz'
					},
				]
			}
		}
	}
</script>

<style lang="less">
	.bg {
		padding-top: 1upx;
		width: 750upx;
		height: 338upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
	}
	.btn {
		width: 678upx;
		height: 750upx;
		background: rgba(255, 255, 255, 1);
		box-shadow: 4upx 4upx 16upx 0upx rgba(179, 190, 233, 0.5);
		border-radius: 16upx;
		margin: -106upx auto 0;
	}
	
	.item {
		width: 628upx;
		height: 120upx;
		margin: 0 auto;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
</style>
