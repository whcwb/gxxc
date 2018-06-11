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
                    <form-items :parent="v" :parentFormInputs="formInputs1"></form-items>
                </Row>
                <Row style="text-align: center">
                    <Button v-if="!edit1" type="primary" @click="edit1 = true">编辑</Button>
                    <Button v-else type="primary" @click="save('1')">保存</Button>
                </Row>
            </Form>
        </Row>
	</div>
</template>

<script>
    import formItems from '../components/formItems'
    import table1 from './table1'
    import table2 from './table2'

    export default {
        name: 'cp',
        components: {table1,table2,formItems},
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
                    {label:'费用名称',prop:'cpMc',span:6},
                    {label:'费用总金额',prop:'cpJl',append:'元',span:6},
                    {label:'是否分佣',prop:'cpYj',dict:'ZDCLK0064',span:6},
                    {label:'一级佣金',prop:'cpYjyj',append:'元',span:6},
                    {label:'二级佣金',prop:'cpRjyj',append:'元',span:6},
                ],
                formInputs2:[
                    {label:'费用名称',prop:'cpMc',span:6},
                    {label:'费用总金额',prop:'cpJl',append:'元',span:6},
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
