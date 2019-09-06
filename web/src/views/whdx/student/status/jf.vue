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
					<Row>
						<jf-list :parent="v"></jf-list>
					</Row>
        		</Form>
        	</div>
        	<div slot='footer'>
        		<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
        		<Button type="primary" @click="v.util.save(v)">确定</Button>
        	</div>
        </Modal>
	</div>
</template>

<script>
	import jfList from './jfList'
	export default {
		name: 'ksJfForm',
		components:{jfList},
		data() {
			return {
			    v:this,
                operate:'新建',
                saveUrl:this.apis.ksJf.ADD,
				showModal: true,
				readonly: false,
				formItem: {
                    jfJl:0,
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
                    {label:'学员缴费时间',prop:'jfSj',type:"date"},
                    {label:'缴费渠道',prop:'jfFs'},
                    {label:'科目编码',prop:'kmId',dict:'ZDCLK0067',excludeDict:['4']},
                    {label:'缴费金额',prop:'jfJl',append:'元',handler:(o)=>{
                        if (isNaN(o)) o = 0;
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
		    this.formItem.jfJl = 0;
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;
            if (this.userType == 'k1' || this.userType == 'k2' || this.userType == 'k3' || this.userType == 'k4'){
                let km = this.userType.charAt(1);
                this.formItem.kmId = km;
                this.formInputs[3].disabled = true;
            }
            this.formItem.jfSj = new Date().format('yyyy-MM-dd');
		},
		methods: {
		}
	}
</script>

<style>

</style>
