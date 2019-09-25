<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<Col span="2">
				<Button type="info" @click="allot" >
					<Icon type="md-person" size="24"></Icon>
				</Button>
			</Col>
			<Col span="22">
				<Input search enter-button placeholder="请输入姓名丶身份证号丶联系电话搜索"
					   clearable
					   v-model="form.cond"
					   @on-enter="getpager"
					   @on-change="getpager"
				/>
			</Col>
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
    import allot from './allot.vue'
    import formData from './formData.vue'

    export default {
        name: 'byxxTable',
        components: {allot,formData},
        data() {
            return {
                v:this,
                SpinShow: true,
				pagerUrl:'/api/ptyh/getDfpYh',
                apiRoot:this.apis.student,
                deleteUrl:this.apis.removeUserInfo,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "",  type: 'selection',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '身份证号',key:'yhZjhm'},
                    {title: '联系电话',key:'yhZh',searchKey:'yhZhLike'},
                    {title: '科一考试时间',key:'kssj',searchKey:'yhZhLike'},
                    {title: '科一考试成绩',key:'yhZh',searchKey:'yhZhLike',
					  render:(h,p)=>{
                    	if (p.row.hg == '1'){
                    		return h('tag','合格')
						}else {
							return h('tag','不合格')
						}
					  }
					},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
						fixed:'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-person','分配教练',()=>{
                                    this.choosedItem = params.row;
                                    this.componentName = 'formData'
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
				choosedData:[],
                form: {
                    km:"2",
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
				this.componentName = "allot";
			},
            pageChange(event) {
                this.util.pageChange(this, event);
            },
			exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,联系电话,是否有驾驶证,认证状态,教练姓名,教练电话',
					keys:'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
				}
				window.open(this.apis.exportData+'?ddSfjx=1&exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
			},
			getpager(){
				this.util.initTable(this)
			},
        }
    }
</script>
