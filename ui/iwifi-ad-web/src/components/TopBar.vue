<template>
  <div class="top-bar">
    <Menu mode="horizontal" theme="primary">
      <div class="logo-panel">
        爱WiFi人脸考勤平台
      </div>
      <div class="menu-panel">
        <Submenu name="userInfo">
          <template slot="title">
            <!--<Icon type="stats-bars"></Icon>-->
            {{currentUser.account}}
          </template>
          <MenuItem name="userInfo-password">
            <!--<router-link :to="{ name: 'login' }">退出登录</router-link>-->
            <router-link :to="{ name: 'modifyPassword' }" class="ivu-btn-text ivu-btn-large">修改密码</router-link>
          </MenuItem>
          <MenuItem name="userInfo-logout">
            <!--<router-link :to="{ name: 'login' }">退出登录</router-link>-->
            <Button type="text" @click="logout" size="large">退出登录</Button>
          </MenuItem>
          <!--<MenuItem name="userInfo-modify">用户信息</MenuItem>-->
        </Submenu>
      </div>
    </Menu>
  </div>
</template>
<script>
  import $http from '@/js/http2';
  import {setToken, getSessionObj} from "../js/util";

  export default {
    data() {
      return {
        currentUser: getSessionObj('currentUser')

      };
    },
    methods: {
      logout() {
        //防止重复注销
        if (this.$router.history.current.name !== 'login') {
          //$http.get('/admin/index/logout').then(() => {
          setToken('');
          this.$router.push({
            name: 'login'
          });
          //});
        }
      }
    },
    created() {
      window.VueBus.$on('on-logout', () => {
        this.logout();
      });
    }
  };
</script>
