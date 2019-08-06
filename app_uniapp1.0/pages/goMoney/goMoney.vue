<template>
	<view>
		<view class="page">
			<view class="image-list">
				<view class="box-row" style="padding: 40rpx;">
					<view style="width:100%; height: 175px; background-color: #F0AD4E;box-shadow:0px 2px 8px 0px rgba(185,73,23,0.5);border-radius:8px;">
						<view class="Tit">学员A套餐</view>
					</view>
					<view style="width: 100%; height: 175px; background-color: #2B81AF;box-shadow:0px 2px 8px 0px rgba(185,73,23,0.5);border-radius:8px;">
						<view class="Tit" style="padding-top: 20px;">学员B套餐</view>
					</view>
				</view>
					<view class="image-content" style="padding: 40rpx;">
						<img style="width: 100%; height: 100%; background-color: #eeeeee;"  src="./file/jf1.png">
						 <img style="width: 100%; height: 100%; background-color: #eeeeee;"  src="./file/jf2.png">
					</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				 array: [{
					mode: 'scaleToFill',
					text: 'file/jf1'
				}],
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
.Tit{
	padding-top: 12px;
	padding-left: 26px;
	font-size:22px;
	font-family:PingFang-SC-Bold;
	font-weight:bold;
	color:rgba(255,255,255,1);
	line-height:30px;
}
.jine{
	padding-bottom: 20px;
	padding-left: 26px;
	font-size:30px;
	font-family:FZZDHJW--GB1-0;
	font-weight:normal;
	color:rgba(255,255,255,1);
	line-height:37px;
}
.nr{
	padding-top: 36px;
	padding-left: 26px;
	font-size:12px;
	font-family:PingFangSC-Regular;
	font-weight:400;
	color:rgba(255,255,255,1);
	line-height:17px;
}
</style>
