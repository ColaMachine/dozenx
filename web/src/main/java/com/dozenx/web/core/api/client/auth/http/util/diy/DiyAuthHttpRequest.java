package com.dozenx.web.core.api.client.auth.http.util.diy;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.exception.ValidException;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.MD5Util;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.api.client.auth.http.bean.HttpResult;
import com.dozenx.web.core.api.client.auth.http.bean.TokenHttpResult;
import com.dozenx.web.core.api.client.auth.http.util.HttpUtil;
import com.dozenx.web.core.api.client.auth.http.util.TokenRequestResult;
import com.dozenx.web.core.cache.service.RedisService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.ConfigUtil;

import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:47 2018/12/7
 * @Modified By:
 */
public abstract class DiyAuthHttpRequest {


    /**
     * {"data":{"expiresIn":959,"loseTimestamp":1571119979206,"oauthTimestamp":1571118179206,"oauthToken":"5da55c63bfaed204741c80ed"},"state":"success"}
     *
     * @param url
     * @param params
     * @return
     */
    public Map<String, Object> sendGetRequest(String url, String params) {
        //1.获取token 并附加在token上面 先从redis 获取 没有
        String priURL = urlAddAccessToken(url);//请求参数添加access_token
        //在去发送请求获取
        HttpResult httpResult = HttpUtil.get(priURL, params);//发送get请求
        //2.分析请求返回结果（第一次）
        TokenHttpResult tokenHttpResult = analysisPriResult(httpResult, priURL, params);//分析第一次发送请求结果
        if (tokenHttpResult.getCode() == 403) {//状态码为403，代表access_token已经失效
            //3.发送请求(第二次)
            String secURL = urlResetAccessToken(url);//请求参数重置access_token
            httpResult = HttpUtil.get(secURL, params);//发送get请求
            //4.分析请求返回结果（第二次）
            tokenHttpResult = analysisPriResult(httpResult, secURL, params);//分析第一次发送请求结果
        }
        //5.分析接口返回值，并转成map
        return analysisResultToMap(url, params, tokenHttpResult.getResult());
    }

    public Map<String, Object> sendPostRequest(String url, String params) {
        //1.发送请求(第一次)
        String priURL = urlAddAccessToken(url);//请求参数添加access_token
        HttpResult httpResult = HttpUtil.postBody(priURL, params);//发送get请求
        //2.分析请求返回结果（第一次）
        TokenHttpResult tokenHttpResult = analysisPriResult(httpResult, priURL, params);//分析第一次发送请求结果
        if (tokenHttpResult.getCode() == 403) {//状态码为403，代表access_token已经失效
            //3.发送请求(第二次)
            String secURL = urlResetAccessToken(url);//请求参数重置access_token
            httpResult = HttpUtil.get(secURL, params);//发送get请求
            //4.分析请求返回结果（第二次）
            tokenHttpResult = analysisPriResult(httpResult, secURL, params);//分析第一次发送请求结果
        }
        //5.分析接口返回值，并转成map
        return analysisResultToMap(url, params, tokenHttpResult.getResult());
    }

    /**
     * 分析接口返回值，并转成map
     *
     * @param interfaceUrl    url
     * @param interfaceParam  参数
     * @param interfaceResult 接口返回值
     * @return map
     * @author 许小满
     * @date 2017年9月4日 下午6:46:44
     */
    private Map<String, Object> analysisResultToMap(String interfaceUrl, String interfaceParam, String interfaceResult) {
        if (StringUtil.isBlank(interfaceResult)) {
            throw new InterfaceException("E046401201", ErrorMessage.getErrorMsg("E2000009"), interfaceUrl, interfaceParam);//接口无返回值!
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(interfaceResult, Map.class);//将字符串转成json
        if (resultMap == null || resultMap.isEmpty()) {//未得到结果
            throw new InterfaceException("E046401201", ErrorMessage.getErrorMsg("E2000009"), interfaceUrl, interfaceParam);//接口返回值转map后为空！
        }

//        String resultCode = (String) resultMap.get("code");
//        if (StringUtil.isBlank(resultCode) || !resultCode.equals("0")) {
//            throw new InterfaceException((String) resultMap.get("message"), interfaceUrl, interfaceParam, interfaceResult);
//        }
//
        return getResultMap(resultMap);
    }

    public Map<String,Object> simplegetResultMap(Map<String, Object> resultMap){

        String resultCode = (String) resultMap.get("code");
        if (StringUtil.isBlank(resultCode) || !resultCode.equals("0")) {
            throw new InterfaceException((String) resultMap.get("message"), "结果分析报错");
        }

        return resultMap;
    }

    public abstract Map<String,Object> getResultMap(Map<String, Object> resultMap);
    /**
     * 请求url添加access_token
     *
     * @param url 接口url
     * @return 参数
     * @author 许小满
     * @date 2017年9月5日 下午7:12:23
     */
    public String urlAddAccessToken(String url) {
        if (StringUtil.isBlank(url)) {
            throw new ValidException("E0000002", ErrorMessage.getErrorMsg("E0000002", "接口地址"));//{0}不允许为空!
        }
        StringBuilder newURL = new StringBuilder(url);//生成的新的url，自动拼接access_token
        if (url.indexOf("?") == -1) {//不包含?时
            newURL.append("?").append("access_token=").append(getAccessToken());
        } else {//包含?时
            newURL.append("&").append("access_token=").append(getAccessToken());
        }
        return newURL.toString();
    }


    /**生成数据中心access_token服务层*/
//    private static TokenService tokenService;

    /**
     * 获取tokenService实例
     * @return tokenService
     * @author 周颖
     * @date 2017年1月18日 下午8:29:56
     */
//    public static TokenService getTokenService(){
//        if(tokenService == null){
//            Object object = BeanUtil.getBean("tokenService");
//            tokenService = (TokenService)object;
//        }
//        return tokenService;
//    }

    /**
     * 获取数据中心access_token
     *
     * @return access_token
     * @throws Exception 异常
     * @author 周颖
     * @date 2017年1月18日 下午8:30:01
     */
    public String getAccessToken() {
        String key = RedisConstants.TOKEN_REDIS_KEY;//获取数据中心access_token rediskey
        RedisService redisService =(RedisService) BeanUtil.getBean("redisService");

        String accessToken = redisService.get(key);//redis获取access_token
        if (StringUtil.isNotBlank(accessToken)) {//如果不为空
            return accessToken;//返回access_token
        }
        return getNewAccessToken(key);//返回生成的access_token
    }


    /**
     * 重置access_token
     *
     * @return access_token
     * @throws Exception 异常
     * @author 周颖
     * @date 2017年1月19日 上午8:56:57
     */
    public String resetAccessToken() {
        String key = RedisConstants.TOKEN_REDIS_KEY;//获取数据中心access_token rediskey
        return getNewAccessToken(key);//返回生成的access_token
    }


    /**
     * 请求参数重置access_token
     *
     * @param url 接口url
     * @return 参数
     * @author 许小满
     * @date 2017年9月5日 下午7:12:23
     */
    public String urlResetAccessToken(String url) {
        if (StringUtil.isBlank(url)) {
            throw new ValidException("E0000002", ErrorMessage.getErrorMsg("E0000002", "接口地址"));//{0}不允许为空!
        }
        StringBuilder newURL = new StringBuilder(url);//生成的新的url，自动拼接access_token
        if (url.indexOf("?") == -1) {//不包含?时
            newURL.append("?").append("access_token=").append(resetAccessToken());
        } else {//包含?时
            newURL.append("&").append("access_token=").append(resetAccessToken());
        }
        return newURL.toString();
    }


    /**
     * 分析第一次发送请求结果
     *
     * @param httpResult 接口返回结果
     * @param path       path
     * @param params     请求参数
     * @return 接口返回值
     * @author 许小满
     * @date 2017年9月4日 下午6:28:11
     */
    public TokenHttpResult analysisPriResult(HttpResult httpResult, String path, String params) {
        int code = httpResult.getCode();//接口状态码
        String result = httpResult.getResult();//接口返回值
        /* "200" 直接返回成功信息 */
        if (code == 200) {
            return new TokenHttpResult(code, result);
        }
        /* 400 Bad Request 请求出现语法错误  */
        else if (code == 400) {
            throw new InterfaceException("04640129211", ErrorMessage.getErrorMsg("E2000023"), path, params);//参数不符合规范!
        }
        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
        else if (code == 403) {//针对数据中心token失效
            if (StringUtil.isBlank(result)) {
                //非token失效，则抛出异常信息
                throw new InterfaceException("04640129211", ErrorMessage.getErrorMsg("E2000009"), path, params);//接口无返回值!
            }
            Map<String, Object> returnMap = JsonUtil.fromJson(result, Map.class);
            String errorMessage = (String) returnMap.get("errormessage");//错误信息
            if (StringUtil.isBlank(errorMessage)) {
                throw new InterfaceException(ErrorMessage.getErrorMsg("E0000002", "errorMessage"), path, params, result);//{0}不允许为空!
            }
            if (errorMessage.indexOf("凭证无效") > -1) {
                return new TokenHttpResult(code, result);
            } else {
                throw new InterfaceException(errorMessage, path, params, result);//接口异常!
            }
        }
        /* 其它状态码  */
        else {
            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), path, params, result);//接口异常!
        }
    }

   // public abstract TokenHttpResult analysisPriResult(HttpResult httpResult, String path, String params);

    /**
     * 对二次请求返回的信息格式化，统一返回
     *
     * @return 接口返回值
     * @author 许小满
     * @date 2017年9月4日 下午7:45:33
     */
//    public  TokenHttpResult analysisSecResult(HttpResult httpResult, String path, String params){
//        int code = httpResult.getCode();//状态码
//        String result = httpResult.getResult();//接口返回值
//        /* "200" 直接返回成功信息 */
//        if(code == 200){
//            return new TokenHttpResult(code, result);
//        }
//        /* 400 Bad Request 请求出现语法错误 */
//        else if (code == 400){
//            throw new InterfaceException("04640129211",ErrorMessage.getErrorMsg("E2000023"), path, params);//参数不符合规范!
//        }
//        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
//        else if(code == 403){ //"403"
//            if(StringUtil.isBlank(result)){
//                //非token失效，则抛出异常信息
//                throw new InterfaceException("04640129212",ErrorMessage.getErrorMsg("E2000009"), path, params);//接口无返回值!
//            }
//            Map<String,Object> returnMap = JsonUtil.fromJson(result, Map.class);
//            String errorMessage = (String) returnMap.get("errormessage");//错误信息
//            if(StringUtil.isBlank(errorMessage)){
//                throw new InterfaceException(ErrorMessage.getErrorMsg("E0000002", "errorMessage"), path, params, result);//{0}不允许为空!
//            }
//            throw new InterfaceException(errorMessage, path, params, result);//接口异常!
//        }
//        /* 其它状态码 */
//        else {
//            throw new InterfaceException(ErrorMessage.getErrorMsg("E2000018"), path, params, result);//接口异常!
//        }
//    }
    public String getNewAccessToken(String key) {
        String url = ConfigUtil.getConfig("dbcenter.auth.token.url");
        String currentTime = "" + System.currentTimeMillis() / 1000;
        String token = "";
        String appId = ConfigUtil.getConfig("dbcenter.auth.token.appid");
        String appKey = ConfigUtil.getConfig("dbcenter.auth.token.appkey");

        try {
            token = MD5Util.getStringMD5String(appId + "_" + appKey + "_" + currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = HttpRequestUtil.sendGet(HttpUtil.joinUrlAndParams(url, "token=" + token + "&timestamp=" + currentTime + "&appid=" + appId));//接口返回值
        if (StringUtil.isBlank(result)) {//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.net.http.result.null.code"), url);//抛接口异常 接口无返回值！
        }
//        Map<String, Object> resultMap = JsonUtil.fromJson(result, Map.class);//转成map
//        if(resultMap == null){//如果为空
//            throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"),url);//抛接口异常 接口返回值不允许为空!
//        }
//        if(!resultMap.get("code").equals("0")){//如果返回失败 fail
//            throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
//        }
//        Map<String, Object> data = (Map<String,Object>)resultMap.get("data");//获取data数据
//        String oauthToken = (String) data.get("access_token");//access_token
//        //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
//        //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
//        // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
//        int seconds = (int) data.get("expires_in");
        TokenRequestResult tokenRequestResult = getTokenResultFromResultStr(result);
        RedisService redisService =(RedisService) BeanUtil.getBean("redisService");

        redisService.setex(key, tokenRequestResult.getToken(), tokenRequestResult.getToeknLeftTime());//access_token存到redis
        return tokenRequestResult.getToken();//返回access_token
    }

    public TokenRequestResult simpleGetTokenResultFromResultStr(String result) {
        TokenRequestResult tokenRequestResult = new TokenRequestResult();
        Map<String, Object> resultMap = JsonUtil.fromJson(result, Map.class);//转成map
        if (resultMap == null) {//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"), "获取token");//抛接口异常 接口返回值不允许为空!
        }

        tokenRequestResult.setCode((String) resultMap.get("code"));
        if (!resultMap.get("code").equals("0")) {//如果返回失败 fail
            throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
        }
        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");//获取data数据
        String oauthToken = (String) data.get("access_token");//access_token
        tokenRequestResult.setToken(oauthToken);
        //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
        //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
        // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
        int seconds = (int) data.get("expires_in");
        tokenRequestResult.setToeknLeftTime(seconds);
        return tokenRequestResult;
    }

    public abstract TokenRequestResult getTokenResultFromResultStr(String result);


//        {
//            TokenRequestResult tokenRequestResult =new TokenRequestResult();
//
//
//
//            Map<String, Object> resultMap = JsonUtil.fromJson(result, Map.class);//转成map
//            if(resultMap == null){//如果为空
//                throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"),url);//抛接口异常 接口返回值不允许为空!
//            }
//
//            tokenRequestResult.setCode((String)resultMap.get("code"));
//            if(!resultMap.get("code").equals("0")){//如果返回失败 fail
//                throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
//            }
//            Map<String, Object> data = (Map<String,Object>)resultMap.get("data");//获取data数据
//            String oauthToken = (String) data.get("access_token");//access_token
//            tokenRequestResult.setToken(oauthToken);
//            //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
//            //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
//            // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
//            int seconds = (int) data.get("expires_in");
//            tokenRequestResult.setToeknLeftTime(seconds);
//            return tokenRequestResult;
//        }

}
