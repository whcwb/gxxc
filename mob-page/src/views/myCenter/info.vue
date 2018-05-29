<style lang="less">
</style>
<template>
    <div id="myCenterInfo" class="box">
      <div class="body">
          <mt-header fixed title="个人信息">
              <div slot="left">
                <Icon type="ios-arrow-back" style="font-size: 26px" @click.native="goback"></Icon>
              </div>
          </mt-header>
          <!-- 用户设置 -->
          <div style="padding-top: 40px;color: black;">
              <Row type="flex" justify="start">
                <Col span="24">
                <Card dis-hover>
                  <mt-cell title="头像" style="border-bottom: 1px #e9eaec solid;padding-bottom: 10px">
                      <Avatar shape="square" icon="person" size="large" style="width: 50px;height: 50px;line-height: 50px"/>
                  </mt-cell>
                  <mt-cell title="姓名" value="某某" style="border-bottom: 1px #e9eaec solid;"></mt-cell>
                  <mt-cell title="修改密码" value="****"></mt-cell>
                </Card>
                </Col>
              </Row>
              <Row type="flex" justify="start" style="margin-top: 20px">
                <Col span="24">
                <Card dis-hover style="text-align: center">
                  <mt-cell title="退出登录"></mt-cell>
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
    import {  Cell, Header } from 'mint-ui';

    export default {
        name: "myCenter",
        components: {
          Card,Row,Col,Avatar,Tag,Alert,Button,Icon,
          [Cell.name]:Cell,
          [Header.name]:Header
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
