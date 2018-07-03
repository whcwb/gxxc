<style lang="less">
	@import '../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
        	<search-items :parent="v"></search-items>
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
        	<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
	</div>
</template>

<script>
	import audit from './audit'
	import confirm from './confirm'
    import batchImport from './batchImport'

    export default {
        name: 'tx',
        components: {confirm,audit,batchImport},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.tx,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'用户名称',key:'yhMc',searchKey:'yhMcLike'},
                    {title:'提现金额',key:'ttJe',render:(h,p)=>{
                            return h('div',parseFloat(p.row.ttJe/100)+'元')
                        }},
                    {title:'提现时间',key:'ttSj'},
                    {title:'审核人',key:'ttShr'},
                    {title:'银行卡号',key:'ttYhkh'},
                    {title:'所属银行',key:'ttKhh'},
                    {title:'提现姓名',key:'txXm'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            let buttons = [];
                            if (params.row.ttShzt === '0'){
                                buttons.push(
                                    this.util.buildButton(this,h,'success','checkmark','审核',()=>{
                                        this.choosedItem = params.row;
                                        this.componentName = 'audit'
                                    }),
                                )
                            }
                            if (params.row.ttShzt === '1' && params.row.ttZt == '0'){
                                buttons.push(
                                    this.util.buildButton(this,h,'success','checkmark-circled','确认',()=>{
                                        this.choosedItem = params.row;
                                        this.componentName = 'confirm'
                                    }),
                                )
                            }
                            return h('div', buttons);
                        }
                    }
                ],
                pageData: [],
                form: {
                    ttShzt:'1',
                    ttZt:'0',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
            exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'用户名称,提现方式,提现金额,提现时间,提现状态,银行卡号,开户行,提现姓名',
                    keys:'yhMc,ttFs,ttJe,ttSj,ttZt,ttYhkh,ttKhh,txXm'
                }
                window.open(this.apis.exportData+"?exportType=tx&ttShzt=1&ttZt=0&cols="+params.cols+"&keys="+params.keys);
            }
        }
    }
</script>
