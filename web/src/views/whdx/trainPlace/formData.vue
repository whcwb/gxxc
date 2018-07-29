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
		name: 'trainPlaceForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
				},
                formInputs:[
                    {label:'驾校代码',prop:'schoolCode',type:'foreignKey'},
                    {label:'区域代码',prop:'regionCode',dict:'ZDCLK0060',type:'dict'},
                    {label:'训练场地名称',prop:'placeName'},
                    {label:'场地缩略图',prop:'placeIcon'},
                    {label:'地理信息坐标',prop:'placeCoordinate'},
                    {label:'经度',prop:'longitude'},
                    {label:'纬度',prop:'latitude'},
                    {label:'地址',prop:'address'},
                    {label:'实测面积',prop:'measuredArea'},
                    {label:'备注',prop:'remark'},
                    {label:'培训车型',prop:'techDriverType'},
                    {label:'车辆数',prop:'approvedCarNum'},
                ],
                ruleInline:{
				},
                foreignList:{
                    schoolCode:{url:this.apis.school.QUERY,key:'schoolCode',val:'schoolName',items:[]},
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
