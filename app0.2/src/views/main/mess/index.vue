<style lang="less">
  @import "./xueche";
</style>
<template>
      <div id="xueche">
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
            <el-card class="card-body" shadow="never" style="height:300px;overflow: auto">
              <div slot="header" class="title">
                <span>学车进度</span>
              </div>
              <div>
                <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
                  <el-tab-pane name="first">
                    <span slot="label">
                      <img :src="tabLabel[0].tabImg" width="120" />
                      <span class="title-name">受理进度</span>
                      <span class="title-subname">受理成功可考试</span>
                    </span>
                    <div class="content">
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
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="second">
                    <span slot="label">
                      <img :src="tabLabel[1].tabImg" width="120" />
                      <span class="title-name">科目一</span>
                      <span class="title-subname">基础理论知识考试</span>
                    </span>
                    <div class="content">
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>考试成绩</span>
                        </div>
                        <el-row type="flex" justify="space-around">
                          <el-col :span="10">
                            <el-row type="flex" style="text-align: center">
                              <el-col :span="2" style="font-weight: bold;font-size: 26px">
                                0
                              </el-col>
                              <el-col :span="8" style="margin-top: 8px">
                                已考
                              </el-col>
                              <el-col :span="12" style="margin-top: 8px">
                                石羊湖考场
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
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>约考记录</span>
                        </div>
                        <el-row v-for="o in 4" :key="o" class="text item" justify="end" style="padding: 5px">
                          <el-col :span="18">
                            石羊湖考场-2018年6月19日18:46:55
                          </el-col>
                          <el-col :span="2" :offset="4">
                            <el-button icon="el-icon-location-outline" circle size="mini"></el-button>
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
                    角色管理</el-tab-pane>
                  <el-tab-pane label="科目三" name="fourth">
                    <span slot="label">
                      <img :src="tabLabel[3].tabImg" width="120" />
                      <span class="title-name">科目三</span>
                      <span class="title-subname">道路驾驶技能考试</span>
                    </span>
                    定时任务补偿</el-tab-pane>
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
