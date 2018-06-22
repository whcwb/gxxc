<template>
  <div class="box_col" style="background-image: url('static/login/backImg.png');font-size: 0.3rem">
    文件配置中……
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


    }
  }
</script>
