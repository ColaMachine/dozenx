package com.dozenx.web.module.monitor.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.MapUtils;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:56 2018/8/3
 * @Modified By:
 */


@APIs(description = "用户")
@Controller
@RequestMapping(Constants.WEBROOT + "/monitor")
public class MonitorController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MonitorController.class);

    /**
     * 说明:ajax请求MapData信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2017-11-20 10:48:51
     */
    @API(summary = "监控接口",
            description = "监控接口",
            parameters = {
                    @Param(name = "updatetime", description = "更新时间", dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {
        //检测广告系统是否正常
//        String url = "http://ad.51awifi.com/advertsrv/ads/req?usermac=D8C46A555609&adpos=4&devid=RADIUS-RADIUS-20150203-44ffb97d&devmac=50DA00D16B80&t=1532393604072";
//        try {
//            String result = HttpRequestUtil.sendGetWithException(url);
//            HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
//            int r = MapUtils.getInteger(resultMap, "r");
//            if (r != 0) {
//                logger.info(result);
//                EmailUtil.send("371452875@qq.com", result);
//            }
//        } catch (Exception e) {
//            e.getMessage();
//            EmailUtil.send("371452875@qq.com", e.getMessage());
//        }
//
//
//        String hianan = "http://ad.51awifi.com/advertsrv/ads/req?usermac=D8C46A555609&adpos=4&devid=RADIUS-RADIUS-20150203-44ffb97d&devmac=50DA00D16B80&t=1532393604072";
//        try {
//            String result = HttpRequestUtil.sendGetWithException(url);
//            HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
//            int r = MapUtils.getInteger(resultMap, "r");
//            if (r != 0) {
//                logger.info(result);
//                EmailUtil.send("371452875@qq.com", result);
//            }
//        } catch (Exception e) {
//            e.getMessage();
//            EmailUtil.send("371452875@qq.com", e.getMessage());
//        }
//
//
//        try {
//            String hbaseTestUrl = "http://ad.51awifi.com/advertsrv/hbase/test";
//            String result = HttpRequestUtil.sendGetWithException(hbaseTestUrl);
//            logger.info(result);
//            HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
//            int r = MapUtils.getInteger(resultMap, "r");
//            if (r != 0) {
//                EmailUtil.send("371452875@qq.com", result);
//                String reconnectUrl = "http://ad.51awifi.com/advertsrv/hbase/reconnect";
//                HttpRequestUtil.sendGetWithException(reconnectUrl);
//            }
//
//        } catch (Exception e) {
//            e.getMessage();
//            EmailUtil.send("371452875@qq.com", e.getMessage());
//        }

        return this.getResult();
    }


    public static void main(String args[]) {
        String[] domainList = new String[]{"http://ad.51awifi.com"/*, "http://202.100.244.186:83"*/};

//       while(true) {
//           for (int i = 0; i < domainList.length; i++) {
//               String adsUrl = domainList[i] + "/advertsrv/ads/req?usermac=D8C46A555609&adpos=4&devid=RADIUS-RADIUS-20150203-44ffb97d&devmac=50DA00D16B80&t=1532393604072";
//               String testUrl = domainList[i] + "/advertsrv/hbase/test";
//               String reconnectUrl = domainList[i] + "/advertsrv/hbase/reconnect";
//               try {
//                   String result = HttpRequestUtil.sendGetWithException(testUrl);
//                   logger.info(result);
//                   HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
//                   int r = MapUtils.getInteger(resultMap, "r");
//                   if (r != 0) {
//                       EmailUtil.send("371452875@qq.com", result);
//                       HttpRequestUtil.sendGetWithException(reconnectUrl);
//                   }
//
//
//                   result = HttpRequestUtil.sendGetWithException(adsUrl);
//                   logger.info(result);
//                   resultMap = JsonUtil.fromJson(result, HashMap.class);
//                   r = MapUtils.getInteger(resultMap, "r");
//                   if (r != 0) {
//                       EmailUtil.send("371452875@qq.com", result);
//                       HttpRequestUtil.sendGetWithException(reconnectUrl);
//                   }
//               } catch (Exception e) {
//                   e.getMessage();
//                   try {
//                       EmailUtil.send("371452875@qq.com", e.getMessage());
//                   } catch (Exception e1) {
//                       e1.printStackTrace();
//                   }
//               }
//
//
//           }
//           try {
//               Thread.sleep(10000);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
//       }
    }
}
