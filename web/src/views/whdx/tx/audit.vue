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
								<Col span="12">
									<FormItem v-if="formItem.ttShzt == '2'" prop='ttBz' label='失败原因'>
										<Input v-model="formItem.ttBz"></Input>
									</FormItem>
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
	export default {
		name: 'txForm',
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
                    {label:'用户名称',prop:'yhMc',disabled:true},
                    {label:'开户行',prop:'ttKhh',disabled:true},
                    {label:'银行卡号',prop:'ttYhkh',disabled:true},
                    {label:'提现方式',prop:'ttFs',dict:'ZDCLK0047',disabled:true},
                    {label:'提现姓名',prop:'ttXm',disabled:true},
                    {label:'提现金额',prop:'ttJe',append:'元',disabled:true,handler:(o)=>{
                            return parseFloat(o/100);
                        }
                    },
                    {separator:true,label:'审核结果'},
                    {label:'提现审核状态',prop:'ttShzt',dict:'ZDCLK0049',type:'radio'},
                ],
                ruleInline:{
				},
                formValid:true,
			}
		},
		created(){
		    this.util.initFormModal(this);
		},
		methods: {
            beforeSave(){
                if (this.formItem.ttShzt == '2' && (this.formItem.ttBz == null || this.formItem.ttBz == '')){
                    this.$Message.error("请填写备注");
                    this.formValid = false;
                    return;
                }
                this.formValid = true;
            },
		}
	}
</script>

<style>

</style>
