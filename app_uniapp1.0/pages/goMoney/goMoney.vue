<template>
	<view>
		<view class="page">
			<view class="image-list">
					<view class="image-content" style="padding:26upx 40rpx;">
						<view style="position: relative;" v-for="item in list">
							<view style="margin: 32upx 0 0 44upx;position: absolute;">
								<view class="title">
									{{item.title}}
								</view>
								<view class="mess">
									{{item.mess}}
								</view>
								<view class="money">
									{{item.money}}元
								</view>
							</view>
							<img style="width: 680upx; height: 324upx; background-color: #eeeeee;"  :src="item.imgSrc">							
						</view>
					</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// imgSrc:图片必须存放于static/img/goMoney文件夹中，否则not found此图片
				list:[
					{
						title:'学员A套餐',
						mess:'购买此套餐既可享受学车服务及平台学车推荐服务',
						money:'2600',
						imgSrc:'/static/img/goMoney/jf21.png'
					},
					{
						title:'学员B套餐',
						mess:'购买此套餐既可享受平台学车推荐服务',
						money:'200',
						imgSrc:'/static/img/goMoney/jf22.png'
					}
				]
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
	
	.title{
		font-size:44upx;
		font-family:PingFang-SC-Bold;
		font-weight:bold;
		color:rgba(255,255,255,1);
	}
	
	.mess{
		margin-top: 4upx;
		width:384upx;
		font-size:24upx;
		font-family:PingFangSC-Regular;
		font-weight:400;
		color:rgba(255,255,255,1);
	}
	
	.money{
		margin-top: 25upx;
		font-size:60upx;
		font-family:FZZDHJW--GB1-0;
		font-weight:normal;
		color:rgba(255,255,255,1);
	}
</style>
