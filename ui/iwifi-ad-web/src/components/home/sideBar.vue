<template>
  <div class="side-bar">
    <Menu :active-name="activeMenuItem" theme="dark" width="220" :open-names="openMenus" style="height:100%;"
          @on-select="selectMenuItem" @on-open-change="openMenu" ref="userMenu">
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
  import {setSessionObj, getSessionObj} from '../../js/util';
  import $http from '@/js/http2';
  import menu from '../../data/menu.json';

  let openMenus = getSessionObj('openMenus') || [];
  /**
   * 配置menuList
   */
  export default {
    created(){
      this.getMenuList();
      this.monitorMenuSelect();
    },
    mounted(){
      this.activeMenuItem = getSessionObj('activeMenuItem') || '';
    },
    data() {
      return {
        menuList: [],
        openMenus: openMenus,
        activeMenuItem:''
      };
    },
    methods: {
      selectMenuItem(activeMenuItem) {
        //为了监听全局on-menu-select事件时，需要手动再设置this.activeMenuItem=activeMenuItem
        setTimeout(()=>{
          this.activeMenuItem = activeMenuItem;
        })

        setSessionObj('activeMenuItem', activeMenuItem);
        /**
         * 请先在router/index.js下配置正确的路由(Home路由下面配置)，然后将对应的data/menu.json的id修改为对应的路由即可
         * 这里只支持path的跳转方式
         */
        this.$router.push({path:activeMenuItem});
      },
      openMenu(openMenus) {
        // this.openMenus = openMenus;
        setSessionObj('openMenus', openMenus);
      },
      getMenuList() {
        this.menuList = menu;
        if (!this.$refs.userMenu) {
          return;
        }
        //如果有激活的路由，则显示相应的页面
        if (this.activeMenuItem) {
          this.selectMenuItem(this.activeMenuItem);
        }
      },
      //监听全局事件，更新menu状态
      monitorMenuSelect() {
        window.VueBus.$on('on-menu-select', (path) => {
          this.selectMenuItem(path);
        });
      }
    }
  };
</script>
