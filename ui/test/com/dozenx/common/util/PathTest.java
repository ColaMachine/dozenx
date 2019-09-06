package com.dozenx.common.util;

import com.dozenx.common.Path.PathManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:46 2019/5/29
 * @Modified By:
 */
public class PathTest {

    private Logger logger = LoggerFactory.getLogger(PathTest.class);

    @Test
    public void list() throws Exception {
        URL s = PathManager.class.getResource("/log4j.properties");
        logger.info("PathManager.class.getResource(\"\"): " + s);
       // logger.info("PathTest.class.getClassLoader().getResource(\"\"): " + PathManager.class.getClassLoader().getResource("/log4j.properties"));
    }
}
