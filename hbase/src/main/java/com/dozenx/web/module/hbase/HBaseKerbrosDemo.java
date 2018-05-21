package com.dozenx.web.module.hbase;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HBaseKerbrosDemo {
	private static Configuration conf = null;

	static {
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
		conf.set("hbase.zookeeper.quorum", "192.168.212.68,192.168.212.69,192.168.212.70");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.security.authentication", "kerberos");

		UserGroupInformation.setConfiguration(conf);
		try {
			UserGroupInformation.loginUserFromKeytab("hbase/p10.awifi.com@AWIFI.COM", "/root/hdfs.keytab");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<JSONObject> scanSpan(String startRow, String stopRow, String tableName, String family, String column) throws Exception {
//		HTable table = new HTable(conf, tableName);
//		System.out.println("tablename:" + new String(table.getTableName()));
//		Scan s = new Scan();
//		ResultScanner rs = table.getScanner(s);
//		for (Result r : rs) {
//			System.out.println(r.toString());
//			KeyValue[] kv = r.raw();
//			for (int i = 0; i < kv.length; i++) {
//				System.out.print(new String(kv[i].getRow()) + "");
//				System.out.print(new String(kv[i].getFamily()) + ":");
//				System.out.print(new String(kv[i].getQualifier()) + "");
//				System.out.print(kv[i].getTimestamp() + "");
//				System.out.println(new String(kv[i].getValue()));
//			}
//		}
		TableName tname = null;
		Table table = null;
		HBaseUtil util = new HBaseUtil();
		Connection conn = null;
		ResultScanner scanner = null;
		List<JSONObject> json = new ArrayList<JSONObject>();
		try {
			conn = util.getConnection();
			tname = TableName.valueOf(tableName);
			table = conn.getTable(tname);
			Scan scan = new Scan();
			scan.setReversed(true);
			System.out.println("****************************" + startRow);
			System.out.println("*****************************" + stopRow);
			scan.setStartRow(stopRow.getBytes());
			scan.setStopRow(startRow.getBytes());
			scan.addColumn(family.getBytes(), column.getBytes());
			System.out.println("Scanning...");
			scanner = table.getScanner(scan);
			Result next = scanner.next();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (next != null) {
				JSONObject object = JSONObject
						.parseObject(Bytes.toString(next.getValue(family.getBytes(), column.getBytes())));
				String time_local = object.getString("time_local");
				Long time = new Long(Long.valueOf(time_local) * 1000);
				System.out.println("time " + time);
				String d = format.format(time);
				System.out.println("format time " + d);
				object.put("time_local", d);// 将数据中的timestap格式替换为yyyy-MM-dd HH:mm:ss格式
				String uri_request = object.getString("uri");
				String uri_request_new = "";
				switch (uri_request) {
				case "0":
					uri_request_new = "/aci10/login";
					break;
				case "1":
					uri_request_new = "/api10/login";
					break;
				case "2":
					uri_request_new = "/api/portal";
					break;
				case "3":
					uri_request_new = "/site";
					break;
				case "4":
					uri_request_new = "/auth/login";
					break;
				case "5":
					uri_request_new = "/auth/phoneAuth.htm";
					break;
				case "6":
					uri_request_new = "/auth/accountauth.htm";
					break;
				case "7":
					uri_request_new = "/auth/sendSmsCode.htm";
					break;
				case "8":
					uri_request_new = "/api10/ping";
					break;
				case "9":
					uri_request_new = "/api10/auth";
					break;
				default:
					uri_request_new = "";
					break;
				}
				object.put("uri", uri_request_new);
				json.add(object);
				next = scanner.next();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			table.close();
			scanner.close();
			conn.close();
		}
		return json;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HBaseKerbrosDemo.scanSpan("1","2","h_span","family","column");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}