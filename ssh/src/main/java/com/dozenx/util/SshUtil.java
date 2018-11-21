package com.dozenx.util;

import ch.ethz.ssh2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 8:57 2018/4/10
 * @Modified By:
 */
public class SshUtil {
    private static Logger logger = LoggerFactory.getLogger(SshUtil.class);

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


    public static void upload(String localRootPath, String remoteRootPath, String tomcatPath, String thisFile, String userName,
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
                try {
                    exec(session, "mkdir -p " + remoteFileDir); //
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
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
            try {
                System.out.println("unzip -o " + remoteFileDir + "/" + thisFile + "  -d " + remoteFileDir);
                exec(session, "unzip -o " + remoteFileDir + "/" + thisFile + "  -d " + remoteFileDir); //

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // session.close();
            }


        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } finally {
            con.close();
        }

    }

    public static void exec(String ip, String userName, String pwd, String cmd) {
        Connection con = new Connection(ip);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if (!isAuthed) {
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

            SCPClient scpClient = con.createSCPClient(); //
            SFTPv3Client sftpClient = new SFTPv3Client(con);

            ch.ethz.ssh2.Session session = con.openSession();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                exec(session, cmd); //
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } finally {
            con.close();
        }
    }

    public static void loginAndExec(String ip, String userName, String pwd, String[] cmd) {
        Connection con = new Connection(ip);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if (!isAuthed) {
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

//            SCPClient scpClient = con.c.createSCPClient(); //
//            SFTPv3Client sftpClient = new SFTPv3Client(con);
            //ConnectionInfo info = con.getConnectionInfo();
//            ch.ethz.ssh2.Session session =con
//                    .openSession();
//            // 这句非常重要，开启远程的客户端
//            session.requestPTY("vt100", 80, 24, 640, 480, null);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < cmd.length; i++) {
                execConnection(con, "source ~/.bash_profile ;" + cmd[i]); //
                Thread.sleep(2000);
            }


        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    //    public static void exec( ch.ethz.ssh2.Session session ,String command)throws Exception{
//        session.execCommand(command); //
//
//        InputStream stdout = null;
//        BufferedReader br = null;
//        try {
//            stdout = new StreamGobbler(session.getStdout());
//            br = new BufferedReader(new InputStreamReader(stdout));
//        } catch (Exception e) {
//            logger.error("error in print ssh mkdir log", e);
//            e.printStackTrace();
//        } finally {
//            while (true) {
//                String line = br.readLine();
//                if (line == null)
//                    break;
//                logger.debug(line);
//                System.out.println(line);
//            }
//            if (br != null) {
//                br.close();
//            }
//            if (stdout != null) {
//                stdout.close();
//            }
//        }
//        session.close();
//    }
//
    public static void execConnection(Connection connection, String command) throws Exception {
        Session session = connection.openSession();
        logger.debug("command:" + command);
        //  session.requestPTY("vt100", 80, 24, 640, 480, null);
        session.execCommand(command);
        InputStream stdout = null;
        BufferedReader br = null;
        try {
            stdout = new StreamGobbler(session.getStdout());
            br = new BufferedReader(new InputStreamReader(stdout));
        } catch (Exception e) {
            logger.error("error in print ssh mkdir log", e);
            e.printStackTrace();
        } finally {
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                logger.debug(line);
                System.out.println(line);
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

    public static void exec(ch.ethz.ssh2.Session session, String command) throws Exception {
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
                System.out.println(line);
            }
            if (br != null) {
                br.close();
            }
            if (stdout != null) {
                stdout.close();
            }
        }
    }

    public static void testExecCmd() {
        String serverIp = "192.168.212.90";
        String userName = "root";
        String pwd = "awifi@123";
        //String cmd = "/data/service/tomcat-npbiz-advert-8096/bin/startup.sh";
        //String[] cmd = new String[]{"/bin/bash /data/service/tomcat-npbiz-advert-8096/bin/startup.sh"};
        // String[] cmd = new String[]{"source ~/.bash_profile  ;export PATH=$JAVA_HOME/bin:$PATH;echo $PATH  \n "};
        String[] cmd = new String[]{"/data/service/tomcat-npbiz-advert-8096/bin/shutdown.sh  \n "};
        SshUtil.loginAndExec(serverIp, userName, pwd, cmd);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cmd = new String[]{"/data/service/tomcat-npbiz-advert-8096/bin/startup.sh  \n "};
        SshUtil.loginAndExec(serverIp, userName, pwd, cmd);
    }

    public static void updateTomecatWebappAndRestart() {
//        String localRootPathStr="G:\\advert-workspace\\code\\trunk\\advert\\target\\classes";
//        String remoteRootPathStr ="/service/tomcat-npbiz-advert-8096/awifi-advert/WEB-INF/classes";
//        String tomcatPath = "/service/tomcat-npbiz-advert-8096";

//        String localRootPathStr="G:\\advert-workspace\\code\\trunk\\advert\\target\\classes";
//       String remoteRootPathStr ="/data/service/tomcat-npbiz-advert-8096/awifi-advert/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-npbiz-advert-8096";
//        String localRootPathStr="G:\\E-zhike\\code\\trunk\\ezhike-web\\target\\classes";
//        String remoteRootPathStr ="/data/service/tomcat-ezhike-biz-8084/awifi-ezhike-web/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-ezhike-biz-8084";

//
        String localRootPathStr = "G:\\E-zhike\\code\\trunk\\ezhike-web\\target\\classes";
        String remoteRootPathStr = "/data/service/tomcat-ezhike-biz-8085/awifi-ezhike-web/WEB-INF/classes";
        String tomcatPath = "/data/service/tomcat-ezhike-biz-8085";


//        String localRootPathStr="G:\\E-zhike\\code\\trunk\\spider\\target\\classes";
//        String remoteRootPathStr ="/data/service/tomcat-spider-biz-8084/awifi-spider/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-spider-biz-8084";


        String serverIp = "192.168.212.90";
        String userName = "root";
        String pwd = "awifi@123";
        String relativePath = "com";
        Path localRootPath = Paths.get(localRootPathStr);
        localRootPath.resolve(relativePath);
        //List<File> file = FileUtil.listFile(localRootPath.resolve(relativePath).toFile());
        //  List<File> fileList =new ArrayList<>();
        //   File file =new File("G:\\advert-workspace\\code\\trunk\\advert\\target\\classes\\com");
        //   fileList.add(file);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包" + folderPath);

        try {
            com.dozenx.util.ZipUtil.foldToZip(folderPath, localRootPathStr + "\\a.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把文件上传到服务器指定的目录
        SshUtil.upload(localRootPathStr, remoteRootPathStr, tomcatPath, "a.zip", userName, pwd, serverIp);
        SshUtil.restartTomcat(serverIp, userName, pwd, tomcatPath);
    }


    public static void AlphaAdvertUpdateTomecatWebappAndRestart() {
//

        String serverIps[] = new String[]{"192.168.213.6", "192.168.213.7", "192.168.213.36"};


        String localRootPathStr = "G:\\advert-workspace\\code\\trunk\\advert\\target\\classes";
        String remoteRootPathStr = "/data/service/tomcat-npbiz-advert-8096/awifi-advert/WEB-INF/classes";
        String tomcatPath = "/data/service/tomcat-npbiz-advert-8096";


//        String localRootPathStr="G:\\E-zhike\\code\\trunk\\spider\\target\\classes";
//        String remoteRootPathStr ="/data/service/tomcat-spider-biz-8084/awifi-spider/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-spider-biz-8084";


        String userName = "root";
        String pwd = "awifi@123";
        String relativePath = "com";
        Path localRootPath = Paths.get(localRootPathStr);
        localRootPath.resolve(relativePath);
        //List<File> file = FileUtil.listFile(localRootPath.resolve(relativePath).toFile());
        //  List<File> fileList =new ArrayList<>();
        //   File file =new File("G:\\advert-workspace\\code\\trunk\\advert\\target\\classes\\com");
        //   fileList.add(file);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包" + folderPath);

        for (int i = 0; i < serverIps.length; i++) {
            String serverIp = serverIps[i];
            try {
                com.dozenx.util.ZipUtil.foldToZip(folderPath, localRootPathStr + "\\a.zip");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //把文件上传到服务器指定的目录
            SshUtil.upload(localRootPathStr, remoteRootPathStr, tomcatPath, "a.zip", userName, pwd, serverIp);
            SshUtil.restartTomcat(serverIp, userName, pwd, tomcatPath);
        }

    }


    public static void update(String serverIp, String localZipFolder, String tomcatPath, String webappName, String userName, String pwd) {
        String relativePath = "com";
        Path localRootPath = Paths.get(localZipFolder);
        localRootPath.resolve(relativePath);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包" + folderPath);
        try {
            com.dozenx.util.ZipUtil.foldToZip(folderPath, localZipFolder + "\\a.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把文件上传到服务器指定的目录
        SshUtil.upload(localZipFolder, tomcatPath + "/" + webappName + "/WEB-INF/classes", tomcatPath, "a.zip", userName, pwd, serverIp);
        SshUtil.restartTomcat(serverIp, userName, pwd, tomcatPath);

    }

    public static void restartTomcat(String serverIp, String userName, String pwd, String tomcatPath) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] cmd = new String[]{tomcatPath + "/bin/shutdown.sh   \n", tomcatPath + "/bin/startup.sh   \n"};
        SshUtil.loginAndExec(serverIp, userName, pwd, cmd);

    }


    public static void ezhikeUpdateTomecatWebappAndRestart() {
//
        String localRootPathStr = "G:\\advert-workspace\\code\\trunk\\advert\\target\\classes";
        String remoteRootPathStr = "/data/service/tomcat-npbiz-advert-8096/awifi-advert/WEB-INF/classes";
        String tomcatPath = "/data/service/tomcat-npbiz-advert-8096";


//        String localRootPathStr="G:\\E-zhike\\code\\trunk\\spider\\target\\classes";
//        String remoteRootPathStr ="/data/service/tomcat-spider-biz-8084/awifi-spider/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-spider-biz-8084";


        String userName = "root";
        String pwd = "awifi@123";
        String relativePath = "com";
        Path localRootPath = Paths.get(localRootPathStr);
        localRootPath.resolve(relativePath);
        //List<File> file = FileUtil.listFile(localRootPath.resolve(relativePath).toFile());
        //  List<File> fileList =new ArrayList<>();
        //   File file =new File("G:\\advert-workspace\\code\\trunk\\advert\\target\\classes\\com");
        //   fileList.add(file);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包" + folderPath);

        try {
            com.dozenx.util.ZipUtil.foldToZip(folderPath, localRootPathStr + "\\a.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverIp = "192.168.212.90";
        //把文件上传到服务器指定的目录
        SshUtil.upload(localRootPathStr, remoteRootPathStr, tomcatPath, "a.zip", userName, pwd, serverIp);
        SshUtil.restartTomcat(serverIp, userName, pwd, tomcatPath);

    }


    public static void AlphaSSCUpdateTomecatWebappAndRestart() {
//

        String serverIps[] = new String[]{"192.168.212.90"};


        String localRootPathStr = "G:\\ssc-workspace\\SSCrobot\\code\\trunk\\selenium\\target\\classes";
        String remoteRootPathStr = "/data/service/tomcat-webrobot-biz-8086/awifi-webrobot/WEB-INF/classes";
        String tomcatPath = "/data/service/tomcat-webrobot-biz-8086";


//        String localRootPathStr="G:\\E-zhike\\code\\trunk\\spider\\target\\classes";
//        String remoteRootPathStr ="/data/service/tomcat-spider-biz-8084/awifi-spider/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-spider-biz-8084";


        String userName = "root";
        String pwd = "awifi@123";
        String relativePath = "com";
        Path localRootPath = Paths.get(localRootPathStr);
        localRootPath.resolve(relativePath);
        //List<File> file = FileUtil.listFile(localRootPath.resolve(relativePath).toFile());
        //  List<File> fileList =new ArrayList<>();
        //   File file =new File("G:\\advert-workspace\\code\\trunk\\advert\\target\\classes\\com");
        //   fileList.add(file);
        String folderPath = localRootPath.resolve(relativePath).toString();
        System.out.println("正在对folderPath进行打包" + folderPath);

        for (int i = 0; i < serverIps.length; i++) {
            String serverIp = serverIps[i];
            try {
                com.dozenx.util.ZipUtil.foldToZip(folderPath, localRootPathStr + "\\a.zip");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //把文件上传到服务器指定的目录
            SshUtil.upload(localRootPathStr, remoteRootPathStr, tomcatPath, "a.zip", userName, pwd, serverIp);
          // SshUtil.restartTomcat(serverIp, userName, pwd, tomcatPath);
        }

    }

    public static void main(String args[]) {
        //
        // SshUtil.testExecCmd();
//        String userName = "root";
//        String pwd = "awifi@123";
        //ezhike
//        SshUtil.update("192.168.212.90","G:\\E-zhike\\code\\trunk\\ezhike-web\\target\\classes","/data/service/tomcat-ezhike-biz-8085"
//        ,"awifi-ezhike-web","root","awifi@123");

//
//        //
//        SshUtil.update("192.168.212.90","G:\\advert-workspace\\code\\trunk\\advert\\target\\classes","/data/service/tomcat-ezhike-biz-8085"
//                ,"awifi-ezhike-web","root","awifi@123");
//
//        SshUtil.update("192.168.212.90","G:\\advert-workspace\\code\\trunk\\advert\\target\\classes","/data/service/tomcat-ezhike-biz-8085"
//                ,"awifi-ezhike-web","root","awifi@123");
//
//        SshUtil.update("192.168.212.90","G:\\advert-workspace\\code\\trunk\\advert\\target\\classes","/data/service/tomcat-ezhike-biz-8085"
//                ,"awifi-ezhike-web","root","awifi@123");


//        String localRootPathStr = "G:\\E-zhike\\code\\trunk\\ezhike-web\\target\\classes";
//        String remoteRootPathStr = "/data/service/tomcat-ezhike-biz-8085/awifi-ezhike-web/WEB-INF/classes";
//        String tomcatPath = "/data/service/tomcat-ezhike-biz-8085";
//
     //   SshUtil.AlphaAdvertUpdateTomecatWebappAndRestart();

        SshUtil.AlphaSSCUpdateTomecatWebappAndRestart();
        //  SshUtil .updateTomecatWebappAndRestart();
    }
}
