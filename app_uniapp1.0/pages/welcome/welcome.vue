<template>
	<view style="width: 100%;">
		<!-- <img src="/static/img/head.png" style="width: 176upx;height: 176upx;margin-top: -56upx;"> -->
		<!-- <image src="/static/img/welcome.png" :style="{width: '100%',height:imgheight*2+'upx'}"></image> -->
		<image src="/static/img/welcome.png" style="width: 100%;height: 1454upx;"></image>
	</view>
</template>  

<script>
	export default {
		data() {
			return {
				imgheight:200
			}
		},
		methods: {
			toLogin(){
				uni.navigateTo({
				    url: '/pages/login/login',
				});
			}
		},
		onLoad() {
			setTimeout((val,index,arr)=>{
				uni.navigateTo({
				    url: '/pages/login/login',
				});
			},3000)
			
			var v=this
			uni.getSystemInfo({
				success:function (res) {
						v.imgheight=res.windowHeight
					}
			})
		}
	}
</script>

<style>

</style>
