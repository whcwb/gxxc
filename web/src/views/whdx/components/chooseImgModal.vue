<template>
	<div>
	<Modal v-model="showModal" width='900' :closable="false" @on-cancel="close" @on-ok="ok" title="选取图片">
	    <div class="demo-upload-list" v-for="item in uploadList">
	        <template v-if="item.status === 'finished'">
	            <img :src="item.url" style="width: 100%">
	            <div class="demo-upload-list-cover">
	                <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
	            </div>
	        </template>
	        <template v-else>
	            <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
	        </template>
	    </div>
	    <Upload
	        ref="upload"
	        :show-upload-list="false"
	        :default-file-list="defaultList"
	        :on-success="handleSuccess"
	        :format="['jpg','jpeg','png']"
	        :max-size="1000"
	        :on-format-error="handleFormatError"
	        :on-exceeded-size="handleMaxSize"
	        :before-upload="handleBeforeUpload"
	        multiple
	        type="drag"
	        :action="uploadUrl+'?targetPath=schoolFile'"
	        style="display: inline-block;width:58px;">
	        <div style="width: 58px;height:58px;line-height: 58px;">
	            <Icon type="camera" size="20"></Icon>
	        </div>
	    </Upload>
	</Modal>
	</div>
</template>
<script>

    export default {
    	name:'',
        data () {
            return {
                showModal:true,
                defaultList: [],
                imgName: '',
                visible: false,
                uploadList: [],
				uploadUrl:this.apis.UPLOAD,
				staticPath:this.apis.getImgUrl,
                urlList:''
            }
        },
        created(){
			if (this.$parent.$data.choosedImgs){
                this.urlList = this.$parent.$data.choosedImgs;
			}
        	this.dataList()
        },
        methods: {
        	dataList(){
        		let paths = this.urlList.split(',')
        		for (let r of paths){
        		    if (r == '')continue;
                    this.defaultList.push({'url':this.staticPath+r})
				}
            },
            handleView (name) {
                this.imgName = name;
                this.visible = true;
            },
            handleRemove (file) {
                const fileList = this.$refs.upload.fileList;
                this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
                this.$emit('removeFile',file.url.replace(this.staticPath,'')+',')
            },
            handleSuccess (res, file,fileList) {
                this.$emit('addImg',res.message);
                file.url = this.staticPath + res.message;
            },
            handleFormatError (file) {
                this.$Notice.warning({
                    title: '文件格式错误',
                    desc: '图片仅支持 jpg、jpeg、png'
                });
            },
            handleMaxSize (file) {
                this.$Notice.warning({
                    title: '文件太大了',
                    desc: '文件不能超过18M'
                });
            },
            handleBeforeUpload () {
                const check = this.uploadList.length < 5;
                if (!check) {
                    this.$Notice.warning({
                        title: 'Up to five pictures can be uploaded.'
                    });
                }
                return check;
            },
            ok(){
        	    let paths = '';
                for (let r of this.uploadList){
        	        paths += r.response.message + ',';
				}
                this.$emit('chooseImgFinish',paths)
                this.close()
            },
            close(){
                let v = this;
                v.showModal = false;
                setTimeout(() => {
                    v.$parent.$data.componentName = "";
                }, 200)
            }
        },
        mounted () {
            this.uploadList = this.$refs.upload.fileList;
        }
    }
</script>
