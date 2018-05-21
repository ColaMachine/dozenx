<template>
  <div>
    <h1>忘记密码</h1>
  </div>
</template>
<style lang="less">
  /*@import "../css/login.scss";*/
</style>
<script type="text/javascript">
  import axios from 'axios';
  //  import iView from ''
  export default {
    components: {},
    data() {
      return {
        formData: {
          userName: '',
          password: ''
        }
      };
    },
    methods: {
      login(name) {
//        let _this = this;
//        this.$refs[name].validate(valid => {
//          if (valid) {
//            if (_this.formData.user && _this.formData.pass) {
//              if (_this.formData.user == "superadmin" && _this.formData.pass == "123456") {
//                let _this = this;
//                axios.get("/awifi-oauth-server-web/tokenController/authorizationGrant?oauthCode=np-biz_6uxg").then(function (response) {
//                  if (response.status == 200) {
//                    let respData = response.data;
//                    sessionStorage.access_token = respData.data.oauthToken;
//                    _this.$router.push({
//                      path: `/dataStatistics`
//                    })
//                    location.reload()
//                  }
//                }).catch(function (error) {
//                  console.log("error", error);
//                });
//              } else {
//                this.$Notice.error({
//                  title: "用户名或者密码输入错误"
//                });
//              }
//            } else {
//              this.$Notice.error({
//                title: "请输入用户名和密码"
//              });
//            }
//          }
//        });
      }
    },
    mounted() {
    }
  };
</script>
