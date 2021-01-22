<template>
  <div>
    <ul class="search-bar">
      <li class="title">视频源管理</li>
      <li class="org-add">
        <Button class="search-btn" @click="showFormModal">新建</Button>
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
      title="视频源信息" :width="600">
        <div style="margin:10px 60px;">
          <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="130">
            <FormItem label="视频源名称" prop='name'>
              <Input type="text" v-model="formData.name" placeholder="请输入视频源名称" :maxlength='20'></Input>
            </FormItem>
            <FormItem label="视频源地址" prop='videoUrl'>
              <Input type="text" v-model="formData.videoUrl" placeholder="请输入视频源地址" :maxlength='100'></Input>
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
        currentRecord: {},
        formModalVisible: false,
        photoModal:false,
        photoForm:{},
        columns: [
          {
            title: '源名称',
            key: 'name',
            align: 'center',
          },
          {
            title: '源地址',
            key: 'videoUrl',
            align: 'center',
          },
          {
            title: '创建时间',
            key: 'createTime',
            align: 'center',
            render:(h,params)=>{
              let {createTime} = params.row
              return h('span',createTime?new Date(createTime).toLocaleString():'')
            }
          },
          {
            title: '操作',
            align: 'center',

            render: (h, params) => {
            let {id,name,videoUrl} = params.row
            return h('div', [
              h('a', {
                'class': ['tbl-link', 'normal-link'],
                 on: {
                 click:()=> {
                   this.id=id
                  this.formData = {name,videoUrl}
                  this.showFormModal();
                 }}
              }, '编辑'),
              h('a', {
                'class': ['tbl-link','action-link'],
                on: {
                 click:()=> {
                     this.open(id)
                  }}
              }, '启动'),
              h('a', {
                'class': ['tbl-link', 'info-link'],
                on: {
                 click:()=> {
                     this.close(id)
                  }}
              }, '停止'),
              h('a', {
                'class': ['tbl-link', 'error-link'],
                on: {
                 click:()=> {
                     this.confirmDelete(id,name);
                  }}
              }, '删除')
            ]);
          }
        }],
        tblData: [],
        formData:{
          name:'',
          videoUrl:''
        },
        ruleValidate:{
          name:[
            {required:true,message:'必填',trigger:'blur'}
          ],
          videoUrl:[
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
        $http.get('/api/mssrv/videosource/getListByParams', {
          params: {
            params:  this.searchData
          }
        }).then((data) => {
          this.tblData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      },
      open(id){
        $http.put(`/api/mssrv/videosource/start`,{id}).then(() => {
            this.$Notice.success({
              title: '启动成功',
            });
        });
      },
      close(id){
        $http.put(`/api/mssrv/videosource/stop`,{id}).then(() => {
            this.$Notice.success({
              title: '停止成功',
            });
        });
      },
      confirmDelete(id, name) {
        if (!id) {
          return;
        }
        this.$Modal.confirm({
            title: '删除',
            content: `确认删除视频源<span style="color:red;">${name}</span>？`,
            onOk: () => {
              this.del(id);
            },
          });
      },
      del(id) {
        $http.delete(`/api/mssrv/videosource/delete`,{
          params:{
            params:{id}
          }
        }).then(() => {
            this.$Notice.success({
              title: '删除成功',
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
              this.search(1);
            }
            if (this.id) {
              $http.put(`/api/mssrv/videosource/update`, Object.assign(this.formData,{id:this.id})).then(cb);
            }else{
              $http.post('/api/mssrv/videosource/add', this.formData).then(cb);
            }
          }
        });
      },
      onCancel(){
        this.photoModal = false
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
          this.id=''
          this.$refs.form.resetFields();
        }
      }
    }
  };
</script>
