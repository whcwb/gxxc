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
	import formItems from '../components/formItems'
	export default {
		name: 'cpForm',
		components:{formItems},
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'产品名称',prop:'cpMc'},
                    {label:'产品类型',prop:'cpType',dict:'ZDCLK0063'},
                    {label:'产品总金额',prop:'cpJl',type:'number',append:'元'},
                    {label:'是否分佣',prop:'cpYj',dict:'ZDCLK0064'},
                    {label:'一级佣金',prop:'cpYjyj',type:'number',append:'元'},
                    {label:'二级佣金',prop:'cpRjyj',type:'number',append:'元'},
                    {label:'产品是否有效',prop:'cpYx',dict:'ZDCLK0065'},
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
