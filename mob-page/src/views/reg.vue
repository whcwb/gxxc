<template>
  <div id="reg">
      <mt-header fixed title="注册">
        <router-link to="/" slot="left">
          <md-icon name="arrow-left" size="lg"></md-icon>
        </router-link>
      </mt-header>
      <!-- 注册引导流程 -->
      <div id="step">
        <Steps :current="stepIndex">
          <Step title="输入手机号"></Step>
          <Step title="输入验证码"></Step>
          <Step title="设置密码"></Step>
        </Steps>
      </div>
      <!-- 注册引导页内容 -->
      <div v-show="stepIndex == 0">
        <md-field style="margin-bottom: 20px">
          <md-input-item
            ref="name"
            placeholder="请输入手机号码"
            type="phone"
            v-model="form.phone"
            clearable
            :error="error"
            style="border-bottom: 1px gray solid;"
          >
            <i class="iconfont icon-mobile" slot="left" style="font-size: 26px"></i>
          </md-input-item>
        </md-field>
        <div class="box-row">
          <div class="body-O" style="margin-right: 20px;margin-left: 20px">
            <md-button @click="phoneNext">获取短信验证码</md-button>
          </div>
        </div>
      </div>
      <div v-show="stepIndex == 1">
        <md-captcha
          ref="captchaRef"
          :maxlength="6"
          :isView="true"
          :count="10"
          @send="getPhoneCode"
          @submit="vaildCodeNext"
        >
          验证码已发送至{{form.vaildPhone}}
        </md-captcha>
      </div>
      <div v-show="stepIndex == 2">
        <md-field style="margin-bottom: 20px">
          <md-input-item
            ref="name"
            placeholder="请输入姓名"
            v-model="form.name"
            clearable
            style="border-bottom: 1px gray solid;"
          >
            <i class="iconfont icon-user" slot="left" style="font-size: 26px"></i>
          </md-input-item>
          <md-input-item
            ref="name"
            placeholder="请输入密码"
            v-model="form.pwd"
            type="password"
            clearable
            style="border-bottom: 1px gray solid;"
          >
            <i class="iconfont icon-lock" slot="left" style="font-size: 26px"></i>
          </md-input-item>
          <md-input-item
            ref="name"
            placeholder="请输入确认密码"
            v-model="form.secPwd"
            clearable
            type="password"
            style="border-bottom: 1px #e9eaec solid;"
          >
            <i class="iconfont icon-lock" slot="left" style="font-size: 26px"></i>
          </md-input-item>
        </md-field>
        <div class="box-row">
          <div class="body-O" style="margin-right: 20px;margin-left: 20px">
            <md-button @click="reg" style="background-color: #2d8cf0">注册</md-button>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
  import { Button, InputItem, Icon,Field, FieldItem, Captcha  } from 'mand-mobile'
  import {  Header,Toast } from 'mint-ui';
  import { Steps, Step,Tabs,TabPane } from 'iview';

  export default {
    name: 'home-view',
    data(){
      return {
          stepIndex:0,
          error:'',
          form:{
              phone:'',
              vaildPhone:'',
              code:'',
              name:'',
              pwd:'',
              secPwd:''
          },
      }
    },
    components: {
      [Icon.name]: Icon,
      [Button.name]: Button,
      [InputItem.name]: InputItem,
      [Field.name]: Field,
      [FieldItem.name]: FieldItem,
      [Header.name]: Header,
      [Steps.name]: Steps,
      [Step.name]: Step,
      [Tabs.name]: Tabs,
      [TabPane.name]: TabPane,
      [Captcha.name]: Captcha,

  },
    created(){
    },
    methods: {
      //获取短信验证码
      phoneNext() {
          if (this.form.phone == '' || this.form.phone.length < 11){
              this.error = "请输入正确手机号码";
              return;
          }else{
            this.error = "";
            this.getPhoneCode()
          }

          this.form.vaildPhone = this.form.phone.substring(0,3) + "****" + this.form.phone.substring(this.form.phone.length - 4);
          this.$refs.captchaRef.countdown();
      },
      //获取短信验证码
      getPhoneCode(){
        var v = this
        this.$http.post(this.apis.PHINECODE,{'zh':v.form.phone,'yyyqm':localStorage.getItem('yqm')}).then((res)=>{
          if(res.code==200){
            v.stepIndex = 1;
          }else {
            // Toast.failed(res.message)
            Toast(res.message);
          }
        }).catch((err)=>{
          console.log('出错了!')
        })
      },
      //验证短信验证码是否有效
      vaildCodeNext(code){
        console.log('*********',code)
        this.form.code = code
        //请求接口判断验证码是否正确
        var v = this
        this.$http.post(this.apis.YZDX,{zh:v.form.phone,yyyqm:code,type:1}).then((res)=>{
            if(res.code==200){
              console.log(res);
              v.stepIndex = 2;
            }
         }).catch((err)=> {
            console.log('报错了');
         });

      },
      reg(){
          //请求接口进行注册操作
        var v = this
          this.$http.post(this.apis.USERSAVE,{
            yhBm:v.form.name,
            yhZh:v.form.phone,
            yhMm:v.form.pwd,
            yhYyyqm:localStorage.getItem('yqm'),
            yhLx:'1',
            addType:'3',
            telIdentifying:this.form.code
          }).then((res)=>{
              console.log(res);
              // Toast.info('注册成功');
            if(res.code==200){
              Toast({
                message: '操作成功',
                iconClass: 'icon icon-success'
              });
              setTimeout(()=>{
                  this.$router.push("/");
              }, 1000);
            }else {
              console.log(res.message)
            }
          }).catch((err)=> {
               console.log('报错了');
          });

      }
    }
  }
</script>

<style lang="less">
  #reg{
    width: 100%;
    padding: 0;
    margin: 0;
    height: 100%;

    #step{
      text-align: center;
      padding-top: 80px;
    }
    .md-button.primary.large,
    .md-button.primary.small {
      width: 100%;
      height: 60px;
      line-height: 60px;
      font-size: 26px;
      font-weight: 500;
    }

    .md-number-keyboard-container{
      margin-top: 20px;
    }
  }
</style>
