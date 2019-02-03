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
      <MaterialForm :value="currentRecord" @on-submit="onFormSubmit" :reset-flag="formModalVisible"
                    :readonly="formReadonly"/>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import MaterialForm from './MaterialForm.vue';
  // import Mock from 'mockjs';

  // Mock.mock(/lists/, {
  //     "r": 0,
  //     "data|15": [
  //       {
  //         "id|+1": 1000,
  //         "code|+1": 1000,
  //         "type": 123,
  //         "name": "@CNAME",
  //         "width|100-1000": 210,
  //         "height|100-1000": 310,
  //         "imgUrl": "@URL",
  //         "jumpUrl": "@URL",
  //         "status|0-1": 1,
  //         "createTime": "@DATETIME",
  //         "updateTime": "@DATETIME"
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
  export default {
    components: {
      PageHeader,
      SearchBar,
      MaterialForm
    },
    computed: {
      formModalTitle() {
        return this.formReadonly ? '查看素材（只读）' :
          (this.currentRecord.id ? '修改素材' : '添加素材');
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
          name: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '素材编码',
            key: 'code',
            align: 'center',
          }, {
            title: '素材名称',
            key: 'name',
            align: 'center',
          }, {
            title: '尺寸',
            key: 'size',
            align: 'center',
            // width: 200
          }, {
            title: '创建时间',
            key: 'createTime',
            align: 'center',
            // width: 200
          },{
            title: '状态',
            key: 'auditStatusName',
            align: 'center',
          },
          {
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
                }, '查看'), h('a', {
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
        $http.get('/home/material/list', {
          params: {
            params: this.searchParams
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
      onAdd() {
        var record ={
          name:'',
          width:'480',
          height:'360',
          size:'480*360',
          type:'',
          imgUrl:'',
          jumpUrl:''
        };
        this.formReadonly = false;
        this.currentRecord = record;
        this.showFormModal();
      },
      onEdit(row) {
        console.log('onEdit', row);
        this.formReadonly = false;
        row.size = row.width+"*"+row.height;
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
        // this.getAccount(row.id);
      },
      onView(row) {
        this.formReadonly = true;
        row.size = row.width+"*"+row.height;
        this.currentRecord = this.getCurrentRecordCopy(row);
        this.showFormModal();
        // this.getAccount(row.id);
      },
      onDelete(row) {
        this.confirmDelete(row.id, row.name);
      },
      onFormSubmit(formData) {
        if(formData.auditStatus == 9){
          formData.auditStatus = 0;
          formData.auditStatusName ="未审核";
        }
        let isEdit = formData.id ? true : false;
        let url = `/home/material`;
        let method = isEdit ? 'put' : 'post';
        // let method = 'post';
        let title = `${isEdit ? '修改' : '新增'}素材成功`;
        $http({
          method: method,
          url: url,
          data: formData
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
          title: '删除素材',
          content: `确认删除素材<span style="color:red;">${name}</span>？`,
          okText: '删除',
          onOk: () => {
            this.del(id);
          },
        });
      },
      del(id) {
        $http.delete(`/home/material/${id}`).then(() => {
          this.$Notice.success({
            title: '删除素材成功',
          });
          this.search(1);
        });
      },
      // getAccount(id) {
      //   $http.get(`/home/sys/auth/user/view/${id}`).then(({data}) => {
      //     data.fullLocation = [data.province, data.city, data.county];
      //     this.currentRecord = data;
      //     this.showFormModal();
      //   });
      // },
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
    watch: {
      // 'searchParams.location': {
      //   // deep: true,
      //   handler(val){
      //     this.searchParams.province=val[0];
      //     this.searchParams.
      //   }
      // }
    }
  };
</script>

