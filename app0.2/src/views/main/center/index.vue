<style lang="less">
  #myCenterHome{
    font-size: 0.2rem;
    background-color: #f2f2f2;
    .box_header{
      background-color: #0676d8;
      height: 1.0rem;
      .touxiang{
        width: 0.8rem;
        position: relative;
        img{
          width: 0.6rem;
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%,-50%);
        }
      }
      .box_cen{
        padding: 0.2rem;
        font-size: 0.18rem;
        color: #fff;
      }
      .ewCode{
        width: 0.8rem;
        text-align: center;
        color: #fff;
        line-height: 1rem;
      }
    }
    .box_money{
      background-color: #fff;
      padding: 0.2rem 0.2rem 0.2rem 0.3rem;
      .num{
        .ut{
          font-size: 0.1rem;
        }
      }
      .txt{
        font-size: 0.15rem;
        margin-top: 0.05rem;
        color: #7f7f7f;
      }
    }
  }
</style>
<template>
      <div id="myCenterHome" class="box_col">
        <div class="box_header box-row">
          <div class="touxiang">
            <img src="static/login/LOGO.png" alt="">
          </div>
          <div class="box_row_100 box_cen">
            <div>
              周晓涵
            </div>
            <div style="font-size: 0.14rem">
              学员
            </div>
          </div>
          <div class="ewCode">
            <i class="iconfont icon-erweima"
            style="font-size: 0.3rem"
            ></i>
          </div>
        </div>
        <div class="box_money box-row">
          <div class="box_row_100">
            <div class="num">
              3000.00 <span class="ut">元</span>
            </div>
            <div class="txt">
              账户余额
            </div>
          </div>
          <div class="box_row_100">
            <div class="num">1500.00<span class="ut">元</span>
            </div>
            <div class="txt">邀请奖励
            </div>
          </div>
          <div class="box_row_100">
            <div class="num">3<span class="ut">人</span>
            </div>
            <div class="txt">
              邀请人数
            </div>
          </div>
        </div>

        <div style="margin-top: 0.2rem">
          <mt-cell title="账单" value=""
                   to="/myCenter-sfrz"
                   is-link style="border-bottom: 1px #e9eaec solid;">
            <i class="iconfont icon-xiaoxizhangdanfill"
               style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
               slot="icon"></i>
            <!--<span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">-->
                  <!--<Icon v-if="usermess.yhZt=='1'"-->
                        <!--type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>-->
          </mt-cell>
          <mt-cell title="团队" value=""
                   to="/myCenter-sfrz"
                   is-link style="border-bottom: 1px #e9eaec solid;">
            <i class="iconfont icon-tuandui"
               style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
               slot="icon"></i>
            <!--<span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">-->
            <!--<Icon v-if="usermess.yhZt=='1'"-->
            <!--type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>-->
          </mt-cell>
        </div>
        <div style="margin-top: 0.2rem">
          <mt-cell title="实名认证" value="未认证"
                   to="/myCenter-sfrz"
                   is-link style="border-bottom: 1px #e9eaec solid;">
            <i class="iconfont icon-anquan"
               style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
               slot="icon"></i>
            <!--<span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">-->
            <!--<Icon v-if="usermess.yhZt=='1'"-->
            <!--type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>-->
          </mt-cell>
          <mt-cell title="实名认证" value="未认证"
                   to="/myCenter-sfrz"
                   is-link style="border-bottom: 1px #e9eaec solid;">
            <i class="iconfont icon-anquan"
               style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
               slot="icon"></i>
            <!--<span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">-->
            <!--<Icon v-if="usermess.yhZt=='1'"-->
            <!--type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>-->
          </mt-cell>
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
          this.util.auto(window, document ,4)
        }
    }
</script>

<style scoped>

</style>
