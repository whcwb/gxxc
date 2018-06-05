
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
				<search-items :parent="v" :label-with="100"></search-items>
				<Button type="info" @click="exportData">
					<Icon type="ios-download-outline"></Icon>
				</Button>
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
    import audit from './audit.vue'
    import allot from './allot.vue'
	import searchItems from '../../components/searchItems'

    export default {
        name: 'byxxTable',
        components: {formData,searchItems,sublist,allot,audit},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.teacher,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '手机号',key:'yhSjhm',searchKey:'yhSjhmLike'},
                    {title: '所属区域',key:'jlQu',dict:'ZDCLK0060',searchType:'dict'},
                    {title: '认证状态',key:'yhJlsh',dict:'ZDCLK0043',searchType:'dict'},
                    {title: '教练驾龄',key:'jlJl'},
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
                                this.util.buildButton(this,h,'success','person','已分配学员',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'sublist'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
                    yhLx:"2",
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
			exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,账号,缴费状态,是否有驾驶证,认证状态',
					keys:'yhXm,yhZh,ddSfjx,yhSfyjz,yhZt'
				}
				window.open(this.apis.exportData+'?exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
			}
        }
    }
</script>
