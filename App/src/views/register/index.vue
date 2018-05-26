<style lang="less">
  #register{
      position: relative;
      .img{
        background-color: #fff;
        position: absolute;
        top:0;
        z-index: 200;
      }
      .body_box{
        padding: 0 0.35rem
      }
  }
</style>
<template>
      <div id="register" class="box">
        <div class="img" v-if="img">
          <img src="../../img/sys.jpg" width="100%" alt="">
        </div>
        <headTit tit="注册"></headTit>
        <div class="md-example-child md-example-child-steps md-example-child-steps-0">
          <md-steps
            :current="stepscurrent"
            :steps="steps">
          </md-steps>
        </div>
        <div class="body_box" v-if="stepscurrent==0">
          <div class="md-example-child md-example-child-input-item-2">
            <md-field>
              <md-input-item
                title="手机号"
                type="phone"
                v-model="phone"
                placeholder="电话号码"
              ></md-input-item>
            </md-field>
          </div>
          <div class="box-row" style="margin-top: 0.2rem;padding: 0.3rem">
            <div style="color: #2d8cf0;font-size: 0.32rem" @click="$router.push({name:'login'})">登录</div>
            <div class="body-O" style="text-align: right">
              <Button type="primary"
                      style="font-size: 0.3rem"
                @click="fistNext">下一步</Button>
            </div>
          </div>
        </div>
        <div class="body" v-if="stepscurrent==1">
          <div class="md-example-child md-example-child-input-item-2">
            <md-field>
              <md-input-item
                title="验证码"
                type="text"
                v-model="yzCode"
                placeholder="验证码"
              ></md-input-item>
            </md-field>
          </div>
          <div class="box-row" style="margin-top: 0.2rem;padding: 0.3rem">
            <div style="color: #2d8cf0;font-size: 0.32rem" @click="$router.push({name:'login'})">登录</div>
            <div class="body-O" style="text-align: right">
              <Button type="primary"
                      style="font-size: 0.3rem"
                      @click="TwONext">下一步</Button>
            </div>
          </div>
        </div>
        <div class="body" v-if="stepscurrent==2">
          <div class="md-example-child md-example-child-input-item-2">
            <md-field>
              <md-input-item
                title=" 请设置密码"
                type="password"
                placeholder="请设置密码"
                align="right"
              ></md-input-item>
            </md-field>
            <md-field>
              <md-input-item
                title="请确认密码"
                type="password"
                placeholder="请确认密码"
                align="right"
              ></md-input-item>
            </md-field>
          </div>
          <div class="box-row" style="margin-top: 0.2rem;padding: 0.3rem">
            <div style="color;font-size: 0.32rem: #2d8cf0" @click="$router.push({name:'login'})">登录</div>
            <div class="body-O" style="text-align: right">
              <Button type="primary"
                      style="font-size: 0.3rem"
                      @click="ThrNext">下一步</Button>
            </div>
          </div>
        </div>
      </div>
</template>

<script>
    import headTit from '../comp/headTit'
    import {Steps,InputItem, Field,Toast} from 'mand-mobile'
    import {Button} from 'iview'
    export default {
        name: "index",
        components:{
          [Steps.name]: Steps,
          [InputItem.name]: InputItem,
          [Field.name]: Field,
          headTit,Button
        },
        data() {
          return {
            img:true,
            stepscurrent:0,
            steps: [
              {
                name: '手机号码',
              },
              {
                name: '验证码',
              },
              {
                name: '设置密码',
              },
            ],
            phone:'',
            yzCode:'',
            password:''

          }
        },
        created(){
          var v = this
          setTimeout(function () {
              v.img=false
          },2*1000)
        },
        methods:{
          fistNext(){
            this.stepscurrent=1
          },
          TwONext(){
            this.stepscurrent=2
          },
          ThrNext(){
            Toast.succeed('注册成功，请重新登录',2000,true,)
            var v = this
            // Toast({
            //   icon:'',
            //   content:'注册成功，请重新登录',
            //   duration:2000,
            //   hasMask:true
            // })
            setTimeout(function () {
                v.$router.push({name:'login'})
            },1500)
          }
        }
    }
</script>

<style scoped>

</style>
