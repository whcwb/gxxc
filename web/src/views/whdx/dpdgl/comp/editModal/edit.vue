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
                            <Input type="text" v-model="item.subCode" readonly placeholder="代培点代码"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem prop="subPhone" label="负责人姓名">
                            <Select
                                    v-model="item.subPhone"
                                    filterable
                                    clearable
                                    remote
                                    :remote-method="remoteMethod"
                                    :loading="loading1"
                                    @on-change="(val)=>{phone = val}"
                            >
                                <Option v-for="(it, index) in userList" :value="it.yhZh" :key="index">{{it.yhXm + " " +
                                    it.yhZh}}
                                </Option>
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
                    <Col span="16">
                        <FormItem prop="subAddr" label="代培点地址">
                            <Input type="text" v-model="item.subAddr" placeholder="代培点代码"/>
                        </FormItem>
                    </Col>
                    <Col span="6" style="float: right">
                        <FormItem prop="subArea" label="代培点区域">
                            <Select filterable clearable v-model="item.subArea" placeholder="代培点区域">
                                <Option v-for="(item,index) in this.dictUtil.getByCode(this,'ZDCLK0060') "
                                        :value="item.val" :key="item.key">{{item.val}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="24">
                        <div class="demo-upload-list" v-for="item in uploadList">
                            <img :src="item">
                            <div class="demo-upload-list-cover">
                                <Icon type="ios-eye-outline" @click.native="handleView(item)"></Icon>
<!--                                <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>-->
                            </div>
                        </div>
                        <Tooltip content="上传封面图" placement="top">
                            <Upload
                                    :multiple="false"
                                    ref="upload"
                                    :on-success="successUpload"
                                    :show-upload-list="false"
                                    :format="['jpg','jpeg','png']"
                                    :max-size="4096"
                                    type="drag"
                                    action="http://www.520xclm.com:8080/biz/upload"
                                    style="display: inline-block;width:58px;">

                                <div style="width: 58px;height:58px;line-height: 58px;">
                                    <Icon type="ios-cloud-upload" size="40"></Icon>

                                </div>

                            </Upload>
                        </Tooltip>
                    </Col>
                </Row>
            </Form>

            <div slot="footer" class="box_row rowRight">
                <Button @click="cancel">取消</Button>
                <Button type="info" @click="submit">修改</Button>
            </div>
            <Modal title="View Image" v-model="visible">
                <img :src="path" v-if="visible" style="width: 100%">
            </Modal>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                staticPath:this.apis.getImgUrl,
                showModal: true,
                phone: '',
                userList: [],
                loading1: false,
                visible: false,
                uploadList: [],
                path: '',
                item: {
                    subName: '',//代培点名称
                    subCode: '',// 代码
                    subPhone: '',//代培点号码 负责人 账号
                    subAddr: '',//代培点地址
                    subImg: '',//代培点图片
                    subArea: ''
                },
                ruleInline: {
                    subName: [
                        {required: true, message: '代培点名称', trigger: 'blur'}
                    ],
                    subCode: [
                        {required: true, message: '代培点代码', trigger: 'blur'}
                    ],
                    subPhone: [
                        {required: true, message: '负责人名称', trigger: 'blur'},
                        {required: true, message: '负责人名称', trigger: 'change'}
                    ],
                    subAddr: [
                        {required: true, message: '代培点地址', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.item = this.$parent.itemMess
            this.uploadList.push(this.item.subImg)
            console.log(this.$parent.itemMess.subPhone)
        },
        methods: {
            handleView(file) {
                this.visible = true;
                this.path = file
            },
            handleRemove(file) {
                this.uploadList.splice(0, 1)
            },
            cancel() {
                this.$emit('close')
            },
            successUpload(res, file) {
                this.uploadList = []
                if (res.code === 200) {
                    this.uploadList.push(this.staticPath + res.message)
                }
            },
            submit() {
                this.$refs['formInline'].validate((valid) => {
                    if (valid) {
                        this.item.subImg = this.uploadList[0]
                        this.$http.post('/api/subschool/update', this.item).then(res => {
                            if (res.code == 200) {
                                this.$parent.pageChange(1)
                                this.cancel()
                            } else {
                                this.$Message.error(res.message);
                            }
                        }).catch(err => {
                            this.$Message.error('Fail!');
                        })
                    }
                })
            },

            remoteMethod(query) {
                console.log(query);
                console.log(query.length);
                if (query != '' && query != ' ') {
                    this.loading1 = true;
                    this.$http.get('/api/ptyh/query',
                        {params: {cond: query}}
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
    .demo-upload-list {
        display: inline-block;
        width: 60px;
        height: 60px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
        margin-right: 4px;
    }

    .demo-upload-list img {
        width: 100%;
        height: 100%;
    }

    .demo-upload-list-cover {
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0, 0, 0, .6);
    }

    .demo-upload-list:hover .demo-upload-list-cover {
        display: block;
    }

    .demo-upload-list-cover i {
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }

</style>
