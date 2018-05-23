<template>
	<div>
		<Modal
				v-model="showModal"
				height="460"
				width="900"
				:closable='false'
				:mask-closable="false"
				title="证件照片">
			<Form :model="usermes" :label-width="120">
			<Row>
				<Col>
					<FormItem prop="xm" label='姓名：'>
						<Input type="text" v-model="usermes.xm"  disabled ></Input>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col>
					<FormItem prop="xm" label='身份证号：'>
						<Input type="text" v-model="usermes.sfzmhm" disabled></Input>
					</FormItem>
				</Col>
			</Row>
			</Form>
			<Row style="padding-bottom: 15px">
				<Card dis-hover>
					<Row>
						<Col span="8">
							<div style="text-align:center">
								<div class="demo-upload-list" v-if="sfzzmFile != null">
									<template v-if="sfzzmFile.status === 'finished'">
										<img :src="sfzzmFile.url">
										<div class="demo-upload-list-cover">
											<Icon type="ios-eye-outline" size="32" @click.native="handleView(sfzzmFile.url)"></Icon>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<Icon type="refresh" size="32" @click.native="handleRemove(sfzzmFile)"></Icon>
										</div>
									</template>
									<template v-else>
										<Progress v-if="sfzzmFile.showProgress" :percent="sfzzmFile.percentage" hide-info></Progress>
									</template>
								</div>

								<Upload
										v-show="sfzzmFile == null"
										ref="upload"
										:headers="{'userid':curUser.userId, 'token':curUser.token}"
										:show-upload-list="false"
										:on-success="(res, file,fileList)=>{successCallback(res, file,'sfzzmFile')}"
										:format="['jpg','jpeg','png']"
										:max-size="2048"
										:on-format-error="handleFormatError"
										:on-exceeded-size="handleMaxSize"
										type="drag"
										:action="uploadUrl+'/'+usermes.yhid+'/10/sfzzmFile?targetPath=sfzFile'"
										style="display: inline-block;width:180px;height:180px">
									<div style="width: 180px;height:180px;line-height: 200px;">
										<Icon type="ios-cloud-upload" size="80" style="color: #3399ff"></Icon>
									</div>
								</Upload>
								<h3>身份证正面</h3>
							</div>
						</Col>
						<Col span="8">
							<div style="text-align:center">
								<div class="demo-upload-list" v-if="sfzfmFile != null">
									<template v-if="sfzfmFile.status === 'finished'">
										<img :src="sfzfmFile.url">
										<div class="demo-upload-list-cover">
											<Icon type="ios-eye-outline" size="32" @click.native="handleView(sfzfmFile.url)"></Icon>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<Icon type="refresh" size="32" @click.native="handleRemove(sfzfmFile)"></Icon>
										</div>
									</template>
									<template v-else>
										<Progress v-if="sfzfmFile.showProgress" :percent="sfzfmFile.percentage" hide-info></Progress>
									</template>
								</div>

								<Upload
										v-show="sfzfmFile == null"
										ref="upload"
										:headers="{'userid':curUser.userId, 'token':curUser.token}"
										:show-upload-list="false"
										:on-success="(res, file,fileList)=>{successCallback(res, file,'sfzfmFile')}"
										:format="['jpg','jpeg','png']"
										:max-size="2048"
										:on-format-error="handleFormatError"
										:on-exceeded-size="handleMaxSize"
										type="drag"
										:action="uploadUrl+'/'+usermes.yhid+'/10/sfzfmFile?targetPath=sfzFile'"
										style="display: inline-block;width:180px;height:180px">
									<div style="width: 180px;height:180px;line-height: 200px;">
										<Icon type="ios-cloud-upload" size="80" style="color: #3399ff"></Icon>
									</div>
								</Upload>
								<h3>身份证反面</h3>
							</div>
						</Col>
						<Col span="8">
							<div style="text-align:center">
								<div class="demo-upload-list" v-if="jlzzmFile != null">
									<template v-if="jlzzmFile.status === 'finished'">
										<img :src="jlzzmFile.url">
										<div class="demo-upload-list-cover">
											<Icon type="ios-eye-outline" size="32" @click.native="handleView(jlzzmFile.url)"></Icon>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<Icon type="refresh" size="32" @click.native="handleRemove(jlzzmFile)"></Icon>
										</div>
									</template>
									<template v-else>
										<Progress v-if="jlzzmFile.showProgress" :percent="jlzzmFile.percentage" hide-info></Progress>
									</template>
								</div>

								<Upload
										v-show="jlzzmFile == null"
										ref="upload"
										:show-upload-list="false"
										:headers="{'userid':curUser.userId, 'token':curUser.token}"
										:on-success="(res, file,fileList)=>{successCallback(res, file,'jlzzmFile')}"
										:format="['jpg','jpeg','png']"
										:max-size="2048"
										:on-format-error="handleFormatError"
										:on-exceeded-size="handleMaxSize"
										type="drag"
										:action="uploadUrl+'/'+usermes.yhid+'/10/jlzzmFile?targetPath=jlzFile'"
										style="display: inline-block;width:180px;height:180px">
									<div style="width: 180px;height:180px;line-height: 200px;">
										<Icon type="ios-cloud-upload" size="80" style="color: #3399ff"></Icon>
									</div>
								</Upload>
								<h3>教练证</h3>
							</div>
						</Col>
					</Row>
				</Card>
			</Row>
			<Row>
				<Card dis-hover>
					<Row>
						<Col span="12">
							<div style="text-align:center">
								<div class="demo-upload-list" v-if="jszzmFile != null">
									<template v-if="jszzmFile.status === 'finished'">
										<img :src="jszzmFile.url">
										<div class="demo-upload-list-cover">
											<Icon type="ios-eye-outline" size="32" @click.native="handleView(jszzmFile.url)"></Icon>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<Icon type="refresh" size="32" @click.native="handleRemove(jszzmFile)"></Icon>
										</div>
									</template>
									<template v-else>
										<Progress v-if="jszzmFile.showProgress" :percent="jszzmFile.percentage" hide-info></Progress>
									</template>
								</div>

								<Upload
										v-show="jszzmFile == null"
										ref="upload"
										:show-upload-list="false"
										:headers="{'userid':curUser.userId, 'token':curUser.token}"
										:on-success="(res, file,fileList)=>{successCallback(res, file,'jszzmFile')}"
										:format="['jpg','jpeg','png']"
										:max-size="2048"
										:on-format-error="handleFormatError"
										:on-exceeded-size="handleMaxSize"
										type="drag"
										:action="uploadUrl+'/'+usermes.yhid+'/10/jszzmFile?targetPath=jszFile'"
										style="display: inline-block;width:180px;height:180px">
									<div style="width: 180px;height:180px;line-height: 200px;">
										<Icon type="ios-cloud-upload" size="80" style="color: #3399ff"></Icon>
									</div>
								</Upload>
								<h3>驾驶证正本</h3>
							</div>
						</Col>
						<Col span="12">
							<div style="text-align:center">
								<div class="demo-upload-list" v-if="jszfmFile != null">
									<template v-if="jszfmFile.status === 'finished'">
										<img :src="jszfmFile.url">
										<div class="demo-upload-list-cover">
											<Icon type="ios-eye-outline" size="32" @click.native="handleView(jszfmFile.url)"></Icon>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<Icon type="refresh" size="32" @click.native="handleRemove(jszfmFile)"></Icon>
										</div>
									</template>
									<template v-else>
										<Progress v-if="jszfmFile.showProgress" :percent="jszfmFile.percentage" hide-info></Progress>
									</template>
								</div>

								<Upload
										v-show="jszfmFile == null"
										ref="upload"
										:show-upload-list="false"
										:headers="{'userid':curUser.userId, 'token':curUser.token}"
										:on-success="(res, file,fileList)=>{successCallback(res, file,'jszfmFile')}"
										:format="['jpg','jpeg','png']"
										:max-size="2048"
										:on-format-error="handleFormatError"
										:on-exceeded-size="handleMaxSize"
										type="drag"
										:action="uploadUrl+'/'+usermes.yhid+'/10/jszfmFile?targetPath=jszFile'"
										style="display: inline-block;width:180px;height:180px">
									<div style="width: 180px;height:180px;line-height: 200px;">
										<Icon type="ios-cloud-upload" size="80" style="color: #3399ff"></Icon>
									</div>
								</Upload>
								<h3>驾驶证副本</h3>
							</div>
						</Col>
					</Row>
				</Card>
			</Row>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<!--<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>-->
			</div>
		</Modal>

		<Modal title="图片预览" v-model="visible" >
			<img :src="imgUrl" v-if="visible" style="width: 100%">
			<div slot="footer"></div>
		</Modal>
	</div>
</template>
<script>
    import Cookies from 'js-cookie';
    export default {
    	name:'',
        data () {
            return {
				curUser:{},
                visible:false,
                showModal:true,
                defaultList: [],
                imgUrl: '',
                visible: false,
                uploadList: [],
				uploadUrl:this.apis.FILE.UPLOAD,
				//身份证正面
				sfzzmFile:null,
				//身份证反面
                sfzfmFile:null,
				//驾驶证正本
                jszzmFile:null,
                //驾驶证副本
                jszfmFile:null,
                //教练证
                jlzzmFile:null,
            }
        },
        props:{
            usermes:{
                type:Object,
                default:{}
            },
			urlList:{
				type:String,
				default:''
			}
		},
        created(){
            this.curUser = JSON.parse(Cookies.get('result')).accessToken;
            this.$http.get(this.apis.FILE.FINDBYPID + "/" + this.usermes.yhid).then((res) =>{
                if (res.code == 200){
                    for (let item of res.result){
                        this.$data[item.vfDamc] = {
                            name:item.vfDamc,
							status:'finished',
							url:this.apis.STATIC_PATH + item.vfNetPath
                        };
					}
				}
            })
        },
        methods: {
            //更新
            updateFiles(){

			},
            colse(){
                var v = this
                v.$parent.compName = ''
            },
        	dataList(){
        		let ArrList = this.urlList.split(',')
	        	for(var i=0;i<ArrList.length-1;i++){
	        		this.defaultList.push({'url':this.apis.STATIC_PATH+ArrList[i]})
	        	}
        	},
            handleView (url) {
                this.imgUrl = url;
                this.visible = true;
            },
			//文件上传成功后，回调该方法，进行后续处理
            successCallback (res, file, locDataName) {
        	    if (res.code == 200){
                    //拼接文件全路径url
                    file.url = this.apis.STATIC_PATH + res.message;
                    //重新给name赋值，以便预览图片和删除文件时使用
                    file.name = locDataName;
                    //将文件对象和data的属性进行绑定
                    this.$data[locDataName] = file;
                    console.log(file);
				}else{
                    this.$Message.error("文件上传失败："+res.message);
				}
            },
            handleRemove (file) {
                this.$data[file.name] = null;
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
            }
        },
        mounted () {
            this.uploadList = this.$refs.upload.fileList;
        }
    }
</script>

<style scoped>
	.demo-upload-list{
		display: inline-block;
		width: 260px;
		height: 180px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
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
		padding-top: 60px;
		display: block;
	}
	.demo-upload-list-cover i{
		color: #fff;
		font-size: 20px;
		cursor: pointer;
		margin: 0 2px;
	}
</style>