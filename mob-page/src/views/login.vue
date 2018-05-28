<template>
  <div id="login">
      <div id="logo">
        <img src="static/icon/logo.png" width="120" height="120">
      </div>
    <mt-header title="标题过长会隐藏后面的内容啊哈哈哈哈">
    <router-link to="/" slot="left">
      <md-icon name="arrow-left" size="lg"></md-icon>
    </router-link>
    <mt-button icon="more" slot="right"></mt-button>
    </mt-header>
    <md-button  type="ghost" size="small" disabled style="margin-left:5px">Primary</md-button>
    <!--<md-button @click="handleClick">点我</md-button>-->
    <mt-tabbar>
      <mt-tab-item id="外卖">
        外卖
      </mt-tab-item>
      <mt-tab-item id="订单">
        订单
      </mt-tab-item>
      <mt-tab-item id="发现">
        发现
      </mt-tab-item>
      <mt-tab-item id="我的">
        我的
      </mt-tab-item>
    </mt-tabbar>
    我是登录界面
  </div>
</template>

<script>
  import { Button, Toast, Icon } from 'mand-mobile'
  import { Header, Tabbar, TabItem } from 'mint-ui';

  export default {
    name: 'home-view',
    components: {
      [Icon.name]: Icon,
      [Button.name]: Button,
      [Header.name]: Header,
      [Tabbar.name]: Tabbar,
      [TabItem.name]: TabItem,
    },
    methods: {
      handleClick() {
        Toast.info('不错哟~')
      }
    }
  }
</script>

<style scoped>
  #login{
    background-color: #2b85e4;
    width: 100%;
    padding: 0;
    margin: 0;
    height: 100%;
  }
  #logo{
    text-align: center;
  }
  #logo img{
    position: relative;
    padding-top: 80px;
    left: 0;
    right: 0;
  }
</style>
