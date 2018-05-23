<template>
    <div>
        <Col v-for="i in formInputs" :span="i.span ? i.span : 12">
            <FormItem :prop='i.prop' :label='i.label'>
                <Input v-if="!i.type || i.type ==='text'" type="text" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="readonly && i.readonly" :disabled="readonly && i.disabled"></Input>
                <DatePicker v-else-if="i.type == 'date'"  :value="formItem[i.prop]" type="date" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"></DatePicker>
                <DatePicker v-else-if="i.type == 'datetime'"  :value="formItem[i.prop]" type="datetime" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"></DatePicker>
                <Select v-else-if="i.type === 'dict'" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'">
                    <Option v-for = '(item,index) in v.dictUtil.getByCode(v,i.dict)' :value="item.key">{{item.val}}</Option>
                </Select>
                <Select v-else-if="i.type === 'foreignKey'" :disabled="i.disabled" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'">
                    <Option v-for = '(item,index) in foreignList[i.prop].items' :value="item.key">{{item.val}}</Option>
                </Select>
            </FormItem>
        </Col>
    </div>
</template>

<script>
    export default {
        name: "formItems",
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
              readonly:false,
              foreignList:[],
              formInputs:[],
              formItem:{},
          }
        },
        created(){
            if (this.parent.foreignList){
                this.foreignList = this.parent.foreignList;
            }
            if (this.parent.formInputs){
                this.formInputs = this.parent.formInputs;
            }
            if (this.parent.formItem){
                this.formItem = this.parent.formItem;
            }
            if (this.parent.readonly){
                this.readonly = this.parent.readonly;
            }
        }
    }
</script>

<style scoped>

</style>