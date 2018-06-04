<style lang="less">
  #stumess{
    font-size: 0.26rem;
  }
</style>
<template>
      <div id="stumess" class="box">
        <box-head tit="学员信息">
          <div slot="left" style="color: #E0DADF">
            <i class="iconfont icon-left1"></i>
          </div>
        </box-head>
        <div style="padding:0 0.2rem">
          <div class="box-row" style="border-bottom: solid 1px #ededed">
            <div>
              <i class="iconfont icon-wo" style="color: #00cc66;font-size:1.5rem"></i>
            </div>
            <div class="body-O" style="padding: 0.6rem 0 0 0.2rem">
              <div class="box-row">
                <div class="body-O">
                    姓名：小明
                </div>
                <div class="body-O">
                  电话： <a :href="'tel:'+ tell">{{tell}}</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="body">
          <Row>
            <Col span="11" style="margin-left: 5px;margin-right: 5px;margin-bottom: 5px">
              <Card dis-hover>
                <p slot="title">科目一成绩单</p>
                <imgup demoImg="static/djcl.png">
                </imgup>
              </Card>
            </Col>
            <Col span="12">
              <Card dis-hover>
                <p slot="title">科目二成绩单</p>
                <imgup demoImg="static/djcl.png">
                </imgup>
              </Card>
            </Col>
          </Row>
          <Row>
            <Col span="11" style="margin-left: 5px;margin-right: 5px;margin-bottom: 5px">
              <Card dis-hover>
                <p slot="title">科目三成绩单</p>
                <imgup demoImg="static/djcl.png">
                </imgup>
              </Card>
            </Col>
            <Col span="12">
              <Card dis-hover>
                <p slot="title">科目四成绩单</p>
                <imgup demoImg="static/djcl.png">
                </imgup>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
</template>

<script>
  import imgup from '@/views/components/upLoad/imgUpload'
  import {Card, Row, Col } from 'iview'
    export default {
        name: "stuMess",
        components:{
          Card,Row,Col,imgup
        },
        data(){
          return{
            tell:12345678911
          }
        }
    }
</script>

<style scoped>

</style>
