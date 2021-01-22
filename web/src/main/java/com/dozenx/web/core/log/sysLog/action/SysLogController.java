/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 15:15:27
 * 文件说明: 
 */

package com.dozenx.web.core.log.sysLog.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.JsonUtil;
//import com.dozenx.common.util.ExcelUtil;
import java.math.BigDecimal;
import com.dozenx.swagger.annotation.*;
import java.util.LinkedHashMap;
import com.dozenx.common.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.slf4j.Logger;
import com.dozenx.common.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.dozenx.swagger.annotation.API;
import com.dozenx.swagger.annotation.APIs;
import com.dozenx.swagger.annotation.DataType;
import com.dozenx.swagger.annotation.Param;
import com.dozenx.web.core.log.sysLog.service.SysLogService;
import com.dozenx.web.core.log.sysLog.bean.SysLog;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.common.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.common.Path.PathManager;
import com.dozenx.common.exception.BizException;
import java.nio.file.Files;
import com.dozenx.common.config.SysConfig;

@APIs(description = "系统日志")
@Controller
@RequestMapping("/sys/log")
public class SysLogController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysLogController.class);
    /** 权限service **/
    @Autowired
    private SysLogService sysLogService;
    



  /**
         * 说明:添加SysLog信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 15:15:27
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个系统日志信息",
            description = "添加单个系统日志信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                + "  \"logPath\":\"代码路径,类型 STRING, required = false \" "
                + "  \"logType\":\"日志类型,类型 BYTE, required = false \" "
                + "  \"logCode\":\"日志编号,类型 INTEGER, required = false \" "
                + "  \"param\":\"操作参数,类型 STRING, required = false \" "
                + "  \"userName\":\"用户,类型 STRING, required = false \" "
                + "  \"logMsg\":\"消息,类型 STRING, required = false \" "
                + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                + "  \"startTime\":\"开始时间,类型 DATE_TIME, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysLog sysLog =    getInfoFromMap(bodyParam);


            return sysLogService.save(sysLog);

        }

 /**
         * 说明:删除SysLog信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 15:15:27
         */
         @API( summary="根据id删除单个系统日志信息",
            description = "根据id删除单个系统日志信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            sysLogService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysLog信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-10-25 15:15:27
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个系统日志信息",
    description = "更新单个系统日志信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                       + "  \"logPath\":\"代码路径,类型 STRING, required = false \" "
                       + "  \"logType\":\"日志类型,类型 BYTE, required = false \" "
                       + "  \"logCode\":\"日志编号,类型 INTEGER, required = false \" "
                       + "  \"param\":\"操作参数,类型 STRING, required = false \" "
                       + "  \"userName\":\"用户,类型 STRING, required = false \" "
                       + "  \"logMsg\":\"消息,类型 STRING, required = false \" "
                       + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                       + "  \"startTime\":\"开始时间,类型 DATE_TIME, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysLog sysLog =    getInfoFromMap(bodyParam);
    return sysLogService.save(sysLog);

    }
/**
     * 说明:ajax请求SysLog信息
     * @author dozen.zhang
     * @date 2019-10-25 15:15:27
     * @return String
     */
       @API(summary="系统日志列表接口",
                 description="系统日志列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"编号,类型 INTEGER, required = false \" "
                                 + " \"logPath\":\"代码路径,类型 STRING, required = false \" "
                                 + " \"logType\":\"日志类型,类型 BYTE, required = false \" "
                                 + " \"logCode\":\"日志编号,类型 INTEGER, required = false \" "
                                 + " \"param\":\"操作参数,类型 STRING, required = false \" "
                                 + " \"userName\":\"用户,类型 STRING, required = false \" "
                                 + " \"logMsg\":\"消息,类型 STRING, required = false \" "
                                 + " \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                                 + " \"startTime\":\"开始时间,类型 DATE_TIME, required = false \" "
                                 + "  }",
                        in = InType.query, dataType = DataType.STRING, required = true),

         })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
         Page page = RequestUtil.getPage(params);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }

                String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String logPath = MapUtils.getString(params,"logPath");
        if(!StringUtil.isBlank(logPath)){
            params.put("logPath",logPath);
        }
        String logPathLike = MapUtils.getString(params,"logPathLike");
        if(!StringUtil.isBlank(logPathLike)){
            params.put("logPathLike",logPathLike);
        }
        String logType = MapUtils.getString(params,"logType");
        if(!StringUtil.isBlank(logType)){
            params.put("logType",logType);
        }
        String logCode = MapUtils.getString(params,"logCode");
        if(!StringUtil.isBlank(logCode)){
            params.put("logCode",logCode);
        }
        String param = MapUtils.getString(params,"param");
        if(!StringUtil.isBlank(param)){
            params.put("param",param);
        }
        String paramLike = MapUtils.getString(params,"paramLike");
        if(!StringUtil.isBlank(paramLike)){
            params.put("paramLike",paramLike);
        }
        String userName = MapUtils.getString(params,"userName");
        if(!StringUtil.isBlank(userName)){
            params.put("userName",userName);
        }
        String userNameLike = MapUtils.getString(params,"userNameLike");
        if(!StringUtil.isBlank(userNameLike)){
            params.put("userNameLike",userNameLike);
        }
        String logMsg = MapUtils.getString(params,"logMsg");
        if(!StringUtil.isBlank(logMsg)){
            params.put("logMsg",logMsg);
        }
        String logMsgLike = MapUtils.getString(params,"logMsgLike");
        if(!StringUtil.isBlank(logMsgLike)){
            params.put("logMsgLike",logMsgLike);
        }
        String createTime = MapUtils.getString(params,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",new Timestamp( Long.valueOf(createTimeBegin)));
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",new Timestamp( Long.valueOf(createTimeEnd)));
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = MapUtils.getString(params,"startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                params.put("startTime",startTime);
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTime",new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeBegin = MapUtils.getString(params,"startTimeBegin");
        if(!StringUtil.isBlank(startTimeBegin)){
            if(StringUtil.checkNumeric(startTimeBegin)){
                params.put("startTimeBegin",new Timestamp( Long.valueOf(startTimeBegin)));
            }else if(StringUtil.checkDateStr(startTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeBegin",new Timestamp( DateUtil.parseToDate(startTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTimeEnd = MapUtils.getString(params,"startTimeEnd");
        if(!StringUtil.isBlank(startTimeEnd)){
            if(StringUtil.checkNumeric(startTimeEnd)){
                params.put("startTimeEnd",new Timestamp( Long.valueOf(startTimeEnd)));
            }else if(StringUtil.checkDateStr(startTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("startTimeEnd",new Timestamp( DateUtil.parseToDate(startTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<SysLog> sysLogs = sysLogService.listByParams4Page(params);
        return ResultUtil.getResult(sysLogs, page);
    }


    private SysLog getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysLog sysLog =new  SysLog();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysLog.setId(Integer.valueOf(id));
        }
        String logPath = MapUtils.getString(bodyParam,"logPath");
        if(!StringUtil.isBlank(logPath)){
                sysLog.setLogPath(String.valueOf(logPath));
        }
        String logType = MapUtils.getString(bodyParam,"logType");
        if(!StringUtil.isBlank(logType)){
                sysLog.setLogType(Byte.valueOf(logType));
        }
        String logCode = MapUtils.getString(bodyParam,"logCode");
        if(!StringUtil.isBlank(logCode)){
                sysLog.setLogCode(Integer.valueOf(logCode));
        }
        String param = MapUtils.getString(bodyParam,"param");
        if(!StringUtil.isBlank(param)){
                sysLog.setParam(String.valueOf(param));
        }
        String userName = MapUtils.getString(bodyParam,"userName");
        if(!StringUtil.isBlank(userName)){
                sysLog.setUserName(String.valueOf(userName));
        }
        String logMsg = MapUtils.getString(bodyParam,"logMsg");
        if(!StringUtil.isBlank(logMsg)){
                sysLog.setLogMsg(String.valueOf(logMsg));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sysLog.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sysLog.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String startTime = MapUtils.getString(bodyParam,"startTime");
        if(!StringUtil.isBlank(startTime)){
            if(StringUtil.checkNumeric(startTime)){
                sysLog.setStartTime(Timestamp.valueOf(startTime));
            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
                sysLog.setStartTime(new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("logPath", logPath, "代码路径",  new Rule[]{new Length(100)});
        vu.add("logType", logType, "日志类型",  new Rule[]{new Digits(10,0)});
        vu.add("logCode", logCode, "日志编号",  new Rule[]{new Digits(10,0)});
        vu.add("param", param, "操作参数",  new Rule[]{new Length(200)});
        vu.add("userName", userName, "用户",  new Rule[]{new Length(40)});
        vu.add("logMsg", logMsg, "消息",  new Rule[]{new Length(200)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("startTime", startTime, "开始时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysLog;
    }





//       /**
//         * 导出
//         * @param request
//         * @return
//         * @author dozen.zhang
//         */
//        @API(summary="系统日志列表导出接口",
//          description="系统日志列表导出接口",
//          parameters={
//          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="logPath" , description="代码路径 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="logType" , description="日志类型 ",in=InType.params,dataType = DataType.BYTE,required =false),// false
//             @Param(name="logCode" , description="日志编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="param" , description="操作参数 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="userName" , description="用户 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="logMsg" , description="消息 ",in=InType.params,dataType = DataType.STRING,required =false),// false
//             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
//             @Param(name="startTime" , description="开始时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
//          })
//        @RequestMapping(value = "/export", method = RequestMethod.GET)
//        @ResponseBody
//        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{
//
//             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
//              Page page = RequestUtil.getPage(params);
//             if(page ==null){
//                  return this.getWrongResultFromCfg("err.param.page");
//             }
//
//                     String id = MapUtils.getString(params,"id");
//        if(!StringUtil.isBlank(id)){
//            params.put("id",id);
//        }
//        String logPath = MapUtils.getString(params,"logPath");
//        if(!StringUtil.isBlank(logPath)){
//            params.put("logPath",logPath);
//        }
//        String logPathLike = MapUtils.getString(params,"logPathLike");
//        if(!StringUtil.isBlank(logPathLike)){
//            params.put("logPathLike",logPathLike);
//        }
//        String logType = MapUtils.getString(params,"logType");
//        if(!StringUtil.isBlank(logType)){
//            params.put("logType",logType);
//        }
//        String logCode = MapUtils.getString(params,"logCode");
//        if(!StringUtil.isBlank(logCode)){
//            params.put("logCode",logCode);
//        }
//        String param = MapUtils.getString(params,"param");
//        if(!StringUtil.isBlank(param)){
//            params.put("param",param);
//        }
//        String paramLike = MapUtils.getString(params,"paramLike");
//        if(!StringUtil.isBlank(paramLike)){
//            params.put("paramLike",paramLike);
//        }
//        String userName = MapUtils.getString(params,"userName");
//        if(!StringUtil.isBlank(userName)){
//            params.put("userName",userName);
//        }
//        String userNameLike = MapUtils.getString(params,"userNameLike");
//        if(!StringUtil.isBlank(userNameLike)){
//            params.put("userNameLike",userNameLike);
//        }
//        String logMsg = MapUtils.getString(params,"logMsg");
//        if(!StringUtil.isBlank(logMsg)){
//            params.put("logMsg",logMsg);
//        }
//        String logMsgLike = MapUtils.getString(params,"logMsgLike");
//        if(!StringUtil.isBlank(logMsgLike)){
//            params.put("logMsgLike",logMsgLike);
//        }
//        String createTime = MapUtils.getString(params,"createTime");
//        if(!StringUtil.isBlank(createTime)){
//            if(StringUtil.checkNumeric(createTime)){
//                params.put("createTime",createTime);
//            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeBegin = MapUtils.getString(params,"createTimeBegin");
//        if(!StringUtil.isBlank(createTimeBegin)){
//            if(StringUtil.checkNumeric(createTimeBegin)){
//                params.put("createTimeBegin",createTimeBegin);
//            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
//        if(!StringUtil.isBlank(createTimeEnd)){
//            if(StringUtil.checkNumeric(createTimeEnd)){
//                params.put("createTimeEnd",createTimeEnd);
//            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String startTime = MapUtils.getString(params,"startTime");
//        if(!StringUtil.isBlank(startTime)){
//            if(StringUtil.checkNumeric(startTime)){
//                params.put("startTime",startTime);
//            }else if(StringUtil.checkDateStr(startTime, "yyyy-MM-dd HH:mm:ss")){
//                params.put("startTime",new Timestamp( DateUtil.parseToDate(startTime, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String startTimeBegin = MapUtils.getString(params,"startTimeBegin");
//        if(!StringUtil.isBlank(startTimeBegin)){
//            if(StringUtil.checkNumeric(startTimeBegin)){
//                params.put("startTimeBegin",startTimeBegin);
//            }else if(StringUtil.checkDateStr(startTimeBegin, "yyyy-MM-dd HH:mm:ss")){
//                params.put("startTimeBegin",new Timestamp( DateUtil.parseToDate(startTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//        String startTimeEnd = MapUtils.getString(params,"startTimeEnd");
//        if(!StringUtil.isBlank(startTimeEnd)){
//            if(StringUtil.checkNumeric(startTimeEnd)){
//                params.put("startTimeEnd",startTimeEnd);
//            }else if(StringUtil.checkDateStr(startTimeEnd, "yyyy-MM-dd HH:mm:ss")){
//                params.put("startTimeEnd",new Timestamp( DateUtil.parseToDate(startTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
//            }
//        }
//
//             params.put("page",page);
//             List<SysLog> list = sysLogService.listByParams4Page(params);
//            // 存放临时文件
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "list.xlsx");
//              String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")+".xlsx";
//
//            String folder = request.getSession().getServletContext()
//                    .getRealPath("/")
//                    + "xlstmp";
//
//
//            File folder_file = new File(folder);
//            if (!folder_file.exists()) {
//                folder_file.mkdir();
//            }
//            String fileName = folder + File.separator
//                      + randomName;
//            // 得到导出Excle时清单的英中文map
//            LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
//            colTitle.put("id", "编号");
//            colTitle.put("logPath", "代码路径");
//            colTitle.put("logType", "日志类型");
//            colTitle.put("logCode", "日志编号");
//            colTitle.put("param", "操作参数");
//            colTitle.put("userName", "用户");
//            colTitle.put("logMsg", "消息");
//            colTitle.put("createTime", "创建时间");
//            colTitle.put("startTime", "开始时间");
//            List<Map> finalList = new ArrayList<Map>();
//            for (int i = 0; i < list.size(); i++) {
//                SysLog sm = list.get(i);
//                HashMap<String,Object> map = new HashMap<String,Object>();
//                map.put("id",  list.get(i).getId());
//                map.put("logPath",  list.get(i).getLogPath());
//                map.put("logType",  list.get(i).getLogType());
//                map.put("logCode",  list.get(i).getLogCode());
//                map.put("param",  list.get(i).getParam());
//                map.put("userName",  list.get(i).getUserName());
//                map.put("logMsg",  list.get(i).getLogMsg());
//                map.put("createTime",  list.get(i).getCreateTime());
//                map.put("startTime",  list.get(i).getStartTime());
//                finalList.add(map);
//            }
//            try {
//                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
//                }
//                /*
//                 * return new ResponseEntity<byte[]>(
//                 * FileUtils.readFileToByteArray(new File(fileName)), headers,
//                 * HttpStatus.CREATED);
//                 */
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return this.getResult(0, "数据为空，导出失败");
//
//        }

}
