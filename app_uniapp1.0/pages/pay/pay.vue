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
						// #ifdef APP-PLUS
							this.AppWxPay(res.result)
						// #endif
						
						// #ifdef H5
							this.WxPay(res.result)
						// #endif
					} else {
						console.log('失败')
						uni.showToast({
							title:res.message,
							icon:"none"
						})
					}
				})
			},
			AppWxPay(mess){
				uni.requestPayment({
				    provider: 'wxpay',
				    orderInfo: mess.nonceStr, //微信、支付宝订单数据
				    success: function (res) {
				        console.log('success:' + JSON.stringify(res));
				    },
				    fail: function (err) {
				        console.log('fail:' + JSON.stringify(err));
				    }
				});
			},
			WxPay(mess){
				var v = this
				  WeixinJSBridge.invoke(
					'getBrandWCPayRequest', {
					  "appId":mess.appId,     //公众号名称，由商户传入
					  "timeStamp":mess.timeStamp,         //时间戳，自1970年以来的秒数
					  "nonceStr":mess.nonceStr, //随机串
					  "package":mess.package,
					  "signType":mess.signType,         //微信签名方式：
					  "paySign":mess.paySign //微信签名
					},
					function(res){
					  console.log(res)
					  if(res.err_msg=='get_brand_wcpay_request:ok'){
						uni.showToast({ title: '支付成功' })
						// signUrl = ''
						uni.switchTab({
							url:'/pages/main/user/user'
						})
					  }else if(res.err_msg=='get_brand_wcpay_request::fail'){
						uni.showToast({ title: '支付失败',icon:"none"})
						uni.navigateBack()
					  }else if(res.err_msg=='get_brand_wcpay_request:cancel'){
						uni.showToast({ title: '支付取消',icon:"none" })
					  }
					}
				  );
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
