<style lang="less">
  /*@import "./homepage/home";*/
</style>
<template>
  <div id="homeSty" class="box">
    <!--<a @click="pay">支付测试内容</a>-->
    <div class="body">
      <div style="height: 100%;">
        <home-index v-if="tabId=='tab-home'"></home-index>
        <user-index v-else-if="tabId=='tab-user'"></user-index>
      </div>
    </div>
    <div class="fooder">
      <mt-tabbar v-model="tabId">
        <mt-tab-item id="tab-home">
          <div style="margin-bottom: 0.08rem">
            <i class="iconfont icon-shouye1"></i>
          </div>
          首页
        </mt-tab-item>
        <mt-tab-item id="tab-user">
          <div style="margin-bottom: 0.08rem">
            <i class="iconfont icon-gerenzhongxin"></i>
          </div>
          我的
        </mt-tab-item>
      </mt-tabbar>
    </div>
  </div>
</template>

<script>
  import { Header, Swipe, SwipeItem , Tabbar, TabItem,Button, TabContainer, TabContainerItem } from 'mint-ui';
  import homeIndex from '@/views/homepage/homeIndex.vue'
  import userIndex from '@/views/myCenter'
  export default {
    name: "index",
    components:{
      [Header.name]: Header,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [Tabbar.name]: Tabbar,
      [TabItem.name]: TabItem,
      [Button.name]:Button,
      [TabContainer.name]:TabContainer,
      [TabContainerItem.name]:TabContainerItem,
      homeIndex,userIndex
    },
    data(){
      return{
        tabId:this.$store.state.app.tabId
      }
    },
    watch:{
      tabId:function (n,o) {
        this.$store.commit('M_tabId', n)
      }
    },
    created(){

    },
    methods:{

        pay(){
          this.$http.post(this.apis.CPPAY,{ddZftd:2,cpId:2}).then((res)=>{
            console.log(res)
            if(res.code==200){
                let v = res.result;
              WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                  "appId":v.appId,     //公众号名称，由商户传入
                  "timeStamp":v.timeStamp,         //时间戳，自1970年以来的秒数
                  "nonceStr":v.nonceStr, //随机串
                  "package":v.package,
                  "signType":v.signType,         //微信签名方式：
                  "paySign":v.paySign //微信签名
                },
                function(res){
                  if(res.err_msg == "get_brand_wcpay_request:ok" ) {

                  }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
              );
            }else {
              Toast(res.message)
            }
          }).catch((err)=>{
            console.log(err);
            alert(1);
          })
        }
    }
  }
</script>

<style scoped>

</style>
