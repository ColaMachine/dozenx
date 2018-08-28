<template>
  <div>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
      <FormItem label="广告编码" prop="id" v-if="flag">
        <p>{{formValidate.id}}</p>
      </FormItem>
      <FormItem label="广告位" prop="advertSpaceId" required>
        <Select v-model="formValidate.advertSpaceId" clearable style="width:200px">
          <Option v-for="item in id" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>

      <FormItem label="投放行业" required>
        <select-industy v-model="formValidate.industryList" ref="formIndustry"></select-industy>
      </FormItem>
      <FormItem label="投放区域" required>
        <select-location v-model="formValidate.areaList" ref="formArea"></select-location>
      </FormItem>
      <FormItem label="合作方" prop="advertPartnerId" required>
        <Select v-model="formValidate.advertPartnerId" clearable style="width:200px">
          <Option v-for="item in partner" :value="item.id" :key="item.id">{{ item.platformName }}</Option>
        </Select>
      </FormItem>
      <FormItem label="起始时间" required>
        <Row>
          <Col span="12">
          <FormItem prop="effectStartTime">
            <DatePicker type="date" placeholder="开始日期" format="yyyy-MM-dd"
                        v-model="formValidate.effectStartTime"></DatePicker>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem prop="effectEndTime">
            <DatePicker type="date" placeholder="结束日期" format="yyyy-MM-dd"
                        v-model="formValidate.effectEndTime"></DatePicker>
          </FormItem>
          </Col>
        </Row>
      </FormItem>
      <FormItem label="备注">
        <Input v-model="formValidate.remark" placeholder="填写合同信息等" style="width: 400px"></Input>
      </FormItem>
    </Form>
    <div slot="footer" class="addFooter" v-if="details">
      <Button type="error" size="default" @click="handleSubmit('formValidate')">提交</Button>
    </div>
  </div>
</template>
<script>
  import SelectLocation from './SelectLocation.vue'
  import SelectIndusty from './SelectIndustry.vue'
  import $http from '@/js/http2.js'
  import $http2 from '@/js/http.js'
  import DateFormat from '@/js/dateFormat';

  export default {
    props: ['appDate', 'flag', 'details'],
    components: {
      SelectLocation,
      SelectIndusty
    },
    data() {
      return {
        addName: [],
        id: [],
        name: [],
        partner: [],
        formValidate: this.appDate,
        ruleValidate: {
          advertSpaceId: [{required: true, type: 'number', message: "请选择广告位", trigger: "blur"}],
          advertPartnerId: [{required: true, type: 'number',message: "请选择合作方", trigger: "blur"}],
          effectStartTime: [{required: true, type: 'date', message: '请选择开始日期', trigger: 'blur'}],
          effectEndTime: [{required: true, type: 'date', message: '请选择结束日期', trigger: 'blur'}],
        }
      };
    },
    methods: {
      search() {
        //获取所有的广告位
        let _this = this;
        $http.get('/advertsrv/advertspace/all').then(function (resp) {
          _this.id = resp.data;
        })
        //获取合作方
        $http.get("/advertsrv/partner/platform/list").then(function (resp) {
          _this.partner = resp.data;
        })
      },
      handleSubmit(name) {
        let _this = this;
        this.$refs[name].validate(valid => {

          if (valid) {
            //获取投放区域
            _this.sessionStorageGet("locationValue");
            //获取投放行业
            _this.industryGet('industry');
            if (_this.formValidate.industryList == "") {
              _this.$Notice.error({
                title: '请选择投放行业',
              });
              return false;
            }
            if (_this.formValidate.areaList == "") {
              _this.$Notice.error({
                title: '请选择投放区域',
              });
              return false;
            }
            // if (_this.formValidate.advertPartnerId == "") {
            //   _this.$Notice.error({
            //     title: '请输入合作方',
            //   });
            //   return false;
            // }
            if (_this.formValidate.effectStartTime.getTime() > _this.formValidate.effectEndTime.getTime()) {
              _this.$Notice.error({
                title: '结束时间要大于开始时间',
              });
              return false;
            }
            this.addStrategy();
          }
        });
      },
      //添加创建策略
      addStrategy() {
        let _this = this;
        this.formValidate.startTime = DateFormat.format.date(this.formValidate.effectStartTime, 'yyyy-MM-dd');
        this.formValidate.endTime = DateFormat.format.date(this.formValidate.effectEndTime, 'yyyy-MM-dd');
        let url = '/advertsrv/advertstrategy';
        let method = this.flag ? 'put' : 'post';
        let title = `${this.flag ? '修改' : '添加'}广告位成功`;
        $http({
          method: method,
          url: url,
          data: this.formValidate,
        }).then(function (rep) {
          _this.$Notice.success({
            title: title
          });
          _this.$emit("on-cancel");
          _this.$refs["formIndustry"].clearIndustry();
          _this.$refs["formArea"].clearArea();
          sessionStorage.removeItem('locationValue');
          sessionStorage.removeItem('industry');
        }).catch(function () {
          _this.$emit("on-cancel");
          _this.$refs["formIndustry"].clearIndustry();
          _this.$refs["formArea"].clearArea();
          sessionStorage.removeItem('locationValue');
          sessionStorage.removeItem('industry');
        })
      },
      sessionStorageGet(key) {
        var result = JSON.parse(sessionStorage.getItem(key));
        let _this = this;
        _this.formValidate.areaList = [];
        result.forEach(function (element) {
          _this.formValidate.areaList.push({
            provinceId: parseFloat(element.provinceId),
            provinceName: element.provinceName,
            cityId: parseFloat(element.cityId),
            cityName: element.cityName,
            countyId: parseFloat(element.countyId),
            countyName: element.countyName
          })
        })
      },

      industryGet(key) {
        var result = JSON.parse(sessionStorage.getItem(key));
        let _this = this;
        _this.formValidate.industryList = [];
        result.forEach(function (element) {
          _this.formValidate.industryList.push({
            industryCode: element.industryCode,
            industryName: element.industryName
          })
        })
      }
    },
    mounted() {
      this.search();
    }
  };
</script>

