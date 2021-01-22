package com.dozenx.net.netty.server.controller;

import com.dozenx.net.netty.cmd.LogCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 17/2/18.
 */
public class LogController extends GameServerHandler {
    private static Logger logger = LoggerFactory.getLogger("json");

    public LogController(ServerContext serverContext) {
        super(serverContext);
    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {


        LogCmd logCmd = (LogCmd) request.getCmd();
        logger.info(logCmd.msg);
       // System.out.println(logCmd.msg);

        return null;

    }
}
