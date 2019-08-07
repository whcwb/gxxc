<template>
	<view style="width: 100%;">
		<!-- <image src="/static/img/welcome.png" :style="{width: '100%',height:imgheight*2+'upx'}"></image> -->
		<image src="/static/img/welcome.png" style="width: 100vw;height: 100vh;"></image>
		<w-loading text="加载中.." mask="false" click="true" ref="loading"></w-loading>
	</view>
</template>  

<script>
	import wLoading from "@/components/w-loading/w-loading.vue";
	export default {
		components:{
			wLoading
		},
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
		onReady() {
			this.$refs.loading.open()
			setTimeout((val,index,arr)=>{
				uni.navigateTo({
				    url: '/pages/login/login',
				});
			},3000)
		}
	}
</script>

<style>

</style>
