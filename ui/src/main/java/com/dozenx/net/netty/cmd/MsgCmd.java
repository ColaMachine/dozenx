package com.dozenx.net.netty.cmd;

import com.dozenx.common.net.ByteBufferWrap;
import com.dozenx.common.util.ByteUtil;
import com.dozenx.common.util.LogUtil;

/**
 * Created by luying on 17/2/7.
 */
public class MsgCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.MSG;
    String msg   ;


    public MsgCmd(byte[] bytes){

        parse(bytes);
    }
    public MsgCmd(String msg){


        this.msg = msg;

    }


    //|result|length|msg|threadId|
    public byte[] toBytes(){
//        LogUtil.println(JSON.toJSONString(this));
        return ByteUtil.createBuffer().put(cmdType.getType()).putLenStr(msg).array();
    }


    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.msg = byteBufferWrap.getLenStr();

    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

}
