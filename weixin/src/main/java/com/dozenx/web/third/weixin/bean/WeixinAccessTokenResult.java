package com.dozenx.web.third.weixin.bean;

/**
 * Created by dozen.zhang on 2017/11/8.
 */
public class WeixinAccessTokenResult extends WeixinBaseBean {
    String access_token;
    int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }




}
