/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysUser.action;

import com.alibaba.fastjson.JSON;
import com.dozenx.swagger.annotation.*;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ParamException;
import com.dozenx.web.core.Constants;
import com.dozenx.common.util.*;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.annotation.RequiresPermission;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.auth.sysUserDepart.service.SysUserDepartService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.OperLogUtil;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;


@APIs(description = "用户模块")
@Controller
@RequestMapping(Constants.WEBROOT + "/sys/auth/user")
public class SysUserController extends BaseController {
    /**
     * 日志
     **/
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);
    /**
     * 权限service
     **/
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDepartService sysUserDepartService;

    /**
     * 说明: 跳转到角色列表页面
     *
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/SysUserList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/SysUserListMapper.html";
    }


    /**
     * 说明:ajax请求角色信息
     *
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        //Object obj = SpringBeanFactoryUtils.getBean("SysConfigService");

        //ConfigUtil.getConfig("SysConfigService");
        Page page = RequestUtil.getPage(request);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String username = request.getParameter("username");
        if (!StringUtil.isBlank(username)) {
            params.put("username", username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if (!StringUtil.isBlank(usernameLike)) {
            params.put("usernameLike", usernameLike);
        }
        String password = request.getParameter("password");
        if (!StringUtil.isBlank(password)) {
            params.put("password", password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if (!StringUtil.isBlank(passwordLike)) {
            params.put("passwordLike", passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if (!StringUtil.isBlank(nkname)) {
            params.put("nkname", nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if (!StringUtil.isBlank(nknameLike)) {
            params.put("nknameLike", nknameLike);
        }
        String type = request.getParameter("type");
        if (!StringUtil.isBlank(type)) {
            params.put("type", type);
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String email = request.getParameter("email");
        if (!StringUtil.isBlank(email)) {
            params.put("email", email);
        }
        String emailLike = request.getParameter("emailLike");
        if (!StringUtil.isBlank(emailLike)) {
            params.put("emailLike", emailLike);
        }
        String telno = request.getParameter("telno");
        if (!StringUtil.isBlank(telno)) {
            params.put("telno", telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if (!StringUtil.isBlank(telnoLike)) {
            params.put("telnoLike", telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if (!StringUtil.isBlank(idcard)) {
            params.put("idcard", idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if (!StringUtil.isBlank(idcardLike)) {
            params.put("idcardLike", idcardLike);
        }
        String sex = request.getParameter("sex");
        if (!StringUtil.isBlank(sex)) {
            params.put("sex", sex);
        }
        String birth = request.getParameter("birth");
        if (!StringUtil.isBlank(birth)) {
            if (StringUtil.checkNumeric(birth)) {
                params.put("birth", birth);
            } else if (StringUtil.checkDateStr(birth, "yyyy-MM-dd")) {
                params.put("birth", DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if (!StringUtil.isBlank(birthBegin)) {
            if (StringUtil.checkNumeric(birthBegin)) {
                params.put("birthBegin", birthBegin);
            } else if (StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")) {
                params.put("birthBegin", DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if (!StringUtil.isBlank(birthEnd)) {
            if (StringUtil.checkNumeric(birthEnd)) {
                params.put("birthEnd", birthEnd);
            } else if (StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")) {
                params.put("birthEnd", DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if (!StringUtil.isBlank(integral)) {
            params.put("integral", integral);
        }
        String address = request.getParameter("address");
        if (!StringUtil.isBlank(address)) {
            params.put("address", address);
        }
        String addressLike = request.getParameter("addressLike");
        if (!StringUtil.isBlank(addressLike)) {
            params.put("addressLike", addressLike);
        }
        String weichat = request.getParameter("weichat");
        if (!StringUtil.isBlank(weichat)) {
            params.put("weichat", weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if (!StringUtil.isBlank(weichatLike)) {
            params.put("weichatLike", weichatLike);
        }
        String qq = request.getParameter("qq");
        if (!StringUtil.isBlank(qq)) {
            params.put("qq", qq);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<SysUser> sysUsers = sysUserService.listByParams4Page(params);
        return ResultUtil.getResult(sysUsers, page);
    }

    /**
     * 说明:ajax请求角色信息 无分页版本
     *
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String username = request.getParameter("username");
        if (!StringUtil.isBlank(username)) {
            params.put("username", username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if (!StringUtil.isBlank(usernameLike)) {
            params.put("usernameLike", usernameLike);
        }
        String password = request.getParameter("password");
        if (!StringUtil.isBlank(password)) {
            params.put("password", password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if (!StringUtil.isBlank(passwordLike)) {
            params.put("passwordLike", passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if (!StringUtil.isBlank(nkname)) {
            params.put("nkname", nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if (!StringUtil.isBlank(nknameLike)) {
            params.put("nknameLike", nknameLike);
        }
        String type = request.getParameter("type");
        if (!StringUtil.isBlank(type)) {
            params.put("type", type);
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String email = request.getParameter("email");
        if (!StringUtil.isBlank(email)) {
            params.put("email", email);
        }
        String emailLike = request.getParameter("emailLike");
        if (!StringUtil.isBlank(emailLike)) {
            params.put("emailLike", emailLike);
        }
        String telno = request.getParameter("telno");
        if (!StringUtil.isBlank(telno)) {
            params.put("telno", telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if (!StringUtil.isBlank(telnoLike)) {
            params.put("telnoLike", telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if (!StringUtil.isBlank(idcard)) {
            params.put("idcard", idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if (!StringUtil.isBlank(idcardLike)) {
            params.put("idcardLike", idcardLike);
        }
        String sex = request.getParameter("sex");
        if (!StringUtil.isBlank(sex)) {
            params.put("sex", sex);
        }
        String birth = request.getParameter("birth");
        if (!StringUtil.isBlank(birth)) {
            if (StringUtil.checkNumeric(birth)) {
                params.put("birth", birth);
            } else if (StringUtil.checkDateStr(birth, "yyyy-MM-dd")) {
                params.put("birth", DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if (!StringUtil.isBlank(birthBegin)) {
            if (StringUtil.checkNumeric(birthBegin)) {
                params.put("birthBegin", birthBegin);
            } else if (StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")) {
                params.put("birthBegin", DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if (!StringUtil.isBlank(birthEnd)) {
            if (StringUtil.checkNumeric(birthEnd)) {
                params.put("birthEnd", birthEnd);
            } else if (StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")) {
                params.put("birthEnd", DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if (!StringUtil.isBlank(integral)) {
            params.put("integral", integral);
        }
        String address = request.getParameter("address");
        if (!StringUtil.isBlank(address)) {
            params.put("address", address);
        }
        String addressLike = request.getParameter("addressLike");
        if (!StringUtil.isBlank(addressLike)) {
            params.put("addressLike", addressLike);
        }
        String weichat = request.getParameter("weichat");
        if (!StringUtil.isBlank(weichat)) {
            params.put("weichat", weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if (!StringUtil.isBlank(weichatLike)) {
            params.put("weichatLike", weichatLike);
        }
        String qq = request.getParameter("qq");
        if (!StringUtil.isBlank(qq)) {
            params.put("qq", qq);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        List<SysUser> sysUsers = sysUserService.listByParams(params);
        return ResultUtil.getDataResult(sysUsers);
    }

    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit(HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SysUserEdit.html";
    }

    @RequestMapping(value = "/view.htm")
    public Object viewPage(HttpServletRequest request) {
        return "/static/html/SysUserView.html";
    }

    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (!StringUtil.isBlank(id)) {
            SysUser bean = sysUserService.selectByPrimaryKey(Long.valueOf(id));
            bean.setPassword(null);
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        SysUser bean = sysUserService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }


    /**
     * 说明:保存角色信息
     *
     * @param request
     * @return Object
     * @throws Exception
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SysUser sysUser = new SysUser();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            sysUser.setId(Long.valueOf(id)) ;
        }

        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            sysUser.setUsername(String.valueOf(username)) ;
        }

        String password = request.getParameter("password");
        if(!StringUtil.isBlank(password)){
            sysUser.setPassword(String.valueOf(password)) ;
        }

        String nkname = request.getParameter("nkname");
        if(!StringUtil.isBlank(nkname)){
            sysUser.setNkname(String.valueOf(nkname)) ;
        }

        String type = request.getParameter("type");
        if(!StringUtil.isBlank(type)){
            sysUser.setType(Integer.valueOf(type)) ;
        }

        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            sysUser.setStatus(Integer.valueOf(status)) ;
        }

        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            sysUser.setEmail(String.valueOf(email)) ;
        }

        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            sysUser.setTelno(String.valueOf(telno)) ;
        }

        String idcard = request.getParameter("idcard");
        if(!StringUtil.isBlank(idcard)){
            sysUser.setIdcard(String.valueOf(idcard)) ;
        }

        String sex = request.getParameter("sex");
        if(!StringUtil.isBlank(sex)){
            sysUser.setSex(Integer.valueOf(sex)) ;
        }

        String birth = request.getParameter("birth");
        if(!StringUtil.isBlank(birth)){
            sysUser.setBirth(Date.valueOf(birth)) ;
        }

        String integral = request.getParameter("integral");
        if(!StringUtil.isBlank(integral)){
            sysUser.setIntegral(Integer.valueOf(integral)) ;
        }

        String address = request.getParameter("address");
        if(!StringUtil.isBlank(address)){
            sysUser.setAddress(String.valueOf(address)) ;
        }

        String weichat = request.getParameter("weichat");
        if(!StringUtil.isBlank(weichat)){
            sysUser.setWeichat(String.valueOf(weichat)) ;
        }

        String qq = request.getParameter("qq");
        if(!StringUtil.isBlank(qq)){
            sysUser.setQq(Long.valueOf(qq)) ;
        }

        String face = request.getParameter("face");
        if(!StringUtil.isBlank(face)){
            sysUser.setFace(String.valueOf(face)) ;
        }

        String remark = request.getParameter("remark");
        if(!StringUtil.isBlank(remark)){
            sysUser.setRemark(String.valueOf(remark)) ;
        }

        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            sysUser.setCreatetime(Timestamp.valueOf(createtime)) ;
        }

        String updatetime = request.getParameter("updatetime");
        if(!StringUtil.isBlank(updatetime)){
            sysUser.setUpdatetime(Timestamp.valueOf(updatetime)) ;
        }
        */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            sysUser.setId(Long.valueOf(id));
        }
        String username = request.getParameter("username");
        if (!StringUtil.isBlank(username)) {
            sysUser.setUsername(username);
        }
        String password = request.getParameter("password");
        if (!StringUtil.isBlank(password)) {
            sysUser.setPassword(password);
        }
        String nkname = request.getParameter("nkname");
        if (!StringUtil.isBlank(nkname)) {
            sysUser.setNkname(nkname);
        }
        String type = request.getParameter("type");
        if (!StringUtil.isBlank(type)) {
            sysUser.setType(Integer.valueOf(type));
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            sysUser.setStatus(Integer.valueOf(status));
        }
        String email = request.getParameter("email");
        if (!StringUtil.isBlank(email)) {
            sysUser.setEmail(email);
        }
        String telno = request.getParameter("telno");
        if (!StringUtil.isBlank(telno)) {
            sysUser.setTelno(telno);
        }
        String idcard = request.getParameter("idcard");
        if (!StringUtil.isBlank(idcard)) {
            sysUser.setIdcard(idcard);
        }
        String sex = request.getParameter("sex");
        if (!StringUtil.isBlank(sex)) {
            sysUser.setSex(Integer.valueOf(sex));
        }
        String birth = request.getParameter("birth");
        if (!StringUtil.isBlank(birth)) {
            if (StringUtil.checkNumeric(birth)) {
                sysUser.setBirth(new Date(birth));
            } else if (StringUtil.checkDateStr(birth, "yyyy-MM-dd")) {
                sysUser.setBirth(DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if (!StringUtil.isBlank(integral)) {
            sysUser.setIntegral(Integer.valueOf(integral));
        }
        String address = request.getParameter("address");
        if (!StringUtil.isBlank(address)) {
            sysUser.setAddress(address);
        }
        String weichat = request.getParameter("weichat");
        if (!StringUtil.isBlank(weichat)) {
            sysUser.setWeichat(weichat);
        }
        String qq = request.getParameter("qq");
        if (!StringUtil.isBlank(qq)) {
            sysUser.setQq(Long.valueOf(qq));
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            sysUser.setFace(face);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            sysUser.setRemark(remark);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                sysUser.setCreatetime(Timestamp.valueOf(createtime));
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                sysUser.setCreatetime(new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                sysUser.setUpdatetime(Timestamp.valueOf(updatetime));
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                sysUser.setUpdatetime(new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("username", username, "用户名", new Rule[]{new Length(20), new NotEmpty()});
        vu.add("password", password, "密码", new Rule[]{new Length(50), new NotEmpty()});
        vu.add("nkname", nkname, "昵称", new Rule[]{new Length(20)});
        vu.add("type", type, "类型", new Rule[]{new Digits(4, 0)});
        vu.add("status", status, "状态", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"1", "2", "3"}), new NotEmpty()});
        vu.add("email", email, "邮箱地址", new Rule[]{new Length(50), new EmailRule()});
        vu.add("telno", telno, "手机号码", new Rule[]{new Length(11), new PhoneRule()});
        vu.add("idcard", idcard, "身份证号码", new Rule[]{new Length(18)});
        vu.add("sex", sex, "性别", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"0", "1", "2"})});
        vu.add("birth", birth, "出生年月", new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("integral", integral, "积分", new Rule[]{new Digits(11, 0)});
        vu.add("address", address, "地址", new Rule[]{new Length(50)});
        vu.add("weichat", weichat, "微信", new Rule[]{new Length(20)});
        vu.add("qq", qq, "qq", new Rule[]{new Digits(11, 0)});
        vu.add("face", face, "头像", new Rule[]{new Length(100)});
        vu.add("remark", remark, "备注", new Rule[]{new Length(200)});
        vu.add("createtime", createtime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("updatetime", updatetime, "更新时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            return this.getResult(302, validStr);
        }

        return sysUserService.save(sysUser);

    }


    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        sysUserService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 多行删除
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[] = idStr.split(",");
        Long idAry[] = new Long[idStrAry.length];
        for (int i = 0, length = idAry.length; i < length; i++) {
            ValidateUtil vu = new ValidateUtil();
            String validStr = "";
            String id = idStrAry[i];
            vu.add("id", id, "编号", new Rule[]{});

            try {
                validStr = vu.validateString();
            } catch (Exception e) {
                e.printStackTrace();
                validStr = "验证程序异常";
                return ResultUtil.getResult(302, validStr);
            }

            if (StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302, validStr);
            }
            idAry[i] = Long.valueOf(idStrAry[i]);
        }
        return sysUserService.multilDelete(idAry);
    }

    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public Object exportExcel(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String username = request.getParameter("username");
        if (!StringUtil.isBlank(username)) {
            params.put("username", username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if (!StringUtil.isBlank(usernameLike)) {
            params.put("usernameLike", usernameLike);
        }
        String password = request.getParameter("password");
        if (!StringUtil.isBlank(password)) {
            params.put("password", password);
        }
        String passwordLike = request.getParameter("passwordLike");
        if (!StringUtil.isBlank(passwordLike)) {
            params.put("passwordLike", passwordLike);
        }
        String nkname = request.getParameter("nkname");
        if (!StringUtil.isBlank(nkname)) {
            params.put("nkname", nkname);
        }
        String nknameLike = request.getParameter("nknameLike");
        if (!StringUtil.isBlank(nknameLike)) {
            params.put("nknameLike", nknameLike);
        }
        String type = request.getParameter("type");
        if (!StringUtil.isBlank(type)) {
            params.put("type", type);
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String email = request.getParameter("email");
        if (!StringUtil.isBlank(email)) {
            params.put("email", email);
        }
        String emailLike = request.getParameter("emailLike");
        if (!StringUtil.isBlank(emailLike)) {
            params.put("emailLike", emailLike);
        }
        String telno = request.getParameter("telno");
        if (!StringUtil.isBlank(telno)) {
            params.put("telno", telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if (!StringUtil.isBlank(telnoLike)) {
            params.put("telnoLike", telnoLike);
        }
        String idcard = request.getParameter("idcard");
        if (!StringUtil.isBlank(idcard)) {
            params.put("idcard", idcard);
        }
        String idcardLike = request.getParameter("idcardLike");
        if (!StringUtil.isBlank(idcardLike)) {
            params.put("idcardLike", idcardLike);
        }
        String sex = request.getParameter("sex");
        if (!StringUtil.isBlank(sex)) {
            params.put("sex", sex);
        }
        String birth = request.getParameter("birth");
        if (!StringUtil.isBlank(birth)) {
            if (StringUtil.checkNumeric(birth)) {
                params.put("birth", birth);
            } else if (StringUtil.checkDateStr(birth, "yyyy-MM-dd")) {
                params.put("birth", DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }
        String birthBegin = request.getParameter("birthBegin");
        if (!StringUtil.isBlank(birthBegin)) {
            if (StringUtil.checkNumeric(birthBegin)) {
                params.put("birthBegin", birthBegin);
            } else if (StringUtil.checkDateStr(birthBegin, "yyyy-MM-dd")) {
                params.put("birthBegin", DateUtil.parseToDate(birthBegin, "yyyy-MM-dd"));
            }
        }
        String birthEnd = request.getParameter("birthEnd");
        if (!StringUtil.isBlank(birthEnd)) {
            if (StringUtil.checkNumeric(birthEnd)) {
                params.put("birthEnd", birthEnd);
            } else if (StringUtil.checkDateStr(birthEnd, "yyyy-MM-dd")) {
                params.put("birthEnd", DateUtil.parseToDate(birthEnd, "yyyy-MM-dd"));
            }
        }
        String integral = request.getParameter("integral");
        if (!StringUtil.isBlank(integral)) {
            params.put("integral", integral);
        }
        String address = request.getParameter("address");
        if (!StringUtil.isBlank(address)) {
            params.put("address", address);
        }
        String addressLike = request.getParameter("addressLike");
        if (!StringUtil.isBlank(addressLike)) {
            params.put("addressLike", addressLike);
        }
        String weichat = request.getParameter("weichat");
        if (!StringUtil.isBlank(weichat)) {
            params.put("weichat", weichat);
        }
        String weichatLike = request.getParameter("weichatLike");
        if (!StringUtil.isBlank(weichatLike)) {
            params.put("weichatLike", weichatLike);
        }
        String qq = request.getParameter("qq");
        if (!StringUtil.isBlank(qq)) {
            params.put("qq", qq);
        }
        String face = request.getParameter("face");
        if (!StringUtil.isBlank(face)) {
            params.put("face", face);
        }
        String faceLike = request.getParameter("faceLike");
        if (!StringUtil.isBlank(faceLike)) {
            params.put("faceLike", faceLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime", new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeBegin", new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtimeEnd", new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetime = request.getParameter("updatetime");
        if (!StringUtil.isBlank(updatetime)) {
            if (StringUtil.checkNumeric(updatetime)) {
                params.put("updatetime", updatetime);
            } else if (StringUtil.checkDateStr(updatetime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetime", new Timestamp(DateUtil.parseToDate(updatetime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeBegin = request.getParameter("updatetimeBegin");
        if (!StringUtil.isBlank(updatetimeBegin)) {
            if (StringUtil.checkNumeric(updatetimeBegin)) {
                params.put("updatetimeBegin", updatetimeBegin);
            } else if (StringUtil.checkDateStr(updatetimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeBegin", new Timestamp(DateUtil.parseToDate(updatetimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String updatetimeEnd = request.getParameter("updatetimeEnd");
        if (!StringUtil.isBlank(updatetimeEnd)) {
            if (StringUtil.checkNumeric(updatetimeEnd)) {
                params.put("updatetimeEnd", updatetimeEnd);
            } else if (StringUtil.checkDateStr(updatetimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("updatetimeEnd", new Timestamp(DateUtil.parseToDate(updatetimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        // 查询list集合
        List<SysUser> list = sysUserService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("username", "用户名");
        colTitle.put("password", "密码");
        colTitle.put("nkname", "昵称");
        colTitle.put("type", "类型");
        colTitle.put("status", "状态");
        colTitle.put("email", "邮箱地址");
        colTitle.put("telno", "手机号码");
        colTitle.put("idcard", "身份证号码");
        colTitle.put("sex", "性别");
        colTitle.put("birth", "出生年月");
        colTitle.put("integral", "积分");
        colTitle.put("address", "地址");
        colTitle.put("weichat", "微信");
        colTitle.put("qq", "qq");
        colTitle.put("face", "头像");
        colTitle.put("remark", "备注");
        colTitle.put("createtime", "创建时间");
        colTitle.put("updatetime", "更新时间");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysUser sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("username", list.get(i).getUsername());
            map.put("password", list.get(i).getPassword());
            map.put("nkname", list.get(i).getNkname());
            map.put("type", list.get(i).getType());
            map.put("status", list.get(i).getStatus());
            map.put("email", list.get(i).getEmail());
            map.put("telno", list.get(i).getTelno());
            map.put("idcard", list.get(i).getIdcard());
            map.put("sex", list.get(i).getSex());
            map.put("birth", list.get(i).getBirth());
            map.put("integral", list.get(i).getIntegral());
            map.put("address", list.get(i).getAddress());
            map.put("wechat", list.get(i).getWechat());
            map.put("qq", list.get(i).getQq());
            map.put("face", list.get(i).getFace());
            map.put("remark", list.get(i).getRemark());
            map.put("createtime", list.get(i).getCreatetime());
            map.put("updatetime", list.get(i).getUpdatetime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, fileName, "导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");

    }

    @API(summary = "批量导入信息",
            description = "批量导入信息",
            consumes = "multipart/form-data",
            parameters = {
                    @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
            })
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO importExcel(@RequestParam("file") MultipartFile file) {
        // 将spring 的file 装成 普通file
        File xlsFile = null;
        if (file != null) {
            try {
                String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
                String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
                Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
                xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
            } catch (Exception e) {
                logger.error("文件上传出错", e);
                throw new BizException("E041412312", "文件上传出错");
            }
        }
        String result = "";
        try {

            // 将导入的中文列名匹配至数据库对应字段
            int success = 0;
            int fail = 0;
            StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
            //            Map<String, String> colMatch = new HashMap<String, String>();
            //            colMatch.put("姓名", "name");
            //            colMatch.put("单位", "org");
            //            colMatch.put("邮箱", "email");


            List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
            for (int i = 0; i < list.size(); i++) {

                Map<String, String> map = list.get(i);
                String email = MapUtils.getStringValue(map, "邮箱");
                // 检验手机号是否符合规范,不符合continue
                if (!StringUtil.isEmail(email)) {
                    //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
                    logger.info(" import conf ==> the telphone:" + email + " is not email");
                    fail++;
                    errorMsg.append("" + email + " 不是邮箱地址;");
                    continue;
                }
                HashMap params = new HashMap();
                params.put("email", email);
                //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在

                SysUser bean = getInfoFromMap(params);

                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    sysUserService.save(bean);
                    success++;//成功数增加
                } catch (Exception e) {

                    fail++;//失败数增加
                    logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                    errorMsg.append("the telphone:" + email + " update fail;");
                }

            }
            if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
            }
            return this.getResult(3090182, "导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入失败", e);
            if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                throw new BizException("E041412313", "导入的excel需为2003版本");
            } else {
                throw new BizException("E041412313", e.getMessage());
            }
        }


    }


    @API(summary = "数据批量同步",
            description = "数据批量同步",
            consumes = "multipart/form-data",
            parameters = {
                    @Param(name = "params", description = "用户列表json字符串", in = InType.form, dataType = DataType.FILE, required = true),
            })
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO sync(HttpServletRequest request) {
        // 将spring 的file 装成 普通file

        String result = "";
        try {
            // 将导入的中文列名匹配至数据库对应字段
            int success = 0;
            int fail = 0;
            StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
            //            Map<String, String> colMatch = new HashMap<String, String>();
            //            colMatch.put("姓名", "name");
            //            colMatch.put("单位", "org");
            //            colMatch.put("邮箱", "email");

            String params = request.getParameter("params");

            List<HashMap> list = JSON.parseArray(params, HashMap.class);
            // List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
            for (int i = 0; i < list.size(); i++) {


                Map<String, Object> map = list.get(i);

                String userId = MapUtils.getString(map, "id");
                String name = MapUtils.getString(map, "name");
                String telno = MapUtils.getString(map, "telno");
                String remark = MapUtils.getString(map, "remark");
                String email = MapUtils.getString(map, "email");
//                String wechat = MapUtils.getString(map,"wechat");
//                String nkname = MapUtils.getString(map,"nkname");
                // 检验手机号是否符合规范,不符合continue
                if (!StringUtil.isEmail(email)) {
                    //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
                    logger.info(" import conf ==> the telphone:" + email + " is not email");
                    fail++;
                    errorMsg.append("" + email + " 不是邮箱地址;");
                    continue;
                }


                //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在


                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    SysUser bean = getInfoFromMap(map);
                    //查看这个outId是否有了
                    HashMap outIdMap = new HashMap();
                    outIdMap.put("username", bean.getUsername());
                    List<SysUser> sysUsers = sysUserService.listByParams(outIdMap);
                    if (sysUsers != null && sysUsers.size() > 0) {
                        SysUser sysUser = sysUsers.get(0);
                        sysUser.setOutId(bean.getOutId());//从access同步到用户id
                        if(StringUtil.isBlank(sysUser.getEmail()) && StringUtil.isNotBlank(bean.getEmail())){
                            sysUser.setEmail(bean.getEmail());//从user.xls同步到用户信息
                        }
                        if(StringUtil.isBlank(sysUser.getTelno()) && StringUtil.isNotBlank(bean.getTelno())){
                            sysUser.setTelno(bean.getTelno());//从user.xls同步到用户信息
                        }
                        ResultDTO resultDTO =sysUserService.save(sysUser);
                        if(!resultDTO.isRight()){
                            throw new BizException("E2000016",  resultDTO.getMsg());

                        }
                    }else{
                        logger.info("数据库不存在该用户:"+bean.getUsername());
                    }

                    success++;//成功数增加
                } catch (Exception e) {
                    logger.error("error",e);
                    fail++;//失败数增加
                    logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
                    errorMsg.append("the telphone:" + email + " update fail;");
                }

            }
            if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
                throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
            }
            return this.getResult(3090182, "导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入失败", e);
            if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
                throw new BizException("E041412313", "导入的excel需为2003版本");
            } else {
                throw new BizException("E041412313", e.getMessage());
            }
        }


    }


    /**
     * 说明:ajax请求用户信息
     *
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */

    @API(summary = "用户列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 用户列表分页查询接口", parameters = {

            @Param(name = "params", description = "{telno:13958173965, name:\"123\", curPage:1,pageSize:30 }" +
                    "{telno:手机号码 \n" +
                    "name:'姓名', 支持模糊查询\n" +
                    "curPage:1 //当前页\n" +
                    "pageSize:30//每页记录数，数字，不允许为空\n" +
                    "}", dataType = DataType.STRING, in = InType.query, required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    @RequiresPermission
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object list(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) {
        Map<String, Object> params = JsonUtil.fromJson(paramStr, Map.class);
        Page page = RequestUtil.getPage(params);
        params.put("page", page);

        String nameLike = MapUtils.getStringValue(params, "name");
        if(StringUtil.isNotBlank(nameLike)){
            params.put("accountLike", nameLike);//账号关键字查询
            params.remove("name");
        }
       // params.put("userNameLike", MapUtils.getStringValue(params, "userNameLike"));//姓名查询

        List<HashMap<String, Object>> sysUsers = sysUserService.listWithRoleInfoByParams4Page(params);
        return ResultUtil.getResult(sysUsers, page);
    }


    @API(summary = "人员下拉 ,非管理员只能下拉自己的",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 根据角色查询用户列表接口 提供下拉选择", parameters = {

//            @Param(name = "id", description = "{2}"
//                  , dataType = DataType.INTEGER, in=InType.path,required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    //@RequiresPermission
    @RequestMapping(value = "/select", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object select(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        if (!isAdmin(request)) {
            params.put("id", getUserId(request));
        }
        List<HashMap<String, Object>> sysUsers = sysUserService.listIdNameByParams(params);
        return ResultUtil.getDataResult(sysUsers);
    }

    @API(summary = "根据角色查询用户列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 根据角色查询用户列表接口 提供下拉选择", parameters = {

//            @Param(name = "id", description = "{2}"
//                  , dataType = DataType.INTEGER, in=InType.path,required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    //@RequiresPermission
    @RequestMapping(value = "/role/select/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object selectByRole(HttpServletRequest request, @PathVariable("id") Long roleId) {

        List<HashMap<String, Object>> sysUsers = sysUserService.listIdNameByRole(roleId);
        return ResultUtil.getDataResult(sysUsers);
    }

    /**
     * 说明:保存角色信息
     *
     * @param request
     * @return Object
     * @throws Exception
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    @API(summary = "用户添加接口",
            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {

            @Param(name = "account", description = "账号"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "username", description = "用户名"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "password", description = "密码"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "email", description = "用户邮箱"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "telno", description = "手机号码"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "face", description = "头像"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "roleIds", description = "角色id数组"
                    , dataType = DataType.ARRAY, in = InType.body, required = false),
            @Param(name = "province", description = "省id"
                    , dataType = DataType.STRING, in = InType.body, required = true),

            @Param(name = "city", description = "市id"
                    , dataType = DataType.STRING, in = InType.body, required = false),

            @Param(name = "county", description = "区id"
                    , dataType = DataType.STRING, in = InType.body, required = false),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    @RequiresPermission
    @ResponseBody
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    public ResultDTO add(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysUser sysUser = getInfoFromMap(bodyParam);


        //检查用户名
        //valid
        //获取角色id数组

        //保存用户信息并 关联角色信息

        //获取组织信息 然后添加组织信息
//        Long depart = MapUtils.getLong(bodyParam, "depart");
        ResultDTO result = null;
//        if (depart != null) {
//            logger.info("添加组织关系");
        result = sysUserService.saveWithRoleInfo(sysUser);
//            SysUserDepart sysUserDepart = new SysUserDepart();
//            sysUserDepart.setUserId(sysUser.getId());
//            sysUserDepart.setDepartId(depart);
//            result = sysUserDepartService.save(sysUserDepart);
//        }

        if(StringUtil.isNotBlank(ConfigUtil.getConfig("updated.user.face"))){
            String resultStr =   HttpRequestUtil.sendGet(ConfigUtil.getConfig("updated.user.face")+"?userId="+sysUser.getId());

        }
        OperLogUtil.add(request, "系统管理", "账号管理","添加账号,账号:"+ sysUser.getAccount() + sysUser.getUsername());
        return result;

    }


    @API(summary = "用户资料更新接口",

            consumes = "application/json",
            description = "sysUserController 用户资料更新接口 更新关联的角色信息 不更新角色信息.所有信息封装成json 在body体中 ", parameters = {

            @Param(name = "id", description = "用户id"
                    , dataType = DataType.LONG, in = InType.path, required = true),
            @Param(name = "username", description = "用户名"
                    , dataType = DataType.LONG, in = InType.body, required = true),
            @Param(name = "password", description = "密码"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "email", description = "用户邮箱"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "telno", description = "手机号码"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "face", description = "头像"
                    , dataType = DataType.STRING, in = InType.body, required = true),

            @Param(name = "province", description = "省id"
                    , dataType = DataType.STRING, in = InType.body, required = true),

            @Param(name = "city", description = "市id"
                    , dataType = DataType.STRING, in = InType.body, required = true),

            @Param(name = "county", description = "区id"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "roleIds", description = "角色id数组"
                    , dataType = DataType.ARRAY, in = InType.body, required = false),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequiresPermission
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody


    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)

    public Object update(HttpServletRequest request, @PathVariable Long id, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysUser sysUser = getInfoFromMap(bodyParam);
        id = MapUtils.getLong(bodyParam, "id");
        if (id == null || id == 0) {
            return ResultUtil.getResult(10102001, ErrorMessage.getErrorMsg("err.param.null", "用户id"));
        }

        sysUser.setId(id);

        //获取角色id

        sysUser.setPassword(null);


        OperLogUtil.add(request, "系统管理", "账号管理","修改账号,账号:"+ sysUser.getAccount() + sysUser.getUsername());
        ResultDTO resultDTO =  sysUserService.saveWithRoleInfo(sysUser);
        //新增完用户后才能更新人脸 报账主功能是通的
        try {
            if (StringUtil.isNotBlank(ConfigUtil.getConfig("updated.user.face"))) {
                logger.info("开始调用updated.user.face接口"+ConfigUtil.getConfig("updated.user.face"));
                String resultStr = HttpRequestUtil.sendGet(ConfigUtil.getConfig("updated.user.face") + "?userId=" + sysUser.getId());

            }
        }catch (Exception e){
            logger.error("更新人脸特征库报错",e);
        }
        return resultDTO;
    }

    @API(summary = "用户资料删除接口",

            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 用户删除接口", parameters = {

            @Param(name = "id", description = "用户id", dataType = DataType.LONG, in = InType.path, required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequiresPermission
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Object deleteRestFul(@PathVariable("id") Long id, HttpServletRequest request) {

        if (id == null) {
            return this.getResult(10202003, ErrorMessage.getErrorMsg("err.param.null", "用户id"));
        }


        SysUser sysUser = sysUserService.getUserById(id);
        if (sysUser == null) {
            return this.getResult(10205460, ErrorMessage.getErrorMsg("err.param.null", "用户不存在"));
        }
        OperLogUtil.add(request, "系统管理", "账号管理", "删除账号,账号:"+ sysUser.getAccount() + sysUser.getUsername());

        sysUserService.delete(id);//将状态为改成9

        return this.getResult(SUCC);
    }


    @API(summary = "用户详情接口",

            consumes = MediaType.APPLICATION_JSON_VALUE,
            description = "用户详情接口", parameters = {

            @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in = InType.path, required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequiresPermission
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object viewRestFul(@PathVariable("id") Long id, HttpServletRequest request) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        if (id > 0) {
            SysUser bean = sysUserService.selectWithRoleInfoByPrimaryKey(Long.valueOf(id));
            bean.setPassword(null);
            return this.getResult(bean);

        }

        //返回角色信息
        return this.getResult(10102300, ErrorMessage.getErrorMsg("err.param.null", "用户id"));

    }

    private SysUser getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        SysUser sysUser = new SysUser();
        String username = MapUtils.getString(bodyParam, "username");
        if (!StringUtil.isBlank(username)) {
            sysUser.setUsername(username);
        }

        String account = MapUtils.getString(bodyParam, "account");
        if (!StringUtil.isBlank(account)) {
            sysUser.setAccount(account);
        }
        String password = MapUtils.getString(bodyParam, "password");
        if (!StringUtil.isBlank(password)) {
            sysUser.setPassword(MD5Util.getStringMD5String(password));//别忘记md5加密
        }
        String nkname = MapUtils.getString(bodyParam, "nkname");
        if (!StringUtil.isBlank(nkname)) {
            sysUser.setNkname(nkname);
        }

        String email = MapUtils.getString(bodyParam, "email");
        if (!StringUtil.isBlank(email)) {
            sysUser.setEmail(email);
        }
        String telno = MapUtils.getString(bodyParam, "telno");
        if (!StringUtil.isBlank(telno)) {
            sysUser.setTelno(telno);
        }
        String idcard = MapUtils.getString(bodyParam, "idcard");
        if (!StringUtil.isBlank(idcard)) {
            sysUser.setIdcard(idcard);
        }
        String sex = MapUtils.getString(bodyParam, "sex");
        if (!StringUtil.isBlank(sex)) {
            sysUser.setSex(Integer.valueOf(sex));
        }
        String birth = MapUtils.getString(bodyParam, "birth");
        if (!StringUtil.isBlank(birth)) {
            if (StringUtil.checkNumeric(birth)) {
                sysUser.setBirth(new Date(birth));
            } else if (StringUtil.checkDateStr(birth, "yyyy-MM-dd")) {
                sysUser.setBirth(DateUtil.parseToDate(birth, "yyyy-MM-dd"));
            }
        }

        String address = MapUtils.getString(bodyParam, "address");
        if (!StringUtil.isBlank(address)) {
            sysUser.setAddress(address);
        }

        String qq = MapUtils.getString(bodyParam, "qq");
        if (!StringUtil.isBlank(qq)) {
            sysUser.setQq(Long.valueOf(qq));
        }
        String face = MapUtils.getString(bodyParam, "face");
        if (!StringUtil.isBlank(face)) {
            sysUser.setFace(face);
        }
        String remark = MapUtils.getString(bodyParam, "remark");
        if (!StringUtil.isBlank(remark)) {
            sysUser.setRemark(remark);
        }
        String code = MapUtils.getString(bodyParam, "code");
        if (!StringUtil.isBlank(code)) {
            sysUser.setCode(code);
        }
        String org = MapUtils.getString(bodyParam, "org");
        if (!StringUtil.isBlank(org)) {
            sysUser.setOrg(org);
        }
        String depart = MapUtils.getString(bodyParam, "depart");
        if (!StringUtil.isBlank(depart)) {
            sysUser.setDepart(depart);
        }

        Integer province = MapUtils.getInteger(bodyParam, "province");
        if (province != null && province > 0) {
            sysUser.setProvince(province);
        }

        Integer city = MapUtils.getInteger(bodyParam, "city");
        if (city != null && city > 0) {
            sysUser.setCity(city);
        }

        Integer county = MapUtils.getInteger(bodyParam, "county");
        if (county != null && county > 0) {
            sysUser.setCounty(county);
        }


        Long outId = MapUtils.getLong(bodyParam, "outId");
        if (outId != null && outId > 0) {
            sysUser.setOutId(outId);
        }

        Object roleIdsObj = bodyParam.get("roleIds");
        if (roleIdsObj != null) {
            sysUser.setRoleIds(JsonUtil.convertDigitAryToLongAry((ArrayList<Number>) bodyParam.get("roleIds")));
        }

        //基础的参数校验  不对任何参数进行非空校验 需要的话 自行再校验一般
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("username", sysUser.getUsername(), "用户名", new Rule[]{new Length(30), new Regex(RegexConstants.ZHONGWEN_ALPHA_NUMBER_PATTERN)});
        vu.add("password", password, "密码", new Rule[]{new Length(50)});
        vu.add("nkname", nkname, "昵称", new Rule[]{new Length(20)});
        vu.add("email", email, "邮箱地址", new Rule[]{new Length(50), new EmailRule()});
        vu.add("telno", telno, "手机号码", new Rule[]{new Length(11), new PhoneRule(), new NotEmpty()});
        vu.add("idcard", idcard, "身份证号码", new Rule[]{new Length(18)});
        vu.add("sex", sex, "性别", new Rule[]{new Digits(1, 0), new CheckBox(new String[]{"0", "1", "2"})});
        vu.add("birth", birth, "出生年月", new Rule[]{new DateValue("yyyy-MM-dd")});
        vu.add("address", address, "地址", new Rule[]{new Length(50)});
        vu.add("qq", qq, "qq", new Rule[]{new Digits(11, 0)});
        vu.add("face", face, "头像", new Rule[]{new Length(100)});
        vu.add("province", province, "省", new Rule[]{new Numeric()});
        vu.add("city", province, "市", new Rule[]{new Numeric()});
        vu.add("county", province, "区", new Rule[]{new Numeric()});

        vu.add("remark", remark, "备注", new Rule[]{new Length(100)});
        vu.add("account", account, "账号", new Rule[]{new Length(30), new NotEmpty(), new Regex("^[0-9a-zA-Z_@\\.]+$")});
        validStr = vu.validateString();
        if (StringUtil.isNotBlank(validStr)) {
            logger.debug("用户添加 参数验证错误" + validStr);
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return sysUser;
    }


    @API(summary = "用户密码重置功能",

            consumes = "application/json",
            description = "sysUserController 用户密码重置功能 更新关联的角色密码信息 不更新角色信息.所有信息封装成json 在body体中 ", parameters = {

            @Param(name = "oldPassword", description = "旧密码"
                    , dataType = DataType.STRING, in = InType.body, required = true),
            @Param(name = "newPassword", description = "新密码"
                    , dataType = DataType.STRING, in = InType.body, required = true),

    })

    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    /**
     * 修改密码
     */
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequiresLogin
    @RequestMapping(value = "/pwd/update", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Object updatePwd(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {

        String oldPassword = MapUtils.getString(bodyParam, "oldPassword");
        String newPassword = MapUtils.getString(bodyParam, "newPassword");

        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("newPassword", oldPassword, "新密码", new Rule[]{new Required(), new Length(6, 20), new Regex(RegexConstants.PASSWORD)});
        vu.add("oldPassword", oldPassword, "旧密码", new Rule[]{new Required(), new Length(6, 20)});

        Long userId = this.getUserId(request);
        String userName = this.getUserName(request);
        sysUserService.getUserById(userId);
        OperLogUtil.add(request, "系统管理", "账号管理", "修改密码"+userName);
        return sysUserService.updateUserPwd(userId, oldPassword, newPassword);
    }

    public static void main(String args[]) {
    }
}
