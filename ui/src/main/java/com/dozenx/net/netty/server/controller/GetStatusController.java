package com.dozenx.net.netty.server.controller;


import com.dozenx.common.util.StringUtil;
import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.LoginCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.SSCServer;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;

import java.util.HashMap;

/**
 * Created by luying on 17/2/18.
 */
public class GetStatusController extends GameServerHandler {

    public GetStatusController(ServerContext serverContext) {
        super(serverContext);

    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {
//        RobotWoker robotWoker = CoreRegistry.get(RobotWoker.class);
//
//
//        int completeMIssion = 0;
//        String missionCode = "";
//        int totalCount = 0;
//        String status = "stop";
//        Long longTime = 0l;
//        Long totalRunTime = 0l;
//        if (robotWoker != null) {
//            longTime = (System.currentTimeMillis() - robotWoker.startTime) / 1000;
//         //   longTime = System.currentTimeMillis() - robotWoker.startTime;
//            int finedTask = 0;
//            boolean finshed = false;
//
//            if (robotWoker.planPaymentAndPayment != null && robotWoker.planPaymentAndPayment.getBaseInfo() != null) {
//                missionCode = robotWoker.planPaymentAndPayment.getBaseInfo().getAccountNumber();
//            }
//
//            completeMIssion = robotWoker.finshedTask<0?0:robotWoker.finshedTask;
//
//            finshed = robotWoker.finished;
//            status = finshed?"stop":"running";
//        }
////        "data": {
////            "longTime": 1300, //单位秒  运行时间
////                    "completeMIssion": 11,//处理完多少任务
////                    "missionCode": "123123",//正在处理的单号
////                    "totalCount":123,//累计处理任务量
////                    "status":"running"/"stop" , //运行状态
////                    "totalRunTime":1233 //累计运行时间 单位秒
////        }
////    }
//        HashMap resultMap = new HashMap<>();
//        resultMap.put("longTime", longTime);
//        resultMap.put("completeMIssion", completeMIssion);
//        resultMap.put("missionCode", missionCode);
//        resultMap.put("totalCount", totalCount);
//        resultMap.put("status", status);
//        resultMap.put("totalRunTime", (System.currentTimeMillis() - SSCRobotEngine.startTime) / 1000);
//        String json = JsonUtil.toJsonString(resultMap);
//        ResultCmd resultCmd = new ResultCmd(1, json, 0);
        return null;
    }

}
