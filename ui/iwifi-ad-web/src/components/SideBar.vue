<template>
  <div class="side-bar" style="height:100%">
    <Menu :active-name="activeMenuItem" theme="light" width="auto" :open-names="openMenus" style="height:100%;"
          @on-select="selectMenuItem" @on-open-change="openMenu" ref="userMenu">
      <!--<Submenu name="1">-->
      <!--<template slot="title">-->
      <!--<Icon type="ios-navigate"></Icon>-->
      <!--Item 1-->
      <!--</template>-->
      <!--<MenuItem name="1-1">Option 1</MenuItem>-->
      <!--<MenuItem name="1-2">Option 2</MenuItem>-->
      <!--<MenuItem name="1-3">Option 3</MenuItem>-->
      <!--</Submenu>-->
      <!--<Submenu name="2">-->
      <!--<template slot="title">-->
      <!--<Icon type="ios-keypad"></Icon>-->
      <!--Item 2-->
      <!--</template>-->
      <!--<MenuItem name="2-1">Option 1</MenuItem>-->
      <!--<MenuItem name="2-2">Option 2</MenuItem>-->
      <!--</Submenu>-->
      <!--<Submenu name="3">-->
      <!--<template slot="title">-->
      <!--<Icon type="ios-analytics"></Icon>-->
      <!--Item 3-->
      <!--</template>-->
      <!--<MenuItem name="3-1">Option 1</MenuItem>-->
      <!--<MenuItem name="3-2">Option 2</MenuItem>-->
      <!--</Submenu>-->
      <template v-for="menu in menuList">
        <!--如果有children对象，则生成Submenu -->
        <Submenu :name="menu.url" :key="menu.url" class="side-bar-menu" v-if="menu.childs">
          <template slot="title">
            <span :class="['icon', menu.icon]">{{menu.name}}</span>
          </template>
          <MenuItem v-for="menuItem in menu.childs" :name="menuItem.url" :key="menuItem.url"
                    class="side-bar-menu-item">
            {{menuItem.name}}
          </MenuItem>
        </Submenu>
        <!--没有children对象，则生成MenuItem -->
        <MenuItem v-else :name="menu.url" :key="menu.url" class="side-bar-menu-item">
          {{menu.name}}
        </MenuItem>
      </template>
    </Menu>
  </div>
</template>
<script>
  import {setSessionObj, getSessionObj} from '../js/util';
  import $http from '@/js/http2';

  let activeMenuItem = getSessionObj('activeMenuItem') || '';
  let openMenus = getSessionObj('openMenus') || [];
  console.log(activeMenuItem, openMenus);
  /**
   * 配置menuList
   */
  export default {
    data() {
      return {
        menuList: [],
        openMenus: openMenus,
        activeMenuItem: activeMenuItem
      };
    },
    methods: {
      selectMenuItem(activeMenuItem) {
        //为了监听全局on-menu-select事件时，需要手动再设置this.activeMenuItem=activeMenuItem
        this.activeMenuItem = activeMenuItem;
        setSessionObj('activeMenuItem', activeMenuItem);
        /**
         * 请先在router/index.js下配置正确的路由(Home路由下面配置)，然后将对应的data/menu.json的id修改为对应的路由即可
         * 这里只支持path的跳转方式
         */
        this.$router.push(activeMenuItem);
      },
      openMenu(openMenus) {
        // this.openMenus = openMenus;
        setSessionObj('openMenus', openMenus);
      },
      getMenuList() {
        $http.get('/advertsrv/sys/auth/menu/tree/my').then((data) => {
          this.menuList = data.data;
          //bug fix
          //!!!!防止死循环调用
          //在mounted的时候由于发送了ajax请求，导致了如果在home组件中token验证失败，重定向至首页。
          // 而此时ajax返回，本回调函数被调用，在selectMenuItem方法中又会将路由跳转至对应菜单页面
          //这样就形成了死循环
          if (!this.$refs.userMenu) {
            return;
          }
          //菜单数据被更新，需要手动恢复菜单打开状态
          this.recoverMenuStatus();
          //如果有激活的路由，则显示相应的页面
          if (this.activeMenuItem) {
            this.selectMenuItem(this.activeMenuItem);
          }
        });
      },
      recoverMenuStatus() {
        //一定要在nextTick里面调用
        this.$nextTick(() => {
          this.$refs.userMenu.updateOpened();
          this.$refs.userMenu.updateActiveName();
        });
      },
      //监听全局事件，更新menu状态
      monitorMenuSelect() {
        window.VueBus.$on('on-menu-select', (path) => {
          this.selectMenuItem(path);
        });
      }
    },
    mounted() {
      this.getMenuList();
      this.monitorMenuSelect();
    }
  };
</script>
<!--<style lang="less">-->
<!--@import '../css/theme.less';-->
<!--.side-bar {-->
<!--.ivu-menu-submenu-title {-->
<!--border-bottom: 1px solid #DDD;-->
<!--border-left: 1px solid #DDD;-->
<!--color: #999;-->
<!--}-->
<!--.side-bar-menu-item {-->
<!--border-bottom: 1px solid #DDD;-->
<!--border-left: 1px solid #DDD;-->
<!--font-size: 14px;-->
<!--}-->
<!--.ivu-menu-item-active:not(.ivu-menu-submenu) {-->
<!--background-color: rgba(238, 77, 38, 0.1);-->
<!--border-bottom: 1px solid;-->
<!--border-left: 1px solid;-->
<!--border-top: 1px solid;-->
<!--color: @primary-color;-->
<!--}-->
<!--}-->

<!--</style>-->
