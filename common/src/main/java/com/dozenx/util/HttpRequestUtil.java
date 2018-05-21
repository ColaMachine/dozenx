package com.dozenx.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HttpRequestUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HttpRequestUtil.class);


    public static HttpURLConnection sendPostRequest(String path, String params) throws Exception {
        byte[] paramData = params.getBytes();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod("POST");
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);
        // 设定传送的内容类型
        conn.setRequestProperty("Content-Type", "image/jpeg");
        conn.setRequestProperty("Content-Length", String.valueOf(paramData.length));
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        // 设置 连接主机超时（单位：毫秒）
        conn.setConnectTimeout(10000);
        // 设置从主机读取数据超时（单位：毫秒）
        conn.setReadTimeout(10000);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(paramData);
        outputStream.flush();
        outputStream.close();


        return conn;
    }

    public static HttpURLConnection sendGetRequest(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 默认情况下是false;
        conn.setDoOutput(false);
        // 设置是否从httpUrlConnection读入，默认情况下是true
        conn.setDoInput(true);
        // Get 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        // 设置 连接主机超时（单位：毫秒）
        conn.setConnectTimeout(10000);
        // 设置从主机读取数据超时（单位：毫秒）
        conn.setReadTimeout(10000);
        return conn;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGet(String url, Map map) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            if (map.size() > 0) {
                String paramStr = MapUtils.join(map, "&");
                if (url.indexOf("?") > 0) {
                    url += "&" + paramStr;
                } else {
                    url += "?" + paramStr;
                }
            }
            return UrlRead(url);
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGetWithCookie(String url, Map map, Map<String, Cookie> cookieMap) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            if (map.size() > 0) {
                String paramStr = MapUtils.join(map, "&");
                if (url.indexOf("?") > 0) {
                    url += "&" + paramStr;
                } else {
                    url += "?" + paramStr;
                }
            }
            return UrlRead(url, cookieMap);
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }

    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param map 发送请求的URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendDelete(String urlStr, Map map) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //connection.setRequestProperty("name", "robben");
            connection.setRequestProperty("content-type", "text/html");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8");
            // 将要传递的集合转换成JSON格式

            // 组织要传递的参数
            out.write("" + map);
            out.flush();
            out.close();
            // 获取返回的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = null;
            StringBuffer content = new StringBuffer();
            while ((line = in.readLine()) != null) {
                // line 为返回值，这就可以判断是否成功
                content.append(line);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    public static String sendPut(String urlStr, Map map) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //connection.setRequestProperty("name", "robben");
            connection.setRequestProperty("content-type", "text/html");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8");
            // 将要传递的集合转换成JSON格式

            // 组织要传递的参数
            out.write("" + map);
            out.flush();
            out.close();
            // 获取返回的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = null;
            StringBuffer content = new StringBuffer();
            while ((line = in.readLine()) != null) {
                // line 为返回值，这就可以判断是否成功
                content.append(line);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGet(String url, String param, String ip, String port) {


        StringBuffer result = new StringBuffer("");
        Long startTime = System.currentTimeMillis();
        BufferedReader in = null;
        try {

            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("X-Forwarded-For", ip);
            connection.setRequestProperty("X-Forwarded-Port", port);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
           /* String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }*/

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n" + urlNameString + " \n cost time:" + (endTime - startTime) + "\n result:" + result);

        } catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            Long endTime = System.currentTimeMillis();
            //   logger.info("fail to httpget \n "+url+"   cost time:"+(endTime-startTime));
            // logger.error("send http get error use url: "+url+"error :"+"cost time:"+(endTime-startTime),e);
            logger.error("send http get error ", e);
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("close http request failt:", e2);
                // e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String UrlRead(String url) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性


            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            String responseCookie = connection.getHeaderField("Set-Cookie");// 取到所用的Cookie
            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n" + url + "  cost time:" + (endTime - startTime) + "\n result:" + result);
        } catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            Long endTime = System.currentTimeMillis();
            //logger.info("fail to httpget \n "+url+"   cost time:"+(endTime-startTime));
            //logger.error("send http get error use url: "+url+"error :"+e.getMessage());
            // logger.error("send http get error use url: "+url+"error :"+"cost time:"+(endTime-startTime),e);
            logger.error("send http get error " + url, e);
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String UrlRead(String url, Map<String, Cookie> cookieMap) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性


            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接

            HttpRequestUtil.setCookie(connection, cookieMap);
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            // String responseCookie = connection.getHeaderField("Set-Cookie");// 取到所用的Cookie

            Map newCookieMap = GetCookieMap(connection);
            cookieMap.putAll(newCookieMap);

            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n" + url + "  cost time:" + (endTime - startTime) + "\n result:" + result);
        } catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            Long endTime = System.currentTimeMillis();
            //logger.info("fail to httpget \n "+url+"   cost time:"+(endTime-startTime));
            //logger.error("send http get error use url: "+url+"error :"+e.getMessage());
            // logger.error("send http get error use url: "+url+"error :"+"cost time:"+(endTime-startTime),e);
            logger.error("send http get error " + url, e);
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGet(String url, String param) {

        try {
            if (!StringUtil.isBlank(param)) {

                if (url.indexOf("?") > 0) {
                    url += "&" + param;
                } else {
                    url += "?" + param;
                }
            }

            if (url.startsWith("https")) {
                return HttpsConnection.doGet(url, null, 2000, 2000);
            } else
                return UrlRead(url);
        } catch (Exception e) {
            return "400";
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGetWithCookie(String url, String param, Map<String, Cookie> cookieMap) {

        try {
            if (!StringUtil.isBlank(param)) {

                if (url.indexOf("?") > 0) {
                    url += "&" + param;
                } else {
                    url += "?" + param;
                }
            }

            if (url.startsWith("https")) {
                return HttpsConnection.doGet(url, null, 2000, 2000);
            } else
                return UrlRead(url, cookieMap);
        } catch (Exception e) {
            return "400";
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param url 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @modificationHistory.
     */
    public static String sendGet(String url) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            return UrlRead(url);
        } catch (Exception e) {
            return "400";
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", "text/html");

            conn.setRequestProperty("Accept-Encoding", "identity");

            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    public static String sendPost(String url, String param, String contentType) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", contentType);

            conn.setRequestProperty("Accept-Encoding", "identity");

            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            String session_value = conn.getHeaderField("Set-Cookie");
            logger.info("session_value" + session_value);
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    public static String sendPostWithCookie(String url, String param, String contentType, Map<String, Cookie> cookieMap) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", contentType);

            conn.setRequestProperty("Accept-Encoding", "identity");

            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", contentType);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);

            HttpRequestUtil.setCookie(conn, cookieMap);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }

            Map newCookieMap = GetCookieMap(conn);
            cookieMap.putAll(newCookieMap);


            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendX(String url, String param, String contentType, String httpType) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod(httpType);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", contentType);

            conn.setRequestProperty("Accept-Encoding", "identity");

            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendXWithCookie(String url, String param, String contentType, String httpType, Map<String, Cookie> cookieMap) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod(httpType);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", contentType);

            conn.setRequestProperty("Accept-Encoding", "identity");

            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", contentType);
            HttpRequestUtil.setCookie(conn, cookieMap);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            Map<String, Cookie> newCookieMap = GetCookieMap(conn);
            cookieMap.putAll(newCookieMap);
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 多个参数post
     *
     * @param surl   参数
     * @param params 参数
     * @return String
     * @throws MalformedURLException        异常
     * @throws IOException                  抛出IO异常
     * @throws UnsupportedEncodingException 编码异常
     * @author syf
     * @creationDate. 2015年7月14日 下午1:49:17
     */
    public static String sendPost(String surl, String[] params)
            throws MalformedURLException, IOException,
            UnsupportedEncodingException {
        URL url = new URL(surl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter outputstream = new OutputStreamWriter(
                connection.getOutputStream(), "UTF-8");
        StringBuffer param = new StringBuffer("");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                param.append(params[i]);
            }
            outputstream.write(param.toString()); // post的关键所在！
        }
        // remember to clean up
        outputstream.flush();
        outputstream.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine = "";
        StringBuffer sTotalString = new StringBuffer("");
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        InputStreamReader reader = new InputStreamReader(l_urlStream);
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString.append(sCurrentLine + "\r\n");
        }
        reader.close();
        l_reader.close();
        l_urlStream.close();
        // //System.out.println(sTotalString);
        return sTotalString.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty  是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params,
                                String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * @param url    参数
     * @param params 参数
     */
    public static void postForm(String url, Map<String, String> params) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out
                            .println("--------------------------------------");
                    System.out.println("Response content: "
                            + EntityUtils.toString(entity, "UTF-8"));
                    System.out
                            .println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向指定 URL 发送POST form表单请求方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> params) {
        OutputStream out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");

        try {
            String bodyString = MapUtils.join(params, "=", "&");
            System.out.println(bodyString);
            byte[] body = bodyString
                    .getBytes("utf-8");// ("[" + JSON.toJSONString(params) + "]")
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(body.length));

//            PrintWriter  out2 = new PrintWriter(conn.getOutputStream());
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = conn.getOutputStream();
            // 发送请求参数
            out.write(body);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    public static Map<String, Cookie> GetCookieMap(URLConnection conn) {
        String cookieStr = GetCookieStr(conn);
        String[] cookieStrAry = cookieStr.split(";");
        Map<String, Cookie> newCookieMap = new HashMap<>();
        for (int i = 0; i < cookieStrAry.length; i++) {
            if (StringUtil.isBlank(cookieStrAry[i])) {
                continue;
            }
            String[] newCookieStrAry = cookieStrAry[i].split("=");
            // newCookieMap.put(newCookieStrAry[0],newCookieStrAry[1]);

            Cookie cookie = new Cookie(newCookieStrAry[0], newCookieStrAry[1]);
            newCookieMap.put(newCookieStrAry[0], cookie);
        }
        return newCookieMap;
    }

    public static String GetCookieStr(URLConnection conn) {
        String key;
        String cookieVal;
        String cookieStr = "";
        for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("set-cookie")) {

                cookieVal = conn.getHeaderField(i);
                System.out.println("cookieVal:" + cookieVal);
                cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                cookieStr = cookieStr + cookieVal + ";";
            }
        }
        return cookieStr;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param httpclient
     * @param url        请求的URL地址
     * @param params     请求的查询参数,可以为null
     * @param charset    字符集
     * @param pretty     是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(CloseableHttpClient httpclient, String url, Map<String, String> params,
                                String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();


        HttpMethod method = new PostMethod(url);
        // method.setRequestHeader("Host","xxxx");
        method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Cache-Control", "max-age=0");
        // method.setRequestHeader("Origin","http://xxx.com");
        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //method.setRequestHeader("Referer","http://www.xxx.com/logging.php?action=login");
        //method.setRequestHeader("Accept-Encoding","gzip, deflate");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cookie", "is_use_cookiex=yes; cdb_cookietime=2592000; cdb_oldtopics=D2094663D; cdb_fid5=1480639303; cdb_sid=FwV7KV; is_use_cookied=yes");


        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {

            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param httpclient
     * @param url        请求的URL地址
     * @param params     请求的查询参数,可以为null
     * @param charset    字符集
     * @param pretty     是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(CloseableHttpClient httpclient, String url, Map<String, String> params,
                               String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);

        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 利用socket 进行通讯
     *
     * @param header
     * @param posturl
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    public static String doGetWithSocket(HttpHeader header, String posturl, HashMap params, String charset) throws IOException {
        String url = posturl;
        Long startTime = System.currentTimeMillis();

        if (url.startsWith("http://")) {
            url = url.substring(7);
        }
        int index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index);
        }
        String host = "";
        int port = 80;
        try {
            URL urlc = new URL("http://" + url);
            host = urlc.getHost();
            port = urlc.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InetAddress addr = InetAddress.getByName(host);
        System.out.println(addr.getHostAddress());
        Socket socket = new Socket(addr.getHostAddress(), 80);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), charset);
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
        // InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/zhongwen.txt")),"utf-8");
        InputStream inputStream = socket.getInputStream();

       /* byte[] bytes = new byte[10240];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes) ;
        int len=0;
        while((len=inputStream.read(bytes))!=-1){


        }*/
        InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
        //String headerTxt =FileUtil.readFile2Str("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/header.txt");
        BufferedReader bufferedReader = new BufferedReader(streamReader);


        String formData = "";


        Iterator iter = header.params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            formData += key + "=" + value + "&";
        }
        String cookie = "";
        iter = header.cookies.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            cookie += key + "=" + value + "; ";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("GET ").append(posturl).append(" HTTP/1.1").append("\r\n")
                .append("Host: ").append(header.Host).append("\r\n")
                .append("Connection: ").append(header.Connection).append("\r\n")
                // .append("Connection:").append(header.Connection).append("\r\n")
                .append("Cache-Control: ").append(header.Cache_Control).append("\r\n")
                //.append("Content-Length: ").append(formData.length()).append("\r\n")
                .append("Origin: ").append(header.Origin).append("\r\n")
                .append("User-Agent: ").append(header.User_Agent).append("\r\n")
                .append("Content-Type: ").append(header.Content_Type).append("\r\n")
                .append("Accept: ").append(header.Accept).append("\r\n")
                .append("Accept-Language: ").append(header.Accept_Language).append("\r\n")
                .append("Accept-Encoding: ").append(header.Accept_Encoding).append("\r\n")
                .append("Referer: ").append(header.Referer).append("\r\n")
                .append("Cookie: ").append(cookie).append("\r\n\r\n");


        // bufferedWriter.write(headerTxt);
        bufferedWriter.write(sb.toString());
        //bufferedWriter.write("\r\n");
        // bufferedWriter.write(formData);
        //bufferedWriter.write("\r\n");
        bufferedWriter.flush();
        StringBuffer receiveData = new StringBuffer();
        String s = "";
        //logger.info(sb.toString());
        boolean bodyStart = false;
        boolean chunked = false;
        // System.out.println("get开始接收数据");
        logger.info("get begin receive , timelapse:" + (System.currentTimeMillis() - startTime));
        while (null != (s = bufferedReader.readLine())) {
            logger.info(s);
            //logger.info("请求返回结果:"+s);
            //logger.info("get receive:" + s);
            if (StringUtil.checkAlphaNumeric(s) && s.length() == 4) {
                logger.info("this maybe chunksize:" + s);
            }
            if (bodyStart) {
                if (chunked) {
                    String numStr = s;
                    if (!StringUtil.checkAlphaNumeric(s) || s.length() != 4) {
                        // logger.error("应该是数字却捕捉到非数字字符串 in chunked:"+s);
                        continue;
                    } else {
                        int num = Integer.parseInt(numStr, 16);
                        /*s= bufferedReader.readLine();
                        if(s.length()>0){
                            logger.error("chun数字后应该跟着的是 in chunked:"+s);
                        }*/

                        logger.info("chunk size" + numStr + " num:" + num);
                        if (num == 0) {
                            break;
                        }
                        String data = "";
                        int chunkSum = 0;
                        while (/*data.length()<num*/chunkSum < num && (s = bufferedReader.readLine()) != null) {
                            if (StringUtil.checkAlphaNumeric(s) && s.length() > 0 && s.length() <= 4) {
                                logger.info("chunkSum:" + chunkSum + " chunksize:" + num);
                                logger.info("this maybe chunksize in chunk loop:" + s);
                            }
                            chunkSum += s.getBytes("gbk").length + 2;
                            logger.info("chunkSum:" + chunkSum + " chunksize:" + num + "conent:" + s);
                            data +=/*new String(s.getBytes(charset),charset)*/s + "\r\n";
                            //logger.info("post receive:" + s+" data length:"+data.length());
                        }
                        receiveData.append(data).append("\r\n");
                        continue;
                    }

                } else {
                    receiveData.append(s).append("\r\n");
                }
            } else if (s.contains("Transfer-Encoding: chunked")) {
                chunked = true;
            } else if (s.startsWith("Set-Cookie:")) {
                s = s.replace("Set-Cookie:", "");
                index = s.indexOf("=");
                String name = s.substring(0, index);
                String value = s.substring(index + 1);
                if (name.contains("=")) {
                    logger.error(" name substring endindex is wrong");
                    System.exit(0);
                }
                header.cookies.put(name, value);
            } else if (StringUtil.isBlank(s)) {
                logger.debug(" the body begin");
                bodyStart = true;
            }


        }

        logger.info("get receive data complete" + (System.currentTimeMillis() - startTime));
        return receiveData.toString();


    }

    /**
     * 利用socket 进行通讯
     *
     * @param header
     * @param posturl
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    public static String doPost(HttpHeader header, String posturl, HashMap params, String charset) throws IOException {
        Long startTime = System.currentTimeMillis();
        System.out.println("开始post请求");
        String url = posturl;
        String formdata = "formhash=afabcdb9&referer=index.php&loginfield=username&username=zjlgdxfj&password=123456fj&questionid=0&answer=&cookietime=2592000&loginmode=&styleid=&loginsubmit=%CC%E1+%26%23160%3B+%BD%BB";
        int contentLength = formdata.length();

        String headerTxt = FileUtil.readFile2Str("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/header.txt");

        if (url.startsWith("http://")) {
            url = url.substring(7);
        }
        int index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index);
        }
        String host = "";
        int port = 80;
        try {
            URL urlc = new URL("http://" + url);
            host = urlc.getHost();
            port = urlc.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InetAddress addr = InetAddress.getByName(host);
        System.out.println(addr.getHostAddress());
        Socket socket = new Socket(/*addr.getHostAddress()*/"173.192.169.27", 80);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), charset);
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
        // InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/zhongwen.txt")),"utf-8");

        InputStreamReader streamReader = new InputStreamReader(socket.getInputStream(), charset);
        //String headerTxt =FileUtil.readFile2Str("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/header.txt");
        BufferedReader bufferedReader = new BufferedReader(streamReader);


        String formData = "";

        Iterator iter = header.params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            formData += key + "=" + value + "&";
        }
        String cookie = "";
        iter = header.cookies.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            cookie += key + "=" + value + "; ";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("POST ").append(posturl).append(" HTTP/1.1").append("\r\n")
                .append("Host: ").append(header.Host).append("\r\n")
                .append("Connection: ").append(header.Connection).append("\r\n")
                // .append("Connection:").append(header.Connection).append("\r\n")
                .append("Cache-Control: ").append(header.Cache_Control).append("\r\n")
                .append("Content-Length: ").append(formData.length()).append("\r\n")
                .append("Origin: ").append(header.Origin).append("\r\n")
                .append("User-Agent: ").append(header.User_Agent).append("\r\n")
                .append("Content-Type: ").append(header.Content_Type).append("\r\n")
                .append("Accept: ").append(header.Accept).append("\r\n")
                .append("Accept-Language: ").append(header.Accept_Language).append("\r\n")
                .append("Accept-Encoding: ").append(header.Accept_Encoding).append("\r\n")
                .append("Referer: ").append(header.Referer).append("\r\n")
                .append("Cookie: ").append(cookie).append("\r\n\r\n");


        // bufferedWriter.write(headerTxt);
        bufferedWriter.write(sb.toString());
        //bufferedWriter.write("\r\n");
        bufferedWriter.write(formData);
        //bufferedWriter.write("\r\n");
        bufferedWriter.flush();
        StringBuffer receiveData = new StringBuffer();
        String s = "";
        // logger.info(sb.toString());
        boolean bodyStart = false;
        boolean chunked = false;

        //Transfer-Encoding:chunked

        logger.info("post begin receive,timelapse:" + (System.currentTimeMillis() - startTime));
        while (null != (s = bufferedReader.readLine())) {

            //logger.info(s);
            //  System.out.println(s);
            logger.info("post receive:" + s);
            if (bodyStart) {
                if (chunked) {
                    String numStr = s;
                    if (StringUtil.isBlank(s)) {
                        continue;
                    }
                    int num = Integer.parseInt(numStr, 16);
                    logger.info("chunk size" + numStr + " " + num);
                    if (num == 0) {
                        break;
                    }
                    String data = "";
                    int totalLength = 0;
                    while (data.length() < num && (s = bufferedReader.readLine()) != null) {

                        data += s + "\r\n";
                        //totalLength += s.getBytes(charset).length+2;
                        //logger.info("totalLength:"+totalLength);
                        logger.info("post receive:" + s + " data length:" + data.length());
                    }
                    receiveData.append(s).append("\r\n");
                    continue;
                } else {
                    receiveData.append(s).append("\r\n");
                }
            } else if (s.contains("Transfer-Encoding: chunked")) {
                chunked = true;
            } else if (s.startsWith("Set-Cookie:")) {
                s = s.replace("Set-Cookie:", "");
                index = s.indexOf("=");
                String name = s.substring(0, index);
                String value = s.substring(index + 1);
                if (name.contains("=")) {
                    logger.error(" name substring endindex is wrong");
                    System.exit(0);
                }
                header.cookies.put(name, value);
            } else if (StringUtil.isBlank(s)) {
                logger.debug(" the body begin");
                bodyStart = true;
            }

        }
        logger.info("post receive data complete:" + (System.currentTimeMillis() - startTime));
        return receiveData.toString();


    }

    public static void loginPostWithSocket() throws IOException {
        String formdata = "formhash=afabcdb9&referer=index.php&loginfield=username&username=zjlgdxfj&password=123456fj&questionid=0&answer=&cookietime=2592000&loginmode=&styleid=&loginsubmit=%CC%E1+%26%23160%3B+%BD%BB";
        int contentLength = formdata.length();
        Socket socket = new Socket("173.192.169.27", 80);
        System.out.println("contentLength" + contentLength);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), "gbk");
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
        // InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/zhongwen.txt")),"utf-8");

        InputStreamReader streamReader = new InputStreamReader(socket.getInputStream(), "gbk");
        String headerTxt = FileUtil.readFile2Str("/Users/luying/Documents/workspace/calendar/src/main/java/pachong/header.txt");
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        bufferedWriter.write(headerTxt);
        bufferedWriter.write(formdata);
        bufferedWriter.flush();
        System.out.println(headerTxt);
        StringBuffer receiveData = new StringBuffer();
        String s = "";
        while (null != (s = bufferedReader.readLine())) {
            //s= new String(s.getBytes("gbk"), "gbk");
            System.out.println(s);
            receiveData.append(s);
        }
        String loginConent = receiveData.toString();
    }


//
//    public static String doPost(DefaultHttpClient httpclient, String url, Map<String, String> params,
//                                String charset, boolean pretty) {
//        StringBuffer response = new StringBuffer();
//        // DefaultHttpClient client = new DefaultHttpClient();
//
//
//        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
//
//            public void process(
//                    final HttpRequest request,
//                    final HttpContext context) throws HttpException, IOException {
//                if (!request.containsHeader("Accept-Encoding")) {
//                    request.addHeader("Accept-Encoding", "gzip");
//                }
//            }
//
//        });
//
//        httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
//
//            public void process(
//                    final HttpResponse response,
//                    final HttpContext context) throws HttpException, IOException {
//                HttpEntity entity = response.getEntity();
//                Header ceheader = entity.getContentEncoding();
//                if (ceheader != null) {
//                    HeaderElement[] codecs = ceheader.getElements();
//                    for (int i = 0; i < codecs.length; i++) {
//                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
//                            response.setEntity(
//                                    new GzipDecompressingEntity(response.getEntity()));
//                            return;
//                        }
//                    }
//                }
//            }
//
//        });
//
//
//
//        //HttpMethod method = new PostMethod(url);
//        HttpPost method =new HttpPost(url);
//        // method.setRequestHeader("Host","www.xxx.com");
////        method.setRequestHeader("Connection","keep-alive");
////        method.setRequestHeader("Cache-Control","max-age=0");
////        method.setRequestHeader("Origin","http://www.xxxx.com");
////        method.setRequestHeader("Upgrade-Insecure-Requests","1");
////        method.setRequestHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
////        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
////        method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,**/*//*;q=0.8");
////        //method.setRequestHeader("Referer","http://www.xxxx.com/logging.php?action=login");
////        method.setRequestHeader("Accept-Encoding","gzip, deflate");
////        method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
////        method.setRequestHeader("Cookie","is_use_cookiex=yes; cdb_cookietime=2592000; cdb_oldtopics=D2094663D; cdb_fid5=1480639303; cdb_sid=FwV7KV; is_use_cookied=yes");
//
//
//        // 设置Http Post数据
//        if (params != null) {
//            HttpMethodParams p = new HttpMethodParams();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                p.setParameter(entry.getKey(), entry.getValue());
//            }
//            method.setParams(p);
//        }
//        try {
//
//
//            httpclient.execute(method);
//            if (1<3) {
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(method.getgetResponseBodyAsStream(),
//                                charset));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    if (pretty) {
//                        response.append(line).append(
//                                System.getProperty("line.separator"));
//                    } else {
//                        response.append(line);
//                    }
//                }
//                reader.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            method.releaseConnection();
//        }
//        return response.toString();
//    }


    /**
     * 下载文件到本地
     *
     * @param urlString 被下载的文件地址
     * @param filename  本地文件名
     * @throws Exception 各种异常
     */
    public static void download(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = null;
        try {
            os = new FileOutputStream(filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } finally {

            os.close();
            is.close();
        }
        // 完毕，关闭所有链接

    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {

        String contentType = request.getContentType();

        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        if (buffer == null) {
            return "";
        }
        return new String(buffer, charEncoding);
    }


    /**
     * 保存Cookies
     *
     * @param response servlet请求
     * @param value    保存值
     * @author jxf
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return response;
    }


    /**
     * 保存Cookies
     *
     * @param response  servlet请求
     * @param cookieMap 保存值
     * @author jxf
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, Map<String, Cookie> cookieMap) {


        Iterator it = cookieMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Cookie> entry = (Map.Entry<String, Cookie>) it.next();
            String key = entry.getKey();
            Cookie cookie = entry.getValue();


            cookie.setDomain("192.168.3.15");
            cookie.setPath("/");

            // 将Cookie添加到Response中,使之生效
            response.addCookie(cookie);
        }
        Cookie cookie = new Cookie("access", "123123");
        //cookie.setMaxAge(3600); 让cookie跟session时间走
        cookie.setMaxAge(30000);
        cookie.setDomain("192.168.3.15");
        cookie.setPath("/");

        response.addCookie(cookie);
        return response;
    }


    /**
     * 保存Cookies
     *
     * @param conn      servlet请求
     * @param cookieMap 保存值
     * @author jxf
     */
    public static void setCookie(URLConnection conn, Map<String, Cookie> cookieMap) {


        Iterator it = cookieMap.entrySet().iterator();
        String cookieStr = "";
        while (it.hasNext()) {
            Map.Entry<String, Cookie> entry = (Map.Entry<String, Cookie>) it.next();
            String key = entry.getKey();
            Cookie value = entry.getValue();
            cookieStr += key + "=" + value.getValue() + ";";
            // 将Cookie添加到Response中,使之生效

        }
        conn.setRequestProperty("Cookie", cookieStr);

    }


    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 从url 上保存文件(ex 图片)到本地
     *
     * @param url
     * @param name
     * @param path
     */

    public static void saveFileFromUrl(String url, String name, Path path) throws Exception {
        if (url.startsWith("https")) {
            HttpsConnection.saveFileFromUrl(url, name, path);
            return;
        }
        BufferedInputStream in = null;
        Long startTime = System.currentTimeMillis();
        OutputStream out = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedInputStream(
                    connection.getInputStream());

            File file = path.resolve(name).toFile();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            byte[] bts = new byte[1024];

            out = new FileOutputStream(file);
            int len = 0;
            while ((len = in.read(bts, 0, 1024)) > 0) {
                out.write(bts, 0, len);
            }
            //开始往外输出文件


            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n" + url + "  cost time:" + (endTime - startTime) + "\n result:");
        } catch (Exception e) {
            logger.error("send http get error " + url, e);
            throw new IOException(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * 从url 上保存文件(ex 图片)到本地
     *  如果报错直接抛出异常
     * @param url
     */

    public static InputStream getInputStream(String url) throws Exception {

        BufferedInputStream in = null;
        Long startTime = System.currentTimeMillis();

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedInputStream(
                    connection.getInputStream());


            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n" + url + "  cost time:" + (endTime - startTime) + "\n result:");

            return in;
            //开始往外输出文件



        } catch (Exception e) {
            logger.error("send http get error " + url, e);
            throw new IOException(e.getMessage());

        }
        // 使用finally块来关闭输入流

    }
    public static void main(String args[]) {
//
//        String url = "http://192.168.41.53/sms-service/sms/send?mobile=18368729738&msg=%E4%BA%BA%E8%84%B8%E8%AF%86%E5%88%AB%E5%A4%B1%E8%B4%A5&access_token=5aaf89c1bfaed238e1eea6c1";
//
//        String result = HttpRequestUtil.sendGet(url);
//        System.out.println(result);

        String url = "http://pic-bucket.nosdn.127.net/photo/0001/2018-04-07/DEQ8E03N00AP0001NOS.jpg";

        String result = null;
        try {
            HttpRequestUtil.saveFileFromUrl(url, "saveFileFromUrlPic", Paths.get("g:/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}


