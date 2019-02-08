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
        <RoleForm :formData="currentRole" @on-submit="onFormSubmit" :reset-flag="formModalVisible"/>
      </div>
    </Modal>
    <Modal
      :mask-closable="false"
      v-model="permissionModalVisible"
      :title="permissionModalTitle" :width="600">
      <!--<div slot="header">-->
      <!--<div>DSP ID：{{}}</div>-->
      <!--</div>-->
      <!--<div slot="footer">-->
      <!--</div>-->
      <div style="padding:20px;">
        <PermissionForm :form-data="currentPermission" @on-submit="onPermissionSubmit"
                        :resetFlag="permissionModalVisible"/>
      </div>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import RoleForm from './RoleForm.vue';
  import PermissionForm from './PermissionForm.vue';

  export default {
    components: {
      PageHeader,
      SearchBar,
      RoleForm,
      PermissionForm
    },
    computed: {
      formModalTitle() {
        return this.currentRole.id ? '修改角色' : '新增角色';
      },
      permissionModalTitle() {
        return `权限设置 - ${this.currentRole.name}`;
      }
    },
    data() {
      return {
        formModalVisible: false,
        permissionModalVisible: false,
        currentRole: {},
        currentPermission: [],
        searchParams: {
          curPage: 1,
          pageSize: getPageSize(),
          code: '',
          nameLike: ''
        },
        totalRecords: 0,
        tableCols: [
          {
            title: '角色编码',
            key: 'code',
            align: 'center',
            width: 300
          }, {
            title: '角色名称',
            key: 'name',
            align: 'center',
            width: 300
          }, {
            title: '角色描述',
            key: 'remark',
            align: 'center',
            // width: 200
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
                      _this.onEditRole(record.row);
                    }
                  }
                }, '修改'), h('a', {
                  'class': ['tbl-link', 'info-link'],
                  on: {
                    click() {
                      _this.onEditPermission(record.row);
                    }
                  }
                }, '权限设置'), h('a', {
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
        $http.get('/home/sys/auth/role/list', {
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
      showPermissionModal() {
        this.setPermissionModalVisible(true);
      },
      hidePermissionModal() {
        this.setPermissionModalVisible(false);
      },
      setPermissionModalVisible(isVisible) {
        this.permissionModalVisible = isVisible;
      },
      onAdd() {
        this.currentRole = {};
        this.showFormModal();
      },
      onEditRole(row) {
        console.log('onEditRole', row);
        this.currentRole = Object.assign({}, row);
        this.showFormModal();
      },
      onEditPermission(row) {
        // console.log('onEditPermission', row);
        this.currentRole = Object.assign({}, row);
        // this.currentRole = row;
        this.getCurrentPermission(row.id);
        // this.showPermissionModal();
      },
      onView(row) {
        this.currentRole = Object.assign({}, row);
        this.showFormModal();
      },
      onDelete(row) {
        this.confirmDeleteRole(row.id, row.name);
      },
      onFormSubmit() {
        let isEdit = this.currentRole.id ? true : false;
        let url = isEdit ? ('/home/sys/auth/role/update/'+this.currentRole.id) : '/home/sys/auth/role/add'
        let method = isEdit ? 'put' : 'post';
        let title = `${isEdit ? '修改' : '新增'}角色成功`;
        $http({
          method: method,
          url: url,
          data: this.currentRole
        }).then((data) => {
          this.$Notice.success({
            title: title
          });
          this.hideFormModal();
          this.search();
        });
      },
      confirmDeleteRole(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
          title: '删除角色',
          content: `确认删除角色<span style="color:red;">${name}</span>？`,
          okText: '删除',
          onOk: () => {
            this.deleteRole(id);
          },
        });
      },
      deleteRole(id) {
        $http.delete(`/home/sys/auth/role/del/${id}`).then(() => {
          this.$Notice.success({
            title: '删除角色成功',
          });
          this.search(1);
        });
      },
      getCurrentPermission(id) {
        $http.get(`/home/sys/auth/role/permission/tree/${id}`).then((data) => {
          this.currentPermission = [];
          data.data.forEach((item) => {
            if (!item.childs || !item.childs.length) {
              return;
            }
            this.currentPermission.push({
              id: item.id,
              renderKey: Math.random(),
              checkAllLabel: item.name,
              options: item.childs,
              value: item.childs.filter((subItem) => {
                return subItem.checked;
              }).map((subItem) => {
                return subItem.id;
              })
            });
          });
          this.showPermissionModal();
        });
      },
      onPermissionSubmit(permissionData) {
        $http.put('/home/sys/auth/role/permission/update', {
          roleId: this.currentRole.id,
          permissionIds: permissionData
        }).then((data) => {
          this.$Notice.success({
            title: '权限设置成功',
          });
          this.hidePermissionModal();
        });
      }
      // confirmCloseModal() {
      //   this.$Modal.confirm({
      //     title: '',
      //     content: '关闭窗口将清空当前表单数据，是否确认？',
      //     okText: '关闭',
      //     onOk: () => {
      //       this.hideFormModal();
      //     },
      //   });
      // },
    },
    mounted() {
      this.search(1);
    },
    watch: {
      // currentRole(newVal, oldVal) {
      //   console.log('currentRole', newVal, oldVal);
      // }
    }
  };
</script>

