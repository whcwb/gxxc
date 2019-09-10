<template>
      <div>
            <Modal
                    v-model="showModal"
                    class-name="vertical-center-modal"
                    title="新增/编辑"
                    :closable="false"
                    :mask-closable="false">

                  <Form ref="formInline" :model="formInline" :rules="ruleInline">
                        <Row :gutter="32">
                              <Col span="12">
                                    <FormItem prop="subName" label="代培点名称">
                                          <Input type="text" v-model="formInline.subName" placeholder="代培点名称"/>
                                    </FormItem>
                              </Col>
                              <Col span="12">
                                    <FormItem prop="subCode" label="代培点代码">
                                          <Input type="text" v-model="formInline.subCode" placeholder="代培点代码"/>
                                    </FormItem>
                              </Col>
                        </Row>
                        <Row :gutter="32">
                              <Col span="12">
                                    <FormItem prop="subPhone" label="负责人姓名">
                                          <Select
                                                  v-model="formInline.subPhone"
                                                  filterable
                                                  remote
                                                  :remote-method="remoteMethod"
                                                  :loading="loading1"
                                                      @on-change="(val)=>{phone = val}"
                                          >
                                                <Option v-for="(it, index) in userList" :value="it.yhZh" :key="index">{{it.yhXm}}</Option>
                                          </Select>

                                    </FormItem>
                              </Col>
                              <Col span="12">
                                    <FormItem label="负责人电话">
                                          <Input type="text" readonly v-model="phone" placeholder="负责人电话"/>
                                    </FormItem>
                              </Col>
                        </Row>
                        <Row>
                              <Col span="24">
                                    <FormItem prop="subAddr" label="代培点地址">
                                          <Input type="text" v-model="formInline.subAddr" placeholder="代培点代码"/>
                                    </FormItem>
                              </Col>
                        </Row>
                  </Form>

                  <div slot="footer" class="box_row rowRight">
                        <Button @click="cancel">取消</Button>
                        <Button type="info" @click="submit">保存</Button>
                  </div>
            </Modal>
      </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                showModal: true,
                phone: '',
                userList: [],
                loading1: false,
                formInline: {
                    subName: '',//代培点名称
                    subCode: '',// 代码
                    subPhone: '',//代培点号码 负责人 账号
                    subAddr: '',//代培点地址
                    subImg: '',//代培点图片
                },
                ruleInline: {
                    subName: [
                        {required: true, message: '代培点名称', trigger: 'blur'}
                    ],
                    subCode: [
                        {required: true, message: '代培点代码', trigger: 'blur'}
                    ],
                    subPhone:[
                        {required: true, message: '负责名称', trigger: 'blur'},
                        {required: true, message: '负责名称', trigger: 'change'}
                    ],
                    subAddr:[
                        {required: true, message: '代培点地址', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            cancel(){
                this.$emit('close')
            },
            submit(){
                this.$refs['formInline'].validate((valid) => {
                    if (valid) {
                        this.$http.post('/api/subschool/save',this.formInline).then(res=>{
                            if(res.code == 200){
                                this.cancel()
                            }else {
                                this.$Message.error(res.message);
                            }
                        }).catch(err=>{
                            this.$Message.error('Fail!');
                        })
                    }
                })
            },

            remoteMethod(query) {
                console.log(query);
                console.log(query.length);
                if (query != ''&&query != ' ') {
                    this.loading1 = true;
                    this.$http.get('/api/ptyh/pager', {
                        yhXmLike: query,
                        pageNum: 1,
                        pageSize: 9999
                    }).then(res => {
                        setTimeout(() => {
                            this.loading1 = false;
                            this.userList = res.page.list
                        }, 200);
                    }).catch(err => {
                    })
                } else {
                    this.userList = [];
                }
            },
        }
    }
</script>

<style scoped>

</style>