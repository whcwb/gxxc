<template>
   <!-- <view class="content">
        <view class="btn-row">
            <button v-if="!hasLogin" type="primary" class="primary" @tap="bindLogin">登录</button>
            <button v-if="hasLogin" type="default" @tap="bindLogout">退出登录</button>
        </view>
    </view> -->
	
	
	<view>
		<view class="bg">
			<view style="display: flex;align-content: center;margin: 48upx 0 0 64upx;">
				<img src="/static/index/1.png" style="border-radius: 50%;width: 132upx;height: 132upx;">	
				<view class="personMessage">
					<view style="margin-bottom: 10upx;">李文超</view>
					<view style="">15496854152</view>
				</view>	
				<img src="/static/img/code.png" @tap="toCode" style="position: absolute;width: 74upx;height: 74upx;top: 86upx;right:56upx ;">	
			</view>
		</view>
		<view class="btn">
			<view class="item" :style="{borderBottom:index===btnList.length-1?'none':'2upx solid #DFE7EE'}" v-for="(item,index) in btnList"  @tap="toPage(item)">
				<view style="display: flex;align-items: center;">
					<img :src="item.src" style="margin-right:12upx;width: 40upx;height: 40upx;">
					<text>{{item.text}}</text>
				</view>
				<img src="/static/my/right.png" style="width: 70upx;height: 70upx;">
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
				btnList:[
					{
						src:'/static/my/my/3.png',
						text:'立即缴费',
						toPath:'/pages/goMoney/goMoney'
					},
					{
						src:'/static/my/my/3.png',
						text:'实名认证',
						toPath:'/pages/rellyName/rellyName'
					},
					{
						src:'/static/my/my/1.png',
						text:'邀请奖励',
						toPath:'/pages/yqJl/yqJl'
					},
					{
						src:'/static/my/my/2.png',
						text:'我的账单',
						toPath:'account/account'
					},
					{
						src:'/static/my/my/4.png',
						text:'我的团队',
						toPath:'/pages/myTeam/myTeam'
					}
				]
			}
		},
        methods: {
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
			toCode(){
				uni.navigateTo({
				    url: 'code/code',				
				});
			},
			toPage(item){
				console.log(item);
				uni.navigateTo({
				    url: item.toPath,				
				});
			}
        }
    }
</script>

<style>
.bg{
	padding-top: 1upx;
	width:750upx;
	height:338upx;
	background:linear-gradient(132deg,rgba(59,147,253,1) 0%,rgba(60,128,253,1) 41%,rgba(55,84,252,1) 100%);
}

.btn{
	width:678upx;
	height:580upx;
	background:rgba(255,255,255,1);
	box-shadow:4upx 4upx 16upx 0upx rgba(179,190,233,0.5);
	border-radius:16upx;
	margin: -106upx auto 0;
}

.item{
	width: 628upx;
	height: 142upx;
	margin: 0 auto;
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.personMessage{
	display: inline-block;
	font-size:28upx;
	font-family:PingFangSC-Regular;
	font-weight:400;
	color:rgba(255,255,255,1);
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: center;
	margin-left: 30upx;
}
</style>
