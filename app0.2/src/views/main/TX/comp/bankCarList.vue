<template>
      <div id="bankList" class="box">
        <div class="box-row"
             style="height: 0.8rem;line-height: 0.8rem;
             color: #fff;background-color: #00000000">
          <div style="width: 0.6rem;text-align: center;line-height: 0.4rem"
               @click="$parent.compName=''">
            <i class="iconfont icon-left1"></i>
          </div>
          <div class="box_row_100" style="font-size: 0.3rem;font-weight: 700;line-height: 0.85rem">
            我的银行卡
          </div>
          <div style="width: 0.6rem;text-align: center;line-height: 0.4rem"
            @click="$parent.compName='addbankCard'">
            <i class="iconfont icon-add" style="font-size: 0.44rem"></i>
          </div>
        </div>
        <div class="body" style="padding: 0.2rem 0.35rem 0">
          <div v-for="(item,index) in bankList"
               @click="seltBankCard(item,index)"
            style="padding: 0.2rem 0">
              <div style="background-color: #fff;padding: 0.1rem 0.2rem;font-size: 0.32rem">
                <div style="border-bottom: 1px #eaeaea solid;padding: 0.1rem 0">
                  {{item.yhkSsyh}}
                </div>
                <div style="padding: 0.1rem 0">
                  {{item.yhkKh}}
                </div>
              </div>
          </div>
        </div>
      </div>
</template>

<script>
  import {  Cell, Toast} from 'mint-ui';
    export default {
        name: "bankCarList",
        components:{
          [Cell.name]:Cell,
        },
        data(){
          return{
          }
        },
        props:{
          bankList:{
            type:Array,
            default:[]
          }
        },
        created(){
        },
        mounted(){
          this.util.auto(window, document,7.5)
        },
        methods:{
          seltBankCard(item,index){
            this.$parent.compName = ''
            this.$emit('seltBankCard',index)
          }
        }
    }
</script>

<style scoped>

</style>
