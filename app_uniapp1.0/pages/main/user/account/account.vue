<template>
	<view style="width: 100%;">
		<view class="item" v-for="(item,index) in itemList" :style="{borderBottom:index==itemList.length-1?'none':'2upx solid #DFE7EE'}">
			<view class="left">
				<img :src="item.zjFs=='1'?'/static/img/my/zd1.png' : 'static/img/my/zd2.png'" style="width: 90upx;height: 90upx;">
				<view style="display: inline-block;margin-left: 28upx;">
					<view>{{mx[item.mxlx]}}</view>
					<view>{{item.cjsj}}</view>
				</view>
			</view>
			<view>
				<view style="display: inline-block;">
					<view style="text-align: right;">{{item.mxlx=='2'?'+':'-'}}{{item.zjJe/100}}</view>
					<view style="color:rgba(86,150,51,1);"> {{item.zjZt | zjZt}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import mixin from '@/common/mixin.js'
	export default {
		data() {
			return {
				itemList:[],
				mx:['','付款','分佣','消费','提现']
			}
		},
		mixins:[mixin],
		methods: {
			getMess(){
				var v=this
				this.$http.post(this.apis.ZDLIST,{pageSize:10,pageNum:1}).then(res=>{
				  if(res.code==200){
					  v.itemList=res.page.list
				  }
				  
				})
			}
		},
		onLoad() {
			this.getMess()
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
