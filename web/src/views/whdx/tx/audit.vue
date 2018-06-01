<style lang="less">
	@import '../../../styles/common.less';
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
		data() {
			return {
			    v:this,
                operate:'新建',
		        components:{formItems},
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'用户名称',prop:'yhMc'},
                    {label:'提现方式',prop:'ttFs',dict:'ZDCLK0047'},
                    {label:'提现金额(单位分)',prop:'ttJe'},
                    {label:'提现时间',prop:'ttSj'},
                    {label:'提现状态',prop:'ttZt',dict:'ZDCLK0048'},
                    {label:'红包发送的次数',prop:'ttHbcs'},
                    {label:'提现红包凭证 (用于查询红包是否已经被领取)',prop:'ttHbpz'},
                    {label:'审核人',prop:'ttShr'},
                    {label:'提现审核状态',prop:'ttShzt',dict:'ZDCLK0049'},
                    {label:'备注',prop:'ttBz'},
                    {label:'银行卡号',prop:'ttYhkh'},
                    {label:'开户行',prop:'ttKhh'},
                    {label:'提现姓名',prop:'ttXm'},
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
