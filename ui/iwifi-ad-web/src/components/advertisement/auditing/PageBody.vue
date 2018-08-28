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
    <Modal
      :mask-closable="false"
      v-model="formModalVisible"
      :title="formModalTitle" :width="600">
      <MaterialForm :value="currentRecord" @on-submit="onFormSubmit" :reset-flag="formModalVisible"
                    :readonly="formReadonly" :disabled="formDisabled" :remarkreadonly="remarkReadonly" :isAudit="isAudit"/>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import MaterialForm from './MaterialForm.vue';
  export default {
    components: {
      PageHeader,
      SearchBar,
      MaterialForm
    },
    data() {
      return {
        formModalVisible: false,
        formReadonly: false,
        formDisabled:false,
        isAudit:false,
        remarkReadonly:false,
        currentRecord: {},
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          auditStatus: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '素材编码',
            key: 'code',
            align: 'center',
            width: 100
          },{
            title: '素材名称',
            key: 'name',
            align: 'center',

          }, {
            title: '尺寸',
            key: 'size',
            align: 'center',

          }, {
            title: '状态',
            key: 'auditStatusName',
            align: 'center',
          }, {
            title: '提交者',
            key: 'username',
            align: 'center',
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
        }, '详情'),
        h('a', {
          'class': ['tbl-link', 'info-link'],
          on: {
            click() {
              _this.onEdit(record.row);
            }
          }
        }, '修改'),
        h('a', {
          'class': ['tbl-link', 'info-link'],
          on: {
            click() {
              _this.onAudit(record.row);
            }
          }
        }, '审核')
      ]);
    }
    }
        ],
        tableData: []
      }
    },
    computed: {
      formModalTitle() {
        return this.remarkReadonly ? '查看素材（只读）' :
          (this.isAudit ? '素材审核' : '修改素材');
      },
    },
    methods: {
      search(curPage) {
        if (curPage) {
          this.searchParams.curPage = curPage;
        }
        $http.get('/advertsrv/auditing/list', {
          params: {
            params:  this.searchParams
          }
        }).then((data) => {
          if (data.data) {
          data.data.forEach((ele) => {
            ele.size = `${ele.width}*${ele.height}`;
            if(ele.auditStatus == 0){
              ele.auditStatusName ="未审核";
            }else if(ele.auditStatus == 1) {
              ele.auditStatusName ="通过";
            }else if(ele.auditStatus == 9) {
              ele.auditStatusName ="未通过";
            }
        })
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
      onView(row) {
        this.formReadonly = true;
        this.formDisabled = false;
        this.remarkReadonly = true;
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
      },
      onEdit(row){
        this.formReadonly = false;
        this.formDisabled = true;
        this.remarkReadonly = false;
        this.isAudit = false;
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
      },
      onAudit(row) {
        this.isAudit = true;
        this.formReadonly = false;
        this.formDisabled = true;
        this.remarkReadonly = false;
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
      },
      onFormSubmit(formData) {
        if(this.isAudit){
          var url = `/advertsrv/auditing`;
        }else{
          var url = `/advertsrv/auditing/edit`;
        }
        let title = `审核结果提交成功`;
        var form = {
          id: formData.id,
          auditStatus:formData.auditStatus,
          auditRemark:formData.auditRemark,
          cnzzUrl:formData.cnzzUrl
        };
        $http({
          method: 'put',
          url: url,
          data: form
        }).then((data) => {
          this.$Notice.success({
          title: title
        });
        this.hideFormModal();
        this.search();
      });
      },
      getCurrentRecordCopy() {
        return Object.assign({
          width: 0,
          height: 0,
          type: '图片素材'
        }, ...arguments);

      }
    },
    mounted() {
      this.search(1);
    },
  };
</script>

