<style lang="less">
  /*@import "./homepage/home";*/
</style>
<template>
  <div id="homeSty" class="box">
    <div class="body">
      <div style="height: 100%;">
        <home-index v-if="tabId=='tab-home'"></home-index>
        <user-index v-else-if="tabId=='tab-user'"></user-index>
        <!--<mt-tab-container v-model="tabId">-->
        <!--<mt-tab-container-item id="tab-home">-->
        <!--<home-index></home-index>-->
        <!--</mt-tab-container-item>-->
        <!--<mt-tab-container-item id="tab-user">-->
        <!--<user-index></user-index>-->
        <!--</mt-tab-container-item>-->
        <!--</mt-tab-container>-->
      </div>
    </div>
    <div class="fooder">
      <mt-tabbar v-model="tabId">
        <mt-tab-item id="tab-home">
          <div style="margin-bottom: 0.08rem">
            <i class="iconfont icon-shouye1"></i>
          </div>
          首页
        </mt-tab-item>
        <mt-tab-item id="tab-user">
          <div style="margin-bottom: 0.08rem">
            <i class="iconfont icon-gerenzhongxin"></i>
          </div>
          我的
        </mt-tab-item>
      </mt-tabbar>
    </div>
  </div>
</template>

<script>
  import { Header, Swipe, SwipeItem , Tabbar, TabItem,Button, TabContainer, TabContainerItem } from 'mint-ui';
  import homeIndex from '@/views/homepage/homeIndex.vue'
  import userIndex from '@/views/myCenter'
  export default {
    name: "index",
    components:{
      [Header.name]: Header,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [Tabbar.name]: Tabbar,
      [TabItem.name]: TabItem,
      [Button.name]:Button,
      [TabContainer.name]:TabContainer,
      [TabContainerItem.name]:TabContainerItem,
      homeIndex,userIndex
    },
    data(){
      return{
        tabId:this.$store.state.app.tabId
      }
    },
    watch:{
      tabId:function (n,o) {
        this.$store.commit('M_tabId', n)
      }
    },
    created(){

    },
    methods:{
    }
  }
</script>

<style scoped>

</style>
