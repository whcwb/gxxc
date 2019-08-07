<template>
	<view>
		<view class="page">
			<view class="image-list">
					<view class="image-content" style="padding: 40rpx;">
						<img style="width: 100%; height: 100%; background-color: #eeeeee;"  src="./file/jf21.png">
						 <img style="width: 100%; height: 100%; background-color: #eeeeee;"  src="./file/jf22.png">
					</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			imageError: function(e) {
				console.error('image发生error事件，携带值为' + e.detail.errMsg)
			}
		}
	}
</script>

<style>
	.img{
		background-image: url('~@/pages/goMoney/file/jf21.png');
	}
</style>
