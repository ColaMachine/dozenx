/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2019-10-25 16:43:07
 * 文件说明: 
 */

package com.dozenx.web.core.auth.sysRole.action;
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

import com.dozenx.web.core.annotation.RequiresPermission;
import com.dozenx.web.core.auth.sysRolePermission.service.SysRolePermissionService;
import com.dozenx.web.core.log.ErrorMessage;
import com.dozenx.web.core.log.OperLogUtil;
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
import com.dozenx.web.core.auth.sysRole.service.SysRoleService;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
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

@APIs(description = "角色")
@Controller
@RequestMapping("/sys/auth/role")
public class SysRoleController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    /** 权限service **/
    @Autowired
    private SysRoleService sysRoleService;
    



  /**
         * 说明:添加SysRole信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 16:43:07
         */
        // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
        @API( summary="添加单个角色信息",
            description = "添加单个角色信息",
            parameters={

                @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                + "  \"roleName\":\"角色名,类型 STRING, required = true \" "
                + "  \"roleCode\":\"角色代码,类型 STRING, required = true \" "
                + "  \"orderNo\":\"排序,类型 INTEGER, required = true \" "
                + "  \"status\":\"状态,类型 INTEGER, required = true \" "
                + "  \"remark\":\"备注,类型 STRING, required = false \" "
                + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                + "  }", in = InType.body, dataType = DataType.STRING, required = true),
                  })
        @RequestMapping(value = "add",method = RequestMethod.POST)
        @ResponseBody
        public ResultDTO saveInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
            SysRole sysRole =    getInfoFromMap(bodyParam);
            return sysRoleService.save(sysRole);

        }

 /**
         * 说明:删除SysRole信息
         * @param request
         * @throws Exception
         * @return ResultDTO
         * @author dozen.zhang
         * @date 2019-10-25 16:43:07
         */
         @API( summary="根据id删除单个角色信息",
            description = "根据id删除单个角色信息",
            parameters={
             @Param(name="id" , description="编号",in=InType.path,dataType= DataType.INTEGER,required = true),
            })
        @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)//{id}
        @ResponseBody
        public ResultDTO delete(@PathVariable Integer id,HttpServletRequest request) {
            sysRoleService.delete(id);
            return this.getResult(SUCC);
        }


    /**
    * 说明:添加SysRole信息
    * @param request
    * @throws Exception
    * @return ResultDTO
    * @author dozen.zhang
    * @date 2019-10-25 16:43:07
    */
    // @RequiresPermissions(value={"auth:edit" ,"auth:save" },logical=Logical.OR)
    @API( summary="更新单个角色信息",
    description = "更新单个角色信息",
    parameters={
              @Param(name = "body", description = "父亲节点id", schema = "{   \"id\":\"编号,类型 INTEGER, required = false \" "
                       + "  \"roleName\":\"角色名,类型 STRING, required = true \" "
                       + "  \"roleCode\":\"角色代码,类型 STRING, required = true \" "
                       + "  \"orderNo\":\"排序,类型 INTEGER, required = true \" "
                       + "  \"status\":\"状态,类型 INTEGER, required = true \" "
                       + "  \"remark\":\"备注,类型 STRING, required = false \" "
                       + "  \"createTime\":\"创建时间,类型 DATE_TIME, required = false \" "
                       + " } ", in = InType.body, dataType = DataType.STRING, required = true),
    })
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultDTO updateInBody(HttpServletRequest request,@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
    SysRole sysRole =    getInfoFromMap(bodyParam);
    return sysRoleService.save(sysRole);

    }
/**
     * 说明:ajax请求SysRole信息
     * @author dozen.zhang
     * @date 2019-10-25 16:43:07
     * @return String
     */
       @API(summary="角色列表接口",
                 description="角色列表接口",
                 parameters={

                        @Param(name = "params",  description = "{ \"pageSize\":15,\"curPage\":1,"
                          +"\"id\":\"编号,类型 INTEGER, required = false \" "
                                 + " \"roleName\":\"角色名,类型 STRING, required = true \" "
                                 + " \"roleCode\":\"角色代码,类型 STRING, required = true \" "
                                 + " \"orderNo\":\"排序,类型 INTEGER, required = true \" "
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
        String roleName = MapUtils.getString(params,"roleName");
        if(!StringUtil.isBlank(roleName)){
            params.put("roleName",roleName);
        }
        String roleNameLike = MapUtils.getString(params,"roleNameLike");
        if(!StringUtil.isBlank(roleNameLike)){
            params.put("roleNameLike",roleNameLike);
        }
        String roleCode = MapUtils.getString(params,"roleCode");
        if(!StringUtil.isBlank(roleCode)){
            params.put("roleCode",roleCode);
        }
        String roleCodeLike = MapUtils.getString(params,"roleCodeLike");
        if(!StringUtil.isBlank(roleCodeLike)){
            params.put("roleCodeLike",roleCodeLike);
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
        List<SysRole> sysRoles = sysRoleService.listByParams4Page(params);
        return ResultUtil.getResult(sysRoles, page);
    }


    private SysRole getInfoFromMap(Map<String, Object> bodyParam) throws Exception {
       SysRole sysRole =new  SysRole();

                String id = MapUtils.getString(bodyParam,"id");
        if(!StringUtil.isBlank(id)){
                sysRole.setId(Integer.valueOf(id));
        }
        String roleName = MapUtils.getString(bodyParam,"roleName");
        if(!StringUtil.isBlank(roleName)){
                sysRole.setRoleName(String.valueOf(roleName));
        }
        String roleCode = MapUtils.getString(bodyParam,"roleCode");
        if(!StringUtil.isBlank(roleCode)){
                sysRole.setRoleCode(String.valueOf(roleCode));
        }
        String orderNo = MapUtils.getString(bodyParam,"orderNo");
        if(!StringUtil.isBlank(orderNo)){
                sysRole.setOrderNo(Integer.valueOf(orderNo));
        }
        String status = MapUtils.getString(bodyParam,"status");
        if(!StringUtil.isBlank(status)){
                sysRole.setStatus(Integer.valueOf(status));
        }
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
                sysRole.setRemark(String.valueOf(remark));
        }
        String createTime = MapUtils.getString(bodyParam,"createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                sysRole.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                sysRole.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
                ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "编号",  new Rule[]{new Digits(10,0)});
        vu.add("roleName", roleName, "角色名",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("roleCode", roleCode, "角色代码",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(11,0)});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","9"})});
        vu.add("remark", remark, "备注",  new Rule[]{new Length(255)});
        vu.add("createTime", createTime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();


        if(StringUtil.isNotBlank(validStr)) {
            throw new ParamException(10002000, validStr);//bean的校验
        }
        return  sysRole;
    }





       /**
         * 导出
         * @param request
         * @return
         * @author dozen.zhang
         */
        @API(summary="角色列表导出接口",
          description="角色列表导出接口",
          parameters={
          @Param(name="pageSize", description="分页大小",in=InType.params, dataType= DataType.INTEGER,required = true),
          @Param(name="curPage", description="当前页",in=InType.params, dataType= DataType.INTEGER,required = true),
             @Param(name="id" , description="编号 ",in=InType.params,dataType = DataType.INTEGER,required =false),// false
             @Param(name="roleName" , description="角色名 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="roleCode" , description="角色代码 ",in=InType.params,dataType = DataType.STRING,required =false),// true
             @Param(name="orderNo" , description="排序 ",in=InType.params,dataType = DataType.INTEGER,required =false),// true
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
        String roleName = MapUtils.getString(params,"roleName");
        if(!StringUtil.isBlank(roleName)){
            params.put("roleName",roleName);
        }
        String roleNameLike = MapUtils.getString(params,"roleNameLike");
        if(!StringUtil.isBlank(roleNameLike)){
            params.put("roleNameLike",roleNameLike);
        }
        String roleCode = MapUtils.getString(params,"roleCode");
        if(!StringUtil.isBlank(roleCode)){
            params.put("roleCode",roleCode);
        }
        String roleCodeLike = MapUtils.getString(params,"roleCodeLike");
        if(!StringUtil.isBlank(roleCodeLike)){
            params.put("roleCodeLike",roleCodeLike);
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
             List<SysRole> list = sysRoleService.listByParams4Page(params);
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
            colTitle.put("roleName", "角色名");
            colTitle.put("roleCode", "角色代码");
            colTitle.put("orderNo", "排序");
            colTitle.put("status", "状态");
            colTitle.put("remark", "备注");
            colTitle.put("createTime", "创建时间");
            List<Map> finalList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                SysRole sm = list.get(i);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",  list.get(i).getId());
                map.put("roleName",  list.get(i).getRoleName());
                map.put("roleCode",  list.get(i).getRoleCode());
                map.put("orderNo",  list.get(i).getOrderNo());
                map.put("status",  list.get(i).getStatus());
                map.put("remark",  list.get(i).getRemark());
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



    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @API(summary = "角色列表接口",
            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 角色列表分页查询接口", parameters = {

            @Param(name = "params", description = "{\"curPage\":1,\"pageSize\":20,\"code\":\"\",\"name\":\"\"}", dataType = DataType.STRING, in="query",required = true),
    })
    @APIResponse(value = "{\"r\":0,\"data\":[{\"id\":333,\"name\":\"ccc\",\"code\":\"ccc\",\"orderNo\":1,\"remark\":\"ccc\"},{\"id\":123123,\"name\":\"管理员1\",\"code\":\"role_admin\",\"orderNo\":1,\"remark\":\"管理员1\"}],\"page\":{\"curPage\":1,\"totalPage\":1,\"pageSize\":20,\"totalCount\":14,\"beginIndex\":0,\"hasPrePage\":false,\"hasNextPage\":false},\"right\":true}")
    @RequestMapping(value = "/drop/list",method=RequestMethod.GET,produces="application/json")
    @ResponseBody
    public Object droplistRestful(HttpServletRequest request) {
        List<SysRole> sysRoles = sysRoleService.listByParams(new HashMap<String,Object>());
        return ResultUtil.getDataResult(sysRoles);
    }


    /**
     * 说明:添加角色信息
     *
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */

    @API(summary = "角色添加接口",
            consumes = "application/json",
            description = "sysRoleController 角色添加接口", parameters = {
            @Param(name = "body", description = "{'roleName':'角色名称','roleCode':'角色代号','orderNo':'排序号','remark':'角色备注'}"
                    , dataType = DataType.STRING, in="body",required = true),

    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/add",method=RequestMethod.POST,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object add(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =new  SysRole();

        String roleName = MapUtils.getString(bodyParam,"roleName");
        if(!StringUtil.isBlank(roleName)){
            sysRole.setRoleName(roleName);
        }
        String roleCode = MapUtils.getString(bodyParam,"roleCode");

        if(!StringUtil.isBlank(roleCode)){
            sysRole.setRoleCode(roleCode);
        }
        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysRole.setOrderNo(orderNo);
        }
        sysRole.setStatus(0);
        sysRole.setCreateTime(DateUtil.getNowTimeStamp());
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysRole.setRemark(remark);
        }
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("roleName", roleName, "角色名",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ZHONGWEN_ALPHA_NUMBER_PATTERN)});
        vu.add("roleCode", roleCode, "角色代码",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ALPHA_NUMBER_PATTERN)});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(3,0)});//考虑到角色没必要强制排序 将从修改中移除此项目 2018年2月24日09:05:59 ,new NotEmpty()

        vu.add("remark", remark, "备注",  new Rule[]{new Length(255),new CharRegex()});
        //vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }
        OperLogUtil.add(request,"角色管理","角色新增","角色新增:"+sysRole.getRoleName());

        return sysRoleService.save(sysRole);

    }

    @API(summary = "角色更新接口",
            consumes = "application/json",
            description = "sysRoleController 角色添加接口", parameters = {
            @Param(name = "id", description = "角色id"
                    , dataType = DataType.LONG, in="body",required = true),
            @Param(name = "name", description = "角色名"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "orderNo", description = "排序号"
                    , dataType = DataType.LONG, in="body",required = false),
            @Param(name = "code", description = "角色代号"
                    , dataType = DataType.STRING, in="body",required = true),
            @Param(name = "remark", description = "角色备注"
                    , dataType = DataType.STRING, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/update/{id}",method=RequestMethod.PUT,produces="application/json")
    @RequiresPermission
    @ResponseBody

    public Object update(HttpServletRequest request,@PathVariable Integer id ,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =getInfoFromMap(bodyParam);
        OperLogUtil.add(request,"角色管理","角色更新","角色更新:"+sysRole.getRoleName());
        return sysRoleService.save(sysRole);

    }

    @API(summary = "角色详情接口",
            consumes = "application/json",
            description = "角色详情接口", parameters = {
            @Param(name = "id", description = "/view/{id}", dataType = DataType.STRING, in="path",required = true),
    })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"id\": 123, \"username\": \"123\", \"password\": \"123\", \"nkname\": \"123\", \"status\": 1, \"telno\": \"13969696969\", \"idcard\": \"23\", \"sex\": 0, \"birth\": \"Feb 1, 2018 12:00:00 AM\", \"integral\": 123, \"address\": \"123\", \"wechat\": \"123\", \"qq\": 123, \"face\": \"static/img/timg.jpeg\", \"remark\": \"123\", \"createTime\": \"Feb 6, 2018 3:23:10 PM\", \"updateTime\": \"Feb 6, 2018 3:23:10 PM\" } }")
    @RequestMapping(value = "/view/{id}" ,method=RequestMethod.GET,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object view(@PathVariable ("id") Integer id , HttpServletRequest request) {

        if(id==null || id ==0 ){

            return this.getResult(10202002, ErrorMessage.getErrorMsg("err.param.null","角色id"));
        }
        SysRole bean = sysRoleService.selectByPrimaryKey(id);
        return this.getResult(bean);


    }

    @API(summary = "角色删除接口",

            consumes = "application/x-www-form-urlencoded",
            description = "sysUserController 角色删除接口", parameters = {

            @Param(name = "id", description = "用户id", dataType = DataType.LONG, in="PATH",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    @RequiresPermission
    @RequestMapping(value = "/del/{id}" ,method=RequestMethod.DELETE,produces="application/json" )
    @ResponseBody
    public Object deleteRestFul(@PathVariable("id") Integer id, HttpServletRequest request ) {

        if(id==null ){
            return this.getResult(10202003, ErrorMessage.getErrorMsg("err.param.null","角色id"));
        }
        SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
        if(sysRole!=null ) {
            OperLogUtil.add(request, "角色管理", "角色删除", "角色删除:" + sysRole.getRoleName());
            sysRoleService.delete(id);//将状态为改成9
        }
        return this.getResult(SUCC);
    }




    @API(summary = "角色添加接口",
            consumes = "application/json",
            description = "sysRoleController 角色添加接口", parameters = {
            @Param(name = "body", description = "{\"roleName\":\"角色名称\",\"roleCode\":\"角色代号\",\"orderNo\":\"排序号\",\"remark\":\"角色备注\",\"permissionIds\":[1,2,3,4]}"
                    , dataType = DataType.STRING, in="body",required = true),

    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/addRoleAndPermission",method=RequestMethod.POST,produces="application/json")
    @RequiresPermission
    @ResponseBody
    public Object addRoleAndPermission(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =new  SysRole();

        String roleName = MapUtils.getString(bodyParam,"roleName");
        if(!StringUtil.isBlank(roleName)){
            sysRole.setRoleName(roleName);
        }
        String roleCode = MapUtils.getString(bodyParam,"roleCode");

        if(!StringUtil.isBlank(roleCode)){
            sysRole.setRoleCode(roleCode);
        }
        Integer orderNo = MapUtils.getInteger(bodyParam,"orderNo");
        if(orderNo!=null){
            sysRole.setOrderNo(orderNo);
        }
        sysRole.setStatus(0);
        sysRole.setCreateTime(DateUtil.getNowTimeStamp());
        String remark = MapUtils.getString(bodyParam,"remark");
        if(!StringUtil.isBlank(remark)){
            sysRole.setRemark(remark);
        }
        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";

        vu.add("roleName", roleName, "角色名",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ZHONGWEN_ALPHA_NUMBER_PATTERN)});
        vu.add("roleCode", roleCode, "角色代码",  new Rule[]{new Length(20),new NotEmpty(),new Regex(RegexConstants.ALPHA_NUMBER_PATTERN)});
        vu.add("orderNo", orderNo, "排序",  new Rule[]{new Digits(3,0)});//考虑到角色没必要强制排序 将从修改中移除此项目 2018年2月24日09:05:59 ,new NotEmpty()

        vu.add("remark", remark, "备注",  new Rule[]{new Length(255),new CharRegex()});
        //vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        validStr = vu.validateString();
        if(StringUtil.isNotBlank(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

         sysRoleService.save(sysRole);
        if(sysRole.getId() == null ){
            logger.error("mybatis 需要把sysRole新增后产生的id带上");
            throw new BizException(30504671,"mybatis 需要把sysRole新增后产生的id带上");
        }
        bodyParam.put("roleId",sysRole.getId());

        return this.getResult(sysRolePermissionService.updateRolePermissions(bodyParam));

    }
    @Autowired
    SysRolePermissionService sysRolePermissionService ;
    @API(summary = "角色及其权限更新接口",
            consumes = "application/json",
            description = "sysRoleController 角色及其权限更新接口", parameters = {
            @Param(name = "body", description = "{\"id\":3,\"roleName\":\"角色名称\",\"roleCode\":\"角色代号\",\"orderNo\":\"排序号\",\"remark\":\"角色备注\",\"permissionIds\":[1,2,3,4]}"
                    , dataType = DataType.STRING, in="body",required = true),
//            @Param(name = "id", description = "角色id"
//                    , dataType = DataType.LONG, in="body",required = true),
//            @Param(name = "name", description = "角色名"
//                    , dataType = DataType.STRING, in="body",required = true),
//            @Param(name = "orderNo", description = "排序号"
//                    , dataType = DataType.LONG, in="body",required = false),
//            @Param(name = "code", description = "角色代号"
//                    , dataType = DataType.STRING, in="body",required = true),
//            @Param(name = "remark", description = "角色备注"
//                    , dataType = DataType.STRING, in="body",required = true),
//            @Param(name = "permissionIds", description = "权限id数组"
//                    , dataType = DataType.ARRAY, in="body",required = true),
    })
    @APIResponse(value = "{\"r\":0,msg:'xxxx'}")
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/updateRoleAndPermission",method=RequestMethod.PUT,produces="application/json")
    @RequiresPermission
    @ResponseBody

    public Object updateRoleAndPermission(HttpServletRequest request,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        SysRole sysRole =getInfoFromMap(bodyParam);

         sysRoleService.save(sysRole);
        bodyParam.put("roleId",sysRole.getId());
        return this.getResult(sysRolePermissionService.updateRolePermissions(bodyParam));
    }

}
