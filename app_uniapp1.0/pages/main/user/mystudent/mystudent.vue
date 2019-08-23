<template>
	<view class="box_col teamPagerBox">
		<view class="seacherBox">
			<mSearch :show='false' :mode="2" @search="search($event)" placeholder='请输入姓名'></mSearch>
		</view>
		<view  class="box_col_100 noData" v-if="newsList.length == 0" style="text-align: center;">
			<image src="../../../static/img/zanwu.png" mode="scaleToFill"></image>
			<view style="font-size: 22px;font-weight: 600;color: #70C1EE;">暂无学员信息</view>
		</view>
		<view v-else class=" teamListBox">
				<view class="itemSty box_row" v-for="(it,index) in newsList" :key="index" @click="toXymess(it)">
					<view style="display: flex;flex-direction:row;align-items: center;">
						<view style="margin-right: 15upx;background-color: #007AFF;color: #FFFFFF;text-align: center;vertical-align: middle;height:40rpx ;width: 40rpx; border-radius: 25px;">
							<b>{{index+1}}</b>
						</view>
						<img :src="'http://www.520xclm.com:8001/'+it.yhTx">
						<view class="name">
							{{it.yhXm}}
						</view>
					</view>	
					
					<view style="display: flex;flex-direction:row;align-items: center;">	
						<view style="margin-right: 15upx;" @click.stop="phone(it.yhSjhm)">
							<uni-icon type='phone' color='#007AFF' size="30"></uni-icon>
						</view>
					    <view v-if="it.yhDqzt == '0'" class="butTyp slmoney">
					    	受理
					    </view>
					    <view v-if="it.yhDqzt == '1'" class="butTyp slmoney">
					    	科一
					    </view>
					    <view v-if="it.yhDqzt == '2'" class="butTyp slmoney">
					    	科二
					    </view>
					    <view v-if="it.yhDqzt == '3'" class="butTyp slmoney">
					    	科三
					    </view>
					    <view v-if="it.yhDqzt == '4'" class="butTyp slmoney">
					    	科四
					    </view>
						<!-- <view v-if="it.userDetail.yhLx == '1' && it.userDetail.yhZt =='1'" class="butTyp onMoney">
							A类
						</view>
						<view v-if="it.userDetail.yhLx == '3'&& it.userDetail.yhZt =='1'" class="butTyp offMoney">
							B类
						</view>
						<view v-if="it.userDetail.yhZt !='1'" class="butTyp offMoney">
							未认证
						</view> -->
					</view>
				</view>
		</view>
		<view class="loadingbox" style="text-align: center;">
			{{contentText[loadingType]}}
		</view>
				<!-- <uni-load-more :loadingType="loadingType" :contentText="contentText"></uni-load-more> -->
	</view>
</template>

<script>
	import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	import slFilter from '@/components/songlazy-sl-filter/sl-filter/sl-filter.vue';
	import uniIcon from "@/components/uni-icon/uni-icon.vue";
	var _self,
		page = 1,
		timer = null;
	export default {
		name: "",
		components: {
			mSearch,
			uniLoadMore,
			slFilter,
			uniIcon
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
				loadingType: 0, //定义加载方式 0---contentdown  1---contentrefresh 2---contentnomore
				contentText: {
					'0': "上拉显示更多",
					'1': "正在加载...",
					'2': "没有更多数据了",
				},
				params:{
					yhlx:"",
					xm: "",
					pageNum: 1,
					pageSize: 10
				},
				nextPage:null
			}
		},
		onPullDownRefresh: function() {
			//下拉刷新的时候请求一次数据
			this.newsList = []
			this.params.pageNum = 1
			this.params.yhxm = ""
			this.params.yhlx = ""
			this.getPagerList();
			setInterval(()=>{
				uni.stopPullDownRefresh();
			},2000)
		},
		onReachBottom() {
			//触底的时候请求数据，即为上拉加载更多
			//为了更加清楚的看到效果，添加了定时器
			if(this.nextPage > 0){
				this.loadingType = 1
				setTimeout(()=>{
					this.params.pageNum = this.nextPage
					this.getPagerList();
				},1500)
			}
		},
		onShow() {
			this.getPagerList();
		},
		created() {

		},
		methods: {
			phone(id) {
				uni.makePhoneCall({
					phoneNumber: id //仅为示例
				});
			},
			toXymess(item){//查看学员详情  只能看A类学员
			    console.log('item',item);
					uni.setStorage({
						key:'xymess',
						data:item
					})
					uni.navigateTo({
						url:"../../../xymess/xymess"
					})
			},
			setYhlx(typ){
				this.newsList = []
				this.params.yhlx = typ
				this.params.pageNum = 1
				this.getPagerList();
			},
			deteleObject(obj) { //数组去重复
			    var uniques = [];
			    var stringify = {};
			    for (var i = 0; i < obj.length; i++) {
			        var keys = Object.keys(obj[i]);
			        keys.sort(function(a, b) {
			            return (Number(a) - Number(b));
			        });
			        var str = '';
			        for (var j = 0; j < keys.length; j++) {
			            str += JSON.stringify(keys[j]);
			            str += JSON.stringify(obj[i][keys[j]]);
			        }
			        if (!stringify.hasOwnProperty(str)) {
			            uniques.push(obj[i]);
			            stringify[str] = true;
			        }
			    }
			    uniques = uniques;
			    return uniques;
			},
			getPagerList() {
				this.$http.get('/app/jl/myStudent', this.params).then((res) => {
					if (res.code == 200) {
						if(this.newsList.length == 0){
							this.newsList = res.page.list
						}else{
							this.newsList = this.newsList.concat(res.page.list)
							this.newsList = this.deteleObject(this.newsList)
						}
						this.nextPage = res.page.nextPage
						this.loadingType = 1
						if(res.page.nextPage == 0){
							this.loadingType = 2
						}
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none',
							duration: 1500
						});
					}
				})
			},
			search(e) {
				this.params.yhxm = e
				this.newsList = []
				this.params.pageNum = 1
				this.getPagerList()
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
		// padding-top: 168rpx;
		.seacherBox{
			// position: fixed;
			// left: 0;
			// top:0;
			// right: 0;
			// margin-top:88rpx;
			.studentBox{
				background-color: #ffffff;
				text-align: center;
				.item{
					font-size: 36rpx;
					border-bottom: #ededed 6rpx solid;
					padding: 18rpx 0;
				}
				.studentAll{
					.item;
					border-right: #ededed 2rpx solid;
				}
				.studentA{
					.item;
					border-left: #ededed 2rpx solid;
					border-right: #ededed 2rpx solid;
				}
				.studentB{
					.item;
					border-left: #ededed 2rpx solid;
				}
				.setItem{
					color: rgb(59, 147, 253);
					border-bottom: rgb(59, 147, 253) 6rpx solid;
				}
			}
		}
	}
	.teamListBox {
		background-color: #ffffff;
		flex: 1;
		overflow-y: auto;
		padding: 0 36rpx;

		.itemSty {
			border-bottom: solid 2rpx #DFE7EE;
			padding: 30rpx 0;
			display: flex;
			justify-content: space-between;
			align-content: center;

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
				border-radius: 20upx
			}
            .slmoney{
            	background: #5CACEE;
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

	.loadingbox{
		text-align: center;
		padding: 16rpx;
		font-size: 36rpx;
	}
	
</style>
