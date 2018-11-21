/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-11-5 20:09:43
 * 文件说明:
 */

package com.dozenx.web.module.checkin.checkinOut.action;

import com.alibaba.fastjson.JSON;
import com.cpj.swagger.annotation.*;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.SysConfig;
import com.dozenx.core.exception.BizException;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.*;
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
import com.dozenx.web.module.checkin.checkinOut.bean.CheckinOut;
import com.dozenx.web.module.checkin.checkinOut.service.CheckinOutService;
import com.dozenx.web.util.RedisUtil;
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

@APIs(description = "考勤")
@Controller
@RequestMapping("/checkin")
public class CheckinOutController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(CheckinOutController.class);
    /** 权限service **/
    @Autowired
    private CheckinOutService checkinOutService;
    @Autowired
    private SysUserService  sysUserService;

    /**
     * 说明:ajax请求CheckinOut信息
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     * @return String
     */
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
    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {
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
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
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

        params.put("page", page);
        List<CheckinOut> checkinOuts = checkinOutService.listByParams4Page(params);
        return ResultUtil.getResult(checkinOuts, page);
    }

    /**
     * 说明:ajax请求CheckinOut信息 无分页版本
     * @return ResultDTO 返回结果
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
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

        List<CheckinOut> checkinOuts = checkinOutService.listByParams(params);
        return ResultUtil.getDataResult(checkinOuts);
    }

    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "根据id查询单个考勤信息",
            description = "根据id查询单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "id", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(@PathVariable Long id, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap</*String,Object*/>();
        if (id > 0) {
            CheckinOut bean = checkinOutService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

    }


    /**
     * 说明:查看单条信息
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "根据id查询单个考勤信息",
            description = "根据id查询单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/view.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getById(HttpServletRequest request) {
        String id = request.getParameter("id");
        CheckinOut bean = checkinOutService.selectByPrimaryKey(Long.valueOf(id));
        //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
        // result.put("bean", bean);
        return this.getResult(bean);
    }


    /**
     * 说明:更新CheckinOut信息
     *
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "更新id更新单个考勤信息",
            description = "更新id更新单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", type = "LONG", required = false),
                    @Param(name = "userId", description = "用户Id", type = "LONG", required = true),
                    @Param(name = "checkType", description = "考勤类型", type = "INTEGER", required = true),
                    @Param(name = "checkTime", description = "创建时间", type = "DATE_TIME", required = false),
            })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json", method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable Long id,
        CheckinOut checkinOut = new CheckinOut();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            checkinOut.setId(Long.valueOf(id)) ;
        }
        
        String userId = request.getParameter("userId");
        if(!StringUtil.isBlank(userId)){
            checkinOut.setUserId(Long.valueOf(userId)) ;
        }
        
        String checkType = request.getParameter("checkType");
        if(!StringUtil.isBlank(checkType)){
            checkinOut.setCheckType(Integer.valueOf(checkType)) ;
        }
        
        String checkTime = request.getParameter("checkTime");
        if(!StringUtil.isBlank(checkTime)){
            checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
        }
        */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            checkinOut.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            checkinOut.setUserId(Long.valueOf(userId));
        }
        String checkType = request.getParameter("checkType");
        if (!StringUtil.isBlank(checkType)) {
            checkinOut.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
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
            return ResultUtil.getResult(302, validStr);
        }

        return checkinOutService.save(checkinOut);

    }


    /**
     * 说明:添加CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @API(summary = "添加单个考勤信息",
            description = "添加单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = true),
                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "add.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO add(HttpServletRequest request) throws Exception {
        CheckinOut checkinOut = new CheckinOut();
            /*
            String id = request.getParameter("id");
            if(!StringUtil.isBlank(id)){
                checkinOut.setId(Long.valueOf(id)) ;
            }
            
            String userId = request.getParameter("userId");
            if(!StringUtil.isBlank(userId)){
                checkinOut.setUserId(Long.valueOf(userId)) ;
            }
            
            String checkType = request.getParameter("checkType");
            if(!StringUtil.isBlank(checkType)){
                checkinOut.setCheckType(Integer.valueOf(checkType)) ;
            }
            
            String checkTime = request.getParameter("checkTime");
            if(!StringUtil.isBlank(checkTime)){
                checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
            }
            */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            checkinOut.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            checkinOut.setUserId(Long.valueOf(userId));
        }
        String checkType = request.getParameter("checkType");
        if (!StringUtil.isBlank(checkType)) {
            checkinOut.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
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
            return ResultUtil.getResult(302, validStr);
        }

        return checkinOutService.save(checkinOut);

    }


    /**
     * 说明:添加CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个考勤信息",
            description = "添加单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", dataType = DataType.LONG, required = true),
                    @Param(name = "checkType", description = "考勤类型", dataType = DataType.INTEGER, required = true),
                    @Param(name = "checkTime", description = "创建时间", dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO save(HttpServletRequest request) throws Exception {
        CheckinOut checkinOut = new CheckinOut();
                    /*
                    String id = request.getParameter("id");
                    if(!StringUtil.isBlank(id)){
                        checkinOut.setId(Long.valueOf(id)) ;
                    }
                    
                    String userId = request.getParameter("userId");
                    if(!StringUtil.isBlank(userId)){
                        checkinOut.setUserId(Long.valueOf(userId)) ;
                    }
                    
                    String checkType = request.getParameter("checkType");
                    if(!StringUtil.isBlank(checkType)){
                        checkinOut.setCheckType(Integer.valueOf(checkType)) ;
                    }
                    
                    String checkTime = request.getParameter("checkTime");
                    if(!StringUtil.isBlank(checkTime)){
                        checkinOut.setCheckTime(Timestamp.valueOf(checkTime)) ;
                    }
                    */
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            checkinOut.setId(Long.valueOf(id));
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            checkinOut.setUserId(Long.valueOf(userId));
        }
        String checkType = request.getParameter("checkType");
        if (!StringUtil.isBlank(checkType)) {
            checkinOut.setCheckType(Integer.valueOf(checkType));
        }
        String checkTime = request.getParameter("checkTime");
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
            return ResultUtil.getResult(302, validStr);
        }

        return checkinOutService.save(checkinOut);

    }

    /**
     * 说明:删除CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "根据id删除单个考勤信息",
            description = "根据id删除单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete.json", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable Long id,
        String idStr = request.getParameter("id");
        if (StringUtil.isBlank(idStr)) {
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        checkinOutService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 说明:删除CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @API(summary = "根据id删除单个考勤信息",
            description = "根据id删除单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.path, dataType = DataType.LONG, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Long id, HttpServletRequest request) {
        checkinOutService.delete(id);
        return this.getResult(SUCC);
    }

    /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public ResultDTO multiDelete(HttpServletRequest request) {
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
        return checkinOutService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody
    public ResultDTO exportExcel(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String userId = request.getParameter("userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
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

        // 查询list集合
        List<CheckinOut> list = checkinOutService.listByParams(params);
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

                CheckinOut bean = getInfoFromMap(params);

                //  if (count > 0) {

                //      logger.warn("邮箱已经存在:" + email);
                //     errorMsg.append("邮箱已经存在:" + email);
                //   continue;

                // }

                try {
                    checkinOutService.save(bean);
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


    /**
     * 说明: 跳转到CheckinOut列表页面
     *
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String listHtml() {
        return "/static/html/CheckinOutList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapperHtml() {
        return "/static/html/CheckinOutListMapper.html";
    }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @RequestMapping(value = "/edit.htm", method = RequestMethod.GET)
    public String editHtml(HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/CheckinOutEdit.html";
    }

    /**
     * 说明:跳转查看页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
    public String viewHtml(HttpServletRequest request) {
        return "/static/html/CheckinOutView.html";
    }


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


    /**
     * 说明:添加CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个考勤信息",
            description = "添加单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "checkType", description = "考勤类型", in = InType.body, dataType = DataType.INTEGER, required = true),
                    @Param(name = "checkTime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        CheckinOut checkinOut = getInfoFromMap(bodyParam);


        return checkinOutService.save(checkinOut);

    }


    /**
     * 说明:添加CheckinOut信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "更新单个考勤信息",
            description = "更新单个考勤信息",
            parameters = {
                    @Param(name = "id", description = "编号", in = InType.body, dataType = DataType.LONG, required = false),
                    @Param(name = "userId", description = "用户Id", in = InType.body, dataType = DataType.LONG, required = true),
                    @Param(name = "checkType", description = "考勤类型", in = InType.body, dataType = DataType.INTEGER, required = true),
                    @Param(name = "checkTime", description = "创建时间", in = InType.body, dataType = DataType.DATE_TIME, required = false),
            })
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        CheckinOut checkinOut = getInfoFromMap(bodyParam);
        return checkinOutService.save(checkinOut);

    }

    /**
     * 说明:ajax请求CheckinOut信息
     * @author dozen.zhang
     * @date 2018-11-5 20:09:43
     * @return String
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
        List<CheckinOut> checkinOuts = checkinOutService.listByParams4Page(params);
        return ResultUtil.getResult(checkinOuts, page);
    }


    /**
     * 导出
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
                    if(bean.getUserId()==null)
                        continue;
                    checkinOutService.save(bean);

                    SysUser sysUser = sysUserService.getUserByOutId(bean.getUserId());
                    //根据userid 获得username
                    if(sysUser!=null ) {

                        if(StringUtil.isNotBlank(sysUser.getWechat())){
                            RedisUtil.lpush("checkinList", sysUser.getWechat()+" 恭喜你成功打卡"+DateUtil.toDateStr(new Date(bean.getCheckTime().getTime()),"yyyy-MM-dd-HH:mm:ss"));

                        }else {
                            RedisUtil.lpush("checkinList", sysUser.getUsername()+" 恭喜你成功打卡"+DateUtil.toDateStr(new Date(bean.getCheckTime().getTime()),"yyyy-MM-dd-HH:mm:ss"));

                        }

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
        result= result.replace(" ",",");
        if(StringUtil.isNotBlank(result)){
            RedisUtil.lpush("checkinList","王作品 即将迟到"+result);

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
        SysUser sysUser  = sysUserService.getUserById(user.getUserId());

       String start = request.getParameter("start")+" 00:00:00";;
        String end = request.getParameter("end")+" 23:59:59";

        HashMap map =new HashMap();
        map.put("checkTimeBegin",start);
        map.put("checkTimeEnd",end);
        map.put("userId",sysUser.getOutId());

        List<CheckinOut> checkinOuts = this.checkinOutService.listByParams(map);



        return this.getResult(checkinOuts);

    }
}
