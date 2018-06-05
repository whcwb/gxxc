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
          </slot>
        </div>
        <div class="body-O tit" :style="{textAlign:titAlign}">
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
          tit:{//header的名字
            type:String,
            default:'tit'
          },
          titAlign:{//header内容的对齐方式  left  right  默认center
            type:String,
            default:'center'
          },
          leftToName:{//返回button 的去向 传递 路由的path  默认 back() 返回上一级
            type:String,
            default:'back()'
          }
        },
      methods:{
        back(){
          if(this.leftToName ==='back()'){
            this.$router.back()
          }else {
            this.$router.push({path:this.leftToName})
          }
        }
      }
    }
</script>

<style scoped>

</style>
