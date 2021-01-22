/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-12-17 14:16:58
 * 文件说明: 
 */

package com.dozenx.web.module.interfaceapi.module.action;
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

import com.dozenx.web.core.tree.TreeData;
import com.dozenx.web.core.tree.TreeNode;
import com.dozenx.web.module.interfaceapi.interfaceinfo.pojo.InterfaceInfo;
import com.dozenx.web.module.interfaceapi.interfaceinfo.service.InterfaceInfoService;
import org.bouncycastle.math.raw.Mod;
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
import com.dozenx.web.module.interfaceapi.module.service.ModuleService;
import com.dozenx.web.module.interfaceapi.module.bean.Module;
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

@APIs(description = "模块")
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ModuleController.class);
    /** 权限service **/
    @Autowired
    private ModuleService moduleService;
    



  /**
         * 说明:添加Module信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-12-17 14:16:58
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个模块信息",
            description = "添加单个模块信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 INTEGER, required = false \" "
                + "  \"moduleName\":\"模块名称,类型 STRING, required = true \" "
                + "  \"pid\":\"自关联父id,类型 INTEGER, required = true \" "
                + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            Module module =    getInfoFromMap(bodyParam);


            return moduleService.save(module);

        }

 /**
         * 说明:删除Module信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-12-17 14:16:58
         */
         @API( summary="根据id删除单个模块信息",
            description = "根据id删除单个模块信息",
            parameters={
             @Param(name="id" , description="id",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            moduleService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加Module信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-12-17 14:16:58
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个模块信息",
    description = "更新单个模块信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"id,类型 INTEGER, required = false \" "
                       + "  \"moduleName\":\"模块名称,类型 STRING, required = true \" "
                       + "  \"pid\":\"自关联父id,类型 INTEGER, required = true \" "
                       + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    Module module =    getInfoFromMap(bodyParam);
    return moduleService.save(module);

    }
/**
     * 说明:ajax请求Module信息
     * @author dozen.zhang
     * @date 2019-12-17 14:16:58
     * @return String
     */
       @API(summary="模块列表接口",
                 description="模块列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"id,类型 INTEGER, required = false \" "
                                 + " \"moduleName\":\"模块名称,类型 STRING, required = true \" "
                                 + " \"pid\":\"自关联父id,类型 INTEGER, required = true \" "
                                 + " \"createTime\":\"创建时间,类型 DATE_TIME, required = true \" "
                                 + "  }",
                        in = InType.query, dataType = DataType.STRING, required = true),

         })
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request,@RequestParam(name = "params", required = true) String paramStr ) throws Exception{
        List list = moduleService.listByParams(new HashMap());

        TreeData treeData  =  new TreeData();
           treeData.loadData(list);
        HashMap<String, Object> params = JsonUtil.fromJson(paramStr, HashMap.class);
         Page page = RequestUtil.getPage(params);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }

                String id = MapUtils.getString(params,"id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String moduleName = MapUtils.getString(params,"moduleName");
        if(!StringUtil.isBlank(moduleName)){
            params.put("moduleName",moduleName);
        }
        String moduleNameLike = MapUtils.getString(params,"moduleNameLike");
        if(!StringUtil.isBlank(moduleNameLike)){
            params.put("moduleNameLike",moduleNameLike);
        }
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
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
        List<Module> modules = moduleService.listByParams4Page(params);
        return ResultUtil.getResult(modules, page);
    }


    private Module getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       Module module =new  Module();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                module.setId(Integer.valueOf(id));
        }
        String moduleName = MapUtils.getString(bodyParam,"moduleName");
        if(!StringUtil.isBlank(moduleName)){
                module.setModuleName(String.valueOf(moduleName));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                module.setPid(Integer.valueOf(pid));
        }


        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "id",  new Rule[]{new Digits(8,0)});
        vu.add("moduleName", moduleName, "模块名称",  new Rule[]{new Length(200),new NotEmpty()});
        vu.add("pid", pid, "自关联父id",  new Rule[]{new Digits(8,0),new NotEmpty()});

        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  module;
    }





       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="模块列表导出接口",
          description="模块列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="id ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="moduleName" , description="模块名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="pid" , description="自关联父id ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
             @Param(name="createTime" , description="创建时间 ",in=InType.params,dataType = DataType.DATE_TIME,required =false),// true
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
        String moduleName = MapUtils.getString(params,"moduleName");
        if(!StringUtil.isBlank(moduleName)){
            params.put("moduleName",moduleName);
        }
        String moduleNameLike = MapUtils.getString(params,"moduleNameLike");
        if(!StringUtil.isBlank(moduleNameLike)){
            params.put("moduleNameLike",moduleNameLike);
        }
        String pid = MapUtils.getString(params,"pid");
        if(!StringUtil.isBlank(pid)){
            params.put("pid",pid);
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
             List<Module> list = moduleService.listByParams4Page(params);
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
            colTitle.put("moduleName", "模块名称");
            colTitle.put("pid", "自关联父id");
            colTitle.put("createTime", "创建时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Module sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("moduleName",  list.get(i).getModuleName());
                map.put("pid",  list.get(i).getPid());
                map.put("createTime",  list.get(i).getCreateTime());
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



    @API(summary="模块列表接口",
            description="模块列表接口",
            parameters={
            })
    @RequestMapping(value = "/tree" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO tree( ) throws Exception{
        List<Module> list = moduleService.listByParams(new HashMap());

        TreeData treeData  =  new TreeData();
        treeData.root = new TreeNode(0,"root",0);
        List<TreeNode> treeNodes  =new ArrayList<>();
        for(Module module: list){
            TreeNode treeNode =new TreeNode(module.getId(),module.getModuleName(),module.getPid(),0);
            treeNodes.add(treeNode);
        }


//        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.selectAll();
//        for(InterfaceInfo interfaceInfo : interfaceInfoList ){
//            TreeNode treeNode =new TreeNode(1000000+interfaceInfo.getId(),interfaceInfo.getInterfaceName(),interfaceInfo.getModuleId(),1);
//            treeNodes.add(treeNode);
//        }
        treeData.loadData(treeNodes);
        return ResultUtil.getDataResult(treeData.root);
    }



    @API(summary="模块列表接口",
            description="模块列表接口",
            parameters={
            })
    @RequestMapping(value = "/tree/leaf" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO treeAndLeaf( ) throws Exception{
        List<Module> list = moduleService.listByParams(new HashMap());

        TreeData treeData  =  new TreeData();
        treeData.root = new TreeNode(0,"root",0);
        List<TreeNode> treeNodes  =new ArrayList<>();
        for(Module module: list){
            TreeNode treeNode =new TreeNode(module.getId(),module.getModuleName(),module.getPid(),0);
            treeNodes.add(treeNode);
        }


        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.selectAll();
        for(InterfaceInfo interfaceInfo : interfaceInfoList ){
            TreeNode treeNode =new TreeNode(1000000+interfaceInfo.getId(),interfaceInfo.getInterfaceName(),interfaceInfo.getModuleId(),1);
            treeNodes.add(treeNode);
        }
        treeData.loadData(treeNodes);
        return ResultUtil.getDataResult(treeData.root);
    }


    @API(summary="模块列表接口",
            description="模块列表接口",
            parameters={
            })
    @RequestMapping(value = "/tree/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO treeAndLeaf( @PathVariable(name="id")  String id) throws Exception{
        List treeNodes =new ArrayList();
        Map map =new HashMap();
        map.put("moduleId",id);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.listByParams(map);
        for(InterfaceInfo interfaceInfo : interfaceInfoList ){
            TreeNode treeNode =new TreeNode(1000000+interfaceInfo.getId(),interfaceInfo.getHttpType()+" "+interfaceInfo.getInterfaceName(),interfaceInfo.getModuleId(),1);
            treeNodes.add(treeNode);
        }



        HashMap params =new HashMap();
        params.put("pid",id);
        List<Module> list = moduleService.listByParams(params);

        TreeData treeData  =  new TreeData();
        treeData.root = new TreeNode(0,"root",0);
        List<TreeNode> foldTreeNodes  =new ArrayList<>();
        for(Module module: list){
            TreeNode treeNode =new TreeNode(module.getId(),module.getModuleName(),module.getPid(),0);
            treeNodes.add(treeNode);
        }
        return ResultUtil.getDataResult(treeNodes);
    }
    @Resource
    InterfaceInfoService interfaceInfoService;

}
