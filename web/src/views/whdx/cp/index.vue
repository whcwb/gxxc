<template>
	<div class="boxbackborder">
        <Row>
            <Form ref="form"
                  :model="formItem1"
                  :rules="ruleInline"
                  :label-width="100"
                  :styles="{top: '20px'}">
                <Row style="text-align: center;margin-top: 16px;margin-bottom: 16px;">
                    <h3>学费</h3>
                </Row>
                <Row>
                    <Col span="6" v-for="(i,index) in formInputs1">
                        <FormItem :key="index" :label="i.label">
                            <Input v-if="!i.dict &&(!i.type || i.type ==='text')" v-model="formItem1[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="!edit1">
                                <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                                <span v-if="i.append" slot="append">{{i.append}}</span>
                            </Input>
                            <Select v-else-if="i.dict || i.type === 'dict'" filterable clearable  v-model="formItem1[i.prop]" :placeholder="'请选择'+i.label+'...'" :disabled="!edit1" >
                                <Option v-for = '(item,index) in v.dictUtil.getByCode(v,i.dict)' :value="item.key" :key="item.key">{{item.val}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row style="text-align: center">
                    <Button v-if="!edit1" type="primary" @click="edit1 = true">编辑</Button>
                    <Button v-else type="primary" @click="save('1')">保存</Button>
                </Row>
            </Form>
            <Form ref="form"
                  :model="formItem2"
                  :rules="ruleInline"
                  :label-width="100"
                  :styles="{top: '20px'}">
                <Row style="text-align: center;margin-top: 16px;margin-bottom: 16px;">
                    <h3>补考费</h3>
                </Row>
                <Row>
                    <Col span="6" v-for="(i,index) in formInputs2">
                        <FormItem :key="index" :label="i.label">
                            <Input v-if="!i.dict &&(!i.type || i.type ==='text')" v-model="formItem2[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="!edit2">
                            <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                            <span v-if="i.append" slot="append">{{i.append}}</span>
                            </Input>
                            <Select v-else-if="i.dict || i.type === 'dict'" filterable clearable  v-model="formItem2[i.prop]" :placeholder="'请选择'+i.label+'...'" :disabled="!edit2" >
                                <Option v-for = '(item,index) in v.dictUtil.getByCode(v,i.dict)' :value="item.key" :key="item.key">{{item.val}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row style="text-align: center">
                    <Button v-if="!edit2" type="primary" @click="edit2 = true">编辑</Button>
                    <Button v-else type="primary" @click="save('2')">保存</Button>
                </Row>
            </Form>
        </Row>
	</div>
</template>

<script>
    import table1 from './table1'
    import table2 from './table2'

    export default {
        name: 'cp',
        components: {table1,table2},
        data() {
            return {
                v:this,
                operate:'新建',
                showModal: true,
                readonly: false,
                edit1:false,
                edit2:false,
                formItem1: {

                },
                formItem2: {

                },
                formInputs1:[
                    {label:'产品名称',prop:'cpMc'},
                    {label:'产品总金额',prop:'cpJl',append:'元'},
                    {label:'是否分佣',prop:'cpYj',dict:'ZDCLK0064'},
                    {label:'一级佣金',prop:'cpYjyj',append:'元'},
                    {label:'二级佣金',prop:'cpRjyj',append:'元'},
                ],
                formInputs2:[
                    {label:'产品名称',prop:'cpMc'},
                    {label:'产品总金额',prop:'cpJl',append:'元'},
                ],
                ruleInline:{
                }
            }
        },
        created() {
            this.getCp("1");
            this.getCp("2");
        },
        methods: {
            save(cpType){
                this.$http.post(this.apis.cp.ADD,this['formItem'+cpType]).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        this['edit'+cpType] = false;
                        this.getCp(cpType);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            getCp(cpType){
                let v = this;
                this.$http.get(this.apis.cp.getcplx,{params:{cpType:cpType}}).then((res)=>{
                    if (res.code === 200){
                        this['formItem'+cpType] = res.result;
                    }
                })
            },
        }
    }
</script>
