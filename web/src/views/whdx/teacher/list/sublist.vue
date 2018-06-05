<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Modal v-model="showModal" width='1200' :closable='false' :mask-closable="false" title="已分配学员">
			<div style="overflow: auto;height: 500px;">
				<Row style="padding-bottom: 16px;">
					<search-items :parent="v"  ></search-items>
					<Button type="primary" @click="v.util.getPageData(v)">
						<Icon type="search"></Icon>
					</Button>
				</Row>
				<Row style="position: relative;">
					<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
				</Row>
				<Row class="margin-top-10 pageSty">
					<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
						  @on-change='pageChange'></Page>
				</Row>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    import searchItems from '../../components/searchItems'
    export default {
        name: 'byxxTable',
        components: {searchItems},
        data() {
            return {
                showModal: true,
                v:this,
                SpinShow: true,
                apiRoot:this.apis.user,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '类型',key:'yhLx'},
                    {title: '缴费状态',key:'ddSfjx',dict:'jfzt'},
                    {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz'},
                    {title: '认证状态',key:'yhZt',dict:'rzzt'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','card','详情',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'detail'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
                    yhSjid:'',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
				item:{},
            }
        },
        created() {
            this.item = this.$parent.choosedItem;
            this.form.yhSjid = this.item.id;
            this.util.initTable(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
