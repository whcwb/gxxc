<style lang="less">
  #jxList{
    .messList{
      padding: 0.2rem;
    }
  }
</style>
<template>
      <div id="jxList" class="box">
        <div >
            <mt-header title="训练场">
              <mt-button slot="left" @click="$router.back()">返回</mt-button>
            </mt-header>
        </div>
        <div class="body messList">
          <div class="list">
            训练场
          </div>
        </div>
      </div>
</template>

<script>
    import { Header,Button } from 'mint-ui';
    export default {
        name: "",
        components:{
          [Header.name]:Header,
          [Button.name]:Button
        }
    }
</script>

<style scoped>

</style>
