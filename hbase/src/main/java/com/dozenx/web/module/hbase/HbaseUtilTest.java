package com.dozenx.web.module.hbase;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.JsonUtil;
import com.dozenx.web.module.hbase.HbaseConstants;
import com.dozenx.web.util.ConfigUtil;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 10:41 2018/2/28
 * @Modified By:
 */
public class HbaseUtilTest {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HbaseUtilTest.class);

    private static String SERIES = "s";
    private static String TABLENAME = "AF_TABLE";
    private static Connection conn;



    public static void init() {
       init2();//
        //initWindows();
        System.out.println("ddd");
    }
//    public static void init1() {
//
//        // System.setProperty("hadoop.home.dir", "D:\\hbase\\hadoop-common-2.2.0-bin-master");
//        // System.setProperty("java.security.krb5.conf", ConfigUtil.getConfig("krb5PATH"));
//
//        Configuration config = HBaseConfiguration.create();
//        // config.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
//        config.set("hadoop.security.authentication" , "Kerberos" );
//        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
//        // 这样我们就不需要交互式输入密码了
//        //config.set("keytab.file" ,ConfigUtil.getConfig("keytabPATH"));
//        // 这个可以理解成用户名信息，也就是Principal
//        //config.set("kerberos.principal" , HbaseConstants.userName );
//        logger.info("username"+ConfigUtil.getConfig("userName"));
//        logger.info("keytabPATH"+ConfigUtil.getConfig("keytabPATH"));
//
//        config.set("hbase.master.kerberos.principal", ConfigUtil.getConfig("userName"));
//        config.set("hbase.regionserver.kerberos.principal",  ConfigUtil.getConfig("userName"));
//
//        try {
//            UserGroupInformation.setConfiguration(config);
//            UserGroupInformation. loginUserFromKeytab(ConfigUtil.getConfig("userName"), ConfigUtil.getConfig("keytabPATH") );
//            System.out.println("hello");
//            conn = ConnectionFactory.createConnection(config);
//            System.out.println("连接是否关闭了"+conn.isClosed());
//            createTable(TABLENAME, SERIES);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    public static void init2() {
        System.out.println();

        String keytabPath =PathManager.getInstance().getClassPath().resolve(ConfigUtil.getConfig("addPropertiesPath")).resolve("hbase/hdfs.keytab").toString();

        String krb5Path =PathManager.getInstance().getClassPath().resolve(ConfigUtil.getConfig("addPropertiesPath")).resolve("hbase/krb5.conf").toString();





      // System.setProperty("hadoop.home.dir",HbaseConstants.winuitlsPath);
        System.setProperty("java.security.krb5.conf", krb5Path);

        logger.info("username"+ConfigUtil.getConfig("userName"));
        logger.info("keytabPATH"+keytabPath);
        logger.info("krb5Path"+krb5Path);

        String userName = ConfigUtil.getConfig("userName");

        Configuration config = HBaseConfiguration.create();
        // config.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        config.set("hadoop.security.authentication" , "Kerberos" );
        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
        // 这样我们就不需要交互式输入密码了
        //config.set("keytab.file" ,ConfigUtil.getConfig("keytabPATH"));
        // 这个可以理解成用户名信息，也就是Principal
        //config.set("kerberos.principal" , HbaseConstants.userName );


        config.set("hbase.master.kerberos.principal",userName );
        config.set("hbase.regionserver.kerberos.principal",   userName);

        try {
            UserGroupInformation.setConfiguration(config);
            UserGroupInformation. loginUserFromKeytab( userName, keytabPath );
            System.out.println("hello");
            conn = ConnectionFactory.createConnection(config);
            System.out.println("连接是否关闭了"+conn.isClosed());
            createTable(TABLENAME, SERIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initWindows() {

         System.setProperty("hadoop.home.dir",HbaseConstants.winuitlsPath);
         System.setProperty("java.security.krb5.conf", HbaseConstants.krb5PATH);

        Configuration config = HBaseConfiguration.create();
        // config.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        config.set("hadoop.security.authentication" , "Kerberos" );
        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
        // 这样我们就不需要交互式输入密码了
        //config.set("keytab.file" ,ConfigUtil.getConfig("keytabPATH"));
        // 这个可以理解成用户名信息，也就是Principal
        //config.set("kerberos.principal" , HbaseConstants.userName );
        logger.info("username",HbaseConstants.userName);
        logger.info("keytabPATH",HbaseConstants.keytabPATH);

        config.set("hbase.master.kerberos.principal", HbaseConstants.userName);
        config.set("hbase.regionserver.kerberos.principal",   HbaseConstants.userName);

        try {
            UserGroupInformation.setConfiguration(config);
            UserGroupInformation. loginUserFromKeytab( HbaseConstants.userName, HbaseConstants.keytabPATH );
            System.out.println("hello");
            conn = ConnectionFactory.createConnection(config);
            System.out.println("连接是否关闭了"+conn.isClosed());
            createTable(TABLENAME, SERIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initWithNo() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.212.90");

        try {

            conn = ConnectionFactory.createConnection(config);
            System.out.println("连接是否关闭了" + conn.isClosed());
            createTable(TABLENAME, SERIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //   System.setProperty("hadoop.home.dir", "D:\\hbase\\hadoop-common-2.2.0-bin-master");
        //System.setProperty("java.security.krb5.conf", ConfigUtil.getConfig("keytabPATH"));

    }

    //创建表
    public static void createTable(String tableName, String seriesStr) throws IllegalArgumentException, IOException {
        Admin admin = null;
        TableName table = TableName.valueOf(tableName);
        try {
            admin = conn.getAdmin();

            if (!admin.tableExists(table)) {
                System.out.println(tableName + " table not Exists");
                HTableDescriptor descriptor = new HTableDescriptor(table);
                String[] series = seriesStr.split(",");
                for (String s : series) {
                    descriptor.addFamily(new HColumnDescriptor(s.getBytes()));
                }
                admin.createTable(descriptor);
            }
        } finally {
            IOUtils.closeQuietly(admin);
        }
    }

    public static Connection getConn() {
        init();
        return conn;
    }


    //添加数据
    public static void add(String rowKey, Map<String, Object> columns) throws IOException  {
        init();
        Table table = null;
        try {

            table = conn.getTable(TableName.valueOf(TABLENAME));
            Put put = new Put(Bytes.toBytes(rowKey));
            for (Entry<String, Object> entry : columns.entrySet()) {
                put.addColumn(SERIES.getBytes(), Bytes.toBytes(entry.getKey()),
                        Bytes.toBytes(entry.getValue()+"") );
            }
            table.put(put);
        } finally {
            IOUtils.closeQuietly(table);
        }
        System.out.print("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOK");
    }


    //根据rowkey获取数据
    public static Map<String, String> getAllValue(String rowKey) throws IllegalArgumentException, IOException {
        Table table = null;
        Map<String, String> resultMap = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(SERIES.getBytes());
            Result res = table.get(get);
            Map<byte[], byte[]> result = res.getFamilyMap(SERIES.getBytes());
            Iterator<Entry<byte[], byte[]>> it = result.entrySet().iterator();
            resultMap = new HashMap<String, String>();
            while (it.hasNext()) {
                Entry<byte[], byte[]> entry = it.next();
                resultMap.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()));
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
        return resultMap;
    }

    //根据rowkey和column获取数据
    public static String getValueBySeries(String rowKey, String column) throws IllegalArgumentException, IOException {
        Table table = null;
        String resultStr = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(SERIES), Bytes.toBytes(column));
            Result res = table.get(get);
            byte[] result = res.getValue(Bytes.toBytes(SERIES), Bytes.toBytes(column));
            resultStr = Bytes.toString(result);
        } finally {
            IOUtils.closeQuietly(table);
        }
        return resultStr;
    }

    //根据table查询所有数据
    public static void  getValueByTable() throws Exception {
        Map<String, String> resultMap = null;
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            ResultScanner rs = table.getScanner(new Scan());
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println(
                            "列：" + new String(keyValue.getFamily()) + "====值:" + new String(keyValue.getValue()));
                }
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
    }
    //删除表
    public static void dropTable(String tableName) throws IOException {
        Admin admin = null;
        TableName table = TableName.valueOf(tableName);
        try {
            admin = conn.getAdmin();
            if (admin.tableExists(table)) {
                admin.disableTable(table);
                admin.deleteTable(table);
            }
        } finally {
            IOUtils.closeQuietly(admin);
        }
    }




    public static void main(String[] args) throws Exception {
        init();

//
//        Configuration config = HBaseConfiguration.create();
//        config.set("hbase.zookeeper.quorum", "192.168.3.224");
//
//        try {
//
//            conn = ConnectionFactory.createConnection(config);
//            System.out.println("连接是否关闭了" + conn.isClosed());
//            createTable(TABLENAME, SERIES);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }






        //创建表
       // createTable(TABLENAME,"");

        //添加数据1
        String rowKey1 =  "2017-11-11";;
        Map<String, Object> columns = new HashMap<String, Object>();
        columns.put("original_data1", "original_data01_u1001c1001_1");
        columns.put("original_data2", "original_data01_u1001c1001_2");

        add(rowKey1,columns);
        //添加数据2

        Map<String, Object> columns2 = new HashMap<String, Object>();
        columns2.put("original_data1", "original_data02_u1001c1002_1");
        columns2.put("original_data2", "original_data02_u1001c1002_2");
        add(rowKey1,columns2);

        //查询数据1-1
        Map<String, String> map1=  getAllValue(rowKey1);
        for (Entry<String, String> entry : map1.entrySet()) {
            System.out.println("map1-"+entry.getKey()+":"+entry.getValue());
        }


        //查询数据2
        String original_data_value =  getValueBySeries(rowKey1,"original_data");
        System.out.println("original_data_value->"+original_data_value);

        //查看表中所有数据
        getValueByTable();
    }
}
