<template>
    <div style="display: inline-block">
        <Col v-for="r in tableColumns" v-if="r.searchKey || r.searchType" style="margin-bottom: 10px;" :span="groupSpan">
            <Col :span="labelSpan">
                <label class="searchLabel">{{r.title}}:</label>
            </Col>
            <Col :span="inputSpan">
                <Input v-if="!r.searchType || r.searchType == 'text'" v-model="form[r.searchKey]" :placeholder="'请输入'+r.title" style="width: 100%"></Input>
                <DatePicker v-else-if="r.searchType == 'daterange'" v-model="dateRange" @on-change="form[r.searchKey] = parent.util.dateRangeChange(dateRange)" confirm format="yyyy-MM-dd" type="daterange" placeholder="请输时间" style="width: 200px"></DatePicker>
                <Select v-else-if="r.searchType === 'dict'" filterable clearable  v-model="form[r.key]" :placeholder="'请选择'+r.title+'...'" style="width: auto">
                    <Option v-for = '(item,index) in parent.dictUtil.getByCode(parent,r.dict)' :value="item.key">{{item.val}}</Option>
                </Select>
            </Col>
        </Col>
        <Col span="1" style="margin-left: 10px;">
            <Button type="primary" @click="parent.util.getPageData(parent)">
                <Icon type="search"></Icon>
            </Button>
        </Col>
        <Col v-if="showCreate == 'true'" span="1" style="margin-left: 10px;">
            <Button type="primary" @click="parent.util.add(parent)">
                <Icon type="plus-round"></Icon>
            </Button>
        </Col>
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
            },
            showCreate:{
                type:String,
                default:'false'
            },
            groupSpan:{
                type:Number,
                default:7
            },
            // labelSpan:{
            //     type:Number,
            //     default:8
            // },
        },
        data(){
            return{
                dateRange:'',
                tableColumns:[],
                form:{},
                labelSpan:8,
                inputSpan:16,
                maxLabelLength:0,
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
            this.getMaxLabelLength();
            this.getLabelSpan();
        },
        methods:{
            getMaxLabelLength(){
                let maxLength = 0;
                for (let r of this.tableColumns){
                    if (r.title.length > maxLength){
                        maxLength = r.title.length;
                    }
                }
                this.maxLabelLength = maxLength;
            },
            getLabelSpan(){
                let l = parseInt(this.maxLabelLength*1.2);
                console.log(this.maxLabelLength);
                console.log(l);
                this.labelSpan = l;
                this.inputSpan = 24 - l;
            }
        }
    }
</script>

<style scoped>

</style>