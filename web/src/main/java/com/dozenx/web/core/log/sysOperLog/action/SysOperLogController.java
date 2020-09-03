/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 11:27:28
 * 文件说明:
 */

package com.dozenx.web.core.log.sysOperLog.action;

import com.dozenx.common.config.SysConfig;
import com.dozenx.common.exception.ParamException;
import com.dozenx.common.util.*;
import com.dozenx.swagger.annotation.*;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.sysOperLog.bean.SysOperLog;
import com.dozenx.web.core.log.sysOperLog.service.SysOperLogService;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

@APIs(description = "操作日志")
@Controller
@RequestMapping("/pub/log/")
public class SysOperLogController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysOperLogController.class);
    /** 权限service **/
    @Autowired
    private SysOperLogService sysOperLogService;


    /**
     * 说明:添加SysOperLog信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-10-25 11:27:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个操作日志信息",
            description = "添加单个操作日志信息",
            parameters = {

                    @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
                            + "  \"moduleName\":\"模块名称,类型 STRING, required = false \" "
                            + "  \"operResult\":\"结果,类型 STRING, required = false \" "
                            + "  \"params\":\"参数,类型 STRING, required = false \" "
                            + "  \"compName\":\"操作对象,类型 STRING, required = false \" "
                            + "  \"operDetail\":\"操作详情,类型 STRING, required = false \" "
                            + "  \"userId\":\"操作人,类型 LONG, required = false \" "
                            + "  \"userIp\":\"用户ip,类型 STRING, required = false \" "
                            + "  \"userName\":\"操作人,类型 STRING, required = false \" "
                            + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                            + "  }", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysOperLog sysOperLog = getInfoFromMap(bodyParam);
        return sysOperLogService.save(sysOperLog);
    }

    /**
     * 说明:删除SysOperLog信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-10-25 11:27:28
     */
    @API(summary = "根据id删除单个操作日志信息",
            description = "根据id删除单个操作日志信息",
            parameters = {
                    @Param(name = "id", description = "主键", in = InType.path, dataType = DataType.INTEGER, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Integer id, HttpServletRequest request) {
        sysOperLogService.delete(id);
        return this.getResult(SUCC);
    }


    /**
     * 说明:添加SysOperLog信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2019-10-25 11:27:28
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "更新单个操作日志信息",
            description = "更新单个操作日志信息",
            parameters = {
                    @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
                            + "  \"moduleName\":\"模块名称,类型 STRING, required = false \" "
                            + "  \"operResult\":\"结果,类型 STRING, required = false \" "
                            + "  \"params\":\"参数,类型 STRING, required = false \" "
                            + "  \"compName\":\"操作对象,类型 STRING, required = false \" "
                            + "  \"operDetail\":\"操作详情,类型 STRING, required = false \" "
                            + "  \"userId\":\"操作人,类型 LONG, required = false \" "
                            + "  \"userIp\":\"用户ip,类型 STRING, required = false \" "
                            + "  \"userName\":\"操作人,类型 STRING, required = false \" "
                            + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                            + " } ", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysOperLog sysOperLog = getInfoFromMap(bodyParam);
        return sysOperLogService.save(sysOperLog);

    }

    /**
     * 说明:ajax请求SysOperLog信息
     * @author dozen.zhang
     * @date 2019-10-25 11:27:28
     * @return String
     */
    @API(summary = "操作日志列表接口",
            description = "操作日志列表接口",
            parameters = {

                    @Param(name = "params", description = "{ \"pageSize\":15,\"curPage\":1,"
                            + "\"id\":\"主键,类型 INTEGER, required = false \" "
                            + " \"moduleName\":\"模块名称,类型 STRING, required = false \" "
                            + " \"operResult\":\"结果,类型 STRING, required = false \" "
                            + " \"params\":\"参数,类型 STRING, required = false \" "
                            + " \"compName\":\"操作对象,类型 STRING, required = false \" "
                            + " \"operDetail\":\"操作详情,类型 STRING, required = false \" "
                            + " \"userId\":\"操作人,类型 LONG, required = false \" "
                            + " \"userIp\":\"用户ip,类型 STRING, required = false \" "
                            + " \"userName\":\"操作人,类型 STRING, required = false \" "
                            + " \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                            + "  }",
                            in = InType.query, dataType = DataType.STRING, required = true),

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
        String moduleName = MapUtils.getString(params, "moduleName");
        if (!StringUtil.isBlank(moduleName)) {
            params.put("moduleName", moduleName);
        }
        String moduleNameLike = MapUtils.getString(params, "moduleNameLike");
        if (!StringUtil.isBlank(moduleNameLike)) {
            params.put("moduleNameLike", moduleNameLike);
        }
        String operResult = MapUtils.getString(params, "operResult");
        if (!StringUtil.isBlank(operResult)) {
            params.put("operResult", operResult);
        }
        String operResultLike = MapUtils.getString(params, "operResultLike");
        if (!StringUtil.isBlank(operResultLike)) {
            params.put("operResultLike", operResultLike);
        }
        String paramsStr = MapUtils.getString(params, "params");
        if (!StringUtil.isBlank(paramsStr)) {
            params.put("params", params);
        }
        String paramsLike = MapUtils.getString(params, "paramsLike");
        if (!StringUtil.isBlank(paramsLike)) {
            params.put("paramsLike", paramsLike);
        }
        String compName = MapUtils.getString(params, "compName");
        if (!StringUtil.isBlank(compName)) {
            params.put("compName", compName);
        }
        String compNameLike = MapUtils.getString(params, "compNameLike");
        if (!StringUtil.isBlank(compNameLike)) {
            params.put("compNameLike", compNameLike);
        }
        String operDetail = MapUtils.getString(params, "operDetail");
        if (!StringUtil.isBlank(operDetail)) {
            params.put("operDetail", operDetail);
        }
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String userIp = MapUtils.getString(params, "userIp");
        if (!StringUtil.isBlank(userIp)) {
            params.put("userIp", userIp);
        }
        String userIpLike = MapUtils.getString(params, "userIpLike");
        if (!StringUtil.isBlank(userIpLike)) {
            params.put("userIpLike", userIpLike);
        }
        String userName = MapUtils.getString(params, "userName");
        if (!StringUtil.isBlank(userName)) {
            params.put("userName", userName);
        }
        String userNameLike = MapUtils.getString(params, "userNameLike");
        if (!StringUtil.isBlank(userNameLike)) {
            params.put("userNameLike", userNameLike);
        }
        String createTime = MapUtils.getString(params, "createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                params.put("createTime", createTime);
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTime", new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params, "createTimeBegin");
        if (!StringUtil.isBlank(createTimeBegin)) {
            if (StringUtil.checkNumeric(createTimeBegin)) {
                params.put("createTimeBegin", createTimeBegin);
            }else if (StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd")) {
                params.put("createTimeBegin", new Timestamp(DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd").getTime()));
            } else if (StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeBegin", new Timestamp(DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params, "createTimeEnd");
        if (!StringUtil.isBlank(createTimeEnd)) {
            if (StringUtil.checkNumeric(createTimeEnd)) {
                params.put("createTimeEnd", createTimeEnd);
            } else if (StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd")) {
                params.put("createTimeEnd", new Timestamp(DateUtil.parseToDate(createTimeEnd+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()));
            }else if (StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeEnd", new Timestamp(DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String keywords = MapUtils.getString(params, "keywords");//关键字，允许为空， 支持[名称|key|value|备注]模糊查询

        if (!StringUtil.isBlank(keywords)) {
            params.put("keywords", keywords);
        }
        params.put("page", page);
        List<SysOperLog> sysOperLogs = sysOperLogService.listByParams4Page(params);
        return ResultUtil.getResult(sysOperLogs, page);
    }


    private SysOperLog getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        SysOperLog sysOperLog = new SysOperLog();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            sysOperLog.setId(Integer.valueOf(id));
        }
        String moduleName = MapUtils.getString(bodyParam, "moduleName");
        if (!StringUtil.isBlank(moduleName)) {
            sysOperLog.setModuleName(String.valueOf(moduleName));
        }
        String operResult = MapUtils.getString(bodyParam, "operResult");
        if (!StringUtil.isBlank(operResult)) {
            sysOperLog.setOperResult(String.valueOf(operResult));
        }
        String params = MapUtils.getString(bodyParam, "params");
        if (!StringUtil.isBlank(params)) {
            sysOperLog.setParams(String.valueOf(params));
        }
        String compName = MapUtils.getString(bodyParam, "compName");
        if (!StringUtil.isBlank(compName)) {
            sysOperLog.setCompName(String.valueOf(compName));
        }
        String operDetail = MapUtils.getString(bodyParam, "operDetail");
        if (!StringUtil.isBlank(operDetail)) {
            sysOperLog.setOperDetail(String.valueOf(operDetail));
        }
        String userId = MapUtils.getString(bodyParam, "userId");
        if (!StringUtil.isBlank(userId)) {
            sysOperLog.setUserId(Long.valueOf(userId));
        }
        String userIp = MapUtils.getString(bodyParam, "userIp");
        if (!StringUtil.isBlank(userIp)) {
            sysOperLog.setUserIp(String.valueOf(userIp));
        }
        String userName = MapUtils.getString(bodyParam, "userName");
        if (!StringUtil.isBlank(userName)) {
            sysOperLog.setUserName(String.valueOf(userName));
        }
        String createTime = MapUtils.getString(bodyParam, "createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                sysOperLog.setCreateTime(Timestamp.valueOf(createTime));
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                sysOperLog.setCreateTime(new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "主键", new Rule[]{new Digits(10, 0)});
        vu.add("moduleName", moduleName, "模块名称", new Rule[]{new Length(40)});
        vu.add("operResult", operResult, "结果", new Rule[]{new Length(200)});
        vu.add("params", params, "参数", new Rule[]{new Length(200)});
        vu.add("compName", compName, "操作对象", new Rule[]{new Length(40)});
        vu.add("operDetail", operDetail, "操作详情", new Rule[]{});
        vu.add("userId", userId, "操作人", new Rule[]{new Digits(11, 0)});
        vu.add("userIp", userIp, "用户ip", new Rule[]{new Length(30)});
        vu.add("userName", userName, "操作人", new Rule[]{new Length(30)});
        vu.add("createTime", createTime, "创建时间", new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"), new NotEmpty()});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return sysOperLog;
    }


    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary = "操作日志列表导出接口",
            description = "操作日志列表导出接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "主键 ", in = InType.params, dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "moduleName", description = "模块名称 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "operResult", description = "结果 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "params", description = "参数 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "compName", description = "操作对象 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "operDetail", description = "操作详情 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "userId", description = "操作人 ", in = InType.params, dataType = DataType.LONG, required = false),// false
                    @Param(name = "userIp", description = "用户ip ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "userName", description = "操作人 ", in = InType.params, dataType = DataType.STRING, required = false),// false
                    @Param(name = "createTime", description = "创建时间 ", in = InType.params, dataType = DataType.DATE_TIME, required = false),// true
            })
    @RequestMapping(value = "/export", method = RequestMethod.GET)
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
        String moduleName = MapUtils.getString(params, "moduleName");
        if (!StringUtil.isBlank(moduleName)) {
            params.put("moduleName", moduleName);
        }
        String moduleNameLike = MapUtils.getString(params, "moduleNameLike");
        if (!StringUtil.isBlank(moduleNameLike)) {
            params.put("moduleNameLike", moduleNameLike);
        }
        String operResult = MapUtils.getString(params, "operResult");
        if (!StringUtil.isBlank(operResult)) {
            params.put("operResult", operResult);
        }
        String operResultLike = MapUtils.getString(params, "operResultLike");
        if (!StringUtil.isBlank(operResultLike)) {
            params.put("operResultLike", operResultLike);
        }
        String paramsStr = MapUtils.getString(params, "params");
        if (!StringUtil.isBlank(paramsStr)) {
            params.put("params", paramsStr);
        }
        String paramsLike = MapUtils.getString(params, "paramsLike");
        if (!StringUtil.isBlank(paramsLike)) {
            params.put("paramsLike", paramsLike);
        }
        String compName = MapUtils.getString(params, "compName");
        if (!StringUtil.isBlank(compName)) {
            params.put("compName", compName);
        }
        String compNameLike = MapUtils.getString(params, "compNameLike");
        if (!StringUtil.isBlank(compNameLike)) {
            params.put("compNameLike", compNameLike);
        }
        String operDetail = MapUtils.getString(params, "operDetail");
        if (!StringUtil.isBlank(operDetail)) {
            params.put("operDetail", operDetail);
        }
        String userId = MapUtils.getString(params, "userId");
        if (!StringUtil.isBlank(userId)) {
            params.put("userId", userId);
        }
        String userIp = MapUtils.getString(params, "userIp");
        if (!StringUtil.isBlank(userIp)) {
            params.put("userIp", userIp);
        }
        String userIpLike = MapUtils.getString(params, "userIpLike");
        if (!StringUtil.isBlank(userIpLike)) {
            params.put("userIpLike", userIpLike);
        }
        String userName = MapUtils.getString(params, "userName");
        if (!StringUtil.isBlank(userName)) {
            params.put("userName", userName);
        }
        String userNameLike = MapUtils.getString(params, "userNameLike");
        if (!StringUtil.isBlank(userNameLike)) {
            params.put("userNameLike", userNameLike);
        }
        String createTime = MapUtils.getString(params, "createTime");
        if (!StringUtil.isBlank(createTime)) {
            if (StringUtil.checkNumeric(createTime)) {
                params.put("createTime", createTime);
            } else if (StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTime", new Timestamp(DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params, "createTimeBegin");
        if (!StringUtil.isBlank(createTimeBegin)) {
            if (StringUtil.checkNumeric(createTimeBegin)) {
                params.put("createTimeBegin", createTimeBegin);
            } else if (StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeBegin", new Timestamp(DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params, "createTimeEnd");
        if (!StringUtil.isBlank(createTimeEnd)) {
            if (StringUtil.checkNumeric(createTimeEnd)) {
                params.put("createTimeEnd", createTimeEnd);
            } else if (StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createTimeEnd", new Timestamp(DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page", page);
        List<SysOperLog> list = sysOperLogService.listByParams4Page(params);
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
        colTitle.put("id", "主键");
        colTitle.put("moduleName", "模块名称");
        colTitle.put("operResult", "结果");
        colTitle.put("params", "参数");
        colTitle.put("compName", "操作对象");
        colTitle.put("operDetail", "操作详情");
        colTitle.put("userId", "操作人");
        colTitle.put("userIp", "用户ip");
        colTitle.put("userName", "操作人");
        colTitle.put("createTime", "创建时间");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            SysOperLog sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("moduleName", list.get(i).getModuleName());
            map.put("operResult", list.get(i).getOperResult());
            map.put("params", list.get(i).getParams());
            map.put("compName", list.get(i).getCompName());
            map.put("operDetail", list.get(i).getOperDetail());
            map.put("userId", list.get(i).getUserId());
            map.put("userIp", list.get(i).getUserIp());
            map.put("userName", list.get(i).getUserName());
            map.put("createTime", list.get(i).getCreateTime());
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

}
