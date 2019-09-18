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
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '缴费方式',key:'jfFs',dict: "JFQD"},
                    {title: '缴费金额',key:'jfJl',dict:'KSFY'},
                    {title: '缴费时间',key:'jfSj'},
                    {title: '缴费科目',key:'kmId',type:'dict',dict:'ZDCLK0067',render:(h,p)=>{
                            let s = this.dictUtil.getItemByCode(this,'ZDCLK0067',p.row.kmId);
                            return h('div',s.val);
                        }},
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
            this.form.yhId = this.parent.formItem.yhId;
            this.getData(this.form.yhId);
        },
        methods: {
            getData(yhId){
                let v = this;
                this.$http.get(this.apis.ksJf.QUERY,{params:this.form}).then((res)=>{
                    if (res.code === 200 && res.page.list){
                        v.pageData = res.page.list;
                        v.util.fillTableColumns(v)
                    }
                })
            },
        }
    }
</script>
