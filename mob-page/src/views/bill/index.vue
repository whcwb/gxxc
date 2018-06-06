<style lang="less">
  #bill{
    .billList{
      .tit{
        background-color: #f2f2f2;
        padding: 0.1rem 0.2rem;
        .mon{
          color: #000;font-size: 0.26rem
        }
        .sz{//收支
          font-size: 0.2rem;
          .icon-ico-money{
            font-size: 0.2rem;
          }
        }
      }

    }
  }
</style>
<template>
      <div id="bill" class="box">
        <div>
          <box-head tit="账单" leftToName="home">
            <div slot="left" style="color: #E0DADF">
              <i class="iconfont icon-left1"></i>
            </div>
          </box-head>
        </div>
        <div class="body">
          <div class="billList" v-for="(item,index) in [,,,]">
            <div class="box-row" v-for="it in [,,,,,]">
              <div style="width: 1.3rem;padding: 0.2rem">
                <img
                  style="width: 100%"
                  src="/static/bill.png" alt="">
              </div>
              <div class="body-O" style="border-bottom: #949494 solid 1px;margin-right: 0.2rem">
                  <div class="box-row" style="padding-top: 0.25rem;
                          color: #505050;
                          font-size: 0.3rem;
                          font-weight: 700">
                    <div class="body-O">
                      零钱提现
                    </div>
                    <div>
                      -886.00
                    </div>
                  </div>
                  <div style="padding-top: 0.15rem;font-size: 0.2rem;
                  color: #949494;
                  font-weight: 600">
                    04-27  11:00
                  </div>
              </div>
            </div>
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
        created(){
          // this.zdListType()
          this.getList()
        },
        methods:{
          zdListType(){
            this.$http.post(this.apis.ZDLISTTYPE,{'typeCode':''}).then((res)=>{
              console.log(res)

            }).catch((err)=>{

            })
          },
          getList(){
              this.$http.post(this.apis.ZDLIST).then((res)=>{
                console.log(res)
                if(res.code==200){

                }
              }).catch((err)=>{})

          }
        }
    }
</script>

<style scoped>

</style>
