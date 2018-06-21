<style>
  .header_up{
    height: 60px;
    background-color: #26a2ff;
  }
  .header_down{
    height: 60px;
    text-align: center;
  }
  ._headerIcon{
    position: relative;
    height: 0.8rem;
    width: 0.8rem;
    top: -0.8rem;
  }
  .coach_name{
    text-align: center;
    font-size: 16px;
    padding: 16px;
  }
  ._separator{
    height: 30px;
    background-color: rgba(240,245,248,1);
  }
  ._title{
    font-size: 18px;
  }
</style>
<template>
  <div>
    <mt-header title="教练详情">
      <router-link to="/Home" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <div class="header_img">
      <div class="header_up"></div>
      <div class="header_down">
        <el-button disabled icon="iconfont icon-user" circle class="_headerIcon"></el-button>
      </div>
    </div>
    <div class="coach_name">
      <span>{{coach.yhXm}}</span>
      <i class="el-icon-circle-check-outline" style="color: #00a854"></i>
      <div>
        <el-rate
          v-model="coach.jlPf"
          disabled
          show-score
          text-color="#ff9900"
          score-template="{value}">
        </el-rate>
      </div>
      <div>
        <span style="color: rgba(165,165,165,1)">{{coach.qymc}}</span>
      </div>
    </div>
    <div class="_separator"></div>
    <div class="detail" style="padding: 16px;">
      <span class="_title">教练详情</span>
      <div class="inner_div" style="background-color: rgb(247,247,247);border-radius: 14px;padding: 16px;">
        <i style="font-size: 18px;color: rgba(253,150,41,1)">{{coach.yhXm}}</i>
        <div style="color:rgba(165,165,165,1);font-size: 14px;">
          <span>性别：</span><span>男</span><br>
          <span>教龄：</span><span>{{coach.jlJl}}</span><br>
          <span>电话：</span><span>{{coach.yhSjhm}}</span><br>
          <span>报价：</span><span>2500元/全部</span><br>
        </div>
      </div>
    </div>
    <div class="_separator"></div>
    <div style="text-align: center">
      <el-button type="success" round>报名咨询</el-button>
    </div>
  </div>
</template>

<script>
    export default {
        name: "coach",
      data(){
        return {
          coach:{},
        }
      },
      created(){
        console.log(this.$route.params.coach);
        this.coach = this.$route.params.coach;
      },
      mounted(){
      }
    }
</script>

<style scoped>

</style>
