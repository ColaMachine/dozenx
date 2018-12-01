package com.dozenx.util;

import com.dozenx.core.Path.PathManager;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:19 2018/4/25
 * @Modified By:
 */

public class ImageUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtilTest.class);
    @Test
    public void testSaveImageFromUrl() throws Exception {
        String rightImageUrl ="https://www.baidu.com/img/bd_logo1.png?where=super";
        ImageUtil.saveImageFromUrl(rightImageUrl, PathManager.getInstance().getHomePath().resolve("test"),"testSaveImageFromUrl");
        String falseImageUrl ="https://www.baidu123.com/img/bd_logo1.png?where=super1";

//        try{
//            ImageUtil.saveImageFromUrl(falseImageUrl, PathManager.getInstance().getHomePath().resolve("test"),"testSaveImageFromUrl");
//            assert(false);
//
//            logger.debug("错误的图片url 应该要报错");
//        }catch (Exception e){
//            logger.debug("应该要报错的");
//
//        }

    }
}
