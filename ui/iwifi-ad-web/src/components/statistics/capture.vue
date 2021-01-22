<template>
  <div>
    <ul class="search-bar" style="padding-bottom:0">
      <li class="title">抓拍统计</li>
      <li class="bar">
        <DatePicker v-model='searchData.checkTimeBegin' placeholder="开始时间" style="width: 130px" size='large' :editable='false'></DatePicker>
        -
        <DatePicker v-model='searchData.checkTimeEnd' placeholder="结束时间" style="width: 130px" size='large' :editable='false'></DatePicker>
        <Select v-model="searchData.orgId" style="width:180px" placeholder="请选择机构" size='large' clearable>
            <Option v-for="item in orgList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Select v-model="searchData.camera" style="width:180px" size='large' placeholder="请选择视频源" clearable>
            <Option v-for="item in videoList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Button type="primary" @click="search(1)">查询</Button>
      </li>
    </ul>
    <div class="content-wrap">
      <section class="captureWrapper">
        <div class="item" v-for='(item,index) in captureData'>
          <div class="captureImg">
            <h4>抓拍人脸</h4>
            <img :src="item.regUrl" alt="">
            <p>{{item.userName}}</p>
          </div>
          <i-circle :percent="item.score" :size="55" stroke-color="#F5A623" :stroke-width='8'>
            <span class="demo-Circle-inner" style="font-size:10px">{{item.score}}%</span>
          </i-circle>
          <div class="libraryImg">
            <h4>底库人脸</h4>
            <img :src="item.oriUrl" alt="">
            <p>{{item.checkTime?new Date(item.checkTime).toLocaleString():''}}</p>
          </div>
        </div>
      </section>
      <div class='page' v-show="totalRecords">
        <Page :current.sync="searchData.curPage"
              :total="totalRecords"
              :page-size="searchData.pageSize"
              @on-change="search"
              simple></Page>
      </div>
    </div>
  </div>
</template>
<style lang='less' scoped>
  .captureWrapper{
    display: flex;
    flex-wrap: wrap;
    .item{
      display: flex;
      align-items: center;
      margin:20px 15px;;
      font-size: 12px;
      h4{
        color: #0062AD;
      }
      .captureImg,.libraryImg{
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 110px;
        img{
          margin:5px 0;
          width: 66px;
          height: 66px;
        }
        p{
          white-space: nowrap;
        }
      }
    }
  }
</style>
<script>
  import $http from '@/js/http2';
  export default {
    components: {

    },
    created() {
      this.getOrg();
      this.getVideo();
      this.search(1);
      // this.captureData = [
      //   {aface:require('../../assets/avatar.png'),name:'海王星辰健康药房',bface:require('../../assets/avatar.png'),time:new Date().toLocaleString(),percent:80},
      //   {aface:require('../../assets/avatar.png'),name:'华方健康大药房',bface:require('../../assets/avatar.png'),time:new Date().toLocaleString(),percent:50},
      //   {aface:require('../../assets/avatar.png'),name:'海王星辰健康药房',bface:require('../../assets/avatar.png'),time:new Date().toLocaleString(),percent:40},
      //   {aface:require('../../assets/avatar.png'),name:'华方健康大药房',bface:require('../../assets/avatar.png'),time:new Date().toLocaleString(),percent:79},
      //   {aface:require('../../assets/avatar.png'),name:'华方健康大药房',bface:require('../../assets/avatar.png'),time:new Date().toLocaleString(),percent:66}
      // ]
    },
    data() {
      return {
        searchData: {
          orgId:'',
          camera:'',
          checkTimeBegin:'',
          checkTimeEnd:'',
          curPage: 1,
          pageSize:10
        },
        orgList:[],
        videoList:[],
        captureData:[],
        totalRecords: 0,
        columns: [
          {
            title: '序号',
            key: 'createTime',
            align: 'center',
          },
          {
            title: '机构名称',
            key: 'zzDate',
            align: 'center',
          },
          {
            title: '报警时间',
            key: 'zzDate',
            align: 'center',
          },
          {
            title: '结束时间',
            key: 'tax',
            align: 'center',
          },
          {
            title: '持续时间',
            key: 'tax',
            align: 'center',
          }
        ],
        tblData: []
      }
    },
    methods: {
      getOrg(){
        $http.get('/api/mssrv/sys/auth/org/list', {
          params: {
            params:  {curPage: 1,pageSize:99}
          }
        }).then((res) => {
          this.orgList = res.data.map((item)=>{
            return {
              value:item.id,
              label:item.name
            }
          })
        });
      },
      getVideo(){
        $http.get('/api/mssrv/videosource/getListByParams', {
          params: {
            params:  {curPage: 1,pageSize:99}
          }
        }).then((res) => {
          this.videoList = res.data.map((item)=>{
            return {
              value:item.id,
              label:item.name
            }
          })
        });
      },
      search(curPage) {
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get('/api/mssrv/checkin/faceCheckinOut/list', {
          params: {
            params:  this.searchData
          }
        }).then((data) => {
          this.captureData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      }
    },
    watch:{

    }
  };
</script>
