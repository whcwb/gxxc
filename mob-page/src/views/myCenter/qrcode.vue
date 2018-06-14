<style lang="less">
  #myCenterQrcode{
    background-color: #fbf9f9;
  }
</style>
<template>
    <div id="myCenterQrcode" class="box">
      <div>
        <box-head tit="我的二维码">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
      </div>
      <div class="body">
          <!-- 用户设置 -->
          <div style="padding-top: 40px;">
              <!--<Row type="flex" justify="start">-->
                <!--<Col span="24">-->
                  <!--<Card dis-hover>-->
                    <!--<md-result-page-->
                      <!--class="customized"-->
                      <!--img-url="//manhattan.didistatic.com/static/manhattan/do1_JX7bcfXqLpStKRv31xlp"-->
                      <!--text="没有缴费用户不显示二维码"-->
                      <!--subtext="需要做一个显示图">-->
                    <!--</md-result-page>-->
                  <!--</Card>-->
                <!--</Col>-->
              <!--</Row>-->
              <Row type="flex" justify="start" style="margin: 20px">
                <Col span="24">
                  <Card dis-hover>
                    <Row type="flex" justify="start" align="middle">
                      <Col span="4">
                        <!--<Avatar shape="square" icon="person" size="large" style="width: 50px;height: 50px;line-height: 50px"/>-->
                        <img :src="usermess.yhTx"
                             style="width: 1rem;height: 1rem;border-radius: 0.5rem"
                             alt="">
                      </Col>
                      <Col span="8">
                        <span style="font-size:16px"> {{usermess.yhXm}}</span>
                      </Col>
                    </Row>
                    <Row type="flex" justify="start" align="middle" v-if="!ewm">
                      <Col span="24" style="text-align: center">
                        <div style="padding: 0.2rem">
                            <img :src="ewm" style="width: 100%" alt="">
                        </div>
                        <div style="text-align: center;">扫一扫上面的二维码图案，加入我们吧</div>
                      </Col>
                    </Row>
                    <div v-else
                    style="text-align: center;font-size: 0.3rem;font-weight: 700;line-height: 1.5rem">
                      邀请码生成中……
                      请稍候
                    </div>
                  </Card>
                </Col>
              </Row>
          </div>
      </div>
    </div>
</template>

<script>
    import {Card, Row, Col, Avatar, Icon  } from 'iview'
    import { ResultPage } from 'mand-mobile'
    import { Header } from 'mint-ui';

    export default {
        name: "myCenter",
        components: {
          Card,Row,Col,Avatar,Icon,
          [Header.name]:Header,
          [ResultPage.name]:ResultPage
        },
        data(){
          return{
            usermess:this.$route.params,
            ewm:this.$route.params.yhZsyqmImg
          }
        },
        created(){
        },
        methods:{
            goback(){
              this.$router.go(-1);
            }
        }
    }
</script>

<style scoped>

</style>
