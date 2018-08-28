import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/Home.vue';
import Login from '../components/Login.vue';
import ForgetPassword from '../components/ForgetPassword.vue';
import ModifyPassword from '../components/modifyPassword.vue';
import AdPosition from '../components/advertisement/position/App.vue';
import AdStrategy from '../components/advertisement/strategy/App.vue';
import AdRelease from '../components/advertisement/release/PageBody.vue';
import AdMaterial from '../components/advertisement/material/PageBody.vue';
import AdAuditing from '../components/advertisement/auditing/PageBody.vue';
import PlatformList from '../components/platform/list/PageBody.vue';
import DailyReport from '../components/report/daily/PageBody.vue';
import SystemRole from '../components/system/role/PageBody.vue';
import SystemAccount from '../components/system/account/PageBody.vue';
import SystemLogger from '../components/system/logger/PageBody.vue';
//import Parent from '../components/Parent.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      children: [
        //左侧菜单路由请在这里配置
        // {
        //   path: 'forgetPassword',
        //   component: ForgetPassword
        // },
        // {
        //   // 当 /user/:id/posts 匹配成功
        //   // UserPosts 会被渲染在 User 的 <router-view> 中
        //   path: 'posts',
        //   component: UserPosts
        // }
        {
          path: 'ad/position',
          component: AdPosition
        },
        {
          path: 'ad/strategy',
          component: AdStrategy
        },
        {
          path: 'ad/release',
          component: AdRelease
        },
        {
          path: 'ad/material',
          component: AdMaterial
        },
        {
          path: 'ad/auditing',
          component: AdAuditing
        },
        {
          path: 'report/daily',
          component: DailyReport
        }, {
          path: 'platform/list',
          component: PlatformList
        }, {
          path: 'system/role',
          component: SystemRole
        },
        {
          path: 'system/account',
          component: SystemAccount
        },
        {
          path: 'system/logger',
          component: SystemLogger
        },
        {
          path: '/modifyPassword',
          name: 'modifyPassword',
          component: ModifyPassword
        },

      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/forgetPassword',
      name: 'forgetPassword',
      component: ForgetPassword
    },
    //没找到路由，回到首页
    // {
    //   path: '*', redirect: '/'
    // },
    {
      path: '*', beforeEnter: (to, from, next) => {
        window.VueBus.$Notice.open({
          title: `页面不存在的`,
          desc: '回到首页，只能帮你到这儿啦~o(╯□╰)o'
        });
        console.log('路由不存在', to, from);
        next('/');
      }
    }
  ]
})
