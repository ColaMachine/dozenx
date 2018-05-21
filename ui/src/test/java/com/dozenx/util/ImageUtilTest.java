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
        String rightImageUrl ="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3302978487,4193537134&fm=85&s=10A3F3B24620050FC8963800030030C8";
        ImageUtil.saveImageFromUrl(rightImageUrl, PathManager.getInstance().getHomePath().resolve("test"),"testSaveImageFromUrl");
        String falseImageUrl ="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3302";

        try{
            ImageUtil.saveImageFromUrl(falseImageUrl, PathManager.getInstance().getHomePath().resolve("test"),"testSaveImageFromUrl");
            assert(false);

            logger.debug("错误的图片url 应该要报错");
        }catch (Exception e){
            logger.debug("应该要报错的",e);

        }

    }
}
