package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.util.CmdUtil;
import com.dozenx.web.util.ConfigUtil;

import java.io.IOException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2018/11/19
 * @Modified By:
 */
public class VirtualDoorService {

    public static void open(){
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
