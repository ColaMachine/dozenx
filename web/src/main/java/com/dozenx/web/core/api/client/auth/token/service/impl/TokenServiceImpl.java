package com.dozenx.web.core.api.client.auth.token.service.impl;

import java.util.Map;

import com.dozenx.core.exception.BizException;
import com.dozenx.core.exception.InterfaceException;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.JsonUtil;
import com.dozenx.web.core.api.client.auth.token.service.TokenService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.util.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午3:00:29
 * 创建作者：周颖
 * 文件名称：TokenServiceImpl.java
 * 版本：  v1.0
 * 功能：获取数据中心access_token实现类
 * 修改记录：
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    /**
     * 获取数据中心access_token
     * @param key rediskey
     * @return access_token oauthToken
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年1月18日 下午8:01:52
     */
    @SuppressWarnings("unchecked")
    public String getAccessToken(String key){
        String url = ConfigUtil.getConfig(key+".accesstoken.url");
        String result = HttpRequestUtil.sendGet(url);//接口返回值
        if(StringUtils.isBlank(result)){//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.net.http.result.null.code"),url);//抛接口异常 接口无返回值！
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(result, Map.class);//转成map
        if(resultMap == null){//如果为空
            throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"),url);//抛接口异常 接口返回值不允许为空!
        }
        if(!resultMap.get("state").equals("success")){//如果返回失败 fail 
            throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
        }
        Map<String, Object> data = (Map<String,Object>)resultMap.get("data");//获取data数据
        String oauthToken = (String) data.get("oauthToken");//access_token
        //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
        //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
        // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
        int seconds = (int) data.get("expiresIn");
        RedisUtil.set(key, oauthToken, seconds);//access_token存到redis
        return oauthToken;//返回access_token
    }
}