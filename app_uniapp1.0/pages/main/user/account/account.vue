<template>
	<view style="width: 100%;">
		<view class="item" v-for="(item,index) in itemList" :style="{borderBottom:index==itemList.length-1?'none':'2upx solid #DFE7EE'}">
			<view class="left">
				<img src="/static/img/money.png" style="width: 90upx;height: 90upx;">
				<view style="display: inline-block;margin-left: 28upx;">
					<view>{{item.name}}</view>
					<view>{{item.date}}</view>
				</view>
			</view>
			<view class="">
				<view style="display: inline-block;">
					<view style="text-align: right;">{{item.add?'+':'-'}}{{item.money}}</view>
					<view style="color:rgba(86,150,51,1);">{{item.state=='finish'?'处理成功':'未处理'}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				itemList:[
					{
						name:'兑换',
						date:'2019.08.05',
						add:false,						
						money:'500',
						state:'finish'
					},
					{
						name:'兑换',
						date:'2019.08.05',
						add:false,						
						money:'500',
						state:'finish'
					},
					{
						name:'兑换',
						date:'2019.08.05',
						add:false,						
						money:'500',
						state:'finish'
					}
				]
			}
		},
		methods: {

		}
	}
</script>

<style>
	.item {
		height: 150upx;
		width: 628upx;
		margin: 0 auto;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 32upx;
		font-family: PingFangSC-Regular;
		font-weight: 400;
		color: rgba(51, 51, 51, 1);
	}

	.left {
		display: flex;
		align-items: center;
	}
</style>
