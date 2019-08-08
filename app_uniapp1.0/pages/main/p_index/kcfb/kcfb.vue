<template>
	<view class="box_col">
		<view class="box_row">
			<view class="Tit">
				<text>学车流程</text>
			</view>
		</view>
		<view class="tit">
			<view class="boby" style="text-indent: 50rpx;">
				考试顺序按照科目一、科目二、科目三、科目四一次进行，前一科目考试合格后，可以参加下一科目的考试;前一科目考试不合格的，继续该科目考试。</p>
			</view>
			<view class="boby" style="text-indent: 50rpx;">
				<b class='b'>1</b>科目一：考试主要考核道路交通安全法律法规、交通信号、通行规则等基本知识。科目一考试试卷由100道题目组成，题型为判断题和单项选择题，满分100分，90分合格。
			</view>
			<view class="boby" style="text-indent: 50rpx;">
				<b class='b'>2</b>科目二：小型汽车场地考试项目为“训练和考试均为5项”，即倒车入库、坡道定点停车与起步、侧方停车、曲线行驶和直角转弯。
			</view>
			<view class="boby" style="text-indent: 50rpx;">
				<b class='b'>3</b>科目三路口部分：道路驾驶技能和安全文明驾驶常识。基本项目有16项，包括上车准备、起步、直线行驶、变更车道、靠边停车、通过路口、路口左转弯、路口右转弯、通过人行横道线、通过学校区域、通过公共汽车站、会车、超车、掉头、百米加减档、夜间行驶。小型汽车考试里程不少于3公里，并抽取不少于20%进行夜间考试;不进行夜间考试的，应当进行模拟夜间灯光使用考试。
			</view>
			<view class="boby" style="text-indent: 50rpx;">
				<b class='b'>4</b>科目四理论考试部分：安全文明驾驶常识考试。主要内容包括，安全文明驾驶操作要求、恶劣气象和复杂道路条件下的安全驾驶知识、爆胎等紧急情况下的临危处置方法以及发生交通事故后的处置知识等内容。考试试卷由50道题目组成，题目以案例、图片、动画等形式为主，题型为判断题、单项选择题和多想选择题，满分100分，90分合格。
			</view>
			
		</view>
	</view>
</template>

<script>
	import uniBadge from "@/components/uni-badge/uni-badge.vue"
	export default {
		components: {uniBadge},
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.Tit{
	padding:0 46rpx;
	padding-top: 36rpx;
	font-size:20px;
	font-family:PingFangSC-Semibold;
	font-weight:600;
	color:rgba(51,51,51,1);
	line-height:28px;
}
.tit{
	padding: 0 46rpx;
	padding-top: 36rpx;
	font-size:32upx;
	font-family:PingFangSC-Semibold;
	font-weight:600;
	color:rgba(51,51,51,1);
	line-height:28px;
}
.boby{
	margin-top: 10rpx;
	font-size:28upx;
	font-family:PingFangSC-Regular;
	font-weight:400;
	color:rgba(51,51,51,1);
	line-height:50rpx;
}
.b{
	margin-right: 10rpx;
    border-radius: 50%;
    background-color: #5090F1;
    color: #fff;
    display:inline-block;
    text-align: center;
    line-height: 20px;
    height: 20px;
    width: 20px;
}
</style>
