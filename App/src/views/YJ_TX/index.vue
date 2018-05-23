<style lang="less">
  #yjtx{
      .messList{
        padding: 0 0.3rem;
        font-size: 0.28rem;
        color: #818181;
        font-weight: 600;
        .box-row{
          line-height: 0.6rem;
          border-bottom: solid 4px #ededed;
          padding: 0.1rem 0;
        }
      }
      .submit{
        span{
          font-size: 0.3rem;
        }
      }
  }
</style>
<template>
      <div id="yjtx" class="box">
        <headTit tit="佣金提现"></headTit>
        <div class="messList">
            <div class="box-row">
              <div class="body-O">
                  可提现佣金:
              </div>
              <div>
                  0.00元
              </div>
            </div>
        </div>
        <md-field>
            <md-field-item
                  name="item3"
                  title="提现金额"
                  customized
                  align="center">
                  <md-input-item
                    ref="input1"
                    placeholder="请输入您要提现的金额"
                    align="right"
                    type="money"
                  ></md-input-item>
            </md-field-item>
        </md-field>
        <div class="submit" style="padding:0.3rem ">
          <Button type="error" long
                  style="padding: 0.18rem">
            提现
          </Button>
        </div>
      </div>
</template>

<script>
  import headTit from '../comp/headTit'
  import {Button} from 'iview'
  import {Field, FieldItem, InputItem} from 'mand-mobile'

  export default {
        name: "index",
        components:{
          headTit,Button,
          [Field.name]: Field,
          [FieldItem.name]: FieldItem,
          [InputItem.name]: InputItem,
        }
    }
</script>

<style lang="stylus" scoped>
  .md-example-child-field-1
    .md-input-item
      background #FFF
      padding 0 32px
    .strong-tip
      font-size 24px
      color color-text-hightlight
</style>
