<script>
	import {  
        mapMutations  
    } from 'vuex';  
	export default {
		onLaunch: function() {
			console.log('App Launch');
			 uni.getStorage({//获得保存在本地的用户信息  
                key: 'uerInfo',  
                success:(res) => {  
                    this.login(res.data);  
                    uni.request({// 再次校验并刷新token的有效时间  
                        url: `${this.$serverUrl}/auth.php`,  
                        header: {  
                            "Content-Type": "application/x-www-form-urlencoded",  
                            "Token":res.data.token  
                        },  
                        data: {  
                            "username":res.data.user_name  
                        },  
                        method: "POST",  
                        success: (e) => {  
                            if (e.statusCode === 200 && e.data.code === 0) {  
                                this.login(e.data);  
                            }  
                        }  
                    })  
                }  
            });  
		},
		onShow: function() {
			console.log('App Show');
		},
		onHide: function() {
			console.log('App Hide');
		}
	}
</script>

<style lang="less">
	@import "./common/uni.css";

	/*每个页面公共css */
	page {
		min-height: 100%;
		display: flex;
	}

	/* #ifdef MP-BAIDU */
	page {
		width: 100%;
		height: 100%;
		display: block;
	}

	swan-template {
		width: 100%;
		min-height: 100%;
		display: flex;
	}

	/* 原生组件模式下需要注意组件外部样式 */
	custom-component {
		width: 100%;
		min-height: 100%;
		display: flex;
	}

	/* #endif */

	/* #ifdef MP-ALIPAY */
	page {
		min-height: 100vh;
	}

	/* #endif */
	
	//  flex布局
	//容器属性 横向布局 垂直对齐方式 用于弹性布局的 容器
	//横向布局 上 中 下  文本居中
	.pagebody{
		flex: 1;
		width: 100%;
		overflow: auto;
	}
	.colTop {
	  align-items: flex-start;
	}

	.colBottom {
	  align-items: flex-end;
	}

	.colCenter {
	  align-items: center;
	}

	.colText {
	  align-items: stretch;
	}

	//容器属性 横向 模块的分布
	.rowleft {
	  justify-content: flex-start;
	}

	.rowRight {
	  justify-content: flex-end;
	}

	.rowCenter {
	  justify-content: center;
	}
	.rowBetween{
	  justify-content: space-between;
	}
	.rowAuto { //模块平分
	  justify-content: space-around;
	}

	//项目属性
	.flexItemA {
	  align-self: auto;
	}

	.flexItemF {
	  align-self: flex-start;
	}

	.flexItemE {
	  align-self: flex-end;
	}

	.flexItemC {
	  align-self: center;
	}

	.flexItemB {
	  align-self: baseline;
	}

	.flexItemS {
	  align-self: stretch;
	}

	.box_col { //纵向
	  height: 100%;
	  display: flex;
	  flex-direction: column;
	  .box_col_100 {
		flex: 1;
	  }
	  .box_col_auto {
		flex: 1;
		overflow: auto;
	  }
	  .box_col_autoY {
		flex: 1;
		overflow-y: auto;
	  }
	  .box_col_autoX {
		flex: 1;
		overflow-x: auto;
	  }
	}

	.box_row { //横向
	  //height: 100%;
	  display: flex;
	  flex-direction: row;
	  .box_row_100 {
		flex: 1;
	  }
	  .box_row_1auto {
		flex: 1;
		overflow: auto;
	  }
	  .box_row_200 {
		flex: 2;
	  }
	  .box_row_2auto {
		flex: 2;
		overflow: auto;
	  }
	  .box_row_3auto {
		flex: 3;
		overflow: auto;
	  }
	  .box_row_4auto {
		flex: 4;
		overflow: auto;
	  }
	  .box_row_5auto {
		flex: 5;
		overflow: auto;
	  }
	}

	.box_row_z { //横向布局禁止换行
	  display: flex;
	  flex-direction: row;
	  flex-wrap: nowrap;
	  height: 100%;
	}

	.box_row_list { //横向布局自动换行
	  display: flex;
	  flex-direction: row;
	  flex-wrap: wrap;

	}
	
	
	// --------------------------
	/* 原生组件模式下需要注意组件外部样式 */
	m-input {
		width: 100%;
		min-height: 100%;
		display: flex;
	}

	.content {
		display: flex;
		// flex: 1;
		width: 100%;
		flex-direction: column;
		background-color: #ededed;
		// overflow: hidden;
	}

	.input-group {
		background-color: #F5F6F9;
		margin-top: 40upx;
		position: relative;
	}

	.input-group::before {
		position: absolute;
		right: 0;
		top: 0;
		left: 0;
		height: 1upx;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.input-group::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 0;
		height: 1upx;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.input-row {
		display: flex;
		flex-direction: row;
		position: relative;
	}

	.input-row .title {
		width: 20%;
		height: 50upx;
		min-height: 50upx;
		padding: 15upx 0;
		padding-left: 30upx;
		line-height: 50upx;
	}

	.input-row.border::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 15upx;
		height: 1upx;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.btn-row {
		margin-top: 50upx;
		padding: 20upx;
	}

	button.primary {
		background-color: #0faeff;
	}
</style>
