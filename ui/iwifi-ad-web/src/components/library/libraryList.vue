<template>
  <div class="video-stream">
    <div class="face-list">
      <header>
        <span class="title">底库人像信息</span>
        <a @click='$router.back()' class="more">返回</a>
      </header>
      <main>
        <ul class="avatar">
          <li v-for='item in captureData'>
            <img :src="item.faces[0]"/>
            <p>姓名：{{item.username}}</p>
            <div style='display:flex;justify-content: space-between;'>
              <Button type="primary" ghost size='small' @click='edit(item)'>编辑</Button>
              <Button type="error" ghost size='small' @click='del(item)'>删除</Button>
            </div>
          </li>
        </ul>
      </main>
      <Page
      :current.sync="searchData.curPage"
      :total="totalRecords"
      :page-size="searchData.pageSize"
       simple class="page"
       @on-change="changePage"/>
    </div>
    <Modal
      class='iModal'
      :footer-hide="true"
      v-model="photoModal"
      title="编辑人像" :width="600">
        <ModalForm :data="photoForm" v-if='photoModal' @cancel='onCancel' @submit='onSubmit'/>
    </Modal>
  </div>
</template>
<style lang="less" scoped>
  @import '../../css/face.less';
  .ivu-btn-small{
    padding: 4px 10px;
  }
</style>

<script>
  import $http from '@/js/http2';
  import ModalForm from './components/modalForm'
  export default {
    components: {
      ModalForm
    },
    created() {
      let {id} = this.$route.query
      if (id) {
        this.searchData.groupId=id
      }
      this.search(1);
    },
    data() {
      return {
        photoModal:false,
        photoForm:{},
        searchData: {
          curPage: 1,
          pageSize: 30
        },
        totalRecords: 0,
        captureData:[
          // {img:require('../../assets/avatar.png')},
          // {img:require('../../assets/avatar.png')}
        ]
      }
    },
    methods:{
      changePage(current){
        this.search(current)
      },
      search(curPage){
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get('/api/mssrv/sys/auth/group/user/list', {
          params: {
            params:  this.searchData
          }
        }).then((res) => {
          this.captureData = res.data;
          this.totalRecords = res.page.totalCount;
          // this.totalRecords = 115
          console.log(res.page.totalCount);
        });
      },
      edit(item){
        this.photoForm = item
        this.photoModal=true
      },
      del(item) {
        this.$Modal.confirm({
            title: '删除',
            content: `确认删除此张人像信息？`,
            onOk: () => {
              let data = {
                userId:item.id,
                groupId:this.searchData.groupId
              }
              $http.delete(`/api/mssrv/sys/auth/group/user/delete`,{data}).then(() => {
                  this.$Notice.success({
                    title: '删除成功',
                  });
                  this.search(1);
              });
            },
          });
      },
      onCancel(){
        this.photoModal=false
      },
      onSubmit(data){
        $http.put(`/api/mssrv/sys/auth/group/user/update`, Object.assign(data,{groupId:this.searchData.groupId,userId:this.photoForm.userId})).then(()=>{
          this.$Notice.success({
            title: "提交成功"
          });
          this.photoModal=false
          this.search();
        });
      },
    }
  };
</script>
