<style lang="less">
@import "./login_N";
</style>
<template>
<div id="login" class="box">
  <div class="tit">
    登录
  </div>
  <div class="from md-example-child md-example-child-input-item-1">
    <md-field>
      <md-input-item
        v-for="(item,index) in loginMes"
        style="border-bottom: 2px solid #d9d9d9"
        v-model="item.val"
        :type="item.type"
        :title="item.title"
        :placeholder="item.placeholder"
        is-title-latent
        clearable
        @focus="focus(item,index)"
        @blur="blur(item,index)"
      >
        <Icon :type="item.soltLeft"
              size="22"
              :color="item.iconColor" slot="left"></Icon>
        <div class="error" slot="right" v-show="item.error">
          <div class="errorMess box-row">
            <div class="body-O">
                {{item.errortxt}}
            </div>
            <div>
              <Icon type="information-circled" color="#af3e42"></Icon>
            </div>
          </div>
        </div>
      </md-input-item>
    </md-field>
  </div>
  <div class="but" @click="submit">
      <button>
        登录
      </button>
  </div>
  <div class="box-row resAndforget">
    <div class="body-O">
    注册
    </div>
    <div class="body-O">
      忘记密码？
    </div>
  </div>
</div>
</template>

<script>
  import {InputItem, Field} from 'mand-mobile'
  import {Icon} from 'iview'
  export default {
        name: "login_N",
        components: {
          [InputItem.name]: InputItem,
          [Field.name]: Field,
          Icon
        },
        data(){
          return{
              loginMes:[
                {
                  title:'手机号码',
                  placeholder:'请输入手机号码',
                  type:'phone',
                  soltLeft:'ios-telephone-outline',
                  iconColor:'#d9d9d9',
                  val:'',
                  error:false,
                  errortxt:'请输入正确的手机号码!'
                },{
                  title:'密码',
                  placeholder:'请输入密码',
                  type:'password',
                  soltLeft:'ios-locked-outline',
                  iconColor:'#d9d9d9',
                  val:'',
                  error:false,
                  errortxt:'请输密码!'
                }
              ]
          }
        },
        methods:{
          focus(item,index){
            item.iconColor = '#a34cf1'
            item.error=false
          },
          blur(item,index){
            var phone = /^[1][3,5,7,8][0-9]{9}$/
            if(index==0){
              console.log(phone.test(item.val))
              if(phone.test(item.val)){
                item.error=false
              }else {
                item.error=true
              }
            }else if(index==1){
              if(item.val.length>=8){
                item.error=false
              }else {
                item.error=true
              }
            }
            item.iconColor = '#d9d9d9'
          },
          submit(){
            var v = this
            if(v.loginMes[0].error==false&&v.loginMes[1].error==false&&v.loginMes[0].val!=""){
              alert('denglu ')
            }else{
              v.loginMes.forEach((item,index)=>{
                item.error = true
              })
            }
          }
        }
    }
</script>

<style scoped>

</style>
