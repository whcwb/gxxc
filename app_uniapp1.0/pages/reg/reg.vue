<template>
	<!-- <view class="content">
        <view class="input-group">
            <view class="input-row border">
                <text class="title">账号：</text>
                <m-input type="text" focus clearable v-model="account" placeholder="请输入账号"></m-input>
            </view>
            <view class="input-row border">
                <text class="title">密码：</text>
                <m-input type="password" displayable v-model="password" placeholder="请输入密码"></m-input>
            </view>
            <view class="input-row">
                <text class="title">邮箱：</text>
                <m-input type="text" clearable v-model="email" placeholder="请输入邮箱"></m-input>
            </view>
        </view>
        <view class="btn-row">
            <button type="primary" class="primary" @tap="register">注册</button>
        </view>
    </view> -->
	<view style="width: 100%;background:rgba(255,255,255,1);">
		<img src="/static/img/banner.png" style="height: 336upx;width: 750upx;">
		<view class="inputMess" v-for="item in inputList">
			<input class="uni-input input" :placeholder="item.placeholder" v-model="item.val" />
			<view v-if="item.placeholder==='请输入验证码'" class="inputCodeTip">请获取验证码</view>
		</view>
		<view class="btn" @click="reg">
			立即注册
		</view>
		<view style="margin-bottom: 114upx;display: flex;justify-content: center;align-items: center;">
			<checkbox value="c" checked='cb' />我已阅读并同意
			<text style="font-size:28upx;font-weight:400;color:rgba(51,128,168,1);" @click="openPopup">
				《注册协议》
			</text>
		</view>
		<uni-popup ref="popup" type="center">
			<view class="AgreementMessSty" bindtap="">
				<view class='messTop'>
					用户注册协议
				</view>
				<view class='messcontent' style="overflow-y: auto;">
					<view class="fontsty">
						用户在注册之前，应当仔细阅读本协议，并同意遵守本协议后方可成为注册用户。一旦注册成功，则用户与吉驾无忧之间自动形成协议关系，用户应当受本协议的约束。用户在使用特殊的服务或产品时，应当同意接受相关协议后方能使用。
					</view>
					<view>
						<view class="fonttit">一、协议声明</view>
						<view class="fontsty">
							1.1 本协议则可由武汉天弘腾创科技有限公司随时更新，用户应当及时关注并同意本站不承担通知义务。本站的通知、公告、声明或其它类似内容是本协议的一部分
						</view>
					</view>
					<view>
						<view class="fonttit">二、服务内容</view>
						<view class="fontsty">
							2.1 武汉天弘腾创科技有限公司的具体内容由本站根据实际情况提供。
						</view>
						<view class="fontsty">
							2.2 本站仅提供相关的网络服务，除此之外与相关网络服务有关的设备(如个人电脑、手机、及其他与接入互联网或移动网有关的装置)及所需的费用(如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费)均应由用户自行负担。
						</view>
					</view>
					<view>
						<view class="fonttit">三、用户帐号</view>
						<view class="fontsty">
							3.1 经本站注册系统完成注册程序并通过身份认证的用户即成为正式用户，可以获得本站规定用户所应享有的一切权限；未经认证仅享有本站规定的部分学员权限。武汉天弘腾创科技有限公司有权对学员的权限设计进行变更。
						</view>
						<view class="fontsty">
							3.2
							用户只能按照注册要求使用真实姓名，及身份证号注册。用户有义务保证密码和帐号的安全，用户利用该密码和帐号所进行的一切活动引起的任何损失或损害，由用户自行承担全部责任，本站不承担任何责任。如用户发现帐号遭到未授权的使用或发生其他任何安全问题，应立即修改帐号密码并妥善保管，如有必要，请通知本站。因黑客行为或用户的保管疏忽导致帐号非法使用，本站不承担任何责任。
						</view>
					</view>
					<view>
						<view class="fonttit">四、使用规则</view>
						<view class="fontsty">
							4.1
							遵守中华人民共和国相关法律法规，包括但不限于《中华人民共和国计算机信息系统安全保护条例》、《计算机软件保护条例》、《最高人民法院关于审理涉及计算机网络著作权纠纷案件适用法律若干问题的解释(法释[2004]1号)》、《全国人大常委会关于维护互联网安全的决定》、《互联网电子公告服务管理规定》、《互联网新闻信息服务管理规定》、《互联网著作权行政保护办法》和《信息网络传播权保护条例》等有关计算机互联网规定和知识产权的法律和法规、实施办法。
						</view>
						<view class="fontsty">
							4.2 用户对其自行发表、上传或传送的内容负全部责任，所有用户不得在本站任何页面发布、转载、传送含有下列内容之一的信息，否则本站有权自行处理并不通知用户：
							<br>(1)违反宪法确定的基本原则的；<br>
							(2)危害国家安全，泄漏国家机密，颠覆国家政权，破坏国家统一的；<br>
							(3)损害国家荣誉和利益的；<br>
							(4)煽动民族仇恨、民族歧视，破坏民族团结的；<br>
							(5)破坏国家宗教政策，宣扬邪教和封建迷信的；<br>
							(6)散布谣言，扰乱社会秩序，破坏社会稳定的；<br>
							(7)散布淫秽、色情、赌博、暴力、恐怖或者教唆犯罪的；<br>
							(8)侮辱或者诽谤他人，侵害他人合法权益的；<br>
							(9)煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的；<br>
							(10)以非法民间组织名义活动的；<br>
							(11)含有法律、行政法规禁止的其他内容的。
						</view>
						<view class="fontsty">
							4.3
							用户承诺对其发表或者上传于本站的所有信息(即属于《中华人民共和国著作权法》规定的作品，包括但不限于文字、图片、音乐、电影、表演和录音录像制品和电脑程序等)均享有完整的知识产权，或者已经得到相关权利人的合法授权；如用户违反本条规定造成本站被第三人索赔的，用户应全额补偿本站一切费用(包括但不限于各种赔偿费、诉讼代理费及为此支出的其它合理费用)；
						</view>
						<view class="fontsty">
							4.4
							当第三方认为用户发表或者上传于本站的信息侵犯其权利，并根据《信息网络传播权保护条例》或者相关法律规定向本站发送权利通知书时，用户同意本站可以自行判断决定删除涉嫌侵权信息，除非用户提交书面证据材料排除侵权的可能性，本站将不会自动恢复上述删除的信息；
							<br>(1)不得为任何非法目的而使用网络服务系统；<br>
							(2)遵守所有与网络服务有关的网络协议、规定和程序；<br>
							(3)不得利用本站进行任何可能对互联网的正常运转造成不利影响的行为；<br>
							(4)不得利用本站进行任何不利于本站的行为。
						</view>
						<view class="fontsty">
							4.5 如用户在使用网络服务时违反上述任何规定，本站有权要求用户改正或直接采取一切必要的措施(包括但不限于删除用户张贴的内容、暂停或终止用户使用网络服务的权利)以减轻用户不当行为而造成的影响。
						</view>
					</view>
					<view>
						<view class="fonttit">五、隐私保护</view>
						<view class="fontsty">
							5.1 本站不对外公开或向第三方提供单个用户的注册资料及用户在使用网络服务时存储在本站的非公开内容，但下列情况除外： (1)事先获得用户的明确授权；
							<br>(1)事先获得用户的明确授权；<br>
							(2)根据有关的法律法规要求；<br>
							(3)按照相关政府主管部门的要求；<br>
							(4)为维护社会公众的利益。
						</view>
						<view class="fontsty">
							5.2 本站可能会与第三方合作向用户提供相关的网络服务，在此情况下，如该第三方同意承担与本站同等的保护用户隐私的责任，则本站有权将用户的注册资料等提供给该第三方。
						</view>
						<view class="fontsty">
							5.3 在不透露单个用户隐私资料的前提下，本站有权对整个用户数据库进行分析并对用户数据库进行商业上的利用。
						</view>
					</view>
					<view>
						<view class="fonttit">六、版权声明</view>
						<view class="fontsty">
							6.1 本站的文字、图片、音频、视频等版权均归永兴元科技有限公司享有或与作者共同享有，未经本站许可，不得任意转载。
						</view>
						<view class="fontsty">
							6.2 本站特有的标识、版面设计、编排方式等版权均属永兴元科技有限公司享有，未经本站许可，不得任意复制或转载。
						</view>
						<view class="fontsty">
							6.3 使用本站的任何内容均应注明“来源于武汉天弘腾创科技有限公司”及署上作者姓名，按法律规定需要支付稿酬的，应当通知本站及作者及支付稿酬，并独立承担一切法律责任。
						</view>
						<view class="fontsty">
							6.4 本站享有所有作品用于其它用途的优先权，包括但不限于网站、电子杂志、平面出版等，但在使用前会通知作者，并按同行业的标准支付稿酬。
						</view>
						<view class="fontsty">
							6.5 本站所有内容仅代表作者自己的立场和观点，与本站无关，由作者本人承担一切法律责任。
						</view>
						<view class="fontsty">
							6.6 恶意转载本站内容的，本站保留将其诉诸法律的权利。
						</view>
					</view>
					<view>
						<view class="fonttit">七、责任声明</view>
						<view class="fontsty">
							7.1 用户明确同意其使用本站网络服务所存在的风险及一切后果将完全由用户本人承担，武汉天弘腾创科技有限公司对此不承担任何责任。
						</view>
						<view class="fontsty">
							7.2 本站无法保证网络服务一定能满足用户的要求，也不保证网络服务的及时性、安全性、准确性。
						</view>
						<view class="fontsty">
							7.3 本站不保证为方便用户而设置的外部链接的准确性和完整性，同时，对于该等外部链接指向的不由本站实际控制的任何网页上的内容，本站不承担任何责任。
						</view>
						<view class="fontsty">
							7.4 对于因不可抗力或本站不能控制的原因造成的网络服务中断或其它缺陷，本站不承担任何责任，但将尽力减少因此而给用户造成的损失和影响。
						</view>
						<view class="fontsty">
							7.5 对于站向用户提供的下列产品或者服务的质量缺陷本身及其引发的任何损失，本站无需承担任何责任:
							<br>(1)本站向用户免费提供的各项网络服务；<br>
							(2)本站向用户赠送的任何产品或者服务。
						</view>
						<view class="fontsty">
							7.6 本站有权于任何时间暂时或永久修改或终止本服务(或其任何部分)，而无论其通知与否，本站对用户和任何第三人均无需承担任何责任。
						</view>
					</view>
					<view>
						<view class="fonttit">八、附则</view>
						<view class="fontsty">
							8.1 本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。
						</view>
						<view class="fontsty">
							8.2 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。
						</view>
						<view class="fontsty">
							8.3 本协议解释权及修订权归天弘腾创科技有限公司所有。
						</view>
					</view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import service from '../../service.js';
	import mInput from '../../components/m-input.vue';
	import uniPopup from "@/components/uni-popup/uni-popup.vue"
	export default {
		components: {
			mInput,
			uniPopup
		},
		data() {
			return {
				account: '',
				password: '',
				email: '',
				cb:false,
				inputList: [ //验证、提交可let临时数组or对象,若有新属性，可添加
					{
						placeholder: '请输入手机号',
						val: ''
					},
					{
						placeholder: '请输入验证码',
						val: ''
					},
					{
						placeholder: '请输入密码',
						val: ''
					},
					{
						placeholder: '请输入真实姓名',
						val: ''
					},
					{
						placeholder: '请输入身份证号',
						val: ''
					},
					{
						placeholder: '请输入邀请码',
						val: ''
					}
				]
			}
		},
		methods: {
			reg(){
				if(this.cb == false){
					this.openPopup()
				}else{
					cconsole.log(this.cb);
				}
			},
			openPopup() {
				this.$refs.popup.open()
			},
			closePopup() {
				this.$refs.popup.close()
				this.cb = true
			},
			toHt() {
				uni.navigateTo({
					url: '../center/learnCarFile/learnCarFile'
				})
			},
			register() {
				/**
				 * 客户端对账号信息进行一些必要的校验。
				 * 实际开发中，根据业务需要进行处理，这里仅做示例。
				 */
				if (this.account.length < 5) {
					uni.showToast({
						icon: 'none',
						title: '账号最短为 5 个字符'
					});
					return;
				}
				if (this.password.length < 6) {
					uni.showToast({
						icon: 'none',
						title: '密码最短为 6 个字符'
					});
					return;
				}
				if (this.email.length < 3 || !~this.email.indexOf('@')) {
					uni.showToast({
						icon: 'none',
						title: '邮箱地址不合法'
					});
					return;
				}

				const data = {
					account: this.account,
					password: this.password,
					email: this.email
				}
				service.addUser(data);
				uni.showToast({
					title: '注册成功'
				});
				uni.navigateBack({
					delta: 1
				});
			}
		}
	}
</script>

<style lang="less">
	.inputMess {
		position: relative;
		width: 100%;
		background: rgba(255, 255, 255, 1);
	}

	.input {
		height: 122upx;
		width: 678upx;
		border-bottom: 2upx solid #DFE7EE;
		margin: 0 auto;
	}

	.btn {
		width: 592upx;
		height: 104upx;
		background: linear-gradient(132deg, rgba(59, 147, 253, 1) 0%, rgba(60, 128, 253, 1) 41%, rgba(55, 84, 252, 1) 100%);
		box-shadow: 0 8upx 16upx 0 rgba(69, 124, 232, 0.5);
		border-radius: 52upx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 58upx auto 40upx;
		font-size: 40upx;
		font-weight: 400;
		color: rgba(255, 255, 255, 1);
	}

	.inputCodeTip {
		position: absolute;
		right: 48upx;
		top: 50%;
		transform: translateY(-50%);
		font-size: 32upx;
		font-weight: 400;
		color: rgba(37, 128, 222, 1);
	}

	/deep/ .input-placeholder {
		font-size: 32upx;
		font-weight: 400;
		color: rgba(200, 200, 200, 1);
	}

	/deep/ .uni-input {
		padding: 0
	}
	
	.AgreementMessSty{
		  background-color: #fff;
		  height: 100%;
		  font-size: 48rpx;
		  line-height: 1.8;
		  display:flex;
		  flex-direction:column;
		  justify-content:space-between;
		  
		  .messTop{
			font-size: 50rpx;
			font-weight: 700;
			text-align: center;
			color: #f75d42;
			padding: 35rpx 0;
			position: relative;
			
			.close{
			  position: absolute;
			  top: 10rpx;
			  left: 10rpx;
			  font-size: 30rpx!important;
			}
		  }
		  .messcontent{
			flex-grow:1;
			padding: 15rpx 20rpx;
			.fonttit{
			  text-align: left;
			  font-weight: 700;
			  font-size: 32rpx;
			}
			.fontsty{
				text-align: left;
			  font-size: 28rpx;
			  text-indent:50rpx;
			  margin-top: 15rpx;
			}
		  }
	}
</style>
