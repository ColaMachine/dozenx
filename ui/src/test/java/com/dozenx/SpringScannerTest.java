package com.dozenx;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 9:03 2019/4/3
 * @Modified By:
 */
public class SpringScannerTest {
    @Test
    public void getResult() throws IOException {
        String packageSearchPath = "classpath*:com/dozenx/web/**/*.class";
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        System.out.println();
        for (Resource resource : resources) {
            System.out.println(resource.toString());
        }
    }
}
