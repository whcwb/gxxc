<template>
    <div>
        <Modal v-model="showModal" width='900' :closable='false'
               :mask-closable="false" title="学员列表">
            <Table :columns="tableColumns" :data="pageData"></Table>
            <div class="box_row rowRight" style="padding: 8px">
                <Page :total="total" show-total
                      :page-size="params.pageSize"
                      :current="params.pageNum"
                      :page-size-opts="pageSizeOpts"
                      @on-page-size-change='(e)=>{params.pageSize=e;pageChange()}'
                      @on-change="pageChange"
                      show-sizer/>
            </div>
            <div slot='footer'>
                <Button type="primary" @click="close">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: "student",
        data() {
            return {
                total: 0,
                showModal: true,
                tableColumns: [
                    {
                        title: '#',
                        type: 'index'
                    },
                    {
                        title: '姓名',
                        key: 'yhXm'
                    },
                    {
                        title: '身份证号码',
                        key: 'yhZjhm'
                    }, {
                        title: '手机号码',
                        key: 'yhZh'
                    }
                ],
                pageSizeOpts: [10],
                pageData: [],
                params: {
                    pageSize: 10,
                    pageNum: 1,
                    subId: ''
                }
            }
        },
        created() {
            this.params.subId = this.$parent.itemMess.id
            this.getPageData()
        },
        methods: {
            pageChange(e) {
                var v = this
                v.params.pageNum = e
                v.getPageData()
            },
            close() {
                let v = this;
                v.$parent.compName = "";
            },
            getPageData() {
                this.$http.get("/api/ptyh/getSubStudent", {params: this.params}).then(res => {
                    if (res.code === 200) {
                        this.pageData = res.page.list
                        this.total = res.total
                    } else {
                        this.$Message.error(res.message)
                    }
                })
            }
        }

    }
</script>

<style scoped>

</style>