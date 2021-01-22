/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-11-21 10:08:19
 * 文件说明: 
 */

package com.dozenx.web.core.sms.sysSmsRecord.action;
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
import com.dozenx.web.core.sms.sysSmsRecord.service.SysSmsRecordService;
import com.dozenx.web.core.sms.sysSmsRecord.bean.SysSmsRecord;
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

@APIs(description = "短信验证码发送历史")
@Controller
@RequestMapping("/sms")
public class SysSmsRecordController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysSmsRecordController.class);
    /** 权限service **/
    @Autowired
    private SysSmsRecordService sysSmsRecordService;
    



  /**
         * 说明:添加SysSmsRecord信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-11-21 10:08:19
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个短信验证码发送历史信息",
            description = "添加单个短信验证码发送历史信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 LONG, required = false \" "
                + "  \"phone\":\"手机号码,类型 STRING, required = true \" "
                + "  \"biz\":\"业务类型,类型 STRING, required = true \" "
                + "  \"createTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                + "  \"content\":\"内容,类型 STRING, required = true \" "
                + "  \"status\":\"发送状态,类型 BYTE, required = false \" "
                + "  \"reason\":\"失败原因,类型 STRING, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysSmsRecord sysSmsRecord =    getInfoFromMap(bodyParam);


            return sysSmsRecordService.save(sysSmsRecord);

        }

 /**
         * 说明:删除SysSmsRecord信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-11-21 10:08:19
         */
         @API( summary="根据id删除单个短信验证码发送历史信息",
            description = "根据id删除单个短信验证码发送历史信息",
            parameters={
             @Param(name="id" , description="id",in=InType.path,dataType= DataType.LONG,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Long id,HttpServletRequest request) {
            sysSmsRecordService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysSmsRecord信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-11-21 10:08:19
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个短信验证码发送历史信息",
    description = "更新单个短信验证码发送历史信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 LONG, required = false \" "
                       + "  \"phone\":\"手机号码,类型 STRING, required = true \" "
                       + "  \"biz\":\"业务类型,类型 STRING, required = true \" "
                       + "  \"createTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                       + "  \"content\":\"内容,类型 STRING, required = true \" "
                       + "  \"status\":\"发送状态,类型 BYTE, required = false \" "
                       + "  \"reason\":\"失败原因,类型 STRING, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysSmsRecord sysSmsRecord =    getInfoFromMap(bodyParam);
    return sysSmsRecordService.save(sysSmsRecord);

    }
/**
     * 说明:ajax请求SysSmsRecord信息
     * @author dozen.zhang
     * @date 2019-11-21 10:08:19
     * @return String
     */
       @API(summary="短信验证码发送历史列表接口",
                 description="短信验证码发送历史列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"id,类型 LONG, required = false \" "
                                 + " \"phone\":\"手机号码,类型 STRING, required = true \" "
                                 + " \"biz\":\"业务类型,类型 STRING, required = true \" "
                                 + " \"createTime\":\"发送时间,类型 DATE_TIME, required = true \" "
                                 + " \"content\":\"内容,类型 STRING, required = true \" "
                                 + " \"status\":\"发送状态,类型 BYTE, required = false \" "
                                 + " \"reason\":\"失败原因,类型 STRING, required = false \" "
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
        String phone = MapUtils.getString(params,"phone");
        if(!StringUtil.isBlank(phone)){
            params.put("phone",phone);
        }
        String phoneLike = MapUtils.getString(params,"phoneLike");
        if(!StringUtil.isBlank(phoneLike)){
            params.put("phoneLike",phoneLike);
        }
        String biz = MapUtils.getString(params,"biz");
        if(!StringUtil.isBlank(biz)){
            params.put("biz",biz);
        }
        String bizLike = MapUtils.getString(params,"bizLike");
        if(!StringUtil.isBlank(bizLike)){
            params.put("bizLike",bizLike);
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
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = MapUtils.getString(params,"createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
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

        params.put("page",page);
        List<SysSmsRecord> sysSmsRecords = sysSmsRecordService.listByParams4Page(params);
        return ResultUtil.getResult(sysSmsRecords, page);
    }


    private SysSmsRecord getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysSmsRecord sysSmsRecord =new  SysSmsRecord();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysSmsRecord.setId(Long.valueOf(id));
        }
        String phone = MapUtils.getString(bodyParam,"phone");
        if(!StringUtil.isBlank(phone)){
                sysSmsRecord.setPhone(String.valueOf(phone));
        }
        String biz = MapUtils.getString(bodyParam,"biz");
        if(!StringUtil.isBlank(biz)){
                sysSmsRecord.setBiz(String.valueOf(biz));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sysSmsRecord.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sysSmsRecord.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String content = MapUtils.getString(bodyParam,"content");
        if(!StringUtil.isBlank(content)){
                sysSmsRecord.setContent(String.valueOf(content));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                sysSmsRecord.setStatus(Byte.valueOf(status));
        }
        String reason = MapUtils.getString(bodyParam,"reason");
        if(!StringUtil.isBlank(reason)){
                sysSmsRecord.setReason(String.valueOf(reason));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "id",  new Rule[]{new Digits(11,0)});
        vu.add("phone", phone, "手机号码",  new Rule[]{new Length(11),new NotEmpty(),new PhoneRule()});
        vu.add("biz", biz, "业务类型",  new Rule[]{new Length(13),new NotEmpty(),new AlphaRule()});
        vu.add("createTime", createTime, "发送时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"),new NotEmpty()});
        vu.add("content", content, "内容",  new Rule[]{new Length(200),new NotEmpty()});
        vu.add("status", status, "发送状态",  new Rule[]{new Digits(10,0),new CheckBox(new String[]{"1","2"})});
        vu.add("reason", reason, "失败原因",  new Rule[]{new Length(200)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysSmsRecord;
    }





//       /**
//         * 导出
//         * @param request
//         * @return
//         * @author dozen.zhang
//         */
//        @API(summary="短信验证码发送历史列表导出接口",
//          description="短信验证码发送历史列表导出接口",
//          parameters={
//          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//             @Param(name="id" , description="id ",in=InType.params,dataType = DataType.LONG,required =false),// false
//             @Param(name="phone" , description="手机号码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="biz" , description="业务类型 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="createTime" , description="发送时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// true
//             @Param(name="content" , description="内容 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="status" , description="发送状态 ",in=InType.params,dataType = DataType.BYTE,required =false),// false
//             @Param(name="reason" , description="失败原因 ",in=InType.params,dataType = DataType.STRING,required =false),// false
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
//        String phone = MapUtils.getString(params,"phone");
//        if(!StringUtil.isBlank(phone)){
//            params.put("phone",phone);
//        }
//        String phoneLike = MapUtils.getString(params,"phoneLike");
//        if(!StringUtil.isBlank(phoneLike)){
//            params.put("phoneLike",phoneLike);
//        }
//        String biz = MapUtils.getString(params,"biz");
//        if(!StringUtil.isBlank(biz)){
//            params.put("biz",biz);
//        }
//        String bizLike = MapUtils.getString(params,"bizLike");
//        if(!StringUtil.isBlank(bizLike)){
//            params.put("bizLike",bizLike);
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
//        String content = MapUtils.getString(params,"content");
//        if(!StringUtil.isBlank(content)){
//            params.put("content",content);
//        }
//        String contentLike = MapUtils.getString(params,"contentLike");
//        if(!StringUtil.isBlank(contentLike)){
//            params.put("contentLike",contentLike);
//        }
//        String status = MapUtils.getString(params,"status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String reason = MapUtils.getString(params,"reason");
//        if(!StringUtil.isBlank(reason)){
//            params.put("reason",reason);
//        }
//        String reasonLike = MapUtils.getString(params,"reasonLike");
//        if(!StringUtil.isBlank(reasonLike)){
//            params.put("reasonLike",reasonLike);
//        }
//
//             params.put("page",page);
//             List<SysSmsRecord> list = sysSmsRecordService.listByParams4Page(params);
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
//            colTitle.put("id", "id");
//            colTitle.put("phone", "手机号码");
//            colTitle.put("biz", "业务类型");
//            colTitle.put("createTime", "发送时间");
//            colTitle.put("content", "内容");
//            colTitle.put("status", "发送状态");
//            colTitle.put("reason", "失败原因");
//            List<Map> finalList = new ArrayList<Map>();
//            for (int i = 0; i < list.size(); i++) {
//                SysSmsRecord sm = list.get(i);
//                HashMap<String,Object> map = new HashMap<String,Object>();
//                map.put("id",  list.get(i).getId());
//                map.put("phone",  list.get(i).getPhone());
//                map.put("biz",  list.get(i).getBiz());
//                map.put("createTime",  list.get(i).getCreateTime());
//                map.put("content",  list.get(i).getContent());
//                map.put("status",  list.get(i).getStatus());
//                map.put("reason",  list.get(i).getReason());
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
