<template>
	<view class="box_col teamPagerBox">
		<view class="seacherBox">
			<mSearch :show='false' :mode="2" @search="search($event,2)" placeholder='请输入姓名'></mSearch>
		</view>
		<view  class="box_col_100 noData" v-if="newsList.length == 0" style="text-align: center;">
			<image src="../../../static/img/zanwu.png" mode="scaleToFill"></image>
			<view style="font-size: 22px;font-weight: 600;color: #70C1EE;">暂无团队成员</view>
		</view>
		<view v-else class=" teamListBox">
				<view class="itemSty box_row" v-for="(it,index) in newsList" :key="index">
					<img :src="it.userDetail.yhTx" alt="">
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
				<uni-load-more :loadingType="loadingType" :contentText="contentText"></uni-load-more>
	</view>
</template>

<script>
	import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	import slFilter from '@/components/songlazy-sl-filter/sl-filter/sl-filter.vue';
	var _self,
		page = 1,
		timer = null;
	export default {
		name: "",
		components: {
			mSearch,
			uniLoadMore,
			slFilter
		},
		data() {
			return {
				val0: '',
				themeColor: '#3B93FD',
				independence: true,
				filterResult: '',
				menuList: [{
					'title': '筛选',
					'isMutiple': false,
					'key': 'key_1',
					'isSort': true,
					'detailList': [{
							'title': '已缴费',
							'value': 1
						},
						{
							'title': '未交费',
							'value': 0
						}
					]
				}],
				newsList: [],
				loadingText: '加载中...',
				// 上啦加载
				loadingType: 1, //定义加载方式 0---contentdown  1---contentrefresh 2---contentnomore
				contentText: {
					contentdown: "上拉显示更多",
					contentrefresh: "正在加载...",
					contentnomore: "没有更多数据了"},
				params:{
					yhxm: "",
					pageNum: 1,
					pageSize: 10
				},
				nextPage:null
			}
		},
		watch:{
			"newsList":function(n,o){
				console.log('1111',n)
				console.log('2222',this.nextPage)
			}
		},
		onPullDownRefresh: function() {
			//下拉刷新的时候请求一次数据
			this.newsList = []
			this.getnewsList();
		},
		onReachBottom() {
			//触底的时候请求数据，即为上拉加载更多
			//为了更加清楚的看到效果，添加了定时器
			if(this.nextPage > 0){
				this.params.pageNum = this.nextPage
				this.getPagerList();
			}
			
			// if (timer != null) {
			// 	clearTimeout(timer);
			// }
			// timer = setTimeout(function() {
				// _self.getmorenews();
			// }, 3000);

			// 正常应为:
			// _self.getmorenews();
		},

		onShow() {
			this.getPagerList();
		},
		created() {

		},
		methods: {
			getmorenews: function() {
				var v = this
				if (_self.loadingType !== 0) { //loadingType!=0;直接返回
					return false;
				}
				_self.loadingType = 1;
				uni.showNavigationBarLoading(); //显示加载动画
				this.$http.post(this.apis.TEAMMESS, {
					yhxm: '',
					grade: '',
					yhlx: '',
					sfjf: '',
					pageNum: page,
					pageSize: 8
				}).then((res) => {
					if (res.code == 200) {
						if (res.page.list == null) {
							_self.loadingType = 2;
							uni.hideNavigationBarLoading(); //关闭加载动画
							return;
						}
						this.newsList = res.page.list
						page++; //得到数据之后page+1
						v.newsList.concat(res.page.list)
						console.log('v.newsList', v.newsList);
						_self.loadingType = 2;
						uni.hideNavigationBarLoading();
						uni.stopPullDownRefresh(); //得到数据后停止下拉刷新
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none',
							duration: 1500
						});
					}
				})
			},
			getnewsList: function(Arr) { //第一次回去数据
				page = 1;
				this.loadingType = 0;
				uni.showNavigationBarLoading();

				this.$http.post(this.apis.TEAMMESS, {
					yhxm: '',
					grade: '',
					yhlx: '',
					sfjf: '',
					pageNum: page,
					pageSize: 8
				}).then((res) => {
					if (res.code == 200) {
						this.newsList = res.page.list
						page++; //得到数据之后page+1
						// _self.newsList = res.page.list.split('--hcSplitor--');
						uni.hideNavigationBarLoading();
						uni.stopPullDownRefresh(); //得到数据后停止下拉刷新
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none',
							duration: 1500
						});
					}
				})
			},

			getPagerList() {
				this.$http.post(this.apis.TEAMMESS, this.params).then((res) => {
					if (res.code == 200) {
						if(this.newsList.length == 0){
							this.newsList = res.page.list
						}else{
							this.newsList = this.newsList.concat(res.page.list)
						}
						this.nextPage = res.page.nextPage
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none',
							duration: 1500
						});
					}
				})
			},
			search(e, val) {
				console.log(e, val);
				this['val' + val] = e;
				this.getPagerList([e, '', '', '', 1])
			},
			result(val) {
				this.filterResult = JSON.stringify(val, null, 2)
				this.getPagerList(['', '', '', val.key_1, 1])
			}
		}
	}
</script>

<style lang="less">
	uni-page-body{
		// height: 100%!important;
		// overflow-y: auto;
	}
	.teamPagerBox{
		background:rgba(245,246,249,1);
		width: 100%;
		padding-top: 88rpx;
		.seacherBox{
			position: fixed;
			left: 0;
			top:0;
			right: 0;
			margin-top:88rpx;
		}
	}
	.teamListBox {
		background-color: #ffffff;
		// flex: 1;
		// overflow-y: auto;
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
				width: 112rpx;
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
				background: rgba(100, 146, 244, 1);
			}

			.phoneSty {
				font-size: 28rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(102, 102, 102, 1);
			}
		}
	}

	.loading-text {
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
