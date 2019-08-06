<template>
	<view class="box_col yqjl-index">
		<view class="MyMoneyBox">
			<view class="title">
				可提现
			</view>
			<view class="box_row rowBetween center allMoney">
				<view class="moneyVal">
					￥200.00
				</view>
				<view class="moneyBut"></view>
			</view>
			<view class="lable">
				每天最多提现500元！
			</view>
		</view>
	
		<view class="txTltle">
			<view class="titSty">
				提现明细
			</view>
		</view>
		<view class="box_col_auto detailBox">
			<view :class="index == 3 ?'itemBox itemBoxlast':'itemBox'" v-for="(it,index) in 4" :key="index">
				<view class="detailItem">
					<view class="box_row rowBetween">
						<view class="detailName">
							提现
						</view>
						<view class="money">
							-200
						</view>
					</view>
					<view class="box_row rowBetween">
						<view class="time">
							2019.08.05 15:00
						</view>
						<view class="detailState">
							提现中
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="less">
.yqjl-index{
	width: 100%;
	height: 100vh;
	background-color: #F5F6F9;
	.MyMoneyBox{
		width: 680rpx;
		height: 316rpx;
		background:rgba(255,255,255,1);
		box-shadow:0px 2px 8px 0px rgba(203,203,214,0.5);
		border-radius:16rpx;
		margin:26rpx auto 16rpx;
		// padding: 30rpx 22rpx 0 22rpx;
		.title{
			font-size:44rpx;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(45,45,45,1);
			margin: 30rpx 22rpx 0 22rpx;
		}
	
		.allMoney{
			margin: 40rpx 22rpx 0 22rpx;
			padding-bottom: 32rpx;
			border-bottom: solid 1px #DFE7EE;
			.moneyVal{
				font-size:52rpx;
				font-family:PingFangSC-Medium;
				font-weight:500;
				color:rgba(45,45,45,1);
			}
			.moneyBut{
				width: 86px;
				height: 30px;
				background-image: url("./file/img/tx.png");
				background-position: center;
				background-size: 100% 100%;
				background-repeat: no-repeat;
			}
		}
		.lable{
			font-size:28rpx;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(236,64,64,1);
			margin: 20rpx 22rpx 0 22rpx;
		}
	}
	
	.txTltle{
		height: 120rpx;
		line-height: 120rpx;
		border-radius: 16rpx 16rpx 0 0;
		background-color: #ffffff;
		width: 680rpx;
		margin: 0 auto;
		// box-shadow:0px 2px 8px 0px rgba(203,203,214,1);

		.titSty{
			margin:auto 11px;
			font-size:44rpx;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(45,45,45,1);
		}
	}

	.detailBox{
		margin-bottom:16rpx; 
		// box-shadow:0px 2px 8px 0px rgba(203,203,214,1);
		.itemBox{
			width: 680rpx;
			margin: 0 auto;
			background-color: #ffffff;
		}
		.itemBoxlast{
			border-radius:0 0 16rpx 16rpx;
		}
		.detailItem{
			margin: 0 22rpx;
			border-top: solid 1px #DFE7EE;
			padding: 22rpx 0 ;
			.detailName{
				font-size:36rpx;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(45,45,45,1);
			}
			.money{
				font-size:36rpx;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(236,64,64,1);
			}
			.time{
				font-size:32rpx;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(153,153,153,1);
			}
			.detailState{
				font-size:28rpx;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(153,153,153,1);
			}
		}
		
	}
}
</style>
