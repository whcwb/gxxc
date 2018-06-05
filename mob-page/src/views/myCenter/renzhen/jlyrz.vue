<style lang="less">
  #myCenterJlyrz{
    background-color: #fbf9f9;

    .ivu-tabs-nav{
      text-align: center;
      font-size: 16px;
      width:100%
    }
    .ivu-tabs-ink-bar{
      width:33%
    }
  }
</style>
<template>
    <div id="myCenterJlyrz" class="box">
      <div>
        <box-head tit="教练员认证">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left"></i>
          </div>
        </box-head>
      </div>
      <div class="body" style="padding-top: 40px;">
        <!-- 认证审核 -->
        <div style="margin: 20px">
          <Steps :current="stepIndex">
            <Step title="填写资料"></Step>
            <Step title="上传证件照片"></Step>
            <Step title="提交审核"></Step>
          </Steps>
        </div>
        <div v-show="stepIndex == 0">
          <Card dis-hover>
            <p slot="title">基本信息</p>
            <mt-field label="真实姓名" placeholder="请输入真实姓名" v-model="form.name"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-field label="身份证号" placeholder="请输入身份证号" v-model="form.sfzmhm"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-cell title="驾龄" :value="form.jl" is-link style="border-bottom: 1px #e9eaec solid;" @click.native="showSelector"></mt-cell>
            <md-selector
              v-model="isSelectorShow"
              :data="data"
              :default-index="1"
              title="驾龄"
              okText="确认"
              cancelText="取消"
              @confirm="onSelectorConfirm($event)"
            ></md-selector>
            <mt-cell title="所属区域" :value="form.ssqy" is-link style="border-bottom: 1px #e9eaec solid;" @click.native="()=>{areaPickerShow = true}"></mt-cell>
            <md-picker
              ref="areaPicker"
              v-model="areaPickerShow"
              :data="areaData"
              @confirm="onPickerConfirm"
              title="选择区域"
            ></md-picker>
            <mt-field label="居住地址" placeholder="请输入居住地址" v-model="form.sfzmhm"style="border-bottom: 1px #e9eaec solid;"></mt-field>
          </Card>
          <Card dis-hover style="margin-top: 10px">
            <p slot="title">紧急联系人</p>
            <mt-field label="联系人" placeholder="请输入紧急联系人" v-model="form.sfzmhm"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-field label="联系电话" placeholder="请输入紧急联系人电话" v-model="form.sfzmhm"></mt-field>
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
              <div align="center">
                <img src="static/renzhen/sfzzm.png" width="120" height="120">
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
        </div>
      </div>
    </div>
</template>

<script>
  import {Card, Row, Col, Avatar, Icon,Steps, Step  } from 'iview'
  import { Header,Button,Field,Toast,Cell   } from 'mint-ui';
  import { ResultPage,Selector, FieldItem,Picker  } from 'mand-mobile'

  export default {
    name: "myCenter",
    data(){
      return {
        stepIndex:0,
        isSelectorShow: false,
        areaPickerShow:false,
        data: [
            {
              text: '5年',
            },
            {
              text: '5-10年',
            },
            {
              text: '10年以上',
            },
        ],
        areaData:[[
          {text:'武昌区',value:'420101'},
          {text:'洪山区',value:'420102'},
          {text:'江夏区',value:'420103'},
          {text:'汉阳区',value:'420104'},
          {text:'东西湖区',value:'420105'},]
        ],
        errorText:{
          name:'',
          sfzmhm:'',
          jl:'5年',
          ssqy:''
        },
        form:{
          name:'',
          sfzmhm:''
        }
      }
    },
    components: {
      Card,Row,Col,Avatar,Icon,Steps, Step,
      [Header.name]:Header,
      [Field.name]:Field,
      [Button.name]:Button,
      [Selector .name]:Selector,
      [Cell.name]: Cell,
      [Picker.name]: Picker,
      [ResultPage.name]:ResultPage
    },
    methods:{
      showSelector() {
        this.isSelectorShow = true
      },
      onSelectorConfirm({text}) {
        this.form.jl = text
      },
      onPickerConfirm() {
        const values = this.$refs['areaPicker'].getColumnValues()

        let res = ''
        values.forEach(value => {
          value && (res += `${value.text} `)
        })
        this.form.ssqy = '武汉市' + res
      },
      goback(){
        this.$router.go(-1);
      },
      toPhotoNext(){
        /*if (this.form.name == ""){
         Toast({message:'请先输入真实姓名',position:'bottom'});
         return;
         }
         if (this.form.sfzmhm == "" || this.form.sfzmhm.length < 18){
         Toast({message:'请输入正确的18位身份证号码',position:'bottom'});
         return;
         }*/
        //切换到下一个界面
        this.stepIndex = 1;
      },
      toResult(){
        //切换到下一个界面
        this.stepIndex = 2;
      }
    }
  }
</script>

<style scoped>

</style>
