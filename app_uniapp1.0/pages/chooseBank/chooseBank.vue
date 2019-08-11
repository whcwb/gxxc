<template>
	<view style="width: 100%;height: 100vh;background-color: #F5F6F9;">
		<view class="addBank">
			<view @tap="toAddCard" style="margin: 0 auto;width: 60upx;font-size: 70upx;font-weight: 400;">+</view>
		</view>
		<view v-if="bankList.length==0" class="none">暂无银行卡，请添加</view>
		<view v-if="bankList.length!=0" :class="index==bankListIndex?'MyMoneyBox choose':'MyMoneyBox'" v-for="(item,index) in bankList"
		 @tap="choose(index)">
			<view>
				<img src="/static/img/welcome.png" style="width: 100%;height: 80%;">
				<view style="padding: 0.15rem 0.3rem;font-size: 0.33rem">
					{{bankList[bankListIndex].yhkKh}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				classControl: 'MyMoneyBox',
				bankList: [],
				bankListIndex: '-1',
				ye: ''
			}
		},
		onNavigationBarButtonTap() { //点击了提现按钮
			if (this.bankListIndex < 0 || this.bankListIndex >= this.bankList.length) {
				console.log("未选择银行卡")
				return
			}

			var v = this
			let bank = this.bankList[this.bankListIndex].id

			this.ye = parseFloat(this.ye)
			this.$http.post(this.apis.TX, {
				'ttje': this.ye * 100,
				'yhkid': bank,
				ttfs: 1
			}).then(res => {
				if (res.code == 200) {
					uni.showToast({
						title: '提现成功',
						duration: 2000
					});
					setTimeout(()=>{
						uni.navigateTo({
							url: '/pages/yqJl/yqJl',
						});
					},2000) 
				}else{
					 uni.showToast({
						title: res.message,
						duration: 2000,
						icon:'none'
					});
				}
			})
		},
		methods: {
			getBankList() {
				this.$http.post(this.apis.BANKLIST).then((res) => {
					if (res.code == 200 && res.result) {
						this.bankList = res.result
						console.log(this.bankList)
					} else {
						this.bankList = []
					}
				}).catch((err) => {

				})
			},
			choose(index) {
				this.bankListIndex = index
			},
			getYe() {
				this.$http.post(this.apis.USERZH).then(res => {
					if (res.code == 200) {
						this.ye = res.result.yhZhye / 100
					}else{
						uni.showToast({
							title: res.message,
							duration: 2000
						});
					}
				})
			},
			toAddCard(){
				uni.navigateTo({
					url: '/pages/addBankCard/addBankCard',
				});
			}
		},
		onLoad() {
			this.getYe() //获取余额
			this.getBankList() //获取银行卡列表
		}
	}
</script>

<style>
	.MyMoneyBox {
		width: 680rpx;
		height: 316rpx;
		background: rgba(255, 255, 255, 1);
		box-shadow: 0px 2px 8px 0px rgba(203, 203, 214, 0.5);
		border-radius: 16rpx;
		margin: 0 auto 16rpx;
		border: 2upx solid #E6E7EC;
		padding: 3upx;
	}

	.choose {
		border: 5upx solid #106f4a;
	}

	.addBank {
		text-align: center;
		height: 100upx;
		line-height: 100upx;
	}

	.none {
		font-size: 40upx;
		text-align: center;
	}
</style>
