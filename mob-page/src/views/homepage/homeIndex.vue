<style lang="less">
@import "./home";
</style>
<template>
      <div id="homeSty" class="box">
        <div class="tit">
          520学车联盟
          <!--<mt-header fixed title="520学车联盟"></mt-header>-->
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
        <div class="bookmark box-row-list">
          <div class="bokList">
            <a href="#qy">
              <mt-button type="primary">
                区域选着
              </mt-button>
            </a>
          </div>
          <div class="bokList">
            <a href="#bm">
              <mt-button type="primary">
                报名详细
              </mt-button>
            </a>
          </div>
          <div class="bokList">
            <a href="#xc">
              <mt-button type="primary">
                学车指引
              </mt-button>
            </a>
          </div>
          <div class="bokList">
            <a href="#ks">
              <mt-button type="primary">
                考试须知
              </mt-button>
            </a>
          </div>
        </div>
        <div class="body">
          <a name="qy"></a>
          <div class="qy fu">
            <div class="Htit">
              区域查找
            </div>
            <div class="box-row-list mess">
              <div class="qyName"
                   @click="goMap(item)"
                   v-for="(item,index) in qylist">
                {{item.name}}
              </div>
            </div>
          </div>

          <a name="bm"></a>
          <div class="bm fu">
            <div class="Htit">
              报名详细
            </div>
            <div class="mess">
              选着您
            </div>
          </div>

          <a name="xc"></a>
          <div class="xc fu">
            <div class="Htit">
              学车指引
            </div>
            <div class="mess">
              我要学车
            </div>
          </div>

          <a name="ks"></a>
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
      </div>
</template>

<script>
    import { Header, Swipe, SwipeItem , Tabbar, TabItem,Button, TabContainer, TabContainerItem } from 'mint-ui';
    import list from './qylist'
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
          [TabContainerItem.name]:TabContainerItem
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
              name:'region',
              params:item
            })
          }
        }
    }
</script>

<style scoped>

</style>
