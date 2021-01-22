package com.dozenx.web.util;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


@ConfigurationProperties(prefix = "config")
//@Data
@Component
//@RefreshScope
public class SpringInitUtil implements InitializingBean, ApplicationListener<RefreshEvent> {
    //配置注入
    @Autowired
    Environment environment;

    public void setEnvironment(Environment environment){
        this.environment=environment;
    }
    @PostConstruct
    public void init(){

        //=========================spring init util 启动中=========================================
        logger.info("=========================spring init util 启动中=========================================");
        //让configUtil从 environment中去取参数
        ConfigUtil.environment =environment;

//        System.out.println(environment.toString());
        // ConfigUtil.init();

        String jsonStr= JSON.toJSONString(this);
        logger.info(jsonStr);
        InputStream is = new ByteArrayInputStream(jsonStr.getBytes());
        try {
            //加载这个json字符串
            Config.CONFIG = Config.load(is );
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
            if(defaultConfig==null){
                Config.CONFIG = Config.load(PathManager.getInstance().getClassPath().resolve("config.cfg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        RedisUtil.init();

    }


    private static final Logger logger = LoggerFactory.getLogger(SpringInitUtil.class);
    /**
     * 验证码配置
     */
    private ValidCodeConfig validCode;
    /**
     * 短信每次发送量
     */
    private int pvSmsSendAmount;
    /**
     * 日志
     */
    public static int a;
    public static String b;
    public static Double c;
    public static Integer d;
    /**
     * 系统日志配置
     */
    private SystemConfig system = new SystemConfig();
    /**
     * 缓存配置
     */
    private CacheConfig cache = new CacheConfig();
    /**
     * 图片服务配置
     */
    private ImageConfig image = new ImageConfig();

    /**
     * 单例 配置
     */
    EmailConfig email =new EmailConfig();

    public  EmailConfig getEmail() {
        return email;
    }

    public  void setEmail(EmailConfig email) {
        this.email = email;
    }


    public SystemConfig getSystem() {
        return system;
    }

    public CacheConfig getCache() {
        return cache;
    }



    /**
     * 获取配置文件路径
     *
     * @return String
     */
    public static Path getConfigFile() {
        return PathManager.getInstance().getClassPath().resolve("config.cfg");
    }

    /*
     * public static void save(Path toFile,Config config) throws IOException{
     * try(BufferedWriter writer =Files.newBufferedWriter(toFile,
     * Charset.forName("UTF-8"))){ createGson().toJson(config,writer); } }
     *
     */

    public ValidCodeConfig getValidCode() {
        return validCode;
    }

    public void setValidCode(ValidCodeConfig validCode) {
        this.validCode = validCode;
    }

    public ImageConfig getImage() {
        return image;
    }

    public void setPvSmsSendAmount(int pvSmsSendAmount) {
        this.pvSmsSendAmount = pvSmsSendAmount;
    }

    public void setSystem(SystemConfig system) {
        this.system = system;
    }

    public void setCache(CacheConfig cache) {
        this.cache = cache;
    }

    public void setImage(ImageConfig image) {
        this.image = image;
    }

    public int getPvSmsSendAmount() {
        return pvSmsSendAmount;
    }


    @Override
    public void afterPropertiesSet() /*throws Exception*/ {
        logger.info("consul config changed afterPropertiesSet");
        //this.init();
    }

    @Override
    public void onApplicationEvent(RefreshEvent event) {
        logger.info("consul config changed onApplicationEvent");
    }
}
