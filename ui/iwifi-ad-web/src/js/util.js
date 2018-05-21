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
  return 20;
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
