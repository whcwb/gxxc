<style lang="less">
  .box{//纵向
    height: 100%;
    display: flex;
    flex-direction: column ;
    .body-D{
      flex:1;
    }
  }
  .box-row{//横向
    display: flex;
    flex-direction: row ;
    .body-O{
      flex:1;
    }
    .body-F{
      flex:1;
      overflow: auto;
    }
  }
  #boxHead{
    height: 0.8rem;
    background-color: #2d8cf0;
    text-align: center;
    line-height: 0.8rem;
    .left,.right{
      width: 0.6rem;
    }
    .tit{
      color: #fff;
      font-size: 0.3rem;
    }

  }
</style>
<template>
      <div id="boxHead" class="box-row">
        <div class="left" @click="back">
          <slot name="left" >
              返回
          </slot>
        </div>
        <div class="body-O tit">
            {{tit}}
        </div>
        <div class="right">
          <slot name="right" class="right">

          </slot>
        </div>
      </div>
</template>

<script>
    export default {
        name: "boxHead",
        props:{
          tit:{
            type:String,
            default:'tit'
          },
          leftToName:{
            type:String,
            default:'back()'
          }
        },
      methods:{
        back(){
          if(this.leftToName ==='back()'){
            this.$router.back()
          }else {
            this.$router.push({name:this.leftToName})
          }
        }
      }
    }
</script>

<style scoped>

</style>
