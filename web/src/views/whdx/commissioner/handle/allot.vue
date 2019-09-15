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
               :mask-closable="false" :title="operate+''">
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
				<Button type="default" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="confirm">确定</Button>
			</div>
        </Modal>
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
                operate:'分配',
				showModal: true,
                pagerUrl:this.apis.teacher.QUERY,
				readonly: false,
                form: {
                    jlTypeLike:"slzy",
                    jlZt:'0',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '联系电话',key:'yhSjhm',searchKey:'yhSjhmLike'},
                    {title: '培训车型',key:'jlCx',searchKey:'jlCxLike'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-ribbon','分配',()=>{
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

                },
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
            save(id){
                let userList = this.$parent.choosedData;
                let yhIds = '';
                for (let r of userList){
                    yhIds += r.id+',';
                }
                let params = {
                    yhIds:yhIds,
                    jlid:id,
                    jlType:0
                }
                let v = this;
                this.$http.post(this.apis.student.assignStudents,params).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        v.util.closeDialog(v);
                        v.util.getPageData(v.$parent)
                    }
                })
            }
		}
	}
</script>

<style>

</style>
