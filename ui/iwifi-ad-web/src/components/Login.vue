<template>
  <div>
    <div class="logo-panel">
      <img class="logo" src="../assets/logo.png" height="32"/>
    </div>
    <div class="content-panel">
      <div class="login-form-wrapper">
        <LoginForm :formData="formData" @on-login="login"/>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  import $http from '@/js/http2';
  import {setToken, setSessionObj} from '@/js/util';
  import LoginForm from './LoginForm.vue';
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
        $http.post('/advertsrv/sys/auth/login', {
          loginName: this.formData.userName,
          pwd: md5(this.formData.password),
          picCaptcha: this.formData.code
        }).then((data) => {
          // console.log(data);
          setToken(md5(this.formData.userName));
          setSessionObj('currentUser', data.data);
          this.$router.push({
            name: 'home'
          });
        }).catch(() => {
          window.VueBus.$emit('re-validate-code');
        });

      }
    },
    mounted() {
    }
  };
</script>
<style lang="less" scoped>
  @import '../css/mixins.less';

  .logo-panel {
    height: 90px;
    text-align: center;
    position: relative;
    img.logo {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      margin: auto;
    }
  }

  .content-panel {
    height: 600px;
    position: relative;
    background-image: url("../assets/login-bg.png");
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;
    .login-form-wrapper {
      width: 360px;
      height: 360px;
      border-radius: 6px;
      background-color: #FFF;
      padding: 30px;
      position: absolute;
      top: 120px;
      right: 180px;
      opacity: 0.9;
    }
  }
</style>
