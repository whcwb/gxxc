<style lang="less">
    @import '../../../../styles/common.less';

</style>
<!--角色管理-->
<template>
    <div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<div  style="display: inline-block">
				<label class="searchLabel">角色名称:</label>
				<Input v-model="findMess.jsmcLike"
					   placeholder="请输入角色"
					   style="width: 200px"
					   @on-keyup.enter="findMessList()"
					   @on-change="findMessList"></Input>
			</div>
			<Button type="primary" @click="v.util.getPageData(v)">
				<Icon type="search"></Icon>
			</Button>
			<Button type="primary" @click="v.util.add(v)">
				<Icon type="plus-round"></Icon>
			</Button>
		</Row>
			<Row style="position: relative;">
				<Table
						:row-class-name="rowClassName"
						:height="tableHeight"
						:columns="tableTiT"
						:data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal
					  :current=page.pageNum
					  :page-size=page.pageSize
					  show-total
					  show-elevator
					  @on-change='pageChange'></Page>
			</Row>
    	<component
    		:is="compName"
    		:messdata="messdata"
    		:usermesType="userMesType"
    		:Dictionary="Dictionary"
    		@listF='listF'></component>
    </div>
</template>

<script>
	import mixins from '@/mixins'


	import addrole from './comp/addmess.vue'
	import modifyRolePermission from './comp/modifyRolePermission.vue'
	export default {
    	name:'char',
    	mixins:[mixins],
    	components: {
    		addrole,
            modifyRolePermission
		},
        data () {
            return {
            	SpinShow:true,
            	messdata:'',
            	userMesType:true,
            	tableHeight: 220,
            	compName:'',
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:8
            	},
            	//弹层
            	showModal:false,
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
	                {
                        title: '角色编码',
                        width:120,
                        align:'center',
                        key: 'jsId'
                    },
                    {
                        title: '角色名称',
                        align:'center',
                        key: 'jsmc'
                    },
                    {
                        title: '类型',
                        align:'center',
                        key: 'jslx',
                        render:(h,p)=>{
	                     	let val = this.dictUtil.getValByCode(this,this.lmdmDictionary,p.row.jslx)
	    					return h('div',val)
                        }
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'bz'
                    },
                    // {
                    //     title: '创建时间',
                    //     align:'center',
                    //     key: 'cjsj'
                    // },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
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
											this.messdata = params.row
											this.compName = 'addrole'
										}
									}
								}),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        icon:'close',
                                        shape:'circle',
                                        size:'small'
                                    },
                                    style: {
                                        cursor: "pointer",
                                        margin:'0 8px 0 0'
                                    },
                                    on: {
                                        click: () => {
                                        	this.listDele(params.row.jsId)
											this.getmess()
                                        }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                tableData: [
	                {
	                	jsId:'123456',
	                	jsmc:'驾驶员角色',
	                	jslx:'员工',
	                	bz:'角色说明',
	                	cjsj:'2017-05-02 09:10:00'
	                }
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
//              cjsjInRange:[],
                findMess:{
//              	cjsjInRange:'',
                	jsmcLike:'',
                	pageNum:1,
            		pageSize:8
                },
                Dictionary:[],
				lmdmDictionary:'ZDCLK0004'
            }
        },
        watch: {
//			cjsjInRange:function(newQuestion, oldQuestion){
//				this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
//			},
		},
        created(){
            this.util.initTableHeight(this)
            this.getmess()
            this.getLXDic()//字典数据
        },
        mounted(){
        },
        methods: {
        	getLXDic(){
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
            },
        	getmess(){
        		this.$http.get(this.apis.ROLE.QUERY).then((res) =>{
					this.tableData = res.page.list
					this.SpinShow = false;
				})
        	},
        	RootShowF(val) {
				this.messdata = val.row
				this.compName = 'mess'
			},
			//收索事件
        	findMessList(){
        		var v = this
        		v.SpinShow = true;
				this.$http.get(this.apis.ROLE.QUERY,{params:v.findMess}).then((res) =>{
					//log(res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
        	},
			//数据删除
        	listDele(id) {
        		this.util.del(this,this.apis.ROLE.DELE,[id],()=>{
                    this.getmess();
				});
            },
            //添加数据
        	AddDataList(){
        		var v = this
    			v.compName = 'addrole'
    			this.userMesType = true
    			this.messdata = null
            },
            listF(res){
            	this.getmess()
            	this.compName = ''
            },
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
//      		log(v.page)
			}
        }
    }
</script>
