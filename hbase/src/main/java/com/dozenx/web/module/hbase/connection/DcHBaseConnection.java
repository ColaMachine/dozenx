package com.dozenx.web.module.hbase.connection;

import com.dozenx.common.util.PropertiesUtil;
import com.dozenx.web.util.ConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: MyHBaseConnection
 * @Description: HBase连接创建类
 * @author: Dingxc
 * @date: 2015年9月2日 上午10:24:34
 * @version: 0.0.1
 */
public class DcHBaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DcHBaseConnection.class);
    // 构造函数私有

    /**
     * 构造函数私有
     */
    private DcHBaseConnection() {
    }

    /** Hbase配置类 */
    // private static final Configuration CONF = HBaseConfiguration.create();

    /**
     * HBase连接
     */
    private static Connection CONN;

    private static Integer lock = 0;

    public static final boolean kerbrosEnable = Boolean.valueOf(PropertiesUtil.get("kerbrosEnable","hbase.properties"));//这里改成false 了还需要更改alpha下面的core-site.xml 和 hbase-*.xml文件 删除掉

    static final String UTF8 = "UTF-8";


    /**
     * @return Connection
     * @throws IOException if has error
     * @Title: getConnection
     * @Description: 创建HBase连接
     */
    public static Connection getConnection() {
        if (null == CONN) {
            synchronized (DcHBaseConnection.class) {
                if (CONN == null) {
                    CONN = ForceGetConnection();//ConnectionFactory.createConnection(CONF);
                }
                if (CONN == null) {
                    logger.error("error get hbase connection");
                }
            }
        }

        return CONN;
    }

    /**
     * @param conn Connection
     * @throws IOException if has error
     * @Title: closeConnection
     * @Description: 关闭HBase连接
     * @return: void
     */
    public static void closeConnection(Connection conn) throws IOException {
        if (null != conn && !conn.isClosed()) {
            conn.close();
        }
    }


    /**
     * @return Connection
     * @throws IOException if has error
     * @Title: getConnection
     * @Description: 创建HBase连接
     */
    public static Connection ForceGetConnection() {
        synchronized (lock) {//加锁 防止多次请求同时涌入
            logger.info("======begin hbase reconnection===========");
            Configuration conf = HBaseConfiguration.create();

            InputStream is = null;
            // InputStream pr;

            try {
                if (kerbrosEnable) {//如果启用了 kerbros 需要复杂一点的配置
                    logger.info("begin kerbros");
                    is = new FileInputStream(DcHBaseConnection.class.getClassLoader().getResource("hbase-site.xml").getPath());
                    //is = DcHBaseConnection.class.getResourceAsStream("/hbase-site.xml");
                    conf.addResource(is);
                    // pr = new FileInputStream(DcHBaseConnection.class.getClassLoader().getResource("kerberos.properties").getPath());
//            Properties props = new Properties();
//            props.load(pr);
                    String user = ConfigUtil.getConfig("userName");
                    String keytabPath = ConfigUtil.getConfig("keytabPATH");
                    String krbPath = ConfigUtil.getConfig("krb5PATH");
                    logger.debug("user:" + user);
                    logger.debug("keyPath:" + keytabPath);
                    logger.debug("krbPath:" + krbPath);
                    System.setProperty("sun.security.krb5.debug", "true");
                    System.setProperty("java.security.krb5.conf", krbPath);
                    conf.set("hbase.hconnection.threads.max", "100");//最大线程数设置为100
                    conf.set("hbase.hconnection.threads.core", "50");//核心线程数设置为50
                    conf.set("hbase.security.authentication", "Kerberos");
                    conf.set("hbase.rpc.timeout", "5000");//最大超时时间5s
                    ///=========new
//                conf.set("hbase.security.authorization", "true");
//                conf.set("hbase.regionserver.kerberos.principal", user);
//                conf.set("hbase.regionserver.keytab.file", keytabPath);


                    //          CONF.set("java.security.krb5.conf", krbPath);
                    UserGroupInformation.setConfiguration(conf);
                    UserGroupInformation.loginUserFromKeytab(user, keytabPath);
                    logger.info("end kerbros");
                } else {
                    logger.info("hbaseip " + ConfigUtil.getConfig("hbaseIp"));
                    conf.set("hbase.zookeeper.quorum", ConfigUtil.getConfig("hbaseIp"));
                }

//

                Connection connection = null;
                try {
                    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
                    connection = ConnectionFactory.createConnection(conf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //测试连接是否可用
                if (connection != null && testHbase(connection)) {
                    Connection lastConn = CONN;
                    CONN = connection;
                    if (lastConn != null) {
                        try {
                            logger.info("shudown last");
                            lastConn.close();//关闭上一个连接
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return connection;
                } else {
                    logger.info("获取连接失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
    }

    public static boolean testHbase(Connection connection) {
        String tableName = "testzw";
        HBaseAdmin admin = null;
        try {
            logger.debug("hbase connection:" + CONN);
            //获得admin，创建表
            admin = (HBaseAdmin) connection.getAdmin();
            // 判断表是否存在，如果不存在，创建
            if (!admin.tableExists(tableName)) {
                logger.debug("creating HBase table...");
                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
                logger.debug("add family to HBase table...");
                //加列族
                tableDesc.addFamily(new HColumnDescriptor("s"));
                admin.createTable(tableDesc);
            } else {
                logger.debug("table is exists...");
            }

            logger.debug("create HBase table OK...table name:" + tableName + ",family name:s");
            return true;
        } catch (IOException e) {
            logger.error("create HBase table error... message:" , e);
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
