<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
				<search-items :parent="v" :label-with="100"></search-items>
				<Button type="info" @click="exportData">
					<Icon type="ios-download-outline"></Icon>
				</Button>
		</Row>
		<Row style="position: relative;">
			<Table size="large" :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionChange"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<pager :parent="v"></pager>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
	import jf from '../status/jf'
	import jg from '../status/jg'
	import sl from '../status/sl'
	import yk from '../status/yk'
    import formData from './formData.vue'
    import sublist from './sublist.vue'
    import status from './status.vue'
    import audit from './audit.vue'
    import allot from './allot.vue'
    import xgjl from './xgjl.vue'
	import VueBarcode from 'vue-barcode'
    export default {
        name: 'byxxTable',
        components: {formData,sublist,allot,audit,status,'barcode': VueBarcode,xgjl,jf,jg,sl,yk},
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
                    {title: '身份证号',key:'yhZjhm',searchKey:'yhZhLike'},
					{title: '账号',key:'yhZh',searchKey:'yhZhLike'},
                    // {title: '缴费状态',key:'ddSfjx',dict:'jfzt',searchType:'dict'},
                    // {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz',searchType:'dict'},
                    // {title: '认证状态',key:'yhZt',dict:'ZDCLK0043',searchType:'dict'},
                    {title: '受理状态',key:'yhXySlType',dict:'ZDCLK0071'},
                    {title: '流水号',key:'yhLsh'},
                    // {title: '流水号条码',key:'yhLsh',width:280,
					// 	render:(h,p)=>{
                    // 	   return h('div',[
					//
					// 		   h('barcode',
					// 					{
					// 					   style:{
					// 							   height:'80px'
					// 					   },
					// 						props:{
					// 							value:p.row.yhLsh,
					// 						}
					// 					}
					// 			)
					// 	   ],)
					//
					// 	}
					// },
                    {title: '约考状态',key:'yhXyYkType',dict:'ykzt'},
                    // {title: '锁定',key:'yhSfsd',
                    //     render:(h,p)=>{
                    //         return this.util.buildSwitch(h,p.row.yhSfsd && p.row.yhSfsd == '1' ? true:false,(value)=>{
                    //             let rzt = value ? '1':'0'
                    //             let v = this;
                    //             this.$http.post(this.apis.student.updateSfsd,{'id':p.row.id,'yhSfsd':rzt}).then((res) =>{
                    //                 if(res.code==200){
                    //                     this.$Message.success(res.message);
                    //                 }else{
                    //                     this.$Message.error(res.message);
                    //                 }
                    //                 v.util.getPageData(v)
                    //             })
                    //         })
                    //     }
                    // },
					{title: '车型',key:'yhCx',dict:'chexing'},
					{title: '分配状态',key:'yhIxySffp',dict:'fpzt',searchType:'dict'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 180,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-card','受理',()=>{
									this.getSl(params.row.id);
                                }),
                                this.util.buildButton(this,h,'success','ios-car','约考',()=>{
									this.getYk(params.row.id);
                                }),

                                this.util.buildButton(this,h,'success','logo-yen','缴费',()=>{
									this.getJf(params.row.id);
                                }),
								this.util.buildButton(this,h,'warning','md-create','修改教练',()=>{
									this.choosedItem = params.row;
									this.componentName = 'xgjl'
								}),
                                this.util.buildButton(this,h,'error','md-close','删除',()=>{
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
                    yhLx:"1",
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
			getData(api,yhId,componentName){
				this.$http.get(api,{params:{yhId:yhId,pageSize:1,orderBy:'cjsj desc'}}).then((res)=>{
					if (res.code === 200 && res.page.list && res.page.list.length > 0){
						this.choosedItem = res.page.list[0];
					}else{
						this.choosedItem = {yhId:yhId};
					}
					this.componentName = componentName
				})
			},
			getSl(yhId){
				this.getData(this.apis.kssl.QUERY,yhId,'sl');
			},
			getJf(yhId){
				this.getData(this.apis.ksJf.QUERY,yhId,'jf');
			},
			getYk(yhId){
				this.getData(this.apis.ksyk.QUERY,yhId,'yk');
			},
			gettm(text){
				JsBarcode("#barcode", text)
				this.$nextTick()

			},
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
                console.log('allot');
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
                console.log('componentName');
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
