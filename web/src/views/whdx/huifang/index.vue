<template>
    <div class="boxbackborder">
        <Row>
            <Col span="4">
                <Select v-model="form.hf" @on-change="getpager">
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
        <component :is="componentName"></component>
    </div>
</template>

<script>

    // import batchImport from './batchImport'
    import hf from './hf';
    export default {
        name: 'byxxTable',
        components:{hf },
        data() {
            return {
                cityList: [
                    {
                        value: 0,
                        label: '待回访'
                    },
                    {
                        value: 1,
                        label: '已回访'
                    },
                ],
                model4: 0,
                v:this,
                SpinShow: true,
                pagerUrl:'/api/ptyh/getDhfYh',
                tableHeight: 680,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',align:'center'},
                    {title: '身份证号',key:'yhZjhm',align:'center'},
                    {title: '联系电话',key:'yhZh',align:'center'},
                    {title: '注册时间',key:'cjsj',align:'center'},
                    {title: '套餐',key:'cpmc',align:'center'},
                    {title: '价格',key:'cpje',align:'center',
                      render:(h,p)=>{
                        return p.row.cpje/100
                      }
                    },
                    // {title: '代培点',align:'center',render:(h,p)=>{
                    //         if (this.form.km == 2){
                    //             return h ('div',p.row.yhK2SubName)
                    //         }else {
                    //             return h ('div',p.row.yhK3SubName)
                    //         }
                    //     }},
                    // {title: '费用',align:'center',render:(h,p)=>{
                    //         if (this.form.km == 2){
                    //             return h ('div',p.row.yhK2SubJe/100)
                    //         }else {
                    //             return h ('div',p.row.yhK3SubJe/100)
                    //         }
                    //     }},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            if (params.row.hfsj !=''){
                                return h('div',"已回访")
                            }else {
                                let buttons = [];
                                buttons.push(
                                    this.util.buildButton(this,h,'success','md-checkmark','确认回访',()=>{
                                        this.componentName = 'hf';
                                        this.choosedItem = params.row;
                                        // swal({
                                        //     title: "确认已回访?",
                                        //     text: "",
                                        //     icon: "warning",
                                        //     buttons:['取消','确认'],
                                        // }).then((willDelete) => {
                                        //     if (willDelete) {
                                        //         this.$http.post('/api/ptyh/shSubFee',{id:params.row.id,km:this.form.km}).then((res)=>{
                                        //             if (res.code == 200){
                                        //                 this.util.initTable(this)
                                        //             }else {
                                        //                 this.$Message.error(res.message)
                                        //             }
                                        //         })
                                        //     } else {
                                        //     }
                                        // });
                                    }),
                                )
                                return h('div', buttons);
                            }

                        }
                    }
                ],
                pageData: [],
                choosedData:[],
                form: {
                    cond:'',
                    hf:0,
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
            // this.getpager()
        },

        methods: {
            getMenu(name){
                if(name == "1"){
                    this.from.hf = 0
                    this.util.initTable(this)
                }
                if(name == "2"){
                    this.from.hf = 1
                    this.util.initTable(this)
                }
            },
            dk(id){
                let p = {};
                if (id){
                    p.id = id;
                }
                this.$http.post(this.apis.tx.dk,p).then((res)=>{
                    if (res.code === 200){
                        this.util.getPageData(this);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            getpager(){
                this.util.initTable(this)
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
