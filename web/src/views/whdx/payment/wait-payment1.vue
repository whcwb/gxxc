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
		<Row>
			<h2 style="color: red">合计：{{totalMoney}}元</h2>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>

    import batchImport from './batchImport'
    export default {
        name: 'byxxTable',
		components:{batchImport},
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
                        return h('div','120元')
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
				userType:'',
                totalMoney:0,
            }
        },
        created() {
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;
            this.util.initTable(this)
        },
        methods: {
            onGetPageData(){
                this.totalMoney = this.pageData.length * 120;
            },
			exportData(){
				window.open(this.apis.url + this.apis.ksJf.EXPORT+'?km=1');
			},
        }
    }
</script>
