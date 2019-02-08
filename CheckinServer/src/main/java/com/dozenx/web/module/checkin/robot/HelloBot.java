package com.dozenx.web.module.checkin.robot;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.PropertiesUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.RedisUtil;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.Account;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.DateUtils;
import io.github.biezhi.wechat.utils.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:48 2018/10/10
 * @Modified By:
 */
public class HelloBot extends WeChatBot {
    //https://biezhi.github.io/wechat-api/#/?id=%E5%88%9B%E5%BB%BA%E6%9C%BA%E5%99%A8%E4%BA%BA
    private static final Logger logger = LoggerFactory.getLogger(HelloBot.class);

    public HelloBot(Config config) {

        super(config);
       new ConfigUtil().loadProperty();


    }

    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
           // logger.info("接收到 [{}] 的消息: {}", message.getName(), message.getText());

//            if (message.getFromUserName().equals("@ff89f7940737c5a1c4f9aebccf2b269e3a3af343a97a19bbdfd38a99177e8017")) {
//                this.sendMsg(message.getFromUserName(), "自动回复:你在说什么呢 " + message.getText());
//            }
            // this.sendMsg(message.getFromUserName(), "自动回复:你在说什么呢 " + message.getText());
        }
    }

    @Bind(msgType = MsgType.IMAGE)
    public void handleImg(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
           // logger.info("接收到 [{}] 的消息: {}", message.getName(), message.getImagePath());
            // this.sendMsg(message.getFromUserName(), "自动回复:你在说什么呢 " + message.getText());
        }
    }

    @Override
    protected void other() {
        while (true) {

            String txt = RedisUtil.rpop("checkinList");
            if (StringUtil.isNotBlank(txt)) {
                logger.info(txt);
                String[] ary = txt.split(" ");

                if (ary.length > 1) {
                    if(ary[0].startsWith("@")){
                     /*   Account account  = this.api().getAccountById(ary[0]);
                        if (account != null) {
                            logger.info("发送" + ary[0] + "消息" + ary[1]);
                            this.sendMsg(ary[0], ary[1]);
                        }*/
                        this.sendMsg(ary[0], ary[1]);
                    }else {
                        Account account = this.api().getAccountByName(ary[0]);
                        if (account != null) {
                            logger.info("发送" + ary[0] + "消息"+ account.getUserName() + ary[1]);
                            this.sendMsg(account.getUserName(), ary[1]);
                        }
                    }
                }
            }else{
                DateUtils.sleep(1000L);//只有空闲的时候才减速
            }
//            Scanner scanner = new Scanner(System.in);
//            try {
//                if (scanner.hasNext()) {
//                    String text = scanner.next();
//                    if ("say".equals(text)) {
//                        //  String[] ary = text.split(" ");
//                        //say 修*远 123
//
//                        Account account = this.api().getAccountByName(scanner.next());
//                        this.sendMsg(account.getUserName(), scanner.next());
//                    } else if ("list".equals(text)) {
//                        String[] ary = text.split(" ");
//                        this.sendMsg(ary[1], ary[2]);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }



        }
    }
//    private static void InitLog4jConfig() {
//        Properties props = null;
//        FileInputStream fis = null;
//        try {
//            // 从配置文件dbinfo.properties中读取配置信息
//            props = new Properties();
//            System.out.println(PathManager.getInstance().getClassPath().resolve("log4j.properties").toAbsolutePath());
//            fis = new FileInputStream(PathManager.getInstance().getClassPath().resolve("log4j.properties").toFile());
//            props.load(fis);
//            PropertyConfigurator.configure(props);//装入log4j配置信息
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null)
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            fis = null;
//        }
//    }
    public static void main(String[] args) {

      //  new ConfigUtil().loadProperty();
       // System.out.println(ConfigUtil.getConfig("cache.redis.ip"));
        //InitLog4jConfig();

        HelloBot helloBot = new HelloBot(Config.me().autoLogin(true).showTerminal(true));
        helloBot.start();
        //后面不会执行到 因为内部有while无限循环方法在执行中
//        while (true) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (helloBot.isRunning()) {
//                helloBot.sendMsg("萤火虫", "现在时间" + DateUtil.getNowStringDate());
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
