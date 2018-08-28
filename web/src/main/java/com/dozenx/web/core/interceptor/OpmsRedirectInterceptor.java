
/**
* @Title: OpmsRedirectInterceptor.java
* @Package com.awifi.opms.base.interceptor
* @Description: TODO(用一句话描述该文件做什么)
* @author Administrator
* @date 2016年10月24日
* @version V1.0
*/
package com.dozenx.web.core.interceptor;

import com.dozenx.web.util.HttpPostUtil;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.MapUtils;
import com.dozenx.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;


/**
 * @ClassName:OpmsRedirectInterceptor
 * @Description:TODO
 * @author wujh
 * @date 2016年10月24日 上午10:42:19
 *
 */
public class OpmsRedirectInterceptor extends HandlerInterceptorAdapter {

    /**
     * 获取实际tob服务地址
     */

    /**  */
    private static final Log logger = LogFactory.getLog(OpmsRedirectInterceptor.class);

    /*
     * (非 Javadoc) <p>Title: preHandle</p> <p>Description:在业务处理器处理请求之前被调用,拦截所有opms/**的请求 </p>
     * 
     * @param request
     * 
     * @param response
     * 
     * @param handler
     * 
     * @return boolean
     * 
     * @throws Exception
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //这里的请求分为两种
        //原则是我把domain 换掉 把proxy去掉掉就可以了
        //原则 传上来一个proxy url 和

        //从request 获取一个原始带proxy的url


        //将proxy前面包括proxy 全部替换为proxy url



        // 营销装维平台地址 先写死 以后从properties 中获取
        String proxyUrl = request.getParameter("url");//类似于

        String host = request.getParameter("host");//类似于

       // String proxyDomain = host;

        //原来的url
        String requestUri = request.getRequestURI();
       String queryStr =  request.getQueryString();

        //截取后缀
       // int index = requestUri.indexOf("/proxy");
       // requestUri=requestUri+"?"+queryStr;
      //  String urlSuffix = requestUri.substring(index+"/proxy".length());//截取proxy后面的内容

        //拼接后的url为
        String newUrl  = com.dozenx.util.URLUtil.connact(host,proxyUrl);

//        if(newUrl.indexOf("?")>-1){
//            newUrl =newUrl.substring(0,newUrl.indexOf("?"));
//        }
        logger.info("newurl:"+newUrl);
        /*
        if(requestUri.startsWith("/api")){
            requestUri=requestUri.substring(4);
        }*/
        //String contextPath = request.getContextPath();
        // 获取请求的相对路径
       // String url = requestUri.substring(contextPath.length());
        //String usertoken = "";
        HashMap<String, Object> userInfoMap = new HashMap<String, Object>();
        // 获取session中的用户信息
       /* userInfoMap.put("id", sessionUser.getId());
        userInfoMap.put("provinceId", sessionUser.getProvinceId());
        userInfoMap.put("cityId", sessionUser.getCityId());
        userInfoMap.put("countyId", sessionUser.getAreaId());
        userInfoMap.put("roleId", sessionUser.getOrgId());*/
        
        
//        String path = request.getServletPath();
//        if(path.startsWith("/api") && !path.equals("/api")){
//            path=path.substring(4);
//        }
//        response.sendRedirect(newUrl);
//
//    return false;
       /* Matcher slashMatcher = Pattern.compile("/").matcher(opmsSchema);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if(mIdx == 3){
                break;
            }
        }
        int start =  slashMatcher.start();
        if(start>0){
            opmsSchema =opmsSchema.substring(0,start);
        }
        String redirectUrl = opmsSchema + path;
        logger.info("requestUri:" + requestUri);
        logger.info("contextPath:" + contextPath);
        logger.info("url:" + url);
        logger.info("redirectUrl:" + redirectUrl);
        logger.info("usertoken:" + usertoken);*/

     //   logger.info("path:" + path);
        HashMap<String, String> urlQueryParam = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        // 将所有参数都封装成map
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            /*
             * String[] paramValues = request.getParameterValues(paramName); if (paramValues.length == 1) { String
             * paramValue = paramValues[0]; if (paramValue.length() != 0) { // System.out.println("参数：" + paramName +
             * "=" + paramValue); urlQueryParam.put(paramName, paramValue); } }
             */
            if(paramName.equals("url")){
                continue;
            }
            urlQueryParam.put(paramName, URLEncoder.encode(paramValue));
        }
        // 包括用户信息
     /*   urlQueryParam.put("usertoken", JsonUtil.toJson(userInfoMap));
        urlQueryParam.put("pagesize", ""+Integer.parseInt(SysConfigUtil.getParamValue("page.pageSize")));
       */
        if (request instanceof MultipartHttpServletRequest) {
            logger.info("说明是文件上传");
        }
        String result = "";
        Map<String,Cookie> cookieMap = HttpRequestUtil.ReadCookieMap(request);
        // 开始获取文件流信息
        // ByteBuffer fileByteBuffer = null;
      //  request.getMethod()

        //首先请求分为4得嘞
        String contentType = request.getContentType();
        if (request.getMethod().equals("POST") || request.getContentLength() > 297) {
            HttpPostUtil postUtil = new HttpPostUtil(newUrl);

            if(contentType.startsWith("multipart/form-data")) {    //如果有文件上传
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                logger.info("有上传文件");
                Iterator<?> it = multipartRequest.getFileNames();
                while (it.hasNext()) {
                    String filename = it.next().toString();
                    CommonsMultipartFile cmFile = (CommonsMultipartFile) multipartRequest.getFile(filename);
                    postUtil.addFileParameter(filename, cmFile);
                }
            }
            if(contentType.startsWith("application/json")) {// it may be like {a:1,b:2}
                Set<String> keySet = urlQueryParam.keySet();


                String bodyParam = HttpRequestUtil.getRequestPostStr(request);




                result = HttpRequestUtil.sendPostWithCookie(newUrl,bodyParam,contentType,cookieMap );
                //set cookie to response
                HttpRequestUtil.setCookie(response,cookieMap);
            }else{
                Set<String> keySet = urlQueryParam.keySet();
                for (Iterator<String> urlQueryParamIt = keySet.iterator(); urlQueryParamIt.hasNext(); ) {
                    String name = urlQueryParamIt.next();
                    String value = urlQueryParam.get(name);
                    postUtil.addTextParameter(name, value);

                }
                byte[] b = postUtil.send();
                result = new String(b);
            }

            /*
             * while (it.hasNext()) { String filename = it.next().toString(); CommonsMultipartFile cmFile =
             * (CommonsMultipartFile) multipartRequest.getFile(filename); postUtil.addFileParameter(filename, cmFile); }
             */



        } else if(request.getMethod().equalsIgnoreCase("DELETE")) {
            if(contentType.startsWith("application/json")) {// it may be like {a:1,b:2}

            }
            String bodyParam = HttpRequestUtil.getRequestPostStr(request);
            if(StringUtil.isBlank(bodyParam)  && urlQueryParam.size()>0){
                //把urlQueryParam加入到url参数中
               newUrl+= "?"+MapUtils.join(urlQueryParam,"=","&");
            }
            result = HttpRequestUtil.sendXWithCookie(newUrl, bodyParam,contentType,"DELETE" ,cookieMap);

            //set cookie to response
            HttpRequestUtil.setCookie(response,cookieMap);
        } else if(request.getMethod().equalsIgnoreCase("PUT")) {
            String bodyParam = HttpRequestUtil.getRequestPostStr(request);

            result = HttpRequestUtil.sendXWithCookie(newUrl, bodyParam,contentType,"PUT" ,cookieMap);
        }else if(request.getMethod().equalsIgnoreCase("GET")){

            // 发送请求
            // response.sendRedirect(redirectUrl);
           // urlQueryParam.put("access_token","123");
            result = HttpRequestUtil.sendGetWithCookie(newUrl, urlQueryParam,cookieMap);

            HttpRequestUtil.setCookie(response,cookieMap);
        }

        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        logger.info(newUrl + "返回结果" + result);
        response.getWriter().println(result);
        response.getWriter().flush();
        response.getWriter().close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        super.afterCompletion(request, response, handler, ex);
    }

}
