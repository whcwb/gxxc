<template>
  <div class="updatePassword box" >
    <div class="box-row"
         style="height: 0.8rem;line-height: 0.8rem;
             border-bottom: #dedede 1px solid;
             color: #757575;
             background-color: #f1f1f1">
      <div style="width: 0.6rem;text-align: center"
           @click="$parent.compname=''">
        <i class="iconfont icon-left1"></i>
      </div>
      <div style="font-size: 0.3rem;font-weight: 700;line-height: 0.85rem">
        密码修改
      </div>
    </div>
    <div class="" style="padding: 0.2rem;">
      <div style="font-size: 0.26rem;margin-top: 0.2rem">
        原始密码：
      </div>
      <div style="margin-top: 0.2rem">
        <input type="password"
               v-model="upworldMes.oldPwd"
               class="BMinput" placeholder="请输入原始新密码">
      </div>
      <div style="font-size: 0.26rem;margin-top: 0.2rem">
        新密码：
      </div>
      <div style="margin-top: 0.2rem">
        <input type="password"
               v-model="upworldMes.newPwd"
               class="BMinput" placeholder="请输入新密码">
      </div>
      <div style="margin-top: 0.2rem">
        <!--<Button type="info" long-->
                <!--@click="UPwoeld"-->
                <!--style="padding:0.1rem;font-size: 0.35rem;font-weight: 700"-->
        <!--&gt;确定</Button>-->
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
