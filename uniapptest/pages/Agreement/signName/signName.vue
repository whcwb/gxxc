<template>
	<view>
		<!-- <vuesigncanvas :options="options" 
		@result="saveResult" @clear="clear" >
		</vuesigncanvas> -->
	</view>
</template>

<script>
	/* import vuesigncanvas from 'vue-sign-canvas'
	require('vue-sign-canvas/dist/vue-sign-canvas.min.css') */
	export default {
		 components:{
			/* vuesigncanvas */
		  },
		data() {
			return {
				options:{
				brushColor: '#ff2600',
				brushWidth: ui.DEFAULT_CONTENT_HEIGHT,
				brushType: 'marker',
				width: 500,
				height: 600,
				shadowEnable: false,
				shadowWidth: 1,
				brushWidth:5,
				canvasBackgroundColor: '#fff',
			  }
			}
		},
		methods: {
			saveResult (data) {
				console.log(data)
			 //  this.$http("POST",ui.getApp().apis.SIGN,{base64Data:data.substring(22)},(res)=>{
				// if(res.code==200){
				//   ui.getApp().signUrl = res.result
				//   this.goBack()
				//   // window.location.href = ui.getApp().signUrl
				//   //http://www.520xclm.com:8001//user_autograph/7cfb610a1ca04ac08e5fb9ae65175db6.png
				// }
			 //  })
			},
			clear () {
			},
			goBack(){
				
			}
		}
	}
</script>

<style lang="less">
.titColor{
  font-size: 30rpx;
  color:#FFA060;
}
</style>
