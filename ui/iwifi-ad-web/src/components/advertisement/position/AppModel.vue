<template>
  <div>
               <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                   <FormItem label="广告编码" prop="code">
                       <Input v-model="formValidate.code" :maxlength="4" :disabled ='flag'></Input>
                   </FormItem>
                     <FormItem label="广告名称" prop="name">
                       <Input v-model="formValidate.name" :maxlength="20"></Input>
                   </FormItem>
                    <FormItem label="尺寸" class="sizeContent" >
                        <InputNumber  v-model="formValidate.length" :max="9999" :min="0"></InputNumber > 
                        <Icon type="ios-close-empty" :size="20"></Icon>
                        <InputNumber v-model="formValidate.width" :max="9999" :min="0"></InputNumber>
                   </FormItem>
               </Form>
            <div slot="footer" class="addFooter" v-if="details">
                <Button type="error" size="default"  @click="handleSubmit('formValidate')">提交</Button>
            </div>
  </div>
</template>
<script>
// import $http from '../../../js/http'
import $http from '@/js/http2';
import {validateName,validateId} from "../../../js/validate"
export default {
  props: ['flag','appDate','details'],
  data() {
    return {
      formValidate:this.appDate,
      ruleValidate: {
        code: [
          {required: true, message: "请输入广告名称", trigger: "blur" },
          {validator: validateId, trigger: 'blur'}
          ],
        name: [
          {required: true, message: "请输入广告名称", trigger: "blur" },
          {validator:validateName,trigger:'blur'}
          ]
      }
    };
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let _this = this;
          let formparams= this.formValidate;
          let url = '/advertsrv/advertspace';
          let method = this.flag ? 'put' : 'post';
          let title = `${this.flag ? '修改' : '添加'}广告位成功`;
          $http({
            method: method,
            url: url,
            data: this.formValidate
          }).then((data) => {
            this.$Notice.success({
              title: title
            });
            _this.$emit('on-cancel')
          }).catch(function(err){
            //  _this.$emit('on-cancel')
          });
        }
      });
    },
  }
};
</script>

