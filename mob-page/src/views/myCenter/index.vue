<style lang="less">
  @import "./mycenter";
</style>
<template>
    <div id="myCenter" class="box">
      <!-- 用户信息区域 -->
      <Row type="flex" justify="start" align="middle" style="height:80px">
        <Col span="20" @click.native="setting">
            <Row>
                <Col span="2" push="1">
                    <Avatar icon="person" size="large" style="size: 30px"/>
                </Col>
                <Col span="8" push="3">
                    <span style="font-weight: bold">用户姓名</span>
                    <Row>
                        <Col span="8"><Tag>学员</Tag></Col>
                        <Col span="12"><Tag>未实名</Tag></Col>
                    </Row>
                </Col>
            </Row>
        </Col>
        <Col span="2" offset="1" @click.native="showQrcode"><i class="iconfont icon-qrcode" style="font-size: 28px"></i></Col>
      </Row>
      <Row type="flex" justify="start">
        <Col span="24">
        <Alert show-icon type="warning" closable>
          快邀请朋友加入吧！
        </Alert>
        </Col>
      </Row>
      <div class="body">
        <div>
          <!-- 用户余额区域 -->
          <Row type="flex" justify="start">
            <Col span="24">
              <Card style="margin: 20px" dis-hover>
                <div style="text-align:center">
                  <h3>我的余额（元）</h3>
                  <span style="font-size: 40px;font-weight: bold">300.00</span>
                  <Row type="flex" justify="center">
                    <Col span="6">
                      <Button type="primary" shape="circle"
                              @click="$router.push({name:'pay'})"
                              style="font-size: 14px;width:60px">缴费</Button>
                    </Col>
                    <Col span="6">
                      <Button type="primary" shape="circle"
                              @click="$router.push({name:'tx'})"
                              style="font-size: 14px;width:60px">提现</Button>
                    </Col>
                  </Row>
                </div>
              </Card>
            </Col>
          </Row>
          <!-- 用户设置 -->
          <Row type="flex" justify="start">
            <Col span="24">
              <Card dis-hover>
                <div @click="$router.push({name:'bill'})">
                  <mt-cell title="账单" is-link style="border-bottom: 1px #e9eaec solid;" >
                    <i class="iconfont icon-detail" style="font-size: 20px" slot="icon"></i>
                  </mt-cell>
                </div>
                <mt-cell title="团队" is-link>
                  <i class="iconfont icon-team" style="font-size: 20px" slot="icon"></i>
                </mt-cell>
              </Card>
            </Col>
          </Row>
          <Row type="flex" justify="start" style="margin-top: 20px">
            <Col span="24">
            <Card dis-hover>
              <mt-cell title="实名认证" value="未认证" to="/myCenter-sfrz" is-link style="border-bottom: 1px #e9eaec solid;">
                <i class="iconfont icon-shimingrenzheng" style="font-size: 20px" slot="icon"></i>
              </mt-cell>
              <mt-cell title="教练员认证" value="未认证" to="/myCenter-jlyrz" is-link>
                <i class="iconfont icon-ai-ca" style="font-size: 20px" slot="icon"></i>
                <span style="color: #19be6b"><Icon type="ios-checkmark"></Icon>&nbsp;&nbsp;已认证</span>
              </mt-cell>
            </Card>
            </Col>
          </Row>
        </div>
      </div>
    </div>
</template>

<script>
    import {Card, Row, Col, Avatar, Tag, Alert, Button, Icon  } from 'iview'
    import { Toast} from 'mand-mobile'
    import {  Cell } from 'mint-ui';
    export default {
        name: "myCenter",
        components: {
          Card,Row,Col,Avatar,Tag,Alert,Button,Icon,
          [Cell.name]:Cell
        },
        methods:{
            setting(){
                this.$router.push('/myCenter-info');
            },
            showQrcode(){
                this.$router.push('/myCenter-qrcode');
            }
        }
    }
</script>

<style scoped>

</style>
