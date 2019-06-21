package com.dozenx.web.core;
import com.dozenx.util.MD5Util;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.web.context.ContextLoaderListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 12:24 2018/3/20
 * @Modified By:
 */
public class test {

    public static void main(String argsp[]){
        String s="${basePath}123123${basePath}";
        System.out.println(s);
        s=s.replaceAll("\\$\\{basePath\\}","hello");
        System.out.println(s);
    }
    public static void main1(String args[]){
        InetAddress wcAddr = new InetSocketAddress(0).getAddress();
        System.out.println(wcAddr.getAddress());
        String s ="123456qs";
        try {
            System.out.println(MD5Util.getStringMD5String(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Long time = System.currentTimeMillis()/1000;
//        System.out.println();
//        System.out.println();
////        Jedis jedis = new Jedis("192.168.41.75") ;
////        String output ;
////        jedis.set( "hello", "world" ) ;
////        output = jedis.get( "hello") ;
////        System. out.println(output) ;
//
//
//        JedisPoolConfig config = new JedisPoolConfig();
//        // config.setMaxActive(MAX_ACTIVE);
//        config.setMaxTotal(1);
//        config.setMaxIdle(1);
//
//        config.setMaxWaitMillis(10000);
//        // config.setSoftMinEvictableIdleTimeMillis();
//        config.setMinEvictableIdleTimeMillis(3000);
//        config.setSoftMinEvictableIdleTimeMillis(3000);
//        //  config.setMaxWait(MAX_WAIT);
//        config.setTestOnBorrow(false);
//        // logger.debug(String.format("初始化redis ADDR:%s"));
//        //   jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
//        JedisPool jedisPool = new JedisPool(config, "192.168.41.75", 6379, 2000,null, 1);
//        System.out.println(jedisPool.getResource().get("zzw"));
//        jedisPool.getResource().set("zzw","test");
//        Object object = test.class.getClassLoader();
//        System.out.println(object );
//
//         object = test.class.getClassLoader().getParent();
//        System.out.println(object );
//
//
//        object = test.class.getClassLoader().getParent().getParent();
//        System.out.println(object );
//
//       // URLClassPath ucp = test.class.getClassLoader().getParent().getBootstrapClassPath();
//
//        URLClassPath ucp = sun.misc.Launcher.getBootstrapClassPath();
//        System.out.println("ucp.getURLs()[0]:"+ ucp.getURLs()[0]);
//
//
//       Object obj = ucp.getResource("");
//        System.out.println("ucp.getResource(\"\"):"+ obj);
//
//        System.out.println("test.class.getResource(\"/\"):"+test.class.getResource("/"));
//        System.out.println("test.class.getResource(\"\"):"+test.class.getResource(""));
//
//        System.out.println(test.class.getClassLoader().getResource("/"));
//        System.out.println("test.class.getClassLoader().getResource(\"\"):"+test.class.getClassLoader().getResource(""));
//
//
//
//        System.out.println("test.class.getClassLoader().getResource(\"\"):"+test.class.getClassLoader().getResource(""));
//
//
//        System.out.println("测试classLoader.getResource推论是否按照预想的一样,应该是一样的结果");
//        System.out.println("test.class.getClassLoader().getResource(\"\"): "+test.class.getClassLoader().getResource(""));
//
//        System.out.println("test.class.getClassLoader().getParent().getResource(\"\"): "+test.class.getClassLoader().getParent().getResource(""));
//
//
//        System.out.println(test.class.getName());
//        List<String > packages = new ArrayList<>();
//        packages.add("com.dozenx");
//        List<Class<?>> classes = null;
//        try {
//            //classes =  com.cpj.swagger.util.ReflectUtils.scanPackage().scanClazzs(packages);
//            List<Package> pkgs =  com.cpj.swagger.util.ReflectUtils.scanPackage("com.dozenx",true);
//        System.out.print(pkgs.size());
//        } catch (Exception  e) {
//            e.printStackTrace();
//        }
//        String s = "params=%7B%22merchantId%22%3A1%2C%22pageSize%22%3A20%2C%22pageNum%22%3A1%7D";
//        System.out.println(URLDecoder.decode(s));
//
//        System.out.println(URLEncoder.encode("params={\"pageSize\":20,\"pageNum\":1}"));
//        System.out.println(123);
    }
}
