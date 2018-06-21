<!--
车辆监控

-->
<style type="text/css">
  #allmap {
    height: 100%;
    width: 100%;
  }
</style>
<!--地图选点-->
<template>
  <div style="height: 100%;background-color: #00FFFF;">
    <div id="allmap"></div>
  </div>
</template>

<script>


  export default {
    name: 'getmapdot',
    components: {
    },
    data() {
      return {
        map: '',
        // mapcenter: {
        //   lng: 114.357527,
        //   lat: 30.550822
        // },
        zoom: 14,
      }
    },
    props:{
      mapcenter: {
        type:Object,
        default:()=>{
          return {
            lng: 114.357527,
            lat: 30.550822
          }
        }
      },
    },
    created() {
    },
    mounted() {
      this.Buildmap()
    },
    methods: {
      Buildmap() {
        var v = this
        // 百度地图API功能
        this.map = new BMap.Map("allmap");    // 创建Map实例
        this.map.centerAndZoom(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat), this.zoom);  // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件
        this.map.addControl(new BMap.MapTypeControl({
            mapTypes: [
              BMAP_NORMAL_MAP
            ]
          })
        );
        this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
        this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
        this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
        this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件

        v.addMarker(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat))
      },
      addMarker(point) {
        var marker = new BMap.Marker(point);
        this.map.addOverlay(marker);
      },
      clear() {
        this.map.clearOverlays();//清楚数据点
      }
    }
  }
</script>
