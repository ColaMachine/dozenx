// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'babel-polyfill';
import Vue from 'vue';
import App from './App.vue';
import router from './router/index';
import iView from 'iview';
import './css/index.less';

Vue.config.productionTip = false;
Vue.use(iView);

/* eslint-disable no-new */
//新建全局Vue对象，用作Vue的全局事件传递或者iVue全局方法调用，如window.VueBus.$notice.error
window.VueBus = new Vue();
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  beforeCreate() {
  }
});

