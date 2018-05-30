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
        <!--<div class="box-row" style="background-color: #86a9cd;height: 0.7rem;padding: 0 0.2rem 0 0.1rem">-->
          <!--<div class="body-O jxFind">-->
            <!--<i class="iconfont "></i>-->
            <!--<input class="SearchInput"-->
                   <!--placeholder="请输入关键字搜索"-->
                   <!--type="text" v-model="Search">-->
          <!--</div>-->
          <!--<div style="width: 25%">-->
            <!--<select class="selectSTY" name="" id="">-->
              <!--<option :value="item.name"-->
                      <!--v-for="(item,index) in qylist"-->
                      <!--:selected="index==2">{{item.name}}</option>-->
            <!--</select>-->
          <!--</div>-->
        <!--</div>-->
        <div class="body">
          <div class="listItem" v-for="(item,index) in [,,,,]">
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
    },
    data(){
      return{
        Search:'',
        qylist:[]
      }
    }
  }
</script>

<style scoped>

</style>
