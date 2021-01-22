package com.dozenx.net.netty.server;

import com.dozenx.net.netty.cmd.CmdUtil;
import com.dozenx.net.netty.cmd.GameCmd;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dozen.zhang on 2017/1/16.
 */
public class ServerDecoder extends ByteToMessageDecoder {
    private Logger logger = LoggerFactory.getLogger(ServerEncoder.class);


    public ServerDecoder() {

    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        logger.info("开始解码");

        if (in.readableBytes() < 4) {
            //logger.info("不足4个字节:" + in.readableBytes());
            return;
        }
        try {
            in.markReaderIndex();//标记当前的位置
            int dataLength = in.readInt();
            if (dataLength < 0) {
                ctx.close();
            }
//            if (in.readableBytes() < dataLength) {
//                in.resetReaderIndex();
//            }

            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();//回到之前标记的位置
                //缓存当前剩余的buffer数据，等待剩下数据包到来
                return;
            }

            byte[] data = new byte[dataLength];
            in.readBytes(data, 0, dataLength);

            GameCmd cmd = CmdUtil.getCmd(data);
            if (cmd != null)
                out.add(cmd);
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }

    }
}