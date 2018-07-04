<style lang="less">
  #productList{
    font-size: 0.2rem;
    background: #f2f2f2;
    .cpList{
      padding:0 0 0.1rem 0;
      .cpItems{
        /*height: 0.8rem;*/
        margin-bottom: 0.05rem;
        background-color: #fff;
        .titType{
          border-radius: 0.3rem;
          padding-top: 0.1rem;
          font-size: 0.18rem;
          font-weight: 700;
          color: #0a87e4;
        }
        .cpMess{
          font-size: 0.14rem;
          padding: 0.1rem 0;
          color: #000;
          width: 100%;display: block;word-break: break-all;
          padding-top: 0.08rem;
        }
        .cpMoney{
          padding:0.1rem 0.05rem 0.02rem 0.05rem;
          font-size: 0.2rem;
          font-weight: 700;
          color: #eb873a;
          i{
            font-size: 0.2rem;
          }
        }
      }
    }

  }
</style>
<template>
  <div class="box_col" id="productList">
    <mt-header title="套餐">
      <div slot="left" @click="$router.back()">
        <mt-button icon="back"></mt-button>
      </div>
    </mt-header>
    <div class="box_col_auto cpList">

      <div class="cpItems box-row"
           v-for="(item,index) in cpList">
        <!--<div style="width:0.9rem">-->
          <!--<div class="titType">-->
            <!--{{item.cpMc}}-->
          <!--</div>-->
        <!--</div>-->
        <div class="box_row_100" style="padding-left: 0.12rem">
          <div class="titType">
            {{item.cpMc}}
          </div>
          <div class="cpMess" style="">
            {{item.cpXx}}
          </div>
        </div>
        <div class="box_col" style="width: 1rem;">
            <div class="box_col_100 cpMoney">
              <i class="iconfont icon-ico-money"></i>
              {{item.cpJl/100}}
            </div>
            <div class="box_col_100" style="padding-top: 0.1rem">
              <el-button type="primary" size="small"
                         @click="paymoney(item)">购买套餐</el-button>
            </div>
        </div>

      </div>
    </div>

  </div>

</template>

<script>
  export default {
    name: "index",
    components: {},
    data(){
      return{
        cpList:[]
      }
    },
    created(){
      this.util.auto(window, document ,4)
      this.getCplist()
    },
    methods:{
      getCplist(){
        var v = this
        this.$http.post(this.apis.CPLIST).then((res)=>{
            if(res.code == 200 && res.result){
              v.cpList =  res.result
            }
        }).catch((err)=>{

        })
      },
      paymoney(item){
        this.$router.push({name:'pay',
          params:item})
      }
    }

  }
</script>

<style scoped>

</style>
