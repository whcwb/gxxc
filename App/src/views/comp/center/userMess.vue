<style lang="less">
  #usermess{
    background-color: #ededed;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 200;
    .userMessList{
      .listBox{
        background-color: #fff;
        padding:0.2rem;
        margin-top: 0.2rem;
        .box-row{
          border-bottom: solid 2px #dbdbdb;
          height: 0.8rem;
          line-height: 0.8rem;
        }
      }
    }
  }
</style>
<template>
      <div id="usermess" class="box">
        <div class="boxHeader" style="background-color: #fff">
          <div class="box-row" s>
            <div class="titLeft"
                 @click="$parent.compName = ''"
                 style="padding-top: 0.1rem">
              <Icon type="chevron-left" size="22"></Icon>
            </div>
            <div class="titCenter body-O" style="text-align: center;font-size: 0.36rem">
              用户信息
            </div>
            <div class="titRight">
            </div>
          </div>
        </div>
        <div class="body userMessList">
            <div class="listBox">
                <div class="box-row">
                  <div class="body-O">
                    头像
                  </div>
                  <div>
                    123
                  </div>
                </div>
                <div class="box-row">
                  <div class="body-O">
                    昵称
                  </div>
                  <div>
                    店小二
                  </div>
                </div>
            </div>
            <div class="submit" style="padding:0.3rem "
                 @click="$router.push({name:'yjtx'})">
              <Button type="success" long
                      style="padding: 0.18rem">
                确认
              </Button>
            </div>
        </div>
      </div>
</template>

<script>
    import {Icon,Button} from 'iview'
    export default {
        name: "userMess",
        components:{
          Icon,Button
        },
    }
</script>

<style scoped>

</style>
