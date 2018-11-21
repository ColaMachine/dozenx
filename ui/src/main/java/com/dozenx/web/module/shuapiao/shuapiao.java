package com.dozenx.web.module.shuapiao;

import com.dozenx.util.StringUtil;
import com.dozenx.util.idcard.IdCardVerification;

import java.io.*;
import java.net.URLEncoder;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:05 2018/10/26
 * @Modified By:
 */
public class shuapiao {

    public static void main(String args[]){


       // String url ="http://z.hangzhou.com.cn/2018/hbzjtp/index.htm?from=groupmessage&isappinstalled=0";

        String url ="http://cloud.hangzhou.com.cn/vote//tpx_vote.php?vlist161%5B%5D=3215&name={name}&myid={id}&mobile={mobile}&zhaonum161=1&fuck161%5B%5D=3215&vt_button=+%E6%8A%95+%E7%A5%A8+&vt_btnsumbit=yes&voteid=161&isluan=%E6%98%AF&vt_my=";
        File file =new File("G:\\zzw\\2000W\\最后5000.csv");

        try {

            FileReader fr = new FileReader("G:\\zzw\\2000W\\最后5000.csv");
            BufferedReader bf =new BufferedReader(fr);

            bf.readLine();
            while(true){
                String txt =bf.readLine();
                if(txt==null){
                    break;
                }
                String arg[]=txt.split(",");
                String name =arg[0];
                String id =arg[4];

                String mobile = arg[19];
                if(!StringUtil.isPhone(mobile)){
                    continue;
                }
                if(StringUtil.isBlank(name) || StringUtil.isBlank(id) || StringUtil.isBlank(mobile)){
                    continue;
                }

                if(id.length()<=17){
                    continue;
                }


                if(IdCardVerification.INVALIDCALIBRATION.equals(IdCardVerification.IDCardValidate(id))){
                    id= IdCardVerification.getRightId(id.substring(0,17),id);
                }
                if(!IdCardVerification.VALIDITY.equals(IdCardVerification.IDCardValidate(id))){
                   continue;
                }



                String newUrl = url.replace("{name}", URLEncoder.encode(name)).replace("{id}",id).replace("{mobile}",mobile);
                System.out.println("<a href=\""+newUrl+"\">"+newUrl+"</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
