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
            font-size: 0.28rem;
            .text{
              font-size: 0.3rem;
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
  .mint-navbar{
    .mint-tab-item{
      .mint-tab-item-label{
        font-size: 0.24rem;
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
          <mt-navbar v-model="selected">
            <mt-tab-item id="0">全部</mt-tab-item>
            <mt-tab-item id="1">一级</mt-tab-item>
            <mt-tab-item id="2">二级</mt-tab-item>
          </mt-navbar>
          <div class="body timeList" style="">
            <div class="list" v-for="item in  list">
              <div class="box-row ddsty">
                <div style="width: 0.8rem">
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
    import { Navbar, TabItem } from 'mint-ui';
    import {Icon} from 'iview'
    export default {
        name: "index",
        components:{
          Icon,
          [Navbar.name]: Navbar,
          [TabItem.name]: TabItem,
        },
        data(){
          return{
            selected:'0',
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
            ],
            pageList:{
              userGrade:0,
              pageSize:999999999,
              pageNum:1
            },
          }
        },
      watch:{
        selected:function(n,o) {
          this.list = this.ALL[n]
        }
      },
      created(){
        this.list= this.ALL[0]
        this.teamList()
      },
        methods:{
          tabbarClick(n,o){
            var v =this
            v.list = v.ALL[n]
            this.teamList()
          },
          teamList(){
            var v = this
            this.$http.post(this.apis.TEAM,this.pageList).then((res)=>{

            }).catch((err)=>{

            })
          }
        }

    }
</script>

<style scoped>

</style>
