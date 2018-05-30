<style lang="less">
  #jxList{
    .messList{
      padding: 0.2rem;
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
        <div >
            <mt-header title="训练场">
              <mt-button slot="left" @click="$router.push({name:'Home'})">返回</mt-button>
            </mt-header>
        </div>
        <div class="body messList">
          <div class="list box-row"  v-for="(item,index) in [,,,,,,,,,]">
            <div style="width: 2rem">
              <img src="static/xlc.jpg"
                   width="100%"
                   height="100%"
                   alt="">
            </div>
            <div class="body-O" style="text-align: left;padding: 0 0 0 0.2rem">
               <div style="text-align: left">
                    ***驾校训练场
                    <div style="float: right">
                          占地面积10万亩
                    </div>
               </div>
              <div>
                地址：光谷大道光谷一路52号
              </div>
              <div>
                交通：566 ， 567 ，568
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
