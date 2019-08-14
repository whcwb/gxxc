<template>
	<view class="box_col pager-index">
		<!-- <view class="status_bar"></view> -->
		<view class="pagerTit-Box">
			<swiper class="swiper" :autoplay="true" style="height: 100%;">
				<swiper-item v-for="(item,index) in items" :key='index'>
					<image style="width: 100%;height: 100%;" :src='item.hdTpdz' mode="widthFix"></image>
				</swiper-item>
			</swiper>
			<view class="funcBox" v-show="userMess">
				<div class="box_row colCenter rowBetween haveUser">
					<div @click='toTx'>
						<div class="titleSty">
							累计奖励
						</div>
						<div class="linesty"></div>
						<div class="valSty">
							{{zhYE.yhZhye/100}}元
						</div>
					</div>
					<img class="eCodeSty" src="./file/img/eCode.png" alt="" @click='toCode'>
					<div>
						<div class="titleSty">
							累计邀请
						</div>
						<div class="linesty"></div>
						<div class="valSty">
							{{USERMESS.userInviteCount}}人
						</div>
					</div>
				</div>
			</view>
			<view class="funcBox" v-show="!userMess">
				<img src="./file/img/idcard.png" style="left: 50%;transform: translateX(-50%);position: absolute;width: 188rpx;height: 188rpx;">
				<div class="noUser box_row rowCenter colBottom" style="left: 50%;transform: translateX(-50%);position: absolute;">
					<div class="text">
						您尚未实名认证
					</div>
					<div class="goAutSty" @click="goAut">
						点击去认证
					</div>
				</div>
			</view>
		</view>

		<div v-if='USERMESS.yhLx != 1' class="advbox" @click="ChangeUser">
			<view style="position: absolute;left: 50%;transform: translateX(-50%);top: 45upx;">报名学车</view>
			<view style="position: absolute;left: 50%;top: 90upx;">只需2600</view>
			<img src="./file/img/index_banner.png">
		</div>

		<view class="butBox box_row rowAuto colCenter">
			<view class="butItenSty" @click="goJkzn">
				<img src="./file/img/flfg2.png" alt="">
				<view class="text">
					法律法规
				</view>
			</view>
			<view class="butItenSty" @click="gobkjf">
				<img src="./file/img/jkzn2.png" alt="">
				<view class="text">
					驾考指南
				</view>
			</view>
			<view class="butItenSty" @click="goplfw">
				<img src="./file/img/jtbs2.png" alt="">
				<view class="text">
					交通标志
				</view>
			</view>
			<view class="butItenSty" @click="gokcfb">
				<img src="./file/img/xllc2.png" alt="">
				<view class="text">
					学车流程
				</view>
			</view>
		</view>
		<view class="teamTitBox box_row colCenter">
			<view class="hline"></view>
			<div class="titText">
				学员信息
			</div>
		</view>
		<view v-if="newsList.length == 0" style="text-align: center;">
			<image src="../../../static/img/zanwu.png" mode="scaleToFill"></image>
			<view style="font-size: 22px;font-weight: 600;color: #70C1EE;">暂无团队成员</view>
		</view>
		<view v-else class="teamListBox">
			<view class="itemSty box_row" v-for="(it,index) in newsList" :key="index">
					<view style="display: flex;flex-direction:row;align-items: center;">
						<view style="margin-right: 15upx;background-color: #007AFF;color: #FFFFFF;text-align: center;vertical-align: middle;height:40rpx ;width: 40rpx; border-radius: 25px;">
							<b>{{index+1}}</b>
						</view>
						<img :src="it.userDetail.yhTx" alt="">
						<view class="name">
							{{it.yhXm}}
						</view>
					</view>	
					
					<view style="display: flex;flex-direction:row;align-items: center;">	
						<view style="margin-right: 15upx;" @click="phone(it.yhSjhm)">
							<uni-icon type='phone' color='#007AFF' size="30" @click='phone(it.yhSjhm)'></uni-icon>
						</view>

						<view v-if="it.userDetail.yhLx == '1' && it.userDetail.yhZt =='1'" class="butTyp onMoney">
							A类
						</view>
						<view v-if="it.userDetail.yhLx == '3'&& it.userDetail.yhZt =='1'" class="butTyp offMoney">
							B类
						</view>
						<view v-if="it.userDetail.yhZt !='1'" class="butTyp offMoney">
							未认证
						</view>
					</view>
				</view>
					<!-- <view class="phoneSty">
						{{it.yhSjhm}}
					</view> -->
		</view>

	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import uniBadge from "@/components/uni-badge/uni-badge.vue"
	import uniIcon from "@/components/uni-icon/uni-icon.vue"
	export default {
		components: {
			uniBadge,
			uniIcon
		},
		computed: mapState(['hasLogin', 'userName']),
		data() {
			return {
				userMess: true,
				USERMESS: '', //用户信息
				zhYE: {
					yhZhye: 0 //账户余额
				},
				items: [{}], //banner图
				newsList: []
			}
		},
		onLoad() {},
		onShow() {
			this.Met.getUserInfo()
			this.getbanner()
			this.getYE()
			this.getUsermess()
			this.getnewsList()
			if (this.judgeClient() == 'Android') {
				setTimeout(() => {
					this.wxApi.checkJsApi();
					this.wxApi.share(this.USERMESS.id);
				}, 3000);
			} else if (this.judgeClient() == 'IOS') {
				setTimeout(() => {
					this.wxApi.checkJsApi();
					this.wxApi.share(this.USERMESS.id);
				}, 3000);

			} else {

			}
		},
		created() {

		},
		methods: {
			phone(id) {
				uni.makePhoneCall({
					phoneNumber: id //仅为示例
				});
			},
			judgeClient() {
				let u = navigator.userAgent;
				let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //判断是否是 android终端
				let isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //判断是否是 iOS终端
				console.log('是否是Android：' + isAndroid); //true,false
				console.log('是否是iOS：' + isIOS);
				if (isAndroid) {
					return 'Android';
				} else if (isIOS) {
					return 'IOS';
				} else {
					return 'PC';
				}
			},
			getnewsList() { //第一次回去数据
				this.$http.post(this.apis.TEAMMESS, {
					yhxm: '',
					grade: '',
					yhlx: '',
					sfjf: '',
					pageNum: 1,
					pageSize: 20
				}).then((res) => {
					if (res.code == 200) {
						this.newsList = res.page.list
						// _self.newsList = res.page.list.split('--hcSplitor--');
						//得到数据后停止下拉刷新
					} else {
						uni.showToast({
							title: res.message,
							icon: 'none',
							duration: 1500
						});
					}
				})
			},

			getUsermess() {
				this.$http.post(this.apis.USERMESS, {}).then((res) => {
					if (res.code == 200) {
						this.USERMESS = res.result
						uni.setStorage({
							key:'usermess',
							data:res.result
						})
						if (this.USERMESS.yhZt == 1) {
							this.userMess = true
						} else {
							this.userMess = false
						}
					}
				})
			},
			getbanner() { //获取轮播图
				this.$http.post(this.apis.SWIPER, {
					hdSxs: 0
				}).then((res) => {
					if (res.code == 200) {
						this.items = res.page.list
					}
				})
			},
			getYE() { //获取账户余额
				var v = this
				this.$http.post(this.apis.USERZH, {}).then((res) => {
					if (res.code == 200) {
						if( res.result.yhZhye < 0){
							v.zhYE.yhZhye = 0
						}else{
							v.zhYE.yhZhye = res.result.yhZhye
						}
					} else {
						uni.showToast({
							title: res.message
						})
					}
				})
			},
			toTx() {
				uni.navigateTo({
					url: '/pages/yqJl/yqJl'
				});
			},
			ChangeUser() {
				uni.navigateTo({
					url: '/pages/goMoney/goMoney'
				})
			},
			goAut() {
				uni.navigateTo({
					url: '/pages/rellyName/rellyName'
				});
			},
			toCode() {
				uni.navigateTo({
					url: '/pages/main/user/code/code'
				});
			},
			goJkzn() {
				uni.navigateTo({
					url: '/pages/main/p_index/jkzn/jkzn'
				});
			},
			gobkjf() {
				uni.navigateTo({
					url: '/pages/main/p_index/bkjf/bkjf'
				});
			},
			goplfw() {
				uni.navigateTo({
					url: '/pages/main/p_index/plfw/plfw'
				});
			},
			gokcfb() {
				uni.navigateTo({
					url: '/pages/main/p_index/kcfb/kcfb'
				});
			}
		}
	}
</script>

<style lang="less">
	.pager-index {
		width: 100%;
		// height: 100vh;
		overflow-y: auto;
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
				font-size: 24upx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(80, 144, 241, 1);
			}

			.valSty {
				font-size: 28upx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(80, 144, 241, 1);
			}

			.linesty {
				width: 52rpx;
				height: 4rpx;
				background-color: #BBC7EC;
				margin: 10rpx auto;
			}

			.eCodeSty {
				width: 112rpx;
				height: 112rpx;
			}
		}

		.noUser {
			height: 100%;

			.text {
				margin-bottom: 22rpx;
				font-size: 28rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(153, 153, 153, 1);
				border-bottom: rgba(255, 255, 255, 0) solid 1px;
			}

			.goAutSty {
				margin-bottom: 22rpx;
				font-size: 28rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(80, 144, 241, 1);
				border-bottom: rgba(80, 144, 241, 1) solid 1px;
			}
		}

		.butBox {
			height: 232rpx;
			background-color: #ffffff;
			margin-top: 20rpx;

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
			padding: 0 36rpx;
			height: 116rpx;

			.hline {
				margin-right: 12rpx;
				width: 8rpx;
				height: 30rpx;
				background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
			}

			.titText {
				font-size: 32rpx;
				font-family: PingFangSC-Medium;
				font-weight: 500;
				color: rgba(51, 51, 51, 1);
				background-color: grba(255, 255, 255, 0);
			}
		}

		.advbox {
			position: relative;
			text-align: center;

			view{
				font-size:44upx;
				font-family:FZZDHJW--GB1-0;
				font-weight:normal;
				color:rgba(255,255,255,1);
			}
			
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
			max-height: 50vh;

			.itemSty {
				border-bottom: solid 2rpx #DFE7EE;
				padding: 30rpx 0;
				display: flex;
				justify-content: space-between;
				align-content: center;

				img {
					margin-right: 30rpx;
					width: 96rpx;
					height: 96rpx;
					border-radius: 100%;
				}

				.name {
					width: 192rpx;
					font-size: 32rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(51, 51, 51, 1);
					margin-right: 15upx;
				}

				.butTyp {
					height: 38rpx;
					width: 90rpx;
					text-align: center;
					border-radius: 2px;
					font-size: 22rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(255, 255, 255, 1);
					line-height: 38rpx;
					margin-right: 14rpx;
					padding: 7upx 15upx;
					border-radius: 20upx
				}

				.onMoney {
					background: rgba(251, 164, 19, 1);

				}

				.offMoney {
					background-color:rgba(180,180,180, 0.7);
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
