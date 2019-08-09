<template>
	<view style="width: 100%;">
		<view class="inputMess">
			<input class="uni-input input" v-model="form.yhkXm" focus placeholder="请输入您的真实姓名" />
			<input class="uni-input input" v-model="form.dn" style="border-bottom: none;" focus placeholder="请输入银行卡绑定的手机号码" />
			<input class="uni-input input" v-model="form.yhkKh" style="border-bottom: none;" focus placeholder="请输入银行卡卡号" />
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					yhkXm: '',
					dn: '',
					yhkKh: ''
				}
			}
		},
		methods: {
			saveBankCard() {
				uni.showLoading({
					title:'绑定中'
				})
				this.$http.post(this.apis.ADDBANK, this.form).then(res => {
					if (res.code == 200) {
						uni.hideLoading()
						uni.showToast({
							title: '绑定成功',
							duration: 2000
						});
					} else {
						uni.showToast({
							title: '绑定失败',
							duration: 2000
						});
					}
				})
			}
		},
		onNavigationBarButtonTap() { //点击了添加按钮,验证银行卡
			if (this.form.yhkXm == '' || this.form.yhkKh == '' || this.form.dn == '') {
				uni.showToast({
					title: '填写不完整',
					duration: 2000,
					icon:'none'
				});
				return
			}

			var v = this
			this.$http.post(this.apis.YZYHK, this.form).then(res => {
				if (res.code == 200) {
					uni.showToast({
						title: '验证成功',
						duration: 2000
					});
					setTimeout(() => {
						v.saveBankCard()
					}, 2000);
				} else {
					uni.showToast({
						title: res.message,
						duration: 2000
					});
				}
			})

		}
	}
</script>

<style>
	.inputMess {
		width: 100%;
		height: 276upx;
		background: rgba(255, 255, 255, 1);
	}

	.input {
		height: 136upx;
		width: 678upx;
		border-bottom: 2upx solid #DFE7EE;
		margin: 0 auto;
	}
</style>
