package com.dozenx.web.module.hbase.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.dozenx.web.module.hbase.HbaseConstants;
import com.dozenx.web.util.ConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * @ClassName: MyHBaseConnection
 * @Description: HBase连接创建类
 * @author: Dingxc
 * @date: 2015年9月2日 上午10:24:34
 * @version: 0.0.1
 */
public class DcHBaseConnection {

    // 构造函数私有
    /**构造函数私有*/
    private DcHBaseConnection() {}

    /** Hbase配置类 */
    private static final Configuration CONF = HBaseConfiguration.create();

    /** HBase连接 */
    private static Connection CONN;
    
    
    static final String UTF8 = "UTF-8";

    static {
        InputStream is;
        InputStream pr;

        try {
            is = new FileInputStream(DcHBaseConnection.class.getClassLoader().getResource("hbase-site.xml").getPath());
            //is = DcHBaseConnection.class.getResourceAsStream("/hbase-site.xml");
            CONF.addResource(is);
//            pr = new FileInputStream(DcHBaseConnection.class.getClassLoader().getResource("kerberos.properties").getPath());
//
//            Properties props = new Properties();
//            props.load(pr);
            String user = ConfigUtil.getConfig("userName");
            String keyPath = ConfigUtil.getConfig("keytabPATH");
                   String krbPath = ConfigUtil.getConfig("krb5PATH");
            System.out.println("user:"+user);
            System.out.println("keyPath:"+keyPath);
            System.out.println("krbPath:"+krbPath);
            System.setProperty("java.security.krb5.conf",krbPath);

            CONF.set("hbase.security.authentication", "Kerberos");
            //          CONF.set("java.security.krb5.conf", krbPath);
            UserGroupInformation.setConfiguration(CONF);
            UserGroupInformation.loginUserFromKeytab(user, keyPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: getConnection
     * @Description: 创建HBase连接
     * @throws IOException if has error
     * @return  Connection
     */
    public static Connection getConnection() {
        if (null == CONN) {
            try {
                CONF.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
                CONN = ConnectionFactory.createConnection(CONF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CONN;
    }

    /**
     * @Title: closeConnection
     * @Description: 关闭HBase连接
     * @param conn Connection
     * @throws IOException if has error
     * @return: void
     */
    public static void closeConnection(Connection conn) throws IOException {
        if (null != conn && !conn.isClosed()) {
            conn.close();
        }
    }
}
