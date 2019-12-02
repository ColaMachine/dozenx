package com.dozenx.web.core.auth.action;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.StringUtil;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.module.storage.bean.LitemallStorage;
import com.dozenx.web.module.storage.service.StorageService;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

//import com.dozenx.web.module.merchant.bean.SessionDTO;
//import com.dozenx.web.module.merchant.bean.SessionUser;
//import com.dozenx.web.third.dbcenter.bean.PubUser;
//import com.dozenx.web.third.dbcenter.service.UserBaseService;

/**
 * Created by dozen.zhang on 2017/3/9.
 */


@APIs(description = "用户模块")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    //    @Resource
//    private UserBaseService userBaseService;
    @Resource
    SysUserService userService;

    @Resource
    AuthService authService;

    @RequestMapping(value = "/info.json")
    @ResponseBody
    @RequiresLogin
    public ResultDTO info(HttpServletRequest request) {
        // SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        SessionUser sysUser = this.getUser(request);
        /*sessionUser.setNick("nick");
        sessionUser.setAddress("address");
        sessionUser.setBirthday(new Date().getTime());
        sessionUser.setSex(1);
        sessionUser.setFace("/images/icon/face.png");*/

        return this.getResult(sysUser);
    }


    @RequestMapping(value = "/info")
    @ResponseBody
    public ResultDTO info1(HttpServletRequest request) {
        SessionUser sysUser = this.getUser(request);
        return this.getResult(sysUser);
    }

    /**
     * 用户个人资料修改
     *
     * @param face
     * @param nick
     * @param sex
     * @param birthday
     * @param address
     * @return
     * @author 宋展辉 2015年12月17日 上午9:28:41
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserInfo(@RequestParam("face") String face, @RequestParam("nick") String nick,
                                 @RequestParam("sex") String sex, @RequestParam("birthday") String birthday, String email,
                                 @RequestParam("address") String address, HttpServletRequest request) {
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("face", face, "头像", new Rule[]{new Length(40), new NotEmpty()});
            vu.add("nick", nick, "用户名", new Rule[]{new Length(20), new NotEmpty()});

            vu.add("sex", sex, "类型", new Rule[]{new NumberRange("0", "2"), new NotEmpty()});
            // vu.add("birthday", birthday, "生日",  new Rule[]{new DateValue("yyyy-MM-dd"),new NotEmpty()});
            vu.add("email", email, "邮箱地址", new Rule[]{new Length(50), new EmailRule()});
            //vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new PhoneRule()});
            //vu.add("idcard", idcard, "身份证号码",  new Rule[]{new Length(18)});
            //vu.add("sex", sex, "性别",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"0","1","2"})});
            vu.add("birthday", birthday, "出生年月", new Rule[]{new DateValue("yyyy-MM-dd")});
            //vu.add("integral", integral, "积分",  new Rule[]{new Digits(11,0)});
            vu.add("address", address, "地址", new Rule[]{new Length(50)});
            // vu.add("weichat", weichat, "微信",  new Rule[]{new Length(20)});
            //vu.add("qq", qq, "qq", new Rule[]{new Digits(11, 0)});
            //vu.add("face", face, "头像",  new Rule[]{new Length(100)});
            //vu.add("remark", remark, "备注",  new Rule[]{new Length(200)});
            // vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
            // vu.add("updatetime", updatetime, "更新时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }
        // sessionUser.setSex(Integer.valueOf(sex));
        //sessionUser.setAddress(address);
        sessionUser.setNick(nick);
        // sessionUser.setBirthday(DateUtil.parseToDate(birthday, "yyyy-MM-dd").getTime());//.str2Date(birthday, "yyyy-MM-dd"));
        sessionUser.setFace(face);
        try {
            SysUser sysUser = userService.selectByPrimaryKey(sessionUser.getUserId());
            if (sysUser != null) {
                sysUser.setFace(face);
                //  sysUser.setBirth(new Date(sessionUser.getBirthday()));
                sysUser.setTelno(sessionUser.getPhone());
                sysUser.setNkname(nick);
                sysUser.setAddress(address);
                sysUser.setId(sessionUser.getUserId());
                userService.save(sysUser);
            }
            //  request.getSession().setAttribute(Constants.SESSION_DTO,sessionDTO);
            // sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
            ///更新其他系统数据
//           PubUser user =new PubUser();
//            user.setSex(String.valueOf(sex));
//            user.setId(sessionUser.getId());
//            user.setAddress(address);
//            user.setUserNick(nick);
//            user.setBirthday(DateUtil.parseToDate(birthday, "yyyy-MM-dd"));
//            user.setFaceInfo(face);
//            String userstr = JsonUtil.toJsonString(user);
//            userBaseService.update(user);
            // 更新session中的用户信息

            return getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getWrongResultFromCfg("err.db");
        }
    }


    /**
     * 用户个人资料修改
     *
     * @param face
     * @param nick
     * @param sex
     * @param birth
     * @param address
     * @return
     * @author 宋展辉 2015年12月17日 上午9:28:41
     */
    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserFace(HttpServletRequest request) {

        String face = request.getParameter("face");
        String nick = request.getParameter("nick");
        String sex = request.getParameter("sex");
        String birth = request.getParameter("birth");
        String address = request.getParameter("address");
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("face", face, "头像", new Rule[]{new Length(40), new NotEmpty()});
            vu.add("nick", nick, "用户名", new Rule[]{new Length(20)});
            vu.add("sex", sex, "类型", new Rule[]{new NumberRange("0", "2")});
            vu.add("birthday", birth, "出生年月", new Rule[]{new DateValue("yyyy-MM-dd")});
            vu.add("address", address, "地址", new Rule[]{new Length(50)});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }
        sessionUser.setSex(Integer.valueOf(sex));
        sessionUser.setAddress(address);
        sessionUser.setNick(nick);
        sessionUser.setBirth(birth);//.str2Date(birthday, "yyyy-MM-dd"));
        sessionUser.setFace(face);
        try {
            SysUser sysUser = userService.selectByPrimaryKey(sessionUser.getUserId());
            ResultDTO result = null;
            if (sysUser != null) {
                sysUser.setFace(face);
                if (StringUtil.isNotBlank(sessionUser.getBirth())) {
                    sysUser.setBirth(DateUtil.parseToDate(sessionUser.getBirth(), "yyyy-MM-dd"));
                }

                sysUser.setTelno(sessionUser.getPhone());
                sysUser.setNkname(nick);
                sysUser.setAddress(address);
                sysUser.setId(sessionUser.getUserId());
                result = userService.save(sysUser);
                if (!result.isRight())
                    return result;
                //异步任务 推算出 个人的 人脸特征矩阵 并录入到数据库中
                if (StringUtil.isNotBlank(ConfigUtil.getConfig("updated.user.face"))) {
                    String resultStr = HttpRequestUtil.sendGet(ConfigUtil.getConfig("updated.user.face") + "?userId=" + sessionUser.getUserId());

                }


            }
            return getResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return getWrongResultFromCfg("err.db");
        }


    }

    /**
     * 这个方法和logincontroller的重复了
     * 说明:登录的时候获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
//    @RequestMapping(value = "/loginsms/request.json", method = RequestMethod.POST)
//    public @ResponseBody
//    ResultDTO sendSmsValidCode4Login(HttpServletRequest request) throws Exception {
//        String ip =  RequestUtil.getIp(request);
//        String phone =request.getParameter("phone");
//        return authService.sendSmsValidCode4LoginDirectRegister(ip,phone,(String)request.getSession().getAttribute("hellosms"));
//    }


    /**
     * 用户个人资料修改
     *
     * @param face
     * @param nick
     * @param sex
     * @param birth
     * @param address
     * @return
     * @author 宋展辉 2015年12月17日 上午9:28:41
     */


    @API(summary = "头像更新接口",
            description = "头像更新接口",
            parameters = {

                    @Param(name = "file", description = "图片", in = InType.form, dataType = DataType.FILE, required = true),// false
            })
    @RequestMapping(value = "avatar/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresLogin
    @ResponseBody
    public Object updateMineFace(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file) {
        //String avatar = MapUtils.getString(bodyParam,"avatar");

        if (file == null) {
            throw new BizException(30105289, "上传图片不能为空");
        }
        SessionUser sessionUser = this.getUser(request);
        String url = "";
        try {
            LitemallStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(),
                    file.getContentType(), file.getOriginalFilename());
            url = litemallStorage.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SysUser sysUser = userService.selectByPrimaryKey(sessionUser.getUserId());
            ResultDTO result = null;
            if (sysUser != null) {
                sysUser.setFace(url);
                if (StringUtil.isNotBlank(sessionUser.getBirth())) {
                    sysUser.setBirth(DateUtil.parseToDate(sessionUser.getBirth(), "yyyy-MM-dd"));
                }


                result = userService.save(sysUser);
                if (!result.isRight())
                    return result;
                //异步任务 推算出 个人的 人脸特征矩阵 并录入到数据库中
                if (StringUtil.isNotBlank(ConfigUtil.getConfig("updated.user.face"))) {
                    String resultStr = HttpRequestUtil.sendGet(ConfigUtil.getConfig("updated.user.face") + "?userId=" + sessionUser.getUserId());

                }

                //更新session值
                sessionUser.setFace(url);
                this.setSessionAttribute(request, Constants.SESSION_USER, sessionUser);
            }
            return getDataResult(url);
        } catch (Exception e) {
            e.printStackTrace();
            return getWrongResultFromCfg("err.db");
        }


    }

    /**
     * 用户个人资料修改
     *
     * @param face
     * @param nick
     * @param sex
     * @param birth
     * @param address
     * @return
     * @author 宋展辉 2015年12月17日 上午9:28:41
     */


    @API(summary = "密码更新",
            description = "密码更新",
            parameters = {
                    @Param(name = "pwd", description = "密码", in = InType.body, schema = "{'oldpwd':'旧密码','newpwd':'新密码'}", dataType = DataType.STRING, required = true),// false
            })

    @RequestMapping(value = "/pwd/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO pwdUpdate(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) {

        String oldpwd = MapUtils.getString(bodyParam, "oldpwd");
        String newpwd = MapUtils.getString(bodyParam, "newpwd");

        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
//        if(!sessionUser.getPwd().equals(oldpwd)){
//            throw new BizException(30105353,"旧密码不对");
//        }
        ResultDTO resultDTO = userService.updateUserPwd(sessionUser.getUserId(), oldpwd, newpwd);
        return resultDTO;
    }


    @API(summary = "昵称更新",
            description = "昵称更新",
            parameters = {
                    @Param(name = "nick", description = "昵称", in = InType.body, schema = "{'nick':'昵称'}", dataType = DataType.STRING, required = true),// false
            })

    @RequestMapping(value = "/nick/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO nickupdate(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {

        String nickName = MapUtils.getString(bodyParam, "nickName");

        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("nickName", nickName, "用户名", new Rule[]{new Length(20), new NotEmpty()});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        sessionUser.setNick(nickName);
        this.setSessionAttribute(request,Constants.SESSION_USER,sessionUser);
        SysUser sysUser = userService.getUserById(sessionUser.getUserId());
        sysUser.setNkname(nickName);

        return userService.save(sysUser);
    }


    @API(summary = "性别更新",
            description = "性别更新",
            parameters = {
                    @Param(name = "sex", description = "邮箱地址", in = InType.body, schema = "{'setx':'手机号码'}", dataType = DataType.INTEGER, required = true),// false
            })

    @RequestMapping(value = "/sex/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO sexupdate(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {

        Integer sex = MapUtils.getInteger(bodyParam, "sex");

        if (sex != 1 && sex != 2 && sex != 0) {
            return this.getResult(30105395, "性别值错误");
        }
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        sessionUser.setSex(sex);
        SysUser sysUser = userService.getUserById(sessionUser.getUserId());
        sysUser.setSex(sex);

        return userService.save(sysUser);
    }

    @API(summary = "手机更新",
            description = "手机更新",
            parameters = {
                    @Param(name = "telno", description = "邮箱地址", in = InType.body, schema = "{'code',:'手机验证码','telno':'手机号码'}", dataType = DataType.STRING, required = true),// false
            })
    @RequestMapping(value = "/telno/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO telno(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String telno = MapUtils.getString(bodyParam, "telno");
        String pwd = MapUtils.getString(bodyParam, "pwd");
        String code = MapUtils.getString(bodyParam, "code");
        ResultDTO result = valideCodeService.imgValidCode("calendar", this.getSessionParam(request, "uid"), code);
        if (!result.isRight()) {
            return result;
        }
        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("telno", telno, "手机号", new Rule[]{new PhoneRule(), new NotEmpty()});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        sessionUser.setPhone(telno);
        SysUser sysUser = userService.getUserById(sessionUser.getUserId());
        sysUser.setTelno(telno);
        return userService.save(sysUser);
    }

    @Autowired
    ValidCodeService valideCodeService;

    @API(summary = "邮箱地址更新",
            description = "邮箱地址更新",
            parameters = {
                    @Param(name = "email", description = "邮箱地址", in = InType.body, schema = "{'code',:'邮箱验证码','email':'邮箱地址'}", dataType = DataType.STRING, required = true),// false
            })
    @RequestMapping(value = "/email/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO email(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        String email = MapUtils.getString(bodyParam, "email");
        String code = MapUtils.getString(bodyParam, "code");
        ResultDTO resultDTO = valideCodeService.validCode("ca", email, code, false);
        if (!resultDTO.isRight()) {
            return resultDTO;
        }
        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);

        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("email", email, "邮箱地址", new Rule[]{new EmailRule(), new NotEmpty()});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }
        sessionUser.setEmail(email);
        SysUser sysUser = userService.getUserById(sessionUser.getUserId());
        sysUser.setEmail(email);
        return userService.save(sysUser);
    }


    @API(summary = "用户名更新",
            description = "用户名更新",
            parameters = {
                    @Param(name = "userName", description = "用户名", in = InType.body, schema = "{'userName':'新的用户名'}", dataType = DataType.STRING, required = true),// false
            })
    @RequestMapping(value = "/username/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO usernameupdate(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {

        String userName = MapUtils.getString(bodyParam, "userName");
        try {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            vu.add("userName", userName, "用户名", new Rule[]{new Length(20), new NotEmpty()});
            validStr = vu.validateString();
            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }

        } catch (Exception e) {
            return ResultUtil.getResult(302, "系统错误");
        }

        // 获取userid
//        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser = this.getUser(request);
        sessionUser.setUserName(userName);
        SysUser sysUser = userService.getUserById(sessionUser.getUserId());
        sysUser.setUsername(userName);

        return userService.save(sysUser);
    }

    @Autowired
    StorageService storageService;
}
