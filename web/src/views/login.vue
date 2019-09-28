<style lang="less">
	@import '../styles/common.less';
    @import './login.less';
	.loginForm{
		width: 400px;
		position:absolute;
		top:250px;
		right: 150px;
		float: right;
		display: inline-block;
		/*background-image: url('/static/login-card.jpg');*/
		background-size: cover;
		padding: 16px;
		text-shadow:5px 5px 6px #282828;
	}
	.loginBg{
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		width: 100%;
		height: 100%;
		text-align: center;
		background:rgba(0,0,0,0.5);
	}

	.login-top{
		text-align: center;
	}
	.loginImg{
		width: 50%;
	}
	.login{
		background-color: rgba(0,0,0,0.5);
		text-shadow:5px 2px 6px #000;
	    .imgLeft{
	    	position: relative;
	    	.loginImg{
	    		width: 90%;
	    		position: absolute;
	    		bottom: 0;
			    border-radius: 50px;
	    	}
	    }
	    .from{
	    	/*position: relative;*/
	    	.loginTiT{
	    		/*position: absolute;*/
	    		/*top: -50px;*/
	    		text-align: center;
				text-shadow:5px 2px 6px #000;

	    	}
	    	.fromList{
	    		padding-top: 10px;
				text-shadow:5px 2px 6px #000;
	    	}
	    }

    }
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit" >

		<div class="loginForm">
			<Row>
				<div class="login-top">
					<h1 style="color: white;text-shadow:5px 2px 6px #000;font-family:'华文行楷';font-size: 35px">吉驾无忧管理服务平台</h1><br>
<!--					<img style="height: 150px;width: 150px" class="loginImg" src="/static/1024.png" alt="" />-->
				</div>
			</Row>
			<div style="position: absolute;top:150px;color: #ffffff;text-align: left;width: 100%;padding-left: 350px">
				<Row style="text-align: left;font-size: 50px;">

				</Row>
			</div>


			<Row style="padding-top: 20px;text-shadow:5px 2px 6px #000;">
				<div class="body-O from">

					<Form ref="loginForm" :model="form" :rules="rules">
						<div class="fromList">
							<FormItem prop="username">
								<Input v-model="form.username" placeholder="请输入用户名"  class="ivu-input">
								<span slot="prepend">
		                                    <Icon :size="16" type="md-person"></Icon>
		                                </span>
								</Input>
							</FormItem>
						</div>
						<div class="fromList">
							<FormItem prop="password">
								<Input type="password" v-model="form.password" placeholder="请输入密码" class="ivu-input">
								<span slot="prepend">
		                                    <Icon :size="14" type="md-lock"></Icon>
		                                </span>
								</Input>
							</FormItem>


						</div>
						<div class="fromList">
							<FormItem>
								<Button @click="handleSubmit" size="large"
										style="height: 50px;font-size: 22px;background-color: #21D4FD;
								background-image: linear-gradient(19deg, #21D4FD 0%, #B721FF 100%);
								color: #F0F0F0;border-radius: 25px;text-shadow:5px 2px 6px #000;" long

								>登 录</Button>


							</FormItem>
						</div>

					</Form>
				</div>
			</Row>
		</div>
		<div style="position: absolute;bottom:20px;color: #ffffff;text-align: center;width: 100%;text-shadow:5px 2px 6px #000;">
			<Row style="text-align: center;font-size: 20px">
				© Copyright Reserved 版权所有
			</Row>
			<Row style="text-align: center;font-size: 16px">
				武汉天弘腾创科技有限公司
			</Row>
			<Row style="text-align: center;font-size: 16px">

			</Row>
		</div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';

import menuList from '../data/list'
import {appRouter} from '../router/router';
export default {
    data () {
        return {
        	SpinShow:false,
            form: {
                username: 'admini',
                password: 'THXC-Web123**'
            },
            menus:[],
            rules: {
                username: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
	computed:{
		title(){
		    return this.$store.state.app.title;
		}
	},
    created(){
        menuList.menuTree = [];
    },
    methods: {
        handleSubmit () {
        	var v = this
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                	v.SpinShow = true
                	v.$http.post(this.apis.LOGIN.QUERY, this.form).then((res) =>{
                		if(res.code===200) {
                            Cookies.set('usermess', this.form.username);
                            Cookies.set('result', res.result);
                            sessionStorage.setItem("userInfo",JSON.stringify(res.result.userInfo));
                            v.initDict();
                            v.getMenuTree();
                            v.SpinShow = false
                        }else if(res.code===500){
                            this.$Message.error(res.message);
                            this.form.username='';
                            this.form.password='';
                		}else{
                            this.$Message.error("用户登陆失败，请重试！");
                            this.form.username='';
                            this.form.password='';
                		}
                		v.SpinShow = false
                	}).catch((error) =>{
                		v.SpinShow = false
                		log('error',error)
                	})
                }
            }),
            setTimeout(()=>{
            	v.SpinShow = false
            },500)
        },
        getMenuTree(){
        	var v = this
        	this.$http.get(this.apis.USERROOT.GET_MENU_TREE).then((res) =>{
        		if(res.code===200){
                    v.session.setItem('menuList',res.result)
//                  menuList.menuTree = res.result;
                    this.addToMenuList(res.result);
                    this.$router.push('home')

                }
        	}).catch((error) =>{
        		log(error)
        	})
        },
        addToMenuList(list){
            for(let r of list){
                menuList.menuList.push(r.name);
                if (r.children){
                    this.addToMenuList(r.children);
                }
            }
        },
        getMenuList(){
        	this.$http.get(this.apis.USERROOT.GET_MENU_LIST).then((res) =>{
        		if(res.code===200){
                    menuList.menuList = res.result;
        		    this.getMenuTree();
                }
        	}).catch((error) =>{
        		log(error)
        	})
        },
        addToList(list){
            for (let r of list){
                this.menus.push(r);
                if (r.children){
                    for (let c of r.children){
                        c.pid = r.name;
                    }
                    this.addToList(r.children);
                }
            }
        },
        initDict(){
            this.$http.get(this.apis.DICTIONARY.QUERY,{params:{pageSize:10000}}).then((res) =>{
                if(res.code===200){
                    let dictMap = new Map();
                    for (let r of res.page.list){
                        let a = [];
                        for (let e of r.zdxmList){
                            a.push({key:e.zddm,val:e.zdmc,color:e.by1});
                        }
                        dictMap.set(r.lmdm,a)
                    }
                    this.session.setItem('dictMap',dictMap)
                }
            }).catch((error) =>{
                log(error)
            })
        },
        initMenu(){
            this.addToList(appRouter,this.menus);
            for (let r of this.menus){
                delete r.children;
                delete r.component;
            }

            let params = {menus:JSON.stringify(this.menus)}
            this.$http.post(this.apis.USERROOT.INIT_MENU,params).then((res) =>{
                if(res.code===200){
                    log(res);
                }
            }).catch((error) =>{
                log(error)
            })
        }
    }
};
</script>

<style>
.ivu-input /deep/ input{
	height: 50px;
	font-size: 20px;
}
</style>
