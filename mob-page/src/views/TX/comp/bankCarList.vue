<template>
      <div id="bankList" class="box">
        <div class="box-row"
             style="height: 0.8rem;line-height: 0.8rem;
             color: #fff;
             background-color: #00000000">
          <div style="width: 0.6rem;text-align: center"
               @click="$parent.compName=''">
            <i class="iconfont icon-left1"></i>
          </div>
          <div class="body" style="font-size: 0.3rem;font-weight: 700;line-height: 0.85rem">
            我的银行卡
          </div>
          <div style="width: 0.6rem;text-align: center"
            @click="$parent.compName='addbankCard'">
            <i class="iconfont icon-add" style="font-size: 0.44rem"></i>
          </div>
        </div>
      </div>
</template>

<script>
    export default {
        name: "bankCarList",
      mounted(){
        this.MyFunc.auto(window, document,7.5)
      },
    }
</script>

<style scoped>

</style>
