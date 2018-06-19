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
        name: 'ksYk',
        components:{formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.ksyk,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'平台用户ID',key:'yhId'},
                    {title:'科目编码',key:'kmCode',dict:'ZDCLK0067'},
                    {title:'考场名称',key:'schoolName'},
                    {title:'预约考试时间',key:'ykSj'},
                    {title:'用户姓名',key:'yhXm'},
                    {title:'用户证件号码',key:'yhZjhm'},
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
