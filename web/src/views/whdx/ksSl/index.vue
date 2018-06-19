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
        name: 'ksSl',
        components:{formData},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.kssl,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'',key:'id'},
                    {title:'学员ID',key:'yhId'},
                    {title:'驾校代码 BIZ_DRIVERS_SCHOOL 表的主键',key:'schoolCode'},
                    {title:'受理时间',key:'slSj'},
                    {title:'受理流水',key:'slLs'},
                    {title:'受理车型(车型字典项：ZDCLK0069  A1 A2……)',key:'slCx'},
                    {title:'考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)',key:'slKsyy'},
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
