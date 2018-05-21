/**
 * Created by Administrator on 2017/9/7.
 */

import QueryString from 'querystring';

function isString(str) {
    return (typeof str == 'string') && str.constructor == String;
}
function isNumber(obj) {
    return (typeof obj == 'number') && obj.constructor == Number;
}
function isObject(obj) {
    return (typeof obj == 'object');
}

function setLocalStorage(key, value) {
    if (!key || !value)
        return;

    try {
        if (isObject(value)) {
            localStorage.setItem(key, JSON.stringify(value));
        } else if (isString(value))
            localStorage.setItem(key, value);
    } catch
        (e) {
        console.error('stanley:', e);
    }
}

function removeLocalStorage(key) {
    if (!key)
        return;

    localStorage.removeItem(key);
}

function getLocalStorage(key) {
    let ret = null;
    try {
        let value = localStorage.getItem(key);
        if (!value)
            return ret;
        ret = JSON.parse(value);
    } catch (e) {
        console.error('stanley:', e);
    }
    return ret;
}

function getUrlParams() {
    var params = window.location.href;
    params = params.split('?');
    if (params.length < 2)
        return null;

    params = params[1];

    if (!params)
        return null;
    return QueryString.parse(params) || null;
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


// 把Date格式的时间转换为字串'1985-02-25'
function formatDate(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
}

// 把Date格式的时间转换为字串'1985-02'
function formatDateYearMonth(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    return y + '-' + m;
}

// 把Date格式的时间转换为字串'1985-02-25 22:15:03'
function formatDateDetail(date) {
    let y = date.toLocaleDateString().replace(/\//g, '-');
    let t = date.toTimeString().substr(0, 8);
    return y + ' ' + t;
}

function detailTimeFromStamp(stamp) {
    if (!stamp)
        return '-';

    if (!(stamp > 315504001000))
        return 'n/a';
    return formatDateDetail(new Date(stamp));
}

function itemTypeOf(obj) {
    const toString = Object.prototype.toString;
    const map = {
        '[object Boolean]': 'boolean',
        '[object Number]': 'number',
        '[object String]': 'string',
        '[object Function]': 'function',
        '[object Array]': 'array',
        '[object Date]': 'date',
        '[object RegExp]': 'regExp',
        '[object Undefined]': 'undefined',
        '[object Null]': 'null',
        '[object Object]': 'object'
    };
    return map[toString.call(obj)];
}

function deepCopy(data) {
    const t = itemTypeOf(data);
    let o;
    if (t === 'array') {
        o = [];
    } else if (t === 'object') {
        o = {};
    } else {
        return data;
    }
    if (t === 'array') {
        for (let i = 0; i < data.length; i++) {
            o.push(deepCopy(data[i]));
        }
    } else if (t === 'object') {
        for (let i in data) {
            o[i] = deepCopy(data[i]);
        }
    }
    return o;
}


function getURLParameter(name) {
    var paramStr = window.location.search || window.location.hash || window.location.href;
    if (paramStr.indexOf('?') < 0) {
        return null;
    }
    paramStr = paramStr.substr(paramStr.indexOf('?') + 1);
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = paramStr.match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

export default {
    getQueryString,
    formatDate,
    formatDateDetail,
    formatDateYearMonth,
    deepCopy,
    itemTypeOf,
    detailTimeFromStamp,
    getURLParameter,
    getUrlParams,
    getLocalStorage,
    setLocalStorage,
    removeLocalStorage
}