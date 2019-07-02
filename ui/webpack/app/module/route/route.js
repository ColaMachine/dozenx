
// 首先定义或者引入路由的组件
// 方法一：直接定义路由组件
/*const goods = { template: '<p>goods</p>' };
const ratings = { template: '<p>ratings</p>' };
const seller = { template: '<p>seller</p>' };*/
// 方法二：import引入路由组件
import Vue from 'vue'
import VueRouter from 'vue-router'
import home from "../../../src/home.vue";
import about from "../../../src/about.vue";
import buttonExample from '../../module/example/general/buttonExample.vue'
Vue.use(VueRouter);
//
import gridExample from '../../module/example/layout/gridExample.vue';
import layoutExample from '../../module/example/layout/layoutExample.vue';
import apiExample from '../../module/example/api/apiExample.vue';
import collapseExample from '../../module/example/datadisplay/collapseExample.vue';
import tabExample from '../../module/example/datadisplay/tabExample.vue';
import menuExample from '../../module/example/navigation/menuExample.vue';
import dropdownExample from '../../module/example/navigation/dropdownExample.vue';
import siteExample from '../../module/example/site/siteExample.vue';
import inputExample from '../../module/example/dataentry/inputExample.vue';
import formExample from '../../module/example/dataentry/formExample.vue';
import appDialogueExample from '../../module/example/datadisplay/appDialogueExample.vue';
import tableExample from '../../module/example/datadisplay/tableExample.vue';
import crouselExample from '../../module/example/datadisplay/crouselExample.vue';//gun dong tupian
import phoneAppExample from '../../module/example/datadisplay/phoneAppExample.vue';
import iconListExample from '../../module/example/navigation/iconListExample.vue';
import sliderTextExample from '../../module/example/datadisplay/sliderTextExample.vue';
import richInputExample from '../../module/example/dataentry/richInputExample.vue';




import appImgListExample from '../../module/example/datadisplay/appImgListExample.vue';
import zwAppBottomBarExample from '../../module/example/datadisplay/zwAppBottomBarExample.vue';
import zwSlidPanelExample from '../../module/example/datadisplay/zwSlidPanelExample.vue';
import zwJumbotronExample from '../../module/example/datadisplay/zwJumbotronExample.vue';
import zhihuijiatingExample from '../smarthome/zhihuijiatingExample.vue';
import wangluoguanli from '../smarthome/wangluoguanli.vue';
import deviceViewExample from '../smarthome/deviceViewExample.vue';
import deviceLockPwdModify from '../smarthome/deviceLockPwdModify.vue';
import deviceLockView from '../smarthome/deviceLockView.vue';
import deviceMockView from '../smarthome/deviceMockView.vue';
import deviceRedView from '../smarthome/deviceRedView.vue';
import deviceWaterView from '../smarthome/deviceWaterView.vue';
import deviceSocketView from '../smarthome/deviceSocketView.vue';
import deviceLockPwdView from '../smarthome/deviceLockPwdView.vue';
import deviceErrorListView from '../smarthome/deviceErrorListView.vue';
import deviceMsgList from '../smarthome/deviceMsgList.vue';

import login from '../smarthome/login.vue';//登录注册页面
import loginSmsStep1 from '../smarthome/loginSmsStep1.vue';//登录输入图片验证码页面
import loginUP from '../smarthome/loginUP.vue';//登录输入图片验证码页面
import loginSmsStep2 from '../smarthome/loginSmsStep2.vue';//登录输入短信验证码页面
import userCenter from '../smarthome/userCenter.vue';
import userInfo from '../../module/phone/userInfo.vue';
import zwNavExample from '../../module/example/navigation/zwNavExample.vue';
import alertExample from '../../module/example/feedback/alertExample.vue';
import phoneMain from '../../module/phone/phoneMain.vue';
import map from '../../module/phone/map.vue';
import placeView from '../../module/phone/placeView.vue';
import goodView from '../../module/phone/goodView.vue';

import shopMain from '../../module/phone/shopMain.vue';//登录注册页面



  import zwGlobalSearchExample from '../../module/example/shop/zwGlobalSearchExample.vue';
   import zwGoodArticalExample from '../../module/example/shop/zwGoodArticalExample.vue';
   import PcGoodView from '../../module/shop/PcGoodView.vue';

   import zwLoginExample from '../../module/example/dataentry/zwLoginExample.vue';//登录注册页面

// 然后定义路由(routes)，components还可以是Vue.extend()创建的
const routes = [
{
        path:"/home",
        component: home
    },
    {
        path: "/about",
        component: about
    },
    // 重定向
    {
        path: '/',
        redirect: '/home'
    },
//example区域
{ path: '/button', component: buttonExample },
{ path: '/layout', component: layoutExample },
{ path: '/grid', component: gridExample },
{ path: '/api', component: apiExample },
{ path: '/collapse', component:collapseExample },
{ path: '/menu', component:menuExample },
{ path: '/dorpdown', component:dropdownExample },
{ path: '/site', component:siteExample },
{ path: '/input', component:inputExample },
{ path: '/tabs', component:tabExample },
{ path: '/form', component:formExample },

{ path: '/crouselExample', component:crouselExample },
{ path: '/richInputExample', component:richInputExample },

{ path: '/appDialougeExample', component:appDialogueExample },
{ path: '/tableExample', component:tableExample },
{ path: '/phoneAppExample', component:phoneAppExample },
{ path: '/iconListExample', component:iconListExample },
{ path: '/sliderTextExample', component:sliderTextExample },
{ path: '/appImgListExample', component:appImgListExample },
{ path: '/zwAppBottomBarExample', component:zwAppBottomBarExample },
{ path: '/zwSlidPanelExample', component:zwSlidPanelExample },
{ path: '/zwJumbotronExample', component:zwJumbotronExample },
{ path: '/zhihuijiatingExample', component:zhihuijiatingExample },
{ path: '/deviceViewExample', component:deviceViewExample },
// 智慧家庭
{ path: '/deviceLockView', component:deviceLockView },//门锁
{ path: '/deviceMockView', component:deviceMockView },//烟感
{ path: '/deviceRedView', component:deviceRedView },//烟感
{ path: '/deviceWaterView', component:deviceWaterView },//烟感

{ path: '/deviceSocketView', component:deviceSocketView },//插座
{ path: '/deviceLockPwdView', component:deviceLockPwdView },//门锁密码列表
{ path: '/deviceLockPwdModify', component:deviceLockPwdModify },//门锁密码修改
{ path: '/deviceErrorListView', component:deviceErrorListView },//门锁警告列表
{ path: '/login', component:login },//登录按钮
{ path: '/loginSmsStep1', component:loginSmsStep1 },//登录图片验证码输入
{ path: '/loginUP', component:loginUP },//登录图片验证码输入
{ path: '/loginSmsStep2', component:loginSmsStep2 },//登录短信验证码输入
{ path: '/alert', component:alertExample },
{ path: '/userCenter', component:userCenter },
{ path: '/deviceMsgList', component:deviceMsgList },
{ path: '/wangluoguanli', component:wangluoguanli },

{ path: '/navbar', component:zwNavExample },
{ path: '/userInfo', component:userInfo },
//web页面展示
{ path: '/zwGlobalSearchExample', component:zwGlobalSearchExample },
{ path: '/zwGoodArticalExample', component:zwGoodArticalExample },
//手机页面展示
{ path: '/phoneMain', component:phoneMain },
{ path: '/placeView', component:placeView },
{ path: '/goodView', component:goodView },
{ path: '/map', component:map },
{ path: '/shopMain', component:shopMain },
{ path: '/PcGoodView', component:PcGoodView },
{ path: '/zwLoginExample', component:zwLoginExample },


];
// 接着创建路由实例
var router = new VueRouter({
  // ES6缩写语法，相当于routes:routes
  routes
});

export default router;
