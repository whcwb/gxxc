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
	export default {
		name: 'ksJgForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'',prop:'id'},
                    {label:'学员用户id',prop:'yhId'},
                    {label:'成绩单图片地址',prop:'cjdImg'},
                    {label:'科目编码 [ZDCLK0067]',prop:'kmCode'},
                    {label:'考试成绩',prop:'ksCj'},
                    {label:'学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）',prop:'ksSfhg'},
                    {label:'考场名称',prop:'schoolName'},
                    {label:'用户姓名',prop:'yhXm'},
                    {label:'用户证件号码',prop:'yhZjhm'},
                    {label:'创建人',prop:'cjr'},
                    {label:'创建时间',prop:'cjsj'},
                ],
                ruleInline:{
				}
			}
		},
		created(){
		    this.util.initFormModal(this);
		},
		methods: {
		}
	}
</script>

<style>

</style>
