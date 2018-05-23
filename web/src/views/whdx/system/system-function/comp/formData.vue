<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
			:mask-closable="false" :title="operate+'功能'">
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="form"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col v-for="i in formInputs" :span="i.span ? i.span : 12" >
							<FormItem :prop='i.prop' :label='i.label'>
								<Input type="text" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='状态'>
								<Select filterable clearable  v-model="formItem.zt" placeholder="请填选择状态...">
									<Option v-for = '(item,index) in dicts.zt.items' :value="item.key">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='排序'>
								<!--<Input type="number" v-model="formItem.px" placeholder="请填写排序..."></Input>-->
								<input class="input" type="number" min="0"  v-model="formItem.px"  placeholder="请填写排序..."/>
							</FormItem>
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
	export default {
		name: '',
		data() {
			return {
			    v:this,
			    apiRoot :this.apis.FUNCTION,
                operate:'新建',
				showModal: true,
				readonly: false,
				formItem: {
					px:1,
					zt:'00'
				},
                formInputs:[
                    {label:'功能名称',prop:'gnmc',required:true},
                    {label:'功能代码',prop:'gndm',required:true},
                    {label:'服务代码',prop:'fwdm',required:true},
                    {label:'URL',prop:'url',required:true},
                    {label:'图标',prop:'tb',required:true},
                    {label:'父节点',prop:'fjd'},
                    {label:'跳转地址',prop:'tzdz',required:true},
                    {label:'API 前缀',prop:'apiQz',required:true},
                    {label:'API 后缀',prop:'apiHz'},
                    {label:'备注信息',prop:'bz'},
                ],
                ruleInline:{
                    px: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' },
                    	{ min: 0, message: '请将信息填写完整', trigger: 'blur' }
                    ],
				},
                dicts:{
                    zt:{code:'ZDCLK0007',items:[]}
                }
			}
		},
		created(){
            /**
			 * 初始化表单页面 *必须
             */
		    this.util.initFormModal(this);
            this.util.initDict(this)
		},
		methods: {
            /**
			 * 执行保存操作之前需要删除formItem.children，以免后台接收数据错误（此方法非必须）
             */
            beforeSave(){
                delete this.formItem.children;
			},
		}
	}
</script>

<style>

</style>
