package com.dozenx.net.netty.cmd;

import com.dozenx.common.net.ByteBufferWrap;
import com.dozenx.common.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class GetStatusCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.GetStatus;



    // userId | length| username:msg|
    public GetStatusCmd(byte[] bytes){

        parse(bytes);
    }
    public GetStatusCmd(){


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
