package com.dozenx.web.core.codegen.bean;

//import ch.ethz.ssh2.crypto.digest.MD5;
import com.dozenx.common.util.MD5Util;

/**
 * Created by dozen.zhang on 2017/9/28.
 */
public class SearchBarParser {
    public static void main(String args[]){
        String appid ="b0e94d42";
        String appkey="63f67d4fa3f1";
        String timestamp = ""+new java.util.Date().getTime()/1000;
        String token = null;
        try {
            token = MD5Util.getStringMD5String(appid+"_"+appkey+"_"+timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("timestamp:"+timestamp);
        System.out.println("token:"+token);
        System.out.println("http://beta-np.51awifi.com/appsrv/access_token?appid="+appid+"&timestamp="+timestamp+"&token="+token);
    }
}
