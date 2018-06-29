<style lang="less">
  .timeList{
    padding: 0.15rem 0;
    font-size: 0.26rem;
      .list{
        background-color: #fff;
        margin-bottom: 0.2rem;
        border-bottom:solid 2px #949494;
        padding: 0.1rem 0.05rem;
        .ddsty{
          /*background-color: #eb873a;*/
          .box_row_100{
            padding-left: 0.2rem;
            text-align: right;
            font-size: 0.2rem;
            .text{
              font-size: 0.2rem;
            }
            .phone{
              a{
                color: #2d8cf0;
              }
            }
            .money{
              /*color: #ff8e00;*/
              font-weight: 600;
              font-size: 0.18rem;
              padding: 0.06rem;
              text-align: left;
            }
            .typ{
              font-weight: 500;
              font-size: 0.18rem;
              padding: 0.06rem;
            }
          }
        }
      }
  }
  .mint-navbar{
    .mint-tab-item{
      .mint-tab-item-label{
        font-size: 0.2rem;
      }
    }
  }
</style>
<template>
      <div class="box_col">
          <mt-header title="我的团队">
            <router-link to="/Home" slot="left">
              <mt-button icon="back"></mt-button>
            </router-link>
          </mt-header>
          <mt-navbar v-model="selected">
            <mt-tab-item id="0">全部</mt-tab-item>
            <mt-tab-item id="1">一级</mt-tab-item>
            <mt-tab-item id="2">二级</mt-tab-item>
          </mt-navbar>
          <div class="box_row_1auto timeList" style="">
            <div class="list" v-for="(item,index) in list">
              <div class="box-row ddsty">
                <div style="width: 0.6rem">
                  <img :src="item.userDetail.yhTx | yhTx"
                       style="width: 100%;border-radius: 0.5rem">
                </div>
                <div class="box_row_100" style="text-align: right;margin-left: 0.15rem">
                  <div class="box">
                    <div class="body text">
                      <div class="box-row">
                        <div class="box_row_100" style="text-align: left">
                          {{item.yhXm | yhXm}}
                        </div>
                        <div class="phone">
                          <a :href="'tel:'+item.yhSjhm">{{item.yhSjhm}}</a>
                        </div>
                      </div>
                    </div>
                    <div class="box-row">
                        <div class="money"
                             :style="{color:item.userDetail.ddSfjx=='1'?'#00b65f':'#ff8800'}">
                          {{item.userDetail.ddSfjx | ddSfjx}}
                        </div>
                        <div class="typ box_row_100">
                          {{item.userDetail.yhLx | yhLx}}
                        </div>
                        <div class="typ box_row_100">
                          {{item.userGrade | userGrade}}
                        </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="list.length==0" style="text-align: center">
            <span style="font-size: 0.25rem;font-weight: 700">
              无团队信息
            </span>
          </div>
      </div>
</template>

<script>
    import { Navbar, TabItem } from 'mint-ui';
    // import {Icon} from 'iview'
    export default {
        name: "index",
        components:{
          // Icon,
          [Navbar.name]: Navbar,
          [TabItem.name]: TabItem,
        },
        data(){
          return{
            selected:'0',
            list:[],
            pageList:{
              userGrade:0,
              pageSize:999999999,
              pageNum:1
            },
          }
        },
      filters:{
        yhLx:(val)=>{
          if(val==1){
            return '学员'
          }else if(val==3){
            return '会员'
          }
        },
        yhXm:(val)=>{
          if(val){
            return val
          }
          return '***'
        },
        yhTx:(val)=>{
          if(val){
            return val
          }
          return 'static/login/LOGO.png'
        },
        ddSfjx:(val)=>{
          switch (val){
            case '0':
              return '未交费'
            break;
            case '1':
              return '已交费'
          }
        },
        userGrade:(val)=>{
          switch (val){
            case '1':
              return '一级用户'
              break;
            case '2':
              return '二级用户'
          }
        }
      },
      watch:{
        selected:function(n,o) {
          this.pageList.userGrade = n
          this.teamList()
        }
      },
      created(){
        // this.list= this.ALL[0]
        this.teamList()
      },
        methods:{
          tabbarClick(n,o){
            var v =this
            v.list = v.ALL[n]
            this.teamList()
          },
          teamList(){
            var v = this
            this.$http.post(this.apis.TEAM,this.pageList).then((res)=>{
              console.log(res)
              v.list = res.page.list
            }).catch((err)=>{

            })
          }
        }

    }
</script>

<style scoped>

</style>
