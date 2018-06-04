<style lang="less">
  #student{
    font-size: 0.24rem;
    .mint-navbar{
      .mint-tab-item{
        .mint-tab-item-label{
          font-size: 0.24rem;
        }
      }
    }

  }
</style>
<template>
  <div id="student" class="box">
    <box-head tit="我的学员">
      <div slot="left" style="color: #E0DADF">
        <i class="iconfont icon-left1"></i>
      </div>
    </box-head>
    <!--<div>-->
      <mt-navbar v-model="selected" style="font-size: 0.3rem">
        <mt-tab-item id="1">科目一</mt-tab-item>
        <mt-tab-item id="2">科目二</mt-tab-item>
        <mt-tab-item id="4">科目三</mt-tab-item>
        <mt-tab-item id="5">科目四</mt-tab-item>
        <mt-tab-item id="0">已结束</mt-tab-item>
      </mt-navbar>
    <!--</div>-->
    <div class="body" style="margin-top: 0.12rem">
        <div class="list box-row" style="height: 1.5rem;border-bottom:solid 1px #dedede">
              <div style="font-size: 0.8rem;font-weight: 700;padding: 0.2rem 0.4rem;">
                    学
              </div>
              <div class="body-O" style="font-size: 0.3rem">
                <!--<div class="time" style="padding-top: 0.2rem">-->
                  <!--报名时间：2018-01-01-->
                <!--</div>-->
                <div class="box-row" style="padding: 0.15rem 0.2rem 0 0">
                  <div class="" style="padding: 0 0.2rem 0 0">
                    小明
                  </div>
                  <div class="body-O">
                    13112345678
                  </div>
                  <div class="" >
                    08/05/06
                  </div>
                </div>
                <div class="box-row" style="font-size: 0.24rem;padding-top: 0.14rem;">
                  <div class="body-O">
                    <div>
                     科目一
                    </div>
                    <div>
                      90
                    </div>
                  </div>
                  <div class="body-O">
                    <div>
                      科目二
                    </div>
                    <div>
                      *****
                    </div>
                  </div>
                  <div class="body-O">
                    <div>
                     科目三
                    </div>
                    <div>
                      ****
                    </div>
                  </div>
                  <div class="body-O">
                    <div>
                      科目四
                    </div>
                    <div>
                      ****
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
    export default {
        name: "student",
        components:{
          [Navbar.name]:Navbar,
          [TabItem.name]:TabItem
        },
        data(){
          return{
            selected:'1'
          }
        }
    }
</script>

<style scoped>

</style>
