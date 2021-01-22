package com.dozenx.net.netty.client;

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
public class ClientDecoder extends ByteToMessageDecoder {
    private Logger logger = LoggerFactory.getLogger(ClientEncoder.class);


    public ClientDecoder() {

    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //logger.info("开始解码");
        if (in.readableBytes() < 4) {//这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            logger.info("不足4个字节");
            return;
        }
        try {
            in.markReaderIndex();//我们标记一下当前的readIndex的位置
            int dataLength = in.readInt(); // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
            if (dataLength < 0) {// 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
                ctx.close();
            }
            if (in.readableBytes() < dataLength) {//读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
                logger.info("resetReaderIndex");
                in.resetReaderIndex();
            }
            byte[] data = new byte[dataLength];
            in.readBytes(data);

            GameCmd cmd = CmdUtil.getCmd(data);
            if (cmd != null)
                out.add(cmd);
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }

    }
}