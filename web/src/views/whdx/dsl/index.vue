<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <Col span="4">
                <Select v-model="form.sl" @on-change="getpager">
                    <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
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
    import qrsl from "./comp/qrsl"
    export default {
        name: 'byxxTable',
        components:{qrsl},
        data() {
            return {
                cityList: [
                    {
                        value: 0,
                        label: '待受理'
                    },
                    {
                        value: 1,
                        label: '已受理'
                    },
                ],
                v:this,
                SpinShow: true,
                pagerUrl:'/api/ptyh/getDslYh',
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'xm',align:'center'},
                    {title: '身份证号码',key:'yhZjhm',searchKey:'idCard',align:'center'},
                    {title: '联系电话',key:'yhZh',searchKey:'phone',align:'center'},
                    {title: '注册时间',key:'cjsj',align:'center'},
                    {title: '套餐',key:'cpmc',align:'center'},
                    {title: '价格',key:'cpje',align:'center',
                        render:(h,p)=>{
                            return p.row.cpje/100
                        }
                    },
                    {title: '是否回访',key:'hfsj',align:'center',
                        render:(h,p)=>{
                          if (p.row.hfsj ==''){
                              return h('div','否')
                          }else {
                              return h('div','是')
                          }

                        }
                    },

                    {
                        title: '操作',
                        key: 'action',
                        width: 180,
                        fixed:'right',
                        render: (h, params) => {
                            if (params.row.yhXySlType == '4'){
                                return h('div',"已受理")
                            }else {
                                return h('div', [
                                    this.util.buildButton(this,h,'success','md-checkmark','确认受理',()=>{
                                        this.getJf(params.row.id,params.row);
                                    }),

                                ]);
                            }

                        }
                    }
                    // {title: '金额',key:'money',render:(h,p)=>{
                    //         return h('div','120元')
                    //     }},
                ],
                pageData: [],
                choosedData:[],
                form: {
                    cond:'',
                    sl:0,
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
                this.getData(this.apis.ksJf.QUERY,yhId,'qrsl',row);
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
                this.util.initTable(this)
            },
        }
    }
</script>
