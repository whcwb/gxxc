<style lang="less">
  #kcfb{
    .listItem{
      border: solid 1px #949494;
      margin-bottom: 0.4rem;
      width: 48%;
      margin: 0.2rem 1%;
      font-size: 0.22rem;
    }
  }

</style>
<template>
      <div id="kcfb" class="box">
        <div>
          <box-head tit="考场分布" leftToName="/home">
            <div slot="left" style="color: #E0DADF">
              <i class="iconfont icon-left1"></i>
            </div>
          </box-head>
        </div>
        <div class="box-row" style="background-color: #86a9cd;height: 0.7rem;padding: 0 0.2rem 0 0.1rem">
          <div class="body-O jxFind">
            <i class="iconfont "></i>
            <input class="SearchInput"
                   placeholder="请输入关键字搜索"
                   type="text" v-model="Search">
          </div>
          <div style="width: 25%;text-align: center">
            <select class="selectSTY" name=""
                    v-model="selectVal"
                    id="" style="text-align: center">
              <option :value="index"
                      v-for="(item,index) in qylist">{{item.quyu}}</option>
            </select>
          </div>
        </div>
        <div class="body" style="padding: 0.2rem">
          <div class="box-row-list">
            <div class="listItem" v-for="(item,index) in qumess">
              <h2 style="margin: 0.1rem 0">{{item.name}}</h2>
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
                      {{item.name}}
                      湖北省武汉市蔡甸区X088
                    </div>
                </div>
                <div style="margin: 0.2rem">
                  <span style="color: #ff7d00">
                      <i class="iconfont icon-zhilupai"></i>
                      交通：
                    </span>
                    <div>
                      {{item.gj}}
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
        selectVal:0,
        qylist:this.$route.params,
        qumess:[]
      }
    },
    watch:{
      selectVal:function (n,o) {
        this.qumess = this.qylist[n].mess
      }
    },
    created(){
      this.qumess = this.qylist[this.selectVal].mess
      console.log(this.$route.params)
    }

  }
</script>

<style scoped>

</style>
