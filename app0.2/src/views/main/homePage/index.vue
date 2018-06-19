<style lang="less">
  #homePage{
    font-size: 0.2rem;
    background-color:#f2f2f2;
    .mint-tab-item-label{
      font-size: 0.16rem!important;
    }
    /*.mint-navbar .mint-tab-item.is-selected{*/
      /*border-bottom: 0.06rem solid #26a2ff;*/
    /*}*/
    /*功能模块*/
    .gnmk{
      background-color: #fff;
      height: 0.6rem;
      text-align: center;
      .box_row_100{
        position: relative;
        .iconfont{
          position: absolute;
          top: 0.08rem;
          left: 50%;
          transform: translate(-50%,0);
          font-size: 0.25rem;
          color: #0c98f9;
        }
        .text{
          font-size: 0.12rem;
          position: absolute;
          bottom: 0.05rem;
          left: 50%;
          transform: translate(-50%,0);
        }
      }
    }

    /*模块样式*/
    .moSty{
      padding: 0.15rem;
      background-color: #fff;
      margin-top: 0.2rem;
    }
    /*考试秘籍*/
    .ksmj{
    }
    /*优惠*/
    .ksyh{

    }
    /*邀请好友*/
    .yqhy{
      .yqhy_mess{
        background-color: #0a7dda;
        border-radius: 0.1rem;
        margin-top: 0.12rem;
        padding: 0.15rem;
        .num{
          color: #fff;
        }
        .txt{
          color: #4aa3f7;
          font-size: 0.15rem;
          margin-top: 0.05rem;
        }
      }
    }
    /*训练厂List*/
    .xlcList{
      padding: 0 0.15rem;
      .list{
        .iconfont{
          color:#029ded ;
        }
        .xlcMess{
          font-size: 0.18rem;
          .name{
          }
          .money{
            color: #029ded;
          }
        }
        .xlcmap{
          padding: 0.05rem 0;
          .icon{
            margin-right: 0.1rem;
          }
          .mess{
            font-size: 0.16rem;
          }

        }
        .xlcBus{
          .icon{
            margin-right: 0.1rem;
          }
          .mess{
            font-size: 0.16rem;
          }
        }
      }
    }
  }
</style>
<template>
      <div id="homePage" class="box_col">
        <div class="box_col_auto" style="margin-bottom: 0.1rem">
          <div style="height: 1.4rem;background-color: bisque;text-align: center">
            <mt-swipe :auto="4000">
              <mt-swipe-item>1</mt-swipe-item>
              <mt-swipe-item>2</mt-swipe-item>
              <mt-swipe-item>3</mt-swipe-item>
            </mt-swipe>
          </div>
          <!--功能模块-->
          <div class="box-row gnmk">
            <div class="box_row_100">
              <i class="iconfont icon-fenlei"></i>
              <div class="text">
                训练场地
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-liuchengxiangdao"></i>
              <div class="text">
                学车流程
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-shuben"></i>
              <div class="text">
                学车指引
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-ditu"></i>
              <div class="text">
                考场分布
              </div>
            </div>
          </div>
          <!--考试秘籍-->
          <div class="ksmj moSty">
            <div>
              Hi,周周
            </div>
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 margin-top: 0.15rem;font-size: 0.16rem">
                <div class="box_row_100">
                  老夫送你一个考试秘籍，快来来看看
                </div>
                <div style="color: #7f7f7f">
                  更多
                </div>
            </div>
            <div style="margin-top: 0.15rem;
            background: linear-gradient(to bottom right, #fff , #dbecfe)">
              <img src="static/home/00_03.png" alt="" style="width: 100%"
                @click="goLook">
            </div>
          </div>
          <!--优惠-->
          <div class="ksyh moSty">
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 margin-top: 0.15rem;font-size: 0.16rem">
              <div class="box_row_100">
                老夫送你一个优惠，你要不要
              </div>
              <!--<div style="color: #7f7f7f">-->
                <!--更多-->
              <!--</div>-->
            </div>
            <div class="box-row">
              <div class="box_row_100" style="margin: 0.15rem">
                <img src="static/home/66_03.png"
                     style="width: 100%"
                     alt="">
              </div>
              <div class="box_row_100" style="margin: 0.15rem">
                <img src="static/home/66_05.png"
                     style="width: 100%"
                     alt="">
              </div>
            </div>
          </div>
          <!--邀请好友-->
          <div class="yqhy moSty" style="">
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 font-size: 0.16rem">
              <div class="box_row_100">
                听说邀请好友还有现金，速速围观
              </div>
            </div>
            <div class="yqhy_mess box-row">
              <div class="box_row_100">
                <div class="num">
                  1500元
                </div>
                <div class="txt">
                  累计奖励
                </div>
              </div>
              <div class="box_row_100">
                <div class="num">
                  3人
                </div>
                <div class="txt">
                  已邀请
                </div>
              </div>
              <div class="" style="padding: 0.08rem 0.05rem 0 0 ">
                <i class="iconfont icon-erweima"
                   style="font-size: 0.3rem;color: #f2f2f2"
                ></i>
              </div>
            </div>
          </div>


          <div style="background-color: #fff">
            <div class="moSty" style="padding: 0;margin: 0 0.15rem">
              <mt-navbar v-model="selected">
                <mt-tab-item id="1">推荐</mt-tab-item>
                <mt-tab-item id="2">区域</mt-tab-item>
                <mt-tab-item id="3">价格</mt-tab-item>
              </mt-navbar>
            </div>
            <div class="xlcList" style="margin-top: 0.1rem">
                <div class="list box-row">
                    <div style="width: 1.2rem">
                      <img src="static/home/car0000.png"
                           style="width: 100%"
                           alt="">
                    </div>
                    <div class="box_row_100" style="padding-left: 0.1rem">
                        <div class="box-row xlcMess">
                           <div class="box_row_100 name">
                            ***驾校训练厂
                           </div>
                           <div class="money">
                            ￥25000.00
                           </div>
                        </div>
                        <div class="box-row xlcmap">
                          <div class="icon">
                            <i class="iconfont icon-dingwei"></i>
                          </div>
                          <div class="box_row_100 mess">
                            光谷大道光谷一路52号
                          </div>
                        </div>
                        <div class="box-row xlcBus">
                          <div class="icon">
                            <i class="iconfont icon-bus"></i>
                          </div>
                          <div class="box_row_100 mess">
                            566、567、568
                          </div>
                        </div>
                    </div>
                </div>
            </div>
          </div>
        </div>
      </div>
</template>

<script>
    import {Toast} from 'mint-ui'
    export default {
      name: "index",
      components:{

      },
      data(){
          return{
            selected:'1'
          }
      },
      created(){

      },
      methods:{
        goLook(){
          Toast('去看看');
        }
      }
    }
</script>

<style scoped>

</style>
