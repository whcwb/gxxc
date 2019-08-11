<template>
	<view style="background:rgba(245,246,249,1)">
		<view class="bg">
			<view class="person">
				<img src="/static/my/1.png" style="border-radius: 50%;width: 132upx;height: 132upx;">
				<view style="color:rgba(255,255,255,1);margin: 18upx 0;">王刚教练</view>
				<uni-rate value="4"></uni-rate>
			</view>
		</view>
		<view style="width:748upx;height:136upx;;background:rgba(254,255,255,1);">
			<segmented-control id="tabbar" :values="items" :stickyTop="108" :current="current" @clickItem="onClickItem"></segmented-control>
		</view>
		
		<view class="btnClass">
			<view v-for="item in btnList" style="margin: 38upx 0;text-align: center;width: 30%;">
				<img v-if="item.src!=''" :src="item.src" style="width: 116upx;height: 116upx;"> 
				<view v-else style="width: 116upx;height: 116upx;"></view>
				<view>
					{{item.text}} 
				</view>
			</view>
		</view>
		
		<view>			<!-- 受理进度 -->
			<view style="padding-top: 1upx;margin-top: 14upx;background:rgba(254,255,255,1)">
				<view class="lineC"></view>
				<view class="tip">
					<image src="/static/my/no.png" style="border-radius: 50%;left: 12upx;width: 30upx;height: 30upx;"></image>
					<text style="margin-left: 16upx;font-size:28upx;color:rgba(161,174,198,1);">
						{{current==0?'受理成功可考试':'当前科目考试费'}}
					</text>
					<view v-if="current!=0" class="toPayBtn" :style="{backgroundColor:alreayPay[current-1]?'#6CA5FF':'rgba(237,103,103,1)'}">
						{{alreayPay[current-1]?'已缴费':'去缴费 >'}}
					</view>
				</view>
				<view v-for="item in itemList" style="margin:26upx 0 0 48upx;display: flex;align-items:flex-start">
					<image :src="item.state==='已完成'||item.state==='合格'?'/static/my/ok.png':'/static/my/no.png'" style="top: 14upx;width: 36upx;height: 36upx;"></image>
					<view class="item">
						<view style="padding:0 20upx;margin-top: 14upx;display: flex;justify-content: space-between">
						  <text class="itemText">{{item.name}}</text>
						  <text class="itemFinish" :style="{color:item.state==='已完成'?'':(item.state==='合格'?'#3E8715':'rgba(161,174,198,1)')}">{{item.state}}</text>   
						</view>
						<view class="message">
							<text>{{item.tip}}</text> 
							<text>{{item.date}}</text> 
						</view>
					</view>
				</view>
			</view>
		</view>
		  

	</view>
</template>

<script>
	import uniRate from "@/components/uni-rate/uni-rate.vue"
	import segmentedControl from "@/components/seg/segmented-control.vue";
	export default {
		components: {
			uniRate,
			segmentedControl
		},
		data() {
			return {
				items: ['受理进度', '科一', '科二', '科三', '科四'],
				current: 0,
				btnList:[],
				btnListAll:[
					[
						{text:'报名条件',src:'/static/my/1.png'},
						{text:'学车费用',src:'/static/my/2.png'},
						{text:'学车流程',src:'/static/my/3.png'},
					],
					[
						{text:'顺序练习',src:'/static/my/1.png'},
						{text:'章节练习',src:'/static/my/2.png'},
						{text:'专项练习',src:'/static/my/3.png'},
					],
					[
						{text:'合格标准',src:'/static/my/1.png'},
						{text:'侧方停车',src:'/static/my/2.png'},
						{text:'曲线行驶',src:'/static/my/3.png'},
						{text:'直角转弯',src:'/static/my/1.png'},
						{text:'上坡起步',src:'/static/my/2.png'},
						{text:'倒车入库',src:'/static/my/3.png'},
					],
					[
						{text:'评判标准',src:'/static/my/1.png'},
						{text:'语言指令',src:'/static/my/2.png'},
						{text:'灯光操作',src:'/static/my/3.png'},
						{text:'考试口诀',src:'/static/my/3.png'},
						{text:'',src:''},
						{text:'',src:''},
					],
					[
						{text:'顺序练习',src:'/static/my/1.png'},
						{text:'章节练习',src:'/static/my/2.png'},
						{text:'专项练习',src:'/static/my/3.png'}
					]
				],
				alreayPay:[true,false,false,false],			//科一~四缴费情况
				itemList:[],								//装item的list，以下的list数据在对接接口时可直接写成一个数组
				itemListAll:[
					[
						{
							name:'医院体检',
							state:'已完成',
							tip:'湖北省中医院',
							date:'2019年7月30日'
						},
						{
							name:'入网面签',
							state:'已完成',
							tip:'蓝盾驾校',
							date:'2019年7月31日'
						},
						{
							name:'档案采集',
							state:'待完成',
							tip:'蓝盾驾校',
							date:'2019年8月1日'
						}
					],
					[
						{	
							name:'东山考场',
							state:'合格',
							tip:'第一次考试',
							date:'2019年7月30日',
						}
					],
					[
						{	
							name:'东山考场',
							state:'',
							tip:'暂无信息',
							date:'',
						}
					],
					[
						{	
							name:'未预约',
							state:'',
							tip:'',
							date:'',
						}
					],
					[
						{	
							name:'未预约',
							state:'',
							tip:'',
							date:'',
						}
					]
				]
			}
		},
		methods: {
			onClickItem(index) {
				this.btnList=Object.assign(this.btnListAll[index])
				this.itemList=Object.assign(this.itemListAll[index])
				if (this.current !== index) {
					this.current = index;
				}
			}
		},
		onLoad() {
			this.btnList=Object.assign(this.btnListAll[0])
			this.itemList=Object.assign(this.itemListAll[0])
		}
	}
</script>

<style>
	.bg {
		width: 750upx;
		height: 524upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.person {
		text-align: center;
	}
	
	.lineC{
		width: 4upx;
		height: 610upx;
		background-color: #E8ECF4;
		margin-left: 64upx;
		position: absolute;
	}
	
	.tip{
		width:484upx;
		height:60upx;
		background:#E8ECF4;
		border-radius:30upx;
		margin: 32upx 0 0 38upx;
		line-height: 60upx;
	}
	
	.item{
		width:362upx;
		height:146upx;
		background:rgba(248,250,252,1);
		box-shadow:0px 4upx 8upx 0upx rgba(211,224,255,0.5);
		border-radius:16upx;
		margin-left: 14upx;
		display: inline-block;
	}
	
	.itemText{
		font-size:28upx;
		font-weight:400;
		color:rgba(51,51,51,1);
	}
	
	.itemFinish{
		font-size:10px;
		font-weight:400;
		color:rgba(108,165,255,1);
	}
	
	.message{
		/* width:362upx; */
		height:84upx;
		background:rgba(255,255,255,1);
		box-shadow:0 4upx 8upx 0 rgba(221,231,255,0.5);
		border-radius:0 0 16upx 16upx;
		margin-top: 8upx;
		display: flex;
		flex-direction: column;
		font-size:20upx;
		font-weight:400;
		color:rgba(102,102,102,1);
		justify-content: center;
		padding:0 20upx;
		}
		
		.btnClass{
			display: flex;
			flex-wrap:wrap;
			align-items: flex-start;
			justify-content: space-around;
			margin-top: 14upx;
			width:750upx;
			background:rgba(254,255,255,1);
		}
		
		.toPayBtn{
			width:136upx;
			height:48upx;
			border-radius:24upx;
			float: right;
			margin: 7upx 8upx 0 0;
			font-size:24upx;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(255,255,255,1);
			display: flex;
			justify-content: center;
			align-items: center;
		}
	</style>
