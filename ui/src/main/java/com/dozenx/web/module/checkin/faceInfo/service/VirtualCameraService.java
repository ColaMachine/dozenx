package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.util.CmdUtil;

import java.io.IOException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2018/11/19
 * @Modified By:
 */
public class VirtualCameraService {

    public static void capture(){
        String cmd ="./getpsdata";
        try {
            CmdUtil.execCommand(cmd,"/data/service/tomcat-kq/door_control");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

    }
}
