<template>
	<view class="mask" :class="mask == 'true' || mask == true ? 'mask-show' : ''" @click="Mclose" v-if="show"
	 @touchmove.stop.prevent="preventTouchMove">
		<!-- 加载动画开始 -->
		<!-- loading1~loading30挑选任意一个替换下方html 且替换对应css -->
		<view style="position: fixed;bottom: 15%;display: flex;align-items: center;flex-direction: column;">
			<view class="loader">
				<view class="dot dot1"></view>
				<view class="dot dot2"></view>
				<view class="dot dot3"></view>
				<view class="dot dot4"></view>
			</view>
			<!-- 加载动画结束 -->
			<view class="title">{{text}}</view>
		</view>

	</view>
	<!-- 遮罩层-->
</template>

<script scoped="true">
	export default {
		name: "w-loading",
		props: {
			text: String,
			mask: Boolean | String,
			click: Boolean | String,
		},
		data() {
			return {
				show: false
			};
		},
		methods: {
			preventTouchMove() {
				console.log('stop user scroll it!');
				return;
			},
			Mclose() {
				if (this.click == 'false' || this.click == false) {
					this.show = false
				}
			},
			open() {
				this.show = true
			},
			close() {
				this.show = false
			}
		}
	};
</script>

<style>
	.mask {
		/* pointer-events: none; */
		position: fixed;
		z-index: 99999;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		height: 100vh;
		width: 100vw;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		flex-wrap: wrap;
	}

	.mask.mask-show {
		background: rgba(7, 17, 27, .3);
	}

	.title {
		color: #fff;
		/* margin-top: 130%; */
		font-size: 28upx;
	}





	/* loading加载动画的css */
	.loader {
		color: #eee;
		-webkit-animation: rotate 3.33334s infinite linear;
		animation: rotate 3.33334s infinite linear;
		font-size: 200%;
		height: 45upx;
		line-height: 0;
		top: 80%;
		left: 50%;
		/* position: absolute; */
		width: 45upx;
	}

	@-webkit-keyframes rotate {
		from {
			-webkit-transform: translate(-50%, -50%) rotate(360deg);
			transform: translate(-50%, -50%) rotate(360deg);
		}

		to {
			-webkit-transform: translate(-50%, -50%) rotate(0deg);
			transform: translate(-50%, -50%) rotate(0deg);
		}
	}

	@keyframes rotate {
		from {
			-webkit-transform: translate(-50%, -50%) rotate(360deg);
			transform: translate(-50%, -50%) rotate(360deg);
		}

		to {
			-webkit-transform: translate(-50%, -50%) rotate(0deg);
			transform: translate(-50%, -50%) rotate(0deg);
		}
	}

	.dot {
		background-color: currentColor;
		border-radius: 50%;
		display: inline-block;
		height: 7.5upx;
		position: absolute;
		width: 7.5upx;
		vertical-align: top;
	}

	.dot::after {
		content: "";
		display: inline-block;
		background-color: currentColor;
		position: absolute;
	}

	.dot1 {
		left: 0;
		top: 0;
	}

	.dot1::after {
		-webkit-animation: scaleH 1.66667s -1.25s infinite both linear;
		animation: scaleH 1.66667s -1.25s infinite both linear;
		width: 30uxp;
		height: 1upx;
		top: 3.75upx;
		left: 7.5upx;
	}

	.dot2 {
		bottom: 0;
		left: 0;
	}

	.dot2::after {
		-webkit-animation: scaleV 1.66667s -1.25s infinite reverse both linear;
		animation: scaleV 1.66667s -1.25s infinite reverse both linear;
		width: 1upx;
		height: 30upx;
		bottom: 7.5upx;
		left: 3.75upx;
	}

	.dot3 {
		bottom: 0;
		right: 0;
	}

	.dot3::after {
		-webkit-animation: scaleH 1.66667s -1.66667s infinite reverse both linear;
		animation: scaleH 1.66667s -1.66667s infinite reverse both linear;
		width: 30upx;
		height: 1upx;
		bottom: 3.75upx;
		right: 7.5upx;
	}

	.dot4 {
		top: 0;
		right: 0;
	}

	.dot4::after {
		-webkit-animation: scaleV 1.66667s -0.83334s infinite both linear;
		animation: scaleV 1.66667s -0.83334s infinite both linear;
		height: 20upx;
		width: 1upx;
		top: 7.5upx;
		right: 3.75upx;
	}

	@-webkit-keyframes scaleV {
		from {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		12% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		13% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}

		25% {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}

		100% {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}
	}

	@keyframes scaleV {
		from {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		12% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		13% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}

		25% {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}

		100% {
			-webkit-transform: scale(1, 0);
			transform: scale(1, 0);
			-webkit-transform-origin: 0% 100%;
			transform-origin: 0% 100%;
		}
	}

	@-webkit-keyframes scaleH {
		from {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		12% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		13% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}

		25% {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}

		100% {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}
	}

	@keyframes scaleH {
		from {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		12% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 0% 0%;
			transform-origin: 0% 0%;
		}

		13% {
			-webkit-transform: scale(1, 1);
			transform: scale(1, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}

		25% {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}

		100% {
			-webkit-transform: scale(0, 1);
			transform: scale(0, 1);
			-webkit-transform-origin: 100% 0%;
			transform-origin: 100% 0%;
		}
	}

	@-webkit-keyframes pulse {
		from {
			-webkit-transform: scale(0.1);
			transform: scale(0.1);
		}

		12.5% {
			-webkit-transform: scale(1);
			transform: scale(1);
		}

		25% {
			-webkit-transform: scale(0.1);
			transform: scale(0.1);
		}
	}

	@keyframes pulse {
		from {
			-webkit-transform: scale(0.1);
			transform: scale(0.1);
		}

		12.5% {
			-webkit-transform: scale(1);
			transform: scale(1);
		}

		25% {
			-webkit-transform: scale(0.1);
			transform: scale(0.1);
		}
	}
</style>
