<style lang="less">
  @import "../../styles/box.less";
</style>
<template>
  <div class="boxHeader">
      <div class="box-row">
          <div class="titLeft" @click="back()" style="padding-top: 0.1rem">
              <Icon type="chevron-left" size="22"></Icon>
          </div>
          <div class="titCenter body-O" style="text-align: center;font-size: 0.36rem">
            {{tit}}
          </div>
          <div class="titRight">
              <slot name="headright">
              </slot>
          </div>
      </div>
  </div>
</template>

<script>
    import {Icon} from 'iview'
    export default {
        name: "headTit",
        components:{
          Icon
        },
        props:{
            tit:{
              type:String,
              default:'tit'
            }
        },
        methods:{
          back(){
            this.$router.back()
          }
        }
    }
</script>

<style scoped>

</style>
