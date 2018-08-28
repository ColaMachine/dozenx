<template>
  <div>
    <Table :columns="tableCols" :data="tableData"></Table>
    <div style="padding:20px 0;" v-show="totalRecords">
      <!--<div style="float:left">展现次数合计：{{allTotalCount}}</div>-->
      <Page :current.sync="searchParams.curPage" style="float:right"
            :total="totalRecords"
            :page-size="searchParams.pageSize"
            @on-change="search"
            simple></Page>
    </div>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import DateFormat from '@/js/dateFormat';

  export default {
    props: ['currentRecord'],
    components: {},
    data() {
      return {
        searchParams: {
          curPage: 1,
          pageSize: 10,
        },
        pageSize: getPageSize(),
        tableCols: [
          {
            title: '时间点',
            key: 'hourText',
            width: 100,
            align: 'center'
          }, {
            title: '投放策略',
            key: 'strategyName',
            align: 'center',
            width: 200
          },
          {
            title: '出价(元)',
            key: 'price',
            align: 'center',
            width: 100
          },
          {
            title: '广告位',
            key: 'spaceName',
            align: 'center',
            // width: 200
          }, {
            title: 'PV',
            key: 'pv',
            align: 'center',
            width: 100
          },
          {
            title: 'UV',
            key: 'uv',
            align: 'center',
            width: 100
          },
        ],
        tableData: [],
        totalRecords: 0
      }
    },
    methods: {
      search(curPage) {
        if (curPage) {
          this.searchParams.curPage = curPage;
        } else {
          this.searchParams.curPage = 1;
        }
        $http.get(`/advertsrv/report/lunch/log/detail`, {
          params: {
            lunchTime: this.currentRecord.date,
            strategy: this.currentRecord.strategy,
            strategyCode: this.currentRecord.strategyCode,
            curPage: this.searchParams.curPage,
            pageSize: this.searchParams.pageSize
          }
        }).then((data) => {
          data.data.forEach((item) => {
            item.hourText = item.hour + '点';
          });
          this.tableData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      },
    },
    mounted() {
    },
    watch: {
      currentRecord: {
        deep: true,
        handler(val) {
          this.search();
        }
      }
    }
  };
</script>
