<style lang="less">
@import "./home";
</style>
<template>
      <div id="homeSty" class="box">
        <mt-tab-container v-model="tabId">
          <mt-tab-container-item id="tab-home">
            <home-index></home-index>
          </mt-tab-container-item>
          <mt-tab-container-item id="tab-user">
            <user-index></user-index>
          </mt-tab-container-item>
        </mt-tab-container>
        <div class="fooder">
          <mt-tabbar v-model="tabId">
            <mt-tab-item id="tab-home" style="color: #2d8cf0">
              <div style="margin-bottom: 0.08rem">
                  <i class="iconfont icon-shouye1"></i>
              </div>
              首页
            </mt-tab-item>
            <mt-tab-item id="tab-user" style="color: #6f6f6f">
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
    import list from './qylist'
    import homeIndex from './homeIndex.vue'
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
            qylist:list.qyList,
            tabId:'tab-home'
          }
        },
        created(){
        },
        methods:{
          goMap(item){
            console.log(this.$route)
            this.$router.push({
              name:'jxlist',
              params:item
            })
          }
        }
    }
</script>

<style scoped>

</style>
