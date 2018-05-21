package com.dozenx.web.module.hbase.service;

import org.apache.hadoop.hbase.client.Result;

/**
 * @Title:CallBack
 * @Description:
 * @Copyright:中国电信爱wifi运营中心
 * @author:panhl
 * @date 2017/9/11 0011 14:31
 */
public interface CallBack {
    void apply(Result result);
    void apply();
}