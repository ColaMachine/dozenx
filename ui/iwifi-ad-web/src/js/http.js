//@deprecated
//！！！！此文件已废弃，请使用http2.js以便统一的拦截器处理。
import axios from 'axios';
import querystring from 'querystring';

const token = sessionStorage.token;

function reqUrlParam(reqUrl, params) {
  if (params) {
    let reqParam = '';
    reqParam = querystring.stringify(params);
    // console.log(reqParam);
    reqUrl += (reqParam ? `&${reqParam}` : reqParam);
  }
  return reqUrl;
}

function get(url, config = {params: null, _this: null, _key: null}, cb) {
  let {params, _this, _key} = config;
  let reqUrl = `${url}?token=${token}`;
  reqUrl = reqUrlParam(reqUrl, params);
  // /advertsrv/partner/?params={%22name%22:%22%E5%B8%83%E4%B8%81%E9%85%92%E5%BA%97%22}
  axios.get(reqUrl).then(function (resp) {
    let respData = resp.data;
    //判断token
    if (respData.r === 0) {
      _this[_key] = respData.data;
      if (cb && typeof cb === 'function') {
        cb(respData, _this);
      }
    }
  })
}

function post(url, data = null, config = {params: null, _this: null, msg: '添加成功'}, cb) {
  //params 传参对象
  //_this vue实例对象
  // msg 添加成功提示信息
  let {params, _this, msg} = config;
  let reqUrl = `${url}?token=${token}`;
  reqUrl = reqUrlParam(reqUrl, params);
  // params= {'id':'2418','name':'广14','length':10,'width':20}
  axios.post(reqUrl, `params=${data}`).then(function (resp) {
    let respData = resp.data;
    //判断token
    //判断回掉
    if (cb && typeof cb === 'function') {
      cb(respData, _this);
    } else {
      if (respData.r === 0) {
        _this.$Notice.success({
          title: msg
        })
      } else {
        msg = respData.msg;
        _this.$Notice.success({
          title: msg
        });
      }
      //   goback(_this);
    }
  }).catch(function (err) {
    _this.$Notice.error({
      title: err
    });
  })
}

function put(url, data = null, config = {params: null, _this: null, msg: '修改成功'}, cb) {
  let {params, _this, msg} = config;
  let reqUrl = `${url}?token=${token}`;
  reqUrl = reqUrlParam(reqUrl, params);
  axios.put(reqUrl, `params=${data}`).then(function (resp) {
    let respData = resp.data;
    //判断token
    //判断回掉
    if (cb && typeof cb === 'function') {
      cb(respData, _this);
    } else {
      if (respData.r === 0) {
        _this.$Notice.success({
          title: msg
        })
      } else {
        msg = respData.msg;
        alert(msg);
        _this.$Notice.success({
          title: msg
        });
      }
    }
  }).catch(function (err) {
    _this.$Notice.error({
      title: err
    });
  })
}

function _delete(url, config = {params: null, _this: null, msg: '删除成功', time: 0}, cb) {
  // params 传参对象
  // _this vue实例对象
  // msg 删除成功提示信息
  // time 删除成功几秒后刷新当前页
  let {params, _this, msg, time} = config;
  let reqUrl = `${url}?token=${token}`;
  reqUrl = reqUrlParam(reqUrl, params);
  axios.delete(reqUrl).then(function (resp) {
    let respData = resp.data;
    //判断token
    //判断回掉
    if (cb && typeof cb === 'function') {
      cb(respData)
    } else {
      if (respData.r === 0) {
        _this.$Notice.success({
          title: msg
        });
        if (time > 0) {
          setTimeout(function () {
            _this.search();
          }, time)
        }
      } else {
        msg = respData.msg;
        alert(msg);
        _this.$Notice.success({
          title: msg
        });
      }
      _this.search();
    }
  }).catch(function (err) {
    _this.$Notice.error({
      title: err
    });
  })
}

function list(url, config = {
  params: null,
  _this: null,
  _tblData: 'tblData',
  _totalRecord: 'totalRecord',
  _size: 'size'
}, format, cb) {
  // params 传参值
  // _this vue 实例对象
  // _tblData table 数据键名
  // _totalRecord 分页总数量的键名
  // format 格式化参数的方法
  let {params, _this, _tblData, _totalRecord, _size} = config;
  let reqUrl = `${url}?token=${token}`;
  reqUrl = reqUrlParam(reqUrl, params);
  axios.get(reqUrl).then(function (resp) {
    let respData = resp.data;
    if (format && typeof format === 'function') {
      _this[_tblData] = respData.data
    } else {
      _this[_tblData] = respData.data;
    }
    _this[_totalRecord] = respData.page.totalCount;
    if (cb && typeof cb === 'function') {
      cb(respData, _this);
    }

  })
}

export default {
  list,
  post,
  _delete,
  put,
  get
}
