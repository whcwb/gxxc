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
							<label>身份证正面</label>
							<img class="docImg" src="../../../../../static/sfzzm.jpg"/>
						</Col>
						<Col span="12">
							<label>身份证反面</label>
							<img class="docImg" src="../../../../../static/sfzfm.jpg"/>
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
                operate:'认证',
                saveUrl:this.apis.maintain.ADD,
                showModal: true,
                readonly: false,
                formItem: {
                    yhId:''
                },
                formInputs:[
                    {label:'姓名',prop:'yhXm'},
                    {label:'状态',prop:'yhZt',type:'dict',dict:'rzzt'},
                ],
                ruleInline:{
                }
            }
        },
        created(){
            this.formItem.yhId = this.$parent.choosedItem.yhId
            this.util.initFormModal(this);
            this.getById();
        },
        methods: {
            getById(){
                this.$http.get(this.apis.student.getById+this.formItem.yhId).then((res)=>{
                    if (res.code === 200){
                        this.formItem = res.result;
                    }
                })
            }
        }
    }
</script>

<style>

</style>
