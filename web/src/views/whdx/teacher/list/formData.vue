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
							<img v-if="files.licenceFront != ''" class="docImg" :src="staticPath+files.licenceFront"/>
							<img v-else class="docImg" src="static/jszzb.jpg"/>
						</Col>
						<Col v-if="formItem.yhLx == '2'" span="12">
							<label>驾驶证副本</label>
							<img v-if="files.licenceBack != ''" class="docImg" :src="staticPath+files.licenceBack"/>
							<img v-else class="docImg" src="static/jsz.jpg"/>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">确定</Button>
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
                operate:'教练员',
				saveUrl:this.apis.teacher.ADD,
                staticPath:this.apis.getImgUrl,
				showModal: true,
				readonly: false,
                files:{
                    cardFront:'',
                    cardBack:'',
                    licenceFront:'',
                    licenceBack:''
                },
				formItem: {
				},
                formInputs:[
                    {label:'账号',prop:'yhZh',readonly:true},
                    {label:'姓名',prop:'yhXm'},
                    {label:'类型',prop:'yhLx',type:'dict',dict:'ZDCLK0041'},
                    {label:'性别',prop:'yhXb',type:'dict',dict:'ZDCLK0042'},
                    {label:'身份证号码',prop:'yhZjhm'},
                    {label:'状态',prop:'yhZt',type:'dict',dict:'rzzt'},
                    {label:'是否缴费',prop:'ddSfjx',type:'dict',dict:'ZDCLK0045'},
                ],
                ruleInline:{
				}
			}
		},
		created(){
		    this.util.initFormModal(this);
		    this.getImages();
		},
		methods: {
            getImages(){
                let v = this;
                this.$http.post(this.apis.wj.getByCondition,{yhId:this.formItem.yhId}).then((res)=>{
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
