<style lang="less">
  #myCenterQrcode{
    background-color: #fbf9f9;
  }
</style>
<template>
    <div id="myCenterQrcode" class="box">
      <mt-header fixed title="我的二维码">
        <div slot="left">
          <Icon type="ios-arrow-back" style="font-size: 26px" @click.native="goback"></Icon>
        </div>
      </mt-header>
      <div class="body">
          <!-- 用户设置 -->
          <div style="padding-top: 40px;">
              <Row type="flex" justify="start">
                <Col span="24">
                  <Card dis-hover>
                    <md-result-page
                      class="customized"
                      img-url="//manhattan.didistatic.com/static/manhattan/do1_JX7bcfXqLpStKRv31xlp"
                      text="没有缴费用户不显示二维码"
                      subtext="需要做一个显示图">
                    </md-result-page>
                  </Card>
                </Col>
              </Row>
              <Row type="flex" justify="start" style="margin: 20px">
                <Col span="24">
                  <Card dis-hover>
                    <Row type="flex" justify="start" align="middle">
                      <Col span="4">
                        <Avatar shape="square" icon="person" size="large" style="width: 50px;height: 50px;line-height: 50px"/>
                      </Col>
                      <Col span="8">
                        <span style="font-size:16px">用户姓名</span>
                      </Col>
                    </Row>
                    <Row type="flex" justify="start" align="middle">
                      <Col span="24" style="text-align: center">
                        <i class="iconfont icon-qrcode" style="font-size: 200px"></i>
                      </Col>
                    </Row>
                    <Row type="flex" justify="start" align="middle">
                      <Col span="24" style="text-align: center;color: ">
                        <span>扫一扫上面的二维码图案，加入我们吧</span>
                      </Col>
                    </Row>
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
        methods:{
            goback(){
              this.$router.go(-1);
            }
        }
    }
</script>

<style scoped>

</style>
