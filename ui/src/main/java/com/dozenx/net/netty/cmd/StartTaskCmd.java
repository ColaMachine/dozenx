package com.dozenx.net.netty.cmd;

import com.dozenx.common.net.ByteBufferWrap;
import com.dozenx.common.util.ByteUtil;
import com.dozenx.common.util.LogUtil;

/**
 * Created by luying on 17/2/7.
 */
public class StartTaskCmd extends   BaseGameCmd{
    final CmdType cmdType = CmdType.StartTaskCmd;
    String params;


    // userId | length| username:msg|
    public StartTaskCmd(byte[] bytes){

        parse(bytes);
    }
    public StartTaskCmd( String params){

        this.params = params;

    }


    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
                .putLenStr(this.getParams()).array();

    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.params = byteBufferWrap.getLenStr();

    }
    public String getParams() {
        return params;
    }


    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

    public static void main(String args[]){

       // ByteBuf byteBuf = direct
//        ByteBuffer buffer = ByteBuffer.allocate(1256).order(ByteOrder.nativeOrder());
//        buffer.putInt(0);
//        buffer.putInt(1);
//        buffer.flip();
        ByteBufferWrap byteBufferWrap =new ByteBufferWrap();
        byteBufferWrap.put(1);
       // byteBufferWrap.flip();
        byte[] data =byteBufferWrap.array();

         byteBufferWrap = ByteUtil.createBuffer(data);
      //  byteBufferWrap.flip();
       // data = byteBufferWrap.array();
        //byteBufferWrap.flip();
        //byteBufferWrap.getInt();
        System.out.println(byteBufferWrap.getInt());
      //  System.out.println(ByteOrder.nativeOrder());

      //  System.out.print(buffer.getInt());
    }
}
