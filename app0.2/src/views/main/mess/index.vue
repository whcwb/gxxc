<style lang="less">
  @import "./xueche";
  .carousel{
    color: #fff;
    text-align: center;
    .clTIT{
      font-size: 0.16rem;
      font-weight: 700;
      margin-top: 0.12rem;
    }
    .clMESS{
      margin-top: 0.06rem;
      font-size: 0.14rem;
    }
  }
  .el-carousel__item carousel{
    color: #475669;
    opacity: 0.75;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #fff;
    background-size: 100%;
    background-repeat: no-repeat;
    background-position: center;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #fff;
    background-size: 100%;
    background-repeat: no-repeat;
    background-position: center;
  }
</style>
<template>
  <div id="xueche" style="height:800px;overflow: auto">
    <!-- 专员员信息 -->
    <el-row type="flex" justify="center" class="jlyTitle" style="background-image: url('static/png/title-bg.png')">
      <el-col :span="22">
        <el-card class="box-card" @click.native="showCoachInfo" style="background-image: url('static/png/card-bg.png')">
          <!--<div style="float: right"><i class="el-icon-refresh"></i></div>-->
          <div class="titlefix">
            <el-button disabled icon="iconfont icon-user" circle class="headerIcon"></el-button>
            <div>
              <el-tag type="danger" size="mini" class="headerSubName">{{zyMwssList[initialIndex].jlQu | jlQu}}</el-tag>
            </div>
            <div class="headerName">{{zyMwssList[initialIndex].yhXm | yhXm}}</div>
            <div>
              <el-rate
                v-model="zyMwssList[initialIndex].jlPf"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value}">
              </el-rate>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row type="flex">
      <el-col :span="24">
        <el-alert
          title="注意您的学车进度"
          type="warning"
          :closable="false"
          show-icon>
        </el-alert>
      </el-col>
    </el-row>
    <!-- 学车进度信息 -->
    <div style="padding: 0 0.1rem">
      <el-carousel :interval="0" type="card"
                   @change="swipeClick"
                   :initial-index="initialIndex"
                   indicator-position="none"
                   arrow="never" height="0.8rem">
        <el-carousel-item :style="{backgroundImage:'url('+tabLabel[0].tabImg+')'}">
          <div class="carousel">
            <div class="clTIT">受理进度</div>
            <div class="clMESS">受理成功可考试</div>
          </div>
        </el-carousel-item>
        <el-carousel-item :style="{backgroundImage:'url('+tabLabel[1].tabImg+')'}">
          <div class="carousel">
            <div class="clTIT">科目一</div>
            <div class="clMESS">基础理论知识考试</div>
          </div>
        </el-carousel-item>
        <el-carousel-item :style="{backgroundImage:'url('+tabLabel[2].tabImg+')'}">
          <div class="carousel">
            <div class="clTIT">科目二</div>
            <div class="clMESS">场地驾驶技能考试</div>
          </div>
        </el-carousel-item>
        <el-carousel-item :style="{backgroundImage:'url('+tabLabel[3].tabImg+')'}">
          <div class="carousel">
            <div class="clTIT">科目三</div>
            <div class="clMESS">道路驾驶技能考试</div>
          </div>
        </el-carousel-item>
        <el-carousel-item :style="{backgroundImage:'url('+tabLabel[4].tabImg+')'}">
          <div class="carousel">
            <div class="clTIT">科目四</div>
            <div class="clMESS">安全文明驾驶常识</div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <el-row type="flex">
      <el-col :span="24">
        <el-card class="card-body" shadow="never">
              <div v-if="initialIndex==0">
                <div class="content">
                  <el-row :gutter="20">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_2/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/bm.jpg" class="image">
                          <div style="padding: 10px">
                            <span>报名条件</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_4/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/fy.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>学车费用</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_6/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/tj.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>体检事项</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_8/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/xclc.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>学车流程</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-row :gutter="20" style="padding-top: 10px">
                    <el-col :span="24">
                      <el-card :body-style="{ padding: '0px' }">
                        <div slot="header" class="title">
                          <span>受理进度</span>
                        </div>
                        <el-steps direction="horizontal" :active="handleSteps - 1">
                          <el-step title="医院体检">
                                <span v-if="handleStatus['1']" slot="description">
                                  {{handleStatus['1'].name}}<br>
                                  {{handleStatus['1'].slSj}}
                                </span>
                                <span v-else slot="description">
                                  待完成
                                </span>
                          </el-step>
                          <el-step title="入网面签">
                                <span v-if="handleStatus['2']"  slot="description">
                                  {{handleStatus['2'].name}}<br>
                                  {{handleStatus['2'].slSj}}
                                </span>
                                <span v-else slot="description">
                                  待完成
                                </span>
                          </el-step>
                          <el-step title="档案采集">
                                <span v-if="handleStatus['3']"  slot="description">
                                  {{handleStatus['3'].name}}<br>
                                  {{handleStatus['3'].slSj}}
                                </span>
                                <span v-else slot="description">
                                  待完成
                                </span>
                          </el-step>
                          <el-step title="受理成功">
                                <span v-if="handleStatus['4']"  slot="description">
                                  {{handleStatus['4'].name}}<br>
                                  {{handleStatus['4'].slSj}}
                                </span>
                                <span v-else slot="description">
                                  待完成
                                </span>
                          </el-step>
                        </el-steps>
                      </el-card>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div v-else-if="initialIndex==1">
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/sxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/sxlx.jpg" class="image">
                          <div style="padding: 10px">
                            <span>顺序练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zjlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/zjlx.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>章节练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/zxlx.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>专项练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-row style="padding-bottom: 10px">
                    <el-col>
                      <el-card :body-style="{ padding: '0px' }">
                        <div style="padding: 10px;">
                          <el-row type="flex">
                            <el-col :span="24" style="margin-top: 8px">
                              <span style="font-size:14px">当前科目考试费</span>
                              <el-tag v-if="payInfo['1'] == '已缴'" type="success">已缴</el-tag>
                              <el-tag v-else type="danger">未缴</el-tag>
                            </el-col>
                          </el-row>
                        </div>
                      </el-card>
                    </el-col>
                  </el-row>
                  <el-card shadow="never">
                    <div slot="header" class="clearfix">
                      <span v-if="examInfo['1']">{{examInfo['1'].schoolName}}</span>
                      <span v-else>未预约</span>
                      <i v-if="examInfo['1']" class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px" @click="showMap(examInfo['1'])"></i>
                    </div>
                    <el-row v-if="examInfo['1']" type="flex" justify="space-around">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第一次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['1'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['1'].cj1 >= 90 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['1'].cj1}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                    <el-row v-if="examInfo['1'] && examInfo['1'].cj1 < 90"  type="flex" justify="space-around" style="padding-top: 20px">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第二次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['1'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['1'].cj2 >=90 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['1'].cj2}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-card>
                </div>
              </div>
              <div v-else-if="initialIndex==2" label="科目二" name="step3">
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_212/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/hgbz.jpg" class="image">
                          <div style="padding: 10px">
                            <span>合格标准</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_603/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/cftc.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>侧方停车</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_604/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/qxxs.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>曲线行驶</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_605/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/zjzw.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>直角转弯</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-row :gutter="20" style="padding-top: 10px;padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_602/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/spqb.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>上坡起步</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>

                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_606/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/dcrk.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>倒车入库</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-row style="padding-bottom: 10px">
                    <el-col>
                      <el-card :body-style="{ padding: '0px' }">
                        <div style="padding: 10px;">
                          <el-row type="flex">
                            <el-col :span="24" style="margin-top: 8px">
                              <span style="font-size:14px">当前科目考试费</span>
                              <el-tag v-if="payInfo['2'] == '已缴'" type="success">已缴</el-tag>
                              <el-tag v-else type="danger">未缴</el-tag>
                            </el-col>
                          </el-row>
                        </div>
                      </el-card>
                    </el-col>
                  </el-row>
                  <el-card shadow="never">
                    <div slot="header" class="clearfix">
                      <span v-if="examInfo['2']">{{examInfo['2'].schoolName}}</span>
                      <span v-else>未预约</span>
                      <i v-if="examInfo['2']"  class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px" @click="showMap(examInfo['2'])"></i>
                    </div>
                    <el-row v-if="examInfo['2']" type="flex" justify="space-around">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第一次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['2'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['2'].cj1 >=80 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['2'].cj1}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                    <el-row v-if="examInfo['2'] && examInfo['2'].cj1 < 80"  type="flex" justify="space-around" style="padding-top: 20px">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第二次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['2'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['2'].cj2 >=80 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['2'].cj2}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-card>
                </div>
              </div>
              <div v-else-if="initialIndex==3" label="科目三" name="step4">
                    <!--<span slot="label">-->
                      <!--<img :src="tabLabel[3].tabImg" width="200" height="60"/>-->
                      <!--<span class="title-name">科目三</span>-->
                      <!--<span class="title-subname">道路驾驶技能考试</span>-->
                    <!--</span>-->
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_608/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/hgbz.jpg" class="image">
                          <div style="padding: 10px">
                            <span>评判标准</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/yyzl/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/yyzl.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>语言指令</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/dgcz/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/dg.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>灯光操作</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_627/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/kskj.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>考试口诀</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-row style="padding-bottom: 10px">
                    <el-col>
                      <el-card :body-style="{ padding: '0px' }">
                        <div style="padding: 10px;">
                          <el-row type="flex">
                            <el-col :span="24" style="margin-top: 8px">
                              <span style="font-size:14px">当前科目考试费</span>
                              <el-tag v-if="payInfo['3'] == '已缴'" type="success">已缴</el-tag>
                              <el-tag v-else type="danger">未缴</el-tag>
                            </el-col>
                          </el-row>
                        </div>
                      </el-card>
                    </el-col>
                  </el-row>
                  <el-card shadow="never">
                    <div slot="header" class="clearfix">
                      <span v-if="examInfo['3']">{{examInfo['3'].schoolName}}</span>
                      <span v-else>未预约</span>
                      <i  v-if="examInfo['3']" class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px" @click="showMap(examInfo['3'])"></i>
                    </div>
                    <el-row v-if="examInfo['3']" type="flex" justify="space-around">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第一次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['3'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['3'].cj1 >=80 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['3'].cj1}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                    <el-row v-if="examInfo['3'] && examInfo['3'].cj1 < 80"  type="flex" justify="space-around" style="padding-top: 20px">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第二次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['3'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['3'].cj2 >=80 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['3'].cj2}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-card>
                </div>
              </div>
              <div v-else-if="initialIndex==4" label="科目四" name="step5">
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/sxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/sxlx.jpg" class="image">
                          <div style="padding: 10px">
                            <span>顺序练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zjlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/zjlx.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>章节练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="static/jpg/zxlx.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>专项练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                  </el-row>
                  <el-card shadow="never">
                    <div slot="header" class="clearfix">
                      <span v-if="examInfo['4']">{{examInfo['4'].schoolName}}</span>
                      <span v-else>未预约</span>
                      <i  v-if="examInfo['4']" class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px" @click="showMap(examInfo['4'])"></i>
                    </div>
                    <el-row v-if="examInfo['4']" type="flex" justify="space-around">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第一次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['4'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['4'].cj1 >=90 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['4'].cj1}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                    <el-row v-if="examInfo['4'] && examInfo['4'].cj1 < 90"  type="flex" justify="space-around" style="padding-top: 20px">
                      <el-col :span="10">
                        <el-row type="flex" style="text-align: center">
                          <el-col :span="24" style="font-weight: bold;font-size: 14px">
                            第二次成绩
                          </el-col>
                        </el-row>
                        <div style="border: 1px solid #e9eaec"></div>
                        <el-row type="flex">
                          <el-col :span="24">
                            {{examInfo['4'].ykSj}}
                          </el-col>
                        </el-row>
                      </el-col>
                      <el-col :span="12">
                        <el-row type="flex">
                          <el-col :span="24" :offset="14">
                            <el-badge :value="examInfo['4'].cj2 >=90 ? '合格' : '不合格'" class="cjItem">
                              <el-button circle class="cjCircle">{{examInfo['4'].cj2}}</el-button>
                            </el-badge>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-card>
                </div>
              </div>
        </el-card>
      </el-col>
    </el-row>
    <div id="allmap"></div>
  </div>
</template>

<script>

  import { Toast,Swipe, SwipeItem} from 'mint-ui';
  export default {
    name: "xueche",
    components:{
      [Swipe.name]:Swipe,
      [SwipeItem.name]: SwipeItem
    },
    filters:{
      jlQu:(val)=>{
        switch (val) {
          case "430014":
            return '江岸区'
            break;
          case "4300001":
            return '江汉区'
            break;
          case "4300002":
            return '硚口区'
            break;
          case "430050":
            return '汉阳区'
            break;
          case "4300003":
            return '武昌区'
            break;
          case "430080":
            return '武昌区'
            break;
          case "430080":
            return '青山区'
            break;
          case "430070":
            return '洪山区'
            break;
          case "430040":
            return '东西湖区'
            break;
          case "430090":
            return '汉南区'
            break;
          case "430100":
            return '蔡甸区'
            break;
          case "430200":
            return '蔡甸区'
            break;
          case "432200":
            return '黄陂区'
            break;
          case "431400":
            return '新洲区'
            break;
          default:
            return '***'
            break;
        }
      },
      yhXm:(val)=>{
        if (val){
          return val
        }
        return '暂未分配专员'
      }
    },
    data() {
      return {
        scroll: null,
        tabLabel: [
          {tabImg: 'static/png/tab-default.png'},
          {tabImg: 'static/png/tab-default.png'},
          {tabImg: 'static/png/tab-default.png'},
          {tabImg: 'static/png/tab-default.png'},
          {tabImg: 'static/png/tab-default.png'}
        ],
        activeName2: 'setp1',
        jly: {
          yhXm: '',
          jlQu: '',
          jlPf: 0,
          qymc: ''
        },
        regions: [],
        user:{},
        handleStatus:'',
        handleSteps:0,
          allSteps:1,
        examInfo:[],
        payInfo:[],
        initialIndex:0,
        zyMwss:{
        },
        zyMwssList:[
          {qymc:''}
        ]
      }
    },
    created() {
      this.user = this.$store.state.app.userMess;
      this.getZYmess()

      // this.getCoachInfo();

      this.getResionList();
      this.getHandleStatus();
      this.getExamInfo();
      this.getPayInfo();
      this.swipeClick(this.initialIndex)
    },
    methods: {
      getZYmess(){
        var v = this
        this.$http.post(this.apis.getZYmess).then((res)=>{
          console.log('钻元信息',res);
          res.result.forEach((item,index)=>{
            if(item.jlPf){
              item.jlPf = parseInt(item.jlPf)
            }
          })
          if(res.code==200 && res.result){
            v.zyMwssList= []
            v.zyMwssList = res.result
          }
        }).catch((err)=>{

        })
      },




      swipeClick(index){
        var v = this
        // console.log(index);
        v.initialIndex = index

        // if(!v.zyMwssList[index].yhId){
        //   Toast('暂未分配专员')
        // }
        this.tabLabel.forEach((item,val)=>{
          if(val==index){
            item.tabImg= "static/png/tab-selected.png"
          }else {
            item.tabImg=  "static/png/tab-default.png"
          }
        })
      },


      handleClick(tab, event) {
        for (var item of this.tabLabel) {
          item.tabImg = "static/png/tab-default.png";
        }
        this.tabLabel[tab.index].tabImg = "static/png/tab-selected.png";
      },
      getCoachInfo() {
        this.$http.get(this.apis.getStudentCoach, {params: {yhId: this.user.id}}).then((res) => {
          if (res.code == 200 && res.result) {
            // this.jly = res.result;
            // this.jly.jlPf = isNaN(this.jly.jlPf) ? 0 : parseInt(this.jly.jlPf);
            this.getResionList();
          }
        })
      },
      getExamInfo() {
          let v = this;
        this.$http.get(this.apis.getExamInfo, {params: {yhId: this.user.id}}).then((res) => {
          if (res.code == 200 && res.result) {
            this.examInfo = res.result;
            let s = parseInt(res.message)+1
            this.activeName2 = 'step'+s;
              for (let item of v.tabLabel) {
                  item.tabImg = "static/png/tab-default.png";
              }
              this.tabLabel[s-1].tabImg = "static/png/tab-selected.png";
          }
        })
      },
      getPayInfo() {
        this.$http.get(this.apis.getPayInfo, {params: {yhId: this.user.id}}).then((res) => {
          if (res.code == 200 && res.result) {
            this.payInfo = res.result;
          }
        })
      },
      getHandleStatus(){
        this.$http.get(this.apis.getHandleStatus, {params: {yhId: this.user.id}}).then((res)=>{
          if (res.code == 200 && res.result){
            this.handleStatus = res.result;
            this.handleSteps = parseInt(res.message);
          }
        })
      },
      showCoachInfo(){
        if(this.zyMwssList[this.initialIndex]){
          this.$router.push({name:'coach',params:{coach:this.zyMwssList[this.initialIndex],type:this.initialIndex}})
        }else {
          Toast('暂未分配负责专员')
        }
      },
      showMap(exam){
        if (!exam.examPlaceLat || !exam.examPlaceLng){
            Toast('未设置经纬度')
          return;
        }
        var map = new BMap.Map("allmap");
        var geolocation = new BMap.Geolocation();
        let lat = exam.examPlaceLat;
        let lng = exam.examPlaceLng;
        geolocation.getCurrentPosition(function(r){
          if(this.getStatus() === BMAP_STATUS_SUCCESS){
            location.href="http://api.map.baidu.com/direction?origin="+r.point.lat+","+r.point.lng+"&destination="+lat+","+lng+"&mode=driving&region=武汉&output=html";
          }
          else {
            alert('failed'+this.getStatus());
          }
        },{enableHighAccuracy: true})
      },
      getResionList() {
        this.$http.get(this.apis.zdxmPager, {params: {typeCode: 'ZDCLK0060'}}).then((res) => {
          if (res.code == 200 && res.result) {
            this.regionList = res.result;
            for (let r of this.regionList) {
              if (r.zddm == this.jly.jlQu) {
                this.jly.qymc = r.zdmc;
              }
            }
          }
        })
      }
    }
  }
</script>
