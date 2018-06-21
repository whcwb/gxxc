<style lang="less">
  #reg {
    font-size: 0.2rem;
    background: no-repeat top;
    background-size: 100%;
    .mar_bot{
      margin-bottom: 0.16rem;
    }
  }
</style>
<template>
  <div id="reg" class="box_col" style="background-image:url('static/reg/regBack.png') ">
    <div style="padding: 0.1rem;color: #f2f2f2;font-weight: 700"
          @click="$router.back()">
      登录
    </div>
    <div style="height: 2.2rem">
    </div>
    <div style="color: #fff;font-size: 0.3rem;padding-left:20% ">
      欢迎加入我们
    </div>
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
            style="background-color: #f2b121"
            type="warning" @click="getPhoneCode">获取验证码</el-button>
          <el-button
            v-show="soltTimeShow"
            style="background-color: #f2b121;width: 1.12rem;margin-left: 0"
            type="warning">{{soltTime}}s</el-button>
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
          type="warning">立即注冊</el-button>
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
          yhYyyqm:localStorage.getItem('yqm'),
          yhLx:'1',
          addType:'3',
        },
      }
    },
    watch:{
    },
    created(){
      localStorage.setItem('yqm',123456789)
    },
    methods:{
      changeCode(value){
        console.log(value)
      },
      //获取短信验证码
      getPhoneCode(){
        var v = this
        this.$http.post(this.apis.PHINECODE,{'zh':v.form.yhZh,'yyyqm':localStorage.getItem('yqm')}).then((res)=>{
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
          v.vaildCodeNext()
        }
      },
      vaildCodeNext(){//验证短信是否有效
        //请求接口判断验证码是否正确
        var v = this
        this.$http.post(this.apis.YZDX,{zh:v.form.yhZh,yyyqm:v.code,type:1}).then((res)=>{
          if(res.code==200){
            console.log(res);
            v.reg()
          }else {
            Toast(res.message)
          }
        }).catch((err)=> {
          console.log('报错了');
        });

      },
      reg(){
        //请求接口进行注册操作
        var v = this
        this.$http.post(this.apis.USERSAVE,{
          yhZh:v.form.yhZh,
          yhMm:v.form.yhMm,
          yhYyyqm:localStorage.getItem('yqm'),
          yhLx:'1',
          addType:'3',
          telIdentifying:this.code
        }).then((res)=>{
          console.log(res);
          // Toast.info('注册成功');
          if(res.code==200){
            Toast({
              message: '操作成功',
              iconClass: 'icon icon-success'
            });
            setTimeout(()=>{
              // this.util.userMess(v,()=>{
              //   this.$router.push({name:'Home'});
              // })
              v.login()
            }, 1000);
          }else {
            console.log(res.message)
          }
        }).catch((err)=> {
          console.log('报错了');
        });
      },
      login(){
        var v = this
        this.$http.post(this.apis.LOGIN,{'username':this.form.yhZh,'password':this.form.yhMm}).then((res)=>{
          if(res.code==200){
            localStorage.setItem('token',JSON.stringify(res.result.accessToken))
            this.util.GetUserMess(v,()=>{
              v.$router.push({name:'Home'})
            })
          }else {
            Toast.failed(res.message)
          }
        }).catch((err)=>{
          console.log(err)
          console.log('登录出错了！！！')
        })
      },
    }

  }
</script>

<style scoped>

</style>
