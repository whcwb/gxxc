<style lang="less">
    @import '../../../../styles/common.less';

</style>
<!--日志管理-->
<template>
    <div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<div  style="display: inline-block">
				<label class="searchLabel">操作时间:</label>
				<DatePicker v-model="czsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="getmess()" style="width: 220px"></DatePicker>
			</div>
			<Button type="primary" @click="getmess">
				<Icon type="search"></Icon>
			</Button>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
				  @on-change='pageChange'></Page>
		</Row>
    </div>
</template>

<script>
	import mixins from '@/mixins'


	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
            	SpinShow:true,
				tableHeight: 220,
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:8
            	},
            	//弹层
            	showModal:false,
                tableColumns: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
                    // {
                    //     title: '操作类型',
                    //     width:120,
                    //     align:'center',
                    //     key: 'czlx'
                    // },
                    {
                        title: '操作时间',
                        align:'center',
                        key: 'czsj'
                    },
                    {
                        title: '操作人 ',
                        align:'center',
                        key: 'czr'
                    },
                    // {
                    //     title: '对象ID',
                    //     align:'center',
                    //     key: 'dx_id'
                    // },
                    // {
                    //     title: '对象类型',
                    //     align:'center',
                    //     key: 'dxlx'
                    // },
                    {
                        title: '参数',
                        align:'center',
                        key: 'cs',
						render:(h,p)=>{
                            let s = p.row.cs;
							if (s.length > 20) s = s.substring(0,17)+"...";
                            return  h('Poptip',
                                {
                                    props: {
                                        trigger:'hover',
                                        placement: 'top',
                                        content: p.row.cs,
                                    },
                                },
                                [
                                    h('div',s),
                                ]
                            )
						}
                    },
                    {
                        title: '耗时',
                        align:'center',
                        key: 'zxsj',
						render:(h,p)=>{
                            return h('div',p.row.zxsj+" ms")
						}
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'sm'
                    },
                    {
                        title: '方法',
                        align:'center',
                        key: 'ff'
                    },
                    {
                        title: '操作结果',
                        key: 'jg',
                        width: 150,
                        align: 'center',
                    }
                ],
                pageData: [
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                datetime:[],
                czsjInRange:[],
                form:{
                	czsjInRange:[],
					total:0,
                	pageNum:1,
            		pageSize:8
                }
            }
        },
        watch: {
			czsjInRange:function(newQuestion, oldQuestion){
				this.form.czsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
			},
		},
        created(){
            this.util.initTableHeight(this)
            this.getmess()
        },
        methods: {
        	getmess(){
				var v = this
				v.SpinShow = true;
				this.$http.get(this.apis.DAILY.QUERY,{params:v.form}).then((res) =>{
					log('数据',res)
					v.pageData = res.page.list
					v.form.total = res.page.total;
					v.SpinShow = false;
				})
			},
            pageChange(event){
        		var v = this
        		v.form.pageNum = event
        		v.getmess()
        	},
        }
    }
</script>
