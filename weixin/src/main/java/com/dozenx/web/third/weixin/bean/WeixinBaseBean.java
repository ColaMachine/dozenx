package com.dozenx.web.third.weixin.bean;

/**
 * @Author: dozen.zhang
 * @Description:因为每个微信的返回接口都有 errcode  errmsg两个属性故抽象出来
 *
 * @Date: Created in 10:59 2018/1/22
 * @Modified By:
 */
public class WeixinBaseBean {

   int errcode;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    String errmsg;
}
