package com.dozenx.web.module.spider.service;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:25 2018/8/9
 * @Modified By:
 */
public interface SpiderServiceInterface {

    //爬取景点
    public void spiderSightComment(String url);

    //爬取景点
    public void spiderSightArtical(String url,int pid);

    //爬取酒店
    public void spiderHotel(String url);
}
