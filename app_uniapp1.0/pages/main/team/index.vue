<template>
		<view class="box_col" style="width: 100%">
			<view class="box_col_100">
				<view class="box_row">
					<view class="box_row_100">
						<mSearch :show='false' :mode="2" @search="search($event,2)" placeholder='请输入姓名'></mSearch>
					</view>
					<view  style="width: 80px;">
						 <sl-filter :themeColor="themeColor" :menuList="menuList" @result="result"></sl-filter>
					</view>
				</view>
			</view>
			<view class="box_col_100">
				<view class="teamListBox">
					<view class="itemSty box_row" v-for="(it,index) in newsList" :key="index">
						<!-- <view class="avaSty"> -->
							<img :src="it.userDetail.yhTx" alt="">
						<!-- </view> -->
						<view class="messBox">
							<view class="box_row colCenter">
								<view class="name">
									{{it.yhXm}}
								</view>
								<view v-if="it.userDetail.ddSfjx == '1'" class="butTyp onMoney">
									已缴费
								</view>
								<view v-else class="butTyp offMoney">
									未缴费
								</view>
							</view>
							<view class="phoneSty">
								{{it.yhSjhm}}
							</view>
						</view>
				       </view>
					</view>
			</view>
			<uni-load-more  :loadingType="loadingType" :contentText="contentText" ></uni-load-more>
		</view>
</template>

<script>
	import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	import slFilter from '@/components/songlazy-sl-filter/sl-filter/sl-filter.vue';
	var _self,
	page = 1,
	timer = null;
	export default{
		name:"",
		 components: {
			mSearch,uniLoadMore,slFilter
		},
		data(){
			return {
				val0: '',
				themeColor: '#3B93FD',
				independence:true,
                filterResult: '',
                menuList: [
					{
						'title':'筛选',
						'isMutiple': false,
						'key': 'key_1',
						'isSort': true,
						'detailList': [
						{
							'title': '已缴费',
							'value': 1
						},
						{
							'title': '未交费',
							'value': 0
						}
					]
					}
				],
				newsList: [],
				loadingText: '加载中...',
				loadingType: 0,//定义加载方式 0---contentdown  1---contentrefresh 2---contentnomore
				contentText: {
					contentdown:'上拉显示更多',
					contentrefresh: '正在加载...',
					contentnomore: '没有更多数据了'
				}
			}
		},
		onLoad: function() {
        _self = this;
				//页面一加载时请求一次数据
        this.getnewsList();
		},
		onPullDownRefresh: function() {
					//下拉刷新的时候请求一次数据
			this.getnewsList();
		},
		onReachBottom: function() {
				//触底的时候请求数据，即为上拉加载更多
				//为了更加清楚的看到效果，添加了定时器
			if (timer != null) {
				clearTimeout(timer);
			}
			timer = setTimeout(function() {
				_self.getmorenews();
			}, 1000);
					
					// 正常应为:
					// _self.getmorenews();
		},

		onShow(){
			this.getPagerList(['','','','',1]);
			// console.log('onShow')
		},
		created() {
			
		},
		methods:{
			getmorenews: function() {
				if (_self.loadingType !== 0) {//loadingType!=0;直接返回
					return false;
				}
				_self.loadingType = 1;
				uni.showNavigationBarLoading();//显示加载动画
				this.$http.post(this.apis.TEAMMESS,{yhxm:'',grade:'',yhlx:'',sfjf:'',pageNum:page,pageSize:8}).then((res)=>{
					if(res.code == 200){
						this.newsList = res.page.list
						page++;//得到数据之后page+1
						_self.newsList = res.data.split('');
						uni.hideNavigationBarLoading();
						uni.stopPullDownRefresh();//得到数据后停止下拉刷新
					}else{
						uni.showToast({
							title:res.message,
							icon:'none',
							duration: 1500
						});
					}
				})
			},
			getnewsList: function(Arr) {//第一次回去数据
				page = 1;
				this.loadingType = 0;
				uni.showNavigationBarLoading();
				    
					this.$http.post(this.apis.TEAMMESS,{yhxm:'',grade:'',yhlx:'',sfjf:'',pageNum:page,pageSize:8}).then((res)=>{
						if(res.code == 200){
							this.newsList = res.page.list
							page++;//得到数据之后page+1
							_self.newsList = res.data.split('');
							uni.hideNavigationBarLoading();
							uni.stopPullDownRefresh();//得到数据后停止下拉刷新
						}else{
							uni.showToast({
								title:res.message,
								icon:'none',
								duration: 1500
							});
						}
					})
			},

			getPagerList(Arr){
				this.$http.post(this.apis.TEAMMESS,{yhxm:Arr[0],grade:Arr[1],yhlx:Arr[2],sfjf:Arr[3],pageNum:Arr[4],pageSize:8}).then((res)=>{
					if(res.code == 200){
						this.newsList = res.page.list
						
					}else{
						uni.showToast({
							title:res.message,
							icon:'none',
							duration: 1500
						});
					}
				})
			},
			search(e, val) {
				console.log(e, val);
				this['val'+val] = e;
				this.getPagerList([e,'','','',1])
			},
			 result(val) {
                this.filterResult = JSON.stringify(val, null, 2)
				this.getPagerList(['','','',val.key_1,1])
            }
		}
	}
</script>

<style lang="less">
	.teamListBox {
		background-color: #ffffff;
		flex: 1;
		overflow-y: auto;
		padding: 0 36rpx;
	
		.itemSty {
			border-bottom: solid 2rpx #DFE7EE;
			padding: 30rpx 0;
	
			// .avaSty {
				img {
					margin-right: 30rpx;
					width: 96rpx;
					height: 96rpx;
					border-radius: 100%;
				}
			// }
	
			.name {
				font-size: 36rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(51, 51, 51, 1);
				margin-right: 46rpx;
			}
	
			.butTyp {
				height: 38rpx;
				width: 88rpx;
				text-align: center;
				border-radius: 2px;
				font-size: 22rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(255, 255, 255, 1);
				line-height: 38rpx;
				margin-right: 14rpx;
			}
	
			.onMoney {
				background: rgba(251, 164, 19, 1);
	
			}
	
			.offMoney {
				background-color: #B4B4B4;
			}
	
			.FuserTyp {
				background: rgba(47, 182, 170, 1);
			}
	
			.SuserTyp {
				background:rgba(100,146,244,1);
			}
			
			.phoneSty{
				font-size:28rpx;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(102,102,102,1);
			}
		}
	}
	.loading-text{
		text-align: center;
		font-size: 24rpx;
		margin-top: 24rpx
	}
	// @import "../../../common/iview.css";
	// .ivu-btn-success {
	// 	color: #fff;
	// 	background-color: #19be6b;
	// 	border-color: #19be6b
	// }
</style>
