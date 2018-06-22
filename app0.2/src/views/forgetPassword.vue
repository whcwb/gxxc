<style lang="less">
  #reg {
    font-size: 0.2rem;
    background:no-repeat top;
    background-size: 100%;
    .mar_bot{
      margin-bottom: 0.16rem;
    }
  }
</style>
<template>
  <div id="reg" class="box_col" style="background-image: url('static/login/backImg.png')">
    <div style="padding: 0.1rem;color: #f2f2f2;font-weight: 700"
         @click="$router.back()">
      登录
    </div>
    <!--<div style="height: 2.2rem">-->
    <!--</div>-->
    <!--<div style="color: #fff;font-size: 0.3rem;padding-left:20% ">-->
      <!--欢迎加入我们-->
    <!--</div>-->
    <div class="box_col_100" style="padding: 0.3rem">
      <div class="mar_bot">
        <el-input v-model="form.yhZh"
                  clearable
                  placeholder="请输入手机号"></el-input>
      </div>

      <div class="mar_bot box-row">
        <div class="box_row_100">
          <el-input v-model="code"
                    clearable
                    placeholder="请输入验证码"
                    @change="changeCode"
          ></el-input>
        </div>
        <div style="margin-left: 0.15rem">
          <el-button
            v-show="!soltTimeShow"
            type="primary" @click="getPhoneCode">获取验证码</el-button>
          <el-button
            v-show="soltTimeShow"
            style="background-color: #f2b121;width: 1.12rem;margin-left: 0"
            type="primary">{{soltTime}}s</el-button>
        </div>
      </div>

      <div class="mar_bot">
        <el-input v-model="form.yhMm"
                  type="password"
                  placeholder="请设置帐号密码"></el-input>
      </div>
      <div style="height: 0.5rem">
        <!--注册协议-->
      </div>
      <div style="text-align: center">
        <el-button
          @click="regYZ"
          style="background: linear-gradient(to top, #f3b222 , #fbdb48);width: 70%;"
          type="warning">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import { Toast } from 'mint-ui';
  export default {
    name: "reg",
    components: {},
    data(){
      return{
        code:'',
        soltTimeShow:false,
        soltTime:10,
        form:{
          yhZh:'',
          yhMm:'',
        },
      }
    },
    watch:{
    },
    created(){
    },
    methods:{
      changeCode(value){
        console.log(value)
      },
      //获取短信验证码
      getPhoneCode(){
        var v = this
        this.$http.post(this.apis.GET_FORGET_code,{'zh':v.form.yhZh}).then((res)=>{
          if(res.code==200){
            v.countDown()
          }else {
            Toast(res.message);
          }
        }).catch((err)=>{
          console.log('出错了!')
        })
      },
      countDown(){
        var v = this
        v.soltTimeShow = !v.soltTimeShow
        v.interval()
      },
      //计时器
      interval(){
        var v = this
        var interval = setInterval(function () {
          if(v.soltTime==0){
            clearInterval(interval)
            v.soltTimeShow = !v.soltTimeShow
            v.soltTime=10
          }else {
            v.soltTime -= 1
          }
        },1000)
      },
      regYZ(){
        var v = this
        if(v.form.yhZh==''||v.form.yhZh.length!==11){
          Toast('请输入手机号码!')
          return
        }else if(v.code==''){
          Toast('请输入短信验证码!')
          return
        }else if(v.form.yhMm==''){
          Toast('请设置帐号密码!')
          return
        }else {
          v.reg()
        }
      },
      reg(){
        //重置密码
        var v = this
        this.$http.post(this.apis.GET_FORGET_RESETPWD,{
          tel:v.form.yhZh,
          newPwd:v.form.yhMm,
          code:this.code
        }).then((res)=>{
          console.log(res);
          // Toast.info('注册成功');
          if(res.code==200){
            Toast({
              message: '操作成功',
              iconClass: 'icon icon-success'
            });
            setTimeout(()=>{
              v.$router.push({path:'/Login'})
            }, 1000);
          }else {
            console.log(res.message)
          }
        }).catch((err)=> {
          console.log('报错了');
        });
      },
    }
  }
</script>

<style scoped>

</style>
