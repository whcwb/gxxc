<style lang="less">
  #JP{
    .md-number-keyboard-container{
      height: 4rem;
    }
    .md-number-keyboard-container .keyboard-number .keyboard-number-list .keyboard-number-item{
      height: 1rem;
      line-height: 1rem;
      font-size: 0.3rem;
    }
  }

  #bank{
    background-color: #eaeaea;
    position: relative;
    #bankList,#bankCard{
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
      <div id="bank" class="box">
        <component :is="compName"></component>
        <box-head tit="提现">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
        <div class="body">
          <Row type="flex" justify="start">
            <Col span="24">
              <Card style="border: none">
                <div class="box-row" @click="compName='bankList'">
                  <i class="iconfont icon-detail" style="font-size: 20px" slot="icon"></i>
                  <div class="body-O">
                      <mt-cell title="汉口银行"
                               label="尾号6677 储蓄卡"
                               is-link style="border-bottom: 1px #e9eaec solid;" >
                      </mt-cell>
                  </div>
                </div>
              </Card>
            </Col>
          </Row>

          <div style="margin-top: 0.3rem">
            <Card>
              <div>
                提现金额
              </div>
              <div class="box-row" style="height: 2rem">
                <div>
                  <i class="iconfont icon-ico-money"
                     style=" font-weight: 600;font-size: 0.6rem;line-height: 1.5rem"></i>
                </div>
                <div class="body-O" @click="isKeyBoardShow=true">
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
                  <div>
                    可提现余额{{zhYE.yhZhye}}元
                  </div>
                </div>
              </div>
            </Card>
          </div>

          <div style="margin-top: 0.3rem ;padding: 0  0.3rem">
            <Button type="primary" shape="circle" long
                    @click="TX()"
                  style="padding: 0.15rem;font-size: 0.4rem">提现</Button>
          </div>
        </div>
        <div id="JP" class="md-example-child md-example-child-number-keyboard md-example-child-number-keyboard-0">
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
    import { Header ,Cell ,Toast} from 'mint-ui'
    import {ActionSheet, Dialog ,InputItem ,NumberKeyboard} from 'mand-mobile'
    import {Card ,Row, Col , Button} from 'iview'
    import bankList from './comp/bankCarList'
    import addbankCard from './comp/addBankCard'
    export default {
        name: "index",
        height: 500,
        components:{
          bankList,addbankCard,
          Card ,Row, Col,Button,
          [Header.name]:Header,
          [Cell.name]:Cell,
          [ActionSheet.name]: ActionSheet,
          [InputItem.name]: InputItem,
          [NumberKeyboard.name]: NumberKeyboard,
        },
      data() {
        return {
          isKeyBoardShow: true,//键盘
          number: '',
          zhYE:'',//账户余额
          compName:''
        }
      },
      created(){
        this.zhye()
      },
      methods: {
        zhye(){
          this.$http.post(this.apis.USERZH).then((res)=>{
            if(res.code==200){
              this.zhYE = res.result
            }
            console.log(res)
          }).catch((err)=>{

          })
        },
        onNumberEnter(val) {
          var v = this
          this.number += val;

          console.log(this.number)

          if(parseInt(this.number)>parseInt(this.zhYE.yhZhye)){
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
        confirm(){

        },
        TX(){//                                             银行卡号          开户行         提现方式
          var v = this
          if(this.number){
              this.$http.post(this.apis.TX,{'ttje':this.number,'yhkh':'123123123','khh':'afdf','ttFs':'qweqweq'}).then((res)=>{
                if(res.code==200){
                  Toast(res.message)
                  v.number = ''
                }else{
                  Toast(res.message)
                }
              }).catch((err)=>{

              })
          }else {
            Toast('提现金额不能为空')
          }
        }
      },
    }
</script>

<style scoped>

</style>
