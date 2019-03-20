<template>
  <div>
    <h4 class="form-title">考勤平台登录</h4>
    <Form ref="loginForm" :model="formData" :rules="formRules">
      <FormItem prop="userName">
        <Input v-model="formData.userName" placeholder="用户名" size="large"></Input>
      </FormItem>
      <FormItem prop="password">
        <Input type="password" v-model="formData.password" placeholder="密码" size="large"
               @on-keyup.enter="login"></Input>
      </FormItem>
      <FormItem prop="code">
        <Row>
          <Col span="16">
          <Input v-model="formData.code" placeholder="验证码" size="large" @on-keyup.enter="login"></Input>
          </Col>
          <Col span="2">
          &nbsp;
          </Col>
          <Col span="6">
          <ValidateCode ref="validateCode" url="/home/sys/auth/login/pic/captcha"/>
          </Col>
        </Row>
      </FormItem>
      <FormItem>
        <Button type="primary" size="large" long @click="login">登录</Button>
      </FormItem>
      <!--<FormItem style="text-align: center;">-->
        <!--<router-link to="forgetPassword" style="color:#666666">忘记密码</router-link>-->
      <!--</FormItem>-->
    </Form>
  </div>
</template>
<script type="text/javascript">
  import axios from 'axios';
  import ValidateCode from './ValidateCode.vue';

  export default {
    components: {ValidateCode},
    props: {
      formData: {
        type: Object,
        required: true,
//        default(){
//          return {
//            userName: '',
//            password: '',
//            code: '',
//          }
//        }
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
            // {validator: this.validateCode, trigger: 'blur'}
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
<style lang="less">
  body {
    min-width: 600px;
  }

  .form-title {
    color: #073E7C;
    font-size: 14px;
    margin-bottom: 30px;
  }
</style>
