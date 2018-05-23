<template>
	<div>
		<Modal
		    v-model="showModal"
			height="460"
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'用户'">
    		<Form
				ref="addmess"
				:model="addmess"
				:rules="ruleInline"
    			:label-width="120"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 520px;">
					<Row>
						<Col span="12">
							<FormItem prop="zh" label='登录名：'>
								<Input :readonly="!usermesType" type="text" v-model="addmess.zh" placeholder="请设置登录帐号">
								</Input>
							</FormItem>
						</Col>
						<Col span="12" v-if="showPsd">
							<FormItem prop="mm" label='密码：'>
								<Input type="password" v-model="addmess.mm" placeholder="请设置用户密码">
								</Input>
								<span>*默认：123456</span>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="xm" label='姓名：'>
								<Input type="text" v-model="addmess.xm" placeholder="请输入姓名">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="sjh" label='手机号码：'>
								<Input type="text" v-model="addmess.sjh" placeholder="请输入手机号码">
								</Input>
							</FormItem>
						</Col>
						<Col span="24">
							<FormItem label='用户类型：'>
								<!--<Select filterable clearable  v-model="addmess.lx">
									<Option v-for="item in yhlxDict" :value="item.key">{{item.val}}</Option>
								</Select>-->
								<RadioGroup v-model="addmess.lx">
									<Radio label="10">管理员</Radio>
									<Radio label="20">工作人员</Radio>
									<Radio label="30">教练员</Radio>
								</RadioGroup>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="zjhm" label='身份证号码：'>
								<Input type="text" v-model="addmess.zjhm" style="width:160px" placeholder="请输入身份证号码">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zjcx" label='准驾车型：'>
							<Select filterable clearable  v-model="addmess.zjcx">
								<Option v-for="e in zjcxList" :value="e.key" :key="e.key">{{e.val}}</Option>
							</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24">
						<FormItem label='入职日期：'>
							<DatePicker :value="addmess.rzrq" type="date" placement="top-start" placeholder="请选择日期" @on-change="(date)=>{addmess.rzrq = date}"></DatePicker>
						</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24">
							<FormItem prop="zjhmexp" label='身份证有效期止：'>
								<DatePicker :value="addmess.zjhmexp" type="date" placement="top-start" placeholder="请选择日期" @on-change="(date)=>{addmess.zjhmexp = date}"></DatePicker>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24">
							<FormItem prop="jszclrq" label='驾驶证初领日期：'>
								<DatePicker :value="addmess.jszclrq" type="date" placement="top-start" placeholder="请选择日期" @on-change="(date)=>{addmess.jszclrq = date}"></DatePicker>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24">
							<FormItem prop="jszjzrq" label='驾驶证截止日期：'>
								<DatePicker :value="addmess.jszjzrq" type="date" placement="top-start" placeholder="请选择日期" @on-change="(date)=>{addmess.jszjzrq = date}"></DatePicker>
							</FormItem>
						</Col>

					</Row>
	    		</div>
    		</Form>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>


	export default {
		name:'',
		data(){
			return {
				showModal:true,
				operate:"新增",
				//新增数据
            	addmess: {
                    zh: '',
                    xm:'',
                    mm:'123456',
                    lx:'',
                    xb:'1',
                    zw:'',
                    sjh:'',
                    zjhm:'',
					zjcx:'',
                    zjhmexp:'',
					jszclrq:'',
                    jszjzrq:'',
					rzrq:'',
                    jgdm:''
                },
                showPsd:false,
                ruleInline: {
                  	zh: [
                      	{ required: true, message: '请输入用户名', trigger: 'blur' }
                  	],
                  	xm: [
                      	{ required: true, message: '请输入姓名', trigger: 'blur' }
                  	],
                  	sjh:[
                      	{ required: true,message: '请输入手机号码', trigger: 'blur' }
                  	],
					mm:[
						{ required: true,message: '请输入登录密码', trigger: 'blur' }
					]
              	},
                yhlxDict:[],
                yhlxDictCode:'ZDCLK0003',
				orgList:[],
                zjcxListCode:'ZJCX',
				zjcxList:[]
			}
		},
		props:{
			usermesType:{
				type:Boolean,
				default:true
			},
			usermes:{
				type:Object,
				default:{}
			}
		},
		watch:{
            addmess:{
                handler(curVal,oldVal){
                    if(!this.usermesType){
                        return;
                    }
                    if (curVal.lx == '10'){
                        this.addmess.xm = '管理员';
					}else{
                        this.addmess.xm = '';
					}
                },
                deep:true
			}
		},
		created(){
            if(this.usermesType){
                this.showPsd = true;
            }else{
                this.addmess = JSON.parse(JSON.stringify(this.usermes));
                console.log(this.addmess);
                this.operate = '编辑'
			}

            this.zjcxList = this.dictUtil.getByCode(this,this.zjcxListCode);
			//this.getOrgList();
        },
		methods:{
		    getOrgList(){
		        let v = this;
                v.$http.get(this.apis.FRAMEWORK.QUERY,{params:{pageSize:10000}}).then((res) =>{
                    if(res.code===200){
                        this.orgList = res.page.list;
                    }else{
                        v.$Message.error(res.message);
                    }
                }).catch((error) =>{
                    v.$Message.error('出错了！！！');
                })
			},
			colse(){
                var v = this
                v.$parent.compName = ''
            },
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
                    	if(v.usermesType){
                    		v.$http.post(this.apis.USER.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('用户注册成功');
									v.$emit('listF',res)
								}else{
                                    v.$Message.error(res.message);
								}
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
							})
                    	}else{
                    	    delete v.addmess.mm;
                    		v.$http.post(this.apis.USER.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
									v.$Message.success('用户修改成功');
									v.$emit('listF',res)
								}else{
                                    v.$Message.error(res.message);
								}
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
            },
		}
	}
//15271928827
</script>

<style>
</style>
