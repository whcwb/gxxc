<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Modal v-model="showModal" width='1200' :closable='false' :mask-closable="false" title="已分配学员">
			<div style="overflow: auto;height: 500px;">
				<!--<Row style="padding-bottom: 16px;">-->
					<!--<search-items :parent="v"  ></search-items>-->
					<!--<Button type="primary" @click="v.util.getPageData(v)">-->
						<!--<Icon type="search"></Icon>-->
					<!--</Button>-->
				<!--</Row>-->
				<Row style="position: relative;">
					<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
				</Row>
				<Row class="margin-top-10 pageSty">
					<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
						  @on-change='pageChange'></Page>
				</Row>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    export default {
        name: 'byxxTable',
        data() {
            return {
                showModal: true,
                v:this,
                SpinShow: true,
				pagerUrl:this.apis.user.getStudentList,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike',
						render:(h,p)=>{
                        	return h('span',p.row.userDetail.yhZh)
						}
					},
                    {title: '类型',key:'yhLx',
                        render:(h,p)=>{
                        	let s = this.dictUtil.getValByCode(this,'ZDCLK0041',p.row.userDetail.yhLx)
                            return h('span',s)
                        }
                    },
                    {title: '缴费状态',key:'ddSfjx',dict:'jfzt',
                        render:(h,p)=>{
                            let s = this.dictUtil.getValByCode(this,'jfzt',p.row.userDetail.ddSfjx)
                            return h('span',s)
                        }
                    },
                    {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz',
                        render:(h,p)=>{
                            let s = this.dictUtil.getValByCode(this,'sfyjsz',p.row.userDetail.yhLx)
                            return h('span',s)
                        }
                    },
                    {title: '认证状态',key:'yhZt',dict:'ZDCLK0043',
                        render:(h,p)=>{
                            let s = this.dictUtil.getValByCode(this,'ZDCLK0043',p.row.userDetail.yhZt)
                            return h('span',s)
                        }
                    },
                    // {
                    //     title: '操作',
                    //     key: 'action',
                    //     width: 120,
                    //     render: (h, params) => {
                    //         return h('div', [
                    //             this.util.buildButton(this,h,'success','card','详情',()=>{
                    //                 this.choosedItem = params.row;
                    //                 this.componentName = 'detail'
                    //             }),
                    //         ]);
                    //     }
                    // }
                ],
                pageData: [],
                form: {
                    yhid:'',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
				item:{},
            }
        },
        created() {
            /**
			 * yhid (用户id) ， xyzt (学员状态 0 完成学业，1科目一，2科目二，3科目三，4科目四) , pageNum,pageSize
             */
            this.item = this.$parent.choosedItem;
            this.form.yhid = this.item.yhId;
            this.util.initTable(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
