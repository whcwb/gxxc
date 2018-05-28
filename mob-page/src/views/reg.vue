<template>
  <div id="reg">
      <mt-header fixed title="注册">
        <router-link to="/login" slot="left">
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
        {{stepIndex}}
        <div class="box-row">
          <div class="body-O" style="margin-right: 20px;margin-left: 20px">
            <md-button @click="phoneNext">验证码</md-button>
          </div>
        </div>
      </div>
      <div v-show="stepIndex == 2">
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
  </div>
</template>

<script>
  import { Button, InputItem, Icon, Toast,Field, FieldItem  } from 'mand-mobile'
  import {  Header } from 'mint-ui';
  import { Steps, Step,Tabs,TabPane } from 'iview';

  export default {
    name: 'home-view',
    data(){
      return {
          stepIndex:0,
          error:'',
          form:{
              phone:''
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

  },
    methods: {
      phoneNext() {
          if (this.form.phone == '' || this.form.phone.length < 11){
              this.error = "请输入正确手机号码";
              return;
          }else{
            this.error = "";
          }

          this.stepIndex++;
      },
      login(){
          this.$router.push("/");
      },
      reg(){
        Toast.info('操作成功');
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
  }
</style>
