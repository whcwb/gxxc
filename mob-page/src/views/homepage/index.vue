<style lang="less">
@import "./home";
</style>
<template>
      <div id="homeSty" class="box">
        <div class="tit">
          <mt-header fixed title="520学车联盟"></mt-header>
        </div>
        <div class="swipe">
            <mt-swipe :auto="4000">
              <mt-swipe-item>
                <img src="static/s1.jpg"
                     style="height: 100%;width: 100%"
                     alt="">
              </mt-swipe-item>
              <mt-swipe-item style="background-color: #f0f">
                <img src="static/s2.jpg"
                     style="height: 100%;width: 100%"
                     alt="">
              </mt-swipe-item>
            </mt-swipe>
        </div>
        <div class="body">
          <div class="qy fu">
            <div class="Htit">
                区域查找
            </div>
            <div class="box-row-list mess">
              <div class="qyName" v-for="(item,index) in qylist">
                {{item.name}}
              </div>
            </div>
          </div>

          <div class="bm fu">
            <div class="Htit">
                报名详细
            </div>
            <div class="mess">
              <h1>我要报名</h1>
            </div>
          </div>

          <div class="xc fu">
            <div class="Htit">
              学车指引
            </div>
            <div class="mess">
              <h1>我要学车</h1>
            </div>
          </div>

          <div class="ks fu">
            <div class="Htit">
                考试须知
            </div>
            <div class="mess ksswiper">
              <mt-swipe :auto="1000*6">
                <mt-swipe-item>
                  <div>
                    <h3>科目一考试须知</h3>
                  </div>
                  请带好相关证件

                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目二考试须知</h3>
                  </div>
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目三考试须知</h3>
                  </div>
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目四考试须知</h3>
                  </div>
                </mt-swipe-item>
              </mt-swipe>
            </div>
          </div>
        </div>
        <div class="fooder">
          <mt-tabbar>
            <mt-tab-item id="首页" style="color: #2d8cf0">
              <div style="margin-bottom: 0.08rem">
                  <i class="iconfont icon-shouye1"></i>
              </div>
              首页
            </mt-tab-item>
            <mt-tab-item id="我的">
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
    import { Header, Swipe, SwipeItem , Tabbar, TabItem } from 'mint-ui';
    import list from './qylist'
    export default {
        name: "index",
        components:{
          [Header.name]: Header,
          [Swipe.name]: Swipe,
          [SwipeItem.name]: SwipeItem,
          [Tabbar.name]: Tabbar,
          [TabItem.name]: TabItem
        },
        data(){
          return{
            qylist:list.qyList
          }
        },
        created(){
        }
    }
</script>

<style scoped>

</style>
