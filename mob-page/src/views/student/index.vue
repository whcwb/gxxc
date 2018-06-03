<template>
  <div id="student" class="box">
    <box-head tit="我的学员">
      <div slot="left" style="color: #E0DADF">
        <i class="iconfont icon-left1"></i>
      </div>
    </box-head>
    <!--<div>-->
      <mt-navbar v-model="selected">
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
                <div>
                  报名时间：*****************
                </div>
                <div class="box-row">
                  <div class="body-O">
1
                  </div>
                  <div class="body-O">
2
                  </div>
                  <div class="body-O">
3
                  </div>
                </div>
              </div>
              <div>
                >
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
