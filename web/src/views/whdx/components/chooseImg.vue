<template>
    <div>
        <div class="demo-upload-list box_row_list">
            <div class="imgItem"  v-for="item in fileList" v-if="item.status === 'finished'">
                <img :src="item.url" style="width: 100%">
                <div class="demo-upload-list-cover">
                    <Icon type="ios-trash-outline" size="52" color="#ffffff" @click.native="handleRemove(item)"></Icon>
                </div>
            </div>
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
                :action="uploadApi+'?targetPath=schoolFile'"
                style="display: inline-block;width:58px;">
            <div style="width: 58px;height:58px;line-height: 58px;">
                <Icon type="md-cloud-upload" size="40"/>
            </div>
        </Upload>
    </div>
</template>
<script>

    export default {
        name: '',
        data() {
            return {
                showModal: true,
                defaultList: [],
                imgName: '',
                visible: false,
                fileList: [],
                staticPath: this.apis.getImgUrl,
                urlList: '',
                uploadApi:this.apis.UPLOAD
            }
        },
        props: {
            uploadUrl: {
                type: String,
                default: ''
            },
            path: {
                type: String,
                default: ''
            },
            type: {
                type: String,
                default: ''
            }
        },
        created(){
            if (this.uploadUrl !== ''){
                this.uploadApi = this.uploadUrl
            }
            this.init()
        },
        mounted() {
            this.fileList = this.$refs.upload.fileList;
        },
        methods: {
            init() {
                if (this.path) {
                    this.urlList = this.path;
                    let path = this.urlList.split(',')
                    for (let r of path) {
                        if (r == '') continue;
                        let p = r.indexOf("http:") > -1 ? r : this.staticPath + r
                        this.defaultList.push({'url': p})
                    }
                }
            },
            notify() {
                let path = '';
                for (let r of this.fileList) {
                    path += r.url + ',';
                }
                this.$emit('imgChange', {type:this.type,path:path});
            },
            handleRemove(file) {
                const fileList = this.$refs.upload.fileList;
                this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
                this.notify();
            },
            handleSuccess(res, file, fileList) {
                file.url = this.staticPath + res.message;
                this.notify();
            },
            handleFormatError(file) {
                this.$Notice.warning({
                    title: '文件格式错误',
                    desc: '图片仅支持 jpg、jpeg、png'
                });
            },
            handleMaxSize(file) {
                this.$Notice.warning({
                    title: '文件太大了',
                    desc: '文件不能超过18M'
                });
            },
            handleBeforeUpload() {
                const check = this.fileList.length < 7;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传7个文件'
                    });
                }
                return check;
            },
        },
    }
</script>
<style lang="less">
    .demo-upload-list{
        .imgItem{
            width: 120px;
            height: 120px;
            margin: 10px;
            position: relative;
            img{
                height: 100%;
                width: 100%;
            }
            &:hover{
                .demo-upload-list-cover{
                    display: block;
                }
            }
            .demo-upload-list-cover{
                display: none;
                position: absolute;
                left: 0;
                top: 0;
                right: 0;
                bottom: 0;
                background-color: rgba(0,0,0,0.4);
                i{
                    position: absolute;
                    left: 50%;
                    top: 50%;
                    transform: translate(-50%,-50%);
                }
            }
        }
    }
</style>
