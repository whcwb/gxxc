<style lang="less">
  @import "./yj";
</style>
<!--
*****佣金*******
-->
<template>
      <div id="yj" class="box">
        <headTit tit="佣金"></headTit>
        <div class="center_tit">
          <div class="box-row" style="padding-top:0.2rem ">
            <div class="yongjin">
              <div>
                可提现佣金
              </div>
              <div>
                0.00元
              </div>
            </div>
            <div class="body-O tixian" @click="$router.push({name:'yjtxmx'})">
              <Button type="ghost">提现明细</Button>
            </div>
          </div>
        </div>
        <div class="yjType">
          <div class="box-row">
              <div class="body-O">
                可提现佣金
              </div>
              <div>
                0.00元
              </div>
          </div>
          <div class="box-row">
            <div class="body-O">
              可提现佣金
            </div>
            <div>
              0.00元
            </div>
          </div>
        </div>
        <div class="submit" style="padding:0.3rem "
             @click="$router.push({name:'yjtx'})">
          <Button type="error" long
                  style="padding: 0.18rem">
              提现
          </Button>
        </div>
      </div>
</template>

<script>
    import {Button} from 'iview'
    import headTit from '../comp/headTit'
    export default {
        name: "index",
        components:{
          Button,headTit
        },
    }
</script>

<style scoped>

</style>
