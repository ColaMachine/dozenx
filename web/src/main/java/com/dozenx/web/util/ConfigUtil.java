package com.dozenx.web.util;

import com.dozenx.web.util.CacheUtil;
import com.dozenx.util.StringUtil;
import org.slf4j.Logger;

import java.util.Properties;


/**
 * Created by dozen.zhang on 2016/12/5.
 */
public class ConfigUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigUtil.class);

    public static Properties properties =new Properties();
    /**
     * 根据name获取配置项值
     * @param name
     * @return
     */
    public static String getConfig(String name) {
//        Object object = BeanUtil.getBean("sysConfigService");
        if (StringUtil.isBlank(name)) {
            logger.error("ConfigUtil.getConfig 参数不能为空");
            return null;
            // throw new Exception("config param is null");
        }
        //直接从配置文件中读取
        String value = (String) properties.get(name);
        if (StringUtil.isNotBlank(value)) {
            return value;
        }
        //如果配置文件中没有这一项目
        //从缓存中读取
         value = (String) CacheUtil.getInstance().readCache(name, String.class);
        if (StringUtil.isBlank(value)) {
            // Object object = BeanUtil.getBean("sysConfigService");
            //如果缓存中没有这个项目就从数据库中读取 好像顺序反了,应该先从缓存中读取
            //读不到在从配置文件或者数据库中读取
            //虽然每次都去缓存中去获取不太好,但是貌似没有更好的方式了,因为缓存能实现唯一修改后立即生效
            //配置文件的话 如果多台服务器还要一个一个的修改台麻烦了,数据库只要改一个地方就可以了
                //从数据库中改了之后立马 更新缓存 跟新了缓存之后 整个系统就都正常了
            logger.error("配置信息未找到:"+name);
            return null;
//                if (config != null) {
//                    value = config.getValue();
//                    CacheUtil.getInstance().writeCache(name, value);
//                    return value;
//                } else {
//                    return null;
//                }
        } else {

            return value;
        }

    }


}
