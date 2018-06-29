<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
				<search-items :parent="v" :label-with="100"></search-items>
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

    export default {
        name: 'byxxTable',
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.ksJf,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '学员姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '缴费方式',key:'jfFs'},
                    {title: '缴费金额',key:'jfJl',append:'元'},
                    {title: '缴费时间',key:'jfSj'},
                    {title: '缴费科目',key:'kmId',type:'dict',dict:'ZDCLK0067',searchType:'dict'},
                ],
                pageData: [],
				choosedData:[],
                form: {
                    yhLx:"1",
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                    zylx:'1'
                },
				userType:''
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
            selectionChange(e){
				this.choosedData = e;
			},
			exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,账号,是否有驾驶证,认证状态,专员姓名,专员电话',
					keys:'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
				}
				window.open(this.apis.exportData+'?ddSfjx=1&exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
			},
        }
    }
</script>
