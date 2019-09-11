<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="v" :label-with="100"></search-items>
            <Button type="info" @click="exportData">
                <Icon type="ios-download-outline"></Icon>
            </Button>
            <Tooltip content="批量导入" placement="top" :transfer="true">
                <Button type="success" @click="componentName='batchImport'">
                    <Icon type="md-arrow-up"></Icon>
                </Button>
            </Tooltip>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData" ></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    import jf from '../student/status/jf'
    import batchImport from './batchImport'
    export default {
        name: 'byxxTable',
        components:{batchImport,jf},
        data() {
            return {
                v:this,
                SpinShow: true,
                pagerUrl:this.apis.ksJf.DJKSF,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'xm',align:'center'},
                    {title: '身份证号码',key:'yhZjhm',searchKey:'idCard',align:'center'},
                    {title: '手机号',key:'yhZh',searchKey:'phone',align:'center'},
                    {title: '流水号',key:'yhLsh',align:'center'},
                    {title: '流水号条码',key:'yhLsh',width:280,align:'center',
                        render:(h,p)=>{
                            return h('div',[

                                h('barcode',
                                    {
                                        style:{
                                            height:'80px'
                                        },
                                        props:{
                                            value:p.row.yhLsh,
                                        }
                                    }
                                )
                            ],)

                        }
                    },
                    {title: '科目',key:'yhXyJfType',align:'center',dict:'ZDCLK0067',searchKey:'dict',render:(h,p)=>{
                           if(p.row.yhXyJfType == 1){
                               return h('div','科目一')
                           }
                            if(p.row.yhXyJfType == 2){
                                return h('div','科目二')
                            }
                            if(p.row.yhXyJfType == 3){
                                return h('div','科目三')
                            }
                            if(p.row.yhXyJfType == 4){
                                return h('div','科目四')
                            }

                        }},
                    {
                        title: '操作',
                        key: 'action',
                        width: 180,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','logo-yen','缴费',()=>{
                                    this.getJf(params.row.id);
                                }),

                            ]);
                        }
                    }
                    // {title: '金额',key:'money',render:(h,p)=>{
                    //         return h('div','120元')
                    //     }},
                ],
                pageData: [],
                choosedData:[],
                form: {
                    xm:'',
                    phone:'',
                    km:"",
                    idCard:'',
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
            // this.getPager()
        },
        methods: {
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

            getJf(yhId){
                this.getData(this.apis.ksJf.QUERY,yhId,'jf');
            },
            getPager(){
                this.$http.get(this.apis.ksJf.DJKSF,{params:this.form}).then((res)=>{
                    if (res.code == 200){
                        this.pageData = res.page.list
                    }
                })
            },
            onGetPageData(){
                this.totalMoney = this.pageData.length * 120;
            },
            exportData(){
                window.open(this.apis.url + this.apis.ksJf.EXPORT+'?km=1');
            },
        }
    }
</script>
