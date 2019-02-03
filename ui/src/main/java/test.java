import com.dozenx.util.*;
import com.dozenx.web.util.EmailUtil;
import com.dozenx.web.util.RedisUtil;
import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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
        String wenhao = URLEncoder.encode("?");
        System.out.println(wenhao);

        try {
           Sheet sheet = ExcelUtil.getExcelSheetFromInputStream(new FileInputStream(new File("C:\\Users\\dozen.zhang\\Desktop\\设备采集数据2019.xls")));
            List<Map<Integer,String>> list = ExcelUtil.getListFromSheet(sheet);
            int i=0;
            for(Map<Integer,String> map : list){
                System.out.println(map.get(2));
                RedisUtil.setex("asdf_"+i,map.get(2),24*60*60);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
