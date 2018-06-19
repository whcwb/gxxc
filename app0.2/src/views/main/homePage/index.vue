<style lang="less">
  #homePage{
    /*功能模块*/
    .gnmk{
      text-align: center;position: relative;
      .iconfont{
        position: absolute;
        top: 0.08rem;
        left: 50%;
        transform: translate(-50%,0);
        font-size: 0.25rem;
      }
      .text{
        font-size: 0.15rem;
        position: absolute;
        bottom: 0.05rem;
        left: 50%;
        transform: translate(-50%,0);
      }
    }
  }
</style>
<template>
      <div id="homePage" class="box_col">
        <div class="box_col_auto">
          <div style="height: 1.4rem;background-color: bisque;text-align: center">
            <mt-swipe :auto="4000">
              <mt-swipe-item>1</mt-swipe-item>
              <mt-swipe-item>2</mt-swipe-item>
              <mt-swipe-item>3</mt-swipe-item>
            </mt-swipe>
          </div>
          <!--功能模块-->
          <div class="box-row gnmk" style="height: 0.6rem;background-color: aqua;text-align: center">
            <div class="box_row_100">
              <i class="iconfont icon-fenlei"></i>
              <div class="text">
                12
              </div>
            </div>
            <div class="box_row_100">

            </div>
            <div class="box_row_100">

            </div>
            <div class="box_row_100">

            </div>
          </div>
        </div>
      </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>

</style>
