/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2020-8-7 16:07:43
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysResource.action;
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
import com.dozenx.web.core.auth.sysResource.service.SysResourceService;
import com.dozenx.web.core.auth.sysResource.bean.SysResource;
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

@APIs(description = "资源配置")
@Controller
@RequestMapping("/sys/auth/resource")
public class SysResourceController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysResourceController.class);
    /** 权限service **/
    @Autowired
    private SysResourceService sysResourceService;
    



  /**
         * 说明:添加SysResource信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2020-8-7 16:07:43
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个资源配置信息",
            description = "添加单个资源配置信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                + "  \"pid\":\"父主键,类型 INTEGER, required = true \" "
                + "  \"name\":\"资源名称,类型 STRING, required = true \" "
                + "  \"code\":\"资源代码,类型 STRING, required = true \" "
                + "  \"type\":\"资源分类,类型 STRING, required = true \" "
                + "  \"url\":\"资源对应URL,类型 STRING, required = false \" "
                + "  \"order\":\"排序id,类型 INTEGER, required = false \" "
                + "  \"status\":\"状态,类型 INTEGER, required = true \" "
                + "  \"remark\":\"备注,类型 STRING, required = false \" "
                + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysResource sysResource =    getInfoFromMap(bodyParam);


            return sysResourceService.save(sysResource);

        }

 /**
         * 说明:删除SysResource信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2020-8-7 16:07:43
         */
         @API( summary="根据id删除单个资源配置信息",
            description = "根据id删除单个资源配置信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            sysResourceService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysResource信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2020-8-7 16:07:43
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个资源配置信息",
    description = "更新单个资源配置信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                       + "  \"pid\":\"父主键,类型 INTEGER, required = true \" "
                       + "  \"name\":\"资源名称,类型 STRING, required = true \" "
                       + "  \"code\":\"资源代码,类型 STRING, required = true \" "
                       + "  \"type\":\"资源分类,类型 STRING, required = true \" "
                       + "  \"url\":\"资源对应URL,类型 STRING, required = false \" "
                       + "  \"order\":\"排序id,类型 INTEGER, required = false \" "
                       + "  \"status\":\"状态,类型 INTEGER, required = true \" "
                       + "  \"remark\":\"备注,类型 STRING, required = false \" "
                       + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysResource sysResource =    getInfoFromMap(bodyParam);
    return sysResourceService.save(sysResource);

    }
/**
     * 说明:ajax请求SysResource信息
     * @author dozen.zhang
     * @date 2020-8-7 16:07:43
     * @return String
     */
       @API(summary="资源配置列表接口",
                 description="资源配置列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"编号,类型 INTEGER, required = false \" "
                                 + " \"pid\":\"父主键,类型 INTEGER, required = true \" "
                                 + " \"name\":\"资源名称,类型 STRING, required = true \" "
                                 + " \"code\":\"资源代码,类型 STRING, required = true \" "
                                 + " \"type\":\"资源分类,类型 STRING, required = true \" "
                                 + " \"url\":\"资源对应URL,类型 STRING, required = false \" "
                                 + " \"order\":\"排序id,类型 INTEGER, required = false \" "
                                 + " \"status\":\"状态,类型 INTEGER, required = true \" "
                                 + " \"remark\":\"备注,类型 STRING, required = false \" "
                                 + " \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
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
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String code = MapUtils.getString(params,"code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = MapUtils.getString(params,"codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = MapUtils.getString(params,"typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }
        String url = MapUtils.getString(params,"url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = MapUtils.getString(params,"urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String order = MapUtils.getString(params,"order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
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

        params.put("page",page);
        List<SysResource> sysResources = sysResourceService.listByParams4Page(params);
        return ResultUtil.getResult(sysResources, page);
    }


    private SysResource getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysResource sysResource =new  SysResource();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysResource.setId(Integer.valueOf(id));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                sysResource.setPid(Integer.valueOf(pid));
        }
        String name = MapUtils.getString(bodyParam,"name");
        if(!StringUtil.isBlank(name)){
                sysResource.setName(String.valueOf(name));
        }
        String code = MapUtils.getString(bodyParam,"code");
        if(!StringUtil.isBlank(code)){
                sysResource.setCode(String.valueOf(code));
        }
        String type = MapUtils.getString(bodyParam,"type");
        if(!StringUtil.isBlank(type)){
                sysResource.setType(String.valueOf(type));
        }
        String url = MapUtils.getString(bodyParam,"url");
        if(!StringUtil.isBlank(url)){
                sysResource.setUrl(String.valueOf(url));
        }
        String order = MapUtils.getString(bodyParam,"order");
        if(!StringUtil.isBlank(order)){
                sysResource.setOrder(Integer.valueOf(order));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                sysResource.setStatus(Integer.valueOf(status));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                sysResource.setRemark(String.valueOf(remark));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sysResource.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sysResource.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("pid", pid, "父主键",  new Rule[]{new Digits(10,0),new NotEmpty()});
        vu.add("name", name, "资源名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "资源代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("type", type, "资源分类",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("url", url, "资源对应URL",  new Rule[]{new Length(255)});
        vu.add("order", order, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysResource;
    }





       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="资源配置列表导出接口",
          description="资源配置列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="pid" , description="父主键 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="name" , description="资源名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="code" , description="资源代码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="type" , description="资源分类 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="url" , description="资源对应URL ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="order" , description="排序id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="remark" , description="备注 ",in=InType.params,dataType = DataType.STRING,required =false),// false
             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// false
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
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
        }
        String name = MapUtils.getString(params,"name");
        if(!StringUtil.isBlank(name)){
            params.put("name",name);
        }
        String nameLike = MapUtils.getString(params,"nameLike");
        if(!StringUtil.isBlank(nameLike)){
            params.put("nameLike",nameLike);
        }
        String code = MapUtils.getString(params,"code");
        if(!StringUtil.isBlank(code)){
            params.put("code",code);
        }
        String codeLike = MapUtils.getString(params,"codeLike");
        if(!StringUtil.isBlank(codeLike)){
            params.put("codeLike",codeLike);
        }
        String type = MapUtils.getString(params,"type");
        if(!StringUtil.isBlank(type)){
            params.put("type",type);
        }
        String typeLike = MapUtils.getString(params,"typeLike");
        if(!StringUtil.isBlank(typeLike)){
            params.put("typeLike",typeLike);
        }
        String url = MapUtils.getString(params,"url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = MapUtils.getString(params,"urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }
        String order = MapUtils.getString(params,"order");
        if(!StringUtil.isBlank(order)){
            params.put("order",order);
        }
        String status = MapUtils.getString(params,"status");
        if(!StringUtil.isBlank(status)){
            params.put("status",status);
        }
        String remark = MapUtils.getString(params,"remark");
        if(!StringUtil.isBlank(remark)){
            params.put("remark",remark);
        }
        String remarkLike = MapUtils.getString(params,"remarkLike");
        if(!StringUtil.isBlank(remarkLike)){
            params.put("remarkLike",remarkLike);
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

             params.put("page",page);
             List<SysResource> list = sysResourceService.listByParams4Page(params);
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
            colTitle.put("id", "编号");
            colTitle.put("pid", "父主键");
            colTitle.put("name", "资源名称");
            colTitle.put("code", "资源代码");
            colTitle.put("type", "资源分类");
            colTitle.put("url", "资源对应URL");
            colTitle.put("order", "排序id");
            colTitle.put("status", "状态");
            colTitle.put("remark", "备注");
            colTitle.put("createTime", "创建时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                SysResource sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("pid",  list.get(i).getPid());
                map.put("name",  list.get(i).getName());
                map.put("code",  list.get(i).getCode());
                map.put("type",  list.get(i).getType());
                map.put("url",  list.get(i).getUrl());
                map.put("order",  list.get(i).getOrder());
                map.put("status",  list.get(i).getStatus());
                map.put("remark",  list.get(i).getRemark());
                map.put("createTime",  list.get(i).getCreateTime());
                finalList.add(map);
            }
            try {
//                if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                    return this.getResult(SUCC,SysConfig.PATH+"/xlstmp/"+randomName,"导出成功");
//                }
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
