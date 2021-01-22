<template>
  <div class="total-panel">
      <div class="login-form-wrapper">
        <div class="title">智能分析系统</div>
        <LoginForm :formData="formData" @on-login="login"/>
      </div>
  </div>
</template>
<script type="text/javascript">
  import $http from '@/js/http2';
  import {setToken,setSessionObj} from '@/js/util';
  import LoginForm from './loginForm.vue';
  import md5 from 'js-md5';

  export default {
    components: {
      LoginForm
    },
    data() {
      return {
        formData: {
          userName: '',
          password: '',
          code: ''
        }
      };
    },
    methods: {
      login() {
        $http.post('/api/mssrv/sys/auth/login', {
          loginName: this.formData.userName,
          pwd: md5(this.formData.password),
          picCaptcha: this.formData.code
        }).then((data) => {
          setToken(md5(this.formData.userName));
          setSessionObj('currentUser', data.data);
          setSessionObj('activeMenuItem','/face')
          this.$router.push({path:'/face'});
        }).catch(() => {
          window.VueBus.$emit('re-validate-code');
        });
      }
    }
  };
</script>

<style lang="less">
  @import '../../css/mixins.less';
  @import '../../css/login.less';
</style>
