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
      <AccountForm :formData="currentRecord" @on-submit="onFormSubmit" :reset-flag="formModalVisible"
                   :readonly="formReadonly"/>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import AccountForm from './AccountForm.vue';

  export default {
    components: {
      PageHeader,
      SearchBar,
      AccountForm
    },
    computed: {
      formModalTitle() {
        return this.formReadonly ? '查看账号（只读）' :
          (this.currentRecord.id ? '修改账号' : '添加账号');
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
          roleId: '',
          location: ['', '', ''],
          name: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '账号',
            key: 'account',
            align: 'center',
            width: 100
          },{
            title: '名称',
            key: 'username',
            align: 'center',
            width: 100
          }, {
            title: '角色',
            key: 'roleNames',
            align: 'center',
            width: 100
          }, {
            title: '地区',
            key: 'location',
            align: 'center',
            // width: 200
          }, {
            title: '部门',
            key: 'department',
            align: 'center',
            // width: 200
          }, {
            title: '联系方式',
            key: 'telno',
            align: 'center',
            // width: 200
          }, {
            title: '头像',
            align: 'center',
            // width: 200

                render: (h, record) => {
                                  let _this = this;
                                  return h('a',{attrs:{'target':'_blank','href':'/home/'+record.row.face}},

                                   [
                                    h('img', {


                                      'style':"width:150px;height:150px;",
                                      attrs:{
                                        'src': "/home/"+record.row.face.replace("/jpg/","/thumb/"),
                                      }
                                    }, '查看')
                                  ]);
                                }
          } ,{
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
        $http.get('/home/sys/auth/user/list', {
          params: {
            params: this.searchParams
          }
        }).then((data) => {
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
        this.formReadonly = false;
        this.currentRecord = {
          roleIds: []
        };
        this.showFormModal();
      },
      onEdit(row) {
        console.log('onEdit', row);
        // this.currentRecord = Object.assign({}, row);
        // this.showFormModal();
        this.formReadonly = false;
        this.getAccount(row.id);
      },
      onView(row) {
        this.formReadonly = true;
        this.getAccount(row.id);
      },
      onDelete(row) {
        this.confirmDelete(row.id, row.account);
      },
      onFormSubmit() {
        let isEdit = this.currentRecord.id ? true : false;
        let url = isEdit ? `/home/sys/auth/user/update/${this.currentRecord.id}` : '/home/sys/auth/user/add';
        let method = isEdit ? 'put' : 'post';
        // let method = 'post';
        let title = `${isEdit ? '修改' : '新增'}账号成功`;
        $http({
          method: method,
          url: url,
          data: Object.assign(this.currentRecord, {
            province: this.currentRecord.fullLocation[0],
            city: this.currentRecord.fullLocation[1],
            county: this.currentRecord.fullLocation[2]
          })
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
          title: '删除账号',
          content: `确认删除账号<span style="color:red;">${name}</span>？`,
          okText: '删除',
          onOk: () => {
            this.del(id);
          },
        });
      },
      del(id) {
        $http.delete(`/home/sys/auth/user/del/${id}`).then(() => {
          this.$Notice.success({
            title: '删除账号成功',
          });
          this.search(1);
        });
      },
      getAccount(id) {
        $http.get(`/home/sys/auth/user/view/${id}`).then(({data}) => {
          data.fullLocation = [data.province, data.city, data.county];
          this.currentRecord = data;
          this.showFormModal();
        });
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

