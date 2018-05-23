<style lang="less">
  @import "./home";
</style>
<template>
  <div id="home_index" class="box">
    <div>
        <md-tab-bar
          :titles="titles"
        ></md-tab-bar>
    </div>
    <div class="home_center body">
      <div class="rd">
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
	import { TabBar ,ActionBar, Toast,Button} from 'mand-mobile'
  export default {
    name: 'home-view',
    components: {
      [TabBar.name]: TabBar,
      [Button.name]: Button,
      [ActionBar.name]: ActionBar,
    },
    data(){
      return{
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
      handleClick() {
        this.$router.push({
          name:'myCenter'
        })
      },
    }
  }
</script>
