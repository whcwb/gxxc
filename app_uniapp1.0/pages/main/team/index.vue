<template>
		<view class="box_col" style="width: 100%;">
			<view class="box_row">
				<view class="box_row_100">
					<mSearch :show='false' :mode="2" @search="search($event,2)" placeholder='请输入姓名'></mSearch>
				</view>
				<view  style="width: 80px;">
					 <sl-filter :themeColor="themeColor" :menuList="menuList" @result="result"></sl-filter>
				</view>
			</view>
			<view class="box_col_100">
				<view class="teamListBox">
					<view class="itemSty box_row" v-for="(it,index) in studentList" :key="index">
						<!-- <view class="avaSty"> -->
							<img src="./file/img/jkzn.png" alt="">
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
			<view class="loading-text" style="margin-bottom: 120upx;">
			{{loadingType === 0 ? contentText.contentdown : (loadingType === 1 ? contentText.contentrefresh : contentText.contentnomore)}}
			</view>
		</view>
</template>

<script>
	import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	import slFilter from '@/components/songlazy-sl-filter/sl-filter/sl-filter.vue';
	var  _self,
	page = 1;
	export default{
		name:"",
		 components: {
			mSearch,uniLoadMore,slFilter
		},
		data(){
			return {
				// more = contentdown: "上拉显示更多",
            // loading =contentrefresh: "正在加载...",
            // nomore = contentnomore: "没有更多数据了"
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
							'value': 'val_1_1'
						},
						{
							'title': '未交费',
							'value': 'val_1_2'
						}
					]
					}
				],
				studentJson:[],
				studentList:[],
				loadingType: 0,
				pageNum:8,
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
		onReachBottom() {
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
		 
		},

		onShow(){
			// console.log('onShow')
		},
		created() {
			this.getPagerList(['','','','',1]);
		},
		methods:{
			getPagerList(Arr){
				this.$http.post(this.apis.TEAMMESS,{yhxm:Arr[0],grade:Arr[1],yhlx:Arr[2],sfjf:Arr[3],pageNum:Arr[4],pageSize:10}).then((res)=>{
					if(res.code == 200){
						this.studentList = res.page.list
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
			},
			 result(val) {
                console.log('filter_result:' + JSON.stringify(val));
                this.filterResult = JSON.stringify(val, null, 2)
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
