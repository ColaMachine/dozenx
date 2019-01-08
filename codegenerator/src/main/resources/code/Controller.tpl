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
import ${table.pkg}.${abc}.service.${Abc}Service;
import ${table.pkg}.${abc}.bean.${Abc};
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

<#if table.mapper??>
import cola.machine.bean.${table.mapper.mapper};
import cola.machine.service.${table.mapper.mapper}Service;
import cola.machine.bean.${table.mapper.child};
import cola.machine.service.${table.mapper.child}Service;

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
     * 说明:ajax请求${table.name}信息
     * @author dozen.zhang
     * @date ${.now}
     * @return String
     */
       @API(summary="${table.remark}列表接口",
                 description="${table.remark}列表接口",
                 parameters={
                 @Param(name="pageSize", description="分页大小", dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页", dataType= DataType.INTEGER,required = true),
                  <#list table.cols as col>
                    @Param(name="${col.name}" , description="${col.remark}",dataType = DataType.<@apiType>${col.type}</@apiType>,required =false),// ${col.nn?c}
                   </#list>
         })
    @RequestMapping(value = "/list.json" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception{
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
${getSearchParam}
        params.put("page",page);
        List<${Abc}> ${abc}s = ${abc}Service.listByParams4Page(params);
        return ResultUtil.getResult(${abc}s, page);
    }
    
   /**
    * 说明:ajax请求${table.name}信息 无分页版本
    * @return ResultDTO 返回结果
    * @author dozen.zhang
    * @date ${.now}
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public ResultDTO listAll(HttpServletRequest request) {
        ${getSearchParam}
        List<${Abc}> ${abc}s = ${abc}Service.listByParams(params);
        return ResultUtil.getDataResult(${abc}s);
    }

   /**
    * 说明:查看单条信息
    * @param request 发请求
    * @return String
    * @author dozen.zhang
    * @date ${.now}
    */
     @API( summary="根据id查询单个${table.remark}信息",
               description = "根据id查询单个${table.remark}信息",
               parameters={
                       @Param(name="id" , description="${table.pk.name}",in=InType.path,dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(@PathVariable <@javaType>${table.pk.type}</@javaType> ${table.pk.name},HttpServletRequest request) {
             ${controllerViewMethod}
        }



     /**
        * 说明:查看单条信息
        * @param request 发请求
        * @return String
        * @author dozen.zhang
        * @date ${.now}
        */
      @API( summary="根据id查询单个${table.remark}信息",
               description = "根据id查询单个${table.remark}信息",
               parameters={
                       @Param(name="id" , description="${table.pk.name}",dataType= DataType.LONG,required = true),
               })
        @RequestMapping(value = "/view.json",method = RequestMethod.GET)
        @ResponseBody
        public ResultDTO getById(HttpServletRequest request) {
         String id = request.getParameter("id");
            ${Abc} bean = ${abc}Service.selectByPrimaryKey(<@javaType>${table.pk.type}</@javaType>.valueOf(id));
          //  HashMap<String,ResultDTO> result =new HashMap<String,ResultDTO>();
           // result.put("bean", bean);
            return this.getResult(bean);
        }


    /**
     * 说明:更新${table.name}信息
     * 
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date ${.now}
     */
      @API( summary="更新id更新单个${table.remark}信息",
        description = "更新id更新单个${table.remark}信息",
        parameters={
          <#list table.cols as col>
           @Param(name="${col.name}" , description="${col.remark}",type="<@apiType>${col.type}</@apiType>",required = ${col.nn?c}),
          </#list>
        })
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "update.json",method = RequestMethod.PUT)///{id}
    @ResponseBody
    public ResultDTO update(HttpServletRequest request) throws Exception {//@PathVariable <@javaType>${table.pk.type}</@javaType> id,
        ${Abc} ${abc} =new  ${Abc}();
        /*<#list table.cols as col>
        String ${col.name} = request.getParameter("${col.name}");
        if(!StringUtil.isBlank(${col.name})){
            ${abc}.set${col.name[0]?upper_case}${col.name[1..]}(<@javaType>${col.type}</@javaType>.valueOf(${col.name})) ;
        }
        </#list>*/
${setParam}
        //valid
${validCode}
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
         * 说明:添加${table.name}信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date ${.now}
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
        @API( summary="添加单个${table.remark}信息",
            description = "添加单个${table.remark}信息",
            parameters={
              <#list table.cols as col>
               @Param(name="${col.name}" , description="${col.remark}",dataType = DataType.<@apiType>${col.type}</@apiType>,required = ${col.nn?c}),
              </#list>
            })
        @RequestMapping(value = "add.json",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO add(HttpServletRequest request) throws Exception {
            ${Abc} ${abc} =new  ${Abc}();
            /*<#list table.cols as col>
            String ${col.name} = request.getParameter("${col.name}");
            if(!StringUtil.isBlank(${col.name})){
                ${abc}.set${col.name[0]?upper_case}${col.name[1..]}(<@javaType>${col.type}</@javaType>.valueOf(${col.name})) ;
            }
            </#list>*/
    ${setParam}
            //valid
    ${validCode}
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
                      <#list table.cols as col>
                       @Param(name="${col.name}" , description="${col.remark}",dataType = DataType.<@apiType>${col.type}</@apiType>,required = ${col.nn?c}),
                      </#list>
                    })
                @RequestMapping(value = "save.json",method = RequestMethod.POST)
                @ResponseBody
                public ResultDTO save(HttpServletRequest request) throws Exception {
                    ${Abc} ${abc} =new  ${Abc}();
                    /*<#list table.cols as col>
                    String ${col.name} = request.getParameter("${col.name}");
                    if(!StringUtil.isBlank(${col.name})){
                        ${abc}.set${col.name[0]?upper_case}${col.name[1..]}(<@javaType>${col.type}</@javaType>.valueOf(${col.name})) ;
                    }
                    </#list>*/
            ${setParam}
                    //valid
            ${validCode}
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

   <#if table.mapper??>
   <#if table.mapper.mapper==Abc>
    @RequestMapping(value = "/msave.json")
    @ResponseBody
    public ResultDTO msave(HttpServletRequest request) throws Exception {
        String ${table.mapper.parentid}s= request.getParameter("${table.mapper.parentid}s");
        String ${table.mapper.childid}s= request.getParameter("${table.mapper.childid}s");
        return ${table.name?uncap_first}Service.msave( ${table.mapper.parentid}s, ${table.mapper.childid}s);
    }
    </#if>
    </#if>
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
         @Param(name="${table.pk.name}" , description="${table.pk.remark}",dataType= DataType.<@apiType>${table.pk.type}</@apiType>,required = true),
        })
    @RequestMapping(value = "/delete.json",method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(HttpServletRequest request) {//@PathVariable <@javaType>${table.pk.type}</@javaType> id,
        String ${table.pk.name}Str = request.getParameter("${table.pk.name}");
        if(StringUtil.isBlank(${table.pk.name}Str)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        <@javaType>${table.pk.type}</@javaType> ${table.pk.name} = <@javaType>${table.pk.type}</@javaType>.valueOf(${table.pk.name}Str);
        ${abc}Service.delete(${table.pk.name});
        return this.getResult(SUCC);
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
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public ResultDTO multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("${table.pk.name}s");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        <@javaType>${table.pk.type}</@javaType> idAry[]=new <@javaType>${table.pk.type}</@javaType>[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String <@getabc>${table.pk.name}</@getabc> = idStrAry[i];
            ${idvalid}
            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotBlank(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=<@javaType>${table.pk.type}</@javaType>.valueOf(idStrAry[i]);
        }
       return  ${abc}Service.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public ResultDTO exportExcel(HttpServletRequest request){
       ${getSearchParam}
        // 查询list集合
        List<${Abc}> list =${abc}Service.listByParams(params);
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
                return this.getResult(SUCC,fileName,"导出成功");
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
    public ResultDTO importExcel(@RequestParam("file") MultipartFile file){
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

                        ${Abc} bean = getInfoFromMap(params);

                      //  if (count > 0) {

                      //      logger.warn("邮箱已经存在:" + email);
                       //     errorMsg.append("邮箱已经存在:" + email);
                         //   continue;

                       // }

                        try {
                            ${abc}Service.save(bean);
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
                    return this.getResult(3090182,"导入完成，成功导入" + success + "条，失败" + fail + "条。" + errorMsg.toString());

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
         * 说明: 跳转到${table.name}列表页面
         *
         * @return
         * @return String
         * @author dozen.zhang
         * @date 2015年11月15日下午12:30:45
         */
        @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
        public String listHtml() {
            return "/static/html/${Abc}List.html";
        }

        @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
        public String listMapperHtml() {
            return "/static/html/${Abc}ListMapper.html";
        }


    /**
     * 说明:跳转编辑页面
     * @param request 发请求
     * @return String
     * @author dozen.zhang
     * @date ${.now}
     */
    @RequestMapping(value = "/edit.htm",method = RequestMethod.GET)
    public String editHtml( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/${Abc}Edit.html";
    }
    /**
         * 说明:跳转查看页面
         * @param request 发请求
         * @return String
         * @author dozen.zhang
         * @date ${.now}
         */
    @RequestMapping(value = "/view.htm",method = RequestMethod.GET)
    public String viewHtml( HttpServletRequest request) {
        return "/static/html/${Abc}View.html";
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
                          <#list table.cols as col>
                           @Param(name="${col.name}" , description="${col.remark}${col.comment!}"  ,in=InType.body,dataType = DataType.<@apiType>${col.type}</@apiType>,required = ${col.nn?c}),
                          </#list>
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
        <#list table.cols as col>
        @Param(name="${col.name}" , description="${col.remark}  ${col.comment!}",in=InType.body,dataType = DataType.<@apiType>${col.type}</@apiType>,required = ${col.nn?c}),
        </#list>
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
                 @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
                 @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
                  <#list table.cols as col>
                    @Param(name="${col.name}" , description="${col.remark}  ${col.comment!}",in=InType.params,dataType = DataType.<@apiType>${col.type}</@apiType>,required =false),// ${col.nn?c}
                   </#list>
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
        @RequestMapping(value = "/export")
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
