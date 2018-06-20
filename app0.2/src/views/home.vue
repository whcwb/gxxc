<style lang="less">
  @import "./home";
</style>
<template>
  <div id="home" class="box_col">
    <div class="box_col_auto">
      <component :is="compname"></component>
    </div>
    <div class="homeFooter">
      <div class="box-row" style="height: 100%">
        <div class="box_row_100 footList" v-for="(item,index) in footerList"
             :style="{color:item.now ? '#409eff':'#9c9c9c'}"
              @click="footClick(item,index)">
          <i class="iconfont" :class="item.icon"></i>
          <div class="text">
            {{item.text}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import homePage from './main/homePage'
  import infor from './main/infor'
  import mess from  './main/mess'
  import Homecenter from './main/center'
  export default {
    name: 'home-view',
    components: {
      homePage,infor,mess,Homecenter
    },
    data(){
      return{
        compname:'',
        compnameList:['homePage','mess','Homecenter'],
        footerNum:this.$store.state.app.tabId,
        footerList:[
          {
            now:false,
            icon:'icon-shouye',
            text:'首页'
          },
          // {
          //   now:false,
          //   icon:'icon-zixun',
          //   text:'资讯'
          // },
          {
            now:false,
            icon:'icon-xiaoxi',
            text:'学车'
          },
          {
            now:false,
            icon:'icon-user',
            text:'我的'
          }
        ]
      }
    },
    created(){
      //测试
      this.footerNum = 1;
      //
      this.compname = this.compnameList[this.footerNum]
      this.footerList.forEach((mes,val)=>{
        if(val==this.footerNum){
          mes.now = true
        }else {
          mes.now = false
        }
      })
    },
    methods: {
      footClick(item,index){
        this.$store.commit('M_tabId',index)
        this.compname = this.compnameList[index]
        this.footerList.forEach((mes,val)=>{
          if(val==index){
            mes.now = true
          }else {
            mes.now = false
          }
        })
      }
    }
  }
</script>
