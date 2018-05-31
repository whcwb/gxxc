<style lang="less">
  .timeList{
    padding: 0.25rem;
    font-size: 0.3rem;
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
              font-size: 0.35rem;
            }
            .money{
              /*color: #ff8e00;*/
              font-weight: 600;
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
</style>
<template>
      <div class="box">
          <box-head tit="我的团队">
            <div slot="left" style="color: #E0DADF">
              <i class="iconfont icon-left1"></i>
            </div>
          </box-head>
          <md-tab-bar
            :titles="titles"
            @indexChanged="tabbarClick"
          ></md-tab-bar>
          <div class="body timeList" style="">
            <div class="list" v-for="item in  list">
              <div class="box-row ddsty">
                <div style="width: 1.1rem">
                  <img src="/static/bill.png"
                       width="100%" alt="">
                </div>
                <div class="body-O" style="text-align: right">
                  <div class="box">
                    <div class="body text">
                      <div class="box-row">
                        <div class="body-O">
                          {{item.text}}
                        </div>
                        <div class="body-O">
                          <a :href="'tel:'+item.phone">{{item.phone}}</a>
                        </div>
                      </div>
                    </div>
                    <div class="money" :style="{color:item.color}">
                      <!--<Icon type="social-yen"></Icon>-->
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
    import { TabBar } from 'mand-mobile'
    import {Icon} from 'iview'
    export default {
        name: "index",
        components:{
          Icon,
          [TabBar.name]: TabBar,
        },
        data(){
          return{
            titles: ['全部','一级','二级'],
            list:[],
            ALL:[
              [{//已完成
                text:'夏明明',
                phone:'13112312332',
                money:'已交费',
                color:"#00b65f",
                typ:'一级'
              },{//已完成
                text:'李小二',
                phone:'13112312332',
                money:'未交费',
                color:"#ff8800",
                typ:'二级'
              }],
              [{//已完成
                text:'夏明明',
                phone:'13112312332',
                money:'已交费',
                color:"#00b65f",
                typ:'一级'
              }],
              [//已取消
                {
                  text:'李小二',
                  phone:'13112312332',
                  money:'未交费',
                  color:"#ff8800",
                  typ:'二级'
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
