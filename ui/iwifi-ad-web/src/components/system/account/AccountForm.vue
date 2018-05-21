<template>
  <div>
    <Form ref="form" :model="formData" :rules="readonly?{}:formRules" :label-width="80">
      <FormItem prop="username" label="账号" required>
        <Input v-model="formData.username" placeholder="账号" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="telno" label="电话号码" required>
        <Input v-model="formData.telno" placeholder="电话号码" :readonly="readonly" :maxlength="11"></Input>
      </FormItem>
      <FormItem prop="roleIds" label="用户角色" required>
        <Select v-model="formData.roleIds" multiple style="height:auto;" :disabled="readonly">
          <Option v-for="item in roleList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>
      <FormItem prop="fullLocation" label="所在地区" required v-if="resetFlag">
        <SelectLocation v-model="formData.fullLocation" :disabled="readonly"></SelectLocation>
      </FormItem>
      <FormItem prop="address" label="详细地址" >
        <Input v-model="formData.address" placeholder="详细地址" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="email" label="邮箱" >
        <Input v-model="formData.email" placeholder="邮箱" :readonly="readonly"></Input>
      </FormItem>
      <FormItem prop="remark" label="备注" >
        <Input v-model="formData.remark" placeholder="备注" :readonly="readonly"></Input>
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
  import SelectLocation from '@/components/SelectLocationSimple.vue';
  import $http from '@/js/http2';
  import {validateMultipleRoles, validateFullLocation, validatePhone} from "@/js/validate";

  export default {
    components: {
      SelectLocation
    },
    props: ['formData', 'readonly', 'resetFlag'],
    data() {
      return {
        roleList: [],
        formRules: {
          username: [
            {required: true, message: '请填写账号', trigger: 'blur'}
          ],
          telno: [
            {required: true, message: '请填写电话号码', trigger: 'blur'},
            {validator: validatePhone, trigger: 'blur'}
          ],
          roleIds: [
            {required: true, type: 'array', min: 1, message: '请选择用户权限', trigger: 'change'},
          ],
          fullLocation: [
            {validator: validateFullLocation, trigger: 'change'}
          ],
          address: [
            {required: true, message: '请填写详细地址', trigger: 'blur'}
          ],
          email: [
            {required: true, type: 'email', message: '请填写正确的邮箱地址', trigger: 'blur'}
          ],
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
      getRoleList() {
        $http.get('/advertsrv/sys/auth/role/list', {
          params: {
            params: {
              curPage: 1,
              pageSize: 1000000
            }
          }
        }).then((data) => {
          this.roleList = data.data;
        });
      }
      // initFormData(id = this.roleId) {
      //   if (!id) {
      //     this.formData = {};
      //
      //   } else {
      //     $http.get(`/advertsrv/sys/auth/role/view/${id}`).then((data) => {
      //       this.formData = data.data;
      //       this.$refs.roleForm.resetFields();
      //     }).catch((error) => {
      //       this.formData = {};
      //       this.$refs.roleForm.resetFields();
      //     });
      //   }
      // }
    },
    mounted() {
      this.getRoleList();
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
