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
        components:{formData},
        name: 'trainPlace',
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.trainPlace,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'训练场地名称',key:'placeName',searchKey:'placeNameLike'},
                    {title:'场地缩略图',key:'placeIcon'},
                    {title:'地址',key:'address'},
                    {title:'实测面积',key:'measuredArea'},
                    {title:'培训车型',key:'techDriverType'},
                    {title:'驾校名称',key:'schoolName'},
                    {title:'车辆数',key:'approvedCarNum'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
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
