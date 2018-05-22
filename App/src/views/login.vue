<style lang="less">
@import  "./login.less";
</style>
<template>

<div id="login" style="background-size:100%;background: url('../../static/bg.png') no-repeat center;height: 100%">
  <div style="font-size: 0.5rem;text-align: center;margin-bottom: 0.8rem;color: white">登录</div>
  <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
  <Row>
    <Col span="24" >
      <md-field>
        <div >
          <Icon type="ios-person-outline" size="30" style="float: left;margin-top: 25px;margin-right: 10px;"></Icon>
          <md-input-item  style="float: left" ref="name" title="真实姓名"  placeholder="投保人姓名"  is-title-latent clearable >
          </md-input-item>
        </div>
        <div>
          <Icon type="ios-locked-outline" size="30" style="float: left;margin-top: 25px;margin-right: 10px;"></Icon>
          <md-input-item
            ref="id"
            title="身份证号"
            placeholder="投保人身份证号"
            is-title-latent
            clearable
          ></md-input-item>
        </div>

      </md-field>
    </Col>
  </Row>


<div style="padding: 0.2rem;margin-top: 0.8rem;" class="but">

  <Button  type="success" long @click="handleSubmit('formInline')">登录</Button>
</div>
    <div style="padding: 0.2rem" class="but">
      <Button type="error" long> 注册</Button>
    </div>

  </Form>

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
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$Message.success('Success!');
          } else {
            this.$Message.error('Fail!');
          }
        })
      }
    }
  }

</script>

