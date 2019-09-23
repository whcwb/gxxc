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
						<Col span="12">
							<FormItem prop='slType' label='受理类型'>
								<Select  filterable clearable  v-model="formItem.slType" placeholder="请选择类型..."  @on-change="typeChange">
									<Option v-for = '(item,index) in v.dictUtil.getByCode(v,"ZDCLK0071")' :value="item.key" :key="item.key">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col v-if="formItem.slType == '1'" span="12">
							<FormItem prop='name' label='医院名称'>
								<Input v-model="formItem.name" placeholder="请输入医院名称"></Input>
							</FormItem>
						</Col>
						<Col v-if="formItem.slType != '1'" span="12">
							<FormItem prop='name' label='驾校名称'>
								<Select  filterable clearable  v-model="formItem.code" placeholder="请选择驾校..." label-in-value @on-change="schoolChange">
									<Option v-for = '(item,index) in schoolList' :value="item.val" :key="item.key" :label="item.val">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12" v-if="showLsh" >
							<FormItem prop='lsh' label='流水号'>
								<Input v-model="formItem.lsh" placeholder="请输入流水号"></Input>
							</FormItem>
						</Col>
						<Col span="12" v-if="showLsh" >
							<FormItem prop='yhCx' label='车型'>
								<Select  filterable clearable  v-model="formItem.yhCx" placeholder="请选择车型..." label-in-value @on-change="cxChange">
									<Option v-for = '(item,index) in cxList' :value="item.val"  :key="item.key" :label="item.val">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12" v-if="showLsh" >
							<FormItem prop='yhYwlx' label='业务类型'>
								<Select  filterable clearable  v-model="formItem.yhYwlx" placeholder="请选择业务类型..." label-in-value @on-change="ywChange">
									<Option v-for = '(item,index) in ywList' :value="item.val"  :key="item.key" :label="item.val">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
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
        		<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
        		<Button type="primary"  @click="v.util.save(v)">确定</Button>
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
                    {label:'学员',prop:'yhId',type:'foreignKey'},
                    {label:'受理时间',prop:'slSj',type:'date'},
                ],
                ruleInline:{
				},
				ywList:[
					{key:'初次申领',val:'初次申领'},{key:'增驾申请',val:'增驾申请'}
				],
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
				cxList:[],
				showLsh:false,
			}
		},
		created(){
		    this.util.initFormModal(this);
		    this.formItem.slSj = new Date().format('yyyy-MM-dd');
		    this.formItem.name = '';
		    this.formItem.code = '';
		   // console.log(this.formItem.slType)
            this.getHandleStatus();
            // this.getSchoolList();
            this.getcxList()
			this.cxList = this.dictUtil.getByCode(this, "chexing")
			this.schoolList = this.dictUtil.getByCode(this, "ZDCLK1017")
			this.$forceUpdate()
		},
		mounted(){
		},
		methods: {
            typeChange(o){
                this.formItem.slType = o;
				this.showLsh = o == '4'
				this.$forceUpdate()
            },
            schoolChange(o){
                this.formItem.name = o.label;
            },
			cxChange(o){
				this.formItem.yhCx = o.label;
			},
			ywChange(o){
				this.formItem.yhYwlx = o.label;
			},
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
			getcxList(){
				this.cxList = this.dictUtil.getValByCode(this,'chexing')
				console.log(this.cxList);
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
                    }
                })
            },
		}
	}
</script>

<style>

</style>
