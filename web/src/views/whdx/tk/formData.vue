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
		name: 'ksJfForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
                saveUrl:this.apis.tk.tk,
				formItem: {
				},
                formInputs:[
                    {label:'学员',prop:'yhXm',disabled:true},
                    {label:'证件号码',prop:'yhZjhm',disabled:true},
                    {label:'创建时间',prop:'cjsj',disabled:true},
                    {label:'经办人',prop:'jbrXm',disabled:true},
                    {label:'退款状态',prop:'tkType',dict:'tkzt',type:'dict'},
                    {label:'退款描述',prop:'tkMessage'},
                ],
                ruleInline:{
				},
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
