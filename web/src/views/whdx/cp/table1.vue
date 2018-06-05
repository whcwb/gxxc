<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
        	<search-items :parent="v"></search-items>
        </Row>
        <Row style="position: relative;">
        	<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
        	<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator @on-change='(e)=>{v.util.pageChange(v, e)}'></Page>
        </Row>
        <component :is="componentName"></component>
	</div>
</template>

<script>
    import formData from './formData.vue'
	import searchItems from '../components/searchItems'

    export default {
        name: 'cp',
        components: {formData,searchItems},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.cp.getcplx,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'产品名称',key:'cpMc',searchKey:'cpMcLike'},
                    {title:'产品类型',key:'cpType',searchKey:'cpTypeLike',dict:'ZDCLK0063',searchType:'dict'},
                    {title:'产品总金额',key:'cpJl',append:'元'},
                    {title:'是否分佣',key:'cpYj',dict:'ZDCLK0064',searchType:'dict'},
                    {title:'一级佣金',key:'cpYjyj',append:'元'},
                    {title:'二级佣金',key:'cpRjyj',append:'元'},
                    {title:'产品是否有效',key:'cpYx',dict:'ZDCLK0065',searchType:'dict'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        fixed: 'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildEditButton(this,h,params),
                                this.util.buildDeleteButton(this,h,params.row.id),
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
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
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
