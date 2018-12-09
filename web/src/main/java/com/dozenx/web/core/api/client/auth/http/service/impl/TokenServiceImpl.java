package com.dozenx.web.core.api.client.auth.http.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.MD5Util;
import com.dozenx.web.core.RedisConstants;
import com.dozenx.web.core.api.client.auth.token.service.TokenService;
import com.dozenx.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by colamachine on 18-12-9.
 */
@Service("TokenService")
public class TokenServiceImpl implements TokenService {
    Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    /** 申请token的url */
    @Value("${token.url}")
    private String  tokenUrl; //申请token的url
    @Value("${token.appid}")
    private String  appid; //申请token的url
    @Value("${token.appkey}")
    private String  appkey; //申请token的url
    @Override
    public String getAccessToken(String redisKey) {

        HashMap map =new HashMap();
        map.put("appid","0001");

        String currentTime =""+ System.currentTimeMillis()/1000;
        map.put("timestamp",currentTime);
        try {
            String token = MD5Util.getStringMD5String(appid+"_"+appkey+"_ss"+currentTime);
            map.put("token",token);
            String result = HttpRequestUtil.sendGet(tokenUrl, map);//http://192.168.188.8:3502
            logger.info("token url return :"+result);
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            JSONObject obj = (JSONObject)jsonObject.get("data");
            String accessToken =obj.getString("access_token");
            RedisUtil.setex(redisKey,accessToken , 600*1000);
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
