/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-8-15 17:16:32
 * 文件说明: 
 */

package com.dozenx.web.module.email.emailRecord.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import com.cpj.swagger.annotation.*;
import java.util.LinkedHashMap;
import com.dozenx.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.slf4j.Logger;
import com.dozenx.core.exception.ParamException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.web.module.email.emailRecord.service.EmailRecordService;
import com.dozenx.web.module.email.emailRecord.bean.EmailRecord;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.util.StringUtil;
import com.dozenx.web.util.RequestUtil;
import org.springframework.web.bind.annotation.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.exception.BizException;
import java.nio.file.Files;
import com.dozenx.core.config.SysConfig;

@APIs(description = "邮件发送历史")
@Controller
@RequestMapping("/email/rec")
public class EmailRecordController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(EmailRecordController.class);
    /** 权限service **/
    @Autowired
    private EmailRecordService emailRecordService;
    



  /**
         * 说明:添加EmailRecord信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-8-15 17:16:32
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个邮件发送历史信息",
            description = "添加单个邮件发送历史信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 INTEGER, required = false \" "
                + "  \"to\":\"手机号码,类型 STRING, required = true \" "
                + "  \"platform\":\"系统名称,类型 STRING, required = true \" "
                + "  \"biz\":\"业务,类型 STRING, required = true \" "
                + "  \"addTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                + "  \"content\":\"内容,类型 STRING, required = true \" "
                + "  \"title\":\"标题,类型 STRING, required = true \" "
                + "  \"status\":\"发送状态,类型 BYTE, required = false \" "
                + "  \"reason\":\"失败原因,类型 STRING, required = false \" "
                + "  \"user\":\"用户,类型 LONG, required = false \" "
                + "  \"ip\":\"ip地址,类型 STRING, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            EmailRecord emailRecord =    getInfoFromMap(bodyParam);


            return emailRecordService.save(emailRecord);

        }

 /**
         * 说明:删除EmailRecord信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-8-15 17:16:32
         */
         @API( summary="根据id删除单个邮件发送历史信息",
            description = "根据id删除单个邮件发送历史信息",
            parameters={
             @Param(name="id" , description="id",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            emailRecordService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加EmailRecord信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-8-15 17:16:32
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个邮件发送历史信息",
    description = "更新单个邮件发送历史信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 INTEGER, required = false \" "
                       + "  \"to\":\"手机号码,类型 STRING, required = true \" "
                       + "  \"platform\":\"系统名称,类型 STRING, required = true \" "
                       + "  \"biz\":\"业务,类型 STRING, required = true \" "
                       + "  \"addTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                       + "  \"content\":\"内容,类型 STRING, required = true \" "
                       + "  \"title\":\"标题,类型 STRING, required = true \" "
                       + "  \"status\":\"发送状态,类型 BYTE, required = false \" "
                       + "  \"reason\":\"失败原因,类型 STRING, required = false \" "
                       + "  \"user\":\"用户,类型 LONG, required = false \" "
                       + "  \"ip\":\"ip地址,类型 STRING, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    EmailRecord emailRecord =    getInfoFromMap(bodyParam);
    return emailRecordService.save(emailRecord);

    }
/**
     * 说明:ajax请求EmailRecord信息
     * @author dozen.zhang
     * @date 2019-8-15 17:16:32
     * @return String
     */
       @API(summary="邮件发送历史列表接口",
                 description="邮件发送历史列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"id,类型 INTEGER, required = false \" "
                                 + " \"to\":\"手机号码,类型 STRING, required = true \" "
                                 + " \"platform\":\"系统名称,类型 STRING, required = true \" "
                                 + " \"biz\":\"业务,类型 STRING, required = true \" "
                                 + " \"addTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                                 + " \"content\":\"内容,类型 STRING, required = true \" "
                                 + " \"title\":\"标题,类型 STRING, required = true \" "
                                 + " \"status\":\"发送状态,类型 BYTE, required = false \" "
                                 + " \"reason\":\"失败原因,类型 STRING, required = false \" "
                                 + " \"user\":\"用户,类型 LONG, required = false \" "
                                 + " \"ip\":\"ip地址,类型 STRING, required = false \" "
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
        String to = MapUtils.getString(params,"to");
        if(!StringUtil.isBlank(to)){
            params.put("to",to);
        }
        String toLike = MapUtils.getString(params,"toLike");
        if(!StringUtil.isBlank(toLike)){
            params.put("toLike",toLike);
        }
        String platform = MapUtils.getString(params,"platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = MapUtils.getString(params,"platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String biz = MapUtils.getString(params,"biz");
        if(!StringUtil.isBlank(biz)){
            params.put("biz",biz);
        }
        String bizLike = MapUtils.getString(params,"bizLike");
        if(!StringUtil.isBlank(bizLike)){
            params.put("bizLike",bizLike);
        }
        String addTime = MapUtils.getString(params,"addTime");
        if(!StringUtil.isBlank(addTime)){
            if(StringUtil.checkNumeric(addTime)){
                params.put("addTime",addTime);
            }else if(StringUtil.checkDateStr(addTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTime",new Timestamp( DateUtil.parseToDate(addTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String addTimeBegin = MapUtils.getString(params,"addTimeBegin");
        if(!StringUtil.isBlank(addTimeBegin)){
            if(StringUtil.checkNumeric(addTimeBegin)){
                params.put("addTimeBegin",addTimeBegin);
            }else if(StringUtil.checkDateStr(addTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTimeBegin",new Timestamp( DateUtil.parseToDate(addTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String addTimeEnd = MapUtils.getString(params,"addTimeEnd");
        if(!StringUtil.isBlank(addTimeEnd)){
            if(StringUtil.checkNumeric(addTimeEnd)){
                params.put("addTimeEnd",addTimeEnd);
            }else if(StringUtil.checkDateStr(addTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTimeEnd",new Timestamp( DateUtil.parseToDate(addTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String reason = MapUtils.getString(params,"reason");
        if(!StringUtil.isBlank(reason)){
            params.put("reason",reason);
        }
        String reasonLike = MapUtils.getString(params,"reasonLike");
        if(!StringUtil.isBlank(reasonLike)){
            params.put("reasonLike",reasonLike);
        }
        String user = MapUtils.getString(params,"user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String ip = MapUtils.getString(params,"ip");
        if(!StringUtil.isBlank(ip)){
            params.put("ip",ip);
        }
        String ipLike = MapUtils.getString(params,"ipLike");
        if(!StringUtil.isBlank(ipLike)){
            params.put("ipLike",ipLike);
        }

        params.put("page",page);
        List<EmailRecord> emailRecords = emailRecordService.listByParams4Page(params);
        return ResultUtil.getResult(emailRecords, page);
    }


    private EmailRecord getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       EmailRecord emailRecord =new  EmailRecord();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                emailRecord.setId(Integer.valueOf(id));
        }
        String to = MapUtils.getString(bodyParam,"to");
        if(!StringUtil.isBlank(to)){
                emailRecord.setTo(String.valueOf(to));
        }
        String platform = MapUtils.getString(bodyParam,"platform");
        if(!StringUtil.isBlank(platform)){
                emailRecord.setPlatform(String.valueOf(platform));
        }
        String biz = MapUtils.getString(bodyParam,"biz");
        if(!StringUtil.isBlank(biz)){
                emailRecord.setBiz(String.valueOf(biz));
        }
        String addTime = MapUtils.getString(bodyParam,"addTime");
        if(!StringUtil.isBlank(addTime)){
            if(StringUtil.checkNumeric(addTime)){
                emailRecord.setAddTime(Timestamp.valueOf(addTime));
            }else if(StringUtil.checkDateStr(addTime, "yyyy-MM-dd HH:mm:ss")){
                emailRecord.setAddTime(new Timestamp( DateUtil.parseToDate(addTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String content = MapUtils.getString(bodyParam,"content");
        if(!StringUtil.isBlank(content)){
                emailRecord.setContent(String.valueOf(content));
        }
        String title = MapUtils.getString(bodyParam,"title");
        if(!StringUtil.isBlank(title)){
                emailRecord.setTitle(String.valueOf(title));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                emailRecord.setStatus(Byte.valueOf(status));
        }
        String reason = MapUtils.getString(bodyParam,"reason");
        if(!StringUtil.isBlank(reason)){
                emailRecord.setReason(String.valueOf(reason));
        }
        String user = MapUtils.getString(bodyParam,"user");
        if(!StringUtil.isBlank(user)){
                emailRecord.setUser(Long.valueOf(user));
        }
        String ip = MapUtils.getString(bodyParam,"ip");
        if(!StringUtil.isBlank(ip)){
                emailRecord.setIp(String.valueOf(ip));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "id",  new Rule[]{new Digits(10,0)});
        vu.add("to", to, "手机号码",  new Rule[]{new Length(11),new NotEmpty(),new PhoneRule()});
        vu.add("platform", platform, "系统名称",  new Rule[]{new Length(13),new NotEmpty(),new AlphaRule()});
        vu.add("biz", biz, "业务",  new Rule[]{new Length(13),new NotEmpty(),new AlphaRule()});
        vu.add("addTime", addTime, "发送时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"),new NotEmpty()});
        vu.add("content", content, "内容",  new Rule[]{new Length(200),new NotEmpty()});
        vu.add("title", title, "标题",  new Rule[]{new Length(200),new NotEmpty()});
        vu.add("status", status, "发送状态",  new Rule[]{new Digits(10,0),new CheckBox(new String[]{"1","2"})});
        vu.add("reason", reason, "失败原因",  new Rule[]{new Length(200)});
        vu.add("user", user, "用户",  new Rule[]{new Digits(11,0)});
        vu.add("ip", ip, "ip地址",  new Rule[]{new Length(15)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  emailRecord;
    }





       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="邮件发送历史列表导出接口",
          description="邮件发送历史列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="to" , description="手机号码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="platform" , description="系统名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="biz" , description="业务 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="addTime" , description="发送时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// true
             @Param(name="content" , description="内容 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="title" , description="标题 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="status" , description="发送状态 ",in=InType.params,dataType = DataType.BYTE,required =false),// false
             @Param(name="reason" , description="失败原因 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="user" , description="用户 ",in=InType.params,dataType = DataType.LONG,required =false),// false
             @Param(name="ip" , description="ip地址 ",in=InType.params,dataType = DataType.STRING,required =false),// false
          })
        @RequestMapping(value = "/export", method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
              Page page = RequestUtil.getPage(params);
             if(page ==null){
                  return this.getWrongResultFromCfg("err.param.page");
             }

                     String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String to = MapUtils.getString(params,"to");
        if(!StringUtil.isBlank(to)){
            params.put("to",to);
        }
        String toLike = MapUtils.getString(params,"toLike");
        if(!StringUtil.isBlank(toLike)){
            params.put("toLike",toLike);
        }
        String platform = MapUtils.getString(params,"platform");
        if(!StringUtil.isBlank(platform)){
            params.put("platform",platform);
        }
        String platformLike = MapUtils.getString(params,"platformLike");
        if(!StringUtil.isBlank(platformLike)){
            params.put("platformLike",platformLike);
        }
        String biz = MapUtils.getString(params,"biz");
        if(!StringUtil.isBlank(biz)){
            params.put("biz",biz);
        }
        String bizLike = MapUtils.getString(params,"bizLike");
        if(!StringUtil.isBlank(bizLike)){
            params.put("bizLike",bizLike);
        }
        String addTime = MapUtils.getString(params,"addTime");
        if(!StringUtil.isBlank(addTime)){
            if(StringUtil.checkNumeric(addTime)){
                params.put("addTime",addTime);
            }else if(StringUtil.checkDateStr(addTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTime",new Timestamp( DateUtil.parseToDate(addTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String addTimeBegin = MapUtils.getString(params,"addTimeBegin");
        if(!StringUtil.isBlank(addTimeBegin)){
            if(StringUtil.checkNumeric(addTimeBegin)){
                params.put("addTimeBegin",addTimeBegin);
            }else if(StringUtil.checkDateStr(addTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTimeBegin",new Timestamp( DateUtil.parseToDate(addTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String addTimeEnd = MapUtils.getString(params,"addTimeEnd");
        if(!StringUtil.isBlank(addTimeEnd)){
            if(StringUtil.checkNumeric(addTimeEnd)){
                params.put("addTimeEnd",addTimeEnd);
            }else if(StringUtil.checkDateStr(addTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("addTimeEnd",new Timestamp( DateUtil.parseToDate(addTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String content = MapUtils.getString(params,"content");
        if(!StringUtil.isBlank(content)){
            params.put("content",content);
        }
        String contentLike = MapUtils.getString(params,"contentLike");
        if(!StringUtil.isBlank(contentLike)){
            params.put("contentLike",contentLike);
        }
        String title = MapUtils.getString(params,"title");
        if(!StringUtil.isBlank(title)){
            params.put("title",title);
        }
        String titleLike = MapUtils.getString(params,"titleLike");
        if(!StringUtil.isBlank(titleLike)){
            params.put("titleLike",titleLike);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String reason = MapUtils.getString(params,"reason");
        if(!StringUtil.isBlank(reason)){
            params.put("reason",reason);
        }
        String reasonLike = MapUtils.getString(params,"reasonLike");
        if(!StringUtil.isBlank(reasonLike)){
            params.put("reasonLike",reasonLike);
        }
        String user = MapUtils.getString(params,"user");
        if(!StringUtil.isBlank(user)){
            params.put("user",user);
        }
        String ip = MapUtils.getString(params,"ip");
        if(!StringUtil.isBlank(ip)){
            params.put("ip",ip);
        }
        String ipLike = MapUtils.getString(params,"ipLike");
        if(!StringUtil.isBlank(ipLike)){
            params.put("ipLike",ipLike);
        }

             params.put("page",page);
             List<EmailRecord> list = emailRecordService.listByParams4Page(params);
            // 存放临时文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "list.xlsx");
              String randomName = DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")+".xlsx";

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
            colTitle.put("id", "id");
            colTitle.put("to", "手机号码");
            colTitle.put("platform", "系统名称");
            colTitle.put("biz", "业务");
            colTitle.put("addTime", "发送时间");
            colTitle.put("content", "内容");
            colTitle.put("title", "标题");
            colTitle.put("status", "发送状态");
            colTitle.put("reason", "失败原因");
            colTitle.put("user", "用户");
            colTitle.put("ip", "ip地址");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                EmailRecord sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("to",  list.get(i).getTo());
                map.put("platform",  list.get(i).getPlatform());
                map.put("biz",  list.get(i).getBiz());
                map.put("addTime",  list.get(i).getAddTime());
                map.put("content",  list.get(i).getContent());
                map.put("title",  list.get(i).getTitle());
                map.put("status",  list.get(i).getStatus());
                map.put("reason",  list.get(i).getReason());
                map.put("user",  list.get(i).getUser());
                map.put("ip",  list.get(i).getIp());
                finalList.add(map);
            }
            try {
                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
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
