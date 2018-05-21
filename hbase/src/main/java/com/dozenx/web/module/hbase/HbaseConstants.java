package com.dozenx.web.module.hbase;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 14:24 2018/3/1
 * @Modified By:
 */
public class HbaseConstants {
    //windows
    public  static String hbaseIp = "192.168.212.68,192.168.212.69,192.168.212.70";
    public  static String krb5PATH = "G:\\advert-workspace\\code\\trunk\\advert\\src\\main\\resources\\alpha\\hbase\\krb5.conf";
    public  static String keytabPATH = "G:\\advert-workspace\\code\\trunk\\advert\\src\\main\\resources\\alpha\\hbase\\hdfs.keytab";
    public static String userName = "hbase/alpha-cdn-hadoop01.novalocal@NOVALOCAL";
    public static String port ="2181";
    public static String winuitlsPath =  "G:\\software\\hadoop\\hadoop-common-2.2.0-bin-master\\";


    //广告pv family
    public static byte[] ADVERT_IMG_PV_FAMILY="s".getBytes();
    //广告pv column
    public static byte[] ADVERT_IMG_PV_COLUMN="s".getBytes();

    //广告pv family
    public static byte[] ADVERT_IMG_UV_FAMILY="s".getBytes();
    //广告pv column
    public static byte[] ADVERT_IMG_UV_COLUMN="s".getBytes();



    //广告浏览pv的 表名
    public static String ADVERT_IMG_REQ_PV="advert_req_pv";
    //广告浏览uv的 表名
    public static String ADVERT_IMG_REQ_UV="advert_req_uv";
    //广告点击的pv的 表名
    public static String ADVERT_IMG_CLICK_PV="advert_click_pv";
    //广告点击uv的 表名
    public static String ADVERT_IMG_CLICK_UV="advert_click_uv";

    //linux
//    public  static String hbaseIp = "192.168.212.68,192.168.212.69,192.168.212.70";
//    public  static String krb5PATH = "/etc/krb5.conf";
//    public  static String keytabPATH = "/root/hdfs.keytab";
//
//
//    public static String userName = "hbase/p05.awifi.com @AWIFI.COM";
//    public static String port ="2181";
//    public static String winuitlsPath =  "G:\\software\\hadoop\\hadoop-common-2.2.0-bin-master\\";
}
