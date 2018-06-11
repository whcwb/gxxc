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
	export default {
		name: 'orderForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
                foreignList:{
                    yhId:{url:this.apis.student.QUERY,key:'id',val:'yhXm',items:[]},
                },
				formItem: {
				},
                formInputs:[
                    {label:'用户',prop:'yhId',type:'foreignKey'},
                    {label:'订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)',prop:'ddZt'},
                    {label:'支付通道(ZDCLK0038 1、支付宝  2、微信  3、银联  4、快钱……)',prop:'ddZftd'},
                    {label:'支付时间',prop:'ddZfsj'},
                    {label:'支付状态（ZDCLK0039  0,待支付 1、支付成功  2、支付失败）',prop:'ddZfzt'},
                    {label:'支付金额(单位 分)',prop:'ddZfje'},
                    {label:'支付凭证ID(保存支付通道返回的CODE)',prop:'ddZfpz'},
                    {label:'姓名',prop:'yhXm'},
                    {label:'订单备注',prop:'ddBz'},
                    {label:'上级ID',prop:'yhSjid'},
                    {label:'上上级ID',prop:'yhSsjid'},
                    {label:'定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )',prop:'jobType'},
                    {label:'JOB处理结果描述',prop:'jobDescribe'},
                    {label:'定时任务处理时间。',prop:'jobDisposeDate'},
                    {label:'订单支付成功后，将实际支付的金额回写到这里。用于验证订单支付是否异常',prop:'payMoney'},
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
