<template>
	<view class="box_col" style="width: 100%;">
		
		<view style="">
			<cmd-cell-item title="我的头像" brief="" addon="图片" arrow  @click='upTx'/>
			<cmd-cell-item title="我的昵称" brief=""  addon="156231236442" arrow/>
			<cmd-cell-item title="修改密码" brief="" arrow @click='toxgpwd'/>
		</view>
		<view style="margin: 60rpx;">
			<button type="warn">退出登录</button>
		</view>
		
		<view class="box_col_100">
			
		</view>
		
		<view class="copyright">
			<view>
				Copyright@2018-2019
			</view>
			<view>
				吉驾无忧
			</view>
			<view>
				武汉天弘腾创科技有限公司
			</view>
		</view>
	</view>
	
</template>

<script>
	import cmdCellItem from '@/components/cmd-cell-item/cmd-cell-item.vue'
	export default {
		components: {cmdCellItem},
		data() {
			return {
				
			}
		},
		methods: {
			upTx(){
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: function (res) {
						console.log(JSON.stringify(res.tempFilePaths));
					}
				});
			},
			toxgpwd(){
				uni.navigateTo({
					url:'../xgpwd/xgpwd'
				})
			}
		}
	}
</script>

<style>
 .txItem{
    background-color: #fff;
    padding: 25rpx 25rpx;
    margin-bottom:5rpx;  
    font-size: 30rpx;
  }
   .copyright{
    text-align: center;
    padding-bottom: 120rpx;
  }
</style>
