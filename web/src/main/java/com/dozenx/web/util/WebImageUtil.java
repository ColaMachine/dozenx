package com.dozenx.web.util;

import com.dozenx.core.config.Config;
import com.dozenx.core.exception.ValidException;
import com.dozenx.util.FilePathUtil;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 11:26 2018/6/11
 * @Modified By:
 */
public class WebImageUtil {
    private static final Logger logger  = LoggerFactory.getLogger(WebImageUtil.class);
    public static String  saveUploadFileToDisk(MultipartFile file,String path,String name ){
        Path pth = Paths.get(path);
        File destFile = pth.toFile();
        if(!destFile.exists()){
            destFile.mkdirs();
        }
        String type = file.getContentType();
        try {
            FileUtil.writeFileFromStream(file.getInputStream(),name, pth);
        } catch (Exception var10) {
            throw  new ValidException("E2030004","图片大小不合法");
        }
        return FilePathUtil.joinPath(path,name);
    }

    /**
     * 保存spring mvc 的 MultipartFile对象到路径上
     * @param file
     * @param path
     * @param name
     * @return
     */
    public static String  saveUploadImageToDisk(MultipartFile file,String path,String name ){
        Path pth = Paths.get(path);
        File destFile = pth.toFile();
        if(!destFile.exists()){
            destFile.mkdirs();
        }
        destFile= pth.resolve(name).toFile();
        logger.info("path"+path);
        logger.info("name"+name);
        String type = file.getContentType();
        if(StringUtil.isBlank(type)) {
            throw  new ValidException("E2030001","图片格式为空不正确");
        } else if(!type.equals("image/jpeg") && !type.equals("image/png") && !type.equals("image/bmp")) {
            throw  new ValidException("E2030002","图片格式不正确");
        } else {

            BufferedImage img = null;
            try {
                img = ImageIO.read(file.getInputStream());
            } catch (Exception e) {
                logger.info("保存图片出错",e);
                throw  new ValidException("E2030004","图片读取出错");
            }
            if(img == null || img.getWidth() <= 0 || img.getHeight() <= 0) {
                throw  new ValidException("E2030003","图片大小不合法");
            }
            try {
                ImageIO.write(img, Config.getInstance().getImage().getType(), destFile);
            } catch (Exception e) {
                logger.info("保存图片出错",e);
                throw  new ValidException("E2030005","图片保存出错");
            }
            return FilePathUtil.joinPath(path,name);
        }
    }


}
