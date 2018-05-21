package com.dozenx.web.module.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

public class HBaseUtil {

	// private String QUORUM = "172.20.3.4,172.20.3.41,172.20.3.51";
	private String QUORUM = "172.20.3.5,172.20.3.6,172.20.3.7,172.20.3.8,172.20.3.9";
	private String CLIENTPORT = "2181";
	Connection conn = null;

	public Connection getConnection() {
		Configuration conf = new HBaseConfiguration().create();
		// 这个配置文件主要是记录 kerberos的相关配置信息，例如KDC是哪个IP？默认的realm是哪个？
		// 如果没有这个配置文件这边认证的时候肯定不知道KDC的路径喽
		// 这个文件也是从远程服务器上copy下来的
		System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");

		conf = HBaseConfiguration.create();
		conf.set("hadoop.security.authentication", "Kerberos");
		// 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
		// 这样我们就不需要交互式输入密码了
		conf.set("keytab.file", "/root/hdfs.keytab");
		// 这个可以理解成用户名信息，也就是Principal
		conf.set("kerberos.principal", "hbase/1722.myip.domain@HADOOP.COM");
		conf.set("hbase.master.kerberos.principal", "hbase/_HOST@AWIFI.COM");
		conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@AWIFI.COM");
		conf.set("hbase.zookeeper.quorum", QUORUM);
		conf.set("hbase.zookeeper.property.clientPort", CLIENTPORT);
		conf.set("hbase.security.authentication", "kerberos");

		UserGroupInformation.setConfiguration(conf);
		try {
			UserGroupInformation.loginUserFromKeytab("hbase/p10.awifi.com@AWIFI.COM", "/root/hdfs.keytab");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// public Connection getConnectrion() {
	// try {
	// Configuration conf = new HBaseConfiguration().create();
	// conf.set("hbase.zookeeper.property.clientPort", CLIENTPORT);
	// conf.set("hbase.zookeeper.quorum", QUORUM);
	// conn = ConnectionFactory.createConnection(conf);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return conn;
	// }
}
