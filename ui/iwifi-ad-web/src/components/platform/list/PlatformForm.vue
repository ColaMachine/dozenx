<template>
  <div>
    <Form ref="form" :model="formData" :rules="readonly?{}:formRules" :label-width="80">
      <FormItem prop="name" label="公司名称" required>
        <Input v-model="formData.name" placeholder="公司名称" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="businessLicences" label="营业执照" required>
        <Input v-model="formData.businessLicences" placeholder="营业执照" :readonly="readonly"></Input>
      </FormItem>
      <Row>
        <Col span="12">
        <FormItem prop="contacts" label="联系人" required>
          <Input v-model="formData.contacts" placeholder="联系人" :readonly="readonly"></Input>
        </FormItem>
        </Col>
        <Col span="1">&nbsp;</Col>
        <Col span="11">
        <FormItem prop="telephone" label="联系电话" required>
          <Input v-model="formData.telephone" placeholder="联系电话" :readonly="readonly"></Input>
        </FormItem>
        </Col>
      </Row>
      <FormItem prop="platformName" label="平台名称" required>
        <Input v-model="formData.platformName" placeholder="平台名称" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="platformDes" label="平台描述" required>
        <Input v-model="formData.platformDes" placeholder="平台描述" type="textarea"
               :autosize="{minRows: 2,maxRows: 5}" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="platformUrl" label="平台地址">
        <Input v-model="formData.platformUrl" placeholder="平台地址" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="platformParameter" label="平台参数">
        <Input v-model="formData.platformParameter" placeholder="平台参数" type="textarea"
               :autosize="{minRows: 2,maxRows: 5}" :readonly="readonly" :maxlength="2000"></Input>
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

  export default {
    components: {},
    props: ['formData', 'readonly', 'resetFlag'],
    data() {
      return {
        formRules: {
          name: [
            {required: true, message: '请填写公司名称', trigger: 'blur'}
          ],
          businessLicences: [
            {required: true, message: '请填写营业执照', trigger: 'blur'}
          ],
          contacts: [
            {required: true, message: '请填写联系人', trigger: 'blur'}
          ],
          telephone: [
            {required: true, message: '请填写联系电话', trigger: 'blur'}
          ],
          platformName: [
            {required: true, message: '请填写平台名称', trigger: 'blur'}
          ],
          platformDes: [
            {required: true, message: '请填写平台描述', trigger: 'blur'}
          ],
          platformUrl: [
            // {required: true, message: '请填写平台地址', trigger: 'blur'}
          ],
          platformParameter: [
            // {required: true, message: '请填写平台参数', trigger: 'blur'}
          ]
        },
      };
    },
    methods: {
      submit(name) {
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$emit('on-submit');
          }
        });
      },
      resetForm() {
        this.$refs.form.resetFields();
      },
    },
    mounted() {
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
