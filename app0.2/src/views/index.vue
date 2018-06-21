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
                sessionStorage.setItem("ISLOGIN",true);

                this.wechatUtil.getAccessToken();
                let usermess = localStorage.getItem('userMess')
                if(usermess){
                  this.$router.push({path:'/Home'})
                }else {
                  this.$router.push({path:'/'})
                }
            });
        }


    }
  }
</script>
