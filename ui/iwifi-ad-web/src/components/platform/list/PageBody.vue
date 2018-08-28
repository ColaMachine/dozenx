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
        <PlatformForm :formData="currentRecord" :readonly="formReadonly" @on-submit="onFormSubmit"
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
  import PlatformForm from './PlatformForm.vue';
  import DateFormat from '@/js/dateFormat';

  export default {
    components: {
      PageHeader,
      SearchBar,
      PlatformForm
    },
    computed: {
      formModalTitle() {
        return this.formReadonly ? '查看合作平台（只读）' :
          (this.currentRecord.id ? `编辑合作平台 - DSP ID：${this.currentRecord.id}` : '新增合作平台');
      }
    },
    data() {
      return {
        formModalVisible: false,
        formReadonly: false,
        currentRecord: {},
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          platformName: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '平台名称',
            key: 'platformName',
            align: 'center',
            width: 200
          }, {
            title: 'DSP ID',
            key: 'id',
            align: 'center',
            width: 200
          }, {
            title: '联系人',
            key: 'contacts',
            align: 'center',
            width: 200
          }, {
            title: '联系电话',
            key: 'telephone',
            align: 'center',
            width: 200
          }, {
            title: '创建时间',
            key: 'createTime',
            align: 'center',
            width: 200
          }, {
            title: '操作',
            // key: 'createDate',
            width: 200,
            align: 'center',
            render: (h, record) => {
              let _this = this;
              return h('div', [
                h('a', {
                  'class': ['tbl-link', 'info-link'],
                  on: {
                    click() {
                      _this.onView(record.row);
                    }
                  }
                }, '详情'), h('a', {
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
        $http.get('/advertsrv/partner/list', {
          params: {
            params: this.searchParams
          }
        }).then(({data = [], page}) => {
          data.forEach((item) => {
            item.createTime = DateFormat.format.date(item.createTime, 'yyyy-MM-dd HH:mm:ss');
          });
          this.tableData = data;
          this.totalRecords = page.totalCount;
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
