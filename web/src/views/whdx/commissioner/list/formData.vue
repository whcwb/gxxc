<style lang="less">
	@import '../../../../styles/common.less';
	.docImg{
		width: 100%;
		padding: 10px;
	}
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='1200' :closable='false'
			:mask-closable="false" :title="operate+''">
			<div style="overflow: auto;height: 500px;">
				<Form ref="form"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<form-items :parent="v"></form-items>
						<Col span="12">
							<FormItem label="教练类型" prop="jsId">
								<Select v-model="formItem.jsId" multiple >
									<Option value="slzy">受理</Option>
									<Option value="k1">科目一</Option>
									<Option value="k2">科目二</Option>
									<Option value="k3">科目三</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="培训车型" prop="jlCx">
								<Select v-model="formItem.jlCx" multiple >
									<Option value="C1">C1</Option>
									<Option value="C2">C2</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
				<!--	<Row style="text-align: center">
						<img style="height: 120px" src="" alt="">
						<Upload :action="apis.BASE_URL + '/upload'">
							<Button icon="ios-cloud-upload-outline">上传头像</Button>
						</Upload>
					</Row>-->
				</Form>
			</div>
			<div slot='footer'>
				<Button @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import chooseImg from '../../components/chooseImg'
	export default {
		name: 'byxxForm',
		components:{
		    chooseImg
		},
		data() {
			return {
			    v:this,
                operate:'教练',
				saveUrl:this.apis.teacher.ADDJL,
                staticPath:this.apis.getImgUrl,
				// uploadPrivatePath:this.apis.UPLOAD_PRIVATE,
				uploadPrivatePath:this.apis.UPLOAD,
				showModal: true,
				readonly: false,
                files:{
                    cardFront:'',
                    cardBack:'',
                    licenceFront:'',
                    licenceBack:''
                },
				formItem: {
					jsId:[],
					jlCx:[]
				},
                formInputs:[
                    {label:'手机号码',prop:'yhZh',readonly:true},
                    // {label:'密码',prop:'yhMm'},
                    // {label:'姓名',prop:'yhXm'},
                    // {label:'身份证号码',prop:'yhZjhm'},
                    // {label:'手机号码',prop:'yhSjhm'},
                    {label:'区域',prop:'jlQu',dict:'ZDCLK0060'},
                    // {label:'联系电话',prop:'jlJjlxrdh'},
                    {label:'教练地址',prop:'jlZz'},

                ],
                ruleInline:{
				},
			}
		},
        /**
		 * yhXm:姓名
         yhZjhm:320333333333333333
         yhSjhm:18672922222
         jlJl:11
         jlQu:10
         jlZml:证明人
         jlJjlxr:紧急联系人
         jlJjlxrdh:联系电话
         jlZz:教练地址
         jlImg:/aaa.jpg
         jlMs:教练简界
         imgList:./aaa.jpg,./bbb.jpg,-,-
         yhMm:
         */
		created(){
		    this.util.initFormModal(this);
		},
		methods: {
		    beforeSave(){
		        this.formItem.yhLx = this.formItem.jsId;
            },
            imgChange(o){
		        let type = o.type;
		        this.files[type] = o.path;
		        this.addImg(type,path);
            },
			addImg(type,path){
		        let t = "cardFront";
		        switch(type){
					case "cardFront": t = "10";break;
					case "cardBack": t = "11";break;
					case "licenceFront": t = "20";break;
					case "licenceBack": t = "21";break;
				}
		        let params = {
                    yhId:this.formItem.yhId,
                    wjTpdz:path,
                    wjSx:t
				}
                this.$http.post(this.apis.wj.ADD,params).then((res)=>{
                    if (res.code === 200 && res.result){
                        for (let r of res.result){
                            switch(r.wjSx){
                                case '10':
                                    v.files.cardFront = r.wjTpdz;
                                    break;
                                case '11':
                                    v.files.cardBack = r.wjTpdz;
                                    break;
                                case '20':
                                    v.files.licenceFront = r.wjTpdz;
                                    break;
                                case '21':
                                    v.files.licenceBack = r.wjTpdz;
                                    break;
                                default:
                            }
                        }
                        console.log(v.files);
                    }
                })
			}
		}
	}
</script>

<style>

</style>
