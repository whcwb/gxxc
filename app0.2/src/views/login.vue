<style lang="less">
  @import "./login";
</style>
<template>
      <div id="login" class="box_col" style="background-image: url('static/login/backImg.png')">
        <div class="login_Logo">
          <img src="static/login/LOGO.png" style="width: 1.1rem;" alt="">
        </div>
        <div class="login_tit">
          学车联盟
        </div>
        <div class="login_form">
          <el-form :model="loginForm" status-icon
                   :rules="FormRules"
                   label-position="top"
                   ref="loginForm" class="demo-ruleForm">
            <el-form-item label="" prop="username">
              <el-input type="text"
                        v-model="loginForm.username"
                        placeholder="帐号"
                        auto-complete="off"
              ></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input type="password"
                        v-model.number="loginForm.password"
                        placeholder="密码"
                        auto-complete="off"></el-input>
            </el-form-item>
            <div style="text-align: right;font-size: 0.15rem;color: #fff;">
              <span style="border-bottom: solid 1px #fff"
              @click="$router.push({path:'/forget'})">
               忘记密码
              </span>
            </div>
            <el-form-item>
              <el-button style="width: 100%;color:#409eff;font-weight: 700;margin-top: 0.2rem"
                         @click="submitForm('loginForm')">登录</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="box_col_auto" style="font-size: 0.23rem;color: #fff;text-align: center;">
          <div>
            <el-button  @click="reg" round>创建帐号</el-button>
          </div>
        </div>
      </div>
</template>

<script>
    import { Toast } from 'mint-ui';
    export default {
        name: "login",
        data(){
          var mm = (rule, value, callback) => {
            if (value === '') {
              return callback(new Error('请输入密码！'));
            }else {
              callback();
            }
          };
          var zh = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入帐号！'));
            }else if(value.length!=11){
              callback(new Error('你的输入帐号不正确！'));
            }else {
              callback();
            }
          };
          return{
            loginForm: {
              username: '',
              password: ''
            },
            FormRules: {
              username: [
                { validator: zh, trigger: 'blur' }
              ],
              password: [
                { validator: mm, trigger: 'blur' }
              ]
            }
          };
        },
        created(){
          this.util.auto(window, document ,4)
          // let openid = localStorage.getItem("openid");
          // console.log(openid)
          // if(!openid){
          //   console.log(openid)
          //   this.$router.push({name:'index'})
          // }
          let ISLOGIN = sessionStorage.getItem("ISLOGIN");
          if(ISLOGIN == null){
            let openid = localStorage.getItem("openid");
            this.wechatUtil.getAccessToken(openid);
            sessionStorage.setItem("ISLOGIN",true);
          }
        },
        methods:{
          submitForm(formName) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                var v = this
                this.$http.post(this.apis.LOGIN,this.loginForm).then((res)=>{
                  if(res.code==200){
                    localStorage.setItem('token',JSON.stringify(res.result.accessToken))
                    v.util.GetUserMess(v,(res)=>{
                      v.$router.push({path:'/Home'})
                    })
                  }else {
                    Toast(res.message)
                  }
                }).catch((err)=>{
                  console.log(err)
                  console.log('登录出错了！！！')
                })
              } else {
                console.log('error submit!!');
                return false;
              }
            });
          },
          reg(){
            var v = this
            alert('1')
            this.wechatUtil.qrScan((messtoback)=>{
              alert('2')
              // alert('微信'+messtoback)
              // Toast.succeed('微信'+messtoback);
              v.codeyz(messtoback)
              // v.codeyz(v.yqm)
            })
          },
          codeyz(val){
            var v = this
            alert(val)
            this.$http.post(this.apis.CODEYZ,{'code':val}).then((res)=>{
              if(res.code==200){
                localStorage.setItem('yqm',val)
                v.$router.push("/reg");
              }else {
                Toast(res.message)
              }
            }).catch((err)=>{
              alert('失败'+err)
            })
          },
        }
    }
</script>

<style scoped>

</style>
