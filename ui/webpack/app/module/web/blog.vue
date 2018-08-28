<template>
  <zwLayout>
    <zwHeader class="bg-black ">

      <zwRow>
        <zwCol class="pull-left" span=6>
          <zwMenu mode="horizontal" theme="white">
            <zwMenuItem key="mail">
              <zwIcon type="home" />首页
            </zwMenuItem>

          </zwMenu>
        </zwCol>
        <zwCol class="pull-right" span=18>
          <zwMenu mode="horizontal" theme="white">


            <zwSubMenu>
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

            <zwMenuItem >
              <a href="/manage.htm" target="_blank" rel="noopener noreferrer">管理系统</a>
            </zwMenuItem>

             <zwMenuItem >
              <a href="/static/html/vue/vueExample.html#/" target="_blank" rel="noopener noreferrer">组件库</a>
            </zwMenuItem>
             <zwMenuItem >
                  <a href="/static/html/vue/vuePhoneIndex.html#/" target="_blank" rel="noopener noreferrer">手机</a>
                </zwMenuItem>

            <zwMenuItem>
              <zwDropDown v-show="!isLogin">
                <zwButton slot="button" sizeNum="">登录</zwButton>
                <div class="zw-menu bg-white" slot="menu">

                  <iframe style="width:250px;height:400px" src="/static/html/login_iframe.html" />
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
              <zwMenuItem key="setting:1">
                <zwIcon type="user" />修改个人信息</zwMenuItem>
              <zwMenuItem key="setting:1">
                <zwIcon type="sign-out" />退出登录</zwMenuItem>
              <zwMenuItemGroup title="购物">
                <zwMenuItem key="setting:1">我的收藏</zwMenuItem>
                <zwMenuItem key="setting:2">我的购物车</zwMenuItem>
              </zwMenuItemGroup>
              <zwMenuItemGroup title="直播">
                <zwMenuItem key="setting:3">我的收藏</zwMenuItem>
                <zwMenuItem key="setting:4">我的观看记录</zwMenuItem>
              </zwMenuItemGroup>

            </zwSubMenu>

          </zwMenu>

       </zwCol>

      </zwRow>
    </zwHeader>
    <zwLayout class="zw-layout-has-sider ">


      <zwContent>
        <div id="router">
                <zwRow style="margin:12px;">
<zwCol  class="pull-left" span=6>


            <zwPanel canFold=false state="open" style="">
                          <span slot="title" name="title">重大事件</span>
                           <p  slot="body" name="body">

                            <zwSimpleTable :list="list" :param="tableParam"> </zwSimpleTable>

                            </p>

                        </zwPanel>

                         </zwCol>


<zwCol  class="pull-left" span=11>


            <zwPanel canFold=false state="open" style="">
                          <span slot="title" name="title">最新动态</span>
                           <p  slot="body" name="body">

                            <zwSimpleTable :list="list" :param="tableParam"> </zwSimpleTable>

                            </p>

                        </zwPanel>

                         </zwCol>

                         <zwCol  class="pull-left" span=6>


                                     <zwPanel canFold=false state="open" style="">
                                                   <span slot="title" name="title">相册</span>
                                                    <p  slot="body" name="body">

                                                     <zwCrousel :imglist="imglist" width="300" height=150> </zwCrousel>

                                                     </p>

                                                 </zwPanel>

                                                  </zwCol>
                              </zwRow>


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
  import apiPath from '../../component/datadisplay/apiPath.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
  import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';
  import zwInput from '../../component/dataentry/zwInput.vue';

  import zwSimpleTable from '../../component/datadisplay/zwSimpleTable.vue';
  import zwTable from '../../component/datadisplay/zwTable.vue';


import zwCrousel from '../../component/datadisplay/zwCrousel.vue';

  export default {
    components: {
      zwRow, zwCol, zwIcon, zwBox, zwHeader, zwContent, zwFooter, zwLayout, zwSider, zwCollapse, zwPanel, apiPath,
      zwButton, zwMenu, zwMenuItem, zwMenuItemGroup, zwSubMenu, zwDropDown, zwInput, zwFormItem, zwForm,zwTable,zwSimpleTable,zwCrousel
    },
    data() {
      return {
        msg: 'Hello World!',
        isLogin: false,
        loginName: "",

          list:[
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                                     ],

                                     imglist:[
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/1.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/2.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/3.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/4.jpg"},
                                      {titile:"1",subTitle:"2",pic:"http://www.internetke.com/jsEffects/2014052304/images/5.jpg"},

                                     ],
                     tableParam:[
                      {   name : 'titile',width :10,} ,
                      {   name : 'subTitle',width : 10, } ,
                          {   name : 'pic',width : 80, } ,
                     ],
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