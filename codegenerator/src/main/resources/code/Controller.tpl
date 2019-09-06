/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date ${.now}
 * 文件说明: 
 */

package ${table.pkg}.${abc}.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.dozenx.common.util.MapUtils;
import com.dozenx.common.util.JsonUtil;
import com.dozenx.common.util.ExcelUtil;
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
import ${table.pkg}.${abc}.service.${Abc}Service;
import ${table.pkg}.${abc}.bean.${Abc};
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

<#if table.mapper??>
import ${table.pkg}.${table.mapper.mapper};
import ${table.pkg}.service.${table.mapper.mapper}Service;
import ${table.pkg}.bean.${table.mapper.child};
import ${table.pkg}.service.${table.mapper.child}Service;

</#if>
@APIs(description = "${table.remark}")
@Controller
@RequestMapping("${table.baseUrl}")
public class ${Abc}Controller extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(${Abc}Controller.class);
    /** 权限service **/
    @Autowired
    private ${Abc}Service ${abc}Service;
    
    <#if table.mapper??>
     <#if table.mapper.mapper==table.name>

     <#else>
    @Autowired
    private <@getAbc>${table.mapper.mapper}</@getAbc>Service <@getabc>${table.mapper.mapper}</@getabc>Service;

     @Autowired
    private <@getAbc>${table.mapper.child}</@getAbc>Service <@getabc>${table.mapper.child}</@getabc>Service;
</#if>
    </#if>



  /**
         * 说明:添加${table.name}信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date ${.now}
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个${table.remark}信息",
            description = "添加单个${table.remark}信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{  <#list table.cols as col> \"${col.name}\":\"${col.remark!}${col.comment!},类型 <@apiType>${col.type}</@apiType>, required = ${col.nn?c} \" "
                + " </#list> }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            ${Abc} ${abc} =    getInfoFromMap(bodyParam);


              <#if table.mapper??>
                <#if table.mapper.parent==table.name>
            String childids = request.getParameter("childids");
            return ${abc}Service.saveWithChilds(${abc},childids);
            <#else>
              return ${abc}Service.save(${abc});
              </#if>

            <#else>
            return ${abc}Service.save(${abc});
            </#if>

        }

 /**
         * 说明:删除${table.name}信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date ${.now}
         */
         @API( summary="根据id删除单个${table.remark}信息",
            description = "根据id删除单个${table.remark}信息",
            parameters={
             @Param(name="${table.pk.name}" , description="${table.pk.remark}",in=InType.path,dataType= DataType.<@apiType>${table.pk.type}</@apiType>,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable <@javaType>${table.pk.type}</@javaType> id,HttpServletRequest request) {
            ${abc}Service.delete(${table.pk.name});
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加${table.name}信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date ${.now}
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个${table.remark}信息",
    description = "更新单个${table.remark}信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{  <#list table.cols as col> \"${col.name}\":\"${col.remark!}${col.comment!},类型 <@apiType>${col.type}</@apiType>, required = ${col.nn?c} \" "
                       + " </#list>} ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    ${Abc} ${abc} =    getInfoFromMap(bodyParam);
    <#if table.mapper??>
    <#if table.mapper.parent==table.name>
    String childids = request.getParameter("childids");
    return ${abc}Service.saveWithChilds(${abc},childids);
    <#else>
    return ${abc}Service.save(${abc});
    </#if>

    <#else>
    return ${abc}Service.save(${abc});
    </#if>

    }
/**
     * 说明:ajax请求${table.name}信息
     * @author dozen.zhang
     * @date ${.now}
     * @return String
     */
       @API(summary="${table.remark}列表接口",
                 description="${table.remark}列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"<#list table.cols as col>\"${col.name}\":\"${col.remark!}${col.comment!},类型 <@apiType>${col.type}</@apiType>, required = ${col.nn?c} \" "
                                 + " </#list> }",
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

        ${getSearchParamFromParams}
        params.put("page",page);
        List<${Abc}> ${abc}s = ${abc}Service.listByParams4Page(params);
        return ResultUtil.getResult(${abc}s, page);
    }


    private ${Abc} getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       ${Abc} ${abc} =new  ${Abc}();

        ${getInfoParam}
        //valid
        ${validCode2}

        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  ${abc};
    }





       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="${table.remark}列表导出接口",
          description="${table.remark}列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
           <#list table.cols as col>
             @Param(name="${col.name}" , description="${col.remark} ${col.comment!}",in=InType.params,dataType = DataType.<@apiType>${col.type}</@apiType>,required =false),// ${col.nn?c}
            </#list>
          })
        @RequestMapping(value = "/export", method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO exportExcelInBody(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{

             HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
              Page page = RequestUtil.getPage(params);
             if(page ==null){
                  return this.getWrongResultFromCfg("err.param.page");
             }

             ${getSearchParamFromParams}
             params.put("page",page);
             List<${Abc}> list = ${abc}Service.listByParams4Page(params);
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
            <#list table.cols as col>
            colTitle.put("${col.name}", "${col.remark}");
            </#list>
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                ${Abc} sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
              <#list table.cols as col>
                map.put("${col.name}",  list.get(i).get${col.name[0]?upper_case}${col.name[1..]}());
              </#list>
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
