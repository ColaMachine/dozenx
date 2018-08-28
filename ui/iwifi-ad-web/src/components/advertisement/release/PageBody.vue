<template>
  <div>
    <PageHeader @on-add="onAdd"/>
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
    <Modal
      :mask-closable="false"
      v-model="formModalVisible"
      :title="formModalTitle" :width="600">
      <!--<div slot="header">-->
      <!--<div>DSP ID：{{}}</div>-->
      <!--</div>-->
      <!--<div slot="footer">-->
      <!--</div>-->
      <div style="padding:20px;">
        <ReleaseForm :value="currentRecord" @on-submit="onFormSubmit" :reset-flag="formModalVisible"
                     :readonly="formReadonly"/>
      </div>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import ReleaseForm from './ReleaseForm.vue';
  import DateFormat from '@/js/dateFormat';
  // import Mock from 'mockjs';
  //
  // Mock.mock(/list/, {
  //     "r": 0,
  //     "data|15": [
  //       {
  //         "id|+1": 1000,
  //         "name": "策略@CNAME",
  //         "price|100-10000.0-2": 500.05,
  //         "state|0-1": 1,
  //         "materialId|1-10": 7,
  //         "advertSpaceList|0-8": [
  //           {
  //             "advertSpaceId|1-10": 7,
  //             "advertSpaceName": "广告位@CNAME"
  //           }
  //         ],
  //
  //         "effectStartTime": "@DATE(T)",
  //         "effectEndTime": "@DATE(T)",
  //         "remark": '评价@CNAME',
  //       }
  //     ],
  //     "page": {
  //       "curPage": 1,
  //       "totalPage": 5,
  //       "pageSize": 15,
  //       "totalCount": 70,
  //       "beginIndex": 0,
  //       "hasPrePage": false,
  //       "hasNextPage": false
  //     },
  //     "right": true
  //   }
  // );
  // Mock.mock(/enable|disable/, {
  //   r: 0
  // });
  export default {
    components: {
      PageHeader,
      SearchBar,
      ReleaseForm,
    },
    computed: {
      formModalTitle() {
        return this.formReadonly ? '查看投放策略（只读）' :
          (this.currentRecord.id ? '修改投放策略' : '添加投放策略');
      },
    },
    data() {
      return {
        formModalVisible: false,
        formReadonly: false,
        currentRecord: {},
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          // advertPartnerId: '',
          createUser: '',
          name: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '策略编码',
            key: 'id',
            align: 'center',
            width: 300
          }, {
            title: '策略名称',
            key: 'name',
            align: 'center',
            width: 300
          }, {
            title: '状态',
            key: 'stateText',
            align: 'center',
            // width: 200
          }, {
            title: '操作',
            // key: 'createDate',
            width: 250,
            align: 'center',
            render: (h, record) => {
              let _this = this;
              return h('div', [
                h('a', {
                  'class': ['tbl-link', record.row.state == 0 ? 'action-link' : 'error-link'],
                  on: {
                    click() {
                      _this.onSetState(record.row);
                    }
                  }
                }, record.row.state == 0 ? '启用' : '暂停'),
                h('a', {
                  'class': ['tbl-link', 'info-link'],
                  on: {
                    click() {
                      _this.onView(record.row);
                    }
                  }
                }, '详情'),
                h('a', {
                  'class': ['tbl-link', 'info-link'],
                  on: {
                    click() {
                      _this.onEdit(record.row);
                    }
                  }
                }, '修改'), h('a', {
                  'class': ['tbl-link', 'error-link'],
                  on: {
                    click() {
                      _this.onDelete(record.row);
                    }
                  }
                }, '删除'),
              ]);
            }
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
        $http.get('/advertsrv/advertstrategy/list', {
          params: {
            params: this.searchParams
          }
        }).then((data) => {
          if (data.data) {
            data.data.forEach((ele) => {
              ele.stateText = ele.state == 1 ? '启用' : '停用';
            });
          }
          this.tableData = data.data;
          this.totalRecords = data.page.totalCount;
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
        this.currentRecord = this.getCurrentRecordCopy();
        this.formReadonly = false;
        this.showFormModal();
      },
      onEdit(row) {
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.formReadonly = false;
        this.showFormModal();
      },
      onView(row) {
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.formReadonly = true;
        this.showFormModal();
      },
      onSetState(row) {
        this.confirmSetState(row);
      },
      onDelete(row) {
        this.confirmDelete(row.id, row.name);
      },
      onFormSubmit(formData) {
        let isEdit = formData.id ? true : false;
        let url = '/advertsrv/advertstrategy';
        let method = isEdit ? 'put' : 'post';
        let title = `${isEdit ? '修改' : '新增'}投放策略成功`;
        $http({
          method: method,
          url: url,
          data: Object.assign(formData, {
            effectStartTime: DateFormat.format.date(formData.effectStartTime, 'yyyy-MM-dd HH') + ':00:00',
            effectEndTime: DateFormat.format.date(formData.effectEndTime, 'yyyy-MM-dd HH') + ':00:00',
          })
        }).then((data) => {
          this.$Notice.success({
            title: title
          });
          this.hideFormModal();
          this.search();


          // _this.$refs["formIndustry"].clearIndustry();
          //_this.$refs["formArea"].clearArea();
        }).catch(function () {
         // _this.$emit("on-cancel");
          //_this.$refs["formIndustry"].clearIndustry();
         // _this.$refs["formArea"].clearArea();
        });
      },
      confirmSetState(row) {
        if (!row) {
          return;
        }
        let actionText = row.state ? '暂停' : '启用';
        this.$Modal.confirm({
          title: `${actionText}投放策略`,
          content: `确认${actionText}投放策略<span style="color:red;">${row.name}</span>？`,
          okText: `${actionText}`,
          onOk: () => {
            this.setState(row);
          },
        });
      },
      setState(row) {
        let url = `/advertsrv/advertstrategy/${row.state ? 'disable' : 'enable'}/${row.id}`;
        let actionText = row.state ? '暂停' : '启用';
        $http.get(url).then(() => {
          this.$Notice.success({
            title: `${actionText}投放策略成功`,
          });
          this.search();
        });
      },
      confirmDelete(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
          title: '删除投放策略',
          content: `确认删除投放策略<span style="color:red;">${name}</span>？`,
          okText: '删除',
          onOk: () => {
            this.deleteRelease(id);
          },
        });
      },
      deleteRelease(id) {
        $http.delete(`/advertsrv/advertstrategy/${id}`).then(() => {
          this.$Notice.success({
            title: '删除投放策略成功',
          });
          this.search(1);
        });
      },
      getCurrentRecordCopy(record) {
        return Object.assign({
          price: 0,
          advertSpaceIds: [],
          effectStartTime: '',
          effectEndTime: ''
        }, record, record ? {
          effectStartTime: new Date(parseInt(record.iEffectStartTime)),
          effectEndTime: new Date(parseInt(record.iEffectEndTime)),
          advertSpaceIds: record.spaceList ? record.spaceList.map((item) => {
            return item.id;
          }) : []
        } : {});

      }
    },
    mounted() {
      this.search(1);
    },
    watch: {
      // currentRecord(newVal, oldVal) {
      //   console.log('currentRecord', newVal, oldVal);
      // }
    }
  };
</script>

