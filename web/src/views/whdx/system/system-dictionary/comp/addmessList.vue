<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建字典类目">
			<div style="overflow: auto;height: 400px;">
				<Form
					:model='addmess'
					:label-width="100"
					:rules="ruleInline"
					label-position="right">
					<FormItem prop='zddm' label='类目代码：'>
						<Input type="text" v-model="addmess.zddm" placeholder="请填写类目编码..."></Input>
					</FormItem>
					<FormItem prop='zdmc' label='类目名称：'>
						<Input type="text" v-model="addmess.zdmc" placeholder="请填写类目名称..."></Input>
					</FormItem>
					<FormItem label='颜色：'>
						<Row>
							<Col span="8">
								<Select v-model="colorType">
									<Option value="select">选择</Option>
									<Option value="input">自定义</Option>
								</Select>
							</Col>
							<Col span="8">
								<Select v-if="colorType == 'select'" v-model="addmess.by1">
									<Option value=""></Option>
									<Option value="blue">blue</Option>
									<Option value="green">green</Option>
									<Option value="red">red</Option>
									<Option value="yellow">yellow</Option>
								</Select>
							</Col>
							<Col span="8">
								<Input v-if="colorType == 'input'" v-model="addmess.by1"></Input>
							</Col>
							<Col span="8">
								<Tag :color="addmess.by1">颜色预览</Tag>
							</Col>
						</Row>
					</FormItem>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="addDicList">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>

	export default {
		name: '',
		data() {
			return {
				showModal: true,
				mesF: false,
				colorType:'select',
				addmess: {
					zdmc: '',
					zddm: '',
					zdlmdm:''
				},
				ruleInline: {
                  zdmc: [
                      { required: true, message: '请输入类目名称', trigger: 'blur' }
                  ],
                  zddm: [
                      { required: true, message: '请输入类目编码', trigger: 'blur' }
                  ]
              	},
			}
		},
		props:{
			dicListMess:{
//				type:Object,
				type:String,
				default:''
			}
		},
		created(){
			this.addmess.zdlmdm = this.dicListMess
		},
		methods: {
			colse() {
				var v = this
				v.$parent.compName = ''
			},
			addDicList(){
				var v = this
				log('字典数据',v.addmess)
				this.$http.post(this.apis.DICTIONARY_LIST.ADD,v.addmess).then((res) =>{
					log('字典添加',res)
					if(res.code===200){
						v.$parent.getmess()
						v.$Message.success('操作成功');
						v.$parent.compName = ''
					}else{
						v.$Message.error('操作失败!');
					}
				})
			}
		}
	}
</script>

<style>

</style>