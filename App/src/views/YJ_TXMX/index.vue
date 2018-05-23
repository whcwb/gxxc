<style lang="less">
  #yjtxmx{
    .yjmx{
        padding: 0.2rem;
        background-color: #ebebeb;
        .list{
          background-color: #fff;
          margin-bottom: 0.2rem;
          border-bottom:solid 2px #949494;
          padding: 0.2rem 0.2rem 0;
          .box-row{
              font-size: 0.3rem;
              color: #000;
              .time{
                  i{
                    color: #00a0ff;
                  }
              }
              .money{
                i{
                  color: #ff8e00;
                }

              }
          }
          .fs{
              margin: 0.2rem 0;
              .togo{
                i{
                    color: #21aa3f;
                }
              }
              .typ{

              }
          }
        }
    }
  }

</style>
<template>
      <div id="yjtxmx" class="box">
        <headTit tit="提现明细"></headTit>
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
              <div class="box-row">
                <div class="time body-O">
                    <Icon type="ios-clock"></Icon>
                    时间:{{item.time}}
                </div>
                <div class="money">
                    <Icon type="social-yen"></Icon>
                    {{item.money}}元
                </div>
              </div>
              <div class="fs box-row">
                <div class="body-O togo">
                    <Icon type="shuffle"></Icon>
                    银行卡 {{item.to}}
                </div>
                <div class="typ">
                  {{item.typ}}
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
            titles: ['全部', '待审核', '待打款','已打款','无效'],
            list:[],
            ALL:[
              [{//全部
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'待审核'
              },{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'待打款'
              },{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'已打款'
              }],
              [//待审核
                {
                  time:'2018-07-08 09:00:00',
                  money:'500',
                  to:'8888*********222',
                  typ:'待审核'
                },{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'待审核'
              }],
              [
                {
                  time:'2018-07-08 09:00:00',
                  money:'500',
                  to:'8888*********222',
                  typ:'待打款'
                },{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'待打款'
              }],
              [{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'已打款'
                },{
                time:'2018-07-08 09:00:00',
                money:'500',
                to:'8888*********222',
                typ:'已打款'
              }],
              []
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
