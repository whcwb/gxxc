<style lang="less">
	@import '../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
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
                    {title: '考场名称',key:'schoolName'},
                    {title: '考试时间',key:'ykSj'},
                    {title: '第一次成绩',key:'cj1'},
                    {title: '第二次成绩',key:'cj2'},
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
                this.$http.get(this.apis.ksyk.QUERY,{params:this.form}).then((res)=>{
                    if (res.code === 200 && res.page.list){
                        v.pageData = res.page.list;
                        v.util.fillTableColumns(v)
                    }
                })
            },
        }
    }
</script>
