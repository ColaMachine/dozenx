package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.util.CmdUtil;
import com.dozenx.util.DateUtil;
import com.dozenx.web.util.RedisUtil;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:35 2018/11/19
 * @Modified By:
 */
public class VirtualWeixinService {

    public static void sendMsg(String userName ,String msg){

        RedisUtil.lpush("checkinList", userName + " "+msg.trim() );

    }

    public static void main(String args[]){

    }
}
