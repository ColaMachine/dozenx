<template>
  <zwRow>
          <zwCol class="pull-left " span=24 style="background-color:white" >

              <zwGlobalSearch></zwGlobalSearch>
            <zwMenu mode="horizontal" class="sub-channel" style="padding-left:3%;padding-right:3%"  color="white"  theme="white">


          <span style="float:left" >
              <zwMenuItem key="mail" >
                <zwIcon type="home" />首页
              </zwMenuItem>
          </span >

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
                <a href="/home/static/html/PhoneCalendarView.html" target="_blank" rel="noopener noreferrer">日历</a>
              </zwMenuItem>

              <zwMenuItem>
                <a href="/home/static/html/vue/vueExample.html#/" target="_blank" rel="noopener noreferrer">组件库</a>
              </zwMenuItem>
              <zwMenuItem>
                <a href="/home/static/html/vue/shopIndex.html#/shopMain" target="_blank" rel="noopener noreferrer">手机</a>
              </zwMenuItem>

              <zwMenuItem>

                <zwMenuItem v-show="!isLogin">
                  <a href="javascript:void(0)" @click="goLogin">登录</a>
                </zwMenuItem>

                <!--  <zwDropDown v-show="!isLogin">
                  <zwButton slot="button" sizeNum="">

                  <a href="javascript:void(0)"  @click="goLogin" >登录</a>

                  </zwButton>
                  <div class="zw-menu bg-white" slot="menu">

                    <iframe style="width:250px;height:400px" :src="PATH+'/static/html/login_iframe.html'" />
                   <zwForm >
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
          <zwSubMenu triggerType="move" style="width:20%" v-show="isLogin">
            <template slot="title"> {{user.userName}}</template>
            <zwMenuItem key="setting:personinfo">
              <zwIcon type="user" />修改个人信息</zwMenuItem>
            <zwMenuItem key="setting:1">

              <zwIcon type="sign-out" />
              <a href="#" click="goLoginOut()">退出登录 </a>
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
       }



    },
    created(){
 Ajax.getJSON(PATH+"/goods/view.json?id="+this.id, null, function(result) {
                               if(result.r == AJAX_SUCC && result.data) {

                                   this.goodData =result.data;

                                   var ary =[];
                                   ary.push({url:'',pic:window.getPathValue(this.data.img)});
                                    ary.push({url:'',pic:window.getPathValue(this.data.img1)});
                                     ary.push({url:'',pic:window.getPathValue(this.data.img2)});
                                      ary.push({url:'',pic:window.getPathValue(this.data.img3)});
                                        console.log("imgList",this.imgList)
                                      this.imgList= ary;




        currLink =location.href;
        title = this.data.name;

        imgUrl = "http://www.dapark.top/home/"+this.data.img;
        //alert(document.domain);
        desc = this.data.name;


       Ajax.getJSON(PATH+"/weixin/signatrue",{url:window.location.href},function(sinatrueResult){
          var sinatrueResultData = sinatrueResult.data;
           initWx(sinatrueResultData);


       });

                                    //  console.log("---------------------------")
                                   //   console.log(ary)
                                   //    console.log("wx",wx);
                                     //  alert("wx onMenuShareTimeline1111111"+this.data.name);

                               }
                             }.Apply(this));
    },
    mounted() {

  var storeUser = localStorage.getItem('user');
        if(storeUser){
        this.loginUser=eval("("+storeUser+")");
        this.loginUser.face=PATH+"/"+this.loginUser.face;
            this.isLogin=true;
        }

    },
    computed: {

    },
    methods: {

goLoginOut: function() {
        window.location = PATH + "/sys/auth/logout.htm";
      },
      goLogin: function() {
        window.location = PATH + "/sys/auth/login.htm";
      },
    }
  }
</script>
<style scoped>


</style>