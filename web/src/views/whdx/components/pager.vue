<template>
    <Page :total=parent.form.total :current=parent.form.pageNum :page-size=parent.form.pageSize show-total show-elevator @on-change='(e)=>{parent.util.pageChange(parent, e)}'></Page>
</template>

<script>
    export default {
        name: "pager",
        props:{
            parent:{
                type:Object,
                default:function(){
                    return {};
                }
            },
        },
    }
</script>

