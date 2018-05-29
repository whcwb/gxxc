<template>
  <div id="home">
    <mt-header title="标题过长会隐藏后面的内容啊哈哈哈哈">
      <router-link to="/" slot="left">
        <md-icon name="arrow-left" size="lg"></md-icon>
      </router-link>
    </mt-header>
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
