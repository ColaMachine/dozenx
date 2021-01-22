<template>
  <!-- <div class="top-bar">
    <Menu mode="horizontal" theme="primary">
      <div class="logo-panel">
        智能分析系统
      </div>
      <div class="menu-panel">
        <Submenu name="userInfo">
          <template slot="title">
            {{currentUser ? currentUser.account:""}}
          </template>
          <MenuItem name="userInfo-password">
            <router-link :to="{ name: 'modifyPassword' }" class="ivu-btn-text ivu-btn-large">修改密码</router-link>
          </MenuItem>
          <MenuItem name="userInfo-logout">
            <Button type="text" @click="logout" size="large">退出登录</Button>
          </MenuItem>
        </Submenu>
      </div>
    </Menu>
  </div> -->
  <Header>
      <div class="title">智能分析系统</div>
          <transition name="slide-up">
              <span @mouseover="menuShow" @mouseout="menuHide" class="member-menu-wrap" :class="{'arrow-down-hover': isHover}">
                  <p>
                    {{ username }}
                    <Icon type="ios-arrow-down" size='20'/>
                  </p>
                  <ul v-show="memberMenu" class="member-menu">
                      <!-- <li><router-link :to="{ name: 'modifyPassword' }">修改密码</router-link></li> -->
                      <li><a @click='logout'>退出系统</a></li>
                  </ul>
              </span>
          </transition>
  </Header>
</template>
<script>
  import $http from '@/js/http2';
  import {setToken, getSessionObj} from "../../js/util";
  export default {
    data() {
      return {
        memberMenu: false,
        isHover: false
      };
    },
    computed:{
      username(){
        return getSessionObj('currentUser').username
      }
    },
    methods: {
      menuShow() {
          this.memberMenu = true;
          this.isHover = true;
      },
      menuHide() {
          this.memberMenu = false;
          this.isHover = false;
      },
      logout() {
        //防止重复注销
        if (this.$router.history.current.name !== 'login') {
          setToken('');
          this.$router.push({
            name: 'login'
          });
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
