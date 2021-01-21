import request from '@/utils/request'
import md5 from 'js-md5';
export function loginByUsername(username, password) {
   const data = {
    loginName: username,
    pwd: md5(md5(password)),
    picCaptcha: 5719
  }
  return request({
    url: '/sys/auth/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/sys/auth/info',
    method: 'get',
    params: { token }
  })
}

