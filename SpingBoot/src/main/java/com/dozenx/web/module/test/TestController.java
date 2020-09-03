package com.dozenx.web.module.test;

import com.dozenx.common.config.Config;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StreamUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.ConfigUtil;
import javafx.scene.control.ColorPicker;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:42 2019/5/15
 * @Modified By:
 */
@Controller
@RequestMapping("/test/")
public class TestController extends BaseController {

    public static void main(String args[]){


        String s="/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAaAFADASIA\\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+mTT\\nRW0Ek88iRQxqXeR2CqqgZJJPQAd6S4SWSBlhm8qTgq+0MAQc8juD0PQ4PBB5rmfFt7dQ+DNdhvrR\\nstp9wq3FspeNj5bAZH3kPU85UD+MmgTOP0bx/wCO77w4/iZ9D0q40mBpPNijaSCUoiEmRWclSoPH\\nGSSCMdx0Ov8AxLs9L8Eaf4osLJ762vZxCsbyeSyHD5z8rcgoRxx3BI68Jovjzw9p3wXl0Ka8dtUa\\n0uYBbJC5O6R32ncRtxhwTz0z34rQvvDz2vwy8B6Jq8OfO1u3E8J3KQsrSsUPQhgr4Poc/WpRFN3e\\n51mu/EB9P8Bab4j07TlvJL9VAhSXeID5Tu5JUfME2MGHy9DkjFc4fF/izVvC2n6xDqmnWE1/N5Fh\\nY29uGkun8zYwYyEhAuMgjjnkgkVxWix3ko1Lw9eypu8LafrDq6DKyB0ERQcAgBiz7jknOMCt34a6\\npa6bbQy3Y1LVL2xR4bPTrO0837PG53vMDwOWbbnOcHByCMTN9LkYhpWV7Nnrd3qo8N+FBqOuTeY9\\nrbp9peFc+ZJwp2jA6seOg55wK5MfEbWotEj1688GzQaO2xmuPt6FgjMFDCMqGOcjHQHIOcHNM8Ur\\nqHjD4e6nFBa6lFewXAmjtJUEMs0W7IDR91ALAAEljED14rD8TeOtF134bw6Jp58zVruO2iFlaWrq\\nsThkYqoIxgFdoAJPI7c1otjojZpWPXbS7gv7KC8tn3wTxrLG+CNysMg4PI4PepqzPDtpPYeGNJs7\\nlNk8FnDFImQdrKgBGRweR2rToEFQXVnb3sQjuIlkVTuUnqjdmU9VYZ4IwRU9FAGJdv8A2Ls26zbx\\nRvkrBqUmd2OoWQsGGc8lt+MjAAGDpS2cF7Fbm+tLeWSF1mRXUSCKUdGUkdRk4bAP0qzRQAyaGK5g\\nkgniSWGRSjxuoZWUjBBB6gjtVeGKw0e1gtbeCG0tt/lxxwxhI1ZiTjAGFyfpkkDqRVumyRpNE8Uq\\nK8bgqyMMhgeoI7igDPvP9G1eyuxwku61l7Dn5kZj7MpUA95eOuDoSRpNE8UqK8bgqyMMhgeoI7is\\n3Ro0k0sxOitHHdTqiMMhQk77AB2C7Vx6YGOlalAGb/pOmf8APxe2h+jSwj9C64+r5H8efl0qKKAP\\n/9k=";

//        String d = URLDecoder.(s);
//        System.out.println(d);
    }
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


    @Autowired
    private ApplicationContext applicationContext;



}