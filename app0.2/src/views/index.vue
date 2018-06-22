<style lang="less">
  .el-loading-spinner{
    transform: translate(0,-50%);
  }
</style>
<template>
  <div class="box_col" style="background-image: url('static/login/backImg.png');font-size: 0.3rem">

  </div>
</template>

<script>

  export default {
    name: "index-page",
    data(){
        return{

        }
    },
    created(){
      this.openFullScreen2()
        let authCode = this.wechatUtil.getQueryString("code");
        if (!authCode){
            this.wechatUtil.getCode();
        }else{
            this.wechatUtil.getOpenid(authCode,(res)=>{
                localStorage.setItem("openid",res);
                sessionStorage.setItem("ISLOGIN",true);
                this.wechatUtil.initConfig();
            });
        }
    },
    methods:{
      openFullScreen2() {
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        // setTimeout(() => {
        //   loading.close();
        // }, 2000);
      }
    }
  }
</script>
