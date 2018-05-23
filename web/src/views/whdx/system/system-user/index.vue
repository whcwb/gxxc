<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--用户管理-->
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<div v-for="s in searchItems" style="display: inline-block">
				<label class="searchLabel">{{s.label}}:</label>
				<Input v-model="findMess[s.formKey]" :placeholder="'请输入'+s.label" style="width: 200px"></Input>
			</div>
			<Button type="primary" @click="findMessList">
				<Icon type="search"></Icon>
			</Button>
			<Button type="primary" @click="AddDataList">
				<Icon type="plus-round"></Icon>
			</Button>
		</Row>
			<Row style="position: relative;">
				<Table
						size='large'
						:height="tableHeight"
						:row-class-name="rowClassName"
						:columns="tableTiT"
						:data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		<component
			:is="compName"
			:usermes="usermes"
			:usermesType="userMesType"
			@listF='listF'></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	import newmess from './comp/newmes.vue'
	import changemes from './comp/changmes.vue'
    import addlistfileImg from './comp/addlistfileImg.vue'
	export default {
		name: 'char',
		components: {
			newmess,
			changemes,
            addlistfileImg
		},
		mixins: [mixins],
		data() {
			return {
                searchItems:[
                    {label:'用户姓名',formKey:'xmLike'},
                ],
				//tab高度
				tableHeight: 220,
				//动态组建
				compName: '',
				//动态组建数据
				usermes:{},
				userMesType:true,
				//分页
				//---数据总数
				pageTotal: 2,
				page: {
					//---当前页码
					pageNum: 1,
					//---每页显示条数
					pageSize:8
				},
				tableTiT: [
					{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '帐号',
						align: 'center',
						key: 'zh'
					},
					{
						title: '姓名',
						align: 'center',
						key: 'xm'
					},
                    {
                        title: '类型',
                        align: 'center',
                        key: 'lx',
                        render:(h, params)=>{
                            let val = '';
                            if (params.row.lx === '20'){
                                val = '工作人员';
							}else if (params.row.lx === '30'){
                                val = '教练员';
                            }else{
                                val = '管理员';
							}

                            return h('div',val)
                        }
                    },
                    {
                        title: '手机号',
                        width: 160,
                        align: 'center',
                        key: 'sjh'
                    },
					{
						title: '身份证号',
						align: 'center',
						key: 'zjhm'
					},
					{
						title: '操作',
						key: 'action',
						width: 180,
						align: 'center',
						render: (h, params) => {
							if(params.row.lx==='su'){
								return ''
							}
							return h('div', [
							h('Button', {
									props: {
										type: 'success',
										icon: 'edit',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.userMesType = false
											this.usermes = params.row
											this.compName = 'newmess'
										}
									}
								}),
								h('Tooltip', {
                                    props: {
                                        placement: 'top',
                                        content: '角色分配',
                                    }
                                },
								[h('Button', {
									props: {
										type: 'primary',
										icon: 'navicon-round',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.RootShowF(params.row)

										}
									}
								})
								]),
                                h('Tooltip', {
                                    props: {
                                        placement: 'top',
                                        content: '证件照片查看',
                                    }
                                },
								[h('Button', {
                                    props: {
                                        type: 'info',
                                        icon: 'ios-eye',
                                        shape: 'circle',
                                        size: 'small'
                                    },
                                    style: {
                                        cursor: "pointer",
                                        margin: '0 8px 0 0'
                                    },
                                    on: {
                                        click: () => {
                                            this.showImgFile(params.row)
                                        }
                                    }
                                })]
								),
								h('Button', {
									props: {
										type: 'error',
										icon: 'close',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.listDele(params.row.yhid)
										}
									}
								})
							]);
						}
					}
				],
				tableData: [],
				//收索
//				cjsjInRange:[],
				findMess: {
					sjhLike:'',
					xmLike: '',
					pageNum: 1,
					pageSize:8
				},
				yhlxDict:[],
				yhlxDictCode:'ZDCLK0003'
			}
		},
		watch: {
//			cjsjInRange:function(newQuestion, oldQuestion){
//				this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
//			},
		},
		created() {
            this.util.initTableHeight(this)
            this.getmess()
            this.getDict()
		},
		methods: {
		    getDict(){
                this.yhlxDict = this.dictUtil.getByCode(this,this.yhlxDictCode);
                log(this.yhlxDict);
            },
			enter(mes){
//				log(mes)
//
//				log('页面高度',Math.floor((this.getWindowHeight() - 290)/48))
			},
			getmess(){
				var v = this
				this.$http.get(this.apis.USER.QUERY,{params:v.findMess}).then((res) =>{
//					log(res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
				})
			},
			//权限分配
			RootShowF(val) {
				var v = this
				v.compName = 'changemes'
				this.usermes = val;
            },
            //证件照片查看
            showImgFile(val) {
                var v = this
                v.compName = 'addlistfileImg';
                this.usermes = val;
            },
			//收索事件
			findMessList() {
				var v = this
				this.$http.get(this.apis.USER.QUERY,{params:v.findMess}).then((res) =>{
//					log(res)
					v.tableData = res.page.list
				})
			},
			//添加新用户信息
			AddDataList() {
				var v = this
				v.compName = 'newmess'
				v.userMesType = true
				this.usermes = null
			},
			listF(res){
				this.getmess()
				this.compName = ''
			},
			//删除数据
			listDele(id){
				this.util.del(this,this.apis.USER.DELE,[id],()=>{
                    this.getmess();
				});
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.findMess.pageNum = event
				v.getmess()
			}
		}
	}
</script>
