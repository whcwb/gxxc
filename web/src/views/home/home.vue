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
				height: 220px;
				/*background: rgba(0,0,0,0.5);*/
				background-color: #fff;
			}
		}
	}
</style>
<template>
	<div class="box" style="height: 100%;background:#fff">
<!--		<component :is="componentName"></component>-->
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-people" color="#2d8cf0" intro-text="平台用户"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-person" color="#64d572" :iconSize="50" intro-text="A类学员"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="md-person" color="#ffd572" intro-text="B类学员"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="未实名认证"></infor-card>-->
<!--				</Col>-->
<!--			</Row>-->
<!--		</div>-->
<!--		<div  class="homeE" style="padding: 5px 3px;">-->
<!--			<Row :gutter="8" class="margin-bottom-10 indexCarType">-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="user_created_count" :end-val="count.total" iconType="ios-people" color="#2d8cf0" intro-text="今日注册用户"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="visit_count" :end-val="count.lqby"  iconType="ios-person" color="#64d572" :iconSize="50" intro-text="今日新增A类学员"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="collection_count" :end-val="count.stop" iconType="md-person" color="#ffd572" intro-text="今日新增B类学员"></infor-card>-->
<!--				</Col>-->
<!--				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">-->
<!--					<infor-card id-name="transfer_count" :end-val="count.offline" iconType="md-people" color="#f25e43" intro-text="今日新增未实名认证"></infor-card>-->
<!--				</Col>-->
<!--			</Row>-->
<!--		</div>-->
<!--		<div style="padding: 5px 3px;height: 600px">-->
<!--			<Row>-->
<!--				<Col span="6">-->
<!--					<div style="height: 100%;width: 100%"  id="yhfx"></div>-->
<!--				</Col>-->

<!--			</Row>-->
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
		},
		methods: {
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
