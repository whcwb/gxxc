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
						<Steps :current="currentStep" size="small">
							<Step  :title="item.title" :content="item.content" v-for="(item,index) in steps"></Step>
						</Steps>
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
                    {label:'单位名称',prop:'name'},
                    {label:'受理时间',prop:'slSj',type:'date'},
                    {label:'受理类型',prop:'slType',dict:'ZDCLK0071',type:'dict'},
                ],
                ruleInline:{
				},
                foreignList:{
                    code:{url:this.apis.school.QUERY,key:'schoolCode',val:'schoolName',items:[]},
                    yhId:{url:this.apis.student.QUERY,key:'id',val:'yhXm',items:[]},
                },
				steps:[],
				currentStep:0,
                handleStatus:'',
                handleSteps:0,
			}
		},
		created(){
		    this.util.initFormModal(this);
            this.getHandleStatus();
		},
		mounted(){
		},
		methods: {
            getHandleStatus(){
                this.$http.get(this.apis.getHandleStatus, {params: {yhId: this.formItem.yhId}}).then((res)=>{
                    if (res.code == 200){
                        this.handleStatus = res.result;
                        this.handleSteps = parseInt(res.message);
                        for (let k in this.handleStatus){
                            this.steps.push({title:this.dictUtil.getValByCode(this,'ZDCLK0071',k),content:this.handleStatus[k].name})
						}
                        console.log(this.steps);
                        this.currentStep = this.steps.length;
                    }
                })
            },


		}
	}
</script>

<style>

</style>
