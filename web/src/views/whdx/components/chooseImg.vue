<template>
    <div>
        <div class="demo-upload-list" v-for="item in fileList">
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
            if (this.uploadUrl == ''){
                this.uploadUrl = this.apis.UPLOAD
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
                const check = this.fileList.length < 5;
                if (!check) {
                    this.$Notice.warning({
                        title: '最多只能上传5个文件'
                    });
                }
                return check;
            },
        },
    }
</script>
