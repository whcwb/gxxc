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
			   :mask-closable="false" title="认证">
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
							<img v-else class="docImg" src="static/card_front.png"/>
						</Col>
						<Col span="12">
							<label>身份证反面</label>
							<img v-if="files.cardBack != ''" class="docImg" :src="staticPath+files.cardBack"/>
							<img v-else class="docImg" src="static/card_back.png"/>
						</Col>
						<Col v-if="formItem.yhLx == '2'" span="12">
							<label>驾驶证正本</label>
							<img class="docImg" src="../../../../../static/jszzb.jpg"/>
						</Col>
						<Col v-if="formItem.yhLx == '2'" span="12">
							<label>驾驶证副本</label>
							<img class="docImg" src="../../../../../static/jsz.jpg"/>
						</Col>
					</Row>
					<Row>
						<form-items :parent="v" :parentFormInputs="formInputs2"></form-items>
						<Col span="12">
							<FormItem v-if="formItem.yhZt == '2'" prop='yhZtMs' label='失败原因'>
								<Input v-model="formItem.yhZtMs"></Input>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">确认</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    export default {
        name: 'byxxForm',
        data() {
            return {
                v:this,
                operate:'认证',
                saveUrl:this.apis.student.updateyhrz,
                staticPath:this.apis.getImgUrl,
				formValid:true,
                showModal: true,
                readonly: false,
                files:{
                    cardFront:'',
                    cardBack:'',
                    licenceFront:'',
                    licenceBack:''
                },
                formItem: {
                    id:'',
                    yhZtMs:'',
                },
                formInputs1:[
                    {separator:true,label:'基本信息'},
                    {label:'姓名',prop:'yhXm',disabled:true},
                    {label:'性别',prop:'yhXb',type:'dict',dict:'ZDCLK0042',disabled:true},
                    {label:'身份证号码',prop:'yhZjhm',disabled:true},
                ],
                formInputs2:[
                    {separator:true,label:'审核结果'},
                    {label: '审核结果',prop:'yhZt',dict:'ZDCLK0043',type:'radio',excludeDict:['0']},
                ],
                ruleInline:{
                },
                saveParams:{}
            }
        },
        created(){
            this.formItem = this.$parent.choosedItem
            this.util.initFormModal(this);
            this.getImages();
        },
        methods: {
            pass(){
                this.formItem.yhZt = '1';
                this.util.save(this);
            },
            reject(){
                this.formItem.yhZt = '2';
                this.util.save(this);
            },
            beforeSave(){
                if (this.formItem.yhZt == '2' && (this.formItem.yhZtMs == null || this.formItem.yhZtMs == '')){
                    this.$Message.error("请填写备注");
                    this.formValid = false;
                    return;
                }
                this.formValid = true;
                this.saveParams = {
                    id:this.$parent.choosedItem.id,
                    yhZt:this.formItem.yhZt,
                    yhZtMs:this.formItem.yhZtMs,
				};
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
