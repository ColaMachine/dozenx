<template>
  <div>
    <PageHeader/>
    <SearchBar v-model="searchParams" @on-search="search(1)"/>
    <div style="padding:20px 30px;">
      <Table :columns="tableCols" :data="tableData"></Table>
      <div style="float:right;margin-top:60px;" v-show="totalRecords">
        <Page :current.sync="searchParams.curPage"
              :total="totalRecords"
              :page-size="searchParams.pageSize"
              @on-change="search"
              simple></Page>
      </div>
    </div>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import DateFormat from '@/js/dateFormat';

  export default {
    components: {
      PageHeader,
      SearchBar
    },
    data() {
      return {
        currentRecord: {},
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          keywords: '',
          date: '',   //日期
          userName: '' //用户名
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '用户账号',
            key: 'userName',
            align: 'center',
            width: 100
          },{
            title: '操作模块',
            key: 'moduleName',
            align: 'center',

          }, {
            title: '操作对象',
            key: 'compName',
            align: 'center',

          }, {
            title: '操作详情',
            key: 'detail',
            align: 'center',
          }, {
            title: '操作时间',
            key: 'createTime',
            align: 'center',
          }
        ],
        tableData: []
      }
    },
    methods: {
      search(curPage) {
        if (curPage) {
          this.searchParams.curPage = curPage;
        }
        let params = Object.assign({}, this.searchParams);
        if (!params.date) {
          delete params.date;
        } else {
          params.date = DateFormat.format.date(params.date, 'yyyy-MM-dd');
        }
        $http.get('/home/log/oper/list', {
          params: {
            params: params
          }
        }).then((data) => {
          this.tableData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      }
    },
    mounted() {
      this.search(1);
    },
  };
</script>

