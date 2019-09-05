<template>
	<view>
		<view class="page">
			<view class="image-list">
				<view class="image-content" style="padding:26upx 40rpx;">
					<view style="position: relative;" v-for="(item,index) in list" @tap="toFile(item)">
						<view style="margin: 32upx 0 0 44upx;position: absolute;">
							<view class="title">
								{{item.cpMc}}
							</view>
							<view class="mess">
								{{item.cpXx}}
							</view>
							<view class="money">
								{{item.cpJl/100}}元
							</view>
						</view>
						<img style="width: 680upx; height: 324upx; background-color: #eeeeee;" :src="'/static/img/goMoney/jf2'+(index%2+1)+'.png'">
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {
		data() {
			return {
				list: []
			}
		},
		computed: mapState([
			'payMess',
			'signUrl'
		]),
		methods: {
			...mapMutations(['setPayMess', 'setSignUrl']),
			imageError: function(e) {
				console.error('image发生error事件，携带值为' + e.detail.errMsg)
			},
			getUser() {
				var v = this
				this.$http.post(this.apis.CPLIST, {}).then(res => {
					if (res.code == 200 && res.result) {
						this.list = res.result
					}else{
						uni.showToast({
							title: res.message,
							duration: 2000
						});
					}
				})
			},
			toFile(item) {
				this.setPayMess(item)						//将此套餐存入vuex中
				if(item.id == '464480253394288641'){
					uni.navigateTo({
						url: '/pages/upStudentImg/index'
					})
				}else{
					uni.navigateTo({
						url: '/pages/center/learnCarFile/learnCarFile'
					})
				}
			}
		},
		onLoad() {
			this.getUser()
		}
	}
</script>

<style>
	.img {
		background-image: url('~@/pages/goMoney/file/jf21.png');
	}

	.title {
		font-size: 44upx;
		font-family: PingFang-SC-Bold;
		font-weight: bold;
		color: rgba(255, 255, 255, 1);
	}

	.mess {
		margin-top: 4upx;
		width: 390upx;
		font-size: 24upx;
		font-family: PingFangSC-Regular;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}

	.money {
		margin-top: 25upx;
		font-size: 60upx;
		font-family: FZZDHJW--GB1-0;
		font-weight: normal;
		color: rgba(255, 255, 255, 1);
	}
</style>
