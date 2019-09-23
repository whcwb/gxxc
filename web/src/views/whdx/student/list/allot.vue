<style lang="less">
	@import '../../../../styles/common.less';
	.docImg{
		width: 100%;
		padding: 10px;
	}
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
			:mask-closable="false" :title="operate+''"
			   ok-text="关闭"
			   @on-cancel="close"
			   @on-ok="close"

		>
			<div style="overflow: auto;height: 600px;">
				<Tabs>
					<Tab-pane v-if="state == 0" label="受理教练" icon="ios-download-outline">
						<allot1 :item="item" :parent="v"></allot1>
					</Tab-pane>
					<Tab-pane v-if="state == 1" label="科一教练" icon="ios-upload-outline">
						<allot2 :item="item" :parent="v"></allot2>
					</Tab-pane>
					<Tab-pane v-if="state == 2" label="科二教练" icon="ios-upload-outline">
						<allot3 :item="item" :parent="v"></allot3>
					</Tab-pane>
					<Tab-pane v-if="state == 3" label="科三" icon="ios-upload-outline">
						<div style="font-size: 24px;font-weight: 600;text-align: center">
							当前学员已分配,若修改请点击修改教练
						</div>
					</Tab-pane>
				</Tabs>
			</div>
		</Modal>
	</div>
</template>

<script>
    import allot1 from './allot1'
    import allot2 from './allot2'
    import allot3 from './allot3'
    import allot4 from './allot4'
	export default {
		name: 'byxxForm',
		components:{allot1,allot2,allot3,allot4},
		data() {
			return {
			    v:this,
                operate:'分配',
				showModal: true,
                apiRoot:this.apis.student,
				readonly: false,
                form: {
					jlTypeLike: '',
					jlZt: 0,
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
				choosedData:[],
                tableColumns: [
                    {title: "#",  type: 'index'},
                    {title: '姓名',key:'yhXm',searchKey:'yhXmLike'},
                    {title: '电话',key:'yhZh',searchKey:'yhZhLike'},
                    {
                        title: '操作',
                        key: 'action',
                        width: 120,
                        render: (h, params) => {
                            return h('div', [
                                this.util.buildButton(this,h,'success','ribbon-b','分配',()=>{
                                    this.confirm(params.row.id);
                                }),
                            ]);
                        }
                    }
                ],
                pageData: [],
                ruleInline:{
				},
                item:{

                },
				state:0,
			}
		},
		created(){
            console.log('created');
		    this.choosedData = this.$parent.choosedData
			this.getState();

			this.util.initTable(this)
		},
		mounted(){
            console.log('mounted');
        },
		methods: {
		    getState(){
		        this.state=parseInt( this.$parent.row.yhXyFpzyType);
				console.log(this.state);
				if(this.state == '0'){
					this.form.jlTypeLike = 'slzy'
				}else if(this.state == '1'){
					this.form.jlTypeLike = 'k1'
				}else {
					this.form.jlTypeLike = 'k2'
				}
			},
			close(){
		    	this.$parent.componentName = ''
			}
		}
	}
</script>

<style>

</style>
