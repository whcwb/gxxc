<style lang="less">
  #jxList{
    .messList{
      padding: 0.2rem 0.1rem;
      .list{
        border-bottom: #949494 solid 2px;
        padding-bottom: 0.2rem;
        margin: 0.15rem;
      }
    }
  }
</style>
<template>
      <div id="jxList" class="box">
        <div>
          <box-head tit="训练场" leftToName="/home">
            <div slot="left" style="color: #E0DADF">
              <i class="iconfont icon-left"></i>
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
            <div style="width: 25%">
              <select class="selectSTY" name="" id="">
                <option :value="item.name"
                        v-for="(item,index) in qylist"
                        :selected="index==2">{{item.name}}</option>
              </select>
            </div>
        </div>
        <div class="body messList">
          <div class="list box-row"  v-for="(item,index) in [,,,,,,,,,]">
            <div style="width: 2rem" @click="$router.push({name:'jxMess'})">
              <img src="static/images/xlc.jpg"
                   width="100%"
                   height="100%"
                   alt="">
            </div>
            <div class="body-O" style="text-align: left;padding: 0 0 0 0.2rem">
               <div style="overflow: hidden">
                    <span style="font-size: 0.3rem;font-weight: 600">
                    ***驾校训练场
                    </span>
                    <span style="float: right;font-size: 0.3rem;font-weight: 600;color: #2d8cf0">
                        <i class="iconfont icon-ico-money"></i>
                        2500.00元
                    </span>
               </div>
              <div style="font-size: 0.24rem">
                地址：光谷大道光谷一路52号
              </div>
              <div style="font-size: 0.24rem;overflow: hidden">
                交通：566 ， 567 ，568
                <mt-button type="primary"
                           size="small"
                           style="float: right"
                >立即报名</mt-button>
              </div>
            </div>
          </div>
        </div>
      </div>
</template>

<script>
    import { Header,Button } from 'mint-ui';
    import list from '../homepage/qylist'
    export default {
        name: "",
        components:{
          [Header.name]:Header,
          [Button.name]:Button
        },
        created(){
          console.log(this.$route)
        },
        data(){
          return{
            qylist:list.qyList,
            searchVal:'',
            Search:''
          }
        }
    }
</script>

<style scoped>

</style>
