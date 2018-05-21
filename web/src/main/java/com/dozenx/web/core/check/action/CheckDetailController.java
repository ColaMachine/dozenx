package com.dozenx.web.core.check.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.RedisUtil;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import redis.clients.jedis.Jedis;

/**
 * 商户登录 /ms/login/init.shtml
 * 
 * @author kjl
 * @date 2015-11-2
 * 
 */
@Controller
@RequestMapping(value = "")
public class CheckDetailController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CheckDetailController.class);


    //@RequestMapping(value = "/server/status")
    //@ResponseBody
    public ResultDTO testRedis() {
        HashMap map = new HashMap();
        map.put("Active", RedisUtil.jedisPool.getNumActive());
        map.put("idle", RedisUtil.jedisPool.getNumIdle());
        map.put("waiter", RedisUtil.jedisPool.getNumWaiters());
        try {
            RedisUtil.setex("test","1", RedisConstants.HOUR_SECONDS);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            // logger.info("redis ip:"+PropertiesOperateUtil.GetConfig("config", "cache.redis.ip"));
            map.put("test",RedisUtil.get("test"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResultUtil.getDataResult(map);
    }

}