<template>
      <div>
            <Modal
                    v-model="showModal"
                    class-name="vertical-center-modal"
                    title="编辑"
                    :closable="false"
                    :mask-closable="false">

                  <Form ref="formInline" :model="item" :rules="ruleInline">
                        <Row :gutter="32">
                              <Col span="12">
                                    <FormItem prop="subName" label="代培点名称">
                                          <Input type="text" v-model="item.subName" placeholder="代培点名称"/>
                                    </FormItem>
                              </Col>
                              <Col span="12">
                                    <FormItem prop="subCode" label="代培点代码">
                                          <Input type="text" v-model="item.subCode"  readonly placeholder="代培点代码"/>
                                    </FormItem>
                              </Col>
                        </Row>
                        <Row :gutter="32">
                              <Col span="12">
                                    <FormItem prop="subPhone" label="负责人姓名">
                                          <Select
                                                  v-model="item.subPhone"
                                                  filterable
                                                  remote
                                                  :remote-method="remoteMethod"
                                                  :loading="loading1"
                                                  @on-change="(val)=>{phone = val}"
                                          >
                                                <Option v-for="(it, index) in userList" :value="it.yhZh" :key="index">{{it.yhXm + " " + it.yhZh}}</Option>
                                          </Select>

                                    </FormItem>
                              </Col>
                              <Col span="12">
                                    <FormItem label="负责人电话">
                                          <Input type="text" v-if="phone == ''" readonly v-model="item.subPhone" placeholder="负责人电话"/>
                                          <Input type="text" v-else readonly v-model="phone" placeholder="负责人电话"/>
                                    </FormItem>
                              </Col>
                        </Row>
                        <Row>
                              <Col span="24">
                                    <FormItem prop="subAddr" label="代培点地址">
                                          <Input type="text" v-model="item.subAddr" placeholder="代培点代码"/>
                                    </FormItem>
                              </Col>
                        </Row>
                  </Form>

                  <div slot="footer" class="box_row rowRight">
                        <Button @click="cancel">取消</Button>
                        <Button type="info" @click="submit">修改</Button>
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
                item: {
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
          created(){
            this.item = this.$parent.itemMess
                console.log(this.$parent.itemMess.subPhone)
          },
        methods: {
            cancel(){
                this.$emit('close')
            },
            submit(){
                this.$refs['formInline'].validate((valid) => {
                    if (valid) {
                        this.$http.post('/api/subschool/update',this.item).then(res=>{
                            if(res.code == 200){
                                  this.$parent.pageChange(1)
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
                    this.$http.get('/api/ptyh/query',
                      {params:{cond: query}}
                    ).then(res => {
                        setTimeout(() => {
                            this.loading1 = false;
                            this.userList = res.result
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