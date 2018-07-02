<style lang="less">
  #myCenterHome {
    font-size: 0.2rem;
    background-color: #f2f2f2;
    .box_header {
      background-color: #0676d8;
      height: 1.0rem;
      .touxiang {
        width: 0.8rem;
        position: relative;
        img {
          width: 0.6rem;
          height: 0.6rem;
          border-radius: 0.3rem;
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
        }
      }
      .box_cen {
        padding: 0.2rem;
        font-size: 0.18rem;
        color: #fff;
      }
      .ewCode {
        width: 0.8rem;
        text-align: center;
        color: #fff;
        line-height: 1rem;
      }
    }
    .box_money {
      background-color: #fff;
      padding: 0.2rem 0.2rem 0.2rem 0.3rem;
      .num {
        .ut {
          font-size: 0.1rem;
        }
      }
      .txt {
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
      <div class="touxiang" @click="$router.push({name:'myCenterInfo'})">
        <img :src="usermess.yhTx" alt="">
      </div>
      <div class="box_row_100 box_cen">
        <div>
          {{usermess.yhXm | yhXm}}
        </div>
        <div class="box-row" style="margin-top: 0.15rem">
          <div>
            <i class="iconfont icon-dianhua"></i>
          </div>
          <div>
            {{usermess.yhZh}}
          </div>
        </div>
        <!--<div style="font-size: 0.14rem;margin-top: 0.15rem">-->
        <!--<el-tag type="danger"size="small"-->
        <!--v-if="usermess.yhZt=='0'"-->
        <!--&gt;未实名</el-tag>-->
        <!--<el-tag type="success" size="small"-->
        <!--v-if="usermess.yhZt=='1'&&usermess.yhLx=='1'"-->
        <!--&gt;学员</el-tag>-->
        <!--<el-tag type="success" size="small"-->
        <!--v-if="usermess.yhZt=='1'&&usermess.yhLx=='2'"-->
        <!--&gt;教练</el-tag>-->
        <!--</div>-->
      </div>
      <div class="ewCode" @click="showQrcode('ewm')">
        <i class="iconfont icon-erweima"
           style="font-size: 0.3rem"
        ></i>
      </div>
    </div>
    <div class="box_money box-row">
      <div class="box_row_100">
        <div class="num">
          {{zhYE.yhZhye/100 | yhZhye}} <span class="ut">元</span>
        </div>
        <div class="txt">
          账户余额
        </div>
      </div>
      <div class="box_row_100">
        <div class="num">
          {{zhYE.yhZhye/100 | yhZhye}}<span class="ut">元</span>
        </div>
        <div class="txt">邀请奖励
        </div>
      </div>
      <div class="box_row_100" style="text-align: right">
        <div class="box-row">
          <div class="box_row_100" style="margin: 0.05rem">
            <el-button type="primary"
                       v-if="usermess.ddSfjx=='0'&&usermess.yhZt=='1'"
                       @click="$router.push({name:'productList'})"
            >缴费
            </el-button>
            <el-button type="primary"
                       v-else-if="usermess.ddSfjx=='1'"
                       @click="okjf"
            >已缴费
            </el-button>
            <el-button type="primary"
                       v-else
                       @click="tost"
            >缴费
            </el-button>
          </div>
          <div class="box_row_100" style="margin: 0.05rem">
            <el-button type="success"
                       v-if="usermess.ddSfjx=='1'"
                       @click="$router.push({name:'tx'})"
            >提现
            </el-button>
            <el-button type="success"
                       v-else
                       @click="txyz"
            >提现
            </el-button>
          </div>
        </div>

      </div>
    </div>

    <div style="margin-top: 0.2rem">
      <mt-cell title="我的账单" value=""
               to="/bill"
               is-link style="border-bottom: 1px #e9eaec solid;">
        <i class="iconfont icon-xiaoxizhangdanfill"
           style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
           slot="icon"></i>
      </mt-cell>
      <mt-cell title="我的团队" value=""
               to="/myTeam"
               is-link style="border-bottom: 1px #e9eaec solid;">
        <i class="iconfont icon-tuandui"
           style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
           slot="icon"></i>
      </mt-cell>
    </div>
    <div style="margin-top: 0.2rem">
      <mt-cell title="实名认证" value="未认证"
               to="/smrz"
               is-link style="border-bottom: 1px #e9eaec solid;">
        <i class="iconfont icon-anquan"
           style="font-size: 20px;color: #2d8cf0;margin-left: 0.1rem"
           slot="icon"></i>
        <span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">
            <span v-if="usermess.yhZt=='1'"
            ></span>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>
      </mt-cell>
    </div>
    <!--<div style="position: fixed;z-index: 1000">-->
    <!--<sign-canvas @saveResult="saveResult"></sign-canvas>-->
    <!--</div>-->
  </div>
</template>

<script>
  import {Toast} from 'mint-ui';
  import signCanvas from '../../components/canvas/signCanvas'

  export default {
    name: "index",
    components: {
      signCanvas
    },
    filters: {
      yhXm: (val) => {
        if (val) {
          return val
        }
        return '未实名'
      },
      yhZhye(val) {
        if (val == '') {
          return 0
        }
        return val
      },
      yhZt: function (val) {
        switch (val) {
          case '0':
            return '审核中';
            break;
          case '1':
            return '已认证';
            break;
          case '2':
            return '审核驳回';
            break;
          case '-1':
            return '未认证';
            break;
          default:
            return val
            break;
        }
      },
    },
    data() {
      return {
        usermess: this.$store.state.app.userMess,
        //   {
        //   yhTx: ''
        // },
        zhYE: {
          yhZhye: 0
        }
      }
    },
    created() {
      var v = this
      // if(v.usermess===''){
      this.util.GetUserMess(v, (res) => {
        this.usermess = res
      })
      // }
      this.getYE()
    },
    methods: {
      saveResult(mes) {
        alert('传递' + mes)
      },
      showQrcode(val) {
        var v = this
        if (val == 'ewm') {
          if (v.usermess.ddSfjx == '0') {
            Toast('请先缴费')
          } else {
            this.$router.push({name: 'myCenterQrcode'});
          }
        } else if (val == 'td') {
          this.$router.push({name: 'myteam'})
        }
      },
      getYE() {
        var v = this
        this.$http.post(this.apis.USERZH).then((res) => {
          if (res.code) {
            v.zhYE.yhZhye = res.result.yhZhye
          } else {
            Toast(res.message)
          }
        }).catch((err) => {
          Toast('账户余额获取失败')
        })
      },
      tost() {
        Toast('请实名认证')
      },
      okjf() {
        Toast('您已缴费')
      },
      txyz() {
        Toast('请先完成缴费')
      }
    }
  }
</script>

<style scoped>

</style>
