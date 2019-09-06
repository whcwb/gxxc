<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="v" :label-with="100"></search-items>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData"
                   @on-selection-change="selectionChange"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    import status from './status.vue'

    export default {
        name: 'byxxTable',
        components: {status},
        data() {
            return {
                v: this,
                SpinShow: true,
                pagerUrl: this.apis.student.status_query,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange: '',
                tableColumns: [
                    {title: "#", type: 'index', width: 60},
                    {title: '姓名', key: 'yhXm', width: 100 ,searchKey: 'yhXmLike'},
                    {title: '联系电话', key: 'yhZh', width: 100},
                    {title: '身份证号码', key: 'yhZjhm', width: 100, searchKey: 'yhZjhmLike'},
                    {title: '受理状态',key:'yhXySlType', width: 90,dict:'ZDCLK0071',searchType:'dict'},
                    {title: '考试状态', key: 'yhXyYkType', dict: 'ykzt', searchType: 'dict'},
                    {
                        title: '科目一', key: 'km1', render: (h, p) => {
                            return this.buildRow(h,p.row.zy1,p.row.km1);
                        }
                    },
                    {
                        title: '科目二', key: 'km2', render: (h, p) => {
                            return this.buildRow(h,p.row.zy2,p.row.km2);
                        }
                    },
                    {
                        title: '科目三', key: 'km3', render: (h, p) => {
                            return this.buildRow(h,p.row.zy3,p.row.km3);
                        }
                    },
                    {
                        title: '科目四', key: 'km1', render: (h, p) => {
                            return this.buildRow(h,p.row.zy4,p.row.km4);
                        }
                    },
                    {
                        title: '详情',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-card','学习进度',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'status'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                choosedData: [],
                form: {
                    yhXySlType: '4',
                    yhLx: "1",
                    byBysjInRange: '',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                    zylx: '1'
                },
                userType: ''
            }
        },
        created() {
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;
            this.util.initTable(this)
        },
        methods: {
            buildRow(h,z,k){
                let zy = '';
                if (z) {
                    zy += '教练：' + z.yhXm;
                }
                let yk = '';
                let cj = '';
                if (k) {
                    let km = k;
                    yk += km.ykSj+','+km.schoolName;
                    if (km.cj1){
                        yk += ','+km.cj1+'分';
                    }
                    if (km.cj2){
                        yk += ','+km.cj2+'分';
                    }
                }
                return h('div', {},[h('div',{width:'100%'},zy),h('div',yk)]);
            },
            selectionChange(e) {
                this.choosedData = e;
            },
            allot() {
                if (this.choosedData.length == 0) {
                    this.$Message.error("请选择学员")
                    return;
                }
                for (let r of this.choosedData) {
                    if (r.yhIxySffp == '1') {
                        this.$Message.error("请选择未分配的学员")
                        return;
                    }
                    if (r.ddSfjx != '1') {
                        this.$Message.error("请选择已缴费的学员")
                        return;
                    }
                }
                this.componentName = allot;
            },
            pageChange(event) {
                this.util.pageChange(this, event);
            },
            exportData() {
                let params = {
                    exportType: 'ptyh',
                    cols: '姓名,账号,是否有驾驶证,认证状态,教练姓名,教练电话',
                    keys: 'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
                }
                window.open(this.apis.exportData + '?ddSfjx=1&exportType=' + params.exportType + "&cols=" + params.cols + "&keys=" + params.keys);
            },
            getData(api, yhId, componentName) {
                this.$http.get(api, {params: {yhId: yhId, pageSize: 1, orderBy: 'cjsj desc'}}).then((res) => {
                    if (res.code === 200 && res.page.list && res.page.list.length > 0) {
                        this.choosedItem = res.page.list[0];
                    } else {
                        this.choosedItem = {yhId: yhId};
                    }
                    this.componentName = componentName
                })
            },
            getSl(yhId) {
                this.getData(this.apis.kssl.QUERY, yhId, 'sl');
            },
            getJf(yhId) {
                this.getData(this.apis.ksJf.QUERY, yhId, 'jf');
            },
            getYk(yhId) {
                this.getData(this.apis.ksyk.QUERY, yhId, 'yk');
            },
            getJg(yhId) {
                this.getData(this.apis.ksjg.QUERY, yhId, 'jg');
            },
        }
    }
</script>
