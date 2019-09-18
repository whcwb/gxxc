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
			<div style="overflow: auto;height: 500px;">
                <Row style="padding-bottom: 16px;">
                    <search-items :parent="v" :label-with="100"></search-items>
                </Row>
                <Row style="position: relative;">
                    <Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
                </Row>
                <Row class="margin-top-10 pageSty">
                    <Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
                          @on-change='pageChange'></Page>
                </Row>
			</div>
			<div slot='footer'>
				<Button type="default" @click="close">取消</Button>
				<Button type="primary" @click="confirm">确定</Button>
			</div>
	</div>
</template>

<script>
    import fromData from '../../teacher/list/formData'
	export default {
		name: 'byxxForm',
		components:{fromData},
        props:{
            item:{
                type:Object,
                default:function(){
                    return {};
                }
            },
            parent:{
                type:Object,
                default:function(){
                    return {};
                }
            }
        },
		data() {
			return {
			    v:this,
                id:'',
                operate:'分配',
                tableHeight:220,
				showModal: true,
                apiRoot:this.apis.teacher,
				readonly: false,
                form: {
                    yhZt:'1',
                    yhLx:"2",
                    zt:'',
                    jlTypeLike:"k2",
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '电话',key:'jlJjlxrdh',searchKey:'jlJjlxrdhLike'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-person-add','分配',()=>{
                                    this.confirm(params.row.yhId);
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                ruleInline:{
				},
                formItem:{

                }
			}
		},
		created(){
            this.util.initTable(this)
		    this.formItem = this.item
		},
		methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
            confirm(id){
                swal({
                    title: "确认分配?",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.save(id);
                    } else {
                    }
                });
			},
            save(id,a){
                let v = this;
                let jl = {
                    id:v.formItem.id,
                    jlId:id,
                    km:2,
                    flag:a
                };
                this.$http.post('/api/ptyh/updateAssignStudent',jl).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        this.close()
                        v.util.getPageData(parent.$parent)
                    }else if(res.code == 555) {

                        jl.flag = '1'
                        console.log(jl.flag);
                        swal({
                            title: "请注意",
                            text: res.message,
                            icon: "warning",
                            buttons:['取消','确认'],
                        }).then((willDelete) => {
                            if (willDelete) {
                               this.save(jl.id,jl.flag)
                            } else {
                            }
                        });
                    }else {
                        this.$Message.error(res.message)
                    }
                })
            },
            close(){
                this.$parent.close()
            }
		}
	}
</script>

<style>

</style>
