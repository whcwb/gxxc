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
        		<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
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
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
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
