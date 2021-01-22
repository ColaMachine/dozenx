package com.dozenx.net.netty.cmd;

import com.dozenx.common.net.ByteBufferWrap;
import com.dozenx.common.util.ByteUtil;
import com.dozenx.common.util.LogUtil;
/**
 * Created by luying on 17/2/7.
 */
public class EndTaskCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.EndTaskCmd;



    // userId | length| username:msg|
    public EndTaskCmd(byte[] bytes){

        parse(bytes);
    }
    public EndTaskCmd(){


    }


    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
              .array();

    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();


    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }


}
