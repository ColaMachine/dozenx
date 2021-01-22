package com.dozenx.web.module.sms.service.impl;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.InterfaceException;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.api.client.auth.http.util.HttpUtil;
import com.dozenx.web.core.api.client.auth.http.util.TokenRequestResult;
import com.dozenx.web.core.api.client.auth.http.util.diy.DiyAuthHttpRequest;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.module.sms.service.SmsSendService;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:51 2019/10/15
 * @Modified By:
 */
@Service("smsSendService")
public class DBSmsSendServiceImpl implements SmsSendService {
    private static final Logger logger = LoggerFactory.getLogger(DBSmsSendServiceImpl.class);

    @Override

    public void send(String phone, String msg) {
        logger.info("read send sms " + msg);
        sendByDbcenter(phone, msg);
        //   return getSmsApiService().sendMsg(cellphone, msg);
    }

    public Map<String, Object> sendByDbcenter(String phone, String msg) {

        String interfaceUrl = ConfigUtil.getConfig("dbcenter.sms.url");//接口url
        Map<String, String> paramMap = new HashMap<String, String>(2);//参数
        paramMap.put("mobile", phone);//手机号
        paramMap.put("msg", msg);//短信内容
        String interfaceParam = HttpUtil.getParams(paramMap);//接口参数

        DiyAuthHttpRequest diyAuthHttpRequest = new DiyAuthHttpRequest() {


            @Override
            public Map<String, Object> getResultMap(Map<String, Object> resultMap) {
                String resultCode = (String) resultMap.get("resultCode");
                if (StringUtil.isBlank(resultCode) || !resultCode.equals("0")) {
                    throw new InterfaceException((String) resultMap.get("message"), "结果分析报错");
                }
                return resultMap;
            }

            @Override
            public TokenRequestResult getTokenResultFromResultStr(String result) {
                {
                    TokenRequestResult tokenRequestResult = new TokenRequestResult();

                    //result:{"data":{"expiresIn":738,"loseTimestamp":1571127179398,"oauthTimestamp":1571125379398,
                    // "oauthToken":"5da57883bfaed204741c80f6"},"state":"success"}
                    Map resultMap = JsonUtil.fromJson(result, Map.class);//转成map
                    if (resultMap == null) {//如果为空
                        throw new InterfaceException(ErrorMessage.getErrorMsg("err.param.null"), " ");//抛接口异常 接口返回值不允许为空!
                    }

                    tokenRequestResult.setCode((String) resultMap.get("state"));
                    if (!tokenRequestResult.getCode().equals("success")) {//如果返回失败 fail
                        throw new BizException(30505048, ErrorMessage.getErrorMsg("err.net.http.result.null.code"));//抛异常 获取数据中心access_token失败!
                    }
                    Map<String, Object> data = (Map<String, Object>) resultMap.get("data");//获取data数据
                    String oauthToken = (String) data.get("oauthToken");//access_token
                    tokenRequestResult.setToken(oauthToken);
                    //Long oauthTimestamp = (Long) data.get("oauthTimestamp");//token生成时间
                    //Long loseTimestamp = (Long) data.get("loseTimestamp");//token失效时间
                    // int seconds = (int) ((loseTimestamp-oauthTimestamp)/1000);//access_token有效时间
                    int seconds = (int) data.get("expiresIn");
                    tokenRequestResult.setToeknLeftTime(seconds);
                    return tokenRequestResult;
                }
            }
        };
        return diyAuthHttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
//        return AuthHttpRequest.sendGetRequest(interfaceUrl, interfaceParam,"state","success",
//                "oauthToken","expiresIn");

        //   return getSmsApiService().sendMsg(cellphone, msg);
    }
}
