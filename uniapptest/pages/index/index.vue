<template>
	<view class="content">
		<view class="bg">
			<view class="text">邀好友学车</view>
			<view class="text2">享优惠</view>
			<view class="bottom"></view>
			<image src="/static/index/bg.png" style="position: absolute;top:200upx;right:30upx ;width: 350upx;height: 200upx;"></image>
		</view>
		<view class="whiteBG">
			<view>
				<view class="ljjl">累计奖励</view>
				<view style="margin: 6upx auto;width:52upx;height:4upx;background-color: #BBC7EC;"></view>
				<view class="money">2800元</view>
			</view>
			<image src="/static/index/code.png" style="width: 110upx;height: 110upx;"></image>
			<view>
				<view class="ljjl">邀请人数</view>
				<view style="margin: 6upx auto;width:52upx;height:4upx;background-color: #BBC7EC;"></view>
				<view class="money">20人</view>
			</view>
		</view>
		<view style="font-family:PingFangSC-Regular;display: flex;align-items: center;justify-content: space-around;margin-top: 40upx;width:750upx;height:232upx;background:rgba(254,255,255,1);">
			<view v-for="item in btnList">
				<img :src="item.src" style="width: 116upx;height: 116upx;"> 
				<view>
					{{item.text}} 
				</view>
			</view>
		</view>
		<view style="margin-top: 18upx;background:rgba(254,255,255,1);">
			<view class="studentMessage">学员信息</view>
			<view class="studentItem" v-for="item in studentList">
				<image :src="item.src" style="width: 96upx;height: 96upx;"></image>
				<view style="margin-left: 28upx;display: flex;flex-direction:column;align-items: ;">
					<view style="text-align: left;">{{item.name}} 
					<text class="pay" :style="{backgroundColor:item.pay?'rgba(251,164,19,1)':'rgba(180,180,180,1)'}">{{item.pay?'已缴费':'未缴费'}}</text>
					<text class="pay" :style="{marginLeft:'14upx',backgroundColor:item.straight?'rgba(47,182,170,1)':'rgba(100,146,244,1)'}">
						{{item.straight?'直介':'转介'}}
					</text>
					</view>
					<view style="text-align: left;">{{item.phone}}</view>
				</view>
			</view>
		</view>
		<view class="loading-text" style="margin-bottom: 110upx;">
		{{loadingType === 0 ? contentText.contentdown : (loadingType === 1 ? contentText.contentrefresh : contentText.contentnomore)}}</view>
	</view>
</template>

<script>
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue"
	var  _self,
	page = 1;
	
	export default {
		components: {uniLoadMore},    
		data() {
			return {
				btnList:[
					{
						src:'/static/index/1.png',
						text:'驾考指南'
					},
					{
						src:'/static/index/2.png',
						text:'补考缴费'
					},
					{
						src:'/static/index/3.png',
						text:'陪练服务'
					},
					{
						src:'/static/index/4.png',
						text:'考场分布'
					},
				],
				studentJson:[
					{
						name:'李文超',
						phone:'158769452',
						pay:true,
						straight:true,
						src:'/static/index/1.png'
					},
					{
						name:'岳琴琴',
						phone:'135953252',
						pay:false,
						straight:false,
						src:'/static/index/2.png'
					},
					{
						name:'李彬彬',
						phone:'168232340',
						pay:false,
						straight:false,
						src:'/static/index/3.png'
					},
					{
						name:'李文超',
						phone:'158769452',
						pay:true,
						straight:true,
						src:'/static/index/1.png'
					},
					{
						name:'岳琴琴',
						phone:'135953252',
						pay:false,
						straight:false,
						src:'/static/index/2.png'
					},
					{
						name:'李彬彬',
						phone:'168232340',
						pay:false,
						straight:false,
						src:'/static/index/3.png'
					},
					{
						name:'李文超',
						phone:'158769452',
						pay:true,
						straight:true,
						src:'/static/index/1.png'
					},
					{
						name:'岳琴琴',
						phone:'135953252',
						pay:false,
						straight:false,
						src:'/static/index/2.png'
					},
					{
						name:'李彬彬',
						phone:'168232340',
						pay:false,
						straight:false,
						src:'/static/index/3.png'
					},
					{
						name:'李文超',
						phone:'158769452',
						pay:true,
						straight:true,
						src:'/static/index/1.png'
					},
					{
						name:'岳琴琴',
						phone:'135953252',
						pay:false,
						straight:false,
						src:'/static/index/2.png'
					},
					{
						name:'李彬彬',
						phone:'168232340',
						pay:false,
						straight:false,
						src:'/static/index/3.png'
					}
				],
				studentList:[],
				loadingType: 0,
				pageNum:4,
				contentText: {
					contentdown: "上拉显示更多",
					contentrefresh: "正在加载...",
					contentnomore: "没有更多数据了"
				}
			}
		},
		onLoad: function (options) {
			_self = this;
			this.studentJson.map((val,index,arr)=>{						//模拟获取数据，模拟为一次显示4条
				if(index<this.pageNum)
					this.studentList[this.studentList.length]=val
			})
			console.log(this.studentJson.length)
		},
		// 上拉加载
		onReachBottom: function() {
			var v=this
			// console.log(_self.newsList.length);
			if (_self.loadingType != 0) {//loadingType!=0;直接返回
				return false;
			}
			_self.loadingType = 1;
			//设置一个定时器，能看出加载的效果
			setTimeout(()=>{
				var pageNum1=v.pageNum+4
				v.studentJson.map((val,index,arr)=>{						//模拟获取数据，模拟为一次显示4条
					if(index>=v.pageNum&&index<pageNum1)
						v.studentList.push(val)
				})
				console.log(v.studentList)
				if(v.studentList.length>=v.studentJson.length){
								_self.loadingType = 2;
								return false;
				}
				v.pageNum=pageNum1
				_self.loadingType = 0;
			},2000)
			
			// uni.showNavigationBarLoading();//显示加载动画
			// uni.request({
			// 	url:'../../static/data/news.json?page='+page,
			// 	success: function(res) {
			// 		if (_self.newsList.length==res.data.pages_count) {//没有数据
			// 			_self.loadingType = 2;
			// 			uni.hideNavigationBarLoading();//关闭加载动画
			// 			return false;
			// 		}
			// 		page++;//每触底一次 page +1
			// 		// console.log(page);
			// 		for(var i=_self.newsList.length;i<res.data.pages_count;i++){
			// 			_self.newsList = _self.newsList.concat(res.data.data[i]);//将数据拼接在一起
			// 		}
			// 		_self.loadingType = 0;//将loadingType归0重置
			// 		uni.hideNavigationBarLoading();//关闭加载动画
			// 		
			// 	}
			// });
		},
		methods:{	
			getNewsList: function() {//第一次回去数据
				_self.loadingType = 0;
				
				
				
				// uni.request({
				// 	url: '',
				// 	success: function(res) {
				// 	}
				// });
				
			}

		}
		
	}
</script>

<style>
	.content {
		text-align: center;
		height: 1624upx;
		background-color: #F5F6F9;
	}

	.logo {
		height: 200upx;
		width: 200upx;
		margin-top: 200upx;
	}

	.title {
		font-size: 36upx;
		color: #8f8f94;
	}
	
	.bg{
		padding-top: 2upx;
		width: 100%;
		height: 524upx;
		background:linear-gradient(132deg,rgba(59,147,253,1) 0%,rgba(60,128,253,1) 41%,rgba(55,84,252,1) 100%);
	}
	
	.text{
		margin: 156upx 0 0 40upx;
		width:320upx;
		height:76upx;
		font-size:64upx;
		font-family:FZHZGBJW--GB1-0;
		font-weight:normal;
		color:rgba(255,255,255,1);
		line-height:76upx;
	}
	
	.text2{
		width:192upx;
		height:76upx;
		font-size:64upx;
		font-family:FZHZGBJW--GB1-0;
		font-weight:normal;
		color:rgba(255,255,255,1);
		line-height:76upx;
		margin: 6upx 0 14upx 40upx;
	}
	
	.bottom{
		margin-left: 44upx;
		width:40px;
		height:6px;
		background:rgba(233,197,113,1);
	}
	
	.whiteBG{
		margin: -90upx auto 0;
		width:339px;
		height:109px;
		background:rgba(255,255,255,1);
		box-shadow:2px 2px 8px 0px rgba(179,190,233,0.5);
		border-radius:8px;
		padding-top: 2upx;
		font-family:PingFangSC-Regular;
		display: flex;
		justify-content: space-around;
		align-items: center;
	}
	
	.ljjl{
		width:128upx;
		height:44upx;
		font-size:32upx;
		font-weight:400;
		color:rgba(80,144,241,1);
		line-height:44upx;
	}
	
	.money{
		width:136upx;
		height:54upx;
		font-size:40upx;
		font-weight:400;
		color:rgba(80,144,241,1);
		line-height:56upx;
	}
	
	.studentMessage{
		width:176upx;
		height:116upx;
		font-size:44upx;
		font-family:PingFangSC-Medium;
		font-weight:600;
		color:rgba(51,51,51,1);
		line-height:116upx;
		margin-left: 36upx;
	}
	
	.studentItem{
		height: 156upx;
		width: 678upx;
		border-bottom:2upx solid #DFE7EE ;
		margin: 0 auto;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		font-family:PingFangSC-Regular;
	}
	
	.pay{
		width:76upx;
		height:32upx;
		line-height:32upx;
		border-radius:4upx;
		margin-left: 46upx;
		font-size:20upx;
		font-family:PingFangSC-Regular;
		font-weight:400;
		color:rgba(255,255,255,1);
		padding: 2upx 8upx
		}
		
	
</style>
