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


//系统管理
import SystemRole from '../components/system/role/PageBody.vue';
import SystemAccount from '../components/system/account/PageBody.vue';
import SystemLogger from '../components/system/logger/PageBody.vue';
import FaceCheckinOut from '../components/kq/FaceCheckinOut/PageBody.vue';
import CheckinOut from '../components/kq/CheckinOut/PageBody.vue';
import CheckinLate from '../components/kq/CheckinLate/PageBody.vue';
//import Parent from '../components/Parent.vue';


// 人脸卡口
import Face from '../components/face/face';
import FaceList from '../components/face/faceList';
// 机构管理
import Organization from '../components/organization/organization';
import LinkVideo from '../components/organization/linkVideo';
import LinkPhoto from '../components/organization/linkPhoto';
// 底库管理
import Library from '../components/library/library'
import LibraryPhoto from '../components/library/libraryList'
// 视频源管理
import Video from '../components/video/video'
// 数据统计
import Capture from '../components/statistics/capture'
import Alarm from '../components/statistics/alarm'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      children: [



       {path: '/face',component: Face},
              {path: '/face/list',component: FaceList},
              {path: '/organization',component: Organization},
              {path: '/organization/linkVideo',component: LinkVideo},
              {path: '/organization/linkPhoto',component: LinkPhoto},
              {path: '/library',component: Library},
              {path: '/library/photo',component: LibraryPhoto},
              {path: '/video',component: Video},
              {path: '/statistics/capture',component: Capture},
              {path: '/statistics/alarm',component: Alarm},
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
          path: 'checkin/FaceCheckinOut/list',//人脸考勤记录
          component: FaceCheckinOut
        },
         {
                  path: 'checkin/checkinOut/list',//人脸考勤记录
                  component: CheckinOut
                },{
                                    path: 'checkin/late/list',//人脸考勤记录
                                    component: CheckinLate
                                  },
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
