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
    <div class="box_col_auto">
      <div class="box-row">
        <div class="box_row_100" style="margin: 0.15rem">
          <imgup :demoImg="imgList.zm" fileType="10"
                 @handleSuccess="(res)=>{handleSuccess(res,0)}"></imgup>
          <!--<img src="static/home/id_03.png"-->
               <!--style="width: 100%"-->
               <!--alt="">-->
        </div>
        <div class="box_row_100" style="margin: 0.15rem">
          <img src="static/home/id_05.png"
               style="width: 100%"
               alt="">
        </div>
      </div>
      <div @click="" style="padding: 0.3rem 0.1rem">
        <el-button type="primary" @click=""
                   style="width: 100%;padding: 0.15rem"
        >提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
    import imgup from '@/views/components/upLoad/zjimgUpload'
    export default {
        name: "smrz",
        components:{
          imgup
        },
        data(){
          return{
            imgList:{
              zm:'static/home/id_03.png',
              bm:'static/home/id_05.png'
            },
          }
        },
        created(){
        },
        methods:{
          handleSuccess(res,val){
            console.log('上传成功事件监听',res)
            console.log(val)
            if(res.code==200){

            }
            // this.UPTx('/'+res.message)
          },
        }
    }
</script>

<style scoped>

</style>
