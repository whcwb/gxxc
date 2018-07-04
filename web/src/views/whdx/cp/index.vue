<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="v" :label-with="100" :show-create-button="false" :show-search-button="false"></search-items>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData"  ></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    import formData from './formData.vue'

    export default {
        name: 'byxxTable',
        components: {formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.cp,
                deleteUrl:this.apis.removeUserInfo,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "",  type: 'index',width:60},
                    {title:'费用名称',key:'cpMc'},
                    {title:'费用类型',key:'cpType',dict:'ZDCLK0063'},
                    {title:'费用总金额',key:'cpJl',type:'number',render:(h,p)=>{
                        return h('div',parseFloat(p.row.cpJl)/100 + '元');
                        }},
                    {title:'是否分佣',key:'cpYj',dict:'ZDCLK0064'},
                    {title:'一级佣金',key:'cpYjyj',type:'number',render:(h,p)=>{
                            return h('div',parseFloat(p.row.cpYjyj)/100 + '元');
                        }},
                    {title:'二级佣金',key:'cpRjyj',type:'number',render:(h,p)=>{
                            return h('div',parseFloat(p.row.cpRjyj)/100 + '元');
                        }},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildEditButton(this,h,params),
                                // this.util.buildDeleteButton(this,h,params.row.id),
                            ]);
                        }
                    }
                ],
                pageData: [],
                choosedData:[],
                form: {
                    cpYx:'1',
                    byBysjInRange:'',
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
