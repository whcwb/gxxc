<template>
  <div class="saveName box_col">
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
        昵称修改
      </div>
    </div>
    <div class="" style="padding: 0.2rem">
      <div style="font-size: 0.26rem">
        用户别名：
      </div>
      <div style="margin-top: 0.2rem">
        <input type="text" v-model="bm" class="BMinput">
      </div>
      <div style="margin-top: 0.2rem">
        <!--<Button type="info" long-->
                <!--@click="UPbm"-->
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
