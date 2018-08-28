<template>
  <div>
    <Form ref="releaseForm" :model="formData" :rules="readonly?{}:formRules" :label-width="100">
      <FormItem prop="name" label="策略名称" required>
        <Input v-model="formData.name" placeholder="策略名称" :readonly="readonly" :maxlength="40"></Input>
      </FormItem>
      <FormItem prop="price" label="策略出价" required>
        <Row>
          <Col span="20">
          <InputNumber v-model="formData.price" placeholder="策略出价" :readonly="readonly" style="width:100%;"
                       :max="999.99" :min="1"></InputNumber>
          </Col>
          <Col span="4" style="text-align: right;">
          元/CPM
          </Col>
        </Row>
      </FormItem>
      <!--<Row>-->
      <!--<Col span="12">-->

      <!--</Col>-->
      <!--<Col span="12">-->

      <!--</Col>-->
      <!--</Row>-->
      <FormItem label="投放行业" required>
        <select-industy v-model="formData.industryList" ref="formIndustry" :readonly="readonly"></select-industy>
      </FormItem>
      <FormItem label="投放区域" required>
        <select-location v-model="formData.areaList" ref="formArea" :readonly="readonly"></select-location>
      </FormItem>

      <FormItem prop="effectStartTime" label="开始时间" required>
        <DatePicker v-model="formData.effectStartTime" format="yyyy-MM-dd HH" type="datetime" placeholder="开始时间"
                    style="width: 100%" :readonly="readonly"></DatePicker>
      </FormItem>
      <FormItem prop="effectEndTime" label="结束时间" required>
        <DatePicker v-model="formData.effectEndTime" format="yyyy-MM-dd HH" type="datetime" placeholder="结束时间"
                    style="width: 100%" :readonly="readonly"></DatePicker>
      </FormItem>
        <FormItem prop="advertSpaceIds" label="投放广告位" required>
        <Select v-model="formData.advertSpaceIds" multiple style="height:auto;" :disabled="readonly">
          <Option v-for="item in adPositionList" :value="item.id" :key="item.id">
            {{`${item.code} - ${item.name} - ${item.width}*${item.height}`}}
          </Option>
        </Select>
      </FormItem>
      <FormItem prop="materialId" label="投放素材" required>
        <Select v-model="formData.materialId" :disabled="readonly">
          <Option v-for="item in adMaterialList" :value="item.id" :key="item.id">
            {{item.name}}
          </Option>
        </Select>
      </FormItem>
      <Row v-if="!readonly">
        <Col span="8">&nbsp;</Col>
        <Col span="8">
        <Button type="primary" size="large" @click="submit" long>提交</Button>
        </Col>
        <Col span="8">&nbsp;</Col>
      </Row>
    </Form>
  </div>
</template>
<script type="text/javascript">
  import $http from '@/js/http2';
  import {validateChnEnNumber, validateEnNumber, validateTime} from '@/js/validate';

   import SelectLocation from './SelectLocation.vue';
  import SelectIndusty from './SelectIndustry.vue';
  // import Mock from 'mockjs';

  // Mock.mock(/position\/list/, {
  //   r: 0,
  //   'data|10': [
  //     {
  //       'id|+1': 1,
  //       'name': '广告位@INT'
  //     }
  //   ]
  // });
  // Mock.mock(/material\/list/, {
  //   r: 0,
  //   'data|10': [
  //     {
  //       'id|+1': 1,
  //       'name': '广告素材@INT'
  //     }
  //   ]
  // });
  export default {
    components: {
      SelectLocation,
      SelectIndusty
    },

    props: ['value', 'readonly', 'resetFlag'],
    data() {
      return {
        formData:{
          areaList:[],
          industryList:[]
        },
        formRules: {
          name: [
            {required: true, message: '请填写策略名称', trigger: 'blur'},
            // {validator: validateChn, trigger: 'blur'}
          ],
          price: [
            {min: 1, message: '请填写策略出价', trigger: 'change', type: 'number'},
            // {validator: validateChn, trigger: 'blur'}
          ],
          effectStartTime: [
             {validator: validateTime, trigger: 'blur'}
          ],
          effectEndTime: [
            {validator: validateTime, trigger: 'blur'}
          ],
          advertSpaceIds: [
            {required: true, message: '请选择投放广告位', trigger: 'change', type: 'array', min: 1},
            // {validator: validateChn, trigger: 'blur'}
          ],
          materialId: [
            {required: true, message: '请选择投放素材', trigger: 'change', type: 'number'},
            // {validator: validateChn, trigger: 'blur'}
          ],
        },
        adPositionList: [],
        adMaterialList: [],

      };
    },
    methods: {
      submit(name) {
        let _this = this;
        this.$refs.releaseForm.validate((valid) => {
          if (valid) {
            let st = this.formData.effectStartTime, et = this.formData.effectEndTime;
            if (st.valueOf() > et.valueOf()) {
              this.$Notice.warning({
                title: '开始时间不得大于结束时间'
              });
              return;
            }
             this.sessionStorageGet("locationValue");
            //获取投放行业
            this.industryGet('industry');

            if (_this.formData.industryList == "") {
              _this.$Notice.error({
                title: '请选择投放行业',
              });
              return false;
            }
            if (_this.formData.areaList == "") {
              _this.$Notice.error({
                title: '请选择投放区域',
              });
              return false;
            }

            this.$emit('on-submit', this.formData);
          }
        });
      },
      resetForm() {
        this.$nextTick(() => {
          this.$refs.releaseForm.resetFields();
          this.formData = Object.assign({}, this.value);

          this.getAdAreaList();
          this.getAdIndustryList();

        });
      },
      getAdPositionList() {
        $http.get('/advertsrv/advertspace/all').then((data) => {
          this.adPositionList = data.data;
        });
      },
      getAdAreaList() {
      let _this =this;
      if(this.formData.id){
        $http.get('/advertsrv/advertstrategy/areas/'+this.formData.id).then((data) => {
          _this.formData.areaList= data.data;
        if( data.data.length !=0 ){
          var select =[];
          data.data.forEach(function(element){
            select.push({
              provinceId:element.provinceId,
              provinceName:element.provinceName,
              cityId:element.cityId,
              cityName:element.cityName
            });
          });
          this.sessionStoragePut('locationValue',select);
        }
      });
      }
      },
      getAdIndustryList() {
      let _this =this;
      if(this.formData.id){
        $http.get('/advertsrv/advertstrategy/industrys/'+this.formData.id).then((data) => {
          _this.formData.industryList=data.data;
        if(data.data.length !=0 ){
          var select =[];
          data.data.forEach(function(element){
            select.push({
              industryName:element.industryName,
              industryCode:element.industryCode
            });
          });
          this.sessionStoragePut('industry',select);
        }
      });
      }
      },
      sessionStoragePut(key,value) {
        sessionStorage.removeItem(key);
        sessionStorage.setItem(key,JSON.stringify(value));
      },
      getAdMaterialList() {
        $http.get('/advertsrv/material/auditing/list', {
          params: {
            params: {
              curPage: 1,
              pageSize: 1000,
            }
          }
        }).then((data) => {
          this.adMaterialList = data.data;
        });
      },
      sessionStorageGet(key) {
        var result = JSON.parse(sessionStorage.getItem(key));
        let _this = this;
        _this.formData.areaList = [];
        result.forEach(function (element) {
          _this.formData.areaList.push({
            provinceId: parseFloat(element.provinceId),
            provinceName: element.provinceName,
            cityId: parseFloat(element.cityId),
            cityName: element.cityName
          })
        })
      },

      industryGet(key) {
        var result = JSON.parse(sessionStorage.getItem(key));
        let _this = this;
        _this.formData.industryList = [];
        result.forEach(function (element) {
          _this.formData.industryList.push({
            industryCode: element.industryCode,
            industryName: element.industryName
          })
        })
      }
      // initFormData(id = this.roleId) {
      //   if (!id) {
      //     this.formData = {};
      //
      //   } else {
      //     $http.get(`/advertsrv/sys/auth/role/view/${id}`).then((data) => {
      //       this.formData = data.data;
      //       this.$refs.releaseForm.resetFields();
      //     }).catch((error) => {
      //       this.formData = {};
      //       this.$refs.releaseForm.resetFields();
      //     });
      //   }
      // }
    },
    mounted() {
      this.getAdPositionList();
      this.getAdMaterialList();
    },
    watch: {
      resetFlag(flag) {
        if (flag) {
          this.resetForm();
        }
      }
    }
  };
</script>
<style lang="less">
</style>
