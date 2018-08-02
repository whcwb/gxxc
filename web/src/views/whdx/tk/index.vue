<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
        	<search-items :parent="v" :showCreateButton="false"></search-items>
        </Row>
        <Row style="position: relative;">
        	<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
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
        name: 'ksJf',
        components:{formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.tk,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'学员',key:'yhXm',searchKey:'yhXmLike'},
                    {title:'证件号码',key:'yhZjhm',searchKey:'yhXmLike'},
                    {title:'创建时间',key:'cjsj'},
                    {title:'退款状态',key:'tkType',dict:'tkzt',searchType:'dict'},
                    {title:'退款描述',key:'tkMessage'},
                    {title:'经办人',key:'jbrXm'},
                    {
                        title: '操作',
                        key: 'action',
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
        }
    }
</script>
