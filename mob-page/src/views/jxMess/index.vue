<style lang="less">
  #jxMess{
    position: relative;
    .icon-leftsquare{
      position: absolute;
      left: 0.2rem;
      top: 0.2rem;
      z-index: 100;
      font-size: 0.5rem;
      color: #aeaeae;
    }
    .swipe{
      height: 5rem;
    }
    .jsmod{
        .modleft{
          text-align: right;
          i{
              font-size: 0.7rem;
          }
          color: #2d8cf0;
        }
        .modright{
          text-align: left;
          padding: 0.08rem 0.2rem 0 0.2rem;
          .num{
            text-align: center;
            font-size: 0.4rem;
            font-weight: 600;
            color: #2d8cf0;
          }
          .text{
            text-align: center;
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
          <mt-swipe :auto="4000">
            <mt-swipe-item>
              <img src="/static/jx1.jpg"
                   style="height: 100%;width: 100%"
                   alt="">
            </mt-swipe-item>
          </mt-swipe>
        </div>
        <div style="padding: 0.3rem 0.2rem;border-bottom: solid 0.3rem #e5e5e5">
            <h1>***驾校</h1>
            <div style="color: #ff9400;position: relative;">
              <i class="iconfont icon-ico-money" style="font-size: 0.35rem;font-weight: 600"></i>
              <span style="font-size: 0.5rem;font-weight: 600">
                2500
              </span>
              <span style="color: #949494">
                门市价：9999
              </span>
              <mt-button
                style="position: absolute;right: 0;bottom: 0;
                       color: #fcffff;font-size: 0.4rem;
                       background: linear-gradient(#ff7e16,#ff4100);"
                >立即抢购</mt-button>
            </div>
        </div>
        <div class="box-row" style="height: 1.6rem;border-bottom: solid 0.3rem #e5e5e5;text-align: center">
            <div class="body-O jsmod">
              <div class="box-row">
                <div class="body-O modleft">
                  <i class="iconfont icon-6zhengjianzhaopian"></i>
                </div>
                <div class="body-O modright">
                  <div class="num">
                      999
                  </div>
                  <div class="text">
                    拿到驾照
                  </div>
                </div>
              </div>
            </div>
            <div class="body-O jsmod">
              <div class="box-row">
                <div class="modleft">
                  <i class="iconfont icon-duoren"></i>
                </div>
                <div class="body-O modright">
                  <div class="num">
                    999
                  </div>
                  <div class="text">
                    月报名数
                  </div>
                </div>
              </div>
            </div>
            <div class="body-O jsmod">
              <div class="box-row">
                <div class="modleft">
                  <i class="iconfont icon-zan"></i>
                </div>
                <div class="body-O modright">
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
        <div>
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
      </div>
</template>

<script>
  import { Swipe, SwipeItem ,Button ,Navbar, TabItem,TabContainer, TabContainerItem } from 'mint-ui';

  export default {
        name: "index",
        components:{
          [Swipe.name]: Swipe,
          [Button.name]: Button,
          [SwipeItem.name]: SwipeItem,
          [Navbar.name]: Navbar,
          [TabItem.name]: TabItem,
          [TabContainer.name]: TabContainer,
          [TabContainerItem.name]: TabContainerItem,
        },
        data(){
          return{
            selected:'1'
          }
        }
    }
</script>

<style scoped>

</style>
