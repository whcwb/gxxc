//import {otherRouter, appRouter} from '@/router/router';
import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
      tabId:'tab-home'
    },
    userMess:'',
    mutations: {
      M_tabId(state,data){
        state.tabId = data
      },
      M_userMess(state,data){
        state.userMess = data
      },
    }
};

export default app;
