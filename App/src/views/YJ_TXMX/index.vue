<style lang="less">
  #yjtxmx{
    .yjmx{
        padding: 0.2rem;
        .list{
          display: none;
          border-bottom:solid 4px #949494;
          padding-top: 0.2rem;
          .box-row{
              font-size: 0.3rem;
              color: #000;
              font-weight: 600;
          }
          .fs{
              margin: 0.2rem 0;
          }
        }
    }
  }

</style>
<template>
      <div id="yjtxmx" class="box">
        <headTit tit="提现明细"></headTit>
        <md-tab-bar
          :titles="titles"
        ></md-tab-bar>
        <div class="body yjmx">
            <div class="list" v-for="item in  [,,,]">
              <div class="box-row">
                <div class="time body-O">
                    <Icon type="ios-clock"></Icon>
                    时间:2018-08-08 09:00:03
                </div>
                <div class="">
                    <Icon type="social-yen"></Icon>
                    500.00元
                </div>
              </div>
              <div class="fs">
                <Icon type="shuffle"></Icon>
                银行卡 8888*********222
              </div>
            </div>
        </div>
      </div>
</template>

<script>
    import headTit from '../comp/headTit'
    import { TabBar } from 'mand-mobile'
    import {Icon} from 'iview'
    export default {
        name: "yjtxmx",
        components:{
          [TabBar.name]: TabBar,
          headTit,Icon
        },
        data() {
          return {
            titles: ['全部', '待审核', '待打款','已大款','无效']
          }
        }
    }
</script>

<style scoped>

</style>
