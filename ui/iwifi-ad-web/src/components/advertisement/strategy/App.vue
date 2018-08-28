<template>
  <div id="advertis">
    <!-- 添加策略 -->
    <Header @on-add="addAd"></Header>
    <div class="advertisContent" :style="{height:advHeight+'px'}">
      <Search @on-click="search" v-model="searchData"></Search>
      <Table :columns="columns1" :data="tblData"></Table>
      <div class="footPage">
        <Page show-sizer
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
    </div>
    <div v-if="modal2">
      <Modal v-model="modal2" width="600" height="320" @on-cancel="cancel" :title="title" class="adverAdd">
        <app-model @on-cancel="cancel" ref="addForm" :details="details" :flag="flag"
                   :appDate="formValidate"></app-model>
      </Modal>
    </div>
  </div>
</template>
<script>
  import Header from "./Header.vue";
  import Search from "./Search.vue";
  import $http from "../../../js/http";
  import $http2 from "@/js/http2";
  import AppModel from "./AppModel.vue";
  import DateFormat from "../../../js/dateFormat";
  // import Paging from '../../Paging.vue'
  export default {
    components: {
      Header,
      Search,
      AppModel,
    },
    data() {
      return {
        id: "",
        title: "",
        flag: Boolean,
        details: Boolean,
        modal2: false,
        Detaildata: [],
        formValidate: {},
        totalRecord: 0,
        advHeight: "",
        searchData: {
          advertSpaceId: Number,
          id: Number,
          // advertPartnerId: Number,
          pageSize: 10,
          curPage: 1
        },
        max: 2,
        columns1: [
          {
            title: "策略编码",
            key: "id",
            align: "center"
          },
          {
            title: "广告位",
            key: "advertSpaceName",
            align: "center"
          },
          {
            title: "行业",
            key: "industryList",
            align: "center",
            render: (h, params) => {
              let industryName = params.row.industryList;
              let industryArr = [];
              if(industryName){
                industryName.forEach(function (element) {
                  industryArr.push(element.industryName);
                }, this);
              }
              return h("div", `${industryArr}`);
            }
          },
          {
            title: "区域",
            key: "areaList",
            align: "center",
            render: (h, params) => {
              let areaArr = [];
            if( params.row.areaList){
              params.row.areaList.forEach(function (element) {
                let city = `${element.provinceName}`;
                if (element.cityName) {
                  city = `${element.provinceName}${element.cityName}`;
                  if (element.countyName) {
                    city = `${element.provinceName}${element.cityName}${element.countyName}`;
                  }
                }
                areaArr.push(city);
              });
            }
              return h("div", `${areaArr}`);
            }
          },
          {
            title: "合作方",
            key: "advertPartnerName",
            align: "center",
          },
          {
            title: "约束时间",
            key: "effectEndTime",
            align: "center",
            render: (h, params) => {
              return `${DateFormat.format.date(
                params.row.effectEndTime,
                "yyyy-MM-dd"
              )}`;
            }
          },
          {
            title: "操作",
            key: "operation",
            width: 180,
            align: "center",
            className: "demo-table-info-column",
            render: (h, params) => {
              let _this = this;
              return ("div", [
                  h("a", {
                      class: ["tbl-link", "info-link"],
                      on: {
                        click: () => {
                          _this.detailAd(params.row);
                        }
                      }
                    }, "详情"
                  ),
                  h("a", {
                    class: ["tbl-link", "info-link"],
                    on: {
                      click: () => {
                        _this.editAd(params.row)
                      }
                    }
                  }, "修改"),
                  h("a", {
                    class: ["tbl-link", "error-link"],
                    on: {
                      click: () => {
                        _this.deleteAd(params.row);
                      }
                    }
                  }, "删除")
                ]
              );
            }
          }
        ],
        tblData: []
      };
    },
    methods: {
      cancel() {
        setTimeout(() => {
          this.modal2 = false;
          this.search();
        }, 50);
      },
      pageSizeChange(pageSize) {
        this.searchData.pageSize = pageSize;
        this.curPageChange(1);
      },
      curPageChange(curPage) {
        this.searchData.curPage = curPage;
        this.search();
      },
      search() {
        let httpConfig = {
          params: {params: JSON.stringify(this.searchData)},
          _this: this,
          _tblData: "tblData",
          _totalRecord: "totalRecord"
        };
        $http.list("/advertsrv/advertstrategy/list", httpConfig);
      },
      searchStrategy(id) {
        let _this = this;
        $http2.get(`/advertsrv/advertstrategy/${id}`).then(function (resp) {
          _this.formValidate = resp.data;
        });
      },
      //删除策略
      deleteAd(row) {
        let _this = this;
        this.$Modal.confirm({
          content: `确定要删除该广告策略<span style="color:red">${row.id}</span>？`,
          onOk: function () {
            $http2.delete(`advertsrv/advertstrategy/${row.id}`).then(function (resp) {
              _this.$Notice.success({
                title: "删除成功"
              });
              _this.search();
            });
          }
        });
      },
      // 修改策略
      editAd(row) {
        let _this = this;
        $http2.get(`/advertsrv/advertstrategy/${row.id}`).then(function (resp) {
          _this.details = true;
          _this.formValidate = resp.data;
          _this.title = "修改策略";
          _this.flag = true;
          setTimeout(function () {
            _this.modal2 = true;
          }, 400)
        });
      },
      //添加策略
      addAd() {
        this.title = "添加策略";
        this.formValidate = {};
        this.modal2 = true;
        this.flag = false;
        this.details = true;
      },
      //详情策略
      detailAd(row) {
        let _this = this;
        $http2.get(`/advertsrv/advertstrategy/${row.id}`).then(function (resp) {
          _this.formValidate = resp.data;
          _this.flag = true;
          _this.details = false;
          _this.title = "策略-详情";
          setTimeout(function () {
            _this.modal2 = true;
          }, 400)
        });
      }
    },
    mounted() {
      this.advHeight = window.innerHeight - 140;
      this.search();
    }
  };
</script>

<style>

</style>

