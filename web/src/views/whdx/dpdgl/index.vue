<template>
    <div class="box_col" style="background-color: #ffffff">
        <Card>
            <div class="box_row colCenter">
                <Input class="topSearch" v-model="params.keyword" search enter-button size="large"
                       placeholder="请输入您要查找的信息（名称、代码、负责人、电话）"
                       @on-search="" style="width: 100%"/>

                <Tooltip content="添加代培点" placement="top" :transfer="true">
                    <Button type="primary" style="margin-left: 14px;transform: translateY(1px)"
                            @click="compName = 'editModal'">
                        <Icon type="md-add" size="24"/>
                    </Button>
                </Tooltip>
            </div>
        </Card>
        <div class="box_col_auto" style="padding: 0 16px">
            <div id="tabHeight" style="height: 100%">
                <Table v-if="tabH!=0" :height="tabH" :columns="tabTit" :data="tabData">
                    <template slot-scope="{ row, index }" slot="event">
                        <div class="box_row rowBetween">
                            <Button type="info" @click="getStudent(row)">
                                <Icon type="ios-people" size="18"/>
                                学员
                            </Button>
                            <Button type="success" @click="bindWeChart(row)">
                                <Icon type="ios-link"/>
                                微信
                            </Button>
                            <Button type="primary">
                                <Icon type="ios-create-outline" size="16"/>
                                编辑
                            </Button>
                            <Button type="error" @click="bindWeChart">
                                <Icon type="ios-trash-outline" size="18"/>
                                删除
                            </Button>
                        </div>
                    </template>
                </Table>
            </div>
        </div>
        <div class="box_row rowRight" style="padding: 8px">
            <Page :total="total" show-total
                  :page-size="params.pageSize"
                  :current="params.pageNum"
                  :page-size-opts="pageSizeOpts"
                  @on-page-size-change='(e)=>{params.pageSize=e;pageChange()}'
                  @on-change="pageChange"
                  show-sizer/>
        </div>

        <component :is="compName" :item="itemMess"
                   @close="compName = ''"></component>
    </div>
</template>

<script>
    import editModal from './comp/editModal'
    import student from './comp/student'
    export default {
        name: "index",
        components: {editModal,student},
        data() {
            return {
                total: 0,
                compName: "",
                itemMess: {},
                tabH: 0,
                tabTit: [
                    // 代码 名称 负责人姓名 手机号码  绑定微信
                    {
                        title: '代培点名称',
                        key: 'subName',
                    },
                    {
                        title: '代培点代码',
                        key: 'subCode',
                    },
                    {
                        title: '负责人姓名',
                        key: 'subFz',
                    },
                    {
                        title: '手机号码',
                        key: 'subPhone',
                    },
                    {
                        title: '操作',
                        key: 'name',
                        fixed: 'right',
                        slot: 'event',
                        align: "center",
                        width: 400,
                    },
                ],
                tabData: [],
                pageSizeOpts: [1,8,10, 20,30, 40, 50],
                params: {
                    keyword: "",
                    pageNum: 1,
                    pageSize: 10
                }
            }
        },
        created() {
            this.getPageData()
        },
        mounted() {
            this.$nextTick(() => {
                this.tabH = this.getDom_H('tabHeight')
            })
        },
        methods: {
            pageChange(e){
              var v = this
              v.params.pageNum = e
              v.getPageData()
            },
            getPageData() {
                this.$http.get("/api/subschool/pager", {params: this.params}).then(res => {
                    if (res.code === 200) {
                        this.tabData = res.page.list;
                        this.total = res.page.total;
                    }else{
                        this.$Message.error(res.message)
                    }
                })
            },
            bindWeChart(row) {
                if(row.subOpenid){
                    this.swal({
                        title: "已绑定微信",
                        text: row.subOpenid
                    })
                }else{
                    this.swal({
                        title: "未绑定微信",
                    })
                }

            },
            getDom_H(id) {
                return document.getElementById(id).offsetHeight
            },
            getStudent(row){
                this.compName = 'student'
                console.log(row);
                this.itemMess = row
            }
        }
    }
</script>

<style lang="less">
    .topSearch {
        input {
            background-color: #f1f1f140;
        }
    }
</style>