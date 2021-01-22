<template>
  <div>
    <ul class="search-bar">
      <li class="title">关联视频源</li>
      <li class="org-add">
        <Button class="search-btn" @click="add">关联视频源</Button>
      </li>
    </ul>
    <div class="content-wrap">
      <Table :columns="columns" :data="tblData"></Table>
      <div class="page" v-show="totalRecords">
        <Page :current.sync="searchData.curPage"
              :total="totalRecords"
              :page-size="searchData.pageSize"
              @on-change="search"
              simple></Page>
      </div>
    </div>
    <Modal
      class="iModal"
      :footer-hide="true"
      v-model="formModalVisible"
      title="关联视频源" :width="600">
      <Transfer
        :list-style='transferStyle'
        :titles='["待选择","已选择"]'
        :data="transferData"
        :target-keys="targetKeys"
        @on-change="handleChange"></Transfer>
      <div class="btnBar">
          <Button class="btn" size="large" @click="hideFormModal"> 取消</Button>
          <Button type='primary' size="large"  @click="submit"> 确认</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  export default {
    components: {

    },
    created(){
      let {id} = this.$route.query
      if (id) {
        this.searchData.id=id
      }
      this.search(1);
      this.getTransfer()
    },
    mounted() {

    },
    data() {
      return {
        searchData: {
          curPage: 1,
          pageSize: 10
        },
        totalRecords: 0,
        isAudit:false,
        currentRecord: {},
        formModalVisible: false,
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
              return h('span',new Date(createTime).toLocaleString())
            }
          }
        ],
        tblData: [],
        transferData:[],
        targetKeys:[],
        transferStyle:{
          width:'252px',
          height:'250px'
        }
      }
    },
    computed: {

    },
    methods: {
      getTransfer(){
        let {curPage,pageSize} = this.searchData
        $http.get('/api/mssrv/videosource/getListByParams', {
          params: {
            params:{}
          }
        }).then((res) => {
          this.transferData = res.data.map(item=>{
            return {
              key:item.id,
              label:item.name
            }
          })
        });
      },
      getTransferChoice(){
        $http.get('/api/mssrv/videosource/getListByorgId', {
          params: {
            params:{
              id:this.searchData.id,
              curPage:1,
              pageSize:999
            }
          }
        }).then((res) => {
          this.targetKeys = res.data.map(item=>item.id)
        });
      },
      search(curPage) {
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get(`/api/mssrv/videosource/getListByorgId`, {
          params: {
            params:  this.searchData
          }
        }).then((data) => {
          // this.targetKeys = data.data.map(item=>item.id)
          this.tblData = data.data;
          this.totalRecords = data.page.totalCount;
        });
      },

      handleChange(newTargetKeys){
        this.targetKeys = newTargetKeys;
      },
      add() {
        this.currentRecord = {};
        this.showFormModal();
      },
      submit() {
        // if (!this.targetKeys.length) {
        //   this.$Message.warning('已选择列表不能为空，请再次选择')
        //   return
        // }
        $http.put('/api/mssrv/sysOrgVideo/batchAdd', {orgId:this.searchData.id,videoIdList:this.targetKeys}).then((res) => {
          this.$Notice.success({
          title: "提交成功"
        });
        this.hideFormModal();
        this.search(1);
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
        if (val) {
          // this.targetKeys = []
          // this.getTransfer()
          this.getTransferChoice()
        }
      }
    }

  };
</script>
