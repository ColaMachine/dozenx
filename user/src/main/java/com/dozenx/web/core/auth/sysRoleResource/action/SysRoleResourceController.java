/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2020-8-7 16:07:43
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysRoleResource.action;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
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

import com.dozenx.web.core.auth.sysRolePermission.service.SysRolePermissionService;
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
import com.dozenx.web.core.auth.sysRoleResource.service.SysRoleResourceService;
import com.dozenx.web.core.auth.sysRoleResource.bean.SysRoleResource;
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



@APIs(description = "角色资源关系")
@Controller
@RequestMapping("/sys/auth/role/resource")
public class SysRoleResourceController extends BaseController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRoleResourceController.class);
    /** 权限service **/
    @Autowired
    private SysRoleResourceService sysRoleResourceService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 说明:添加SysRoleResource信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2020-8-7 16:07:43
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API(summary = "添加单个角色资源关系信息",
            description = "添加单个角色资源关系信息",
            parameters = {

                    @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
                            + "  \"roleId\":\"角色id,类型 INTEGER, required = true \" "
                            + "  \"resourceId\":\"资源id,类型 INTEGER, required = true \" "
                            + "  }", in = InType.body, dataType = DataType.STRING, required = true),
            })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO saveInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        SysRoleResource sysRoleResource = getInfoFromMap(bodyParam);
        return sysRoleResourceService.save(sysRoleResource);
    }

    /**
     * 说明:删除SysRoleResource信息
     * @param request
     * @throws Exception
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2020-8-7 16:07:43
     */
    @API(summary = "根据id删除单个角色资源关系信息",
            description = "根据id删除单个角色资源关系信息",
            parameters = {
                    @Param(name = "id", description = "主键", in = InType.path, dataType = DataType.INTEGER, required = true),
            })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)//{id}
    @ResponseBody
    public ResultDTO delete(@PathVariable Integer id, HttpServletRequest request) {
        sysRoleResourceService.delete(id);
        return this.getResult(SUCC);
    }


//    /**
//     * 说明:添加SysRoleResource信息
//     * @param request
//     * @throws Exception
//     * @return ResultDTO
//     * @author dozen.zhang
//     * @date 2020-8-7 16:07:43
//     */
//    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
//    @API(summary = "更新单个角色资源关系信息",
//            description = "更新单个角色资源关系信息",
//            parameters = {
//                    @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"主键,类型 INTEGER, required = false \" "
//                            + "  \"roleId\":\"角色id,类型 INTEGER, required = true \" "
//                            + "  \"resourceId\":\"资源id,类型 INTEGER, required = true \" "
//                            + " } ", in = InType.body, dataType = DataType.STRING, required = true),
//            })
//    @RequestMapping(value = "update", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResultDTO updateInBody(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
//        SysRoleResource sysRoleResource = getInfoFromMap(bodyParam);
//        return sysRoleResourceService.save(sysRoleResource);
//
//
//    }

    /**
     * 说明:ajax请求SysRoleResource信息
     * @author dozen.zhang
     * @date 2020-8-7 16:07:43
     * @return String
     */
    @API(summary = "角色资源关系列表接口",
            description = "角色资源关系列表接口",
            parameters = {

                    @Param(name = "params", description = "{ \"pageSize\":15,\"curPage\":1,"
                            + "\"id\":\"主键,类型 INTEGER, required = false \" "
                            + " \"roleId\":\"角色id,类型 INTEGER, required = true \" "
                            + " \"resourceId\":\"资源id,类型 INTEGER, required = true \" "
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
        String roleId = MapUtils.getString(params, "roleId");
        if (!StringUtil.isBlank(roleId)) {
            params.put("roleId", roleId);
        }
        String resourceId = MapUtils.getString(params, "resourceId");
        if (!StringUtil.isBlank(resourceId)) {
            params.put("resourceId", resourceId);
        }

        params.put("page", page);
        List<SysRoleResource> sysRoleResources = sysRoleResourceService.listByParams4Page(params);
        return ResultUtil.getResult(sysRoleResources, page);
    }


    private SysRoleResource getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
        SysRoleResource sysRoleResource = new SysRoleResource();

        String id = MapUtils.getString(bodyParam, "id");
        if (!StringUtil.isBlank(id)) {
            sysRoleResource.setId(Integer.valueOf(id));
        }
        String roleId = MapUtils.getString(bodyParam, "roleId");
        if (!StringUtil.isBlank(roleId)) {
            sysRoleResource.setRoleId(Integer.valueOf(roleId));
        }
        String resourceId = MapUtils.getString(bodyParam, "resourceId");
        if (!StringUtil.isBlank(resourceId)) {
            sysRoleResource.setResourceId(Integer.valueOf(resourceId));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr = "";
        vu.add("id", id, "主键", new Rule[]{new Digits(11, 0)});
        vu.add("roleId", roleId, "角色id", new Rule[]{new Digits(9, 0), new NotEmpty()});
        vu.add("resourceId", resourceId, "资源id", new Rule[]{new Digits(9, 0), new NotEmpty()});
        validStr = vu.validateString();


        if (StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return sysRoleResource;
    }


    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @API(summary = "角色资源关系列表导出接口",
            description = "角色资源关系列表导出接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", in = InType.params, dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "主键 ", in = InType.params, dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "roleId", description = "角色id ", in = InType.params, dataType = DataType.INTEGER, required = false),// true
                    @Param(name = "resourceId", description = "资源id ", in = InType.params, dataType = DataType.INTEGER, required = false),// true
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
        String roleId = MapUtils.getString(params, "roleId");
        if (!StringUtil.isBlank(roleId)) {
            params.put("roleId", roleId);
        }
        String resourceId = MapUtils.getString(params, "resourceId");
        if (!StringUtil.isBlank(resourceId)) {
            params.put("resourceId", resourceId);
        }

        params.put("page", page);
        List<SysRoleResource> list = sysRoleResourceService.listByParams4Page(params);
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
        colTitle.put("roleId", "角色id");
        colTitle.put("resourceId", "资源id");
        List<Map> finalList = new ArrayList<Map>();
        for (int i = 0; i < list.size(); i++) {
            SysRoleResource sm = list.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", list.get(i).getId());
            map.put("roleId", list.get(i).getRoleId());
            map.put("resourceId", list.get(i).getResourceId());
            finalList.add(map);
        }
        try {
//            if (ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
//                return this.getResult(SUCC, SysConfig.PATH + "/xlstmp/" + randomName, "导出成功");
//            }
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


    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":123,\"name\":\"123\",\"url\":\"123\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":10,\"totalCount\":1,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false}}")
    @RequestMapping(value = "/tree/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    //获取某个人的资源树状json数据
    public ResultDTO tree(HttpServletRequest request, @PathVariable("id") Integer id) {
        return this.getResult(this.sysRoleResourceService.tree(id));
    }


    @API(summary = "2.角色权限批量更新接口",
            consumes = "application/json",
            description = "sysUserController 用户添加接口", parameters = {
            @Param(name = "roleId", description = "角色id"
                    , dataType = DataType.INTEGER, in="body",required = true),
            @Param(name = "resourceIds", description = "权限id数组"
                    , dataType = DataType.ARRAY, in="body",required = true),
    })


    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequestMapping(value = "/update",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public Object roleupdate(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        logger.info("");
//        boolean admin = isAdmin(request);
//        if(!admin){
            Map<String, Object> stringObjectMap = sysRoleResourceService.roleupdateRolePermissions(bodyParam);
            Integer right = MapUtils.getInteger(stringObjectMap, "right");
            if(right!=0){
                throw new ParseException("授权中 包含角色上级角色 不存在权限，请检查上级权限",2000001);
            }
             return this.getResult();
//        }
       // return sysRolePermissionService.updateRolePermissions(bodyParam);
    }
}
