<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<Select v-model="form.jlZtIn" style="width:100px" @on-change="changeZt">
				<Option v-for="item in jlZtList" :value="item.key" :key="item.key">{{ item.value }}</Option>
			</Select>
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
	import modify from './modify'
    export default {
        name: 'byxxTable',
		components:{formData,modify},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.teacher,
                deleteUrl:this.apis.removeUserInfo,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
				jlZtList: [{key:'0,1',value:'全部'},{key:'0',value:'在职'},{key:'1',value:'停用'}],
                tableColumns: [
                    {title: "",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '账号',key:'yhSjhm',searchKey:'yhSjhmLike'},
					{title:'证件号码', key: 'yhZjhm',searchKey:'yhZjhmLike'},
					{title:"注册时间", key:'cjsj'},
					{
						title: '状态',
						key: 'jlZt', render: (h, p) => {
							let status = true
							if(p.row.jlZt == '1'){
								status = false
							}
							return h('i-switch', {
								props:{
									value: status,
									size: 'large'
								},
								on: {
									'on-change': val => {
										let jlZt = '0'
										if (!val){
													jlZt = '1'
										}
										 this.changeJlZt(p.row.yhId,jlZt)
									}
								}
							},[
									h('span',{
										slot:'open',
										domProps: {
											innerHTML: '在职'
										}
									}),
									h('span', {
										slot: 'close',
										domProps: {
											innerHTML: '停用'
										}
									})
							])
						}
					},
                    {
                    	title: '培训科目',
						key:'jlType',
						minWidth:200,
						render: (h,p) => {
                    		if (p.row.jlType == ''){
                    			return h('div','-')
							}
                    		let split = p.row.jlType.split(',');
                    		let msg = ''
                    		for (let k in split) {
                    			let m = split[k]
								if (m == 'slzy'){
									msg = msg + ','  +'受理'
								}
								if (m == 'k1'){
									msg += "," + "科一"
								}
								if(m == 'k2'){
									msg += "," + "科二"
								}
								if(m == 'k3'){
									msg += ","  + "科三"
								}
								if(m == 'k4'){
									msg  += "," + "科四"
								}
							}
                    		return h('div',msg.substring(1))
						}
					},
					{
						title: '培训车型',
						key:'jlCx'
					},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','card','详情',()=>{
                                    this.$http.get(this.apis.teacher.getById+params.row.yhId).then((res)=>{
                                        if (res.code == 200 && res.result){
                                            this.choosedItem = res.result
                                            this.componentName = 'modify'
                                        }
                                    })
                                })/*,
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
								}),*/
                            ]);
                        }
                    }
                ],
                pageData: [],
				choosedData:[],
                form: {
                    // yhLxIn:"slzy,k1,k2,k3",
					// showRoles:'true',
                    yhLx:"3",
					jlZtIn:'0,1',
                    byBysjInRange:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.util.initTable(this)
        	// this.getPager()
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
			changeZt(key){
            	this.form.jlZtIn = key
				this.util.initTable(this)
			},
			changeJlZt(yhId, jlzt){
					this.$http.post(this.apis.teacher.updateJlZt, {yhId: yhId, jlZt: jlzt}).then(res  => {
						this.$Message.info(res.message)
						this.util.initTable(this)
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
