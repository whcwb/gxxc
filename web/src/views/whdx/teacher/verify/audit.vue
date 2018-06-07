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
		<Modal v-model="showModal" width='900' :closable='false'
			   :mask-closable="false" title="审核">
			<div style="overflow: auto;height: 500px;">
				<Form ref="form"
					  :model="formItem"
					  :rules="ruleInline"
					  :label-width="100"
					  :styles="{top: '20px'}">
					<Row>
						<form-items :parent="v" :parentFormInputs="formInputs1"></form-items>
					</Row>
					<Row>
						<Col span="12">
							<label>身份证正面</label>
							<img v-if="files.cardFront != ''" class="docImg" :src="staticPath+files.cardFront"/>
							<img v-else class="docImg" src="static/sfzzm.jpg"/>
						</Col>
						<Col span="12">
							<label>身份证反面</label>
							<img v-if="files.cardBack != ''" class="docImg" :src="staticPath+files.cardBack"/>
							<img v-else class="docImg" src="static/sfzfm.jpg"/>
						</Col>
						<Col v-if="formItem.yhLx == '2'" span="12">
							<label>驾驶证正本</label>
							<img v-if="files.licenceFront != ''" class="docImg" :src="staticPath+files.licenceFront"/>
							<img v-else class="docImg" src="static/jszzb.jpg"/>
						</Col>
						<Col v-if="formItem.yhLx == '2'" span="12">
							<label>驾驶证副本</label>
							<img v-if="files.licenceBack != ''" class="docImg" :src="staticPath+files.licenceBack"/>
							<img v-else class="docImg" src="static/jsz.jpg"/>
						</Col>
					</Row>
					<Row>
						<form-items :parent="v" :parentFormInputs="formInputs2"></form-items>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">认证</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    import formItems from '../../components/formItems'
    export default {
        name: 'byxxForm',
        components:{formItems},
        data() {
            return {
                v:this,
				staticPath:this.apis.getImgUrl,
                showModal: true,
                saveUrl:this.apis.teacher.updateyhrz,
                readonly: false,
				files:{
                    cardFront:'',
					cardBack:'',
					licenceFront:'',
					licenceBack:''
				},
                formItem: {
                    id:''
                },
                formInputs1:[
                    {separator:true,label:'基本信息'},
                    {label:'姓名',prop:'yhXm',disabled:true},
                    {label:'性别',prop:'yhXb',type:'dict',dict:'ZDCLK0042',disabled:true},
                    {label:'身份证号码',prop:'yhZjhm',disabled:true},
                    {label: '手机号',prop:'yhSjhm',disabled:true},
                    {label: '所属区域',prop:'jlQu',dict:'ZDCLK0060',type:'dict',disabled:true},
                    {label: '教练驾龄',prop:'jlJl',disabled:true},
                    {label: '紧急联系人',prop:'jlJjlxr',disabled:true},
                    {label: '紧急联系人电话',prop:'jlJjlxrdh',disabled:true},
                ],
                formInputs2:[
                    {separator:true,label:'审核结果'},
                    {label: '审核结果',prop:'yhJlsh',dict:'ZDCLK0043',type:'dict'},
                    {label: '失败原因',prop:'yhZtMs'},
                ],
                ruleInline:{
                }
            }
        },
        created(){
            this.formItem = this.$parent.choosedItem
            this.util.initFormModal(this);
            this.getImages();
        },
        methods: {
            beforeSave(){
                this.formItem = {};
                this.formItem.id = this.$parent.choosedItem.id
                this.formItem.yhJlsh = '1';
            },
            getImages(){
                let v = this;
                this.$http.post(this.apis.wj.getByCondition,{yhId:this.formItem.id}).then((res)=>{
                    if (res.code === 200 && res.result){
                        for (let r of res.result){
                            switch(r.wjSx){
                                case '10':
                                    v.files.cardFront = r.wjTpdz;
                                    break;
                                case '11':
                                    v.files.cardback = r.wjTpdz;
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
                    }
                })
            }
        }
    }
</script>

<style>

</style>
