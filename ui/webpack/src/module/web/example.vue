<template>
  <zwLayout>
    <zwHeader class="bg-black ">

      <zwRow>
        <zwCol class="pull-left" span=12>
          <zwMenu mode="horizontal" theme="white">
            <zwMenuItem key="mail">
              <zwIcon type="home" />首页
            </zwMenuItem>
            <zwMenuItem key="app" disabled>
              <zwIcon type="appstore" />功能2
            </zwMenuItem>
          </zwMenu>
        </zwCol>
        <zwCol class="pull-right" span=12>
          <zwMenu mode="horizontal" theme="white">
            <zwMenuItem key="mail">
              <zwIcon type="home" />首页
            </zwMenuItem>
            <zwMenuItem key="app" disabled>
              <zwIcon type="appstore" />功能2
            </zwMenuItem>

            <zwSubMenu triggerType="move">
              <template slot="title">
                <zwIcon type="appstore" />功能3</template>

              <zwMenuItemGroup title="Item 1">
                <zwMenuItem key="gn3">功能3</zwMenuItem>
                <zwMenuItem key="xx2">Option 2</zwMenuItem>
              </zwMenuItemGroup>
              <zwMenuItemGroup title="Item 2">
                <zwMenuItem key="xx3">Option 3</zwMenuItem>
                <zwMenuItem key="xx4">Option 4</zwMenuItem>
              </zwMenuItemGroup>

            </zwSubMenu>

            <zwMenuItem key="alipay">
              <a href="https://ant.design" target="_blank" rel="noopener noreferrer">Navigation Four - Link</a>
            </zwMenuItem>

            <zwMenuItem>
              <zwDropDown v-show="!isLogin">
                <zwButton slot="button" sizeNum="">登录</zwButton>
                <div class="zw-menu bg-white" slot="menu">

                  <iframe style="width:250px;height:400px" />
                  <!-- <zwForm >
                                                <zwFormItem >
                                                    <zwInput  clear=true  label="用户名" placeholder="请输入用户名">


                                                    </zwInput>
                                                </zwFormItem>
                                                 <zwFormItem >
                                                    <zwInput icon="password"  clear=true type="password" placeholder="请输入密码"></zwInput>
                                                 </zwFormItem>

                                                   <zwFormItem >
                                                        <zwInput icon="code"   placeholder="请输入验证密码">
                                                            <span  slot="append"> <img style="width:100%" src="" ></img></span>
                                                        </zwInput>
                                                  </zwFormItem>

                                                <zwFormItem><zwButton style="width:100%" type="primary" :loading_delay=5 sizeNum="large">登录</zwButton></zwFormItem>
                                            </zwForm>
-->

                </div>
              </zwDropDown>

            </zwMenuItem>
            <zwSubMenu>
              <template slot="title"> dozenx</template>
              <zwMenuItem key="xggrxx">
                <zwIcon type="user" />修改个人信息</zwMenuItem>
              <zwMenuItem key="tcdl">
                <zwIcon type="sign-out" />退出登录</zwMenuItem>
              <zwMenuItemGroup title="购物">
                <zwMenuItem key="wdsc">我的收藏</zwMenuItem>
                <zwMenuItem key="wdgwc">我的购物车</zwMenuItem>
              </zwMenuItemGroup>
              <zwMenuItemGroup title="直播">
                <zwMenuItem key="wdsc">我的收藏</zwMenuItem>
                <zwMenuItem key="wdgkjl">我的观看记录</zwMenuItem>
              </zwMenuItemGroup>

            </zwSubMenu>

          </zwMenu>

        </zwCol>

      </zwRow>
    </zwHeader>
    <zwLayout class="zw-layout-has-sider ">
      <zwSider>

        <zwMenu mode="inline">
          <zwMenuItem key="mail">
            <zwIcon type="mail" /><a href="#/api">api</a>
          </zwMenuItem>
          <zwMenuItem key="layout">
            <zwIcon type="mail" /><a href="#/layout">layout</a>
          </zwMenuItem>
          <zwMenuItem key="grid" disabled>
            <zwIcon type="appstore" /><a href="#/grid">grid</a>
          </zwMenuItem>
          <zwMenuItem key="输入框" disabled>
            <zwIcon type="appstore" /><a href="#/input">输入框</a>
          </zwMenuItem>
           <zwMenuItem key="登录" disabled>
                      <zwIcon type="appstore" /><a href="#/zwLoginExample">登录框</a>
                    </zwMenuItem>
            <zwMenuItem key="form" disabled>
                      <zwIcon type="appstore" /><a href="#/form">form</a>
                    </zwMenuItem>
            <zwMenuItem key="富文本输入框" disabled>
                      <zwIcon type="appstore" /><a href="#/richInputExample">富文本输入框</a>
                    </zwMenuItem>

          <zwMenuItem key="appstore" disabled>
            <zwIcon type="appstore" /><a href="#/button">button</a>


          </zwMenuItem>
          <zwSubMenu>
            <template slot="title">
              <zwIcon type="search" />data display</template>

            <zwMenuItem key="collapse"><a href="#/collapse">collapse</a></zwMenuItem>
            <zwMenuItem key="Tabs"><a href="#/tabs">Tabs 标签页</a></zwMenuItem>
             <zwMenuItem key="手机端底部弹框"><a href="#/appDialougeExample">手机端底部弹框</a></zwMenuItem>
            <zwMenuItem key="table"><a href="#/tableExample">table</a></zwMenuItem>
                <zwMenuItem key="滚动图片"><a href="#/crouselExample">滚动图片</a></zwMenuItem>
                <zwMenuItem key="app"><a href="#/phoneAppExample">app</a></zwMenuItem>
                <zwMenuItem key="小图标链接"><a href="#/iconListExample">小图标链接</a></zwMenuItem>
                   <zwMenuItem key="滚动文字"><a href="#/sliderTextExample">滚动文字</a></zwMenuItem>
                    <zwMenuItem key="手机动态图片列表"><a href="#/appImgListExample">手机动态图片列表</a></zwMenuItem>
                      <zwMenuItem key="zwAppBottomBarExample"><a href="#/zwAppBottomBarExample">手机底部工具栏</a></zwMenuItem>
                        <zwMenuItem key="zwSlidPanelExample"><a href="#/zwSlidPanelExample">手机可切换面板</a></zwMenuItem>
                          <zwMenuItem key="zwJumbotronExample"><a href="#/zwJumbotronExample">巨幕</a></zwMenuItem>
                           <zwMenuItem key="zhihuijiatingExample"><a href="#/zhihuijiatingExample">智慧家庭</a></zwMenuItem>

                            <zwMenuItem key="lockPwdModify"><a href="#/lockPwdModify">密码设置</a></zwMenuItem>
          </zwSubMenu>

          <zwSubMenu>
            <template slot="title">
              <zwIcon type="search" />Navigation</template>

            <zwMenuItem key="dropdownmenu"><a href="#/dropdownmenu">DropDown 下拉菜单</a></zwMenuItem>
            <zwMenuItem key="menu"><a href="#/menu">menu 导航菜单</a></zwMenuItem>
            <zwMenuItem key="navbar"><a href="#/navbar"> 导航条</a></zwMenuItem>
          </zwSubMenu>

          <zwSubMenu>
                      <template slot="title">
                        <zwIcon type="search" />shop</template>

                      <zwMenuItem key="zwGlobalSearchExample"><a href="#zwGlobalSearchExample"> global search bar</a></zwMenuItem>
  <zwMenuItem key="zwGoodArticleExample"><a href="#zwGoodArticleExample">GoodArticle</a></zwMenuItem>
                    </zwSubMenu>
             <zwSubMenu>
                <template slot="title">
                    <zwIcon type="search" />feedback</template>

                    <zwMenuItem key="alert"><a href="#/alert">alert</a></zwMenuItem>

              </zwSubMenu>
        </zwMenu>

      </zwSider>
      <zwContent>
        <div id="router">

          <!-- 路由出口 -->
          <!-- 路由匹配到的组件将渲染在这里 -->
          <router-view></router-view>
        </div>
      </zwContent>
    </zwLayout>

    <zwFooter>Footer</zwFooter>

  </zwLayout>
</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwLayout from '../../component/layout/zwLayout.vue';
  import zwHeader from '../../component/layout/zwHeader.vue';
  import zwContent from '../../component/layout/zwContent.vue';
  import zwSider from '../../component/layout/zwSider.vue';
  import zwFooter from '../../component/layout/zwFooter.vue';
  import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
  import zwPanel from '../../component/datadisplay/zwPanel.vue';
  //import apiPath from '../../component/datadisplay/apiPath.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
 /* import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';
  import zwInput from '../../component/dataentry/zwInput.vue';*/

  export default {
    components: {
      zwRow, zwCol, zwIcon, zwBox, zwHeader, zwContent, zwFooter, zwLayout, zwSider, zwCollapse, zwPanel, /*apiPath,*/
      zwButton, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwDropDown, /*zwInput, zwFormItem, zwForm*/
    },
    data() {
      return {
        msg: 'Hello World!',
        isLogin: false,
        loginName: "",
      }
    },
    mounted() {
     // $.ajax(PATH + "/userinfo", function(data) {
    //    })
        //判断是否已经登录
    }
  }
</script>
<style scoped>
  #example {
    background: red;
    height: 100vh;
  }
</style>