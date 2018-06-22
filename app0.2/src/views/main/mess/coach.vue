<style lang="less">
  #codch{
    .header_up {
      height: 60px;
      background-color: #26a2ff;
    }

    .header_down {
      height: 60px;
      text-align: center;
    }

    ._headerIcon {
      position: relative;
      height: 0.8rem;
      width: 0.8rem;
      top: -0.8rem;
    }

    .coach_name {
      text-align: center;
      font-size: 16px;
      padding: 16px;
    }

    ._separator {
      height: 30px;
      background-color: rgba(240, 245, 248, 1);
    }

    ._title {
      font-size: 18px;
    }
    .NEWPF{
      background-color: #f7f7f7;
      border-radius: 0.1rem;
      padding: 0 0.1rem;
      .el-rate{
        height: 0.5rem!important;
        line-height: 0.5rem;
      }
    }
  }

</style>
<template>
  <div id="codch" class="box_col">
    <mt-header title="教练详情">
      <router-link to="/Home" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <div class="box_col_auto">
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
        <div class="_title" style="padding: 0 0 0.1rem 0">教练详情</div>
        <div class="inner_div" style="background-color: rgb(247,247,247);border-radius: 14px;padding: 16px;">
          <div style="font-size: 18px;color: rgba(253,150,41,1);padding:0 0 0.1rem 0">{{coach.yhXm}}</div>
          <div style="color:rgba(165,165,165,1);font-size: 14px;">
            <span>性别：</span><span>男</span><br>
            <span>教龄：</span><span>{{coach.jlJl}}</span><br>
            <span>电话：</span><span>{{coach.yhSjhm}}</span><br>
            <span>报价：</span><span>2500元/全部</span><br>
          </div>
        </div>
      </div>
      <div class="_separator"></div>
      <div style="padding: 0.1rem 16px">
        <div class="_title" style="padding: 0 0 0.1rem 0">学员评价</div>
        <div class="NEWPF box-row">
          <div class="box_col_100">
              <el-rate
                v-model="pf"
                :disabled="pf_disabled"
                show-score
                text-color="#ff9900"
                score-template="{value}">
              </el-rate>
          </div>
          <div style="font-size: 0.1rem;line-height: 0.5rem">
            <el-button type="success" size="mini"
                       @click="studentPF"
                       v-show="!pf_disabled"
                       icon="el-icon-check" circle></el-button>
          </div>
        </div>

      </div>
    </div>




    <div class="_separator"></div>
    <!--<div style="text-align: center">-->
      <el-button type="success" round style="width: 60%;margin:0.1rem auto">报名咨询</el-button>
    <!--</div>-->
  </div>
</template>

<script>
  import {Toast} from 'mint-ui';

  export default {
    name: "coach",
    data() {
      return {
        coach: {},
        pf:0,
        pf_disabled:false
      }
    },
    created() {
      this.util.auto(window, document , 4)
      if(this.$route.params.coach){
        console.log(this.$route.params)
        this.coach = this.$route.params.coach;
        this.GETstudentPF()
        if(this.coach.jlPf){

        }
        console.log(this.coach)
      }else {
        this.$router.push('/Home')
      }
    },
    mounted() {
    },
    methods: {
      GETstudentPF(){
        var v = this
        this.$http.post(this.apis.GET_PF).then((res) => {
          console.log('频分信息',res);
          if (res.code == 200) {
            if(res.result.id){
              v.pf=res.result.yhFz
              v.pf_disabled = true
            }
          } else {
            Toast(res.message);
          }
        })
      },
      studentPF() {
        var v = this
        this.$http.post(this.apis.PF_TEACHER,{yhFz:v.pf,yhPl:'文字评论'}).then((res) => {
          console.log(res);
          if (res.code == 200) {
            v.GETstudentPF()
            v.getCoachInfo()
          } else {
            Toast(res.message);
          }
        })
      },
      getCoachInfo() {
        this.$http.get(this.apis.getStudentCoach, {params: {yhId: this.$store.state.app.userMess.id}}).then((res) => {
          if (res.code == 200 && res.result) {
            this.coach = res.result;
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
