<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--服务管理-->
<template>
	<div class="boxbackborder box">
		<Row style="padding-bottom: 16px;">
			<div  style="display: inline-block">
				<label class="searchLabel">服务名称:</label>
				<Input v-model="findMess.fwmcLike" placeholder="请输服务名称" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
			</div>
			<Button type="primary" @click="findMessList()">
				<Icon type="search"></Icon>
				<!--查询-->
			</Button>
		</Row>
			<Row style="position: relative;">
				<Table :height="tableHeight" :row-class-name="rowClassName" :columns="tableTiT" :data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		<component
			:is="compName"
			:chmess="chmess"
			:Dictionary="Dictionary"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'


	import addmess from './comp/addmess.vue'
	import mess from './comp/mess.vue'
	export default {
		name: 'char',
		mixins: [mixins],
		components: {
			addmess,
			mess
		},
		data() {
			return {
				SpinShow:true,
				tableHeight: 220,
				compName: '',
				usermes:{},
				userMesType:true,
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize:8
				},
				//弹层
				showModal: false,
				chmess:{},
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '服务名称',
						width: 120,
						align: 'center',
						key: 'fwmc'
					},
					{
						title: '服务代码',
						align: 'center',
						key: 'fwdm'
					},
					{
						title: '状态',
						align: 'center',
						key: 'zt',
                        render:(h,p)=>{
	                     	let val = this.dictUtil.getValByCode(this,this.lmdmDictionary,p.row.zt)
	    					return h('div', [
								h('span',{
									style:{
										fontWeight:900,
										color:p.row.zt=='00'?'#279a3b':'#ed3f14',
									}
								},val)
							]);
                        }
					},
					{
						title: 'API前缀',
						align: 'center',
						key: 'apiQz'
					},
					{
						title: '图标',
						align: 'center',
						key: 'tb',
						render: (h, params) => {
							return h('div', [
								h('Icon', {
									props: {
										type: params.row.tb,
										size: '22'
									},
									on: {
										click: () => {
											//log('数据调试', params)
										}
									}
								})
							]);
						}
					},
					// {
					// 	title: '创建时间',
					// 	width: '100',
					// 	align: 'center',
					// 	key: 'cjsj'
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
											this.compName = 'mess'
											this.chmess = params.row
										}
									}
								}),
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
											this.listDele(params.row.fwId)
										}
									}
								})
							]);
						}
					}
				],
				tableData: [{
						fwmc: '学生服务',
						fwdm: 'C0123654',
						zt: '正常',
						apiQz: '163.160.255.03',
						tb: 'arrow-expand',
						cjr: '李达',
						cjsj: '2017-05-02 09:10:00'
					}
				],
				//收索
//				cjsjInRange: [],
				findMess: {
//					cjsjInRange:[],
					fwmcLike:'',
					pageNum: 1,
					pageSize:8
				},
				Dictionary:[],
				lmdmDictionary:'ZDCLK0006'
			}
		},
//		watch: {
//			cjsjInRange:function(newQuestion, oldQuestion){
//				this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
//			},
//		},
		created() {
            this.util.initTableHeight(this)
            this.getmess()
            this.getLXDic()
		},
		methods: {
			getLXDic(){
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
                log('字典',this.Dictionary)
            },
			getmess(){
				var v = this
				this.$http.get(this.apis.ITMS.QUERY).then((res) =>{
					v.tableData = res.page.list
					v.SpinShow = false;
					v.pageTotal=res.page.total
				})
			},
			//删除数据
			listDele(id){
				this.util.del(this,this.apis.ITMS.DELE,[id],()=>{
                    this.getmess();
				});
			},
			AddDataList() {
				var v = this
				v.compName = 'addmess'
			},
			pageChange(event) {
				var v = this
				v.page.pageNum = event
			},
			findMessList() {
				var v = this
				v.SpinShow = true;
				this.$http.get(this.apis.ITMS.QUERY,{params:v.findMess}).then((res) =>{
					//log(res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
			},
		}
	}
</script>
