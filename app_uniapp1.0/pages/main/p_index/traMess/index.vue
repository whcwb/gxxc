<template>
	<view class="reaBox">
		<img class="traImg" :src="traMess.subImg.split(',')[0]" alt="">
		<view class="schoolName">
			<!-- {{traMess.subName}} --> 负责人
		</view>
		<view class="box_row">
			<view class="yhNameSty">
				<img src="../file/yonghu.png" alt="">
				{{traMess.subFz}}
			</view>
			<view class="yhPhoneSty" @click="phone(traMess.subPhone)">
				<img src="../file/dianhua.png" alt="">
				{{traMess.subPhone}}
			</view>
		</view>
		<!-- <view class="address">
			{{traMess.address}}
		</view> -->
		<view class="lineRow"></view>
		<!-- <view class="imgBoxTit">
			相册
		</view> -->
		<view class="lineRow"></view>
		<view class="imgBox" v-for="(item,index) in imgList" :key="index">
			<view class="address">{{item.address}}</view>
			<view class="imgItem" v-for="(a,index) in item.placeImg.split(',')" :key="index">
				<img class="traImg" :src="a" alt="">
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
		data() {
			return {
				urlImg: this.apis.getImgUrl,
				imgList:[]
			}
		},
		onShow() {
			// placeImg
			console.log(this.traMess)
			uni.setNavigationBarTitle({
				title: this.traMess.subName
			});
			this.getpagerList()
		},
		methods: {
			getpagerList() {
				console.log(this.traMess.id);
				this.$http.get('/app/subschool/getAllTrainPlace', {
					id: this.traMess.id
				}).then((res) => {
					if (res.code == 200) {
						this.imgList = res.result
						for(let i = 0; i<this.imgList.length;i++){
							this.imgList.placeImg = this.imgList.placeImg.split(',')
						}
						
					} else {

					}
				})
			},
			phone(id) {
				uni.makePhoneCall({
					phoneNumber: id //仅为示例
				});
			}
		}
	}
</script>

<style lang="less">
	.padd {
		padding: 10rpx 40rpx;
	}

	.reaBox {
		width: 100%;

		.traImg {
			width: 100%;
			height: 300rpx;
		}

		.schoolName {
			.padd;
			font-size: 48rpx;
		}

		.yhNameSty {
			.padd;
			font-size: 30rpx;

			img {
				width: 40rpx;
				height: 40rpx;
				transform: translateY(9rpx);
				margin-right: 10rpx;
			}
		}

		.yhPhoneSty {
			.padd;
			font-size: 30rpx;
			text-decoration: underline;
			color: #0000FF;

			img {
				width: 44rpx;
				height: 44rpx;
				transform: translateY(9rpx);
				margin-right: 10rpx;
			}
		}

		.address {
			.padd;
			font-size: 30rpx;
		}

		.lineRow {
			margin: 0 40rpx;
			height: 2rpx;
			width: 100%;
			background-color: #bfbfbf;
		}

		.imgBoxTit {
			font-size: 32rpx;
			font-weight: 600;
			.padd;
		}

		.imgBox {
			.padd;
		}
	}
</style>
