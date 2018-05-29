<style lang="less">
  #kcfn{

  }

</style>
<template>
      <div id="kcfb" class="box">
        <div>
          <mt-header title="考场分布">
            <mt-button slot="left" @click="$router.push({name:'Home'})">返回</mt-button>
          </mt-header>
        </div>
        <div class="box-row-list">
          <div style="width: 25%;padding: 0.2rem">
            <span style="background-color: #949494;
            border: solid 1px #E0DADF;
            color: #E0DADF;
            padding: 0.1rem;border-radius: 0.3rem">
              柏林考场
            </span>
          </div>
          <div style="width: 25%;padding: 0.2rem"
                v-for="item in ['一','二','三']">
            <span style="background-color: #949494;
            border: solid 1px #E0DADF;
            color: #E0DADF;
            padding: 0.1rem;border-radius: 0.3rem">
              考场{{item}}
            </span>
          </div>
        </div>
        <div class="body">
          <div class="listItem box">
            <div>
              <img src="/static/blkc.png"
                   style="width: 100%;height: 3rem"
                   alt="">
            </div>
            <div class="box-row" style="padding: 0 0.25rem">
              <div class="body-O" style="margin: 0.2rem">
                <div>
                    名称：柏林考场
                </div>
                <div>
                    位置：湖北省武汉市蔡甸区X088
                </div>
              </div>
              <div class="body-O" style="margin: 0.2rem">
                交通：655 ，667 ，996 ，855
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
