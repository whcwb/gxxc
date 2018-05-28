<template>
    <div class="boxbackborder">
        <Row style="padding-bottom: 16px;">
            <search-items :parent="parent" :show-create-button="showCreateButton"></search-items>
        </Row>
        <Row style="position: relative;">
            <Table :height="parent.tableHeight" :columns="parent.tableColumns" :data="parent.pageData"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <Page :total=parent.form.total :current=parent.form.pageNum :page-size=parent.form.pageSize show-total show-elevator
                  @on-change='(e)=>{parent.util.pageChange(parent, e)}'></Page>
        </Row>
        <component  :is="componentName" ></component>
    </div>
</template>

<script>
    import searchItems from './searchItems'
    export default {
        name: "baseTable",
        components: {
            searchItems
        },
        data(){
            return{
                componentName:''
            }
        },
        props:{
            parent:{
                type:Object,
                default:function(){
                    return {};
                }
            },
            showSearchButton:{
                type:Boolean,
                default:true
            },
            showCreateButton:{
                type:Boolean,
                default:false
            },
        },
    }
</script>

<style scoped>

</style>