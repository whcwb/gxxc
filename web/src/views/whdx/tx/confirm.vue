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
                    {label:'主键id',prop:'id'},
                    {label:'用户ID',prop:'yhId'},
                    {label:'用户名称',prop:'yhMc'},
                    {label:'biz_yjmx佣金明细id',prop:'yjId'},
                    {label:'提现方式 ZDCLK0047 (1、微信红包 2、人工转账)',prop:'ttFs'},
                    {label:'提现金额(单位分)',prop:'ttJe'},
                    {label:'提现时间',prop:'ttSj'},
                    {label:'提现状态 ZDCLK0048 (0 待审核 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请)',prop:'ttZt'},
                    {label:'红包发送的次数',prop:'ttHbcs'},
                    {label:'提现红包凭证 (用于查询红包是否已经被领取)',prop:'ttHbpz'},
                    {label:'父级ID(红包重发时，需要将原始记录的订单ID放到这里。)',prop:'ttFjid'},
                    {label:'审核人',prop:'ttShr'},
                    {label:'提现审核状态 ZDCLK0049 (0、 待审核 1、 审核通过 2、 审核拒绝)',prop:'ttShzt'},
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
