package com.dozenx.web.module.hbase.action;

import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIResponse;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.hbase.service.DcHBaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:06 2018/3/1
 * @Modified By:
 */


@APIs(description = "用户模块")
@Controller
@RequestMapping("/hbase")

public class HbaseController extends BaseController {

    @API(summary = "hbase测试接口",
            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO testHbase() {
        try {
            boolean flag = DcHBaseService.testHbase();
            if(flag){
                return this.getResult(0);
            }else{

                DcHBaseService dcHBaseService = new DcHBaseService();
                    dcHBaseService.ForceGetConnection();
                logger.info("hbase测试失败 尝试重连");
                return this.getResult(1,"hbase测试失败");
            }
        } catch (Exception e) {
            e.printStackTrace();

            DcHBaseService dcHBaseService = new DcHBaseService();
            dcHBaseService.ForceGetConnection();
            logger.info("hbase测试失败 尝试重连");
            return this.getResult(2,"hbase异常"+e.getMessage());
        }

    }


//    @API(summary = "hbase保障接口",
//            consumes = "application/x-www-form-urlencoded",
//            description = "hbase保障接口", parameters = {
//    })
//    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
//    @RequestMapping(value = "/protect", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public ResultDTO protectHbase() {
//        String[] domainList = new String[]{"http://ad.51awifi.com", "http://202.100.244.186:83"};
////        while(true) {
//            for (int i = 0; i < domainList.length; i++) {
//                Long nowTime = System.currentTimeMillis();
//                String adsUrl = domainList[i] + "/advertsrv/ads/req?usermac=D8C46A555609&adpos=4&devid=RADIUS-RADIUS-20150203-44ffb97d&devmac=50DA00D16B80&t=";
//                String testUrl = domainList[i] + "/advertsrv/hbase/test?t=";
//                String reconnectUrl = domainList[i] + "/advertsrv/hbase/reconnect?t=";
//                try {
//
//                    String result = HttpRequestUtil.sendGetWithException(testUrl+nowTime);
//                    logger.info(result);
//                    HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
//                    int r = MapUtils.getInteger(resultMap, "r");
//                    if (r != 0 || r==30202089) {
//                         EmailUtil.send("371452875@qq.com", domainList[i]+result+nowTime);
//                        HttpRequestUtil.sendGetWithException(reconnectUrl);
//                    }
//
////
////                    result = HttpRequestUtil.sendGetWithException(adsUrl+nowTime);
////                    logger.info(result);
////                    resultMap = JsonUtil.fromJson(result, HashMap.class);
////                    r = MapUtils.getInteger(resultMap, "r");
////                    if (r != 0) {
////                        EmailUtil.send("371452875@qq.com", result+nowTime);
////                        HttpRequestUtil.sendGetWithException(reconnectUrl+nowTime);
////                    }
//                } catch (Exception e) {
//                    e.getMessage();
//                    try {
//                        HttpRequestUtil.sendGetWithException(reconnectUrl);
//                        EmailUtil.send("371452875@qq.com",domainList[i]+ e.getMessage()+nowTime);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                }
//
//
//            }
//
//
////            try {
////                testEzhiKeAlpha();
////            } catch (Exception e) {
////                e.printStackTrace();
////                try {
////                    EmailUtil.send("371452875@qq.com","ezhike服务不正常了"+ e.getMessage());
////                } catch (MessagingException e1) {
////                    e1.printStackTrace();
////                }
////            }
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////        }
//        return this.getResult();
//    }

    @API(summary = "hbase接口强制重连接口",
            consumes = "application/x-www-form-urlencoded",
            description = "hbase测试接口", parameters = {
            //         @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/reconnect", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultDTO reconnect() {
        DcHBaseService dcHBaseService = new DcHBaseService();
        try {
            dcHBaseService.ForceGetConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回角色信息
        return this.getResult();
    }

}
