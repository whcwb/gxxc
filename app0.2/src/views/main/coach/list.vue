<template>
  <div>
    <div class="head">
      <span>教练推荐</span>
    </div>
    <div>
      <div>
        <md-drop-menu
          ref="dropMenu1"
          :data="data1"
        ></md-drop-menu>
      </div>
      <div>
        <md-drop-menu
          ref="dropMenu2"
          :data="data2"
        ></md-drop-menu>
      </div>
      <div>
        <md-drop-menu
          ref="dropMenu3"
          :data="data3"
        ></md-drop-menu>
      </div>
    </div>
    <div class="list">
      <div class="item">
        <div class="left">

        </div>
        <div class="mid">

        </div>
        <div class="right">

        </div>
      </div>
    </div>

  </div>
</template>w

<script>
  import { DropMenu } from 'mand-mobile'
    export default {
        name: "list",
      data(){
          return {
            data1: [
              {
                text: '一级选项1',
                options: [
                  {
                    text: '二级选项1',
                  },
                  {
                    text: '二级选项2',
                  },
                ],
              }
              ],
          }
      }
    }
</script>

<style scoped>

</style>
