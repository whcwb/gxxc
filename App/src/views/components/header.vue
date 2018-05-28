<style lang="less">
  @import "//at.alicdn.com/t/font_605313_aoywq14abad4e7b9.css";
  .box-row{//横向
    display: flex;
    flex-direction: row ;
    .body-O{
      flex:1;
    }
  }
  #boxTit{
    height: 0.8rem;
    line-height: 0.8rem;
    background-color: #2d8cf0;
    text-align: center;
    .left,.right{
      padding: 0 0.2rem;
    }
  }

</style>
<template>
  <div id="boxTit" class="box-row">
    <div class="left">
      <i class="iconfont icon-left"></i>
    </div>
    <div class="body-O">
      center
    </div>
    <div class="right">
      right
    </div>
  </div>

</template>

<script>
    export default {
        name: "boxTit",
        props:{
          titConfig:{
            type:Object,
            default:{
              leftShow:true,
              leftClick:'back',

            }
          }
        }
    }
</script>

<style scoped>

</style>
