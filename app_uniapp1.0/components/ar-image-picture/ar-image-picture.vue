<template>
	<view @click="close"  class="ar-image-picture" :class="hasSwiperImage ? 'ar-image-picture-active':''" @touchstart="touch" @touchmove="touch" @touchend="touch" :style="{background:background,'transform':transform,transition:transition}">
		<swiper  :style="{height:screenHeight + 'px'}" :indicator-dots="true" :duration="250" indicator-color="rgba(255,255,255,.3)" indicator-active-color="#ffffff">
			<swiper-item v-for="(value,index) in swiperImages" :key="index" @click="preImg(index)">
				<image :src="value.src" mode="widthFix"></image>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	export default {
		data(){
		 return {
			 screenHeight : 0,
			 background:'#000000',
			 tsY:null,
			 transform :'translate(0,0) scale(1)',
			 aniY : 0,
			 hasTouch : false,
			 transition:'' 
		 }
		},
		props:['swiperImages','hasSwiperImage'],
		 
 
		methods:{
			touch(e){
				switch(e.type){
					case 'touchstart':
					this.touchstart(e)
					break	
					case 'touchmove':
					this.touchmove(e)
					break
					default:
					this.touchend(e)
				}
			},
			touchstart(e){
				this.tsY = e.touches[0].pageY
			},
			touchmove(e){

				var my,y,opacity,ty,scale
				my =  e.touches[0].pageY
				y = my - this.tsY
				
				if(y < 1 ){
					this.background = 'rgba(0,0,0,1)'
					this.transform= 'translate(0, 0) scale(1)'
					this.aniY =0
					this.hasTouch = false
					return
				}
				opacity = (1-y/20)
				ty = y/3
				scale = 1-ty/100
				this.background = 'rgba(0,0,0,'+opacity+')'
				this.transform = 'translate(0,'+ty+'%'+') scale('+scale+')'
				this.aniY = ty
				this.hasTouch = true
				if(y > 30){
					this.close()
				}
			},
			touchend(e){
				var y = this.aniY
				var self = this
				if(y < 100 && self.hasTouch === true){
						self.transition = 'all .3s linear'
						self.transform = 'translate(0,100%) scale(0)'
				}
			},
			close(){
				this.transition = 'all .3s linear'
				this.transform = 'translate(0,100%) scale(0)'
			}
		},
		created() {			
			this.screenHeight = uni.getSystemInfoSync().windowHeight;
		}
	}
</script>

<style scoped>
	.ar-image-picture{
		position: fixed;
		width: 100%;
		height: 100%;
		top: 0;
		left: 0;
		z-index: 100;
		transition: all .2s linear;
		opacity:0;
	}
	.ar-image-picture-active{
		opacity:1
	}
	swiper {
		flex: 1;
		width: 750upx;
		display: flex;
		flex-direction: column;
	}
	
	swiper-item {
		display: flex;
		align-items: center;
	}
	
	image {
		width: 750upx;
		height: 1125upx;
	}
</style>
