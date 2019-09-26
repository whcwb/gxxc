<style type="text/css">

</style>
<template>
    <div>
        <Modal v-model="showModal" width='900' :closable='false'
               :mask-closable="false" title="确认回访">
            <div style="overflow: auto;height: 500px;">
                <Card style="width:100%">
                    <div style="text-align:center">
                        <Form ref="formInline"
                              :model="formItem"
                              :rules="ruleInline"
                              :label-width="100"
                              :styles="{top: '20px'}">
                            <FormItem label="姓名">
                                <Row>
                                    <Col span="11">
                                        <Input  v-model="formItem.yhXm" placeholder="Username" readonly></Input>
                                    </Col>
                                    <Col span="2" style="text-align: center">电话</Col>
                                    <Col span="11">
                                        <Input  v-model="formItem.yhZh" placeholder="Username" readonly></Input>
                                    </Col>
                                </Row>

                            </FormItem>
                            <FormItem label="套餐">
                                <Col span="11">
                                    <Input  v-model="formItem.cpmc" placeholder="Username" readonly></Input>
                                </Col>
                                <Col span="2" style="text-align: center">价格</Col>
                                <Col span="11">
                                    <Input  v-model="formItem.cpje/100" placeholder="Username" readonly></Input>
                                </Col>
                            </FormItem>
                        </Form>
                    </div>
                </Card>
                <Card style="width:100%">
                    <div style="text-align:center;font-weight: 700;font-size: 48px">
                        <h3>{{formItem.yhZh}}</h3>
                    </div>
                </Card>
                <Card style="width:100%">
                    <div style="text-align:center">
                        <h3>回访用语推荐:</h3>
                    </div>
                    <div>
                        <li>亲爱的{{formItem.yhXm}}学员,您好!</li>
                        <h4>恭喜您已缴费成功，成为吉驾无忧的学员。请您保持手机畅通，我们会跟您取得联系，根据您的需要量身定做您的学车流程！祝您学车愉快！</h4>
                    </div>
                </Card>
            </div>
            <div slot='footer'>
                <Button type="default" @click="closeDialog">取消</Button>
                <Button type="primary" @click="save">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'ksJfForm',
        components:{},
        data() {
            return {
                v:this,
                operate:'新建',
                saveUrl:'/api/ptyh/updateHf',
                showModal: true,
                readonly: false,
                formItem: {
                    jfJl:0,
                },
                formInputs:[
                    {label:'姓名',key:'yhXm',prop:'yhId',type:'foreignKey',disabled:true},

                ],
                ruleInline:{
                },
                foreignList:{
                    yhId:{url:this.apis.student.QUERY,key:'id',val:'yhXm',items:[]},
                }
            }
        },
        created(){
            this.formItem = this.$parent.choosedItem
            this.formItem.jfJl = 0;
            let userInfo = sessionStorage.getItem('userInfo');
            this.userType = JSON.parse(userInfo).type;

            this.formItem.jfSj = new Date().format('yyyy-MM-dd');
        },
        methods: {
            closeDialog(){
                this.$parent.componentName = ''
            },
            save(){
                this.$http.post('/api/ptyh/updateHf',{id:this.formItem.id}).then((res)=>{
                    if (res.code == 200){
                        this.$Message.success(res.message)
                        this.closeDialog()
                        this.$parent.getpager()
                    }else {
                        this.$Message.error(res.message)
                    }
                })
            }
        }
    }
</script>

<style>

</style>
