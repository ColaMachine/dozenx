package com.dozenx.net.netty.server;

import com.dozenx.common.util.LogUtil;
import com.dozenx.net.netty.client.GameCallBackTask;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.GetCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:24 2018/9/5
 * @Modified By:
 */
public class SSCServer {

    public static List<Channel> channels = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(SSCServer.class);
    //  public static ChannelFuture future;
    public static Map<Integer, GameCallBackTask> SyncTaskMap = new ConcurrentHashMap<Integer, GameCallBackTask>();
    public static Map<Integer, Object> LockMap = new ConcurrentHashMap<Integer, Object>();


    private String host;//服务器host
    private int port;//服务器端口

    private GameCmd response;//

    public static final Object obj = new Object();

    public SSCServer(String host, int port) {
        this.host = host;
        this.port = port;
    }


//    public ChannelFuture getFuture() throws InterruptedException {
//        if (future == null) {
//            LOGGER.error("还没有客户端连进来");
//        }
//        return future;
//    }

    public void send(GameCmd cmd) throws Exception {

//        future = getFuture();
//        future.channel().writeAndFlush(cmd).sync();
        for (Channel channel : channels) {
            channel.writeAndFlush(cmd).sync();
            LOGGER.info("发送消息完毕 开始等待数据返回");
        }

//        if (response != null) {
//            future.channel().closeFuture().sync();
//        }
    }


    public List<ResultCmd> syncSend(GameCmd cmd) {
        try {
            //future = getFuture();
            if (channels == null || channels.size() == 0)
                return null;//客户端还没连进来
            List<ResultCmd> list = new ArrayList<>();

            for (Channel channel : channels) {
                final Integer threadId = (int) (Math.random() * 100000);
                final Object lock = new Object();
                GameCallBackTask task = new GameCallBackTask(lock) {
                    @Override
                    public void run() {
                        LogUtil.println("进入恢复任务");
                        synchronized (lock) {
                            LogUtil.println("通知线程恢复");
                            lock.notifyAll(); // 收到响应，唤醒线程
                        }
                    }
                };
                LockMap.put(threadId, lock);
                SyncTaskMap.put(threadId, task);
                GetCmd sendCmd = new GetCmd(cmd, threadId);
                byte[] data = sendCmd.toBytes();


                channel.writeAndFlush(sendCmd).sync();
                channel.writeAndFlush('\n');


                synchronized (lock) {
                    LOGGER.info("挂起线程 threadid:" + threadId);
                    lock.wait(51000); // 未收到响应，使线程等待  最多等待多少秒
                }
                LOGGER.info("线程恢复");
                //清除task
                SyncTaskMap.remove(threadId);
                LockMap.remove(threadId);
                list.add(task.getResult());

            }
            return list;
            //}
        } catch (Exception e) {
            LOGGER.info("", e);
            e.printStackTrace();
        }
        return null;
    }

    public static void unlock(int threadId) {
        Object lock = LockMap.get(threadId);
        if (lock != null) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }


//    public static void main(String args[]) {
//        try {
//            new SSCServer(ConfigUtil.getConfig("serverAddress"), Integer.valueOf(ConfigUtil.getConfig("serverPort"))).start();
//        } catch (Exception e) {
//            LOGGER.info("", e);
//            e.printStackTrace();
//        }
//        //logger.info();
//    }

    /*public SSCServer() {

        SSCServer sscServer = new SSCServer("127.0.0.1", Integer.valueOf(ConfigUtil.getConfig("serverPort")));

        new Thread() {
            public void run() {
                try {
                    sscServer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.info("", e);
                }
            }
        }.start();


        CoreRegistry.put(SSCServer.class, sscServer);

        new ServerContext().init();
    }*/

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new ServerEncoder()) // 将 RPC 响应进行编码（为了返回响应）
                                    .addLast(new ServerDecoder()) // 将 RPC 请求进行解码（为了处理请求）/

                                    .addLast(new ServerHandler()); // 处理 RPC 请求
                            // .addLast(new EchoServerHandler());
                            //链式处理 先decoder 请求 拿到  请求的名称/类型 参数 再调用handler 处理请求 再调用encoder把结果返回给请求方
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.DEBUG));
            //   .childOption(ChannelOption.SO_KEEPALIVE, true);

//            String[] array = serverAddress.split(":");
//            String host = array[0];
//            int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(port).sync();

            LOGGER.info("server started on port {}", port);

//            if (serviceRegistry != null) {
//                serviceRegistry.register(serverAddress); // 注册服务地址
//            }

            future.channel().closeFuture().sync();

        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }


//
}
