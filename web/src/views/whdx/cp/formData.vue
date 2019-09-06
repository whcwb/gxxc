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
					<Row v-if="showValidCode">
						<Col span="12">
							<FormItem prop='validCode1' label='验证码1'>
								<Input v-model="validCode1" placeholder="验证码1"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='validCode2' label='验证码2'>
								<Input v-model="validCode2" placeholder="验证码2"></Input>
							</FormItem>
						</Col>
					</Row>
        		</Form>
        	</div>
        	<div slot='footer'>
        		<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
        		<Button v-if="!showValidCode" type="primary" @click="v.save()">确定</Button>
        		<Button v-if="showValidCode" type="primary" @click="v.valid()">验证</Button>
        	</div>
        </Modal>
	</div>
</template>

<script>
	import formItems from '../components/formItems'
	export default {
		name: 'cpForm',
		components:{formItems},
		data() {
			return {
			    v:this,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
                    cpJl:0,
                    cpYjyj:0,
                    cpRjyj:0,
					cpXx:'购买此套餐即可享受学车服务及平台会员推荐服务'
				},
                formInputs:[
                    {label:'费用名称',prop:'cpMc',disabled:true},
                    {label:'费用类型',prop:'cpType',dict:'ZDCLK0063'},
                    {label:'费用总金额',prop:'cpJl',type:'number',append:'元'},
                    {label:'是否分佣',prop:'cpYj',dict:'ZDCLK0064'},
                    {label:'一级佣金',prop:'cpYjyj',type:'number',append:'元'},
                    {label:'二级佣金',prop:'cpRjyj',type:'number',append:'元'},
                ],
                ruleInline:{
				},
				showValidCode:false,
				validCode1:'',
				validCode2:'',
				cpId:'',
			}
		},
		created(){
            this.util.initFormModal(this);
            this.formItem.cpXx = this.formItem.cpType === '1' ? '购买此套餐即可享受学车服务及平台会员推荐服务' : '购买此套餐即可享受平台会员推荐服务'
            this.formItem.cpJl = parseFloat(this.formItem.cpJl) / 100;
            this.formItem.cpYjyj = parseFloat(this.formItem.cpYjyj) / 100;
            this.formItem.cpRjyj = parseFloat(this.formItem.cpRjyj) / 100;
		},
		methods: {
            save(){
                let v= this;
                let p = JSON.parse(JSON.stringify(this.formItem));
                p.cpJl = parseFloat(p.cpJl) * 100;
                p.cpYjyj = parseFloat(p.cpYjyj) * 100;
                p.cpRjyj = parseFloat(p.cpRjyj) * 100;
                this.$http.post(this.apis.cp.ADD,p).then((res)=>{
                    if (res.code === 200){
                        this.cpId = res.result;
                        v.$Message.success("请填写短信验证码,验证码有效期为一天");
                        this.showValidCode = true;
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            valid(){
                let v= this;
                let p = {
                    cpId:this.cpId,
                    code1:this.validCode1,
                    code2:this.validCode2,
				};
                this.$http.post(this.apis.cp.yzcpCode,p).then((res)=>{
                    if (res.code === 200){
                        v.$Message.success(res.message);
                        v.util.getPageData(v.$parent)
                        v.$parent.componentName = ''
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
		}
	}
</script>

<style>

</style>
