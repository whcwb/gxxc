<style lang="less">
	@import '../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
        			:mask-closable="false" :title="operate+''">
        	<div style="overflow: auto;height: 500px;">
				<Row v-if="showImages">
					<Col span="24" v-for="(item,index) in item.agreementImgList.split(';')" v-if="item != null && item !== ''">
						<img :src="staticPath +'Agreement/'+item">
					</Col>
				</Row>
        	</div>
        	<div slot='footer'>
        		<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
        	</div>
        </Modal>
	</div>
</template>

<script>
	export default {
		name: 'orderForm',
		data() {
			return {
			    v:this,
                operate:'查看协议',
				showModal: true,
				readonly: false,
                staticPath:this.apis.STATIC_PATH,
				formItem: {
				},
                ruleInline:{
				},
				item:'',
				showImages:false
			}
		},
		created(){
		    this.item = this.$parent.choosedItem;
		    this.showImages = true;
		},
		methods: {
		}
	}
</script>

<style>

</style>
