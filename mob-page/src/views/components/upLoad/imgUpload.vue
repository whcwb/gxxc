<style>
  .ivu-upload{
    height: 100%;
  }
</style>
<template>
    <!--<div class="box">-->
      <Upload
        ref="upload"
        :show-upload-list="false"
        :default-file-list="defaultList"
        :format="['jpg','jpeg','png']"
        :max-size="20480"
        :on-success="handleSuccess"
        :on-error="handleError"
        :on-format-error="handleFormatError"
        :on-exceeded-size="handleMaxSize"
        :before-upload="handleBeforeUpload"
        multiple
        type="drag"
        :action="apis.upImgUrl"
        style="border: none;width: 100%;height: 100%">
        <div style="height: 100%;width:100%">
          <img :src="demoImg" width="100%" height="100%">
        </div>
      </Upload>
    <!--</div>-->
</template>
<script>
  import {Card,Upload ,Modal,Icon} from 'iview'
  import { Indicator } from 'mint-ui';
  export default {
    name:'imgUp',
    components:{
      Card, Upload,Modal,Icon
    },
    props:{
      demoImg:{
        type:String,
        default:''
      },
    },
    data () {
      return {
        url:this.apis.getImgUrl,
        defaultList: [
        ],
        uploadList: []
      }
    },
    methods: {
      handleRemove (file) {//移除
        const fileList = this.$refs.upload.fileList;
        this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
      },
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
      handleBeforeUpload () {//上传之前
        Indicator.open({
          text: '文件上传中……',
          spinnerType: 'fading-circle'
        });
        const check = this.uploadList.length < 5;
        if (!check) {
          console.log({
            title: 'Up to five pictures can be uploaded.'
          });
        }
        return check;
      }
    },
    mounted () {
      this.uploadList = this.$refs.upload.fileList;
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
