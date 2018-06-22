<style lang="less">
  #coachList {
    font-size: 0.2rem;
    .mint-tab-item-label{
      font-size: 0.15rem;
    }
    .el-rate__icon{
      font-size: 0.15rem
    }
    .Jl_list{
      height:0.8rem;
      padding: 0.05rem;
      margin-top: 0.08rem;
      border-bottom: solid 0.04rem #c7c7c7;
      .leftimg{
        width: 1rem;
        background:no-repeat center;
      }
    }
  }
</style>
<template>
  <div id="coachList" class="box_col">
    <div>
      <mt-header title="教练推荐">
        <router-link to="" slot="left">
          <mt-button icon="back"></mt-button>
        </router-link>
      </mt-header>
    </div>
    <div>
      <mt-navbar v-model="selected">
        <mt-tab-item id="1">推荐</mt-tab-item>
        <mt-tab-item id="2">区域</mt-tab-item>
        <mt-tab-item id="3">价格</mt-tab-item>
      </mt-navbar>
    </div>
    <div class="box_col_auto">
      <div class="Jl_list">
        <div class="box-row" style="height: 100%;">
          <div class="leftimg" :style="{backgroundImage: 'url('+imgurl+')'}">
          </div>
          <div class="box_row_100" style="margin-left: 0.05rem">
            <div class="box_col">
              <div class="box_col_100" style="font-size: 0.16rem">
                王教练(12年教龄)
              </div>
              <div class="box_col_100" style="margin-left: 0.06rem">
                <div class="box-row">
                  <div class="box_row_100">
                    <el-rate
                      v-model="rateVal"
                      disabled
                      show-score
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                  </div>
                  <div style="font-size: 0.15rem;padding: 0 0.05rem;color: #eb873a">
                    3300 <span style="font-size: 0.14rem">元</span>
                  </div>
                </div>
              </div>
              <div class="box_col_100" style="font-size: 0.14rem">
                ***驾校****训练基地欢迎你
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>


    <div v-show="false">

      <div class="_head" style="height: 40px;text-align: center">
        <i style="font-size: 14px">教练推荐</i>
      </div>
      <div style="clear: both"></div>
      <div class="">
        <div class="" style=";border-bottom: 1px solid #eee">
          <div class="left" style="width: 30%;float: left">
            <img src="/static/png/tab-default.png" style="width: 100%">
          </div>
          <div class="mid" style="width: 70%;float: left">
            <div class="mid_up">
              <i>赵教练（12年教龄）</i>
              <i class="el-icon-circle-check-outline" style="color: #00a854"></i>
            </div>
            <div class="mid_mid">
              <el-rate
                v-model="coach.jlPf"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value}">
              </el-rate>
            </div>
            <div class="mid_down">
              <i>天天驾校欢迎您</i>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import {DropMenu} from 'mand-mobile'

  export default {
    name: "list",
    components: {
      [DropMenu.name]: DropMenu,
    },
    data() {
      return {
        selected:'1',
        imgurl:'static/home/car0000.png',
        rateVal:4.6,

        coach: {jlPf: 5},
        data: [
          {
            text: '排量',
            options: [
              {
                text: '1.6L',
              },
              {
                text: '1.8L',
              },
              {
                text: '2.0L',
              },
              {
                text: '1.2T',
              },
              {
                text: '1.4T',
              },
              {
                text: '1.6T',
              },
            ],
          },
          {
            text: '变速箱',
            options: [
              {
                text: '手动挡',
              },
              {
                text: '自动挡',
              },
              {
                text: '手自一体',
              },
            ],
          },
        ],

      }
    },
    created() {
      this.util.auto(window, document, 4)
    }
  }
</script>

<style scoped>

</style>
