package com.dozenx.web.module.sms.service;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:49 2019/10/15
 * @Modified By:
 */
public interface SmsSendService {
    public void send(String phone, String content);
}
