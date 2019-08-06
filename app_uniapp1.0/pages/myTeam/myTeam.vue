<template>
		<view class="box_col" style="width: 100%;">
			<view class="box_row">
				<view class="box_row_100">
					<mSearch :mode="2" button="inside" @search="search($event,2)" placeholder='请输入姓名'></mSearch>
				</view>
				<view  style="width: 80px;">
					 <sl-filter :themeColor="themeColor" :menuList="menuList" @result="result"></sl-filter>
				</view>
			</view>
			<view class="box_col_100">
				<view class="teamListBox">
					<view class="itemSty box_row" v-for="(it,index) in 10" :key="index">
						<!-- <view class="avaSty"> -->
							<img src="./file/img/jkzn.png" alt="">
						<!-- </view> -->
						<view class="messBox">
							<view class="box_row colCenter">
								<view class="name">
									李 文
								</view>
								<view class="butTyp onMoney">
									已缴费
								</view>
								<view class="butTyp offMoney">
									未缴费
								</view>
								<!-- <view class="butTyp FuserTyp">
									直介
								</view>
								<view class="butTyp SuserTyp">
									转介
								</view> -->
							</view>
							<view class="phoneSty">
								13112345678
							</view>
						</view>
				       </view>
					</view>
			</view>
		</view>
</template>

<script>
	import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	import slFilter from '@/components/songlazy-sl-filter/sl-filter/sl-filter.vue';
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
							'value': 'val_1_1'
						},
						{
							'title': '未交费',
							'value': 'val_1_2'
						}
					]
					}
				]
			}
		},
		onShow(){
			// console.log('onShow')
		},
		methods:{
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
	// @import "../../../common/iview.css";
	// .ivu-btn-success {
	// 	color: #fff;
	// 	background-color: #19be6b;
	// 	border-color: #19be6b
	// }
</style>
