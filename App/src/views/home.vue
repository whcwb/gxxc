<style lang="less">
  @import "./home";
  #home_index{
    .tit{
      height: 1rem;
      line-height: 1rem;
      text-align: center;
      font-size: 0.36rem;
      font-weight: 700;
      color: #949494;
    }
    .swiper{
      padding: 0.2rem;
    }
  }
</style>
<style lang="stylus" scoped>
  .md-example-child
    height 250px
    .banner-item
      float left
      width 100%
      height 100%
      line-height 250px
      text-align center
      font-size 28px
      color #FFF
      box-align center
      align-items center
      box-pack center
      justify-content center
      text-decoration-line none
</style>
<template>
  <div id="home_index" class="box">
    <div class="tit">
      520学车
    </div>
    <div class="swiper">
      <div class="md-example-child md-example-child-swiper md-example-child-swiper-0">
        <md-swiper
          ref="swiper">
          <md-swiper-item :key="index" v-for="(item, index) in simple">
            <div style="height: 100%">
              <img :src="item.url" style="height: 100%;width: 100%" alt="">
            </div>
          </md-swiper-item>
        </md-swiper>
      </div>
    </div>
    <div>
        <md-tab-bar
          :titles="titles"
          @indexChanged="tabCh"
        ></md-tab-bar>
    </div>
    <div class="home_center body">
      <div class="rd" v-show="!tpshow">
        <div class="rd_list" v-for="item in [,,,,,]">
            <h1>
              ***驾校
            </h1>
            <h5>
              05月15日发布
            </h5>
            <p>
              通过高速线路将因特网接入人员较密集的场所，由于该地区所发射出的电波可以达到距接入点半径数十米至100米的地方，用户只要将支持无线LAN的笔记本电脑或PDA拿到该区域内，即可高速接入因特网。也就是说，厂商不用耗费资金来进行网络布线接入，从而节省了大量的成本.所以被称为“热点”
            </p>
            <img src="../img/jx.jpg" alt="">
        </div>
      </div>
      <div class="hd" v-show="tpshow">
        <div class="hd_list"  v-for="item in [,,,,,]">
            <div class="box-row">
                <div class="body-O">
                  <div class="box">
                    <div class="body">
                      <p>
                        通过高速线路将因特网接入人员较密集的场所，由于该地区所发射出的电波可以达到距接入点半径数十米至1”
                      </p>
                    </div>
                    <div class="box-row" style="margin-top: 0.2rem">
                      <div>
                        <h1>
                          ***驾校
                        </h1>
                      </div>
                      <div class="body-O" style="padding-left: 0.2rem">
                        <h5>
                          05月15日发布
                        </h5>
                      </div>
                    </div>
                  </div>

                </div>
                <div class="img">
                  <img src="../img/jx.jpg" width="100%" alt="">
                </div>
            </div>
        </div>
      </div>
    </div>
    <div id="index_fooder" class="box-row md-example-child md-example-child-button md-example-child-button-0">
      <div class="body-O">
        <md-button style="background-color: #fff;border: solid 1px #fff;color: #fc9153">首页</md-button>
      </div>
      <div class="body-O" @click="handleClick">
        <md-button style="background-color: #fff;border: solid 1px #fff;color: #a5a5a5">我</md-button>
      </div>
    </div>
  </div>
</template>

<script>
	import { TabBar ,ActionBar, Toast,Button,Swiper, SwiperItem} from 'mand-mobile'
  export default {
    name: 'home-view',
    components: {
      [TabBar.name]: TabBar,
      [Button.name]: Button,
      [ActionBar.name]: ActionBar,
      [Swiper.name]: Swiper,
      [SwiperItem.name]: SwiperItem,
    },
    data(){
      return{
        simple:[
          {
            url:'/static/s1.jpg'
          },{
            url:'/static/s2.jpg'
          }
        ],
        tpshow:false,
        titles:['热点','驾校','训练场'],
        data: [
          {
            text: '首页',
            type:'ghost-primary'
          },
          {
            text: '我',
            onClick: this.handleClick,
          }
        ]
      }
    },
    methods: {
      tabCh(n,o){
        if(n==1){
          this.tpshow = true
        }else {
          this.tpshow = false
        }
      },
      handleClick() {
        this.$router.push({
          name:'myCenter'
        })
      },
    }
  }
</script>
