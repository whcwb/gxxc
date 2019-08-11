<template>
	<view>
		<view>
			<vuesigncanvas options="options" @result="saveResult" @clear="clear">
			</vuesigncanvas>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import vuesigncanvas from 'vue-sign-canvas'
	require('vue-sign-canvas/dist/vue-sign-canvas.min.css')
	export default {
		config: {
			backgroundColor: '#ffffff',
		},
		computed: mapState(['signUrl']),
		components: {
			vuesigncanvas
		},
		data() {
			return {
				options: {
					brushColor: '#ff2600',
					brushWidth: 800,
					brushType: 'marker',
					width: 500,
					height: 600,
					shadowEnable: false,
					shadowWidth: 1,
					brushWidth: 5,
					canvasBackgroundColor: '#fff',
				}
			}
		},
		mounted() {
			this.$refs.sign.$el.childNodes[2].childNodes[0].style.width = '80px';
			this.$refs.sign.$el.childNodes[2].childNodes[2].style.width = '80px';
			this.$refs.sign.$el.childNodes[2].childNodes[0].style.height = '50px';
			this.$refs.sign.$el.childNodes[2].childNodes[2].style.height = '50px';
		},
		methods: {
			...mapMutations(['setSignUrl']),
			saveResult(data) {
				this.$http.post(this.apis.SIGN, {
					base64Data: data.substring(22)
				}).then(res => {
					if (res.code == 200) {
						this.setSignUrl(res.result)
						uni.navigateBack({

						})
					}
				})

			},
			clear() {},
		}
	}
</script>

<style lang="less">
	.titColor {
		font-size: 30rpx;
		color: #FFA060;
	}
</style>
