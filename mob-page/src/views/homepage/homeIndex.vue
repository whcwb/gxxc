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
            <mt-swipe-item v-for="(item,index) in banerImg">
              <img :src="item.hdTpdz"
                   style="height: 100%;width: 100%"
                   alt="">
            </mt-swipe-item>
          </mt-swipe>
        </div>
        <div class="bookmark box-row-list">
          <div class="bokList">
            <a href="#qy">
              <mt-button type="primary">
                区域选择
              </mt-button>
            </a>
          </div>
          <div class="bokList">
            <a href="#bm">
              <mt-button type="primary">
                报名流程
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
                考场分布
              </mt-button>
            </a>
          </div>
        </div>
        <div class="body">
          <a name="qy"></a>
          <div class="qy fu">
            <div class="Htit">
              区域选择
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
              <span style="font-size: 0.24rem;font-weight: 500;color: #ff5900">
                以下学习资源来源于驾考宝典</span>
            </div>
            <div class="mess" style="height: 2.8rem;">
              <mt-swipe :auto="1000*30">
                <mt-swipe-item>
                  <div class="box-row">
                    <h3>科目一理论学习</h3>
                  </div>
                  <div class="box-row ky">
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/exercise/0-car-kemu1-beijing.html">
                        <i class="iconfont icon-buoumaotubiao25"></i>
                        <div>
                          顺序练习
                        </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/chapter/car-kemu1-beijing.html">
                        <i class="iconfont icon-gongnengqiehuan2"></i>
                        <div>
                          章节练习
                        </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/strengthen/car-kemu1-beijing.html">
                          <i class="iconfont icon-wenjian"></i>
                          <div>
                            专项练习
                          </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/exam/car-kemu1-beijing.html">
                        <i class="iconfont icon-sifakaoshi"></i>
                        <div>
                          全真模拟
                        </div>
                      </a>
                    </div>
                  </div>
                  <!--科目一，又称科目一理论考试、驾驶员理论考试，是机动车驾驶证考核的一部分 。根据《机动车驾驶证申领和使用规定》，考 试内容包括驾车理论基础、道路安全法律法规、地方性法规等相关知识。考试形式为上机考试，100道题，90分及以上过关。-->

                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目二视频学习</h3>
                  </div>
                  <div style="text-align: center">
                    <a href="http://m.jiakaobaodian.com/mnks/kemu2/car-beijing.html">
                      <img style="width: 100%;height: 2.5rem"
                        src="/static/ke.jpg" alt="">
                    </a>
                  </div>
                  <!--小车科目二考试又叫小路考，包括倒车入库、侧方停车、坡道定点、直角拐弯、曲线行驶等5个科目二考试技巧项目，满分100分，80分及以上合格，更多内容请看小车C1科目二考试内容及合格标准。-->
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目三视频学习</h3>
                  </div>
                  <div style="text-align: center">
                    <a target="_blank" href="http://m.jiakaobaodian.com/mnks/kemu3/car-beijing.html">
                      <img style="width: 100%;height: 2.5rem"
                           src="/static/kms.jpg" alt="">
                    </a>
                  </div>
                  <!--小车科目三考试又叫大路考，您需要掌握包括上车准备、直线行驶、百米加减档、灯光模拟考试等17项科目三考试技巧，满分为100分，80分及以上为及格。更多内容请看小车C1科目三考试内容及合格标准。-->
                </mt-swipe-item>
                <mt-swipe-item>
                  <div>
                    <h3>科目四理论学习</h3>
                  </div>
                  <div class="box-row ksi">
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/exercise/0-car-kemu4-beijing.html">
                        <i class="iconfont icon-buoumaotubiao25"></i>
                        <div>
                          顺序练习
                        </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/chapter/car-kemu4-beijing.html">
                        <i class="iconfont icon-gongnengqiehuan2"></i>
                        <div>
                          章节练习
                        </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/strengthen/car-kemu4-beijing.html">
                        <i class="iconfont icon-wenjian"></i>
                        <div>
                          专项练习
                        </div>
                      </a>
                    </div>
                    <div class="body-O">
                      <a href="http://m.jiakaobaodian.com/mnks/exam/car-kemu4-beijing.html">
                        <i class="iconfont icon-sifakaoshi"></i>
                        <div>
                          全真模拟
                        </div>
                      </a>
                    </div>
                  </div>
                  <!--科目四，又称科目四理论考试、驾驶员理论考试，是机动车驾驶证考核的一部分。公安部123号令实施后，科目三考试分为两项内容，除了路考，增加了安全文明驾驶考试，俗称“科目四”，考量“车德”。因为这个考试在科目三之后进行，所以大家都习惯称之为科目四考试。实际的官方说法中没有科目四一说-->
                </mt-swipe-item>
              </mt-swipe>
            </div>
          </div>

          <a name="ks"></a>
          <div class="ks fu">
            <div class="Htit">
              考场分布
            </div>
            <div class="mess ksswiper box-row-list">
              <div class="ksList" @click="$router.push({name:'kcfb'})">
                <div class="ksess">
                  <div>
                    科目一
                  </div>
                  <div>
                    考场分布
                  </div>
                </div>
              </div>
              <div class="ksList"  @click="$router.push({name:'kcfb'})">
                <div class="ksess">
                  <div>
                    科目二
                  </div>
                  <div>
                    考场分布
                  </div>
                </div>
              </div>
              <div class="ksList"  @click="$router.push({name:'kcfb'})">
                <div class="ksess">
                  <div>
                    科目三
                  </div>
                  <div>
                    考场分布
                  </div>
                </div>
              </div>
              <div class="ksList" @click="$router.push({name:'kcfb'})">
                <div class="ksess">
                  <div>
                    科目四
                  </div>
                  <div>
                    考场分布
                  </div>
                </div>
              </div>

              <mt-swipe :auto="1000*6" v-show="false">
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
            tabId:'tab-home',
            banerImg:[]
          }
        },
        created(){
          this.getSwiperImg()
        },
        methods:{
          goMap(item){
            console.log(this.$route)
            this.$router.push({
              name:'jxlist',
              params:item
            })
          },
          getSwiperImg(){
            this.$http.post(this.apis.SWIPER,{hdSxs:0}).then((res)=>{
              if(res.code==200){
                this.banerImg = res.page.list
              }
              console.log(res)
            }).catch((err)=>{

            })
          }
        }
    }
</script>

<style scoped>

</style>
