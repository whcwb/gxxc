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
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionChange"></Table>
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
            selectionChange(e){
				this.choosedData = e;
			},
            allot(){
                if (this.choosedData.length == 0){
                    this.$Message.error("请选择学员")
					return;
				}
				for (let r of this.choosedData){
                    if (r.yhIxySffp == '1'){
                        this.$Message.error("请选择未分配的学员")
                        return;
					}
                    if (r.ddSfjx != '1'){
                        this.$Message.error("请选择已缴费的学员")
                        return;
					}
				}
				this.componentName = allot;
			},
            pageChange(event) {
                this.util.pageChange(this, event);
            },
			exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,身份证号码,手机号,科目',
					keys:'yhXm,yhZjhm,yhZh,km'
				}
				window.open(this.apis.exportData+'?km=1&exportType=ksjf&cols='+params.cols+'&keys='+params.keys);
			},
			getData(api,yhId,componentName){
                this.$http.get(api,{params:{yhId:yhId,pageSize:1,orderBy:'cjsj desc'}}).then((res)=>{
                    if (res.code === 200 && res.page.list && res.page.list.length > 0){
                        this.choosedItem = res.page.list[0];
                    }else{
                        this.choosedItem = {yhId:yhId};
					}
                    this.componentName = componentName
                })
			},
            getSl(yhId){
                this.getData(this.apis.kssl.QUERY,yhId,'sl');
            },
            getJf(yhId){
                this.getData(this.apis.ksJf.QUERY,yhId,'jf');
            },
            getYk(yhId){
                this.getData(this.apis.ksyk.QUERY,yhId,'yk');
            },
            getJg(yhId){
                this.getData(this.apis.ksjg.QUERY,yhId,'jg');
            },
        }
    }
</script>
