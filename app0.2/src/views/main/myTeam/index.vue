<style lang="less">
  .mint-navbar {
    .mint-tab-item {
      .mint-tab-item-label {
        font-size: 0.2rem;
      }
    }
  }

  #myTeam {
    background-color: #f2f2f2;
    .teamList {
      padding: 0.05rem 0;
      font-size: 0.2rem;
      .teamItem {
        background-color: #fff;
        margin-bottom: 0.05rem;
        height: 0.8rem;
        .iconImg {
          width: 0.8rem;
          position: relative;
          img {
            width: 0.5rem;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
          }
        }
        .username {
          font-size: 0.18rem;
          font-weight: 700;
          color: #484646;
        }
        .phone {
          padding: 0 0.15rem;
          font-size: 0.16rem;
          font-weight: 700;
          a {
            color: #2d8cf0;
          }
        }
        .userMoney {
          font-size: 0.16rem;
          font-weight: 700;
        }
        .userType {
          font-size: 0.16rem;
          font-weight: 600;
          color: #2d8cf0;
        }
        .userGrade {
          font-size: 0.16rem;
          font-weight: 600;
          color: #808080;
          text-align: right;
          padding: 0 0.15rem;
        }
      }
    }
  }
</style>
<template>
  <div id="myTeam" class="box_col">
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
    <div class="box_row_1auto teamList">
      <div class="box-row teamItem" v-for="(item,index) in list">
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
            <div class="box_col_100" style="padding-top: 0.12rem">
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
  import {Navbar, TabItem} from 'mint-ui';
  import mixin from '@/mixin'
  export default {
    name: "index",
    mixins:[mixin],
    components: {
      // Icon,
      [Navbar.name]: Navbar,
      [TabItem.name]: TabItem,
    },
    data() {
      return {
        selected: '0',
        list: [],
        pageList: {
          userGrade: 0,
          pageSize: 999999999,
          pageNum: 1
        },
      }
    },
    watch: {
      selected: function (n, o) {
        this.pageList.userGrade = n
        this.teamList()
      }
    },
    created() {
      // this.list= this.ALL[0]
      this.teamList()
    },
    methods: {
      tabbarClick(n, o) {
        var v = this
        v.list = v.ALL[n]
        this.teamList()
      },
      teamList() {
        var v = this
        this.$http.post(this.apis.TEAM, this.pageList).then((res) => {
          console.log(res)
          v.list = res.page.list
        }).catch((err) => {

        })
      }
    }

  }
</script>

<style scoped>

</style>
