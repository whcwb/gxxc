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
        <component :is="compName"
                   @seltBankCard="seltBankCard"
                   :bankList="bankList"></component>
        <box-head tit="提现">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
        <div class="body">
          <Row type="flex" justify="start">
            <Col span="24">
              <Card style="border: none">
                <div v-if="bankList.length==0"
                    @click="compName = 'addbankCard'"
                    style="text-align: right;color: #eb873a;font-size: 0.28rem">
                    绑定银行卡后，才能提现！点
                    <span style="color: #ff7100;font-size: 0.32rem;font-weight: 700">我</span>
                    绑定银行卡！
                </div>
                <div class="box-row" @click="compName='bankList'" v-else>
                  <i class="iconfont icon-detail" style="font-size: 20px" slot="icon"></i>
                  <div class="body-O">
                      <mt-cell :title="bankList[bankListIndex].yhkSsyh"
                               :label="bankList[bankListIndex].yhkKh"
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
                    可提现余额{{zhYE.yhZhye/100}}元
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
          compName:'',
          bankListIndex:0,
          bankList:[]
        }
      },
      created(){
        this.zhye()
        this.getbanklist()
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
          // let bank =v.bankList[v.bankListIndex].id
          if(v.bankList.length==0){
            Toast('请选择银行卡')
            return
          }
          if(this.number){
              this.$http.post(this.apis.TX,{'ttje':this.number*100,'yhkid':v.bankList[v.bankListIndex].id}).then((res)=>{
                if(res.code==200){
                  Toast(res.message)
                  v.number = ''
                  v.$router.push({name:'bill'})
                }else{
                  Toast(res.message)
                }
              }).catch((err)=>{

              })
          }else {
            Toast('提现金额不能为空')
          }
        },
        getbanklist(){
          this.$http.post(this.apis.BANKLIST).then((res)=>{
            if(res.code==200 && res.result){
              this.bankList = res.result
            }else {

            }
          }).catch((err)=>{

          })
        },
        seltBankCard(index){
          // console.log(item)
          console.log(index)
          this.bankListIndex = index
        }
      },
    }
</script>

<style scoped>

</style>
