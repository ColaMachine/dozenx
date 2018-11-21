/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年9月11日
 * 文件说明: 
 */
package com.dozenx.web.third.weixin.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.encrypt.EncryptUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.third.weixin.WeixinConstants;
import com.dozenx.web.third.weixin.WeixinUtil;
import com.dozenx.web.third.weixin.bean.WeixinOauthAccessTokenResult;
import com.dozenx.web.third.weixin.bean.WeixinUser;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.TerminalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@APIs(description = "微信集合类")
@Controller
@Scope("prototype")
@RequestMapping(Constants.WEBROOT+"/weixin")
public class WeixinController extends BaseController {
	@Autowired
	private SysUserService userService;
	@Autowired
	ValidCodeService validCodeService;
	@Autowired
	private AuthService authService;

	@Resource(name="sysUserService")
	SysUserService sysUserService;


	@API(summary = "跳转qq分享页的",consumes="application/x-www-form-urlencoded",
			description = "跳转qq分享页的" ,
			parameters = {
					@Param(name = "url", description = "当前网页url", dataType = DataType.STRING, required = true),
			})
	@RequestMapping(value = "/qq", method = RequestMethod.GET)
	public String  qq(HttpServletRequest request){
		String access_tocken ="I8DfWtWrTMlMqEnkS-0eobGLzkyPYxf4JV0LqqY1N0tVbQB8jKWLZUG-aOREFQ6DOxyUkLjki7gH9F2A_dhBUJS30-N-n1K_Tadfjq54Q7w";
		String ticket ="kgt8ON7yVITDhtdwci0qecxIYYsD2jPTf9FbI1SBbQXn0tvmcEnn9454E8lcSNRHgkp-G-NEdbZyULcFOaPOrQ";
		request.setAttribute("ticket", ticket);
		String timestamp = System.currentTimeMillis()/1000+"";//(new Date().getTime()+"");//.substring(0,10);
		
		String appid ="wxcfc589b7bf5662e3";
		String appsecret="e12106e8a1f70c82b9cb7ff05d3341c4";
		
		request.setAttribute("timestamp", timestamp);
		
		request.setAttribute("appid", appid);
		
		String nonceStr = StringUtil.getRandomAlphaString(16);
		
		request.setAttribute("nonceStr", nonceStr);
		
		System.out.println("nonceStr:"+nonceStr);
		System.out.println("timestamp:"+timestamp);
		String url ="http://60.191.109.203:8181/share/qq";
		 url ="http://192.168.0.100:8080/calendar/share/qq";
		String originStr= "jsapi_ticket="+ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
		System.out.println("originStr:"+originStr);  
		String signature ="";//SHA1.getDigestOfString(originStr.getBytes()).toLowerCase();
		System.out.println("signature:"+signature);
		request.setAttribute("signature", signature);
		return "/share.jsp";
	}

	@API(summary = "获得微信的signatrue",consumes="application/x-www-form-urlencoded",
			description = "调用微信的jssdk之前需要获得微信的signature,1拿取传入的url 生成url 和随机字符串 " +
					"2获取微信accessToken(系统缓存7200)" +
					"3获取jstiket(系统换船7200)" +
					"4noncestr timestamp url jsapi_ticket 获取signature 票拿到签名" +
					"5返回所有结果" ,
			parameters = {
			@Param(name = "url", description = "当前网页url", dataType = DataType.STRING, required = true),
	})
	@APIResponse(value = "{r:0,msg:'',data:{" +
			"jsapi_ticket:'通往微信的船票通过accessToken拿到'," +
			"appId:'微信appid'," +
			"signature:'校验串'," +
			"timestamp:'时间戳'," +
			"noncestr:'随机串'" +
			"}")
	@RequestMapping(value="signatrue", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultDTO getSignature(HttpServletRequest request)throws  Exception{
		String weburl = request.getParameter("url");
		logger.info("weburl"+weburl);

		//String jsapiTicket = WeixinUtil.getJsapiTicket();
		Long timestamp = System.currentTimeMillis()/1000;
		int noncestr = new Random().nextInt();//随机数

		//WeixinAccessToken accessTokenUtil = new WeixinAccessToken();
		//String accessToken = WeixinUtil.getAccessToken();

		//System.out.println("accessToken:" + accessToken);
		//获取jsapi_ticket
		String jsapi_ticket = WeixinUtil.getJsapiTicket();

		//生成signature
		List<String> nameList = new ArrayList<String>();
		nameList.add("noncestr");
		nameList.add("timestamp");
		nameList.add("url");
		nameList.add("jsapi_ticket");
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("noncestr", noncestr);
		valueMap.put("timestamp", timestamp);
		valueMap.put("url", weburl);
		valueMap.put("jsapi_ticket", jsapi_ticket);
		Collections.sort(nameList);
		String origin = "";
		for (int i = 0; i < nameList.size(); i++) {
			origin += nameList.get(i) + "=" + valueMap.get(nameList.get(i)).toString() + "&";
		}
		origin = origin.substring(0, origin.length() - 1);
		String signature = EncryptUtil.encryptBySHA1(origin);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("appId", ConfigUtil.getConfig(WeixinConstants.WEIXIN_APPID));
		map.put("signature", signature.toLowerCase());
		map.put("timestamp", timestamp.toString());
		map.put("noncestr", String.valueOf(noncestr));
		logger.info("weixin getSignature value map :"+ JsonUtil.toJsonString(valueMap));
		logger.info("weixin getSignature result:"+ JsonUtil.toJsonString(map));
		return ResultUtil.getDataResult(map);

	}


	/** 
	 * @Author: dozen.zhang 
	 * @Description: 获取微信的oauthtoken 详解 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	 * @Date: 2018/1/21
	 */
	@APIResponse(value = "{r:0,msg:'',data:{" +
			"jsapi_ticket:'通往微信的船票通过accessToken拿到'," +
			"appId:'微信appid'," +
			"signature:'校验串'," +
			"timestamp:'时间戳'," +
			"noncestr:'随机串'" +
			"}")
	@RequestMapping(value="oauth/token", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultDTO getOauthAccessTokenByCode(HttpServletRequest request)throws  Exception{
		String code = request.getParameter("code");
		logger.info("code"+code);


		//===获取code后，请求以下链接获取access_token 和openid
		// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		//


		WeixinOauthAccessTokenResult oauthResult = WeixinUtil.getOauthAccessToken(code);

		logger.debug("accessToken:" + oauthResult.getAccess_token());
		logger.debug("openid:" + oauthResult.getOpenid());

		//=============去查我们的数据库里用没有这个openid 如果有的话直接做seession==================


		//去拿userInfo
		HashMap<String,String> paras = new HashMap();
		paras.put("weichat",oauthResult.getOpenid());
		List<SysUser>  sysUsers = sysUserService.listByParams(paras);
		//==如果没有的话 先缓存这个openid 让用户去完成注册
		if(sysUsers==null || sysUsers.size()==0){

			//插入一条心的数据 让用户重新输入手机号码

			request.getSession().setAttribute(WeixinConstants.WEIXIN_SESSION_OPENID,oauthResult.getOpenid());

			WeixinUser weixinUser = WeixinUtil.getUserInfo(oauthResult.getAccess_token(),oauthResult.getOpenid());

			request.getSession().setAttribute(WeixinConstants.WEIXIN_SESSION_USER,weixinUser);
			return this.getResult(29905568, ErrorMessage.getErrorMsg("err.weixin.openid.no.user"));
		}else{
			//直接拿到用户直接登录
			request.getSession().setAttribute(Constants.SESSION_USER,sysUsers.get(0));
			//并将用户信息同步到用户表中

//
//			SysUser sysUser = sysUsers.get(0);
//			sysUser.setNkname(weixinUser.getNickname());
//			sysUser.setFace(weixinUser.getHeadimgurl());
//			sysUser.setWeichat(weixinUser.getOpenid());
//			sysUser.setSex(Integer.valueOf(weixinUser.getSex()));
//			sysUser.setAddress(weixinUser.getProvince()+weixinUser.getCity()+weixinUser.getCountry());
//			sysUserService.save(sysUser);
			return this.getResult();
		}


	}
	/**
	 * token 校验接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "token", method = RequestMethod.GET)
	//@ResponseBody
	public String token(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String echostr = request.getParameter("echostr");
		logger.info("echostr"+echostr);

		PrintWriter pw =null;

		try{

			pw = response.getWriter();

		}catch(IOException e) {

			e.printStackTrace();

		}
		if(StringUtil.isBlank(echostr)){
			echostr ="1352059704919408057";
		}
		pw.append(echostr);

		pw.flush();


		return null;

	}

	/**
	 * 说明:微信里登录提交 通过短信验证码 和手机号码进行登录 没有密码
	 *
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:33:39
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	ResultDTO login(HttpServletRequest request) throws Exception {


		// String value =this.uploadFieldName;
		Map map = RequestUtil.request2Map(request);
		String userAgent  = request.getHeader("user-agent");
		String type= TerminalUtil.getTerminalType(userAgent);
		logger.info(type);
		String phone = request.getParameter("phone");
		String pwd = "123456";

		String smsCaptcha = request.getParameter("code");
		//String sessionid = request.getParameter("sessionid");


		ResultDTO result = validCodeService.smsValidCode("calendar",phone, smsCaptcha);
		if (!result.isRight()) {
			return result;
		}
		//为了防止用户暴力碰库 验证码验证用户提交信息安全与否

		//  result = this.userService.loginValid(phone, pwd);
		//if (result.isRight()) {
		//取得用户 并将openid 更新到用户信息表里 存入到session里
		SysUser user = userService.getUserByTelno(phone);
		//如果用户不存在 需要创建用户
		if(user==null){
			user =new SysUser() ;
			user.setUsername(phone);
			user.setTelno(phone);

		}
		//需要判断次openid 是否有关联了其他的账号 如果有的话 需要先去掉

		String openid= (String)request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_OPENID);
		SysUser anotrherAccount = userService.getUserByWxopenid(openid);
		if(anotrherAccount !=null){
			anotrherAccount.setWeichat(null);
			userService.save(anotrherAccount);
		}

		user.setWeichat((String)request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_OPENID));
		Object object = request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_USER);
		if(object!=null){
			WeixinUser weixinUser =(WeixinUser)object;
			if(weixinUser!=null){
				user.setFace(weixinUser.getHeadimgurl());
				user.setAddress(weixinUser.getProvince()+weixinUser.getCity()+weixinUser.getCountry());
				user.setNkname(weixinUser.getNickname());
				user.setSex(Integer.valueOf(weixinUser.getSex()));
				user.setRemark(weixinUser.getUnionid());
			}
		}
		userService.save(user);


		request.getSession().setAttribute(Constants.SESSION_USER, user);//塞入到用户session中
		// List<SysResource> resources = authService.listResourcesByUserid(user.getId());
      /*  List<SysMenu> menus = authService.listMenusByUserid(user.getId());
        List<String> resStr = new ArrayList<String>();

        request.getSession().setAttribute("resourceList", resStr);
        request.getSession().setAttribute("resourceStr", StringUtil.join(",",resStr.toArray(new String[resStr.size()])));*/
		result.setData(null);//不能把用户信息传给前端 会泄漏信息
		// }

		//若果密码输入多次 增加 验证码 和锁定功能
		return result;
	}
}