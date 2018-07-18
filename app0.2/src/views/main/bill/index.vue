<style lang="less">
  #bill{
    .billList{
      background-color: #fff;
      margin-bottom: 0.05rem;
      .tit{
      }
    }
    font-size: 0.2rem;
    .billItem{
      background-color: #fff;
      margin-bottom: 0.05rem;
      height: 1rem;
      .iconImg{
        width: 0.8rem;
        position: relative;
        img{
          width:0.5rem;
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%,-50%);
        }
      }
    }
  }
</style>
<template>
      <div id="bill" class="box_col" style="background-color: #f2f2f2">
        <div>
          <mt-header title="账单">
            <router-link to="/Home" slot="left">
              <mt-button icon="back"></mt-button>
            </router-link>
          </mt-header>
        </div>
        <div class="box_col_auto">
          <div class="billItem box-row" v-for="(item,index) in pageList">
            <div class="iconImg">
              <img
                :src="item.zjFs=='1' ? 'static/bank/zd1.png' : 'static/bank/zd2.png'" alt="">
            </div>
            <div class="box_row_100">

                <div class="box_col">
                  <div class="box_col_100">
                      <div class="box-row" style="padding-top: 0.12rem">
                          <div class="box_row_100">
                              {{item.mxlx | mxlx}}
                          </div>
                          <div style="padding: 0 0.15rem">
                              {{item.zjFs | zjFs}}
                              {{item.zjJe/100}}
                          </div>
                      </div>
                  </div>
                  <div class="box_col_100">
                      <div class="box-row" style="padding: 0.1rem 0 0.02rem 0">
                        <div class="box_row_100" style="font-size: 0.15rem">
                          {{item.cjsj}}
                        </div>
                        <div style="font-size: 0.13rem;font-weight: 700;padding: 0 0.15rem">
                          <div>
                            <div :style="{color:item.zjZt=='1' ? '#000' : 'red'}">{{item.zjZt | zjZt}}</div>
                            <div>{{item.zjBz}}</div>
                          </div>
                          <!--<div v-show="item.zjZt==0">-->
                            <!--<div>{{item.txShZt | txShZt}}</div>-->
                            <!--<div v-show="item.txShZt==2">{{item.zjBz}}</div>-->
                          <!--</div>-->
                        </div>
                      </div>
                  </div>
                </div>

            </div>
          </div>
          <div v-show="pageList.length==0" style="font-size: 0.5rem;font-weight: 700;color: #999999;text-align: center;line-height: 2.8rem">
            暂无账单数据
          </div>
        </div>
      </div>
</template>

<script>
  import { Header,Button } from 'mint-ui'
    export default {
        name: "index",
        components:{
          [Header.name]:Header,
            [Button.name]:Button
        },
        filters:{
          zjZt:(val)=>{
            switch (val){
              case '0':
                return '提现冻结'
                break;
              case '1':
                return '处理成功'
                break;
              case '2':
                return '提现失败'
                break;
            }
          },
          txShZt:(val)=>{
            switch (val){
              case '0':
                return '待审核'
                break;
              case '1':
                return '待打款'
                break;
              case '2':
                return '审核失败'
                break;
            }
          },
          mxlx:(val)=>{
            switch (val){
              case '1':
                return '充值'
              break;
              case '2':
                return '分佣'
              break;
              case '3':
                  return '消费'
              break;
              case '4':
                return '提现'
              break;
            }
          },
          zjFs:(val)=>{
            switch (val){
              case '1':
                return ''
                break;
              case '-1':
                return '-'
                break;
            }
          }
        },
        data(){
          return{
            pageList:[]
          }
        },
        created(){
          this.util.auto(window, document, 4)
          // this.zdListType()
          this.getList()
        },
        methods:{
          zdListType(){
            this.$http.post(this.apis.ZDLISTTYPE,{'typeCode':''}).then((res)=>{
              console.log(res)6

            }).catch((err)=>{

            })
          },
          getList(){
              var v = this
              this.$http.post(this.apis.ZDLIST).then((res)=>{
                console.log(res)
                if(res.code==200){
                   v.pageList = res.page.list
                }
              }).catch((err)=>{})

          }
        }
    }
</script>

<style scoped>

</style>
