<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
        			:mask-closable="false" title="缴费">
        	<div style="overflow: auto;height: 500px;">
        		<Form ref="form"
        						:model="formItem"
        						:rules="ruleInline"
        						:label-width="100"
        						:styles="{top: '20px'}">
        			<Row>
        				<form-items :parent="v"></form-items>
        			</Row>
					<Row style="text-align:center">
						<barcode :value="lsh" ></barcode>
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
		props:{
			row:{}
		},
		data() {
			return {
			    v:this,
				lsh:'',
                operate:'新建',
                saveUrl:this.apis.ksJf.ADD,
				showModal: true,
				readonly: false,
				formItem: {
                    jfJl:0,
					lsh:'',
					jfFs:1,
				},
                formInputs:[
                    {label:'姓名',prop:'yhId',type:'foreignKey',disabled:true},
                    {label:'缴费时间',prop:'jfSj',type:"date"},
                    {label:'渠道',prop:'jfFs',dict:'JFQD',type:'val'},
                    {label:'科目',prop:'kmId',dict:'ZDCLK0067',excludeDict:['4'],render:(h,p)=>{
							if (p.row.kmId =='1'){
								this.formItem.jfJl = '1'
							}
							else if (p.row.kmId =="2"){
								this.formItem.jfJl = 'k2'
								this.$nextTick()
							}
							else if (p.row.kmId == "3"){
								this.formItem.jfJl = '3'
							}
							else {

							}
						}
					},
                    {label:'金额',prop:'jfJl',append:'元',dict:'KSFY',type:'val'
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
			this.lsh = this.$parent.row.yhLsh
			console.log(this.formItem.lsh,'1122');
			this.util.initFormModal(this);
		    this.formItem.jfJl = 0;
            let userInfo = sessionStorage.getItem('userInfo');
			console.log(userInfo,"info");
			this.userType = JSON.parse(userInfo).type;
            if (this.userType == 'k1' || this.userType == 'k2' || this.userType == 'k3' || this.userType == 'k4'){
                let km = this.userType.charAt(1);
                this.formItem.kmId = km;
                this.formInputs[3].disabled = true;
            }
            this.formItem.jfSj = new Date().format('yyyy-MM-dd');
            this.formItem.jfFs = "支付宝"
            this.formItem.kmId = this.$parent.row.yhXyJfType

            this.$nextTick()
		},
		watch:{
			'formItem.kmId'(val) {
				if (val =='1'){
					this.formItem.jfJl = '120'
				}
				else if (val =='2'){
					this.formItem.jfJl = '150'
					this.$nextTick()
				}
				else if (val =='3'){
					this.formItem.jfJl = '230'
				}
				else {

				}
			}
		},
		methods: {
		}
	}
</script>

<style>

</style>
