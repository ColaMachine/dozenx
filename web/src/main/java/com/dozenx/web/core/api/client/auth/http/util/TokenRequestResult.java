package com.dozenx.web.core.api.client.auth.http.util;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:16 2019/10/15
 * @Modified By:
 */
public class TokenRequestResult {

   private String code ;
   private String msg;
   private String token;
   private int toeknLeftTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getToeknLeftTime() {
        return toeknLeftTime;
    }

    public void setToeknLeftTime(int toeknLeftTime) {
        this.toeknLeftTime = toeknLeftTime;
    }
}
