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
        name: 'ksJg',
        components:{formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.ksjg,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'',key:'id'},
                    {title:'学员用户id',key:'yhId'},
                    {title:'成绩单图片地址',key:'cjdImg'},
                    {title:'科目编码 [ZDCLK0067]',key:'kmCode'},
                    {title:'考试成绩',key:'ksCj'},
                    {title:'学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）',key:'ksSfhg'},
                    {title:'考场名称',key:'schoolName'},
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
