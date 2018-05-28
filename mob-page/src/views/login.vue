<template>
  <div id="login">
      <!-- logo区域 -->
      <div id="logo">
        <img src="static/icon/logo.png" width="120" height="120">
        <dt style="font-size: 28px;color: white">
          学 车 联 盟
        </dt>
      </div>
      <!-- 登录输入项区域 -->
      <div>
          <md-input-item
            ref="name"
            title="账号"
            placeholder="请输入登录账号"
            is-title-latent
            clearable
            style="border-bottom: 1px gray solid;margin: 20px;;margin-bottom: 5px"
          >
            <i class="iconfont icon-user" slot="left" style="font-size: 26px"></i>
          </md-input-item>
          <md-input-item
            ref="id"
            title="密码"
            placeholder="请输入登录密码"
            is-title-latent
            clearable
            style="border-bottom: 1px gray solid;margin: 20px;margin-top: 0px"
          >
            <i class="iconfont icon-lock" slot="left" style="font-size: 26px"></i>
          </md-input-item>
      </div>
      <!-- 操作按钮区域 -->
      <div class="box-row">
        <div class="body-O"></div>
        <div style="margin-right: 20px">
            <a style="color: #dddee1;font-size: 20px;vertical-align: top" @click="handleClick">忘记密码？</a>
        </div>
      </div>
      <div class="box-row">
        <div class="body-O" style="margin-right: 20px;margin-left: 20px">
          <md-button @click="login" type="ghost-primary" style="width:100%;font-size: 26px;color:white;border-bottom-color:#19be6b">登&nbsp;&nbsp;&nbsp;&nbsp;录</md-button>
        </div>
      </div>
      <div class="box-row">
        <div class="body-O" style="text-align: center">
          <a style="color: #FFCC66;font-size: 24px;" @click="reg">创 建 账 号</a>
        </div>
      </div>
  </div>
</template>

<script>
  import { Button, InputItem, Icon, Toast } from 'mand-mobile'

  export default {
    name: 'home-view',
    components: {
      [Icon.name]: Icon,
      [Button.name]: Button,
      [InputItem.name]: InputItem
    },
    methods: {
      handleClick() {
        Toast.succeed('操作成功');
      },
      login(){
          this.$router.push("/");
      },
      reg(){
          this.$router.push("/reg");
      }
    }
  }
</script>

<style lang="less">
  #login{
    background-color: #2b85e4;
    width: 100%;
    padding: 0;
    margin: 0;
    height: 100%;

    #logo{
      text-align: center;
    }
    #logo img{
      position: relative;
      padding-top: 80px;
      left: 0;
      right: 0;
    }
    .md-input-item.is-title-latent .md-input-item-title{
      color: white;
    }
  }
</style>
