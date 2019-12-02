/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-11-5 20:09:43
 * 文件说明:
 */

package com.dozenx.web.module.checkin.checkinOut.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.DateValue;
import com.dozenx.web.core.rules.Digits;
import com.dozenx.web.core.rules.NotEmpty;
import com.dozenx.web.core.rules.Rule;
import com.dozenx.web.module.checkin.checkinLate.bean.CheckinLate;
import com.dozenx.web.module.checkin.checkinLate.service.CheckinLateService;
import com.dozenx.web.module.checkin.checkinOut.bean.CheckinOut;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTask;
import com.dozenx.web.module.checkin.checkinOut.bean.FinishTaskData;
import com.dozenx.web.module.checkin.checkinOut.service.CheckinOutService;
import com.dozenx.web.module.checkin.faceCheckinOut.bean.FaceCheckinOut;
import com.dozenx.web.module.checkin.faceCheckinOut.service.FaceCheckinOutService;
import com.dozenx.web.module.checkin.faceInfo.service.VirtualWeixinService;
import com.dozenx.web.module.email.email.service.EmailSendService;
import com.dozenx.web.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

@APIs(description = "考勤")
@Controller
@RequestMapping("/checkin")
public class CheckinOutController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(CheckinOutController.class);
    /**
     * 权限service
     **/
    @Autowired
    private CheckinOutService checkinOutService;

    @Autowired
    private CheckinLateService checkinLateService;


    @Autowired
    private FaceCheckinOutService faceCheckinOutService;

    @Autowired
    private SysUserService sysUserService;

    //    /**
//     * 说明:ajax请求CheckinOut信息
//     *
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "考勤列表接口",
//            description = "考勤列表接口",
//            parameters = {
//                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
//                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
//                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),// false
//                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = false),// true
//                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = false),// true
//                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),// false
//            })
//    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO list(HttpServletRequest request) throws Exception {
//        Page page = RequestUtil.getPage(request);
//        if (page == null) {
//            return this.getWrongResultFromCfg("err.param.page");
//        }
//
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            params.put("id", id);
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            params.put("userId", userId);
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            params.put("checkType", checkType);
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                params.put("checkTime", checkTime);
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeBegin = request.getParameter("checkTimeBegin");
//        if (!StringUtil.isBlank(checkTimeBegin)) {
//            if (StringUtil.checkNumeric(checkTimeBegin)) {
//                params.put("checkTimeBegin", checkTimeBegin);
//            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeEnd = request.getParameter("checkTimeEnd");
//        if (!StringUtil.isBlank(checkTimeEnd)) {
//            if (StringUtil.checkNumeric(checkTimeEnd)) {
//                params.put("checkTimeEnd", checkTimeEnd);
//            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        params.put("page", page);
//        List<CheckinOut> checkinOuts = checkinOutService.listByParams4Page(params);
//        return ResultUtil.getResult(checkinOuts, page);
//    }
//
//
    @API(summary = "考勤列表接口",
            description = "考勤列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = false),// true
                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = false),// true
                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/listActivities.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO listActivities(HttpServletRequest request) throws Exception {
        Page page = RequestUtil.getPage(request);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }

        String userId = request.getParameter("userId");
        SysUser sysUser = sysUserService.getUserById(Long.valueOf(userId));
        logger.info("userId" + userId);


        if (sysUser == null) {
            return this.getResult(30801163, "该用户不存在");
        }
        Long otherUserId = sysUser.getOutId();
        if (otherUserId == null) {
            return this.getResult(30801163, "该用户的考勤模块不存在");
        } else {
            params.put("userId", otherUserId);
        }
        String checkType = request.getParameter("checkType");
        if (!StringUtil.isBlank(checkType)) {
            params.put("checkType", checkType);
        }
        String checkTime = request.getParameter("checkTime");
        if (!StringUtil.isBlank(checkTime)) {
            if (StringUtil.checkNumeric(checkTime)) {
                params.put("checkTime", checkTime);
            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = request.getParameter("checkTimeBegin");
        if (!StringUtil.isBlank(checkTimeBegin)) {
            if (StringUtil.checkNumeric(checkTimeBegin)) {
                params.put("checkTimeBegin", checkTimeBegin);
            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = request.getParameter("checkTimeEnd");
        if (!StringUtil.isBlank(checkTimeEnd)) {
            if (StringUtil.checkNumeric(checkTimeEnd)) {
                params.put("checkTimeEnd", checkTimeEnd);
            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String showType = request.getParameter("showType");
        params.put("page", page);
        logger.info("params" + JsonUtil.toJsonString(params));
        List finalList = new ArrayList<>();
        if (StringUtil.isBlank(showType) || showType.equals("0")) {


            List<CheckinOut> checkinOuts = checkinOutService.listByParams(params);

            logger.info("checkinOuts" + checkinOuts.size());
            for (CheckinOut checkinOut : checkinOuts) {
                HashMap map = new HashMap();
                map.put("isdel", false);
                map.put("title", "签到" + DateUtil.toDateStr(new Date(checkinOut.getCheckTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
                map.put("id", checkinOut.getId());
                map.put("startTime", checkinOut.getCheckTime().getTime() / 60000);
                map.put("endTime", checkinOut.getCheckTime().getTime() / 60000);
                map.put("userId", otherUserId);
                map.put("type", 9);
                map.put("edit", 0);
//            "isdel": false,
//                    "title": "写学习博客",
//                    "id": 2018111245,
//                    "startTime": 25700040,
//                    "endTime": 25700100,
//                    "userId": 207,
//                    "type": 0,
//                    "privacy": 0

                finalList.add(map);
            }
        }

        if (StringUtil.isBlank(showType) || showType.equals("2")) {


            params.put("userId", sysUser.getId());
            List<CheckinLate> checkinLates = checkinLateService.listByParams(params);


            for (CheckinLate checkinLate : checkinLates) {
                HashMap map = new HashMap();
                map.put("isdel", false);
                if (checkinLate.getCheckType() == 1) {
                    map.put("title", "迟到" + DateUtil.toDateStr(new Date(checkinLate.getCheckTime().getTime()), "yyyy-MM-dd HH:mm:ss"));

                } else {
                    map.put("title", "未打卡" + DateUtil.toDateStr(new Date(checkinLate.getCheckTime().getTime()), "yyyy-MM-dd HH:mm:ss"));

                }
                map.put("id", checkinLate.getId());
                map.put("startTime", checkinLate.getCheckTime().getTime() / 60000);
                map.put("endTime", checkinLate.getCheckTime().getTime() / 60000);
                map.put("userId", otherUserId);
                map.put("type", 10);
                map.put("edit", 0);
                finalList.add(map);
            }
        }
        if (StringUtil.isBlank(showType) || showType.equals("1")) {


            logger.info("params" + JsonUtil.toJsonString(params));

            List<FaceCheckinOut> faceCheckinOuts = faceCheckinOutService.listByParams(params);
            logger.info("faceCheckinOuts" + faceCheckinOuts.size());

            for (FaceCheckinOut faceCheckinOut : faceCheckinOuts) {
                HashMap map = new HashMap();
                map.put("isdel", false);
                map.put("title", "摄像头打卡" + DateUtil.toDateStr(new Date(faceCheckinOut.getCheckTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
                map.put("id", faceCheckinOut.getId());
                map.put("startTime", faceCheckinOut.getCheckTime().getTime() / 60000);
                map.put("endTime", faceCheckinOut.getCheckTime().getTime() / 60000);
                map.put("userId", otherUserId);
                map.put("type", 9);
                map.put("edit", 0);
                finalList.add(map);
            }
        }
        return ResultUtil.getResult(finalList, page);
    }

    //
//    /**
//     * 说明:ajax请求CheckinOut信息 无分页版本
//     *
//     * @return ResultDTO 返回结果
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @RequestMapping(value = "/listAll.json")
//    @ResponseBody
//    public ResultDTO listAll(HttpServletRequest request) {
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            params.put("id", id);
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            params.put("userId", userId);
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            params.put("checkType", checkType);
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                params.put("checkTime", checkTime);
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeBegin = request.getParameter("checkTimeBegin");
//        if (!StringUtil.isBlank(checkTimeBegin)) {
//            if (StringUtil.checkNumeric(checkTimeBegin)) {
//                params.put("checkTimeBegin", checkTimeBegin);
//            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeEnd = request.getParameter("checkTimeEnd");
//        if (!StringUtil.isBlank(checkTimeEnd)) {
//            if (StringUtil.checkNumeric(checkTimeEnd)) {
//                params.put("checkTimeEnd", checkTimeEnd);
//            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        List<CheckinOut> checkinOuts = checkinOutService.listByParams(params);
//        return ResultUtil.getDataResult(checkinOuts);
//    }
//
//    /**
//     * 说明:查看单条信息
//     *
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "根据id查询单个考勤信息",
//            description = "根据id查询单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "id", in = InType.path, dataType = DataType.LONG, required = true),
//            })
//    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO getById(@PathVariable Long id, HttpServletRequest request) {
//        HashMap<String, Object> result = new HashMap</*String,Object*/>();
//        if (id > 0) {
//            CheckinOut bean = checkinOutService.selectByPrimaryKey(Long.valueOf(id));
//            result.put("bean", bean);
//        }
//        return this.getResult(result);
//
//    }
//
//
//    /**
//     * 说明:查看单条信息
//     *
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "根据id查询单个考勤信息",
//            description = "根据id查询单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
//            })
//    @RequestMapping(value = "/view.json", method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO getById(HttpServletRequest request) {
//        String id = request.getParameter("id");
//        CheckinOut bean = checkinOutService.selectByPrimaryKey(Long.valueOf(id));
//        //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
//        // result.put("bean", bean);
//        return this.getResult(bean);
//    }
//
//
//    /**
//     * 说明:更新CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "更新id更新单个考勤信息",
//            description = "更新id更新单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", type = "LONG", required = false),
//                    @Param(name = "userId", description = "用户Id", type = "LONG", required = true),
//                    @Param(name = "checkType", description = "考勤类型", type = "INTEGER", required = true),
//                    @Param(name = "checkTime", description = "创建时间", type = "DATE_TIME", required = false),
//            })
//    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//    @RequestMapping(value = "update.json", method = RequestMethod.PUT)///{id}
//    @ResponseBody
//    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
//        CheckinOut checkinOut = new CheckinOut();
//        /*
//        String id = request.getParameter("id");
//        if(!StringUtil.isBlank(id)){
//            checkinOut.setId(Long.valueOf(id)) ;
//        }
//
//        String userId = request.getParameter("userId");
//        if(!StringUtil.isBlank(userId)){
//            checkinOut.setUserId(Long.valueOf(userId)) ;
//        }
//
//        String checkType = request.getParameter("checkType");
//        if(!StringUtil.isBlank(checkType)){
//            checkinOut.setCheckType(Integer.valueOf(checkType)) ;
//        }
//
//        String checkTime = request.getParameter("checkTime");
//        if(!StringUtil.isBlank(checkTime)){
//            checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
//        }
//        */
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            checkinOut.setId(Long.valueOf(id));
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            checkinOut.setUserId(Long.valueOf(userId));
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            checkinOut.setCheckType(Integer.valueOf(checkType));
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                checkinOut.setCheckTime(Timestamp.valueOf(checkTime));
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                checkinOut.setCheckTime(new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr = "";
//        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
//        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
//        vu.add("checkType", checkType, "考勤类型", new Rule[]{new Digits(3, 0), new NotEmpty()});
//        vu.add("checkTime", checkTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if (StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302, validStr);
//        }
//
//        return checkinOutService.save(checkinOut);
//
//    }
//
//
//    /**
//     * 说明:添加CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
//    @API(summary = "添加单个考勤信息",
//            description = "添加单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
//                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
//                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = true),
//                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
//            })
//    @RequestMapping(value = "add.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultDTO add(HttpServletRequest request) throws Exception {
//        CheckinOut checkinOut = new CheckinOut();
//            /*
//            String id = request.getParameter("id");
//            if(!StringUtil.isBlank(id)){
//                checkinOut.setId(Long.valueOf(id)) ;
//            }
//
//            String userId = request.getParameter("userId");
//            if(!StringUtil.isBlank(userId)){
//                checkinOut.setUserId(Long.valueOf(userId)) ;
//            }
//
//            String checkType = request.getParameter("checkType");
//            if(!StringUtil.isBlank(checkType)){
//                checkinOut.setCheckType(Integer.valueOf(checkType)) ;
//            }
//
//            String checkTime = request.getParameter("checkTime");
//            if(!StringUtil.isBlank(checkTime)){
//                checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
//            }
//            */
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            checkinOut.setId(Long.valueOf(id));
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            checkinOut.setUserId(Long.valueOf(userId));
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            checkinOut.setCheckType(Integer.valueOf(checkType));
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                checkinOut.setCheckTime(Timestamp.valueOf(checkTime));
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                checkinOut.setCheckTime(new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr = "";
//        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
//        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
//        vu.add("checkType", checkType, "考勤类型", new Rule[]{new Digits(3, 0), new NotEmpty()});
//        vu.add("checkTime", checkTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if (StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302, validStr);
//        }
//
//        return checkinOutService.save(checkinOut);
//
//    }
//
//
//    /**
//     * 说明:添加CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//    @API(summary = "添加单个考勤信息",
//            description = "添加单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
//                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
//                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = true),
//                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
//            })
//    @RequestMapping(value = "save.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultDTO save(HttpServletRequest request) throws Exception {
//        CheckinOut checkinOut = new CheckinOut();
//                    /*
//                    String id = request.getParameter("id");
//                    if(!StringUtil.isBlank(id)){
//                        checkinOut.setId(Long.valueOf(id)) ;
//                    }
//
//                    String userId = request.getParameter("userId");
//                    if(!StringUtil.isBlank(userId)){
//                        checkinOut.setUserId(Long.valueOf(userId)) ;
//                    }
//
//                    String checkType = request.getParameter("checkType");
//                    if(!StringUtil.isBlank(checkType)){
//                        checkinOut.setCheckType(Integer.valueOf(checkType)) ;
//                    }
//
//                    String checkTime = request.getParameter("checkTime");
//                    if(!StringUtil.isBlank(checkTime)){
//                        checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
//                    }
//                    */
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            checkinOut.setId(Long.valueOf(id));
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            checkinOut.setUserId(Long.valueOf(userId));
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            checkinOut.setCheckType(Integer.valueOf(checkType));
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                checkinOut.setCheckTime(Timestamp.valueOf(checkTime));
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                checkinOut.setCheckTime(new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        //valid
//        ValidateUtil vu = new ValidateUtil();
//        String validStr = "";
//        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
//        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
//        vu.add("checkType", checkType, "考勤类型", new Rule[]{new Digits(3, 0), new NotEmpty()});
//        vu.add("checkTime", checkTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
//        validStr = vu.validateString();
//        if (StringUtil.isNotBlank(validStr)) {
//            return ResultUtil.getResult(302, validStr);
//        }
//
//        return checkinOutService.save(checkinOut);
//
//    }
//
//    /**
//     * 说明:删除CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "根据id删除单个考勤信息",
//            description = "根据id删除单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = true),
//            })
//    @RequestMapping(value = "/delete.json", method = RequestMethod.DELETE)//{id}
//    @ResponseBody
//    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
//        String idStr = request.getParameter("id");
//        if (StringUtil.isBlank(idStr)) {
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        Long id = Long.valueOf(idStr);
//        checkinOutService.delete(id);
//        return this.getResult(SUCC);
//    }
//
//    /**
//     * 说明:删除CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @API(summary = "根据id删除单个考勤信息",
//            description = "根据id删除单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", in = InType.path, dataType = DataType.LONG, required = true),
//            })
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
//    @ResponseBody
//    public ResultDTO delete(@PathVariable Long id, HttpServletRequest request) {
//        checkinOutService.delete(id);
//        return this.getResult(SUCC);
//    }
//
//    /**
//     * 多行删除
//     *
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/mdel.json")
//    @ResponseBody
//    public ResultDTO multiDelete(HttpServletRequest request) {
//        String idStr = request.getParameter("ids");
//        if (StringUtil.isBlank(idStr)) {
//            return this.getWrongResultFromCfg("err.param.notnull");
//        }
//        String idStrAry[] = idStr.split(",");
//        Long idAry[] = new Long[idStrAry.length];
//        for (int i = 0, length = idAry.length; i < length; i++) {
//            ValidateUtil vu = new ValidateUtil();
//            String validStr = "";
//            String id = idStrAry[i];
//            vu.add("id", id, "编号", new Rule[]{});
//
//            try {
//                validStr = vu.validateString();
//            } catch (Exception e) {
//                e.printStackTrace();
//                validStr = "验证程序异常";
//                return ResultUtil.getResult(302, validStr);
//            }
//
//            if (StringUtil.isNotBlank(validStr)) {
//                return ResultUtil.getResult(302, validStr);
//            }
//            idAry[i] = Long.valueOf(idStrAry[i]);
//        }
//        return checkinOutService.multilDelete(idAry);
//    }
//
//    /**
//     * 导出
//     *
//     * @param request
//     * @return
//     * @author dozen.zhang
//     */
//    @RequestMapping(value = "/export.json")
//    @ResponseBody
//    public ResultDTO exportExcel(HttpServletRequest request) {
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        String id = request.getParameter("id");
//        if (!StringUtil.isBlank(id)) {
//            params.put("id", id);
//        }
//        String userId = request.getParameter("userId");
//        if (!StringUtil.isBlank(userId)) {
//            params.put("userId", userId);
//        }
//        String checkType = request.getParameter("checkType");
//        if (!StringUtil.isBlank(checkType)) {
//            params.put("checkType", checkType);
//        }
//        String checkTime = request.getParameter("checkTime");
//        if (!StringUtil.isBlank(checkTime)) {
//            if (StringUtil.checkNumeric(checkTime)) {
//                params.put("checkTime", checkTime);
//            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeBegin = request.getParameter("checkTimeBegin");
//        if (!StringUtil.isBlank(checkTimeBegin)) {
//            if (StringUtil.checkNumeric(checkTimeBegin)) {
//                params.put("checkTimeBegin", checkTimeBegin);
//            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String checkTimeEnd = request.getParameter("checkTimeEnd");
//        if (!StringUtil.isBlank(checkTimeEnd)) {
//            if (StringUtil.checkNumeric(checkTimeEnd)) {
//                params.put("checkTimeEnd", checkTimeEnd);
//            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
//                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//        // 查询list集合
//        List<CheckinOut> list = checkinOutService.listByParams(params);
//        // 存放临时文件
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "list.xlsx");
//        String folder = request.getSession().getServletContext()
//                .getRealPath("/")
//                + "xlstmp";
//        File folder_file = new File(folder);
//        if (!folder_file.exists()) {
//            folder_file.mkdir();
//        }
//        String fileName = folder + File.separator
//                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
//                + ".xlsx";
//        // 得到导出Excle时清单的英中文map
//        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
//        colTitle.put("id", "编号");
//        colTitle.put("userId", "用户Id");
//        colTitle.put("checkType", "考勤类型");
//        colTitle.put("checkTime", "创建时间");
//        List<Map> finalList = new ArrayList<Map>();
//        for (int i = 0; i < list.size(); i++) {
//            CheckinOut sm = list.get(i);
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("id", list.get(i).getId());
//            map.put("userId", list.get(i).getUserId());
//            map.put("checkType", list.get(i).getCheckType());
//            map.put("checkTime", list.get(i).getCheckTime());
//            finalList.add(map);
//        }
//        try {
//            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                return this.getResult(SUCC, fileName, "导出成功");
//            }
//            /*
//             * return new ResponseEntity<byte[]>(
//             * FileUtils.readFileToByteArray(new File(fileName)), headers,
//             * HttpStatus.CREATED);
//             */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this.getResult(0, "数据为空，导出失败");
//
//    }
//
//    @API(summary = "批量导入信息",
//            description = "批量导入信息",
//            consumes = "multipart/form-data",
//            parameters = {
//                    @Param(name = "file", description = "编号", in = InType.form, dataType = DataType.FILE, required = true),
//            })
//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultDTO importExcel(@RequestParam("file") MultipartFile file) {
//        // 将spring 的file 装成 普通file
//        File xlsFile = null;
//        if (file != null) {
//            try {
//                String fileName = System.currentTimeMillis() + ".xls";//取一个随机的名称
//                String s = PathManager.getInstance().getTmpPath().resolve(fileName).toString();//存入tmp文件夹
//                Files.copy(file.getInputStream(), PathManager.getInstance().getTmpPath().resolve(fileName));//存到本地
//                xlsFile = PathManager.getInstance().getTmpPath().resolve(fileName).toFile();//读取
//            } catch (Exception e) {
//                logger.error("文件上传出错", e);
//                throw new BizException("E041412312", "文件上传出错");
//            }
//        }
//        String result = "";
//        try {
//
//            // 将导入的中文列名匹配至数据库对应字段
//            int success = 0;
//            int fail = 0;
//            StringBuffer errorMsg = new StringBuffer();//如果某行报错了 需要告知哪一行错误
//            //            Map<String, String> colMatch = new HashMap<String, String>();
//            //            colMatch.put("姓名", "name");
//            //            colMatch.put("单位", "org");
//            //            colMatch.put("邮箱", "email");
//
//
//            List<Map<String, String>> list = ExcelUtil.getExcelData(xlsFile);//excel 转成 list数据
//            for (int i = 0; i < list.size(); i++) {
//
//                Map<String, String> map = list.get(i);
//                String email = MapUtils.getStringValue(map, "邮箱");
//                // 检验手机号是否符合规范,不符合continue
//                if (!StringUtil.isEmail(email)) {
//                    //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
//                    logger.info(" import conf ==> the telphone:" + email + " is not email");
//                    fail++;
//                    errorMsg.append("" + email + " 不是邮箱地址;");
//                    continue;
//                }
//                HashMap params = new HashMap();
//                params.put("email", email);
//                //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在
//
//                CheckinOut bean = getInfoFromMap(params);
//
//                //  if (count > 0) {
//
//                //      logger.warn("邮箱已经存在:" + email);
//                //     errorMsg.append("邮箱已经存在:" + email);
//                //   continue;
//
//                // }
//
//                try {
//                    checkinOutService.save(bean);
//                    success++;//成功数增加
//                } catch (Exception e) {
//
//                    fail++;//失败数增加
//                    logger.info("packageservice import conf ==> update fail ==>the telphone:" + email + "", e);
//                    errorMsg.append("the telphone:" + email + " update fail;");
//                }
//
//            }
//            if (StringUtil.isNotBlank(errorMsg.toString()) && fail > 0) {
//                throw new BizException("E2000016", "导入失败, 失败" + fail + "条。" + errorMsg.toString());
//            }
//            return this.getResult(3090182, "导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("导入失败", e);
//            if (e instanceof org.apache.poi.poifs.filesystem.OfficeXmlFileException) {
//                throw new BizException("E041412313", "导入的excel需为2003版本");
//            } else {
//                throw new BizException("E041412313", e.getMessage());
//            }
//        }
//
//
//    }
//
//
//    /**
//     * 说明: 跳转到CheckinOut列表页面
//     *
//     * @return String
//     * @author dozen.zhang
//     * @date 2015年11月15日下午12:30:45
//     */
//    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
//    public String listHtml() {
//        return "/static/html/CheckinOutList.html";
//    }
//
//    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
//    public String listMapperHtml() {
//        return "/static/html/CheckinOutListMapper.html";
//    }
//
//
//    /**
//     * 说明:跳转编辑页面
//     *
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
//    public String editHtml(HttpServletRequest request) {
//        // 查找所有的角色
//        return "/static/html/CheckinOutEdit.html";
//    }
//
//    /**
//     * 说明:跳转查看页面
//     *
//     * @param request 发请求
//     * @return String
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
//    public String viewHtml(HttpServletRequest request) {
//        return "/static/html/CheckinOutView.html";
//    }
//
//
    private CheckinOut getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        CheckinOut checkinOut = new CheckinOut();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            checkinOut.setId(Long.valueOf(id));
        }
        String userId = MapUtils.getString(bodyParam, "userId");
        if (!StringUtil.isBlank(userId)) {
            checkinOut.setUserId(Long.valueOf(userId));
        }
        String checkType = MapUtils.getString(bodyParam, "checkType");
        if (!StringUtil.isBlank(checkType)) {
            checkinOut.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = MapUtils.getString(bodyParam, "checkTime");
        if (!StringUtil.isBlank(checkTime)) {
            if (StringUtil.checkNumeric(checkTime)) {
                checkinOut.setCheckTime(Timestamp.valueOf(checkTime));
            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
                checkinOut.setCheckTime(new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "编号", new Rule[]{new Digits(15, 0)});
        vu.add("userId", userId, "用户Id", new Rule[]{new Digits(13, 0), new NotEmpty()});
        vu.add("checkType", checkType, "考勤类型", new Rule[]{new Digits(3, 0), new NotEmpty()});
        vu.add("checkTime", checkTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return checkinOut;
    }
//
//
//    /**
//     * 说明:添加CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//    @API(summary = "添加单个考勤信息",
//            description = "添加单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
//                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
//                    @Param(name = "checkType", description = "考勤类型", in = InType.body, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "checkTime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//            })
//    @RequestMapping(value = "add", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
//        CheckinOut checkinOut = getInfoFromMap(bodyParam);
//
//
//        return checkinOutService.save(checkinOut);
//
//    }
//
//
//    /**
//     * 说明:添加CheckinOut信息
//     *
//     * @param request
//     * @return ResultDTO
//     * @throws Exception
//     * @author dozen.zhang
//     * @date 2018-11-5 20:09:43
//     */
//    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//    @API(summary = "更新单个考勤信息",
//            description = "更新单个考勤信息",
//            parameters = {
//                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
//                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
//                    @Param(name = "checkType", description = "考勤类型", in = InType.body, dataType = DataType.INTEGER, required = true),
//                    @Param(name = "checkTime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
//            })
//    @RequestMapping(value = "update", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
//        CheckinOut checkinOut = getInfoFromMap(bodyParam);
//        return checkinOutService.save(checkinOut);
//
//    }

    /**
     * 说明:ajax请求CheckinOut信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "考勤列表接口",
            description = "考勤列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", in = InType.params, dataType = DataType.LONG, required = false),// true
                    @Param(name = "checkType", description = "考勤类型", in = InType.params, dataType = DataType.INTEGER, required = false),// true
                    @Param(name = "checkTime", description = "创建时间", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) throws Exception {

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
        Page page = RequestUtil.getPage(params);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        String id = MapUtils.getString(params, "id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String checkType = MapUtils.getString(params, "checkType");
        if (!StringUtil.isBlank(checkType)) {
            params.put("checkType", checkType);
        }
        String checkTime = MapUtils.getString(params, "checkTime");
        if (!StringUtil.isBlank(checkTime)) {
            if (StringUtil.checkNumeric(checkTime)) {
                params.put("checkTime", checkTime);
            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTime", checkTime);
            }
        }
        String checkTimeBegin = MapUtils.getString(params, "checkTimeBegin");
        if (!StringUtil.isBlank(checkTimeBegin)) {
            if (StringUtil.checkNumeric(checkTimeBegin)) {
                params.put("checkTimeBegin", checkTimeBegin);
            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeBegin", checkTimeBegin);
            }
        }
        String checkTimeEnd = MapUtils.getString(params, "checkTimeEnd");
        if (!StringUtil.isBlank(checkTimeEnd)) {
            if (StringUtil.checkNumeric(checkTimeEnd)) {
                params.put("checkTimeEnd", checkTimeEnd);
            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeEnd", checkTimeEnd);
            }
        }

        params.put("page", page);
        List<CheckinOut> checkinOuts = checkinOutService.listByParams4Page(params);
        return ResultUtil.getResult(checkinOuts, page);
    }


    /**
     * 导出
     *
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary = "考勤列表导出接口",
            description = "考勤列表导出接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "userId", description = "用户Id", in = InType.params, dataType = DataType.LONG, required = false),// true
                    @Param(name = "checkType", description = "考勤类型", in = InType.params, dataType = DataType.INTEGER, required = false),// true
                    @Param(name = "checkTime", description = "创建时间", in = InType.params, dataType = DataType.DATE_TIME, required = false),// false
            })
    @RequestMapping(value = "/export")
    @ResponseBody
    public ResultDTO exportExcelInBody(HttpServletRequest request, @RequestParam(name = "params", required = true) String paramStr) throws Exception {

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
        Page page = RequestUtil.getPage(params);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        String id = MapUtils.getString(params, "id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String checkType = MapUtils.getString(params, "checkType");
        if (!StringUtil.isBlank(checkType)) {
            params.put("checkType", checkType);
        }
        String checkTime = MapUtils.getString(params, "checkTime");
        if (!StringUtil.isBlank(checkTime)) {
            if (StringUtil.checkNumeric(checkTime)) {
                params.put("checkTime", checkTime);
            } else if (StringUtil.checkDateStr(checkTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTime", new Timestamp(DateUtil.parseToDate(checkTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeBegin = MapUtils.getString(params, "checkTimeBegin");
        if (!StringUtil.isBlank(checkTimeBegin)) {
            if (StringUtil.checkNumeric(checkTimeBegin)) {
                params.put("checkTimeBegin", checkTimeBegin);
            } else if (StringUtil.checkDateStr(checkTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeBegin", new Timestamp(DateUtil.parseToDate(checkTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String checkTimeEnd = MapUtils.getString(params, "checkTimeEnd");
        if (!StringUtil.isBlank(checkTimeEnd)) {
            if (StringUtil.checkNumeric(checkTimeEnd)) {
                params.put("checkTimeEnd", checkTimeEnd);
            } else if (StringUtil.checkDateStr(checkTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("checkTimeEnd", new Timestamp(DateUtil.parseToDate(checkTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<CheckinOut> list = checkinOutService.listByParams4Page(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS") + ".xlsx";

        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";


        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + randomName;
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "编号");
        colTitle.put("userId", "用户Id");
        colTitle.put("checkType", "考勤类型");
        colTitle.put("checkTime", "创建时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            CheckinOut sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("userId", list.get(i).getUserId());
            map.put("checkType", list.get(i).getCheckType());
            map.put("checkTime", list.get(i).getCheckTime());
            finalList.add(map);
        }
        try {
            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, SysConfig.PATH + "/xlstmp/" + randomName, "导出成功");
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


    @API(summary = "提醒",
            description = "提醒",
            consumes = "multipart/form-data",
            parameters = {
            })
    @RequestMapping(value = "/remind", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO remind(HttpServletRequest request) {
        // 将spring 的file 装成 普通file
        //查找到数据
        String timeStr = "08:28-06:00-08:31,12:18-11:50-12:21,13:50-13:20-13:51,18:30-17:30-18:30";
        String[] timeStrAry = timeStr.split(",");

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String begin = null;
        String end = null;
        //TODO delete
        String date = DateUtil.getTodayDate();
        for (int i = 0; i < timeStrAry.length; i++) {
            String hourAndMin[] = timeStrAry[i].split("-")[0].split(":");
            if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {//正好在这个点上面 就进行验证

            } else {//否则到下个点去校验
                //TODO delete
                //continue;
            }
            begin = timeStrAry[i].split("-")[1];
            end = timeStrAry[i].split("-")[2];
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]), Integer.valueOf(end.split(":")[0]), Integer.valueOf(end.split(":")[1]));
//                calendar1.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hourAndMin[0]));
//                calendar1.set(Calendar.MINUTE,Integer.valueOf(hourAndMin[1]));
            if (calendar1.getTimeInMillis() < System.currentTimeMillis()) {//
                //TODO delete
                //break;
            }
            if (DateUtil.parseToDate(date + " " + end + ":00", "yyyy-MM-dd HH:mm:ss").getTime() > System.currentTimeMillis()) {
                //TODO delete
                // break;
            }

            List<SysUser> sysUsers = checkinOutService.listUsersNotCheckIn(date + " " + end + ":00", date + " " + end + ":00");

            //break;
            //  } else {
            //  }
        }

        //3个时间段
        //判断现在在那个时间段
        //然后根据当前时间段 查找有哪些人还没有考勤过的

        //然后给这些人发送消息 消息入栈
        return this.getResult();

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


                Long userId = MapUtils.getLong(map, "userId");
                //String checkTime = MapUtils.getString(map,"checkTime");
                // 检验手机号是否符合规范,不符合continue
//                if (!StringUtil.isEmail(email)) {
//                    //                    throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", telphone));// 手机号码不符合一般格式。
//                    logger.info(" import conf ==> the telphone:" + email + " is not email");
//                    fail++;
//                    errorMsg.append("" + email + " 不是邮箱地址;");
//                    continue;
//                }


                //  int count = contactsService.countByParams(params);//检查邮箱地址是否存在


                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    CheckinOut bean = getInfoFromMap(map);
                    //查看这个outId是否有了

                    if (bean.getUserId() == null)
                        continue;


                    HashMap confilicMap = new HashMap();
                    confilicMap.put("userId", bean.getUserId());
                    confilicMap.put("checkTime", bean.getCheckTime());
                    List<CheckinOut> checkOuts = checkinOutService.listByParams(confilicMap);
                    if (checkOuts != null && checkOuts.size() > 0) {
                        //已经保存过了
                        //  logger.info("发现重复数据");
                        continue;
                    }
                    //  logger.info("");

                    checkinOutService.save(bean);

                    SysUser sysUser = sysUserService.getUserByOutId(bean.getUserId());
                    //根据userid 获得username
                    if (sysUser != null) {

                        //if (StringUtil.isNotBlank(sysUser.getWechat())) {
                        //  RedisUtil.lpush("checkinList", sysUser.getWechat() + " 恭喜你成功打卡" + DateUtil.toDateStr(new Date(bean.getCheckTime().getTime()), "yyyy-MM-dd-HH:mm:ss"));

                        //} else {
                        RedisUtil.lpush("checkinList", sysUser.getUsername() + " 打卡成功" + DateUtil.toDateStr(new Date(bean.getCheckTime().getTime()), "yyyy-MM-dd-HH:mm:ss"));

                        // }

                        logger.info("打卡者:" + sysUser.getUsername());
                    }
                    success++;//成功数增加
                } catch (Exception e) {

                    fail++;//失败数增加
                    logger.info("packageservice import conf ==> update fail ==>the telphone:" + userId + "", e);
                    errorMsg.append("the userId:" + userId + " update fail;");
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


    @API(summary = "迟到用户提醒",
            description = "迟到用户提醒",
            consumes = "multipart/form-data",
            parameters = {
                    @Param(name = "params", description = "用户列表json字符串", in = InType.form, dataType = DataType.FILE, required = true),
            })
    @RequestMapping(value = "/late", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO late(HttpServletRequest request) {
        // 将spring 的file 装成 普通file
        //通过接口 让客户端查询access 库 查询出本次迟到人员推送微信消息
        String result = request.getParameter("params");
        result = result.replace(" ", ",");
        if (StringUtil.isNotBlank(result)) {
            RedisUtil.lpush("checkinList", "王作品 即将迟到" + result);

        }

        return this.getResult();

    }


    @API(summary = "根据开始时间结束时间查询考情记录",
            description = "根据开始时间结束时间查询考情记录",
            consumes = "multipart/form-data",
            parameters = {
                    @Param(name = "start", description = "用户列表json字符串", in = InType.form, dataType = DataType.FILE, required = true),
                    @Param(name = "end", description = "用户列表json字符串", in = InType.form, dataType = DataType.FILE, required = true),
            })
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    @RequiresLogin // 需要用户登录后才能使用
    public ResultDTO report(HttpServletRequest request) {
        // 将spring 的file 装成 普通file
        SessionUser user = this.getUser(request);
        SysUser sysUser = sysUserService.getUserById(user.getUserId());

        String start = request.getParameter("start") + " 00:00:00";
        ;
        String end = request.getParameter("end") + " 23:59:59";

        HashMap map = new HashMap();
        map.put("checkTimeBegin", start);
        map.put("checkTimeEnd", end);
        map.put("userId", sysUser.getOutId());

        List<CheckinOut> checkinOuts = this.checkinOutService.listByParams(map);

        //进行日期项目合并
        List<DateList> calendarList = new ArrayList<DateList>();
        DateList dateList = new DateList();
        String date = "";
        Calendar calendar = Calendar.getInstance();


        for (int i = 0; i < checkinOuts.size(); i++) {
            CheckinOut checkinOut = checkinOuts.get(i);
            Timestamp timestamp = checkinOut.getCheckTime();

            calendar.setTimeInMillis(timestamp.getTime());
            String checkDate = DateUtil.formatToString(calendar.getTime(), "yyyy-MM-dd");
            if (checkDate.equals(date)) {

            } else {
                dateList = new DateList();
                calendarList.add(dateList);
            }
            dateList.list.add("打卡" + DateUtil.formatToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss "));
        }


        String jsonStr = JsonUtil.toJson(calendarList);

        return this.getResult(calendarList);

    }

    class DateList {
        public String date;
        public List<String> list = new ArrayList<>();
    }

    public static void main(String args[]) {
        HashMap map = new HashMap();
        map.put("image", URLEncoder.encode(ImageUtil.ImageToBase64ByLocal("g://wangzuoping3.jpg")));
        map.put("maxFaceNum", "11");
        map.put("faceFields", "embedding");
        map.put("filename", "wangzuoping.jpg");
        String result = HttpRequestUtil.sendPost("http://127.0.0.1:8080/atomsrv/face/recog/face", map);
        ResultDTO resultDTO = JsonUtil.toJavaBean(result, ResultDTO.class);
        JSONObject jsonObject = (JSONObject) resultDTO.getData();
        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);


        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
            FinishTask finishTask = finishTaskData.getResult().get(i);
            if (finishTask.getLocation().getWidth() < 100) continue;
            Double[] thisMan = finishTask.getEmbedding();

            double sum = 0;
            double[] wangzuoping = new double[]{-0.008701, 0.148611, 0.058275, -0.08266, -0.079701, 0.160656, 0.064619, -0.017223, 0.017382, -0.048058, 0.180767, 0.124326, 0.067978, -0.063315, -0.016848, 0.075297, -0.02827, 0.008627, 0.088819, -0.056016, 0.051633, -0.082347, 0.080371, 0.06307, 0.081415, 0.011967, -0.001232, -0.166948, 0.007133, -0.106017, 0.048015, 0.207153, -0.148783, -0.099527, 0.067234, -0.021443, -0.000009, 0.186995, 0.085177, 0.042677, -0.005818, 0.195454, 0.22936, 0.076654, 0.042842, -0.011548, 0.104181, -0.028195, -0.0386, -0.203294, -0.020634, 0.148309, -0.048626, 0.023338, 0.036419, -0.102889, -0.023942, 0.068996, -0.031568, 0.035257, -0.089888, 0.102456, 0.054087, 0.076793, 0.133463, -0.017299, -0.077482, -0.035074, 0.019985, -0.03882, -0.01904, 0.044068, 0.004656, -0.088485, -0.13872, -0.049296, 0.004236, -0.119121, 0.170487, -0.000908, -0.088348, 0.023045, 0.017032, 0.010858, -0.079928, 0.07815, 0.095446, 0.10186, 0.036595, -0.096792, 0.052386, 0.246397, 0.004021, -0.115781, 0.038569, 0.071648, -0.094515, -0.095816, 0.031187, 0.012754, 0.000567, -0.15687, -0.063205, 0.013722, 0.020087, 0.131345, 0.103935, -0.082715, -0.071745, 0.074268, 0.104169, 0.011771, -0.091282, -0.067506, 0.027194, 0.058555, 0.034997, 0.003118, -0.066417, -0.079658, -0.21855, -0.068168, -0.015519, -0.044153, 0.058829, -0.002689, -0.07084, -0.043101};


            for (int j = 0; j < thisMan.length; j++) {
                sum += wangzuoping[j] * thisMan[j];
            }
            System.out.println(sum);
        }

        // System.out.println(result);

        //遍历结果 获取width 大的那张然后和数据库去比对
        //先写死一个


    }


//    @RequestMapping(value = "/notifyLate")
//    @ResponseBody
//@PostConstruct
    //@Scheduled(cron = "0 */1 * * * ?")
//    @Scheduled(cron = "0 22 12 * * ?")
//    @Scheduled(cron = "0 32 8 * * ?")
//    @Scheduled(cron = "0 51 13 * * ?")
//    @Scheduled(cron = "0 0 10 * * ?")
    //=08:30-06:00-08:31,12:18-11:50-12:21,13:50-13:20-13:51,18:30-17:30-18:30
//    @PostConstruct
//    public ResultDTO notifyLateTickTack() throws Exception {
//        //创建scheduler
//          Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        //定义一个Trigger
//
//        Properties properties  =  PropertiesUtil.load( PathManager.getInstance().getClassPath().resolve("main.properties").toFile());
//        String timeStr= properties.getProperty("late.check.time");
//        String[] timeSecAry = timeStr.split(",");
//        for(int i=0;i<timeSecAry.length;i++){
//            String timeSec=timeSecAry[i];
//            if(StringUtil.isBlank(timeSec)){
//                continue;
//            }
//            String[] shijianFanWeiAry = timeSec.split("-");
//            if(shijianFanWeiAry.length<=2){
//                logger.error("late.check.time is error format"+ timeStr);
//            }
//
//            String[] time = shijianFanWeiAry[0].split(":");
//
//            JobDetail job = newJob(NotifyLate.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
//                    .withIdentity("job"+i, "group1") //定义name/group
//                    .usingJobData("time", timeSec) //定义属性
//                    .usingJobData("begin", shijianFanWeiAry[1]) //定义属性
//                    .usingJobData("end", shijianFanWeiAry[2]) //定义属性
//                    .build();
//
//
//            //创建一trigger
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_"+i, "group_2")
//                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 "+ Integer.valueOf(time[2])+" "+Integer.valueOf(time[1])+" * * ?")).build();
//
//
//            scheduler.scheduleJob(job, trigger);
//        }
//        return  this.getResult();
////
////        Calendar calendar = Calendar.getInstance();
////        String timeStrAry[] = ConfigUtil.getConfig("late.check.time").split(",");
////        int hour = calendar.get(Calendar.HOUR_OF_DAY);
////        if(hour = )
////        logger.info("开始查询" + date + " " + begin + " " + end + "");
////
////        return this.getResult();
//    }


    @API(summary = "kq03 通知迟到人员(每天四个点)",
            description = "",
            parameters = {
            })


    @Scheduled(cron = "0 */1 * * * ?")
    public ResultDTO notifyLate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        String timeStrAry[] = ConfigUtil.getConfig("kq.late.check.time").split(",");
        logger.info("PushByChidaoThread run");
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            // return null;//如果是双休日不提醒

        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String begin = null;
        String end = null;
        String noCheckinTime = null;
        //TODO delete
        String date = DateUtil.getTodayDate();
        for (int i = 0; i < timeStrAry.length; i++) {
            String hourAndMin[] = timeStrAry[i].split("-")[0].split(":");
            if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {//正好在这个点上面 就进行验证
                logger.error("it's time to check late  " + timeStrAry[i]);
            } else {//否则到下个点去校验
                //TODO delete
                continue;
            }
            begin = timeStrAry[i].split("-")[1];//正常的考勤时间开始时间
            if (begin == null) {
                logger.error("error in begin ");
            }
            end = timeStrAry[i].split("-")[2];//正常的考勤时间结束时间
            if (begin == null) {
                logger.error("error in end ");
            }
            noCheckinTime = timeStrAry[i].split("-")[3];//未打卡的截止时间
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]), Integer.valueOf(end.split(":")[0]), Integer.valueOf(end.split(":")[1]));
            logger.info("开始查询" + date + " " + begin + " " + end + "");
            computeLate(date, begin, end, noCheckinTime);
        }

        return this.getResult();
    }


    @API(summary = "考勤列表接口",
            description = "考勤列表接口",
            parameters = {

                    @Param(name = "date", description = "日期 2019-9-18", dataType = DataType.STRING, required = false),// false

                    @Param(name = "time", description = "检查时间9:32-6:30-8:31-9:30(结算的时间点  -   正常的考勤时间开始时间  - 正常的考情时间结束  -   未打卡截止时间 )", dataType = DataType.DATE_TIME, required = true),// false
            })

    @RequestMapping(value = "/late/compute", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO lateCompute(HttpServletRequest request) throws Exception {
        String timeStrAry = request.getParameter("time");
        String hourAndMin[] = timeStrAry.split("-")[0].split(":");
//            if (hour == Integer.valueOf(hourAndMin[0]) && minute == Integer.valueOf(hourAndMin[1])) {//正好在这个点上面 就进行验证
//                logger.error("it's time to check late  " + timeStrAry);
//            } else {//否则到下个点去校验
//                //TODO delete
//                continue;
//            }
        String begin = timeStrAry.split("-")[1];//正常的考勤时间开始时间
        if (begin == null) {
            logger.error("error in begin ");
        }
        String end = timeStrAry.split("-")[2];//正常的考勤时间结束时间
        if (begin == null) {
            logger.error("error in end ");
        }
        String noCheckinTime = timeStrAry.split("-")[3];//未打卡的截止时间
        String date = request.getParameter("date");
        if (StringUtil.isBlank(date)) {
            date = DateUtil.getTodayDate();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]), Integer.valueOf(end.split(":")[0]), Integer.valueOf(end.split(":")[1]));
        logger.info("开始查询" + date + " " + begin + " " + end + "");
        computeLate(date, begin, end, noCheckinTime);
        return this.getResult();
    }

    /**
     * 计算这个人是 正常考勤 迟到 还是缺勤(忘记打卡)
     *
     * @param date
     * @param begin
     * @param end
     * @param now
     */
    public void computeLate(String date, String begin, String end, String now) {
        logger.info(date + " " + begin + " " + end);


        //==================查询整个事件段的考勤数据=======================
        // String dateStr = getTodayDate();
        // String dateStr = "2018-10-17";
        //===========纯粹的考勤记录检查迟到 如果要加上摄像头 考勤签到的话 需要加入 摄像头考勤数据监测==============
        List<SysUser> lateUserList = checkinOutService.listUsersNotCheckInMachineAndCamera(date + " " + begin + ":00", date + " " + end + ":00");
        List<SysUser> secondLateUserList = checkinOutService.listUsersNotCheckInMachineAndCamera(date + " " + end + ":00", date + " " + now + ":00");

        //给每个人发通知
        for (SysUser sysUser : lateUserList) {
            //插入一条迟到数据库记录

            //如果在一个小时内都还没有打卡 就是未打卡 否则是迟到
            boolean late = true;
            Long dakashijian =null;
            //去查她下一个时间段有没有打卡
            for (SysUser secondUser : secondLateUserList) {//遍历下一个时间未打卡的人员
                if (secondUser.getId() == sysUser.getId()) {//如果都没出现
                    //迟到

                    late = false;//说明这个人是未打卡
                    break;
                }
            }

            //去查 摄像头签到 这个人是否有记录
            try {
                //==========保存这个人的迟到记录
                CheckinLate checkinLate = new CheckinLate();
                checkinLate.setKqUserId(sysUser.getOutId());//考勤机用户id
                checkinLate.setCheckType(late ? 1 : 2);//1迟到 2未打卡
                checkinLate.setUserName(sysUser.getUsername());
                checkinLate.setUserId(sysUser.getId());//系统用户id
                checkinLate.setCheckTime(new Timestamp(new Date().getTime()));//DateUtil.parseToDate(date + " " + end + ":00", DateUtil.YYYY_MM_DD_HH_MM_SS).getTime()
                checkinLateService.save(checkinLate);//保存迟到记录


                //查询有没有迟到的记录
                //  List<SysUser > lateUserList = checkinOutService.listUsersNotCheckIn2(date + " " + begin + ":00", date + " " + begin + ":00");

                //发送迟到通知
                //    CheckinMessageService.sendMsg();


                if(sysUser.getUsername().equals("武俊英") && StringUtil.isNotBlank(sysUser.getEmail())){

                    logger.info("准备发送迟到 未打卡  消息" + sysUser.getUsername() + DateUtil.toDateStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_DASH) + (late ? "迟到" : "未打卡"));

                    emailSendService.sendEmail(sysUser.getEmail(),"考勤异常","【aWiFi】XXX您好，经智慧门禁系统检测，您在"+begin+" 到 " +end+ " 的打卡存在异常，请及时考勤打卡，如已打卡，敬请忽略。详情请关注微信公众号“爱WiFi运营中心”，进入员工之家进行查询");
                }
                //VirtualWeixinService.sendMsg(sysUser.getUsername(), DateUtil.toDateStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_DASH) + (late ? "迟到" : "未打卡"));//date + " " + begin + ":00" + "未打卡"
            } catch (Exception e) {
                e.printStackTrace();
            }
            //

        }

        //EmailUtil.send("371452875@qq.com", sb.toString());

        //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
        //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
    }
@Autowired
EmailSendService emailSendService;
    /**
     * 测试ai识别接口是否通畅
     *
     * @param request
     * @return
     */
    public ResultDTO testFaceInterface(HttpServletRequest request) {

        HashMap map = new HashMap();
        map.put("image", URLEncoder.encode(ImageUtil.ImageToBase64ByLocal("g://wangzuoping3.jpg")));
        map.put("maxFaceNum", "11");
        map.put("faceFields", "embedding");
        map.put("filename", "wangzuoping.jpg");
        String result = HttpRequestUtil.sendPost("http://127.0.0.1:8080/atomsrv/face/recog/face", map);
        ResultDTO resultDTO = JsonUtil.toJavaBean(result, ResultDTO.class);
        JSONObject jsonObject = (JSONObject) resultDTO.getData();
        FinishTaskData finishTaskData = JSON.toJavaObject(jsonObject, FinishTaskData.class);


        for (int i = 0; i < finishTaskData.getResult().size(); i++) {
            FinishTask finishTask = finishTaskData.getResult().get(i);
            if (finishTask.getLocation().getWidth() < 100) continue;
            Double[] thisMan = finishTask.getEmbedding();

            double sum = 0;
            double[] wangzuoping = new double[]{-0.008701, 0.148611, 0.058275, -0.08266, -0.079701, 0.160656, 0.064619, -0.017223, 0.017382, -0.048058, 0.180767, 0.124326, 0.067978, -0.063315, -0.016848, 0.075297, -0.02827, 0.008627, 0.088819, -0.056016, 0.051633, -0.082347, 0.080371, 0.06307, 0.081415, 0.011967, -0.001232, -0.166948, 0.007133, -0.106017, 0.048015, 0.207153, -0.148783, -0.099527, 0.067234, -0.021443, -0.000009, 0.186995, 0.085177, 0.042677, -0.005818, 0.195454, 0.22936, 0.076654, 0.042842, -0.011548, 0.104181, -0.028195, -0.0386, -0.203294, -0.020634, 0.148309, -0.048626, 0.023338, 0.036419, -0.102889, -0.023942, 0.068996, -0.031568, 0.035257, -0.089888, 0.102456, 0.054087, 0.076793, 0.133463, -0.017299, -0.077482, -0.035074, 0.019985, -0.03882, -0.01904, 0.044068, 0.004656, -0.088485, -0.13872, -0.049296, 0.004236, -0.119121, 0.170487, -0.000908, -0.088348, 0.023045, 0.017032, 0.010858, -0.079928, 0.07815, 0.095446, 0.10186, 0.036595, -0.096792, 0.052386, 0.246397, 0.004021, -0.115781, 0.038569, 0.071648, -0.094515, -0.095816, 0.031187, 0.012754, 0.000567, -0.15687, -0.063205, 0.013722, 0.020087, 0.131345, 0.103935, -0.082715, -0.071745, 0.074268, 0.104169, 0.011771, -0.091282, -0.067506, 0.027194, 0.058555, 0.034997, 0.003118, -0.066417, -0.079658, -0.21855, -0.068168, -0.015519, -0.044153, 0.058829, -0.002689, -0.07084, -0.043101};


            for (int j = 0; j < thisMan.length; j++) {
                sum += wangzuoping[j] * thisMan[j];
            }
            System.out.println(sum);
        }
        return this.getResult();

    }
//
//    public class NotifyLate implements  Job{
//
//        @Override
//        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//                Calendar calendar  =Calendar.getInstance();
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            String date = DateUtil.toDateStr(calendar.getTime(),DateUtil.YYYY_MM_DD);
//            String begin = jobExecutionContext.getJobDetail().getJobDataMap().getString("begin");
//            String end = jobExecutionContext.getJobDetail().getJobDataMap().getString("end");
//            this.process(date,begin,end,"");
//        }
//
//
//        public void process(String date, String begin, String end, String now) {
//            logger.info(date + " " + begin + " " + end);
//
//
//            //查询整个事件段的考勤数据
//            // String dateStr = getTodayDate();
//            // String dateStr = "2018-10-17";
//            //纯粹的考勤记录检查迟到 如果要加上摄像头 考勤签到的话 需要加入 摄像头考勤数据监测
//            List<SysUser> lateUser = checkinOutService.listUsersNotCheckIn2(date + " " + begin + ":00", date + " " + begin + ":00");
//
//            //给每个人发通知
//            for (SysUser sysUser : lateUser) {
//                //插入一条迟到数据库记录
//
//                //去查 摄像头签到 这个人是否有记录
//                try {
//
//                    CheckinLate checkinLate = new CheckinLate();
//                    checkinLate.setKqUserId(sysUser.getOutId());//考勤机用户id
//                    checkinLate.setCheckType(1);//1暂时无用
//                    checkinLate.setUserName(sysUser.getUsername());
//                    checkinLate.setUserId(sysUser.getId());//系统用户id
//                    checkinLate.setCheckTime(new Timestamp(DateUtil.parseToDate(date + " " + begin + ":00", DateUtil.YYYY_MM_DD_HH_MM_SS).getTime()));
//                    checkinLateService.save(checkinLate);//保存迟到记录
//
//                    //发送迟到通知
//                    //    CheckinMessageService.sendMsg();
//                    VirtualWeixinService.sendMsg(sysUser.getUsername(), date + " " + begin + ":00" + "未打卡");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                //
//
//            }
//
//            //EmailUtil.send("371452875@qq.com", sb.toString());
//
//            //readFileACCESS(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//            //getUserMap(new File("C:\\Users\\dozen.zhang\\Desktop/ATT2000.MDB"));
//        }
//    }
}
