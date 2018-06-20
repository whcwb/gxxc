<style lang="less">
@import "./home";
</style>
<template>
      <div id="homeSty" class="box">
        <div class="body">
          <div style="height: 100%;">
            <home-index v-if="tabId=='tab-home'"></home-index>
            <user-index v-else-if="tabId=='tab-user'"></user-index>
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
