<template>
      <div class="box">
          <headTit tit="我的团队"></headTit>
          <md-tab-bar
            :titles="titles"
          ></md-tab-bar>
          <div class="body" style="">

          </div>
      </div>
</template>

<script>
    import headTit from '../comp/headTit'
    import { TabBar } from 'mand-mobile'
    export default {
        name: "index",
        components:{
          headTit,
          [TabBar.name]: TabBar,
        },
        data(){
          return{
            titles: ['全部','一级','二级']
          }
        }
    }
</script>

<style scoped>

</style>
