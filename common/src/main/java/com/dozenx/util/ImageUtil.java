package com.dozenx.util;


//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.SCPClient;
//import ch.ethz.ssh2.SFTPv3Client;
//import ch.ethz.ssh2.StreamGobbler;
//import com.alibaba.fastjson.util.IOUtils;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.Config;
import com.dozenx.core.config.ImageConfig;
import com.dozenx.util.encrypt.Base64Util;
//import com.dozenx.web.core.log.ResultDTO;
//import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.nio.file.Path;

/**
 * 二维码生成海报
 *
 * @author dozen.zhang
 */
public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static BufferedImage templateImage;

//
//
//    public static void getBase64FromUrl (String url) throws Exception {
//        //  url = "http://open.e.189.cn/api/account/generateQrcodeImg.do?clientType=1&appId=8138110044&format=&sign=2F26885DE0BAA8CDEBD45AD4F38942ECAFA9B4C9&paras=0E6F1099785CE61B794B8F49C8D8754EECDA0E5650768AAA3C3CBF18016505FD865475F300D72A5A825404A3E5534480B0C1017A0466596CBE3EBE8175EB6F06C1B1FE296AE40EF96E3405095F5A7D3123B28F400C91F23F0BE3CF961636FE57319AF8E93330A3A070685F19&version=&uuid=https%3A%2F%2Fopen.e.189.cn%2Fapi%2Faccount%2FqrClinentLogin.do%3Fparas%3Dnew_uuid%253Dcb7c0zapglauzgsv%257C8138110044";
//
//        HttpURLConnection connection = HttpRequestUtil.sendGetRequest(url);
//        InputStream is = connection.getInputStream();
//        int responseCode =  connection.getResponseCode();
//        System.out.println("responseCode=" + responseCode);
//
//
//
//        byte[] byteAry = new byte[1024];
//
//
//        // sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//        //String fileStr =  encoder.encode(fileBytes);
//        // 对字节数组Base64编码
//        // System.out.println(fileStr);
//
//        System.out.println("------------------------");
//
//        //System.out.println(new String(fileBytes));
//        File outFile = new File("c");
//
//        OutputStream os = new FileOutputStream(outFile);
//
//        int count = 0;
//        while((count=is.read(byteAry))>0){
//            os.write(byteAry, 0, count);
//        }
//
//        os.close();
//        is.close();
//
//    }

    /**
     * 图像放大
     *
     * @param originalImage
     * @param times
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {
        int width = originalImage.getWidth() * times;
        int height = originalImage.getHeight() * times;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 图像放大
     *
     * @param originalImage
     * @return
     */
    public static BufferedImage fixSize(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 生成海报到系统配置目录 拿两张图片合成
     *
     * @param smallImage
     * @return
     * @throws IOException
     */
    public static String generatePoster(String smallImage) throws IOException {
        ImageConfig image = Config.getInstance().getImage();
        if (smallImage.startsWith("/") || smallImage.startsWith("\\")) {
            smallImage = smallImage.substring(1);
        }

        try {
            if (templateImage == null) {
                File templatePoster = PathManager.getInstance().getWebRootPath().resolve("poster").resolve("src.jpg")
                        .toFile();
                templateImage = ImageIO.read(templatePoster);
            }

            // 读入小图

            File small = PathManager.getInstance().getImagePath().resolve(smallImage).toFile();

            BufferedImage smallBuffImage = ImageIO.read(small);
            smallBuffImage = zoomInImage(smallBuffImage, 5);
            templateImage.createGraphics().drawImage(smallBuffImage, 500, 800, null);

            // 相对url

            // 文件无后缀名

            // 文件名带后缀名
            if (smallImage.startsWith(image.getQrcodeDir())) {
                smallImage = smallImage.substring(image.getQrcodeDir().length() + 1);
            }

            String fileWithFixx = /*
                                   * DateUtil.formatToString(new Date(),
                                   * DateUtil.YYYYMMDDHHMMSS) +
                                   * RandomString.randomString(4) + ".jpg";
                                   */
                    smallImage.replace(image.getQrcodeDir() + "/", "").replace(".gif", ".jpg");// .replace(".gif",
            // ".jpg");

            // 实际路径
            Path absolutePath = PathManager.getInstance().getPosterPath().resolve(fileWithFixx);

            File directory = absolutePath.getParent().toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            ImageIO.write(templateImage, "jpeg", absolutePath.toFile());

            String poster_dir = image.getPosterDir().trim();
            if (poster_dir.startsWith("/"))
                poster_dir = poster_dir.substring(1);
            if (!poster_dir.endsWith("/"))
                poster_dir += "/";

            // 上传文件到服务器
            /*
             * if(image.isServerEnable()) ImageUtil.upload(new String[] {
             * poster_dir + fileWithFixx });
             */
            return poster_dir + fileWithFixx;
        } catch (IOException e) {
            logger.error("generate poster error ", e);
            throw new IOException("generate Poster fail", e);
        }
    }

    /**
     * 在服务器上把要打印的海报打包成zip
     *
     * @param relatvieFilePaths
     * @return
     * @throws IOException
     */
    /*
     * public static String zipFileOnServer(String[] relatvieFilePaths) throws
     * IOException { logger.debug("begin zipFileOnserver , the param is :" +
     * relatvieFilePaths); ImageConfig image = Config.getInstance().getImage();
     * String user = image.getServerUser(); String pass = image.getServerPwd();
     * String host = image.getServerIp(); String dir = image.getServerDir();
     * Connection con = new Connection(host); try { con.connect(); boolean
     * isAuthed = con.authenticateWithPassword(user, pass);
     * logger.debug("isAuthed====" + isAuthed); // 拼接目录名 String zipfile =
     * "demo.zip";
     * 
     * String serverPath = image.getServerDir(); if (!serverPath.endsWith("/"))
     * { serverPath += "/"; } String poster_zip_dir =
     * image.getPosterZipDir().trim(); if (poster_zip_dir.startsWith("/"))
     * poster_zip_dir = poster_zip_dir.substring(1); if
     * (!poster_zip_dir.endsWith("/")) poster_zip_dir += "/";
     * 
     * String relativePath = poster_zip_dir + zipfile; if
     * (relativePath.startsWith("/")) relativePath = relativePath.substring(1);
     * 
     * String cmd = "rm -f " + serverPath + relativePath+" &&  mkdir -p " +
     * serverPath + poster_zip_dir + " && zip -j " + serverPath + relativePath;
     * for (int i = 0; i < relatvieFilePaths.length; i++) { cmd += " " +
     * serverPath + relatvieFilePaths[i]; }
     * 
     * ch.ethz.ssh2.Session session = con.openSession();
     * 
     * // 尝试创建目录
     * 
     * logger.debug("excute zip cmd on image server: " + cmd);
     * 
     * session.execCommand(cmd); // logger.debug(
     * "Here is some information about the remote host:"); InputStream stdout =
     * new StreamGobbler(session.getStdout()); BufferedReader br = new
     * BufferedReader(new InputStreamReader(stdout)); while (true) { String line
     * = br.readLine(); if (line == null) break; logger.debug(line);
     * 
     * }
     * 
     * Show exit status, if available (otherwise "null") logger.debug(
     * "ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir, 6);
     * session.close();
     * 
     * con.close(); return relativePath; } catch (IOException e) {
     * logger.error("服务器给海报打zip包出错", e); throw e; }
     * 
     * }
     */
    /**
     * 生成海报并打包
     *
     * @param smallImages
     * @param bigImage
     * @param width
     * @param height
     * @return
     * @throws IOException
     *//*
       * public static String generateHaiBao(String[] smallImages, String
       * bigImage, int width, int height) throws IOException {
       * 
       * Path finalImages[] = new Path[smallImages.length]; try { // 读入大图 File
       * big = new File(bigImage); BufferedImage bigBuffImage =
       * ImageIO.read(big); for (int i = 0; i < smallImages.length; i++) { //
       * 读入小图 File small = new File(smallImages[i]);
       * 
       * BufferedImage smallBuffImage = ImageIO.read(small);
       * 
       * bigBuffImage.createGraphics().drawImage(smallBuffImage, width, height,
       * null); String file = RandomGUIDUtil.generateKey(); finalImages[i] =
       * PathManager.getInstance().getPosterPath().resolve(file + ".jpg");
       * ImageIO.write(bigBuffImage, "jpeg", finalImages[i].toFile()); }
       * 
       * byte[] buffer = new byte[1024]; // 生成的ZIP文件名为Demo.zip Path strZipName =
       * PathManager.getInstance().getPosterZipPath()
       * .resolve(RandomGUIDUtil.generateKey() + ".zip"); ZipOutputStream out =
       * new ZipOutputStream(new FileOutputStream(strZipName.toFile())); //
       * 需要同时下载的两个文件result.txt ，source.txt for (int i = 0; i <
       * finalImages.length; i++) { File file = finalImages[i].toFile();
       * FileInputStream fis = new FileInputStream(file);
       * 
       * out.putNextEntry(new ZipEntry(file.getName()));
       * 
       * int len;
       * 
       * // 读入需要下载的文件的内容，打包到zip文件
       * 
       * while ((len = fis.read(buffer)) > 0) {
       * 
       * out.write(buffer, 0, len);
       * 
       * }
       * 
       * out.closeEntry();
       * 
       * fis.close();
       * 
       * }
       * 
       * out.close();
       * 
       * System.out.println("生成" + strZipName + "成功"); return
       * strZipName.toString(); } catch (IOException e) { // TODO Auto-generated
       * catch block e.printStackTrace(); throw e;
       * 
       * }
       * 
       * }
       */

    /**
     * 上传文件到服务器
     * <p>
     * <p>
     * 是相对于webroot的路径
     */
//    public static void upload(String[] filePaths) {
//        ImageConfig image =
//                Config.getInstance().getImage();
//        if (!image.isServerEnable()) return;
//        String user = image.getServerUser();
//        String pass = image.getServerPwd();
//        String host = image.getServerIp();
//        String dir = image.getServerDir();
//        Connection con = new Connection(host);
//        try {
//            con.connect();
//            boolean
//                    isAuthed = con.authenticateWithPassword(user, pass);
//            System.out.println("isAuthed====" + isAuthed);
//            SCPClient scpClient =
//                    con.createSCPClient(); //
//            SFTPv3Client sftpClient = new
//                    SFTPv3Client(con);
//            for (int i = 0; i < filePaths.length; i++) {
//                String
//                        filePath = filePaths[i];
//                if (filePath.startsWith("/")) filePath =
//                        filePath.substring(1);
//                String localPath =
//                        PathManager.getInstance().getWebRootPath().resolve(filePath).toString();
//                int index = filePath.lastIndexOf("/");
//                String theDir = "";
//                if (index !=
//                        -1) {
//                    ch.ethz.ssh2.Session session = con.openSession();
//                    theDir =
//                            filePath.substring(0, index);
//                    theDir = dir + "/" + theDir;
//                    session.execCommand("mkdir -p " + theDir); //
//                    logger.debug(
//                    "Here is some information about the remote host:");
//                    InputStream stdout =
//                            new StreamGobbler(session.getStdout());
//                    BufferedReader br = new
//                            BufferedReader(new InputStreamReader(stdout));
//                    while (true) {
//                        String line
//                                = br.readLine();
//                        if (line == null) break;
//                        logger.debug(line);
//
//                    }
//                   // Show exit status,if available(otherwise"null")
//                    logger.debug(
//                            "ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir, 6);
//                    logger.info("创建目录+" + theDir);
//                    session.close();
//                    logger.debug(
//                            "local image file :" + localPath);
//                }
//                scpClient.put(localPath, theDir); //从本地复制文件到远程目录
//            } con.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static boolean compressImg(String inputPath, String outputPath, Integer width, Integer height,
                                      boolean proportion) {
        try {
            File file = new File(inputPath);
            if (!file.exists()) {
                return false;
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                logger.error("the image( " + inputPath + " )is wrong!");
                return false;
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = width; // 输出的图片宽度
                    newHeight = height; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(outputPath);
                // 压缩质量 百分比
                float JPEGcompression = 0.7f;
                int dpi = 300;// 分辨率
                saveAsJPEG(dpi, tag, JPEGcompression, out);
                // 关闭输出流

                // JPEGImageEncoder可适用于其他图片类型的转换
                // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                // encoder.encode(tag);
                out.close();
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean compressForFix(String inputPath) {
        try {
            String outpath = inputPath.replaceAll("(\\.[^\\.]*)$", "_min" + "$1");
            Image img = ImageIO.read(new File(inputPath));
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            int newWidth = 0;
            int newHeight = 0;
            if (width > 1000) {
                newWidth = width / 2;
                newHeight = height / 2;
            } else if (width > 500) {
                newWidth = width * 2 / 3;
                newHeight = height * 2 / 3;
            } else {
                newWidth = width;
                newHeight = height;
            }
            return compressImg(inputPath, outpath, newWidth, newHeight, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    public void putIntoRedis(String inputPath){


    }
    public static void main(String args[]) {
        String imagePth ="C:\\Users\\dozen.zhang\\Pictures\\parent.png";
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePth));
            //ByteArrayOutputStream outputStream =new ByteArrayOutputStream();

           byte[] bts =  FileUtil.getBytes(imagePth);
            String s = Base64Util.encode(bts);
            System.out.println(s);
            //ImageUtil.imageToB
          //  ImageIO.write( bufferedImage,"png", outputStream);
          //  RedisUtil.setByteAry("hello",outputStream.toByteArray());

            String ss ="data:image/png;base64,iVBORw0KGgoAAAANSUh";
            System.out.print(ss.substring(ss.indexOf(",")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // try {
        // /*
        // * ImageUtil.generateHaiBao( new String[] {
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png",
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png",
        // * "C:\\Users\\dozen.zhang\\Pictures\\QQ图片20150729112804.png" },
        // * "C:\\Users\\dozen.zhang\\Pictures\\qyyygqfjzm_45324.jpg", 300,
        // * 400);
        // */
        // ImageUtil.generatePoster("qrcode/QQ图片20150916161338.gif");
        //
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

      /*  ImageUtil.upload(
                new String[]{
                        "C:\\Users\\dozen.zhang\\Pictures\\finalImg.jpg"
                }
        );
        long start = System.currentTimeMillis();
       // compressForFix("E:\\1.jpg");

        long end = System.currentTimeMillis();
        System.out.println(end - start);*/

        String parentPath = "C:\\Users\\dozen.zhang\\Pictures\\qyyygqfjzm_45324.jpg";
        String childPath =  "C:\\Users\\dozen.zhang\\Pictures\\child.jpg";

        searchPosition(parentPath,childPath);
    }

    /**
     * 说明:
     *
     * @param path
     * @param imageName
     * @param imageData
     * @return void
     * @throws IOException
     * @author dozen.zhang
     * @date 2015年12月20日下午12:52:39
     */
    public static String  saveBase64Image(String path, String imageName, String imageData) throws IOException {
        int success = 0;
        String message = "";
        if (null == imageData || imageData.length() < 100) {
            // 数据太短，明显不合理
            throw new IOException("err.upload.img.tooshort");
          //  return ResultUtil.getWrongResultFromCfg("err.upload.img.tooshort");
        } else {
            if (imageData.startsWith("%2B")) {
                imageData = URLDecoder.decode(imageData, "UTF-8").substring(1);
            } else if (imageData.startsWith("+")) {
                imageData = imageData.substring(1);
            }
            imageData = imageData.substring(imageData.indexOf("iVBO"));
            if(imageData.indexOf("base64")>0){
                imageData = imageData.substring(imageData.indexOf("base64")+7);
            }
            // 去除开头不合理的数据
            // imageData = URLDecoder.decode(imageData, "UTF-8");
            // imageData = imageData.substring(30);
            // int position=imageData.indexOf(",");
            // imageData=imageData.substring(position+1);
            // data:image/jpeg;base64,/9j/4AAQSkZJRgABA
            // data:image/png;base64,iVBORw0KGgoAAAANSUh
            // System.out.println(imageData);
            byte[] data = decodeBase64(imageData);
            int len = data.length;
            int len2 = imageData.length();
            if (null == imageName || imageName.length() < 1) {
                imageName = System.currentTimeMillis() + ".png";
            }
            saveImageToDisk(data, path, imageName);
            //
            success = 1;
            message = "上传成功,参数长度:" + len2 + "字符，解析文件大小:" + len + "字节";
        }
        return imageName;
       // return ResultUtil.getResult(0, imageName, "上传成功", null);
    }

    /**
     * base64 转 bufferedImage
     * @param base64Str
     * @return
     * @throws Exception
     */
    public static BufferedImage base64ToImage(String base64Str)throws  Exception{
        if (base64Str.startsWith("%2B")) {
            base64Str = URLDecoder.decode(base64Str, "UTF-8").substring(1);
        } else if (base64Str.startsWith("+")) {
            base64Str = base64Str.substring(1);
        }
        base64Str = base64Str.substring(base64Str.indexOf("iVBO"));

        if(base64Str.startsWith("data")){
            base64Str= base64Str.substring(base64Str.indexOf(","));
        }


        byte[] data = decodeBase64(base64Str);
        ByteArrayInputStream in = new ByteArrayInputStream(data);    //将b作为输入流；
        BufferedImage image = ImageIO.read( in);     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();

        return image;
    }
    // references: http://blog.csdn.net/remote_roamer/article/details/2979822
    private static boolean saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        int len = data.length;

        File file =new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 写入到文件
   /*     System.out.println(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]);
        }*/
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    private static byte[] decodeBase64(String imageData) throws IOException {
       /* sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        return decoder.decodeBuffer(imageData)*/


        byte[] data = Base64Util.decodeBuffer(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                // 调整异常数据

                data[i] += 256;
            }
        }
        return data;
    }

    /**
     * 以JPEG编码保存图片
     *
     * @param dpi             分辨率
     * @param image_to_save   要处理的图像图片
     * @param JPEGcompression 压缩比
     * @param fos             文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos)
            throws IOException {

        // useful documentation at
        // http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
        // useful example program at
        // http://johnbokma.com/java/obtaining-image-metadata.html to output
        // JPEG data

        // old jpeg class
        // com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder =
        // com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
        // com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam =
        // jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);

        // Image writer
        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        // and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);

        // if(dpi != null && !dpi.equals("")){
        //
        // //old metadata
        // //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
        // //jpegEncodeParam.setXDensity(dpi);
        // //jpegEncodeParam.setYDensity(dpi);
        //
        // //new metadata
        // Element tree = (Element)
        // imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
        // Element jfif =
        // (Element)tree.getElementsByTagName("app0JFIF").item(0);
        // jfif.setAttribute("Xdensity", Integer.toString(dpi) );
        // jfif.setAttribute("Ydensity", Integer.toString(dpi));
        //
        // }

        if (JPEGcompression >= 0 && JPEGcompression <= 1f) {

            // old compression
            // jpegEncodeParam.setQuality(JPEGcompression,false);

            // new Compression
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);

        }

        // old write and clean
        // jpegEncoder.encode(image_to_save, jpegEncodeParam);

        // new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();

    }

    public static void searchPosition(String parentImgPath,String childImgPath){
        Long startTime = System.currentTimeMillis();
        BufferedImage parentImg =null;
        BufferedImage childImg=null;

        try {
            parentImg = ImageIO.read(new File(parentImgPath));
            childImg= ImageIO.read(new File(childImgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pwidth =parentImg.getWidth();
        int pheight = parentImg.getHeight();
        int cwidth =childImg.getWidth();
        int cheight =childImg.getHeight();
       for(int i=0;i<pwidth-cwidth;i++){
            for(int j=0;j<pheight-cheight;j++){
                if(compareImage(parentImg,childImg,i,j ,pwidth,pheight ,cwidth,cheight )){
                    System.out.println(i +" "+j);
                    Long endTime =System.currentTimeMillis();
                    System.out.print("耗时"+(endTime-startTime));
                   return;
                }
               /* if(parentImg.getRGB(i,j) == childImg.getRGB(0,0)){

                }*/
            }
       }
        System.out.println("未找到");

    }
    public static boolean compareImage(BufferedImage pimg,BufferedImage cimg,int x,int y,int pw,int ph,int cw,int ch){



        for(int i=0;i<cw;i+=10){
            for(int j=0;j<ch;j+=10){
              try {
                  int prgb = pimg.getRGB(x + i, y + j);
                  int crgb = cimg.getRGB(i, j);
                  if (rgbDiff(prgb ,crgb)>6  ) {
                      return false;
                  }
              }catch (Exception e){
                  e.printStackTrace();
                  System.out.println("当前位置:"+x+":"+y+":"+i+":"+j);
                  System.exit(0);
              }
            }
        }
        return true;
    }
    public static int getR(int vlaue){
        return  (vlaue & (255<<(4*8))) >>(4*8);
    }
    public static int getG(int vlaue){
        return  (vlaue & (255<<(2*8))) >>(2*8);
    }
    public static int getB(int vlaue){
        return  (vlaue & (255)) ;
    }
    public static int  rgbDiff(int rgbA,int rgbB){
        int rDiff =Math.abs(getR(rgbA)-getR(rgbB))+
                Math.abs(getG(rgbA)-getG(rgbB))+
                Math.abs(getB(rgbA)-getB(rgbB));
        return rDiff;
    }
    public static void getTezheng(BufferedImage cimg){
        int width=cimg.getWidth();
        int height=cimg.getHeight();
    }




    public static int[][] getGrayPicture(String filename) throws FileNotFoundException, IOException
    {
        File file =new File(filename);
        BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        int[][] heights=new int[imageWidth][imageHeight];
        for(int i = originalImage.getMinX();i < imageWidth ;i++)
        {
            for(int j = originalImage.getMinY();j < imageHeight ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                heights[i][j]=red;
            }

        }
        return heights;

    }
    public static float[][] getGrayPicturef(String filename) throws FileNotFoundException, IOException
    {
        File file =PathManager.getInstance().getHomePath().resolve(filename).toFile();
        BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        float[][] heights=new float[imageWidth][imageHeight];
        for(int i = originalImage.getMinX();i < imageWidth ;i++)
        {
            for(int j = originalImage.getMinY();j < imageHeight ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                heights[i][j]=red;
            }

        }
        return heights;

    }
    public static Color[][] getGrayPicture(String filename,int minX,int minY,int maxX,int maxY) throws FileNotFoundException, IOException
    {
        //ImageIO.read( ImageIO.read(PathManager.getInstance().getInstallPath().resolve(filename).toUri())));
        File file =PathManager.getInstance().getHomePath().resolve(filename).toFile();
        BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        Color[][] heights=new Color[maxX-minX ][maxY-minY ];
        for(int i = minX;i < maxX ;i++)
        {
            for(int j = minY;j < maxY ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                if(red!=0){
                    blue = originalImage.getColorModel().getBlue(data);
                    green = originalImage.getColorModel().getGreen(data);
                    heights[i-minX][j-minY]=new Color(red,blue,green);
                }
            }

        }
        return heights;

    }


    public static Color[][] getGrayPicture(BufferedImage originalImage,int minX,int minY,int maxX,int maxY) throws FileNotFoundException, IOException
    {
        //ImageIO.read( ImageIO.read(PathManager.getInstance().getInstallPath().resolve(filename).toUri())));
        originalImage.getColorModel();
        //System.out.println(originalImage.getColorModel());

        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        Color[][] heights=new Color[maxX-minX ][maxY-minY ];
        for(int i = minX;i < maxX ;i++)
        {
            for(int j = minY;j < maxY ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                if(red!=0){
                    blue = originalImage.getColorModel().getBlue(data);
                    green = originalImage.getColorModel().getGreen(data);
                    heights[i-minX][j-minY]=new Color(red,blue,green);
                }
            }

        }
        return heights;

    }

    public static int[][] getColorPicture(String filename) throws FileNotFoundException, IOException
    {
        File file =new File(filename);
        BufferedImage originalImage=ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        int[][] heights=new int[imageWidth][imageHeight];
        for(int i = originalImage.getMinX();i < imageWidth ;i++)
        {
            for(int j = originalImage.getMinY();j < imageHeight ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                heights[i][j]=red;
            }

        }
        return heights;

    }

    /**
     * 图片转成灰度
     * @param originalImage
     * @return
     */

    public BufferedImage getGrayPicture(BufferedImage originalImage)
    {
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());
        int green=0,red=0,blue=0,rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        for(int i = originalImage.getMinX();i < imageWidth ;i++)
        {
            for(int j = originalImage.getMinY();j < imageHeight ;j++)
            {
//ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                red = (red*3 + green*6 + blue*1)/10;
                green = red;
                blue = green;
/*
���ｫr��g��b��ת��Ϊrgbֵ����ΪbufferedImageû���ṩ���õ�����ɫ�ķ�����ֻ������rgb��rgb���Ϊ8388608�����������ֵʱ��Ӧ��ȥ255*255*255��16777216
*/
                rgb = (red*256 + green)*256+blue;
                if(rgb>8388608)
                {
                    rgb = rgb - 16777216;
                }
//��rgbֵд��ͼƬ
                System.out.printf(" %d %d %d \r\n",red,blue,green);
            }

        }

        return originalImage;
    }

    public BufferedImage getGrayPictureAPI(BufferedImage originalImage)
    {
        BufferedImage grayPicture;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        grayPicture = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace
                .getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(originalImage, grayPicture);
        return grayPicture;
    }


    /**
     * 将数组转成图片
     * @param bts
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage rgbbyteToImage(byte[] bts,int width,int height){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] data=new int[width*height*3];
        for(int i=0;i<data.length;i++){
            data[i]=bts[i];
        }
        image.setRGB(0, 0, width, height, data, 0, width);
        return image;
    }

    /**
     * 图片转成数组
     * @param bufferedImage
     * @return
     */
    public static byte[] imageToRGBByte(BufferedImage bufferedImage){

        int width =bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        byte[] bytes = new byte[height*width*3];
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                Object data = bufferedImage.getRaster().getDataElements(i, j, null);
                int red = bufferedImage.getColorModel().getRed(data);
                int blue = bufferedImage.getColorModel().getBlue(data);
                int green = bufferedImage.getColorModel().getGreen(data);
                bytes[(j*width+i)*3+0]= (byte)(red);
                bytes[(j*width+i)*3+1]= (byte)(green);
                bytes[(j*width+i)*3+2]= (byte)(blue);
            }
        }

        return bytes;
    }


    public static BufferedImage bytesToImage(byte[] b) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(b);    //将b作为输入流；
        BufferedImage image = ImageIO.read( in);     //将in作为输入流，读取图片存入image中，而这里in可以
        return image;
    }

    /**
     * 从url 下载图片
     * @param url
     * @throws Exception  非图片 url 不可读  写文件失败
     */
    public static   BufferedImage saveImageFromUrl(String url ,Path path,String fileName) throws Exception {
        InputStream inputStream =null;
        try {
             inputStream = HttpRequestUtil.getInputStream(url);//获取输入流 在final里关闭
            BufferedImage bufferedImage = ImageIO.read(inputStream);//将流转化成图片 如果不是图片这里会报错 直接爆出异常给调用者
            if(bufferedImage==null){
                throw new IOException("图片读取失败 iamge read failed!:"+url);
            }
            int width = bufferedImage.getWidth();//获取个宽度
            int height = bufferedImage.getHeight();//获取高度
            logger.debug("读取网络图片成功 read image from url succ:" + url + "成功 width:" + width + "height:" + height);

            File file = path.resolve(fileName).toFile();//获取文件位置 尝试创建目录
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            ImageIO.write(bufferedImage,"jpeg",file);//开始写文件
            //保存完图片
            logger.debug("save image file to disk  succ 下载图片:" +file.getAbsolutePath());
            return bufferedImage;
        }catch ( Exception e){
            logger.error( "saveImageFromUrl",e);
            throw e;
        }finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }

    }


}
