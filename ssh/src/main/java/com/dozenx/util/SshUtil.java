package com.dozenx.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.zip.*;
import org.apache.tools.zip.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:57 2018/4/10
 * @Modified By:
 */
public class SshUtil {
     private static  Logger logger = LoggerFactory.getLogger(SshUtil.class);
    /**
     * 上传文件 uploadfile
     *
     * @param localRootPath  c:/forder
     * @param remoteRootPath /folder
     * @param relativePaths  a.txt
     * @param userName       username
     * @param pwd            password
     * @param serverIp       192.168.11.231
     */
    public static void upload(String localRootPath, String remoteRootPath, String[] relativePaths, String userName,
                              String pwd, String serverIp) {
        Connection con = new Connection(serverIp);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if (!isAuthed) {
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

            SCPClient scpClient = con.createSCPClient(); //
            SFTPv3Client sftpClient = new SFTPv3Client(con);
            for (int i = 0; i < relativePaths.length; i++) {
                String relativePath = relativePaths[i];
                if (relativePath.startsWith(File.separator)) {
                    relativePath = relativePath.substring(1);
                }
                String localPath = localRootPath + File.separator + relativePath;
                String remotePath = remoteRootPath + File.separator + relativePath;
                int index = remotePath.lastIndexOf(File.separator);
                String remoteFileDir = "";
                if (index != -1) {
                    ch.ethz.ssh2.Session session = con.openSession();
                    remoteFileDir = remotePath.substring(0, index);

                    session.execCommand("mkdir -p " + remoteFileDir); //
                    logger.debug("Here is some information about the remote host:");
                    InputStream stdout = null;
                    BufferedReader br = null;
                    try {
                        stdout = new StreamGobbler(session.getStdout());
                        br = new BufferedReader(new InputStreamReader(stdout));
                    } catch (Exception e) {
                        logger.error("error in print ssh mkdir log", e);
                    } finally {
                        if (br != null) {
                            br.close();
                        }
                        if (stdout != null) {
                            stdout.close();
                        }
                    }
                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        logger.debug(line);
                    }
                    // Show exit status,if available(otherwise"null")
                    logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir,
                    // 6);
                    logger.info("创建目录+" + remoteFileDir);
                    session.close();
                    logger.debug("local image file :" + localPath);
                } else {
                    logger.debug("error:in copy " + localPath);
                }
                scpClient.put(localPath, remoteFileDir); // 从本地复制文件到远程目录
            }

        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } finally {
            con.close();
        }

    }


    public static void upload(String localRootPath ,String remoteRootPath, String thisFile, String userName,
                              String pwd, String serverIp) {
        Connection con = new Connection(serverIp);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if (!isAuthed) {
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

            SCPClient scpClient = con.createSCPClient(); //
            SFTPv3Client sftpClient = new SFTPv3Client(con);
            String relativePath = thisFile;
            if (relativePath.startsWith(File.separator)) {
                relativePath = relativePath.substring(1);
            }
            String localPath = localRootPath + File.separator + relativePath;
            String remotePath = remoteRootPath + File.separator + relativePath;
            int index = remotePath.lastIndexOf(File.separator);
            String remoteFileDir = "";
            if (index != -1) {
                ch.ethz.ssh2.Session session = con.openSession();
                remoteFileDir = remotePath.substring(0, index);
                try{
                    exec(session,"mkdir -p " + remoteFileDir); //
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    session.close();
                }

                logger.debug("Here is some information about the remote host:");
                logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir,
                // 6);
                logger.info("创建目录+" + remoteFileDir);





                logger.debug("local image file :" + localPath);
            } else {
                logger.debug("error:in copy " + localPath);
            }


            scpClient.put(localPath, remoteFileDir); // 从本地复制文件到远程目录
            ch.ethz.ssh2.Session session = con.openSession();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                System.out.println("unzip -o "+remoteFileDir+"/"+thisFile+"  -d "+remoteFileDir);
                exec(session,"unzip -o "+remoteFileDir+"/"+thisFile+"  -d "+remoteFileDir); //
            }catch (Exception e){
                e.printStackTrace();
            }finally {
               // session.close();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            session = con.openSession();
            try{
                System.out.println("/service/tomcat-npbiz-advert-8096/bin/shutdown.sh");
                exec(session,"/service/tomcat-npbiz-advert-8096/bin/shutdown.sh"); //
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                session.close();
            }
            System.out.println("start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            session = con.openSession();
            try{
                System.out.println("/service/tomcat-npbiz-advert-8096/bin/startup.sh");
                exec(session,"/service/tomcat-npbiz-advert-8096/bin/startup.sh"); //
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                session.close();
            }




        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } finally {
            con.close();
        }

    }

    public static void exec( ch.ethz.ssh2.Session session ,String command)throws Exception{
        session.execCommand(command); //

        InputStream stdout = null;
        BufferedReader br = null;
        try {
            stdout = new StreamGobbler(session.getStdout());
            br = new BufferedReader(new InputStreamReader(stdout));
        } catch (Exception e) {
            logger.error("error in print ssh mkdir log", e);
        } finally {
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                logger.debug(line);
            }
            if (br != null) {
                br.close();
            }
            if (stdout != null) {
                stdout.close();
            }
        }






        session.close();
    }

    public static void main(String args[]){
        String localRootPathStr="G:\\advert-workspace\\code\\trunk\\advert\\target\\classes";
        String remoteRootPathStr ="/service/tomcat-npbiz-advert-8096/awifi-advert/WEB-INF/classes";
        String serverIp ="192.168.212.90";
        String userName ="root";
        String pwd="awifi@123";
        String relativePath="com";
        Path localRootPath  =Paths.get(localRootPathStr);
        localRootPath.resolve(relativePath);
        //List<File> file = FileUtil.listFile(localRootPath.resolve(relativePath).toFile());
      //  List<File> fileList =new ArrayList<>();
     //   File file =new File("G:\\advert-workspace\\code\\trunk\\advert\\target\\classes\\com");
     //   fileList.add(file);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包"+folderPath);

        try {
            com.dozenx.util.ZipUtil.foldToZip(folderPath,localRootPathStr+"\\a.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把文件上传到服务器指定的目录
        SshUtil.upload(localRootPathStr,remoteRootPathStr,"a.zip",userName,pwd,serverIp);

    }
}
