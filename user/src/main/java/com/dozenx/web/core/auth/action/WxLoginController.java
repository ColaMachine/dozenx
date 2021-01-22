package com.dozenx.web.core.auth.action;

import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.RandomValidateCode;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.OperLogUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.TerminalUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@APIs(description = "普通用户登录模块")
@Controller
@RequestMapping(Constants.WEBROOT + "/wx/auth/")
public class WxLoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(WxLoginController.class);
    @Autowired
    ValidCodeService validCodeService;

    @Autowired
    private AuthService authService;

    public ValidCodeService getValidCodeService() {
        return validCodeService;
    }

    public void setValidCodeService(ValidCodeService validCodeService) {
        this.validCodeService = validCodeService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public SysUserService getUserService() {
        return userService;
    }

    public void setUserService(SysUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private SysUserService userService;



    /**
     * 说明:发送验证码 然后发送短信验证码到用户手机
     * 用户拿到短信验证码就可以直接登录了
     *
     * @param request 请求对象
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
    @RequestMapping(value = "/login/sms/code", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO sendSmsValidCode4Login(HttpServletRequest request) throws Exception {

        String phone = request.getParameter("phone");   //手机号码
        if(StringUtil.isBlank(phone) || !StringUtil.isPhone(phone)){
            throw new ParamException(30505076,"手机号不正确");
        }
        HashMap<String,String> map =new HashMap<>(1);
        map.put("telno",phone);
        int count = userService.countByParams(map);
        if(count==0){
            throw new ParamException(30505082,"该手机号用户不存在");
        }
        String ip = RequestUtil.getIp(request);

        String helloSms = (String) this.getSessionParam(request,"hellosms");
        return validCodeService.getSmsValidCode("calendar", phone, ip);
    }


    /**
     * 说明:登录提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
    @API(summary = "用户登录接口",
            consumes = "application/json",
            description = " 用户登录接口", parameters = {


            @Param(name="body" , description="用户登录json",schema = "{ \"loginName\":\"用户名\", \"pwd\": \"md5两次后的密码\", \"picCaptcha\": \"\"}", in=InType.body,dataType = DataType.STRING,required = true),

//
//            @Param(name = "loginName", description = "用户名",schema = ""
//                    , dataType = DataType.STRING, in = "body", required = true),
//            @Param(name = "pwd", description = "加密后的密码"
//                    , dataType = DataType.STRING, in = "body", required = true),
//            @Param(name = "picCaptcha", description = "验证码"
//                    , dataType = DataType.LONG, in = "body", required = true),

    })
    /**
     * 通过账号密码和图形验证码登录
     */
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/login/pwd", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultDTO loginWithAccountAndPwd(HttpServletRequest request, HttpServletResponse response,@RequestBody(required = true) Map<String, Object> bodyParam) {
        //保存长期登录状态的方案上次看到有一个 newwt的方案 现在又找不到了
        //我的思路是 先按照原先的方式登录 那么 在 后台的session 中会贮存用户对象 然后再在redis中序列化 以sessionkey 为key的用户对象
        //那么这种方案就不能在多机的方案中使用 需要session 同步  然后我们需要再在 getsessionUser方面进行该着 先从requet中去取用户信息
        //如果取不到 就拿sessionid 去redis去取 ,
        //这种就能确保用户的信息长期保存了 这个 前端
        // Map cookies = HttpRequestUtil.ReadCookieMap(request);
        String userAgent = request.getHeader("user-agent");
        try {
            String type = TerminalUtil.getTerminalType(userAgent);
            logger.info("user login os:登录的操作系统为:" + type);//打印用户的操作系统
        }catch(Exception e){
            e.printStackTrace();
        }

        String account = MapUtils.getString(bodyParam, "loginName");//用户名
        String pwd = MapUtils.getString(bodyParam, "pwd");//密码加密后的md5密码
//        String imgCaptcha = MapUtils.getString(bodyParam, "picCaptcha");//图片验证码

        //为了防止用户暴力碰库 验证码验证用户提交信息安全与否
        //如果用OpmsRedirect会出现sessionId是空
//        ResultDTO result = validCodeService.imgValidCode("calendar", this.getSessionParam( request,"uid"), imgCaptcha);
//        if (!result.isRight()) {
//            return result;
//        }
        //

        ResultDTO result = this.userService.loginValidWithEncryptedPwd(account, pwd);
        if (result.isRight()) {


            //延长cookie的时间
            this.resetLoginToken(request,response,this.getSessionId(request));
         //   CookieUtil.setCookie(response,"jsessionid",this.getSessionId(request),SESSION_LIVE_TIME);
            SysUser user = (SysUser) result.getData();
            this.setSessionAttribute(request , Constants.SESSION_USER, user.getSessionUser());//塞入到用户session中

        }
        OperLogUtil.add(request,"登录","用户名密码登录","登录账号:"+account);
        response.addHeader("Authorization",this.getSessionId(request));
        //若果密码输入多次 增加 验证码 和锁定功能
        return result;
    }


    /**
     * 通过手机号码和短信验证码进行登录
     * @param request
     * @param bodyParam
     * @return
     */

    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/login/sms", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultDTO loginWithSmsAndTel(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> bodyParam) {

        // Map cookies = HttpRequestUtil.ReadCookieMap(request);
        String userAgent = request.getHeader("user-agent");
        String type = TerminalUtil.getTerminalType(userAgent);
        logger.info("user login os:登录的操作系统为:" + type);//打印用户的操作系统
        String phone = MapUtils.getString(bodyParam, "phone");//用户名
//        String pwd = MapUtils.getString(bodyParam, "pwd");//密码加密后的md5密码
        String smsCode = MapUtils.getString(bodyParam, "smsCode");//图片验证码

        if(StringUtil.isBlank(phone) || !StringUtil.isPhone(phone)){
            throw new ParamException(30505076,"手机号不正确");
        }
        if(StringUtil.isBlank(smsCode) || smsCode.length()>10){
            throw new ParamException(30505076,"验证码不正确");
        }

        //为了防止用户暴力碰库 验证码验证用户提交信息安全与否
        //如果用OpmsRedirect会出现sessionId是空
        ResultDTO result = validCodeService.smsValidCode("calendar", (String) this.getSessionParam(request,"uid"), smsCode);
        if (!result.isRight()) {
            return result;
        }
        //
        SysUser sysUser = userService.getUserByTelno(phone);
        if(sysUser ==null){
            return this.getResult(30505221,"用户不存在");
        }else{
            this.setSessionAttribute(request , Constants.SESSION_USER, sysUser.getSessionUser());//塞入到用户session中
            response.addHeader("Authorization",this.getSessionId(request));
            return  this.getResult();
        }

    }

    /**
     * 说明:登录提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
    @API(summary = "用户登录接口(无验证码接口)",
            consumes = "application/json",
            description = "用户登录接口", parameters = {
            @Param(name = "bodyparam", description = "验证码",schema = "{\"loginName\":\"15325028686\",\"pwd\":\"e10adc3949ba59abbe56e057f20f883e\"}"
                    , dataType = DataType.LONG, in = "body", required = true),
    })
    /**
     * 通过账号密码登录 无验证码
     */
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/login/no/captcha", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultDTO loginWidthAccountAndPwd(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> bodyParam) {

        // Map cookies = HttpRequestUtil.ReadCookieMap(request);
        String userAgent = request.getHeader("user-agent");
        String type = TerminalUtil.getTerminalType(userAgent);
        logger.info("user login os:登录的操作系统为:" + type);//打印用户的操作系统
        String account = MapUtils.getString(bodyParam, "loginName");//用户名
        String pwd = MapUtils.getString(bodyParam, "pwd");//密码加密后的md5密码
        //  String imgCaptcha = MapUtils.getString(bodyParam,"picCaptcha");//图片验证码

        //为了防止用户暴力碰库 验证码验证用户提交信息安全与否
        //如果用OpmsRedirect会出现sessionId是空
//        ResultDTO  result = validCodeService.imgValidCode("calendar",(String)request.getSession().getAttribute("uid"), imgCaptcha);
//        if (!result.isRight()) {
//            return result;
//        }

        ResultDTO result = this.userService.loginValidWithEncryptedPwd(account, pwd);
        if (result.isRight()) {
            SysUser user = (SysUser) result.getData();
            this.setSessionAttribute(request , Constants.SESSION_USER, user.getSessionUser());//塞入到用户session中
            //获取这个人的所有权限
            //List<SysPermission> permissions = authService.listPermissionByUserid(user.getId());
            List<String> permissions = authService.listPermissionStrByUserid(user.getId());
            List<SysRole> sysRoles = authService.listRolesByUserid(user.getId());
            if (sysRoles != null && sysRoles.size() > 0) {
                String[] roleCodesAry = new String[sysRoles.size()];
                for (int i = 0; i < sysRoles.size(); i++) {
                    if (sysRoles != null) {
                        roleCodesAry[i] = sysRoles.get(i).getRoleCode();

                    }

                }
                this.setSessionAttribute(request , Constants.SESSION_ROLES, roleCodesAry);//塞入到用户session中
            }


            this.setSessionAttribute(request , Constants.SESSION_PERMISSIONS, permissions);//塞入到用户session中
            //   List<SysMenu> menus = authService.listMenusByUserid(user.getId());


            //result.setData(null);//不能把用户信息传给前端 会泄漏信息

            Long userId = this.getUserId(request);
            //根据用户id查找菜单



            List<SysMenu> finalList = new ArrayList<SysMenu>();//最终返回前台的list


        }

        //若果密码输入多次 增加 验证码 和锁定功能
        response.addHeader("Authorization",this.getSessionId(request));
        return result;
    }

    /**
     * 帐号密码登录接口
     * account
     * pwd 两次md5加密密码
     * picCaptcha
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginPost.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO loginPost(HttpServletRequest request) {


        // String value =this.uploadFieldName;
        Map map = RequestUtil.request2Map(request);
        String userAgent = request.getHeader("user-agent");
        String type = TerminalUtil.getTerminalType(userAgent);
        logger.info(type);
        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");
        String smsCaptcha = request.getParameter("smsCaptcha");
        //String sessionid = request.getParameter("sessionid");


//        ResultDTO result = validCodeService.remoteValidSms(email, smsCaptcha);
//        if (!result.isRight()) {
//            // return result;
//        }
        //为了防止用户暴力碰库 验证码验证用户提交信息安全与否
        ResultDTO result = validCodeService.imgValidCode("calendar", request.getRequestedSessionId(), imgCaptcha);
        if (!result.isRight()) {
            return result;
        }
        result = this.userService.loginValid(account, pwd);
        if (result.isRight()) {
            SysUser user = (SysUser) result.getData();
            this.setSessionAttribute(request , Constants.SESSION_USER, user.getSessionUser());//塞入到用户session中
            // List<SysResource> resources = authService.listResourcesByUserid(user.getId());
          /*  List<SysMenu> menus = authService.listMenusByUserid(user.getId());
            List<String> resStr = new ArrayList<String>();

            this.setSessionAttribute(request , "resourceList", resStr);
            this.setSessionAttribute(request , "resourceStr", StringUtil.join(",",resStr.toArray(new String[resStr.size()])));*/
            result.setData(user.getId());//不能把用户信息传给前端 会泄漏信息
        }

        //若果密码输入多次 增加 验证码 和锁定功能
        return result;
    }


//    /**
//     * 说明:登录提交
//     *
//     * @param request
//     * @return
//     * @author dozen.zhang
//     * @date 2015年5月14日上午11:33:39
//     */
//    @RequestMapping(value = "/loginPost2.json", method = RequestMethod.POST)
//    public @ResponseBody
//    ResultDTO loginPost2(HttpServletRequest request) {
//
//
//        // String value =this.uploadFieldName;
//        Map map = RequestUtil.request2Map(request);
//        String userAgent  = request.getHeader("user-agent");
//        String type= TerminalUtil.getTerminalType(userAgent);
//        logger.info(type);
//        String phone = request.getParameter("phone");
//        String pwd = "123456";
//
//        String smsCaptcha = request.getParameter("code");
//        //String sessionid = request.getParameter("sessionid");
//
//ƒ
//        ResultDTO result = validCodeService.smsValidCode("calendar",phone, smsCaptcha);
//        if (!result.isRight()) {
//            return result;
//        }
//        //为了防止用户暴力碰库 验证码验证用户提交信息安全与否
//
//        //  result = this.userService.loginValid(phone, pwd);
//        //if (result.isRight()) {
//        //取得用户 并将openid 更新到用户信息表里 存入到session里
//        SysUser user = userService.getUserByTelno(phone);
//        //如果用户不存在 需要创建用户
//        if(user==null){
//            user =new SysUser() ;
//            user.setUsername(phone);
//            user.setTelno(phone);
//
//        }
//        //需要判断次openid 是否有关联了其他的账号 如果有的话 需要先去掉
//
//        String openid= (String)request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_OPENID);
//        SysUser anotrherAccount = userService.getUserByWxopenid(openid);
//        if(anotrherAccount !=null){
//            anotrherAccount.setWeichat(null);
//            userService.save(anotrherAccount);
//        }
//
//        user.setWeichat((String)request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_OPENID));
//        Object object = request.getSession().getAttribute(WeixinConstants.WEIXIN_SESSION_USER);
//        if(object!=null){
//            WeixinUser weixinUser =(WeixinUser)object;
//            if(weixinUser!=null){
//                user.setFace(weixinUser.getHeadimgurl());
//                user.setAddress(weixinUser.getProvince()+weixinUser.getCity()+weixinUser.getCountry());
//                user.setNkname(weixinUser.getNickname());
//                user.setSex(Integer.valueOf(weixinUser.getSex()));
//                user.setRemark(weixinUser.getUnionid());
//            }
//        }
//        userService.save(user);
//
//
//        this.setSessionAttribute(request , Constants.SESSION_USER, user);//塞入到用户session中
//        // List<SysResource> resources = authService.listResourcesByUserid(user.getId());
//      /*  List<SysMenu> menus = authService.listMenusByUserid(user.getId());
//        List<String> resStr = new ArrayList<String>();
//
//        this.setSessionAttribute(request , "resourceList", resStr);
//        this.setSessionAttribute(request , "resourceStr", StringUtil.join(",",resStr.toArray(new String[resStr.size()])));*/
//        result.setData(null);//不能把用户信息传给前端 会泄漏信息
//        // }
//
//        //若果密码输入多次 增加 验证码 和锁定功能
//        return result;
//    }

    /**
     * 说明:转到注册页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:55
     */
    @RequestMapping(value = "/register.htm", method = RequestMethod.GET)
    public ModelAndView registerGet(HttpServletRequest request, Model model) {
        return new ModelAndView( "registerWithEmail","path", SysConfig.PATH);
    }

    /**
     * 说明:注册提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/registerPost.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO registerPost(HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复

        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");

        // String smsCaptcha = request.getParameter("smsCaptcha");
        String sessionid = request.getRequestedSessionId();

        ValidateUtil vu = new ValidateUtil();
        String validStr = "";

        vu.add("username", username, "用户名", new Rule[]{new Required(), new Length(20), new NotEmpty()});
        vu.add("password", password, "密码", new Rule[]{new Required(), new Length(50), new NotEmpty()});

        try {
            validStr = vu.validateString();
        } catch (Exception e) {

            return ResultUtil.getResult(302, "验证参数错误");
        }
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }


        SysUser user = new SysUser();
        user.setPassword(password);
        user.setAccount(username);
        user.setUsername(username);
        if (StringUtil.isPhone(email)) {
            user.setTelno(email);
        } else if (StringUtil.isEmail(email)) {
            user.setEmail(email);
        } else {
            return ResultUtil.getResult(30106113, "邮箱或手机号输入错误");
        }
        ResultDTO result = validCodeService.remoteValidImg(sessionid, imgCaptcha);
        if (!result.isRight()) {
            return result;
        } //未验证手机或者邮箱
        result = this.userService.addSmsValidUnEncrypedRegisterUser(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {
//            HttpSession session = request.getSession();
            user.setPassword("");
            // user.setStatus(1);
            this.setSessionAttribute(request,"user", user);
        }
        return result;
    }

    /**
     * 说明:注册提交 只有用户名和密码
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/register/uep", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO registerOnlyNamePwd(HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复

        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");

        // String smsCaptcha = request.getParameter("smsCaptcha");
        String sessionid = request.getRequestedSessionId();

        ValidateUtil vu = new ValidateUtil();
        String validStr = "";

        vu.add("username", username, "用户名", new Rule[]{new Required(), new Length(20), new NotEmpty()});
        vu.add("password", password, "密码", new Rule[]{new Required(), new Length(50), new NotEmpty()});
        vu.add("email", email, "邮箱", new Rule[]{new EmailRule(), new Length(50), new NotEmpty()});

        try {
            validStr = vu.validateString();
        } catch (Exception e) {

            return ResultUtil.getResult(302, "验证参数错误");
        }
        if (StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302, validStr);
        }


        SysUser user = new SysUser();
        user.setPassword(password);
        user.setAccount(username);
        user.setUsername(username);

        user.setEmail(email);

        ResultDTO result = validCodeService.remoteValidImg(sessionid, imgCaptcha);
        if (!result.isRight()) {
            return result;
        }
        result = this.userService.saveRegisterUserSimple(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {
//            HttpSession session = request.getSession();
//            user.setPassword("");
            // user.setStatus(1);
            this.setSessionAttribute(request,Constants.SESSION_USER, user);
        }
        return result;

    }

    @RequestMapping(value = "/requestValidPhoneCode.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO requestValidPhoneCode(HttpServletRequest request) {
        String ip = RequestUtil.getIp(request);
        SysUser user = (SysUser) this.getSessionAttribute(request,"user",SysUser.class);
        if (user == null) {
            return ResultUtil.getResult(30106112, "未登陆");
        }
        if (StringUtil.isBlank(user.getTelno())) {
            return ResultUtil.getResult(30106111, "手机号未填写");
        }
        String phone = user.getTelno();


        //status 位置标识 0000 新注册  冻结 邮箱验证  手机验证
        int status = user.getStatus();
        if ((status & 1) == 1) {
            //说明已经激活过了
            return ResultUtil.getResult(30106110, "手机已验证过了");
        }
        ResultDTO result = validCodeService.getSmsValidCode("calendar", phone, ip);
        return result;
    }

    /**
     * 说明:激活邮件回跳页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:35:09
     */
    @RequestMapping(value = "/validEmail.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO activeWithEmail(HttpServletRequest request) {
        String code = request.getParameter("code");
        String email = request.getParameter("email");
        ResultDTO result;

        result = this.userService.activeWithEmail(email, code);

        return result;
    }

    @RequestMapping(value = "/validPhone.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO validPhone(HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复
        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
//        HttpSession session = request.getSession();
        SessionUser user = this.getUser(request);
        if (user == null) {
            return ResultUtil.getResult(300, "未登陆");
        }
        if (StringUtil.isBlank(user.getPhone())) {
            return ResultUtil.getResult(301, "手机号未填写");
        }
        String phone = user.getPhone();
        // String phone = request.getParameter("phone");
        String smsCaptcha = request.getParameter("smsCaptcha");
        if (StringUtil.isBlank(smsCaptcha) || smsCaptcha.length() < 4 || smsCaptcha.length() > 12) {
            return ResultUtil.getResult(301, "请填写正确验证码");
        }
        ResultDTO result = validCodeService.remoteValidSms(phone, smsCaptcha);
        if (!result.isRight()) {
            return result;
        }
        //status 位置标识 0000 新注册  冻结 邮箱验证  手机验证
        int status = user.getStatus();
        if ((status & 1) == 1) {
            //说明已经激活过了
            return ResultUtil.getResult(301, "手机已验证过了");
        }
        status = status | 1;
        userService.updateStatus(status, user.getUserId());
        //result = this.userService.saveRegisterUser(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {

            user.setStatus(status);
           this.setUser(request,user);
        }
        return result;
    }

    /**
     * 说明:等待激活页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:35
     */
    @RequestMapping(value = "/waitActive.htm", method = RequestMethod.GET)
    public String waitActive(HttpServletRequest request) {
        return "/active/waitActive.html";
    }

    /**
     * 说明:激活邮件回跳页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:35:09
     */
    @RequestMapping(value = "/active.htm", method = RequestMethod.GET)
    public String active(HttpServletRequest request) {
        String activeid = request.getParameter("activeid");
        String account = request.getParameter("account");
        ResultDTO result;
        if (StringUtil.isNotBlank(activeid)) {
            result = this.userService.updateUserActive(activeid,account,"register");
        } else {
            request.setAttribute("msg", "激活url无效");
            return "/error.jsp";
        }
        if (result.isRight()) {
            // 把用户信息传入到session 中并让他登录到首页
            SysUser user = (SysUser) result.getData();
            this.setSessionAttribute(request , "user", user);
        } else {
            // 提示激活url无效
            request.setAttribute("msg", result.getMsg());
            return "/error.jsp";
        }
        return "/active/active.jsp";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index3(HttpServletRequest request, Model model) {
        return new ModelAndView( "index","path", SysConfig.PATH);
    }

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET ,produces = "text/html")
    public ModelAndView index(HttpServletRequest request, Model model) {
        return new ModelAndView( "index","path", SysConfig.PATH);
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public ModelAndView index2(HttpServletRequest request, Model model) {
        return new ModelAndView( "index","path", SysConfig.PATH);
    }


    @RequestMapping(value = "/moreCssJs.htm", method = RequestMethod.GET)
    public ModelAndView moreCssJs(HttpServletRequest request, Model model) {
        return new ModelAndView( "moreCssJs","path", SysConfig.PATH);
    }


    @RequestMapping(value = "/forgetpwd.htm", method = RequestMethod.GET)
    public String forgetPwd(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        request.setAttribute("imgname", imgName);
        this.setSessionAttribute(request , "validatecode", code);
        // TODO 需增加回收机制 回收已经生成过的图片
        return "/static/html/zforgetpwd.html";
    }


    @RequestMapping(value = "/validatecode", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO validateCode(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        ResultDTO result = new ResultDTO();
        this.setSessionAttribute(request , "validatecode", code);
        result.setR(1);
        result.setData(imgName);

        return result;
    }

    @RequestMapping(value = "/forgetpwd/save.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO sendPwdRstEmail(HttpServletRequest request) {
        // 生成图片
        // 得到验证码
//        String validatecode = (String) request.getSession().getAttribute("validatecode");
        // 验证验证码
//        String code = request.getParameter("code");
        /*if (!validatecode.equals(code)) {
            return this.getWrongResultFromCfg("validatecode.wrong");
        }*/

        if (StringUtil.isEmail(request.getParameter("phone"))) {
            String email = request.getParameter("phone");
            return userService.saveSendPwdrstEmail(email);
        } else {
            return this.getResult(301, "参数错误");
        }

        // 发送邮件
        // return "/login/pwdreset.jsp";
    }

    /**
     * 说明:从密码重置链接中跳转到系统的密码重置页面
     *
     * @param id
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月20日下午4:24:22
     */
    @RequestMapping(value = "/pwdrst/edit.htm", method = RequestMethod.POST)
    public String editPwdrst(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "/login/pwdreset.jsp";
    }

    @RequestMapping(value = "/pwdrst/save.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO savePwdrst(HttpServletRequest request) {
        String account = request.getParameter("account");
        if (StringUtil.isBlank(account)) {
            return ResultUtil.getResult(30106109, "账号不能为空");
        }
        if (StringUtil.isEmail(account)) {

        } else if (StringUtil.isPhone(account)) {

        } else {
            return ResultUtil.getFailResult("ACCOUNT_FORMAT_ERR");
        }
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");


        ResultDTO result = userService.savePwdrst(account, pwd, code);
        // 发送邮件
        return result;
    }

    @API(summary = "忘记密码密码重置接口",
            consumes = "application/json",
            description = " 忘记密码密码重置接口", parameters = {
            @Param(name="body" , description="用户登录json",schema = "{ \"account\":\"用户名\", \"pwd\": \"未加密的密码\", \"code\": \"短信验证码\"}", in=InType.body,dataType = DataType.STRING,required = true),
    })
    @RequestMapping(value = "/pwdrst", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResultDTO pwdrst(HttpServletRequest request,@RequestBody (required = true) Map<String, Object> bodyParam) {
        String account = MapUtils.getString(bodyParam,"account");
        if (StringUtil.isBlank(account)) {
            return ResultUtil.getResult(30106109, "账号不能为空");
        }
        if (StringUtil.isEmail(account)) {

        } else if (StringUtil.isPhone(account)) {

        } else {
            return ResultUtil.getFailResult("ACCOUNT_FORMAT_ERR");
        }
        String pwd =MapUtils.getString(bodyParam,"pwd");
        String code = MapUtils.getString(bodyParam,"code"); ;


        ResultDTO result = userService.savePwdrst(account, pwd, code);
        // 发送邮件
        return result;
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public ModelAndView ModelAndView(HttpServletRequest request, Model model) {
        this.removeSession(request,Constants.SESSION_USER);
        return new ModelAndView( "login","path",SysConfig.PATH);

    }



    @RequestMapping(value = "/logout.json", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO logoutJson(HttpServletRequest request) {
        this.removeSession(request,Constants.SESSION_USER);
       // request.getSession().removeAttribute(Constants.SESSION_USER);
        return this.getResult();
    }

    @RequestMapping(value = "/logout", method ={ RequestMethod.GET ,RequestMethod.POST})

    public
    @ResponseBody
    ResultDTO logout(HttpServletRequest request) {
        this.removeSession(request,Constants.SESSION_USER);
        return this.getResult();
    }

    @RequestMapping(value = "/user/edit.htm", method = RequestMethod.GET)
    public String userEdit(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/static/html/userEdit.html";
    }
/*
    @RequestMapping(value = "/user/edit.htm", method = RequestMethod.GET)
    public String editUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/editUser";
    }

    @RequestMapping(value = "/user/view.htm", method = RequestMethod.GET)
    public String viewUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/viewUser";
    }*/


//    public static void main(String[] args) {
//        ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\Users\\dozen.zhang\\Documents\\calendar\\src\\main\\resources\\config\\xml\\applicationContext.xml");
//        Object object = ac.getBean("validCodeService");
//        System.out.println(object);
//    }


    /**
     * @Author: dozen.zhang
     * @Description:获取二维验证码图片接口
     * @Date: 2018/2/8
     */
    @API(summary = "获取验证码",
            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = {

    })
    @APIResponse(value = "{\"r\":0,\"data\":base64二维验证码图片}")
    @RequestMapping(value = "/login/pic/captcha", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResultDTO imgCode(HttpServletRequest request, HttpServletResponse response) {
        //request.getSession(true);//强制生成session 以防止 getRequestedSessionId返回为null
        //logger.info("sessionid:" + request.getSession().getId());
        //===设置到网站的根目录下 jsession cookie值 这样访问任何微服务都会带上这个cookie值
        Cookie hit=new Cookie("JSESSIONID",this.getSessionId(request));
        hit.setHttpOnly(true);//如果设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法访问该Cookie
        hit.setMaxAge(60*60);//设置生存期为1小时
//		hit.setDomain("www.zifansky.cn");//子域，在这个子域下才可以访问该Cookie
		hit.setPath("/");//在这个路径下面的页面才可以访问该Cookie
//		hit.setSecure(true);//如果设置了Secure，则只有当使用https协议连接时cookie才可以被页面访问
        response.addCookie(hit);
        UUID uuid = UUID.randomUUID();//每次都生成新的uuid 会导致一个问题
        String uuidStr = uuid.toString();
        this.setSessionAttribute(request , "uid", uuidStr);
        return validCodeService.getImgValidCode("calendar", uuidStr);
    }

    @RequestMapping(value = "/info")
    @ResponseBody
    public ResultDTO info(HttpServletRequest request) {
        SessionUser sysUser = this.getUser(request);
        return this.getResult(sysUser);
    }
}
