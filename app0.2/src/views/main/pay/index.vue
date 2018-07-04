<style lang="less">
  #payMoney{
    position: relative;
    font-size: 0.22rem;
    .decoration{
      text-decoration:underline ;
    }
    #agree{
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      background: #ffffffc2;
      z-index: 100;
      /*padding:0.2rem;*/
      .agreeMess{
        background-color: #fff;
        .tit{
          color: #f75d42;
          font-size: 0.65rem;
          padding-top: 0.3rem;
          font-weight: 700;
        }
        .fonttit{
          font-weight: 700;
          font-size: 0.35rem;
        }
        .fontsty{
          font-size: 0.25rem;
          text-indent:0.3rem;
          margin-top: 0.1rem;
        }
        .userSign{
          position: relative;
          .signImg{
            position: absolute;
            left: -0.2rem;
            top: -0.5rem;
            z-index: 101;
            width: 1.6rem;
            height: 1.6rem;
            border-radius:2.5rem;
            border: #f54d55 0.03rem solid;
            img{
              width: 100%;
              height: 100%;
            }
          }
        }
      }
    }
    #sign{
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      background: #ffffff;
      z-index: 200;
    }
    .md-popup-title-bar{
      height: 1rem;
    }
    .choose-text{
      padding: 0.2rem 0;
      .choose-number{
        margin: 0;
      }
      .choose-describe{
        font-size: 0.22rem;
      }
    }
    .choose-channel{
      padding: 0 1.2rem;
      ul{
        margin: 0.2rem 0;
      }
    }
  }
</style>
<template>
  <div class="box_col" id="payMoney">
    <div id="agree" v-show="agreeShow">
      <div class="agreeMess box_col">
        <div style="text-align: center;position: relative">
          <div class="tit">用户缴费协议</div>
        </div>
        <div class="" style="padding: 0.05rem 0.3rem 0.1rem 0.3rem">
          <div class="box-row">
            <div class="box_row_100">
              <div>
                甲方:<span class="decoration">{{usermess.yhXm}}</span>
              </div>
              <div>
                电话:<span class="decoration">{{usermess.yhZh}}</span>
              </div>
              <div>
                证件号码: <span class="decoration">{{usermess.yhZjhm}}</span>
              </div>
            </div>
            <div class="box_row_100">
              <div>
                乙方:<span class="decoration">武汉天弘腾创科技有限公司</span>
              </div>
              <div>
                代表人:<span class="decoration">腾创</span>
              </div>
              <div>
                电话: <span class="decoration">13133446688</span>
              </div>
            </div>
          </div>
        </div>
        <div class="box_col_auto" style="padding: 0.35rem 0.5rem;">
          <div class="fontsty">
            甲乙双方经友好协商，依据中华人民共和国相关法律、法规的规定，本着自愿、平等、互惠互利、诚实信用的原则，乙方为甲方提供创业培训、项目运营咨询、工商税务咨询、创业资源对接等服务，在以下方面达成一致。双方约定共同遵守：
          </div>
          <div>
            <div class="fonttit">第一条：甲方权利与义务</div>
            <div class="fontsty">
              1、本协议有效期内，甲方是乙方创业培训服务的购买方和代理方，可以以自己名义代理乙方签订乙方经营范围内的业务合同，为乙方提供招揽、联系培训业务的专项服务。
            </div>
            <div class="fontsty">
              2、甲方在业务开展前，应得到乙方许可或授权。
            </div>
            <div class="fontsty">
              3、甲方在交纳培训费用后，有权享受乙方每月提供的创业培训课程及相关资源。
            </div>
            <div class="fontsty">
              4、甲方运用自身渠道为乙方进行课程及项目推广，并拥有联系、安排、接洽与乙方有关的培训工作及相关事宜的权利。
            </div>
            <div class="fontsty">
              5、甲方拥有与本协议工作内容相关的乙方的名称、音频、图标、文案及相关资质的使用权。甲方与客户签订的各种与乙方培训工作相关的合约和细则，应提前通知乙方，并与乙方就创业服务的细节问题进行沟通协商
            </div>
          </div>

          <div>
            <div class="fonttit">第二条：乙方权利与义务</div>
            <div class="fontsty">
              1、本协议生效后，乙方应向甲方提供为期一年的创业服务，服务内容主要是提供创业课程、创业政策解读、工商税务咨询、项目运营辅导、团队搭建、资源对接等服务。
            </div>
            <div class="fontsty">
              2、乙方应依据甲方实际情况，每个月不定期举办至少一次线下创业交流会。
            </div>
            <div class="fontsty">
              3、乙方有权在培训前，从甲方处获得课酬 2500 元。
            </div>
            <div class="fontsty">
              4、对甲方招收的学员，乙方应确保提供较为满意的服务，在学员积极配合的前提下，保证学员在接受培训后取得相关证书或凭证。
            </div>
          </div>
          <div>
            <div class="fonttit">第三条：合作期限</div>
            <div class="fontsty">
              双方合作期间为  1  年，<span style="color: red;">{{getdateStr(0)}}</span>起，至<span style="color: red;">{{getdateStr(1)}}</span>止，本协议届满前   1   个月，甲乙双方可就续签事宜进行协商并签订相关协议；协议期满，双方未签订新签约或就本协议签订相关补充协议，则本协议到期终止。
            </div>
          </div>
          <div>
            <div class="fonttit">第四条：乙方酬劳及双方利润分配</div>
            <div class="fontsty">
              1、乙方就驾考招生项目给予甲方的合作报价为  2500  元/人，甲方招收的学员在缴纳驾考培训费用2500元后，乙方平台系统应向甲方支付  800  元/人的酬劳，且甲方招收的学员在招到新学员时，乙方平台系统应支付甲方 400 元/人的酬劳，具体依据学车联盟平台运营管理方案施行。
            </div>
            <div class="fontsty">
              2、甲乙双方在本协议履行期间，应共享收益，同担风险，若发生客户退款、减免部分款项之事，双方应共同协调处理，产生的损失按照收益比例分担。
            </div>
          </div>
          <div>
            <div class="fonttit">第五条：协议的解除或终止</div>
            <div class="fontsty">
              1、甲乙双方协商一致，可以解除本协议。
            </div>
            <div class="fontsty">
              2、有下列情形之一，甲乙任何一方有权单方解除本协议。
            </div>
            <div class="fontsty">
              （1）一方涉入本协议外的法律纠纷而不能履行本协议约定工作。
            </div>
            <div class="fontsty">
              （2）一方人身自由或经营权利受限或不能自由活动、不能履行本协议约定的义务。
            </div>
            <div class="fontsty">
              （3）一方在本协议中虚假承诺，且对另一方签订本协议有重大影响的，受影响方有权解除协议。
            </div>
          </div>
          <div>
            <div class="fonttit">第六条：保密约定</div>
            <div class="fontsty">
              1、甲乙双方保证对在讨论、签订、执行本协议过程中所获悉的属于对方的且无法自公开渠道获得的文件及资料（包括商业秘密、公司计划、运营活动、财务信息、技术信息、经营信息、折扣及其他涉密信息）予以保密。
            </div>
            <div class="fontsty">
              2、未经该资料和文件的原提供方书面同意，另一方不得向任何第三方泄露该商业秘密的全部或部分内容。
            </div>
            <div class="fontsty">
              3、本保密条款在本协议解除或终止后两年内仍然有效，双方亦应遵守。
            </div>
          </div>
          <div>
            <div class="fonttit">第七条：违约责任</div>
            <div class="fontsty">
              本协议履行过程中，甲乙双方因一方行为造成另一方实际损失，行为造成方应予赔偿。若商定的课程、培训项目乙方对课程已经确认后因乙方原因未履约，甲方由此可向乙方追偿。
            </div>
          </div>
          <div>
            <div class="fonttit">第八条：争议解决</div>
            <div class="fontsty">
              1、本协议的订立、解释、履行和争议的解决均适用中华人民共和国法律。
            </div>
            <div class="fontsty">
              2、因本协议引起的或与本协议事项有关的争议，双方应友好协商解决；协商不成，任何一方可向对方所在地人民法院提起诉讼解决。
            </div>
          </div>
          <div>
            <div class="fonttit">第九条：其他事项</div>
            <div class="fontsty">
              1、本协议经双方签字、盖章之日起生效。
            </div>
            <div class="fontsty">
              2、本协议一式   2   份，甲乙双方各执   1   份，具有同等法律效力。
            </div>
            <div class="fontsty">
              3、除非本协议另有规定，甲乙双方协商一致并签署相关协议，可转让本协议约定的全部或部分权利给第三方。
            </div>
          </div>
        </div>
        <div class="box-row" style="padding: 0.15rem 0.3rem">
          <div class="userSign" style="margin-right: 0.2rem">
            <div class="signImg"  v-show="saveResultUrl!=''">
              <img :src="apis.getImgUrl+saveResultUrl" alt="">
            </div>
            <div>
              甲方代表:<span class="decoration">{{usermess.yhXm}}</span>
            </div>
            <div>
              甲方（签章）:
              <el-button v-if="sign" type="primary" icon="el-icon-edit" circle
                         @click="signShow=!signShow"
              >请签名</el-button>
              <el-button v-else type="success" icon="el-icon-check" circle>
                已签名
              </el-button>
            </div>
            <div>
              时间：{{getdateStr(0)}}
            </div>
          </div>
          <div class="box_row_100" style="position: relative">
            <img src="static/gz/thtc.png" style="width: 1.6rem;
                  position: absolute;
                  top: -0.5rem;
                  right: 0.5rem;
                  transform: rotate(-60deg);">
              <div>
                乙方代表:<span class="decoration">武汉天弘腾创科技有限公司</span>
              </div>
              <div>
                乙方（签章）:
              </div>
              <div>
                时间：{{getdateStr(0)}}
              </div>
          </div>
        </div>
        <div class="box-row">
          <div class="box_row_100" style="padding: 0.2rem">
            <el-button type="success" round
              @click="agreeShowOk"
              style="width: 100%;font-size: 0.4rem"
            >同意</el-button>
          </div>
          <div class="box_row_100" style="padding: 0.2rem">
            <el-button type="info" round
                       @click="$router.push({name:'Home'})"
                       style="width: 100%;font-size: 0.4rem"
            >拒绝</el-button>
          </div>
        </div>
      </div>
    </div>
    <div id="sign" v-show="signShow">
      <signcanvas @saveResult="saveResult"></signcanvas>

      <span style="position: absolute;top: 0;z-index: 300;right: 0;
      padding: 0.2rem;font-size: 0.35rem;font-weight: 600;color: #868686"
      @click="signShow=!signShow">
        关闭
      </span>
      <div style="position: absolute;top: 0;z-index: 300;left: 0;
      padding: 0.2rem;font-size: 0.25rem;color: #fb5f00">
        请将手机横屏签名..
      </div>
    </div>
    <div class="box-row"style="background-color: #2d8cf0;height: 0.8rem;;line-height: 0.8rem;text-align: center">
          <div style="height: 0.8rem;width: 1rem;
          line-height: 0.8rem;
          text-align: center;color: #ededed"
          @click="$router.push({path:'/Home'})">
            <i class="iconfont icon-left1"></i>
          </div>
          <div class="box_row_100" style="font-weight: 700;font-size: 0.28rem;color: #fff">
            缴费
          </div>
        <div style="height: 1.5rem;width: 1.2rem;text-align: center;">
        </div>
    </div>
    <div class="body md-example-child md-example-child-cashier">
      <md-field
        v-show="false"
        title="支付结果"
      >
        <md-radio
          v-model="cashierResult"
          :options="cashierResults"
        ></md-radio>
      </md-field>
      <md-field
      >
        <!--title="支付配置"-->
        <md-input-item
          title="支付金额"
          align="right"
          type="money"
          v-model="cashierAmount"
        >
        </md-input-item>
        <md-field-item
          v-show="false"
          title="发送验证码"
          align="right"
        >
          <md-switch v-model="isCashierCaptcha"></md-switch>
        </md-field-item>
      </md-field>
      <md-button @click="payMoney">{{ isCashierhow ? '立即支付' : '立即支付' }}</md-button>
      <md-cashier
        id="payMoney"
        ref="cashier"
        v-model="isCashierhow"
        :channels="cashierChannels"
        :payment-amount="cashierAmount"
        payment-describe="共享时代"
        @select="onCashierSelect"
        @pay="onCashierPay"
        @cancel="onCashierCancel"
      ></md-cashier>
    </div>
  </div>
</template>

<script>
  import {Toast, Button, Radio, Field, FieldItem, InputItem, Switch, Cashier} from 'mand-mobile'
  import signcanvas from '../../components/canvas/signCanvas'
  export default {
    name: 'cashier-demo',
    /* DELETE */
    height: 100,
    /* DELETE */
    components: {
      signcanvas,
      [Button.name]: Button,
      [Radio.name]: Radio,
      [Field.name]: Field,
      [FieldItem.name]: FieldItem,
      [InputItem.name]: InputItem,
      [Switch.name]: Switch,
      [Cashier.name]: Cashier,
    },
    data() {
      return {
        sign:true,
        signShow:false,
        usermess: this.$store.state.app.userMess,
        isCashierhow: false,
        isCashierCaptcha: false,//非否发送验证码
        cp:{},
        cashierAmount: '0.00',
        money:0,
        cashierResult: 'success',
        cashierResults: [
          {
            text: '支付成功',
            value: 'success',
          },
          {
            text: '支付失败',
            value: 'fail',
          },
        ],
        cashierChannels: [
          // {
          //   icon: 'cashier-icon-1',
          //   text: '招商银行储蓄卡(0056)支付',
          //   value: '001',
          // },
          // {
          //   icon: 'cashier-icon-2',
          //   text: '支付宝支付',
          //   value: '002',
          // },
          {
            icon: 'cashier-icon-3',
            text: '微信支付',
            value: '2',
          },
          // {
          //   icon: 'cashier-icon-4',
          //   text: 'QQ钱包支付',
          //   value: '004',
          // },
          // {
          //   icon: 'cashier-icon-5',
          //   text: '一网通支付',
          //   value: '005',
          // },
        ],
        payMess:{},
        agreeShow:true,
        checkboxVal:false,
        saveResultUrl:''
      }
    },
    watch:{
      checkboxVal:function (n,o) {
        if(n){
          this.agreeShow = false
        }
      }
    },
    created(){
      this.util.auto(window, document ,7.5)
      console.log(this.$route.params);
      if(this.$route.params.cpJl){
        this.cp = this.$route.params
        this.cashierAmount ="'" + parseInt(this.$route.params.cpJl)/100 +"'"
      }else {
        this.$router.back()
      }

      // this.getCPlist()

    },
    computed: {
      cashier() {
        return this.$refs.cashier
      },
    },
    methods: {
      saveResult(mes){
        this.saveResultUrl = mes
        this.sign = false
        this.signShow = false
      },
      agreeShowOk(){
        if(this.sign){
          Toast.info('请签字')
        }else {
          this.checkboxVal = true
        }
      },
      //获取当前时间
      getdateStr(Y){
        var NowDate = new Date
        let Year = NowDate.getFullYear()+Y
        let Month = NowDate.getMonth()+1
        let Day = NowDate.getDate()
        let Hours = NowDate.getHours()
        let Minutes = NowDate.getMinutes()
        let Seconds = NowDate.getSeconds()
          // +Seconds+'秒'
        let time = Year+'年'+Month+'月'+Day+'日'+Hours+'时'+Minutes+'分'
        return time
      },
      doPay() {
        var v = this
        if (this.isCashierCaptcha) {
          this.cashier.next('captcha', {
            text: '验证码发送至 156 **** 8965',
            onSend: countdown => {
              console.log('[Mand Mobile] Send Captcha')
              this.sendCaptcha().then(() => {
                countdown()
              })
            },
            onSubmit: code => {
              console.log(`[Mand Mobile] Send Submit ${code}`)
              this.checkCaptcha(code).then(res => {
                if (res) {
                  this.createPay().then(() => {
                    this.cashier.next(this.cashierResult)
                  })
                }
              })
            },
          })
        } else {
          this.createPay().then((res) => {
            console.log('**********',res)
            this.cashier.next(this.cashierResult)

            //  *****************************
            this.$store.commit('CHjf', true);
            setTimeout(function () {
              v.$router.push({name:'payMoneyOk'})
            },1000*2.7)
          })
        }
      },
      // Create a pay request & check pay result
      createPay() {
        this.cashier.next('loading')
        return new Promise(resolve => {
          this.timer = setTimeout(() => {
            resolve()
          }, 3000)
        })
      },
      // Create a captcha sending request
      sendCaptcha() {
        return new Promise(resolve => {
          this.timer = setTimeout(() => {
            resolve()
          }, 200)
        })
      },
      // Create a captcha checking request
      checkCaptcha(code) {
        return new Promise(resolve => {
          this.timer = setTimeout(() => {
            resolve(!!code)
          }, 200)
        })
      },
      onCashierSelect(item) {
        console.log('支付渠道选中')
        console.log(`[Mand Mobile] Select ${JSON.stringify(item)}`)
      },
      onCashierPay(item) {
        var  v = this;
        WeixinJSBridge.invoke(
          'getBrandWCPayRequest', {
            "appId":v.payMess.appId,     //公众号名称，由商户传入
            "timeStamp":v.payMess.timeStamp,         //时间戳，自1970年以来的秒数
            "nonceStr":v.payMess.nonceStr, //随机串
            "package":v.payMess.package,
            "signType":v.payMess.signType,         //微信签名方式：
            "paySign":v.payMess.paySign //微信签名
          },
          function(res){
            console.log(res)
            if(res.err_msg=='get_brand_wcpay_request:ok'){
              v.cashierResult = 'success'
              v.doPay()
            }else if(res.err_msg=='get_brand_wcpay_request::fail'){
              v.cashierResult = 'fail'
              v.doPay()
            }else if(res.err_msg=='get_brand_wcpay_request:cancel'){
              v.isCashierhow = !v.isCashierhow
              Toast.info('支付取消')
            }
            // if(res.err_msg == "get_brand_wcpay_request:ok" ) {
            //
            // }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
          }
        );
        console.log('支付确认')
        console.log(item)
      },
      onCashierCancel() {
        console.log('取消')
        // Abort pay request or checking request
        this.timer && clearTimeout(this.timer)
      },
      getCPlist(){//获取缴费
        var v = this
        this.$http.post(this.apis.CPTYPE,{cpType:1}).then((res)=>{
          console.log(res)
          if(res.code==200){
            v.cp = res.result
            v.cashierAmount ="'" + parseInt(res.result.cpJl)/100 +"'"
            // v.payMoney(res.result.id)
          }

        }).catch((err)=>{

        })
      },
      payMoney(){
        var v = this

        console.log(v.cp)
        this.$http.post(this.apis.CPPAY,{ddZftd:2,cpId:v.cp.id,userAutograph:v.saveResultUrl}).then((res)=>{
          console.log(res)
          if(res.code==200){
            console.log(res.result)
            v.payMess = res.result
            this.isCashierhow = !this.isCashierhow
          }else {
            Toast.info(res.message)
          }
        }).catch((err)=>{

        })
      },
    }
  }

</script>
<!--<style lang="less">-->
  <!--.md-button.primary.large, .md-button.primary.small{-->
    <!--height: 1rem;-->
    <!--line-height: 1rem;-->
  <!--}-->
<!--</style>-->
<style lang="stylus">
  .md-example-child-cashier
    .md-field
      margin-bottom 30px
    .choose-channel-item
      .item-icon.cashier-icon-1
        background url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAABHklEQVR4Ae2WgUYEURSGh70oQIB6gh6gHmBsqhfqHdq2hAJTUYAACMCytqQISUDAVpsEsWi09TX/XFcxYeJ0E/vzMf4z7uc6cJMQWi6l1egU5AUYkfszXZp8TVFmBfwyWbhZU0UcXKrbdWMJ5ZLwNaIwl5CY1BPen8HgAtoT1dn6pJ/dnRoKn64p01mpztQpj1eGwsNlyuTPsDX92W/P+A70j6FQ3BxR5nI3dP4bNDPeochmYZTD+xvsz8HBvP8evWhmLvScb1Cm3/Mo6sLcXLg5BcMHQhgO1BkL+8fUjP6NLLw9MRB+R0h19h+F4x3WYSystVe/rx/zJ0+MXtxHVNstxBO6ZqKw2tiLINxJQrzULenKxjvVWV3W3GLwfAD9KR4TBA12SgAAAABJRU5ErkJggg==') center no-repeat
        background-size 26px
      .item-icon.cashier-icon-2
        background url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAAB7ElEQVR4Ac2WA6weQRSFb62gtuPajetGtW0FNcO6cVK7XdRu2Ia149R2Z6bP9r6T+yb5bZ7k/Bh9gzt3l1hXfzUiS+4iU36GK2AnTq6AP/PYJ/42JhZ+oPAp7CTWYDDUVHu4IDneCaD8kkTgV4r4zA5+akBuivhMI56llyLv768wXkpfYNK3NGXAaHQhpxlHY9KAlpwc6Qojt6W2urZYnYoFWA5XhWhTSaf/dmHYDqc2/v+NFPiZDLmKzv7pxAPAZIvWZPwbjrqN8B242LU6edUtgMZGGjS3XI8R6IRTj/yI2xhiPNofA7CPCyiuRAL8wM9FUy6AH8I5urwE/gwb8FSdS311+ldz1KsIgGIpmWpNGEGSQYbaC7f1gWIyPClD3uaJBgWeld3x/S3MyCwkWwz1AfqsWCzV8EIfIAeJqd6GAcuHx7oFylz4AVlqGl116gQ/c3HABTTkFH1pywPD1Esy//V0wdQMlJe6Rex3MtQ2OvO/fRgZXbziGVqqP+CmXm0m/AED2WT9G0GOU4sg/sYVCZLCyngrLTHRJ9K9Gl4jWzShYDoj+5Ih7keQiSQWcwgTHqKBPrP8wanK/D+QLzwOH4DeOgDu+maeiPwmBS9RuFNJA1pyV9JfhF33BHS9vZVxhFTWjCn2kYZVAyRlGm3AoxGeAAAAAElFTkSuQmCC') center no-repeat
        background-size 26px
      .item-icon.cashier-icon-3
        background url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAYCAYAAADpnJ2CAAABiklEQVR4AbWVAWRCURSGf8gIGQyYgUEAgyAAbCCAAAQQGBAYIGAwAAgIwAMJGIsqRTAgQPAQEsKDu//kXHLddV+v+w6f0HnvP/e8/5yLYPzgAXN0yICsSUoyZUeW+l/nlFs45miSRF9scpKRBDM0rhGqacXmRobhE8/wwsQtMZHYyjv/E2swYU9MZPZW1DVGWoLYEQt88ffR/W7DyEIHCn3ab4gVKpjhzbayHlOI9K2QHqZFNsSctKSSCEI78oE17h1PTJy8PnRwjYdNjlanpEdqsLHC04XnltA2+KuWiudoe9y7xQLvWKF6JlSVE5DjxZZf3CRs91nV3zqjXYxw55iuk9PlGQKJGaZ4hoR123lM8cqc3yu+dSqC40BS4lkSdf9zQcY4tSic2LQLQgc5K+jmrl3Wu0DiUkxy4+o72LEJnDISUrDjtKREwTHc0DkalSA2sfPqE62oKWKJDWRmgzd+BKENaUGjLMGjzmbbLoiigmvSk/tMnKa3y0DQ9vdIM9S6gKCK6FqLGX+Ik2Cgy7oRZQAAAABJRU5ErkJggg==') center no-repeat
        background-size 26px
      .item-icon.cashier-icon-4
        background url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAcCAYAAAB75n/uAAADaElEQVR4AWIgADiAOAaI1wDxUxZAadUA5GoTBCd59Wzbtm3btm3btm3btm3btt/5+v/6uLXRl/q7qu+SRc/OzKbXIhAh5ZXB1QZLyf8ANz+noAseNZhc3ETb4NOaocUiX+JFlYJiEuU1cdNBzGQSjbVWN3bu3BkHDx5EgQIFQsb69u2LjRs3IkWKFHqgE+ICndUNFCB27drFIBxDoUKFcP/+fZLj9rIpL05wVV1co0YNEBQdP3484sWLx9OjXbt2JD5//mwvwBaHV1KvPctCsEyLFy9GxIgRQeErV67g2rVrePz4sf1eOEBGLtB56tQpEPPmzQO/x4gRAx8+fADRvXt3R02PJnaQw97inKnCYkiLTBjf2IJ5bQTkxJax0KdpdiSPK44CxBcdk5LE7qqWaGxDwZv5AmxyzjtTLKiVP3QfNfakTVBZdDzOHHPDhESREBxkQA3Br4Vh8WlyJAr568Leq634MC4yPs63onz2UPEVySL7vswcc6LoeJIx+vPXmWNgW8ooSBs+DDcgY2LBoYEWvBoaDVeaJMSNlvFD+G5sZJYLsaMGlTJiGOxPFRXUuJ8x+nnREO16+ui+nAzm4qSRUT5qWES2Bpbg7OCwuNcjJok1nawMzjlUix4Oq5NHhrr3WJqonnqj5/HUJ9IEnkAnx5n6rMSRQFLQ0VpmkSSslVlNkyAkD647/y8pkx9vC6XiYrf4tnh6dEocHapWsDdNVK/Y/Pnz8XvdYlOiH6rlx7fh3eFx8iDg54uyZcvq13W8jT1s3boVPyYPVYXg/+8vfJ4/htedayT42d/TAzoaNmyoB7gquj3QJXkqNQBPZwZ6gCBtgcpp06bZBPD79QMmQFNUhYMpj4M+dOYEHdT3y0f8nDsBX3u3wpdujU1l4OHhgQgRIsBqld+K7VNbBgU1mlhNx6SZuYtVq1ZRUL2e04TaGuLTbmnP7uDv379Ily4dxZ/bOqkGPt5Mk802i+bNm4MHy5ZccokzYJOMN/jn6WxBscxhwVfM29sbjsBSsmclMoc47x9q2BffLP00x/TfM0DQvkYqjB45BHyT+Q7zNeNvpWuntmhUIgq4RtsHaokObJS2nHRAf2a1s5/s3NFXtvOzauE6qeWoRHUMHjT4mQJBfGpws8HKyrrKHOOcso57DlJDFPwHtUxGlWBNgLkAAAAASUVORK5CYII=') center no-repeat
        background-size 26px
      .item-icon.cashier-icon-5
        background url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAMAAABF0y+mAAABPlBMVEUAAAC/AEDIFS3GFS3HFS3HFS3HFS3MADPGFC7GFS3FFSvHFS3GFS3HFS3HFS3KIjnooKnmmKPJHDTMLELqqLHmlaDIGjHONEnssLjkjJjHFi7////hgI3rqrLdcoDwwsjUS17KITjed4T44+XIGC/LJz3++fr119vQPFD//v7NMEXgf43ca3r78PLZX3DVUWPpoavut77IGTD44+bxxszmlqHxw8nHFy/44OPttr3LJjz99/jaZXXXWWrhgY7+/P3//f3XV2jzzdPgf4zut7/nmqTxxMrllJ/yyM7dcH/++vvMK0HWUmTQO0/ZYXHba3r77vDJHTTts7v22Nzxxcv55OffdoXed4XRP1PedIPaZHThg5D11dnVTmDJHzb77e/55+rLJj377/Hqp7DkjprTRlnlkZzRPlLNL0TXVmdZnHwTAAAAD3RSTlMABEqUzPH/BXDmMNZV+P145yZGAAABMklEQVR4AXTQ02KcARCG4Z/v2t5vVdv2to1t2/d/BcnEfE7H45xxPT8IIQx8z3WuikRjnItFI5dj8QRXJOIXsWSKa1LJ81iaG9Kn0XiKW6TiFoskuFXCtopyh+jRfTFMJpvLA4ViqQxUqrU6MdfxwJSkBtCUWkBb6oDn+Jh796UHD3n0WHoCT6Vnz8F3AsyLl6+k1zQk6Q1vpXdA4IQWe//h4yfp85evFvz2/YdlQOhgfkq/nkm/pT9S96/1NifBf9L/HpnePql/QIMYa0tHGhoekRkdk16NT0yCtQ2AKalIfVrSDO9npbl5TGCnPO9KC7AoaQmWpRUwvj1hVWoDa+vaKMPmlrbBePa+LzvVCsDu3j7AweEyxSAJghFfwOONMnyRjS+Z4Etg+JIm4USNNzsAAPHdK2mIKv4bAAAAAElFTkSuQmCC') center no-repeat
        background-size 26px
</style>
