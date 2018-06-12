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
						<Col span="24">
							<Col span="4">
								<label>分配状态</label>
							</Col>
							<Col span="20">
								<Select v-model="formItem.yhIxySffp">
									<Option value="0">未分配</Option>
									<Option value="1">已分配</Option>
								</Select>
							</Col>
						</Col>
						<Col span="24" style="margin-top: 16px;">
							<Col span="4">
								<label>备注</label>
							</Col>
							<Col span="20">
								<Input v-model="formItem.yhFpms"></Input>
							</Col>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="confirm">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: 'byxxForm',
		data() {
			return {
			    v:this,
                operate:'分配',
				showModal: true,
				readonly: false,
				formItem: {
                    yhId:'',
                    yhIxySffp:'0'
				},
                ruleInline:{
				}
			}
		},
		created(){
		    this.formItem = this.$parent.choosedItem
		},
		methods: {
            confirm(){
		        let v = this;
                console.log(this.formItem);
                this.$http.post(this.apis.student.updateSffp,this.formItem).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        v.util.closeDialog(v);
                    }
                })
			}
		}
	}
</script>

<style>

</style>
