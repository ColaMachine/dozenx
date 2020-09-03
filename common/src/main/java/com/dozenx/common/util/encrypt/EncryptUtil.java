package com.dozenx.common.util.encrypt;

import com.dozenx.common.util.CommonUtils;
import com.dozenx.common.util.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;


/**
 * 字符串的加密和解密
 *
 * @author awifi-core
 * @date 2015年1月6日 下午6:07:31
 */
public final class EncryptUtil {
    /**
     *
     */
    private static final String ALGORITHM = "DES";

    /**
     * The Default Key.
     */
    public static final String DEFAULT_KEY = "asdfsadf@#$%^$%^%^&*&asdf24243423234";

    /**
     * 说明:加密
     *
     * @param originalString 原始字符串（待加密）
     * @return
     * @throws Exception
     */
    public static String encrypt(final String originalString) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
        return CommonUtils.parseHexStringFromBytes(bEn);
    }

    /**
     * 说明:加密
     *
     * @param originalString 原始字符串（待加密）
     * @param key            按自定义键值加密
     * @return
     * @throws Exception
     */
    public static String encrypt(final String originalString, final String key) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
        return CommonUtils.parseHexStringFromBytes(bEn);
    }

    /**
     * 加密实际方法
     *
     * @param originalByte 原始字符数组（待加密）
     * @param key          加密键值
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey keySpec = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
        //执行加密操作
        return cipher.doFinal(originalByte);

    }

    /**
     * 解密
     *
     * @param encryptedString 密文字符串（待解密）
     * @return
     * @throws Exception
     */
    public static String decrypt(final String encryptedString) throws Exception {
        byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
        return new String(orginal);
    }

    /**
     * 解密
     *
     * @param encryptedString 密文字符串（待解密）
     * @param key             解密键值
     * @return
     * @throws Exception
     */
    public static String decrypt(final String encryptedString, final String key) throws Exception {
        byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, key.getBytes());
        return new String(orginal);
    }

    /**
     * 解密实际方法
     *
     * @param encryptedByte 密文字符数组（待解密）
     * @param key           解密键值
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey keySpec = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //加密和解密的区别在第一个参数
        cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
        return cipher.doFinal(encryptedByte);
    }


    /**
     * byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串转成byte数组
     *
     * @param
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        int hexLength = length;
        while (hexLength % 8 != 0) {
            hexLength++;
        }
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[hexLength];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符串转16进制字符串
     *
     * @param str
     * @return
     */
    public static String stringToHexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转字符串
     *
     * @param str
     * @return
     */
    public static String hexStringToString(String str) {
        byte[] baKeyword = new byte[str.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            str = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return str;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 加密算法
     *
     * @param data 加密数据
     * @param key  秘钥
     * @return 加密结果
     */
    public static byte[] desEnCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            // 创建Cipher对象
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            // 加解密
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密算法
     *
     * @param data 解密数据
     * @param key  秘钥
     * @return 解密结果
     */
    public static byte[] desDeCryt(byte[] data, byte[] key) {
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * DES加密方法
     *
     * @param message   加密数据
     * @param keyString 密钥
     * @return 加密结果
     */
    public static String encryptByDes(String message, String keyString) {
        String dataHexString = stringToHexString(message);
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesToHexString(result);
    }

    /**
     * DES解密方法
     *
     * @param dataHexString 解密数据
     * @param keyString     密钥
     * @return
     */
    public static String decryptByDes(String dataHexString, String keyString) {
        String keyHexString = stringToHexString(keyString);
        byte[] data = hexStringToBytes(dataHexString);
        byte[] key = hexStringToBytes(keyHexString);
        byte[] result = null;
        try {
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory;
            DESKeySpec dks = new DESKeySpec(key);
            keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStringToString(bytesToHexString(result)).trim();
    }

    public static String encryptBySHA1(String content) {
        SHA1 sha1 = new SHA1();
        return sha1.getDigestOfString(content.getBytes());
    }


    public static void main(String args[]) {


        String ssid = "chinanet";
        String pwd = "12345678";
        String sec = "1";
        String s1 = ssid;
        String s2 = "P:" + pwd + ";C:NQf7lE";
        System.out.println("s1:" + s1);
        System.out.println("s2:" + s2);

        String k1 = Base64Util.encode(s1.getBytes());
//		String e2=s2;
        String deivcemac = "3TPB0233532NDDF";
        String reverseDeviceMac = StringUtil.reverse(deivcemac);

        char[] en = bitcode(s2.toCharArray(), deivcemac.getBytes(), reverseDeviceMac.getBytes());
        String e2 = new String(en);
        System.out.println("k1：" + k1);
        System.out.println("e2：" + e2);
        String k2 = Base64Util.encode(e2.getBytes());
        System.out.println("k2:" + k2);
        System.out.println("k1|k2=" + k1 + "|" + k2);

        byte[] oriK2Byte = Base64Util.decode(k2);
        System.out.println("k2" + oriK2Byte);
        for (byte bit : oriK2Byte) {
            System.out.print(bit);
        }
        System.out.println("");
        k2 = "VT0wMjsyMT4/PjtLPElXbjNpTQ==";
        byte[] decodek2Ary = Base64Util.decode(k2);

        String oriK2 = new String(decodek2Ary);

        System.out.println("after decode bitcode s2:" + oriK2);

        String decryptK2 = new String(encryptWIFIInfo( deivcemac.getBytes(),oriK2.toCharArray()));
        System.out.println("ori s2:" + decryptK2);
        //	System.out.println("ori s2 after base64:"+Base64Util.getFromBASE64(decryptK2));
        System.out.println("ori ssid:" + Base64Util.getFromBASE64(k1));

//		System.out.println(bitcode(en,mac.getBytes(),mac1.getBytes()));
    }


    public static void main2(String[] args) {
        String username_id = "石鹏皮皮@126.com";
        try {
            String cookieValue = EncryptUtil.encrypt(username_id);
            System.out.println(cookieValue);
            String value = EncryptUtil.decrypt(cookieValue);
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 浣嶅姞瀵嗚В瀵嗙畻娉�
     *
     * @param str 鏄庢枃锛堝瘑鏂囷級
     * @param s1  瀵嗛挜s1锛堣澶囩爜锛�
     * @param s2  瀵嗛挜s2锛堣澶囬�鐮侊級
     * @return 瀵嗘枃锛堟槑鏂囷級
     */
    public static char[] bitcode(char[] str, byte[] s1, byte[] s2) {
        int strLen, n;
        char[] wen;
        strLen = str.length;
        wen = new char[strLen];
        for (int i = 0; i < strLen; i++) {
            if (i % 2 == 0) {
                n = (i % (2 * s1.length)) / 2;
                wen[i] = (char) (str[i] ^ s1[n]);
                System.out.println("第" + i + "次" + (int) str[i] + " ^ " + " " + s1[n] + " =" + (int) wen[i]);
            } else {
                n = (i % (2 * s2.length) - 1) / 2;

                wen[i] = (char) (str[i] ^ s2[n]);
                System.out.println("第" + i + "次" + (int) str[i] + " ^ " + " " + s2[n] + " =" + (int) wen[i]);
            }
        }
        return wen;
    }

    public static char[] bitcode2(char[] origin_s2, byte[] s1) {


        int ascii_idx;
        int dev_uid_len = s1.length;
        byte[] dev_uid = s1;


        int s2_len = origin_s2.length;


        for (int i = 1; i <= s2_len; ++i) {
            if (i % 2 == 0) {
                ascii_idx = (i % (4 * dev_uid_len) + 1) / 2;
                origin_s2[i - 1] ^= ascii_idx % 2 == 0 ? dev_uid[(ascii_idx - 1) / 2] / 10 : dev_uid[(ascii_idx - 1) / 2] % 10;
            } else {
                ascii_idx = (i % (4 * dev_uid_len) + 1) / 2;
                origin_s2[i - 1] ^= ascii_idx % 2 == 0 ? dev_uid[dev_uid_len - 1 - (ascii_idx - 1) / 2] % 10 : dev_uid[dev_uid_len - 1 - (ascii_idx - 1) / 2] % 10;
            }
        }

        return origin_s2;
    }


//
//	{ for (int i=0; i < textlen; i++) { if (i%2 == 0) {
//		index = (i%(keylen * 4))/2; result[i] = text[i]^keyArr[index]; } else { index = (i%(keylen * 4) - 1)/2; result[i] = text[i]^reversedKeyArr[index]; } }
//}if (result)
//void encryptWIFIInfo( char [] key, char[] text,char[] result,size_t length){
//		long keylen,textlen;
//		int index;
//		keylen = strlen(key);
//		textlen = length;
//		int keyArr[keylen * 2];
//		int reversedKeyArr[keylen * 2];
//		for (int i = 0; i < keylen; i++) {
//			keyArr[2*i] = key[i]/10;
//		} keyArr[2*i + 1] = key[i]%10; reversedKeyArr[keylen*2 - 2 - 2*i] = key[i]/10; reversedKeyArr[keylen*2 - 1 - 2*i] = key[i]%10;



   static  char[] encryptWIFIInfo(byte[] key, char[] text) {
        int keylen=0;
        int textlen=0;
        int index;
	   textlen = text.length;
        keylen = key.length;
		char[] result = new char[textlen];


        int[] keyArr = new int[keylen * 2];

        int[] reversedKeyArr = new int[keylen * 2];

        for (int i = 0; i < keylen; i++) {

            keyArr[2 * i] = key[i] / 10;

            keyArr[2 * i + 1] = key[i] % 10;

            reversedKeyArr[keylen * 2 - 2 - 2 * i] = key[i] / 10;

            reversedKeyArr[keylen * 2 - 1 - 2 * i] = key[i] % 10;

        }

        if (result != null) {

            for (int i = 0; i < textlen; i++) {

                if (i % 2 == 0) {

                    index = (i % (keylen * 4)) / 2;

                    result[i] = (char) (text[i] ^ keyArr[index]);

                } else {

                    index = (i % (keylen * 4) - 1) / 2;

                    result[i] = (char) (text[i] ^ reversedKeyArr[index]);

                }

            }

        }
        return result;
    }
}
