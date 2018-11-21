import com.dozenx.util.*;
import com.dozenx.web.util.EmailUtil;
import com.google.gson.JsonObject;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 22:52 2018/4/7
 * @Modified By:
 */
public class test {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(test.class);
    public static void main(String args[]) {
        System.out.println(RegexUtil.match("37145@12@178.cn" , "^[0-9a-zA-Z_@\\.]+$"));
        String[] domainList = new String[]{"http://ad.51awifi.com", "http://202.100.244.186:83"};
        while(true) {
            for (int i = 0; i < domainList.length; i++) {
                Long nowTime = System.currentTimeMillis();
                String adsUrl = domainList[i] + "/advertsrv/ads/req?usermac=D8C46A555609&adpos=4&devid=RADIUS-RADIUS-20150203-44ffb97d&devmac=50DA00D16B80&t=";
                String testUrl = domainList[i] + "/advertsrv/hbase/test?t=";
                String reconnectUrl = domainList[i] + "/advertsrv/hbase/reconnect?t=";
                try {

                    String result = HttpRequestUtil.sendGetWithException(testUrl+nowTime);
                    logger.info(result);
                    HashMap resultMap = JsonUtil.fromJson(result, HashMap.class);
                    int r = MapUtils.getInteger(resultMap, "r");
                    if (r != 0 || r==30202089) {
                        EmailUtil.send("371452875@qq.com", domainList[i]+result+nowTime);
                        HttpRequestUtil.sendGetWithException(reconnectUrl);
                    }
System.out.println(URLEncoder.encode(URLEncoder.encode("正在处理履约计划列账及付款 卢超群(B)省本部网络发展部20180727-009022")));
                    System.out.println(URLDecoder.decode("http://alpha-ad.51awifi.com/webrobot/result/list?curPage=1&pageSize=10&name=%C2%C4%D4%BC%BC%C6%BB%AE%C1%D0%D5%CB%BC%B0%B8%B6%BF%EE%C2%AC%B3%AC%C8%BA%28B%29%CA%A1%B1%BE%B2%BF%CD%F8%C2%E7%B7%A2%D5%B9%B2%BF20180727-009022"));

//
//                    result = HttpRequestUtil.sendGetWithException(adsUrl+nowTime);
//                    logger.info(result);
//                    resultMap = JsonUtil.fromJson(result, HashMap.class);
//                    r = MapUtils.getInteger(resultMap, "r");
//                    if (r != 0) {
//                        EmailUtil.send("371452875@qq.com", result+nowTime);
//                        HttpRequestUtil.sendGetWithException(reconnectUrl+nowTime);
//                    }
                } catch (Exception e) {
                    e.getMessage();
                    try {
                        HttpRequestUtil.sendGetWithException(reconnectUrl);
                     EmailUtil.send("371452875@qq.com",domainList[i]+ e.getMessage()+nowTime);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }


            }


//            try {
//                testEzhiKeAlpha();
//            } catch (Exception e) {
//                e.printStackTrace();
//                try {
//                    EmailUtil.send("371452875@qq.com","ezhike服务不正常了"+ e.getMessage());
//                } catch (MessagingException e1) {
//                    e1.printStackTrace();
//                }
//            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testEzhiKeAlpha() throws Exception {
        String url ="http://alpha-ezhike.51awifi.com/ezhikesrv/customer/realtime?params=%7B%22buildId%22:90%7D";
        String result = HttpRequestUtil.sendGet(url);
        Map map = JsonUtil.toJavaBean(result,HashMap.class);
        int r = MapUtils.getInteger(map,"r");
        if(r!=0){
            throw new Exception(result);
        }
        Map dataMap =(com.alibaba.fastjson.JSONObject) map.get("data");
        if(MapUtils.getIntValue(dataMap,"activeUseCounter")==0){
            throw new Exception(result);
        }
    }
//    public static void main(String args[]){
//
//        try {
//            System.out.println(MD5Util.getStringMD5String("123456"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(URLDecoder.decode("https://alpha-np.51awifi.com/hotmapsrv/merchant/import?access_token=AT_NP_BIZ_6EF13CC5C5874378AA4E5FEA917DE658&params=%7B%22carrieroperator%22%3A%22%E7%94%B5%E4%BF%A1%22%7D"));
//        System.out.println(URLDecoder.decode("https://alpha-np.51awifi.com/hotmapsrv/merchant/import?access_token=AT_NP_BIZ_6EF13CC5C5874378AA4E5FEA917DE658&params=%7B%22carrieroperator%22%3A%22%E7%94%B5%E4%BF%A1%22%7D"));
////
////        String url = "http://192.168.41.53/sms-service/sms/send?mobile=18368729738&msg=%E4%BA%BA%E8%84%B8%E8%AF%86%E5%88%AB%E5%A4%B1%E8%B4%A5&access_token=5aaf89c1bfaed238e1eea6c1";
////
////        String result = HttpRequestUtil.sendGet(url);
////        System.out.println(result);
//
////        String url = "http://pic-bucket.nosdn.127.net/photo/0001/2018-04-07/DEQ8E03N00AP0001NOS.jpg";
////
////        String result = null;
////        try {
////            HttpRequestUtil.saveFileFromUrl(url,"saveFileFromUrlPic", Paths.get("g:/"));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        System.out.println(result);
//    }
}
