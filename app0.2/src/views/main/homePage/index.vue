<style lang="less">
  #homePage{
    font-size: 0.2rem;
    background-color:#f2f2f2;
    .select{
      /*width: 100%;*/
      /*height: 100%;*/
      background-color: #fff;
      text-align: center;
      font-size: 0.14rem;
      border: none;
    }
    .mint-tab-item-label{
      font-size: 0.16rem!important;
      color: #3ca0e6;
    }
    /*mandMobile_________star*/
    .md-popup.with-mask{
      z-index: 9999;
    }
    .md-drop-menu{
      position: relative!important;
      z-index: 100!important;
      font-size: 0.16rem;
      height: 0.18rem;
      color: #000;
      background:#ffffff00!important;
      border-bottom: none!important;
    }
    .md-drop-menu .md-drop-menu-list{
      padding-top: 0!important;
      font-size: 0.16rem;
    }
    .md-radio .md-field .md-field-item{
      padding: 0.15rem 0.3rem;
    }
    .md-radio .md-field .md-field-item .md-field-item-content.left{
      font-size: 0.16rem;
    }
    .md-drop-menu .md-drop-menu-bar{
      /*background-color: red;*/
      background:#ffffff00!important;
      border-bottom: none!important;
    }
    .md-drop-menu .md-drop-menu-bar::before{
      background:#ffffff00!important;
    }
    /*.md-drop-menu .md-drop-menu-bar .bar-item:last-of-type{*/
      /*background-color: red;*/
    /*}*/
    /*mandMobile_________end*/

    /*功能模块*/
    .gnmk{
      background-color: #fff;
      height: 0.6rem;
      text-align: center;
      .box_row_100{
        position: relative;
        .iconfont{
          position: absolute;
          top: 0.08rem;
          left: 50%;
          transform: translate(-50%,0);
          font-size: 0.25rem;
          color: #0c98f9;
        }
        .text{
          font-size: 0.12rem;
          position: absolute;
          bottom: 0.05rem;
          left: 50%;
          transform: translate(-50%,0);
        }
      }
    }

    /*模块样式*/
    .moSty{
      padding: 0.15rem;
      background-color: #fff;
      margin-top: 0.2rem;
    }
    /*考试秘籍*/
    .ksmj{
    }
    /*优惠*/
    .ksyh{

    }
    /*邀请好友*/
    .yqhy{
      .yqhy_mess{
        background-color: #0a7dda;
        border-radius: 0.1rem;
        margin-top: 0.12rem;
        padding: 0.15rem;
        .num{
          color: #fff;
        }
        .txt{
          color: #4aa3f7;
          font-size: 0.15rem;
          margin-top: 0.05rem;
        }
      }
    }
    /*训练厂List*/
    .xlcList{
      padding: 0 0.15rem;
      .list{
        border-bottom: solid 1px #999999;
        padding-bottom: 0.1rem;
        margin-bottom: 0.05rem;
        .iconfont{
          color:#029ded ;
        }
        .xlcMess{
          font-size: 0.15rem;
          .name{
          }
          .money{
            color: #029ded;
          }
        }
        .xlcmap{
          padding: 0.05rem 0;
          .icon{
            margin-right: 0.1rem;
          }
          .mess{
            font-size: 0.14rem;
          }

        }
        .xlcBus{
          .icon{
            margin-right: 0.1rem;
          }
          .mess{
            font-size: 0.16rem;
          }
        }
      }
    }
  }
</style>
<template>
      <div id="homePage" class="box_col">
        <div class="box_col_auto" style="margin-bottom: 0.1rem">
          <div style="height: 1.5rem;text-align: center">
            <mt-swipe :auto="4000">
              <mt-swipe-item v-for="(item,index) in SwiperImg">
                <img :src="item.hdTpdz"
                     style="width: 100%" alt="">
              </mt-swipe-item>
            </mt-swipe>
          </div>
          <!--功能模块-->
          <div class="box-row gnmk" v-show="false">
            <div class="box_row_100">
              <i class="iconfont icon-fenlei"></i>
              <div class="text">
                训练场地
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-liuchengxiangdao"></i>
              <div class="text">
                学车流程
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-shuben"></i>
              <div class="text">
                学车指引
              </div>
            </div>
            <div class="box_row_100">
              <i class="iconfont icon-ditu"></i>
              <div class="text">
                考场分布
              </div>
            </div>
          </div>
          <!--考试秘籍-->
          <div class="ksmj moSty" v-show="false">
            <div>
              Hi,周周
            </div>
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 margin-top: 0.15rem;font-size: 0.16rem">
                <div class="box_row_100">
                  老夫送你一个考试秘籍，快来来看看
                </div>
                <div style="color: #7f7f7f">
                  更多
                </div>
            </div>
            <div style="margin-top: 0.15rem;
            background: linear-gradient(to bottom right, #fff , #dbecfe)">
              <img src="static/home/00_03.png" alt="" style="width: 100%"
                @click="goLook">
            </div>
          </div>
          <!--优惠-->
          <div class="ksyh moSty" v-show="false">
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 margin-top: 0.15rem;font-size: 0.16rem">
              <div class="box_row_100">
                老夫送你一个优惠，你要不要
              </div>
              <!--<div style="color: #7f7f7f">-->
                <!--更多-->
              <!--</div>-->
            </div>
            <div class="box-row">
              <div class="box_row_100" style="margin: 0.15rem">
                <img src="static/home/66_03.png"
                     style="width: 100%"
                     alt="">
              </div>
              <div class="box_row_100" style="margin: 0.15rem">
                <img src="static/home/66_05.png"
                     style="width: 100%"
                     alt="">
              </div>
            </div>
          </div>
          <!--邀请好友-->
          <div class="yqhy moSty" style="">
            <div class="box-row"
                 style="border-left: #62adfe 0.04rem solid;padding-left: 0.1rem;
                 font-size: 0.16rem">
              <div class="box_row_100">
                听说邀请好友还有现金，速速围观
              </div>
            </div>
            <div class="yqhy_mess box-row">
              <div class="box_row_100">
                <div class="num">
                  {{zhYE.yhZhye/100 | yhZhye}}元
                </div>
                <div class="txt">
                  累计奖励
                </div>
              </div>
              <div class="box_row_100">
                <div class="num">
                  0人
                </div>
                <div class="txt">
                  已邀请
                </div>
              </div>
              <div class=""
                   @click="showQrcode('ewm')"
                   style="padding: 0.08rem 0.05rem 0 0 ">
                <i class="iconfont icon-erweima"
                   style="font-size: 0.3rem;color: #f2f2f2"
                ></i>
              </div>
            </div>
          </div>


          <div style="background-color: #fff">
            <div class="moSty" style="padding: 0;margin: 0 0.15rem">
              <mt-navbar v-model="selected">
                <mt-tab-item id="1">推荐</mt-tab-item>
                <mt-tab-item id="2">
                  <!--<select class="select" name="" id="">-->
                    <!--<option value="" selected>全部</option>-->
                    <!--<option v-for="(item,index) in qulist"-->
                      <!--:value="index"-->
                    <!--&gt;{{item.name}}</option>-->
                  <!--</select>-->

                  <md-drop-menu
                    ref="dropMenu0"
                    v-model="dropTxt"
                    :default-value="['武汉市']"
                    :data="data"
                    @change="getSelectedValue"
                  ></md-drop-menu>



                </mt-tab-item>
                <mt-tab-item id="3">价格</mt-tab-item>
              </mt-navbar>
            </div>
            <div class="xlcList" style="margin-top: 0.1rem">
                <div class="list box-row"
                     v-for="(item,index) in xlcList"
                      @click="$router.push({name:'xlc',params:item})">
                    <div style="width: 1.2rem">
                      <img src="static/home/car0000.png"
                           style="width: 100%"
                           alt="">
                    </div>
                    <div class="box_row_100" style="padding-left: 0.1rem">
                        <div class="box-row xlcMess">
                           <div class="box_row_100 name">
                            <!--***驾校训练厂-->
                             {{item.schoolName}}
                           </div>
                           <div class="money">
                            ￥2500.00
                           </div>
                        </div>
                        <div class="box-row xlcmap">
                          <div class="icon">
                            <i class="iconfont icon-dingwei"></i>
                          </div>
                          <div class="box_row_100 mess">
                            {{item.address}}
                          </div>
                        </div>
                        <div class="box-row xlcBus">
                          <div class="icon">
                            <i class="iconfont icon-bus"></i>
                          </div>
                          <div class="box_row_100 mess">
                            566、567、568
                          </div>
                        </div>
                        <div style="font-size: 0.12rem;text-align: right;color: #949494">
                          >3000米
                        </div>
                    </div>
                </div>
            </div>
          </div>
        </div>
      </div>
</template>

<script>
    import {Toast} from 'mint-ui'
    import { DropMenu } from 'mand-mobile'

    export default {
      name: "index",
      components:{
        [DropMenu.name]:DropMenu
      },
      filters: {
        yhZhye(val) {
          if (val == '') {
            return 0
          }
          return val
        },
      },
      watch:{
        dropTxt:function (n,o) {
          console.log(n)
          console.log(o)
          this.xlcList.forEach((item ,index)=>{
            item.address = n + '训练场'
          })
        },
        selected:function (n,o) {
          console.log(n)
          console.log(o)
        }
      },
      data(){
          return{
            selected:'1',
            SwiperImg:[],
            xlcList:[],
            usermess: this.$store.state.app.userMess,
            zhYE: {
              yhZhye: 0
            },
            dropTxt:'',
            data: [
              {
                text: '一级选项1',
                options: [
                  {text:'武汉市'},
                  {text:'江岸区'},
                  {text:'江汉区'},
                  {text:'硚口区'},
                  {text:'汉阳区'},
                  {text:'武昌区'},
                  {text:'青山区'},
                  {text:'洪山区'},
                  {text:'东西湖区'},
                  {text:'汉南区'},
                  {text:'蔡甸区'},
                  {text:'江夏区'},
                  {text:'黄陂区'},
                  {text:'新洲区'},
                ],
              },
            ],

            qulist:[
              {text:'江岸区'},
              {text:'江汉区'},
              {text:'硚口区'},
              {text:'汉阳区'},
              {text:'武昌区'},
              {text:'青山区'},
              {text:'洪山区'},
              {text:'东西湖区'},
              {text:'汉南区'},
              {text:'蔡甸区'},
              {text:'江夏区'},
              {text:'黄陂区'},
              {text:'新洲区'},
            ]
          }
      },
      created(){
        var v = this
        this.getSwiperImg()
        this.XLC()
        this.util.GetUserMess(v, (res) => {
          v.usermess = res
        })
        this.getYE()
      },
      methods:{
        getSelectedValue(val,item){
          console.log(item);
          this.dropTxt = item.text
        },
        goLook(){
          Toast('去看看');
        },
        showQrcode(val) {
          var v = this
          if (v.usermess.ddSfjx == '0') {
            Toast('请先缴费')
          } else {
            this.$router.push({name: 'myCenterQrcode'});
          }
        },
        getYE(){
          var v = this
          this.$http.post(this.apis.USERZH).then((res)=>{
            if(res.code){
              v.zhYE.yhZhye = res.result.yhZhye
            }else{
              Toast(res.message)
            }
          }).catch((err)=>{
            Toast('账户余额获取失败')
          })
        },
        getSwiperImg(){
          this.$http.post(this.apis.SWIPER,{hdSxs:0}).then((res)=>{
            if(res.code==200){
              this.SwiperImg = res.page.list
            }
            console.log(res)
          }).catch((err)=>{

          })
        },
        XLC(){
          var v = this
          this.$http.post(this.apis.XLC).then((res)=>{
            if(res.code==200){
              v.xlcList = res.page.list
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
