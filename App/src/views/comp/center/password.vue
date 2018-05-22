<style lang="less">
  #subpassword{
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 200;
    background-color: #fff;
  }
</style>
<template>
      <div id="subpassword" class="box">
        <div class="boxHeader">
            <div class="box-row" s>
              <div class="titLeft"
                   @click="$parent.compName = ''"
                   style="padding-top: 0.1rem">
                <Icon type="chevron-left" size="22"></Icon>
              </div>
              <div class="titCenter body-O" style="text-align: center;font-size: 0.36rem">
                密码修改
              </div>
              <div class="titRight">
              </div>
            </div>
        </div>
        <div class="body passForm">
          <md-field>
            <md-input-item
              title="原始密码"
              type="password"
              placeholder="请输入原始密码"
              align="right"
            ></md-input-item>
          </md-field>
          <md-field>
            <md-input-item
              title=" 新密码"
              type="password"
              placeholder="请输入新密码"
              align="right"
            ></md-input-item>
          </md-field>
          <md-field>
            <md-input-item
              title="确认密码"
              type="password"
              placeholder="请确认您的密码"
              align="right"
            ></md-input-item>
          </md-field>
          <div class="submit" style="padding:0.3rem "
               @click="$router.push({name:'yjtx'})">
            <Button type="success" long
                    style="padding: 0.18rem">
              确认修改
            </Button>
          </div>
        </div>
      </div>
</template>

<script>
    import {Icon,Button} from 'iview'
    import {InputItem, Field} from 'mand-mobile'
    export default {
        name: "password",
        components:{
          Icon,Button,
          [InputItem.name]: InputItem,
          [Field.name]: Field,
        },
        data(){
            return{
            }
        }
    }
</script>

<style scoped>

</style>
