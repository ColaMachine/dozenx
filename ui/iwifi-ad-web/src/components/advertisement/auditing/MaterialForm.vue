<template>
  <div>
    <Form ref="form" :model="formData" :rules="readonly?{}:formRules" :label-width="120">
      <FormItem prop="code" label="素材编码" required>
        <Input v-model="formData.code" placeholder="素材编码" :disabled="disabled||readonly"></Input>
      </FormItem>
      <FormItem prop="name" label="素材名称" required>
        <Input v-model="formData.name" placeholder="素材名称" :disabled="disabled||readonly"></Input>
      </FormItem>
      <Row>
        <Col span="12">
        <FormItem prop="width" label="素材宽度" required>
          <InputNumber v-model="formData.width" placeholder="宽" :max="9999" :disabled="disabled||readonly"
                       :min="1" style="width:100%;"></InputNumber>
        </FormItem>
        </Col>
        <Col span="12">
        <FormItem prop="height" label="素材高度" required>
          <InputNumber v-model="formData.height" placeholder="高" :max="9999" :disabled="disabled||readonly"
                       :min="1" style="width:100%;"></InputNumber>
        </FormItem>
        </Col>
      </Row>

      <Row>
      <Col span="19"  v-if="!readonly">
      <FormItem prop="imgUrl" label="素材地址">
        <Input v-model="formData.imgUrl" placeholder="素材地址" :disabled="disabled||readonly"></Input>
      </FormItem>
      </Col>
        <Col span="24"  v-if="readonly">
        <FormItem prop="imgUrl" label="素材地址">
          <Input v-model="formData.imgUrl" placeholder="素材地址" :disabled="disabled||readonly"></Input>
        </FormItem>
        </Col>
        <Col span="1" v-if="!readonly">&nbsp;</Col>
        <Col span="4" v-if="!readonly">
          <Button type="default" size="large" @click="jumpTo(formData.imgUrl)" long>查看</Button>
        </Col>
      </Row>
      <Row>
      <Col span="19" v-if="!readonly">
      <FormItem prop="jumpUrl" label="跳转地址">
        <Input v-model="formData.jumpUrl" placeholder="跳转地址" :disabled="disabled||readonly"></Input>
      </FormItem>
        </Col>
        <Col span="24" v-if="readonly">
        <FormItem prop="jumpUrl" label="跳转地址">
          <Input v-model="formData.jumpUrl" placeholder="跳转地址" :disabled="disabled||readonly"></Input>
        </FormItem>
        </Col>
        <Col span="1" v-if="!readonly">&nbsp;</Col>
        <Col span="4" v-if="!readonly">
        <Button type="default" size="large" @click="jumpTo(formData.jumpUrl)" long>查看</Button>
        </Col>
      </Row>
      <FormItem prop="auditRemark" label="备注">
        <Input type="textarea" v-model="formData.auditRemark" :autosize="{minRows: 2,maxRows: 4}" placeholder="备注" :maxlength="100" :disabled="remarkreadonly"></Input>
      </FormItem>
      <FormItem prop="cnzzUrl" label="cnzz检测地址">
        <Input v-model="formData.cnzzUrl" placeholder="cnzz检测地址" :disabled="remarkreadonly"></Input>
      </FormItem>
      <Row v-if="!remarkreadonly && isAudit ">
        <Col span="6">&nbsp;</Col>
        <Col span="4">
        <Button type="primary" size="large" @click="submit(1)" long>通过</Button>
        </Col>
        <Col span="2">&nbsp;</Col>
        <Col span="4">
        <Button type="default" size="large" @click="submit(9)" long>不通过</Button>
        </Col>
      </Row>
      <Row v-if="!remarkreadonly && !isAudit">
        <Col span="6">&nbsp;</Col>
        <Col span="4">
        <Button type="primary" size="large" @click="submit()" long>提交</Button>
        </Col>
      </Row>
    </Form>
  </div>
</template>
<script type="text/javascript">
  import $http from '@/js/http2';
  import {validateMultipleRoles, validateFullLocation, validatePhone,validateCnzzUrl} from "@/js/validate";

  export default {
    components: {
    },
    props: ['value', 'readonly','remarkreadonly', 'resetFlag','isAudit','disabled'],
    data() {
      return {
        formData: this.value,
        typeList: [{
          id: '图片素材',
          label: '图片素材'
        }],
        formRules: {
          cnzzUrl: [
          ],
        },
      };
    },
    methods: {
      submit(auditStatus) {
        if(auditStatus){
          this.formData.auditStatus = auditStatus;
        }
        if(auditStatus == 1 ){
          this.formRules.cnzzUrl.push({required: true, type: 'url', message: '请填写准确的cnzz检测地址', trigger: 'blur'});
        }
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$emit('on-submit',this.formData);
          }
        });
      },
      jumpTo(url){
        window.open(url);
      },
      resetForm() {
        this.$nextTick(() => {
          this.$refs.form.resetFields();
          this.formData = Object.assign({}, this.value);
        });
      }
    },
    mounted() {
    },
    watch: {
      resetFlag(flag) {
        if (flag) {
          this.resetForm();
        }
      },
    }
  };
</script>
<style lang="less">
  .ivu-select-multiple {
    .ivu-select-selection {
      height: auto !important;
    }
  }
</style>
