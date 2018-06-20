<style lang="less">
  #stumess{
    font-size: 0.2rem;
  }
</style>
<template>
      <div id="stumess" class="box">
        <mt-header title="学员信息">
          <router-link to="/myStudent" slot="left">
            <mt-button icon="back"></mt-button>
          </router-link>
        </mt-header>
        <div style="padding:0 0.2rem">
          <div class="box-row" style="">
            <div>
              <i class="iconfont icon-wo"
                 style="color: #00cc66;font-size:0.8rem"></i>
            </div>
            <div class="box_row_100" style="padding: 0.12rem 0 0 0.2rem;font-size: 0.16rem">
              <div class="box-row">
                <div class="box_row_100">
                    姓名：{{xyMwss.yhxm}}
                </div>
                <div class="box_row_100">
                  电话： <a :href="'tel:'+ xyMwss.yhzh">
                          <i class="iconfont icon-dianhua"
                             style="font-size: 0.2rem;color: #2b85e4"
                          ></i>
                        </a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="body" style="padding: 0 0.2rem">

          <div class="box-row-list">
            <div class="kmcj" style="background-color: #2b85e4">

            </div>
          </div>
          <!--<Row :gutter="10">-->
            <!--<Col span="12" style="margin-bottom: 5px" v-for="(item,index) in xyMwss.markList">-->
              <!--<Card dis-hover>-->
                <!--<p slot="title">{{item.kmBm | km}}成绩单</p>-->
                <!--<div slot="extra">-->
                    <!--<Input v-model="item.xyCj"-->
                           <!--size="large" placeholder="成绩"></Input>-->
                <!--</div>-->
                <!--<div style="height: 2.8rem">-->
                    <!--<imgup :demoImg="xyMwss.markList[index].imgUrl | upImg"-->
                           <!--@handleSuccess="(res)=>handleSuccess(res,index)">-->
                    <!--</imgup>-->
                <!--</div>-->
                <!--<div style="padding-top: 0.2rem">-->
                  <!--<Button type="info" long-->
                          <!--@click=saveMes(index,item.kmBm,item.xyCj)>{{item.kmBm | km}}成绩单上传</Button>-->
                <!--</div>-->
              <!--</Card>-->
            <!--</Col>-->
            <!--<Col span="12">-->
              <!--<Card dis-hover>-->
                <!--<p slot="title">科目二成绩单</p>-->
                <!--&lt;!&ndash;<imgup demoImg="static/djcl.png">&ndash;&gt;-->
                <!--&lt;!&ndash;</imgup>&ndash;&gt;-->
              <!--</Card>-->
            <!--</Col>-->
          <!--</Row>-->
          <!--<Row>-->
            <!--<Col span="11" style="margin-left: 5px;margin-right: 5px;margin-bottom: 5px">-->
              <!--<Card dis-hover>-->
                <!--<p slot="title">科目三成绩单</p>-->
                <!--&lt;!&ndash;<imgup demoImg="static/djcl.png">&ndash;&gt;-->
                <!--&lt;!&ndash;</imgup>&ndash;&gt;-->
              <!--</Card>-->
            <!--</Col>-->
            <!--<Col span="12">-->
              <!--<Card dis-hover>-->
                <!--<p slot="title">科目四成绩单</p>-->
                <!--&lt;!&ndash;<imgup demoImg="static/djcl.png">&ndash;&gt;-->
                <!--&lt;!&ndash;</imgup>&ndash;&gt;-->
              <!--</Card>-->
            <!--</Col>-->
          <!--</Row>-->
        </div>
      </div>
</template>

<script>
  import imgup from '@/views/components/upLoad/imgUpload'
  import { Toast } from 'mint-ui';
    export default {
        name: "stuMess",
        components:{
        },
        data(){
          return{
            tell:12345678911,
            xyMwss:{
              markList:[]
            },
            imglist:['0','0','0','0']
          }
        },
        filters:{
          upImg:(val)=>{
            if(val){
              return val
            }
            return 'static/images/djcl.png'
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
        created(){
          console.log(this.$route)
          this.getXYmess()
        },
        methods:{
          getXYmess(){
            var v = this
            this.$http.post(this.apis.XYMESS,{'xyid':this.$route.params.id}).then((res)=>{
                this.xyMwss = res.result
            }).catch((err)=>{

            })
          },
          saveMes(index,km,cj){
            var v = this
            if(cj==''){
              Toast('请填写科目成绩');
              return
            }else if(v.xyMwss.markList[index].imgUrl==''){
              Toast('请上传科目成绩单');
            }else {
              this.$http.post(this.apis.XYCJD,{
                'xyId':v.$route.params.id,
                'imgUrl':v.imglist[index],
                'kmBm':km,
                'xyCj':cj
              }).then((res)=>{
                if (res.code==200){
                  Toast(res.message)
                  v.getXYmess()
                }
              }).catch((err)=>{

              })
            }
          },
          handleSuccess(res,val){
            console.log('上传成功',res)
            var v= this
            this.imglist.splice(val,1,'/'+res.message)
            this.xyMwss.markList[val].imgUrl=v.apis.getImgUrl+res.message
          }
        }
    }
</script>

<style scoped>

</style>
