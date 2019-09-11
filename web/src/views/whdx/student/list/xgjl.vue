<style lang="less">
    @import '../../../../styles/common.less';
    .docImg{
        width: 100%;
        padding: 10px;
    }
</style>
<style type="text/css">

</style>
<template>
    <div>
        <Modal v-model="showModal" width='900' :closable='false'
               :mask-closable="false" :title="operate+''"
               @on-cancel="close"
        >
            <div style="overflow: auto;height: 500px;">
                <Tabs>
                    <Tab-pane  label="科二教练" icon="ios-upload-outline">
                        <allot3 :item="item" :parent="v"></allot3>
                    </Tab-pane>
                    <Tab-pane label="科三教练" icon="ios-upload-outline">
                        <allot4 :item="item" :parent="v"></allot4>
                    </Tab-pane>
                </Tabs>
            </div>
        </Modal>
    </div>
</template>

<script>
    import allot1 from './allot1'
    import allot2 from './allot2'
    import allot3 from './allot3'
    import allot4 from './allot4'
    export default {
        name: 'byxxForm',
        components:{allot1,allot2,allot3,allot4},
        props:{

        },
        data() {
            return {
                v:this,
                id:'',
                operate:'修改教练',
                showModal: true,
                apiRoot:this.apis.teacher,
                readonly: false,
                form: {
                    yhZt:'1',
                    yhLx:"2",
                    zt:'',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                choosedData:[],
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','ribbon-b','分配',()=>{
                                    this.confirm(params.row.id);
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                ruleInline:{
                },
                item:{
                    id:''
                },
                state:0,
            }
        },
        created(){
            this.item.id = this.$parent.choosedItem.id;
            // alert(this.id)
            // console.log('created');
            this.util.initTable(this)
            this.choosedData = this.$parent.choosedData
            this.getState();
        },
        mounted(){
            console.log('mounted');
        },
        methods: {
            close(){
                this.$parent.componentName = ''
            },
            getState(){

                this.state = 2;
            }
        }
    }
</script>

<style>

</style>
