<style lang="less">
	@import "../../styles/common.less";
	.homeE{
		.indexCarType{
			border-bottom: solid 1px #C0C0C0;
			box-shadow: 2px 5px 5px #888888;
		}
		.divpadd{
			box-shadow: 2px 5px 5px #888888;
    		border: solid 2px #ded9d9;
			.divbgcolor{
				height: 250px;
				/*background: rgba(0,0,0,0.5);*/
				background-color: #fff;
			}
		}
	}
	.lc{
		font-size: 18px;
		font-family: "PingFang SC";
		font-weight: 600;
		color: rgb(73, 73, 73);
		line-height:25px;
	}
</style>
<template>
	<div class="box" style="height: 100%;background:#fff">
		<component :is="componentName"></component>
		<div  class="homeE" style="padding: 5px 3px;">
			<Row style="text-align: right;padding-right: 20px;padding-top: 20px">
				<Icon style="cursor: pointer" type="md-refresh" size="24" @click="getpager(1)"/>
			</Row>
			<Row :gutter="8" style="padding: 80px 0">
				<Col :xs="24" :sm="12" :md="6" style="text-align: center" >
					<img src="/static/home/1.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="hf">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待确认回访</div>
					<div style="font-size: 24px;color: #2DAA6B">{{pop.hf}}人</div>
				</Col>
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/2.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="sl">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待确认受理</div>
					<div style="font-size: 24px;color: #F89124">{{pop.dsl}}人</div>
				</Col>
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/3.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="ksf">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待缴考试费</div>
					<div style="font-size: 24px;color: #F25F5F">{{pop.djf}}人</div>
				</Col>
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/4.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="fp">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待分配专员</div>
					<div style="font-size: 24px;color: #2DAA6B">{{pop.dfp}}人</div>
				</Col>
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-person" color="#515a6e" :iconSize="50" intro-text="A类学员(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="md-person" color="#17233d" intro-text="B类学员(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#515a6e" intro-text="未实名认证(人)"></infor-card>-->
<!--				</Col>-->
			</Row>
			<Row :gutter="8" >
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/5.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="lr">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待录入成绩</div>
					<div style="font-size: 24px;color: #AB55E8">{{pop.dlr}}人</div>
				</Col>
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/6.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="fy">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待确认分佣</div>
					<div style="font-size: 24px;color:#DB3E3E">{{pop.dfy}}笔</div>
				</Col>
				<Col :xs="24" :sm="12" :md="6" style="text-align: center">
					<img src="/static/home/7.png" style="cursor: pointer;height: 100px;width: 100px" alt="" @click="peixunfei">
					<div>---------------------------------------------------------------</div>
					<div class="lc">待付培训费</div>
					<div style="font-size: 24px;color: #56A1F1">{{pop.pxf}}笔</div>
				</Col>
			</Row>
		</div>
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-people" color="#2db7f5" intro-text="今日注册用户(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-person" color="#2db7f5" :iconSize="50" intro-text="今日新增A类学员(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="md-person" color="#2db7f5" intro-text="今日新增B类学员(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#2db7f5" intro-text="今日新增未实名认证(人)"></infor-card>-->
<!--				</Col>-->
<!--			</Row>-->
<!--		</div>-->
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-call" color="#2d8cf0" intro-text="待确认回访(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-clipboard-outline" color="#64d572" :iconSize="50" intro-text="待确认受理(人)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-person" color="#64d572" :iconSize="50" intro-text="待确认分佣(笔)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="md-person" color="#ffd572" intro-text="代付培训费(科二)"></infor-card>-->
<!--				</Col>-->

<!--			</Row>-->
<!--		</div>-->
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="logo-yen" color="#ffd572" intro-text="待缴考试费(科一)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-people" color="#2d8cf0" intro-text="待录入成绩(科一)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="待分配专员(科二科三)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="代付培训费(科三)"></infor-card>-->
<!--				</Col>-->
<!--			</Row>-->
<!--		</div>-->
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="logo-yen" color="#ffd572" intro-text="待缴考试费(科二)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-people" color="#2d8cf0" intro-text="待录入成绩(科二)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="待缴考试费(科三)"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="待录入成绩(科三)"></infor-card>-->
<!--				</Col>-->
<!--			</Row>-->
<!--		</div>-->
<!--		<div class="homeE" style="padding: 5px 3px">-->

<!--			<eLine id-name="transfer_count" intro-text="待录入成绩(科三)"></eLine>-->


<!--		</div>-->
	</div>
</template>

<script>
	import inforCard from './components/inforCard.vue'
	import eLine from './compEcharts/line.vue'
	import yPie from './compEcharts/yearPie.vue'
	import safeline from './compEcharts/safeline.vue'
	import listpie from './compEcharts/listPie.vue'
	import extra from './compEcharts/extrabar.vue'
	import scbar from './compEcharts/scbar.vue'
	import risk from './compEcharts/riskRecord.vue'
	import echarts from 'echarts';

	import csMessbar from './compEcharts/comp/csMessbar'

	export default {
		name: 'home',
		components: {
			inforCard,eLine,yPie,
			safeline,listpie,extra,scbar,risk,csMessbar
		},
		data() {
			return {
                userType:'',
                showChart:false,
                componentName:'',
				count: {
                    lqby:0,
                    total: 0,
                    online: 0,
                    stop: 0,
                    offline: 0
				},
				pop:{
					dfp: 0,
					dfy: 0,
					djf: 0,
					dlr: 0,
					dsl: 0,
					hf: 0,
					pxf: 0
				}
			};
		},
        computed:{
            title(){
                return this.$store.state.app.title;
            },
            echData(){
                return this.$store.state.app.ech
            }
        },
		watch:{
			echData:function (n,o) {
				this.componentName = 'csMessbar'
			}
		},
		created() {
			this.initChart()
			this.getpager()
		},
		methods: {
			hf(){
				this.$router.push('/platform/huifang')
			},
			sl(){
				this.$router.push('/platform/dsl')
			},
			lr(){
				this.$router.push('/platform/dlr')
			},
			fp(){
				this.$router.push('/platform/dfp')
			},
			fy(){
				this.$router.push('/platform/tx')
			},
			ksf(){
				this.$router.push('/platform/kaoshifei')
			},
			peixunfei(){
				this.$router.push('/platform/peixunfei')
			},

			getpager(id){
				if (id == 1){

					this.$Message.success("已刷新")
				}
				this.$http.post('/api/ptyh/sytj',{}).then((res)=>{
					if (res.code == 200){
						this.pop = res.result
					}else {
						this.$Message.error(res.message)
					}
				})
			},
			initChart(){
				var v = this
				this.$nextTick(() => {
					var dataSourcePie = echarts.init(document.getElementById("yhfx"));
					const option = {
						title : {
							text: '某站点用户访问来源',
							subtext: '纯属虚构',
							x:'center'
						},
						tooltip : {
							trigger: 'item',
							formatter: "{a} <br/>{b} : {c} ({d}%)"
						},
						legend: {
							orient: 'vertical',
							left: 'left',
							data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
						},
						series : [
							{
								name: '访问来源',
								type: 'pie',
								radius : '55%',
								center: ['50%', '60%'],
								data:[
									{value:335, name:'直接访问'},
									{value:310, name:'邮件营销'},
									{value:234, name:'联盟广告'},
									{value:135, name:'视频广告'},
									{value:1548, name:'搜索引擎'}
								],
								itemStyle: {
									emphasis: {
										shadowBlur: 10,
										shadowOffsetX: 0,
										shadowColor: 'rgba(0, 0, 0, 0.5)'
									}
								}
							}
						]
					};
					dataSourcePie.setOption(option);
					window.addEventListener('resize', function () {
						dataSourcePie.resize();
					});
				});
			},
		}
	};
</script>
