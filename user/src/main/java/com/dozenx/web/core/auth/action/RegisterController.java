package com.dozenx.web.core.auth.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.config.SysConfig;
import com.dozenx.util.MapUtils;
import com.dozenx.util.RandomValidateCode;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.TerminalUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import com.dozenx.web.module.merchant.bean.SessionDTO;
//import com.dozenx.web.third.weixin.WeixinConstants;
//import com.dozenx.web.third.weixin.bean.WeixinUser;

@APIs(description = "注册模块")
@Controller
@RequestMapping(Constants.WEBROOT + "/sys/auth/reg")
public class RegisterController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    ValidCodeService validCodeService;
    @Autowired
    private AuthService authService;
    @Autowired
    private SysMenuService sysMenuService;


    //    @Value("#{configProperties[uploadFieldName]}")
//    private String uploadFieldName;
    @Autowired
    private SysUserService userService;


    /**
     * 说明:转到注册页面
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:55
     */
    @RequestMapping(value = "/htm", method = RequestMethod.GET)
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
    ResultDTO registerPost(HttpServletRequest request) throws Exception {
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
        }
        result = this.userService.saveRegisterUserWithEamilCode(user,request.getParameter("emailCode"));// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {
            HttpSession session = request.getSession();
            user.setPassword("");
            // user.setStatus(1);
            session.setAttribute("user", user);
        }
        return result;

    }



    /**
     * 说明:注册提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/email.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO registerEmail(HttpServletRequest request) throws Exception {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复

        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效
        String email = request.getParameter("email");

        String password = request.getParameter("pwd");
        String captcha = request.getParameter("captcha");
        String emailCode = request.getParameter("emailCode");
        // String smsCaptcha = request.getParameter("smsCaptcha");
        String sessionid = request.getRequestedSessionId();

        ValidateUtil vu = new ValidateUtil();
        String validStr = "";

        vu.add("email", email, "邮箱", new Rule[]{new Required(), new Length(20), new NotEmpty(),new EmailRule()});
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
        user.setAccount(email);

        user.setEmail(email);

        //校验邮箱验证码

//        @Autowired
//        private ActiveService activeService;

        ResultDTO result = validCodeService.remoteValidImg(sessionid, captcha);
        if (!result.isRight()) {
            return result;
        }
            result = this.userService.saveRegisterUserWithEamilCode(user,emailCode);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {
            HttpSession session = request.getSession();
            user.setPassword("");
            // user.setStatus(1);
            session.setAttribute("user", user);
        }
        return result;

    }

    /**
     * 说明:注册提交
     *
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/email/code.json", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultDTO sendEmailCode(HttpServletRequest request) {
       String email =request.getParameter("email");
        return userService.sendEmailCode(email);

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
            HttpSession session = request.getSession();
            user.setPassword("");
            // user.setStatus(1);
            session.setAttribute(Constants.SESSION_USER, user);
        }
        return result;

    }

    @RequestMapping(value = "/requestValidPhoneCode.json", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultDTO requestValidPhoneCode(HttpServletRequest request) {
        String ip = RequestUtil.getIp(request);
        SessionUser user = this.getUser(request);
        if (user == null) {
            return ResultUtil.getResult(30106112, "未登陆");
        }
        if (StringUtil.isBlank(user.getPhone())) {
            return ResultUtil.getResult(30106111, "手机号未填写");
        }
        String phone = user.getPhone();


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
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            return ResultUtil.getResult(300, "未登陆");
        }
        if (StringUtil.isBlank(user.getTelno())) {
            return ResultUtil.getResult(301, "手机号未填写");
        }
        String phone = user.getTelno();
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
        userService.updateStatus(status, user.getId());
        //result = this.userService.saveRegisterUser(user);// .loginValid(loginName,
        // pwd);
        if (result.isRight()) {

            user.setPassword("");
            user.setStatus(status);
            session.setAttribute("user", user);
        }
        return result;
    }

}
