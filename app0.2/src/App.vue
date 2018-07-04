<template>
  <div id="app" class="box_col" :style="{minHeight:minHeight}">
    <!--<transition name="el-fade-in-linear">-->
      <router-view/>
    <!--</transition>-->
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      minHeight:'0px'
    }
  },
  mounted(){
    this.minHeight = this.getWindowHeight()+'px'
    // console.log('页面高度',this.getWindowHeight())
    // alert(this.getWindowHeight())
  },
  methods:{
    getWindowHeight(){
      var windowHeight = window.innerHeight
      return windowHeight
    }
  }
}
</script>

<style>
  @import "//at.alicdn.com/t/font_683602_c3dtpic8yd6.css";
</style>
