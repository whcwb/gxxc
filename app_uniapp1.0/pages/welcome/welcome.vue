<template>
	<view class="welcome box_col">
		<!-- #ifdef APP-PLUS -->
		<view class="status_bar"></view>
		<!-- #endif -->
		<view class="box_col_100 bodyBox">
			<img src="./file/welcome.png" alt="">
		</view>
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

<style lang="less">
	.welcome{
		width: 100%;
		height: 100vh;
		background-color: #007AFF;
		.status_bar {
			//app 内嵌样式
			height: var(--status-bar-height);
			width: 100%;
		}
		.bodyBox{
			img{
				width: 100%;
				height: 100%;
			}
		}
	}
</style>
