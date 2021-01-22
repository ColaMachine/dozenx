package com.dozenx.common.util.encrypt;

//import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Base64;

public class Base64Util {

    // 将 s 进行 BASE64 编码
    public static String getBASE64(String s) {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    public static byte[] decode(String s) {
        if (s == null)
            return null;

        try {
            byte[] b = decryptBASE64(s);
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    private static char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',

            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };
    private int col;

    public static String encode(byte[] b) {
        if (b == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(b);
    }

    // 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {
        if (s == null)
            return null;
//			BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decryptBASE64(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] decodeBuffer(String s) throws IOException {
        return decryptBASE64(s);
    }

    public static void main(String args[]) {
        System.out.println(Base64Util.encode("EZVIZ-AB1-AQ7V".getBytes()));
    }


    /**
     * BASE64Encoder 加密
     *
     * @param data 要加密的数据
     * @return 加密后的字符串
     */
    public static String encryptBASE64(byte[] data) {
        // BASE64Encoder encoder = new BASE64Encoder();
        // String encode = encoder.encode(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(data);
        return encode;
    }

    /**
     * BASE64Decoder 解密
     *
     * @param data 要解密的字符串
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] decryptBASE64(String data) {
        // BASE64Decoder decoder = new BASE64Decoder();
        // byte[] buffer = decoder.decodeBuffer(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(data);
        return buffer;
    }
}
