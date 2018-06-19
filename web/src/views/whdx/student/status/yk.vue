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
		name: 'ksYkForm',
		data() {
			return {
			    v:this,
                operate:'新建',
                saveUrl:this.apis.ksyk.ADD,
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'用户',prop:'yhId',type:'foreignKey'},
                    {label:'科目编码',prop:'kmCode',dict:'ZDCLK0067'},
                    {label:'考场名称',prop:'examPlaceId',type:'foreignKey'},
                    {label:'预约考试时间',prop:'ykSj',type:'datetime'},
                ],
                ruleInline:{
				},
                foreignList:{
                    examPlaceId:{url:this.apis.examPlace.QUERY,key:'id',val:'name',items:[]},
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
