<template>
	<view style="background-color: rgba(245,246,249,1);width: 100%;">
		<view class="bg">
			<text>支付金额</text>
			<text>￥{{payMess.cpJl/100}}元</text>
		</view>
		<view class="btn" @tap="pay">
			立即支付
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {
		computed: mapState(['payMess']),
		data() {
			return {

			}
		},
		methods: {
			...mapMutations(['setPayMess']),
			pay() {
				var v = this

				this.$http.post(this.apis.CPPAY, {
					ddZftd: 2,
					cpId: v.payMess.id,
					// userAutograph: ui.getApp().signUrl
				}).then(res => {
					if (res.code == 200) {
						console.log(res.result)
						// v.wxPayMess = res.result
						this.WxPay(res.result)
					} else {
						console.log('失败')
					}
				})
			}
		},
		onLoad() {
			console.log(this.payMess)
		}
	}
</script>

<style>
	.bg {
		height: 100upx;
		background-color: white;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding-left: 20upx;
		padding-right: 20upx;
	}

	.btn {
		width: 592upx;
		height: 104upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
		box-shadow: 0 8upx 16upx 0 rgba(69, 124, 232, 0.5);
		border-radius: 52upx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 40upx auto;
		font-size: 40upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}
</style>
