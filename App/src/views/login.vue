<style lang="less">
@import  "./login.less";
</style>
<template>

<div id="login" style="background-size:100%;background: url('../../static/bjt.jpg') no-repeat center;height: 100%">
  <div style="font-size: 0.5rem;text-align: center;margin-bottom: 0.8rem;color: white">登录</div>
  <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
  <Row>
    <Col span="24" style="background-color:rgba(255,255,255,0.1) ">
      <md-field>
        <div>
          <md-input-item
            title="手机号"
            type="phone"
            v-model="formInline.phone"
            placeholder=" xxx xxxx xxxx"
          >
            <Icon type="ios-person-outline" slot="left"  size="30" ></Icon>
          </md-input-item>

        </div>
        <div>
          <md-input-item
            title="密码"
            type="password"
            v-model="formInline.password"
            placeholder=" *********"
          >
            <Icon type="ios-locked-outline" slot="left" size="30"></Icon>

          </md-input-item>
        </div>

      </md-field>
    </Col>
  </Row>
  </Form>
<div style="padding: 0.2rem;margin-top: 0.8rem;" class="but">
  <Button  type="success" long   style="font-size: 0.3rem" @click="$router.push({name:'home'})">登录</Button>
</div>
<div style="padding: 0.2rem" class="but" >
  <Button type="error" long   style="font-size: 0.3rem" @click="$router.push({name:'register'})"> 注册</Button>
</div>

</div>
</template>

<script>
  import {InputItem, Field,} from 'mand-mobile'
  import {Button,Icon,Form,FormItem ,Input ,Row,Col} from 'iview'
  export default {
    name: 'input-item-demo',
    /* DELETE */
    title: '普通输入框',
    /* DELETE */
    components: {
      [InputItem.name]: InputItem,
      [Field.name]: Field,
      Button,
      Icon,Form,FormItem ,Input,Row,Col
    },
    data () {
      return {
        formInline: {
          phone:"",
          user: '',
          password: ''
        },
        ruleInline: {
          user: [
            { required: true, message: '请输入账号', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { type: 'string', min: 6, message: 'The password length cannot be less than 6 bits', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      asd(it){
        alert(it)
      },
      handleSubmit(name) {
        console.log(name);
        if (this.formInline.phone === '' || this.formInline.password === '' ){
          this.$Message.error('庆asdfasdgsda !');
          return;
        }
        this.$Message.success("success");
      }
    }
  }

</script>

