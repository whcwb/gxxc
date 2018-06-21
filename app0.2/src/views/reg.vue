<style lang="less">
  #reg {
    font-size: 0.2rem;
    background: url('/static/reg/regBack.png') no-repeat top;
    background-size: 100%;
    .mar_bot{
      margin-bottom: 0.16rem;
    }
  }
</style>
<template>
  <div id="reg" class="box_col">
    <div style="height: 2.7rem">
    </div>
    <div style="color: #fff;font-size: 0.3rem;padding-left:20% ">
      欢迎加入我们
    </div>
    <div class="box_col_100" style="padding: 0.3rem">
      <div class="mar_bot">
        <el-input v-model="form.yhZh"
                  clearable
                  placeholder="请输入手机号"></el-input>
      </div>

      <div class="mar_bot box-row">
        <div class="box_row_100">
          <el-input v-model="code"
                    clearable
                    placeholder="请输入验证码"
                    @change="changeCode"
          ></el-input>
        </div>
        <div style="margin-left: 0.15rem">
          <el-button
            style="background-color: #f2b121"
            type="warning">获取验证码</el-button>
        </div>
      </div>

      <div class="mar_bot">
        <el-input v-model="form.yhMm" placeholder="请设置帐号密码"></el-input>
      </div>
      <div>
        注册协议
      </div>
      <div>
        立即注册
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "reg",
    components: {},
    data(){
      return{
        code:'',
        form:{
          yhZh:'帐号',
          yhMm:'密码',
          yhYyyqm:localStorage.getItem('yqm'),
          yhLx:'1',
          addType:'3',
        },
      }
    },
    methods:{
      changeCode(value){
        console.log(value)
      },
    }

  }
</script>

<style scoped>

</style>
