package com.dozenx.web.module.test;

import com.dozenx.core.config.Config;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.StreamUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:42 2019/5/15
 * @Modified By:
 */
@Controller
@RequestMapping("/test/")
public class TestController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    /**
     * 权限service
     **/


    /**
     * 说明:ajax请求ArtComment信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-8-14 10:07:36
     */

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {

        logger.info(JsonUtil.toJsonString(Config.getInstance()));
        logger.info("the class in jar ");
        logger.info("spring.datasource.type "+ ConfigUtil.getConfig("spring.datasource.type"));
        logger.info("Config.class.getResource(\"/\"): "+Config.class.getResource("/"));
        logger.info("Config.class.getResource(\"\"): "+Config.class.getResource(""));

        logger.info("Config.class.getClassLoader().getResource(\"/\"): "+Config.class.getClassLoader().getResource("/"));
        logger.info("Config.class.getClassLoader().getResource(\"\") "+Config.class.getClassLoader().getResource(""));
        logger.info("the class not in jar ");

        logger.info("TestController.class.getResource(\"/\"): "+TestController.class.getResource("/"));
        logger.info("TestController.class.getResource(\"\"): "+TestController.class.getResource(""));

        logger.info("TestController.class.getClassLoader().getResource(\"/\"): "+TestController.class.getClassLoader().getResource("/"));
        logger.info("TestController.class.getClassLoader().getResource(\"\") "+TestController.class.getClassLoader().getResource(""));


        String s = "/application.yml";
        InputStream inputStream = Config.class.getResourceAsStream(s);
        String txt = new StreamUtil().parse_String(inputStream);
        logger.info(txt);
        return this.getResult();
    }
}