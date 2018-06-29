<style lang="less">
@import "./infor";
#infoSTY {
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
      <!--<div style="width: 0.5rem;"></div>-->
      <div class="box_row_100" style="position: relative;margin: 0 0.2rem 0 0.1rem">
        <input class="el-input__inner" type="text"
               placeholder="姓名查找"
               v-model="search " @keyup.enter="searchClick(search)">
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
    <div class="box_col_auto">
      <div class="listitem" v-for="(item,index) in teamList" @click="goMess(item)">
        <div class="box-row" style="border-bottom: slategray 0.02rem solid">
          <div style="width: 0.6rem">
            <img :src="item.userDetail.yhTx | yhTx"
                 style="width: 100%;border-radius: 0.5rem">
          </div>
          <div class="box_row_100" style="text-align: right;margin-left: 0.15rem">
            <div class="box" style="line-height: 0.35rem">
              <div class="box-row">
                  <div class="box_row_100" style="text-align: left">
                    {{item.yhXm | yhXm}}
                  </div>
                  <div class="phone">
                    <a :href="'tel:'+item.yhSjhm">{{item.yhSjhm}}</a>
                  </div>
                </div>
              <div class="box-row">
                <div class=""
                     :style="{color:item.userDetail.ddSfjx=='1'?'#00b65f':'#ff8800'}">
                  {{item.userDetail.ddSfjx | ddSfjx}}
                </div>
                <div class="box_row_100" style="text-align: center">
                  {{item.userDetail.yhLx | yhLx}}
                </div>
                <div class="typ">
                  {{item.userGrade | userGrade}}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="item.userDetail.yhLx==1" class="yhDqzt">
          学员当前进度 : {{item.yhDqzt | yhDqzt}}
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
  export default {
    name: "index",
    components:{

    },
    filters:{
      yhDqzt:(val)=>{
        switch (val){
          case "0":
            return '档案信息受理中'
          break;
          case "1":
            return '科目一进行中'
          break;
          case "2":
            return '科目二进行中'
          break;
          case "3":
            return '科目三进行中'
          break;
          case "4":
            return '科目四进行中'
          break
          default:
            return '完结'
        }
      },
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
      this.getTDlist()
    },
    methods:{
      goMess(item){
        this.$router.push({name:'process',params:{id:item.userDetail.id}})
      },
      searchClick(val){
        // alert(val)
      },
      selectChoose(item,index){
        this.selectShow = !this.selectShow
        this.defaultVal = this.selectList[index]
        if(item.type=='grade'){
          this.getTDlist(item.val,'','')
        }else if(item.type=='yhlx'){
          this.getTDlist('',item.val,'')
        }else if(item.type=='sfjf'){
          this.getTDlist('','',item.val)
        }else {
          this.getTDlist()
        }
      },
      getTDlist(grade,yhlx,sfjf){
        var v = this
        this.$http.post(this.apis.TEAMMESS,{grade:grade,yhlx:yhlx,sfjf:sfjf,pageNum:1,pageSize:999999}).then((res)=>{
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
