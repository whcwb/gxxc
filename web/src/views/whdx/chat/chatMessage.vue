<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <Input v-model="searchVal" placeholder="" style="width: 300px"/>
            <Button type="primary" @click="getData">
                <Icon type="search"></Icon>
            </Button>
        </Row>
        <Row style="position: relative;">
            <Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <!--<pager :parent="v"></pager>-->
            <Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator @on-change='(e)=>changeTab(e)'></Page>
        </Row>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import formData from './formData'

    export default {
        name: "chatMessage",
        components:{formData},
        data() {
            return {
                tableHeight: '200',
                v: this,
                componentName:'',
                SpinShow: true,
                apiRoot: this.apis.chat,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title: '成交时间', key: 'cjsj'},
                    {title: '用户姓名', key: 'userName'},
                    {title: '聊天内容', key: 'content'},
                    {
                        title: '回复情况',
                        key: 'type',
                        render: (h, params) => {
                            let type = params.row.type
                            return h('span', type === '0' ? '未回复' : '已回复');
                        },
                        filters: [
                            {
                                label: '未回复',
                                value: 1
                            },
                            {
                                label: '已回复',
                                value: 2
                            }
                        ],
                        filterMultiple: false,
                        filterMethod (value, row) {
                            if (value === 1) {
                                return row.type == '0';
                            } else if (value === 2) {
                                return row.type == '1';
                            }
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildEditButton(this, h, params),
                            ]);
                        }
                    }
                ],
                pageData: [],
                choosedItem: null,
                searchVal: '',
                pageParam: {pageNum: 1},
                form: {
                    total: 0,
                    pageNum: 1,
                    pageSize: 8
                }
            }
        },
        created() {
            // this.util.initTable(this)
            this.util.initPageSize(this);
            this.util.initTableHeight(this);
            this.util.fillTableColumns(this)
            this.getData()
        },
        methods: {
            getData() {
                let userName=this.searchVal==''?'':`&userName=${this.searchVal}`
                this.$http.get(`${this.apiRoot.QUERY}?pageNum=${this.form.pageNum}&pageSize=${this.form.pageSize}`+userName).then((res) => {
                    if (res.code === 200) {
                        this.pageData = res.page.list
                        this.form.total = res.page.total
                    }
                })
            },
            changeTab(e){
                this.form.pageNum = e
                this.getData()
            }
        }
    }
</script>

<style scoped>

</style>
