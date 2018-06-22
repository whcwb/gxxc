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
                saveUrl:this.apis.ksJf.ADD,
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
                    {label:'学员缴费时间',prop:'jfSj',type:"date"},
                    {label:'缴费渠道',prop:'jfFs'},
                    {label:'科目编码',prop:'kmId',dict:'ZDCLK0067'},
                    {label:'缴费金额',prop:'jfJl',append:'元',handler:(o)=>{
                            return parseFloat(o/100);}
					},
                ],
                ruleInline:{
				},
                foreignList:{
                    yhId:{url:this.apis.student.QUERY,key:'id',val:'yhXm',items:[]},
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
