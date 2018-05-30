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

  #yhkxz{
    header{
      height: 1rem;
      line-height: 1rem;
    }
    .md-action-sheet .md-action-sheet-content >ul li{
      height: 1rem;
      line-height: 1rem;
    }
    ul li{

    }
  }
</style>
<template>
      <div class="box" style="background-color: #eaeaea">
        <div>
          <mt-header title="提现">
            <span slot="left" @click="$router.push({name:'Home'})">返回</span>
          </mt-header>
        </div>
        <div class="body">
          <Row type="flex" justify="start">
            <Col span="24">
              <Card style="border: none">
                <div class="box-row" @click="value=true,isKeyBoardShow=false">
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
                <div class="body-O">
                  <md-input-item
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
                    可提现余额0.6元
                  </div>
                </div>
              </div>
            </Card>
          </div>

          <div style="margin-top: 0.3rem ;padding: 0  0.3rem">
            <Button type="primary" shape="circle" long
                  style="padding: 0.15rem;font-size: 0.4rem">提现</Button>
          </div>
        </div>
        <div id="JP" class="md-example-child md-example-child-number-keyboard md-example-child-number-keyboard-0">
          <md-number-keyboard
            v-model="isKeyBoardShow"
            @enter="onNumberEnter"
            @delete="onNumberDelete"
          ></md-number-keyboard>
          <div class="md-example-display" v-show="isKeyBoardShow" v-text="number"></div>
        </div>
        <div id="yhkxz" class="md-example-child md-example-child-action-sheet">
          <md-action-sheet
            v-model="value"
            :title="title"
            :default-index="defaultIndex"
            :cancel-text="cancelText"
            :options="options"
            @selected="selected"
          ></md-action-sheet>
        </div>
      </div>
</template>

<script>
    import { Header ,Cell } from 'mint-ui'
    import {ActionSheet, Dialog ,InputItem ,NumberKeyboard} from 'mand-mobile'
    import {Card ,Row, Col , Button} from 'iview'
    export default {
        name: "index",
        height: 500,
        components:{
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
          value: false,
          title: '选着您的银行卡',
          options: [
            {
              label: '招商银行卡_尾号6699',
              value: 0,
            },
            {
              label: '工商银行卡_尾号6699',
              value: 1,
            },
            {
              label: '汉口银行卡_尾号6699',
              value: 2,
            },
          ],
          defaultIndex: 1,
          cancelText: '取消',
        }
      },
      methods: {
        onNumberEnter(val) {
          this.number += val
        },
        onNumberDelete() {
          if (this.number === '') {
            return
          }
          this.number = this.number.substr(0, this.number.length - 1)
        },
        selected(item) {
          console.log('action-sheet selected:', JSON.stringify(item))
        },
      },
    }
</script>

<style scoped>

</style>
