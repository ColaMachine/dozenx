package com.dozenx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
    public static void main(String args[]){


//        try {
//
//            JedisPoolConfig config = new JedisPoolConfig();
//            // config.setMaxActive(MAX_ACTIVE);
//            config.setMaxTotal(11);
//            config.setMaxIdle(1);
//
//            config.setMaxWaitMillis(1000);
//            // config.setSoftMinEvictableIdleTimeMillis();
//            config.setMinEvictableIdleTimeMillis(3000);
//            config.setSoftMinEvictableIdleTimeMillis(3000);
////              config.setMaxWait(MAX_WAIT);
//            config.setTestOnBorrow(true);
//
//            // logger.debug(String.format("初始化redis ADDR:%s"));
//            //   jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
//
//            JedisPool jedisPool = new JedisPool(config, "192.168.213.91", 6379, 1000, "Awifi@123", 0);
//            Jedis jedis = jedisPool.getResource();
//
//            for(;;){
//                jedis.set("a","1");
//                String a= jedis.get("a");
//                Thread.sleep(1000);
//                logger.info("redis成功");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
