<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
        	<search-items :parent="v" :showCreateButton="true"></search-items>
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
                apiRoot:this.apis.ksJf,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'',key:'id'},
                    {title:'学员ID',key:'yhId'},
                    {title:'学员缴费时间',key:'jfSj'},
                    {title:'缴费渠道',key:'jfFs'},
                    {title:'科目编码 [ZDCLK0067]',key:'kmId'},
                    {title:'操作人',key:'czr'},
                    {title:'缴费金额(单位分)',key:'jfJl'},
                    {title:'用户姓名',key:'yhXm'},
                    {title:'用户证件号码',key:'yhZjhm'},
                    {title:'创建人',key:'cjr'},
                    {title:'创建时间',key:'cjsj'},
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
        }
    }
</script>
