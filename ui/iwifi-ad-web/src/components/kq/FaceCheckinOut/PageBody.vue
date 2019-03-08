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

  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {getPageSize} from "@/js/util";
  import PageHeader from './PageHeader.vue';
  import SearchBar from './SearchBar.vue';
  import AccountForm from './AccountForm.vue';
 import DateFormat from '@/js/dateFormat';
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
            title: 'id',
            key: 'id',
            align: 'center',
            width: 100
          },{
            title: '用户id',
            key: 'userId',
            align: 'center',
            width: 100
          }, {
            title: '用户名称',
            key: 'userName',
            align: 'center',
            width: 100
          }, {
            title: '摄像头',
            key: 'camera',
            align: 'center',
            // width: 200
          }, {
            title: '时间',
            key: 'checkTime',
            align: 'center',
            // width: 200
          }, {
            title: '分数',
            key: 'score',
            align: 'center',
            // width: 200
          },{
                title: '图像',
                key: 'img',
                align: 'center',
                // width: 200
                 render: (h, record) => {
                      let _this = this;
                      return h('a',{attrs:{'target':'_blank','href':'/home/checkin/'+record.row.id+'.png'}},

                       [
                        h('img', {


                          'style':"width:150px;height:150px;",
                          attrs:{
                            'src': "/home/checkin/"+record.row.id+".png",
                          }
                        }, '查看')
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
let params = Object.assign({}, this.searchParams);
        if (!params.checkTimeBegin) {
          delete params.checkTimeBegin;
        } else {
          params.checkTimeBegin = DateFormat.format.date(params.checkTimeBegin, 'yyyy-MM-dd')+" 00:00:00" ;
        }
        if (!params.checkTimeEnd) {
          delete params.checkTimeEnd;
        } else {
          params.checkTimeEnd = DateFormat.format.date(params.checkTimeEnd, 'yyyy-MM-dd')+" 23:59:59" ;
        }
        $http.get('/home/checkin/faceCheckinOut/list', {
          params: {
            params: params
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

