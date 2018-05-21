<style lang="less">
@import  "./login.less";
</style>
<template>

  <div class="md-example-child md-example-child-input-item-0">

    <md-field>
      <md-input-item
        ref="input0"
        title="账号"
        placeholder="手机号"
        :maxlength="5"
      ></md-input-item>
      <md-input-item
        ref="input0"
        title="密码"
        placeholder="请输入您的密码"
        :maxlength="5"
      ></md-input-item>
    </md-field>
  </div>
</template>

<script>
  import {InputItem, Field} from 'mand-mobile'

  export default {
    name: 'input-item-demo',
    /* DELETE */
    title: '普通输入框',
    /* DELETE */
    components: {
      [InputItem.name]: InputItem,
      [Field.name]: Field,
    },
  }

</script>
