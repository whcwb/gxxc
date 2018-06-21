<template>
  <div class="updatePassword box" >
    <div class="box-row"
         style="height: 0.5rem;
             border-bottom: #dedede 1px solid;
             color: #757575;
             line-height: 0.55rem;
             background-color: #f1f1f1">
      <div style="width: 0.6rem;text-align: center"
           @click="$parent.compname=''">
        <i class="iconfont icon-left1"></i>
      </div>
      <div style="font-size: 0.18rem;font-weight: 700;">
        密码修改
      </div>
    </div>
    <div class="" style="padding: 0.2rem;">
      <div style="font-size: 0.18rem;margin-top: 0.2rem">
        原始密码：
      </div>
      <div style="margin-top: 0.1rem">
        <input type="password"
               v-model="upworldMes.oldPwd"
               class="BMinput" placeholder="请输入原始新密码">
      </div>
      <div style="font-size: 0.18rem;margin-top: 0.2rem">
        新密码：
      </div>
      <div style="margin-top: 0.1rem">
        <input type="password"
               v-model="upworldMes.newPwd"
               class="BMinput" placeholder="请输入新密码">
      </div>
      <div style="margin-top: 0.2rem">
        <el-button type="primary" @click="UPwoeld"
                   style="width: 100%;padding: 0.15rem"
        >确定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  // import {Button} from 'iview'
  import {Toast} from 'mint-ui';
    export default {
        name: "upWorld",
        components:{
        },
        data(){
          return{
            upworldMes:{
              oldPwd:'',
              newPwd:'',
            },
          }
        },
        methods:{
          UPwoeld(){
            var v = this
            this.$http.post(this.apis.UPWORLD,v.upworldMes).then((res)=>{
              if(res.code==200){
                v.$parent.compname = ''
                v.$parent.goOut()
              }else {
                Toast(res.message);
              }
            }).catch((err)=>{

            })
          },
        }
    }
</script>

<style scoped>

</style>
