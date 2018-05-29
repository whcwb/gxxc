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
              区域选着
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
                报名流程
            </div>
            <div class="mess box-row-list">
              <div style="width: 14%">
                选着您心目中的训练场地
              </div>
              <div style="width: 14%;text-align: center">
                <i class="iconfont icon-1202youjiantou"></i>
              </div>
              <div style="width: 14%">
                亲身体验训练场
              </div>
              <div style="width: 14%;text-align: center">
                <i class="iconfont icon-1202youjiantou"></i>
              </div>
              <div style="width: 14%">
                缴费2500元
              </div>
              <div style="width: 14%;text-align: center">
                <i class="iconfont icon-1202youjiantou"></i>
              </div>
              <div style="width: 14%">
                报名成功
              </div>
            </div>
          </div>

          <a name="xc"></a>
          <div class="xc fu">
            <div class="Htit">
                学车指引
            </div>
            <div class="mess" style="height: 2.8rem;">
              <mt-swipe :auto="1000*6">
                <mt-swipe-item>
                  <div>
                    <h3>科目一学习</h3>
                  </div>
                  科目一，又称科目一理论考试、驾驶员理论考试，是机动车驾驶证考核的一部分 。根据《机动车驾驶证申领和使用规定》，考 试内容包括驾车理论基础、道路安全法律法规、地方性法规等相关知识。考试形式为上机考试，100道题，90分及以上过关。

                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目二学习</h3>
                  </div>
                  小车科目二考试又叫小路考，包括倒车入库、侧方停车、坡道定点、直角拐弯、曲线行驶等5个科目二考试技巧项目，满分100分，80分及以上合格，更多内容请看小车C1科目二考试内容及合格标准。
                  <div style="text-align: right">
                    <a href="###">
                    科目二官方视频讲解>>>>>>
                    </a>
                  </div>
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目三学习</h3>
                  </div>
                  小车科目三考试又叫大路考，您需要掌握包括上车准备、直线行驶、百米加减档、灯光模拟考试等17项科目三考试技巧，满分为100分，80分及以上为及格。更多内容请看小车C1科目三考试内容及合格标准。
                  <div style="text-align: right">
                    <a href="###">
                    科目三官方视频讲解>>>>>>
                    </a>
                  </div>
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目四考学习</h3>
                  </div>
                  科目四，又称科目四理论考试、驾驶员理论考试，是机动车驾驶证考核的一部分。公安部123号令实施后，科目三考试分为两项内容，除了路考，增加了安全文明驾驶考试，俗称“科目四”，考量“车德”。因为这个考试在科目三之后进行，所以大家都习惯称之为科目四考试。实际的官方说法中没有科目四一说
                </mt-swipe-item>
              </mt-swipe>
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
                  请带身份证件和准考证到指定的地点考试

                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目二考试须知</h3>
                  </div>
                  请带身份证件和准考证到指定的地点考试
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目三考试须知</h3>
                  </div>
                  请带身份证件和准考证到指定的地点考试
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目四考试须知</h3>
                  </div>
                  请带身份证件和准考证到指定的地点考试
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
            <mt-tab-item id="我的" style="color: #6f6f6f">
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
    import { Header, Swipe, SwipeItem , Tabbar, TabItem,Button } from 'mint-ui';
    import list from './qylist'
    export default {
        name: "index",
        components:{
          [Header.name]: Header,
          [Swipe.name]: Swipe,
          [SwipeItem.name]: SwipeItem,
          [Tabbar.name]: Tabbar,
          [TabItem.name]: TabItem,
          [Button.name]:Button
        },
        data(){
          return{
            qylist:list.qyList
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
