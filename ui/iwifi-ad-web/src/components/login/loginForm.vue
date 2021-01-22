<template>
  <div>
    <Form ref="loginForm" :model="formData" :rules="formRules" class="login-form">
      <FormItem prop="userName">
        <Input v-model="formData.userName" placeholder="请输入用户名" size="large"></Input>
      </FormItem>
      <FormItem prop="password">
        <Input type="password" v-model="formData.password" placeholder="请输入密码" size="large" @on-keyup.enter="login"></Input>
      </FormItem>
      <FormItem prop="code">
        <Row>
          <Col span="14">
          <Input v-model="formData.code" placeholder="请输入验证码" size="large" @on-keyup.enter="login"></Input>
          </Col>
          <Col span="2">
          &nbsp;
          </Col>
          <Col span="8">
          <ValidateCode ref="validateCode" url="/api/mssrv/sys/auth/login/pic/captcha"/>
          </Col>
        </Row>
      </FormItem>
      <FormItem>
        <Button type="primary" size="large" long @click="login" class="login-button">登录</Button>
        <router-link to="forgetPassword">忘记密码</router-link>
      </FormItem>
    </Form>
  </div>
</template>
<script type="text/javascript">
  import axios from 'axios';
  import ValidateCode from './validateCode.vue';
  export default {
    components: {ValidateCode},
    props: {
      formData: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        formRules: {
          userName: [
            {required: true, message: '请填写用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请填写密码', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请填写验证码', trigger: 'blur'},
          ]
        }
      };
    },
    methods: {
      login(name) {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.$emit('on-login');
          }
        });
      },
      validateCode(rule, value, callback) {
        var _this = this;
        if (value != _this.$refs.validateCode.getValidateCode()) {
          callback(new Error('验证码不正确'));
        } else {
          callback();
        }
      }
    },
    mounted() {
    }
  };
</script>
