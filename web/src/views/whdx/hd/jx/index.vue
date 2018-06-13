<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v" show-create="true"></search-items>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<pager :parent="v"></pager>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
    import formData from './formData.vue'
	import searchItems from '../../components/searchItems'

    export default {
        name: 'byxxTable',
        components: {formData,searchItems},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.hd,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '标题',key:'hdBt',searchKey:'hdBtLike'},
                    {title: '正文',key:'hdZw'},
                    // {
                    //     title: '操作',
                    //     key: 'action',
                    //     width: 120,
                    //     render: (h, params) => {
                    //         return h('div', [
                    //             this.util.buildButton(this,h,'info','ios-color-wand','保养',()=>{
                    //                 this.choosedItem = params.row;
                    //                 this.componentName = 'formData'
                    //             }),
                    //             this.util.buildButton(this,h,'success','compose','历史记录',()=>{
                    //                 this.choosedItem = params.row;
                    //                 this.componentName = 'history'
                    //             }),
                    //         ]);
                    //     }
                    // }
                ],
                pageData: [],
                form: {
                    hdSx:1,
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
