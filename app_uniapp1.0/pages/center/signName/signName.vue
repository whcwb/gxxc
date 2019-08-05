<template>
	<view>
		<view>
			<!-- <vuesigncanvas options="options" 
			@result="saveResult" @clear="clear" >
			</vuesigncanvas> -->
		</view>
	</view>
</template>

<script>
	// import vuesigncanvas from 'vue-sign-canvas'
	// require('vue-sign-canvas/dist/vue-sign-canvas.min.css')
	export default {
		 config: {
			 backgroundColor:'#ffffff',
		  },
		  components:{
			// vuesigncanvas
		  },
		data() {
			return {
				 options:{
					brushColor: '#ff2600',
					brushWidth: 800,
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
			 //  this.$http("POST",ui.getApp().apis.SIGN,{base64Data:data.substring(22)},(res)=>{
				// if(res.code==200){
				//   ui.getApp().signUrl = res.result
				uni.navigateBack({
					
				})
				// }
			 //  })
			 
			},
			clear () {
			},
		}
	}
</script>

<style lang="less">
.titColor{
  font-size: 30rpx;
  color:#FFA060;
}
</style>
