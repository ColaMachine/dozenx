<template>
  <div>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
      <FormItem label="广告编码" prop="code" required>
        <Input v-model="formValidate.code" :maxlength="4" :disabled="formValidate.id" :readonly="readonly"></Input>
      </FormItem>
      <FormItem label="广告名称" prop="name" required>
        <Input v-model="formValidate.name" :maxlength="20" :readonly="readonly"></Input>
      </FormItem>
      <Row>
        <Col span="12">
        <FormItem label="宽度" prop="width" required>
          <InputNumber v-model="formValidate.width" :max="9999" :min="1" :readonly="readonly"
                       style="width:100%"></InputNumber>
        </FormItem>
        </Col>
        <Col span="12">
        <FormItem label="高度" prop="height" required>
          <InputNumber v-model="formValidate.height" :max="9999" :min="1" :readonly="readonly"
                       style="width:100%"></InputNumber>
        </FormItem>
        </Col>
      </Row>
      <!--<FormItem label="尺寸" prop="height" >-->
      <!--<InputNumber v-model="formValidate.height" :max="9999" :min="0" :readonly="readonly"></InputNumber>-->
      <!--<Icon type="ios-close-empty" :size="20"></Icon>-->
      <!--<InputNumber v-model="formValidate.width" :max="9999" :min="0" :readonly="readonly"></InputNumber>-->
      <!--</FormItem>-->
    </Form>
    <div slot="footer" class="addFooter" v-if="details">
      <Button type="error" size="default" @click="handleSubmit('formValidate')">提交</Button>
    </div>
  </div>
</template>
<script>
  // import $http from '../../../js/http'
  import $http from '@/js/http2';
  import {validateName, validateId} from "../../../js/validate"

  export default {
    props: ['readonly', 'appDate', 'details'],
    data() {
      return {
        formValidate: this.appDate,
        ruleValidate: {
          code: [
            {required: true, message: "请输入广告编码", trigger: "blur"},
            {validator: validateId, trigger: 'blur'}
          ],
          name: [
            {required: true, message: "请输入广告名称", trigger: "blur"},
            {validator: validateName, trigger: 'blur'}
          ],
          width: [
            {min: 1, message: '请填写广告宽度', type: 'number', trigger: 'change'},
          ],
          height: [
            {min: 1, message: '请填写广告高度', type: 'number', trigger: 'change'},
          ],
        }
      };
    },
    methods: {
      handleSubmit(name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            let _this = this;
            let formparams = this.formValidate;
            let url = '/advertsrv/advertspace';
            let method = this.formValidate.id ? 'put' : 'post';
            let title = `${this.formValidate.id ? '修改' : '添加'}广告位成功`;
            $http({
              method: method,
              url: url,
              data: this.formValidate
            }).then((data) => {
              this.$Notice.success({
                title: title
              });
              _this.$emit('on-cancel')
            }).catch(function (err) {
              //  _this.$emit('on-cancel')
            });
          }
        });
      },
    }
  };
</script>

