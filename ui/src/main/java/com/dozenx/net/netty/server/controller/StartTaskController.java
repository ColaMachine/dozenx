package com.dozenx.net.netty.server.controller;

import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.LogUtil;
import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.cmd.StartTaskCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;

/**
 * Created by luying on 17/2/18.
 */
public class StartTaskController extends GameServerHandler {

    public StartTaskController(ServerContext serverContext) {
      super(serverContext);

    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {
        StartTaskCmd cmd = (StartTaskCmd) request.getCmd();
        LogUtil.println("server接收到说话了 " + ((StartTaskCmd) request.getCmd()).getParams());
        String params = ((StartTaskCmd) request.getCmd()).getParams();
        //将客户端的robotWorker的 filterBean设置为当前值 但是有一个问题是如果任务还没有开始的情况下 robot worker 还没事创建成功了
       // CoreRegistry.get(RobotWoker.class).filterBean= JsonUtil.toJavaBean(params, FilterBean.class);

//        FilterBean filterBean = JsonUtil.toJavaBean(params, FilterBean.class);// 从服务器传过来的参数转换成 filterBean
//        CoreRegistry.put(FilterBean.class ,filterBean);
//        CoreRegistry.get(RobotEngine.class).changeState(new RunState());



        return new ResultCmd(1,"",1);
    }

}
