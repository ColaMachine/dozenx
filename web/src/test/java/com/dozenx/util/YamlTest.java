package com.dozenx.util;

import org.junit.Test;

import java.util.Properties;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2019/5/31
 * @Modified By:
 */
public class YamlTest {

    @Test
    public void testPorperties(){
        Properties properties =new Properties();
        properties.put("a","awifi@123");
        Properties properties1 =new Properties();
        try {
            properties1.put("a", null);
        }catch ( Exception e){
            System.out.println("protpertis 里面不能塞入 null 值");
        }
        properties.putAll(properties1);
        System.out.println(properties.getProperty("a"));



    }
}
