package com.dozenx.net.netty.client;

import com.dozenx.net.netty.cmd.GameCmd;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dozen.zhang on 2017/1/16.
 */
public class ClientEncoder extends MessageToByteEncoder {
    private Logger logger = LoggerFactory.getLogger(ClientEncoder.class);

    public ClientEncoder() {

    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
       // logger.info("开始编码"+in.getClass()+" if" +(in instanceof GameCmd) );
        if(in instanceof GameCmd){
           // logger.info("带编码的对象是gamecmd");
            GameCmd gameCmd =(GameCmd) in;
            byte[] data = gameCmd.toBytes();

            out.writeInt(data.length);
            out.writeBytes(data);
        }
       // out.write(in);
    }
}