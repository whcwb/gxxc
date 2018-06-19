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
		name: 'ksSlForm',
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
                    {label:'学员ID',prop:'yhId'},
                    {label:'驾校代码 BIZ_DRIVERS_SCHOOL 表的主键',prop:'schoolCode'},
                    {label:'受理时间',prop:'slSj'},
                    {label:'受理流水',prop:'slLs'},
                    {label:'受理车型(车型字典项：ZDCLK0069  A1 A2……)',prop:'slCx'},
                    {label:'考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)',prop:'slKsyy'},
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
