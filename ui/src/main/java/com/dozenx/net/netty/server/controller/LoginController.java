package com.dozenx.net.netty.server.controller;

import com.dozenx.common.util.StringUtil;
import com.dozenx.net.netty.cmd.GameCmd;
import com.dozenx.net.netty.cmd.LoginCmd;
import com.dozenx.net.netty.cmd.ResultCmd;
import com.dozenx.net.netty.server.GameServerRequest;
import com.dozenx.net.netty.server.GameServerResponse;
import com.dozenx.net.netty.server.SSCServer;
import com.dozenx.net.netty.server.ServerContext;
import com.dozenx.net.netty.server.handler.GameServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luying on 17/2/18.
 */
public class LoginController extends GameServerHandler {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    //    private UserService userService ;
//    private BagService bagService;
//    private EnemyService enemyService;
    public LoginController(ServerContext serverContext) {
        super(serverContext);
//        bagService = (BagService)serverContext.getService(BagService.class);
//        enemyService =(EnemyService)serverContext.getService(EnemyService.class);
//        userService =(UserService)serverContext.getService(UserService.class);
    }

    public ResultCmd handler(GameServerRequest request, GameServerResponse response) {
        logger.info("server接收到登录请求了");
        GameCmd cmd = request.getCmd();
        LoginCmd loginCmd = (LoginCmd) cmd;
        String userName = loginCmd.getUserName();
        String pwd = loginCmd.getPwd();
        if (StringUtil.isBlank(userName)) {
            return new ResultCmd(32123, "用户名/密码不能为空", loginCmd.getThreadId());
        } else {
            if (userName.equals("robot") && "sjdfly1".equals(loginCmd.getPwd())) {//如果密码和用户名都对了 就建立连接
                logger.info("登录成功");
                SSCServer.channels.add(request.getWorker().channel);//.getWorker();  做这一步是为了让服务器可以主动发送小心给客户端
                return new ResultCmd(1, "登录成功", loginCmd.getThreadId());
            } else {
                return new ResultCmd(15551, "密码错误", loginCmd.getThreadId());
            }
        }

    }
}
