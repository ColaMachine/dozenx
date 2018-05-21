<template>

</template>


<script>
// 首先定义或者引入路由的组件
// 方法一：直接定义路由组件
/*const goods = { template: '<p>goods</p>' };
const ratings = { template: '<p>ratings</p>' };
const seller = { template: '<p>seller</p>' };*/
// 方法二：import引入路由组件
import VueRouter from 'vue-router'
import Vue from 'vue'
import buttonExample from '../../module/example/general/buttonExample.vue'

import gridExample from '../../module/example/layout/gridExample.vue';
import layoutExample from '../../module/example/layout/layoutExample.vue';
import apiExample from '../../module/example/api/apiExample.vue';
import collapseExample from '../../module/example/datadisplay/collapseExample.vue';
import tabExample from '../../module/example/datadisplay/tabExample.vue';
import menuExample from '../../module/example/navigation/menuExample.vue';
import dropdownExample from '../../module/example/navigation/dropdownExample.vue';
import siteExample from '../../module/example/site/siteExample.vue';
import inputExample from '../../module/example/dataentry/inputExample.vue';
import appDialogueExample from '../../module/example/datadisplay/appDialogueExample.vue';
import tableExample from '../../module/example/datadisplay/tableExample.vue';
import crouselExample from '../../module/example/datadisplay/crouselExample.vue';
import phoneAppExample from '../../module/example/datadisplay/phoneAppExample.vue';
import iconListExample from '../../module/example/navigation/iconListExample.vue';
import sliderTextExample from '../../module/example/datadisplay/sliderTextExample.vue';

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
import loginInput from '../smarthome/loginInput.vue';//登录输入图片验证码页面
import loginSmsInput from '../smarthome/loginSmsInput.vue';//登录输入短信验证码页面
import userCenter from '../smarthome/userCenter.vue';

import alertExample from '../../module/example/feedback/alertExample.vue';

// 然后定义路由(routes)，components还可以是Vue.extend()创建的
const routes = [
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

{ path: '/deviceLockView', component:deviceLockView },//门锁
{ path: '/deviceMockView', component:deviceMockView },//烟感
{ path: '/deviceRedView', component:deviceRedView },//烟感
{ path: '/deviceWaterView', component:deviceWaterView },//烟感

{ path: '/deviceSocketView', component:deviceSocketView },//插座
{ path: '/deviceLockPwdView', component:deviceLockPwdView },//门锁密码列表
{ path: '/deviceLockPwdModify', component:deviceLockPwdModify },//门锁密码修改
{ path: '/deviceErrorListView', component:deviceErrorListView },//门锁警告列表
{ path: '/login', component:login },//登录按钮
{ path: '/loginInput', component:loginInput },//登录图片验证码输入
{ path: '/loginSmsInput', component:loginSmsInput },//登录短信验证码输入
{ path: '/alert', component:alertExample },
{ path: '/userCenter', component:userCenter },
{ path: '/deviceMsgList', component:deviceMsgList },
{ path: '/wangluoguanli', component:wangluoguanli },
];
// 接着创建路由实例
const router = new VueRouter({
  // ES6缩写语法，相当于routes:routes
  routes
});
// 最后创建vue实例并挂载
const routeApp = new Vue({
  el: '#router',
  router
});
// 或者
/**const app = new Vue({
  router
}).$mount('#app')**/
</script>