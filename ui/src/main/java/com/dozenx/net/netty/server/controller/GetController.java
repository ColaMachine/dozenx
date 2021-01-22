package com.dozenx.net.netty.server.controller;


import com.dozenx.common.util.LogUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.net.netty.cmd.*;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.SSCServer;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;

/**
 * Created by luying on 17/2/18.
 */
public class GetController extends GameServerHandler {

    public GetController(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        GetCmd cmd = (GetCmd)request.getCmd();


        byte[] data = cmd.getData();
        GameCmd childCmd = CmdUtil.getCmd(data);


        GameServerHandler handler = serverContext.getHandler(childCmd.getCmdType());
        if(handler!= null ){
            ResultCmd resultCmd =handler.handler(new GameServerRequest(childCmd,request.getWorker()),new GameServerResponse());
            if(resultCmd!=null){
                resultCmd.setThreadId(cmd.getThreadId());
//                LogUtil.println("接收到线程id:"+cmd.getThreadId());

                return   resultCmd;
            }else{
                LogUtil.err("resultCmd is null");
            }
        }else{
            LogUtil.err("handler is null");
        }
        return new ResultCmd(1, "失败",cmd.getThreadId());

        //更新其他附近人的此人的装备属性

    }

}
