package com.dozenx.web.core.filter;

import com.dozenx.common.util.JsonUtil;
import com.dozenx.web.core.cache.service.RedisService;
import com.dozenx.web.util.BeanUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 说明:登录过滤器 判断是否登录和url是否是无需登录就可以访问
 *
 * @author dozen.zhang
 * @date 2015年12月21日下午2:10:30
 */
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    protected FilterConfig filterConfig = null;
    private String redirectURL = null;
    List<String> notCheckURLList = new ArrayList<String>();
    // private String[] notCheckUrlArr;
    private String sessionKey = Constants.SESSION_DTO;

    /**
     * 说明:程序结束时候执行
     *
     * @return boolean
     * @author dozen.zhang
     * @date 2015年12月21日下午2:10:30
     */
    public void destroy() {

        notCheckURLList.clear();
    }

    /*
     * 过滤
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     *
     * @author colamachine
     */
	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 *
	 * @author colamachine
	 */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // StringBuffer strb = new StringBuffer();
        servletRequest.setCharacterEncoding("utf-8");
		/*
		 * BufferedReader in = new BufferedReader(new InputStreamReader(
		 * servletRequest.getInputStream()));
		 */
		/*
		 * while (true) { String line = in.readLine(); if (line == null) {
		 * break; } line = URLDecoder.decode(line, "UTF-8"); strb.append(line);
		 * } System.out.println(strb);
		 */
        // VIP Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        if (path != null && (path.endsWith(".js") && path.endsWith(".css"))) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(path);
		/*
		 * if(StringUtil.isNotEmpty(path)&&!"/".equals(path)&&path!=null &&
		 * !path.endsWith(".json") && !path.endsWith(".htm")){
		 * filterChain.doFilter(request, response); return; }
		 */
        // 过滤器 判断是否登录
//        HttpSession session = request.getSession();
        if (sessionKey == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Object object = session.getAttribute(sessionKey);
        // 如果既不是放行url 也没有登录
//        SessionDTO seesionDTO = (SessionDTO) session.getAttribute(Constants.SESSION_DTO);
//        SessionUser sessionUser = null;
//        if (seesionDTO != null) {
//            sessionUser = seesionDTO.getSessionUser();
//        }
       /// Object sessionDTO =  session.getAttribute(Constants.SESSION_USER) ;
        RedisService redisService =(RedisService) BeanUtil.getBean("redisService");

        byte[] ary= redisService.getByteAry(request.getRequestedSessionId()+"_"+Constants.SESSION_USER);
        if (ary == null/* && sessionUser == null*/
      //  if (session.getAttribute(sessionKey) == null/* && sessionUser == null*/
                && (!checkRequestURIIntNotFilterList(request))) {

            ResultDTO result = ResultUtil.getNotLogging();
            // 如果是json 返回json结果
            if (RequestUtil.isAjaxRequest(request)) {
                try {
                    // String s = gson.toJson(result);
                    response.setHeader("Cache-Control", "no-cache");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/json;charset=UTF-8");
                    response.getWriter().println(JsonUtil.toJsonString(result));
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException e) {
                    logger.error("", e);
                }
                // filterChain.doFilter(request, response);
                // response.sendRedirect("../failure.jsp");
                return;
            } else {
                // String msg = ErrorMessage.getErrorMsg(e.getMessage());
                request.setAttribute("errormsg", result.getMsg());

                response.sendRedirect(request.getContextPath() + redirectURL);
            }
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 说明:检查url 是否在放行列表中
     *
     * @param request
     * @return
     * @return boolean
     * @author dozen.zhang
     * @date 2015年12月21日下午2:10:30
     */
    private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
        String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
        // System.out.println(uri + "\t exists:" +
        // notCheckURLList.contains(uri));
        // return notCheckURLList.contains(uri);
        for (String s : notCheckURLList) {
            if (uri.matches(s))
                return true;
        }
        // System.out.println(uri+" 该链接需要登录后才能访问验证 in controll");
        return false;
    }

    /**
     * 说明:初始化
     *
     * @author dozen.zhang
     * @date 2015年12月21日下午2:10:30
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        redirectURL = filterConfig.getInitParameter("redirectURL");
        sessionKey = filterConfig.getInitParameter("checkSessionKey");

        String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
        if (!StringUtil.isBlank(notCheckURLListStr)) {
            notCheckURLListStr = notCheckURLListStr.replaceAll("[\\r\\n\\s\\t]", "");
        }

        if (notCheckURLListStr != null) {
            StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
            notCheckURLList.clear();
            while (st.hasMoreTokens()) {
                notCheckURLList.add(st.nextToken());
            }
        }

        // notCheckUrlArr=(String[])notCheckURLList.toArray();
    }
    public static void main(String args[]){
        URL url = Filter.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("path:"+url.getPath()+"  name:"+url.getFile());
        //System.out.println();
    }
}
