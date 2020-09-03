package com.dozenx.common.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLUtil {
	
	/**
	 * 获取项目classpath目录的绝对路径
	 * @return classes目录的绝对路径<br/>
	 * 	file:/F:/tomcat/webapps/J2EEUtil/WEB-INF/classes/
	 */
	public static String connact(String prefix,String affix) {
		//请

		String newUrl ="";
		if(prefix.endsWith("/")){//如果两个都是绝对路径
			if(affix.startsWith("/")){
				newUrl =prefix+affix.substring(1);
			}else{
				newUrl =prefix+affix;
			}
		}/*else if(prefix.startsWith("http://")){

			int index= prefix.indexOf("/",7);
			prefix= prefix.substring(0,index);
			newUrl = prefix+affix;
		}*/else{
			if(affix.startsWith("/")){
				newUrl =prefix+affix;
			}else{
				newUrl =prefix+"/"+affix;
			}
		}

		return newUrl;
	}




	public static class UrlEntity {
		/**
		 * 基础url
		 */
		public String baseUrl;
		/**
		 * url参数
		 */
		public Map<String, String> params;
	}
	public static String getFileName(String url){
		if(StringUtil.isBlank(url))
			return null;
		int index = url.indexOf("?");
		if(index!=-1){
			url=url.substring(0,index);
		}
		int lastSlashIndex = url.lastIndexOf("/");
		if(lastSlashIndex!=-1){
			String fileName = url.substring(lastSlashIndex+1);
			return fileName;
		}
		return null;
	}
	/**
	 * 解析url
	 *
	 * @param url
	 * @return
	 */
	public static UrlEntity parse(String url) {
		UrlEntity entity = new UrlEntity();
		if (url == null) {
			return entity;
		}
		url = url.trim();
		if (url.equals("")) {
			return entity;
		}
		String[] urlParts = url.split("\\?");
		entity.baseUrl = urlParts[0];
		//没有参数
		if (urlParts.length == 1) {
			return entity;
		}
		//有参数
		String[] params = urlParts[1].split("&");
		entity.params = new HashMap<>();
		for (String param : params) {
			String[] keyValue = param.split("=");
			entity.params.put(keyValue[0], keyValue[1]);
		}

		return entity;
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		UrlEntity entity = parse(null);
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://www.123.com");
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://www.123.com?id=1");
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://www.123.com?id=1&name=小明");
		System.out.println(entity.baseUrl + "\n" + entity.params);

		System.out.println("文件名测试"+getFileName("http://www.123.com/abc.xls"));
	}
	public static void main1(String args[]){
		String url ="nohup ./telnet>a.txt 2>1 &";
		System.out.println(URLEncoder.encode(url));
	}
}
