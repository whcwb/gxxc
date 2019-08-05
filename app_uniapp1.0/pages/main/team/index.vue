<template>
		<view class="box_col">
			<view class="box_row">
				<view class="box_row_100">
					<mSearch :mode="2" button="inside" @search="search($event,2)"></mSearch>
				</view>
				<view  style="width: 80px;">
					<sl-filter :themeColor="themeColor" :menuList="menuList" @result="result"></sl-filter>
				</view>
			</view>
			<view class="box_col_100">
				<view class="teamListBox">
					<view class="itemSty box_row" v-for="(it,index) in 10" :key="index">
						<!-- <view class="avaSty"> -->
							<img src="./flie/img/jkzn.png" alt="">
						<!-- </view> -->
						<view class="messBox">
							<view class="box_row colCenter">
								<view class="name">
									李文差
								</view>
								<view class="butTyp onMoney">
									已缴费
								</view>
								<view class="butTyp offMoney">
									未缴费
								</view>
								<view class="butTyp FuserTyp">
									直介
								</view>
								<view class="butTyp SuserTyp">
									转介
								</view>
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
				themeColor: '#000000',
                filterResult: '',
                menuList: [{
                        'title': '职位',
                        'detailTitle': '请选择职位类型（可多选）',
                        'isMutiple': true,
                        'key': 'jobType',
                        'detailList': [{
                                'title': '不限',
                                'value': ''
                            },
                            {
                                'title': 'uni-app',
                                'value': 'uni-app'
                            },
                            {
                                'title': 'java开发',
                                'value': 'java'
                            },
                            {
                                'title': 'web开发',
                                'value': 'web'
                            },
                            {
                                'title': 'Android开发',
                                'value': 'Android'
                            },
                            {
                                'title': 'iOS开发',
                                'value': 'iOS'
                            }
                        ]

                    },
                    {
                        'title': '月薪',
                        'key': 'salary',
                        'isMutiple': true,
                        'detailTitle': '请选择月薪范围（可多选）',
                        'detailList': [{
                                'title': '不限',
                                'value': ''
                            },
                            {
                                'title': '7000~8000',
                                'value': '7000~8000'
                            },
                            {
                                'title': '8000~9000',
                                'value': '8000~9000'
                            },
                            {
                                'title': '9000~10000',
                                'value': '9000~10000'
                            },
                            {
                                'title': '10000以上',
                                'value': '10000~1000000'
                            }
                        ]

                    },
                    {
                        'title': '单选',
                        'key': 'single',
                        'isMutiple': false,
                        'detailTitle': '请选择（单选）',
                        'detailList': [{
                                'title': '不限',
                                'value': ''
                            },
                            {
                                'title': '条件1',
                                'value': 'test_1'
                            },
                            {
                                'title': '条件2',
                                'value': 'test_2'
                            },
                            {
                                'title': '条件3',
                                'value': 'test_3'
                            },
                            {
                                'title': '条件4',
                                'value': 'test_4'
                            },
                            {
                                'title': '条件5',
                                'value': 'test_5'
                            }
                        ]
                    },
                    {
                        'title': '排序',
                        'key': 'sort',
                        'isSort': true,
                        'detailList': [{
                                'title': '默认排序',
                                'value': ''
                            },
                            {
                                'title': '发布时间',
                                'value': 'add_time'
                            },
                            {
                                'title': '薪资最高',
                                'value': 'wages_up'
                            },
                            {
                                'title': '离我最近',
                                'value': 'location'
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
