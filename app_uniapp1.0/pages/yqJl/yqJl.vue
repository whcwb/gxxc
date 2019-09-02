<template>
	<view class="box_col yqjl-index">
		<view class="MyMoneyBox">
			<view class="title">
				可提现
			</view>
			<view class="box_row rowBetween center allMoney">
				<view class="moneyVal">
					<view class="yeCode">
						￥
					</view>
					<input class="inputSty" v-model="ye" type="number">
				</view>
				<view class="moneyBut" @tap="goTx"></view>
			</view>
			<view class="lable">
				已冻结 {{dj}} 元
			</view>
		</view>

		<view class="txTltle">
			<view class="titSty">
				提现明细
			</view>
		</view>
		<view class="box_col_auto detailBox">
			<view v-if="mxList.length!=0" :class="index == mxList.length-1 ?'itemBox itemBoxlast':'itemBox'" v-for="(item,index) in mxList" :key="index">
				<view class="detailItem">
					<view class="box_row rowBetween">
						<view class="detailName">
							提现
						</view>
						<view class="money">
							-{{item.zjJe/100}}
						</view>
					</view>
					<view class="box_row rowBetween">
						<view class="time">
							{{item.cjsj}}
						</view>
						<view class="detailState">
							{{item.zjZt | zjZt}}
						</view>
					</view>
				</view>
			</view>
			<view v-if="mxList.length==0" class="noData">
				暂无提现
			</view>
		</view>
	</view>
</template>

<script>
	import mixin from '@/common/mixin.js'
	export default {
		data() {
			return {
				ye: 0 ,//余额
				dj:0,//冻结金额
				mxList:[]//提现明细
			}
		},
		mixins:[mixin],
		methods: {
			appTx(){
				// this.toChooseBank()  //提现到银行卡方式
				
				var self = this 
				var openid = uni.getStorageSync('openid')
				if(openid && openid!=''&& openid != undefined){
				   this.Tx()
				}else{
				   uni.showModal({ 
				       title: '提示',
				       content: '首次提现需授权绑定微信账号',
					   confirmText:'去绑定',
				       success: function (res) {
				           if (res.confirm) {
				              // self.wxLog() //微信绑定方式
							  uni.navigateTo({  //手动绑定方式
							  	url:"wxBind/wxBind"
							  })
				           } else if (res.cancel) {
				               console.log('用户点击取消');
				           }
				       }
				   });
				}
			},
			wxLog() { //微信登录 绑定   获取openid
				var self = this
				uni.getProvider({
				    service: 'oauth',
				    success: function (res) {
				        console.log(res.provider)
				        if (~res.provider.indexOf('weixin')) {
				           uni.login({
				           	provider: 'weixin',
				           	success: function(loginRes) {
				           		console.log(loginRes.authResult.openid);
								self.$http.post('/app/ptyh/bindOpenId',{openid:loginRes.authResult.openid}).then((res)=>{
									if(res.code == 200){
										uni.setStorage({
											key:'openid',
											data:loginRes.authResult.openid
										})
										uni.showToast({
											title:'绑定成功'
										})
									}else{
										uni.showToast({
											title:res.message
										})
									}
								})
				           	}
				           });
				        }
				    }
				});
			},
			goTx(){
				// #ifdef APP-PLUS
				  this.appTx()
				// #endif
				// #ifdef H5
				  this.Tx()
				// #endif
			},
			Tx() { //点击了提现按钮
				var v = this
				this.$http.post(this.apis.TX, {
					'ttje': this.ye * 100,
					ttfs: 1
				}).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: '提现成功',
							duration: 1500
						});
						this.getMoney()
						setTimeout(()=>{
							uni.navigateTo({
								url: '/pages/yqJl/yqJl',
							});
						},1500) 
					}else{
						 uni.showToast({
							title: res.message,
							duration: 2000,
							icon:'none'
						});
					}
				})
			},
			//获取余额
			getMoney() {
				this.$http.post(this.apis.USERZH).then(res => {
					if (res.code == 200) {
						if (res.result.yhZhye == 0||res.result.yhZhye < 0||res.result.yhZhye <20000) {		//如果余额小于200，则为0
							this.ye = 0
							console.log('1');
						} else {
							this.ye = res.result.yhZhye / 100
							this.dj = res.result.yhTxdj / 100
						}
					}else{
						uni.showToast({
							title: res.message,
							duration: 2000
						});
					}
				})

				//提现明细
				this.$http.post(this.apis.ZDLIST, {zjFs: -1,pageSize: 10,pageNum: 1,mxLx:'4'}).then(res => {
					if (res.code == 200) {
						this.mxList=res.page.list
					}else{
						uni.showToast({
							title: res.message,
							duration: 2000,
							icon:'none'
						});
					}

				})
			},
			toChooseBank(){
				console.log(this.ye)
				if(this.ye = 0){
					uni.showToast({
						title: '暂无余额可提现',
						duration: 2000
					});
				}
				else{
					uni.navigateTo({
						url: '/pages/chooseBank/chooseBank',
					});
				}
			}
		},
		onShow() {
			this.getMoney()
		}
	}
</script>

<style lang="less">
	.yqjl-index {
		width: 100%;
		height: 100vh;
		background-color: #F5F6F9;

		.MyMoneyBox {
			width: 680rpx;
			height: 316rpx;
			background: rgba(255, 255, 255, 1);
			box-shadow: 0px 2px 8px 0px rgba(203, 203, 214, 0.5);
			border-radius: 16rpx;
			margin: 26rpx auto 16rpx;

			// padding: 30rpx 22rpx 0 22rpx;
			.title {
				font-size: 40upx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(45, 45, 45, 1);
				margin: 30rpx 22rpx 0 22rpx;
			}

			.allMoney {
				margin: 40rpx 22rpx 0 22rpx;
				padding-bottom: 32rpx;
				border-bottom: solid 1px #DFE7EE;

				.moneyVal {
					font-size: 52rpx;
					font-family: PingFangSC-Medium;
					font-weight: 500;
					color: rgba(45, 45, 45, 1);
					position: relative;
					.yeCode{
						position: absolute;
						left: 0;
						top: 0;
						font-size: 52rpx;
						font-family: PingFangSC-Medium;
						font-weight: 500;
						color: rgba(45, 45, 45, 1);
					}
					.inputSty{
						height: 70rpx;
						padding-left: 80rpx;
						font-size: 52rpx;
						font-family: PingFangSC-Medium;
						font-weight: 500;
						color: rgba(45, 45, 45, 1);
					}
				}

				.moneyBut {
					width: 240rpx;
					height: 30px;
					background-image: url("./file/img/tx.png");
					background-position: center;
					background-size: 100% 100%;
					background-repeat: no-repeat;
				}
			}

			.lable {
				font-size: 28rpx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(236, 64, 64, 1);
				margin: 20rpx 22rpx 0 22rpx;
			}
		}

		.txTltle {
			height: 120rpx;
			line-height: 120rpx;
			border-radius: 16rpx 16rpx 0 0;
			background-color: #ffffff;
			width: 680rpx;
			margin: 0 auto;
			// box-shadow:0px 2px 8px 0px rgba(203,203,214,1);

			.titSty {
				margin: auto 11px;
				font-size: 40upx;
				font-family: PingFangSC-Regular;
				font-weight: 400;
				color: rgba(45, 45, 45, 1);
			}
		}
		
		.noData{
			width: 680rpx;
			margin: 0 auto;
			background-color: #ffffff;
			font-size: 36upx;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 200upx
		}

		.detailBox {
			margin-bottom: 16rpx;

			// box-shadow:0px 2px 8px 0px rgba(203,203,214,1);
			.itemBox {
				width: 680rpx;
				margin: 0 auto;
				background-color: #ffffff;
			}

			.itemBoxlast {
				border-radius: 0 0 16rpx 16rpx;
			}

			.detailItem {
				margin: 0 22rpx;
				border-top: solid 1px #DFE7EE;
				padding: 22rpx 0;

				.detailName {
					font-size: 36rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(45, 45, 45, 1);
				}

				.money {
					font-size: 36rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(236, 64, 64, 1);
				}

				.time {
					font-size: 32rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(153, 153, 153, 1);
				}

				.detailState {
					font-size: 28rpx;
					font-family: PingFangSC-Regular;
					font-weight: 400;
					color: rgba(153, 153, 153, 1);
				}
			}

		}
	}
</style>
