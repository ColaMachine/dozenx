import com.dozenx.core.config.Config;
import com.dozenx.util.StreamUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.test.TestController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:26 2019/5/16
 * @Modified By:
 */
public class PathTest {
    private Logger logger = LoggerFactory.getLogger(PathTest.class);
    @Test
    public void list() throws Exception {

        logger.info("PathTest.class.getResource(\"\"): "+PathTest.class.getResource(""));
        logger.info("PathTest.class.getClassLoader().getResource(\"\"): "+PathTest.class.getClassLoader().getResource(""));


        logger.info("the class in jar ");

        logger.info("Config.class.getResource(\"/\"): "+Config.class.getResource("/"));
        logger.info("Config.class.getResource(\"\"): "+Config.class.getResource(""));

        logger.info("Config.class.getClassLoader().getResource(\"/\"): "+Config.class.getClassLoader().getResource("/"));
        logger.info("Config.class.getClassLoader().getResource(\"\") "+Config.class.getClassLoader().getResource(""));
        logger.info("the class not in jar ");

        logger.info("TestController.class.getResource(\"/\"): "+TestController.class.getResource("/"));
        logger.info("TestController.class.getResource(\"\"): "+TestController.class.getResource(""));

        logger.info("TestController.class.getClassLoader().getResource(\"/\"): "+TestController.class.getClassLoader().getResource("/"));
        logger.info("TestController.class.getClassLoader().getResource(\"\") "+TestController.class.getClassLoader().getResource(""));

//
//        System.out.println(Config.class.getResource(""));
//        String s = "/application.yml";
//        InputStream inputStream = Config.class.getResourceAsStream(s);
//        String txt = new StreamUtil().parse_String(inputStream);
//        logger.info(txt);

    }
}
