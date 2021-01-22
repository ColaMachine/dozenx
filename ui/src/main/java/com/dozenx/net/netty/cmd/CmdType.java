package com.dozenx.net.netty.cmd;



import com.dozenx.net.netty.server.controller.*;
import com.dozenx.net.netty.server.handler.GameServerHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum CmdType {
    StartTaskCmd(StartTaskCmd.class, StartTaskController.class),//0
    EndTaskCmd(EndTaskCmd.class, EndTaskController.class),//0
    GET(GetCmd.class, GetController.class),
    GetStatus(GetStatusCmd.class, GetStatusController.class),//0
    LOGIN(LoginCmd.class, LoginController.class),//3
    RESULT(ResultCmd.class),//1
    MSG(MsgCmd.class),//2
    LOG(LogCmd.class,LogController.class),//2
    ;

    /*public CmdType(class<T> extends BaseGameCmd,class<T> extends GameServerHandler){


    }*/


    public static Map<Integer, Class<? extends BaseGameCmd>> idToClassMap = new HashMap<>();

    CmdType(Class<? extends BaseGameCmd> clz, Class<? extends GameServerHandler> handler) {
        this.cmdClass = clz;
        this.gameHandlerClass = handler;

    }

    static {

        for (CmdType cmdType : CmdType.values()) {
            idToClassMap.put(cmdType.ordinal(), cmdType.cmdClass);
        }


    }

    public Class cmdClass;
    public Class gameHandlerClass;

    CmdType(Class<? extends BaseGameCmd> cmdClass) {
        this.cmdClass = cmdClass;
    }

    public int getType() {
        return this.ordinal();
    }


    public static void printSend(GameCmd type) {
      /*  if(type.getCmdType() != CmdType.POS){
            LogUtil.println("send "+type.getCmdType());
        }*/
    }

    public static void printReceive(GameCmd type) {
       /* if(type.getCmdType() != CmdType.POS) {
            LogUtil.println("receive " + type.getCmdType());
        }*/
    }

    public static void printSend(byte[] bytes) {
        //LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes)]);
       /* if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
            LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes) ] +" content"+bytes.length);
        }*/
    }

    public static void printReceive(byte[] bytes) {
        /*if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
           // LogUtil.println("receive "+CmdType.values()[ByteUtil.getInt(bytes)]+" content"+bytes.length);
        }*/

    }
}

