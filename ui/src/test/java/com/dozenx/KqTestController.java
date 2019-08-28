package com.dozenx;

import com.cpj.swagger.util.ReflectUtils;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.ImageUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:48 2019/3/29
 * @Modified By:
 */
public class KqTestController {
    private Logger logger = LoggerFactory.getLogger(KqTestController.class);
    @Test
    public   void testOpeendoor(){
        String url="http://192.168.213.7:8097/home/checkin/faceinfo/recognize";
        HashMap map =new HashMap();
        // map.put("data",URLEncoder.encode(ImageUtil.ImageToBase64ByLocal(PathManager.getInstance().getHomePath().resolve("src/main/webapp/upload/1543074868872.png").toString())));

        map.put("data", URLEncoder.encode(ImageUtil.ImageToBase64ByLocal("G:\\kq-workspace\\人脸资料\\TIM图片20190305094529.jpg")));
        map.put("camera","7098");

        String result =  HttpRequestUtil.sendPost(url,map);
        System.out.println(result);
    }

    @Test
    public void testScan(){
        List<String> list =new ArrayList();
        list.add("com.dozenx.web.**.*Controller");


        try {
            List<Class<?>> classes = ReflectUtils.newScanClazzs(list);
            System.out.println(classes.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Test
    public   void testFufu() throws Exception{
        OkHttpClient mOkHttpClient;
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor)
                .build();
        FormBody requestBody = new FormBody.Builder()
                .add("image",ImageUtil.ImageToBase64ByLocal("G:\\kq-workspace\\人脸资料\\mmexport1543151210973.jpg"))
                .add("taskId","123")
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.200.51:8084/api/ai/antenna")
                .post(requestBody)
                .build();

        Response response =  mOkHttpClient.newCall(request).execute();
        if(response.isSuccessful()){
            //响应成功
            logger.info(response.toString());
        }

    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            if(message.length()>100000){
                message=message.substring(0,1000);
            }
            System.out.println( message);
        }
    }


}
