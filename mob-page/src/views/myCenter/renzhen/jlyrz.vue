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
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
      </div>
      <div class="body" style="padding-top: 10px;">
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
            <mt-field label="真实姓名" placeholder="请输入真实姓名" v-model="form.yhXm"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-field label="身份证号" placeholder="请输入身份证号" v-model="form.yhZjhm"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-cell title="驾龄" :value="form.jlJl" is-link style="border-bottom: 1px #e9eaec solid;" @click.native="showSelector"></mt-cell>
            <md-selector
              v-model="isSelectorShow"
              :data="data"
              :default-index="1"
              title="驾龄"
              okText="确认"
              cancelText="取消"
              @confirm="onSelectorConfirm($event)"
            ></md-selector>
            <mt-cell title="所属区域" :value="ssqy" is-link style="border-bottom: 1px #e9eaec solid;" @click.native="()=>{areaPickerShow = true}"></mt-cell>
            <md-picker
              ref="areaPicker"
              v-model="areaPickerShow"
              :data="areaData"
              @confirm="onPickerConfirm()"
              title="选择区域"
            ></md-picker>
            <mt-field label="居住地址" placeholder="请输入居住地址" v-model="form.jlZz"style="border-bottom: 1px #e9eaec solid;"></mt-field>
          </Card>
          <Card dis-hover style="margin-top: 10px">
            <p slot="title">紧急联系人</p>
            <mt-field label="联系人" placeholder="请输入紧急联系人" v-model="form.jlJilxr"style="border-bottom: 1px #e9eaec solid;"></mt-field>
            <mt-field label="联系电话" placeholder="请输入紧急联系人电话" v-model="form.jlJilxrdh"></mt-field>
          </Card>
          <div style="margin: 20px">
            <mt-button type="danger" size="large" @click="toPhotoNext">下一步</mt-button>
          </div>
        </div>
        <div v-show="stepIndex == 1" style="padding:0.2rem">

          <Row :gutter="10">
            <Col span="12" style="margin-bottom: 5px">
              <Card dis-hover>
                <p slot="title">身份证正面</p>
                <div align="center" style="padding:0 0.3rem;height: 2.5rem">
                  <imgup :demoImg="imgList[0]"
                         @handleSuccess="(res)=>handleSuccess(res,0)">
                  </imgup>
                </div>
              </Card>
            </Col>
            <Col span="12">
              <Card dis-hover>
                <p slot="title">身份证反面</p>
                <div align="center" style="padding:0 0.3rem;height: 2.5rem">
                  <imgup :demoImg="imgList[1]"
                         @handleSuccess="(res)=>handleSuccess(res,1)">
                  </imgup>
                </div>
              </Card>
            </Col>
          </Row>
          <Row :gutter="10">
            <Col span="12" style="margin-bottom: 5px">
              <Card dis-hover>
                <p slot="title">驾驶证正本</p>
                <div align="center" style="padding:0 0.3rem;height: 2.5rem">
                  <imgup :demoImg="imgList[2]"
                         @handleSuccess="(res)=>handleSuccess(res,2)">
                  </imgup>
                </div>
              </Card>
            </Col>
            <Col span="12">
              <Card dis-hover>
                <p slot="title">驾驶证副本</p>
                <div align="center" style="padding:0 0.3rem;height: 2.5rem">
                  <imgup :demoImg="imgList[3]"
                         @handleSuccess="(res)=>handleSuccess(res,3)">
                  </imgup>
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
          <!--<div-->
            <!--v-show="userMess.yhJlsh=='2'"-->
            <!--style="font-size: 0.4rem;color: #999999;text-align: center">-->
            <!--{{userMess.yhZtMs}}-->
          <!--</div>-->
          <div style="padding-top: 20px">
            <mt-button v-if="userMess.yhJlsh=='2'"
                       @click="stepIndex=0"
                       type="primary" size="large">重新提交</mt-button>
            <mt-button @click="$router.back()" v-else type="primary" size="large">完成</mt-button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import {Card, Row, Col, Avatar, Icon,Steps, Step  } from 'iview'
  import { Header,Button,Field,Toast,Cell } from 'mint-ui';
  import { ResultPage,Selector, FieldItem,Picker  } from 'mand-mobile'
  import imgup from '@/views/components/upLoad/imgUpload'
  export default {
    name: "myCenter",
    components: {
      imgup,
      Card,Row,Col,Avatar,Icon,Steps, Step,
      [Header.name]:Header,
      [Field.name]:Field,
      [Button.name]:Button,
      [Selector .name]:Selector,
      [Cell.name]: Cell,
      [Picker.name]: Picker,
      [ResultPage.name]:ResultPage
    },
    data(){
      return {
        userMess:'',
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
          {text:'东西湖区',value:'420105'}]
        ],
        errorText:{
          name:'',
          sfzmhm:'',
          jl:'5年',
          ssqy:''
        },
        ssqy:'',
        form:{
          yhXm:'',//姓名
          yhZjhm:'',//证件号
          jlJl:'',//驾龄
          jlQu:'',//教练区域
          jlZz:'',//教练住址
          jlJilxr:'',//教练紧急联系人
          jlJilxrdh:'',//紧急联系电话
          imgList:''
        },
        imgList:['static/renzhen/sfzzm.png','static/renzhen/sfzfm.png','static/renzhen/sfzzm.png','static/renzhen/sfzfm.png'],
        imgListN:['-','-','-','-'],
      }
    },
    created(){
      this.rz()
      this.getUserImg()
    },
    methods:{
      rz(){
        var v =this
        this.util.userMess(this,(res)=>{
          v.userMess = res
          // 0未认证  1已认证  2认证失败
          if(this.userMess.yhJlsh==0){
            this.stepIndex = 0
          }else if(this.userMess.yhJlsh!=0){
            this.stepIndex = 2
          }
        })
      },
      showSelector() {
        this.isSelectorShow = true
      },
      onSelectorConfirm({text}) {
        console.log(text)
        this.form.jlJl = text
      },
      onPickerConfirm() {
        const values = this.$refs['areaPicker'].getColumnValues()
        console.log(values)
        let res = ''
        values.forEach(value => {
          value && (res += `${value.text} `)
        })
        this.ssqy = '武汉市' + res
        this.form.jlQu = values[0].value
        console.log(values[0].value)
      },
      goback(){
        this.$router.go(-1);
      },
      toPhotoNext(){
        const mess = this.form
        if(mess.yhXm==''){
          Toast('请认真填写您的真实姓名');
        }else if (mess.yhZjhm==''){
          Toast('请认真填写您的证件号码');
        }else if (mess.jlJl==''){
          Toast('请认选着您的驾龄');
        }else if (mess.jlQu==''){
          Toast('请选择您所属区域');
        }else if (mess.jlZz==''){
          Toast('请认真填写您的居住地址');
        }else if (mess.jlJilxr==''){
          Toast('请认真填写您紧急联系人姓名');
        }else if (mess.jlJilxrdh==''){
          Toast('请认真填写您紧急联系人电话号码');
        }else {
          //切换到下一个界面
          console.log(this.form)
          this.stepIndex = 1;
        }

      },
      toResult(){
        //切换到下一个界面
        var v =this
        this.form.imgList = this.imgListN.join(',')
        console.log(this.form)
        this.$http.post(this.apis.JLRZ,this.form).then((res)=>{
            if(res.code==200){
              v.stepIndex = 2;
              v.rz()
            }
        }).catch((err)=>{

        })
      },
      handleSuccess(res,val){
        console.log('上传成功事件监听',res)
        console.log(val)
        var v = this
        this.imgList.splice(val,1,v.apis.getImgUrl+res.message)
        this.imgListN.splice(val,1,'/'+res.message)
      },
      getUserImg(){
        let num = 0
        var v =this
        this.$http.post(this.apis.USERIMGMESS).then((res)=>{
          v.imgList = []
          if(res.code==200){
            res.result.forEach((item,index)=>{
              if(item&&(index%2 ==0)){
                v.imgList[index] = 'static/renzhen/sfzzmok.png'
              }else if(item&&!(index%2 ==0)){
                v.imgList[index] = 'static/renzhen/sfzfmok.png'
              }else if(!item&&(index%2 ==0)){
                v.imgList[index] = 'static/renzhen/sfzzm.png'
              }else if(!item&&!(index%2 ==0)){
                v.imgList[index] = 'static/renzhen/sfzfm.png'
              }
            })
          }
        }).catch((err)=>{
        })
      }
    }
  }
</script>

<style scoped>

</style>
