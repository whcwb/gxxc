<style lang="less">
  .tost{
    padding: 0.5rem 1.2rem;
    background: rgba(0,0,0,0.3);
    position: absolute;
    left: 50%;
    top: 45%;
    transform: translate(-50%,-50%);
    color: #E0DADF;
    text-align: center;
    border-radius: 0.2rem;
    font-size: 0.5rem;
    font-weight: 700;
  }
</style>
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
              v-model="from.yhkXm"
              placeholder="请填写真实姓名"
              :maxlength="5"
            ></md-input-item>
            <md-input-item
              ref="input0"
              title="所属银行"
              v-model="from.yhkSsyh"
              placeholder="请填写所属银行"
              :maxlength="5"
            ></md-input-item>
            <md-input-item
              title="银行卡"
              type="bankCard"
              v-model="from.yhkKh"
              placeholder="xxxx xxxx xxxx xxxx"
              :maxlength="20"
            ></md-input-item>
          </md-field>
          <div style="margin: 0.3rem 0">
            <Button type="warning" long
                    @click="yz"
              style="font-size:0.6rem ">提交</Button>
          </div>
        </div>
        <div class="tost" v-show="ToastMessShow">
            {{ToastMess}}
        </div>
      </div>
</template>

<script>
    import { InputItem, Field} from 'mand-mobile'
    import { Button} from 'iview'
    export default {
        name: "addBankCard",
        components:{
            Button,
            [Field.name]:Field,
            [InputItem.name]:InputItem
        },
        data(){
          return{
              from:{
                yhkXm:'',
                yhkSsyh:'',
                yhkKh:''
              },
              ToastMessShow:false,
              ToastMess:'友情提示',
          }
        },
        watch:{
          ToastMessShow:function (n,o) {
            var v = this
            // console.log(n+'------'+o)
            if(n==true){
              setTimeout(function () {
                v.ToastMessShow = false
              },1000*3)
            }
          }
        },
        mounted(){
          this.MyFunc.auto(window, document,11)
        },
        methods:{
          yz(){
            var v = this
            if(v.from.yhkXm==''|| v.from.yhkKh=='' || v.from.yhkSsyh==''){
              v.ToastMessShow = true
              v.ToastMess = '请将填写完整！'
            }else {
              v.addBanh()
            }
          },
          addBanh(){
            var v = this
            this.$http.post(this.apis.ADDBANK,this.from).then((res)=>{
              console.log(res)
              v.ToastMessShow = true
              v.ToastMess = res.message
              if(res.code==200){
                v.$parent.compName = 'bankList'
                v.$parent.getbanklist()
              }
            }).catch((err)=>{

            })
          }
        }
    }
</script>

<style scoped>

</style>
