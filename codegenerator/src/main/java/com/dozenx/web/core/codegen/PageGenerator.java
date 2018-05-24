/**
\ * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import com.google.gson.*;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageGenerator {

    public void readConfig(String relativePath){


    }
    public static void main(String args[]){
        PageGenerator generator =new PageGenerator();
        generator.readConfig("doc/user/dsc/dsc.json");

    }
}
