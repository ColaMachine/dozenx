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

import goodView from '../../module/phone/goodView.vue';
import userInfo from '../../module/phone/userInfo.vue';
import shopMain from '../../module/phone/shopMain.vue';
// 然后定义路由(routes)，components还可以是Vue.extend()创建的
const routes = [

//example区域

{ path: '/userInfo',name:'userInfo', component:userInfo },
{ path: '/shopMain',name:'shopMain', component:shopMain },
{ path: '/goodView/:id',name:'goodView',  component:goodView },

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