<template>
  <div class="video-stream">
    <div class="face-list">
      <header>
        <span class="title">今日抓拍</span>
        <router-link to="/face" class="more">返回&nbsp;</router-link>
      </header>
      <main>
        <ul class="avatar">
          <li v-for='item in captureData'>
            <img :src="item.url"/>
            <p>{{getDate(item.createtime)}}</p>
            <p>{{getTime(item.createtime)}}</p>
          </li>
        </ul>
      </main>
      <div class='page' v-show="totalRecords">
        <Page :current.sync="searchData.curPage"
              :total="totalRecords"
              :page-size="searchData.pageSize"
              @on-change="search"
              simple></Page>
      </div>
      <!-- <Page :current="curPage" :total="totalPage" simple class="page" @on-change="changePage"/> -->
    </div>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  export default {
    components: {
    },
    mounted() {
      this.search(1);
    },
    data() {
      return {
        totalPage:1,
        curPage:1,
        searchData: {
          curPage: 1,
          pageSize: 30
        },
        totalRecords: 0,
        captureData:[]
      }
    },
    methods:{
      search(){
        $http.get('/api/mssrv/checkin/face/capture/list', {
          params: {
            params:  this.searchData
          }
        }).then((resp) => {
          this.totalRecords = resp.page.totalCount
          this.captureData = resp.data
        });
      },
      getDate(date){
        return date?new Date(date).toLocaleDateString():''
      },
      getTime(date){
        return date?new Date(date).toLocaleTimeString():''
      }
    }
  };
</script>
<style lang="less">
  @import '../../css/face.less';
</style>
