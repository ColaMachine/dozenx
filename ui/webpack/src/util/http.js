/**
 * Created by songbo on 2017/8/9.
 */
//import 'whatwg-fetch'
import axios from 'axios'
import notice from './notice'
import querystring from 'querystring';

const TOKEN_FAIL ="E0000020";
let token = sessionStorage.token;

function authorSuccess(token, user) {
    sessionStorage.setItem("token", token);
    sessionStorage.setItem("user", user);
}
function authorGet() {
    return {
        token: sessionStorage.getItem("token"),
        user: sessionStorage.getItem("user")
    };
}
function authorQuite() {
    sessionStorage.clear();
}

//反加true表示请求失败，函数体内控制是否重新登录
function needLogin(code) {
    //token失效重定向
    if (code == TOKEN_FAIL) {
        window.location.hash = "/login";
        authorQuite();
        return true;
    }

    return false;
}

/*
 *  去除对象里的空key,㳀扫描不递归
 *  返回一个新对象
 */
function paramFilter(param) {
    // 仅过滤object对象
    if (typeof param != 'object')
        return param;

    //form data 不做处理
    if (param.constructor === FormData)
        return param;

    if (param.constructor == Array)
        return param;

    let keys = Object.keys(param).filter(function (k) {
        let val = param[k];
        if (val === 0)
            return true;
        return val;
    });

    let newParam = {};
    for (let k of keys) {
        newParam[k] = param[k];
    }

    return newParam;
}

//第一个是axios登录方式注释部分为fetch登录部分，相处理响应值可以看promise
//登录认证不适用 axios
function get(url, config = {_that: null, _key: null}) {
    let {_that, _key, _msgTitle} = config;
    var promise = new Promise(function (resolve, reject) {
        axios({
            url: url,
            method: 'get',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            }
        }).then((response) => {
            resolve(response);
            console.log(response)
            if (!(response.code === '0')) {
                if (_msgTitle)
                    notice("warning", {title: _msgTitle + '失败', description: '服务器故障'});
                return;
            }

            if (_key) {
                _that.setState({
                    [_key]: response.data
                })
            }

        }).catch(function (error) {
            if (_msgTitle)
                notice("error", {title: _msgTitle + '失败', description: '网络故障'});

            console.log('Request failed', error);
        });
    })
    return promise
}

/*
 * post请求，
 * url：需要请求的目录
 * config:{
 data:需要提交的数据，token将来会放到url内;
 _that:发起请求的react组件对象；
 _key:返回数据的state变量名;
 _loading:请求过程中设置动画的变量
 _msgTitle:本次请求的行为标题,无则不提示成功失败
 * }
 *   返回promise异步处理成功后的行为，如退出模态窗，返回上级，
 *   注意：promise中处理数据的需要同步到视图需要单独调用
 */
function post(url = null, config = {data: null, _that: null,}) {
    let {data, _that, _key, _loading, _msgTitle} = config;
    if (!data) {
        console.error('post 无数据')
        return;
    }
    if (!data.hasOwnProperty('token'))
        data.token = sessionStorage.token || '';

    data = paramFilter(data);

    if (_loading)
        _that.setState({[_loading]: true});

    var promise = new Promise(function (resolve, reject) {
        axios.post(url, JSON.stringify(data), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then((response) => {
            if (_loading)
                _that.setState({[_loading]: false});

            let data = response.data;
            if (!(data.code == "0")) {
                _msgTitle ? notice("error", {title: _msgTitle + '失败:', description: data.msg}) : 0;
            } else {
                _msgTitle ? notice("success", {title: _msgTitle + '成功:', description: data.msg}) : 0;
                resolve(response);
                if (_key) {
                    _that.setState({
                        _key: data
                    })
                }
            }
        }).catch(function (err) {
            console.error('$http.post failed', err);
            if (_loading)
                _that.setState({[_loading]: false});

            let title = _msgTitle + '失败';
            notice("error", {title, description: '网络故障'});
        });
    })
    return promise
}

/*
 * post请求获取列表数据，
 * url：需要请求的目录
 * config:{
 data:需要提交的请求参数，token将来会放到url内;
 _that:发起请求的react组件对象；
 _key:返回数据的state变量名;
 _loading:请求过程中设置动画的变量
 _msgTitle:本次请求的行为标题
 * }
 *   format：成功返回的数据在同步到视图前进行格式化的回调
 *   无返回值
 */
function list(url = null, config, format, cb) {
    let {params, _that, _key, _loading, _msgTitle} = config;
    params.token = sessionStorage.token || '';

    //去除空参数
    params = paramFilter(params);

    if (_loading)
        _that.setState({[_loading]: true});

    axios.post(url, JSON.stringify(params), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(({data: jsonData}) => {
        if (_loading)
            _that.setState({[_loading]: false});


        let code = jsonData.code;
        if (needLogin(code))return;

        if (!(code == "0")) {
            //如果失败则清除数据
            if (_key) {
                _that.setState({
                    [_key]: []
                })
            }
            _that.totalRecord = 0;
            _msgTitle && notice("error", {title: _msgTitle + "错误:", description: jsonData.msg});
        } else {
            //_msgTitle && notice("success", {title: _msgTitle + "成功:", description: jsonData.msg});
            if (typeof format == 'function') {
                format(jsonData.list);
            }
            _that.totalRecord = jsonData.total;

            if (_key) {
                _that.setState({
                    [_key]: jsonData.list
                })
            }

            //非标数据通过回调获取
            if (typeof cb == 'function')
                cb(jsonData);
        }
    }).catch(function (error) {
        if (_loading)
            _that.setState({[_loading]: false});

        _msgTitle && notice("error", {title: _msgTitle + "失败", description: '网络故障'});
        console.error('Request failed', error);
    });
}

/*
 * post form请求上传文件
 * url：需要请求的目录
 * config:{
 *      _formData:需要提交的数据，放在body里
 *      _urlParams:需要提交的请求参数，放到url内;
 *      _that:发起请求的react组件对象；
 *      _key:返回数据的state变量名;
 *      _loading:请求过程中设置动画的变量
 *      _msgTitle:本次请求的行为标题,可选
 * }
 * 请求头添加token
 *   成功返回promise
 */
function postForm(url = null, config = {_formData: null, _urlParams: null, _that: null, _msgTitle: '请求'}) {
    let {_formData, _urlParams, _that, _key, _loading, _msgTitle} = config;
    let formdata = new FormData();
    if (_formData) {
        for (let key in _formData) {
            formdata.append(key, _formData[key]);
        }
    }

    let urlParams = Object.assign({}, _urlParams, {token: sessionStorage.token || ''});
    let suffix = querystring.encode({params: JSON.stringify(urlParams)});
    url += "?" + suffix;

    if (_loading)
        _that.setState({[_loading]: true});

    return new Promise(function (resolve, reject) {
        axios({
            url,
            method: 'post',
            data: formdata,
            headers:{token:sessionStorage.token}
        }).then((resp) => {
            console.log('http ok:', resp);

            if (_loading)
                _that.setState({[_loading]: false});

            var jsonData = resp.data;
            if (needLogin(jsonData.code)){

                notice("error", {title: _msgTitle + "错误:", description: jsonData.msg});
            }
            else
                notice("success", {title: _msgTitle + "成功:", description: jsonData.msg});

            resolve(jsonData);
        }).catch(err => {
            console.error('http fail:', err);
            if (_loading)
                _that.setState({[_loading]: false});

            notice("error", {title: _msgTitle + "失败", description: '网络故障'});
        });
    });
}


export default  {
    authorSuccess,
    authorGet,
    authorQuite,
    needLogin,
    get,
    post,
    list,
    postForm,
    paramFilter
}
