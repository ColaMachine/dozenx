package com.dozenx.net.netty.client;

import com.dozenx.common.util.LogUtil;
import com.dozenx.net.netty.CoreRegistry;
import com.dozenx.net.netty.client.handler.ClientInBoundHandler;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.GetCmd;
import com.dozenx.net.netty.cmd.LoginCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.spi.ServiceRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:38 2018/9/5
 * @Modified By:
 */
public class SSCClient /*implements ApplicationContextAware, InitializingBean*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSCClient.class);

    public static ChannelFuture future;

    private static Map<String, Channel> workerMap = new ConcurrentHashMap<String, Channel>();
    private static Map<String, byte[]> messageMap = new ConcurrentHashMap<String, byte[]>();

    public static Map<String, Channel> getWorkerMap() {
        return workerMap;
    }

    public static Map<Integer, GameCallBackTask> SyncTaskMap = new ConcurrentHashMap<Integer, GameCallBackTask>();
    public static Map<Integer, Object> LockMap = new ConcurrentHashMap<Integer, Object>();


    private ServiceRegistry serviceRegistry;

    private Map<String, Object> handlerMap = new HashMap<>(); // 存放接口名与服务对象之间的映射关系
    private String host;//服务器host
    private int port;//服务器端口

    public SSCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ChannelFuture connect() throws InterruptedException {
        try {

//           if (future == null) {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {

//                            channel.pipeline().addLast(new ClientHandler());//
                            channel.pipeline() //.addLast(new LengthFieldBasedFrameDecoder(1024*1024*1024, 0, 4,0,4))

                                    .addLast(new ClientEncoder()) // 将 RPC 响应进行解码（为了处理响应）
                                    .addLast(new ClientDecoder()) // 将 RPC 请求进行编码（为了发送请求）
                                    .addLast(new ClientInBoundHandler()) ;// 使用 RpcClient 发送 RPC 请求
                          //  .addLast(new IdleStateHandler(120, 10, 0, TimeUnit.SECONDS)).addLast(new HeartBeatReqHandler());
                        }
                    }) .option(ChannelOption.SO_BACKLOG, 128) .option(ChannelOption.SO_KEEPALIVE,true);
               /*.handler(new ClientDecoder()).handler(new ClientEncoder()) */ //.handler(new LoggingHandler(LogLevel.INFO));
            //.option(ChannelOption.SO_KEEPALIVE, true);
            future = bootstrap.connect(host, port).sync();
            ResultCmd result = syncSend(new LoginCmd("robot","sjdfly1",1));
            LOGGER.info(new String(result.getMsg()));
            if(result.getResult()==1){

            }
           // future.channel().closeFuture().sync();
            //LOGGER.info("关闭了");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        CoreRegistry.put(SSCClient.class,this);
        return future;
    }


   /* @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        //初始化

    }*/

    //
    public static void main(String args[]) throws Exception {
        // new SSCClient("192.168.120.80",5555).send().start();
        try {
            SSCClient client = new SSCClient("192.168.120.80", 5555); // 初始化 RPC 客户端
            client.connect();
//            ResultCmd cmd =  client.syncSend(new StartTaskCmd("hello"));
          /*  ResultCmd cmd =*/
            client.send(new ResultCmd(1,"hello",123));
         /*   if (cmd != null) {
                System.out.println(new String(cmd.getMsg()));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void send(GameCmd cmd) throws Exception {

//        future = getFuture();
        future.channel().writeAndFlush(cmd);

//        LOGGER.info("发送消息完毕 开始等待数据返回");
        // if (response != null) {

        // }
    }


    public ResultCmd syncSend(GameCmd cmd) {
        try {
//            future = getFuture();
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
            ByteBuf byteBuf = Unpooled.directBuffer(16);
            byteBuf.writeBytes(data);
          future.channel().writeAndFlush(sendCmd).sync();
//            ByteBuf encoded = future.channel().alloc().buffer(sendCmd.toBytes().length);
//            encoded.writeInt(sendCmd.toBytes().length).writeBytes(sendCmd.toBytes());
//            future.channel().writeAndFlush(encoded).sync();
           // future.channel().writeAndFlush(sendCmd.toBytes()).sync();
            synchronized (lock) {
                LogUtil.println("挂起线程 threadid:" + threadId);
                lock.wait(51000); // 未收到响应，使线程等待  最多等待多少秒
            }
            LogUtil.println("线程恢复");
            //清除task
            SyncTaskMap.remove(threadId);
            LockMap.remove(threadId);
            return task.getResult();
            //}
        } catch (Exception e) {
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
}