package com.dozenx.web.module.checkin.faceInfo.service;

import com.dozenx.common.util.CmdUtil;
import com.dozenx.common.util.DateUtil;
import com.dozenx.web.util.RedisUtil;

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
