<style lang="less">
  #kcfb{
    .listItem{
      border: solid 1px #949494;
      margin-bottom: 0.4rem;
      width: 48%;
      margin: 0.2rem auto;
      font-size: 0.22rem;
    }
  }

</style>
<template>
      <div id="kcfb" class="box">
        <div>
          <mt-header title="考场分布">
            <mt-button slot="left" @click="$router.push({name:'Home'})">返回</mt-button>
          </mt-header>
        </div>
        <div class="box-row" style="background-color: #86a9cd;height: 0.7rem;padding: 0 0.2rem 0 0.1rem">
          <div class="body-O jxFind">
            <i class="iconfont "></i>
            <input class="SearchInput"
                   placeholder="请输入关键字搜索"
                   type="text" v-model="Search">
          </div>
          <!--<div style="width: 25%">-->
            <!--<select class="selectSTY" name="" id="">-->
              <!--<option :value="item.name"-->
                      <!--v-for="(item,index) in qylist"-->
                      <!--:selected="index==2">{{item.name}}</option>-->
            <!--</select>-->
          <!--</div>-->
        </div>
        <div class="body" style="padding: 0.2rem">
          <div class="box-row-list">
            <div class="listItem" v-for="(item,index) in [,,,,]">
              <h2 style="margin: 0.1rem 0">柏林考场</h2>
              <div>
                <img src="/static/blkc.png"
                     style="width: 100%;height: 1.5rem"
                     alt="">
              </div>
              <div class="" style="text-align: left">
                <div style="margin: 0.2rem">
                    <span style="color: #00d5ff;">
                      <i class="iconfont icon-dingwei"></i>
                      位置：
                    </span>
                    <div>
                      湖北省武汉市蔡甸区X088
                    </div>
                </div>
                <div style="margin: 0.2rem">
                  <span style="color: #ff7d00">
                      <i class="iconfont icon-zhilupai"></i>
                      交通：
                    </span>
                    <div>
                        655路 ，667路 ，996路 ，855路
                    </div>
                </div>
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
