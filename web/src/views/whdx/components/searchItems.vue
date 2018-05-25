<template>
    <div style="display: inline-block">
        <div v-for="r in tableColumns" v-if="r.searchKey || r.searchType" style="display: inline-block">
            <label class="searchLabel">{{r.title}}:</label>
            <Input v-if="!r.searchType || r.searchType == 'text'" v-model="form[r.searchKey]" :placeholder="'请输入'+r.title" style="width: 200px"></Input>
            <DatePicker v-else-if="r.searchType == 'daterange'" v-model="dateRange" @on-change="form[r.searchKey] = parent.util.dateRangeChange(dateRange)" confirm format="yyyy-MM-dd" type="daterange" placeholder="请输时间" style="width: 200px"></DatePicker>
            <Select v-else-if="r.searchType == 'dict'" filterable clearable  v-model="form[r.key]" :placeholder="'请选择'+r.title+'...'" style="width: auto">
                <Option v-for = '(item,index) in parent.dictUtil.getByCode(parent,r.dict)' :value="item.key">{{item.val}}</Option>
            </Select>
        </div>
    </div>
</template>

<script>
    export default {
        name: "searchItems",
        props:{
            parent:{
                type:Object,
                default:function(){
                    return {};
                }
            }
        },
        data(){
            return{
                dateRange:'',
                tableColumns:[],
                form:{},
            }
        },
        created(){
            if (this.parent.tableColumns){
                this.tableColumns = this.parent.tableColumns;
            }
            if (this.parent.dateRange){
                this.dateRange = this.parent.dateRange;
            }
            if (this.parent.form){
                this.form = this.parent.form;
            }
        }
    }
</script>

<style scoped>

</style>