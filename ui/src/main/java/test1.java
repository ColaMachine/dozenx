import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.MapUtils;
import com.dozenx.web.util.EmailUtil;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 22:52 2018/4/7
 * @Modified By:
 */
public class test1 {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(test.class);
    public static void main(String args[]) {
     System.out.println(URLDecoder.decode(URLDecoder.decode("%25e5%258f%25a4%25e5%25a0%25b0")));
        System.out.println(URLEncoder.encode("%25e5%258f%25a4%25e5%25a0%25b0"));
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
