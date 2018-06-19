const app = {
    state: {
      tabId:2,
      userMess:'',
    },
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
