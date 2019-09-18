<template>
    <div class="boxbackborder">
        <Menu mode="horizontal" theme="light" active-name="1" @on-select="getMenu">
            <MenuItem name="1">

                科目二
            </MenuItem>
            <MenuItem name="2">

                科目三
            </MenuItem>
        </Menu>

                <Row style="padding-bottom: 16px;">
<!--                    <search-items :parent="v" :label-with="100"></search-items>-->
<!--                    <Button type="info" @click="exportData">-->
<!--                        <Icon type="ios-download-outline"></Icon>-->
<!--                    </Button>-->
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
    import expandRow from './table-expand.vue';
    export default {
        name: 'byxxTable',
        components:{expandRow },
        data() {
            return {
                v:this,
                SpinShow: true,
                pagerUrl:'/api/ptyh/pager',
                tableHeight: 720,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    // {
                    //     type: 'expand',
                    //     width: 50,
                    //     render: (h, params) => {
                    //         return h(expandRow, {
                    //             props: {
                    //                 row: params.row
                    //             }
                    //         })
                    //     }
                    // },
                    {title: "#",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',align:'center'},
                    {title: '身份证号',key:'yhZjhm',align:'center'},
                    {title: '联系电话',key:'yhZh',align:'center'},
                    {title: '代培点',align:'center',render:(h,p)=>{
                                if (this.form.km == 2){
                                    return h ('div',p.row.yhK2SubName)
                                }else {
                                    return h ('div',p.row.yhK3SubName)
                                }
                        }},
                    {title: '费用',align:'center',render:(h,p)=>{
                            if (this.form.km == 2){
                                return h ('div',p.row.yhK2SubJe/100)
                            }else {
                                return h ('div',p.row.yhK3SubJe/100)
                            }
                        }},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            let buttons = [];
                            if (params.row.yhK2Sh == '0' || params.row.yhK3Sh == "0"){
                                buttons.push(
                                    this.util.buildButton(this,h,'success','md-checkmark','审核',()=>{
                                        swal({
                                            title: "审核通过?",
                                            text: "",
                                            icon: "warning",
                                            buttons:['取消','确认'],
                                        }).then((willDelete) => {
                                            if (willDelete) {
                                               this.$http.post('/api/ptyh/shSubFee',{id:params.row.id,km:this.form.km}).then((res)=>{
                                                   if (res.code == 200){
                                                       this.util.initTable(this)
                                                   }else {
                                                       this.$Message.error(res.message)
                                                   }
                                               })
                                            } else {
                                            }
                                        });
                                    }),
                                )
                            }
                            if ((params.row.yhK2Sh === '1' && params.row.yhK2SubSj == '')||(params.row.yhK3Sh === '1' &&  params.row.yhK3SubSj == '')){
                                buttons.push(
                                    h('Tooltip',
                                        {props: {placement: 'top',content: '打款',}},
                                        [
                                            h('Button', {
                                                props: {type: 'success',icon: 'md-checkmark-circle',shape: 'circle',size: 'small'},
                                                style: {margin: '0 8px 0 0'},
                                                on: {click:()=> {
                                                        swal({
                                                            title: "确认付款?",
                                                            text: "",
                                                            icon: "warning",
                                                            buttons:['取消','确认'],
                                                        }).then((willDelete) => {
                                                            if (willDelete) {
                                                                this.$http.post('/api/ptyh/updateSubFee',{id:params.row.id,km:this.form.km}).then((res)=>{
                                                                    if (res.code == 200){
                                                                        this.util.initTable(this)
                                                                    }else {
                                                                        this.$Message.error(res.message)
                                                                    }
                                                                })
                                                            } else {
                                                            }
                                                        });
                                                    }
                                                }
                                            }),
                                        ]
                                    ),
                                    this.util.buildButton(this,h,'info','md-checkmark-circle','手动确认',()=>{
                                        swal({
                                            title: "确认已付款?",
                                            text: "",
                                            icon: "warning",
                                            buttons:['取消','确认'],
                                        }).then((willDelete) => {
                                            if (willDelete) {
                                                this.$http.post('/api/ptyh/confirmSubFee',{id:params.row.id,km:this.form.km}).then((res)=>{
                                                    if (res.code == 200){
                                                        this.util.initTable(this)
                                                    }else {
                                                        this.$Message.error(res.message)
                                                    }
                                                })
                                            } else {
                                            }
                                        });
                                    }),
                                )
                            }
                            return h('div', buttons);
                        }
                    }
                ],
                pageData: [],
                choosedData:[],
                form: {
                    km:2,
                    yhK2SubSjIsNull:'1',
                    yhK2SubIdIsNotNull:'1',
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
                    this.form.km = 2
                    this.form.yhK2SubSjIsNull = '1'
                    this.form.yhK2SubIdIsNotNull='1'
                    delete this.form.yhK3SubIdIsNotNull
                    delete this.form.yhK3SubSjIsNull
                    this.util.initTable(this)
                }
                if(name == "2"){
                    this.form.km = 3
                    this.form.yhK3SubSjIsNull = '1'
                    this.form.yhK3SubIdIsNotNull='1'
                    delete this.form.yhK2SubIdIsNotNull
                    delete this.form.yhK2SubSjIsNull
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
                var v = this
                  this.$http.get('/api/ptyh/getSubFee',{params:v.form}).then((res)=>{
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
