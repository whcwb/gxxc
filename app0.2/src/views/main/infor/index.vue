<style lang="less">
@import "./infor";
#infoSTY {
  background-color: #f2f2f2;
  .teamList{
    padding: 0.05rem 0;
    font-size: 0.2rem;
    .teamItem{
      background-color: #fff;
      margin-bottom: 0.05rem;
      /*height: 1rem;*/
      .iconImg{
        width: 0.8rem;
        position: relative;
        img{
          width: 0.5rem;
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%,-50%);
        }
      }
      .username{
        font-size: 0.16rem;
        font-weight: 700;
        color: #484646;
      }
      .phone{
        padding: 0 0.15rem;
        font-size: 0.16rem;
        font-weight: 700;
        a{
          color: #2d8cf0;
        }
      }
      .userMoney{
        font-size: 0.16rem;
        font-weight: 700;
      }
      .userType{
        font-size: 0.16rem;
        font-weight: 600;
        color: #2d8cf0;
      }
      .userGrade{
        font-size: 0.16rem;
        font-weight: 600;
        color: #808080;
        text-align: right;
        padding: 0 0.15rem;
      }
    }
  }

  .listitem{
    background-color: #fff;
    padding: 0.1rem;
    margin-bottom: 0.15rem;
    .yhDqzt{
      font-size: 0.16rem;
    }
    .phone{
      a{
        color: #00b6ff;
      }
    }
  }
}
</style>
<template>
  <div id="infoSTY" class="box_col">
    <div class="box-row header">
      <div class="box_row_100" style="position: relative;margin: 0 0.2rem 0 0.1rem">
        <input class="el-input__inner" type="text"
               placeholder="姓名查找"
               v-model="search" @keyup.enter="searchClick(search)">
      </div>
      <div class="titFun">
        <div @click="selectShow=!selectShow">
          {{defaultVal.text}}
        </div>
        <transition name="bounce">
          <div class="selectList" v-show="selectShow">
            <div class="selectItem" v-for="(item,index) in selectList" @click="selectChoose(item,index)">
              {{item.text}}
            </div>
          </div>
        </transition>
      </div>
    </div>
    <div class="box_col_auto teamList">
      <div class="teamItem" v-for="(item,index) in teamList" @click="goMess(item)">
          <div class="">
              <div class="" style="height: 0.8rem">
                  <div class="box-row" style="height: 100%">
                    <div class="iconImg">
                      <img :src="item.userDetail.yhTx | yhTx">
                    </div>

                    <div class="box_row_100">

                      <div class="box_col">
                        <div class="box_col_100">
                          <div class="box-row" style="padding-top: 0.12rem">
                            <div class="box_row_100 username">
                              {{item.yhXm | yhXm}}
                            </div>
                            <div class="phone">
                              <a :href="'tel:'+item.yhSjhm">{{item.yhSjhm}}</a>
                            </div>
                          </div>
                        </div>
                        <div class="box_col_100" style="padding-top: 0.08rem">
                          <div class="box-row">
                            <div class="userMoney box_row_100"
                                 :style="{color:item.userDetail.ddSfjx=='1'?'#00b65f':'#ff8800'}">
                              {{item.userDetail.ddSfjx | ddSfjx}}
                            </div>
                            <div :style="{color:item.userDetail.yhLx=='3'?'#ff000a':'#2d8cf0'}"
                                 class="userType box_row_100">
                              <span v-show="item.userDetail.ddSfjx=='1'">
                                {{item.userDetail.yhLx | yhLx}}
                              </span>
                            </div>
                            <div class="userGrade box_row_100">
                              {{item.userGrade | userGrade}}
                              {{item.yhslZt}}
                            </div>
                          </div>
                        </div>
                      </div>

                    </div>

                  </div>
              </div>
              <div v-if="item.userDetail.yhLx==1"
                   style="font-size: 0.14rem;padding: 0.02rem 0.15rem;border-top: slategray 1px solid">
                学员当前进度 >>> {{item.yhslZt | yhslZt}}
              </div>

          </div>
      </div>


      <div v-if="teamList.length==0"
          style="text-align: center;font-size: 0.22rem;line-height: 2rem;color: #989898">
        您的团队暂无成员
      </div>

    </div>
  </div>
</template>

<script>
  import mixin from '@/mixin'
  export default {
    name: "index",
    mixins:[mixin],
    components:{},

    watch:{
      search:function (n,o) {
        if(n==''){
          this.getTDlist('','','','')
        }
      }
    },
    data(){
      return{
        search:'',
        selectShow:false,
        selectList:[
          {
            text:'全部'
          },{
            text:'一级',
            type:'grade',
            val:1
          },{
            text:'二级',
            type:'grade',
            val:2
          },{
            text:'会员',
            type:'yhlx',
            val:3
          },{
            text:'学员',
            type:'yhlx',
            val:1
          },{
            text:'已交费',
            type:'sfjf',
            val:1
          },{
            text:'未交费',
            type:'sfjf',
            val:0
          }
        ],
        defaultVal:{},
        teamList:[]
      }
    },
    created(){
      this.defaultVal = this.selectList[0]
      this.getTDlist('','','','')
    },
    methods:{
      goMess(item){
        if(item.userDetail.yhLx!="1"){
          return
        }
        this.$router.push({name:'process',
          params:{
          id:item.userDetail.id,
          number:parseInt(item.yhDqzt)
          }
        })
      },
      searchClick(val){
        // alert(val)
        this.getTDlist(val,'','','')
      },
      selectChoose(item,index){
        this.selectShow = !this.selectShow
        this.defaultVal = this.selectList[index]
        if(item.type=='grade'){
          this.getTDlist('',item.val,'','')
        }else if(item.type=='yhlx'){
          this.getTDlist('','',item.val,'')
        }else if(item.type=='sfjf'){
          this.getTDlist('','','',item.val)
        }else {
          this.getTDlist()
        }
      },
      getTDlist(yhxm,grade,yhlx,sfjf){
        var v = this
        this.$http.post(this.apis.TEAMMESS,{yhxm:yhxm,grade:grade,yhlx:yhlx,sfjf:sfjf,pageNum:1,pageSize:999999}).then((res)=>{
            if(res.code==200 && res.page){
              v.teamList = res.page.list
            }else {
              v.teamList = []
            }
        }).catch((err)=>{

        })
      }
    }
  }
</script>

<style scoped>

</style>
