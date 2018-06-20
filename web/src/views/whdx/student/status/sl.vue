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
                operate:'学习进度',
				saveUrl:this.apis.kssl.ADD,
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
                    {label:'驾校',prop:'code',type:'foreignKey'},
                    {label:'受理时间',prop:'slSj',type:'date'},
                    {label:'考试原因',prop:'slType',dict:'ZDCLK0070',type:'dict'},
                ],
                ruleInline:{
				},
                foreignList:{
                    code:{url:this.apis.school.QUERY,key:'schoolCode',val:'schoolName',items:[]},
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
