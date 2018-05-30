//import {otherRouter, appRouter} from '@/router/router';
import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
      tabId:'tab-home'
    },
    mutations: {
      M_tabId(state,data){
        state.tabId = data
      },
    }
};

export default app;
