<style lang="less">
  @import "./mycenter";
</style>
<template>
  <div id="myCenter" class="box">
    <!-- 用户信息区域 -->
    <Row type="flex" justify="start" align="middle" style="height:80px">
      <Col span="20" @click.native="setting">
        <Row>
          <Col span="6">
            <!--<Avatar icon="person" size="large" style="size: 30px"/>-->
            <div style="width: 1rem;height: 1rem;margin: 0 0.2rem">
              <img :src="usermess.yhTx"
                   style="width: 100%;height: 100%;border-radius: 1rem"
                   alt="">
            </div>
          </Col>
          <Col span="18" style="padding-top: 5px">
            <span style="font-weight: bold">{{usermess.yhBm}}</span>
            <Row>
              <Col span="4">
                <Tag v-if="usermess.yhLx=='1'">学员</Tag>
                <Tag v-else-if="usermess.yhLx=='2'">教练</Tag>
              </Col>
              <Col span="12" v-if="usermess.yhZt=='0'">
                <Tag>未实名</Tag>
              </Col>
              <Col span="12" v-else-if="usermess.yhZt=='1'">
                <Tag>
                  <Icon type="ios-checkmark" color="#19be6b"></Icon>
                  已实名
                </Tag>
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
      <Col span="2" offset="1" @click.native="showQrcode('ewm')">
          <span>
            <i class="iconfont icon-qrcode" style="font-size: 28px"></i>
          </span>
      </Col>
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
                <span style="font-size: 40px;font-weight: bold">
                    {{zhYE.yhZhye/100 | yhZhye}}
                  </span>
                <Row type="flex" justify="center">
                  <Col span="6">
                    <Button type="primary" shape="circle"
                            v-if="usermess.ddSfjx=='0'&&usermess.yhZt=='1'"
                            @click="$router.push({name:'pay'})"
                            style="font-size: 13px;width:60px">缴费
                    </Button>
                    <Button type="success" shape="circle"
                            v-else-if="usermess.ddSfjx=='1'"
                            @click="okjf"
                            style="font-size: 13px;width:60px">已缴费
                    </Button>
                    <Button type="warning" shape="circle"
                            v-else
                            @click="tost"
                            style="font-size: 13px;width:60px">缴费
                    </Button>
                  </Col>
                  <Col span="6">
                    <Button type="primary" shape="circle"
                            @click="$router.push({name:'tx'})"
                            style="font-size: 13px;width:60px">提现
                    </Button>
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
                <mt-cell title="账单" is-link style="border-bottom: 1px #e9eaec solid;">
                  <i class="iconfont icon-detail" style="font-size: 20px" slot="icon"></i>
                </mt-cell>
              </div>
              <div @click="showQrcode('td')">
                <mt-cell title="团队" is-link>
                  <i class="iconfont icon-team" style="font-size: 20px" slot="icon"></i>
                </mt-cell>
              </div>
            </Card>
          </Col>
        </Row>
        <Row type="flex" justify="start" style="margin-top: 20px">
          <Col span="24">
            <Card dis-hover>
              <!--:to="usermess.yhZt=='1'?'/home':'/myCenter-sfrz'"-->
              <mt-cell title="实名认证" value="未认证"
                       to="/myCenter-sfrz"
                       is-link style="border-bottom: 1px #e9eaec solid;">
                <i class="iconfont icon-shimingrenzheng" style="font-size: 20px" slot="icon"></i>
                <span :style="{color: usermess.yhZt=='1' ? '#19be6b' : '#888'}">
                  <Icon v-if="usermess.yhZt=='1'"
                        type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhZt | yhZt}}</span>
              </mt-cell>
              <!--:to="usermess.yhLx=='2'? '/home':'/myCenter-jlyrz'"-->
              <mt-cell title="教练员认证" value="未认证"
                       to="/myCenter-jlyrz"
                       is-link style="border-bottom: 1px #e9eaec solid;">

                <i class="iconfont icon-ai-ca" style="font-size: 20px" slot="icon"></i>
                <span :style="{color: usermess.yhJlsh=='1' ? '#19be6b' : '#888'}">
                  <Icon v-if="usermess.yhJlsh=='1'"
                        type="ios-checkmark"></Icon>&nbsp;&nbsp;{{usermess.yhJlsh | yhZt}}</span>
              </mt-cell>
              <mt-cell title="我的学员" value="0人"
                       v-if="usermess.yhLx=='2'"
                       to="/myStudent"
                       is-link style="border-bottom: 1px #e9eaec solid;">
                <i class="iconfont icon-qianzaixueyuan" style="font-size: 20px" slot="icon"></i>
              </mt-cell>
            </Card>
          </Col>
        </Row>
      </div>
    </div>
  </div>
</template>

<script>
  import {Card, Row, Col, Avatar, Tag, Alert, Button, Icon} from 'iview'
  import {Cell, Toast} from 'mint-ui';

  export default {
    name: "myCenter",
    components: {
      Card, Row, Col, Avatar, Tag, Alert, Button, Icon,
      [Cell.name]: Cell,
    },
    filters: {
      yhZt: function (val) {
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
      yhZhye(val) {
        if (val == '') {
          return 0
        }
        return val
      }
    },
    data() {
      return {
        usermess: {
          yhTx: ''
        },
        zhYE: {
          yhZhye: 0
        }
      }
    },
    created() {
      var v = this
      this.util.userMess(v, (res) => {
        this.usermess = res
      })
      console.log('***---***', this.usermess)
      this.getZhye()
    },
    methods: {
      tost() {
        Toast('请实名认证')
      },
      getZhye() {
        this.$http.post(this.apis.USERZH).then((res) => {
          if (res.code == 200) {
            this.zhYE = res.result
          }
          console.log(res)
        }).catch((err) => {

        })
      },
      setting() {
        this.$router.push('/myCenter-info');
      },
      showQrcode(val) {
        var v = this
        if (this.usermess.yhZsyqmImg) {
          if (val == 'ewm') {
            this.$router.push(
              {
                name: 'myCenterQrcode',
                params: v.usermess
              });
          } else if (val == 'td') {
            this.$router.push({name: 'myteam'})
          }
        } else {
          Toast('请先缴费')
        }
      },
      okjf() {
        Toast('您已缴费')
      }
    }
  }
</script>

<style scoped>

</style>
