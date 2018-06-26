<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
				<search-items :parent="v" :label-with="100" :show-create-button="true"></search-items>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionChange"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<pager :parent="v"></pager>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>

	import formData from './formData'
    export default {
        name: 'byxxTable',
		components:{formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.student,
                deleteUrl:this.apis.removeUserInfo,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '类型',key:'yhLx',dict:'ZDCLK0041'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','card','详情',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'formData'
                                }),
                                this.util.buildButton(this,h,'info','close','删除',()=>{
                                    swal({
                                        title: "是否删除数据?",
                                        text: "",
                                        icon: "warning",
                                        buttons:['取消','确认'],
                                    }).then((willDelete) => {
                                        if (willDelete) {
                                            this.deleteUser(params.row.id);
                                        }
                                    });
								}),
                            ]);
                        }
                    }
                ],
                pageData: [],
				choosedData:[],
                form: {
                    yhLxIn:"slzy,k1,k2,k3",
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
            selectionChange(e){
				this.choosedData = e;
			},
			deleteUser(id){
				this.$http.post(this.apis.removeUserInfo,{userId:id}).then((res)=>{
				    if (res.code == 200){
				        this.$Message.success(res.message);
				        this.util.getPageData(this);
					}else{
				        this.$Message.error(res.message);
					}
				})
			},
            allot(){
                if (this.choosedData.length == 0){
                    this.$Message.error("请选择学员")
					return;
				}
				for (let r of this.choosedData){
                    if (r.yhIxySffp == '1'){
                        this.$Message.error("请选择未分配的学员")
                        return;
					}
                    if (r.ddSfjx != '1'){
                        this.$Message.error("请选择已缴费的学员")
                        return;
					}
				}
				this.componentName = "allot";
			},
            pageChange(event) {
                this.util.pageChange(this, event);
            },
			exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,账号,是否有驾驶证,认证状态,教练姓名,教练电话',
					keys:'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
				}
				window.open(this.apis.exportData+'?ddSfjx=1&exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
			}
        }
    }
</script>
