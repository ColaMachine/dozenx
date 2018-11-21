package com.dozenx;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:00 2018/10/17
 * @Modified By:
 */
public class UserInfo {
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;


    String telno;
    String email;

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
