<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
				<search-items :parent="v" :label-with="100"></search-items>
				<Button type="info" @click="exportData">
					<Icon type="ios-download-outline"></Icon>
				</Button>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionChange"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<pager :parent="v"></pager>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
    import formData from './formData.vue'
    import audit from './audit.vue'

    export default {
        name: 'byxxTable',
        components: {formData,audit},
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
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '缴费状态',key:'ddSfjx',dict:'jfzt',searchType:'dict'},
                    {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz',searchType:'dict'},
                    {title: '认证状态',key:'yhZt',dict:'ZDCLK0043',searchType:'dict'},
                    {title: '分配状态',key:'yhIxySffp',dict:'fpzt',searchType:'dict'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            let buttons = [];
                            if (params.row.yhZt != '1'){
                                buttons.push(
                                    this.util.buildButton(this,h,'success','ribbon-b','认证',()=>{
                                        this.choosedItem = params.row;
                                        this.componentName = 'audit'
                                    }),
								)
							}
                            // buttons.push(
                            //     this.util.buildButton(this,h,'success','card','详情',()=>{
                            //         this.choosedItem = params.row;
                            //         this.componentName = 'formData'
                            //     }),
                            // )
                            return h('div', buttons);
                        }
                    }
                ],
                pageData: [],
				choosedData:[],
                form: {
                    yhLx:"1",
					yhZt:'0',
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
            selectionChange(e){
				this.choosedData = e;
			},
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
