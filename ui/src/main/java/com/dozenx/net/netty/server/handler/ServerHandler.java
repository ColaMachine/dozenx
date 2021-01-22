package com.dozenx.net.netty.server.handler;

import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.cmd.CmdType;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.SSCServer;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.bean.Worker;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:56 2018/9/5
 * @Modified By:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      // logger.info("有客户端连接进来");
        super.channelActive(ctx);

       // ctx.channel().attr("login").set(false);
      //  SSCServer.channel = ctx.channel();
        //往channel map中添加channel信息
//        logger.info("准备发送一个GetStatusCmd消息进行测试");
//        ctx.writeAndFlush(new GetStatusCmd());
//        logger.info("GetStatusCmd发送完毕");
        //System.out.println("Active");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8)); //
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务器端接收到了消息");
        if (msg instanceof GameCmd) {
            System.out.println("接收到的消息是gamecmd");
            GameCmd gameCmd = (GameCmd) msg;

            if (gameCmd.getCmdType() == CmdType.RESULT) {
                ResultCmd resultCmd = (ResultCmd) gameCmd;
                int threadId = resultCmd.getThreadId();
                if(threadId>0) {
                    SSCServer.SyncTaskMap.get(threadId).setResult((ResultCmd) gameCmd);
                    SSCServer.unlock(threadId);
                }

            } else /*if(gameCmd.getCmdType()==CmdType.LOGIN)*/ {
                ServerContext serverContext = CoreRegistry.get(ServerContext.class);
                GameServerHandler controller = serverContext.getHandler(gameCmd.getCmdType());

                if (controller != null) {
                    Worker worker =new Worker(serverContext);
                    worker.channel=ctx.channel();
                    ResultCmd resultCmd = controller.handler(new GameServerRequest(gameCmd, worker), new GameServerResponse());
                    if(resultCmd!=null)
                    ctx.write(resultCmd);
//            if (resultCmd != null) {
//                send(resultCmd.toBytes());
//            }
                }
            }
        } else {
            System.out.println("接收到的消息不是gamecmd");
        }

        // ctx.write(msg);
    }

  /*  @Override
    public void channelRead0(ChannelHandlerContext ctx, GameCmd in) throws Exception {
        System.out.println("Read");

        System.out.println("Client received: ");  //#4

    }*/

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Read Complete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);//addListener(ChannelFutureListener.CLOSE);
        //ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        SSCServer.channels.remove(ctx.channel());
        System.out.println("移除channel");
        cause.printStackTrace();
        ctx.close();
    }

}

