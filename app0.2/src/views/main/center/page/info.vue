<style lang="less">
  #myCenterInfo{
    position: relative;
    font-size: 0.22rem;
    .saveName,.updatePassword{
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      background-color: #fff;
      z-index: 100;
      .BMinput{
        width: 100%;
        height: 0.5rem;
        outline:none;
        border: none;
        border-bottom: #2db7f5 0.06rem solid;
        color: #2db7f5;
        font-size: 0.2rem;
        font-weight: 600;
      }
    }
  }
</style>
<template>
    <div id="myCenterInfo" class="box_col">
      <div>
        <mt-header title="个人信息">
          <router-link to="/Home" slot="left">
            <mt-button icon="back"></mt-button>
          </router-link>
        </mt-header>
      </div>
      <div class="box_col_auto">
          <!-- 用户设置 -->
          <div style="padding-top: 30px;color: black;">
              <mt-cell title="头像" style="border-bottom: 1px #e9eaec solid;padding-bottom: 10px">
                <div style="width: 0.6rem;height: 0.6rem;border-radius: 0.4rem;padding-top: 0.1rem">
                  <imgup :demoImg="userMess.yhTx"
                         @handleSuccess="handleSuccess">
                  </imgup>
                </div>
              </mt-cell>
              <div @click="compname='bm'">
                <mt-cell title="昵称" :value="userMess.yhBm"
                         style="border-bottom: 1px #e9eaec solid;"></mt-cell>
              </div>
              <div @click="">
                <mt-cell title="驾驶证" :value="userMess.yhBm"
                         style="border-bottom: 1px #e9eaec solid;">
                  {{userMess.yhSfyjz | yhSfyjz}}
                  <!--<el-switch-->
                    <!--v-model="switchVal"-->
                    <!--active-color="#13ce66"-->
                    <!--active-value="开"-->
                    <!--inactive-color="#ff4949"-->
                    <!--inactive-value="关">-->
                  <!--</el-switch>-->
                </mt-cell>
              </div>
              <div @click="compname='word'">
                <mt-cell title="修改密码" value="****"></mt-cell>
              </div>
              <div @click="goOut" style="padding: 0.3rem 0.1rem">
                <el-button type="danger"
                           style="width: 100%;padding: 0.15rem"
                >退出登录</el-button>
              </div>
              <div style="text-align: center;padding: 1rem 0 0 0;
              font-size: 0.14rem;color: #949494">
                <div>
                    Copyright@2017-2018
                </div>
                <div>
                  520学车联盟
                </div>
                <div>
                  武汉天弘腾创科技有限公司
                </div>
              </div>
          </div>
      </div>
      <component :is="compname"></component>
      </div>
</template>

<script>
    import bm from '../comp/bm'
    import word from  '../comp/upWorld'
    import imgup from '@/views/components/upLoad/imgUpload'
    export default {
        name: "myCenter",
        components: {
          bm,
          word,
          imgup,
        },
        filters:{
          yhSfyjz(val){
            switch (val) {
              case '0':
                return '无驾驶证';
                break;
              case '1':
                return '有驾驶证';
                break;
              default:
                return "无驾驶证";
                break;
            }
          }
        },
        data(){
          return{
            compname:'',
            userMess:this.$store.state.app.userMess,
            switchVal:''
          }
        },
        created(){
          var v = this
          if(v.userMess===''){
            this.util.GetUserMess(v, (res) => {
              this.userMess = res
            })
          }
        },
        methods:{
          goOut(){
            this.$http.get(this.apis.LOGOUT).then((res)=>{
              if(res.code==200){
                localStorage.clear()
              }

            }).catch((err)=>{

            })
            this.$router.push({path:'/Login'})
          },
          userMessF(){//获取个人信息
            var v = this
            this.$http.post(this.apis.USERMESS).then((res)=>{
              if(res.code==200){
                if(res.result.yhTx == ''){
                  res.result.yhTx ='static/images/userTx.png'
                }
                localStorage.setItem('userMess',JSON.stringify(res.result))
                v.compname = ''
                v.userMess = JSON.parse(localStorage.getItem("userMess"))
              }
            }).catch((err)=>{
              console.log('出错了！！！')
            })
          },
          UPTx(url){
            var v = this
            this.$http.post(this.apis.CHUSERMESS,{'yhTx':url}).then((res)=>{
              if(res.code==200){
                // v.userMessF()
                v.util.GetUserMess(v,(mess)=>{
                  v.userMess = mess
                })
              }
            }).catch((err)=>{

            })
          },
          handleSuccess(res){
            console.log('上传成功事件监听',res)
            this.UPTx('/'+res.message)
          },
        }
    }
</script>
