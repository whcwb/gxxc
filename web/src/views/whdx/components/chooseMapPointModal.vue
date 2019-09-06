<style type="text/css">
	#allmap{
		height: 100%;
		width: 100%;
	}
</style>
<!--地图选点-->
<template>
	<div>
	<Modal v-model="showModal" width='900' :closable="false" :mask-closable="false"  title="选取位置">
		<Row>
			<Col span="2"><label>经度：</label></Col>
			<Col span="10">
				<Input v-model="point.lat"></Input>
			</Col>
			<Col span="2"><label>纬度：</label></Col>
			<Col span="10">
				<Input v-model="point.lng"></Input>
			</Col>
		</Row>
		<br>
		<Row>
			<div style="height: 400px;background-color: #00FFFF">
				<div id="allmap"></div>
			</div>
		</Row>

		<div slot='footer'>
			<Button type="default" @click="close">取消</Button>
			<Button type="primary" @click="ok">确定</Button>
		</div>
	</Modal>
	</div>
</template>

<script>
	export default {
		name:'chooseMapPoint',
		data(){
			return {
                showModal:true,
				map:'',
				mapcenter:{
					lng: 114.357527,
	    			lat: 30.550822
				},
                point:{
                    lat:'',
					lng:''
				},
				zoom:16,
			}
		},

		created(){

        },
		mounted(){
            this.showModal = true;
            if (this.$parent.choosedPoint){
                this.point = this.$parent.choosedPoint;
            }
			this.Buildmap()
		},
		methods:{
			Buildmap(){
				var v = this
				// 百度地图API功能
				this.map = new BMap.Map("allmap");    // 创建Map实例
				this.map.centerAndZoom(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat),this.zoom);  // 初始化地图,设置中心点坐标和地图级别
				//添加地图类型控件
			    this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
			    this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
			    this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
			    this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件

                if (this.$parent.choosedPoint){
                    this.addPoint(this.point.lng,this.point.lat);
                }
			    this.map.addEventListener("click",function(e){
					v.map.clearOverlays();//清楚数据点
                    v.addPoint(e.point.lng,e.point.lat);
                    v.point = e.point;
				});

			},
			addPoint(lng,lat){
                this.map.clearOverlays();//清楚数据点
                let newMapDot = new BMap.Point(lng, lat);//点数据
                var marker = new BMap.Marker(newMapDot); // 创建点
                this.map.addOverlay(marker);
			},
            ok(){
                this.$emit('choosePoint',this.point)
				this.close()
			},
            close(){
                let v = this;
                v.showModal = false;
                setTimeout(() => {
                    v.$parent.$data.componentName = "";
                }, 200)
			}
		}
}
</script>