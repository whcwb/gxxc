<style lang="less">
	@import '../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='1200' :closable='false'
        			:mask-closable="false" title="审核">
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
		name: 'txForm',
        components:{formItems},
		data() {
			return {
			    v:this,
				saveUrl:this.apis.tx.audit,
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {separator:true,label:'基本信息'},
                    {label:'用户名称',prop:'yhMc'},
                    {label:'提现金额',prop:'ttJe',append:'分',handler:(o)=>{
							return parseFloat(o/100);
						}
					},
                    {label:'银行卡号',prop:'ttYhkh'},
                    {label:'开户行',prop:'ttKhh'},
                    {label:'提现方式',prop:'ttFs',dict:'ZDCLK0047'},
                    {label:'提现姓名',prop:'ttXm'},
                    {separator:true,label:'审核结果'},
                    {label:'提现审核状态',prop:'ttShzt',dict:'ZDCLK0049'},
                    {label:'备注',prop:'ttBz'},
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
