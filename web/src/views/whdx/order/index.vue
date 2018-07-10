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
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
	</div>
</template>

<script>
    import showFiles from './showFiles'

    export default {
        name: 'order',
        components:{showFiles},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.order,
                staticPath:this.apis.STATIC_PATH,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title:'订单状态',key:'ddZt',dict:'ZDCLK0037',searchType:'dict'},
                    {title:'支付通道',key:'ddZftd',dict:'ZDCLK0038',searchType:'dict'},
                    {title:'创建时间',key:'cjsj'},
                    {title:'支付时间',key:'ddZfsj'},
                    {title:'支付状态',key:'ddZfzt',dict:'ZDCLK0039',width:150,searchType:'dict'},
                    {title:'支付金额',key:'ddZfje', render:(h,p)=>{
                        return h('div',parseFloat(p.row.ddZfje/100)+'元')
                        }},
                    {title:'订单备注',key:'ddBz'},
                    {title:'处理状态',key:'jobType',dict:'dsrwclzt',searchType:'dict'},
                    {title:'实际支付金额',key:'payMoney',render:(h,p)=>{
                            return h('div',parseFloat(p.row.payMoney/100)+'元')
                        }},
                    {title:'对账状态',key:'billContrastType',dict:'ZDCLK0072',searchType:'dict'},
                    {title:'对账结果描述',key:'billContrastMsg'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        fixed: 'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','eye','查看协议',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'showFiles';
                                }),
                                this.util.buildButton(this,h,'success','arrow-down-a','下载协议',()=>{
                                    if (params.row.agreementPdfList){
                                        window.open(this.staticPath +'Agreement/'+ params.row.agreementPdfList)
                                    }else{
                                        this.$Message.success('暂无协议');
                                    }
                                }),
                            ]);
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
