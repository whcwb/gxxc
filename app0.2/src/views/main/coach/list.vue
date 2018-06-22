<style scoped>
  .dropdown_menu{
    width: 33%;
    display: inline-block;
  }
  i{
    font-size: 14px;
  }
</style>
<template>
  <div>
    <div class="_head" style="height: 40px;text-align: center">
      <i style="font-size: 14px">教练推荐</i>
    </div>
    <div style="clear: both"></div>
    <!--<div>-->
      <!--<div >-->
        <!--<md-drop-menu-->
          <!--ref="dropMenu1"-->
          <!--:data="data"-->
        <!--&gt;</md-drop-menu>-->
      <!--</div>-->
    <!--</div>-->
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
</template>

<script>
  import { DropMenu } from 'mand-mobile'
    export default {
        name: "list",
      components: {
        [DropMenu.name]: DropMenu,
      },
      data(){
          return {
            coach:{jlPf:5},
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
      created(){
        this.util.auto(window, document ,7.5)
      }
    }
</script>

<style scoped>

</style>
