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
                    {title: '受理类型',key:'slType',type:'dict',dict:'ZDCLK0071'},
                ],
                pageData: [],
                form: {
                    userId:'',
                    userGrade:'1',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
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
            this.pageData = this.parent.$data.status[0];
            this.util.fillTableColumns(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
