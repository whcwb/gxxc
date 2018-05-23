<style lang="less">
  #register{

  }
</style>
<template>
      <div id="register" class="box">
        <headTit tit="注册"></headTit>
        <div class="md-example-child md-example-child-steps md-example-child-steps-0">
          <md-steps
            :current="stepscurrent"
            :steps="steps">
          </md-steps>
        </div>
        <div class="body" v-if="stepscurrent==0" style="padding: 0 0.35rem">
          <div class="md-example-child md-example-child-input-item-2">
            <md-field>
              <md-input-item
                title="手机号"
                type="phone"
                v-model="phone"
                placeholder="phone xxx xxxx xxxx"
              ></md-input-item>
            </md-field>
          </div>
          <div class="box-row" style="margin-top: 0.2rem;padding: 0.3rem">
            <div style="color: #2d8cf0" @click="$router.push({name:'login'})">登录</div>
            <div class="body-O" style="text-align: right">
              <Button type="primary">下一步</Button>
            </div>
          </div>
        </div>
        <div class="body" v-if="stepscurrent==1">

        </div>
        <div class="body" v-if="stepscurrent==2">

        </div>
      </div>
</template>

<script>
    import headTit from '../comp/headTit'
    import {Steps,InputItem, Field} from 'mand-mobile'
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
            phone:''
          }
        },
    }
</script>

<style scoped>

</style>
