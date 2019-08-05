<template>
	<view class="box_col pager-index">
		<view class="status_bar"></view>
		<view class="pagerTit-Box">
			<view class="funcBox">
				<div class=" box_row colCenter rowBetween haveUser">
					<div>
						<div class="titleSty">
							累计奖励
						</div>
						<div class="linesty"></div>
						<div class="valSty">
							2800元
						</div>
					</div>
					<img class="eCodeSty" src="./file/img/eCode.png" alt="">
					<div>
						<div class="titleSty">
							累计奖励
						</div>
						<div class="linesty"></div>
						<div class="valSty">
							20人
						</div>
					</div>
				</div>
			</view>
			<!-- <view class="funcBox noUser">
			
			</view> -->
		</view>

		<div class="advbox">
			<img src="./file/img/advimg.png" alt="">
		</div>

		<view class="butBox box_row rowAuto colCenter">
			<view class="butItenSty">
				<img src="./file/img/jkzn.png" alt="">
				<view class="text">
					驾考指南
				</view>
			</view>
			<view class="butItenSty">
				<img src="./file/img/bkjf.png" alt="">
				<view class="text">
					补考缴费
				</view>
			</view>
			<view class="butItenSty">
				<img src="./file/img/plfw.png" alt="">
				<view class="text">
					陪练服务
				</view>
			</view>
			<view class="butItenSty">
				<img src="./file/img/kcfb.png" alt="">
				<view class="text">
					考场分布
				</view>
			</view>
		</view>
		<view class="teamTitBox">
			学员信息
		</view>
		<view class="teamListBox">

			<view class="itemSty box_row" v-for="(it,index) in 10" :key="index">
				<img src="./file/img/jkzn.png" alt="">
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
</template>

<script>
	import {
		mapState
	} from 'vuex'

	export default {
		computed: mapState(['hasLogin', 'userName']),
		onLoad() {
			return
			if (!this.hasLogin) {
				uni.showModal({
					title: '未登录',
					content: '您未登录，需要登录后才能继续',
					/**
					 * 如果需要强制登录，不显示取消按钮
					 */
					showCancel: false,
					success: (res) => {
						if (res.confirm) {
							/**
							 * 如果需要强制登录，使用reLaunch方式
							 */
							if (this.hasLogin) {
								uni.reLaunch({
									url: '../../login/login'
								});
							} else {
								uni.navigateTo({
									url: '../../login/login'
								});
							}
						}
					}
				});
			}
		}
	}
</script>

<style lang="less">
	.pager-index {
		width: 100%;
		height: 100vh;
		background-color: #F5F6F9;

		.status_bar {
			//app 内嵌样式
			height: var(--status-bar-height);
			width: 100%;
			background-color: #3B93FD;
		}

		.pagerTit-Box {
			background-color: #3B93FD;
			height: 350rpx;
			position: relative;
			margin-bottom: 109upx;
		}

		.funcBox {
			width: 90.4%;
			height: 218rpx;
			background-color: #ffffff;
			position: absolute;
			left: 50%;
			bottom: 0;
			transform: translate(-50%, 50%);
			border-radius: 8px;
		}

		.haveUser {
			text-align: center;
			height: 100%;
			padding: 0 40px;
			.titleSty {
				font-size: 32rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(80, 144, 241, 1);
			}

			.valSty {
				font-size:20px;
				font-family:PingFangSC-Regular;
				font-weight:400;
				color:rgba(80,144,241,1);
			}
			.linesty{
				width:52rpx;
				height:4rpx;
				background-color: #BBC7EC;
				margin: 10rpx auto;
			}
			.eCodeSty {
				width: 112rpx;
				height: 112rpx;
			}
		}

		.butBox {
			height: 232rpx;
			background-color: #ffffff;
			margin-top: 20rpx;
			margin-bottom: 18rpx;

			.butItenSty {
				text-align: center;

				img {
					width: 120rpx;
					height: 120rpx;
				}

				.text {
					font-size: 14px;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(51, 51, 51, 1);
				}
			}
		}

		.teamTitBox {
			font-size: 44rpx;
			font-family: PingFangSC-Medium;
			font-weight: 500;
			color: rgba(51, 51, 51, 1);
			background-color: #ffffff;
			padding: 28rpx 36rpx;
		}

		.advbox {
			text-align: center;

			img {
				width: 90.67%;
				height: 172rpx;
				margin: 14rpx auto 0;
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

				img {
					margin-right: 30rpx;
					width: 96rpx;
					height: 96rpx;
					border-radius: 100%;
				}

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

	}
</style>
