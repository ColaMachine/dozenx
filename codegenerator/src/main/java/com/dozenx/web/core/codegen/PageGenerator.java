/**
\ * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.dozenx.common.Path.PathManager;

public class PageGenerator {

    public void readConfig(String relativePath){


    }
    public static void main(String args[]){
        PageGenerator generator =new PageGenerator();
        generator.readConfig("doc/user/dsc/dsc.json");

    }
}
