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
              <mt-button slot="left" @click="$router.push({name:'index'})">返回</mt-button>
            </mt-header>
        </div>
        <div class="body messList">
          <div class="list box-row">
            <div style="width: 2rem">
              <img src="static/xlc.jpg"
                   width="100%"
                   height="100%"
                   alt="">
            </div>
            <div class="body-O" style="text-align: right">
               <div>
                    ***驾校训练场
               </div>
              <div>
                占地10万亩
              </div>
            </div>
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
