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
        <component :is="componentName" @choosePoint="choosePoint" @chooseImgFinish="chooseImgFinish"></component>
	</div>
</template>

<script>
import formData from './formData'
import chooseMapPoint from '../components/chooseMapPointModal'
import chooseImg from '../components/chooseImgModal'
    export default {
        name: 'driversSchool',
        components:{formData,chooseMapPoint,chooseImg},
        data() {
            return {
                searchVal:'',
                v:this,
                SpinShow: true,
                apiRoot:this.apis.school,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                choosedPoint:{},
                choosedImgs:'',
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title:'区域代码',key:'regionCode',dict:'ZDCLK0060'},
                    {title:'驾校名称',key:'schoolName',searchKey:'schoolNameLike'},
                    {title:'驾校地址',key:'schoolAddress'},
                    {title:'联系人',key:'linkMan'},
                    {title:'联系电话',key:'linkTel'},
                    {title:'可培训车型',key:'trainableVehicleType'},
                    {title:'教练总数',key:'coachTotal'},
                    {title:'教练车总数',key:'carTotal'},
                    {title:'场地总面积',key:'totalAreaJlc'},
                    {title:'备注',key:'remark'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-image','驾校图片',()=>{
                                    this.choosedItem = params.row;
                                    this.choosedImgs = params.row.bgFileId
                                    this.componentName = 'chooseImg'
                                }),
                                this.util.buildButton(this,h,'success','ios-pin','地理位置',()=>{
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
            chooseImgFinish(uploadList){
                this.updateImg(uploadList);
            },
            updatePoint(point){
                let param = {
                    schoolCode:this.choosedItem.schoolCode,
                    lat:point.lat,
                    lng:point.lng
                }
                this.$http.post(this.apis.school.CHANGE,param).then((res)=>{
                    if (res.code === 200){
                        this.util.getPageData(this);
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            updateImg(uploadList){
                let param = {
                    schoolCode:this.choosedItem.schoolCode,
                    bgFileId:uploadList,
                }
                this.$http.post(this.apis.school.CHANGE,param).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success(res.message);
                        this.util.getPageData(this);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            }
        }
    }
</script>
