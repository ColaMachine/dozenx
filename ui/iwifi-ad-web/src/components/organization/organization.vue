<template>
  <div>
    <ul class="search-bar">
      <li class="title">机构管理</li>
      <li class="org-add">
        <Button class="search-btn" @click="add">新建</Button>
      </li>
    </ul>
    <div  style="padding:20px 30px;">
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
      :title="formModalTitle" :width="600">
        <div style="margin:10px 60px;">
          <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="130">
            <FormItem label="机构名称" prop='name'>
              <Input type="text" v-model="formData.name" placeholder="请输入机构名称" :maxlength='50'></Input>
            </FormItem>
            <FormItem label="未识别报警间隔" prop='remark'>
              <Input type="text" v-model="formData.remark" placeholder="请输入分钟数" :maxlength='3'>
                <span slot='append'>分</span>
              </Input>
            </FormItem>
          </Form>
          <div class="btnBar">
              <Button class="btn" size="large" @click="hideFormModal"> 取消</Button>
              <Button type='primary' size="large"  @click="submit"> 确认</Button>
          </div>
        </div>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {validateOrgName,validateMinute} from '@/js/validate'
  export default {
    components: {

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
        formModalVisible: false,
        columns: [
          {
            title: '机构名称',
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
            let {name,remark,id} = params.row

            return h('div', [
              h('a', {
                'class': ['tbl-link', 'normal-link'],
                 on: {
                 click:()=> {
                   this.id = id
                  this.formData = {name,remark};
                  this.showFormModal();
                 }}
              }, '编辑'),
              h('a', {
                'class': ['tbl-link', 'error-link'],
                on: {
                 click:()=> {
                     this.confirmDelete(id, name);
                  }}
              }, '删除'),
              h('a', {
                class: ['tbl-link', 'normal-link'],
                on: {
                  click:()=> {
                    this.$router.push({path:'/organization/linkVideo',query:{id}})
                  }}
              }, '查看源视频'),
              h('a', {
                class: ['tbl-link', 'normal-link'],
                on: {
                  click:()=> {
                    this.$router.push({path:'/organization/linkPhoto',query:{id}})
                  }}
              }, '查看底库')
            ]);
          }
        }],
        tblData: [],
        formData:{
          name:'',
          remark:''
        },
        ruleValidate:{
          name:[
            {required:true,validator:validateOrgName,trigger:'blur'}
          ],
          remark:[
            {required:false,validator:validateMinute,trigger:'blur'}
          ]
        }
      }
    },
    computed: {
      formModalTitle() {
        return this.id ? `编辑机构信息` : '添加机构信息';
      }
    },
    methods: {
      search(curPage) {
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get('/api/mssrv/sys/auth/org/list', {
          params: {
            params:  this.searchData
          }
        }).then((data) => {
          this.tblData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      },
      add() {
        this.showFormModal();
      },
      confirmDelete(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
            title: '删除机构信息',
            content: `确认删除机构信息<span style="color:red;">${name}</span>？`,
            okText: '删除',
            onOk: () => {
              this.del(id);
            },
          });
      },
      del(id) {
        $http.delete(`/api/mssrv/sys/auth/org/delete/${id}`).then(() => {
            this.$Notice.success({
              title: '删除机构信息成功',
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
              $http.put(`/api/mssrv/sys/auth/org/update`, Object.assign(this.formData,{id:this.id})).then(cb);
            }else{
              $http.post('/api/mssrv/sys/auth/org/add', this.formData).then(cb);
            }

          }
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
