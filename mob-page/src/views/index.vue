<template>
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

                this.wechatUtil.getAccessToken();
            });
        }

        this.$store.commit('M_SYS',false)
    }
  }
</script>
