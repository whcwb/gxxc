<template>
	<view class="v-tab">
		<view :class="index===selectIndex?'action':'noSel'" v-for="(item,index) in data" :key="index" @click="selectIndex=index,$emit('select',index)">
			{{item.name}}
		</view>
	</view>
</template>

<script>
	export default {
		name:"vTab",
		data() {
			return {
				selectIndex:0,
			};
		},
		props:['data'],
		
	}
</script>

<style lang="scss">
	
	.v-tab{
		height: 136upx;
		line-height: 136upx;
		background: white;
		box-shadow: 0 2upx 5upx $uni-bg-color-grey;
		display: flex;
		justify-content: space-around;
		align-items: stretch;
		font-size: $uni-font-size-base;
		font-size:36upx;
		font-weight:500;
		color:rgba(60,130,253,1);
		
		.action {
			border-bottom: 4upx solid rgba(60,130,253,1);
			font-family:PingFangSC-Medium;
			font-size:36upx;
		}
		
		.noSel{
			font-size:36upx;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(102,102,102,1);
		}
	}
	
	
</style>
