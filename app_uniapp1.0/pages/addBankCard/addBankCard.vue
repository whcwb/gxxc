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
				console.log('绑定中')
				this.$http.post(this.apis.ADDBANK, this.form).then(res => {
					if (res.code == 200) {
						console.log('绑定成功')
					} else {
						console.log('绑定失败')
					}
				})
			}
		},
		onNavigationBarButtonTap() { //点击了添加按钮,验证银行卡
			if (this.form.yhkXm == '' || this.form.yhkKh == '' || this.form.dn == '') {
				console.log('填写不完整')
				return
			}

			var v = this
			this.$http.post(this.apis.YZYHK, this.form).then(res => {
				if (res.code == 200) {
					console.log("验证成功")
					setTimeout(() => {
						v.saveBankCard()
					}, 1500);
				} else {
					console.log('验证失败')
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
