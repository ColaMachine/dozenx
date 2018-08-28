/**
 * Created by fdsafdsa on 2018/2/2.
 */
import $http from '@/js/http2';

//set session obj
export function setSessionObj(key, obj) {
  sessionStorage.setItem(key, JSON.stringify(obj));
}

//get session obj
export function getSessionObj(key) {
  return JSON.parse(sessionStorage.getItem(key));
}

//get token
export function getToken() {
  return sessionStorage.accessToken;
}

//set token
export function setToken(token) {
  sessionStorage.accessToken = token;
}

export function validateToken() {
  //没有token，重定向到首页
  let token = getToken();
  if (!token) {
    logout();
  }
}

export function getPageSize() {
  return 15;
}

export function logout() {
  window.VueBus.$emit('on-logout');
}

export function selectMenu(path) {
  window.VueBus.$emit('on-menu-select', path);
}

export function initWorker(path) {
  return new Worker(path);
}
// 把Date格式的时间转换为字串'1985-02-25'
export function formatDate(date) {
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? '0' + m : m;
  var d = date.getDate();
  d = d < 10 ? ('0' + d) : d;
  return y + '-' + m + '-' + d;
};
