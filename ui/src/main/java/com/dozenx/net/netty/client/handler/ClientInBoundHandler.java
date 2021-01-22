package com.dozenx.net.netty.client.handler;

import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.client.SSCClient;
import com.dozenx.net.netty.cmd.CmdType;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:56 2018/9/5
 * @Modified By:
 */
public class ClientInBoundHandler extends io.netty.channel.ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInBoundHandler.class);

    public static Map<Integer, Object> LockMap = new ConcurrentHashMap<Integer, Object>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info("CLIENT111111111" + getRemoteAddress(ctx) + " 连上服务器了");
        //往channel map中添加channel信息
        SSCClient.getWorkerMap().put(getIPString(ctx), ctx.channel());


    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        logger.info("接收到信息：" );
        if (msg instanceof GameCmd) {
            //logger.info("接收到的消息是gamecmd");
            GameCmd gameCmd = (GameCmd) msg;
            if (gameCmd.getCmdType() == CmdType.RESULT) {
                ResultCmd resultCmd = (ResultCmd) gameCmd;
                int threadId = resultCmd.getThreadId();
                if (threadId > 0) {
                    SSCClient.SyncTaskMap.get(threadId).setResult((ResultCmd) gameCmd);
                    SSCClient.unlock(threadId);
                }
            } else /*if(gameCmd.getCmdType()==CmdType.LOGIN)*/ {
                ServerContext serverContext = CoreRegistry.get(ServerContext.class);
                GameServerHandler controller = serverContext.getHandler(gameCmd.getCmdType());

                if (controller != null) {
                    ResultCmd resultCmd = controller.handler(new GameServerRequest(gameCmd, null), new GameServerResponse());
                    //logger.info("开始回写消息");
                    ctx.write(resultCmd);
//            if (resultCmd != null) {
//                send(resultCmd.toBytes());
//            }
                }
            }
        } else {
            logger.info("接收到的消息不是gamecmd：");
        }
        // ctx.write(resultCmd)/*.addListener(ChannelFutureListener.CLOSE)*/;
        // ReferenceCountUtil.release(msg);
        // ctx.write("\n");
        // ctx.write(WebRobotConstants.end);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // logger.info("读完了");
        //  ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        ctx.flush();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client

        logger.error("连接断开了");
        SSCClient.getWorkerMap().remove(getIPString(ctx));
        ctx.close();
    }


    //    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameCmd cmd)
            throws Exception {
        logger.info("来自设备的信息：" + cmd.toString());


//        controller.handler(new GameServerRequest(cmd, new Worker(CoreRegistry.get(ServerContext.class))), new GameServerResponse());

//        if (cmd.getCmdType() == CmdType.StartTaskCmd) {
//            //启动任务
//            CoreRegistry.get(RobotEngine.class).changeState(new RunState());
//        } else if (cmd.getCmdType() == CmdType.EndTaskCmd) {
//            CoreRegistry.get(RobotWoker.class).finished = true;
//        } else if (cmd.getCmdType() == CmdType.GetStatus) {
//            RobotWoker robotWoker = CoreRegistry.get(RobotWoker.class);
//            long lastTime = System.currentTimeMillis() - robotWoker.startTime;
//            int finedTask = robotWoker.finshedTask;
//            boolean finshed = robotWoker.finished;
//            resultCmd = new ResultCmd(1, lastTime + ";" + finedTask + ";" + finshed, 0);
//        }

        ResultCmd resultCmd = new ResultCmd(1, "", 1);
        ctx.writeAndFlush(resultCmd);//addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                logger.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }

        logger.info("userEventTriggered：");
    }

    public static String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }


    private String getKeyFromArray(byte[] addressDomain) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            sBuffer.append(addressDomain[i]);
        }
        return sBuffer.toString();
    }

    protected String to8BitString(String binaryString) {
        int len = binaryString.length();
        for (int i = 0; i < 8 - len; i++) {
            binaryString = "0" + binaryString;
        }
        return binaryString;
    }

    protected static byte[] combine2Byte(byte[] bt1, byte[] bt2) {
        byte[] byteResult = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, byteResult, 0, bt1.length);
        System.arraycopy(bt2, 0, byteResult, bt1.length, bt2.length);
        return byteResult;
    }


}

