package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.util.CmdUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2018/11/19
 * @Modified By:
 */
public class VirtualDoorService {
    private static  Logger logger = LoggerFactory.getLogger(VirtualDoorService.class);
    static Long lastOpenTime =System.currentTimeMillis();
    static int minInterval=7*1000;
    public static void open(){
        if(System.currentTimeMillis()-lastOpenTime<minInterval){
            return;
        }
        logger.info("正在开门");
        String cmd ="./getpsdata";
        try {
            CmdUtil.execCommand(cmd, ConfigUtil.getConfig("door"));//"/data/service/tomcat-kq-8087/door_control"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

    }
}
