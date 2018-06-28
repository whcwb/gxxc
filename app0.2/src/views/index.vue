<style lang="less">
    .el-loading-spinner{
      transform: translate(0,-70%);
    }
    .el-loading-spinner .el-loading-text{
      font-size: 0.22rem;
    }
  #configLoading{
    font-size: 0.2rem;
    color: #f2f2f2;
    font-weight: 700;
    text-align: center;
  }
</style>
<template>
  <div id="configLoading" class="box_col" style="background-image: url('static/login/backImg.png');font-size: 0.3rem">
    <div class="box_col" v-show="!loadShow">
      <div style="padding: 0.6rem 0">
        <img src="static/login/LOGO.png"
             style="width: 25%"
             alt="">
      </div>
      <div style="line-height: 0.8rem">
        程序加载失败,请稍候再试！
      </div>
      <div class="box_col_100">
        <el-button type="primary" circle @click="loca"
                    style="border: #f2f2f2 solid 0.05rem;padding:0.15rem 0.3rem;
                    margin-top: 1rem;
                    margin:auto;font-size: 0.2rem"> 重新加载</el-button>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    name: "index-page",
    data(){
        return{
          loadShow:true,
          timer:-1,
          loading : this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
        }
    },
    created(){
      var v = this
      v.loading

      let authCode = this.wechatUtil.getQueryString("code");
      if (!authCode){
          this.wechatUtil.getCode();

      }else{
          this.wechatUtil.vueParent = this;
          this.wechatUtil.getOpenid(authCode,(res)=>{
              localStorage.setItem("openid",res);
              sessionStorage.setItem("ISLOGIN",true);
              this.wechatUtil.initConfig();

              v.loadColse(false)
          });
      }

      this.timer =  setTimeout(() => {
        v.loading.close();
        v.loadShow=false;
      }, 1000*1.5);
    },
    mounted(){
      let openid = localStorage.getItem("openid");
      let ISLOGIN = sessionStorage.getItem("ISLOGIN");
      let userToken = localStorage.getItem("userMess");
      if (openid && ISLOGIN && userToken) {
        this.$router.push({path:'/Home'})
      }
    },
    methods:{
      loca(){
        window.location.href = "/wx/";
      },
      openFullScreen2() {
        var v = this
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        this.timer =  setTimeout(() => {
          loading.close();
          v.loadShow=false;
        }, 1000*2.5);
      },
      loadColse(typ){
        if(typ){
          this.loadShow=false;
        }
        clearTimeout(this.timer);
        this.loading.close();
      }
    }
  }
</script>
