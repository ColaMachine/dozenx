<template>
  <div>
    <Form ref="form" :model="formData" :rules="readonly?{}:formRules" :label-width="80">
      <FormItem prop="account" label="账号" required>
        <Input v-model="formData.account" placeholder="账号" :readonly="readonly" :maxlength="40"></Input>
      </FormItem>
      <FormItem prop="username" label="名称" required>
        <Input v-model="formData.username" placeholder="名称" :readonly="readonly"></Input>
      </FormItem>
       <FormItem prop="code" label="编号" required>
              <Input v-model="formData.code" placeholder="编号" :readonly="readonly"></Input>
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
      <FormItem prop="address" label="详细地址">
        <Input v-model="formData.address" placeholder="详细地址" :readonly="readonly" :maxlength="50"></Input>
      </FormItem>
      <FormItem prop="email" label="邮箱">
        <Input v-model="formData.email" placeholder="邮箱" :readonly="readonly"></Input>
      </FormItem>

       <Row  >
          <Col span="19">
          <FormItem prop="face" label="头像">
            <Input v-model="formData.face" placeholder="头像" :disabled="readonly"></Input>
          </FormItem>
          </Col>
          <Col span="1">&nbsp;</Col>
          <Col span="4" v-if="!readonly">
          <!--<Button type="default" size="large" @click="uploadImg(formData.imgUrl)" long>上传</Button>-->
          <ImgUploader :name="uploaderName" :action="uploadAction" @on-success="uploadSuccess" ></ImgUploader>
          </Col>
        </Row>

      <FormItem prop="remark" label="备注">
        <Input v-model="formData.remark" placeholder="备注" :readonly="readonly" :maxlength="100"></Input>
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
import Vue from "vue";
import ImgUploader from './uploader.vue';
  import SelectLocation from '@/components/SelectLocationSimple.vue';
  import $http from '@/js/http2';
  import {validateMultipleRoles, validateFullLocation, validatePhone} from "@/js/validate";

  export default {
    components: {
      SelectLocation,ImgUploader
    },
    props: ['formData', 'readonly', 'resetFlag'],
    data() {
      return {
      uploaderName:"file",
        roleList: [],
        formRules: {
          account: [
            {required: true, message: '请填写账号', trigger: 'blur'}
          ],
          username: [
            {required: true, message: '请填写用户名', trigger: 'blur'}
          ],
          telno: [
            {required: true, message: '请填写电话号码', trigger: 'blur'},
            {validator: validatePhone, trigger: 'blur'}
          ],
          roleIds: [
            {required: true, type: 'array', min: 1, message: '请选择用户角色', trigger: 'change'},
          ],
          fullLocation: [
            {validator: validateFullLocation, trigger: 'change'}
          ],
          address: [
            // {required: true, message: '请填写详细地址', trigger: 'blur'}
          ],
          email: [
            // {required: true, type: 'email', message: '请填写正确的邮箱地址', trigger: 'blur'}
          ],
        },
      };
    },

     computed:{
          uploadAction(){
            return `/home/pubimage/file/upload?access_token=1&width=100&height=100&relativePath=face`
          }
        },

    methods: {
      submit(name) {
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$emit('on-submit');
          }
        });
      },
       uploadSuccess(response){

              Vue.set(this.formData,"face",response.data)
            },

      resetForm() {
        this.$refs.form.resetFields();
      },
      getRoleList() {
        $http.get('/home/sys/auth/role/drop/list', {
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
