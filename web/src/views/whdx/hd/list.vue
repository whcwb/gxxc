<style lang="less">
	@import '../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
			<search-items :parent="v" show-search="false"></search-items>
			<Button type="primary" @click="create">
				<Icon type="plus-round"></Icon>
			</Button>
		</Row>
		<Row style="position: relative;">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
		</Row>
		<Row class="margin-top-10 pageSty">
			<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
				  @on-change='pageChange'></Page>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
    import create from './create.vue'
	import searchItems from '../components/searchItems'

    export default {
        name: 'byxxTable',
        components: {searchItems,create},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.hd,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '标题',key:'hdBt',searchKey:'hdBtLike'},
                    {title: '正文',key:'hdZw'},
                    {title: '类型',key:'hdSx',dict:'ZDCLK0036',searchType:'dict'},
					{title:'推荐',key:'hdtj',
						render:(h,p)=>{
                            return this.util.buildSwitch(h,p.row.hdtj && p.row.hdtj.length > 0 ? true:false,(value)=>{
                                let rzt = value ? '1':''
                                let v = this;
                                this.$http.post(this.apis.hd.hdtj,{'id':p.row.id,'hdtj':rzt}).then((res) =>{
                                    if(res.code==200){
                                        this.$Message.success(res.message);
                                    }else{
                                        this.$Message.error(res.message);
                                    }
                                    v.util.getPageData(v)
                                })
							})
						}
					},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'info','ios-color-wand','编辑',()=>{
                                    this.$router.push({name:'create_news',params:{item:JSON.stringify(params.row)}});
                                }),
                                this.util.buildDeleteButton(this,h,params.row.id),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
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
            create(){
                this.$router.push({name:'create_news'});
			},
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
