package com.dozenx.web.core.auth.action;

import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.rules.*;
//import com.dozenx.web.module.merchant.bean.SessionDTO;
//import com.dozenx.web.module.merchant.bean.SessionUser;
//import com.dozenx.web.third.dbcenter.bean.PubUser;
//import com.dozenx.web.third.dbcenter.service.UserBaseService;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by dozen.zhang on 2017/3/9.
 */


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
//    @Resource
//    private UserBaseService userBaseService;
    @Resource
    SysUserService userService;

    @Resource
    AuthService authService;
    @RequestMapping(value = "/info.json")


    @ResponseBody
    public ResultDTO info(HttpServletRequest request){
       // SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        SessionUser sysUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
        /*sessionUser.setNick("nick");
        sessionUser.setAddress("address");
        sessionUser.setBirthday(new Date().getTime());
        sessionUser.setSex(1);
        sessionUser.setFace("/images/icon/face.png");*/
        return this.getResult(sysUser);
    }



    @RequestMapping(value = "/info")
    @ResponseBody
    public ResultDTO info1(HttpServletRequest request){
        SessionUser sysUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
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
        SessionUser sessionUser  = this.getUser(request);
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
    if(StringUtil.isNotBlank(validStr)) {
        return ResultUtil.getResult(302,validStr);
    }

}catch (Exception e){
    return ResultUtil.getResult(302,"系统错误");
}
       // sessionUser.setSex(Integer.valueOf(sex));
        //sessionUser.setAddress(address);
        sessionUser.setNick(nick);
       // sessionUser.setBirthday(DateUtil.parseToDate(birthday, "yyyy-MM-dd").getTime());//.str2Date(birthday, "yyyy-MM-dd"));
        sessionUser.setFace(face);
        try {
            SysUser sysUser = userService.selectByPrimaryKey(sessionUser.getUserId());
            if(sysUser!=null){
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
}
