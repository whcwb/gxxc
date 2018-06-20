<style lang="less">
  #student{
    font-size: 0.2rem;
    .mint-navbar{
      .mint-tab-item{
        .mint-tab-item-label{
          font-size: 0.16rem;
        }
      }
    }

  }
</style>
<template>
  <div id="student" class="box">
    <mt-header title="我的学员">
      <router-link to="/Home" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <!--<div>-->
      <mt-navbar v-model="selected" style="font-size: 0.3rem">
        <mt-tab-item id="1">科目一</mt-tab-item>
        <mt-tab-item id="2">科目二</mt-tab-item>
        <mt-tab-item id="3">科目三</mt-tab-item>
        <mt-tab-item id="4">科目四</mt-tab-item>
        <mt-tab-item id="0">已结束</mt-tab-item>
      </mt-navbar>
    <!--</div>-->
    <div class="body" style="margin-top: 0.12rem">
        <div class="list box-row"
             v-for="(item,index) in studentList"
             @click="$router.push({name:'stuMess',params:{id:item.map.xyid}})"
             style="padding-bottom: 0.1rem ;border-bottom:solid 1px #dedede">
              <div style="font-size: 0.6rem;font-weight: 700;padding: 0.1rem 0.2rem;">
                    学
              </div>
              <div class="box_row_100" style="font-size: 0.2rem">

                <div class="box-row" style="padding: 0.15rem 0.2rem 0 0">
                  <div class="" style="padding:0 0.1rem 0 0">
                    {{item.map.yhxm}}
                  </div>
                  <div class="box_row_100" style="padding:0.03rem 0 0 0">
                    <a :href="'tel:'+item.map.yhzh">
                      <i class="iconfont icon-dianhua"
                          style="font-size: 0.2rem;color: #2b85e4"
                      ></i>
                    </a>
                  </div>
                  <div class="" style="padding:0.03rem 0 0 0">
                    <!--{{item.cjsj}}-->
                    08/05/06
                  </div>
                </div>

                <div class="box-row" style="font-size: 0.18rem;padding-top: 0.14rem;">
                  <div class="box_row_100" v-for="(km,bh) in item.map.markList">
                    <div style="font-size: 0.16rem">
                     {{km.kmBm | km}}
                    </div>
                    <div>
                      {{km.xyCj | xyCj}}分
                    </div>
                  </div>
                </div>
              </div>
              <div style="width:0.6rem;line-height: 1rem">
                 <i class="iconfont icon-right1" style="font-size: 0.4rem"></i>
              </div>
        </div>
        <div v-show="studentList.length==0" style="font-size: 0.6rem;text-align: center;line-height: 2rem;font-weight: 700">
          无学员数据
        </div>
    </div>
  </div>
</template>

<script>
    import { Navbar, TabItem } from 'mint-ui';
    export default {
        name: "student",
        components:{
          [Navbar.name]:Navbar,
          [TabItem.name]:TabItem
        },
        filters:{
          xyCj:(val)=>{
            if(val){
              return val
            }
            return '**'
          },
          km:(val)=>{
            switch (val){
              case '4':
                return '科目四'
              break;
              case '3':
                return '科目三'
              break;
              case '2':
                return '科目二'
              break;
              case '1':
                return '科目一'
              break;
              default:
                return val
            }
          }
        },
        data(){
          return{
            selected:'1',
            studentList:[]
          }
        },
        watch:{
          selected:function (n,o) {
            this.getList(n)
          }
        },
        created(){
          this.getList(this.selected)
        },
        methods:{
          getList(val){
            var v = this
            this.$http.post(this.apis.SUDENTLIST,{'xyZt':val}).then((res)=>{
              console.log(res)
              if(res.code==200){
                v.studentList = res.result.list
              }
            }).catch((err)=>{

            })
          }
        }
    }
</script>

<style scoped>

</style>
