<template>
	<view>
		<view class="bg">
			<view style="display: flex;align-content: center;margin: 48upx 0 0 64upx;">
				<img :src="user.yhTx" style="border-radius: 50%;width: 132upx;height: 132upx;" @tap='tomymess()'>
				<view class="personMessage">
					<view style="margin-bottom: 10upx;font-size:20px;
						font-family:PingFangSC-Regular;
						font-weight:400;
						color:rgba(255,255,255,1);
						line-height:28px;" @click="tomymess()">{{user.yhXm}}</view>
					<view @click="tomymess()">{{user.yhZh}}</view>
				</view>
				<img :src="user.yhZsyqmImg" @tap="toCode" style="position: absolute;width: 74upx;height: 74upx;top: 86upx;right:56upx ;">
			</view>
		</view>
		<view class="btn">
			<view class="item" :style="{borderBottom:index===btnList.length-1?'none':'2upx solid #DFE7EE'}" v-for="(item,index) in btnList" @tap="toPage(item)">
				<view style="display: flex;align-items: center;">
					<img :src="item.src" style="margin-right:12upx;width: 40upx;height: 40upx;">
					<text>{{item.text}}</text>
				</view>
				<img src="/static/img/my/right.png" style="width: 70upx;height: 70upx;">
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'

	export default {
		computed: {
			...mapState(['hasLogin'])
		},
		data() {
			return {
				user: {},
				btnList: [{
						src: '/static/img/my/ljjf.png',
						text: '立即缴费',
						toPath: '/pages/goMoney/goMoney'
					},
					{
						src: '/static/img/my/smrz.png',
						text: '实名认证',
						toPath: '/pages/rellyName/rellyName'
					},
					{
						src: '/static/img/my/yqjl.png',
						text: '邀请奖励',
						toPath: '/pages/yqJl/yqJl'
					},
					{
						src: '/static/img/my/wdzd.png',
						text: '我的账单',
						toPath: 'account/account'
					}
				]
			}
		},
		onLoad() {
			this.getUser()
		},
		onShow() {
			var data = uni.getStorageSync('usermess')
			if(data.yhLx == 'zy' && this.btnList[this.btnList.length-1].text != '我的学员'){
				this.btnList.push({
						src: '/static/img/my/wdtd.png',
						text: '我的学员',
						toPath: '/pages/main/user/mystudent/mystudent'
					})
			}else if((data.yhLx == '1' && this.btnList[this.btnList.length-1].text != '我的团队')){
				this.btnList.push({
						src: '/static/img/my/wdtd.png',
						text: '我的团队',
						toPath: '/pages/myTeam/myTeam'
				})
			}else if((data.yhLx == '3' && this.btnList[this.btnList.length-1].text != '我的团队')){
				this.btnList.push({
						src: '/static/img/my/wdtd.png',
						text: '我的团队',
						toPath: '/pages/myTeam/myTeam'
				})
			}
		},
		methods: {
			tomymess(){
				uni.navigateTo({
					url: '../../myMess/myMess',
				})
			},
			...mapMutations(['logout']),
			bindLogin() {
				uni.navigateTo({
					url: '../../login/login',
					// #ifdef APP-PLUS
					// #endif
				});
			},
			bindLogout() {
				this.logout();
				/**
				 * 如果需要强制登录跳转回登录页面
				 */
				uni.reLaunch({
					url: '../../login/login',
				});
			},
			getUser() {
				//获取基本信息
				this.$http.post(this.apis.USERMESS).then(res => {
					if (res.code == 200) {
						this.user = res.result
					} else {
						uni.showToast({
							title: res.message,
							duration: 2000,
							icon:'none'
						});
					}
				}).catch(err => {})
			},
			toCode() {
				uni.navigateTo({
					url: 'code/code',
				});
			},
			toPage(item) {
				if (item.text == '立即缴费' && this.user.yhZt != '1') { //源控制语句
					//弹出提示框
					return
				}
				if(item.toPath == '/pages/myTeam/myTeam'){
					uni.switchTab({
						url:'../team/index'
					})
				}else{
					uni.navigateTo({
						url: item.toPath,
					});
				}
				
			}
		}
	}
</script>

<style>
	.bg {
		padding-top: 1upx;
		width: 750upx;
		height: 338upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
	}

	.btn {
		width: 678upx;
		height: 600upx;
		background: rgba(255, 255, 255, 1);
		box-shadow: 4upx 4upx 16upx 0upx rgba(179, 190, 233, 0.5);
		border-radius: 16upx;
		margin: -106upx auto 0;
	}

	.item {
		width: 628upx;
		height: 120upx;
		margin: 0 auto;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.personMessage {
		display: inline-block;
		font-size: 28upx;
		font-family: PingFangSC-Regular;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: center;
		margin-left: 30upx;
	}
</style>
