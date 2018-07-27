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
        <component :is="componentName" @choosePoint="choosePoint"></component>
	</div>
</template>

<script>
    import formData from './formData.vue'

    import chooseMapPoint from '../components/chooseMapPointModal'
    export default {
        name: 'examPlace',
        components: {formData,chooseMapPoint},
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.examPlace,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                choosedPoint:{},
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'名称',key:'name'},
                    {title:'地址',key:'address'},
                    {title:'考试科目',key:'kskm'},
                    {title:'区域代码',key:'regioncode',dict:'ZDCLK0060'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        fixed: 'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','ios-location','地理位置',()=>{
                                    this.choosedItem = params.row;
                                    this.choosedPoint = {lat:params.row.lat,lng:params.row.lng};
                                    this.componentName = 'chooseMapPoint'
                                }),
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
            choosePoint(point){
                this.updatePoint(point);
            },
            updatePoint(point){
                let param = {
                    id:this.choosedItem.id,
                    lat:point.lat,
                    lng:point.lng
                }
                this.$http.post(this.apis.examPlace.CHANGE,param).then((res)=>{
                    if (res.code === 200){
                        this.util.getPageData(this);
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
        }
    }
</script>
