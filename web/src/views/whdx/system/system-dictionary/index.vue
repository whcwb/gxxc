<style lang="less">
	@import '../../../../styles/common.less';
	.bodyWidth{
		box-shadow: 2px 5px 5px #888888
	}
</style>
<!--字典管理-->
<template>
	<div class="boxbackborder box" style="background-color: #fff;">
		<Row style="padding-bottom: 16px;">
			<div  style="display: inline-block">
				<label class="searchLabel">字典名称:</label>
				<Input v-model="findMess.lmmcLike" placeholder="请输入字典名称..."
					   style="width: 200px"
					   @on-keyup.enter="findMessList()"
					   @on-change="findMessList"></Input>
			</div>
			<Button type="primary" @click="findMessList()">
				<Icon type="search"></Icon>
				<!--查询-->
			</Button>
			<Button type="primary" @click="AddDc()">
				<Icon type="plus-round"></Icon>
			</Button>
		</Row>
		<div class="body padding-auto margin-5">
			<div class="box-row-list">
				<div class="bodyWidth" v-for="(item,index) in dictionary" :key='index'>
					<Card style="width:100%">
						<p slot="title">
							<Icon type="ios-film-outline"></Icon>
							{{item.lmmc}} [{{item.lmdm}}]
						</p>
						<span slot="extra">
					        <a href="#" @click.prevent="AddDcList(item,index)">
					            <Icon type="plus-circled" size="24"></Icon>
								<!--新增-->
					        </a>
					        <a href="#" @click.prevent="removeDc(item,index)" style="color: red;">
					            <Icon type="close-circled" size="24"></Icon>
								<!--删除-->
					        </a>
				        </span>
						<div style="height: 240px;">
							<div>
								<Row class="margin-bottom-10">
									<Input v-model="dictionaryMess[index]" clearable placeholder="请输入字典信息..." @on-change="findDicList(item,index,dictionaryMess[index])"></Input>
								</Row>
								<div class="padding-2px-5px box-row-nh" style='background-color: #f8f8f9;text-align: center;'>
									<div class="body-2" style="min-width: 30px;">
										序
									</div>
									<div class="body-8">
										类目编码
									</div>
									<div class="body-8">
										类目名称
									</div>
									<div class="body-2" style="min-width: 50px;">
										操作
									</div>
								</div>
							</div>
							<div style="height: 180px;overflow: auto;">
								<div class="box-row-nh margin-bottom-5" v-for='(items,indexs) in item.zdxmList'  style='text-align: center;'>
									<div class="body-2" style="min-width: 30px;">
										{{(indexs+1)}}
									</div>
									<div class="body-8">
										{{items.zddm}}
									</div>
									<div class="body-8">
										{{items.zdmc}}
									</div>
									<div class="body-2" style="min-width: 50px;">
										<Button type="error" size='small' shape="circle" icon="close" @click="removeDcList(item,items)"></Button>
									</div>
								</div>
							</div>
						</div>
					</Card>
				</div>
			</div>
			<Row class="margin-top-10 pageSty">
				<!--<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>-->
			</Row>
		</div>
		<component :is="compName" :dicListMess='dicListMess'></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'


	import addmess from './comp/addmess.vue'
	import addmessList from './comp/addmessList.vue'
	import mess from './comp/mess.vue'
	export default {
		name: 'char',
		mixins: [mixins],
		components: {
			addmessList,
			addmess,
			mess
		},
		data() {
			return {
				dicListMess: '',
				SpinShow: true,
				compName: '',
				dictionaryMess: [], //字典信息查找
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize:8
				},
				dictionary: [],
				//收索
				findMess: {
					gte_StartTime: '',
					lte_StartTime: '',
					lmmcLike: '',
					like_ScName: '',
					pageNum: 1,
					pageSize: 150
				}
			}
		},
		created() {
				this.SpinShow = false;
			this.getmess()
		},
		methods: {
			getmess() {
				var v = this
				this.$http.post(this.apis.DICTIONARY.QUERY , this.findMess).then((res) => {
					log('字典数据', res)
					v.dictionary = res.page.list
					this.SpinShow = false;
				})
			},
			colsemodal() {
				this.compName = ''
			},
			AddDc() {
				var v = this
				v.compName = 'addmess'
			},
			findMessList(mess) {
				var v = this
				this.$http.post(this.apis.DICTIONARY.QUERY, this.findMess).then((res) => {
					log('字典数据', res)
					v.dictionary = res.page.list
				})
			},
			removeDc(item, index) {
				this.util.del(this,this.apis.DICTIONARY.DELE,[item.lmdm],()=>{
                    this.getmess();
				});
			},
			AddDcList(item, index) {
				var v = this
				v.compName = 'addmessList'
				v.dicListMess = item.lmdm
			},
			findDicList(item, index, mess) {
				var v = this
				this.$http.post(this.apis.DICTIONARY_LIST.QUERY, {
					'zdlmdm': item.lmdm,
					'zdmcLike': mess
				}).then((res) => {
					log('字典数据', res)
					if(res.code === 200) {
						v.dictionary[index].zdxmList = res.page.list
					}
					return
				})
			},
			removeDcList(item, items) {
				var v = this
				swal({
				  title: "是删除数据?",
				  text: "",
				  icon: "warning",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
					v.$http.post(this.apis.DICTIONARY_LIST.DELE, {'ids': [items.zdId]}).then((res) =>{
						if(res.code===200){
							this.$Message.success(res.message);
						}else{
							this.$Message.warning(res.message);
						}
						v.getmess()
					})
				  } else {
				  }
				});
			},
			changrDcList(item, items) {
				var v = this
				v.compName = 'addmessList'
				v.dicListMess = '你好啊！'
			},
			pageChange(event) {
				var v = this
				v.page.pageNum = event
				v.AddDc(v.page)
			}
		}
	}
</script>
