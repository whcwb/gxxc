<template>
    <div>
        <Col v-for="(i,index) in formInputs" :span="i.separator ? 24 : (i.span ? i.span : 12)" :key="index">
            <div v-if="i.separator && i.label"  style="text-align: center;margin-top: 16px;">
                <h3>{{i.label}}</h3>
            </div>
            <div v-if="i.separator" style="border-bottom: 1px solid #e9eaec;height: 2px;text-align: center;margin-top: 1px;margin-bottom: 16px;"></div>
            <FormItem v-else :prop='i.prop' :label='i.label'>
                <Input v-if="!i.dict &&(!i.type || i.type ==='text')" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="parent.readonly && i.readonly" :disabled="parent.readonly && i.disabled">
                    <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                    <span v-if="i.append" slot="append">{{i.append}}</span>
                </Input>
                <InputNumber v-else-if="i.type ==='number'" v-model="formItem[i.prop]" :placeholder="'请填写'+i.label+'...'" :readonly="parent.readonly && i.readonly" :disabled="parent.readonly && i.disabled">
                    <span v-if="i.prepend" slot="prepend">{{i.prepend}}</span>
                    <span v-if="i.append" slot="append">{{i.append}}</span>
                </InputNumber>
                <DatePicker v-else-if="i.type == 'date'"  :value="formItem[i.prop] == null || formItem[i.prop] == '' ? today : formItem[i.prop]" type="date" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"  :readonly="parent.readonly && i.readonly"  :disabled="parent.readonly && i.disabled"></DatePicker>
                <DatePicker v-else-if="i.type == 'datetime'"  :value="formItem[i.prop] || formItem[i.prop] == '' ? now : formItem[i.prop]" type="datetime" placeholder="请选择日期" @on-change="(date)=>{formItem[i.prop] = date}"  :readonly="parent.readonly && i.readonly" :disabled="parent.readonly && i.disabled"></DatePicker>
                <Select v-else-if="i.type === 'foreignKey'" :disabled="parent.readonly && i.disabled" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'">
                    <Option v-for = '(item,index) in foreignList[i.prop].items' :value="item.key" :key="item.key">{{item.val}}</Option>
                </Select>
                <RadioGroup v-else-if="i.type === 'radio'" v-model="formItem[i.prop]">
                    <Radio v-for='(item,index) in parent.dictUtil.getByCode(parent,i.dict)' v-if="i.excludeDict == null || i.excludeDict.indexOf(item.key) < 0" :label="item.key">{{item.val}}</Radio>
                </RadioGroup>
                <CheckboxGroup v-else-if="i.type === 'checkBox'" v-model="formItem[i.prop]" @on-change="changeBox">
                    <Checkbox v-for='(item,index) in parent.dictUtil.getByCode(parent,i.dict)' :label="item.key" >{{item.val}}</Checkbox>
                </CheckboxGroup>
                <Select v-else-if="i.dict || i.type === 'dict'" filterable clearable  v-model="formItem[i.prop]" :placeholder="'请选择'+i.label+'...'" :readonly="parent.readonly && i.readonly" :disabled="parent.readonly && i.disabled">
                    <Option v-for = '(item,index) in parent.dictUtil.getByCode(parent,i.dict)'  v-if="i.excludeDict == null || i.excludeDict.indexOf(item.key) < 0"  :value="item.key" :key="item.key">{{item.val}}</Option>
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
            parentFormItems:{
              type:Object,
              default:function(){
                  return null;
              }
            },
        },
        data(){
          return{
              foreignList:[],
              formInputs:[],
              formItem:{},
              today:'',
              now:''
          }
        },
        created(){
            this.today = new Date().format("yyyy-MM-dd");
            this.now = new Date().format("yyyy-MM-dd HH:mm:SS");
            if (this.parent.foreignList){
                this.foreignList = this.parent.foreignList;
            }
            if (this.parentFormInputs != null){
                this.formInputs = this.parentFormInputs;
            }else if (this.parent.formInputs){
                this.formInputs = this.parent.formInputs;
            }
            if (this.parentFormItems != null){
                this.formItem = this.parentFormItems;
            }else if (this.parent.formItem){
                this.formItem = this.parent.formItem;
            }
            for (let r of this.formInputs){
                if(r.type === 'checkBox'){
                    this.box = []
                    let d = this.parent.dictUtil.getByCode(this.parent,r.dict)
                   let c  =  this.formItem[r.prop].split(",")
                    this.box.push(r.prop)
                    this.box.push(r.dict)
                    for( let v of c){
                        for(let i in d ){
                            if(d[i].key === v){
                                this.box.push(d[i].key)
                            }
                        }
                    }
                this.formItem[r.prop] = this.box
                console.log(this.box)
                }
                if (typeof r.handler === 'function'){
                    this.formItem[r.prop] = r.handler(this.formItem[r.prop]);
                }
            }
        },
        methods: {
            changeBox(p){
                let prop = p[0]
                let dict = p[1]
                let d = this.parent.dictUtil.getByCode(this.parent,dict)
                let a = []
                a.push(prop)
                a.push(dict)
                for(var i = 2 ; i < p.length; i++){
                    for (let k in d){
                        if(d[k].key === p[i]){
                            a.push(d[k].key)
                        }
                    }
                }
                this.formItem[prop] = a
                console.log(this.formItem[prop])
            }
        }
    }
</script>

<style scoped>

</style>
