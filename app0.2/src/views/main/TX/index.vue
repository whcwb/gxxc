<style lang="less">
  #JP {
    .keyboard-number-list {
      margin: 0;
      padding: 0;
    }
    .keyboard-operate-list {
      margin: 0;
      padding: 0;
    }
  }

  #bank {
    background-color: #eaeaea;
    position: relative;
    #bankList, #bankCard {
      background-color: #f2f2f2;
      position: absolute;
      left: 0;
      top: 0;
      right: 0;
      bottom: 0;
      z-index: 9999;
    }
    .icon-ico-money{
      font-weight: 600;
      font-size: 0.4rem;
      color: #ff5e00;
      position: absolute;
      top:0.35rem;
    }
  }
  #input{
    input{
      color: #fc9153!important;
      font-size: 0.5rem!important;
      padding-left:0.6rem ;
    }
  }
  #input{
    .md-input-item-control{
      input::-webkit-input-placeholder{
        font-size: 0.34rem!important;
        color: #c1c1c1;
      }
    }
  }
</style>
<template>
  <div id="bank" class="box_col">
    <component :is="compName"
               @seltBankCard="seltBankCard"
               :bankList="bankList"></component>
    <div class="box-row" style="background-color: #2d8cf0;
          height: 1rem;;line-height: 1.1rem;text-align: center">
      <div style="height: 1rem;width: 1rem;
          line-height: 0.6rem;
          text-align: center;color: #ededed"
           @click="$router.push({name:'Home'})">
        <i class="iconfont icon-left1"></i>
      </div>
      <div class="box_row_100" style="font-weight: 700;font-size: 0.3rem;color: #fff;text-align: center">
        缴费

      </div>
      <div style="height: 1.5rem;width: 1.2rem;text-align: center;">
      </div>
    </div>
    <div class="box_col_auto">
      <div style="background-color: #fff">
        <div>
          <div style="border: none">
            <div v-if="bankList.length==0"
                 @click="compName = 'addbankCard'"
                 style="text-align: right;color: #eb873a;font-size: 0.3rem;padding: 0.2rem 0">
              绑定银行卡后，才能提现！点
              <span style="color: #ff7100;font-size: 0.32rem;font-weight: 700">我</span>
              绑定银行卡！
            </div>

            <div class="box-row" @click="compName='bankList'" v-else>
              <div class="box_row_100" style="font-size: 0.3rem;color: #8c8c8c">
                <div style="padding: 0.12rem 0 0 0.12rem">
                  <img :src="bankList[bankListIndex].yhkLogo" alt="" style="width: 50%">
                </div>
                <div style="padding: 0.15rem 0.3rem;font-size: 0.33rem">
                  {{bankList[bankListIndex].yhkKh}}
                </div>
              </div>
              <div>
                <i class="iconfont icon-right1" style="font-size: 0.6rem;color: #8c8c8c"></i>
              </div>
            </div>
          </div>
        </div>

      </div>

      <div style="margin-top: 0.3rem;background-color: #fff;padding: 0 0.15rem">
        <div class="box-row" style="font-size: 0.4rem;padding: 0.2rem 0 0 0;color: #949494;font-size: 0.35rem">
          <div class="box_row_100">
            提现金额
          </div>
          <div>
            <el-button type="success" plain
            @click="number=zhYE"
            >全部提现</el-button>
          </div>
        </div>
        <div class="box-row" style="height: 2rem">
          <div style="width: 0rem;position: relative">
            <i class="iconfont icon-ico-money"
               style=" "></i>
          </div>
          <div id="input" class="box_row_100" @click="isKeyBoardShow=true">
            <md-input-item
              readonly
              type="money"
              style="border-bottom: #949494 2px solid;"
              v-model="number"
              placeholder="提现金额"
              autofocus="autofocus"
              @focus="isKeyBoardShow=true"
            ></md-input-item>
            <div style="font-size: 0.25rem">
              可提现余额{{zhYE}}元
            </div>
          </div>
        </div>
      </div>
      <div v-show="maxmoney" style="font-size: 0.3rem;color: red;text-align: center">
        提取金额不能大于您的余额
      </div>
      <div style="margin-top: 0.3rem ;padding: 0  0.3rem;text-align: center">
        <el-button type="warning" round
        @click="TX()"
        style="padding: 0.15rem;font-size: 0.4rem;width: 100%;
            background-color: #fc9153">提现</el-button>
      </div>
    </div>
    <div id="JP" class="">
      <md-number-keyboard
        v-model="isKeyBoardShow"
        @enter="onNumberEnter"
        @delete="onNumberDelete"
        @confirm="confirm"
      ></md-number-keyboard>
      <div class="md-example-display" v-show="isKeyBoardShow" v-text="number"></div>
    </div>

  </div>
</template>

<script>
  import {Header} from 'mint-ui'
  import {Toast,ActionSheet, InputItem, NumberKeyboard} from 'mand-mobile'
  import bankList from './comp/bankCarList'
  import addbankCard from './comp/addBankCard'
  export default {
    name: "index",
    height: 500,
    components: {
      bankList,
      addbankCard,
      [ActionSheet.name]: ActionSheet,
      [InputItem.name]: InputItem,
      [NumberKeyboard.name]: NumberKeyboard,
    },
    data() {
      return {
        isKeyBoardShow: true,//键盘
        number: '',
        zhYE: '',//账户余额
        compName: '',
        bankListIndex: 0,
        bankList: [],
        maxmoney:false

      }
    },
    created() {
      this.util.auto(window, document, 7.5)
      this.zhye()
      this.getbanklist()
    },
    methods: {
      zhye() {
        this.$http.post(this.apis.USERZH).then((res) => {
          if (res.code == 200) {
            this.zhYE = (parseInt(res.result.yhZhye)/100).toString()
          }
          console.log(res)
        }).catch((err) => {

        })
      },
      onNumberEnter(val) {
        var v = this
        this.number += val;

        if (parseInt(this.number) > parseInt(this.zhYE)) {
          v.maxmoney=true
          v.onNumberDelete()
        }
      },
      onNumberDelete() {
        if (this.number === '') {
          return
        }
        this.number = this.number.substr(0, this.number.length - 1)
      },
      confirm() {

      },
      TX() {//                                             银行卡号          开户行         提现方式
        var v = this
        // let bank =v.bankList[v.bankListIndex].id
        if (v.bankList.length == 0) {
          Toast.info('请选择银行卡')
          return
        }
        if (this.number) {
          // this.number = parseInt(this.number)
          this.$http.post(this.apis.TX, {
            'ttje': this.number * 100,
            'yhkid': v.bankList[v.bankListIndex].id
          }).then((res) => {
            if (res.code == 200) {
              Toast.info(res.message)
              v.$router.push({name: 'bill'})
            } else {
              Toast.info(res.message)
            }
            v.number = ''
          }).catch((err) => {

          })
        } else {
          Toast.info('提现金额不能为空')
        }
      },
      getbanklist() {
        this.$http.post(this.apis.BANKLIST).then((res) => {
          if (res.code == 200 && res.result) {
            this.bankList = res.result
          } else {
            this.bankList = []
          }
        }).catch((err) => {

        })
      },
      seltBankCard(index) {
        // console.log(item)
        console.log(index)
        this.bankListIndex = index
      }
    },
  }
</script>
