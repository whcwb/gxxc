<template>
      <div id="bankCard" class="box">
        <div class="box-row"
             style="height: 1.1rem;line-height: 1.1rem;
             color: #fff;
             background-color: #00000000">
          <div style="width: 1rem;text-align: center"
               @click="$parent.compName='bankList'">
            <i class="iconfont icon-left1"></i>
          </div>
          <div class="body" style="font-size: 0.45rem;font-weight: 700;line-height: 1.2rem">
            添加银行卡
          </div>
        </div>
        <div class="body" style="padding: 0.3rem">
          <div style="font-size: 0.45rem;padding: 0.1rem 0.2rem;color: #ffa000;font-weight: 600">
            请绑定持卡人本人的银行卡
          </div>
          <md-field>
            <md-input-item
              ref="input0"
              title="姓名"
              placeholder="普通文本"
              :maxlength="5"
            ></md-input-item>
            <md-input-item
              title="银行卡"
              type="bankCard"
              placeholder="xxxx xxxx xxxx xxxx"
            ></md-input-item>
          </md-field>
        </div>
      </div>
</template>

<script>
    import { InputItem, Field} from 'mand-mobile'
    export default {
        name: "addBankCard",
        components:{
            [Field.name]:Field,
            [InputItem.name]:InputItem
        },
        data(){
          return{

          }
        },
        mounted(){
          this.MyFunc.auto(window, document,11)
        },
        methods:{
        }
    }
</script>

<style scoped>

</style>
