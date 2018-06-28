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
						<Col v-if="currentStep == 0" span="12">
							<FormItem prop='name' label='医院名称'>
								<Input v-model="formItem.name" placeholder="请输入医院名称"></Input>
							</FormItem>
						</Col>
						<Col v-if="currentStep != 0" span="12">
							<FormItem prop='name' label='驾校名称'>
								<Select  filterable clearable  v-model="formItem.code" placeholder="请选择驾校...'">
									<Option v-for = '(item,index) in schoolList' :value="item.schoolShortName" :key="item.schoolCode">{{item.schoolShortName}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12" v-if="currentStep == '3'" >
							<FormItem prop='lsh' label='流水号'>
								<Input v-model="formItem.lsh" placeholder="请输入流水号"></Input>
							</FormItem>
						</Col>
        			</Row>
					<Row>
						<Steps v-if="showSteps" :current="currentStep" size="small">
							<Step v-for="(item,index) in steps"  :title="item.title" :content="item.content" @click.native="clickStep(index)"></Step>
						</Steps>
					</Row>
        		</Form>
        	</div>
        	<div slot='footer'>
        		<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
        		<Button type="primary" v-if="showConfirm" @click="v.util.save(v)">确定</Button>
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
                showSteps:false,
				formItem: {
				},
                formInputs:[
                    {label:'学员',prop:'yhId',type:'foreignKey',disabled:true},
                    {label:'受理时间',prop:'slSj',type:'date'},
                    // {label:'受理类型',prop:'slType',dict:'ZDCLK0071',type:'dict'},
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
		    let yhId = this.formItem.yhId;
		    this.formItem = {};
		    this.formItem.yhId = yhId;
            this.getHandleStatus();
            this.getSchoolList();
		},
		mounted(){
		},
		methods: {
            clickStep(index){
                alert(index);
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
                    }
                })
            },
		}
	}
</script>

<style>

</style>
