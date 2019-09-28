<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <Col span="4">
                <RadioGroup v-model="form.km" type="button" @on-change="getpager">
                    <Radio label="1">科目一</Radio>
                    <Radio label="2">科目二</Radio>
                    <Radio label="3">科目三</Radio>
                </RadioGroup>
            </Col>
            <Col span="20">
                <Input search enter-button placeholder="请输入姓名丶身份证号丶联系电话搜索"
                       clearable
                       v-model="form.cond"
                       @on-enter="getpager"
                       @on-change="getpager"
                />
            </Col>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData" ></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName" :row="row"></component>
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
                pagerUrl:'/api/ptyh/getDjfYh',
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'xm',align:'center'},
                    {title: '身份证号码',key:'yhZjhm',searchKey:'idCard',align:'center'},
                    {title: '联系电话',key:'yhZh',searchKey:'phone',align:'center'},
                    {title: '流水号',key:'yhLsh',align:'center'},
                    {title: '受理时间',key:'slsj'},
                    {title: '受理天数',key:'day',render:(h,p)=>{
                        return h('div',parseInt(p.row.day))
                        }},
                    {title: '约考状态',key:'yhXyYkType',dict:'ykzt'},
                    // {title: '流水号条码',key:'yhLsh',width:280,align:'center',
                    //     render:(h,p)=>{
                    //         return h('div',[
                    //
                    //             h('barcode',
                    //                 {
                    //                     style:{
                    //                         height:'80px'
                    //                     },
                    //                     props:{
                    //                         value:p.row.yhLsh,
                    //                     }
                    //                 }
                    //             )
                    //         ],)
                    //
                    //     }
                    // },
                    {title: '待缴科目',key:'yhXyJfType',align:'center',dict:'ZDCLK0067',searchKey:'dict',render:(h,p)=>{
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
                        fixed:'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-checkmark','缴费',()=>{
                                    this.getJf(params.row.id,params.row);
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
                    km:"1",
                    cond:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                userType:'',
                totalMoney:0,
                row:{}
            }
        },
        created() {
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;
            this.util.initTable(this)
            // this.getPager()
        },
        methods: {
            getData(api,yhId,componentName,row){
                this.$http.get(api,{params:{yhId:yhId,pageSize:1,orderBy:'cjsj desc'}}).then((res)=>{
                    if (res.code === 200 && res.page.list && res.page.list.length > 0){
                        this.choosedItem = res.page.list[0];
                    }else{
                        this.choosedItem = {yhId:yhId};
                    }
                    this.row = row
                    this.componentName = componentName
                })
            },

            getJf(yhId,row){
                this.getData(this.apis.ksJf.QUERY,yhId,'jf',row);
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
            getpager(){
                if (this.form.km == '1'){
                    this.tableColumns.splice(5,2);
                    this.tableColumns.splice(5,0,{title: '受理时间',key:'slsj'});
                    this.tableColumns.splice(6,0,{title: '受理天数',key:'day',render:(h,p)=>{
                            return h('div',parseInt(p.row.day))
                        }});
                    console.log(this.tableColumns);
                    this.util.initTable(this)
                }else {
                    this.tableColumns.splice(5,2);
                    this.tableColumns.splice(5,0,{title: '科一合格时间',key:'slsj'});
                    this.tableColumns.splice(6,0,{title: '科一合格天数',key:'day',render:(h,p)=>{
                            return h('div',parseInt(p.row.day))
                        }});
                    console.log(this.tableColumns);
                    this.util.initTable(this)
                }

            },
        }
    }
</script>
