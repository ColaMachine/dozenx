package com.dozenx.net.netty.server;

import com.dozenx.net.netty.cmd.GameCmd;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dozen.zhang on 2017/1/16.
 */
public class ServerEncoder  extends MessageToByteEncoder {
    private Logger logger = LoggerFactory.getLogger(ServerEncoder.class);

    public ServerEncoder() {

    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
//        logger.info("开始编码");
        if(in instanceof GameCmd){
           // logger.info("带编码的消息是gamecmd 开始编码");
            GameCmd gameCmd =(GameCmd) in;
            byte[] data = gameCmd.toBytes();

//            logger.info("消息长度"+data.length);
            out.writeInt(data.length);
            out.writeBytes(data);
           // logger.info("发送完成");
            return;
        }else {
            out.clear();
        }
    }
}