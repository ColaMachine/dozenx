<template>
  <div id="advertis">
    <!-- 添加广告位 -->
    <Header @on-add="addAd"></Header>
    <div class="advertisContent" :style="{height:advHeight+'px'}">
       <Search @on-click="search" v-model="searchData"></Search>
       <Table :columns="columns1" :data="tblData" ></Table>
       <!-- <Paging :current=searchData.current :total=searchData.total :max="max"></Paging> -->
        <div class="footPage" v-show="totalRecord&&totalRecord>0">
          <Page  show-sizer
                  show-elevator
                  show-total
                  simple
                  size="small"
                  :page-size="searchData.pageSize"
                  :current="searchData.curPage"
                  :total="totalRecord"
                  @on-page-size-change="pageSizeChange" 
                  @on-change="curPageChange">
          </Page>
       </div>
       <div v-if="modal2">
        <Modal v-model="modal2" width="600" height="320"  @on-cancel="cancel"  :title="title" class="adverAdd">
             <app-model @on-cancel="cancel" ref="addForm" :flag='flag' :appDate="formValidate"  :details="details"></app-model>
        </Modal>
       </div>
    </div>
  </div>
</template>
<script>
import Header from './Header.vue'
import Search from './Search.vue'
import $http from '../../../js/http'
import AppModel from './AppModel.vue'
import DateFormat from '../../../js/dateFormat'
// import Paging from '../../Paging.vue'
export default {
  components:{
      Header,
      Search,
      AppModel,
  },
  data () {
    return {
      modal2:false,
      formValidate:{},
      title:"",
      flag:Boolean,
      details:Boolean,
      advHeight:'',
      totalRecord:0,
      searchData:{
        pageSize:10,
        curPage:1,
        id:'',
        name:''
      },
       max:2,
       columns1: [
                    {
                        title: '广告位编码',
                        key: 'code',
                        align:'center',
                    },
                    {
                        title: '广告位名称',
                        key: 'name',
                        align:'center',
                    },
                    {
                        title: '尺寸',
                        key: 'length',
                        align:'center',
                        render:(h,params)=>{
                          let length = params.row.length;
                          let width = params.row.width;
                          return h('div',`${length}*${width}`)
                        }
                    },
                    {
                      title:'创建时间',
                      key:'createTime',
                      align:'center',
                       render:(h,params)=>{
                        return `${DateFormat.format.date(
                               params.row.createTime,
                               "yyyy-MM-dd"
                            )}`;
                      }
                    },
                    {
                      title:'操作',
                      key:'length',
                      width:180,
                      align:'center',
                      className: 'demo-table-info-column',
                      render: (h,params)=>{
                      let _this=this;
                       return('div',[
                         h('a',{
                          'class': ['tbl-link', 'info-link'],
                           on:{
                            click:()=>{
                              _this.detailAd(params.row);
                            }
                         }},'详情'),
                         h('a',{
                          'class': ['tbl-link', 'info-link'],
                           on:{
                             click:()=>{
                               _this.editAd(params.row);
                             }
                           }},'修改'),
                           h('a',{
                            'class': ['tbl-link', 'error-link'],
                            on:{
                              click:()=>{
                                _this.deleteAd(params.row)
                              }
                            }},'删除')
                       ])
                      }
                    }
                ],
               tblData: []  
         }
  },
  methods:{
     cancel() {
       setTimeout(() => {
              this.modal2 = false;
              this.search();
          }, 50)
    },
      pageSizeChange(pageSize){
      this.searchData.pageSize=pageSize;
      this.curPageChange(1)

    },
    curPageChange(curPage){
      this.searchData.curPage=curPage;
      this.search();
    },
    search(){
      let httpConfig={
        params:{params:JSON.stringify(this.searchData)},
        _this:this,
        _tblData:'tblData',
        _totalRecord: 'totalRecord'
      };
      $http.list('/advertsrv/advertspace/list',httpConfig);
    },
    //删除广告位
    deleteAd(row){
      let _this=this;
        this.$Modal.confirm({
            content:`确定要删除该广告位 <span style="color:red;">${row.code}</span>吗？`,
                onOk:function(){
                  let httpConfig ={
                  params:row.id,
                  _this:_this,
                  time:800,
                  msg:"删除成功"
                }
                  $http._delete(`advertsrv/advertspace/${row.id}`,httpConfig)
              }
          })
    },
    //修改广告位
    editAd(row){
      this.title="修改广告位";
      this.flag=true;
       this.modal2=true;
       this.formValidate=row;
        this.details=true;
    },
    //添加广告位
    addAd(){
      this.title="添加广告位";
      this.formValidate={};
      this.modal2=true;
      this.flag=false;
      this.details=true;
    },
    //详情广告位
    detailAd(row){
      this.flag=false;
      this.details=false;
      this.title="广告位-详情";
       this.modal2=true;
       this.formValidate=row;
    }
  },
  mounted () {
    this.advHeight=window.innerHeight-140;
    this.search();
  }
}
</script>

<style>

</style>

