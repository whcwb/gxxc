<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v"></search-items>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
				  @on-change='pageChange'></Page>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
    import formData from './formData.vue'
    import sublist from './sublist.vue'
	import searchItems from '../../components/searchItems'

    export default {
        name: 'byxxTable',
        components: {formData,searchItems,sublist},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.student,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "序号", width: 70, type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '类型',key:'yhLx'},
                    {title: '缴费状态',key:'ddSfjx',dict:'jfzt',searchType:'dict'},
                    {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz',searchType:'dict'},
                    {title: '认证状态',key:'yhZt',dict:'rzzt',searchType:'dict'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','card','详情',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'formData'
                                }),
                                this.util.buildButton(this,h,'info','network','下线',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'sublist'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
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
