/**
 * http请求可以传递空值【''】,但不能传递undefind和null
 * urlPrama里的参数，不请允许 ‘’，null,undefined
 */
import QueryString from 'querystring';
import axios from 'axios';
import {message} from 'antd';


const NO_BODY = -999;

/*
 *  去除对象里的空【'',undefind,null】,㳀扫描不递归
 *  返回一个新对象
 */
function filterNone(param) {
    if (!param)return param;

    // 仅过滤object对象
    if (typeof param !== 'object')
        return param;

    //form data 不做处理
    if (param.constructor === FormData)
        return param;

    if (param.constructor === Array)
        return param;

    let keys = Object.keys(param).filter(function (k) {
        let val = param[k];
        if (val === 0)
            return true;
        return val;
    });

    let newParam = {};
    keys.forEach((k) => {
        newParam[k] = param[k];
    })

    return newParam;
}
/*
 *  去除对象里的undefined,null,㳀扫描不递归
 *  返回一个新对象
 */
function filterUndefined(param) {
    if (!param)return param;
    // 仅过滤object对象
    if (typeof param !== 'object')
        return param;

    //form data 不做处理
    if (param.constructor === FormData)
        return param;

    if (param.constructor === Array)
        return param;

    let keys = Object.keys(param).filter(function (k) {
        let val = param[k];
        if (val === 0)
            return true;
        return !(val === undefined || val === null);
    });

    let newParam = {};
    for (let k of keys) {
        newParam[k] = param[k];
    }

    return newParam;
}

/**
 *
 * @param reqUrl 请求url
 * @param params 传参数值
 * @returns {string} 过滤后url和参数字符
 */
function reqUrlParam(reqUrl, params) {
    if (params) {
        let reqParam = '';
        // 过滤value=空字符串、null、undefined 键值对
        let configParams = filterNone(params);
        reqParam = QueryString.stringify(configParams);
        reqUrl += (reqParam ? `&${reqParam}` : reqParam);
    }
    return reqUrl;
}

function urlPlusParam(reqUrl, params) {
    if (!params || !reqUrl)
        return reqUrl;

    if (!Object.keys(params).length)
        return reqUrl;
    // 过滤value=空字符串、null、undefined 键值对
    let pms = filterNone(params);
    pms = QueryString.stringify(pms);

    if (!pms)
        return reqUrl;

    if (reqUrl.indexOf('?') !== -1)
        reqUrl += ('&' + pms);
    else
        reqUrl += ('?' + pms);

    return reqUrl;
}

function Logout() {
    sessionStorage.clear();
    window.location.href = '/login';
}

/**
 * 检验access_token是否有效
 * @param respData {Object} 接口返回值
 * @param _this {vm} vue实例对象
 * @returns {boolean} 有效为真
 */
function checkErrorCode(respData) {
    if (respData.code === 'E0000020' || respData.code === '100') {
        console.error('重新登录', respData)
        Logout();
        return false;
    } else if (respData.code != '0') {
        return false;
    }
    return true;
}

// 开启进度条,并把触发按钮的状态设置为loading
function loadingStart({_this, loadingKey}) {

    if (_this.state.hasOwnProperty(loadingKey))
        _this.setState({
            [loadingKey]: true
        });

}
// 开启进度条,并把触发按钮的状态设置为finish,并返回
function loadingFinishOk({_this, loadingKey, okMsg}) {

    if (_this.state.hasOwnProperty(loadingKey))
        _this.setState({
            [loadingKey]: false
        });
    if (okMsg)
        message.success(okMsg);
}
// 开启进度条,并把触发按钮的状态设置为loading
function loadingError({_this, loadingKey, failMsg}, errInfo) {
    if (_this.state.hasOwnProperty(loadingKey))
        _this.setState({
            [loadingKey]: false
        });
    if (errInfo && failMsg) {
        failMsg = failMsg + ':' + errInfo;
    }

    if (failMsg)//success
        message.error(failMsg);
}


/**
 * 封闭功能：自动设置loading状态；成功失败弹窗提示；token失败重定向；
 *          拼接url参数去除'',undefined,null的并转码，自动添加token到?params={token:xxx}
 *          注意，所有url参数包在了params={}
 * @param httpMethod http方法
 * @param url  接口url
 * @param data  当有post, body，data,
 * @param config {this,urlParams,loadingKey,okMsy,failMsg}
 * @param okCb  成功回调
 * @param failCb  失败回调
 * @private
 */
function _ajaxRequest(httpMethod, url, data, config, okCb, failCb, finallyCb) {
    let {urlParams} = config;

    // 开启进度条
    loadingStart(config);
    // debugger
    let reqUrl = urlPlusParam(url, urlParams);


    let promise;
    if (data === NO_BODY) { // get 等无body的请求
        promise = httpMethod(reqUrl);
    } else if (data && data.IS_POST_FORM_REQUEST) { // post form 专用请求
        promise = httpMethod(reqUrl, data.formData);
    } else { // post 等有body的请求
        promise = httpMethod(reqUrl, filterUndefined(data));
    }


    promise.then(function ({data}) {
        if (!checkErrorCode(data)) {
            loadingError(config, data.errorMsg);
            if (typeof failCb === 'function')
                failCb(data);


        } else {
            loadingFinishOk(config);
            if (typeof okCb === 'function')
                okCb(data);
        }
        if (typeof finallyCb === 'function')
            finallyCb(data);
    }).catch(function (err) {
        loadingError(config, err.toString());
        if (typeof failCb === 'function')
            failCb(err);

        if (typeof finallyCb === 'function')
            finallyCb(err);
    });
}


/**
 * http GET 请求接口数据
 * @param url: string 请求url地址 如 /pubsrv/industrys/
 * @param config?: object={} 配置项 如 {
  * params： {name: value},
  * _this: vue.$this,
  * _key: vue.$data.keyName
  * loading: [string] 是否在抬头显示正在加载进度条，如果值为一个控制按键loading的变量名
  * } }
 */
function get(url, config = {}, okCb, failCb, finallyCb) {
    _ajaxRequest(axios.get, url, NO_BODY, config, okCb, failCb, finallyCb);

}

/**
 * http POST 请求接口数据
 * @param url: string 请求目标url
 * @param data?: {} 请求数据对象
 * @param config?: {} 配置项对象
 */
function post(url, data, config = {}, okCb, failCb, finallyCb) {
    _ajaxRequest(axios.post, url, data, config, okCb, failCb, finallyCb);
}

/**
 * http POST Form 上传文件
 * @param url: string 请求目标url
 * @param data?: {} 请求数据对象
 * @param config?: {} 配置项对象
 */
function postForm(url, data, config = {}, okCb, failCb, finallyCb) {
    let formData = new FormData();
    for (let key in data){
        formData.append(key, data[key]);
    }
    let fData = {formData, IS_POST_FORM_REQUEST: true};
    _ajaxRequest(axios.post, url, fData, config, okCb, failCb, finallyCb);
}


/**
 * http PUT 请求接口数据
 * @param url: string 请求目标url
 * @param data?: {} 请求数据对象
 * @param config?: {} 配置项对象
 */
function put(url, data = null, config = {}, okCb, failCb, finallyCb) {
    _ajaxRequest(axios.put, url, data, config, okCb, failCb, finallyCb);
}

/**
 * http DELETE 请求接口数据
 * @param url: string 请求目标url
 * @param config?: {} 配置项对象
 */
function _delete(url, config = {}, okCb, failCb, finallyCb) {
    _ajaxRequest(axios.delete, url, NO_BODY, config, okCb, failCb, finallyCb);
}



export default {
    filterUndefined,// 过滤请求参数中[undefined,null]
    filterNone,// 过滤请求参数中[undefined,'',null]
    get,
    post,
    postForm,
    put,
    delete: _delete,
    checkErrorCode,
    Logout
}
