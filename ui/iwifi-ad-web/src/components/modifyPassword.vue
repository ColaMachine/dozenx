<template>
  <div>
    <div class="page-header">
      <div class="page-title">
        <Breadcrumb>
          <BreadcrumbItem>修改密码</BreadcrumbItem>
        </Breadcrumb>
      </div>
    </div>
      <Form ref="form" :model="formData" :rules="validateRule" :label-width="80" style="width:600px;margin:70px auto;" >
        <FormItem prop="oldPassword" label="旧密码" >
          <Input type="password" v-model="formData.oldPassword" placeholder="旧密码" :maxlength="20"></Input>
        </FormItem>
        <FormItem prop="newPassword" label="新密码" >
          <Input type="password" v-model="formData.newPassword" placeholder="新密码" :maxlength="20" ></Input>
        </FormItem>
        <FormItem prop="confirmPassword" label="确认密码" >
          <Input type="password" v-model="formData.confirmPassword" placeholder="确认密码" :maxlength="20"></Input>
        </FormItem>
        <Row>
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
  import {validatePassword ,validatePassCheck} from "@/js/validate";

  export default {
    components: {
    },
    data() {
      return {
        formData:{
          oldPassword:'',
          newPassword:'',
          confirmPassword:''
        },
        validateRule: {
          oldPassword: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          newPassword: [
            { validator: validatePassword, trigger: 'blur' }
          ],
          confirmPassword: [
            {  validator: validatePassword, trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
      submit(name) {
        if(this.formData.newPassword != this.formData.confirmPassword){
          this.$Notice.error({
            title: "两次密码输入不一致"
          });
          return;
        }
        this.$refs.form.validate((valid) => {
          if (valid) {
            let url = "/advertsrv/sys/auth/user/pwd/update";
            let params ={
              "oldPassword": this.formData.oldPassword,
              "newPassword":this.formData.newPassword
            }
            $http({
              method: 'put',
              url: url,
              data: params
            }).then((data) => {
              this.$Notice.success({
              title: title
            });
          });
          }
        });
      },
    }
  };
</script>

<style>
  .ivu-menu-primary.ivu-menu-horizontal .ivu-menu-item-active,
  .ivu-menu-primary.ivu-menu-horizontal .ivu-menu-submenu-active,
  .ivu-menu-primary.ivu-menu-horizontal .ivu-menu-item:hover, .ivu-menu-primary.ivu-menu-horizontal
  .ivu-menu-submenu:hover{
    background: transparent;
  }
</style>
