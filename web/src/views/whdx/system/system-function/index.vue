<style lang="less">
    @import '../../../../styles/common.less';
</style>
<!--角色管理-->
<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="v" :show-create-button="true"></search-items>
        </Row>
            <Row style="position: relative;">
                <Table :height="tableHeight" :row-class-name="rowClassName" :columns="tableColumns" :data="pageData"></Table>
            </Row>
            <Row class="margin-top-10 pageSty">
                <Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
                      @on-change='(e)=>{v.util.pageChange(v, e)}'></Page>
            </Row>
        <component  :is="componentName" ></component>
    </div>
</template>

<script>
    import mixins from '@/mixins'
    import formData from './comp/formData.vue'
    import searchItems from '../../components/searchItems'

    export default {
        name: 'char',
        mixins: [mixins],
        components: {
            formData,searchItems
        },
        data() {
            return {
                v:this,
                pageTotal:0,
                SpinShow: true,
                apiRoot:this.apis.FUNCTION,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60,  type: 'index', fixed: 'left'},
                    {title: '功能名称', key: 'gnmc', fixed: 'left',searchKey:'gnmcLike'},
                    {title: '功能代码', key: 'gndm'},
                    {title: '服务代码', key: 'fwdm'},
                    {title: '状态', key: 'zt',dict:'ZDCLK0007'},
                    {title: '排序', key: 'px'},
                    {title: '备注', key: 'bz'},
                    {title: 'URL',  key: 'url'},
                    {title: '父节点',  key: 'fjd'},
                    {title: '跳转地址',key: 'tzdz'},
                    {title: '图标',
                        width: 60,
                        key: 'tb',
                        render: (h, params) => {
                            return h('div', [ h('Icon', {  props: { type: params.row.tb, size: '22' }, }) ]);
                        }
                    },
                    {title: 'API前缀', key: 'apiQz'},
                    {title: 'API后缀', key: 'apiHz'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 100,
                        fixed: 'right',
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildEditButton(this,h,params),
                                this.util.buildDeleteButton(this,h,params.row.gndm)
                            ]);
                        }
                    }
                ],
                pageData: [],
                form: {
                    gnmcLike: '',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
                Dictionary: [],
            }
        },
        created() {
            this.util.initTable(this)
        },
        methods: {
        }
    }
</script>
