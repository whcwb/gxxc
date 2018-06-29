<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v" :label-with="100"></search-items>
			<Button type="info" @click="exportData">
				<Icon type="ios-download-outline"></Icon>
			</Button>
			<Tooltip content="批量导入" placement="top">
				<Button type="success" @click="componentName='batchImport'">
					<Icon type="arrow-return-left"></Icon>
				</Button>
			</Tooltip>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData" ></Table>
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
                pagerUrl:this.apis.ksJf.DJF,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '身份证号码',key:'yhZjhm',searchKey:'yhZhLike'},
                    {title: '手机号',key:'yhZh',searchKey:'yhZh'},
                    {title: '科目',key:'km',render:(h,p)=>{
                        return h('div','1')
						}},
                    {title: '金额',key:'money',render:(h,p)=>{
                        return h('div','150元')
						}},
                ],
                pageData: [],
				choosedData:[],
                form: {
                    km:1,
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
				userType:''
            }
        },
        created() {
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;
            this.util.initTable(this)
        },
        methods: {
            onGetPageData(){
              let totalMoney = this.pageData.length * 150;
              let totalRow = {yhXm:'合计'};
              this.pageData.push(totalRow);
                console.log(this.pageData);
            },
			exportData(){
				window.open(this.apis.ksjf.EXPORT);
			},
        }
    }
</script>
