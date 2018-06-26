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
      background-color: #7e7e7e;
      position: absolute;
      left: 0;
      top: 0;
      right: 0;
      bottom: 0;
      z-index: 9999;
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
              <div>
                <i class="iconfont icon-detail" style="font-size: 0.8rem;color: #8c8c8c"></i>
              </div>
              <div class="box_row_100" style="font-size: 0.3rem;color: #8c8c8c">
                <div style="padding: 0.12rem">
                  {{bankList[bankListIndex].yhkSsyh}}
                </div>
                <div style="padding: 0 0.1rem">
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
        <div style="font-size: 0.4rem;">
          提现金额
        </div>
        <div class="box-row" style="height: 2rem">
          <div>
            <i class="iconfont icon-ico-money"
               style=" font-weight: 600;font-size: 0.6rem;line-height: 1.5rem"></i>
          </div>
          <div class="box_row_100" @click="isKeyBoardShow=true">
            <md-input-item
              disabled
              type="money"
              style="border-bottom: #949494 2px solid"
              v-model="number"
              placeholder="提现金额"
              autofocus="autofocus"
              @focus="isKeyBoardShow=true"
            ></md-input-item>
            <!--@keydown="onInputKeydown"-->
            <!--@change="onInputChange"-->
            <div style="font-size: 0.25rem">
              可提现余额{{zhYE}}元
            </div>
          </div>
        </div>
        <!--<Card>-->
        <!--<div>-->
        <!--提现金额-->
        <!--</div>-->
        <!--<div class="box-row" style="height: 2rem">-->
        <!--<div>-->
        <!--<i class="iconfont icon-ico-money"-->
        <!--style=" font-weight: 600;font-size: 0.6rem;line-height: 1.5rem"></i>-->
        <!--</div>-->
        <!--<div class="body-O" @click="isKeyBoardShow=true">-->
        <!--<md-input-item-->
        <!--disabled-->
        <!--type="money"-->
        <!--style="border-bottom: #949494 2px solid"-->
        <!--v-model="number"-->
        <!--placeholder="提现金额"-->
        <!--autofocus="autofocus"-->
        <!--@focus="isKeyBoardShow=true"-->
        <!--&gt;</md-input-item>-->
        <!--&lt;!&ndash;@keydown="onInputKeydown"&ndash;&gt;-->
        <!--&lt;!&ndash;@change="onInputChange"&ndash;&gt;-->
        <!--<div>-->
        <!--可提现余额{{zhYE.yhZhye/100}}元-->
        <!--</div>-->
        <!--</div>-->
        <!--</div>-->
        <!--</Card>-->
      </div>

      <div style="margin-top: 0.3rem ;padding: 0  0.3rem">
        <!--<Button type="primary" shape="circle" long-->
        <!--@click="TX()"-->
        <!--style="padding: 0.15rem;font-size: 0.4rem">提现</Button>-->
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
  import {Header, Cell, Toast} from 'mint-ui'
  import {ActionSheet, Dialog, InputItem, NumberKeyboard} from 'mand-mobile'
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
        bankList: []
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
            this.zhYE = parseInt(res.result.yhZhye)/100
          }
          console.log(res)
        }).catch((err) => {

        })
      },
      onNumberEnter(val) {
        var v = this
        this.number += val;


        debugger
        console.log(this.number)
        console.log(parseFloat(this.number));
        console.log(parseFloat(this.zhYE));
        let a = parseFloat(this.number)*100
        let b = parseFloat(this.zhYE)
        console.log(a > b);

        if (parseInt(this.number) > parseInt(this.zhYE)) {
          Toast('提取金额不能大于您的余额')
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
          Toast('请选择银行卡')
          return
        }
        if (this.number) {
          this.$http.post(this.apis.TX, {
            'ttje': this.number * 100,
            'yhkid': v.bankList[v.bankListIndex].id
          }).then((res) => {
            if (res.code == 200) {
              Toast(res.message)
              v.number = ''
              v.$router.push({name: 'bill'})
            } else {
              Toast(res.message)
            }
          }).catch((err) => {

          })
        } else {
          Toast('提现金额不能为空')
        }
      },
      getbanklist() {
        this.$http.post(this.apis.BANKLIST).then((res) => {
          if (res.code == 200 && res.result) {
            this.bankList = res.result
          } else {

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
