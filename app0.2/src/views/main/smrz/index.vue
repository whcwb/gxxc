<style lang="less">
  #smrz{
    font-size: 0.16rem;
    background-color: #f2f2f2;
    .box_col_auto{
      padding:0.2rem;

    }
  }
</style>
<template>
  <div id="smrz" class="box_col">
    <mt-header title="实名认证">
      <router-link to="/Home" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <div class="box_col_auto" v-if="userMess.yhZt=='-1'">
      <div class="box-row">
        <div class="box_row_100" style="margin: 0.15rem">
          <imgup :demoImg="imgList.zm" fileType="10"
                 @handleSuccess="(res)=>{handleSuccess(res,0,'10')}"></imgup>
        </div>
        <div class="box_row_100" style="margin: 0.15rem">
          <imgup :demoImg="imgList.bm" fileType="11"
                 @handleSuccess="(res)=>{handleSuccess(res,1,'11')}"></imgup>
        </div>
      </div>
      <div @click="" style="padding: 0.3rem 0.1rem">
        <el-button type="primary" @click="uploadMess"
                   style="width: 100%;padding: 0.15rem"
        >提交</el-button>
      </div>
    </div>
    <div class="box_col_auto" v-else style="text-align: center;background-color: #fff">
      <div v-if="userMess.yhZt=='0'">
        <img src="static/zjsh/zjsh0.png" alt="">
        <div>
            审核中
        </div>
      </div>
      <div v-else-if="userMess.yhZt=='1'">
        <img src="static/zjsh/zjsh1.png"
             alt="">
        <div>
           审核通过
        </div>
        <div class="box-row">
          <div class="box_row_100">
            <el-button round @click="$router.push({path:'/Home'})">返回</el-button>
          </div>
          <div class="box_row_100">
            <el-button type="success" round
                       @click="$router.push({path:'/pay/payIndex'})">缴费</el-button>
          </div>
        </div>
      </div>
      <div v-else-if="userMess.yhZt=='2'">
        <img src="static/zjsh/zjsh2.png" alt="">
        <div>
           审核驳回
        </div>
        <div>
          {{userMess.yhZtMs}}
        </div>
        <div style="padding: 0.3rem 0.1rem">
          <el-button type="danger" @click="gobak"
                     style="width: 100%;padding: 0.15rem"
          >重新提交</el-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
    import imgup from '@/views/components/upLoad/zjimgUpload'
    import {Toast} from 'mint-ui';
    export default {
        name: "smrz",
        components:{
          imgup
        },
        data(){
          return{
            userMess:this.$store.state.app.userMess,
            form:{
              imgList:['-','-','-','-']
            },
            imgList:{
              zm:'static/home/id_03.png',
              bm:'static/home/id_05.png'
            },
          }
        },
        created(){
          this.util.auto(window, document,4)
          this.rz()
        },
        methods:{
          gobak(){
            this.userMess.yhZt='-1'
          },
          rz(){
            var v =this
            this.util.GetUserMess(v,(res)=>{
              v.userMess = res
              // 0审核中  1通过  2驳回（yhZtMs-驳回信息） -1未认证
              if(v.userMess.yhZt=='-1'){
                v.stepIndex = 0
              }else if(v.userMess.yhZt!='-1'){
                v.stepIndex = 2
              }
            })
          },
          handleSuccess(res,val,type){
            console.log('上传成功事件监听',res)
            console.log(val)
            var v = this
            if(res.code==200){
              v.zjsbImg(res.message,val,type)
            }
          },
          //证件识别
          zjsbImg(url,val,code){
            console.log(code)
            var v =this
            this.$http.post(this.apis.ZJSB,{path:url,fileType:code}).then((res)=>{
              if(res.code==200){
                if(val==0){
                  v.imgList.zm = 'static/zjsh/sfzzmok.png'
                  v.form.imgList[val]=url
                }else if(val==1){
                  v.imgList.bm = 'static/zjsh/sfzfmok.png'
                  v.form.imgList[val]=url
                }
              }else {
                Toast(res.message)

              }
              console.log(res)
            }).catch((err)=>{

            })
          },



          uploadMess(){
            var v = this
            this.$http.post(this.apis.IDRZ,{'imgList':v.form.imgList.join(',')}).then((res)=>{
              console.log(res)
              if(res.code==200){
                v.rz()
              }else {
                Toast(res.message)
              }
            }).catch((err)=>{

            })
          }

        }
    }
</script>

<style scoped>

</style>
