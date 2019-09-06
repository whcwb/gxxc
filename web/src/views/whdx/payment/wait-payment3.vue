<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v" :label-with="100"></search-items>
			<Button type="info" @click="exportData">
				<Icon type="ios-download-outline"></Icon>
			</Button>
			<Tooltip content="批量导入" placement="top">
				<Button type="success" @click="componentName='batchImport'">
					<Icon type="md-arrow-up"></Icon>
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
                    {title: '身份证号码',key:'yhZjhm',searchKey:'yhZhLike',render:(h,p)=>{
                            let s = p.row.yhZjhm;
                            if (!s)return '';
                            s = s.substring(0,6)+'******'+s.substring(12,18);
                            return h('div',s)
                        }},
                    {title: '手机号',key:'yhZh',searchKey:'yhZh',render:(h,p)=>{
                            let s = p.row.yhZh;
                            if (!s)return '';
                            s = s.substring(0,3)+'****'+s.substring(7,11);
                            return h('div',s)
                        }},
                    {title: '科目',key:'km',render:(h,p)=>{
                            return h('div','科目三')
                        }},
                    {title: '金额',key:'money',render:(h,p)=>{
                            return h('div','230元')
                        }},
                ],
                pageData: [],
                choosedData:[],
                form: {
                    km:3,
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
                this.totalMoney = this.pageData.length * 230;
            },
            exportData(){
                window.open(this.apis.url + this.apis.ksJf.EXPORT+'?km=3');
            },
        }
    }
</script>
