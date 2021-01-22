/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 16:43:06
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysPermission.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.dozenx.common.exception.ValidException;
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

import com.dozenx.web.core.Constants;
import com.dozenx.web.core.annotation.RequiresLogin;
import com.dozenx.web.core.log.ErrorMessage;
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
import com.dozenx.web.core.auth.sysPermission.service.SysPermissionService;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
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

@APIs(description = "权限配置")
@Controller
@RequestMapping("/sys/auth/permission")
public class SysPermissionController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysPermissionController.class);
    /** 权限service **/
    @Autowired
    private SysPermissionService sysPermissionService;
    



  /**
         * 说明:添加SysPermission信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 16:43:06
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个权限配置信息",
            description = "添加单个权限配置信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                + "  \"pid\":\"父主键,类型 INTEGER, required = false \" "
                + "  \"permissionName\":\"权限名称,类型 STRING, required = true \" "
                + "  \"permissionCode\":\"权限代码,类型 STRING, required = true \" "
                + "  \"permissionUrl\":\"权限url,类型 STRING, required = true \" "
                + "  \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                + "  \"status\":\"状态,类型 BYTE, required = true \" "
                + "  \"remark\":\"备注,类型 STRING, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysPermission sysPermission =    getInfoFromMap(bodyParam);


            return sysPermissionService.save(sysPermission);

        }

 /**
         * 说明:删除SysPermission信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 16:43:06
         */
         @API( summary="根据id删除单个权限配置信息",
            description = "根据id删除单个权限配置信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            sysPermissionService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysPermission信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-10-25 16:43:06
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个权限配置信息",
    description = "更新单个权限配置信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                       + "  \"pid\":\"父主键,类型 INTEGER, required = false \" "
                       + "  \"permissionName\":\"权限名称,类型 STRING, required = true \" "
                       + "  \"permissionCode\":\"权限代码,类型 STRING, required = true \" "
                       + "  \"permissionUrl\":\"权限url,类型 STRING, required = true \" "
                       + "  \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                       + "  \"status\":\"状态,类型 BYTE, required = true \" "
                       + "  \"remark\":\"备注,类型 STRING, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysPermission sysPermission =    getInfoFromMap(bodyParam);
    return sysPermissionService.save(sysPermission);

    }
/**
     * 说明:ajax请求SysPermission信息
     * @author dozen.zhang
     * @date 2019-10-25 16:43:06
     * @return String
     */
       @API(summary="权限配置列表接口",
                 description="权限配置列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"编号,类型 INTEGER, required = false \" "
                                 + " \"pid\":\"父主键,类型 INTEGER, required = false \" "
                                 + " \"permissionName\":\"权限名称,类型 STRING, required = true \" "
                                 + " \"permissionCode\":\"权限代码,类型 STRING, required = true \" "
                                 + " \"permissionUrl\":\"权限url,类型 STRING, required = true \" "
                                 + " \"orderNo\":\"排序id,类型 BYTE, required = false \" "
                                 + " \"status\":\"状态,类型 BYTE, required = true \" "
                                 + " \"remark\":\"备注,类型 STRING, required = false \" "
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
        String permissionName = MapUtils.getString(params,"permissionName");
        if(!StringUtil.isBlank(permissionName)){
            params.put("permissionName",permissionName);
        }
        String permissionNameLike = MapUtils.getString(params,"permissionNameLike");
        if(!StringUtil.isBlank(permissionNameLike)){
            params.put("permissionNameLike",permissionNameLike);
        }
        String permissionCode = MapUtils.getString(params,"permissionCode");
        if(!StringUtil.isBlank(permissionCode)){
            params.put("permissionCode",permissionCode);
        }
        String permissionCodeLike = MapUtils.getString(params,"permissionCodeLike");
        if(!StringUtil.isBlank(permissionCodeLike)){
            params.put("permissionCodeLike",permissionCodeLike);
        }
        String permissionUrl = MapUtils.getString(params,"permissionUrl");
        if(!StringUtil.isBlank(permissionUrl)){
            params.put("permissionUrl",permissionUrl);
        }
        String permissionUrlLike = MapUtils.getString(params,"permissionUrlLike");
        if(!StringUtil.isBlank(permissionUrlLike)){
            params.put("permissionUrlLike",permissionUrlLike);
        }
        String orderNo = MapUtils.getString(params,"orderNo");
        if(!StringUtil.isBlank(orderNo)){
            params.put("orderNo",orderNo);
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

        params.put("page",page);
        List<SysPermission> sysPermissions = sysPermissionService.listByParams4Page(params);
        return ResultUtil.getResult(sysPermissions, page);
    }


    private SysPermission getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysPermission sysPermission =new  SysPermission();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysPermission.setId(Integer.valueOf(id));
        }
        String pid = MapUtils.getString(bodyParam,"pid");
        if(!StringUtil.isBlank(pid)){
                sysPermission.setPid(Integer.valueOf(pid));
        }
        String permissionName = MapUtils.getString(bodyParam,"permissionName");
        if(!StringUtil.isBlank(permissionName)){
                sysPermission.setPermissionName(String.valueOf(permissionName));
        }
        String permissionCode = MapUtils.getString(bodyParam,"permissionCode");
        if(!StringUtil.isBlank(permissionCode)){
                sysPermission.setPermissionCode(String.valueOf(permissionCode));
        }
        String permissionUrl = MapUtils.getString(bodyParam,"permissionUrl");
        if(!StringUtil.isBlank(permissionUrl)){
                sysPermission.setPermissionUrl(String.valueOf(permissionUrl));
        }
        String orderNo = MapUtils.getString(bodyParam,"orderNo");
        if(!StringUtil.isBlank(orderNo)){
                sysPermission.setOrderNo(Integer.valueOf(orderNo));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                sysPermission.setStatus(Integer.valueOf(status));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                sysPermission.setRemark(String.valueOf(remark));
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("pid", pid, "父主键",  new Rule[]{new Digits(10,0)});
        vu.add("permissionName", permissionName, "权限名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("permissionCode", permissionCode, "权限代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("permissionUrl", permissionUrl, "权限url",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序id",  new Rule[]{new Digits(1,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2"}),new NotEmpty()});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysPermission;
    }





//       /**
//         * 导出
//         * @param request
//         * @return
//         * @author dozen.zhang
//         */
//        @API(summary="权限配置列表导出接口",
//          description="权限配置列表导出接口",
//          parameters={
//          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
//          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
//             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="pid" , description="父主键 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
//             @Param(name="permissionName" , description="权限名称 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="permissionCode" , description="权限代码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="permissionUrl" , description="权限url ",in=InType.params,dataType = DataType.STRING,required =false),// true
//             @Param(name="orderNo" , description="排序id ",in=InType.params,dataType = DataType.BYTE,required =false),// false
//             @Param(name="status" , description="状态 ",in=InType.params,dataType = DataType.BYTE,required =false),// true
//             @Param(name="remark" , description="备注 ",in=InType.params,dataType = DataType.STRING,required =false),// false
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
//        String pid = MapUtils.getString(params,"pid");
//        if(!StringUtil.isBlank(pid)){
//            params.put("pid",pid);
//        }
//        String permissionName = MapUtils.getString(params,"permissionName");
//        if(!StringUtil.isBlank(permissionName)){
//            params.put("permissionName",permissionName);
//        }
//        String permissionNameLike = MapUtils.getString(params,"permissionNameLike");
//        if(!StringUtil.isBlank(permissionNameLike)){
//            params.put("permissionNameLike",permissionNameLike);
//        }
//        String permissionCode = MapUtils.getString(params,"permissionCode");
//        if(!StringUtil.isBlank(permissionCode)){
//            params.put("permissionCode",permissionCode);
//        }
//        String permissionCodeLike = MapUtils.getString(params,"permissionCodeLike");
//        if(!StringUtil.isBlank(permissionCodeLike)){
//            params.put("permissionCodeLike",permissionCodeLike);
//        }
//        String permissionUrl = MapUtils.getString(params,"permissionUrl");
//        if(!StringUtil.isBlank(permissionUrl)){
//            params.put("permissionUrl",permissionUrl);
//        }
//        String permissionUrlLike = MapUtils.getString(params,"permissionUrlLike");
//        if(!StringUtil.isBlank(permissionUrlLike)){
//            params.put("permissionUrlLike",permissionUrlLike);
//        }
//        String orderNo = MapUtils.getString(params,"orderNo");
//        if(!StringUtil.isBlank(orderNo)){
//            params.put("orderNo",orderNo);
//        }
//        String status = MapUtils.getString(params,"status");
//        if(!StringUtil.isBlank(status)){
//            params.put("status",status);
//        }
//        String remark = MapUtils.getString(params,"remark");
//        if(!StringUtil.isBlank(remark)){
//            params.put("remark",remark);
//        }
//        String remarkLike = MapUtils.getString(params,"remarkLike");
//        if(!StringUtil.isBlank(remarkLike)){
//            params.put("remarkLike",remarkLike);
//        }
//
//             params.put("page",page);
//             List<SysPermission> list = sysPermissionService.listByParams4Page(params);
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
//            colTitle.put("pid", "父主键");
//            colTitle.put("permissionName", "权限名称");
//            colTitle.put("permissionCode", "权限代码");
//            colTitle.put("permissionUrl", "权限url");
//            colTitle.put("orderNo", "排序id");
//            colTitle.put("status", "状态");
//            colTitle.put("remark", "备注");
//            List<Map> finalList = new ArrayList<Map>();
//            for (int i = 0; i < list.size(); i++) {
//                SysPermission sm = list.get(i);
//                HashMap<String,Object> map = new HashMap<String,Object>();
//                map.put("id",  list.get(i).getId());
//                map.put("pid",  list.get(i).getPid());
//                map.put("permissionName",  list.get(i).getPermissionName());
//                map.put("permissionCode",  list.get(i).getPermissionCode());
//                map.put("permissionUrl",  list.get(i).getPermissionUrl());
//                map.put("orderNo",  list.get(i).getOrderNo());
//                map.put("status",  list.get(i).getStatus());
//                map.put("remark",  list.get(i).getRemark());
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


    @API(summary = "权限列表树状接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysPermissionController 用户列表分页查询接口", parameters = {

    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"url\":\"123\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false}}")

    @RequestMapping(value = "/tree",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object tree( HttpServletRequest request,@RequestParam(name="params",required=true) String paramStr) {
        Map<String,Object> params = JsonUtil.fromJson(paramStr,Map.class);

        List<SysPermission> sysPermissions = sysPermissionService.listByParams(params);


        List<SysPermission> finalList = new ArrayList<SysPermission>();//最终返回前台的list

        //组装成树状结构
        for(int i=0,length=sysPermissions.size();i<length;i++){//倒序 方便找到后删除
            SysPermission sysPermission = sysPermissions.get(i);

            if(sysPermission.getPid()==0){
                finalList.add(sysPermission);
                sysPermission.childs=new ArrayList<>();
                for(int j=0;j<length;j++){//倒序 方便找到后删除
                    SysPermission child = sysPermissions.get(j);//遍历所有的项目查找所有子项

                    if(child.getPid() == sysPermission.getId()){
                        sysPermission.childs.add(child);//塞入到childs中 并从集合中删除
                        // sysMenuTree.remove(j);
                    }
                }
                // sysMenuTree.remove(i);
            }
        }

        return this.getResult(sysPermissions);
    }




    public SysPermission getParamFromMap(Map<String,Object> bodyParam) throws Exception {
        SysPermission sysPermission =new SysPermission();
        Integer id = MapUtils.getInteger(bodyParam,"id");
        sysPermission.setId(id);

        Integer pid = MapUtils.getInteger(bodyParam,"pid");

        if(pid!=null){
            sysPermission.setPid(pid);
        }

        String name = MapUtils.getString(bodyParam,"name");

        if(!StringUtil.isBlank(name)){
            sysPermission.setPermissionName(name);
        }

        String code = MapUtils.getString(bodyParam,"code");

        if(!StringUtil.isBlank(code)){
            sysPermission.setPermissionCode(code);
        }

        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysPermission.setOrderNo(orderNo);
        }
        Integer status = MapUtils.getInteger(bodyParam,"status");

        if(status!=null){
            sysPermission.setStatus(status);
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysPermission.setRemark(remark);
        }
        String url = MapUtils.getString(bodyParam,"url");
        if(!StringUtil.isBlank(url)){
            sysPermission.setPermissionUrl(url);
        }

        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("pid", pid+"", "父主键",  new Rule[]{new Digits(10,0)});
        vu.add("name", name, "权限名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("code", code, "权限代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序id",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","9"})});

        vu.add("url", url, "url",  new Rule[]{new Length(0,250)});

        vu.add("remark", remark, "备注",  new Rule[]{new Length(20)});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            throw new ValidException("302",validStr);
        }
        return sysPermission;
    }

    @API(summary = "权限添加接口",

            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {



            @Param(name = "pid", description = "父节点id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "名称"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "code", description = "编号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "url", description = "url"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序标号"
                    , dataType = DataType.LONG, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/add",method=RequestMethod.POST,produces="application/json")
    @ResponseBody
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    public Object add(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam ) throws Exception {
        SysPermission sysPermission =getParamFromMap(bodyParam);
        return sysPermissionService.save(sysPermission);

    }


    @API(summary = "权限修改接口",
            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {
            @Param(name = "id", description = "id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "pid", description = "父节点id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "名称"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "code", description = "编号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "url", description = "url"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序标号"
                    , dataType = DataType.LONG, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    public Object update( HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam ) throws Exception {
        SysPermission sysPermission =getParamFromMap(bodyParam);
        return sysPermissionService.save(sysPermission);
    }

    @API(summary = "权限删除接口",

            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = {

            @Param(name = "id", description = "id", dataType = DataType.LONG, in="PATH",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")

    @RequestMapping(value = "/del/{id}" ,method=RequestMethod.DELETE,produces="application/json")
    @ResponseBody
    public Object deleteRestFul(@PathVariable("id") Integer id, HttpServletRequest request ) {

        if(id==null ){
            return this.getResult(10202003, ErrorMessage.getErrorMsg("err.param.null","用户id"));
        }

        sysPermissionService.delete(id);//将状态为改成9
        return this.getResult(SUCC);
    }



    @API(summary = "查看详情接口",

            consumes = "application/x-www-form-urlencoded",
            description = "查看详情接口", parameters = {

            @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 编号, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")

    @RequestMapping(value = "/view/{id}" ,method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object viewRestFul(@PathVariable ("id") Integer id , HttpServletRequest request) {

        HashMap<String,Object> result =new HashMap<String,Object>();
        if(id>0){
            SysPermission bean = sysPermissionService.selectByPrimaryKey(Integer.valueOf(id));
            return this.getResult(bean);

        }
        return this.getResult(10102300, ErrorMessage.getErrorMsg("err.param.null","id"));

    }



    /**
     * @Author: dozen.zhang
     * @Description:当前用户树状菜单
     * @Date: 2018/2/8
     */
    @API(summary = "当前用户权限集合",
            consumes = "application/x-www-form-urlencoded",
            description = " ", parameters = { })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"username\":\"123\",\"password\":\"123\",\"nkname\":\"123\",\"type\":null,\"status\":1,\"email\":null,\"telno\":\"13969696969\",\"idcard\":\"23\",\"sex\":0,\"birth\":1517414400000,\"integral\":123,\"address\":\"123\",\"wechat\":\"123\",\"qq\":123,\"face\":\"static/img/timg.jpeg\",\"remark\":\"123\",\"outId\":null,\"createtime\":1517901790000,\"updatetime\":1517901790000}],\"msg\":null,\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"other\":null,\"right\":true}")
    @RequiresLogin
    @RequestMapping(value = "/my",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResultDTO listPermissions(HttpServletRequest request){
        return this.getResult( request.getSession().getAttribute(Constants.SESSION_PERMISSIONS));//塞入到用户session中
    }
}
