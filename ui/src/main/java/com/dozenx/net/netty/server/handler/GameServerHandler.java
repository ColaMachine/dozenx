package com.dozenx.net.netty.server.handler;


import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.ServerContext;

/**
 * Created by luying on 17/2/18.
 */
public class GameServerHandler {
    public ServerContext serverContext;
    public GameServerHandler(ServerContext serverContext){
        this.serverContext =serverContext;
    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response){
        return null;
    }
}
