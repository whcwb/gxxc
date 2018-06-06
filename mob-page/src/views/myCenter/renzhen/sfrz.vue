<style lang="less">
  #myCenterSfrz{
    background-color: #fbf9f9;

    .ivu-tabs-nav{
      text-align: center;
      font-size: 16px;
      width:100%
    }

    .mint-tab-item-label {
      font-size: 16px;
    }
  }
</style>
<template>
    <div id="myCenterSfrz" class="box">
      <div>
        <box-head tit="实名认证">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
      </div>
      <div class="body" style="padding-top: 20px;">
        <!-- 认证审核 -->
        <div style="padding-left: 20px" v-show="stepIndex==1">
          <mt-button type="default"
                     @click="stepIndex=0"
                     size="small" plain>上一步</mt-button>
        </div>
        <div style="margin: 20px">
          <Steps :current="stepIndex">
            <Step title="填写资料"></Step>
            <Step title="上传证件照片"></Step>
            <Step title="提交审核"></Step>
          </Steps>
        </div>
        <div v-show="stepIndex == 0">
          <Card dis-hover>
            <mt-field label="真实姓名" placeholder="请输入真实姓名" v-model="form.yhXm" style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-field label="身份证号" placeholder="请输入身份证号" v-model="form.yhZjhm" style="border-bottom: 1px #e9eaec solid;"></mt-field>
          </Card>
          <div style="margin: 20px">
            <mt-button type="danger" size="large" @click="toPhotoNext">下一步</mt-button>
          </div>
        </div>
        <div v-show="stepIndex == 1">

            <Row>
              <Col span="11" style="margin-left: 5px;margin-right: 5px;margin-bottom: 5px">
                <Card dis-hover>
                  <p slot="title">身份证正面</p>
                  <div align="center" style="padding:0 0.3rem"
                      @click="zjBm(0)">
                    <imgup :demoImg="sfzzm"
                           @handleSuccess="handleSuccess">
                    </imgup>
                  </div>
                </Card>
              </Col>
              <Col span="12">
                <Card dis-hover>
                  <p slot="title">身份证反面</p>
                  <div align="center">
                    <img src="static/renzhen/sfzfm.png" width="120" height="120">
                  </div>
                </Card>
              </Col>
            </Row>
            <Row>
              <Col span="11" style="margin-left: 5px;margin-right: 5px;margin-bottom: 5px">
                <Card dis-hover>
                  <p slot="title">驾驶证正本</p>
                  <div align="center">
                    <img src="static/renzhen/sfzzm.png" width="120" height="120">
                  </div>
                </Card>
              </Col>
              <Col span="12">
                <Card dis-hover>
                  <p slot="title">驾驶证副本</p>
                  <div align="center">
                    <img src="static/renzhen/sfzfm.png" width="120" height="120">
                  </div>
                </Card>
              </Col>
            </Row>
          <div style="margin: 20px">
            <mt-button size="large" @click="toResult">提交审核</mt-button>
          </div>
        </div>
        <div v-show="stepIndex == 2">
          <md-result-page
            class="customized"
            img-url="//manhattan.didistatic.com/static/manhattan/do1_JX7bcfXqLpStKRv31xlp"
            text="需要做一个审核结果界面图"
            subtext="审核会有：审核中、通过、驳回。三种情况">
          </md-result-page>
          <div style="padding-top: 20px">
            <mt-button type="primary" size="large">完成</mt-button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
    import {Card, Row, Col, Avatar, Icon,Tabs,TabPane,Badge,Steps, Step  } from 'iview'
    import { Header,Button,Field,Toast } from 'mint-ui';
    import { ResultPage } from 'mand-mobile'
    import imgup from '@/views/components/upLoad/imgUpload'
    export default {
        name: "myCenter",
        components: {
          imgup,
          Card,Row,Col,Avatar,Icon,Tabs,TabPane,Badge,Steps, Step,
          [Header.name]:Header,
          [Field.name]:Field,
          [Button.name]:Button,
          [ResultPage.name]:ResultPage,
        },
        data(){
          return {
            stepIndex:1,
            form:{
                yhXm:'',
                yhZjhm:'',
            },
            sfzzm:'static/renzhen/sfzzm.png',
            zjCode:0
          }
        },
        methods:{
            goback(){
              this.$router.go(-1);
            },
            toPhotoNext(){
                // if (this.form.yhXm == ""){
                //   Toast({message:'请填写您的姓名'});
                //   return;
                // }else if(this.form.yhZjhm==""||this.form.yhZjhm.length!=18){
                //   Toast({message:'请填写正确的身份证号码！'});
                //   return;
                // }
                  // 切换到下一个界面
                   this.stepIndex = 1;
            },
            toResult(){
              //切换到下一个界面
              this.stepIndex = 2;
            },
            zjBm(val){

            },
            handleSuccess(){

            }
        }
    }
</script>

<style scoped>

</style>
