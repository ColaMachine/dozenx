import axios from 'axios';
import {getToken, logout} from './util';

var loadingTimeout;
var loadingDelay = 1000;
var $http = axios.create({
  //make sure the params object exist
  params: {},
  timeout: 10000
});
//request interceptor
$http.interceptors.request.use(function (config) {
  showLoadingMask();
  // Add access_token to config
  config.params.access_token = getToken();
  return config;
}, function (error) {
  hideLoadingMask();
  console.log('$http error3, request cancelled', error);
  window.VueBus.$Notice.error({
    title: error.response.status,
    desc: '未知错误'
  });
  return Promise.reject(error);
});
//response interceptor
$http.interceptors.response.use(function (response) {
  hideLoadingMask();
  // Do something with response data
  let data = response.data;
  if (data.code == 0 || data.r == 0) {
    //if ok, return the data directly
    return response.data;
  } else {
    if (data.r == 504) {
      logout();
    } else {
      //backend error
      console.log('$http error1, backend normal json error');
      window.VueBus.$Notice.error({
        title: data.code || data.r,
        desc: data.msg || '后台错误'
      });
    }
    //将错误包装成正常的网络错误，以便显式的catch处理
    return Promise.reject({response});
  }
}, function (error) {
  hideLoadingMask();
  console.log('$http error2, request or backend exception error', error);
  if (error.response) {
    window.VueBus.$Notice.error({
      title: error.response.status || '',
      desc: error.response.statusText || '未知错误'
    });
  } else {
    window.VueBus.$Notice.error({
      title: error.code || '',
      desc: error.message || '未知错误'
    });
  }
  return Promise.reject(error);
});

function showLoadingMask() {
  // console.log('showLoadingMask', loadingTimeout);
  if (loadingTimeout) {
    clearTimeout(loadingTimeout);
  }
  loadingTimeout = setTimeout(() => {
    window.VueBus.$Spin.show();
  }, loadingDelay);

  // window.VueBus.$Loading.start();
}

function hideLoadingMask() {
  // console.log('hideLoadingMask', loadingTimeout);
  if (loadingTimeout) {
    clearTimeout(loadingTimeout);
  }
  window.VueBus.$Spin.hide();
  // window.VueBus.$Loading.finish();
}

export default $http;
