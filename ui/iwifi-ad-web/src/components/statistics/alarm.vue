<template>
  <div>
    <ul class="search-bar" style="padding-bottom:0">
      <li class="title">报警统计</li>
      <li class="bar">
        <DatePicker v-model="searchData.startTime" placeholder="报警时间" style="width: 130px" size='large' :editable='false'></DatePicker>
        -
        <DatePicker v-model="searchData.endTime" placeholder="结束时间" style="width: 130px" size='large' :editable='false'></DatePicker>
        <Select v-model="searchData.orgId" style="width:180px" placeholder="请选择机构" size='large' clearable>
            <Option v-for="item in orgList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Button type="primary" @click="search(1)">查询</Button>
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
  </div>
</template>
<script>
  import $http from '@/js/http2';
  export default {
    components: {

    },
    created() {
      this.getOrg()
      this.search(1);
    },
    data() {
      return {
        searchData: {
          orgId:'',
          startTime:'',
          endTime:'',
          curPage: 1,
          pageSize:10
        },
        orgList:[],
        totalRecords: 0,
        columns: [
          {
            title: '机构名称',
            key: 'orgName',
            align: 'center',
          },
          {
            title: '报警时间',
            key: 'zzDate',
            align: 'center',
            render:(h,params)=>{
              let {startTime} = params.row
              return h('span',new Date(startTime).toLocaleString())
            }
          },
          {
            title: '结束时间',
            key: 'tax',
            align: 'center',
            render:(h,params)=>{
              let {endTime} = params.row
              return h('span',new Date(endTime).toLocaleString())
            }
          },
          {
            title: '持续时间（分）',
            key: 'duration',
            align: 'center',
          }
        ],
        tblData: []
      }
    },
    methods: {
      getOrg(){
        $http.get('/api/mssrv/sys/auth/org/list', {
          params: {
            params:  {curPage: 1,pageSize:99}
          }
        }).then((res) => {
          this.orgList = res.data.map((item)=>{
            return {
              value:item.id,
              label:item.name
            }
          })
        });
      },
      search(curPage) {
        console.log(curPage);
        if (curPage) {
          this.searchData.curPage = curPage;
        }
        $http.get('/api/mssrv/warn/getListByParams', {
          params: {
            params:  this.searchData
          }
        }).then((res) => {
          this.tblData = res.data;
          this.totalRecords = res.page.totalCount;
        });
      }
    },
    watch:{

    }
  };
</script>
