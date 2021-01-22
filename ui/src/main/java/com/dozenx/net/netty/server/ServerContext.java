package com.dozenx.net.netty.server;


import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.cmd.CmdType;
import com.dozenx.net.netty.server.handler.GameServerHandler;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class ServerContext {

    public ServerContext(){


    }

    public void init(){


        CoreRegistry.put(ServerContext.class,this);

        for(CmdType cmd :CmdType.values()){
            try {
                if(cmd.gameHandlerClass!=null) {//一个cmd 类型 管理一个handler 实例 实现singletone 模式
                    Constructor c = cmd.gameHandlerClass.getConstructor(ServerContext.class);
                    this.registerHandler(cmd, (GameServerHandler) c.newInstance(this));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    //在服务器端的时候什么时候初始化
    private Map<CmdType, GameServerHandler> allHandlerMap =new HashMap<>();

    public void registerHandler(CmdType type,GameServerHandler handler ){
        allHandlerMap.put(type,handler);
    }

    public GameServerHandler getHandler(CmdType type){
        return allHandlerMap.get(type);
    }

}
