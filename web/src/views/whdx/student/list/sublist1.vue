<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v" group-span="11" label-span="12" input-span="12"></search-items>
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
    import detail from './detail.vue'
	import searchItems from '../../components/searchItems'

    export default {
        name: 'byxxTable',
        components: {detail,searchItems},
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
                    {title: "序号", width: 70, type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '手机号码',key:'yhSjhm',searchKey:'yhSjhmLike'},
                    {title: '类型',key:'yhLx'},
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
            console.log(this.item);
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
