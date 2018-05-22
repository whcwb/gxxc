<style lang="less">
  @import "./myCenter";
</style>
<template>
      <div id="center" class="box">
        <div class="center_tit">
          <div class="box-row" style="border-bottom: solid 2px #dedede">
            <div class="touxiang" @click="$router.push({name:'myMess'})">
              <img src="../../img/titcenter.png" alt="">
            </div>
            <div class="body-O userMess">
              <div class="name">
                张华
              </div>
              <div class="tjr">
                推荐人:全帅
              </div>
            </div>
            <div class="rzMess">
              <div class="name">
                <Icon type="checkmark-circled"
                      size="18"
                      color="#2bd865"></Icon>
                已实名
              </div>
              <div class="money">
                <Icon type="checkmark-circled"
                      size="18"
                      color="#2bd865"></Icon>
                已交费
              </div>
            </div>
          </div>
          <div class="box-row" style="padding-top:0.2rem ">
            <div class="yongjin">
                <div>
                  可提现佣金
                </div>
                <div>
                  0.00元
                </div>
            </div>
            <div class="body-O tixian"
                 @click="$router.push({name:'yjtx'})">
              <Button type="ghost">提现</Button>
            </div>
          </div>
        </div>
        <div class="box-row yingjin">
          <div class="body-O">
              <div class="tit" style="color: #45b95c">
                已提现佣金
              </div>
              <div class="money">
                0元
              </div>
          </div>
          <div style="border-left: 1px solid #bababa"></div>
          <div class="body-O">
            <div class="tit" style="color: #a4ae38">
              未结算佣金
            </div>
            <div class="money">
              0元
            </div>
          </div>
        </div>
        <div class="body">
          <div class="box-row boxlist" style="border-top: solid 15px #dedede">
              <div class="body-O" @click="$router.push({name:'yj'})">
                  <div class="icon">
                      <Icon type="social-usd"></Icon>
                  </div>
                  <div class="tit">
                    佣金
                  </div>
                  <div class="mess">
                      <span class="mess-chil">
                        0.00
                      </span>元
                  </div>
              </div>
              <div class="body-O" @click="$router.push({name:'orderlist'})">
                  <div class="icon">
                      <Icon type="ios-paper"></Icon>
                  </div>
                  <div class="tit">
                    订单
                  </div>
                  <div class="mess">
                        <span class="mess-chil">
                          0.00
                        </span>元
                  </div>
              </div>
              <div class="body-O" @click="$router.push({name:'yjtxmx'})">
                  <div class="icon">
                      <Icon type="android-funnel"></Icon>
                  </div>
                  <div class="tit">
                    提现明细
                  </div>
                  <div class="mess">
                        <span class="mess-chil">
                          0.00
                        </span>元
                  </div>
              </div>
          </div>
          <div class="box-row boxlist">
            <div class="body-O" @click="$router.push({name:'myteam'})">
                <div class="icon">
                    <Icon type="android-people"></Icon>
                </div>
                <div class="tit">
                  我的团队
                </div>
                <div class="mess">
                          <span class="mess-chil">
                            0.00
                          </span>人
                </div>
            </div>
            <div class="body-O" @click="showListen=true">
                <div class="icon">
                    <Icon type="social-buffer"></Icon>
                </div>
                <div class="tit">
                  邀请码
                </div>
            </div>
            <div class="body-O">
            </div>
          </div>
        </div>
        <div style="height: 1.02rem;border-top:solid 1px #dedede">
          <md-action-bar :actions="data"></md-action-bar>
        </div>
        <md-landscape v-model="showListen" :mask-closable="true">
          <img src="../../img/ewm.png">
        </md-landscape>
      </div>
</template>

<script>
    import {Button ,Icon} from 'iview'
    import { ActionBar, Toast,Landscape} from 'mand-mobile'

    export default {
        name: "index",
        components:{
          [Landscape.name]: Landscape,
          [ActionBar.name]: ActionBar,
          Button,Icon,
        },
        data(){
          return{
            showListen:false,
            data: [
              {
                text: '首页',
                onClick: this.handleClick,
              },
              {
                text: '我'
              }
            ]
          }
        },
        methods: {
          handleClick() {
            // Toast.succeed('Click')
            this.$router.push({
              name:'home'
            })
          },
        }
    }
</script>

<style scoped>

</style>
