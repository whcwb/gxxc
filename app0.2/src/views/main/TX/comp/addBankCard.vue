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
             style="height: 1rem;line-height: 1rem;
             color: #fff;background-color: #0676d8">
          <div style="width: 1rem;text-align: center;line-height: 0.5rem"
               @click="$parent.compName='bankList'">
            <i class="iconfont icon-left1"></i>
          </div>
          <div class="" style="font-size: 0.3rem;font-weight: 700;line-height: 1rem">
            添加银行卡
          </div>
          <div class="box_row_100" v-show="bankImg!=''" style="text-align: right">
            <img :src="bankImg" style="width: 50%" alt="">
          </div>
        </div>
        <div class="body" style="">
          <div style="font-size: 0.3rem;padding: 0.1rem 0.2rem;color:#424242;background-color: #fff;border-bottom: #f2f2f2 1px solid">
            <span style="color: red;font-weight: 700;font-size: 0.35rem">*</span>
            请绑定持卡人本人的银行卡
          </div>
          <md-field>
            <md-input-item
              title="姓名"
              v-model="from.yhkXm"
              placeholder="请填写真实姓名"
            ></md-input-item>
            <!--<md-input-item-->
              <!--title="所属银行"-->
              <!--v-model="from.yhkSsyh"-->
              <!--placeholder="请填写所属银行"-->
              <!--:maxlength="5"-->
            <!--&gt;</md-input-item>-->
            <md-input-item
              title="手机号码"
              v-model="from.dn"
              placeholder="请输入银行预留手机号"
              :maxlength="11"
            ></md-input-item>
            <md-input-item
              title="银行卡"
              type="bankCard"
              v-model="from.yhkKh"
              placeholder="xxxx xxxx xxxx xxxx"
              :maxlength="20"
              @focus="yhkYz = true"
            >
              <div slot="right" style="position: relative;width: 100%;height: 100%;color: #ffa7007d!important;" @click="getBankCrdNumber">
                <i class="iconfont icon-xiangji"
                    style="position: absolute;top: 0;top: 12px;font-size: 0.5rem;color: #ffa7007d!important;"></i>
              </div>
            </md-input-item>
              <!--@blur="(name)=>{losesfocus(name,from.yhkKh)}"-->
          </md-field>
          <div v-show="yhkYz" @click="losesfocus" style="padding: 0.1rem 0.6rem;margin: 0.3rem 0">
            <el-button type="warning"
                       style="width: 100%;padding: 0.3rem 0;font-size: 0.3rem"
            >银行卡验证</el-button>
          </div>
          <div v-show="!yhkYz" @click="yz" style="padding: 0.3rem 0.6rem;margin: 0.3rem 0">
            <el-button type="primary"
                       style="width: 100%;padding: 0.3rem 0;font-size: 0.3rem"
            >提交</el-button>
          </div>
          <div style="color: #da0808;font-size: 0.4rem;padding: 0.3rem 0.6rem;margin: 0 0.25rem">
            {{errMess}}
          </div>
        </div>
      </div>
</template>

<script>
    import { InputItem, Field} from 'mand-mobile'
    export default {
        name: "addBankCard",
        components:{
            // Button,
            [Field.name]:Field,
            [InputItem.name]:InputItem
        },
        data(){
          return{
            yhkYz:true,
              from:{
                yhkXm:'',
                // yhkSsyh:'',
                yhkKh:'',
                dn:localStorage.getItem('phone')
              },
            errMess:'',
            bankImg:'',
            // http://lundroid.com/banklogo/8003a3.png
          }
        },
        watch:{
        },
        created(){
          this.util.GetUserMess(this, (res) => {
            this.from.yhkXm = res.yhXm
          })
        },
        mounted(){
          this.util.auto(window, document,7.5)
        },
        methods:{
          losesfocus(){
            var v = this
            if(v.from.yhkXm==''|| v.from.yhkKh=='' || v.from.dn==''){
              v.errMess = '请将填写完整！'
              return
            }

            this.$http.post(this.apis.YZYHK,this.from).then((res)=>{
              console.log('一行卡',res)
              if(res.code==200){
                v.yhkYz = false
                v.bankImg = res.result.bank_logo
                v.errMess = res.result.bank_name + res.result.card_type +'验证通过'
              }else {
                v.errMess = res.message+'请将信息填写完整'
              }
            }).catch((err)=>{
              console.log('出错了');
            })
          },



          yz(){
            var v = this
            if(v.from.yhkXm==''|| v.from.yhkKh=='' || v.from.dn==''){
              v.errMess = '请将填写完整！'
            }else {
              v.addBanh()
            }
          },
          addBanh(){
            var v = this
            this.$http.post(this.apis.ADDBANK,this.from).then((res)=>{
              console.log(res)
              v.errMess = res.message
              if(res.code==200){
                v.$parent.compName = 'bankList'
                v.$parent.getbanklist()
              }
            }).catch((err)=>{

            })
          },
          getBankCrdNumber(){
              var v = this
              this.wechatUtil.chooseImage((imgID)=>{
                v.wechatUtil.uploadImage(imgID[0],(httpID)=>{
                  v.UPIMG(httpID.serverId)
                })
              })
          },
          UPIMG(id){
            var v = this
            this.$http.post(this.apis.WXIMGUP,{code:id,fileType:'30'}).then((res)=>{
              console.log('图片返回参数',res)
              // alert(JSON.stringify(res))
              if(res.code == 200){
                v.from.yhkKh = res.result.bank_card_number
              }
            }).catch((err)=>{
              console.log('银行卡FUCk',err);
            })
          },
        }
    }
</script>

<style scoped>

</style>
