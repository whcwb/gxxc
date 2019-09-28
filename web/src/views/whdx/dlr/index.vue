<style lang="less">
    @import '../../../styles/common.less';
</style>
<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <!--				<search-items :parent="v" :label-with="100"></search-items>-->

            <!--				<Button type="info" @click="exportData">-->
            <!--					<Icon type="ios-download-outline"></Icon>-->
            <!--				</Button>-->
            <Col span="4">
                <RadioGroup v-model="form.km" type="button" @on-change="getPager">
                    <Radio label="1">科目一</Radio>
                    <Radio label="2">科目二</Radio>
                    <Radio label="3">科目三</Radio>
                </RadioGroup>
            </Col>
            <Col span="20">
                <Input search enter-button placeholder="请输入姓名丶身份证号丶联系电话搜索"
                       clearable
                       v-model="form.cond"
                       @on-enter="getPager"
                       @on-change="getPager"
                />
            </Col>

        </Row>
        <Row style="position: relative;">
            <Table size="large" :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionChange"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <pager :parent="v"></pager>
        </Row>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    import yk from './yk'
    import formData from '../student/list/formData.vue'
    import VueBarcode from 'vue-barcode'
    export default {
        name: 'byxxTable',
        components: {formData,yk},
        data() {
            return {
                v:this,
                SpinShow: true,
                pagerUrl:'/api/ptyh/getDlrYh',
                deleteUrl:this.apis.removeUserInfo,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                dateRange:'',
                tableColumns: [
                    {title: "",  type: 'index',width:60},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '身份证号',key:'yhZjhm',searchKey:'yhZjhmLike'},
                    {title: '联系电话',key:'yhZh',searchKey:'yhZhLike'},
                    // {title: '缴费状态',key:'ddSfjx',dict:'jfzt',searchType:'dict'},
                    // {title: '是否有驾驶证',key:'yhSfyjz',dict:'sfyjsz',searchType:'dict'},
                    // {title: '认证状态',key:'yhZt',dict:'ZDCLK0043',searchType:'dict'},
                    // {title: '受理状态',key:'yhXySlType',dict:'ZDCLK0071'},
                    {title: '流水号',key:'yhLsh'},
                    // {title: '流水号条码',key:'yhLsh',width:280,
                    // 	render:(h,p)=>{
                    // 	   return h('div',[
                    //
                    // 		   h('barcode',
                    // 					{
                    // 					   style:{
                    // 							   height:'80px'
                    // 					   },
                    // 						props:{
                    // 							value:p.row.yhLsh,
                    // 						}
                    // 					}
                    // 			)
                    // 	   ],)
                    //
                    // 	}
                    // },
                    {title: '约考状态',key:'yhXyYkType',dict:'ykzt'},
                    // {title: '锁定',key:'yhSfsd',
                    //     render:(h,p)=>{
                    //         return this.util.buildSwitch(h,p.row.yhSfsd && p.row.yhSfsd == '1' ? true:false,(value)=>{
                    //             let rzt = value ? '1':'0'
                    //             let v = this;
                    //             this.$http.post(this.apis.student.updateSfsd,{'id':p.row.id,'yhSfsd':rzt}).then((res) =>{
                    //                 if(res.code==200){
                    //                     this.$Message.success(res.message);
                    //                 }else{
                    //                     this.$Message.error(res.message);
                    //                 }
                    //                 v.util.getPageData(v)
                    //             })
                    //         })
                    //     }
                    // },
                    {title: '车型',key:'yhCx',dict:'chexing'},
                    // {title: '分配状态',key:'yhIxySffp',dict:'fpzt',searchType:'dict'},
                    {
                        title: '操作',
                        key: 'action',
                        fixed: 'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','md-checkmark','录入成绩',()=>{
                                    this.getYk(params.row.id);
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                choosedData:[],
                form: {
                    km:'1',
                    cond:'',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                row:{}
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
            getPager(){
                this.util.initTable(this)
            },
            getData(api,yhId,componentName,row){
                this.$http.get(api,{params:{yhId:yhId,pageSize:1,orderBy:'cjsj desc'}}).then((res)=>{
                    if (res.code === 200 && res.page.list && res.page.list.length > 0){
                        this.choosedItem = res.page.list[0];
                    }else{
                        this.choosedItem = {yhId:yhId};
                    }
                    this.row = row
                    this.componentName = componentName
                })
            },
            getSl(yhId){
                this.getData(this.apis.kssl.QUERY,yhId,'sl');
            },
            getJf(yhId,row){
                this.getData(this.apis.ksJf.QUERY,yhId,'jf',row);
            },
            getFp(yhId,row){
                this.getData(this.apis.ksJf.QUERY,yhId,'allot',row);
            },
            getYk(yhId){
                this.getData(this.apis.ksyk.QUERY,yhId,'yk');
            },
            gettm(text){
                JsBarcode("#barcode", text)
                this.$nextTick()

            },
            selectionChange(e){
                this.choosedData = e;
            },
            deleteUser(id){
                this.$http.post(this.apis.removeUserInfo,{userId:id}).then((res)=>{
                    if (res.code == 200){
                        this.$Message.success(res.message);
                        this.util.getPageData(this);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            allot(){
                console.log('allot');
                if (this.choosedData.length == 0){
                    this.$Message.error("请选择学员")
                    return;
                }
                for (let r of this.choosedData){
                    if (r.yhIxySffp == '1'){
                        this.$Message.error("请选择未分配的学员")
                        return;
                    }
                    if (r.ddSfjx != '1'){
                        this.$Message.error("请选择已缴费的学员")
                        return;
                    }
                }
                console.log('componentName');
                this.componentName = "allot";
            },
            pageChange(event) {
                this.util.pageChange(this, event);
            },
            exportData(){
                let params = {
                    exportType:'ptyh',
                    cols:'姓名,账号,是否有驾驶证,认证状态,教练姓名,教练电话',
                    keys:'yhXm,yhZh,yhSfyjz,yhZt,jlxm,jldh'
                }
                window.open(this.apis.exportData+'?ddSfjx=1&exportType='+params.exportType+"&cols="+params.cols+"&keys="+params.keys);
            }
        }
    }
</script>
