<template>
  <div>
    <ul class="search-bar">
      <li class="title">底库管理</li>
      <li class="org-add">
        <Button class="search-btn" @click="add">新建</Button>
      </li>
    </ul>
    <div class="content-wrap">
      <Table :columns="columns" :data="tblData"></Table>
      <div class='page' v-show="totalRecords">
        <Page :current.sync="searchData.curPage"
              :total="totalRecords"
              :page-size="searchData.pageSize"
              @on-change="search"
              simple></Page>
      </div>
    </div>
    <Modal
      class='iModal'
      :footer-hide="true"
      v-model="formModalVisible"
      title="库信息" :width="600">
        <div style="margin:10px 60px;">
          <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="130">
            <FormItem label="库名称" prop='name'>
              <Input type="text" v-model="formData.name" placeholder="请输入库名称"></Input>
            </FormItem>
          </Form>
          <div class="btnBar">
              <Button class="btn" size="large" @click="hideFormModal"> 取消</Button>
              <Button type='primary' size="large"  @click="submit"> 确认</Button>
          </div>
        </div>
    </Modal>
    <Modal
      class='iModal'
      :footer-hide="true"
      v-model="photoModal"
      title="添加人像" :width="600">
        <ModalForm :data="photoForm" v-if='photoModal' @cancel='onCancel' @submit='onSubmit'/>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import ModalForm from './components/modalForm'
  export default {
    components: {
      ModalForm
    },
    mounted() {
      this.search(1);
    },
    data() {
      return {
        searchData: {
          curPage: 1,
          pageSize:10
        },
        totalRecords: 0,
        isAudit:false,
        currentRecord: {},
        formModalVisible: false,
        photoModal:false,
        photoForm:{},
        columns: [
          {
            title: '库名称',
            key: 'name',
            align: 'center',
          },
          {
            title: '创建时间',
            key: 'createTime',
            align: 'center',
            render:(h,params)=>{
              let {createTime} = params.row
              return h('span',new Date(createTime).toLocaleString())
            }
          },
          {
            title: '操作',
            align: 'center',

            render: (h, params) => {
            let {name,id} = params.row
            return h('div', [
              h('a', {
                'class': ['tbl-link', 'normal-link'],
                 on: {
                 click:()=> {
                  this.id = id
                  this.formData.name = name;
                  this.showFormModal();
                 }}
              }, '编辑'),
              h('a', {
                'class': ['tbl-link', 'error-link'],
                on: {
                 click:()=> {
                     this.confirmDelete(id,name);
                  }}
              }, '删除'),
              h('a', {
                class: ['tbl-link', 'normal-link'],
                on: {
                  click:()=> {
                    this.$router.push({path:'/library/photo',query:{id}})
                  }}
              }, '查看人像'),
              h('a', {
                class: ['tbl-link', 'normal-link'],
                on: {
                  click:()=> {
                    this.id = id
                    this.photoModal=true
                  }}
              }, '添加人像')
            ]);
          }
        }],
        tblData: [],
        formData:{
          name:''
        },
        ruleValidate:{
          name:[
            {required:true,message:'必填',trigger:'blur'}
          ]
        }
      }
    },
    computed: {

    },
    methods: {
      search(curPage) {
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get('/api/mssrv/sys/auth/group/list', {
          params: {
            params:  this.searchData
          }
        }).then((data) => {
          this.tblData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      },
      add() {
        this.currentRecord = {};
        this.showFormModal();
      },
      confirmDelete(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
            title: '删除底库信息',
            content: `确认删除底库信息<span style="color:red;">${name}</span>？`,
            okText: '删除',
            onOk: () => {
              this.del(id);
            },
          });
      },
      del(id) {
        $http.delete(`/api/mssrv/sys/auth/group/delete/${id}`).then(() => {
            this.$Notice.success({
              title: '删除底库成功',
            });
            this.search(1);
        });
      },
      submit() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            let cb = ()=>{
              this.$Notice.success({
                title: "提交成功"
              });
              this.hideFormModal();
              this.search();
            }
            if (this.id) {
              $http.put(`/api/mssrv//sys/auth/group/update`, Object.assign(this.formData,{id:this.id})).then(cb);
            }else{
              $http.post('/api/mssrv/sys/auth/group/add', this.formData).then(cb);
            }
          }
        });
      },
      onCancel(){
        this.photoModal = false
      },
      onSubmit(data){
        $http.post('/api/mssrv/sys/auth/group/user/add', Object.assign(data,{groupId:this.id})).then(()=>{
          this.$Notice.success({
            title: "提交成功"
          });
          this.photoModal=false
          this.search();
        });
      },
      showFormModal() {
        this.formModalVisible = true;
      },
      hideFormModal() {
        this.formModalVisible = false;
      },
    },
    watch:{
      formModalVisible(val){
        if (!val) {
          this.id = ''
          this.$refs.form.resetFields();
        }
      }
    }
  };
</script>
