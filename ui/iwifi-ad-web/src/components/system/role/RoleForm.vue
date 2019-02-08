<template>
  <div>
    <Form ref="roleForm" :model="formData" :rules="readonly?{}:formRules" :label-width="80">
      <FormItem prop="code" label="角色编码" required>
        <Input v-model="formData.code" placeholder="角色编码" :readonly="readonly" :maxlength="23"></Input>
      </FormItem>
      <FormItem prop="name" label="角色名称" required>
        <Input v-model="formData.name" placeholder="角色名称" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="remark" label="角色描述">
        <Input v-model="formData.remark" placeholder="角色描述" :readonly="readonly" :maxlength="100"></Input>
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
  import {validateChnEnNumber, validateEnNumber, validateChn} from '@/js/validate';

  export default {
    components: {},
    props: ['formData', 'readonly', 'resetFlag'],
    data() {
      return {
        formRules: {
          code: [
            {required: true, message: '请填写角色编码', trigger: 'blur'},
            {validator: validateEnNumber, trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请填写角色名称', trigger: 'blur'},
            {validator: validateChn, trigger: 'blur'}
          ],
          remark: [
            {validator: validateChnEnNumber, trigger: 'blur'}
            // {required: true, message: '请填写角色描述', trigger: 'blur'}
          ],
        },
      };
    },
    methods: {
      submit(name) {
        this.$refs.roleForm.validate((valid) => {
          if (valid) {
            this.$emit('on-submit');
          }
        });
      },
      resetForm() {
        this.$refs.roleForm.resetFields();
      },
      // initFormData(id = this.roleId) {
      //   if (!id) {
      //     this.formData = {};
      //
      //   } else {
      //     $http.get(`/home/sys/auth/role/view/${id}`).then((data) => {
      //       this.formData = data.data;
      //       this.$refs.roleForm.resetFields();
      //     }).catch((error) => {
      //       this.formData = {};
      //       this.$refs.roleForm.resetFields();
      //     });
      //   }
      // }
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
