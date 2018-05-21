package com.dozenx.web.third.weixin;

/**
 * Created by dozen.zhang on 2017/11/9.
 */
public class WeixinConstants {
    //微信的appid配置项目名称
    public static final String WEIXIN_APPID="WEIXIN_APPID";
    //微信的accessToken在缓存中的key名称
    public static final String WEIXIN_ACCESSTOKEN_REDIS_KEY ="WEIXIN_ACCESSTOKEN";
    //微信的secret配置项名
    public static final String WEIXIN_APPSECRET="WEIXIN_APPSECRET";
    //微信的请求url配置项名
    public static final String WEXIN_ACCESSTOKEN_URL="WEXIN_ACCESSTOKEN_URL";
    //微信的jsapiTicket在缓存中的key名称
    public static final String WEIXIN_JSAPITICKET_REDIS_KEY ="WEIXIN_JSAPITICKET";

    public static final String WEXIN_OAUTH_ACCESSTOKEN_URL =  "WEXIN_OAUTH_ACCESSTOKEN_URL";
    //微信的code 对应的accessToken在缓存中的key名称
    public static final String   WEIXIN_OAUTH_ACCESSTOKEN_REDIS_KEY ="WEIXIN_OAUTH_ACCESSTOKEN";

    public static final String  WEIXIN_OAUTH_APPID_REDIS_KEY = "WEIXIN_OAUTH_APPID";

    public static final String  WEXIN_OAUTH_USER_URL = "WEXIN_OAUTH_USER_URL";

    public static final String WEIXIN_SESSION_OPENID="WEIXIN_SESSION_OPENID";
    public static final String WEIXIN_SESSION_USER = "WEIXIN_SESSION_USER";
}
