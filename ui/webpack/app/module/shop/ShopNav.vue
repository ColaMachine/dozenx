<template>
  <zwRow>
          <zwCol class="pull-left " span=24 style="background-color:white" >

              <zwGlobalSearch></zwGlobalSearch>
            <zwMenu mode="horizontal" class="sub-channel" style="margin-top:30px;padding-left:3%;padding-right:3%"  color="white"  theme="white">


          <div style="float:left" >
              <zwMenuItem key="mail" >
                <a href="/home/index.htm"><zwIcon type="home" />好物</a>
              </zwMenuItem>
          </div >

          <span style="float:right">
              <zwSubMenu  triggerType="move">
                <template slot="title">
                  <zwIcon type="appstore" />百宝箱</template>

                <zwMenuItemGroup title="Item 1">
                  <a href="/static/html/vue/personalBlog.html#/" target="_blank" rel="noopener noreferrer">写文章</a>
                  <zwMenuItem key="setting:2">Option 2</zwMenuItem>
                </zwMenuItemGroup>
                <zwMenuItemGroup title="Item 2">
                  <zwMenuItem key="setting:3">Option 3</zwMenuItem>
                  <zwMenuItem key="setting:4">Option 4</zwMenuItem>
                </zwMenuItemGroup>

              </zwSubMenu>
 <zwMenuItem>
                <a href="/home/static/html/PhoneCalendarView.html" target="_blank" rel="noopener noreferrer">论坛</a>
              </zwMenuItem>
              <zwMenuItem>
                <a href="/home/static/html/PhoneCalendarView.html" target="_blank" rel="noopener noreferrer">日历</a>
              </zwMenuItem>

              <zwMenuItem>
                <a href="/home/static/html/vue/vueExample.html#/" target="_blank" rel="noopener noreferrer">组件库</a>
              </zwMenuItem>
              <zwMenuItem>
                <a href="/home/static/html/vue/shopIndex.html#/shopMain" target="_blank" rel="noopener noreferrer">手机</a>
              </zwMenuItem>

              <zwMenuItem>





          </zwDropDown>

          </zwMenuItem>

           <zwMenuItem v-show="!isLogin">
                <a  @click="goLogin" target="_blank" rel="noopener noreferrer">登录 | 注册</a>
        </zwMenuItem>
          <zwSubMenu  triggerType="move" style="width:20%" v-show="isLogin">
            <template slot="title"> {{ loginUser.nkname || loginUser.username}}</template>
            <zwMenuItem key="setting:personinfo">
              <zwIcon type="user" />修改个人信息</zwMenuItem>
            <zwMenuItem key="setting:1">

              <zwIcon type="sign-out" />
              <a href="#" @click="goLoginOut">退出登录 </a>
            </zwMenuItem>
            <zwMenuItemGroup title="购物">
              <zwMenuItem key="setting:mycollect">我的收藏</zwMenuItem>
              <zwMenuItem key="setting:myshopcar">我的购物车</zwMenuItem>
            </zwMenuItemGroup>

          </zwSubMenu>

  </span >
          </zwMenu>

        </zwCol>

        </zwRow>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
 import zwGlobalSearch from '../../component/dataentry/zwGlobalSearch.vue';
    import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
 import zwBase from '../../component/zwBase.vue';
  //import {PATH,getQueryString,Ajax} from "../../../../src/main/webapp/static/js/zwcommon"

  export default {
  extends:zwBase,

    components: {
    zwBase,zwSubMenu,
      zwRow,
      zwCol,
      zwIcon,
zwGlobalSearch,

      zwMenu,

      zwMenuItem,

      zwMenuItemGroup,
      zwDropDown,


    },
    name: "phoneMain",
    data() {

      return {
      id:getQueryString("id"),
       user: {},
        isLogin: false,
        userName:null,
        loginUser:{}
       }



    },
    created(){
    },
    mounted() {

  var storeUser = getSessionUser();

        if(storeUser && storeUser.id){
         this.isLogin=true;
        this.loginUser= storeUser;
        }

    },
    computed: {

    },
    methods: {

goLoginOut: function() {
        window.location = PATH + "/sys/auth/logout.htm";
        removeSessionUser("user");
      },
      goLogin: function() {
        window.location = PATH + "/sys/auth/login.htm";
      },
    }
  }
</script>
<style scoped>


</style>