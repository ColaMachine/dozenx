package com.dozenx.web.core.cache.service;

import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {
    @Resource
    RedisTemplate redisTemplate = null;
//    public RedisUtil2(){
//        redisTemplate =
//    }

    private /*static*/ Logger logger = LoggerFactory.getLogger(RedisService.class);

    public /*static*/ Map<String, String> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置值并且设置超时时间
     *
     * @param key
     * @return
     */
    public /*static*/ void setex(String key, String value, int seconds) {
        RedisUtil.setex(key,value,seconds);
    }



    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
   /* private *//*static*//* void set(String key, String value) {
        RedisUtil.set(key,value);

    }
*/
    public /*static*/ void expire(String key, int seconds) {
        RedisUtil.expire(key,seconds);
    }



    /**
     * 获取数据 归还资源
     *
     * @param key
     * @return
     */
    public /*static*/ String get(String key) {
        return RedisUtil.get(key);
    }




    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public /*static*/ void del(String key) {
         RedisUtil.del(key);
    }

    public /*static*/ Set<String> hkeys(String key) {
        return RedisUtil.hkeys(key);
    }



    /**
     * hset + 还连接
     *
     * @param key
     * @param field
     * @param value
     * @author dozen.zhang
     */
    public /*static*/ void hset(String key, String field, String value) {
         RedisUtil.hset(key,field,value);
    }

    /**
     * 强制归还资源的hget
     *
     * @param key
     * @param field
     * @author dozen.zhang
     */
    public /*static*/ String hget(String key, String field) {
        return RedisUtil.hget( key,  field);
    }


    public /*static*/ void hdel(String key, String field) throws Exception {
        RedisUtil.hdel( key,  field);
    }


    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    public /*static*/ void setByteAry(String key, byte[] value, int expireTime) {
        RedisUtil.setByteAry( key,  value,expireTime);
    }


    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    public /*static*/ byte[] getByteAry(String key) {
        return RedisUtil.getByteAry( key);
    }

    /**
     * 自增
     */


    public /*static*/ Long incr(String key) throws Exception {
        return RedisUtil.incr(key);
    }

    public /*static*/ Long incr(String key, int timeout) throws Exception {
        return RedisUtil.incr(key,timeout);
    }


    /**
     * Hash键值 字段 get操作
     * @param key    键
     * @param fields 字段(可多个)
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:13
     */
    public /*static*/ List<String> hmget(final String key, final String... fields) {

        return RedisUtil.hmget(key,fields);
    }


    /**
     * 批量Hash键值设置操作
     *
     * @param dataMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:35
     */
    public /*static*/ String hmsetBatch(Map<String, Map<String, String>> dataMap, Integer seconds) {
        return RedisUtil.hmsetBatch(dataMap,seconds);
    }

    /**
     * 批量Hash键值设置操作
     * 注意 key 和value 都不能为空
     *
     * @param dataMap map
     * @param seconds 有效时间
     * @return 结果
     * @author 尤小平
     * @date 2018年2月6日 下午2:50:35
     */
    public /*static*/ String hmset(String key, Map<String, String> dataMap, Integer seconds) {
        return RedisUtil.hmset(key,dataMap,seconds);
    }


    /**
     * 判断keys是否存在
     *
     * @param key key
     * @return true 存在、false 不存在
     * @author 尤小平
     * @date 2018年2月6日 下午2:51:12
     */
    public /*static*/ Boolean exists(final String key) {
        return RedisUtil.exists(key);
    }


    /**
     * 释放锁
     * author 王作品
     *
     * @param key   锁的key
     * @param value 释放锁的标识
     * @return
     */
    public /*static*/ void lpush(String key, String value) {
         RedisUtil.lpush(key,value);
    }

    public /*static*/ String rpop(String key) {
        return RedisUtil.rpop(key);
    }
//
//    /**
//     * zadd
//     * @param key
//     * @param score
//     * @param member
//     */
//    public /*static*/ void zadd(String key,Long score,String member){
//
//        try {
//            conn = getJedis();
//             conn.zadd(key,score,member);
//
//             logger.info("zadd key:"+key+" score:"+score+" member:"+member);
//        } catch (JedisException e) {
//            logger.error("JedisException报错 +e " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//
//        }
//
//    }
//
//    /**
//     * zrange
//     * @param key
//     * @param startScore
//     * @param endSocre
//     * @return
//     */
//    public /*static*/ Set<String>  zrange(String key,Long startScore,Long endSocre){
//        Jedis conn = null;
//        try {
//            conn = getJedis();
//            logger.info("zrange "+key+" "+startScore+" "+endSocre);
//            return conn.zrange(key,startScore,endSocre);
//        } catch (JedisException e) {
//            logger.error("JedisException报错 +e " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//        return null;
//    }
//    /**
//     * zrangebyscore
//     * @param key
//     * @param startScore
//     * @param endSocre
//     * @return
//     */
//    public /*static*/ Set<String>  zrangebyscore(String key,Long startScore,Long endSocre){
//        Jedis conn = null;
//        try {
//            conn = getJedis();
//            logger.info("zrangebyscore "+key+" "+startScore+" "+endSocre);
//            return conn.zrangeByScore(key,startScore,endSocre);
//        } catch (JedisException e) {
//            logger.error("JedisException报错 +e " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * zrange
//     * @param key
//     * @param member
//     * @return
//     */
//    public /*static*/ void  zrem(String key,String member){
//        Jedis conn = null;
//        try {
//            conn = getJedis();
//             conn.zrem(key,member);
//        } catch (JedisException e) {
//            logger.error("JedisException报错 +e " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
//
//    }
//
//    public /*static*/ String printPoolStatus(){
//        StringBuffer stringBuffer =new StringBuffer();
//        stringBuffer.append("getNumWaiters:").append(jedisPool.getNumWaiters())
//                .append("getNumActive:").append(jedisPool.getNumActive())
//                .append("getNumIdle:").append(jedisPool.getNumIdle())
//                .append("getMaxBorrowWaitTimeMillis:").append(jedisPool.getMaxBorrowWaitTimeMillis())
//                .append("getMeanBorrowWaitTimeMillis:").append(jedisPool.getMeanBorrowWaitTimeMillis());
//        logger.info(stringBuffer.toString());
//        return stringBuffer.toString();
//
//    }















    public  boolean lock(String lockName,String randomVal,int seconds){
        return RedisUtil.lock(lockName,randomVal,seconds);
    }

    public  boolean release(String lockName,String randomVal){

        return RedisUtil.release(lockName,randomVal);
    }
}