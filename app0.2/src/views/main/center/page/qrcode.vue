<style lang="less">
  #myCenterQrcode{
    background-color: #fbf9f9;
    font-size: 0.2rem;
  }
</style>
<template>
    <div id="myCenterQrcode" class="box">
      <div>
        <mt-header title="我的邀请码">
          <router-link to="/Home" slot="left">
            <mt-button icon="back"></mt-button>
          </router-link>
        </mt-header>
      </div>
      <div class="body">
          <!-- 用户设置 -->
          <div style="padding-top: 20px;">
              <div v-if="ewm" style="padding: 0.1rem">
                  <div class="box-row" style="padding: 0 0 0 0.1rem">
                    <div>
                        <img :src="usermess.yhTx"
                             style="width: 0.6rem;height: 0.6rem;border-radius: 0.5rem"
                             alt="">
                    </div>
                    <div class="box_row_100" style="margin-left: 0.2rem;line-height: 0.6rem">
                        <span style="font-size:22px"> {{usermess.yhXm}}</span>
                    </div>
                  </div>
                  <div style="padding: 0.2rem 0.4rem">
                      <img :src="ewm" style="width: 100%" alt="">
                  </div>
                  <div style="text-align: center;">扫一扫上面的二维码图案，加入我们吧</div>
              </div>
              <div v-else
              style="text-align: center;font-size: 0.3rem;font-weight: 700;line-height: 1.5rem">
                邀请码生成中……
                请稍候
              </div>
          </div>
      </div>
    </div>
</template>

<script>

    export default {
        name: "myCenter",
        data(){
          return{
            usermess: this.$store.state.app.userMess,
            ewm:this.$store.state.app.userMess.yhZsyqmImg,
          }
        },
        created(){
          var v = this
          if(v.usermess===''){
            this.util.GetUserMess(v, (res) => {
              this.usermess = res
              this.ewm = res.yhZsyqmImg
            })
          }
        },
        methods:{
            goback(){
              this.$router.go(-1);
            }
        }
    }
</script>

<style scoped>

</style>
