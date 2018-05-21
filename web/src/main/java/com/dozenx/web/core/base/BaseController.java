package com.dozenx.web.core.base;


import com.dozenx.core.exception.InterfaceException;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.service.LogService;

import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
	@ExceptionHandler({ Exception.class })
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
		logger.error("", e);
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
			if (((TypeMismatchException) e).getValue().toString().length() == 0) {
				result = getWrongResultFromCfg("err.param.null");
			}
			result = getWrongResultFromCfg("err.param.type");
		} else if (e instanceof MissingServletRequestParameterException) {
			result = getWrongResultFromCfg("err.err.param.null");
		} else if (e instanceof ParamException) {
			result = new ResultDTO(((ParamException)e).code,((ParamException)e).msg);
		}else if (e instanceof InterfaceException) {
			result = new ResultDTO(Integer.valueOf(((InterfaceException)e).code),((InterfaceException)e).msg);
		}else {
			result = getWrongResultFromCfg(e.getMessage());
		}
		String json = request.getParameter("json");
		//if (RequestUtil.isAjaxRequest(request) || StringUtil.isNotBlank(json)) {
			try {
				String resultStr = JsonUtil.toJsonString(result);
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
	public Long getUserId(HttpServletRequest request){
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
//		SessionUser sessionUser = null;
		if (	 sessionUser != null &&  sessionUser.getUserId()!= null) {
//			sessionUser = seesionDTO.getSessionUser();
			return sessionUser.getUserId();
		}
		return null;
	}

	/* @Author: dozen.zhang
	 * @Description:从sessionUser中获取userId
	 * @Date:17:02 2018/1/2
	 */
	public String getUserName(HttpServletRequest request){
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
//		SessionUser sessionUser = null;
		if (	 sessionUser != null &&  sessionUser.getUserId()!= null) {
//			sessionUser = seesionDTO.getSessionUser();
			return sessionUser.getUserName();
		}
		return null;
	}

	/* @Author: dozen.zhang
	 * @Description:从sessionUser中获取userId
	 * @Date:17:02 2018/1/2
	 */
	public String[] getRoles(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object object = session.getAttribute(Constants.SESSION_ROLES) ;
		return (String[] )session.getAttribute(Constants.SESSION_ROLES) ;

	}
	/**判断是否是管理员**/
	public boolean isAdmin(HttpServletRequest request){
		String[] roles= getRoles(request);
		boolean isAdmin =false;
		if(roles!=null){
			for(int i=0;i<roles.length;i++){
				if(StringUtil.isNotBlank(roles[i]) && roles[i].endsWith("ADMIN")){
					isAdmin=true;
					break;
				}
			}
		}

		return isAdmin;
	}

	/**
	 * @Author: dozen.zhang
	 * @Description: 获取当前用户
	 * @Date: 2018/1/3
	 */
	public SessionUser getUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

		return sessionUser;
	}

	public  void writeJsonStr(HttpServletResponse response,String resultStr ){
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
}
