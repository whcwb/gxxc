<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
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
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>

    export default {
        name: 'byxxTable',
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
                    {title: '单位名称',key:'name'},
                    {title: '受理时间',key:'slSj'},
                    {title: '受理类型',key:'slType',render:(h,p)=>{
                            let s = this.dictUtil.getItemByCode(this,'ZDCLK0071',p.row.slType);
                            return h('div',s.val);
						}},
                ],
                pageData: [],
                form: {
                    yhId:'',
                    pageSize: 1000,
                },
            }
        },
		props:{
          parent:{
              type:Object,
			  default:function(){
			      return {};
			  }
		  }
		},
        created() {
            this.form.yhId = this.parent.formItem.yhId;
            this.getData(this.form.yhId);
        },
        methods: {
            getData(yhId){
                let v = this;
                this.$http.get(this.apis.kssl.QUERY,{params:this.form}).then((res)=>{
                    if (res.code === 200 && res.page.list){
                        v.pageData = res.page.list;
                        v.util.fillTableColumns(v)
                    }
				})
			},
        }
    }
</script>
