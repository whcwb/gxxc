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
						<Steps v-if="showSteps" :current="currentStep" size="small">
							<Step v-for="(item,index) in steps"  :title="item.title" :content="item.content" @click.native="clickStep(index)"></Step>
						</Steps>
					</Row>
					<Row>
						<sl-list :parent="v"></sl-list>
					</Row>
        		</Form>
        	</div>
        	<div slot='footer'>
        		<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
        		<!--<Button type="primary" v-if="showConfirm" @click="v.util.save(v)">确定</Button>-->
        	</div>
        </Modal>
	</div>
</template>

<script>
	import slList from './slList'
	export default {
		name: 'ksSlForm',
		components:{slList},
		data() {
			return {
			    v:this,
                operate:'学习进度',
				saveUrl:this.apis.kssl.ADD,
				showModal: true,
				readonly: false,
                showSteps:false,
				formItem: {
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
                ],
                ruleInline:{
				},
                foreignList:{
                    code:{url:this.apis.school.QUERY,key:'schoolCode',val:'schoolName',items:[]},
                    yhId:{url:this.apis.student.QUERY,key:'id',val:'yhXm',items:[]},
                },
				steps:[
					{title:'医院体验',content:'待完成'},
					{title:'入网面签',content:'待完成'},
					{title:'档案采集',content:'待完成'},
					{title:'受理成功',content:'待完成'},
				],
				currentStep:0,
                handleStatus:'',
                handleSteps:0,
				unitName:'',
                showConfirm:false,
				schoolList:[],
			}
		},
		created(){
		    this.util.initFormModal(this);
		    this.formItem.slSj = new Date().format('yyyy-MM-dd');
		    this.formItem.name = '';
		    this.formItem.code = '';
            this.getHandleStatus();
            this.getSchoolList();
		},
		mounted(){
		},
		methods: {
            clickStep(index){
                // alert(index);
            },
		    getSchoolList(){
		        this.$http.get(this.apis.school.QUERY,{params:{pageSize:10000}}).then((res)=>{
		            if (res.code == 200 && res.page.list){
		                this.schoolList = res.page.list;
					}
				})
			},
		    getUnitName(state){
                return state === 0 ? '医院名称' : '驾校名称';
			},
            getHandleStatus(){
                this.$http.get(this.apis.getHandleStatus, {params: {yhId: this.formItem.yhId}}).then((res)=>{
                    if (res.code == 200){
                        this.handleStatus = res.result;
                        this.handleSteps = parseInt(res.message);
                        let c = 0;
                        for (let k in this.handleStatus){
                            c ++;
                            let unitName = this.handleStatus[k].cjsj + ' '+this.handleStatus[k].name;
                            if (k == '4'){
                                unitName += ':'+this.handleStatus[k].lsh
							}
                            this.steps[parseInt(k) - 1] = {title:this.dictUtil.getValByCode(this,'ZDCLK0071',k),content:unitName};
						}
                        this.currentStep = c;
                        this.showSteps = true;
                        this.unitName = this.getUnitName(c);
                        this.showConfirm = c != 4;
                        this.formItem.slType = c+1;
                        console.log(this.formItem.slType);
                    }
                })
            },
		}
	}
</script>

<style>

</style>
