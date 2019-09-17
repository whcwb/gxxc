<template>
	<view class="reaBox">
		<img class="traImg" :src="traMess.placeCoverImg.split(',')[0]" alt="">
		<view class="schoolName">
			{{traMess.placeName}}
		</view>
		<view class="box_row">
			<view class="yhNameSty">
				<img src="../file/yonghu.png" alt="">
				{{traMess.yhXm}}
			</view>
			<view class="yhPhoneSty" @click="phone(traMess.yhhm)">
				<img src="../file/dianhua.png" alt="">
				{{traMess.yhhm}}
			</view>
		</view>
		<view class="address">
			{{traMess.address}}
		</view>
		<view class="lineRow"></view>
		<!-- <view class="imgBoxTit">
			相册
		</view> -->
		<view class="lineRow"></view>
		<view class="imgBox">
			<view class="imgItem" v-for="(it,index) in traMess.placeImg.split(',')">
				<img class="traImg" :src="it" alt="">
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		name: "tra",
		computed: {
			traMess() {
				return this.$store.state.traMess
			}
		},
		data(){
			return {
					urlImg:this.apis.getImgUrl,
			}
		},
		onShow(){
			// placeImg
			console.log(this.traMess)
			uni.setNavigationBarTitle({
				title: this.traMess.subName
			});
		},
		methods:{
			phone(id) {
				uni.makePhoneCall({
					phoneNumber: id //仅为示例
				});
			}
		}
	}
</script>

<style lang="less">
	.padd{
		padding: 10rpx 40rpx;
	}
	.reaBox{
		width: 100%;
		.traImg{
			width: 100%;
			height: 300rpx;
		}
		.schoolName{
			.padd;
			font-size: 48rpx;
		}
		.yhNameSty{
			.padd;
			font-size: 30rpx;
			img{
				width: 40rpx;
				height: 40rpx;
				transform: translateY(9rpx);
				margin-right: 10rpx;
			}
		}
		.yhPhoneSty{
			.padd;
			font-size: 30rpx;
			text-decoration: underline;
			color: #0000FF;
			img{
				width: 44rpx;
				height: 44rpx;
				transform: translateY(9rpx);
				margin-right: 10rpx;
			}
		}
		.address{
			.padd;
			font-size: 30rpx;
		}
		.lineRow{
			margin: 0 40rpx;
			height: 2rpx;
			width: 100%;
			background-color: #bfbfbf;
		}
		.imgBoxTit{
			font-size: 32rpx;
			font-weight: 600;
			.padd;
		}
		.imgBox{
			.padd;
		}
	}
</style>
