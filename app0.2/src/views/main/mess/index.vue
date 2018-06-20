<style lang="less">
  @import "./xueche";
</style>
<template>
      <div id="xueche" style="height:800px;overflow: auto">
        <!-- 教练员信息 -->
        <el-row type="flex" justify="center" class="jlyTitle">
          <el-col :span="22">
            <el-card class="box-card">
              <!--<div style="float: right"><i class="el-icon-refresh"></i></div>-->
              <div class="titlefix">
                <el-button disabled icon="iconfont icon-user" circle class="headerIcon"></el-button>
                <div>
                  <el-tag  type="danger" size="mini" class="headerSubName">{{jly.szqy}}</el-tag>
                </div>
                <div class="headerName">{{jly.xm}}</div>
                <div>
                  <el-rate
                    v-model="jly.scope"
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
                  <el-tab-pane name="first">
                    <span slot="label">
                      <img :src="tabLabel[0].tabImg" width="120" />
                      <span class="title-name">受理进度</span>
                      <span class="title-subname">受理成功可考试</span>
                    </span>
                    <div class="content">
                      <el-row :gutter="20">
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/info_1_2/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px">
                                <span>报名条件</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/info_1_4/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>学车费用</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/info_1_6/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>体检事项</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/info_1_8/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
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
                            <el-steps direction="vertical" :active="1">
                              <el-step title="医院体检">
                                <span slot="description">
                                  武汉江夏新区测试医院<br>
                                  2018年6月19日17:50:52
                                </span>
                                    </el-step>
                                    <el-step title="入网面签">
                                <span slot="description">
                                  满堂红驾校<br>
                                  2018年6月19日17:50:52
                                </span>
                                    </el-step>
                                    <el-step title="档案采集">
                                <span slot="description">
                                  待完成
                                </span>
                                    </el-step>
                                    <el-step title="受理成功">
                                <span slot="description">
                                  待完成
                                </span>
                              </el-step>
                            </el-steps>
                          </el-card>
                        </el-col>
                      </el-row>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="second">
                    <span slot="label">
                      <img :src="tabLabel[1].tabImg" width="120" />
                      <span class="title-name">科目一</span>
                      <span class="title-subname">基础理论知识考试</span>
                    </span>
                    <div class="content">
                      <el-row :gutter="20" style="padding-bottom: 10px">
                        <el-col :span="8">
                          <a href="http://m.jxedt.com/mnks/ckm1/sxlx/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px">
                                <span>顺序练习</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="8">
                          <a href="http://m.jxedt.com/mnks/ckm1/zjlx/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>章节练习</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="8">
                          <a href="http://m.jxedt.com/mnks/ckm1/zxlx/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
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
                                  <el-tag type="danger">未缴</el-tag>
                                  <el-tag type="success">已缴</el-tag>
                                </el-col>
                              </el-row>
                            </div>
                          </el-card>
                        </el-col>
                      </el-row>
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>石羊湖考场</span>
                          <i class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px"></i>
                        </div>
                        <el-row type="flex" justify="space-around">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第一次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-around" style="padding-top: 20px">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第二次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                      </el-card>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane label="科目二" name="third">
                    <span slot="label">
                      <img :src="tabLabel[2].tabImg" width="120" />
                      <span class="title-name">科目二</span>
                      <span class="title-subname">场地驾驶技能考试</span>
                    </span>
                    <div class="content">
                      <el-row :gutter="20" style="padding-bottom: 10px">
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km2_82_212/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px">
                                <span>合格标准</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km2_82_603/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>侧方停车</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km2_82_604/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>曲线行驶</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km2_82_605/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
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
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>上坡起步</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>

                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km2_82_606/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
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
                                  <el-tag type="danger">未缴</el-tag>
                                  <el-tag type="success">已缴</el-tag>
                                </el-col>
                              </el-row>
                            </div>
                          </el-card>
                        </el-col>
                      </el-row>
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>石羊湖考场</span>
                          <i class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px"></i>
                        </div>
                        <el-row type="flex" justify="space-around">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第一次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-around" style="padding-top: 20px">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第二次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                      </el-card>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane label="科目三" name="fourth">
                    <span slot="label">
                      <img :src="tabLabel[3].tabImg" width="120" />
                      <span class="title-name">科目三</span>
                      <span class="title-subname">道路驾驶技能考试</span>
                    </span>
                    <div class="content">
                      <el-row :gutter="20" style="padding-bottom: 10px">
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km3_607_608/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px">
                                <span>评判标准</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km3/yyzl/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>语言指令</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km3/dgcz/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
                              <div style="padding: 10px;">
                                <span>灯光操作</span>
                              </div>
                            </el-card>
                          </a>
                        </el-col>
                        <el-col :span="6">
                          <a href="http://m.jxedt.com/km3_607_627/">
                            <el-card :body-style="{ padding: '0px' }">
                              <img src="//j2.58cdn.com.cn/jxedt/m/kaoshi/images/bmxz_2.png" class="image">
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
                                  <el-tag type="danger">未缴</el-tag>
                                  <el-tag type="success">已缴</el-tag>
                                </el-col>
                              </el-row>
                            </div>
                          </el-card>
                        </el-col>
                      </el-row>
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>石羊湖考场</span>
                          <i class="el-icon-location-outline" style="float: right; padding: 3px 0;font-size: 18px"></i>
                        </div>
                        <el-row type="flex" justify="space-around">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第一次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-around" style="padding-top: 20px">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="24" style="font-weight: bold;font-size: 14px">
                                第二次成绩
                              </el-col>
                            </el-row>
                            <div style="border: 1px solid #e9eaec"></div>
                            <el-row type="flex">
                              <el-col :span="24">
                                2018年6月19日18:11:29
                              </el-col>
                            </el-row>
                          </el-col>
                          <el-col :span="12">
                            <el-row type="flex">
                              <el-col :span="24":offset="14">
                                <el-badge value="合格" class="cjItem">
                                  <el-button circle class="cjCircle">100</el-button>
                                </el-badge>
                              </el-col>
                            </el-row>
                          </el-col>
                        </el-row>
                      </el-card>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane label="科目四" name="five">
                    <span slot="label">
                      <img :src="tabLabel[4].tabImg" width="120" />
                      <span class="title-name">科目四</span>
                      <span class="title-subname">安全文明驾驶常识</span>
                    </span>
                    定时任务补偿</el-tab-pane>
                </el-tabs>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
</template>

<script>

    export default {
        name: "xueche",
        data() {
            return {
                scroll:null,
                tabLabel:[
                  {tabImg:'static/png/tab-default.png'},
                  {tabImg:'static/png/tab-default.png'},
                  {tabImg:'static/png/tab-default.png'},
                  {tabImg:'static/png/tab-default.png'},
                  {tabImg:'static/png/tab-default.png'}
                ],
                activeName2: 'first',
                jly:{
                    xm:'教练员',
                    szqy:'江夏区',
                    scope: 4.0
                }
            }
        },
        created(){
        },
        methods:{
          handleClick(tab, event) {
              for (var item of this.tabLabel){
                  item.tabImg = "static/png/tab-default.png";
              }
              this.tabLabel[tab.index].tabImg = "static/png/tab-selected.png";
          }
        }
    }
</script>
