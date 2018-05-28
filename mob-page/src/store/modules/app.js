//import {otherRouter, appRouter} from '@/router/router';
import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
      jf:false
    },
    mutations: {
      CHjf(state,data){
        state.jf = data
      },
    }
};

export default app;
