/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.dozenx.util;


//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.SCPClient;
//import ch.ethz.ssh2.SFTPv3Client;
//import ch.ethz.ssh2.StreamGobbler;
import com.dozenx.core.Path.PathManager;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtil.class);

    public static List<File> readAllFileInFold(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("path file not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("path file not exist");
        }
        return listFile(file);
    }

    public static List<File> listFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        for (File childFile : fileAry) {
            if (childFile.isDirectory()) {
                fileList.addAll(listFile(childFile));
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }


    public static List<File> listFile(File file,boolean recursion) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        for (File childFile : fileAry) {
            if (recursion && childFile.isDirectory()) {
                fileList.addAll(listFile(childFile));
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        String content = "";
        try{
            content =  readFile2Str(file);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 读取文件内容 忽略行注释
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile2Str(File file) throws IOException {
        if (!file.exists()) {
            //throw new IOException("path file "+file.getPath()+" not exist");
            return "";
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            //过滤掉注释内容
            s = s.trim();
            if (s.startsWith("//")) {
                continue;
            }
            templateStr.append(s + "\r\n");
        }
        if (templateStr == null || templateStr.toString().length() == 0) {
            throw new IOException("file is empty: " + file);
        }
        return templateStr.toString();
    }


    /**
     * 写文件
     *
     * @param filePath
     * @param content
     * @throws IOException
     */
    public static void writeFile(String filePath, String content) throws IOException {
        FileWriter fileWritter = null;
        File file = new File(filePath);
        // BufferedWriter bufferWritter=null;
        try {
            // if file doesnt exists, then create it
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fileWritter = new FileWriter(file, false);
            fileWritter.write(content);
            // bufferWritter = new BufferedWriter(fileWritter);
            // bufferWritter.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            System.out.println(file.getAbsolutePath().toString());
            e.printStackTrace();
            throw e;
        } finally {
            /*
			 * bufferWritter.flush(); if(bufferWritter!=null )
			 * bufferWritter.close();
			 */

            fileWritter.flush();
            if (fileWritter != null)
                fileWritter.close();

        }
    }

    /**
     * 写文件
     *
     * @param file
     * @param content
     * @throws IOException
     */
    public static void writeFile(File file, String content) throws IOException {
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String args[]) {
        try {
            FileReader fr = new FileReader("G://V2.2.7.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            String name = "";
            StringBuffer result = new StringBuffer();
            while ((s = br.readLine()) != null) {
                // System.out.println(s);
                // System.out.println(s.split("\\s+").length);
                String arr[] = s.split("\\s+");
                System.out.println(arr[0]);
                if (name.equals("")) {
                    name = arr[arr.length - 1];
                } else if (arr[arr.length - 1].trim().equals(name.trim())) {

                    result.append("'").append(arr[0]).append("',");

                } else {

                    // System.out.println(name+"select distinct IPAddress from
                    // wii_device_ssid where deviceid in(select id from
                    // wii_device where DevId in ("+result.toString()+"))");
                    result = new StringBuffer();
                    name = arr[arr.length - 1];
                }
                if (arr.length == 3) {

                }

            }
            // System.out.println(name+"select distinct IPAddress from
            // wii_device_ssid where id in(select id from wii_device where DevId
            // in ("+result.toString()+")");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFile2List(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("read file failed path" + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }

    public static List<String> readFile2List(File file) throws IOException {

        if (!file.exists()) {
            throw new IOException("read file failed path");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }


    /**
     * 上传文件 uploadfile
     *
//     * @param localRootPath  c:/forder
//     * @param remoteRootPath /folder
//     * @param relativePaths  a.txt
//     * @param userName       username
//     * @param pwd            password
//     * @param serverIp       192.168.11.231
     */
//    public static void upload(String localRootPath, String remoteRootPath, String[] relativePaths, String userName,
//                              String pwd, String serverIp) {
//        Connection con = new Connection(serverIp);
//
//        try {
//            con.connect();
//            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
//            if (!isAuthed) {
//                logger.error("ssh upload file username & pwd authed failed");
//                return;
//            }
//
//            SCPClient scpClient = con.createSCPClient(); //
//            SFTPv3Client sftpClient = new SFTPv3Client(con);
//            for (int i = 0; i < relativePaths.length; i++) {
//                String relativePath = relativePaths[i];
//                if (relativePath.startsWith(File.separator)) {
//                    relativePath = relativePath.substring(1);
//                }
//                String localPath = localRootPath + File.separator + relativePath;
//                String remotePath = remoteRootPath + File.separator + relativePath;
//                int index = remotePath.lastIndexOf(File.separator);
//                String remoteFileDir = "";
//                if (index != -1) {
//                    ch.ethz.ssh2.Session session = con.openSession();
//                    remoteFileDir = remotePath.substring(0, index);
//
//                    session.execCommand("mkdir -p " + remoteFileDir); //
//                    logger.debug("Here is some information about the remote host:");
//                    InputStream stdout = null;
//                    BufferedReader br = null;
//                    try {
//                        stdout = new StreamGobbler(session.getStdout());
//                        br = new BufferedReader(new InputStreamReader(stdout));
//                    } catch (Exception e) {
//                        logger.error("error in print ssh mkdir log", e);
//                    } finally {
//                        if (br != null) {
//                            br.close();
//                        }
//                        if (stdout != null) {
//                            stdout.close();
//                        }
//                    }
//                    while (true) {
//                        String line = br.readLine();
//                        if (line == null)
//                            break;
//                        logger.debug(line);
//                    }
//                    // Show exit status,if available(otherwise"null")
//                    logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir,
//                    // 6);
//                    logger.info("创建目录+" + remoteFileDir);
//                    session.close();
//                    logger.debug("local image file :" + localPath);
//                } else {
//                    logger.debug("error:in copy " + localPath);
//                }
//                scpClient.put(localPath, remoteFileDir); // 从本地复制文件到远程目录
//            }
//
//        } catch (IOException e) {
//            logger.error("ssh upload file failed ", e);
//
//        } finally {
//            con.close();
//        }
//
//    }

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

    /**
     * 获得指定文件的byte数组
     */
    public static  byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void getFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFileFromStream(InputStream in, String fileName, Path path) {
        OutputStream out =null;
        try{
            File file = path.resolve(fileName).toFile();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            byte[] bts = new byte[1024];

            out = new FileOutputStream(file);
            int len = 0;
            while ((len = in.read(bts, 0, 1024)) > 0) {
                out.write(bts, 0, len);
            }
        }catch(Exception e ){

        }  finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }



    //缓存文件头信息-文件头信息
    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    static {
// images
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
//
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
        mFileTypes.put("", "");
        mFileTypes.put("", "");
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        return mFileTypes.get(getFileHeader(filePath));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    /**
     * 根据文对象获取文件头信息
     *
     * @param filePath
     *            文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(File filePath) {
        InputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }


    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src
     *            要读取文件头信息的文件的byte数组
     * @return 文件头信息
     *下面这段代码就是用来对文件类型作验证的方法，
    第一个参数是文件的字节数组，第二个就是定义的可通过类型。代码很简单，         主要是注意中间的一处，将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了。
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

//    public static void main(String[] args) throws Exception {
//        final String fileType = getFileType("E:/读书笔记/Java编程思想读书笔记.docx");
//        System.out.println(fileType);
//    }

}
