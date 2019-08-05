<template>
	<view class="">
		<button type="primary" @click="pay">支付一元</button>
		
		<view class="" v-for="(it,index) in appMess" :key="index">
			{{it}}
		</view>
	</view>
</template>

<script>
	export default {
		name:"study",
		data(){
			return {
				appMess:[]
			}
		},
		onLaunch(){
			console.log('onLaunch')
		},
		onShow(){
			console.log('onShow')
		},
		methods:{
			pay(){
				// 获取 支付方式
				uni.getProvider({
					service: 'payment',
					success: (res)=> {
						this.appMess = res.provider
						res.provider
						this.payMoney(res.provider[1])
						console.log('信息',res.provider)
					}
				});
			},
			payMoney(provider){
				uni.requestPayment({
					provider:provider
				})
			}
		}
	}
</script>

<style>
</style>
