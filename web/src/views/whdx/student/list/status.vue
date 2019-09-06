<style lang="less">
	@import '../../../../styles/common.less';
	.docImg{
		width: 100%;
		padding: 10px;
	}
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='1200' :closable='false'
			:mask-closable="false" :title="operate+''">
			<div style="overflow: auto;height: 500px;">
				<Tabs v-if="showTabs">
					<Tab-pane label="受理记录" icon="ios-download-outline">
						<sl-list :parent="v"></sl-list>
					</Tab-pane>
					<Tab-pane label="缴费记录" icon="ios-upload-outline">
                        <jf-list :parent="v"></jf-list>
					</Tab-pane>
					<Tab-pane label="约考记录" icon="ios-upload-outline">
                        <yk-list :parent="v"></yk-list>
					</Tab-pane>
				</Tabs>
			</div>
			<div slot='footer'>
				<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
				<!--<Button type="primary" @click="v.util.save(v)">确定</Button>-->
			</div>
		</Modal>
	</div>
</template>

<script>
	import slList from './slList'
	import ykList from './ykList'
	import jfList from './jfList'
	export default {
		name: 'byxxForm',
		components:{slList,jfList,ykList},
		data() {
			return {
			    v:this,
                operate:'历史记录',
				saveUrl:this.apis.student.CHANGE,
				showModal: true,
				readonly: false,
                files:{
                    cardFront:'',
                    cardBack:'',
                    licenceFront:'',
                    licenceBack:''
                },
				formItem: {
				},
                formInputs:[
                    {label:'账号',prop:'yhZh',disabled:true},
                    {label:'姓名',prop:'yhXm'},
                    {label:'性别',prop:'yhXb',type:'dict',dict:'ZDCLK0042',disabled:true},
                    {label:'身份证号码',prop:'yhZjhm'},
                    {label:'状态',prop:'yhZt',type:'dict',dict:'ZDCLK0043'},
                    {label:'是否缴费',prop:'ddSfjx',type:'dict',dict:'ZDCLK0045'},
                    {label:'受理状态',prop:'yhXySlType',type:'dict',dict:'ZDCLK0071'},
                    {label:'约考状态',prop:'yhXyYkType',type:'dict',dict:'ZDCLK0067'},
                ],
                ruleInline:{
				},
				status:[],
                showTabs:false,
			}
		},
		created(){
		    let id = this.$parent.choosedItem.id;
		    this.getStatus(id);
		},
		methods: {
		    getStatus(id){
                this.$http.post(this.apis.student.getPaymentRecord,{yhid:id}).then((res)=>{
                    if (res.code == 200 && res.result){
                        this.status = res.result;
                        this.showTabs = true;
                    }
				})

			},
		}
	}
</script>

<style>

</style>
