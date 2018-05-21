package com.dozenx.web.third.weixin;

import com.alibaba.fastjson.JSONObject;
import com.dozenx.core.exception.InterfaceException;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.HttpsConnection;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.MessagePropertiesResolver;
import com.dozenx.web.third.weixin.bean.WeixinAccessTokenResult;
import com.dozenx.web.third.weixin.bean.WeixinOauthAccessTokenResult;
import com.dozenx.web.third.weixin.bean.WeixinUser;
import com.dozenx.web.util.CacheUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dozen.zhang on 2017/11/8.
 */
public class WeixinUtil {

    //日志
    private static final Logger logger =LoggerFactory.getLogger(WeixinUtil.class);


    //微信的请求url
    //String weixinAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 获取accessToken
     * 参考文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
     * @return
     */
    public static String  getAccessToken()throws Exception{

        //从缓存中拿取token
        String  accessToken = CacheUtil.get("WEIXIN_ACCESSTOKEN");
        if(!StringUtil.isBlank(accessToken)){//如果有值直接返回
            return  accessToken;
        }
        //缓存中没有去请求
        WeixinAccessTokenResult weixinAccessTokenResult = requestAccessToken();
        accessToken=weixinAccessTokenResult.getAccess_token();
        //==============请求成功把token 放入缓存 并设置超时时间============================
        CacheUtil.set(WeixinConstants.WEIXIN_ACCESSTOKEN_REDIS_KEY,accessToken,weixinAccessTokenResult.getExpires_in());//放入缓存

        return accessToken;

    }
    /**
     * @Author: dozen.zhang
     * @Description: 通过前台http接口获得的code 到后台去获取accessTOken
     * @Date: 2018/1/21
     */
    public static WeixinOauthAccessTokenResult getOauthAccessToken(String code)throws Exception{

        //从缓存中拿取token

     //        { "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"SCOPE" }

        String  accessToken = CacheUtil.get(WeixinConstants.WEIXIN_OAUTH_ACCESSTOKEN_REDIS_KEY+code);
        String openId =  CacheUtil.get(WeixinConstants.WEIXIN_OAUTH_APPID_REDIS_KEY+code);

      //  String.format(url,ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPID),)

        if(!StringUtil.isBlank(accessToken) && !StringUtil.isBlank(openId)){//如果有值直接返回
            WeixinOauthAccessTokenResult weixinOauthToken = new WeixinOauthAccessTokenResult();
            weixinOauthToken.setAccess_token(accessToken);
            weixinOauthToken.setOpenid(openId);
            return weixinOauthToken;
        }
        //缓存中没有去请求
        WeixinOauthAccessTokenResult weixinOauthAccessTokenResult = requestOauthAccessToken(code);
        //==============请求成功把token 放入缓存 并设置超时时间============================
       CacheUtil.set(WeixinConstants.WEIXIN_OAUTH_ACCESSTOKEN_REDIS_KEY+code,weixinOauthAccessTokenResult.getAccess_token(),weixinOauthAccessTokenResult.getExpires_in());//放入缓存
        CacheUtil.set(WeixinConstants.WEIXIN_OAUTH_APPID_REDIS_KEY+code,weixinOauthAccessTokenResult.getOpenid(),weixinOauthAccessTokenResult.getExpires_in());//放入缓存

        return weixinOauthAccessTokenResult;

    }

    /**
     * 发送微信accessToken https请求
     * @return
     * @throws Exception
     */
    private  static WeixinAccessTokenResult requestAccessToken()throws Exception{
        //=============拼接请求url=================================
        String  realWeixinAccessTokenUrl = String.format(ConfigUtil.getConfig(WeixinConstants.WEXIN_ACCESSTOKEN_URL), ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPID), ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPSECRET));

        String result = HttpsConnection.doGet(realWeixinAccessTokenUrl,null, Constants.HTTP_CONNECT_TIME_OUT, Constants.HTTP_READ_TIME_OUT);
        if(StringUtil.isBlank(result)) {
            logger.error("调用微信accessToken 无返回值 the weixin accessToken request has no result return url:"+realWeixinAccessTokenUrl);
            MessagePropertiesResolver mr= new MessagePropertiesResolver("err.weixin.acctoken.request.null");
            throw new InterfaceException(mr.code,mr.msg);
         }
        WeixinAccessTokenResult weixinAccessTokenResult = JsonUtil.toJavaBean(result, WeixinAccessTokenResult.class);
        //==============请求成功把token 返回token============================
        if(weixinAccessTokenResult.getErrcode()==0){//请求成功了

            return weixinAccessTokenResult;
        }
       // if(StringUtil.isBlank(weixinAccessTokenResult.getAccess_token())){
        logger.error("请求微信accessToken失败  the weixin accessToken request fail url:"+realWeixinAccessTokenUrl+" and result "+result);
        //======================失败的异常处理====================================
        if(weixinAccessTokenResult.getErrcode()==-1){
            logger.error(" 请求微信accessToken失败 系统繁忙，此时请开发者稍候再试");
            Thread.sleep(1000);//停顿以秒后再发起请求
            return requestAccessToken();
        }else
        if(weixinAccessTokenResult.getErrcode()==40001){
            logger.error(" AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性");

            throw new InterfaceException(30302013, ErrorMessage.getErrorMsg("err.weixin.acctoken.appsecret"));
        }else
        if(weixinAccessTokenResult.getErrcode()==40002){
            logger.error(" 请确保grant_type字段值为client_credential");

            throw new InterfaceException(30302012, ErrorMessage.getErrorMsg("err.weixin.acctoken.grant"));
        }else
        if(weixinAccessTokenResult.getErrcode()==40164){
            logger.error("调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");

            throw new InterfaceException(30302011, ErrorMessage.getErrorMsg("err.weixin.acctoken.ip"));
        }else{
            throw new InterfaceException(weixinAccessTokenResult.getErrcode()+"",weixinAccessTokenResult.getErrmsg());

        }
    }

    /**
     * 获取微信的jspitike
     * @return
     * @throws Exception
     */
    public static String getJsapiTicket() throws Exception {
        //=========获取缓存中的jsapiTicket==============
        String jsapiTicket = CacheUtil.get(WeixinConstants.WEIXIN_JSAPITICKET_REDIS_KEY);
        if(StringUtil.isNotBlank(jsapiTicket))
            return jsapiTicket;
        //====缓存中没有的话 获取accessToken=============
        String accessToken = getAccessToken();
        if(StringUtil.isBlank(accessToken)){

            throw new InterfaceException(30302010, ErrorMessage.getErrorMsg("err.weixin.acctoken.request.fail"));
        }
        //=====获取ticket
        String url = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",accessToken);
        String responseText = HttpRequestUtil.sendGet(url,"");
        String jsapi_ticket = null;
        JSONObject object = JsonUtil.toJsonObject(responseText);

        if (object.containsKey("errocode")) {
            logger.error("获取weixinticket失败 result:"+responseText);

            throw new InterfaceException(30302009, ErrorMessage.getErrorMsg("err.weixin.jsapiticket.request.fail"));
        }else
        if (object.containsKey("ticket")) {
            jsapi_ticket = object.getString("ticket");
        }else{
            logger.error("获取weixinticket失败 result:"+ JsonUtil.toJsonString(object));


            throw new InterfaceException(30302008, ErrorMessage.getErrorMsg("err.weixin.jsapiticket.request.fail"));
        }
        CacheUtil.set(WeixinConstants.WEIXIN_JSAPITICKET_REDIS_KEY,jsapi_ticket,(int)object.get("expires_in"));
        return jsapi_ticket;

    }




    /**
     * @Author: dozen.zhang
     * @Description:请求 oauth token
     * @Date: 2018/1/22
     */
    private  static WeixinOauthAccessTokenResult requestOauthAccessToken(String code)throws Exception{

//
//        String url =" https://api.weixin.qq.com/sns/oauth2/access_token";
//        Map params =new HashMap();//?appid=%s&secret=%s&code=%s&grant_type=authorization_code
//        params .put("appid",ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPID));//appid
//        params .put("secret",ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPSECRET));//app secret
//        params .put("code",code);//code
//        params .put("grant_type","authorization_code");
//
//        String returnStr = HttpsConnection.doGet(url,params);
//
//        //解析微信返回的结果
//        WeixinOauthAccessTokenResult accessTokenResult = JsonUtils.toJavaBean(returnStr,WeixinOauthAccessTokenResult.class );



        //=============拼接请求url=================================
        String  realWeixinAccessTokenUrl = String.format(ConfigUtil.getConfig(WeixinConstants.WEXIN_OAUTH_ACCESSTOKEN_URL), ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPID), ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPSECRET),
                code
                );

        String result = HttpsConnection.doGet(realWeixinAccessTokenUrl,null, Constants.HTTP_CONNECT_TIME_OUT, Constants.HTTP_READ_TIME_OUT);
        if(StringUtil.isBlank(result)) {
            logger.error("调用微信accessToken 无返回值 the weixin accessToken request has no result return url:"+realWeixinAccessTokenUrl);

            throw new InterfaceException(30302007, ErrorMessage.getErrorMsg("err.weixin.oauth.acctoken.request.null"));
        }
        WeixinOauthAccessTokenResult weixinAccessTokenResult = JsonUtil.toJavaBean(result, WeixinOauthAccessTokenResult.class);
        //==============请求成功把token 返回token============================
        if(weixinAccessTokenResult.getErrcode()==0){//请求成功了

            return weixinAccessTokenResult;
        }
        // if(StringUtil.isBlank(weixinAccessTokenResult.getAccess_token())){
        logger.error("请求微信accessToken失败  the weixin accessToken request fail url:"+realWeixinAccessTokenUrl+" and result "+result);
        //======================失败的异常处理====================================
        if(weixinAccessTokenResult.getErrcode()==-1){
            logger.error(" 请求微信accessToken失败 系统繁忙，此时请开发者稍候再试");
            Thread.sleep(1000);//停顿以秒后再发起请求
            return requestOauthAccessToken(code);
        }else if(weixinAccessTokenResult.getErrcode()==40001){
            logger.error(" AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性");

            throw new InterfaceException(30302006, ErrorMessage.getErrorMsg("err.weixin.acctoken.appsecret"));
        }else  if(weixinAccessTokenResult.getErrcode()==40002){
            logger.error(" 请确保grant_type字段值为client_credential");
            throw new InterfaceException(30302000, ErrorMessage.getErrorMsg("err.weixin.acctoken.grant"));
        }else if(weixinAccessTokenResult.getErrcode()==40164){
            logger.error("调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");

            throw new InterfaceException(30302001, ErrorMessage.getErrorMsg("err.weixin.acctoken.ip"));
        }else if(weixinAccessTokenResult.getErrcode()==40029){
            logger.error("invalid code");
            throw new InterfaceException(30302002, ErrorMessage.getErrorMsg("err.weixin.oauth.invalid"));
        }else{
            throw new InterfaceException(weixinAccessTokenResult.getErrcode()+"",weixinAccessTokenResult.getErrmsg());

        }
    }


    public static WeixinUser getUserInfo(String accessToken, String openId) throws Exception {

        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String  realWeixinAccessTokenUrl = String.format(ConfigUtil.getConfig(WeixinConstants.WEXIN_OAUTH_USER_URL), accessToken, openId);

        String result = HttpsConnection.doGet(realWeixinAccessTokenUrl,null, Constants.HTTP_CONNECT_TIME_OUT, Constants.HTTP_READ_TIME_OUT);
        if(StringUtil.isBlank(result)) {
            logger.error("调用微信user接口 无返回值 the weixin user request has no result return url:"+realWeixinAccessTokenUrl);
            throw new InterfaceException(30302003, ErrorMessage.getErrorMsg("err.weixin.oauth.user.request.fial"));
        }
        WeixinUser weixinUser = JsonUtil.toJavaBean(result, WeixinUser.class);
        //==============请求成功把token 返回token============================
        if(weixinUser.getErrcode()==0){//请求成功了

            return weixinUser;
        }
        // if(StringUtil.isBlank(weixinAccessTokenResult.getAccess_token())){
        logger.error("请求微信accessToken失败  the weixin accessToken request fail url:"+realWeixinAccessTokenUrl+" and result "+result);
        //======================失败的异常处理====================================
        if(weixinUser.getErrcode()==-1){
            logger.error(" 请求微信accessToken失败 系统繁忙，此时请开发者稍候再试");
            Thread.sleep(1000);//停顿以秒后再发起请求
            return getUserInfo(accessToken,openId);
        }else if(weixinUser.getErrcode()==40164){
            logger.error("调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");
            throw new InterfaceException(30302004, ErrorMessage.getErrorMsg("err.weixin.acctoken.ip"));
        }else{
            throw new InterfaceException(30302005, weixinUser.getErrcode()+weixinUser.getErrmsg());
        }
    }

    public static void main(String args[]){
        try {
            WeixinAccessTokenResult token  = WeixinUtil.requestAccessToken();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
