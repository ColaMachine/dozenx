package com.dozenx.web.core.base;


import com.alibaba.fastjson.JSON;
import com.dozenx.common.exception.MyException;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.service.LogService;
import com.dozenx.web.util.CookieUtil;
import com.dozenx.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.dozenx.common.util.MapUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//import com.dozenx.web.message.ErrorMessage;

public class BaseController extends ResultAction {
    @Autowired
    private LogService logService;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截未传必需参数或者参数类型错误的异常并返回相应错误代码
     *
     * @param e
     * @param request
     * @return
     * @author 宋展辉
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    // @ResponseBody
    public Object exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        // 记录当前用户id
        // 记录当前参数
        // 记录当前

        /*********** log4j的一些信息输出 *********************/
        // String url =request.getRequestURI();

        // String remoteAddr = request.getRemoteAddr();
        // NDC.push("ip:"+remoteAddr);
        // Object obj =
        // request.getSession().getAttribute(DefineUtil.SESSION_LOGIN_USER);
        // if(obj!=null && obj instanceof CrmBussinessEmpolyee){
        // Integer id = ((CrmBussinessEmpolyee)obj).getId();
        // NDC.push("userid:"+id);
        // }
        // Gson gson =new Gson();
        // NDC.push("params:"+gson.toJson(request.getParameterMap()));

        // saveExceptionLog(request,e);
        // handleReturn();
        // redirectResponse();
        // NDC.pop();
        // NDC.pop();
        // NDC.pop();
        // NDC.remove();
        /*********** log4j结束 *********/

        ResultDTO result;

		/*
         * if (e instanceof org.apache.shiro.authz.AuthorizationException || e
		 * instanceof org.apache.shiro.authz.UnauthorizedException) { result =
		 * getWrongResultFromCfg("err.logic.no.auth"); }else
		 */
        if (e instanceof TypeMismatchException) {
            logger.error("", e);
            if (((TypeMismatchException) e).getValue().toString().length() == 0) {
                result = getWrongResultFromCfg("err.param.null");
            }
            result = getWrongResultFromCfg("err.param.type");
        } else if (e instanceof MissingServletRequestParameterException) {
            logger.error("", e);
            result = getWrongResultFromCfg("err.err.param.null");
        } else if (e instanceof MyException) {
            if (StringUtil.checkNumeric(((MyException) e).code)) {
                result = new ResultDTO(Integer.valueOf(((MyException) e).code), ((MyException) e).msg);
            } else {
                result = new ResultDTO(0405301, ((MyException) e).code + ":" + ((MyException) e).msg);
            }

        }/*else if (e instanceof InterfaceException) {
            result = new ResultDTO(Integer.valueOf(((InterfaceException)e).code),((InterfaceException)e).msg);
		}*/ else {
            logger.error("", e);
            result = getWrongResultFromCfg(e.getMessage());
        }
        String json = request.getParameter("json");
        //if (RequestUtil.isAjaxRequest(request) || StringUtil.isNotBlank(json)) {
        try {
            String resultStr = JsonUtil.toJsonString(result);
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");

            response.setContentType("application/json;charset=UTF-8");
            //	response.setContentType("text/json;charset=UTF-8");

            response.getWriter().println(resultStr);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            logger.error("return the exception through the jso to clien fail", e1);
        }

        return null;
//		} else {
//			// String msg = ErrorMessage.getErrorMsg(e.getMessage());
//			request.setAttribute("errormsg", result.getMsg());
//			return new ModelAndView("test");
//		}
    }

    public void saveExceptionLog(HttpServletRequest request, Exception e) {
        // StackTraceElement[] s = Thread.currentThread().getStackTrace();
        // 获取service
        /*
		 * AppExceptionLog log =new AppExceptionLog(); ByteArrayOutputStream
		 * baos = new ByteArrayOutputStream(); e.printStackTrace(new
		 * PrintStream(baos)); String exception = baos.toString();
		 * log.setMsg(exception); // log.setClassName(e.getCause().toString());
		 * log.setClassName(e.getStackTrace()[0].getClassName());
		 * log.setTime(new Date());
		 * log.setLineNumber(e.getStackTrace()[0].getLineNumber()); Gson
		 * gson=new Gson(); String sid = request.getParameter("sid");
		 * if(StringUtils.isBlank(sid)) sid="0";
		 * log.setSid(Integer.valueOf(sid));
		 * log.setParams(gson.toJson(request.getParameterMap()));
		 * log.setRequesturl(request.getRequestURI());
		 */
        this.logService.recordException(request, e);

        // 获取异常抛出的controller
        // 获取异常抛出的service
        // 获取request 参数
        // 关联requesturl
        // 保存异常
        // 保存简短异常
    }


    /* @Author: dozen.zhang
     * @Description:从sessionUser中获取userId
     * @Date:17:02 2018/1/2
     */
    public Long getUserId(HttpServletRequest request) {



//		SessionUser sessionUser = null;
        if (request.getParameter("userId") != null) {
            return Long.valueOf(request.getParameter("userId"));
        }

        SessionUser sessionUser =this.getUser(request);
        if (sessionUser != null && sessionUser.getUserId() != null) {
//			sessionUser = seesionDTO.getSessionUser();
            return sessionUser.getUserId();
        }
        return null;
    }

    /* @Author: dozen.zhang
     * @Description:从sessionUser中获取userId
     * @Date:17:02 2018/1/2
     */
    public String getUserName(HttpServletRequest request) {




//		SessionUser sessionUser = null;
        if (request.getParameter("userName") != null) {
            return String.valueOf(request.getParameter("userName"));
        }

        SessionUser sessionUser =this.getUser(request);
        if (sessionUser != null && sessionUser.getUserId() != null) {
//			sessionUser = seesionDTO.getSessionUser();
            return sessionUser.getUserName();
        }
        return null;

    }

    /* @Author: dozen.zhang
     * @Description:从sessionUser中获取userId
     * @Date:17:02 2018/1/2
     */
    public String[] getRoles(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute(Constants.SESSION_ROLES);
        return (String[]) session.getAttribute(Constants.SESSION_ROLES);

    }

    /**
     * 判断是否是管理员
     **/
    public boolean isAdmin(HttpServletRequest request) {
        String[] roles = getRoles(request);
        boolean isAdmin = false;
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                if (StringUtil.isNotBlank(roles[i]) && roles[i].endsWith("ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
        }

        return isAdmin;
    }
    public void setUser(HttpServletRequest reuest){

    }
    /**
     * @Author: dozen.zhang
     * @Description: 获取当前用户
     * @Date: 2018/1/3
     */
    public SessionUser getUser(HttpServletRequest request) {

        return this.getSessionAttribute(request,Constants.SESSION_USER,SessionUser.class);
//        HttpSession session = request.getSession();
//        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
//        if(sessionUser == null){
//            String loginToken = getLoginToken(request);
//            if(loginToken==null){
//                return null;
//            }else{
//
//                String userStr= RedisUtil.get("logintoken"+loginToken);
//                if(userStr ==null ){
//                    return null;
//                }else{
//
//                    sessionUser= JsonUtil.toJavaBean(userStr,SessionUser.class);
//                }
//            }
//        }
//
//        return sessionUser;
    }

    public void writeJsonStr(HttpServletResponse response, String resultStr) {
        try {
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentType("text/json;charset=UTF-8");

            response.getWriter().println(resultStr);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            logger.error("return the exception through the jso to clien fail", e1);
        }
    }

    //统一的sesion设置
//    public void putSession(HttpServletRequest request, String key, Object object) {
//        request.getSession().setAttribute(key, object);
//    }
//
//    //统一的sesion获取
//    public void getSession(HttpServletRequest request, String key, Object object) {
//        request.getSession().getAttribute(key);
//    }

    public int SESSION_LIVE_TIME = 24 * 7 * 60 * 60;


    public void generateCookieSessionKey(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = CookieUtil.getCookieByName(request, "dpksk");//从cookie 中找dpksk字段  每次登录成功都会下发这个字段 标识记住这个用户了
        if (cookie == null) {

            String uuid = UUIDUtil.getUUID();
            CookieUtil.setCookie(response,"dpksk",uuid,SESSION_LIVE_TIME);
        }

    }
    /**
     * 通过cookie中的token来活取值 如果没有token 就默认分配一个
     * @param request
     * @return
     */
    public String getSessionId(HttpServletRequest request/*,HttpServletResponse response*/ ) {
        return request.getSession(true).getId();
    //    Cookie cookie = CookieUtil.getCookieByName(request, "dpksk");//从cookie 中找dpksk字段  每次登录成功都会下发这个字段 标识记住这个用户了
       // if (cookie == null) {

//            String uuid = UUIDUtil.getUUID();
//            CookieUtil.setCookie(response,"dpksk",uuid,SESSION_LIVE_TIME);
         //   return null;
        //}
       // String sessionKey = cookie.getValue();
//        if(StringUtil.isBlank(sessionKey)){
//            return null;
//        }
      //  return sessionKey;
    }
    public void resetLoginToken(HttpServletRequest request, HttpServletResponse response,String token){
        CookieUtil.setCookie(response, "JSESSIONID",getSessionId(request),SESSION_LIVE_TIME);//从cookie 中找dpksk字段  每次登录成功都会下发这个字段 标识记住这个用户了

    }
    public String getLoginToken(HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, "dpksk");//从cookie 中找dpksk字段  每次登录成功都会下发这个字段 标识记住这个用户了
         if (cookie == null) {

//            String uuid = UUIDUtil.getUUID();
//            CookieUtil.setCookie(response,"dpksk",uuid,SESSION_LIVE_TIME);
           return null;
        }
         String loginToken = cookie.getValue();
        if(StringUtil.isBlank(loginToken)){
            return null;
        }
          return loginToken;
    }

    public static void main(String args[]){
        List list = JSON.parseObject("['1','2']",List.class);
        System.out.println(list.size());
    }

    public <T> T getSessionAttribute(HttpServletRequest request, String key, Class<T> clazz) {
        //先从session中直接去取
        Object object = request.getSession().getAttribute(key);
        if(object!=null)
            return (T)object;
        //没有的话拿到这个人的token


        String sessionKey = getSessionId(request);
        if (StringUtil.isBlank(sessionKey)) {

            return null;    //如果token也是空的那么肯定没有登录过
        }
        String result = RedisUtil.get(sessionKey + "_" + key);  //根据token去redis中去取
        if (StringUtil.isBlank(result)) {   //如果是空的就返回null
            return null;
        }

        object =  JsonUtil.toJavaBean(result, clazz);

        request.getSession().setAttribute(key,object);
        return (T)object;

    }

    public String getSessionParam(HttpServletRequest request, String key) {

        String s = (String)request.getSession().getAttribute(key);
        if(s!=null)
            return s;
        //没有的话拿到这个人的token


        String sessionKey = getSessionId(request);
        if (StringUtil.isBlank(sessionKey)) {

            return null;    //如果token也是空的那么肯定没有登录过
        }
        String result = RedisUtil.get(sessionKey + "_" + key);  //根据token去redis中去取
        if (StringUtil.isBlank(result)) {   //如果是空的就返回null
            return null;
        }


        return result;

    }
//
    public void setSessionParam(HttpServletRequest request, String key, String value) {

        String sessionKey = getSessionId(request);
        if(StringUtil.isBlank(value)){  //如果是空的 就删除对应属性
            request.getSession().removeAttribute(key);
            RedisUtil.del(sessionKey + "_" + key);
            return;
        }
        request.getSession().setAttribute(key,value);

        if (value == null) {
            return;
        }
        RedisUtil.setex(sessionKey + "_" + key, key, SESSION_LIVE_TIME);


    }

    public void setSessionAttribute(HttpServletRequest request, String key, Object object) {
        //先往session里面存放 再在redis中存放
        request.getSession().setAttribute(key,object);
        String sessionKey = getSessionId(request);
        if (object == null) {

            return;
        }
        RedisUtil.setex(sessionKey + "_" + key, JsonUtil.toJsonString(object), SESSION_LIVE_TIME);
    }

    public void removeSession(HttpServletRequest request, String key) {
        String sessionKey = getSessionId(request);
        request.getSession().removeAttribute(key);
        RedisUtil.del(sessionKey + "_" + key);
    }
}
