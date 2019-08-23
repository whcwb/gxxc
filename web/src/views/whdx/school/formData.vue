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
		name: 'driversSchoolForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'驾校名称',prop:'schoolName'},
                    {label:'驾校地址',prop:'schoolAddress'},
                    {label:'区域代码',prop:'regionCode',dict:'ZDCLK0060',type:'dict'},
                    {label:'联系人',prop:'linkMan'},
                    {label:'联系电话',prop:'linkTel'},
                    {label:'可培训车型',prop:'trainableVehicleType'},
                    {label:'教练总数',prop:'coachTotal'},
                    {label:'教练车总数',prop:'carTotal'},
                    {label:'场总面积',prop:'totalAreaJlc'},
                    {label:'备注',prop:'remark'},
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
