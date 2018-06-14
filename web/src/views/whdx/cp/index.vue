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
                    <form-items v-if="showForm" :parent="v" :parentFormInputs="formInputs1" :parentFormItems="formItem1"></form-items>
                </Row>
                <Row style="text-align: center">
                    <Button v-if="readonly" type="primary" @click="readonly = false">编辑</Button>
                    <Button v-else type="primary" @click="save('1')">保存</Button>
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
                readonly: true,
                edit1:false,
                edit2:false,
                showForm:false,
                formItem1: {

                },
                formItem2: {

                },
                formInputs1:[
                    {label:'费用名称',prop:'cpMc',span:6,disabled:!this.edit1},
                    {label:'费用总金额',prop:'cpJl',append:'元',span:6,disabled:!this.edit1,handler:(o)=>{
                            return parseFloat(o/100);
                        }},
                    {label:'是否分佣',prop:'cpYj',dict:'ZDCLK0064',span:6,disabled:!this.edit1},
                    {label:'一级佣金',prop:'cpYjyj',append:'元',span:6,disabled:!this.edit1,handler:(o)=>{
                            return parseFloat(o/100);
                        }},
                    {label:'二级佣金',prop:'cpRjyj',append:'元',span:6,disabled:!this.edit1,handler:(o)=>{
                            return parseFloat(o/100);
                        }},
                ],
                ruleInline:{
                }
            }
        },
        created() {
            this.getCp("1");
            // this.getCp("2");
        },
        methods: {
            save(cpType){
                let p = JSON.parse(JSON.stringify(this.formItem1));
                p.cpJl = parseFloat(p.cpJl * 100);
                p.cpYjyj = parseFloat(p.cpYjyj * 100);
                p.cpRjyj = parseFloat(p.cpRjyj * 100);
                this.$http.post(this.apis.cp.ADD,p).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        this.readonly = true;
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
                        this.showForm = true;
                    }
                })
            },
        }
    }
</script>
