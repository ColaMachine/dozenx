import com.dozenx.common.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 22:52 2018/4/7
 * @Modified By:
 */
public class test {
    public static void main(String args[]) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        for(int i=0;i<10;i++){
            robot.mouseMove(300+(int)(Math.random()*50),(int)(300+Math.random()*50));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(test.class);
    public static void main1(String args[]) {



            String cameraId = "rtsp://admin:admin123@192.168.120.111:554";
            String ip = cameraId.substring(cameraId.indexOf("@")+1,cameraId.indexOf(":",cameraId.indexOf("@")));
            System.out.println(ip);

        String regEx="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(cameraId);

        while (m.find()) {
            String result=m.group();
            logger.info("ip 地址"+result);
//break;   加break则提取string中的一个IP
        }


        String className = "//:BOOT-INF.lib.web-1.0-SNAPSHOT.jar!.com.dozenx.web.core.log.action.SysLogTagController";
            int index =-1;
            if((index=className.indexOf("jar!."))!=-1){
                className = className.substring(index+"jar!.".length());
                //:BOOT-INF.lib.web-1.0-SNAPSHOT.jar!.com.dozenx.web.core.log.action.SysLogTagController
            }

            System.out.println(className);


        String wenhao = URLEncoder.encode("?");
        System.out.println(wenhao);

        try {
           Sheet sheet = ExcelUtil.getExcelSheetFromInputStream(new FileInputStream(new File("C:\\Users\\dozen.zhang\\Desktop\\设备采集数据2019.xls")));
            List<Map<Integer,String>> list = ExcelUtil.getListFromSheet(sheet);
            int i=0;
            for(Map<Integer,String> map : list){
                System.out.println(map.get(2));
                //RedisUtil.setex("asdf_"+i,map.get(2),24*60*60);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
