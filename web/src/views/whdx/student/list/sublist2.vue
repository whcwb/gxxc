<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<!--<Row style="padding-bottom: 16px;">-->
			<!--<search-items :parent="v" ></search-items>-->
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
                                    this.componentName = 'formData'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
                    userGrade:'2',
                    userId:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        props:{
            item:{
                type:Object,
                default:function(){
                    return {};
                }
            }
        },
        created() {
            this.form.userId = this.item.id;
            this.util.initTable(this)

        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
