<template>
      <div class="box_col" style="background-color: #ffffff">
            <Card>
                  <div class="box_row colCenter">
                        <Input class="topSearch" v-model="params.keyword" search enter-button size="large"
                               placeholder="请输入您要查找的信息（名称、代码、负责人、电话）"
                               @on-search="" style="width: 100%"/>

                        <Tooltip content="添加代培点" placement="top" :transfer="true">
                              <Button type="primary" style="margin-left: 14px;transform: translateY(1px)">
                                    <Icon type="md-add" size="24"/>
                              </Button>
                        </Tooltip>
                  </div>
            </Card>
            <div class="box_col_auto">
                  <div id="tabHeight" style="height: 100%">
                        <Table v-if="tabH!=0" :height="tabH" :columns="tabTit" :data="tabData">
                              <template slot-scope="{ row, index }" slot="event">
                                    <div class="box_row rowBetween">
                                          <Button type="info">
                                                <Icon type="ios-people" size="18"/>
                                                学员
                                          </Button>
                                          <Button type="success" @click="bindWeChart">
                                                <Icon type="ios-link"/>
                                                微信
                                          </Button>
                                    </div>
                              </template>
                        </Table>
                  </div>
            </div>
      </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                tabH: 0,
                tabTit: [
                    // 代码 名称 负责人姓名 手机号码  绑定微信
                    {
                        title: '代培点名称',
                        key: 'name',
                    },
                    {
                        title: '代培点代码',
                        key: 'name',
                    },
                    {
                        title: '负责人姓名',
                        key: 'name',
                    },
                    {
                        title: '手机号码',
                        key: 'name',
                    },
                    {
                        title: '操作',
                        key: 'name',
                        fixed: 'right',
                        slot: 'event',
                        align: "center",
                        width: 200,
                    },
                ],
                tabData: [
                    {
                        name: '12'
                    }
                ],
                params: {
                    keyword: ""
                }
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.tabH = this.getDom_H('tabHeight')
            })
        },
        methods: {
            bindWeChart() {
                this.swal({
                    title: "绑定维信"
                })
            },
            getDom_H(id) {
                return document.getElementById(id).offsetHeight
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