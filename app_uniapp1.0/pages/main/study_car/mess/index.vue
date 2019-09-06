<template>
	<view>
		<view class="bg">
			<view style="display: flex;align-content: center;margin: 48upx 0 0 64upx;">
				<img :src="imgUrl+zyMess.jlImg" style="border-radius: 50%;width: 132upx;height: 132upx;" @tap='tomymess()'>
				<view class="personMessage" style="flex: 1;">
					<view style="margin-top: 10upx;font-size:20px;
						margin-left: 60rpx;
						font-family:PingFangSC-Regular;
						font-weight:400;
						color:rgba(255,255,255,1);
						line-height:28px;">{{zyMess.yhXm}}</view>
					<view style="margin-left: 60rpx;margin-top: 10upx">
						<uni-rate :value="zyMess.jlPf" :disabled="true"></uni-rate>
					</view>
				</view>
				<img src="./file/phone.png" style="width: 120rpx;height: 120rpx;margin-right: 40rpx;" alt="" @click="phone(zyMess.jlJjlxrdh)">
			</view>
		</view>
		<view class="btn">
			<view class="item" :style="{borderBottom:index===5?'none':'2upx solid #DFE7EE'}" v-for="(item,index) in btnList"
			 :key=index >
				<view style="display: flex;align-items: center;">
					<text>{{item.title}}</text>
				</view>
				{{zyMess.trainPlace.address}}
			</view>
			<view class="item" :style="{borderBottom:index===5?'none':'2upx solid #DFE7EE'}">
				<view style="display: flex;align-items: center;">
					<text>缩略图</text>
				</view>
				<view style="text-align: right;">
					<uni-icon type="eye" size="30" @click="preview()"></uni-icon>
				</view>
				
			</view>
		</view>
		<view class="mapBox">
			<map style="width: 100%; height: 100%;" :scale="10" :latitude="latitude" :longitude="longitude" :markers="covers" @tap="daohang()"></map>
		</view>  
	</view>
</template>

<script>
	import uniIcon from "@/components/uni-icon/uni-icon.vue"
	import uniRate from "@/components/uni-rate/uni-rate.vue"
	export default {
		name: 'JLmess',
		components: {
			uniRate,uniIcon
		},
		computed: {
			zyMess() {
				return this.$store.state.zyMess
			}
		},
		data() {
			return {
				imgUrl: 'http://www.520xclm.com:8001/',
				btnList: [
					{
						title: '地址',
						val: 'trainPlace.address'
					},
				],
				latitude: 30.593001,
				longitude: 114.304504,
				tempFilePaths:[],
				covers: [{
					id:'center',
					title:"武汉市",
					latitude: 30.593001,
					longitude: 114.304504,
					// iconPath: 'http',
					width:200,
					height:200
				}]
			}
		},
		onShow() {
			this.latitude = this.zyMess.trainPlace.latitude
			this.longitude = this.zyMess.trainPlace.longitude
			this.covers[0].latitude = this.latitude
			this.covers[0].longitude = this.longitude
			for(let a of this.zyMess.trainPlace.placeImg.split(',')) {
				if(a!=""){
					
				this.tempFilePaths.push(a)
				}
			}
		},
		methods: {
			preview(res){
				
				                uni.previewImage({  
				                    urls:this.tempFilePaths,  
				                    current:0
				                })  
				// var v = this
				// console.log("112312312312");
				//         uni.previewImage({
				// 			current:1,
				//             urls: v.tempFilePaths,
				//             longPressActions: {
				//                 itemList: ["保存图片"],
				//                 success: function(data) {
				//                     console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
				//                 },
				//                 fail: function(err) {
				//                     console.log(err.errMsg);
				//                 }
				//             }
				//         });
			},
			daohang(){
				var v = this
				uni.openLocation({
				    latitude: v.latitude,
				    longitude: v.longitude,
				    success: function () {
				        console.log('success');
				    }
				});
				// uni.getLocation({
				//     type: 'gcj02', //返回可以用于uni.openLocation的经纬度
				//     success: function (res) {
				//         const latitude = v.latitude;
				//         const longitude = v.longitude;
				// 		
				//        
				//     }
				// });
			},
			phone(id) {
				uni.makePhoneCall({
					phoneNumber: id //仅为示例
				});
			},
		}
	}
</script>

<style lang="less">
	.bg {
		padding-top: 1upx;
		width: 750upx;
		height: 338upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
	}

	.btn {
		width: 678upx;
		// height: 750upx;
		background: rgba(255, 255, 255, 1);
		box-shadow: 4upx 4upx 16upx 0upx rgba(179, 190, 233, 0.5);
		border-radius: 16upx;
		margin: -106upx auto 0;
	}

	.item {
		width: 628upx;
		height: 120upx;
		margin: 0 auto;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.mapBox {
		width: 678upx;
		height: 678upx;
		background: rgba(255, 255, 255, 1);
		box-shadow: 4upx 4upx 16upx 0upx rgba(179, 190, 233, 0.5);
		border-radius: 16upx;
		margin: 10rpx auto 0;
	}
</style>
