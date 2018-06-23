<style lang="less">
  @import "./xueche";
</style>
<template>
  <div id="xueche" style="height:800px;overflow: auto">
    <!-- 教练员信息 -->
    <el-row type="flex" justify="center" class="jlyTitle">
      <el-col :span="22">
        <el-card class="box-card" @click.native="showCoachInfo">
          <!--<div style="float: right"><i class="el-icon-refresh"></i></div>-->
          <div class="titlefix">
            <el-button disabled icon="iconfont icon-user" circle class="headerIcon"></el-button>
            <div>
              <el-tag type="danger" size="mini" class="headerSubName">{{jly.qymc}}</el-tag>
            </div>
            <div class="headerName">{{jly.yhXm}}</div>
            <div>
              <el-rate
                v-model="jly.jlPf"
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
    <el-row type="flex">
      <el-col :span="24">
        <el-card class="card-body" shadow="never">
          <div>
            <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
              <el-tab-pane name="step1">
                    <span slot="label">
                      <img :src="tabLabel[0].tabImg" width="120"/>
                      <span class="title-name">受理进度</span>
                      <span class="title-subname">受理成功可考试</span>
                    </span>
                <div class="content">
                  <el-row :gutter="20">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_2/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/bm.jpg" class="image">
                          <div style="padding: 10px">
                            <span>报名条件</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_4/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/fy.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>学车费用</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_6/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/tj.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>体检事项</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/info_1_8/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/xclc.jpg" class="image">
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
                        <el-steps direction="vertical" :active="handleSteps - 1">
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
              </el-tab-pane>
              <el-tab-pane name="step2">
                    <span slot="label">
                      <img :src="tabLabel[1].tabImg" width="120"/>
                      <span class="title-name">科目一</span>
                      <span class="title-subname">基础理论知识考试</span>
                    </span>
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/sxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/sxlx.jpg" class="image">
                          <div style="padding: 10px">
                            <span>顺序练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zjlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/zjlx.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>章节练习</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="8">
                      <a href="http://m.jxedt.com/mnks/ckm1/zxlx/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/zxlx.jpg" class="image">
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
              </el-tab-pane>
              <el-tab-pane label="科目二" name="step3">
                    <span slot="label">
                      <img :src="tabLabel[2].tabImg" width="120"/>
                      <span class="title-name">科目二</span>
                      <span class="title-subname">场地驾驶技能考试</span>
                    </span>
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_212/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/hgbz.jpg" class="image">
                          <div style="padding: 10px">
                            <span>合格标准</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_603/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/cftc.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>侧方停车</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_604/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/qxxs.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>曲线行驶</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_605/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/zjzw.jpg" class="image">
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
                          <img src="/wx/static/jpg/spqb.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>上坡起步</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>

                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km2_82_606/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/dcrk.jpg" class="image">
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
              </el-tab-pane>
              <el-tab-pane label="科目三" name="step4">
                    <span slot="label">
                      <img :src="tabLabel[3].tabImg" width="120"/>
                      <span class="title-name">科目三</span>
                      <span class="title-subname">道路驾驶技能考试</span>
                    </span>
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_608/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/hgbz.jpg" class="image">
                          <div style="padding: 10px">
                            <span>评判标准</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/yyzl/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/yyzl.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>语言指令</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/dgcz/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/dg.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>灯光操作</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_627/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/kskj.jpg" class="image">
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
              </el-tab-pane>
              <el-tab-pane label="科目四" name="step5">
                    <span slot="label">
                      <img :src="tabLabel[4].tabImg" width="120"/>
                      <span class="title-name">科目四</span>
                      <span class="title-subname">安全文明驾驶常识</span>
                    </span>
                <div class="content">
                  <el-row :gutter="20" style="padding-bottom: 10px">
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_608/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/hgbz.jpg" class="image">
                          <div style="padding: 10px">
                            <span>评判标准</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/yyzl/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/yyzl.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>语言指令</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3/dgcz/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/dg.jpg" class="image">
                          <div style="padding: 10px;">
                            <span>灯光操作</span>
                          </div>
                        </el-card>
                      </a>
                    </el-col>
                    <el-col :span="6">
                      <a href="http://m.jxedt.com/km3_607_627/">
                        <el-card :body-style="{ padding: '0px' }">
                          <img src="/wx/static/jpg/kskj.jpg" class="image">
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
                              <el-tag v-if="payInfo['4'] == '已缴'" type="success">已缴</el-tag>
                              <el-tag v-else type="danger">未缴</el-tag>
                            </el-col>
                          </el-row>
                        </div>
                      </el-card>
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
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div id="allmap"></div>
  </div>
</template>

<script>

  import { Toast } from 'mint-ui';
  export default {
    name: "xueche",
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
          yhXm: '未绑定教练',
          jlQu: '-',
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
      }
    },
    created() {
      this.user = this.$store.state.app.userMess;
      this.getCoachInfo();
      this.getHandleStatus();
      this.getExamInfo();
      this.getPayInfo();
    },
    methods: {
      handleClick(tab, event) {
        for (var item of this.tabLabel) {
          item.tabImg = "static/png/tab-default.png";
        }
        this.tabLabel[tab.index].tabImg = "static/png/tab-selected.png";
      },
      getCoachInfo() {
        this.$http.get(this.apis.getStudentCoach, {params: {yhId: this.user.id}}).then((res) => {
          if (res.code == 200 && res.result) {
            this.jly = res.result;
            this.jly.jlPf = isNaN(this.jly.jlPf) ? 0 : parseInt(this.jly.jlPf);
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
          if (res.code == 200){
            this.handleStatus = res.result;
            this.handleSteps = parseInt(res.message);
          }
        })
      },
      showCoachInfo(){
        this.$router.push({name:'coach',params:{coach:this.jly}})
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
