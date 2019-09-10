<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="v" :label-with="100"></search-items>
            <Button type="info" @click="exportData">
                <Icon type="ios-download-outline"></Icon>
            </Button>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData" ></Table>
        </Row>
        <Row>
            <h2 style="color: red">合计：{{totalMoney}}元</h2>
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
                pagerUrl:'/api/ptyh/getSubFee',
                tableHeight: 720,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {
                        type: 'expand',
                        width: 50,
                        render: (h, params) => {
                            return h(expandRow, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },
                    {title: "#",  type: 'index',width:60},
                    {title: '代培点',key:'subSchoolName',align:'center'},
                    {title: '科目',key:'km',align:'center',render: (h, params) => {
                            if (params.row.km == '2'){
                                return "科目二"
                            }else {
                                return "科目三"
                            }

                        }
                      },
                    {title: '人数',key:'total',align:'center'},
                    {title: '合计',key:'zj',align:'center'},
                ],
                pageData: [],
                choosedData:[],
                form: {
                    km:2,
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
