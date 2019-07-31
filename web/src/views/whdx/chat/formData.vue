<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
        			:mask-closable="false" :title="operate+''">
			<Table :height="tableHeight" :columns="tableColumns" :data="pageData"></Table>
			<Row class="margin-top-10 pageSty">
				<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator @on-change='(e)=>changeTab(e)'></Page>
			</Row>
			<!--<component :is="componentName"></component>-->
        	<div slot='footer'>
        		<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
        		<Button type="primary" @click="v.util.save(v)">确定</Button>
        	</div>
        </Modal>


		<Modal v-model="showDialog" width='900' :closable='false'
			   :mask-closable="false" :title="operate+''">
			聊天内容:{{choosedItem.content==null?'':choosedItem.content}}
			<Input v-model="reply" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="请输入回复内容..." />
			<div slot='footer'>
				<Button type="ghost" @click="showDialog=false">取消</Button>
				<Button type="primary" @click="save">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: 'driversSchoolForm',
		data() {
			return {
			    v:this,
                operate:'新建',
				user:{},
                apiRoot: this.apis.chat,
				showModal: true,
				showDialog:false,
				readonly: false,
                tableHeight: '200',
                choosedItem:{},
                componentName:'',
                reply:'',
				formItem: {
				},
                formInputs:[
                    {label:'驾校名称',prop:'schoolName'},
                    {label:'驾校地址',prop:'schoolAddress'},
                ],
                ruleInline:{
				},
				form: {
                    total: 0,
                    pageNum: 1,
                    pageSize: 8
                },
                pageData:[],
                tableColumns: [
                    {title: "#", width: 60, type: 'index'},
                    {title: '成交时间', key: 'cjsj'},
                    {title: '聊天内容', key: 'content'},
                    {
                        title: '回复情况',
                        key: 'type',
                        render: (h, params) => {
                            let type = params.row.type
                            return h('span', type === '0' ? '未回复' : '已回复');
                        },
                        filters: [
                            {
                                label: '未回复',
                                value: 1
                            },
                            {
                                label: '已回复',
                                value: 2
                            }
                        ],
                        filterMultiple: false,
                        filterMethod (value, row) {
                            if (value === 1) {
                                return row.type == '0';
                            } else if (value === 2) {
                                return row.type == '1';
                            }
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        render: (h, params) => {
                            return h('div', [
                                params.row.type=='0'?
                                this.util.buildButton(this,h,'warning','edit','回复',()=>{
                                    this.choosedItem=params.row
                                    this.showDialog=true
								}):'/'
                            ]);
                        }
                    }
                ],
			}
		},
		created(){
		    // this.util.initFormModal(this);
            if (this.$parent.choosedItem){
                // 深复制，避免数据联动
                this.formItem = JSON.parse(JSON.stringify(this.$parent.choosedItem));
                this.operate = '编辑'
                this.readonly = true
				this.getData()
            }
            console.log(this.$parent.choosedItem)
		},
		methods: {
            getData() {
                this.$http.get(`${this.apiRoot.USERMEASSAGE}?userId=${this.formItem.userId}&pageNum=${this.form.pageNum}&pageSize=${this.form.pageSize}`).then((res) => {
                    if (res.code === 200) {
                        this.pageData = res.page.list
                        this.form.total = res.page.total
                    }
                    else this.$Message.error(res.message);
                })
            },
            save(){
                this.$http.post(this.apiRoot.REPLY,{'userId':this.formItem.userId,'content':this.reply}).then((res) => {
                    if (res.code === 200)
                        this.$Message.success(res.message);
                    this.$Message.error(res.message);
                })
			}
		}
	}
</script>

<style>

</style>
