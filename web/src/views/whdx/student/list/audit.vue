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
							<label>身份证正面</label>
							<img class="docImg" src="../../../../../static/sfzzm.jpg"/>
						</Col>
						<Col span="12">
							<label>身份证反面</label>
							<img class="docImg" src="../../../../../static/sfzfm.jpg"/>
						</Col>
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
    import formItems from '../../components/formItems'
    export default {
        name: 'byxxForm',
        components:{formItems},
        data() {
            return {
                v:this,
                operate:'保养',
                saveUrl:this.apis.maintain.ADD,
                showModal: true,
                readonly: false,
                formItem: {
                    yhId:''
                },
                formInputs:[
                    {label:'账号',prop:'yhZh',readonly:true},
                    {label:'姓名',prop:'yhXm'},
                    {label:'类型',prop:'yhLx',type:'dict',dict:'ZDCLK0041'},
                    {label:'性别',prop:'yhXb',type:'dict',dict:'ZDCLK0042'},
                    {label:'身份证号码',prop:'yhZjhm'},
                    {label:'状态',prop:'yhZt',type:'dict',dict:'rzzt'},
                    {label:'是否缴费',prop:'ddSfjx',type:'dict',dict:'ZDCLK0045'},
                    {label:'是否缴费',prop:'ddSfjx',type:'dict',dict:'rzzt'},
                ],
                ruleInline:{
                }
            }
        },
        created(){
            this.formItem.yhId = this.$parent.choosedItem.yhId
            this.util.initFormModal(this);
            this.getById();
        },
        methods: {
            getById(){
                this.$http.get(this.apis.student.getById+this.formItem.yhId).then((res)=>{
                    if (res.code === 200){
                        this.formItem = res.result;
                    }
                })
            }
        }
    }
</script>

<style>

</style>
