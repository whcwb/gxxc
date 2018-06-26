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
	import jf from './jf'
	import jg from './jg'
	import sl from './sl'
	import yk from './yk'

    export default {
        name: 'byxxTable',
        components: {jf,jg,sl,yk},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.student,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '教练姓名',key:'jlXm'},
                    {title: '教练电话',key:'sjhm'},
                    {
                        title: '操作',
                        key: 'action',
                        render: (h, params) => {
                            let buttons = [];
                            if (this.userType == 'su'){
                                buttons.push(this.util.buildButton(this,h,'success','card','受理',()=>{
                                    this.getSl(params.row.id);
                                }));
                                buttons.push(this.util.buildButton(this,h,'success','social-yen','缴费',()=>{
                                    this.getJf(params.row.id);
                                }));
                                buttons.push(this.util.buildButton(this,h,'success','card','约考',()=>{
                                    this.getYk(params.row.id);
                                }));
							}else if (this.userType == 'slzy'){
                                buttons.push(this.util.buildButton(this,h,'success','card','受理',()=>{
                                    this.getSl(params.row.id);
                                }));
							}else if (this.userType == 'k1' || this.userType == 'k2' ||this.userType == 'k3' ){
                                buttons.push(this.util.buildButton(this,h,'success','social-yen','缴费',()=>{
                                    this.getJf(params.row.id);
                                }));
                                buttons.push(this.util.buildButton(this,h,'success','card','约考',()=>{
                                    this.getYk(params.row.id);
                                }));
							}
                            return h('div', buttons);
                        }
                    }
                ],
                pageData: [],
				choosedData:[],
                form: {
                    ddSfjx:'1',
                    yhLx:"1",
                    byBysjInRange:'',
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
                    cols:'姓名,账号,是否有驾驶证,认证状态,教练姓名,教练电话',
					keys:'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
				}
				window.open(this.apis.exportData+'?ddSfjx=1&exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
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
