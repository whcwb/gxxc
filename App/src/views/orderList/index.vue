<style lang="less">
  #DD{
    .yjmx{
      padding: 0.2rem;
      background-color: #d7d7d74f;
      .list{
        background-color: #fff;
        margin-bottom: 0.2rem;
        border-bottom:solid 2px #949494;
        padding: 0.16rem 0.2rem 0;
        .ddsty{
            .body-O{
              padding-left: 0.2rem;
              text-align: right;
              font-size: 0.3rem;
              .text{

              }
              .money{
                  color: #ff8e00;
                  font-size: 0.3rem;
              }
              .typ{
                font-size: 0.26rem;
                padding: 0.06rem;
              }
            }
        }
      }
    }
  }

</style>
<template>
  <div id="DD" class="box">
    <headTit tit="订单"></headTit>
    <md-tab-bar
      :titles="titles"
      @indexChanged="tabbarClick"
    ></md-tab-bar>
    <div v-show="list.length==0" class="body md-example-child md-example-child-result-page md-example-child-result-page-2">
      <md-result-page
        :buttons="buttons">
      </md-result-page>
    </div>
    <div class="body yjmx">
      <div class="list" v-for="item in  list">
        <div class="box-row ddsty">
          <div style="width: 1.5rem">
            <img src="../../img/ddimg.png"
                 width="100%" alt="">
          </div>
          <div class="body-O">
              <div class="box">
                  <div class="body text">
                    <p>{{item.text}}</p>
                  </div>
                  <div class="money">
                    <Icon type="social-yen"></Icon>
                    {{item.money}}
                  </div>
                  <div class="typ">
                    {{item.typ}}
                  </div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import headTit from '../comp/headTit'
  import { TabBar , ResultPage,Toast} from 'mand-mobile'
  import {Icon} from 'iview'
  export default {
    name: "yjtxmx",
    components:{
      [ResultPage.name]: ResultPage,
      [TabBar.name]: TabBar,
      headTit,Icon
    },
    data() {
      return {
        buttons: [
          {
            text: '信息获取',
            handler() {
              Toast.succeed('数据获取中')
            }
          }
        ],
        titles: ['已完结','已取消'],
        list:[],
        ALL:[
          [{//已完成
            text:'平台合伙人 2500元加入我们',
            money:'2500',
            typ:'已完成'
          }],
          [//已取消
            {
              text:'平台合伙人 2500元加入我们',
              money:'2500',
              typ:'已取消'
          }]
        ]
      }
    },
    created(){
      this.list= this.ALL[0]
    },
    methods:{
      tabbarClick(n,o){
        var v =this
        v.list = v.ALL[n]
      }
    }
  }
</script>

<style scoped>

</style>
