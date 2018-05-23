<style lang="less">
  @import "./myMess";
</style>
<template>
  <div id="myMess" class="box" style="position: relative">
    <div>
      <headTit tit="信息中心"></headTit>
    </div>
    <div class="center_tit">
      <div class="box-row" style="border-bottom: solid 2px #dedede">
        <div class="touxiang" @click="$router.push({name:'myMess'})">
          <img src="../../img/titcenter.png" alt="">
        </div>
        <div class="body-O userMess">
          <div class="name">
            张华
          </div>
          <div class="tjr">
            推荐人:全帅
          </div>
        </div>
        <div class="rzMess" v-show="false">
          <div class="name">
            <Icon type="checkmark-circled"
                  size="18"
                  color="#2bd865"></Icon>
            已实名
          </div>
          <div class="money">
            <Icon type="checkmark-circled"
                  size="18"
                  color="#2bd865"></Icon>
            已交费
          </div>
        </div>
        <div class="rzMess">
          <Button type="ghost" @click="$router.push({name:'rz'})">去认证</Button>
        </div>
      </div>
      <div class="box-row" style="padding-top:0.2rem ">
        <div class="yongjin">
          <div>
            可提现佣金
          </div>
          <div>
            0.00元
          </div>
        </div>
        <div class="body-O tixian">
          <Button type="ghost" @click="$router.push({name:'pay'})">去交费</Button>
        </div>
      </div>
    </div>
    <div class="body func">
      <div class="box-row funcLink"
           v-for="(item,index) in messList"
           @click="goFunc(item)">
          <div class="tit">{{item.tit}}</div>
          <div class="body-O" style="text-align: right">{{item.mess}}</div>
          <div class="link">
            <Icon type="chevron-right"></Icon>
          </div>
      </div>
    </div>
    <div style="padding:0.3rem ">
      <Button type="error" long
              @click="$router.push({name:'login'})"
        style="padding: 0.18rem">
        退出登录</Button>
    </div>
    <component
      :is="compName"></component>
  </div>
</template>

<script>
    import {Button ,Icon} from 'iview'
    import {Toast} from 'mand-mobile'
    import headTit from '../comp/headTit'
    import password from '../comp/center/password'
    import userMess from '../comp/center/userMess'
    export default {
        name: "index",
        components:{
          Button,Icon,headTit,
          password,userMess
        },
        data(){
          return{
            compName:'',
            messList:[
              {
                tit:'个人信息',
                mess:'',
                link:'userMess'
              },
              {
                tit:'手机绑定',
                mess:'',
                link:'phone'
              },
              {
                tit:'密码修改',
                mess:'********',
                link:'password'
              }
            ]
          }
        },
        methods:{
          goFunc(it){
            var v =this
              if(it.link=='password'){
                v.compName ='password'
              }else if(it.link=='userMess'){
                v.compName ='userMess'
              }else {
                Toast.succeed(it.link)
              }
          }
        }
    }
</script>

<style scoped>

</style>
