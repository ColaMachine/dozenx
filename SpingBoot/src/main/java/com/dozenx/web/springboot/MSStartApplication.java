package com.dozenx.web.springboot; /**
 * @Author :王作品
 * @Date: 2018/12/26 10:30
 */


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @program: MS
 * @description:
 * @author: 王作品
 * @create: 2018-12-26 10:30
 **/
public class MSStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MSApplication.class);
    }
}

