<style>
  .el-upload{
    height: 100%;
  }
</style>
<template>
    <div class="box_col">
      <el-upload
        class="upload-demo"
        :action="apis.upImgUrl"
        :multiple="false"
        :show-file-list="false"
        :limit="1"
        :on-success="handleSuccess"
        :on-error="handleError"
        :before-upload="handleBeforeUpload"
        style="height: 100%;width:100%;">
          <div style="height: 100%;width:100%;">
            <img :src="demoImg" style="width: 100%;height: 100%">
          </div>
      </el-upload>
    </div>
</template>
<script>
  import { Indicator } from 'mint-ui';
  export default {
    name:'imgUp',
    components:{
    },
    props:{
      demoImg:{
        type:String,
        default:''
        // static/home/66_03.png
      },
    },
    data () {
      return {
        url:this.apis.getImgUrl,
        defaultList: [
        ],
        uploadList: [],
      }
    },
    created(){
      console.log('图片地址',this.demoImg)
    },
    mounted () {
      // this.uploadList = this.$refs.upload.fileList;
    },
    methods: {
      handleSuccess (res, file) {
        // console.log('文件上传成功')
        // console.log(res)
        // console.log(file)
        Indicator.close();
        this.$emit('handleSuccess',res)
      },
      handleError(res,file){
        Indicator.close();
        console.log('文件上传失败')
        console.log(res)
        console.log(file)
      },
      handleFormatError (file) {
        console.log({
          title: 'The file format is incorrect',
          desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
        });
      },
      handleMaxSize (file) {
        console.log('File  ' + file.name + ' 文件太大了 2M.');
      },
      handleBeforeUpload (file) {//上传之前
        Indicator.open({
          text: '文件上传中……',
          spinnerType: 'fading-circle'
        });
        const isJPG = file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        // return isJPG && isLt2M;
      }
    }
  }
</script>
<style>
  .ivu-upload-drag{
   border: none;
  }
  .ivu-upload-drag:hover{
    border: none;
  }
  .demo-upload-list{
    display: inline-block;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    /*border: 1px solid transparent;*/
    border: none;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
  }
  .demo-upload-list img{
    width: 100%;
    height: 100%;
  }
  .demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
  }
  .demo-upload-list:hover .demo-upload-list-cover{
    display: block;
  }
  .demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }
</style>
