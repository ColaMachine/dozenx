package com.dozenx.core.Path;

import com.dozenx.core.config.Config;
import com.dozenx.util.LogUtil;
import com.dozenx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author dozen.zhang
 *
 */
public final class PathManager {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PathManager.class);
    /**
     * 声明
     */
    private static PathManager INSTANCE;
    /**
     * class目录
     */
    private Path classPath;
    /**
     * 根目录
     */
    private Path homePath;				
    /**
     * web root 目录 
     */
    private Path webRootPath;			
    /**
     * 临时目录
     */
    private Path tmpPath;				
    /**
     * 二维码目录
     */
    private Path qrcodePath;	
    /**
     * 验证码目录
     */
    private Path vcodePath;    
    /**
     * 海报目录
     */
    private Path posterPath;			
    /**
     * zip目录
     */
    private Path posterZipPath;			
    /**
     * 图片目录 
     */
    private Path imagePath;				


    /**
     * 临时目录名 
     */
    private static final String TMP_DIR = "tmp";		
    /**
     * classes 目录名
     */
    private static final String CLASS_DIR = "classes";	
    /**
     * webinf目录名
     */
    private static final String WEBINF_DIR = "WEB-INF";

    // private static final String QRCODE_DIR="qrcode";
    // private static final String POSTER_DIR="poster";
    // private static final String POSTER_ZIP_DIR="poster-zip";
    
  

    static {
        PathManager.getInstance();
        try {
            Config config = Config.getInstance();

            INSTANCE.updateDirs(config);
        } catch (Exception e) {
            logger.error("维护系统默认目录出错", e);
        }
    }


    private static ClassLoader getTCL() throws IllegalAccessException,
            InvocationTargetException {

        // Are we running on a JDK 1.2 or later system?
        Method method = null;
        try {
            method = Thread.class.getMethod("getContextClassLoader", null);
        } catch (NoSuchMethodException e) {
            // We are running on JDK 1.1
            return null;
        }

        return (ClassLoader) method.invoke(Thread.currentThread(), null);
    }
    static private boolean java1 = true;

    static private boolean ignoreTCL = false;

    static public URL getResource(String resource) {
        ClassLoader classLoader = null;
        URL url = null;

        try {
            if(!java1 && !ignoreTCL) {
                classLoader = getTCL();
                if(classLoader != null) {
                   LogUtil.debug("Trying to find ["+resource+"] using context classloader "
                            +classLoader+".");
                    url = classLoader.getResource(resource);
                    if(url != null) {
                        return url;
                    }
                }
            }


        } catch(IllegalAccessException t) {
            LogUtil.println("error when getTCL in pathManager."+t);
        } catch(InvocationTargetException t) {
            if (t.getTargetException() instanceof InterruptedException
                    || t.getTargetException() instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            LogUtil.warn(t.getMessage());
        } catch(Throwable t) {
            //
            //  can't be InterruptedException or InterruptedIOException
            //    since not declared, must be error or RuntimeError.
            logger.warn( t.getMessage());
        }

        // Last ditch attempt: get the resource from the class path. It
        // may be the case that clazz was loaded by the Extentsion class
        // loader which the parent of the system class loader. Hence the
        // code below.
        logger.debug("Trying to find ["+resource+
                "] using ClassLoader.getSystemResource().");
        return ClassLoader.getSystemResource(resource);
    }
    private PathManager() {


        System.out.println(PathManager.getResource("config.properties"));
      //  String protectDomain = PathManager.class.getProtectionDomain().getCodeSource().getLocation().toString();
        PathManager.getResource("");
        try {


            URL urlToSource = PathManager.getResource("");//PathManager.class.getProtectionDomain().getCodeSource().getLocation();
            logger.debug("protectionDomain:" + PathManager.class.getProtectionDomain().getCodeSource().getLocation());

            logger.debug("classLoader resource:" +  PathManager.getResource(""));


            // 向上找到classes目录

            // 这个目录是正确的
            String path = urlToSource.toString();

            path = path.substring(0, path.indexOf("classes") + 7);

            path = path.replace("file:/", "");
            if (path.indexOf(":") != 1) {
                path = File.separator + path;
            }
            classPath = Paths.get(path);
        } catch (Exception e) {
            logger.error("PathManager ", e);
        }
        homePath = classPath.getParent().getParent();
        if (webRootPath == null) {

            // webRootPath
            // 判断是何种方式启动的tomcat
            // 是否是maven
            if (classPath.toUri().toString().contains("target")) {// maven启动方式
                logger.info("--------------maven start mode------------");
                webRootPath = homePath.resolve("src/main/webapp");
            } else {
                logger.info("--------------tomcat start mode------------");
                webRootPath = homePath;
            }

        }

        logger.debug("webRoot:" + webRootPath);
        logger.debug("classPath:" + classPath);

    }
    
    public Path getHomePath() {
        return homePath;
    }

    public void setHomePath(Path homePath) {
        this.homePath = homePath;
    }

    public Path getClassPath() {
        return classPath;
    }
    
    public Path getImagePath() {
        return imagePath;
    }
    
    public Path getTmpPath() {
        return tmpPath;
    }
    public Path getVcodePath() {
        return vcodePath;
    }

    public Path getQrcodePath() {
        return qrcodePath;
    }

    public Path getWebRootPath() {
        return webRootPath;
    }

    /**
     * @param config 配置
     * @throws IOException 抛出异常
     */
    public void updateDirs(Config config) throws IOException {

        tmpPath = webRootPath.resolve(TMP_DIR);
        Files.createDirectories(tmpPath);
        if (StringUtil.isBlank(config.getInstance().getImage().getServerDir())) {
            imagePath = webRootPath;
        } else{
            imagePath=webRootPath.resolve(config.getInstance().getImage().getServerDir());
        }
            //imagePath = Paths.get(chantToUrl(config.getInstance().getImage().getServerDir()));
        Files.createDirectories(imagePath);

        qrcodePath = imagePath.resolve(config.getInstance().getImage().getQrcodeDir());
        Files.createDirectories(qrcodePath);

        posterPath = imagePath.resolve(config.getInstance().getImage().getPosterDir());
        Files.createDirectories(posterPath);

        posterZipPath = imagePath.resolve(config.getInstance().getImage().getPosterZipDir());
        Files.createDirectories(posterZipPath);
        
        vcodePath = webRootPath.resolve(config.getInstance().getImage().getVcodeDir());
        Files.createDirectories(vcodePath);

    }

    public Path getPosterPath() {
        return posterPath;
    }

    public Path getPosterZipPath() {
        return posterZipPath;
    }

    /**
     * @return PathManager
     */
    public static PathManager getInstance() {
        if (INSTANCE == null){
            INSTANCE = new PathManager();
        }
        return INSTANCE;
    }

    /**
     * 根据给点的路劲进行修正
     * 
     * @param path 参数
     * @return String
     */
    public static String changeToSystemLocation(String path) {
        return path.replace("/", File.separator).replace("\\", File.separator);
    }

    /**
     * 改成符合url的路径
     * 
     * @param path 参数
     * @return String
     */
    public static String chantToUrl(String path) {
        return path.replaceAll("\\\\", "/");
    }

    /**
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.println("123123target".contains("target"));

        String s = "C:\\zzw/workspace/kaqm/src/main/webapp/image";
        s = s.replaceAll("\\\\", "/");
        System.out.println(s);
    }

}