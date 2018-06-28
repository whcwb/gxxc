<template>
	<div class="boxbackborder">
		<Modal v-model="showModal" width='1200' :closable='false' :mask-closable="false">
			<div style="overflow: auto;height: 500px;">
				<Tabs>
					<Tab-pane label="一级" icon="ios-download-outline">
						<sublist1 :item="item"></sublist1>
					</Tab-pane>
					<Tab-pane label="二级" icon="ios-upload-outline">
						<sublist2 :item="item"></sublist2>
					</Tab-pane>
				</Tabs>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import sublist1 from './sublist1'
	import sublist2 from './sublist2'

    export default {
        name: 'byxxTable',
        components: {sublist1,sublist2},
        data() {
            return {
                v:this,
                showModal: true,
                apiRoot:this.apis.student,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                item: {
                    userId:'',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.item = this.$parent.choosedItem;
            this.util.initTable(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
