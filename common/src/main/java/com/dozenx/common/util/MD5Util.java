package com.dozenx.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static char md5Chars[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static MessageDigest messagedigest;

	/* 获取一个文件的md5码 */
	public static String getFileMD5String(File file) throws Exception {
		messagedigest = MessageDigest.getInstance("MD5");
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);

		return bufferToHex(messagedigest.digest());
	}

	/* 获取一个字符串的md5码 */
	public static String getStringMD5String(String str) throws Exception {
		messagedigest = MessageDigest.getInstance("MD5");
		messagedigest.update(str.getBytes());
		return bufferToHex(messagedigest.digest());
	}

	/* 验证一个字符串和一个MD5码是否相等 */
	public static boolean check(String str, String md5) throws Exception {
		if (getStringMD5String(str).equals(md5))
			return true;
		else
			return false;
	}

	/* 验证一个文件和一个MD5码是否相等 */
	public static boolean check(File f, String md5) throws Exception {
		if (getFileMD5String(f).equals(md5))
			return true;
		else
			return false;
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = md5Chars[(bt & 0xf0) >> 4];
		char c1 = md5Chars[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String args[]){
		try {
			long time =System.currentTimeMillis();
			//String fileMd5= getFileMD5(new File("G:\\software\\操作系统\\Win7 32\\cn_windows_7_ultimate_with_sp1_x86_dvd_618763.iso"));
			//System.out.println("cost"+(System.currentTimeMillis()-time)+" file md5:"+fileMd5);
			System.out.println(MD5Util.getStringMD5String("awifi123"));
			//System.out.println(MD5Util.getStringMD5String("awifi@123"));
			//System.out.println(MD5Util.getStringMD5String(MD5Util.getStringMD5String("awifi@123")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String getFileMD5(File file){
		BigInteger bigInt = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = fis.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, length);
			}
			bigInt = new BigInteger(1, md.digest());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bigInt.toString(16);
	}
}