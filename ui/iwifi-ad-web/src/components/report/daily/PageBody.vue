<template>
  <div>
    <PageHeader @on-add="onAdd"/>
    <SearchBar v-model="searchParams" @on-search="search(1)"/>
    <div style="padding:20px 30px;">
      <Table :columns="tableCols" :data="tableData" class="report-daily-table"></Table>
      <div style="margin-top:60px;" v-show="totalRecords">
        <div style="float:left">展现次数合计：{{allTotalCount}}</div>
        <Page :current.sync="searchParams.curPage" style="float:right"
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
      SearchBar,
    },
    data() {
      return {
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          advertPartnerName: '',
          startTime: '',
          endTime: ''
        },
        totalRecords: 0,
        allTotalCount: 0,
        tableCols: [
          {
            title: '序号',
            type: 'index',
            width: 80
          }, {
            title: '日期',
            key: 'date',
            align: 'center',
            width: 200
          }, {
            title: '展现次数合计',
            key: 'totalCount',
            align: 'center',
            // width: 200
          }, {
            title: '合作平台',
            key: 'platformList',
            type: 'html',
            align: 'center',
            // width: 200
          }, {
            title: '展现次数',
            key: 'countList',
            type: 'html',
            align: 'center',
            width: 200
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
        $http.get('/advertsrv/lunch/log/', {
          params: {
            params
          }
        }).then(({data = {}, page}) => {
          let list = [];
          let obj = null;
          if (data && data.list) {
            data.list.forEach((dayList) => {
              obj = {
                totalCount: 0,
                date: dayList[0].groupTime,
                platformList: '',
                countList: ''
              };
              dayList.forEach((day, index) => {
                obj.platformList += `<div class="single-count-record">${day.advertPartnerName}</div>`;
                obj.countList += `<div class="single-count-record">${day.viewTimes}</div>`;
                obj.totalCount += day.viewTimes;
              });
              list.push(obj);
            });
          }
          console.log(list);
          this.tableData = list;
          this.totalRecords = page.totalCount;
          this.allTotalCount = data.allViewTime || 0;
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
      onAdd() {
        this.currentRecord = {};
        this.formReadonly = false;
        this.showFormModal();
      },
      onEdit(row) {
        this.formReadonly = false;
        this.getPlatform(row.id);
      },
      onView(row) {
        this.formReadonly = true;
        this.getPlatform(row.id);
      },
      onDelete(row) {
        this.confirmDelete(row.id, row.platformName);
      },
      onFormSubmit() {
        let isEdit = this.currentRecord.id ? true : false;
        let url = '/advertsrv/partner';
        let method = isEdit ? 'put' : 'post';
        let title = `${isEdit ? '编辑' : '新增'}合作平台成功`;
        $http({
          method: method,
          url: url,
          data: this.currentRecord
        }).then((data) => {
          this.$Notice.success({
            title: title
          });
          this.hideFormModal();
          this.search();
        });
      },
      confirmDelete(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
          title: '删除合作平台',
          content: `确认删除合作平台<span style="color:red;">${name}</span>？`,
          okText: '删除',
          onOk: () => {
            this.del(id);
          },
        });
      },
      del(id) {
        $http.delete('/advertsrv/partner', {
          params: {
            params: {
              id
            }
          }
        }).then(() => {
          this.$Notice.success({
            title: '删除合作平台成功',
          });
          this.search(1);
        });
      },
      getPlatform(id) {
        $http.get(`/advertsrv/partner`, {
          params: {
            params: {
              id
            }
          }
        }).then(({data}) => {
          this.currentRecord = data;
          this.showFormModal();
        });
      }
    },
    mounted() {
      this.search(1);
    },
  };
</script>
