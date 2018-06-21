<style lang="less">
  @import "./login";
</style>
<template>
      <div id="login" class="box_col">
        <div class="login_Logo">
          <img src="/static/login/LOGO.png" style="width: 1.1rem;" alt="">
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
              <span style="border-bottom: solid 1px #fff">
               忘记密码
              </span>
            </div>
            <el-form-item>
              <el-button style="width: 100%;color:#409eff;font-weight: 700;margin-top: 0.2rem"
                         @click="submitForm('loginForm')">提交</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="box_col_auto" style="font-size: 0.23rem;color: #fff;text-align: center;">
          <div @click="reg">
            创建帐号
          </div>
        </div>
      </div>
</template>

<script>
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
              username: '13311111111',
              password: '123456'
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
                      v.$router.push({name:'Home'})
                    })
                  }else {
                    Toast.failed(res.message)
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
            v.$router.push("/reg")
            // this.wechatUtil.qrScan((messtoback)=>{
              // alert('微信'+messtoback)
              // Toast.succeed('微信'+messtoback);
              // v.codeyz(messtoback)
              // v.codeyz(v.yqm)
            // })
          },
          codeyz(val){
            var v = this
            // alert(val)
            this.$http.post(this.apis.CODEYZ,{'code':val}).then((res)=>{
              if(res.code==200){
                localStorage.setItem('yqm',val)
                v.$router.push("/reg");
              }else {
                Toast.info(res.message)
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
