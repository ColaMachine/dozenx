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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 17/2/18.
 */
public class EndTaskController extends GameServerHandler {
    private static final Logger logger = LoggerFactory.getLogger(EndTaskController.class);
    public EndTaskController(ServerContext serverContext) {
      super(serverContext);

    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {
//        CoreRegistry.get(RobotWoker.class).finished = true;
        logger.info("完成任务");
        return new ResultCmd(1,"成功",1);
    }

}
