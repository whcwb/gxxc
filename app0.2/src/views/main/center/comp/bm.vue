<template>
  <div class="saveName box_col">
    <div class="box-row"
         style="height: 0.5rem;
             border-bottom: #dedede 1px solid;
             color: #757575;
             line-height: 0.55rem;
             background-color: #f1f1f1">
        <div style="width: 0.6rem;text-align: center;"
             @click="$parent.compname=''">
          <i class="iconfont icon-left1" style="font-size: 0.2rem"></i>
        </div>
        <div style="font-size: 0.18rem;font-weight: 700;">
          昵称修改
        </div>
    </div>
    <div class="" style="padding: 0.2rem">
      <div style="font-size: 0.2rem">
        用户昵称：
      </div>
      <div style="margin-top: 0.2rem">
        <input type="text" v-model="bm" class="BMinput">
      </div>
      <div style="margin-top: 0.2rem">
        <el-button type="primary" @click="UPbm"
          style="width: 100%;padding: 0.15rem"
        >确定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
    import {Toast} from 'mint-ui';
    export default {
        name: "bm",
        components:{
        },
        data(){
          return{
            bm:JSON.parse(localStorage.getItem("userMess")).yhBm,
          }
        },
        methods:{
          UPbm(){
            var v = this
            this.$http.post(this.apis.CHUSERMESS,{'yhBm':v.bm}).then((res)=>{
              if(res.code==200){
                v.$parent.userMessF()
              }
            }).catch((err)=>{

            })
          },
        }
    }
</script>

<style scoped>

</style>
