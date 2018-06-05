<style lang="less">
  #myCenterInfo{
    position: relative;
    .saveName,.updatePassword{
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      background-color: #fff;
      z-index: 100;
      .BMinput{
        width: 100%;
        height: 0.75rem;
        outline:none;
        border: none;
        padding: 0 0.2rem;
        border-bottom: #2db7f5 0.1rem solid;
        color: #2db7f5;
        font-size: 0.36rem;
        font-weight: 600;
      }
    }
  }
</style>
<template>
    <div id="myCenterInfo" class="box">
      <div>
        <box-head tit="个人信息">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
      </div>
      <div class="body">
          <!-- 用户设置 -->
          <div style="padding-top: 30px;color: black;">
              <Row type="flex" justify="start">
                <Col span="24">
                <Card dis-hover>
                  <mt-cell title="头像" style="border-bottom: 1px #e9eaec solid;padding-bottom: 10px">
                    <img :src="userMess.yhTx" style="width: 1rem" alt="">
                      <!--<Avatar shape="square" icon="person" size="large" style="width: 50px;height: 50px;line-height: 50px"/>-->
                  </mt-cell>
                  <div @click="compname='bm'">
                    <mt-cell title="姓名" :value="userMess.yhBm"
                             style="border-bottom: 1px #e9eaec solid;"></mt-cell>
                  </div>
                  <div @click="compname='word'">
                    <mt-cell title="修改密码" value="****"></mt-cell>
                  </div>
                </Card>
                </Col>
              </Row>
              <Row type="flex" justify="start" style="margin-top: 20px">
                <Col span="24">
                  <span @click="goOut">
                      <Card dis-hover style="text-align: center">
                        <mt-cell title="退出登录"></mt-cell>
                      </Card>
                  </span>
                </Col>
              </Row>
          </div>
      </div>
      <component :is="compname"></component>
      </div>
</template>

<script>
    import {Card, Row, Col, Avatar, Tag, Alert, Button, Icon  } from 'iview'
    import {  Cell, Header , Toast} from 'mint-ui';
    import bm from './comp/bm'
    import word from  './comp/upWorld'
    export default {
        name: "myCenter",
        components: {
          bm,word,
          Card,Row,Col,Avatar,Tag,Alert,Button,Icon,
          [Cell.name]:Cell,
          [Header.name]:Header
        },
      filters:{
      },
        data(){
          return{
            compname:'',
            userMess:JSON.parse(localStorage.getItem("userMess")),
          }
        },
      created(){
        this.bm = this.userMess.yhXm
      },
        methods:{
          goOut(){
            this.$http.get(this.apis.LOGOUT).then((res)=>{
              if(res.code==200){
                localStorage.clear()
                this.$router.push({name:'Login'})
              }

            }).catch((err)=>{

            })
          },
          userMessF(){
            var v = this
            this.$http.post(this.apis.USERMESS).then((res)=>{
              if(res.code==200){
                if(res.result.yhTx == ''){
                  res.result.yhTx ='/static/userTx.png'
                }
                localStorage.setItem('userMess',JSON.stringify(res.result))
                v.compname = ''
                v.userMess = JSON.parse(localStorage.getItem("userMess"))
              }
            }).catch((err)=>{
              console.log('出错了！！！')
            })
          }
        }
    }
</script>

<style scoped>

</style>
