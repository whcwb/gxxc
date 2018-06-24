<style lang="less">
  #jxMess{
    font-size: 0.2rem;
    position: relative;
    .icon-leftsquare{
      position: absolute;
      left: 0.2rem;
      top: 0.2rem;
      z-index: 100;
      font-size: 0.3rem;
      color: #aeaeae;
    }
    .swipe{
      height: 2rem;
    }
    .jsmod{
        .modleft{
          text-align: right;
          padding-top:0.1rem ;
          i{
              font-size: 0.35rem;
          }
          color: #2d8cf0;
        }
        .modright{
          text-align: left;
          padding: 0.08rem 0.1rem 0 0.1rem;
          .num{
            text-align: center;
            font-size: 0.2rem;
            font-weight: 600;
            color: #2d8cf0;
          }
          .text{
            text-align: center;
            font-size: 0.13rem;
          }
        }
    }
    .navbar{
      .mint-tab-item-label{
          font-size: 0.3rem;
      }
    }
  }

</style>
<template>
      <div id="jxMess" class="box">
        <i class="iconfont icon-leftsquare"
            @click="$router.back()"></i>
        <div class="swipe">
          <!--static/home/car0000.png-->
          <mt-swipe :auto="4000">
            <mt-swipe-item v-for="item in [,,]">
              <img src="static/home/car0000.png"
                   style="height: 100%;width: 100%"
                   alt="">
            </mt-swipe-item>
          </mt-swipe>
        </div>
        <div style="padding: 0.15rem 0.2rem;border-bottom: solid 0.3rem #e5e5e5">
            <span style="font-size: 0.25rem;font-weight: 600">
              {{xlcmess.schoolName}}
            </span>
            <div style="color: #ff9400;position: relative;">
              <i class="iconfont icon-ico-money" style="font-size: 0.3rem;font-weight: 600"></i>
              <span style="font-size: 0.3rem;font-weight: 600">
                2500
              </span>
              <span style="color: #949494;font-size: 0.18rem">
                门市价：9999
              </span>
              <mt-button
                style="position: absolute;right: 0;bottom: 0;
                       color: #fcffff;font-size: 0.2rem;
                       background: linear-gradient(#ff7e16,#ff4100);"
                >立即抢购</mt-button>
            </div>
        </div>
        <div class="box-row" style="padding: 0.1rem 0.02rem;border-bottom: solid 0.3rem #e5e5e5;text-align: center">
            <div class="box_row_100 jsmod">
              <div class="box-row">
                <div class="box_row_100 modleft">
                  <i class="iconfont icon-6zhengjianzhaopian"></i>
                </div>
                <div class="box_row_100 modright">
                  <div class="num">
                      999
                  </div>
                  <div class="text">
                    拿到驾照
                  </div>
                </div>
              </div>
            </div>
            <div class="box_row_100 jsmod">
              <div class="box-row">
                <div class="modleft">
                  <i class="iconfont icon-duoren"></i>
                </div>
                <div class="box_row_100 modright">
                  <div class="num">
                    999
                  </div>
                  <div class="text">
                    月报名数
                  </div>
                </div>
              </div>
            </div>
            <div class="box_row_100 jsmod">
              <div class="box-row">
                <div class="modleft" style="padding-top: 0.13rem">
                  <i class="iconfont icon-zan" style="font-size: 0.28rem"></i>
                </div>
                <div class="box_row_100 modright">
                  <div class="num">
                    666
                  </div>
                  <div class="text">
                    点赞
                  </div>
                </div>
              </div>
            </div>
        </div>
        <div v-show="false">
          <div class="navbar">
            <mt-navbar v-model="selected">
              <mt-tab-item id="1">费用明细</mt-tab-item>
              <mt-tab-item id="2">驾校详情</mt-tab-item>
              <mt-tab-item id="3">优劣分析</mt-tab-item>
            </mt-navbar>
          </div>
          <mt-tab-container v-model="selected" style="text-align: center">
            <mt-tab-container-item id="1">
              <h1>
               费用明细
              </h1>
            </mt-tab-container-item>
            <mt-tab-container-item id="2">
              <h1>
               驾校详情
              </h1>
            </mt-tab-container-item>
            <mt-tab-container-item id="3">
              <h1>
               优劣分析
              </h1>
            </mt-tab-container-item>
          </mt-tab-container>
        </div>
        <div id="getmap" style="height: 3rem;background-color: #2b85e4">
          <mapCode :mapcenter="{'lng':xlcmess.latitude,'lat':xlcmess.longitude}"></mapCode>
        </div>
      </div>
</template>

<script>
  import mapCode from '../../../components/map/showCode'
  import { Toast } from 'mint-ui';
  export default {
        name: "index",
        components:{
          mapCode
        },
        data(){
          return{
            selected:'1',
            xlcmess:{}
          }
        },
        created(){
          console.log(this.$route.params)
          if(this.$route.params.address){
            this.xlcmess = this.$route.params
          }else {
            Toast('请重新选着训练场')
            this.$router.push({path:'/Home'})
          }
        }
    }
</script>

<style scoped>

</style>
