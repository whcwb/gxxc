export default {
  filters: {
    yhLx: (val) => {//用戶类型
      switch (val){
        case '1':
          return '学员';
          break;
        case '2':
          return '专员';
          break;
        case '3':
          return '会员';
          break;
        default:
          return '用户类型'
          break;
      }
    },

    yhXm: (val) => {//用户姓名
      if (val) {
        return val
      }
      return '未实名'
    },

    yhTx: (val) => {
      if (val) {
        return val
      }
      return 'static/login/LOGO.png'
    },

    userGrade: (val) => {
      switch (val) {
        case '1':
          return '一级用户'
          break;
        case '2':
          return '二级用户'
      }
    },

    yhZhye(val) {//用户账户余额
      if (val == '') {
        return 0
      }
      return val
    },

    ddSfjx: (val) => {
      switch (val) {
        case '0':
          return '未交费'
          break;
        case '1':
          return '已交费'
      }
    },

    yhZt: function (val) {//用户状态
      switch (val) {
        case '0':
          return '审核中';
          break;
        case '1':
          return '已认证';
          break;
        case '2':
          return '审核驳回';
          break;
        case '-1':
          return '未认证';
          break;
        default:
          return val
          break;
      }
    },

    yhDqzt:(val)=>{
      switch (val){
        case "0":
          return '档案信息受理中'
          break;
        case "1":
          return '科目一进行中'
          break;
        case "2":
          return '科目二进行中'
          break;
        case "3":
          return '科目三进行中'
          break;
        case "4":
          return '科目四进行中'
          break
        default:
          return '完结'
      }
    },

    userInviteCount(val){
      if (val) {
        return val
      }
      return 0
    },

  },
  created(){
    this.util.GetUserMess(this, (res) => {})
    console.log('全剧初始化');
  },
  mounted(){
    console.log('全剧初始化2');
  }

}
