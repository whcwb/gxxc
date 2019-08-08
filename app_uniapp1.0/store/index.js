import Vue from 'vue'
import Vuex from 'vuex'
import createLogger from 'vuex/dist/logger';
Vue.use(Vuex)

const store = new Vuex.Store({
	plugins: [createLogger()],
    state: {
        /**
         * 是否需要强制登录
         */
        forcedLogin: false,
        hasLogin: false,
        userName: "",
		userID:"",
		payMess:null,//支付信息
		signUrl:'444'	 //签名
    },
    mutations: {
		// login(state, userName) {
		//     state.userName = userName || '新用户';
		//     state.hasLogin = true;
		// },
        login(state,data) {
            // state.userName = userName || '新用户';
			console.log('^',state)
            state.hasLogin = true;
			uni.setStorageSync('userInfo', data);
        },
        logout(state) {
            state.hasLogin = false;
        },
		setPayMess(state,data){
			state.payMess=data
		},
		setSignUrl(state,data){
			state.signUrl=data
		}
    }
})

export default store
