<style lang="less">
  #productList{
    font-size: 0.2rem;
    background: #dadada;
    .cpList{
      padding: 0.1rem 0;
      .cpItem{
        padding:0.1rem;
        margin: 0.05rem 0 0 0;
        background: #f2f2f2;
        .cpName{
          font-size: 0.22rem;
          font-weight: 700;
          color: #ffa700;
          border-bottom: #eb873a solid 0.02rem
        }
        .cpMoney{
          padding:0 0.05rem 0.02rem 0.05rem;
          font-size: 0.22rem;
          font-weight: 700;
          color: #eb873a;
          i{
            font-size: 0.2rem;
          }
          /*font-size: 22rem;*/
          /*font-weight: 700;*/

        }
        .cpMess{
            font-size: 0.13rem;
            margin-right:0.08rem ;
        }
        .cpOk{
          text-align: right;
          padding: 0.06rem 0;
        }
        .backColor{
          height: 0.05rem;
          background: linear-gradient(to right, #00ff43 , #00b8ff);
        }
        .backColorTop{
          height: 0.05rem;
          background: linear-gradient(to right , #00b8ff , #00ff43);
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
      <div class="cpItem" v-for="(item,index) in cpList">
        <div class="box-row" style="margin-bottom: 0.06rem">
          <div class="cpName box_row_100">
            <span style=";padding: 0 0.05rem 0.02rem 0.05rem">
              {{item.cpMc}}
            </span>
          </div>
          <div class="cpMoney">
            <i class="iconfont icon-ico-money"></i>
            {{item.cpJl/100}}
          </div>
        </div>
        <div class="box-row">
          <div class="cpMess box_row_100">
            产品信息:{{item.cpXx}}
          </div>
          <div class="cpOk">
            <el-button type="success" size="small"
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
