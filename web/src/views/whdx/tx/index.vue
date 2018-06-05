<style lang="less">
	@import '../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
        	<search-items :parent="v"></search-items>
        </Row>
        <Row style="position: relative;">
        	<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
        	<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator @on-change='(e)=>{v.util.pageChange(v, e)}'></Page>
        </Row>
        <component :is="componentName"></component>
	</div>
</template>

<script>
	import searchItems from '../components/searchItems'
	import audit from './audit'
	import confirm from './confirm'

    export default {
        name: 'tx',
        components: {searchItems,confirm,audit},
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
                    {title:'佣金明细',key:'yjId',searchKey:'yjIdLike'},
                    {title:'提现方式',key:'ttFs',dict:'ZDCLK0047',searchType:'dict'},
                    {title:'提现金额',key:'ttJe',render:(h,p)=>{
                            return h('div',parseFloat(p.row.ttJe/100)+'元')
                        }},
                    {title:'提现时间',key:'ttSj',searchKey:'ttSjLike'},
                    {title:'提现状态',key:'ttZt',dict:'ZDCLK0048',searchType:'dict'},
                    {title:'红包发送的次数',key:'ttHbcs'},
                    {title:'提现红包凭证',key:'ttHbpz'},
                    {title:'审核人',key:'ttShr',searchKey:'ttShrLike'},
                    {title:'提现审核状态',key:'ttShzt',dict:'ZDCLK0049',searchType:'dict'},
                    {title:'银行卡号',key:'ttYhkh',searchKey:'ttYhkhLike'},
                    {title:'开户行',key:'ttKhh',searchKey:'ttKhhLike'},
                    {title:'提现姓名',key:'ttXm',searchKey:'ttXmLike'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        fixed: 'right',
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
        }
    }
</script>
