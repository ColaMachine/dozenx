<template>
  <div>
    <PageHeader/>
    <SearchBar v-model="searchParams" @on-search="search(1)"/>
    <div style="padding:20px 30px;">
      <Table :columns="tableCols" :data="tableData"></Table>
      <div style="margin-top:60px;" v-show="totalRecords">
        <!--<div style="float:left">展现次数合计：{{allTotalCount}}</div>-->
        <Page :current.sync="searchParams.curPage" style="float:right"
              :total="totalRecords"
              :page-size="searchParams.pageSize"
              @on-change="search"
              simple></Page>
      </div>
    </div>
    <Modal
      :mask-closable="false"
      v-model="formModalVisible"
      :title="formModalTitle" :width="800">
      <!--<div slot="header">-->
      <!--<div>DSP ID：{{}}</div>-->
      <!--</div>-->
      <!--<div slot="footer">-->
      <!--</div>-->
      <div style="padding:20px;">
        <DailyDetail :currentRecord="currentRecord"
                     :reset-flag="formModalVisible"/>
      </div>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import DailyDetail from './DailyDetail.vue';
  import DateFormat from '@/js/dateFormat';

  export default {
    components: {
      PageHeader,
      SearchBar,
      DailyDetail
    },
    computed: {
      formModalTitle() {
        return this.currentRecord ? this.currentRecord.date : '';
      }
    },
    data() {
      return {
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          strategy: '',
          strategyCode: '',
          startTime: new Date(),
          endTime: new Date()
        },
        formModalVisible: false,
        currentRecord: {},
        totalRecords: 0,
        allTotalCount: 0,
        tableCols: [
          {
            title: '序号',
            type: 'index',
            width: 80,
            align: 'center'
          }, {
            title: '日期',
            key: 'date',
            align: 'center',
            width: 200,
          }, {
            title: '投放策略',
            key: 'strategyName',
            align: 'center',
            width: 200
          }, {
            title: '展现量',
            key: 'reqPv',
            align: 'center',
            // width: 200
          }, {
            title: '点击量',
            key: 'clickPv',
            align: 'center',
            // width: 200
          }, {
            title: 'CPM(元)',
            key: 'cPM',
            align: 'center',
            // width: 200
          }, {
            title: '总价格(元)',
            key: 'totalPrice',
            align: 'center',
            // width: 200
          },
          // {
          //   title: '出价数',
          //   key: 'bidNumber',
          //   align: 'center',
          //   // width: 200
          // },
          {
            title: '竞得数',
            key: 'competeNumber',
            align: 'center',
            render: (h, record) => {
              let _this = this;
              return h('div', [
                h('a', {
                  'class': ['tbl-link', 'action-link'],
                  on: {
                    click() {
                      _this.onView(record.row);
                    }
                  }
                }, record.row.competeNumber),
              ]);
            }
            // width: 200
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
        if (!params.startTime) {
          delete params.startTime;
        } else {
          params.startTime = DateFormat.format.date(params.startTime, 'yyyy-MM-dd');
        }
        if (!params.endTime) {
          delete params.endTime;
        } else {
          params.endTime = DateFormat.format.date(params.endTime, 'yyyy-MM-dd');
        }
        console.log('params', params);
        $http.get('/home/report/lunch/log/list', {
          params: params
        }).then((data) => {
          data.data.forEach((item) => {
            item.date = DateFormat.format.date(item.dateStamp, 'yyyy-MM-dd');
          });
          this.tableData = data.data;
          this.totalRecords = data.page.totalCount;
          // let list = [];
          // let obj = null;
          // if (data && data.list) {
          //   data.list.forEach((dayList) => {
          //     obj = {
          //       totalCount: 0,
          //       date: dayList[0].groupTime,
          //       platformList: '',
          //       countList: ''
          //     };
          //     dayList.forEach((day, index) => {
          //       obj.platformList += `<div class="single-count-record">${day.advertPartnerName}</div>`;
          //       obj.countList += `<div class="single-count-record">${day.viewTimes}</div>`;
          //       obj.totalCount += day.viewTimes;
          //     });
          //     list.push(obj);
          //   });
          // }
          // console.log(list);
          // this.tableData = list;
          // this.totalRecords = page.totalCount;
          // this.allTotalCount = data.allViewTime || 0;
        });
      },
      showFormModal() {
        this.setFormModalVisible(true);
      },
      hideFormModal() {
        this.setFormModalVisible(false);
      },
      setFormModalVisible(isVisible) {
        this.formModalVisible = isVisible;
      },
      onView(row) {
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
      },
      getCurrentRecordCopy() {
        return Object.assign({}, ...arguments);
      }
    },
    mounted() {
      // this.search(1);
    },
  };
</script>
