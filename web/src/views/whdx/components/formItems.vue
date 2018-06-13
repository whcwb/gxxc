<template>
    <div>
        <Col v-for="(i,index) in formInputs" :span="i.separator ? 24 : (i.span ? i.span : 12)" :key="index">
            <div v-if="i.separator && i.label"  style="text-align: center;margin-top: 16px;">
                <h3>{{i.label}}</h3>
            </div>
            <div v-if="i.separator" style="border-bottom: 1px solid #e9eaec;height: 2px;text-align: center;margin-top: 1px;margin-bottom: 16px;"></div>
            <FormItem v-else :prop='i.prop' :label='i.label'>
                <Input v-if="!i.dict &&(!i.type || i.type ==='text')" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="readonly && i.readonly" :disabled="readonly && i.disabled">
                    <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                    <span v-if="i.append" slot="append">{{i.append}}</span>
                </Input>
                <InputNumber v-else-if="i.type ==='number'" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="readonly && i.readonly" :disabled="readonly && i.disabled">
                    <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                    <span v-if="i.append" slot="append">{{i.append}}</span>
                </InputNumber>
                <DatePicker v-else-if="i.type == 'date'"  :value="formItem[i.prop]" type="date" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"  :readonly="readonly && i.readonly"  :disabled="readonly && i.disabled"></DatePicker>
                <DatePicker v-else-if="i.type == 'datetime'"  :value="formItem[i.prop]" type="datetime" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"  :readonly="readonly && i.readonly" :disabled="readonly && i.disabled"></DatePicker>
                <Select v-else-if="i.type === 'foreignKey'" :disabled="readonly && i.disabled" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'">
                    <Option v-for = '(item,index) in foreignList[i.prop].items' :value="item.key" :key="item.key">{{item.val}}</Option>
                </Select>
                <RadioGroup v-else-if="i.type === 'radio'" v-model="formItem[i.prop]">
                    <Radio v-for='(item,index) in parent.dictUtil.getByCode(parent,i.dict)' v-if="i.excludeDict == null || i.excludeDict.indexOf(item.key) < 0" :label="item.key">{{item.val}}</Radio>
                </RadioGroup>
                <Select v-else-if="i.dict || i.type === 'dict'" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'" :readonly="readonly && i.readonly" :disabled="readonly && i.disabled">
                    <Option v-for = '(item,index) in parent.dictUtil.getByCode(parent,i.dict)' :value="item.key" :key="item.key">{{item.val}}</Option>
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
            },
            parentFormInputs:{
              type:Array,
              default:function(){
                  return null;
              }
            },
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
            if (this.parentFormInputs != null){
                this.formInputs = this.parentFormInputs;
            }else if (this.parent.formInputs){
                this.formInputs = this.parent.formInputs;
            }
            if (this.parent.formItem){
                this.formItem = this.parent.formItem;
            }
            if (this.parent.readonly){
                this.readonly = this.parent.readonly;
            }
            for (let r of this.formInputs){
                if (typeof r.handler === 'functino'){
                }
            }
        }
    }
</script>

<style scoped>

</style>