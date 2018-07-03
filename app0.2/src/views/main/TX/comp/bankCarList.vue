<style lang="less">
  .md-dialog{
    z-index: 9999!important;
  }
  #bankList{
    background: no-repeat left;
    background-size: cover;
    .backimg{
      background: no-repeat left;
      background-size: cover;
      height: 3rem;
      margin-bottom: 0.2rem;
    }
  }
</style>
<template>
      <div id="bankList" class="box_col" :style="{backgroundImage:'url('+'static/bank/bankbank.png'+')' }">
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
        <div class="box_col_auto" style="padding: 0.2rem 0.35rem 0">
          <div class="backimg" :style="{backgroundImage:'url('+bankimg[index%3]+')' }" v-for="(item,index) in bankList">
            <div class="box-row" style="height: 100%">
              <div class="box_row_100" @click="seltBankCard(item,index)">
                <div class="box_col">
                  <div class="box_col_100" style="padding-left: 0.2rem">
                    <img :src="item.yhkLogo" alt="">
                  </div>
                  <div style="padding: 0.1rem 0 0.3rem 0;font-size: 0.5rem;
                    text-align: center;color: #fff">
                      {{item.yhkKh}}
                  </div>
                </div>
              </div>
              <div @click="bankCardRemove(item.id)" style="width: 0.5rem;position: relative">
                <i class="iconfont icon-icon" style="color: #ff6000;position: absolute;top: 0.2rem"></i>
              </div>
            </div>
          </div>
          <div v-if="bankList.length==0">
            <div>
             你还没有绑定银行卡，
            </div>
          </div>
        </div>
      </div>
</template>

<script>
    import { Dialog } from 'mand-mobile'
    export default {
        name: "bankCarList",
        components:{
          [Dialog.name]:Dialog,
        },
        data(){
          return{
            bankimg:[
              'static/bank/card1.png',
              'static/bank/card2.png',
              'static/bank/card3.png'
            ]
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
          },
          bankCardRemove(id){
            var v =this
            console.log('cs');

            Dialog.confirm({
              title: '确认',
              content: '请确认是否进行操作',
              confirmText: '确定',
              onConfirm: () => {
                v.$http.post(v.apis.BANKCARDDELE+id).then((res)=>{
                  if(res.code == 200){
                    v.$parent.getbanklist()
                  }
                }).catch((err)=>{

                })
              },
            })
          },
          getbanklist() {
            alert('2')
            var v = this
            this.$http.post(this.apis.BANKLIST).then((res) => {
              if (res.code == 200 && res.result) {
                v.bankList = res.result
              } else {

              }
            }).catch((err) => {

            })
          },
        }
    }
</script>

<style scoped>

</style>
