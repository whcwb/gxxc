<style lang="less">
  #payOk{
    background-color: #fff;
    position: relative;
    text-align: center;
    .icon-duihao2{
      font-size: 1.2rem;
      color: #2b85e4;
      /*position: absolute;*/
      /*top: 25%;*/
      /*left: 50%;*/
      /*transform: translate(-50%,-50%);*/
    }
    .txt{
      font-size: 0.22rem;
      color: #999999;
    }
  }
</style>
<template>
  <div id="payOk" class="box_col">
    <div style="padding: 1rem 0 0.2rem 0">
        <i class="iconfont icon-duihao2"></i>
    </div>
    <div class="txt">
      缴费成功
    </div>
    <div class="box_col_100">

    </div>
    <div style="height: 2.5rem">
      <div class="box-row">
        <div class="box_row_100">
          <el-button round
             @click="$router.push({name:'Home'})"
             style="font-size: 0.2rem;padding: 0.1rem 0.6rem;border-radius: 0.5rem"
          >返回</el-button>
        </div>
        <div class="box_row_100">
          <el-button type="primary" round
                     @click="$router.push({name:'bill'})"
                     style="font-size: 0.2rem;padding: 0.1rem 0.6rem;border-radius: 0.5rem"
          >账单</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    components:{

    },
    data(){
      return{

      }
    },
    created(){
      this.util.auto(window, document , 4)
    }
  }
</script>

<style scoped>

</style>
