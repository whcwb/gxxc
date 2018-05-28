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
  <div class="box" style="height: 100%;background-color: #00FFFF;">
    <div id="allmap"></div>
  </div>
</template>

<script>

  export default {
    name: 'map',
    components: {
    },
    data() {
      return {
        componentName: '',
        choosedItem: null,
        map: '',
        mapcenter: {
          lng: 114.357527,
          lat: 30.550822
        },
        zoom: 12,
        carList: [],
        zoomDot:[],
        fancePoints: [
          {lng: 114.27226, lat: 30.608123},
          {lng: 114.157277, lat: 30.544446},
          {lng: 114.418288, lat: 30.526529},
        ]
      }
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
      },
      //撒点
      showCarPosition() {
        this.clear()
        var v = this
        for (let r of this.carList) {
          log('showCarPosition');
          log(r.lng);
          log(r.lat);
          var point = new BMap.Point(r.lng, r.lat);
          this.addMarker(r, point);
          this.addLabel(r, point);
        }
      },
     clear() {
        this.map.clearOverlays();//清楚数据点
      }
    }
  }
</script>
