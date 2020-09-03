package com.dozenx.web.util;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @ClassName: NetWorkUtil
 * @Description: 网络相关工具类
 * @author: Dingxc
 * @date: 2015年7月24日 上午11:07:24
 * @version: 0.0.1
 */
public class NetWorkUtil {

    /**
     * @Title: getLocalIPAddress
     * @Description: 获得本地机器IP地址
     * @return  String
     * @throws
     */
    public static String getLocalIPAddress() {
        InetAddress addr = null;
        String ipAddrStr = "";
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (null != addr) {
            byte[] ipAddr = addr.getAddress();

            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
        }
        return ipAddrStr;
    }

    /**
     * @Title: getRequestIPAddress
     * @Description: 获得请求方的IP地址
     * @param request HttpServletRequest
     * @return  String
     * @throws
     */
    public static String getRequestIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(){
        String ip="192.168.213.91";
    }



    public static void main(String[] args) {
        // 测试ip 221.232.245.73 湖北武汉
        String ip = "221.232.245.73";
        String address = "";
        try {
         address = new NetWorkUtil().getAddresses("ip="+ip, "utf-8");
        } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
         System.out.println(address);
         // 输出结果为：中国 湖北省 武汉市
    }



     /**  3      * @param content
     *   请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding
     *   服务器端请求编码。如GBK,UTF-8等
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAddresses(String content, String encodingString) throws UnsupportedEncodingException {
        // 这里调用pconline的接口
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = this.getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            // 处理返回的省市区信息
            System.out.println("IP====="+returnStr);
            String[] temp = returnStr.split(",");
            if(temp.length<3){
                return "0";                                        //无效IP，局域网测试
            }
            String region = (temp[5].split(":"))[1].replaceAll("\"", "");
            region = decodeUnicode(region);                        // 省
            System.out.println("region = "+region);

            String country = "";
            String area = "";
            // String region = "";
            String city = "";
            String county = "";
            String isp = "";
            System.out.println("temp的长度="+temp.length);
            for (int i = 0; i < temp.length; i++) {
                switch (i) {
//如果使用的是新浪的接口，那这里的需要修改，case:3 4 5分别对应国家，省，市区
                    case 1:
                        country = (temp[i].split(":"))[2].replaceAll("\"", "");
                        country = decodeUnicode(country);            // 国家
                        break;
                    case 3:
                        area = (temp[i].split(":"))[1].replaceAll("\"", "");
                        area = decodeUnicode(area);                // 地区
                        break;
                    case 5:
                        region = (temp[i].split(":"))[1].replaceAll("\"", "");
                        region = decodeUnicode(region);            // 省份
                        break;
                    case 7:
                        city = (temp[i].split(":"))[1].replaceAll("\"", "");
                        city = decodeUnicode(city);                // 市区
                        break;
                    case 9:
                        county = (temp[i].split(":"))[1].replaceAll("\"", "");
                        county = decodeUnicode(county);            // 地区
                        break;
                    case 11:
                        isp = (temp[i].split(":"))[1].replaceAll("\"", "");
                        isp = decodeUnicode(isp);                 // ISP公司
                        break;
                }
            }
            System.out.println(country+"="+area+"="+region+"="+city+"="+county+"="+isp);
            return region;
        }
        return null;
    }



    /**
     * @param urlStr
     *   请求的地址
     * @param content
     *   请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding
     *   服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection(); // 新建连接实例
            connection.setConnectTimeout(2000);                     // 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);                        // 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);                           // 是否打开输出流 true|false
            connection.setDoInput(true);                            // 是否打开输入流true|false
            connection.setRequestMethod("POST");                    // 提交方法POST|GET
            connection.setUseCaches(false);                         // 是否缓存true|false
            connection.connect();                                   // 打开连接端口
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes(content);                                // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();                                            // 刷新
            out.close();                                            // 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据 ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();                            // 关闭连接
            }
        }
        return null;
    }

    /**
     * unicode 转换成 中文
     *
     * @author fanhui 2007-3-15
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed  encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}
